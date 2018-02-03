package com.ziroom.minsu.services.solr.common.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.TermsResponse;
import org.apache.solr.client.solrj.response.TermsResponse.Term;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.utils.Pager;
import com.ziroom.minsu.services.common.utils.StringUtils;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.search.entity.QueryRequest;
import com.ziroom.minsu.services.search.entity.QueryResult;
import com.ziroom.minsu.services.solr.common.QueryService;
import com.ziroom.minsu.services.solr.constant.SolrConstant;
import com.ziroom.minsu.services.solr.exception.SearchException;
import com.ziroom.minsu.services.solr.index.SolrCore;
import com.ziroom.minsu.services.solr.query.parser.QueryResultParser;
import com.ziroom.minsu.services.solr.query.transfer.QueryParameterTransfer;
import com.ziroom.minsu.services.solr.server.SolrServerWapper;

/**
 * <p> 查询的实现</p>
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
@Service(value = "search.queryService")
public class QueryServiceImpl implements QueryService {

    private final static Logger logger = LoggerFactory.getLogger(QueryServiceImpl.class);

    /**
     * 
     * 获取指定所有文档的id
     *
     * @author zhangyl
     * @created 2017年8月8日 下午4:19:04
     *
     * @param solrCore
     * @return
     */
	public Set<String> getAllIds(SolrCore solrCore, Map<String, Object> filterQueries) {
		Set<String> all = new HashSet<>();

		QueryRequest request = new QueryRequest();
		request.setPageSize(SolrConstant.create_index_page_limit);

		request.setFilterQueries(filterQueries);

		QueryResult result = getIds(solrCore, request);
		List<String> frist = (List<String>) result.getValue();
		all.addAll(frist);
		Integer length = ValueUtil.getintValue(result.getTotal());
		int page = ValueUtil.getPage(length, SolrConstant.create_index_page_limit);
		for (int i = 1; i <= page; i++) {
			// 从第二页开始，循环处理之后的数据信息
			QueryResult resultEle = getIds(solrCore, request);
			List<String> ele = (List<String>) resultEle.getValue();
			all.addAll(ele);
			request.setPageIndex(request.getPageIndex() + 1);
		}
		return all;
	}
    
    /**
     * 获取当前的所有的索引信息
     * @author afi
     * @param indexType
     * @param request
     * @return
     */
    public QueryResult getIds(final SolrCore indexType, final QueryRequest request){
        SolrServerWapper solrServer = SolrServerWapper.getSolrServer(indexType);
        String keyword = request.getQ();

        if(Check.NuNObj(keyword) || keyword.length() == 0) {
            return null;
        }
        if (!keyword.equals("*:*")) {
            keyword = StringUtils.removeInvalidChar(keyword);
            if(Check.NuNObj(keyword) || keyword.length() == 0) {
                return null;
            }
            keyword = "\"" + keyword + "\"";
        }
        SolrQuery params = new SolrQuery(keyword);
        final Pager pager = Pager.rebuildPager(request.getPageIndex(), request.getPageSize());
        params.setStart(pager.getPageIndex());
        params.setRows(pager.getPageSize());
        
        addFilterQuery(params, request.getFilterQueries());
        
        QueryResponse response = solrServer.query(params);

        QueryResult queryResult = new QueryResult();
        SolrDocumentList docs = response.getResults();
        queryResult.setTotal(docs.getNumFound());
        List<String> list = new ArrayList<>();
        if(!Check.NuNObj(docs.getNumFound()) && docs.getNumFound() > 0) {
            for (SolrDocument doc : docs) {
                String id =ValueUtil.getStrValue(doc.getFieldValue("id"));
                list.add(id);
            }
        }
        queryResult.setValue(list);
        return queryResult;
    }
    
	/**
	 * 过滤查询
	 *
	 * @param params
	 * @param filterQueries
	 */
	private void addFilterQuery(SolrQuery params, Map<String, Object> filterQueries) {

		if (filterQueries != null) {
			for (Iterator<Map.Entry<String, Object>> iter = filterQueries.entrySet().iterator(); iter.hasNext();) {
				Map.Entry<String, Object> entry = iter.next();

				String key = entry.getKey();
				Object val = entry.getValue();
				if (Check.NuNObjs(key, val)) {
					continue;
				}
				params.addFilterQuery(key + ':' + val);
			}
		}
	}


    /**
     * 查询条件
     * @param indexType
     * @param request
     * @return
     */
    public QueryResult query(final SolrCore indexType, final QueryRequest request) {
       return query(indexType,null,request);
    }

    /**
     * 查询条件
     * @param indexType
     * @param par
     * @param request
     * @return
     */
    public QueryResult query(final SolrCore indexType,Map<String,Object> par, final QueryRequest request) {
        Long start = System.currentTimeMillis();
        SolrServerWapper solrServer = SolrServerWapper.getSolrServer(indexType);
        Long t1 = System.currentTimeMillis();
        SolrParams params = getSolrParams(indexType, request);
        if(params == null) {
            return new QueryResult(500 , "参数解析完后没有数据");
        }
        Long t2 = System.currentTimeMillis();
        QueryResponse response = solrServer.query(params);
        Long t3 = System.currentTimeMillis();
        QueryResult result = parseQueryResponse(indexType,par,response);
        Long end = System.currentTimeMillis();
        
        if (end-start > 70) {
        	LogUtil.info(logger, "[累计时间超过70ms] 获取server时间:{} 解析参数时间：{} 查询时间：{} 解析结果时间：{} 累计时间：{}",t1-start,t2-t1,t3-t2,end-t3,end-start);
		}
        
        return result;
    }

    /**
     * 构建搜索请求参数.
     * @param indexType
     * @param request
     * @return
     */
    private SolrParams getSolrParams(SolrCore indexType, QueryRequest request) {
        QueryParameterTransfer paramScanner = indexType.getTransfer();
        SolrParams params=null;
        try {
            params = paramScanner.transfer(request);
        } catch (Exception e) {
            LogUtil.error(logger,"转换参数异常：{}", JsonEntityTransform.Object2Json(request));
            throw new SearchException("build query params failture.", e);
        }

        return params;
    }

    /**
     * 解析搜索结果.
     * @param indexType
     * @param par
     * @param response
     * @return
     */
    private QueryResult parseQueryResponse(SolrCore indexType, Map<String,Object> par,QueryResponse response) {
        if (response == null || response.getStatus() != 0) {
            LogUtil.error(logger, "query response:{} ", response);
            throw new SearchException("query response is null or status != 0");
        }
        QueryResultParser queryParser = indexType.getParser();
        QueryResult result;
        try {
            result = queryParser.parse(par,response);
        } catch (Exception e) {
            LogUtil.error(logger, "parseQueryResponse Exception, e:{}", e);
            throw new SearchException("parse response failture.", e);
        }
        return result;
    }

	@Override
	public Set<String> autoCompleteWithFacet(final SolrCore indexType,String[] facetFields, String prefix, int limit){
		SolrServerWapper solrServer = SolrServerWapper.getSolrServer(indexType);  
        SolrQuery query = new SolrQuery();  
        query.setQuery("*:*");
        query.setFacet(true); 
        query.setFacetLimit(limit);  
        query.addFacetField(facetFields);
        query.setFacetPrefix(prefix);
        QueryResponse response = solrServer.query(query); 
        List<FacetField> facetFieldList = response.getFacetFields();
        Set<String> autoCompleteSet=new LinkedHashSet<>();
        for (FacetField facetField : facetFieldList) {
        	List<Count> values = facetField.getValues();
        	for (Count count : values) {
                autoCompleteSet.add(count.getName());
			}
		}
        return autoCompleteSet;
	}

	@Override
	public Set<String> autoCompleteWithTerms(final SolrCore indexType,String autoField, String prefix, int limit){
		SolrServerWapper solrServer = SolrServerWapper.getSolrServer(indexType);  
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set("qt", "/terms");
        solrQuery.setQuery("*:*");
        solrQuery.setTerms(true);
        solrQuery.addTermsField(autoField);
        solrQuery.setTermsPrefix(prefix);
        solrQuery.setTermsLimit(limit);
        QueryResponse response = solrServer.query(solrQuery);
        TermsResponse tRsp = response.getTermsResponse();
        List<Term> terms = tRsp.getTerms(autoField);
        Set<String> autoCompleteSet=new LinkedHashSet<>();
        for (Term term : terms) {
            autoCompleteSet.add(term.getTerm());
        }
        return autoCompleteSet;
	}


    /**
     * 获取小区名称的扩展词
     * @param prefix
     * @param limit
     * @return
     */
    public Set<String> autoCompleteCommunityNameWithTerms(String prefix, int limit){
        SolrServerWapper solrServer = SolrServerWapper.getSolrServer(SolrCore.m_spell);
        String autoField = "communityName";
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set("qt", "/terms");
        solrQuery.addFilterQuery("cityCode", "ss");
        solrQuery.setTerms(true);
        solrQuery.addTermsField(autoField);
        solrQuery.setTermsRegex(".*"+ prefix +".*");
        solrQuery.setTermsLimit(limit);
        QueryResponse response = solrServer.query(solrQuery);
        TermsResponse tRsp = response.getTermsResponse();
        List<Term> terms = tRsp.getTerms(autoField);
        Set<String> autoCompleteSet=new LinkedHashSet<>();
        for (Term term : terms) {
            autoCompleteSet.add(term.getTerm());
        }
        return autoCompleteSet;
    }
}