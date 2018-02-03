package com.ziroom.minsu.services.order.proxy;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseDayRevenueEntity;
import com.ziroom.minsu.entity.order.FinancePunishEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.finance.dto.PunishListRequest;
import com.ziroom.minsu.services.order.api.inner.OrderFinanceService;
import com.ziroom.minsu.services.order.service.FinancePayServiceImpl;
import com.ziroom.minsu.services.order.service.FinancePunishServicesImpl;

/**
 * <p>
 * 付款单表收入表，相关接口
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年4月23日
 * @since 1.0
 * @version 1.0
 */
@Component("order.orderFinanceServiceProxy")
public class OrderFinanceServiceProxy implements OrderFinanceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderFinanceServiceProxy.class);

	@Resource(name = "order.messageSource")
	private MessageSource messageSource;
	
	
	@Resource(name = "order.financePayServiceImpl")
	private FinancePayServiceImpl financePayService;
	
	@Resource(name = "order.financePunishServicesImpl")
	private FinancePunishServicesImpl financePunishServices;

	
	/**
	 * 我的扣款单列表
	 * @author lishaochuan
	 * @create 2016年4月25日
	 * @param request
	 * @return
	 */
	@Override
	public String getPunishList(String request) {
		PunishListRequest punishRequest = JsonEntityTransform.json2Object(request, PunishListRequest.class);
		DataTransferObject dto = new DataTransferObject();
		/** 校验 请求参数存在 */
		if (Check.NuNObj(punishRequest)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请求参数不存在");
			return dto.toJsonString();
		}
		if (Check.NuNObj(punishRequest) || Check.NuNObj(punishRequest.getPunishUid())) {
			LogUtil.error(LOGGER, "请求参数错误,punishRequest:{}", punishRequest);
			throw new BusinessException("请求参数错误");
		}

		PagingResult<FinancePunishEntity> punishList = financePunishServices.getPunishListByCondition(punishRequest);

		dto.putValue("orderHouseList", punishList.getRows());
		dto.putValue("size", punishList.getTotal());
		return dto.toJsonString();
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.order.api.inner.OrderFinanceService#landlordDayRevenueList(java.lang.String)
	 */
	@Override
	public String landlordDayRevenueList(String request) {
		DataTransferObject dto = new DataTransferObject();
		try{
			//判断传入时间是否存在
			if(Check.NuNStr(request)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("参数不能为空");
				return dto.toJsonString();
			}
			List<HouseDayRevenueEntity> list=financePayService.findLandlordDayRevenueList(DateUtil.parseDate(request, "yyyy-MM-dd"));
			dto.putValue("list", list);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
}
