package com.ziroom.minsu.services.search.dto;

import com.ziroom.minsu.valenum.search.CmsSortTypeEnum;

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
 * @Date Created in 2017年08月01日 14:56
 * @since 1.0
 */
public class CmsArticleRequest extends BaseSearchRequest {


    private static final long serialVersionUID = -8315525192382772444L;

    /** 城市code */
    private String cityCode;

    /** 区域code */
    private String areaCode;

    /** 关键词 */
    private String q;

	/** 热门区域 */
	private String hotReginBusiness;
    /**
     * 文章板式
     */
    private String articleType;

    private Map<String, Object> sorts;

    private Integer sortType = CmsSortTypeEnum.DEFAULT.getCode();

    private String category;

    private Set<String> notIds;
    

    /**
	 * @return the hotReginBusiness
	 */
	public String getHotReginBusiness() {
		return hotReginBusiness;
	}

	/**
	 * @param hotReginBusiness the hotReginBusiness to set
	 */
	public void setHotReginBusiness(String hotReginBusiness) {
		this.hotReginBusiness = hotReginBusiness;
	}

	public Set<String> getNotIds() {
        return notIds;
    }

    public void setNotIds(Set<String> notIds) {
        this.notIds = notIds;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public Map<String, Object> getSorts() {
        return sorts;
    }

    public void setSorts(Map<String, Object> sorts) {
        this.sorts = sorts;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }
}
