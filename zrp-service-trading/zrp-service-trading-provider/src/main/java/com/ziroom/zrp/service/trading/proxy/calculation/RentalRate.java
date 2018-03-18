package com.ziroom.zrp.service.trading.proxy.calculation;

import com.alibaba.fastjson.JSON;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.entity.entityenum.ServiceLineEnum;
import com.ziroom.minsu.services.cms.api.inner.ActivityService;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.valenum.zrpenum.ContractTradingEnum;
import com.ziroom.minsu.valenum.zrpenum.ContractTradingEnum004;
import com.ziroom.zrp.houses.entity.RentPriceStrategyEntity;
import com.ziroom.zrp.service.houses.api.RentPriceStrategyService;
import com.ziroom.zrp.service.trading.pojo.LeaseCyclePojo;
import com.ziroom.zrp.service.trading.pojo.PaymentBaseDataPojo;
import com.ziroom.zrp.service.trading.utils.SpringContextUtils;
import com.ziroom.zrp.service.trading.valenum.ActivityCategoryEnum;
import com.ziroom.zrp.service.trading.valenum.LeaseCycleEnum;
import com.ziroom.zrp.service.trading.valenum.finance.CostCodeEnum;
import com.ziroom.zrp.trading.entity.RentContractActivityEntity;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>出租费率类</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @Date Created in 2017年09月14日 10:52
 * @version 1.0
 * @since 1.0
 */
public class RentalRate {
	private static final Logger LOGGER = LoggerFactory.getLogger(RentalRate.class);
	private static final String LOG_RESULT = "【getTextValueForCommon】请求结果={}";
	private static final String LOG_ERR = "【getTextValueForCommon】请求错误={}";
	private static final String ERROR = "访问特洛伊配置服务接口不通,请联系相关人员";
	private static final String DISCOUNT_SEPARATOR = "-";
	protected RentPriceStrategyEntity rentPriceStrategyEntity; //价格调幅对象
	private List<String> oldCustomerRate;
	private List<String> advanceRenewRate;


	private RentPriceStrategyService rentPriceStrategyService;
	private CityTemplateService cityTemplateService;
	private ActivityService activityService;

	protected RentalRate(LeaseCyclePojo leaseCyclePojo) throws Exception{
		rentPriceStrategyService = (RentPriceStrategyService) SpringContextUtils.getApplicationContext().getBean("houses.rentPriceStrategyService");
		cityTemplateService = (CityTemplateService) SpringContextUtils.getApplicationContext().getBean("basedata.cityTemplateService");
		activityService = (ActivityService) SpringContextUtils.getApplicationContext().getBean("cms.activityService");

		//查询房租和服务费调幅
		getRentPriceStrategy(leaseCyclePojo.getProjectId(), leaseCyclePojo.getRentType());
		// 查询续约折扣
		getRenewDiscount();
	}

	public RentalRate() {
	}

	/**
	 * 查询续约折扣
	 */
	private void getRenewDiscount() throws Exception{
		try {
			String enumCode = ContractTradingEnum004.ContractTradingEnum004002.getValue();
			String oldCustomerRateStr = cityTemplateService.listTextValueForCommon(ServiceLineEnum.ZRP.getCode(), enumCode);
			DataTransferObject oldCustomerRateObject = JsonEntityTransform.json2DataTransferObject(oldCustomerRateStr);
			if (oldCustomerRateObject.getCode() == DataTransferObject.SUCCESS) {
				oldCustomerRate = oldCustomerRateObject.parseData("listValue", new TypeReference<List<String>>() {
				});
				LogUtil.info(LOGGER, "【listTextValueForCommon】请求结果={}", oldCustomerRateStr);
			} else {
				LogUtil.error(LOGGER, "【listTextValueForCommon】请求错误={}", oldCustomerRateStr);
				throw new Exception(oldCustomerRateObject.getMsg());
			}

			String enumCode1 = ContractTradingEnum004.ContractTradingEnum004001.getValue();
			String advanceRenewRateStr = cityTemplateService.listTextValueForCommon(ServiceLineEnum.ZRP.getCode(), enumCode1);
			DataTransferObject advanceRenewRateObject = JsonEntityTransform.json2DataTransferObject(advanceRenewRateStr);
			if (advanceRenewRateObject.getCode() == DataTransferObject.SUCCESS) {
				advanceRenewRate = advanceRenewRateObject.parseData("listValue", new TypeReference<List<String>>() {
				});
				LogUtil.info(LOGGER, "【listTextValueForCommon】请求结果={}", advanceRenewRateStr);
			} else {
				LogUtil.error(LOGGER, "【listTextValueForCommon】请求错误={}", advanceRenewRateStr);
				throw new Exception(advanceRenewRateObject.getMsg());
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, LOG_ERR, e);
			throw new Exception(ERROR);
		}
	}


