package com.ziroom.minsu.api.cms.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.ApiMessageConst;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.entity.cms.ActCouponEntity;
import com.ziroom.minsu.entity.cms.ActivityEntity;
import com.ziroom.minsu.entity.cms.ActivityFullEntity;
import com.ziroom.minsu.entity.cms.ActivityGroupEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.JpushRequest;
import com.ziroom.minsu.services.cms.api.inner.*;
import com.ziroom.minsu.services.cms.constant.CouponConst;
import com.ziroom.minsu.services.cms.dto.MobileCouponRequest;
import com.ziroom.minsu.services.cms.entity.CouponItemVo;
import com.ziroom.minsu.services.common.constant.JpushConst;
import com.ziroom.minsu.services.common.jpush.base.MessageTypeEnum;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.JpushPersonType;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.*;


/**
 * <p>房源详情领取优惠券</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年06月15日 09:13
 * @since 1.0
 */
@Controller
@RequestMapping("/houseDetailCoupon")
public class HouseDetailCouponController {
    //活动组号
    private static final String GROUP_SN = "XQYLQ1701";

    private static Logger logger = LoggerFactory.getLogger(HouseDetailCouponController.class);

    @Resource(name = "cms.activityGroupService")
    private ActivityGroupService activityGroupService;

    @Resource(name = "cms.activityService")
    private ActivityService activityService;

    @Resource(name = "cms.actCouponService")
    private ActCouponService actCouponService;

    @Resource(name = "cms.mobileCouponService")
    private MobileCouponService mobileCouponService;

    @Resource(name="cms.activityFullService")
    private ActivityFullService activityFullService;

    @Resource(name="basedata.smsTemplateService")
    private SmsTemplateService smsTemplateService;
    
    @Autowired
	private RedisOperations redisOperations;

    /**
     *  显示房源详情优惠券 文字提示 并且返回活动是否开启
     * @author jixd
     * @created 2017年06月15日 09:59:59
     * @return
     */
    @RequestMapping("/${NO_LGIN_AUTH}/couponBarShow")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> couponBarShow(){
        LogUtil.info(logger,"【couponBarShow】优惠券bar展示");
        try{
            String resultJson = activityGroupService.getGroupBySN(GROUP_SN);
            DataTransferObject dto = new DataTransferObject();
            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if (resultDto.getCode() == DataTransferObject.ERROR){
                return new ResponseEntity<>(ResponseSecurityDto.responseUnEncrypt(resultDto),HttpStatus.OK);
            }
            ActivityGroupEntity groupEntity = resultDto.parseData("obj", new TypeReference<ActivityGroupEntity>() {});
            if (Check.NuNObj(groupEntity)){
                return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("活动不存在"), HttpStatus.OK);
            }
            //活动未开启
            if (groupEntity.getIsValid() == YesOrNoEnum.NO.getCode()){
                return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("活动未开启"), HttpStatus.OK);
            }

