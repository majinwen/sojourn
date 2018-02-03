package com.ziroom.minsu.services.solr.query.transfer.support;

import java.util.Iterator;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.SolrParams;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.search.entity.QueryRequest;
import com.ziroom.minsu.services.solr.query.transfer.AbstractParameterTransfer;
import com.ziroom.minsu.valenum.search.SearchDataSourceTypeEnum;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zl
 * @version 1.0
 * @Date Created in 2017年07月27日 15:13
 * @since 1.0
 */
public class ZryHouseInfoQueryParameterTransfer extends AbstractParameterTransfer {

    /**
     * 参数转化
     * @param request
     * @return
     */
    public SolrParams transfer(QueryRequest request) {

        String keyword = request.getQ();

        if (Check.NuNObj(keyword) || keyword.length() == 0) {
            return null;
        }

        SolrQuery params = new SolrQuery(keyword);

        params.setFields("*,score");

        reBuildPager(params, request.getPageIndex(), request.getPageSize());
        addFilterQuery(params, request.getFilterQueries());
        setReturnFields(params, request.getReturnFields());
        addRangeFilterQuery(params, request.getRangeFilterQueries());
        addSorts(params, request.getSorts());
        addFacet(params, request.getFacet() == 1);

        return params;
    }


    /**
     * 过滤查询
     *
     * @param params
     * @param filterQueries
     */
    protected void addFilterQuery(SolrQuery params,
                                  Map<String, Object> filterQueries) {
    	
    	//区分solr文档里 民宿、自如驿
    	params.addFilterQuery("dataSource:" + SearchDataSourceTypeEnum.ziruyi.getCode());
    	
        if (filterQueries != null) {
            for (Iterator<Map.Entry<String, Object>> iter = filterQueries
                    .entrySet().iterator(); iter.hasNext();) {
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

}
