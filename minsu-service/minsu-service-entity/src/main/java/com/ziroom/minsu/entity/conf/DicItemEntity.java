package com.ziroom.minsu.entity.conf;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>配置字典值</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/19.
 * @version 1.0
 * @since 1.0
 */
public class DicItemEntity extends BaseEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -71442349025171L;

    private Integer id;




    /** 业务id */
    private String fid;

    /**
     * 模板fid
     */
    private String templateFid;

    /** 字典code */
    private String dicCode;

    /** 名称 */
    private String showName;

    /** 值 */
    private String itemValue;

    /** 状态  */
    private Integer itemStatus;
    
    /** 配置项排序位置  */
    private Integer itemIndex;

    /** 创建时间 */
    private Date createDate;

    /** 修改时间 */
    private Date lastModifyDate;

    /** 创建人  */
    private String createFid;

    /** 删除人 */
    private Integer isDel;

    /** 项类型  */
    private Integer dicType;

    public String getTemplateFid() {
        return templateFid;
    }

    public void setTemplateFid(String templateFid) {
        this.templateFid = templateFid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public Integer getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Integer itemStatus) {
        this.itemStatus = itemStatus;
    }
    
    public Integer getItemIndex() {
    	return itemIndex;
    }
    
    public void setItemIndex(Integer itemIndex) {
    	this.itemIndex = itemIndex;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public String getCreateFid() {
        return createFid;
    }

    public void setCreateFid(String createFid) {
        this.createFid = createFid;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

	public Integer getDicType() {
		return dicType;
	}

	public void setDicType(Integer dicType) {
		this.dicType = dicType;
	}
    
}
