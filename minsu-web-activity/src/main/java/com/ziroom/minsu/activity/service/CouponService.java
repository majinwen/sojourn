
package com.ziroom.minsu.activity.service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.activity.constant.SmsTemplateEnum;
import com.ziroom.minsu.entity.cms.ActCouponEntity;
import com.ziroom.minsu.entity.cms.UserActEmptyEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.cms.api.inner.ActCouponService;
import com.ziroom.minsu.services.cms.api.inner.MobileCouponService;
import com.ziroom.minsu.services.cms.api.inner.UserActEmptyService;
import com.ziroom.minsu.services.cms.constant.CouponConst;
import com.ziroom.minsu.services.cms.dto.MobileCouponRequest;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>调用支付接口</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/1.
 * @version 1.0
 * @since 1.0
 */
@Service("api.couponService")
public class CouponService {


    private static final Logger LOGGER = LoggerFactory.getLogger(CouponService.class);


    @Autowired
    private RedisOperations redisOperations;

    @Resource(name = "basedata.smsTemplateService")
    private SmsTemplateService smsTemplateService;

    @Resource(name = "cms.mobileCouponService")
    private MobileCouponService mobileCouponService;

    @Resource(name = "cms.userActEmptyService")
    private UserActEmptyService userActEmptyService;

    @Resource(name="cms.actCouponService")
    private ActCouponService actCouponService;




    /**
     * 插入轮空
     * @param mobile
     * @param groupSn
     */
    public DataTransferObject  saveEmptyGroup(String mobile,String groupSn){
        UserActEmptyEntity userActEmptyEntity = new UserActEmptyEntity();
        userActEmptyEntity.setCustomerMobile(mobile);
        userActEmptyEntity.setGroupSn(groupSn);
        String json = userActEmptyService.saveUserEmpty(JsonEntityTransform.Object2Json(userActEmptyEntity));
        DataTransferObject  countDto = JsonEntityTransform.json2DataTransferObject(json);
        return countDto;
    }


    /**
     * 是否已经轮空
     * @param dto
     * @param mobile
     * @param groupSn
     */
    public void  checkEmptyGroup(DataTransferObject dto,String mobile,String groupSn){
        if (dto.getCode() != DataTransferObject.SUCCESS){
            return;
        }
        String json = userActEmptyService.countEmptyByMobileAndGroupSn(mobile,groupSn);
        DataTransferObject  countDto = JsonEntityTransform.json2DataTransferObject(json);
        if (countDto.getCode() != DataTransferObject.SUCCESS){
            dto.setErrCode(countDto.getCode());
            dto.setMsg(countDto.getMsg());
            return;
        }
        Integer has = SOAResParseUtil.getIntFromDataByKey(json, "num");
        if (ValueUtil.getintValue(has) > 0){
            dto.setErrCode(CouponConst.COUPON_EMPTY.getCode());
            dto.setMsg(CouponConst.COUPON_EMPTY.getName());
            return;
        }
    }

    /**
     * 校验验证码和参数
     * @param mobile
     * @param code
     * @param actSn
     * @param groupSn
     * @return
     */
    public DataTransferObject checkCodeAndParam(String mobile, String code, String actSn, String groupSn){
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(mobile)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("领取电话为空");
            return dto;
        }
        if (Check.NuNStr(code)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("验证码为空");
            return dto;
        }
        LogUtil.info(LOGGER, "领取优惠券 mobile:{}，vcode:{}",mobile,code);
        String key = null;
        if (!Check.NuNStr(actSn)){
            key = RedisKeyConst.getMobileCodeKey(mobile,actSn);
        }else if (!Check.NuNStr(groupSn)){
            key = RedisKeyConst.getMobileCodeKey(mobile,groupSn);
        }else {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数异常");
            return dto;
        }

