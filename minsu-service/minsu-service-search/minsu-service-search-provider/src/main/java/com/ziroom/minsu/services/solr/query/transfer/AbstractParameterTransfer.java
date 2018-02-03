package com.ziroom.minsu.services.solr.query.transfer;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.common.utils.Pager;
import com.ziroom.minsu.services.common.utils.StringUtils;
import com.ziroom.minsu.services.search.entity.QueryRequest;
import com.ziroom.minsu.services.search.entity.SpatialSolrParEntity;
import com.ziroom.minsu.services.solr.constant.SolrConstant;
import com.ziroom.minsu.services.solr.query.par.Range;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.SolrParams;

import java.util.Iterator;
import java.util.Map;

/**
 * <p>抽象的参数转换器</p>
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
public abstract class AbstractParameterTransfer implements  QueryParameterTransfer{


    public SolrParams transfer(QueryRequest request) {
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
        addRequestHandler(params);
        reBuildPager(params, request.getPageIndex(), request.getPageSize());
        setReturnFields(params, request.getReturnFields());
        addFilterQuery(params, request.getFilterQueries());
        addRangeFilterQuery(params, request.getRangeFilterQueries());
        addSorts(params, request.getSorts());
        addFacet(params, request.getFacet() == 1);
        return params;
    }
    
    protected void addRequestHandler(SolrQuery params) {
    	
    }

    protected void setReturnFields(SolrQuery params, String returnFields) {
        if (returnFields != null && returnFields.trim().length() > 0) {
            String[] fields = returnFields.split(",");
            if (fields != null && fields.length > 0) {
                params.setFields(fields);
            }
        }
    }

    /**
     * 重建分页, 防止非法页码及每页显示数量.
     *
     * @param params
     * @param pageIndex
     * @param pageSize
     */
    protected void reBuildPager(SolrQuery params, int pageIndex, int pageSize) {
        final Pager pager = Pager.rebuildPager(pageIndex, pageSize);
        params.setStart(pager.getPageIndex());
        params.setRows(pager.getPageSize());
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
                params.addFilterQuery(key + ':' + val);
            }
        }
    }

    /**
     * 添加区间过滤.
     *
     * @param params
     * @param rangeFilterQueries
     */
    protected void addRangeFilterQuery(SolrQuery params,
                                       Map<String, Map<String, Object>> rangeFilterQueries) {
        if (rangeFilterQueries == null || rangeFilterQueries.isEmpty()) {
            return;
        }

        for (Iterator<Map.Entry<String, Map<String, Object>>> iter = rangeFilterQueries
                .entrySet().iterator(); iter.hasNext();) {
            Map.Entry<String, Map<String, Object>> entry = iter.next();
            String key = entry.getKey();
            Map<String, Object> value = entry.getValue();
            if (Check.NuNStr(key) || Check.NuNMap(value)) {
                continue;
            }

            Object start = value.get(Range.START);
            Object end = value.get(Range.END);
            boolean left = (boolean)value.get(Range.LEFT);
            boolean right = (boolean)value.get(Range.RIGHT);
            if (Check.NuNObjs(start, end)) {
                continue;
            }
            Range range = new Range(start, end, left, right);
            params.addFilterQuery(key + ":" + range.toString());
        }
    }

    /**
     * 添加排序.
     *
     * @param params
     * @param sorts
     */
    protected void addSorts(SolrQuery params, Map<String, Object> sorts) {
        if (!Check.NuNMap(sorts)) {
            for (Iterator<Map.Entry<String, Object>> iter = sorts.entrySet()
                    .iterator(); iter.hasNext();) {
                Map.Entry<String, Object> entry = iter.next();
                if (Check.NuNObj(entry.getKey())) {
                    continue;
                }
                Object value = entry.getValue();
                if (value == null) {
                    value = SolrQuery.ORDER.asc.name();
                }
                if (value.equals(SolrQuery.ORDER.asc.name())
                        || value.equals(SolrQuery.ORDER.desc.name())) {
                    params.addSort(entry.getKey(), SolrQuery.ORDER.valueOf(String
                            .valueOf(value.toString().toLowerCase())));
                }
            }
        }
    }


    /**
     * 添加空间的查询条件
     * @param params
     * @param solrParEntity
     */
    protected void addSpatialSolrPar(SolrQuery params, SpatialSolrParEntity solrParEntity) {
        if (!Check.NuNObj(solrParEntity)) {
           if(!Check.NuNStr(solrParEntity.getLocation()) && !Check.NuNStr(solrParEntity.getSfield())){
               params.set(SolrConstant.PT, solrParEntity.getLocation());
               params.set(SolrConstant.S_FIELD, solrParEntity.getSfield());
               if(!Check.NuNObj(solrParEntity.getOrderRole())){
                   params.set(SolrConstant.SORT, SolrConstant.GEODIST+solrParEntity.getOrderRole());
               }
               params.set(SolrConstant.SPATIAL, true);

           }
        }
    }




    /**
     * 分组聚合统计查询.
     *
     * @param params
     * @param facet
     */
    protected void addFacet(SolrQuery params, boolean facet) {
        if (!facet) {
            return;
        }
        String[] fields = getFacetField();
        if (Check.NuNObject(fields)) {
            return;
        }
        params.setFacet(true);
        params.setFacetMinCount(0);
        params.setFacetLimit(5);
        params.setFacetMissing(false);
        params.setFacetSort(SolrConstant.FACET_SORT);// index
        params.addFacetField(fields);
    }

    /**
     * 获取分组聚合查询字段. 由子类实现并提供不同索引的字段名称.
     *
     * @return
     */
    protected String[] getFacetField() {
        return null;
    }
}
