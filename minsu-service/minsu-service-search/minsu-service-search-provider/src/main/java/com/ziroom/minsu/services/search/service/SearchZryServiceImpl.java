package com.ziroom.minsu.services.search.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.solr.query.par.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.search.dto.HouseInfoRequest;
import com.ziroom.minsu.services.search.entity.QueryRequest;
import com.ziroom.minsu.services.search.entity.QueryResult;
import com.ziroom.minsu.services.solr.common.QueryService;
import com.ziroom.minsu.services.solr.index.SolrCore;

/**
 * <p>自如驿搜索</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zl
 * @version 1.0
 * @Date Created in 2017年07月28日 10:25
 * @since 1.0
 */
@Service(value = "search.searchZryServiceImpl")
public class SearchZryServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchZryServiceImpl.class);

    @Value("#{'${static_base_url}'.trim()}")
    private String staticBaseUrl;

    @Resource(name = "search.queryService")
    QueryService queryService;

    /**
     * 搜索房源
     * @author zl
     * @created 2017/7/28 14:06
     * @param
     * @return 
     */
	public QueryResult searchHouseList(HouseInfoRequest houseInfoRequest) {

		QueryRequest request = new QueryRequest();

		request.setPageIndex(houseInfoRequest.getPage());
		request.setPageSize(houseInfoRequest.getLimit());
		
		if (!Check.NuNStr(houseInfoRequest.getQ())) {
			// 拼音的转化
			String q = com.ziroom.minsu.services.common.utils.StringUtils.Parse(houseInfoRequest.getQ());
			request.setQ(q);
		}

		Map<String, Object> filterQueries = new HashMap<>();

		if(!Check.NuNStr(houseInfoRequest.getCityCode())){
        	filterQueries.put("cityCode", houseInfoRequest.getCityCode());
        }
        if(!Check.NuNStr(houseInfoRequest.getAreaCode())){
            filterQueries.put("areaCode", houseInfoRequest.getAreaCode());
        }
        
        request.setFilterQueries(filterQueries);

		// 设置区间的过滤条件
		Map<String, Map<String, Object>> rangeFilterQueries = new HashMap<>();

		if (!Check.NuNStr(houseInfoRequest.getStartTime())) {
			long start;
			try {
				Date startTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(houseInfoRequest.getStartTime()), "yyyy-MM-dd");
				start = startTime.getTime();
			} catch (Exception e) {
				start = System.currentTimeMillis();
			}
			// 筛选驿站时间在搜索开始时间之前
			rangeFilterQueries.put("passDate", Range.getRangeMap(null, start, true, true));
		}

		if (!Check.NuNStr(houseInfoRequest.getEndTime())) {
			long end;
			try {
				Date endTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(houseInfoRequest.getEndTime()), "yyyy-MM-dd");
				end = endTime.getTime();
			} catch (Exception e) {
				end = System.currentTimeMillis();
			}
			// 筛选驿站到期时间在搜索结束时间之后
			rangeFilterQueries.put("houseEndTime", Range.getRangeMap(end, null, true, true));
		}

		request.setRangeFilterQueries(rangeFilterQueries);

		// 按驿站的默认排序权重
        Map<String,Object> weights = new HashMap<>();
        weights.put("weights", "desc");
        request.setSorts(weights);

        // 结果解析所需条件
		Map<String, Object> par = new HashMap<>();
		par.put("picSize", houseInfoRequest.getPicSize());
		par.put("iconType", null);
		par.put("iconBaseUrl", staticBaseUrl);

		if (!Check.NuNStr(houseInfoRequest.getStartTime())) {
			par.put("startTime", houseInfoRequest.getStartTime());
		}
		if (!Check.NuNStr(houseInfoRequest.getEndTime())) {
			par.put("endTime", houseInfoRequest.getEndTime());
		}

		if (!Check.NuNObj(houseInfoRequest.getPersonCount())) {
			par.put("personCount", houseInfoRequest.getPersonCount());
		}

		LogUtil.info(LOGGER, "SearchZryServiceImpl request={}", request);
		QueryResult result = queryService.query(SolrCore.zry_house_info, par, request);
		return result;
	}

}
