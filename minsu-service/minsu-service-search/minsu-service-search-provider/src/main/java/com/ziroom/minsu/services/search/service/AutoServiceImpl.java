package com.ziroom.minsu.services.search.service;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.search.entity.QueryRequest;
import com.ziroom.minsu.services.search.entity.QueryResult;
import com.ziroom.minsu.services.solr.common.IKAnalyzerService;
import com.ziroom.minsu.services.solr.common.QueryService;
import com.ziroom.minsu.services.solr.index.SolrCore;
import com.ziroom.minsu.services.solr.utils.SpellUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>自动扩展接口</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/27.
 * @version 1.0
 * @since 1.0
 */
@Service(value = "search.autoServiceImpl")
public class AutoServiceImpl {


    @Resource(name = "search.queryService")
    QueryService queryService;

    @Resource(name = "search.ikAnalyzerService")
    IKAnalyzerService ikAnalyzerService;


    /**
     * 获取用户补全信息
     * @param suggest
     * @return
     */
    public QueryResult getSuggestInfo(String suggest,String cityCode){
        QueryRequest request = new QueryRequest();
        request.setPageIndex(0);
        request.setPageSize(10);
        if(!Check.NuNStr(suggest)){
            request.setQ(SpellUtils.trans2Words(suggest,5,""));
        }
        Map<String, Object> filterQueries = new HashMap<String, Object>();
        filterQueries.put("cityCode", cityCode);
        request.setReturnFields("suggestName,areaName");
        QueryResult result = queryService.query(SolrCore.m_suggest, request);
        return result;
    }


    /**
     * 获取房东补全信息
     * @param suggest
     * @return
     */
    public QueryResult getCommunityName(String suggest,String cityCode){
        QueryRequest request = new QueryRequest();
        request.setPageIndex(0);
        request.setPageSize(1);
        if(!Check.NuNStr(suggest)){
            request.setQ(SpellUtils.trans2Words(suggest,5,""));
        }
        Map<String, Object> filterQueries = new HashMap<String, Object>();
        filterQueries.put("cityCode", cityCode);
        request.setReturnFields("id,communityName,areaName");
        QueryResult result = queryService.query(SolrCore.m_spell, request);
        return result;
    }

}
