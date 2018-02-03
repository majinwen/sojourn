package com.ziroom.minsu.entity.message;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>图片类型消息扩展实体，主要用于鉴黄</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class HuanxinImRecordImgEntity extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -9221237406208936557L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 环信im记录主表fid
     */
    private String huanxinFid;

    /**
     * 自如网标识  ZIROOM_MINSU_IM= 代表民宿 ZIROOM_ZRY_IM= 自如驿  ZIROOM_CHANGZU_IM= 自如长租
     */
    private String ziroomFlag;

    /**
     * 图片地址 对应环信im记录表中的url
     */
    private String url;

    /**
     * 文件名字 如 test.jpg
     */
    private String filename;
    
    /**
     * 鉴别标志   0=未鉴别过  1=鉴别过
     */
    private Integer isCertified;

    /**
     * 合规标志   0=合规 1=不合规
     */
    private Integer isCompliance;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除  0=不删除  1=删除  默认0 
     */
    private Integer isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHuanxinFid() {
        return huanxinFid;
    }

    public void setHuanxinFid(String huanxinFid) {
        this.huanxinFid = huanxinFid == null ? null : huanxinFid.trim();
    }

    public String getZiroomFlag() {
        return ziroomFlag;
    }

    public void setZiroomFlag(String ziroomFlag) {
        this.ziroomFlag = ziroomFlag == null ? null : ziroomFlag.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getIsCertified() {
        return isCertified;
    }

    public void setIsCertified(Integer isCertified) {
        this.isCertified = isCertified;
    }

    public Integer getIsCompliance() {
        return isCompliance;
    }

    public void setIsCompliance(Integer isCompliance) {
        this.isCompliance = isCompliance;
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
    
}