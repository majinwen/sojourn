package com.ziroom.minsu.services.solr.query.transfer;

import com.ziroom.minsu.services.search.entity.QueryRequest;
import org.apache.solr.common.params.SolrParams;

/**
 * <p>查询条件转化器</p>
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
public interface QueryParameterTransfer {


    /**
     *
     * 过滤参数, 并根据合法参数构建查询参数.
     *
     * @return
     */
    SolrParams transfer(QueryRequest request);



}