        String codeOrg =redisOperations.get(key);
        if (Check.NuNStr(codeOrg) || !codeOrg.equals(code)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("验证码异常");
            return dto;
        }
        return dto;
    }

    /**
     * 领取优惠券的逻辑
     * @author afi
     * @param mobile
     * @param code
     * @param actSn
     * @param groupSn
     * @return
     */
    public DataTransferObject pullCoupon(String mobile, String code, String actSn, String groupSn){
        /*DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(mobile)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("领取电话为空");
            return dto;
        }
        if (Check.NuNStr(code)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("验证码为空");
            return dto;
        }
        LogUtil.info(LOGGER, "领取优惠券 mobile:{}，vcode:{}",mobile,code);
        String key = null;
        if (!Check.NuNStr(actSn)){
            key = RedisKeyConst.getMobileCodeKey(mobile,actSn);
        }else if (!Check.NuNStr(groupSn)){
            key = RedisKeyConst.getMobileCodeKey(mobile,groupSn);
        }else {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数异常");
            return dto;
        }

        String codeOrg =redisOperations.get(key);
        if (Check.NuNStr(codeOrg) || !codeOrg.equals(code)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("验证码异常");
            return dto;
        }*/
        DataTransferObject dto = checkCodeAndParam(mobile, code, actSn, groupSn);
        if (dto.getCode() == DataTransferObject.ERROR){
            return dto;
        }

        return pullCouponOnly(mobile,actSn,groupSn);
    }



    /**
     * 领取优惠券的逻辑,不做验证码
     * @author afi
     * @param mobile
     * @param actSn
     * @param groupSn
     * @return
     */
    public DataTransferObject pullCouponOnly(String mobile, String actSn, String groupSn){
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(mobile)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("领取电话为空");
            return dto;
        }

        ActCouponEntity couponEntity = null;
        try {
            MobileCouponRequest mobileCouponRequest = new MobileCouponRequest();
            mobileCouponRequest.setMobile(mobile);
            if (!Check.NuNStr(actSn)){
                mobileCouponRequest.setActSn(actSn);
            }
            if (!Check.NuNStr(groupSn)){
                mobileCouponRequest.setGroupSn(groupSn);
            }
            //默认是活动
            mobileCouponRequest.setSourceType(0);

            String json = null;
            if (!Check.NuNStr(actSn)){
                json = this.mobileCouponService.pullActCouponByMobile(JsonEntityTransform.Object2Json(mobileCouponRequest));
            }else {
            	if(!Check.NuNStr(groupSn)&&"2017GUOQING".equals(groupSn)){
            		//需要排名的活动调用返回排名的服务
            		LogUtil.info(LOGGER,"调用了新服务，获取排名{}",mobile);
            		json = this.mobileCouponService.pullGroupCouponByMobileRank(JsonEntityTransform.Object2Json(mobileCouponRequest));
                } else {
                	json = this.mobileCouponService.pullGroupCouponByMobile(JsonEntityTransform.Object2Json(mobileCouponRequest));
				}
            }
            dto = JsonEntityTransform.json2DataTransferObject(json);
            if(dto.getCode() != DataTransferObject.SUCCESS){
                LogUtil.info(LOGGER," 领取失败 mobile:{},json:{}",mobile,json);
            }else{
            	couponEntity =  SOAResParseUtil.getValueFromDataByKey(json, "coupon",ActCouponEntity.class);
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER," mobile:{},e:{}",mobile,e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常");
        }
        if (dto.getCode() == DataTransferObject.SUCCESS){
            if (!Check.NuNStr(actSn)){
                sendMsg(dto,mobile,actSn,couponEntity);
            }else {
                sendMsg(dto,mobile,groupSn,couponEntity);
            }

        }
        //dto.putValue("coupon",couponEntity);
        return dto;
    }

    /**
     * 校验优惠券是否可以领取 （多个组码）
     * @author jixd
     * @created 2017年03月30日 16:02:47
     * @param
     * @return
     */
    public void checkGroupSns(DataTransferObject dto,String mobile,List<String> groupSns){
        if (dto.getCode() != DataTransferObject.SUCCESS){
            return;
        }
        try{
            MobileCouponRequest request = new MobileCouponRequest();
            request.setMobile(mobile);
            request.setGroupSns(groupSns);
            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(actCouponService.countMobileGroupSns(JsonEntityTransform.Object2Json(request)));

            if (resultDto.getCode() == DataTransferObject.ERROR){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg(resultDto.getMsg());
                return;
            }
            Long count = resultDto.parseData("count", new TypeReference<Long>() {});
            if (count > 0){
                dto.setErrCode(CouponConst.COUPON_HAS.getCode());
                dto.setMsg(CouponConst.COUPON_HAS.getName());
                return;
            }
        }catch (Exception e){
            LogUtil.error(LOGGER,"【checkGroupSns校验优惠券异常】e={}",e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务异常");
        }
    }

    /**
     * 调用城市发送短信
     * @author afi
     * @param dto
     * @param mobile
     * @param actSn
     */
    private void sendMsg(DataTransferObject dto,String mobile,String actSn,ActCouponEntity actCouponEntity){
        if (Check.NuNObjs(dto,mobile,actSn)){
            return;
        }
        if (Check.NuNStr(mobile)){
            return;
        }
        if (Check.NuNStr(actSn)){
            return;
        }
        if (dto.getCode() == DataTransferObject.SUCCESS){
            //匹配当前固定的模板信息
            SmsTemplateEnum smsTemplateEnum = SmsTemplateEnum.getByCode(actSn);
            if (Check.NuNObj(smsTemplateEnum)){
                return;
            }
            String msg = smsTemplateEnum.getName();
            if (!Check.NuNObj(actCouponEntity)){
                String money = BigDecimalUtil.div(ValueUtil.getintValue(actCouponEntity.getActCut()),100) +"";
                msg = msg.replace("{money}", money);
            }
            SmsRequest smsRequest = new SmsRequest();
            smsRequest.setMobile(mobile);
            smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.EMPTY.getCode()));
            Map<String, String> conMap = new HashMap<String, String>();
            conMap.put("{1}", msg);
            smsRequest.setParamsMap(conMap);
            LogUtil.info(LOGGER,"发送短信信息:{}",JsonEntityTransform.Object2Json(smsRequest));
            this.smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
        }
    }

    

    /**
     * 领取优惠券的逻辑,不做验证码
     * @author yd
     * @param uid
     * @param actSn
     * @param groupSn
     * @return
     */
    public DataTransferObject pullCouponByUid(String uid, String actSn, String groupSn){
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(uid)){
        	  dto.setErrCode(CouponConst.COUPON_UID_NULL.getCode());
              dto.setMsg(CouponConst.COUPON_UID_NULL.getName());
            return dto;
        }
        
        try {
            MobileCouponRequest mobileCouponRequest = new MobileCouponRequest();
            mobileCouponRequest.setUid(uid);
            if (!Check.NuNStr(actSn)){
                mobileCouponRequest.setActSn(actSn);
            }
            if (!Check.NuNStr(groupSn)){
                mobileCouponRequest.setGroupSn(groupSn);
            }
            //默认是活动
            mobileCouponRequest.setSourceType(0);

            String json = null;
            if (!Check.NuNStr(actSn)){
            	//暂时 不支持 活动码 领取
               //json = this.mobileCouponService.pullActCouponByMobile(JsonEntityTransform.Object2Json(mobileCouponRequest));
            } else {
            	if(!Check.NuNStr(groupSn)&&"2017GUOQING".equals(groupSn)){
            		//需要排名的活动调用返回排名的服务
            		LogUtil.info(LOGGER,"调用了新服务，获取排名groupSn:{},uid:{}",groupSn,uid);
            		json = this.mobileCouponService.pullGroupCouponByUidRank(JsonEntityTransform.Object2Json(mobileCouponRequest));
                } else {
                	json = this.mobileCouponService.pullGroupCouponByUid(JsonEntityTransform.Object2Json(mobileCouponRequest));
				}
            }
            dto = JsonEntityTransform.json2DataTransferObject(json);
            if(dto.getCode() != DataTransferObject.SUCCESS){
                LogUtil.info(LOGGER," 领取失败 uid:{},json:{}",uid,json);
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER," uid:{},e:{}",uid,e);
            dto.setErrCode(CouponConst.COUPON_SYS_ERROR.getCode());
            dto.setMsg(CouponConst.COUPON_SYS_ERROR.getName());
        }
       
        return dto;
    }
}

