package com.ziroom.minsu.services.customer.proxy;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.TelExtensionEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.customer.api.inner.TelExtensionService;
import com.ziroom.minsu.services.customer.constant.CustomerMessageConst;
import com.ziroom.minsu.services.customer.dto.TelExtensionDto;
import com.ziroom.minsu.services.customer.entity.TelExtensionVo;
import com.ziroom.minsu.services.customer.service.CustomerBaseMsgServiceImpl;
import com.ziroom.minsu.services.customer.service.TelExtensionServiceImpl;
import com.ziroom.minsu.valenum.customer.ExtStatusEnum;

/**
 * <p>400电话的绑定</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/6.
 * @version 1.0
 * @since 1.0
 */
@Component("customer.telExtensionServiceProxy")
public class TelExtensionServiceProxy implements TelExtensionService {


    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerMsgManagerServiceProxy.class);


    @Resource(name = "customer.messageSource")
    private MessageSource messageSource;


    @Resource(name = "customer.telExtensionServiceImpl")
    private TelExtensionServiceImpl telExtensionService;


    @Resource(name="customer.customerBaseMsgServiceImpl")
    private CustomerBaseMsgServiceImpl customerBaseMsgService;

    @Resource(name="customer.callCrmServiceProxy")
    private CallCrmServiceProxy callCrmServiceProxy;

    @Value(value = "${crm.400.phone}")
    private String ziroomPhonePre;





    /**
     * 获取当前用户id的绑定vo
     * @author afi
     * @param uid
     * @return
     */
    @Override
    public String getExtensionVoByUid(String uid){
        LogUtil.info(LOGGER, "请求参数:{}", uid);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(uid)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("uid is null");
            return dto.toJsonString();
        }
        TelExtensionVo extensionVo = telExtensionService.getExtensionVoByUid(uid);
        dto.putValue("extensionVo",extensionVo);
        return dto.toJsonString();
    }


    /**
     * 获取当前用户id的绑定情况
     * @author afi
     * @param uid
     * @return
     */
    @Override
    public String getExtensionByUid(String uid){
        LogUtil.info(LOGGER, "请求参数:{}", uid);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(uid)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("uid is null");
            return dto.toJsonString();
        }
        //获取当前的用户绑定情况
        TelExtensionEntity telExtensionEntity = telExtensionService.getExtensionByUid(uid);
        dto.putValue("telExtensionEntity", telExtensionEntity);
        return dto.toJsonString();
    }

    /**
     * 分页获取电话的绑定情况
     * @author afi
     * @param extjson
     * @return
     */
    @Override
    public String getExtensionVOByPage(String extjson) {
        LogUtil.info(LOGGER, "请求参数:{}", extjson);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(extjson)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("request is null");
            return dto.toJsonString();
        }
        TelExtensionDto telExtensionDto = JsonEntityTransform.json2Object(extjson, TelExtensionDto.class);
        PagingResult<TelExtensionVo> pageReuslt = telExtensionService.getExtensionVOByPage(telExtensionDto);
        dto.putValue("total", pageReuslt.getTotal());
        dto.putValue("list", pageReuslt.getRows());
        return dto.toJsonString();
    }



    /**
     * 获取当前用户id的分机号码
     * @author afi
     * @param uid
     * @return
     */
    @Override
    public String getZiroomPhone(String uid){
        LogUtil.debug(LOGGER, "请求参数uid:{}", uid);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(uid)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("uid is null");
            return dto.toJsonString();
        }

        String ziroomPhone = "";
        TelExtensionEntity entity = telExtensionService.getExtensionByUid(uid);
        if(!Check.NuNObj(entity) && !Check.NuNStr(entity.getZiroomPhone())){
            ziroomPhone = ziroomPhonePre + entity.getZiroomPhone();
        }
        dto.putValue("ziroomPhone",ziroomPhone);
        return dto.toJsonString();
    }


    /**
     * 解绑当前400电话
     * @param uid
     * @param createUid
     * @return
     */
    @Override
    public String breakBind(String uid,String createUid){
        LogUtil.info(LOGGER, "请求参数uid:{},createUid:{}", uid, createUid);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(uid)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("uid is null");
            return dto.toJsonString();
        }
        if(Check.NuNStr(createUid)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("createUid is null");
            return dto.toJsonString();
        }
        TelExtensionEntity entity = telExtensionService.getExtensionByUid(uid);
        if(Check.NuNObj(entity)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("user has not binded please check it");
            return dto.toJsonString();
        }
        if(Check.NuNObj(entity.getExtStatus()) && entity.getExtStatus() == ExtStatusEnum.HAS_BREAK.getCode()){
            //当前用户已经解绑
            return dto.toJsonString();
        }
        //直接解绑
        callCrmServiceProxy.postBreakBind(uid, createUid, dto);
        return dto.toJsonString();
    }



    /**
     * 解绑当前400电话
     * @param uid
     * @param createUid
     * @return
     */
    @Override
    public String breakBindAsynchronous(final String uid,final String createUid){
        LogUtil.info(LOGGER, "请求参数uid:{},createUid:{}", uid, createUid);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(uid)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("uid is null");
            return dto.toJsonString();
        }
        if(Check.NuNStr(createUid)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("createUid is null");
            return dto.toJsonString();
        }
        TelExtensionEntity entity = telExtensionService.getExtensionByUid(uid);
        if(Check.NuNObj(entity)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("user has not binded please check it");
            return dto.toJsonString();
        }
        if(Check.NuNObj(entity.getExtStatus()) && entity.getExtStatus() == ExtStatusEnum.HAS_BREAK.getCode()){
            //当前用户已经解绑
            return dto.toJsonString();
        }
        Thread thread = new Thread(){
            @Override
            public void run() {
                callCrmServiceProxy.postBreakBind(uid, createUid);
            }
        };
        //直接解绑
        SendThreadPool.execute(thread);
        return dto.toJsonString();
    }




    /**
     * 更改订单的绑定状态
     * @author afi
     * @param uid
     * @param errorCode
     * @param status
     * @return
     */
    @Override
    public String updateBindStatus(String uid,Integer errorCode,Integer status,String createUid){
        LogUtil.info(LOGGER, "请求参数:uid:{},status:{}", uid,status);
        DataTransferObject dto = new DataTransferObject();

        try{
            //判断是否存在uid
            if(Check.NuNStr(uid)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
                return dto.toJsonString();
            }
            //判断是否存在status
            if(Check.NuNObj(status)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.STATUS_NULL_ERROR));
                return dto.toJsonString();
            }
            telExtensionService.updateStatus(uid, errorCode,status,createUid);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }


    /**
     * 保存当前的绑定信息
     * @author afi
     * @param saveJson
     * @return
     */
    @Override
    public String saveExtensionIdempotent(String saveJson){
        LogUtil.info(LOGGER, "请求参数:{}", saveJson);
        DataTransferObject dto = new DataTransferObject();

        try{
            TelExtensionEntity entity =JsonEntityTransform.json2Object(saveJson, TelExtensionEntity.class);
            //判断是否存在uid
            if(Check.NuNStr(entity.getUid())){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
                return dto.toJsonString();
            }
            //判断是否存在status
            if(Check.NuNObj(entity.getExtStatus())){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.STATUS_NULL_ERROR));
                return dto.toJsonString();
            }
            TelExtensionEntity org = telExtensionService.getExtensionByUid(entity.getUid());
            if(Check.NuNObj(org)){
                //添加
                telExtensionService.addStatus(entity);
            }else {
                //更新
                if(entity.getExtStatus() != org.getExtStatus().intValue()){
                    telExtensionService.updateStatus(entity.getUid(),entity.getErrorCode(),entity.getExtStatus(),entity.getCreateUid());
                }
            }
            TelExtensionVo extensionVo = telExtensionService.getExtensionVoByUid(entity.getUid());
            dto.putValue("extensionVo", extensionVo);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();

    }


    /**
     * 给当前的uid分配分机号
     * @param uid
     * @param createUid
     * @return
     */
    @Override
    public String bindZiroomPhoneAsynchronous(final String uid,final String createUid){
        LogUtil.info(LOGGER, "请求参数uid:{},createUid:{}", uid,createUid);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(uid)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("uid is null");
            return dto.toJsonString();
        }
        if(Check.NuNStr(createUid)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("createUid is null");
            return dto.toJsonString();
        }
        Thread thread = new Thread(){
            @Override
            public void run() {
                TelExtensionEntity entity = telExtensionService.getExtensionByUid(uid);
                if(Check.NuNObj(entity)){
                    //当前用户未绑定 直接绑定
                    callCrmServiceProxy.bindAndPost(uid, createUid);
                }else {
                    //当前用户已经绑定过
                    if(entity.getExtStatus() == ExtStatusEnum.HAS_OK.getCode()){
                    }else {
                        //用户绑定失败重新绑定
                        callCrmServiceProxy.postReAdd(uid,createUid);
                    }
                }
            }
        };
        //直接解绑
        SendThreadPool.execute(thread);
        return dto.toJsonString();
    }

    /**
     * 给当前的uid分配分机号
     * @param uid
     * @param createUid
     * @return
     */
    @Override
    public String bindZiroomPhone(String uid,String createUid){
        LogUtil.info(LOGGER, "请求参数uid:{},createUid:{}", uid,createUid);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(uid)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("uid is null");
            return dto.toJsonString();
        }
        if(Check.NuNStr(createUid)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("createUid is null");
            return dto.toJsonString();
        }
        TelExtensionEntity entity = telExtensionService.getExtensionByUid(uid);
        if(Check.NuNObj(entity)){
            //当前用户未绑定 直接绑定
            callCrmServiceProxy.bindAndPost(uid, createUid, dto);
        }else {
            //当前用户已经绑定过
            if(entity.getExtStatus() == ExtStatusEnum.HAS_OK.getCode()){
                //直接返回
                return dto.toJsonString();
            }else {
                //用户绑定失败重新绑定
                callCrmServiceProxy.postReAdd(uid,createUid);
            }
        }
        return dto.toJsonString();
    }
    
    
    
    /**
     * 通过uid获取唯一绑定成功的电话
     * @author lishaochuan
     * @create 2016年5月12日下午10:20:35
     * @param uid
     * @return
     */
    @Override
    public String getZiroomPhoneByUid(String uid){
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(uid)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("uid is null");
            return dto.toJsonString();
        }

        String ziroomPhone = "";
        TelExtensionEntity entity = telExtensionService.getHaveExtensionByUid(uid);
        if(!Check.NuNObj(entity) && !Check.NuNStr(entity.getZiroomPhone())){
            ziroomPhone = ziroomPhonePre + entity.getZiroomPhone();
        }
        dto.putValue("ziroomPhone",ziroomPhone);
        return dto.toJsonString();
    }


}
