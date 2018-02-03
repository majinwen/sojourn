package com.ziroom.minsu.services.cms.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.NpsAttendEntiy;
import com.ziroom.minsu.entity.cms.NpsEntiy;
import com.ziroom.minsu.services.cms.api.inner.NpsService;
import com.ziroom.minsu.services.cms.constant.CmsMessageConst;
import com.ziroom.minsu.services.cms.dto.NpsAttendRequest;
import com.ziroom.minsu.services.cms.dto.NpsAttendVo;
import com.ziroom.minsu.services.cms.dto.NpsCollectVo;
import com.ziroom.minsu.services.cms.dto.NpsGetCondiRequest;
import com.ziroom.minsu.services.cms.dto.NpsGetRequest;
import com.ziroom.minsu.services.cms.dto.NpsQuantumVo;
import com.ziroom.minsu.services.cms.service.NpsAttendServiceImpl;
import com.ziroom.minsu.services.cms.service.NpsServiceImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/11/11 15:12
 * @version 1.0
 * @since 1.0
 */
@Service("cms.npsProxy")
public class NpsProxy implements NpsService {


    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NpsProxy.class);

    @Resource(name = "cms.messageSource")
    private MessageSource messageSource;

    @Resource(name = "cms.npsServiceImpl")
    private NpsServiceImpl npsServiceImpl;

    @Resource(name = "cms.npsAttendServiceImpl")
    private NpsAttendServiceImpl npsAttendService;


    /**
     * 获取Nps
     * @param paramJson
     * @return
     */
    public String getNps(String paramJson){
        LogUtil.info(LOGGER, "参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            // 校验参数
            NpsGetRequest npsGetRequest = JsonEntityTransform.json2Object(paramJson, NpsGetRequest.class);
            if (Check.NuNObj(npsGetRequest)) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.PARAM_NULL));
                return dto.toJsonString();
            }
            if(Check.NuNObjs(npsGetRequest.getUid(), npsGetRequest.getUserType())){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("参数错误");
                return dto.toJsonString();
            }

            //通过当前时间与当前唯一有效标志去判断是否是否有nps
            NpsEntiy nps = npsServiceImpl.getNps();
            /*Date now = new Date();
            if(now.after(nps.getEndTime()) || now.before(nps.getStartTime())){
                dto.putValue("can", YesOrNoEnum.NO.getCode());
                return dto.toJsonString();
            }*/
            //如果没有查找到当前生效的nps则返回不可显示
            if(Check.NuNObj(nps)){
                dto.putValue("can", YesOrNoEnum.NO.getCode());
                return dto.toJsonString();
            }
            //设置当前的npsCode
            npsGetRequest.setNpsCode(nps.getNpsCode());
            // 查询当前用户角色是否已经参与过
            NpsAttendEntiy npsAttend = npsServiceImpl.getByUidType(npsGetRequest);
            if(!Check.NuNObj(npsAttend)){
                dto.putValue("can", YesOrNoEnum.NO.getCode());
                return dto.toJsonString();
            }
            dto.putValue("can", YesOrNoEnum.YES.getCode());
            dto.putValue("nps", nps);

        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }


    /**
     * troy分页查询Nps参与信息
     * @param paramJson
     * @return
     */
    public String getNpsAttendForPage(String paramJson){
        LogUtil.info(LOGGER, "参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            NpsGetCondiRequest request = JsonEntityTransform.json2Object(paramJson, NpsGetCondiRequest.class);
            PagingResult<NpsAttendVo> pagingResult = npsAttendService.getByCondition(request);
            dto.putValue("list", pagingResult.getRows());
            dto.putValue("total", pagingResult.getTotal());

        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }

    /**
     * troy分页查询Nps汇总
     * @param paramJson
     * @return
     */
    public String getNpsForPage(String paramJson){
        LogUtil.info(LOGGER, "参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            NpsGetCondiRequest request = JsonEntityTransform.json2Object(paramJson, NpsGetCondiRequest.class);
            PagingResult<NpsCollectVo> pagingResult = npsAttendService.getNpsCollect(request);
            dto.putValue("list", pagingResult.getRows());
            dto.putValue("total", pagingResult.getTotal());

        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }


    /**
     * 参与nps调查
     * @param paramJson
     * @return
     */
    public String saveNpsAttend(String paramJson){
        LogUtil.info(LOGGER, "参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            // 校验参数
            NpsAttendRequest npsAttendRequest = JsonEntityTransform.json2Object(paramJson, NpsAttendRequest.class);
            if (Check.NuNObj(npsAttendRequest)) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.PARAM_NULL));
                return dto.toJsonString();
            }
            if(Check.NuNObjs( npsAttendRequest.getUid(), npsAttendRequest.getUserType(),npsAttendRequest.getScore())){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("参与nps调查参数错误");
                return dto.toJsonString();
            }
            if (ValueUtil.getintValue(npsAttendRequest.getScore()) < 0
                    || ValueUtil.getintValue(npsAttendRequest.getScore()) > 10
                    ){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("异常的评分");
                return dto.toJsonString();
            }
            // 获取当前nps
            NpsEntiy npsEntiy = npsServiceImpl.getNps();
            if(Check.NuNObj(npsEntiy)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("异常的npsCode");
                return dto.toJsonString();
            }
            //判断当前用户是否参加过本次nps
            NpsGetRequest npsGet = new NpsGetRequest();
            npsGet.setNpsCode(npsEntiy.getNpsCode());
            npsGet.setUid(npsAttendRequest.getUid());
            npsGet.setUserType(npsAttendRequest.getUserType());
            NpsAttendEntiy attendEntiy = npsServiceImpl.getByUidType(npsGet);
            if(!Check.NuNObj(attendEntiy)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("用户已经参加过nps");
                return dto.toJsonString();
            }
            NpsAttendEntiy npsAttendEntiy = new NpsAttendEntiy();
            npsAttendEntiy.setNpsCode(npsEntiy.getNpsCode());
            npsAttendEntiy.setScore(npsAttendRequest.getScore());
            npsAttendEntiy.setUid(npsAttendRequest.getUid());
            npsAttendEntiy.setUserType(npsAttendRequest.getUserType());
            npsAttendEntiy.setOrderSn(npsAttendRequest.getOrderSn());
            //保存nps的参与情况
            npsServiceImpl.saveNpsAttend(npsAttendEntiy);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }

    /**
     * 修改nps状态
     * @param
     * @return
     */
    @Override
    public String editNpsStatus(NpsEntiy nps) {
        //TODO
        //当nps状态要被修改成为开始状态时，先去查询当前是否已经存在已开始的nps，不存在 允许修改 存在 提示用户关闭当前已存在
        DataTransferObject dto = new DataTransferObject();
        try{
            if(Check.NuNStrStrict(nps.getNpsCode()) || Check.NuNObj(nps.getNpsStatus())){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数错误");
                return dto.toJsonString();
            }
            if(nps.getNpsStatus() == 1){
                NpsEntiy exist = npsServiceImpl.getNps();
                if(!Check.NuNObj(exist)){
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("当前已存在生效nps");
                    return dto.toJsonString();
                }
            }
            npsServiceImpl.updateNps(nps);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }

    @Override
    public String calculateNPS(NpsGetCondiRequest nps) {
        DataTransferObject dto = new DataTransferObject();
        try{
            if(Check.NuNStrStrict(nps.getNpsCode()) || Check.NuNObj(nps.getNpsStartTime()) || Check.NuNObj(nps.getNpsEndTime())){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数错误");
                return dto.toJsonString();
            }
            NpsQuantumVo npsQuantumVo = npsServiceImpl.getCalculateNPS(nps);
            dto.putValue("npsQuantumVo",npsQuantumVo);
        }catch (Exception e){
            LogUtil.error(LOGGER, "calculateNPS(),error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }

    @Override
    public String npsNameList() {
        DataTransferObject dto = new DataTransferObject();
        try{
            List<NpsEntiy> npsNameList = npsServiceImpl.npsNameList();
            dto.putValue("npsNameList",npsNameList);
        }catch (Exception e){
            LogUtil.error(LOGGER, "calculateNPS(),error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }
}
