package com.ziroom.minsu.services.solr.query.transfer.support;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.house.RoomTypeEnum;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.common.params.SolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.search.entity.QueryRequest;
import com.ziroom.minsu.services.solr.query.transfer.AbstractParameterTransfer;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0019;
import com.ziroom.minsu.valenum.search.RoomCountEnum;
import com.ziroom.minsu.valenum.search.SearchDataSourceTypeEnum;
import com.ziroom.minsu.valenum.search.SortTypeEnum;

/**
 * <p>房源的参数信息转换</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/16.
 *
 *
 * @version 1.0
 * @since 1.0
 */
public class HouseInfoQueryParameterTransfer extends AbstractParameterTransfer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseInfoQueryParameterTransfer.class);

    private String dateFormatPattern = "yyyy-MM-dd";

    private int maxDaysLimit = 180;

	/**
	 * 参数转换
	 * 
	 * @param request
	 * @return
	 */
	public SolrParams transfer(QueryRequest request) {

		String keyword = request.getQ();

		if (Check.NuNObj(keyword) || keyword.length() == 0) {
			return null;
		}

		SolrQuery params = new SolrQuery(keyword);

		// params.set("qf","houseName^2 text^0.5");

		// params.set("qf","houseName");

		params.setFields("*,score");

		reBuildPager(params, request.getPageIndex(), request.getPageSize());
		addFilterQuery(params, request.getFilterQueries());
		setReturnFields(params, request.getReturnFields());
		// 价格就走这个
		addRangeFilterQuery(params, request.getRangeFilterQueries());
		addSorts(params, request.getSorts());
		addFacet(params, request.getFacet() == 1);
		// 设置空间的搜索条件 只有在房源这边设置，其他地方暂时没用到
		addSpatialSolrPar(params, request.getSolrParEntity());

		// 搜索框为空或者默认排序的时候设置推荐
		if (!Check.NuNObj(request.getIsRecommend()) && request.getIsRecommend()==YesOrNoEnum.YES.getCode() &&(Check.NuNStr(keyword) || "*:*".equals(keyword))
				&& (!Check.NuNObj(request.getSortType()) && SortTypeEnum.DEFAULT.getCode() == request.getSortType())) {
			addRecommend(params, request);
		}

		return params;
	}

    /**
     * 过滤查询
     * @author afi
     * @param params
     * @param filterQueries
     */
    protected void addFilterQuery(SolrQuery params,Map<String, Object> filterQueries) {
    	
    	//区分solr文档里 民宿、自如驿
    	params.addFilterQuery("dataSource:" + SearchDataSourceTypeEnum.minsu.getCode());
    	
        if (filterQueries != null) {
            Object startTime = null;
            Object endTime = null;
            for (Iterator<Map.Entry<String, Object>> iter = filterQueries
                    .entrySet().iterator(); iter.hasNext();) {
                Map.Entry<String, Object> entry = iter.next();
                String key = entry.getKey();
                Object val = entry.getValue();
                if (Check.NuNObjs(key, val)) {
                    continue;
                }
                if("startTime".equals(key)){
                    startTime = val;
                    continue;
                }
                if("endTime".equals(key)){
                    endTime = val;
                    continue;
                }
                if("roomCount".equals(key)){
                    String solrQuery = RoomCountEnum.getSolrQuery(ValueUtil.getintValue(val));
                    if (!Check.NuNStr(solrQuery)){
                        //当前的搜索条件非空
                        params.addFilterQuery(key + ':' +solrQuery );
                    }
                    continue;
                }
                if("houseType".equals(key)){
                	Set<Integer> houseType = (Set<Integer>) entry.getValue();
                	if (houseType!=null && houseType.size()>0) {
                		
                		List<String> houseTypeList = new ArrayList<>();
                		for (Integer integer : houseType) {
                			houseTypeList.add(integer.toString());
    					}                 		 
						params.addFilterQuery(key + ':' +ValueUtil.transList2StrInSolr(houseTypeList));
					}
                    continue;
                }
                if("longTermLeaseDiscount".equals(key)){
                	Set<String> set = (Set<String>) entry.getValue();
                	if (set!=null && set.size()>0) {
                		StringBuffer str = new StringBuffer();
                		for (String string : set) {
                			if (str.length()==0) {
								str.append("(").append(string).append("*");
							}else{
								str.append(" OR ").append(string).append("*");
							}
						}
                		str.append(")");
                		params.addFilterQuery(key + ':' +str.toString());
                	}
                    continue;
                } 
                
                if("flexiblePrice".equals(key)){
                	Set<String> set = (Set<String>) entry.getValue();
                	if (set!=null && set.size()>0) {
                		StringBuffer str = new StringBuffer();
                		for (String string : set) {
                			if (str.length()==0) {
								str.append("(").append(string).append("*");
							}else{
								str.append(" OR ").append(string).append("*");
							}
						}
                		str.append(")");
                		params.addFilterQuery(key + ':' +str.toString());
                	}
                    continue;
                }
                
                if("houseName".equals(key)){
                	String houseName = (String) entry.getValue();
                	if (Check.NuNStr(houseName)) {
                		params.addFilterQuery(key + ":*" +houseName+"*");
					}
                	 
                    continue;
                }

                // 多种出租方式 下面处理
                if ("multiRentWay".equals(key) || "roomType".equals(key)) {
                    continue;
                }
                
                params.addFilterQuery(key + ':' + val);
            }
            
            if(!Check.NuNObjs(startTime, endTime)){
                try {

                    if(DateSplitUtil.countDateSplit(DateUtil.parseDate(ValueUtil.getStrValue(startTime), dateFormatPattern),DateUtil.parseDate(ValueUtil.getStrValue(endTime), dateFormatPattern)) < maxDaysLimit){
                        //如果当前的时间的跨度大于30天不做精确匹配
                        String value = DateSplitUtil.getParStrByStr(startTime + "", endTime + "");
                        params.addFilterQuery("-occupyDays" + ':' + value); 
                    }
                }catch (Exception e){
                    LogUtil.error(LOGGER, "FilterQuery参数转化异常，e:{}", e);
                }
            }

            // 查询多种出租方式，以下涉及两个字段，有先后逻辑顺序，改动时请注意 2017-11-21 zhangyl2

            // 查询出租方式的fq
            // ""
            // rentWay:(0)
            // rentWay:(1)
            // rentWay:(0 OR 1)
            StringBuilder rentWayFq = new StringBuilder();
            if (!Check.NuNObj(filterQueries.get("multiRentWay"))) {
                // 多种出租方式都查
                Set<String> multiRentWay = (Set<String>) filterQueries.get("multiRentWay");
                rentWayFq.append("rentWay:");

                StringBuilder str = new StringBuilder();
                for (String rentWay : multiRentWay) {
                    if (str.length() == 0) {
                        str.append("(").append(rentWay);
                    } else {
                        str.append(" OR ").append(rentWay);
                    }
                }
                str.append(")");

                rentWayFq.append(str);
            }

            // 选了出租方式，处理roomType
            Integer roomType = (Integer) filterQueries.get("roomType");
            if (rentWayFq.length() > 0) {
                if (Check.NuNObj(roomType) || roomType == RoomTypeEnum.ROOM_TYPE.getCode()) {
                    // 排除共享客厅
                    // rentWay:x AND roomType:0
                    rentWayFq.append(" AND roomType:" + RoomTypeEnum.ROOM_TYPE.getCode());
                } else if (roomType == RoomTypeEnum.HALL_TYPE.getCode()) {
                    // 并集共享客厅
                    // rentWay:x OR roomType:1
                    rentWayFq.append(" OR roomType:" + roomType);
                }
            } else {
                // 没选出租方式，选了共享客厅
                // rentWay:1 AND roomType:1
                if (!Check.NuNObj(roomType) && roomType == RoomTypeEnum.HALL_TYPE.getCode()) {
                    rentWayFq.append("rentWay:" + RentWayEnum.ROOM.getCode() + " AND roomType:" + roomType);
                }
                // 没选出租方式，没选共享客厅
                // 没有任何处理
            }

            if(rentWayFq.length() > 0){
                params.addFilterQuery(rentWayFq.toString());
            }
        }
    }
    
    /**
     * 设置推荐条件
     *
     * @author zl
     * @created 2016年12月8日 下午2:59:19
     *
     * @param params
     * @param request
     */
    private void addRecommend(SolrQuery params,QueryRequest request){
    	
    	try {
    		Map<String, Object> filterQueries =request.getFilterQueries();
    		
    		Date startDate=null;
    		Date endDate=null;
    		String cityCode=null;
    		String startTime =null;
    		String endTime =null;
    		
    		if (!Check.NuNMap(filterQueries)) {
    			startTime = ValueUtil.getStrValue(filterQueries.get("startTime"));
    			endTime = ValueUtil.getStrValue(filterQueries.get("endTime"));
    			if (!Check.NuNStr(startTime) && !Check.NuNStr(endTime)) {
    				try {
    					startDate = DateUtil.parseDate(ValueUtil.getTrimStrValue(startTime), dateFormatPattern);
    					endDate = DateUtil.parseDate(ValueUtil.getTrimStrValue(endTime),dateFormatPattern);
    					
    					if(DateSplitUtil.countDateSplit(startDate,endDate) > 90){
    						endDate = DateSplitUtil.jumpDate(startDate, 90);
    					}
    					
    				}catch (Exception e){
    					LogUtil.error(LOGGER, "query par:{},e:{}", JsonEntityTransform.Object2Json(filterQueries), e);
    				}
    			}
    			cityCode = ValueUtil.getStrValue(filterQueries.get("cityCode"));
    		}
    		
    		if (!Check.NuNStr(cityCode) && (Check.NuNObj(startDate) || Check.NuNObj(endDate))) {//客户在想去的城市里随便看看，还没有决定时间
    			
    			if (!Check.NuNObj(request.getIsTargetCityLocal()) && request.getIsTargetCityLocal() == YesOrNoEnum.YES.getCode()) {
    				//本地人咨询量和订单数量热度靠前商圈中的房源
    				List<SortClause> sorts = params.getSorts();
    				List<SortClause> sortsNew = new ArrayList<>();
    				SortClause cSortClause = new SortClause("currentReginMaxHotIndex", SolrQuery.ORDER.desc);
    				sortsNew.add(cSortClause);
    				if (!Check.NuNCollection(sorts)) {
    					sortsNew.addAll(sorts); 
    				}
    				params.setSorts(sortsNew);
    				
    			}else if (!Check.NuNObj(request.getIsTargetCityLocal()) && request.getIsTargetCityLocal() == YesOrNoEnum.NO.getCode()) {
    				//特定城市的推荐特色房源
    				List<SortClause> sorts = params.getSorts();
    				List<SortClause> sortsNew = new ArrayList<>();
    				SortClause cSortClause = new SortClause("featureHouseIndex", SolrQuery.ORDER.desc);
    				sortsNew.add(cSortClause);
    				if (!Check.NuNCollection(sorts)) {
    					sortsNew.addAll(sorts); 
    				}
    				params.setSorts(sortsNew);				
    			}
    			
    		}else if (!Check.NuNStr(cityCode) && !Check.NuNObj(startDate) && !Check.NuNObj(endDate)) {//客户用比较强烈的冲动，已经计划好要去的地方和时间
    			
    			if (!Check.NuNObj(request.getIsTargetCityLocal()) && request.getIsTargetCityLocal() == YesOrNoEnum.YES.getCode()) {
    				//本地人咨询量和订单数量热度靠前商圈中的房源
    				List<SortClause> sorts = params.getSorts();
    				List<SortClause> sortsNew = new ArrayList<>();
    				SortClause cSortClause = new SortClause("currentReginMaxHotIndex", SolrQuery.ORDER.desc);
    				sortsNew.add(cSortClause);
    				if (!Check.NuNCollection(sorts)) {
    					sortsNew.addAll(sorts); 
    				}
    				params.setSorts(sortsNew);
    				
    			}else if (!Check.NuNObj(request.getIsTargetCityLocal()) && request.getIsTargetCityLocal() == YesOrNoEnum.NO.getCode()) {
    				//特定城市的推荐特色房源
    				List<SortClause> sorts = params.getSorts();
    				List<SortClause> sortsNew = new ArrayList<>();
    				SortClause cSortClause = new SortClause("featureHouseIndex", SolrQuery.ORDER.desc);
    				sortsNew.add(cSortClause);
    				if (!Check.NuNCollection(sorts)) {
    					sortsNew.addAll(sorts); 
    				}
    				params.setSorts(sortsNew);				
    			}
    			
    			try {
    				
    				String dayQ = DateSplitUtil.getParStrByStr(startTime + "", endTime + "");
    				params.set("defType","func");
    				
    				StringBuffer qString = new StringBuffer();
    				qString.append("sum(");
    				
    				qString.append("product(if(exists(top(query({!v='priorityDate:");
    				qString.append(dayQ.replaceAll("OR", "AND")).append("'}))),1000,0),if(exists(query({!v='flexiblePrice:*'})),1,0),if(exists(query({!v='-occupyDays:");
    				qString.append(dayQ).append("'})),1,0),if(exists(query({!v='houseEndTime:[");
    				qString.append(endDate.getTime()).append(" TO *]'})),1,0),if(exists(query({!v='");
    				qString.append(params.getQuery());
    				qString.append("'})),2,1))");
    				
    				qString.append(",").append("product(if(exists(top(query({!v='longTermLeaseDiscount:\"");
    				qString.append(ProductRulesEnum0019.ProductRulesEnum0019001.getValue()).append("\"'}))),1000,0),if(exists(query({!v='-occupyDays:");
    				qString.append(dayQ).append("'})),1,0),if(exists(query({!v='houseEndTime:[");
    				qString.append(endDate.getTime()).append(" TO *]'})),1,0),if(exists(query({!v='");
    				qString.append(params.getQuery()).append("'})),2,1))");
    				
    				qString.append(",").append("product(if(exists(top(query({!v='longTermLeaseDiscount:\"");
    				qString.append(ProductRulesEnum0019.ProductRulesEnum0019002.getValue()).append("\"'}))),1000,0),if(exists(query({!v='-occupyDays:");
    				qString.append(dayQ).append("'})),1,0),if(exists(query({!v='houseEndTime:[");
    				qString.append(endDate.getTime()).append(" TO *]'})),1,0),if(exists(query({!v='");
    				qString.append(params.getQuery()).append("'})),2,1))");
    				
    				qString.append(")");
    				
    				params.setQuery(qString.toString());
    			} catch (Exception e) { 
    				LogUtil.error(LOGGER, "recommend query func:{},e:{}", JsonEntityTransform.Object2Json(request), e);
    			}
    			
    			
    		}else{//客户决定在一个确定的时间旅游/出差，但不知道要去哪里
    			
    			try {
    				
    				params.set("defType","func");
    				StringBuffer qString = new StringBuffer();
    				qString.append("sum(0");
    				
    				if (!Check.NuNObj(startDate) && !Check.NuNObj(endDate)) {
    					String dayQ = DateSplitUtil.getParStrByStr(startTime + "", endTime + "");
    					qString.append(",").append("product(if(exists(top(query({!v='priorityDate:");
        				qString.append(dayQ.replaceAll("OR", "AND")).append("'}))),1000,0),if(exists(query({!v='flexiblePrice:*'})),1,0),if(exists(query({!v='-occupyDays:");
        				qString.append(dayQ).append("'})),1,0),if(exists(query({!v='houseEndTime:[");
        				qString.append(endDate.getTime()).append(" TO *]'})),1,0),if(exists(query({!v='");
        				qString.append(params.getQuery());
        				qString.append("'})),2,1))");
        				
        				qString.append(",").append("product(if(exists(top(query({!v='longTermLeaseDiscount:\"");
        				qString.append(ProductRulesEnum0019.ProductRulesEnum0019001.getValue()).append("\"'}))),1000,0),if(exists(query({!v='-occupyDays:");
        				qString.append(dayQ).append("'})),1,0),if(exists(query({!v='houseEndTime:[");
        				qString.append(endDate.getTime()).append(" TO *]'})),1,0),if(exists(query({!v='");
        				qString.append(params.getQuery()).append("'})),2,1))");
        				
        				qString.append(",").append("product(if(exists(top(query({!v='longTermLeaseDiscount:\"");
        				qString.append(ProductRulesEnum0019.ProductRulesEnum0019002.getValue()).append("\"'}))),1000,0),if(exists(query({!v='-occupyDays:");
        				qString.append(dayQ).append("'})),1,0),if(exists(query({!v='houseEndTime:[");
        				qString.append(endDate.getTime()).append(" TO *]'})),1,0),if(exists(query({!v='");
        				qString.append(params.getQuery()).append("'})),2,1))");
        				
					}
    				
    				if (!Check.NuNStr(request.getInCityName())) {
    					qString.append(",").append("product(if(exists(top(query({!v='currentReginMaxHot:[1 TO *]'}))),1000,0),if(exists(query({!v='cityName:\"");
        				qString.append(request.getInCityName()).append("*\"'})),200,0))");
        				
        				qString.append(",").append("product(if(exists(top(query({!v='currentCityHot:[1 TO *]'}))),1000,0),if(exists(query({!v='-cityName:\"");
        				qString.append(request.getInCityName()).append("*\"'})),1,0),if(exists(query({!v='isFeatureHouse:1'})),1,0))");
					} 				
    				
    				qString.append(")");
    				
    				params.setQuery(qString.toString());
    			} catch (Exception e) { 
    				LogUtil.error(LOGGER, "recommend query func:{},e:{}", JsonEntityTransform.Object2Json(request), e);
    			}
    			
    			
    		}
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "recommend query func:{},e:{}", JsonEntityTransform.Object2Json(request), e);
		}
    	
    	
    	
    }
    
    
}
