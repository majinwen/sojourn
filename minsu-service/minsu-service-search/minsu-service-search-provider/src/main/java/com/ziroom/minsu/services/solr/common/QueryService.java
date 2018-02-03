package com.ziroom.minsu.services.solr.common;

import com.ziroom.minsu.services.search.entity.QueryRequest;
import com.ziroom.minsu.services.search.entity.QueryResult;
import com.ziroom.minsu.services.solr.index.SolrCore;

import java.util.Map;
import java.util.Set;

/**
 * <p>查询索引信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/16.
 * @version 1.0
 * @since 1.0
 */
public interface QueryService {

	Set<String> getAllIds(final SolrCore indexType, final Map<String, Object> filterQueries);

    QueryResult getIds(final SolrCore indexType, final QueryRequest request);

    QueryResult query(final SolrCore indexType, final QueryRequest request);
    QueryResult query(final SolrCore indexType, Map<String,Object> par,final QueryRequest request);
    Set<String> autoCompleteWithFacet(final SolrCore indexType,String[] facetFields,String prefix, int limit);
    Set<String> autoCompleteWithTerms(final SolrCore indexType,String autoField,String prefix, int limit);

    Set<String> autoCompleteCommunityNameWithTerms(String prefix, int limit);
}
