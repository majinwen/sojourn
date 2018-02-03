package com.ziroom.minsu.services.order.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgFirstAdvisoryEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.finance.entity.RefuseOrderVo;
import com.ziroom.minsu.services.finance.entity.RemindOrderVo;
import com.ziroom.minsu.services.order.api.inner.CustomerServeService;
import com.ziroom.minsu.services.order.service.CustomerServeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>troy客服平台proxy</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年8月1日
 * @since 1.0
 * @version 1.0
 */
@Service("order.customerServeProxy")
public class CustomerServeProxy implements CustomerServeService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServeProxy.class);
	
	@Resource(name = "order.messageSource")
	private MessageSource messageSource;
	
	@Resource(name = "order.customerServeServiceImpl")
	private CustomerServeServiceImpl customerServeServiceImpl;
	

	/**
	 * 申请预订且房东未回复的订单
	 * @author lishaochuan
	 * @create 2016年8月1日下午6:45:04
	 * @param param
	 * @return
	 */
	@Override
	public String getRemindOrderList(String param) {
		DataTransferObject dto = new DataTransferObject();
		try {
			PageRequest pageRequest = JsonEntityTransform.json2Object(param, PageRequest.class);
			PagingResult<RemindOrderVo> pageResult = customerServeServiceImpl.getRemindOrderList(pageRequest);
			dto.putValue("total", pageResult.getTotal());
        	dto.putValue("list", pageResult.getRows());
		} catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}


	/**
	 * 查询房东拒绝的申请预定（12小时以内）
	 * @author lishaochuan
	 * @create 2016年8月3日下午2:31:05
	 * @param param
	 * @return
	 */
	@Override
	public String getRefuseOrderList(String param) {
		DataTransferObject dto = new DataTransferObject();
		try {
			PageRequest pageRequest = JsonEntityTransform.json2Object(param, PageRequest.class);
			PagingResult<RefuseOrderVo> pageResult = customerServeServiceImpl.getRefuseOrderList(pageRequest);
			dto.putValue("total", pageResult.getTotal());
        	dto.putValue("list", pageResult.getRows());
		} catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String getAdvisoryOrderInfo(String param) {
		DataTransferObject dto = new DataTransferObject();
		try {
			MsgFirstAdvisoryEntity msgFirstAdvisoryEntity = JsonEntityTransform.json2Entity(param,MsgFirstAdvisoryEntity.class);
			OrderEntity orderEntity = customerServeServiceImpl.getAdvisoryOrderInfo(msgFirstAdvisoryEntity);
			dto.putValue("obj", orderEntity);
		} catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
}
