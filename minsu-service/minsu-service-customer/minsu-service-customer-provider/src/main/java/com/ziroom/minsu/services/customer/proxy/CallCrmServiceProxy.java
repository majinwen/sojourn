package com.ziroom.minsu.services.customer.proxy;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.TelExtensionEntity;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.customer.entity.TelExtensionVo;
import com.ziroom.minsu.services.customer.service.CustomerBaseMsgServiceImpl;
import com.ziroom.minsu.services.customer.service.TelExtensionServiceImpl;
import com.ziroom.minsu.valenum.customer.CrmCodeEnum;
import com.ziroom.minsu.valenum.customer.ExtStatusEnum;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/20.
 * @version 1.0
 * @since 1.0
 */
@Component("customer.callCrmServiceProxy")
public class CallCrmServiceProxy {

    @Value(value = "${crm.host.url}")
    private String crmHostUrl;


    @Value(value = "${crm.add.url}")
    private String crmAddUrl;

    @Value(value = "${crm.update.url}")
    private String crmUpdateUrl;

    @Value(value = "${crm.del.url}")
    private String crmDelUrl;

    @Value(value = "${crm.get.url}")
    private String crmGetUrl;

    @Value(value = "${crm.bind.key}")
    private String bindKey;


    @Resource(name="customer.customerBaseMsgServiceImpl")
    private CustomerBaseMsgServiceImpl customerBaseMsgServiceImpl;

