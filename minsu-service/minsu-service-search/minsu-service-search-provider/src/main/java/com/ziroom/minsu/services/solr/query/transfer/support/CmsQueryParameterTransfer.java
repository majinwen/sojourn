package com.ziroom.minsu.services.solr.query.transfer.support;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.search.entity.QueryRequest;
import com.ziroom.minsu.services.solr.query.transfer.AbstractParameterTransfer;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.SolrParams;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
public class CmsQueryParameterTransfer extends AbstractParameterTransfer {

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
        if (filterQueries != null) {
            for (Iterator<Map.Entry<String, Object>> iter = filterQueries
                    .entrySet().iterator(); iter.hasNext();) {
                Map.Entry<String, Object> entry = iter.next();

                String key = entry.getKey();
                Object val = entry.getValue();
                if (Check.NuNObjs(key, val)) {
                    continue;
                }

                if("notIds".equals(key)){
                    Set<String> set = (Set<String>) entry.getValue();
                    if (set!=null && set.size()>0) {
                        StringBuffer str = new StringBuffer();
                        for (String string : set) {
                            if (str.length()==0) {
                                str.append("(").append(string);
                            }else{
                                str.append(" OR ").append(string);
                            }
                        }
                        str.append(")");
                        params.addFilterQuery("-id:"+str.toString());
                    }
                    continue;
                }


                params.addFilterQuery(key + ':' + val);
            }
        }
    }




}
