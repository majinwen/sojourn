package com.ziroom.minsu.services.order.proxy;


import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderSmartLockEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.constant.SysConst.SmartLock;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.StringUtils;
import com.ziroom.minsu.services.house.smartlock.enumvalue.PasswordTypeEnum;
import com.ziroom.minsu.services.house.smartlock.enumvalue.SmartErrNoEnum;
import com.ziroom.minsu.services.house.smartlock.param.DeleteSLPassByIdParam;
import com.ziroom.minsu.services.order.api.inner.OrderSmartLockService;
import com.ziroom.minsu.services.order.constant.OrderMessageConst;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.service.OrderCommonServiceImpl;
import com.ziroom.minsu.services.order.service.OrderSmartLockServiceImpl;
import com.ziroom.minsu.valenum.ordersmart.TempPswdStatusEnum;

/**
 * 
 * <p>订单只能所代理类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@Component("order.orderSmartLockServiceProxy")
public class OrderSmartLockServiceProxy implements OrderSmartLockService{

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderSmartLockServiceProxy.class);
	
	/**
	 * 线程池框架
	 */
	private final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			50, 100, 3000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

	@Resource(name = "order.messageSource")
	private MessageSource messageSource;

	@Resource(name = "order.orderSmartLockServiceImpl")
	private OrderSmartLockServiceImpl orderSmartLockServiceImpl;
	
	@Resource(name = "order.orderCommonServiceImpl")
	private OrderCommonServiceImpl orderCommonServiceImpl;
	
	@Value("#{'${SMART_LOCKS_DELETE_PASSWORD}'.trim()}")
	private String SMART_LOCKS_DELETE_PASSWORD;

	@Override
	public String findOrderSmartLockByFid(String fid) {
		LogUtil.debug(LOGGER, "fid:{}", fid);
		DataTransferObject dto = new DataTransferObject();

		// 校验智能锁逻辑id不能为空
		if (Check.NuNStr(fid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.SMARTLOCK_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			OrderSmartLockEntity orderSmartLock = orderSmartLockServiceImpl.findOrderSmartLockByFid(fid);
			dto.putValue("orderSmartLock", orderSmartLock);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, OrderMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String findOrderSmartLockByOrderSn(String orderSn) {
		LogUtil.debug(LOGGER, "orderSn:{}", orderSn);
		DataTransferObject dto = new DataTransferObject();

		// 校验智能锁订单编号不能为空
		if (Check.NuNStr(orderSn)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.SMARTLOCK_ORDERSN_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			OrderSmartLockEntity orderSmartLock = orderSmartLockServiceImpl.findOrderSmartLockByOrderSn(orderSn);
			dto.putValue("orderSmartLock", orderSmartLock);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, OrderMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String updateOrderSmartLockByFid(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		OrderSmartLockEntity orderSmartLock = JsonEntityTransform.json2Object(paramJson, OrderSmartLockEntity.class);
		DataTransferObject dto = new DataTransferObject();

		// 校验智能锁不能为空
		if (Check.NuNObj(orderSmartLock)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.SMARTLOCK_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验智能锁逻辑id不能为空
		if (Check.NuNStr(orderSmartLock.getFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.SMARTLOCK_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			int upNum = orderSmartLockServiceImpl.updateOrderSmartLock(orderSmartLock);
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, OrderMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String updateOrderSmartLockByOrderSn(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		OrderSmartLockEntity orderSmartLock = JsonEntityTransform.json2Object(paramJson, OrderSmartLockEntity.class);
		DataTransferObject dto = new DataTransferObject();

		// 校验智能锁不能为空
		if (Check.NuNObj(orderSmartLock)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.SMARTLOCK_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验智能锁订单编号不能为空
		if (Check.NuNStr(orderSmartLock.getOrderSn())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.SMARTLOCK_ORDERSN_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			int result = 0;
			OrderSmartLockEntity orderSmart = this.orderSmartLockServiceImpl.findOrderSmartLockByOrderSn(orderSmartLock.getOrderSn());
			if(Check.NuNObj(orderSmart)){
				result = this.orderSmartLockServiceImpl.insertOrderSmartLock(orderSmartLock);
			}else{
				orderSmartLock.setCreateDate(orderSmart.getCreateDate());
				orderSmartLock.setLastModifyDate(new Date());
				orderSmartLock.setFid(orderSmart.getFid());
				result = orderSmartLockServiceImpl.updateOrderSmartLockByOrderSn(orderSmartLock);
			}
			
			dto.putValue("result", result);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, OrderMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	
	 /**
     * 
     * 更新订单智能锁信息  
     *
     * @author yd
     * @created 2016年6月23日
     *
     * @param orderSmartLock
     * @return
     */
	@Override
    public String updateOrderSmartLockByServiceId(String paramJson) {
    	LogUtil.info(LOGGER, "参数:{}", paramJson);
		OrderSmartLockEntity orderSmartLock = JsonEntityTransform.json2Object(paramJson, OrderSmartLockEntity.class);
		DataTransferObject dto = new DataTransferObject();

		// 校验智能锁不能为空
		if (Check.NuNObj(orderSmartLock)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.SMARTLOCK_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验智能锁订单编号不能为空
		if (Check.NuNStr(orderSmartLock.getServiceId())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("服务serviceid不存在");
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		
		int result = orderSmartLockServiceImpl.updateOrderSmartLockByServiceId(orderSmartLock);
		dto.putValue("result", result);
		
		return dto.toJsonString();
    }
	@Override
	public String closeSmartlockPswd(final String orderSn) {
		LogUtil.info(LOGGER, "orderSn:{}", orderSn);
		DataTransferObject dto = new DataTransferObject();

		// 校验智能锁订单编号不能为空
		if (Check.NuNStr(orderSn)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.SMARTLOCK_ORDERSN_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			OrderSmartLockEntity orderSmartLock = orderSmartLockServiceImpl.findOrderSmartLockByOrderSn(orderSn);
			//校验智能锁信息不能为空
			if(Check.NuNObj(orderSmartLock)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.SMARTLOCK_NULL));
				LogUtil.info(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}
			
			boolean isTempPswdNull = Check.NuNStr(orderSmartLock.getTempPswd());
			boolean isDynaPswdNull = Check.NuNStr(orderSmartLock.getDynaPswd());
			//校验智能锁临时密码与动态密码不能同时为空
			if(isTempPswdNull && isDynaPswdNull){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, 
						OrderMessageConst.SMARTLOCK_PERM_AND_DYNA_PSWD_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}
			
			if(!isTempPswdNull){
				orderSmartLock.setTempPswdStatus(TempPswdStatusEnum.CLOSED.getCode());
			}
			if(!isDynaPswdNull){
				orderSmartLock.setDynaExpiredTime(new Date());
				orderSmartLock.setDynaNum(SmartLock.MAX_DYNAMIC_PSWD_NUM);
			}
			orderSmartLock.setLastModifyDate(new Date());
			LogUtil.info(LOGGER, "orderSmartLock:{}", JsonEntityTransform.Object2Json(orderSmartLock));
			int upNum = orderSmartLockServiceImpl.updateOrderSmartLockByOrderSn(orderSmartLock);
			dto.putValue("upNum", upNum);
			
			if(!isTempPswdNull){
				this.delPermPswd(orderSn);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, OrderMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 删除智能锁永久密码
	 *
	 * @author liujun
	 * @created 2016年6月26日
	 *
	 * @param orderSmartLock
	 * @param orderSn
	 */
	private void delPermPswd(String orderSn) {
		OrderInfoVo order = orderCommonServiceImpl.getOrderInfoByOrderSn(orderSn);
		if(!Check.NuNObj(order)){
			StringBuilder sb = new StringBuilder(100);
			sb.append(SMART_LOCKS_DELETE_PASSWORD);
			
			DeleteSLPassByIdParam param = new DeleteSLPassByIdParam();
			param.setOp_userid(order.getUserUid());
			param.setOp_name(order.getUserName());
			param.setOp_phone(order.getUserTel());
			param.setHouse_id(StringUtils.getSmartLockCode(order.getHouseFid()));
			param.setRoom_id(StringUtils.getSmartLockCode(order.getHouseFid()));
			param.setPassword_type(PasswordTypeEnum.USER_PWD.getCode());
			param.setUser_identify(order.getOrderSn());
			
			String resultJson = CloseableHttpUtil.sendPost(sb.toString(), param);
			JSONObject jsonObj = JSONObject.parseObject(resultJson);
			Integer errno = jsonObj.getInteger("ErrNo");
			if (!Check.NuNObj(errno) && errno.intValue() != SmartErrNoEnum.SUCCESS.getCode()) {
				LogUtil.error(LOGGER, "删除智能锁密码失败,url={},param={},结果:{}", 
						sb.toString(), JsonEntityTransform.Object2Json(param), resultJson);
			}
		} else {
			LogUtil.info(LOGGER, "orderCommonServiceImpl#getOrderInfoByOrderSn查询结果为空,orderSn={}", orderSn);
		}
	}

}
