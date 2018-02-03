package com.ziroom.minsu.services.cms.dto;


import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lun14 on 2017/02/09
 * @version 1.0
 * @since 1.0
 */
public class NpsCollectVo  extends BaseEntity {


    private static final long serialVersionUID = 3791806491289339292L;
    /**
     * 调查名称
     */
    private String npsName;

    /**
     * 调查码
     */
    private String npsCode;

    private String npsStatus;

    private Integer userType;

    /**
     * 调查内容
     */
    private String npsContent;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    private Date createTime;

    private Integer total;

    private Integer recommend;

    private Integer unrecommend;

    private Double npsRate;

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Double getNpsRate() {
        Double rate = 0.0;
        if(total>0){
            rate = recommend * 1.0/total - unrecommend * 1.0 /total;
        }
        return rate;
    }

    public void setNpsRate(Double npsRate) {
        this.npsRate = npsRate;
    }

    public String getNpsCode() {
        return npsCode;
    }

    public void setNpsCode(String npsCode) {
        this.npsCode = npsCode;
    }

    public String getNpsStatus() {
        return npsStatus;
    }

    public void setNpsStatus(String npsStatus) {
        this.npsStatus = npsStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Integer getUnrecommend() {
        return unrecommend;
    }

    public void setUnrecommend(Integer unrecommend) {
        this.unrecommend = unrecommend;
    }

    public String getNpsName() {
        return npsName;
    }

    public void setNpsName(String npsName) {
        this.npsName = npsName;
    }

    public String getNpsContent() {
        return npsContent;
    }

    public void setNpsContent(String npsContent) {
        this.npsContent = npsContent;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
