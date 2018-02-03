package com.ziroom.minsu.services.order.proxy;

import java.util.List;

import javax.annotation.Resource;

import com.asura.framework.base.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.entity.order.OrderRemarkEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.order.api.inner.OrderRemarkService;
import com.ziroom.minsu.services.order.dto.RemarkRequest;
import com.ziroom.minsu.services.order.service.OrderCommonServiceImpl;
import com.ziroom.minsu.services.order.service.OrderRemarkServiceImpl;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

/**
 * <p>
 * 备注
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
@Component("order.orderRemarkServiceProxy")
public class OrderRemarkServiceProxy implements OrderRemarkService{

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderRemarkServiceProxy.class);

	@Resource(name = "order.messageSource")
	private MessageSource messageSource;

	@Resource(name = "order.orderRemarkServiceImpl")
	private OrderRemarkServiceImpl orderRemarkServiceImpl;
	
	@Resource(name = "order.orderCommonServiceImpl")
	private OrderCommonServiceImpl orderCommonServiceImpl ;
	
	/**
	 * 获取订单备注数量
	 * @author lishaochuan
	 * @create 2016年6月27日上午11:56:16
	 * @return
	 */
	@Override
	public String getRemarkCount(String params) {
		LogUtil.info(LOGGER, "参数:{}", params);
		DataTransferObject dto = new DataTransferObject();
        try {
        	RemarkRequest request = JsonEntityTransform.json2Object(params, RemarkRequest.class);
        	if(Check.NuNStr(request.getOrderSn())){
				 LogUtil.info(LOGGER, "订单号为空,params:{}", params);
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("参数订单号为空");
				 return dto.toJsonString();
			}
        	if(Check.NuNStr(request.getUid())){
				 LogUtil.info(LOGGER, "uid为空,params:{}", params);
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("uid为空");
				 return dto.toJsonString();
			}
        	
        	//计算当前订单备注数目
			long remarkNum = orderRemarkServiceImpl.countRemark(request.getOrderSn(), request.getUid());
			if( remarkNum >= 5){
				LogUtil.info(LOGGER, "订单备注大于5条orderSn:{} remarkNum:{}", request.getOrderSn(), remarkNum);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("您最多可以添加5条备注信息");
				return dto.toJsonString();
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
	 * 获取备注列表
	 * @author liyingjie
	 * @param param
	 * @return 
	 */
	@Override
	public String getRemarkList(String param) {
		DataTransferObject dto = new DataTransferObject();
		try{
			RemarkRequest request = JsonEntityTransform.json2Object(param, RemarkRequest.class);
			if(Check.NuNStr(request.getOrderSn())){
				 LogUtil.info(LOGGER, "订单号为空,param:{}", param);
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("参数订单号为空");
				 return dto.toJsonString();
			}
			if(Check.NuNStr(request.getUid())){
				 LogUtil.info(LOGGER, "uid为空,param:{}", param);
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("uid为空");
				 return dto.toJsonString();
			}
			
			
			List<OrderRemarkEntity> resList = orderRemarkServiceImpl.getRemarkList(request.getOrderSn(), request.getUid());
			dto.putValue("list", resList);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
	/**
	 * 添加备注
	 * @author liyingjie
	 * @param param
	 * @return 
	 */
	@Override
	public String insertRemarkRes(String param) {
		LogUtil.info(LOGGER, "参数:{}", param);
		DataTransferObject dto = new DataTransferObject();
		try{
			if(Check.NuNStr(param)){
				 LogUtil.info(LOGGER, "参数为空 param:{}", param);
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("参数为空");
				 return dto.toJsonString();
			}
			
			OrderRemarkEntity remarkEntity = JsonEntityTransform.json2Object(param, OrderRemarkEntity.class);
			if(Check.NuNObj(remarkEntity)){
				 LogUtil.info(LOGGER, "参数为空 remarkEntity:{}", remarkEntity);
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("参数为空 remarkEntity");
				 return dto.toJsonString();
			}
			if(Check.NuNStr(remarkEntity.getRemarkContent())){
				 LogUtil.info(LOGGER, "备注内容为空content:{}", remarkEntity.getRemarkContent());
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("备注内容为空");
				 return dto.toJsonString();
			}
			if(Check.NuNStr(remarkEntity.getOrderSn())){
				 LogUtil.info(LOGGER, "备注 关联订单号为空 orderSn:{}", remarkEntity.getOrderSn());
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("备注 关联订单号为空");
				 return dto.toJsonString();
			}
			if(Check.NuNStr(remarkEntity.getUid())){
				 LogUtil.info(LOGGER, "uid为空 orderSn:{}", remarkEntity.getOrderSn());
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("uid为空");
				 return dto.toJsonString();
			}
			
			OrderEntity orderEntity = orderCommonServiceImpl.getOrderBaseByOrderSn(remarkEntity.getOrderSn());
			if (!remarkEntity.getUid().equals(orderEntity.getLandlordUid())) {
				LogUtil.info(LOGGER, "不是自己的订单，orderSn：{},requestId:{},orderLanUid", remarkEntity.getOrderSn(), remarkEntity.getUid(), orderEntity.getLandlordUid());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("无权限");
				return dto.toJsonString();
			}
			
			//计算当前订单备注数目
			long remarkNum = orderRemarkServiceImpl.countRemark(remarkEntity.getOrderSn(), remarkEntity.getUid());
			if( remarkNum >= 5){
				LogUtil.info(LOGGER, "订单备注大于5条orderSnow:{} remarkNum:{}",remarkEntity.getOrderSn(), remarkNum);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("您最多可以添加5条备注信息");
				return dto.toJsonString();
			}
            String fid = UUIDGenerator.hexUUID();
            remarkEntity.setFid(fid);
            //保存备注
			int result = orderRemarkServiceImpl.saveRemark(remarkEntity);
			if(result == YesOrNoEnum.NO.getCode()){
				LogUtil.info(LOGGER, "保存失败！param:{}",JsonEntityTransform.Object2Json(remarkEntity));
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("保存失败！");
				return dto.toJsonString();
			}
            dto.putValue("fid", fid);
        }catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
	
	/**
	 * 删除备注
	 * @author liyingjie
	 * @param param
	 * @return 
	 */
	@Override
	public String delRemark(String param) {
		DataTransferObject dto = new DataTransferObject();
	    try{
	    	RemarkRequest request = JsonEntityTransform.json2Object(param, RemarkRequest.class);
			if(Check.NuNStr(request.getFid())){
				 LogUtil.info(LOGGER, "备注fid为空，param:{}", param);
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("备注fid为空");
				 return dto.toJsonString();
			}
			if(Check.NuNStr(request.getUid())){
				 LogUtil.info(LOGGER, "uid为空，param:{}", param);
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("uid为空");
				 return dto.toJsonString();
			}
			
			int result = orderRemarkServiceImpl.delRemark(request.getFid(), request.getUid());
			if(result == YesOrNoEnum.NO.getCode()){
				LogUtil.info(LOGGER, "删除失败！param:{}",param);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("保存失败！");
				return dto.toJsonString();
			}
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
}
