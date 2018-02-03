package com.ziroom.minsu.services.solr.query.parser.support;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.search.LabelTipsEntity;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.search.entity.ZryProjectInfoEntity;
import com.ziroom.minsu.services.solr.query.parser.AbstractQueryResultParser;
import com.ziroom.minsu.valenum.search.LabelTipsEnum;
import com.ziroom.minsu.valenum.search.LabelTipsStyleEnum;

/**
 * <p>
 * </p>
 * <p>
 * 
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zl
 * @version 1.0
 * @Date Created in 2017年07月25日 15:08
 * @since 1.0
 */
public class ZryHouseInfoQueryResultParser extends AbstractQueryResultParser<ZryProjectInfoEntity> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ZryHouseInfoQueryResultParser.class);

	private static final String datePatten = "yyMMdd";
	private static final String split = ",";

	@SuppressWarnings("unchecked")
	@Override
	protected ZryProjectInfoEntity doParser(Map<String, Object> par, SolrDocument doc) {
		if (Check.NuNObj(doc)) {
			return null;
		}
		ZryProjectInfoEntity zryProject = new ZryProjectInfoEntity();
		zryProject.setProjectBid(ValueUtil.getStrValue(doc.getFieldValue("houseId")));
		zryProject.setProjectName(ValueUtil.getStrValue(doc.getFieldValue("houseName")));
		zryProject.setCityName(ValueUtil.getStrValue(doc.getFieldValue("cityName")));
		zryProject.setAreaName(ValueUtil.getStrValue(doc.getFieldValue("areaName")));
		zryProject.setHouseModelCount(ValueUtil.getintValue(doc.getFieldValue("houseModelCount")));
		zryProject.setEvaluateCount(ValueUtil.getintValue(doc.getFieldValue("evaluateCount")));
		zryProject.setEvaluateScore(ValueUtil.getdoubleValue(doc.getFieldValue("evaluateScore")));
		zryProject.setMinPrice(ValueUtil.getintValue(doc.getFieldValue("price")) / 100);
		zryProject.setMaxPrice(ValueUtil.getintValue(doc.getFieldValue("priceMax")) / 100);
		zryProject.setPicUrl(ValueUtil.getStrValue(doc.getFieldValue("picUrl")));
		// 状态 默认-0，新上-1，未上-2
		zryProject.setStatus(ValueUtil.getintValue(doc.getFieldValue("status")));

		Date startDate = getStartDate(par);
		Date endDate = getEndTime(par);

		// 房态标签
		List<LabelTipsEntity> labelTipsList = new ArrayList<>();

		// 有筛选时间并且已上架的项目才判断房态标签
		if (!Check.NuNObj(startDate) && !Check.NuNObj(endDate) && startDate.before(endDate) && (zryProject.getStatus() == 0 || zryProject.getStatus() == 1)) {
			// 将来日期的订单锁定库存数
			List<String> orderLockedStocks = (List<String>) doc.getFieldValue("dayOrderLockedStocks");
			// 将来日期的其他锁定库存数
			List<String> selfLockedStocks = (List<String>) doc.getFieldValue("daySelfLockedStocks");
			Integer totalStock = (Integer) doc.getFieldValue("totalStock");

			// 可用库存Map<"yyMMdd", stock>
			Map<String, Integer> stocksMap = new TreeMap<>();

			// 计算订单锁定后的剩余库存
			if (!Check.NuNCollection(orderLockedStocks)) {
				for (String lockedStocks : orderLockedStocks) {
					String[] arr = lockedStocks.split(split);

					Integer lockedStock = Integer.parseInt(arr[1]);

					stocksMap.put(arr[0], totalStock - lockedStock);
				}
			}

			// 计算其他锁定后的剩余量
			if (!Check.NuNCollection(selfLockedStocks)) {
				for (String lockedStocks : selfLockedStocks) {
					String[] arr = lockedStocks.split(split);

					Integer lockedStock = Integer.parseInt(arr[1]);

					// 默认总库存
					Integer stock = totalStock;

					// 获取上一步更新后的库存
					if (stocksMap.containsKey(arr[0])) {
						stock = stocksMap.get(arr[0]);
					}

					stocksMap.put(arr[0], stock - lockedStock);
				}
			}

			// 计算筛选区间内的最小库存
			List<Date> days = DateSplitUtil.dateSplit(startDate, endDate);
			if (Check.NuNCollection(days)) {
				days = new ArrayList<>();
			}

			int minStock = totalStock;
			for (Date date : days) {
				Integer stock = stocksMap.get(DateUtil.dateFormat(date, datePatten));
				if (!Check.NuNObj(stock) && stock < minStock) {
					minStock = stock;
				}
			}

			int order = 1;
			// 满房
			if (minStock == 0) {
				LabelTipsEntity labelEntity = new LabelTipsEntity();
				labelEntity.setIndex(order);
				order++;
				labelEntity.setName(LabelTipsEnum.IS_ZRY_FULL.getName());
				labelEntity.setTipsType(LabelTipsStyleEnum.WORDS_WITH_APP_BUTTON.getCode());
				labelTipsList.add(labelEntity);
			} else {
				// 紧张
				if (minStock < 5) {
					LabelTipsEntity labelEntity = new LabelTipsEntity();
					labelEntity.setIndex(order);
					order++;
					labelEntity.setName(LabelTipsEnum.IS_ZRY_NERVOUS.getName());
					labelEntity.setTipsType(LabelTipsStyleEnum.WORDS_WITH_APP_BUTTON.getCode());
					labelTipsList.add(labelEntity);
				}
				// 床位不足
				// 同行人数
				Integer personCount = (Integer) par.get("personCount");
				if (!Check.NuNObj(personCount) && minStock < personCount) {
					LabelTipsEntity labelEntity = new LabelTipsEntity();
					labelEntity.setIndex(order);
					order++;
					labelEntity.setName(LabelTipsEnum.IS_ZRY_BED_NOT_ENOUGH.getName());
					labelEntity.setTipsType(LabelTipsStyleEnum.WORDS_WITH_APP_BUTTON.getCode());
					labelTipsList.add(labelEntity);
				}
			}
		}

		zryProject.setLabelTipsList(labelTipsList);

		return zryProject;
	}

	/**
	 * 获取搜索起始时间
	 *
	 * @author zl
	 * @created 2017年3月7日 下午4:37:05
	 *
	 * @param par
	 * @return
	 */
	private static Date getStartDate(Map<String, Object> par) {
		String startTime = ValueUtil.getStrValue(par.get("startTime"));
		Date startDate = null;
		if (!Check.NuNStr(startTime)) {
			try {
				startDate = DateUtil.parseDate(startTime, "yyyy-MM-dd");
			} catch (Exception e) {
				LogUtil.info(LOGGER, "startTime={}, parse error:{}", startTime, e);
			}
		}
		return startDate;
	}

	/**
	 * 获取搜索的结束时间
	 *
	 * @author zl
	 * @created 2017年3月7日 下午4:44:33
	 *
	 * @param par
	 * @return
	 */
	private static Date getEndTime(Map<String, Object> par) {
		String endTime = ValueUtil.getStrValue(par.get("endTime"));
		Date endDate = null;
		if (!Check.NuNStr(endTime)) {
			try {
				endDate = DateUtil.parseDate(endTime, "yyyy-MM-dd");
			} catch (Exception e) {
				LogUtil.info(LOGGER, "endTime={},parse error:{}", endTime, e);
			}
		}
		return endDate;
	}
}
