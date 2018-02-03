package com.ziroom.minsu.services.order.proxy;

import javax.annotation.Resource;

import com.ziroom.minsu.services.order.service.OrderStaticsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.order.api.inner.StaticsService;
import com.ziroom.minsu.services.order.dto.OrderLandlordStaticsDto;
import com.ziroom.minsu.services.order.dto.OrderStaticsRequest;
import com.ziroom.minsu.services.order.service.OrderLoadlordServiceImpl;

/**
 * <p>
 * 收益
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie 
 * @since 1.0
 * @version 1.0
 */
@Component("order.staticsServiceProxy")
public class StaticsServiceProxy implements StaticsService{

	private static final Logger LOGGER = LoggerFactory.getLogger(StaticsServiceProxy.class);


	@Resource(name = "order.messageSource")
	private MessageSource messageSource;
	
	@Resource(name="house.houseManageService")
	private HouseManageService houseManageService;
	
	@Resource(name = "order.orderLoadlordServiceImpl")
	private OrderLoadlordServiceImpl orderLoadlordServiceImpl;

    @Resource(name = "order.orderStaticsServiceImpl")
    private OrderStaticsServiceImpl orderStaticsService;
	
	
	@Override
	public String taskStaticsCountInfo(String param) {
		DataTransferObject dto = new DataTransferObject();
		try{
			OrderStaticsRequest request = JsonEntityTransform.json2Object(param, OrderStaticsRequest.class);
			
			if(Check.NuNObj(request)){
				 LogUtil.info(LOGGER, "getStaticsCountLanOrderNum 参数为空 param:{}", JsonEntityTransform.Object2Json(request));
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("getStaticsCountLanOrderNum 参数为空");
				 return dto.toJsonString();
			}
			if(Check.NuNStr(request.getLandlordUid())){
				 LogUtil.info(LOGGER, "getStaticsCountLanOrderNum landlordUid:{}", request.getLandlordUid());
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("getStaticsCountLanOrderNum landlordUid为空");
				 return dto.toJsonString();
			}
			if(Check.NuNObj(request.getLimitTime())){
				 LogUtil.info(LOGGER, "getStaticsCountLanOrderNum limitTime:{}", request.getLimitTime());
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("getStaticsCountLanOrderNum limitTime为空");
				 return dto.toJsonString();
			}
			Long lanOrderNum = orderLoadlordServiceImpl.staticsCountLanOrderNumImp(request);
			Long replyOrderNum = orderLoadlordServiceImpl.staticsCountLanReplyOrderNumImp(request);
			Long lanRefuseOrderNum = orderLoadlordServiceImpl.staticsCountLanRefuseOrderNumImp(request);
			Long sysRefuseOrderNum = orderLoadlordServiceImpl.staticsCountSysRefuseOrderNumImp(request);
			Long replyOrderTime = orderLoadlordServiceImpl.staticsCountLanReplyOrderTimeImp(request);
			dto.putValue("lanOrderNum", lanOrderNum);
			dto.putValue("replyOrderNum", replyOrderNum);
			dto.putValue("lanRefuseOrderNum", lanRefuseOrderNum);
			dto.putValue("sysRefuseOrderNum", sysRefuseOrderNum);
			dto.putValue("replyOrderTime", replyOrderTime);
			
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
	
	
	
	
	/**
	 * 获取房东的订单
	 * @author liyingjie
	 * @param param
	 * @return 
	 */
	@Override
	public String getStaticsCountLanOrderNum(String param) {
		DataTransferObject dto = new DataTransferObject();
		try{
			OrderStaticsRequest request = JsonEntityTransform.json2Object(param, OrderStaticsRequest.class);
			
			if(Check.NuNObj(request)){
				 LogUtil.info(LOGGER, "getStaticsCountLanOrderNum 参数为空 param:{}", JsonEntityTransform.Object2Json(request));
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("getStaticsCountLanOrderNum 参数为空");
				 return dto.toJsonString();
			}
			if(Check.NuNStr(request.getLandlordUid())){
				 LogUtil.info(LOGGER, "getStaticsCountLanOrderNum landlordUid:{}", request.getLandlordUid());
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("getStaticsCountLanOrderNum landlordUid为空");
				 return dto.toJsonString();
			}
			if(Check.NuNObj(request.getLimitTime())){
				 LogUtil.info(LOGGER, "getStaticsCountLanOrderNum limitTime:{}", request.getLimitTime());
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("getStaticsCountLanOrderNum limitTime为空");
				 return dto.toJsonString();
			}
			Long result = orderLoadlordServiceImpl.staticsCountLanOrderNumImp(request);
			dto.putValue("result", result);
			
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	@Override
	public String getStaticsCountLanReplyOrderNum(String param) {
		DataTransferObject dto = new DataTransferObject();
		try{
			OrderStaticsRequest request = JsonEntityTransform.json2Object(param, OrderStaticsRequest.class);
			
			if(Check.NuNObj(request)){
				 LogUtil.info(LOGGER, "getStaticsCountLanReplyOrderNum 参数为空 param:{}", JsonEntityTransform.Object2Json(request));
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("getStaticsCountLanReplyOrderNum 参数为空");
				 return dto.toJsonString();
			}
			
			if(Check.NuNStr(request.getLandlordUid())){
				 LogUtil.info(LOGGER, "getStaticsCountLanReplyOrderNum landlordUid:{}", request.getLandlordUid());
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("getStaticsCountLanReplyOrderNum landlordUid为空");
				 return dto.toJsonString();
			}
			if(Check.NuNObj(request.getLimitTime())){
				 LogUtil.info(LOGGER, "getStaticsCountLanReplyOrderNum limitTime:{}", request.getLimitTime());
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("getStaticsCountLanReplyOrderNum limitTime为空");
				 return dto.toJsonString();
			}
			
			Long result = orderLoadlordServiceImpl.staticsCountLanReplyOrderNumImp(request);
			dto.putValue("result", result);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	@Override
	public String getStaticsCountLanRefuseOrderNum(String param) {
		DataTransferObject dto = new DataTransferObject();
		try{
			OrderStaticsRequest request = JsonEntityTransform.json2Object(param, OrderStaticsRequest.class);
			
			if(Check.NuNObj(request)){
				 LogUtil.info(LOGGER, "getStaticsCountLanRefuseOrderNum 参数为空 param:{}", JsonEntityTransform.Object2Json(request));
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("getStaticsCountLanRefuseOrderNum 参数为空");
				 return dto.toJsonString();
			}
			
			if(Check.NuNStr(request.getLandlordUid())){
				 LogUtil.info(LOGGER, "getStaticsCountLanRefuseOrderNum landlordUid:{}", request.getLandlordUid());
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("getStaticsCountLanRefuseOrderNum landlordUid为空");
				 return dto.toJsonString();
			}
			if(Check.NuNObj(request.getLimitTime())){
				 LogUtil.info(LOGGER, "getStaticsCountLanRefuseOrderNum limitTime:{}", request.getLimitTime());
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("getStaticsCountLanRefuseOrderNum limitTime为空");
				 return dto.toJsonString();
			}
			
			Long result = orderLoadlordServiceImpl.staticsCountLanRefuseOrderNumImp(request);
			dto.putValue("result", result);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	@Override
	public String getStaticsCountSysRefuseOrderNum(String param) {
		DataTransferObject dto = new DataTransferObject();
		try{
			OrderStaticsRequest request = JsonEntityTransform.json2Object(param, OrderStaticsRequest.class);
			
			if(Check.NuNObj(request)){
				 LogUtil.info(LOGGER, "getStaticsCountSysRefuseOrderNum 参数为空 param:{}", JsonEntityTransform.Object2Json(request));
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("getStaticsCountSysRefuseOrderNum 参数为空");
				 return dto.toJsonString();
			}
			
			if(Check.NuNStr(request.getLandlordUid())){
				 LogUtil.info(LOGGER, "getStaticsCountSysRefuseOrderNum landlordUid:{}", request.getLandlordUid());
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("getStaticsCountSysRefuseOrderNum landlordUid为空");
				 return dto.toJsonString();
			}
			if(Check.NuNObj(request.getLimitTime())){
				 LogUtil.info(LOGGER, "getStaticsCountSysRefuseOrderNum limitTime:{}", request.getLimitTime());
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("getStaticsCountSysRefuseOrderNum limitTime为空");
				 return dto.toJsonString();
			}
			
			Long result = orderLoadlordServiceImpl.staticsCountSysRefuseOrderNumImp(request);
			dto.putValue("result", result);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	@Override
	public String getStaticsCountLanReplyOrderTime(String param) {
		DataTransferObject dto = new DataTransferObject();
		try{
			OrderStaticsRequest request = JsonEntityTransform.json2Object(param, OrderStaticsRequest.class);
			
			if(Check.NuNObj(request)){
				 LogUtil.info(LOGGER, "getStaticsCountLanReplyOrderTime 参数为空 param:{}", JsonEntityTransform.Object2Json(request));
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("getStaticsCountLanReplyOrderTime 参数为空");
				 return dto.toJsonString();
			}
			
			if(Check.NuNStr(request.getLandlordUid())){
				 LogUtil.info(LOGGER, "getStaticsCountLanReplyOrderTime landlordUid:{}", request.getLandlordUid());
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("getStaticsCountLanReplyOrderTime landlordUid为空");
				 return dto.toJsonString();
			}
			if(Check.NuNObj(request.getLimitTime())){
				 LogUtil.info(LOGGER, "getStaticsCountLanReplyOrderTime limitTime:{}", request.getLimitTime());
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("getStaticsCountLanReplyOrderTime limitTime为空");
				 return dto.toJsonString();
			}
			
			Long result = orderLoadlordServiceImpl.staticsCountLanReplyOrderTimeImp(request);
			dto.putValue("result", result);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	
    }




	@Override
	public String staticsLandOrderCountInfo(String param) {
		DataTransferObject dto = new DataTransferObject();
		try{
			OrderStaticsRequest request = JsonEntityTransform.json2Object(param, OrderStaticsRequest.class);
			
			if(Check.NuNObj(request)){
				 LogUtil.info(LOGGER, "staticsLandOrderCountInfo 参数为空 param:{}", JsonEntityTransform.Object2Json(request));
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("staticsLandOrderCountInfo 参数为空");
				 return dto.toJsonString();
			}
			
			if(Check.NuNStr(request.getLandlordUid())){
				 LogUtil.info(LOGGER, "staticsLandOrderCountInfo landlordUid:{}", request.getLandlordUid());
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("staticsLandOrderCountInfo landlordUid为空");
				 return dto.toJsonString();
			} 			
			
			OrderLandlordStaticsDto landstatics = orderStaticsService.staticsLandOrderCountInfo(request.getLandlordUid());
			if (Check.NuNObj(landstatics)){
				landstatics = new OrderLandlordStaticsDto();
			}
			
//			landstatics.setLanOrderNum(orderLoadlordServiceImpl.countLanOrderNum(request.getLandlordUid()));
//			landstatics.setAcceptOrderNum(orderLoadlordServiceImpl.acceptOrderNum(request.getLandlordUid()));
//			landstatics.setLanRefuseOrderNum(orderLoadlordServiceImpl.countLanRefuseOrderNum(request.getLandlordUid()));
//			landstatics.setSysRefuseOrderNum(orderLoadlordServiceIsmpl.countSysRefuseOrderNum(request.getLandlordUid()));
//			landstatics.setCanEvaNum(orderLoadlordServiceImpl.countCanEvaNum(request.getLandlordUid()));
//			landstatics.setLandlordEvaedNum(orderLoadlordServiceImpl.countLandlordEvaedNum(request.getLandlordUid()));
//			landstatics.setTenantEvaedNum(orderLoadlordServiceImpl.countTenantEvaedNum(request.getLandlordUid()));
//			landstatics.setWaitLandlordEvaNum(orderLoadlordServiceImpl.countWaitLandlordEvaNum(request.getLandlordUid()));
//			landstatics.setWaitTenantEvaNum(orderLoadlordServiceImpl.countWaitTenantEvaNum(request.getLandlordUid()));
			
			
			dto.putValue("result", landstatics);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
	
}
