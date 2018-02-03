package com.ziroom.minsu.entity.photographer;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>摄影师相关照片实体</p>
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
public class PhotographerBaseMsgPicEntity extends BaseEntity{
    /**
	 * 序列id
	 */
	private static final long serialVersionUID = 4305607140631757667L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 关联编号
     */
    private String fid;

    /**
     * 摄影师uid
     */
    private String photographerUid;

    /**
     * 图片类型 0：证件正面照，1：证件反面照，2：手持证件照，3：用户头像
     */
    private Integer picType;

    /**
     * 图片名称
     */
    private String picName;

    /**
     * 图片访问基础url
     */
    private String picBaseUrl;

    /**
     * 图片后缀
     */
    private String picSuffix;

    /**
     * 图片服务器唯一id
     */
    private String picServerUuid;

    /**
     * 创建时间
     */
    private Date createDate;

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

    public String getPhotographerUid() {
        return photographerUid;
    }

    public void setPhotographerUid(String photographerUid) {
        this.photographerUid = photographerUid == null ? null : photographerUid.trim();
    }

    public Integer getPicType() {
        return picType;
    }

    public void setPicType(Integer picType) {
        this.picType = picType;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName == null ? null : picName.trim();
    }

    public String getPicBaseUrl() {
        return picBaseUrl;
    }

    public void setPicBaseUrl(String picBaseUrl) {
        this.picBaseUrl = picBaseUrl == null ? null : picBaseUrl.trim();
    }

    public String getPicSuffix() {
        return picSuffix;
    }

    public void setPicSuffix(String picSuffix) {
        this.picSuffix = picSuffix == null ? null : picSuffix.trim();
    }

    public String getPicServerUuid() {
        return picServerUuid;
    }

    public void setPicServerUuid(String picServerUuid) {
        this.picServerUuid = picServerUuid == null ? null : picServerUuid.trim();
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

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}