package com.ziroom.minsu.services.solr.query.parser;

import com.ziroom.minsu.services.search.entity.QueryResult;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.Map;

/**
 * <p>解析搜索返回结果</p>
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
public interface QueryResultParser {


    /**
     * 解析响应结果
     * @author afi
     * @param par
     * @param response
     * @return
     */
    QueryResult parse(Map<String,Object> par,QueryResponse response);
}