	/**
	 * 查询标准服务费率
	 */
	protected BigDecimal getStandardServiceRate() throws Exception {
		try {
			String enumCode = ContractTradingEnum.ContractTradingEnum006.getValue();
			String rateStr = cityTemplateService.getTextValueForCommon(ServiceLineEnum.ZRP.getCode(), enumCode);
			DataTransferObject paymentMethodObject = JsonEntityTransform.json2DataTransferObject(rateStr);
			if (paymentMethodObject.getCode() == DataTransferObject.SUCCESS) {
				String discount = paymentMethodObject.parseData("textValue", new TypeReference<String>() {
				});
				LogUtil.info(LOGGER, LOG_RESULT, rateStr);
				return new BigDecimal(discount);
			} else {
				LogUtil.error(LOGGER, LOG_ERR, rateStr);
				throw new Exception(paymentMethodObject.getMsg());
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, LOG_ERR, e);
			throw new Exception(ERROR);
		}
	}

	/**
	 * 获取调幅
	 *
	 * @param projectId 项目标识
	 * @param rentType  出租类型
	 */
	private void getRentPriceStrategy(String projectId, Integer rentType) {
		RentPriceStrategyEntity param = new RentPriceStrategyEntity();
		param.setProjectId(projectId);
		param.setRentType(rentType);
		String strategyStr = rentPriceStrategyService.getRentPriceStrategy(JSON.toJSONString(param));
		DataTransferObject PaymentMethodObject = JsonEntityTransform.json2DataTransferObject(strategyStr);
		if (PaymentMethodObject.getCode() == DataTransferObject.SUCCESS) {
			rentPriceStrategyEntity = PaymentMethodObject.parseData("rentPriceStrategy", new TypeReference<RentPriceStrategyEntity>() {
			});
			LogUtil.info(LOGGER, "【getRentPriceStrategy】请求结果={}", strategyStr);
		} else {
			LogUtil.error(LOGGER, "【getRentPriceStrategy】请求错误={}", strategyStr);
		}
	}

	/**
	 * 获取活动列表 从活动服务
	 * @param paramJson 必须参数
	 * @return
	 * @throws Exception
	 * @author cuigh6
	 * @Date 2017年12月24日
	 */
	protected DataTransferObject getActivityConfigList(String paramJson) throws Exception {
		DataTransferObject dto = null;
		try {
			String result = activityService.listActFeeConditionForZrp(paramJson);
			LogUtil.info(LOGGER, "【listActFeeConditionForZrp】请求结果返回:{}", result);
			dto = JsonEntityTransform.json2DataTransferObject(result);
			if (dto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "【listActFeeConditionForZrp】请求结果返回失败,param={}",paramJson);
				throw new Exception(dto.getMsg());
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【listActFeeConditionForZrp】查询活动失败", e);
			throw new Exception(ERROR);
		}
		return dto;
	}

