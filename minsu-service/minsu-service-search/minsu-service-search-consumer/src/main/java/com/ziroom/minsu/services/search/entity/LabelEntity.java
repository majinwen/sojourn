package com.ziroom.minsu.services.search.entity;

import com.asura.framework.base.entity.BaseEntity;


/**
 * <p>标签信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/16.
 * @version 1.0
 * @since 1.0
 */
public  class LabelEntity extends BaseEntity{

    /** 标签code  */
    private String code;

    /** 标签名称  */
    private String name;

    /** 图标  */
    private String iconUrl;
    /**
     * 序号
     */
    private Integer index;
    
    public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
