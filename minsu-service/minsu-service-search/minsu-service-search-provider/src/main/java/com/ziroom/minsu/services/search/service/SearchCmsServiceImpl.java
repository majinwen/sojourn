package com.ziroom.minsu.services.search.service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.dao.CustomerDbDao;
import com.ziroom.minsu.services.search.dto.CmsArticleDetailRequest;
import com.ziroom.minsu.services.search.dto.CmsArticleRequest;
import com.ziroom.minsu.services.search.dto.HouseCurrentStatsDto;
import com.ziroom.minsu.services.search.dto.HouseInfoRequest;
import com.ziroom.minsu.services.search.entity.*;
import com.ziroom.minsu.services.solr.common.QueryService;
import com.ziroom.minsu.services.solr.index.SolrCore;
import com.ziroom.minsu.valenum.search.CmsSortTypeEnum;
import com.ziroom.minsu.valenum.search.SortTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>cms文章查询</p>
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
@Service(value = "search.searchCmsServiceImpl")
public class SearchCmsServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchCmsServiceImpl.class);

    @Value("#{'${static_base_url}'.trim()}")
    private String staticBaseUrl;

    @Resource(name = "search.queryService")
    private QueryService queryService;

    @Autowired
    private RedisOperations redisOperations;


    /**
     * 搜索文章
     * @author zl
     * @created 2017/7/28 14:06
     * @param
     * @return 
     */
    public QueryResult searchArticleList(CmsArticleRequest paramsRequest) {

        QueryRequest request = new QueryRequest();
        request.setPageIndex(paramsRequest.getPage());
        request.setPageSize(paramsRequest.getLimit());
        request.setInCityName(paramsRequest.getInCityName());
        request.setIsTargetCityLocal(paramsRequest.getIsTargetCityLocal());
        if(!Check.NuNObj(paramsRequest.getIsRecommend())){
            request.setIsRecommend(paramsRequest.getIsRecommend());
        }
        if(!Check.NuNObj(paramsRequest.getSearchSourceTypeEnum())){
            request.setSearchSourceTypeEnum(paramsRequest.getSearchSourceTypeEnum());
        }
        if(!Check.NuNStr(paramsRequest.getQ())){
            String q = com.ziroom.minsu.services.common.utils.StringUtils.Parse(paramsRequest.getQ());
            request.setQ(q);
        }
        Map<String, Object> filterQueries = new HashMap<String, Object>();
        if(!Check.NuNStr(paramsRequest.getCityCode())){
            filterQueries.put("cityCode", paramsRequest.getCityCode());
        }
//        if(!Check.NuNStr(houseInfoRequest.getAreaCode())){
//            filterQueries.put("areaCode", houseInfoRequest.getAreaCode());
//        }

//        if(!Check.NuNStr(paramsRequest.getArticleType())){
//            filterQueries.put("articleType", paramsRequest.getArticleType());
//        }

        if(!Check.NuNStr(paramsRequest.getCategory())){
            filterQueries.put("category", paramsRequest.getCategory());
        }

        if(!Check.NuNCollection(paramsRequest.getNotIds())){
            filterQueries.put("notIds", paramsRequest.getNotIds());
        }

        if(!Check.NuNStr(paramsRequest.getHotReginBusiness())){
            filterQueries.put("businessAreas", paramsRequest.getHotReginBusiness());
        }
        request.setFilterQueries(filterQueries);

        if(Check.NuNObj(paramsRequest.getSorts())){
            CmsSortTypeEnum sortTypeEnum = CmsSortTypeEnum.getByCode(paramsRequest.getSortType());
            paramsRequest.setSorts(sortTypeEnum.getSortMap());
            request.setSortType(sortTypeEnum.getCode());
        }else{
            request.setSortType(paramsRequest.getSortType());
        }
        request.setSorts(paramsRequest.getSorts());

        Map<String,Object> par = new HashMap<>();
        par.put("picSize", paramsRequest.getPicSize());
        par.put("iconType", null);
        par.put("iconBaseUrl", staticBaseUrl);
        if (!Check.NuNObj(paramsRequest.getVersionCode())) {
            par.put("versionCode", paramsRequest.getVersionCode());
        }
        if (!Check.NuNObj(paramsRequest.getSearchSourceTypeEnum())) {
            par.put("searchSourceType", paramsRequest.getSearchSourceTypeEnum().getCode());
        }

         QueryResult result = queryService.query(SolrCore.cms_article_info,par,request);
        return result;
    }

    public CmsArticleDetailEntity getArticleDetail(CmsArticleDetailRequest paramsRequest ) {

        QueryRequest request = new QueryRequest();

        Map<String, Object> filterQueries = new HashMap<String, Object>();
        if(!Check.NuNStr(paramsRequest.getId())){
            filterQueries.put("id", paramsRequest.getId());
        }

        request.setFilterQueries(filterQueries);
        Map<String,Object> par = new HashMap<>();
        par.put("picSize", paramsRequest.getPicSize());
        par.put("iconType", null);
        par.put("iconBaseUrl", staticBaseUrl);
        if (!Check.NuNObj(paramsRequest.getVersionCode())) {
            par.put("versionCode", paramsRequest.getVersionCode());
        }
        if (!Check.NuNObj(paramsRequest.getSearchSourceTypeEnum())) {
            request.setSearchSourceTypeEnum(paramsRequest.getSearchSourceTypeEnum());
            par.put("searchSourceType", paramsRequest.getSearchSourceTypeEnum().getCode());
        }

        QueryResult result = queryService.query(SolrCore.cms_article_info_detail,par,request);
        if (!Check.NuNObj(result) && !Check.NuNObj(result.getValue())) {
            List<CmsArticleDetailEntity> list = (List<CmsArticleDetailEntity>) result.getValue();
            if (!Check.NuNCollection(list)) {
                return list.get(0);
            }
        }
        return null;
    }





}
