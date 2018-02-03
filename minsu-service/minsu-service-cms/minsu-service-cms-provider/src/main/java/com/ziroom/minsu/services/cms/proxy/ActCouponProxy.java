package com.ziroom.minsu.services.cms.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.*;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.*;
import com.ziroom.minsu.entity.order.OrderActivityEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.cms.api.inner.ActCouponService;
import com.ziroom.minsu.services.cms.constant.CmsMessageConst;
import com.ziroom.minsu.services.cms.constant.CouponConst;
import com.ziroom.minsu.services.cms.dto.*;
import com.ziroom.minsu.services.cms.entity.ActCouponInfoUserVo;
import com.ziroom.minsu.services.cms.entity.CouponUserUidVo;
import com.ziroom.minsu.services.cms.entity.OutCouponInfoVo;
import com.ziroom.minsu.services.cms.entity.ZiRoomCouponInfoVo;
import com.ziroom.minsu.services.cms.service.ActCouponServiceImpl;
import com.ziroom.minsu.services.cms.service.ActivityFullServiceImpl;
import com.ziroom.minsu.services.cms.service.ActivityServiceImpl;
import com.ziroom.minsu.services.cms.util.TransCouponUtil;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.cms.ActStatusEnum;
import com.ziroom.minsu.valenum.cms.ActTypeEnum;
import com.ziroom.minsu.valenum.cms.CouponTimeTypeEnum;
import com.ziroom.minsu.valenum.cms.IsCouponEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.order.CouponStatusEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * <p></p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月23日
 * @since 1.0
 * @version 1.0
 */
@Service("cms.actCouponProxy")
public class ActCouponProxy implements ActCouponService{
	
	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ActCouponProxy.class);
	
	@Resource(name = "cms.messageSource")
	private MessageSource messageSource;
	
	@Resource(name = "cms.actCouponServiceImpl")
	private ActCouponServiceImpl actCouponServiceImpl;

	@Resource(name = "cms.activityServiceImpl")
	private ActivityServiceImpl activityServiceImpl ;

    @Resource(name = "cms.activityFullServiceImpl")
    private ActivityFullServiceImpl activityFullService ;

    @Resource(name = "basedata.cityTemplateService")
    private CityTemplateService cityTemplateService;