    @Resource(name = "customer.telExtensionServiceImpl")
    private TelExtensionServiceImpl telExtensionService;


    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CallCrmServiceProxy.class);



    /**
     * 解绑电话
     * @author afi
     * @param uid
     * @param createUid
     */
    public void postBreakBind(String uid,String createUid){
        postBreakBind(uid,createUid,null);
    }

    /**
     * 解绑电话
     * @author afi
     * @param uid
     * @param createUid
     */
    public void postBreakBind(String uid,String createUid,DataTransferObject dto){
        if(Check.NuNObj(dto)){
            dto = new DataTransferObject();
        }
        TelExtensionVo extensionVo = telExtensionService.getExtensionVoByUid(uid);
        if(Check.NuNObj(extensionVo)){
            LogUtil.error(LOGGER,"extensionVo is null on postBreakBind");
            return;
        }
        dto.setErrCode(DataTransferObject.ERROR);
        dto.setMsg("error");
        Integer code = null;
        try {
            Map par = getBreakPar(extensionVo);
            LogUtil.debug(LOGGER,"par is :{}",JsonEntityTransform.Object2Json(par));
            String rstJson = CloseableHttpUtil.sendFormPost(crmHostUrl + crmDelUrl, par);
            JSONObject json = JSONObject.parseObject(rstJson);
            LogUtil.info(LOGGER,"rstJson:{}",rstJson);
            code = json.getInteger("error_code");
            if(Check.NuNObj(code)){
                code = -1;
            }
            if(code == CrmCodeEnum.OK.getCode()){
                //解绑成功
                breakSuccess(uid, code, createUid);
                dto.setErrCode(DataTransferObject.SUCCESS);
                dto.setMsg("");
            }
        }catch (Exception e){
            LogUtil.error(LOGGER, "e：{}", e);
        }
    }


    /**
     * 重新绑定电话信息
     * @author afi
     * @param uid
     */
    public void postReAdd(String uid,String createUid){
        if(Check.NuNObj(uid)){
            throw new BusinessException("uid is null");
        }
        TelExtensionVo extensionVo = telExtensionService.getExtensionVoByUid(uid);
        if(Check.NuNObj(extensionVo)){
            LogUtil.error(LOGGER, "extensionVo is null on doAdd");
            return;
        }
        Integer code = null;
        String rstJson = "";
        try {
            rstJson = CloseableHttpUtil.sendFormPost(crmHostUrl + crmAddUrl, getAddPar(extensionVo));
            JSONObject resJson = JSONObject.parseObject(rstJson);
            code = resJson.getInteger("error_code");
            if(Check.NuNObj(code)){
                code = -1;
            }
            if(code == CrmCodeEnum.OK.getCode() || code == CrmCodeEnum.DOUBLE_OK.getCode()){
                //处理成功
                addSuccess(uid, CrmCodeEnum.OK.getCode(), createUid);
            }
        }catch (Exception e){
            LogUtil.error(LOGGER,"【重新绑定电话信息】rstJson={}, e：{}",rstJson,e);
        }
    }

    /**
     * 对新用户绑定电话
     * @author afi
     * @param uid
     * @param createUid
     */
    public void bindAndPost(String uid,String createUid){
        bindAndPost(uid,createUid,null);
    }

    /**
     * 对新用户绑定电话
     * @author afi
     * @param uid
     * @param createUid
     */
    public void bindAndPost(String uid,String createUid,DataTransferObject dto){
        if(Check.NuNObj(dto)){
            dto = new DataTransferObject();
        }
        CustomerBaseMsgEntity customer = customerBaseMsgServiceImpl.getCustomerBaseMsgEntitybyUid(uid);
        if(Check.NuNObj(customer)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("customer is null");
            return;
        }
        //发送绑定请求
        this.postDoAdd(uid, createUid, dto);
    }



    /**
     * 绑定电话信息
     * @author afi
     * @param uid
     * @param createUid
     */
    private void postDoAdd(String uid,String createUid,DataTransferObject dto){
        //上来就保存订单，支持幂等
        TelExtensionEntity entity  = new TelExtensionEntity();
        entity.setUid(uid);
        entity.setExtStatus(ExtStatusEnum.ERROR.getCode());
        entity.setCreateUid(createUid);
        TelExtensionVo extensionVo = telExtensionService.saveExtensionIdempotent(entity);
        if(Check.NuNObj(extensionVo)){
            LogUtil.error(LOGGER,"extensionVo is null on doAdd");
            return;
        }
        dto.setErrCode(DataTransferObject.ERROR);
        dto.setMsg("error");
        Integer code = null;
        try {
            String rstJson = CloseableHttpUtil.sendFormPost(crmHostUrl + crmAddUrl, getAddPar(extensionVo));
            JSONObject json = JSONObject.parseObject(rstJson);
            LogUtil.info(LOGGER,"rstJson:{}",rstJson);
            code = json.getInteger("error_code");
            if(Check.NuNObj(code)){
                code = -1;
            }
            if(code == CrmCodeEnum.OK.getCode() || code == CrmCodeEnum.DOUBLE_OK.getCode()){
                //处理成功
                addSuccess(uid, code,createUid);
                dto.setErrCode(DataTransferObject.SUCCESS);
                dto.setMsg("");
            }
        }catch (Exception e){
            LogUtil.error(LOGGER, "e：{}", e);
        }
    }



    /**
     * 绑定电话信息
     * @author afi
     * @param uid
     * @param createUid
     */
    public void postDoChange(String uid,String createUid,TelExtensionVo extensionVo){
        //上来就保存订单，支持幂等
        TelExtensionEntity entity  = new TelExtensionEntity();
        entity.setUid(uid);
        entity.setExtStatus(ExtStatusEnum.ERROR.getCode());
        entity.setCreateUid(createUid);
        Integer code = null;
        try {
            String rstJson = CloseableHttpUtil.sendFormPost(crmHostUrl + crmUpdateUrl, getChangePar(extensionVo));
            JSONObject json = JSONObject.parseObject(rstJson);
            LogUtil.info(LOGGER,"rstJson:{}",rstJson);
            code = json.getInteger("error_code");
            if(Check.NuNObj(code)){
                code = -1;
            }
            if(code == CrmCodeEnum.OK.getCode() || code == CrmCodeEnum.DOUBLE_OK.getCode()){
                //处理成功
                addSuccess(uid, code,createUid);
            }else {
                doFailure(uid, code,createUid);
            }
        }catch (Exception e){
            LogUtil.error(LOGGER, "e：{}", e);
        }
    }



    /**
     * 调用失败
     * @author afi
     * @param uid
     * @param errorCode
     */
    private void doFailure(String uid,Integer errorCode,String createUid){
        telExtensionService.updateStatus(uid, errorCode, ExtStatusEnum.ERROR.getCode(), createUid);
    }


    /**
     * 调用或者处理成功
     * @param uid
     * @param errorCode
     */
    private void addSuccess(String uid,Integer errorCode,String createUid){
        telExtensionService.updateStatus(uid, errorCode, ExtStatusEnum.HAS_OK.getCode(), createUid);
    }

    /**
     * 调用或者处理成功
     * @param uid
     * @param errorCode
     */
    private void breakSuccess(String uid,Integer errorCode,String createUid){
        telExtensionService.updateStatus(uid, errorCode, ExtStatusEnum.HAS_BREAK.getCode(), createUid);
    }


    /**
     * 获取添加400电话绑定的参数
     * @param extensionVo
     * @return
     */
    private Map<String,String> getChangePar(TelExtensionVo extensionVo){
        if(Check.NuNObj(extensionVo)){
            LogUtil.error(LOGGER,"extensionVo is null on doAdd");
            throw new BusinessException("extensionVo is null");
        }
        String access_time = new Date().getTime()+"";
        String sys_code = MD5Util.MD5Encode(bindKey + access_time, "UTF-8");

        Map<String,String> addPar = new HashMap<>();
        addPar.put("sys_code",sys_code);
        addPar.put("keeperPhone",extensionVo.getLandPhone());
        addPar.put("keeperId",extensionVo.getUid());
        addPar.put("keeperName",extensionVo.getLandName());
        addPar.put("access_time", access_time);
        addPar.put("extCode", extensionVo.getZiroomPhone());
        //这个的作用是强制走内网的逻辑。文档里面也没说 口头上说的
        addPar.put("crmflag", "1");

        return addPar;
    }


    /**
     * 获取添加400电话绑定的参数
     * @param extensionVo
     * @return
     */
    private Map<String,String> getAddPar(TelExtensionVo extensionVo){
        if(Check.NuNObj(extensionVo)){
            LogUtil.error(LOGGER,"extensionVo is null on doAdd");
            throw new BusinessException("extensionVo is null");
        }
        String access_time = new Date().getTime()+"";
        String sys_code = MD5Util.MD5Encode(bindKey + access_time, "UTF-8");

        Map<String,String> addPar = new HashMap<>();
        addPar.put("sys_code",sys_code);
        addPar.put("keeperPhone",extensionVo.getLandPhone());
        addPar.put("keeperId",extensionVo.getUid());
        addPar.put("keeperName",extensionVo.getLandName());
        addPar.put("access_time", access_time);
        addPar.put("extCode", extensionVo.getZiroomPhone());
        return addPar;
    }


    /**
     * 获取添加400电话绑定的参数
     * @param extensionVo
     * @return
     */
    private Map<String,String> getBreakPar(TelExtensionVo extensionVo){
        if(Check.NuNObj(extensionVo)){
            LogUtil.error(LOGGER,"extensionVo is null on doAdd");
            throw new BusinessException("extensionVo is null");
        }
        String access_time = new Date().getTime()+"";
        String sys_code = MD5Util.MD5Encode(bindKey + access_time, "UTF-8");
        Map<String,String> addPar = new HashMap<>();
        addPar.put("sys_code",sys_code);
        addPar.put("keeperPhone",extensionVo.getLandPhone());
        addPar.put("keeperId",extensionVo.getUid());
        addPar.put("access_time", access_time);
        addPar.put("extCode", extensionVo.getZiroomPhone());
        return addPar;
    }

}
