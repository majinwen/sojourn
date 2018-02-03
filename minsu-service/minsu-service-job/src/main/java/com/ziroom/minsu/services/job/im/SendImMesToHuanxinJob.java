package com.ziroom.minsu.services.job.im;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.cms.api.inner.ShortChainMapService;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.message.api.inner.HuanxinImRecordService;
import com.ziroom.minsu.services.message.dto.MsgFirstDdvisoryRequest;
import com.ziroom.minsu.services.message.entity.SysMsgVo;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.msg.HouseCardEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.minsu.valenum.msg.MsgExtTypeEnum;
import com.ziroom.minsu.valenum.msg.RunStatusEnum;
import com.ziroom.minsu.valenum.msg.TargetTypeEnum;
import org.codehaus.jackson.type.TypeReference;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * <p>5分钟给房客回复</p>
 * <p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR> 注意：本job发送短信每次一条，调用message服务异步线程发送环信每次50条，所以对于同一房东，有环信或短信不能全部收到的风险
 * <BR>	修改日期	2017/4/27 11:24 修改人 wangwentao 修改内容 加入发送短信的逻辑
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @since 1.0
 */
public class SendImMesToHuanxinJob extends AsuraJob {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(SendImMesToHuanxinJob.class);

    /**
     * 每5分钟执行一次
     * 0 0/5 * * * ?
     *
     * @author wangwentao 2017/4/27 11:24
     */
    @Override
    public void run(JobExecutionContext context) {

        LogUtil.info(logger, "【SendImMesToHuanxinJob】开始执行");
        try {
            MsgFirstDdvisoryRequest msgFirstDdvisoryRequest = new MsgFirstDdvisoryRequest();
            msgFirstDdvisoryRequest.setRunTime(DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
            List<Integer> listStatus = new LinkedList<Integer>();
            listStatus.add(RunStatusEnum.NOT_RUN.getValue());
            listStatus.add(RunStatusEnum.RUN_FAILED.getValue());
            msgFirstDdvisoryRequest.setListStatus(listStatus);
            msgFirstDdvisoryRequest.setLimit(MessageConst.limit);
            HuanxinImRecordService huanxinImRecordService = (HuanxinImRecordService) ApplicationContext.getContext().getBean("job.huanxinImRecordService");
            int page = 0;
            PagingResult<SysMsgVo> pagingResult = null;
            do {
                try {
                    LogUtil.info(logger, "【SendImMesToHuanxinJob】第{}次循环", page++);
                    msgFirstDdvisoryRequest.setPage(page);
                    if (Check.NuNObj(huanxinImRecordService)) {
                        LogUtil.info(logger, "【huanxinImRecordService is null】");
                    }
                    DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(huanxinImRecordService.queryByPage(JsonEntityTransform.Object2Json(msgFirstDdvisoryRequest)));
                    LogUtil.info(logger, "queryPage返回{}", dto.toJsonString());
                    pagingResult = dto.parseData("pagingResult", new TypeReference<PagingResult<SysMsgVo>>() {
                    });
                    List<SysMsgVo> listVo = pagingResult.getRows();
                    LogUtil.info(logger, "listVo={}", listVo.toString());
                    if (!Check.NuNCollection(listVo)) {
                        List<SendImMsgRequest> listSendImMsgRequest = new ArrayList<SendImMsgRequest>();
                        for (SysMsgVo msgFirstAdvisoryEntity : listVo) {
                            SendImMsgRequest sendImMsgRequest = new SendImMsgRequest();
                            listSendImMsgRequest.add(sendImMsgRequest);
                            sendImMsgRequest.setFrom(MessageConst.IM_UID_PRE + msgFirstAdvisoryEntity.getToUid());
                            String houseName = "";
                            String appChatRecordsExtStr = msgFirstAdvisoryEntity.getMsgContentExt();
                            if (!Check.NuNStr(appChatRecordsExtStr)) {
                                Map<String, Object> appChatRecordsExt = (Map<String, Object>) JsonEntityTransform.json2Map(appChatRecordsExtStr);
                                appChatRecordsExt.put("houseCard", String.valueOf(HouseCardEnum.HOUSE_CARD_GENERAL.getVal()));
                                appChatRecordsExt.put("msgType", MsgExtTypeEnum.AUTO_REPLY.getCode() + "");
                                appChatRecordsExt.put("msgSenderType", UserTypeEnum.LANDLORD_HUAXIN.getUserCode() + "");
                                appChatRecordsExt.put("roleType", msgFirstAdvisoryEntity.getRoleType() + "");
                                appChatRecordsExt.put("em_ignore_notification", true);
                                houseName = (String) appChatRecordsExt.get("houseName");
                                sendImMsgRequest.setExtMap(appChatRecordsExt);
                            }
                            sendImMsgRequest.setMsg(MessageConst.IM_CONTENT);
                            sendImMsgRequest.setTarget_type(TargetTypeEnum.SINGLE_USER.getHuanxinTargetType());
                            String[] target = new String[]{MessageConst.IM_UID_PRE + msgFirstAdvisoryEntity.getFromUid()};
                            sendImMsgRequest.setTarget(target);
                            //发送短信
                            sendMsg(msgFirstAdvisoryEntity, houseName);
                        }
                        huanxinImRecordService.sendImMesToHuanxin(JsonEntityTransform.Object2Json(listSendImMsgRequest));
                    } else {
                        pagingResult.setTotal(0);
                        LogUtil.info(logger, "【SendImMesToHuanxinJob】没有需要执行的任务");
                    }
                } catch (Exception e) {
                    LogUtil.error(logger, "发送IM消息到环信 异常,继续下一次循环e={},page={}", e, page);
                }
            } while (Check.NuNObj(pagingResult) || pagingResult.getTotal() > 0);
        } catch (Exception e) {
            LogUtil.error(logger, " 发送IM消息到环信 异常e={}", e);
        }
        LogUtil.info(logger, "【SendImMesToHuanxinJob】结束执行");
    }


    /**
     * 发送短信调用common服务
     *
     * @param
     * @return
     * @author wangwentao
     * @created 2017/4/27 15:14
     */
    private void sendMsg(SysMsgVo msgFirstAdvisoryEntity, String houseName) {
        LogUtil.info(logger, "发送短信开始");
        long startTime = System.currentTimeMillis();
        String tenantUid = msgFirstAdvisoryEntity.getFromUid();//首次咨询 from：房客
        String landlordUid = msgFirstAdvisoryEntity.getToUid();//首次咨询 to：房东
        try {
            ShortChainMapService shortChainMapService = (ShortChainMapService) ApplicationContext.getContext().getBean("job.shortChainMapService");
            //生成短链
            ResourceBundle resource = ResourceBundle.getBundle("job");
            String basePath = resource.getString("BASE_PATH");
            LogUtil.info(logger, "basePath={}", basePath);
            if (!Check.NuNStr(basePath)) {
                String url = basePath + "/imApp/ee5f86/goToApp?toChatUsername=" + landlordUid +
                        "&msgSenderType=" + UserTypeEnum.LANDLORD.getUserCode() + "&uid=" + tenantUid;
                String resultJson = shortChainMapService.generateShortLink(url, landlordUid);
                LogUtil.info(logger, "短链生成返回结果:{}", resultJson);
                DataTransferObject shortChainDto = JsonEntityTransform.json2DataTransferObject(resultJson);
                if (shortChainDto.getCode() == DataTransferObject.SUCCESS) {
                    String shortLink = (String) shortChainDto.getData().get("shortLink");
                    //查询电话号码
                    String phone = getPhoneByUid(landlordUid);
                    LogUtil.info(logger, "电话号码为:{}", phone);

                    String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.MSG_LANDLORD_UNREAD_MSG.getCode());
                    if (!Check.NuNStr(phone) && !Check.NuNStr(msgCode)) {
                        SmsTemplateService smsTemplateService = (SmsTemplateService) ApplicationContext.getContext().getBean("job.smsTemplateService");
                        SmsRequest smsRequest = new SmsRequest();
                        Map<String, String> param = new HashMap<String, String>();
                        param.put("{1}", houseName);
                        param.put("{2}", shortLink);
                        smsRequest.setParamsMap(param);
                        smsRequest.setMobile(phone);
                        smsRequest.setSmsCode(msgCode);
                        smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
                        long endTime = System.currentTimeMillis();
                        LogUtil.info(logger, "发送短信成功，短信参数:houseName{}, shortLink:{}, 用时{}ms", houseName, shortLink, startTime - endTime);
                    }
                }
            }
        } catch (BusinessException e) {
            LogUtil.error(logger, "发送短信异常,SysMsgVo={}, hosueName={}, e={}", msgFirstAdvisoryEntity.toJsonStr(), houseName, e);
        }
    }

    /**
     * @param
     * @return String
     * @author wangwentao
     * @created 2017/4/27 15:17
     */
    private String getPhoneByUid(String uid) {
        String phone = "";
        try {
            CustomerMsgManagerService customerMsgManagerService = (CustomerMsgManagerService) ApplicationContext.getContext().getBean("job.customerMsgManagerService");
            DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.getCutomerVo(uid));
            LogUtil.info(logger, "通过用户uid获取用户返回(redis)结果:{}", customerDto.toJsonString());
            if (customerDto.getCode() == DataTransferObject.SUCCESS) {
                CustomerVo customerVo = customerDto.parseData("customerVo", new TypeReference<CustomerVo>() {
                });
                if (!Check.NuNObj(customerVo) && !Check.NuNStr(customerVo.getShowMobile())) {
                    phone = customerVo.getShowMobile();
                }
            }
        } catch (BusinessException e) {
            LogUtil.error(logger, "发送短信异常,获取phone失败uid={},e={}", uid, e);
        }
        return phone;
    }
}