//    @Resource(name="basedata.smsTemplateService")
//    private SmsTemplateService smsTemplateService;





	
	/**
	 * 获取优惠券信息
	 * @author lishaochuan
	 * @create 2016年6月8日下午9:06:03
	 * @param couponSn
	 * @return
	 */
	public String getCouponBySn(String couponSn){
		LogUtil.info(LOGGER, "参数:{}", couponSn);
		DataTransferObject dto = new DataTransferObject();
        try {
        	ActCouponEntity actCoupon = actCouponServiceImpl.getCouponBySn(couponSn);
        	dto.putValue("obj", actCoupon);
        	
        } catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

    /**
     * 获取当前订单的默认优惠券信息
     * @author afi
     * @param paramJson
     * @return
     */
    public String getDefaultCouponByUid(String paramJson){
        DataTransferObject dto = new DataTransferObject();
        try {
            CheckCouponRequest request = JsonEntityTransform.json2Object(paramJson, CheckCouponRequest.class);
            //校验当前的
            this.checkCouponCheckPar(request,dto);
            if (dto.getCode() != DataTransferObject.SUCCESS){
                LogUtil.info(LOGGER, "获取当前的默认优惠券异常:{}", dto.toJsonString());
                return dto.toJsonString();
            }
            List<ActCouponUserEntity> list = actCouponServiceImpl.getCouponListCheckByUidDefault(request);
            if (!Check.NuNCollection(list)){
                //处理当前的免天优惠券
                ActCouponUserEntity actCouponUserEntity = getDefaultCoupon(list,request);
                if (!Check.NuNObj(actCouponUserEntity)){
                    dto.putValue("obj", actCouponUserEntity);
                    dto.putValue("coupon", TransCouponUtil.transCouponEle(actCouponUserEntity));
                }
            }
        } catch(Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }

    /**
     * 获取当前的满足免单的优惠券
     * @author afi
     * @param list
     * @return
     */
    private ActCouponUserEntity getDefaultCoupon(List<ActCouponUserEntity> list,CheckCouponRequest request) throws Exception {
        int cut = 0;
        ActCouponUserEntity actCouponUserEntity = null;
        if (Check.NuNCollection(list)){
            return actCouponUserEntity;
        }
        //便利当前的信息
        for (ActCouponUserEntity couponUserEntity : list) {
            int cutValue = 0;
            if (couponUserEntity.getActType() == ActTypeEnum.FREE.getCode()){
                //免天券
                cutValue = this.countCouponFree(couponUserEntity,request);
            }else {
                cutValue = this.countCouponCache(couponUserEntity,request);
            }
            if (cutValue > cut){
                cut = cutValue;
                actCouponUserEntity = couponUserEntity;
            }
        }
        if (!Check.NuNObj(actCouponUserEntity)){
            //设置城市
            actCouponUserEntity.setCityList(actCouponServiceImpl.getActivityCitiesByActSn(actCouponUserEntity.getActSn()));
            //设置房源限制
            if (actCouponUserEntity.getIsLimitHouse() == YesOrNoEnum.YES.getCode()){
                actCouponUserEntity.setLimitHouseList(actCouponServiceImpl.findHouseByActsn(actCouponUserEntity.getActSn()));
            }
        }
        //返回当前免佣金的逻辑
        return actCouponUserEntity;
    }



    /**
     * 校验当前的额优惠券信息
     * @author afi
     * @param request
     * @param dto
     */
    private  void checkCouponCheckPar(CheckCouponRequest request,DataTransferObject dto){
        if (Check.NuNObj(dto) || dto.getCode() != DataTransferObject.SUCCESS){
            return;
        }
        if (Check.NuNObj(request) ) {
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.PARAM_NULL));
            return;
        }
        if (Check.NuNStr(request.getUid())) {
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("uid为空");
            return;
        }
        if (Check.NuNObj(request.getPrice())) {
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("价格不能为空");
            return;
        }
        if (Check.NuNObj(request.getAllPriceMap())) {
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("价格不能为空");
            return;
        }
        if (Check.NuNStr(request.getCityCode())) {
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("城市不能为空");
            return;
        }
    }

    /**
     * 获取当前用户当前订单满足的信息
     * @author afi
     * @param paramJson
     * @return
     */
    public String getCouponListCheckByUid(String paramJson){
        LogUtil.info(LOGGER, "参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();

        try {
            CheckCouponRequest request = JsonEntityTransform.json2Object(paramJson, CheckCouponRequest.class);
            //校验当前的
            this.checkCouponCheckPar(request,dto);
            if (dto.getCode() != DataTransferObject.SUCCESS){
                LogUtil.info(LOGGER, "获取优惠券列表接口异常:{}", dto.toJsonString());
                return dto.toJsonString();
            }
            List<ActCouponUserEntity> list = actCouponServiceImpl.getCouponListCheckByUidDefault(request);

            List<OutCouponInfoVo> okList = new ArrayList<>();
            if (!Check.NuNCollection(list)){
                for (ActCouponUserEntity entity : list) {
                    int coupon = 0;
                    if (entity.getActType() == ActTypeEnum.FREE.getCode()){
                        //免天券
                        coupon = this.countCouponFree(entity,request);
                    }else {
                        coupon = this.countCouponCache(entity,request);
                    }
                    if (coupon > 0){
                        okList.add(TransCouponUtil.transCouponEle(entity));
                    }
                }
            }
            dto.putValue("list", okList);
            dto.putValue("total", okList.size());
        } catch(Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }

    /**
     * 获取当前用户下的所有优惠券
     * @author afi
     * @param paramJson
     * @return
     */
    public String getCouponListByUid(String paramJson){
        LogUtil.info(LOGGER, "参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();

        try {
            OutCouponRequest request = JsonEntityTransform.json2Object(paramJson, OutCouponRequest.class);

            if (Check.NuNObj(request) ) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.PARAM_NULL));
                LogUtil.error(LOGGER, dto.toJsonString());
                return dto.toJsonString();
            }

            if (Check.NuNStr(request.getUid())) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("uid为空");
                LogUtil.error(LOGGER, dto.toJsonString());
                return dto.toJsonString();
            }

            PagingResult<ActCouponUserEntity> pagingResult = actCouponServiceImpl.getCouponListByUid(request);
            dto.putValue("list", this.transZiroomCouponVo(pagingResult));
            dto.putValue("total", pagingResult.getTotal());
        } catch(Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }


    /**
     * 校验当前的优惠券是否可用
     * @param actCouponUserEntity
     * @param request
     * @return
     */
    private boolean checkCouponOk(ActCouponUserEntity actCouponUserEntity,CheckCouponRequest request)throws Exception{
        boolean checkFlag = false;
        if (Check.NuNObj(actCouponUserEntity)){
            return checkFlag;
        }
        if (Check.NuNObj(request)){
            checkFlag = true;
            return checkFlag;
        }
        if (actCouponUserEntity.getActType() == ActTypeEnum.FREE.getCode()){
            return countCouponFree( actCouponUserEntity, request)>0;
        }else {
            return countCouponCache(actCouponUserEntity, request)>0;
        }
    }


    /**
     * 校验当前的免单优惠券是否可用
     * @author afi
     * @param actCouponUserEntity
     * @param request
     * @return
     */
    private int countCouponCache(ActCouponUserEntity actCouponUserEntity,CheckCouponRequest request)throws Exception{
        int couponMoney = 0;
        if (Check.NuNObjs(actCouponUserEntity, request)){
            return couponMoney;
        }
        if (actCouponUserEntity.getActType() == ActTypeEnum.FREE.getCode()){
            return couponMoney;
        }

        //限制入住时间
        Date startTime = DateUtil.parseDate(request.getStartTime(),DateSplitUtil.timestampPattern);
        Date endTime = DateUtil.parseDate(request.getEndTime(),DateSplitUtil.timestampPattern);
        if (!Check.NuNObj(actCouponUserEntity.getCheckInTime())){
            if (startTime.before(actCouponUserEntity.getCheckInTime())){
                return couponMoney;
            }
        }
        if (!Check.NuNObj(actCouponUserEntity.getCheckOutTime())){
            if (endTime.after(DateSplitUtil.getTomorrow(actCouponUserEntity.getCheckOutTime()))){
                return couponMoney;
            }
        }
        int cost = 0;
        //入住时间
        List<Date> days = DateSplitUtil.dateSplit(startTime, endTime);
        //遍历当前的免天的金额信息
        for (int i = 0; i < days.size(); i++) {
            Date date = days.get(i);
            String str = DateUtil.dateFormat(date);
            int price = request.getAllPriceMap().get(str);
            cost += price;
        }
        //下单的折扣金额金额 比如 7天88折
        if (!Check.NuNObj(request.getRentCut())){
            Double costDou = BigDecimalUtil.mul(cost, request.getRentCut());
            cost = costDou.intValue();
        }

        if (actCouponUserEntity.getActType() == ActTypeEnum.CACHE.getCode()
                || actCouponUserEntity.getActType() == ActTypeEnum.RANDOM.getCode() ){
            //如果是现金券或者随机券
            //佣金
            if (ValueUtil.getintValue(request.getUserCommission()) > 0){
                cost += request.getUserCommission();
            }
            //清洁费
            if (ValueUtil.getintValue(request.getCleanMoney()) > 0){
                cost += request.getCleanMoney();
            }
            //校验是否满足当前的最低门槛限制
            if (!Check.NuNObj(actCouponUserEntity.getActLimit())  && ValueUtil.getintValue(actCouponUserEntity.getActLimit()) > cost){
                //不满足当前的优惠券的门槛
                couponMoney = 0;
            }else {
                couponMoney = actCouponUserEntity.getActCut();
            }
        }else if (actCouponUserEntity.getActType() == ActTypeEnum.CUT.getCode()){
            //如果是现金券或者随机券
            Double couponMoneyDou = BigDecimalUtil.mul(cost, 1-BigDecimalUtil.div(ValueUtil.getintValue(actCouponUserEntity.getActCut()),100));
            couponMoney = couponMoneyDou.intValue();
            if (!Check.NuNObj(actCouponUserEntity.getActMax()) && actCouponUserEntity.getActMax() > 0 && couponMoney > actCouponUserEntity.getActMax()){
                //超过了当前的限制条件
                couponMoney = actCouponUserEntity.getActMax();
            }
        }
        return  couponMoney;
    }


    /**
     * 校验当前的免单优惠券是否可用
     * @author afi
     * @param actCouponUserEntity
     * @param request
     * @return
     */
    private int countCouponFree(ActCouponUserEntity actCouponUserEntity,CheckCouponRequest request)throws Exception{
        int couponMoney = 0;
        if (Check.NuNObjs(actCouponUserEntity, request)){
            return couponMoney;
        }
        if (actCouponUserEntity.getActType() != ActTypeEnum.FREE.getCode()){
            return couponMoney;
        }
        //限制入住时间
        Date startTime = DateUtil.parseDate(request.getStartTime(),DateSplitUtil.timestampPattern);
        Date endTime = DateUtil.parseDate(request.getEndTime(),DateSplitUtil.timestampPattern);
        Date start = DateSplitUtil.getCompareDateFrom(actCouponUserEntity.getCheckInTime(), startTime);
        Date end = DateSplitUtil.getCompareDateTo(actCouponUserEntity.getCheckOutTime(), endTime);
        if (start.after(end)){
            //当前优惠券不可用
            return couponMoney;
        }
        List<Date> days = DateSplitUtil.dateSplit(start, end);
        //优惠券的有效时间是 活动区间和订单时间的最小值
        int acDays = ValueUtil.getMin(days.size(), actCouponUserEntity.getActCut());
        //遍历当前的免天的金额信息
        for (int i = 0; i < acDays; i++) {
            Date date = days.get(i);
            String str = DateUtil.dateFormat(date);
            int price = request.getAllPriceMap().get(str);
            couponMoney += price;
        }
        LogUtil.info(LOGGER, "【选择最优优惠券】价格列表:{}",JsonEntityTransform.Object2Json(request.getAllPriceMap()));
        LogUtil.info(LOGGER, "【选择最优优惠券】当前订单的有效天数：{}，当前活动的免天数：{}，实际的免单天数:{}",days.size(),actCouponUserEntity.getActCut(),acDays);
        double userCommission = ValueUtil.getdoubleValue(request.getUserCommissionRate());
        Double userComm = BigDecimalUtil.mul(couponMoney, userCommission);

        LogUtil.info(LOGGER, "【选择最优优惠券】折扣:{}",request.getRentCut());
        LogUtil.info(LOGGER, "【选择最优优惠券】couponMoney:{}",couponMoney);
        LogUtil.info(LOGGER, "【选择最优优惠券】userCommission:{}",userCommission);
        LogUtil.info(LOGGER, "【选择最优优惠券】userComm:{}",userComm);

        if (!Check.NuNObj(request.getRentCut())){
            Double couponMoneyDou = BigDecimalUtil.mul(couponMoney, request.getRentCut());
            couponMoney = couponMoneyDou.intValue();
        }
        LogUtil.info(LOGGER, "【选择最优优惠券】折扣之后:couponMoney:{}",couponMoney);
        //当前的佣金
        couponMoney += userComm.intValue();
        LogUtil.info(LOGGER, "【选择最优优惠券】佣金之后:couponMoney:{}",couponMoney);
        //清洁费
        couponMoney += ValueUtil.getintValue(request.getCleanMoney());
        LogUtil.info(LOGGER, "【选择最优优惠券】清洁费之后:couponMoney:{}",couponMoney);

        if (!Check.NuNObj(actCouponUserEntity.getActMax()) && actCouponUserEntity.getActMax() > 0 && couponMoney > actCouponUserEntity.getActMax()){
            //超过了当前的限制条件
            couponMoney = 0;
        }
        return  couponMoney;
    }








    /**
     * 将优惠券信息转化成对外的对象
     * @author afi
     * @param pagingResult
     * @return
     */
    private List<OutCouponInfoVo> transCouponVo(PagingResult<ActCouponUserEntity> pagingResult,CheckCouponRequest request)throws Exception{
        List<OutCouponInfoVo> out = new ArrayList<>();
        if (Check.NuNObj(pagingResult)) {
            return out;
        }
        if (Check.NuNCollection(pagingResult.getRows())){
            return out;
        }
        for (ActCouponUserEntity entity : pagingResult.getRows()) {

            if (Check.NuNObj(entity.getActType())){
                LogUtil.error(LOGGER,"异常的优惠券类型：couponSn：{}",entity.getCouponSn());
                pagingResult.setTotal(pagingResult.getTotal() - 1);
                continue;
            }

            CouponStatusEnum statusEnum = CouponStatusEnum.getByCode(entity.getCouponStatus());
            if (Check.NuNObj(statusEnum)){
                LogUtil.error(LOGGER,"异常的优惠券状态：couponSn：{}，request：{}",entity.getCouponSn(),JsonEntityTransform.Object2Json(request));
                pagingResult.setTotal(pagingResult.getTotal() - 1);
                continue;
            }
            if (!this.checkCouponOk(entity,request)){
                LogUtil.info(LOGGER,"当前优惠券不可用：couponSn：{}，request：{}",entity.getCouponSn(),JsonEntityTransform.Object2Json(request));
                pagingResult.setTotal(pagingResult.getTotal() - 1);
                continue;
            }
            //将当前的对象转化对外的对象
            OutCouponInfoVo vo = TransCouponUtil.transCouponEle(entity);
            out.add(vo);
        }
        return out;
    }



    /**
     * 将优惠券信息转化成对外的对象
     * @author afi
     * @param pagingResult
     * @return
     */
    private List<ZiRoomCouponInfoVo> transZiroomCouponVo(PagingResult<ActCouponUserEntity> pagingResult)throws Exception{
        List<ZiRoomCouponInfoVo> out = new ArrayList<>();
        if (Check.NuNObj(pagingResult)) {
            return out;
        }
        if (Check.NuNCollection(pagingResult.getRows())){
            return out;
        }
        for (ActCouponUserEntity entity : pagingResult.getRows()) {
            if (Check.NuNObj(entity.getActType())){
                LogUtil.error(LOGGER,"异常的优惠券类型：couponSn：{}",entity.getCouponSn());
                pagingResult.setTotal(pagingResult.getTotal() - 1);
                continue;
            }
            CouponStatusEnum statusEnum = CouponStatusEnum.getByCode(entity.getCouponStatus());
            if (Check.NuNObj(statusEnum)){
                LogUtil.error(LOGGER,"异常的优惠券状态：couponSn：{}",entity.getCouponSn());
                pagingResult.setTotal(pagingResult.getTotal() - 1);
                continue;
            }
            ZiRoomCouponInfoVo vo = TransCouponUtil.transCouponZiroomEle(entity);
            out.add(vo);
        }
        return out;
    }

	
	/**
	 * 通过活动编号获取优惠券列表
	 * @author lishaochuan
	 * @create 2016年6月8日下午9:07:53
	 * @param paramJson
	 * @return
	 */
	public String getCouponListByActSn(String paramJson){
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
        try {
        	ActCouponRequest request = JsonEntityTransform.json2Object(paramJson, ActCouponRequest.class);
        	PagingResult<ActCouponEntity> pagingResult = actCouponServiceImpl.getCouponListByActSn(request);
        	dto.putValue("total", pagingResult.getTotal());
        	dto.putValue("list", pagingResult.getRows());
        	
        } catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
    }
	
	/**
     * 通过优惠券号获取 优惠券信息、绑定信息
     * @author lishaochuan
     * @create 2016年6月14日下午7:59:04
     * @param couponSn
     * @return
     */
	@Override
	public String getActCouponUserVoByCouponSn(String couponSn) {
		LogUtil.info(LOGGER, "参数:{}", couponSn);
		DataTransferObject dto = new DataTransferObject();
        try {
        	ActCouponUserEntity couponOrder = actCouponServiceImpl.getActCouponUserVoByCouponSn(couponSn);
        	dto.putValue("obj", couponOrder);
        	
        } catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	
	/**
     * 兑换码 
     * @author lishaochuan
     * @create 2016年6月14日下午7:59:04
     * @param paramJson
     * @return
     */
	@Override
	public String exchangeCode(String paramJson) {
		LogUtil.info(LOGGER, "exchangeCode 参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
	    try {
			BindCouponRequest request = JsonEntityTransform.json2Object(paramJson, BindCouponRequest.class);
	    	if (Check.NuNObj(request)) {
	    		LogUtil.info(LOGGER, "exchangeCode bindCouponRequest :{}", JsonEntityTransform.Object2Json(request));
	            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
	            dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.CMS_BIND_REQUEST_NULL));
	            return dto.toJsonString();
	        }
	    	if(Check.NuNStr(request.getUid()) && Check.NuNStr(request.getMobile())){
	    		LogUtil.info(LOGGER, "exchangeCode mobile:{},uid:{}", request.getUid(),request.getMobile());
	            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
	            dto.setMsg("手机号与客户id同时为空！");
	            return dto.toJsonString();
	    	}
	    	if(Check.NuNStr(request.getCouponSn())){
	    		LogUtil.info(LOGGER, "exchangeCode couponSn:{}", request.getCouponSn());
	            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
	            dto.setMsg("号码为空！");
	            return dto.toJsonString();
	    	}
	    	String actSn = request.getCouponSn();
	    	ActivityEntity activity = activityServiceImpl.selectByActSn(actSn);
	    	boolean isActivity = false;
	    	if(!Check.NuNObj(activity)){
	    		isActivity = true;
	    		request.setActSn(activity.getActSn());
	    	}

	    	if(isActivity){//活动码
	    		return this.exchangeActivity(JsonEntityTransform.Object2Json(request));
	    	}else {//优惠券码
	    		return this.bindCoupon(JsonEntityTransform.Object2Json(request));
	    	}
	    }catch (Exception e) {
	    	LogUtil.error(LOGGER, "exchangeCode error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
	    return dto.toString();
	}



    /**
     * 兑换活动码
     * @author afi
     * @create 2016年7月22日下午7:59:04
     * @param paramJson
     * @return
     */
    public String exchangeGroup(String paramJson) {
        LogUtil.info(LOGGER, "exchangeGroup 参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            BindCouponRequest request = JsonEntityTransform.json2Object(paramJson, BindCouponRequest.class);
            if (Check.NuNObj(request)) {
                LogUtil.info(LOGGER, "exchangeGroup bindCouponRequest :{}", JsonEntityTransform.Object2Json(request));
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.CMS_BIND_REQUEST_NULL));
                return dto.toJsonString();
            }
            if( Check.NuNStr(request.getUid())){
                LogUtil.info(LOGGER, "exchangeGroup uid:{}", request.getUid());
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("uid为空！");
                return dto.toJsonString();
            }
            if(Check.NuNStr(request.getGroupSn())){
                LogUtil.info(LOGGER, "exchangeGroup groupSn :{}", request.getGroupSn());
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("参数为空!");
                return dto.toJsonString();
            }else {
                /**
                 * 当有活动组的时候 直接将其他条件设置无效
                 * 活动组只支持uid和groupSn 来领取优惠券
                 * 否则当前的逻辑冗余的太多 导致当前的领取活动变得负责
                 */
                request.setActSn(null);
                request.setCouponSn(null);
            }

            if (ValueUtil.getintValue(actCouponServiceImpl.getCountUserGroupCouNum(request)) > 0 ){
                LogUtil.info(LOGGER, "exchangeGroup groupSn:{},uid:{} tel;{}", request.getGroupSn(),request.getUid(),request.getMobile());
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("不能贪心，本活动只能领一次优惠券哦");
                return dto.toJsonString();
            }
            //通过当前组获取当前优惠券
            ActCouponEntity actCoupon = actCouponServiceImpl.getOneActCouByGroupSn(request.getGroupSn());
            if(Check.NuNObj(actCoupon)){
                LogUtil.info(LOGGER, "exchangeGroup groupSn:{},uid:{}", request.getGroupSn(),request.getUid());
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("活动优惠码已被使用完！");
                return dto.toJsonString();
            }
            request.setCouponSn(actCoupon.getCouponSn());
            return this.distributeCoupon(request,dto,SysConst.RETRIES);
        } catch(Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }


	
	/**
     * 兑换活动码 -now
     * @author liyingjie
     * @create 2016年7月22日下午7:59:04
     * @param paramJson
     * @return
     */
	 private String exchangeActivity(String paramJson) {
		LogUtil.info(LOGGER, "exchangeActivity 参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
        try {
        	BindCouponRequest request = JsonEntityTransform.json2Object(paramJson, BindCouponRequest.class);
        	if (Check.NuNObj(request)) {
        		LogUtil.info(LOGGER, "checkExchangeActivity bindCouponRequest :{}", JsonEntityTransform.Object2Json(request));
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.CMS_BIND_REQUEST_NULL));
                return dto.toJsonString();
            }
        	if( Check.NuNStr(request.getUid()) && Check.NuNStr(request.getMobile())){
        		LogUtil.info(LOGGER, "checkExchangeActivity mobile:{},uid:{}", request.getUid(),request.getMobile());
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("手机号与客户id同时为空！");
                return dto.toJsonString();
        	}
        	if(Check.NuNStr(request.getActSn())){
        		LogUtil.info(LOGGER, "checkExchangeActivity actSn :{}", request.getActSn());
        		dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("活动码为空!");
                return dto.toJsonString();
        	}
        	String actSn = request.getActSn();
            //获取当前的活动
            ActivityFullEntity activityFullEntity = activityFullService.getActivityFullBySn(actSn);
            //校验当前的活动状态
            this.checkActCoupon(activityFullEntity,dto);
            if (dto.getCode() != DataTransferObject.SUCCESS){
                return dto.toJsonString();
            }

            int  times = ValueUtil.getintValue(activityFullEntity.getTimes());
            if (times > 0 && ValueUtil.getintValue(actCouponServiceImpl.getCountUserActCouNum(request)) >= times ){
                LogUtil.info(LOGGER, "checkExchangeActivity actSn:{},uid:{} times;{}", request.getActSn(),request.getUid(),times);
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("不能贪心，本活动只能领"+times+"次优惠券哦");
                return dto.toJsonString();
            }

        	//获取一张优惠券
            ActCouponEntity actCoupon = actCouponServiceImpl.masterForUpdateOfgetCouponByActSn(request.getActSn());
        	if(Check.NuNObj(actCoupon)){
        		LogUtil.info(LOGGER, "checkExchangeActivity actSn:{},uid:{}", request.getActSn(),request.getUid());
        		dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("活动优惠码已被使用完！");
                return dto.toJsonString();
        	}
        	request.setCouponSn(actCoupon.getCouponSn());
        	return this.distributeCoupon(request,dto,SysConst.RETRIES);
        	
        } catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

    /**
     * 校验当前的活动状态是否可被领取
     * @author afi
     * @param activity
     * @param dto
     */
	private void checkActCoupon(ActivityEntity activity,DataTransferObject dto){
        if (Check.NuNObj(dto)){
            return;
        }
        if (dto.getCode() != DataTransferObject.SUCCESS){
            return;
        }
        if(Check.NuNObj(activity)){
            LogUtil.info(LOGGER, "checkExchangeActivity act is null");
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("非法的活动");
            return;
        }

        if(activity.getActStatus() == ActStatusEnum.DISABLE.getCode()){
            LogUtil.info(LOGGER, "checkExchangeActivity actSn:{},actStatus:{}", activity.getActSn(),activity.getActStatus());
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("您的优惠券还未到活动刚开始日期，请在活动开始后兑换");
            return;
        }

        if(activity.getActStatus() == ActStatusEnum.END.getCode()){
            LogUtil.info(LOGGER, "checkExchangeActivity actSn:{},actStatus:{}", activity.getActSn(),activity.getActStatus());
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("优惠券活动已到期，请您输入其他优惠码");
            return;
        }

        if(activity.getIsCoupon() == IsCouponEnum.NO.getCode() || activity.getIsCoupon() == IsCouponEnum.ING.getCode()){
            LogUtil.info(LOGGER, "checkExchangeActivity actSn:{},isCoupon:{}", activity.getActSn(),activity.getIsCoupon());
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("活动券还未生成，请稍后再试！");
            return;
        }

    }

    /**
     * 分配优惠券-now
     * 分配优惠券的入口
     *  1.如果是用公码或者组号去领取活动，第一次先空跑 先遍历一个可用的优惠券，如果遍历失败做递归调用
     *  2.如果领取的是优惠券的私码 完全可以跳过当前操作 当前的操作只是通过公码或者活动组号转化成私码的一个递归调用
     *  3.请谨慎使用该方法，如果用私码调用当前方法虽然进行了兼容，但是会出现重复查新的问题，导致性能比较低
     * @author afi
     * @param request
     * @param dto
     * @param num 最多递归调用的次数
     * @return
     */
	private String distributeCoupon(BindCouponRequest request,DataTransferObject dto,int num){
        String  returnString = "";

        if (Check.NuNObj(request)) {
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("参数异常");
            LogUtil.error(LOGGER, dto.toJsonString());
            return returnString;
        }

        if (Check.NuNStr(request.getUid())) {
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("参数异常");
            LogUtil.error(LOGGER, dto.toJsonString());
            return returnString;
        }

    	//綁定优惠券的所有的公共的逻辑
    	String resultJson = this.bindCoupon(JsonEntityTransform.Object2Json(request));
    	
    	DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
        if(resultDto.getCode() == DataTransferObject.SUCCESS){
        	LogUtil.info(LOGGER, "resultJson:{}", resultJson);
    		return resultDto.toJsonString();
    	}else {
            returnString = resultJson;
        }
        if(num<=0){
        	LogUtil.info(LOGGER, "resultJson:{}", resultJson);
        	return resultDto.toJsonString();
        }
        
    	String couponStatusStr = resultDto.parseData("couponStatus", new TypeReference<String>() {});
    	int couponStatus = 0;
    	if(!Check.NuNStr(couponStatusStr)){
    		couponStatus = Integer.valueOf(couponStatusStr);
    	}
    	
    	if(couponStatus == CouponStatusEnum.GET.getCode()){
            ActCouponEntity actCoupon = null;
            if (Check.NuNStr(request.getActSn())){
                actCoupon = actCouponServiceImpl.masterForUpdateOfgetCouponByActSn(request.getActSn());
            }else if(Check.NuNStr(request.getGroupSn())){
                actCoupon = actCouponServiceImpl.getOneActCouByGroupSn(request.getGroupSn());
            }
        	if(Check.NuNObj(actCoupon)){
        		LogUtil.info(LOGGER, "distributeCoupon par:{}", JsonEntityTransform.Object2Json(request));
        		dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("活动优惠码已被使用完！");
                return dto.toJsonString();
        	}
        	request.setCouponSn(actCoupon.getCouponSn());;
        	returnString = distributeCoupon(request,dto,num-1);
    	}
    	
    	return returnString;
	}



    /**
     * 验证当前电话是否领取优惠券活动
     * @author afi
     * @param paramJson
     * @return
     */
    public String checkActivityByMobile(String paramJson){

        LogUtil.info(LOGGER, "checkActivityByMobile 参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            MobileCouponRequest request = JsonEntityTransform.json2Object(paramJson, MobileCouponRequest.class);
            if (Check.NuNObj(request)) {
                LogUtil.info(LOGGER, "checkActivityByMobile request :{}", JsonEntityTransform.Object2Json(request));
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.CMS_BIND_REQUEST_NULL));
                return dto.toJsonString();
            }

            if(Check.NuNStr(request.getGroupSn())){
                LogUtil.info(LOGGER, "checkActivityByMobile request :{}", JsonEntityTransform.Object2Json(request));
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("6");
                return dto.toJsonString();
            }

            if(!Check.NuNStr(request.getMobile())){
                //校验当前电话是否已经领取
                if(Integer.valueOf(String.valueOf(actCouponServiceImpl.getCountMobileGroupCouNum(request)))>0){
                    LogUtil.info(LOGGER, "checkActivityByMobile groupSn:{},mobile:{}", request.getGroupSn(),request.getMobile());
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg("4");
                    return dto.toJsonString();
                }
            }
            dto.putValue("last",actCouponServiceImpl.getNoExchangeCountByGroupSn(request.getGroupSn()));
        } catch(Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }

    /**
     * 电话领取优惠券活动
     * @author afi
     * @param paramJson
     * @return
     */
    public String pullActivityByMobile(String paramJson) {

        LogUtil.info(LOGGER, "pullActivityByPhone 参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            MobileCouponRequest request = JsonEntityTransform.json2Object(paramJson, MobileCouponRequest.class);
            if (Check.NuNObj(request)) {
                LogUtil.info(LOGGER, "pullActivityByMobile request :{}", JsonEntityTransform.Object2Json(request));
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.CMS_BIND_REQUEST_NULL));
                return dto.toJsonString();
            }
            if(  Check.NuNStr(request.getMobile())){
                LogUtil.info(LOGGER, "pullActivityByMobile mobile:{}",request.getMobile());
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("10");
                return dto.toJsonString();
            }
            if(!Check.NuNStr(request.getActSn())){
                String actSn = request.getActSn();
                ActivityEntity activity = activityServiceImpl.selectByActSn(actSn);

                if(Check.NuNObj(activity)){
                    LogUtil.info(LOGGER, "pullActivityByMobile actSn:{},activity:{}", request.getActSn(),activity);
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg("12");
                    return dto.toJsonString();
                }

                if(activity.getActStatus() == ActStatusEnum.DISABLE.getCode()){
                    LogUtil.info(LOGGER, "pullActivityByMobile actSn:{},actStatus:{}", request.getActSn(),activity.getActStatus());
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg("3");
                    return dto.toJsonString();
                }

                if(activity.getActStatus() == ActStatusEnum.END.getCode()){
                    LogUtil.info(LOGGER, "pullActivityByMobile actSn:{},actStatus:{}", request.getActSn(),activity.getActStatus());
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg("3");
                    return dto.toJsonString();
                }

                if(activity.getIsCoupon() == IsCouponEnum.NO.getCode() || activity.getIsCoupon() == IsCouponEnum.ING.getCode()){
                    LogUtil.info(LOGGER, "pullActivityByMobile actSn:{},isCoupon:{}", request.getActSn(),activity.getIsCoupon());
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg("3");
                    return dto.toJsonString();
                }
                //校验当前电话是否已经领取
                if(Integer.valueOf(String.valueOf(actCouponServiceImpl.getCountMobileActCouNum(request)))>0){
                    LogUtil.info(LOGGER, "pullActivityByMobile actSn:{},mobile:{}", request.getActSn(),request.getMobile());
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg("4");
                    return dto.toJsonString();
                }
            }else if (!Check.NuNStr(request.getGroupSn())){
                //校验当前电话是否已经领取
                if(Integer.valueOf(String.valueOf(actCouponServiceImpl.getCountMobileGroupCouNum(request)))>0){
                    LogUtil.info(LOGGER, "pullActivityByMobile groupSn:{},mobile:{}", request.getGroupSn(),request.getMobile());
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg("4");
                    return dto.toJsonString();
                }
            }else {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("6");
                return dto.toJsonString();
            }

            //递归调用领取优惠券
            this.forceMobileCoupon(request,dto,SysConst.RETRIES);
        } catch(Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }

    /**
     * 强制给当前的电话分配一个有效的活动券
     * @author afi
     * @param request
     * @param dto
     * @param num
     */
    private void forceMobileCoupon(MobileCouponRequest request,DataTransferObject dto,int num){
        if (dto.getCode() != DataTransferObject.SUCCESS){
            return;
        }
        if (num < 0){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("5");
            return;
        }
        if (Check.NuNStr(request.getMobile()) && Check.NuNStr(request.getActSn())){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("5");
            return ;
        }

        ActCouponEntity actCouponEntity = null;
        if (!Check.NuNStr(request.getActSn())){
            //TODO add
            actCouponEntity = actCouponServiceImpl.getAvailableCouponByActSn(request.getActSn());
        }else if (!Check.NuNStr(request.getGroupSn())){
            actCouponEntity = actCouponServiceImpl.getOneActCouByGroupSn(request.getGroupSn());
        }
        if (Check.NuNObj(actCouponEntity)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("2");
            return;
        }
        String couponSn = actCouponServiceImpl.forceMobileCoupon(request,actCouponEntity.getCouponSn(),actCouponEntity.getActSn(),actCouponEntity.getGroupSn());
        if (Check.NuNStr(couponSn)){
            //领取失败 再次领取
            forceMobileCoupon(request,dto,num-1);
        }else {
            dto.putValue("couponSn",couponSn);
            int cut = actCouponEntity.getActCut();
            dto.putValue("cut",BigDecimalUtil.div(cut,100));
        }
    }


	/**
     * 绑定优惠券  - now
     * 所有的逻辑都走这里
     * 1、不管是公码还是私码还是活动组 最后落地的所有的优惠券都走这里
     * 2、当前操作支持幂等操作 优惠券状态已领取并且领取人是自己，直接返回成功
     * 3、
     * @author lishaochuan
     * @create 2016年6月15日上午10:51:51
     * @param paramJson
     * @return
     */
	@Override
	public String bindCoupon(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
        try {
        	BindCouponRequest request = JsonEntityTransform.json2Object(paramJson, BindCouponRequest.class);
        	if (Check.NuNObj(request)) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.CMS_BIND_REQUEST_NULL));
                LogUtil.error(LOGGER, dto.toJsonString());
                return dto.toJsonString();
            }
        	if (Check.NuNStr(request.getCouponSn())) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.CMS_BIND_COUPON_SN_NULL));
                LogUtil.error(LOGGER, dto.toJsonString());
                return dto.toJsonString();
            }
        	if (Check.NuNStr(request.getUid())) {
        		dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("领取uid为空");
                LogUtil.error(LOGGER, dto.toJsonString());
                return dto.toJsonString();
        	}
        	
        	//已经落地的优惠券
        	ActCouponInfoUserVo obj = actCouponServiceImpl.getActCouponInfoVoByCouponSn(request.getCouponSn());
        	// 优惠券不存在
        	if(Check.NuNObj(obj)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.CMS_BIND_COUPON_NULL));
				LogUtil.info(LOGGER, "优惠券不存在,ActCouponInfoUserVo:{},curUid:{}", obj, request.getUid());
				return dto.toJsonString();
        	}

        	String actSn = obj.getActSn();
            if (Check.NuNStr(actSn)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("异常的优惠券");
                LogUtil.info(LOGGER, "当前的优惠券的活动sn为空:{}",  request.getCouponSn());
                return dto.toJsonString();
            }

            //获取当前的活动
            ActivityFullEntity activityFullEntity = activityFullService.getActivityFullBySn(actSn);
            //校验当前的活动状态
            this.checkActCoupon(activityFullEntity,dto);
            if (dto.getCode() != DataTransferObject.SUCCESS){
                return dto.toJsonString();
            }
        	// 优惠券状态已领取并且领取人是自己，直接返回成功
        	if(obj.getCouponStatus() == CouponStatusEnum.GET.getCode() && request.getUid().equals(obj.getUid())){
        		ActCouponEntity coupon = new ActCouponEntity();
    			BeanUtils.copyProperties(obj, coupon);
    			dto.putValue("coupon", coupon);
				return dto.toJsonString();
        	}else {
                /**
                 * 这里不要奇怪
                 * 你可以理解为当前的优惠券没有问题。返回优惠券的状态
                 */
                dto.putValue("couponStatus", obj.getCouponStatus());
            }
        	
			Date date = new Date();
			if (obj.getCouponStatus() == CouponStatusEnum.GET.getCode()) {
				// 优惠券被他人领取
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("抱歉，您的优惠券已被他人领取，无法使用");
				LogUtil.info(LOGGER, "优惠券被他人领取,couponSN:{},couponStatus:{},curUid:{},bindUid:{}", obj.getCouponSn(), obj.getCouponStatus(), request.getUid(), obj.getUid());
				return dto.toJsonString();

			} else if (obj.getCouponStatus() == CouponStatusEnum.FROZEN.getCode()) {
				// 优惠券已被使用
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("此优惠券已被您的其他订单使用");
				LogUtil.info(LOGGER, "优惠券已被使用,couponSN:{},couponStatus:{},curUid:{},bindUid:{}", obj.getCouponSn(), obj.getCouponStatus(), request.getUid(), obj.getUid());
				return dto.toJsonString();

			} else if ( obj.getCouponStatus() == CouponStatusEnum.USED.getCode()) {
                // 优惠券已被使用
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("此优惠券已被您的其他订单使用");
                LogUtil.info(LOGGER, "优惠券已被使用,couponSN:{},couponStatus:{},curUid:{},bindUid:{}", obj.getCouponSn(), obj.getCouponStatus(), request.getUid(), obj.getUid());
                return dto.toJsonString();

            } else if (obj.getCouponStatus() == CouponStatusEnum.OVER_TIME.getCode() ||   ( !Check.NuNObjs(obj.getCouponEndTime(),obj.getCouponStartTime())  && date.after(obj.getCouponEndTime())) ) {
				// 优惠券已过期
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.CMS_BIND_COUPON_EXPIRED));
				LogUtil.info(LOGGER, "优惠券已过期,couponSN:{},couponStatus:{},couponEndTime:{}", obj.getCouponSn(), obj.getCouponStatus(), obj.getCouponEndTime());
				return dto.toJsonString();

			} else if (obj.getCouponStatus() != CouponStatusEnum.WAIT.getCode()) {
				// 优惠券状态错误
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.CMS_BIND_COUPON_STATUS));
				LogUtil.error(LOGGER, "优惠券状态错误,couponSN:{},couponStatus", obj.getCouponSn(), obj.getCouponStatus());
				return dto.toJsonString();
			}
			// 同一活动一个用户只能领一张券
			List<ActCouponUserEntity> couponList = actCouponServiceImpl.getCouponListByActUid(obj.getActSn(), request.getUid());
            int times = ValueUtil.getintValue(obj.getTimes());
			if(times > 0 && couponList.size() >= times){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("不能贪心，本活动只能领"+times+"次优惠券哦");
				LogUtil.info(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}
			// 不在活动有效期范围内
			if(date.before(obj.getActStartTime()) || date.after(obj.getActEndTime())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.CMS_BIND_ACT_EXPIRED));
				LogUtil.info(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

            /**
             * 兼容优惠券的时间状态
             * 优惠券的有效时间分两种
             * 1、固定时间的 生成的时刻就已经知道优惠券的使用时间
             * 2、设置领取时长的 自领取时刻顺延配置时间的有效期
             */
            Date couponStartTime = null;
            Date couponEndTime = null;
            CouponTimeTypeEnum couponTimeType = CouponTimeTypeEnum.getByCode(activityFullEntity.getCouponTimeType());
            if (Check.NuNObj(couponTimeType)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("异常的优惠券时间限制类型！");
                LogUtil.info(LOGGER, dto.toJsonString());
                return dto.toJsonString();
            }
            if (couponTimeType.getCode() == CouponTimeTypeEnum.FIX.getCode()){
                if (Check.NuNObjs(obj.getCouponStartTime(),obj.getCouponEndTime())){
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg("异常的优惠券信息！");
                    LogUtil.info(LOGGER, dto.toJsonString());
                    return dto.toJsonString();
                }
            }else {
                if (ValueUtil.getintValue(activityFullEntity.getCouponTimeLast())<=0){
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg("优惠活动的优惠时长为空！");
                    LogUtil.info(LOGGER, dto.toJsonString());
                    return dto.toJsonString();
                }
                couponStartTime = DateSplitUtil.jumpMinute(new Date(), -10);
                //获取当前的
                couponEndTime = DateSplitUtil.jumpDate(couponStartTime,ValueUtil.getintValue(activityFullEntity.getCouponTimeLast()));
                couponEndTime = DateSplitUtil.jumpMinute(couponEndTime, -11);
            }
            /**
             * 开始真正的绑定
             * 如果再绑定过程中绑定失败 直接提示绑定失败
             */
			actCouponServiceImpl.updateBindCoupon(obj.getCouponSn(), request.getUid(),couponStartTime,couponEndTime);
			ActCouponEntity coupon = new ActCouponEntity();
			BeanUtils.copyProperties(obj, coupon);
			dto.putValue("coupon", coupon);
        } catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}


    /**
     * 根据手机号绑定优惠券
     * @author lisc
     * @param json
     * @return
     */
    @Override
    public String bindCouponByMobile(String json) {
        LogUtil.info(LOGGER, "参数:{}", json);
        DataTransferObject dto = new DataTransferObject();
        try {
            BindCouponMobileRequest request = JsonEntityTransform.json2Object(json, BindCouponMobileRequest.class);
            if (Check.NuNObjs(request, request.getMobile(), request.getUid())) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("参数错误");
                LogUtil.error(LOGGER, "参数错误:{}", json);
                return dto.toJsonString();
            }

            List<CouponMobileLogEntity> couponMobileLogs = actCouponServiceImpl.getNotBindCouponMobile(request.getMobile());
            // 有通过手机号领取的优惠券，并且尚未绑定uid
            if (!Check.NuNCollection(couponMobileLogs)) {
                List<UserCouponEntity> userCoupons = new ArrayList<>();
                for (CouponMobileLogEntity couponMobileLog : couponMobileLogs) {
                    UserCouponEntity userCoupon = new UserCouponEntity();
                    userCoupon.setCouponSn(couponMobileLog.getCouponSn());
                    userCoupon.setUid(request.getUid());
                    userCoupon.setCustomerMobile(request.getMobile());
                    userCoupon.setCreateId(request.getUid());
                    userCoupons.add(userCoupon);
                }
                actCouponServiceImpl.bindCouponByMobile(userCoupons);
            }

        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }

    /**
	 * 批量同步修改优惠券状态、填写覆盖订单号信息
	 * @author lishaochuan
	 * @create 2016年6月16日下午3:44:17
	 * @param paramJson
	 * @return
	 */
	@Override
	public String syncCouponStatus(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
        try {
        	List<OrderActivityEntity> orderActList = JsonEntityTransform.json2ObjectList(paramJson, OrderActivityEntity.class);
        	
        	List<ActCouponEntity> couponList = new ArrayList<ActCouponEntity>();
        	List<UserCouponEntity> orderCouponList = new ArrayList<UserCouponEntity>();
			for (OrderActivityEntity act : orderActList) {
				ActCouponEntity coupon = new ActCouponEntity();
				coupon.setCouponSn(act.getAcFid());
				coupon.setCouponStatus(act.getAcStatus());
				couponList.add(coupon);
				if(!Check.NuNStr(act.getOrderSn())){
					UserCouponEntity orderCoupon = new UserCouponEntity();
					orderCoupon.setCouponSn(act.getAcFid());
					if (CouponStatusEnum.FROZEN.getCode() == act.getAcStatus()){
                        orderCoupon.setUsedTime(new Date());
                    }
                    orderCoupon.setOrderSn(act.getOrderSn());
					orderCouponList.add(orderCoupon);
				}
			}
        	actCouponServiceImpl.updateCouponList(couponList, orderCouponList);
        	
        } catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}


    /**
     * 获取当前未领取的数量
     *
     * @param groupSn
     * @return
     */
    @Override
    public String getNoExchangeCountByGroupSn(String groupSn) {
        LogUtil.info(LOGGER, "参数:{}", groupSn);
        DataTransferObject dto = new DataTransferObject();
        try {
            Long count = actCouponServiceImpl.getNoExchangeCountByGroupSn(groupSn);
            dto.putValue("count", count);

        } catch(Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }

    /**
     * 绑定优惠券 （手机号和订单号绑定一个优惠券）
     * @author jixd
     * @created 2016年12月29日 14:34:34
     * @param
     * @return
     */
    @Override
    public String bindCouponByPhoneAndOrder(String paramJson) {
        DataTransferObject dto = new DataTransferObject();
        LogUtil.info(LOGGER,"bindCouponByPhoneAndOrder参数={}",paramJson);
        MobileCouponRequest mobileCouponRequest = JsonEntityTransform.json2Object(paramJson, MobileCouponRequest.class);
        if (Check.NuNStr(mobileCouponRequest.getMobile())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("手机号码为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(mobileCouponRequest.getOrderSn())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("订单号为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(mobileCouponRequest.getActSn())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("活动为空");
            return dto.toJsonString();
        }
        ActivityEntity activity = activityServiceImpl.selectByActSn(mobileCouponRequest.getActSn());
        if(Check.NuNObj(activity)){
            LogUtil.info(LOGGER, "pullActivityByMobile actSn:{},activity:{}", mobileCouponRequest.getActSn(),activity);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("活动不存在");
            return dto.toJsonString();
        }

        if(activity.getActStatus() == ActStatusEnum.DISABLE.getCode()){
            LogUtil.info(LOGGER, "pullActivityByMobile actSn:{},actStatus:{}", mobileCouponRequest.getActSn(),activity.getActStatus());
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("活动未启用");
            return dto.toJsonString();
        }
        if(activity.getActStatus() == ActStatusEnum.END.getCode()){
            LogUtil.info(LOGGER, "pullActivityByMobile actSn:{},actStatus:{}", mobileCouponRequest.getActSn(),activity.getActStatus());
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("活动已终止");
            return dto.toJsonString();
        }
        if(activity.getIsCoupon() == IsCouponEnum.NO.getCode() || activity.getIsCoupon() == IsCouponEnum.ING.getCode()){
            LogUtil.info(LOGGER, "pullActivityByMobile actSn:{},isCoupon:{}", mobileCouponRequest.getActSn(),activity.getIsCoupon());
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("优惠码未生成");
            return dto.toJsonString();
        }
        String sed = mobileCouponRequest.getMobile() + mobileCouponRequest.getOrderSn();
        String s = MD5Util.MD5Encode(sed);
        CouponMobileLogEntity couponMobileLogEntity = actCouponServiceImpl.getCouponMobileLogByFid(s);
        if (!Check.NuNObj(couponMobileLogEntity)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("已领取优惠券");
            return dto.toJsonString();
        }
        //mobileCouponRequest.setGroupSn(activity.getGroupSn());
        //递归调用领取优惠券
        this.forceMobileCoupon(mobileCouponRequest,dto,SysConst.RETRIES);
        if (dto.getCode() == DataTransferObject.SUCCESS){
            String couponSn = (String) dto.getData().get("couponSn");
            ActCouponUserEntity couponOrder = actCouponServiceImpl.getActCouponUserVoByCouponSn(couponSn);
            dto = new DataTransferObject();
            ZiRoomCouponInfoVo vo = TransCouponUtil.transCouponZiroomEle(couponOrder);
            vo.setStartDate(DateUtil.dateFormat(couponOrder.getCouponStartTime(),"yyyy-MM-dd HH:mm:ss"));
            vo.setEndDate(DateUtil.dateFormat(couponOrder.getCouponEndTime(),"yyyy-MM-dd HH:mm:ss"));
            dto.putValue("couponInfo",vo);
        }else{
            dto.setMsg("服务错误");
        }
        return dto.toJsonString();
    }

    @Override
    public String bindCouponByPhoneNums(String paramJson) {
        DataTransferObject dto = new DataTransferObject();
        LogUtil.info(LOGGER,"bindCouponByPhoneAndOrder参数={}",paramJson);
        MobileCouponRequest mobileCouponRequest = JsonEntityTransform.json2Object(paramJson, MobileCouponRequest.class);
        if (Check.NuNStr(mobileCouponRequest.getMobile())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("手机号码串为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(mobileCouponRequest.getActSn())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("活动号为空");
            return dto.toJsonString();
        }
        ActivityEntity activity = activityServiceImpl.selectByActSn(mobileCouponRequest.getActSn());
        if(Check.NuNObj(activity)){
            LogUtil.info(LOGGER, "bindCouponByPhoneNums actSn:{},activity:{}", mobileCouponRequest.getActSn(),activity);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("活动不存在");
            return dto.toJsonString();
        }

        if(activity.getActStatus() == ActStatusEnum.DISABLE.getCode()){
            LogUtil.info(LOGGER, "bindCouponByPhoneNums actSn:{},actStatus:{}", mobileCouponRequest.getActSn(),activity.getActStatus());
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("活动未启用");
            return dto.toJsonString();
        }
        if(activity.getActStatus() == ActStatusEnum.END.getCode()){
            LogUtil.info(LOGGER, "bindCouponByPhoneNums actSn:{},actStatus:{}", mobileCouponRequest.getActSn(),activity.getActStatus());
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("活动已终止");
            return dto.toJsonString();
        }
        if(activity.getIsCoupon() == IsCouponEnum.NO.getCode() || activity.getIsCoupon() == IsCouponEnum.ING.getCode()){
            LogUtil.info(LOGGER, "bindCouponByPhoneNums actSn:{},isCoupon:{}", mobileCouponRequest.getActSn(),activity.getIsCoupon());
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("优惠码未生成");
            return dto.toJsonString();
        }
        mobileCouponRequest.setGroupSn(activity.getGroupSn());
        String mobiles = mobileCouponRequest.getMobile();
        String[] mobilesArr = mobiles.split("\\s+");
        Long count = actCouponServiceImpl.countAvaliableCouponByActSn(mobileCouponRequest.getActSn());
        List<String> sucList = new ArrayList<>();
        if (count < mobilesArr.length){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("没有足够的优惠券,可用优惠券数量"+count);
            return dto.toJsonString();
        }

        for (int i = 0;i<mobilesArr.length;i++){
            String mobile = mobilesArr[i];
            if (!Check.NuNStr(mobile) && RegExpUtil.isMobilePhoneNum(mobile)){
                mobileCouponRequest.setMobile(mobile);
                this.forceMobileCoupon(mobileCouponRequest,dto,SysConst.RETRIES);
                if (dto.getCode() == DataTransferObject.SUCCESS){
                    sucList.add(mobile);
                }
            }

        }
        if (!Check.NuNCollection(sucList)){
            dto.setErrCode(DataTransferObject.SUCCESS);
            dto.putValue("sucList",sucList);
        }
        return dto.toJsonString();
    }

    @Override
    public String countMobileGroupSns(String paramJson) {
        DataTransferObject dto = new DataTransferObject();
        LogUtil.info(LOGGER,"countMobileGroupSns 参数={}",paramJson);
        MobileCouponRequest mobileCouponRequest = JsonEntityTransform.json2Object(paramJson, MobileCouponRequest.class);
        if (Check.NuNStr(mobileCouponRequest.getMobile())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("领取电话为空");
            return dto.toJsonString();
        }
        if (Check.NuNCollection(mobileCouponRequest.getGroupSns())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("活动组码为空");
            return dto.toJsonString();
        }
        Long aLong = actCouponServiceImpl.countMobileGroupSns(mobileCouponRequest);
        dto.putValue("count",aLong);
        return dto.toJsonString();
    }

    @Override
    public String hasChanceToGetCoupon(String paramJson) {
        DataTransferObject dto = new DataTransferObject();
        LogUtil.info(LOGGER,"hasChanceToGetCoupon 参数={}",paramJson);
        MobileCouponRequest mobileCouponRequest = JsonEntityTransform.json2Object(paramJson, MobileCouponRequest.class);
        String uid = mobileCouponRequest.getUid();
        String actSn = mobileCouponRequest.getActSn();
        if (Check.NuNStr(uid)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("UID为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(actSn)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("活动号为空");
            return dto.toJsonString();
        }
        //当天是否领取过
        Long aLong = actCouponServiceImpl.countCouponNumByUidAndAct(uid,actSn,DateUtil.dateFormat(new Date()));
        if (aLong > 0){
            dto.setErrCode(CouponConst.COUPON_HAS.getCode());
            dto.setMsg(CouponConst.COUPON_HAS.getName());
            return dto.toJsonString();
        }
        ActivityEntity activityEntity = activityServiceImpl.selectByActSn(actSn);
        long all = actCouponServiceImpl.countCouponNumByUidAndAct(uid, actSn, null);
        int times = activityEntity.getTimes();
        if (times > 0 && times <= all){
            dto.setErrCode(CouponConst.COUPON_UID_OVER_LIMITNUM.getCode());
            dto.setMsg(CouponConst.COUPON_UID_OVER_LIMITNUM.getName());
            return dto.toJsonString();
        }
        return dto.toJsonString();
    }

    @Override
    public String listOneMonthExpireUidByGroupSnPage(String paramJson) {
        LogUtil.info(LOGGER,"【listOneMonthExpireUidByGroupSnPage】参数={}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        ActCouponRequest actCouponRequest = JsonEntityTransform.json2Object(paramJson, ActCouponRequest.class);
        if (Check.NuNObj(actCouponRequest)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        if (Check.NuNObj(actCouponRequest.getGroupSn())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("活动组号为空");
            return dto.toJsonString();
        }

        PagingResult<CouponUserUidVo> pagingResult = actCouponServiceImpl.getOneMonthExpireCouponUidByGroupSN(actCouponRequest);
        dto.putValue("list",pagingResult.getRows());
        dto.putValue("count",pagingResult.getTotal());
        return dto.toJsonString();
    }

    /**
     *
     * @author yanb
     * @created 2017年10月25日 20:01:24
     * @param  * @param couponSn
     * @param paramJson
     * @return java.lang.String
     */
    @Override
    public String cancelCoupon(String paramJson) {

        LogUtil.info(LOGGER, "参数:", paramJson);
        DataTransferObject dto = new DataTransferObject();
        CancelCouponDto cancelCouponDto = JsonEntityTransform.json2Object(paramJson, CancelCouponDto.class);
        //校验优惠券号是否为空
        if (Check.NuNStr(cancelCouponDto.getCouponSn())) {
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource,MessageConst.ERROR_CODE));
            dto.setMsg("优惠券号为空");
            LogUtil.error(LOGGER,dto.toJsonString());
            return dto.toJsonString();
        }

        //判断操作人id&name是否为空
        if (Check.NuNStr(cancelCouponDto.getEmpCode()) || Check.NuNStr(cancelCouponDto.getEmpName())) {
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource,MessageConst.ERROR_CODE));
            dto.setMsg("操作人为空");
            LogUtil.error(LOGGER,dto.toJsonString());
            return dto.toJsonString();
        }

        //判断优惠券是否存在
        ActCouponEntity actCoupon = actCouponServiceImpl.getActSnStatusByCouponSn(cancelCouponDto.getCouponSn());
        if (Check.NuNObj(actCoupon)) {
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource,MessageConst.ERROR_CODE));
            dto.setMsg("优惠券不存在");
            LogUtil.error(LOGGER,dto.toJsonString());
            return dto.toJsonString();
        }

        //判断优惠券状态
        if (CouponStatusEnum.WAIT.getCode() != actCoupon.getCouponStatus() && CouponStatusEnum.GET.getCode() != actCoupon.getCouponStatus() && CouponStatusEnum.SEND.getCode() != actCoupon.getCouponStatus()) {
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource,MessageConst.ERROR_CODE));
            dto.setMsg("优惠券状态错误");
            LogUtil.error(LOGGER,dto.toJsonString());
            return dto.toJsonString();
        }


        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("empCode", cancelCouponDto.getEmpCode());
            paramMap.put("empName", cancelCouponDto.getEmpName());
            paramMap.put("remark", cancelCouponDto.getRemark());
            paramMap.put("actCoupon",actCoupon);
            int upNum = actCouponServiceImpl.cancelCoupon(paramMap);
            dto.putValue("upNum",upNum);
        } catch (Exception e) {
            //LogUtil.error("cancelCoupon error:{},paramJson={}",e,paramJson);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource,MessageConst.ERROR_CODE));
            dto.setMsg(e.getMessage());

        }
        return dto.toJsonString();
    }


}
