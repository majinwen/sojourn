package com.ziroom.minsu.services.search.entity;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.valenum.search.SearchSourceTypeEnum;

import java.util.Iterator;
import java.util.Map;

/**
 * <p>搜索的参数</p>
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
public class QueryRequest {

    /** 搜索关键字. */
    private String q = "*:*";

    /** 当前页码 */
    private int pageIndex = 1;

    /** 每页最大记录数 */
    private int pageSize = 10;

    /** 过滤查询属性 */
    private Map<String, Object> filterQueries;

    /** 范围查询属性 */
    private Map<String, Map<String, Object>> rangeFilterQueries;

    /** 排序字段 */
    private Map<String, Object> sorts;
    
	/**
	 * 排序类型
	 * @see com.ziroom.minsu.valenum.search.SortTypeEnum
	 */
	private Integer sortType;

    /** 需要返回的字段列表，多个字段用逗号隔开，如果返回所有的则不做控制*/
    private String returnFields;

    /**
     * 是否分组统计查询.
     * <p>默认为0, 代表不分组统计</p>
     * <p>1: 代表分组, 其他任意值代表不分组.</p>
     */
    private int facet;


    /**
     * 空间查询的条件
     */
    private  SpatialSolrParEntity solrParEntity;
    
    /**
     * 是否在目标城市
     */
    private Integer isTargetCityLocal;
    
    /**
     * 当前所在城市名称
     */
    private String inCityName;
    
    /**
     * 是否推荐
     */
    private Integer isRecommend;    
    
	/**
	 * 搜索入口
	 */
	private SearchSourceTypeEnum searchSourceTypeEnum;
	
	public SearchSourceTypeEnum getSearchSourceTypeEnum() {
		return searchSourceTypeEnum;
	}

	public void setSearchSourceTypeEnum(SearchSourceTypeEnum searchSourceTypeEnum) {
		this.searchSourceTypeEnum = searchSourceTypeEnum;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}
    
    public Integer getSortType() {
		return sortType;
	}

	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}

	public String getInCityName() {
		return inCityName;
	}

	public void setInCityName(String inCityName) {
		this.inCityName = inCityName;
	}

	public Integer getIsTargetCityLocal() {
		return isTargetCityLocal;
	}

	public void setIsTargetCityLocal(Integer isTargetCityLocal) {
		this.isTargetCityLocal = isTargetCityLocal;
	}

	public SpatialSolrParEntity getSolrParEntity() {
        return solrParEntity;
    }

    public void setSolrParEntity(SpatialSolrParEntity solrParEntity) {
        this.solrParEntity = solrParEntity;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        if (Check.NuNStr(q)) {
            return;
        }
        this.q = q;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Map<String, Object> getFilterQueries() {
        return filterQueries;
    }

    public void setFilterQueries(Map<String, Object> filterQueries) {
        this.filterQueries = filterQueries;
    }

    public Map<String, Map<String, Object>> getRangeFilterQueries() {
        return rangeFilterQueries;
    }

    public void setRangeFilterQueries(Map<String, Map<String, Object>> rangeFilterQueries) {
        this.rangeFilterQueries = rangeFilterQueries;
    }

    public Map<String, Object> getSorts() {
        return sorts;
    }

    public void setSorts(Map<String, Object> sorts) {
        this.sorts = sorts;
    }

    public int getFacet() {
        return facet;
    }

    public void setFacet(int facet) {
        this.facet = facet;
    }

    public String getReturnFields() {
        return returnFields;
    }

    public void setReturnFields(String returnFields) {
        this.returnFields = returnFields;
    }

    @Override
    public String toString() {
        StringBuilder tostr = new StringBuilder();
        tostr.append("q:").append(q);
        tostr.append(", pageIndex:").append(pageIndex);
        tostr.append(", pageSize:").append(pageSize);
        tostr.append(", returnFields:").append(returnFields);
        tostr.append(", facet:").append(facet);
        if (this.sorts != null) {
            tostr.append(", sorts:[");
            for (Iterator<Map.Entry<String, Object>> iter = this.sorts.entrySet().iterator(); iter.hasNext();) {
                final Map.Entry<String, Object> entry = iter.next();
                tostr.append(entry.getKey()).append('=').append(entry.getValue()).append(",");
            }
            if (tostr.charAt(tostr.length() - 1) == ',') {
                tostr.deleteCharAt(tostr.length() - 1);
            }
            tostr.append("]");
        }

        if (this.filterQueries != null) {
            tostr.append(", filterQueries: [");
            for (Iterator<Map.Entry<String, Object>> iter = this.filterQueries.entrySet().iterator(); iter.hasNext();) {
                final Map.Entry<String, Object> entry = iter.next();
                tostr.append(entry.getKey()).append('=').append(entry.getValue()).append(",");
            }
            if (tostr.charAt(tostr.length() - 1) == ',') {
                tostr.deleteCharAt(tostr.length() - 1);
            }
            tostr.append("]");
        }
        return tostr.toString();
    }

}
