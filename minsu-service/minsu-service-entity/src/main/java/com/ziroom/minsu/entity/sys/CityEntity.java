package com.ziroom.minsu.entity.sys;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 *
 * <p>城市</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi
 * @since 1.0
 * @version 1.0
 */
public class CityEntity extends BaseEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -7144472568025171L;

    /* id */
    private Integer id;

    /** fid */
    private String fid;

    /** 城市编码 */
    private String cityCode;

    /**  城市名称 */
    private String cityName;

    /** isDel */
    private Integer isDel;

    /** 创建人 */
    private String createFid;

    /**  创建时间 */
    private Date createDate;

    /** 最后一次修改时间 */
    private Date lastModifyDate;


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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getCreateFid() {
        return createFid;
    }

    public void setCreateFid(String createFid) {
        this.createFid = createFid;
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
}
