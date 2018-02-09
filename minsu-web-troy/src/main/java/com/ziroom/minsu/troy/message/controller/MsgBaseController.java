package com.ziroom.minsu.troy.message.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgAdvisoryFollowupEntity;
import com.ziroom.minsu.entity.message.MsgBaseEntity;
import com.ziroom.minsu.entity.message.MsgFirstAdvisoryEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;
import com.ziroom.minsu.services.message.api.inner.MsgAdvisoryFollowupService;
import com.ziroom.minsu.services.message.api.inner.MsgBaseService;
import com.ziroom.minsu.services.message.api.inner.MsgFirstAdvisoryService;
import com.ziroom.minsu.services.message.api.inner.MsgHouseService;
import com.ziroom.minsu.services.message.api.inner.SysComplainService;
import com.ziroom.minsu.services.message.dto.LandlordComplainRequest;
import com.ziroom.minsu.services.message.dto.MsgBaseRequest;
import com.ziroom.minsu.services.message.entity.MsgAdvisoryChatVo;
import com.ziroom.minsu.services.message.entity.SysComplainVo;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.api.inner.OrderFollowService;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.troy.message.service.TroyMsgFirstAdvisoryService;
import com.ziroom.minsu.troy.message.vo.FirstAdvisoryFollowVO;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.msg.IsReadEnum;
import com.ziroom.minsu.valenum.msg.MsgAdvisoryFollowEnum;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <p>留言消息视图层</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("message")
public class MsgBaseController {


    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(MsgBaseController.class);

    @Value("#{'${pic_size}'.trim()}")
    private String picSize;

    @Resource(name = "message.msgHouseService")
    private MsgHouseService msgHouseService;

    @Resource(name = "message.msgBaseService")
    private MsgBaseService msgBaseService;

    @Resource(name = "message.sysComplainService")
    private SysComplainService sysComplainService;

    @Resource(name = "order.orderCommonService")
    private OrderCommonService orderCommonService;

    @Resource(name = "order.orderFollowService")
    private OrderFollowService orderFollowService;

    @Resource(name = "message.msgFirstAdvisoryService")
    private MsgFirstAdvisoryService msgFirstAdvisoryService;

    @Resource(name = "customer.customerInfoService")
    private CustomerInfoService customerInfoService;

    @Resource(name = "house.troyHouseMgtService")
    private TroyHouseMgtService troyHouseMgtService;

    @Resource(name = "basedata.confCityService")
    private ConfCityService confCityService;

    @Resource(name = "message.msgAdvisoryFollowupService")
    private MsgAdvisoryFollowupService msgAdvisoryFollowupService;

    @Resource(name = "customer.customerMsgManagerService")
    private CustomerMsgManagerService customerMsgManagerService;

    @Resource(name = "search.searchServiceApi")
    private SearchService searchService;

    @Resource(name="message.TroyMsgFirstAdvisoryService")
    private TroyMsgFirstAdvisoryService troyMsgFirstAdvisoryService;

    /**
     * 查询当前用户和房东的留言  轮询查询
     *
     * @param request
     * @author yd
     * @created 2016年4月19日 下午6:21:18
     */
    @RequestMapping("/queryMessageBase")
    public ModelAndView queryMessageBase(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/message/listMessageBase");
        return mv;
    }

    /**
     * 根据时间查询当前用户和房东的留言
     *
     * @param request
     * @param msgBaseRequest
     * @return
     * @author yd
     * @created 2016年4月19日 下午8:14:43
     */
    @RequestMapping("/listMsgBaseByTime")
    public @ResponseBody
    List<MsgBaseEntity> listMsgBaseByTime(HttpServletRequest request, @ModelAttribute("msgBaseRequest") MsgBaseRequest msgBaseRequest) {

        msgBaseRequest.setMsgHouseFid("8a9e9c8b541e32c001541e32c0150000");

        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.msgBaseService.queryByCondition(JsonEntityTransform.Object2Json(msgBaseRequest)));

