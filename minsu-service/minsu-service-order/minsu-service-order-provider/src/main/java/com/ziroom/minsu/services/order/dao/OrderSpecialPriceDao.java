package com.ziroom.minsu.services.order.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderConfigEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.entity.order.OrderHouseSnapshotEntity;
import com.ziroom.minsu.entity.order.OrderSpecialPriceEntity;
import com.ziroom.minsu.services.order.utils.FinanceMoneyUtil;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesTonightDisEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> 订单的特殊价格</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/12.
 * @version 1.0
 * @since 1.0
 */
@Repository("order.orderSpecialPriceDao")
public class OrderSpecialPriceDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(OrderSpecialPriceDao.class);

	private String SQLID = "order.orderSpecialPriceDao.";


	@Resource(name = "order.orderBaseDao")
	private OrderBaseDao orderBaseDao;



	@Resource(name = "order.orderConfigDao")
	private OrderConfigDao orderConfigDao;

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 获取订单的特殊价格
	 * @author afi
	 * @created 2016年3月31日 下午20:22:38
	 * @return
	 */
	public List<OrderSpecialPriceEntity> getOrderSpecialPriceList() {
		return mybatisDaoContext.findAll(SQLID + "getOrderSpecialPriceList", OrderSpecialPriceEntity.class);
	}


	/**
	 * 获取当前订单的特殊价格列表
	 * @author afi
	 * @created 2016年3月31日 下午20:22:38
	 * @param orderSn 订单编号
	 * @return
	 */
	public List<OrderSpecialPriceEntity> getOrderSpecialPriceByOrderSn(String orderSn) {
		return mybatisDaoContext.findAll(SQLID + "getOrderSpecialPriceByOrderSn", OrderSpecialPriceEntity.class,orderSn);
	}

	/**
	 * 获取当前订单的特殊价格map
	 * @author afi
	 * @created 2016年4月3日 下午18:22:38
	 * @param orderSn 订单编号
	 * @return
	 */
	public Map<String,Integer> getOrderSpecialPriceMapByOrderSn(String orderSn,int basePrice) {
		Map<String,Integer> priceMap = new HashMap<>();
		List<OrderSpecialPriceEntity> priceEntityList = mybatisDaoContext.findAll(SQLID + "getOrderSpecialPriceByOrderSn", OrderSpecialPriceEntity.class, orderSn);
		if(!Check.NuNObj(priceEntityList)){
			for(OrderSpecialPriceEntity priceEntity: priceEntityList){
				priceMap.put(priceEntity.getPriceDate(), priceEntity.getPriceValue());
			}
		}

		//夹缝配置
		List<OrderConfigEntity>  creviceList = new ArrayList<>();
		List<OrderConfigEntity>  configEntityList = orderConfigDao.getOrderConfigListByOrderSn(orderSn);
		if (!Check.NuNCollection(configEntityList)){
			for (OrderConfigEntity configEntity : configEntityList) {
				if (configEntity.getConfigCode().startsWith(ProductRulesEnum.ProductRulesEnum020.getValue())
						||configEntity.getConfigCode().equals(ProductRulesTonightDisEnum.ProductRulesTonightDisEnum001.getCode())){
					creviceList.add(configEntity);
				}
			}
		}
		//当前的夹缝配置非空
		if (!Check.NuNCollection(creviceList)){
			OrderEntity orderEntity = orderBaseDao.getOrderBaseByOrderSn(orderSn);
			FinanceMoneyUtil.fillCrevice2SpecialPrices(orderEntity.getStartTime(),orderEntity.getEndTime(),basePrice,priceMap,creviceList);
		}
		return priceMap;
	}



	/**
	 * 
	 * 获取当前订单的夹心价格后,优惠价格map
	 *
	 * @author yd
	 * @created 2017年3月8日 上午11:21:12
	 *
	 * @param orderSn  订单编号
	 * @param basePrice 基本价格
	 * @param realEndTime 提前退房时间
	 * @return
	 */
	public int getOrderSandwichDisCountPriceMapByOrderSn(String orderSn,int basePrice,Date realEndTime){

		Map<String,Integer> specialPriceMap = new HashMap<>();
		Map<String,Integer> discountPriceMap = new HashMap<>();
		
		int sandwichDisCountPrice = 0;
		
		if(Check.NuNObj(realEndTime)){
			LogUtil.error(logger, "【提前退房——重新计算夹心价格的优惠】异常orderSn={},realEndTime={}", orderSn,realEndTime);
			throw new BusinessException("提前退房——重新计算夹心价格的优惠");
		}
	
		//夹缝配置
		List<OrderConfigEntity>  creviceList = new ArrayList<>();
		List<OrderConfigEntity>  configEntityList = orderConfigDao.getOrderConfigListByOrderSn(orderSn);
		if (!Check.NuNCollection(configEntityList)){
			for (OrderConfigEntity configEntity : configEntityList) {
				if (configEntity.getConfigCode().startsWith(ProductRulesEnum.ProductRulesEnum020.getValue())){
					creviceList.add(configEntity);
				}
			}
		}

		//当前的夹缝配置非空
		if (!Check.NuNCollection(creviceList)){
			
			List<OrderSpecialPriceEntity> priceEntityList = mybatisDaoContext.findAll(SQLID + "getOrderSpecialPriceByOrderSn", OrderSpecialPriceEntity.class, orderSn);
			if(!Check.NuNObj(priceEntityList)){
				for(OrderSpecialPriceEntity priceEntity: priceEntityList){
					specialPriceMap.put(priceEntity.getPriceDate(), priceEntity.getPriceValue());
				}
			}
			
			OrderEntity orderEntity = orderBaseDao.getOrderBaseByOrderSn(orderSn);
			FinanceMoneyUtil.fillSandwichDiscountMoneyMap(orderEntity.getStartTime(),orderEntity.getEndTime(),realEndTime,basePrice,
					specialPriceMap,creviceList,discountPriceMap);
		}
		
		if(!Check.NuNMap(discountPriceMap)){
			for (Map.Entry<String, Integer> entry: discountPriceMap.entrySet()) {
				sandwichDisCountPrice +=entry.getValue();
			}
		}

		return sandwichDisCountPrice;
	}

	/**
	 * 保存订单的特殊价格
	 * @author afi
	 * @created 2016年3月31日 下午20:22:38
	 * @param special
	 * @return
	 */
	public int insertOrderSpecialPrice(OrderSpecialPriceEntity special) {
		if(Check.NuNObj(special)){
			LogUtil.error(logger,"current special is null on insertOrderSpecialPrice");
			throw new BusinessException("current special is null on insertOrderSpecialPrice");
		}
		if(Check.NuNStr(special.getOrderSn())){
			LogUtil.error(logger,"orderSn is null on insertOrderSpecialPrice");
			throw new BusinessException("orderSn is null on insertOrderSpecialPrice");
		}
		if(Check.NuNObj(special.getPriceDate())){
			LogUtil.error(logger,"priceDate is null on insertOrderSpecialPrice");
			throw new BusinessException("priceDate is null on insertOrderSpecialPrice");
		}

		if(Check.NuNObj(special.getPriceValue()) || special.getPriceValue() < 1){
			LogUtil.error(logger,"priceValue is not ok on insertOrderSpecialPrice");
			throw new BusinessException("priceValue is not ok on insertOrderSpecialPrice");
		}
		return mybatisDaoContext.save(SQLID + "insertOrderSpecialPrice", special);
	}

}
