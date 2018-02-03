package com.ziroom.minsu.entity.photographer;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>摄影师基本信息扩展</p>
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
public class PhotographerBaseMsgExtEntity extends BaseEntity{
    /**
	 * 序列id
	 */
	private static final long serialVersionUID = 4326926403029077407L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 业务编号
     */
    private String fid;

    /**
     * 摄影师基本信息uid
     */
    private String photographerUid;

    /**
     * 证件类型(0=其他 1=身份证 2=护照 )
     */
    private Integer idType;

    /**
     * 证件号
     */
    private String idNo;

    /**
     * 摄影师个人介绍
     */
    private String photographyIntroduce;

    /**
     * 摄影师居住地址
     */
    private String resideAddr;

    /**
     * 工作类型 (专职/兼职 1=专职 2=兼职)
     */
    private Integer jobType;

    /**
     * 创建时间
     */
    private Date createDate;

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

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo == null ? null : idNo.trim();
    }

    public String getPhotographyIntroduce() {
        return photographyIntroduce;
    }

    public void setPhotographyIntroduce(String photographyIntroduce) {
        this.photographyIntroduce = photographyIntroduce == null ? null : photographyIntroduce.trim();
    }

    public String getResideAddr() {
        return resideAddr;
    }

    public void setResideAddr(String resideAddr) {
        this.resideAddr = resideAddr == null ? null : resideAddr.trim();
    }

    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}