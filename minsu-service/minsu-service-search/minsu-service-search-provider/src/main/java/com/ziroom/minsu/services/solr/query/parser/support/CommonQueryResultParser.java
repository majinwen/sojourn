package com.ziroom.minsu.services.solr.query.parser.support;

import com.ziroom.minsu.services.solr.query.parser.AbstractQueryResultParser;
import org.apache.solr.common.SolrDocument;

import java.util.Map;

/**
 * <p>通用的搜索返回结果解析器</p>
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
public class CommonQueryResultParser extends AbstractQueryResultParser<SolrDocument>{

    /**
     * 不做任何的操作
     * @author afi
     * @param doc
     * @return
     */
    @Override
    protected SolrDocument doParser(Map<String,Object> par,SolrDocument doc) {
        return doc;
    }

}
