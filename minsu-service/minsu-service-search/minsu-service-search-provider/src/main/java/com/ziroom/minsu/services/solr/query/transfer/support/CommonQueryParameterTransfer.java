package com.ziroom.minsu.services.solr.query.transfer.support;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.search.entity.QueryRequest;
import com.ziroom.minsu.services.solr.query.transfer.AbstractParameterTransfer;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.SolrParams;

/**
 * <p>通用的参数转换</p>
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
public class CommonQueryParameterTransfer extends AbstractParameterTransfer {


    public SolrParams scanner(QueryRequest request) {

        String keyword = request.getQ();

        if (Check.NuNObj(keyword) || keyword.length() == 0) {
            return null;
        }

        SolrQuery params = new SolrQuery(keyword);

        reBuildPager(params, request.getPageIndex(), request.getPageSize());

        addFilterQuery(params, request.getFilterQueries());

        // 价格就走这个
        addRangeFilterQuery(params, request.getRangeFilterQueries());

        addSorts(params, request.getSorts());
        addFacet(params, request.getFacet() == 1);

        return params;
    }

}