	/**
	 * 获取续约折扣服务费
	 * @param paymentBaseDataPojo 基本数据
	 * @param list 活动列表
	 * @return
	 * @author cuigh6
	 * @Date 2017年12月
	 */
	protected BigDecimal getDiscountServiceFee(PaymentBaseDataPojo paymentBaseDataPojo, List<RentContractActivityEntity> list,BigDecimal servicePrice) {
		RentContractActivityEntity activityEntity;Date conStartDate = paymentBaseDataPojo.getContractPojo().getConStartDate();
		//判断续租 续约次数 和提前续租天数 判断续租时常满几年 暂定一年为365天
		int dayDiff1 = DateSplitUtil.countDateSplitNeq(paymentBaseDataPojo.getContractPojo().getFirstSignStartDate(),
				paymentBaseDataPojo.getContractPojo().getLastSignEndDate()) + 1;
		Date currentDate = new Date();// 如果当前日期在原合同结束日后(包括结束日) 则提前续约天数为0
		int advanceDay = currentDate.after(DateSplitUtil.getYesterday(conStartDate)) ? 0 : DateSplitUtil.countDateSplit(currentDate, DateSplitUtil.getYesterday(conStartDate));
		//老客户续约折扣
		for (String s : oldCustomerRate) {
			String[] strings = s.split("-"); // 续租折扣的比例是按 1-0.8 的格式 前面为年 后面为折扣
			if (dayDiff1 / 365 == Integer.parseInt(strings[0])) {
				BigDecimal renewDisCount = new BigDecimal(strings[1]);
				BigDecimal discountPrice = servicePrice.multiply(renewDisCount).setScale(0, BigDecimal.ROUND_HALF_UP);
				Double activityPrice = servicePrice.subtract(discountPrice).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (activityPrice != 0) {
					activityEntity = new RentContractActivityEntity();
					activityEntity.setActivityName(ActivityCategoryEnum.OLD_CUSTOMER.getName());
					activityEntity.setContractId(paymentBaseDataPojo.getContractPojo().getContractId());
					activityEntity.setCategory(ActivityCategoryEnum.OLD_CUSTOMER.getCode());
					activityEntity.setDiscountAccount(activityPrice);
					activityEntity.setExpenseItemCode(CostCodeEnum.KHFWF.getZraCode());
					list.add(activityEntity);
				}
				servicePrice = discountPrice;//减免后金额
			}
		}
		// 如果原合同为年租合同 可享受 提前续约折扣
		if (LeaseCycleEnum.YEAR.getCode().equals(paymentBaseDataPojo.getContractPojo().getPreConType())) {
			//提前续约折扣
			for (String s : advanceRenewRate) {
				String[] strings = s.split(DISCOUNT_SEPARATOR);// // 续租折扣的比例是按 19-10-0.8 的格式 19为上限天数 10天为下限天数 0.8为折扣
				int up = Integer.parseInt(strings[0]);
				int down = Integer.parseInt(strings[1]);

				if (down < advanceDay && advanceDay <= up) {
					BigDecimal renewDisCount = new BigDecimal(strings[2]);
					BigDecimal discountPrice = servicePrice.multiply(renewDisCount).setScale(0, BigDecimal.ROUND_HALF_UP);
					Double activityPrice = servicePrice.subtract(discountPrice).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
					if (activityPrice != 0) {
						activityEntity = new RentContractActivityEntity();
						activityEntity.setActivityName(ActivityCategoryEnum.ADVANCE.getName());
						activityEntity.setCategory(ActivityCategoryEnum.ADVANCE.getCode());
						activityEntity.setDiscountAccount(activityPrice);// 优惠减免金额
						activityEntity.setContractId(paymentBaseDataPojo.getContractPojo().getContractId());
						activityEntity.setExpenseItemCode(CostCodeEnum.KHFWF.getZraCode());
						list.add(activityEntity);
					}
					servicePrice = discountPrice;//减免后金额
				}
			}
		}
		return servicePrice;
	}
}