            DataTransferObject listDto = JsonEntityTransform.json2DataTransferObject(activityService.listActivityByGroupSn(GROUP_SN));
            if (listDto.getCode() == DataTransferObject.ERROR){
                return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(resultDto.getMsg()), HttpStatus.OK);
            }

            List<ActivityEntity> list = listDto.parseData("list", new TypeReference<List<ActivityEntity>>() {});
            if (Check.NuNCollection(list)){
                return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("活动不存在"), HttpStatus.OK);
            }
            //排序后提取出文字 String.format(ApiMessageConst.ACT_COUPON_LITTLE_MSG, act.getActLimit()/100, act.getActCut()/100)
            //List<String> barList = list.stream().sorted(Comparator.comparing(ActivityEntity::getActCut)).map(a -> a.getActName()).collect(Collectors.toList());
            // List<ActivityEntity> barList = list.stream().sorted(Comparator.comparing(ActivityEntity::getActCut)).collect(Collectors.toList());

            Collections.sort(list, new Comparator<ActivityEntity>() {
                @Override
                public int compare(ActivityEntity o1, ActivityEntity o2) {
                    return o1.getActCut() - o2.getActCut();
                }
            });
            List<String> barList = new ArrayList<>();
            for (ActivityEntity act : list){
                barList.add(String.format(ApiMessageConst.ACT_COUPON_LITTLE_MSG, act.getActLimit()/100, act.getActCut()/100));
            }

            dto.putValue("barList",barList);
            dto.putValue("firstOrderReduce",ApiMessageConst.ACT_FIRST_ORDER_REDUCE);
            LogUtil.info(logger,"返回结果={}",dto.toJsonString());
            return new ResponseEntity<>(ResponseSecurityDto.responseUnEncrypt(dto), HttpStatus.OK);
        }catch (Exception e){
            LogUtil.error(logger,"【couponBarShow】房源详情展示优惠券接口异常e={}",e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
        }


    }


    /**
     * 优惠券列表
     * @author jixd
     * @created 2017年06月15日 13:48:00
     * @param
     * @return
     */
    @RequestMapping("/${NO_LGIN_AUTH}/couponListShow")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> couponListShow(String uid){
        LogUtil.info(logger,"【couponListShow】房源详情优惠券列表展示,uid={}",uid);
        try{
            DataTransferObject dto = new DataTransferObject();
            DataTransferObject listDto = JsonEntityTransform.json2DataTransferObject(activityFullService.listActivityFullByGroupSn(GROUP_SN));
            if (listDto.getCode() == DataTransferObject.ERROR){
                return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(listDto.getMsg()), HttpStatus.OK);
            }
            List<ActivityFullEntity> list = listDto.parseData("list", new TypeReference<List<ActivityFullEntity>>() {});
            if (Check.NuNCollection(list)){
                return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("活动不存在"), HttpStatus.OK);
            }

            Collections.sort(list, new Comparator<ActivityEntity>() {
                @Override
                public int compare(ActivityEntity o1, ActivityEntity o2) {
                    return o1.getActCut() - o2.getActCut();
                }
            });
            List<CouponItemVo> couponList = new ArrayList<>();
            MobileCouponRequest mobileCouponRequest = new MobileCouponRequest();
            mobileCouponRequest.setUid(uid);
            for (ActivityFullEntity activityEntity : list){
                CouponItemVo couponItemVo = new CouponItemVo();
                couponItemVo.setActSn(activityEntity.getActSn());
                couponItemVo.setSymbol("¥");
                couponItemVo.setMoney(String.valueOf(BigDecimalUtil.div(activityEntity.getActCut(),100.0,1)));
                couponItemVo.setDesc(String.format(ApiMessageConst.ACT_COUPON_DETAIL_MSG,activityEntity.getActLimit()/100,activityEntity.getCouponTimeLast()));
                if (!Check.NuNStr(uid)){
                    mobileCouponRequest.setActSn(activityEntity.getActSn());
                    DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(actCouponService.hasChanceToGetCoupon(JsonEntityTransform.Object2Json(mobileCouponRequest)));
                    if (resultDto.getCode() == CouponConst.COUPON_HAS.getCode()
                            || resultDto.getCode() == CouponConst.COUPON_UID_OVER_LIMITNUM.getCode()
                            || resultDto.getCode() == DataTransferObject.ERROR){
                        LogUtil.info(logger,"couponListShow 是否领取结果result={}",resultDto.toJsonString());
                        couponItemVo.setIsCan(0);
                    }
                }
                couponList.add(couponItemVo);
            }
            dto.putValue("couponList",couponList);
            dto.putValue("title",ApiMessageConst.ACT_BIG_TITLE);
            dto.putValue("subTitle",ApiMessageConst.ACT_SUB_TITLE);
            LogUtil.info(logger,"【couponListShow】返回的dto={}",dto.toJsonString());
            return new ResponseEntity<>(ResponseSecurityDto.responseUnEncrypt(dto), HttpStatus.OK);
        }catch (Exception e){
            LogUtil.error(logger,"【couponListShow】房源详情列表优惠券接口异常e={}",e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
        }
    }

    /**
     * 领取优惠券
     * @author jixd
     * @created 2017年06月15日 16:06:11
     * @param
     * @return
     */
    @RequestMapping(value = "/${LOGIN_AUTH}/achieveCoupon",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> achieveCoupon(HttpServletRequest request){
        String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
        LogUtil.info(logger,"【achieveCoupon】房源详情领取优惠券,request={}",paramJson);

        MobileCouponRequest couponRequest = JsonEntityTransform.json2Object(paramJson,MobileCouponRequest.class);
        if (Check.NuNStr(couponRequest.getUid())){
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("UID为空"),HttpStatus.OK);
        }
        if (Check.NuNStr(couponRequest.getActSn())){
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("活动号为空"),HttpStatus.OK);
        }
        DataTransferObject dto = new DataTransferObject();
        String key = RedisKeyConst.getGroupAcnByUid(couponRequest.getGroupSn(),couponRequest.getActSn(),couponRequest.getUid());
        try {
			String listJson = null;
			listJson = redisOperations.get(key);
			// 判断缓存是否存在
			if (!Check.NuNStrStrict(listJson)) {
				dto.setErrCode(CouponConst.COUPON_HAS.getCode());
				dto.setMsg(CouponConst.COUPON_HAS.getName());
				LogUtil.info(logger,"【reids已存在，重复请求】groupSn={},actSn={},uid={},listJson={}",couponRequest.getGroupSn(),couponRequest.getActSn(),couponRequest.getUid(),listJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto),HttpStatus.OK);
			}
			
			redisOperations.setex(key.toString(), RedisKeyConst.ACHIEVE_COUPON_GROUP_ACTSN_UID_TIME, couponRequest.getActSn()+couponRequest.getUid());
		} catch (Exception e) {
			LogUtil.error(logger, "【领取优惠券失败】groupSn={},actSn={},uid={},redis错误,e:{}",couponRequest.getGroupSn(),couponRequest.getActSn(),couponRequest.getUid(), e);
			dto.setErrCode(CouponConst.COUPON_ERROR.getCode());
			dto.setMsg(CouponConst.COUPON_ERROR.getName());
			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto),HttpStatus.OK);
		}
        
        try{
            String resultJson = mobileCouponService.pullActCouponByUid(JsonEntityTransform.Object2Json(couponRequest));
            LogUtil.info(logger,"【achieveCoupon】返回结果result={}",resultJson);
            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if (resultDto.getCode() != DataTransferObject.SUCCESS){
                resultDto.setErrCode(DataTransferObject.ERROR);
                return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(resultDto),HttpStatus.OK);
            }
            //是否弹出提示框 1=是
            resultDto.putValue("isToast",1);
            resultDto.setMsg(ApiMessageConst.ACT_SUCCESS_MSG);
            LogUtil.info(logger,"领券返回结果result={}",resultDto.toJsonString());
            ActCouponEntity coupon = resultDto.parseData("coupon", new TypeReference<ActCouponEntity>() {});
            resultDto.putValue("message",ApiMessageConst.ACT_SUCCESS_MSG);
            jpush(couponRequest,coupon);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(resultDto),HttpStatus.OK);
        }catch (Exception e){
            LogUtil.error(logger,"【achieveCoupon】房源详情领券接口异常e={}",e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("领取优惠券失败"),HttpStatus.OK);
        }
    }

    /**
     * 领券成功以后发送推送消息
     * @param request
     */
    private void jpush(MobileCouponRequest request,ActCouponEntity coupon){
        Map<String, String> jpushMap=new HashMap<>();
        JpushRequest jpushRequest = new JpushRequest();
        if (Check.NuNObj(coupon)){
            return;
        }
        jpushMap.put("{1}",String.valueOf(coupon.getActLimit()/100));
        jpushMap.put("{2}",String.valueOf(coupon.getActCut()/100));
        jpushRequest.setParamsMap(jpushMap);
        jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
        jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
        jpushRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.CMS_HOUSE_DETAIL_COUPON_GET_SUCCESS.getCode()));
        jpushRequest.setTitle("民宿优惠券");
        jpushRequest.setUid(request.getUid());
        //自定义消息
        try{
            Map<String, String> extrasMap = new HashMap<>();
            extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
            extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_8);
            extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"1");
            extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_TENANT);
            extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
            jpushRequest.setExtrasMap(extrasMap);
            smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));
        }catch (Exception e){
            LogUtil.error(logger,"achieveCoupon 推送异常e={}",e);
        }
    }

}
