package com.ziroom.minsu.entity.cms;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>活动申请扩展表</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月29日
 * @since 1.0
 * @version 1.0
 */
public class ActivityApplyExtEntity extends BaseEntity {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -3732167836056428718L;

    /** 序列化ID  */
	private Integer id;

    /** fid  */
	private String fid;

    /** 申请表fid  */
	private String applyFid;

    /**
     * @see com.ziroom.minsu.valenum.cms.ApplyExtType
     * 扩展类型
     * */
	private Integer extType;

    /** 扩展内容  */
	private String content;

    /** 创建时间  */
	private Date createTime;

    /** 最后一次修改时间  */
	private Date lastModifyDate;

    /** 0：未删除 1 已经删除  */
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

	public String getApplyFid() {
		return applyFid;
	}

	public void setApplyFid(String applyFid) {
		this.applyFid = applyFid == null ? null : applyFid.trim();
	}

	public Integer getExtType() {
		return extType;
	}

	public void setExtType(Integer extType) {
		this.extType = extType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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