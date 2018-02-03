package com.ziroom.minsu.entity.base;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>状态配置 </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class StatusConfEntity extends BaseEntity{
    /**
	 * 序列id
	 */
	private static final long serialVersionUID = 8021224781791769979L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 关联编号
     */
    private String fid;

    /**
     * 状态键值
     */
    private String staKey;

    /**
     * 状态值
     */
    private String staVal;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建人uid
     */
    private String createUid;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除
     */
    private Integer isDel;

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
        this.fid = fid == null ? null : fid.trim();
    }

    public String getStaKey() {
        return staKey;
    }

    public void setStaKey(String staKey) {
        this.staKey = staKey == null ? null : staKey.trim();
    }

    public String getStaVal() {
        return staVal;
    }

    public void setStaVal(String staVal) {
        this.staVal = staVal == null ? null : staVal.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid == null ? null : createUid.trim();
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}