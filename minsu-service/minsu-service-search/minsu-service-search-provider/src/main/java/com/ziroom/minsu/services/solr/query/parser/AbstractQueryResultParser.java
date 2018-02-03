package com.ziroom.minsu.services.solr.query.parser;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.search.entity.QueryResult;
import com.ziroom.minsu.services.search.namevalue.LongNameValue;
import com.ziroom.minsu.services.search.namevalue.LongsNameValue;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>抽象的搜索结果解析器，只用来被继承</p>
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
public abstract class AbstractQueryResultParser<T> implements QueryResultParser{


    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractQueryResultParser.class);

    /**
     * 解析参数的返回结果
     * @author afi
     * @param par
     * @param response
     * @return
     */
    public QueryResult parse(Map<String,Object> par,QueryResponse response) {
        QueryResult queryResult = new QueryResult();
        long elapse = response.getElapsedTime();
        queryResult.setElapse(elapse);
        SolrDocumentList docs = response.getResults();
        queryResult.setTotal(docs.getNumFound());
        List<T> list = new ArrayList<>();
        if(!Check.NuNObj(docs.getNumFound()) && docs.getNumFound() > 0) {
            for (SolrDocument doc : docs) {
                T  t = doParser(par,doc);
                if(!Check.NuNObj(t)){
                    //当前对象不为空直接返回
                    list.add(t);
                }
            }
        }
        queryResult.setValue(list);
        return queryResult;
    }





    /**
     * 解析分组聚合.
     * @param response
     */
    protected LongsNameValue[] doParseFacets(QueryResponse response) {
        List<FacetField> fields = response.getFacetFields();
        if (fields == null || fields.isEmpty()) {
            return new LongsNameValue[0];
        }

        int index = 0;
        LongsNameValue[] values = new LongsNameValue[fields.size()];
        for (FacetField field : fields) {
            List<LongNameValue> facets = new ArrayList<LongNameValue>();
            for (FacetField.Count count : field.getValues()) {
                facets.add(new LongNameValue(count.getName(), count.getCount()));
            }
            values[index++] = new LongsNameValue(field.getName(), facets);
        }
        return values;
    }

    /**
     * 根据搜索响应体生成对应的实体对象.
     * @param doc
     * @return
     */
    abstract protected T doParser(Map<String,Object> par,SolrDocument doc);



}