        List<MsgBaseEntity> lisBaseEntities = dto.parseData("listBaseEntities", new TypeReference<List<MsgBaseEntity>>() {
        });
        return lisBaseEntities;
    }

    /**
     * 根据时间查询当前用户和房东的留言
     *
     * @param request
     * @param msgBaseRequest
     * @return
     * @author yd
     * @created 2016年4月19日 下午8:14:43
     */
    @RequestMapping("/saveMsgBase")
    public @ResponseBody
    String saveMsgBase(HttpServletRequest request) {

        String msgContent = request.getParameter("msgContent");
        String msgSentType = request.getParameter("msgSentType");
        String msgHouseFid = request.getParameter("msgHouseFid");

        String reslut = "0";
        if (Check.NuNStr(msgHouseFid) || Check.NuNStr(msgSentType) || Check.NuNStr(msgHouseFid) || UserTypeEnum.getUserTypeByCode(Integer.valueOf(msgSentType)) == null) {
            return reslut;
        }
        MsgBaseEntity msgBaseEntity = new MsgBaseEntity();

        msgBaseEntity.setCreateTime(new Date());
        msgBaseEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
        msgBaseEntity.setIsRead(IsReadEnum.READ.getCode());
        msgBaseEntity.setMsgContent(msgContent);
        msgBaseEntity.setMsgHouseFid(msgHouseFid);
        msgBaseEntity.setMsgSenderType(Integer.valueOf(msgSentType));

        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.msgBaseService.save(JsonEntityTransform.Object2Json(msgBaseEntity)));
        reslut = dto.getData().get("result").toString();
        return reslut;
    }

    /**
     * 房东反馈信息查询页
     *
     * @param request
     * @return
     */
    @RequestMapping("/landlordComplainMsg")
    public ModelAndView landlordComplainMsg(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/message/landlordComplainMsg");
        return mv;
    }


    /**
     * 获取房东反馈信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/getLandlordComplain")
    @ResponseBody
    public PageResult getLandlordComplain(@ModelAttribute("paramRequest") LandlordComplainRequest paramRequest, HttpServletRequest request) {
        String resultJson = sysComplainService.queryByCondition(JsonEntityTransform.Object2Json(paramRequest));
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
        List<SysComplainVo> list = resultDto.parseData("list", new TypeReference<List<SysComplainVo>>() {
        });

        for (SysComplainVo sysComplainVo : list) {
            String content = sysComplainVo.getContent();
            if (!Check.NuNStr(content) && content.length() > 10) {
                content = content.substring(0, 10) + "...";
                sysComplainVo.setContent(content);
            }
            if (Check.NuNStr(sysComplainVo.getComplainUsername())) {
                sysComplainVo.setComplainUsername("------");
            }
            if (Check.NuNStr(sysComplainVo.getComplainMphone())) {
                sysComplainVo.setComplainMphone("------");
            }
        }
        PageResult pageResult = new PageResult();
        pageResult.setRows(list);
        pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
        return pageResult;
    }

    /**
     * 获取一条房东反馈信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/getLandlordComplainById")
    @ResponseBody
    public DataTransferObject getOneLandlordComplain(@ModelAttribute("complainId") String id, HttpServletRequest request) {
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(id)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto;
        }

        try {
            String json = sysComplainService.selectByPrimaryKey(id);
            dto = JsonEntityTransform.json2DataTransferObject(json);
            if (dto.getCode() != DataTransferObject.SUCCESS) {
                LogUtil.error(logger, "查询申请详情失败,fid={},msg:{}", id, dto.toJsonString());
                return dto;
            }
        } catch (Exception e) {
            LogUtil.error(logger, "查询申请详情失败,fid={},error:{}", id, e);
        }

        return dto;
    }

    @RequestMapping("/toMessageFollowDetail")
    @ResponseBody
    public ModelAndView toMessageFollowDetail(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/messageFollow/messageFollowDetail");
        String msgBaseFid = request.getParameter("msgBaseFid");
        LogUtil.info(logger, "msgBaseFid:{}", msgBaseFid);
        String followStatus = request.getParameter("followStatus");
        LogUtil.info(logger, "followStatus:{}", followStatus);
        String showButton = request.getParameter("showButton");
        FirstAdvisoryFollowVO followVO = new FirstAdvisoryFollowVO();
        troyMsgFirstAdvisoryService.getMsgFromFirstAdvisory(mv, followVO, msgBaseFid);

        if ("1".equals(showButton) && !MsgAdvisoryFollowEnum.FOLLOWED.getCode().equals(followStatus)) {
            followVO.setShowButton(1);
        }
        mv.addObject("followStatus", followStatus);
        mv.addObject("firstDetail", followVO);
        return mv;
    }


    @RequestMapping("/saveMsgAdvisoryFollow")
    @ResponseBody
    public DataTransferObject saveMsgAdvisoryFollow(HttpServletRequest request, MsgAdvisoryFollowupEntity follow) {
        if (!Check.NuNObj(follow)) {
            LogUtil.info(logger, "followup:{}", JsonEntityTransform.Object2Json(follow));
        }

        DataTransferObject dto = new DataTransferObject();
        CurrentuserVo currentuserVo = UserUtil.getFullCurrentUser();
        if (Check.NuNObj(currentuserVo)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("请登录");
            return dto;
        }
        String msgBaseFid = request.getParameter("msgBaseFid");
        if (Check.NuNObjs(follow.getMsgFirstAdvisoryFid(), follow.getBeforeStatus(), follow.getRemark(), msgBaseFid)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数异常");
            return dto;
        }

        follow.setFid(UUIDGenerator.hexUUID());
        follow.setEmpFid(currentuserVo.getEmployeeFid());
        follow.setEmpCode(currentuserVo.getEmpCode());
        follow.setEmpName(currentuserVo.getFullName());
        follow.setCreateTime(new Date());
        follow.setIsDel(0);

        MsgFirstAdvisoryEntity msgFirstAdvisoryEntity = null;
        String queryMsgFirstAdvisoryStr = this.msgFirstAdvisoryService.queryByMsgBaseFid(msgBaseFid);
        LogUtil.info(logger, "根据msgBaseFid查询首次咨询表返回:{}", queryMsgFirstAdvisoryStr);
        DataTransferObject dtoMsgAdvisory = JsonEntityTransform.json2DataTransferObject(queryMsgFirstAdvisoryStr);
        if (dtoMsgAdvisory.getCode() == DataTransferObject.SUCCESS) {
            msgFirstAdvisoryEntity = dtoMsgAdvisory.parseData("data", new TypeReference<MsgFirstAdvisoryEntity>() {
            });
        }
        if (Check.NuNObj(msgFirstAdvisoryEntity)) {
            LogUtil.info(logger, "当前首次咨询记录不存在:{}", msgBaseFid);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("当前首次咨询记录不存在");
            return dto;
        }

        Integer followStatus = msgFirstAdvisoryEntity.getFollowStatus();
        LogUtil.info(logger, "beforeStatus ：{}", followStatus);
        follow.setBeforeStatus(followStatus);
        String saveJson = this.msgAdvisoryFollowupService.save(JsonEntityTransform.Object2Json(follow));
        LogUtil.info(logger, "更新查询首次咨询表跟进状态， 插入跟进记录返回:{}", saveJson);
        DataTransferObject saveDto = JsonEntityTransform.json2DataTransferObject(saveJson);
        dto.setErrCode(saveDto.getCode());
        dto.setMsg(saveDto.getMsg());
        return dto;
    }

    @RequestMapping("/listAdvisoryChatInfo")
    @ResponseBody
    public DataTransferObject listAdvisoryChatInfo(String msgBaseFid) {
        DataTransferObject dto = new DataTransferObject();
        LogUtil.info(logger, "msgBaseFid:{}", msgBaseFid);
        if (Check.NuNStr(msgBaseFid)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("msgBaseFid为空");
            return dto;
        }
        try {
            String resultJson = msgBaseService.listChatOnAdvisory(msgBaseFid);
            LogUtil.info(logger, "listAdvisoryChatInfo 查询聊天列表返回结果={}", resultJson);
            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
            List<MsgAdvisoryChatVo> list = resultDto.parseData("list", new TypeReference<List<MsgAdvisoryChatVo>>() {
            });
            CustomerVo tenVo = null;
            CustomerVo lanVo = null;
            if (Check.NuNCollection(list)) {
                list = new ArrayList<>();
            }
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                MsgAdvisoryChatVo vo = (MsgAdvisoryChatVo)iterator.next();
                String tenUid = vo.getTenantUid();
                String landUid = vo.getLandlordUid();
                Integer senType = vo.getMsgSenderType();
                if (Check.NuNObj(senType)) {
                    LogUtil.info(logger, "senType is null");
                    iterator.remove();
                    continue;
                }
                if (Check.NuNObj(tenVo) && senType == UserTypeEnum.TENANT_HUANXIN.getUserType()) {
                    String customerJson = customerMsgManagerService.getCutomerVo(tenUid);
                    tenVo = SOAResParseUtil.getValueFromDataByKey(customerJson, "customerVo", CustomerVo.class);
                    if (!Check.NuNObj(tenVo) && Check.NuNStr(tenVo.getNickName())) {
                        tenVo.setNickName("房客");
                    }
                }
                if (Check.NuNObj(lanVo) && senType == UserTypeEnum.LANDLORD_HUAXIN.getUserType()) {
                    String customerJson = customerMsgManagerService.getCutomerVo(landUid);
                    lanVo = SOAResParseUtil.getValueFromDataByKey(customerJson, "customerVo", CustomerVo.class);
                    if (!Check.NuNObj(lanVo) && Check.NuNStr(lanVo.getNickName())) {
                        lanVo.setNickName("房东");
                    }
                }
                if (senType == UserTypeEnum.TENANT_HUANXIN.getUserType()) {
                    if (Check.NuNObj(tenVo)) {
                        LogUtil.error(logger, "renVo is null , MsgAdvisoryChatVo:{}", JsonEntityTransform.Object2Json(vo));
                        iterator.remove();
                        continue;
                    }
                    vo.setHeadPic(tenVo.getUserPicUrl());
                    vo.setNickName(tenVo.getNickName());
                }
                if (senType == UserTypeEnum.LANDLORD_HUAXIN.getUserType()) {
                    if (Check.NuNObj(lanVo)) {
                        LogUtil.error(logger, "lanVo is null , MsgAdvisoryChatVo:{}", JsonEntityTransform.Object2Json(vo));
                        iterator.remove();
                        continue;
                    }
                    vo.setHeadPic(lanVo.getUserPicUrl());
                    vo.setNickName(lanVo.getNickName());
                }
                //json格式的msgContent
                String content = vo.getContent();
                if (!Check.NuNStr(content)) {
                    JSONObject object = JSONObject.parseObject(content);
                    String msgContent = object.getString("msgContent");
                    String msgRealContent = vo.getMsgRealContent();//真正的消息内容
                    //2017-12-29  im跟进，房东推荐的房源卡片，在页面显示为“不支持的消息,请升级APP版本”，现在改为扩展
                    if((!Check.NuNStr(msgRealContent) && (msgContent.startsWith("不支持的消息")) || (!Check.NuNStr(msgRealContent)  && msgRealContent.startsWith("不支持的消息")))){
                    	
                        JSONObject appChatRecordsExt = object.getJSONObject("appChatRecordsExt");
                        JSONArray shareHouseMsgArray = appChatRecordsExt.getJSONArray("shareHouseMsg");
                        if(4 != appChatRecordsExt.getInteger("msgType") && !Check.NuNObj(shareHouseMsgArray) && shareHouseMsgArray.size()>0){
                        	JSONObject shareHouseMsg = (JSONObject) shareHouseMsgArray.get(0);
                            StringBuffer buffer = new StringBuffer();
                            buffer.append("房源推荐：");
                            buffer.append(shareHouseMsg.getString("name"));
                            buffer.append("|");
                            buffer.append("￥");
                            buffer.append(shareHouseMsg.getString("price"));
                            buffer.append("元/晚");
                            buffer.append("|");
                            buffer.append(shareHouseMsg.getString("city"));
                            String rentWayName = shareHouseMsg.getString("rentWayName");
                            if(Check.NuNStr(rentWayName)){
                            	String rentWay = appChatRecordsExt.getString("rentWay");
                                if(!Check.NuNStr(rentWay)){
                                	 Integer rentWayCode = Integer.valueOf(rentWay);
                                	 RentWayEnum rentWayByCode = RentWayEnum.getRentWayByCode(rentWayCode);
                                	 if(!Check.NuNObj(rentWayByCode)){
                                		 buffer.append("|");
                                		 buffer.append(rentWayByCode.getName());
                                	 }
                                }
                            }else{
                            	buffer.append("|");
                           	    buffer.append(rentWayName);
                            }
                            msgContent = buffer.toString();
                        }else{
                        	 StringBuffer buffer = new StringBuffer();
                             buffer.append("木木表情：");
                             buffer.append(appChatRecordsExt.getString("em_expr_big_name"));
                             msgContent = buffer.toString();
                        }
                    }else{
                    	msgContent=msgRealContent;
                    }
                    LogUtil.info(logger, "im跟进，聊天详情页listAdvisoryChatInfo方法，msgContent={}", msgContent);
                    vo.setContent(msgContent);
                }
            }
            dto.putValue("list", list);
        } catch (Exception e) {
            LogUtil.error(logger, "【listAdvisoryChatInfo】查询im聊天记录异常e={}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务异常");
        }
        return dto;
    }
}
