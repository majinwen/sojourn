package com.ziroom.minsu.services.solr.common;

import com.ziroom.minsu.services.solr.index.SolrCore;

import java.util.List;
import java.util.Set;

/**
 * <p>创建索引</p>
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
public interface IndexService {

    void creatIndex(final SolrCore indexType, Object entity);

    void batchCreateIndex(final SolrCore indexType, List<Object> entityList);
    
    void create(final SolrCore indexType, Object entity);


    /**
     * 删除索引
     * @param solrcore
     * @param query
     */
    void deleteByQuery(SolrCore solrcore,String query);


    /**
     * 通过idList删除索引信息
     * @author afi
     * @param solrcore
     * @param ids
     */
    void deleteByIds(SolrCore solrcore,List<String> ids);


}
