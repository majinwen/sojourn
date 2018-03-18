package com.zra.report.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.zra.common.constant.HouseTypeConstant;

/**
 * <pre>
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑, 永无BUG!
 * 　　　　┃　　　┃Code is far away from bug with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * </pre>
 */
public class ReportRenewEntity {

	/**
	 * 主键id
	 */
    private Integer id;

    /**
	 * 业务id
	 */
    private String reportRenewId;

    /**
	 * 项目id
	 */
    private String projectId;

    /**
	 * 记录时间
	 */
    private Date recordDate;

    /**
	 * 户型id
	 */
    private String houseTypeId;

    /**
	 * 到期房源数---总量
	 */
    private Integer expireRoomTotal;

    /**
	 * 到期房源数---长租
	 */
    private Integer expireRoomLong;

    /**
	 * 到期房源数---短租1-3个月
	 */
    private Integer expireRoomShort1;

    /**
	 * 到期房源数---短租4-6个月
	 */
    private Integer expireRoomShort2;

    /**
	 * 续约量---总数
	 */
    private Integer renewTotal;

    /**
	 * 续约量---长租
	 */
    private Integer renewLong;

    /**
	 * 续约量---短租1-3个月
	 */
    private Integer renewShort1;

    /**
	 * 续约量---短租4-6个月
	 */
    private Integer renewShort2;

    /**
	 * 续约率---总数
	 */
    private BigDecimal renewRateTotal;

    /**
	 * 续约率---长租
	 */
    private BigDecimal renewRateLong;

    /**
	 * 续约率---短租1-3个月
	 */
    private BigDecimal renewRateShort1;

    /**
	 * 续约率---短租4-6个月
	 */
    private BigDecimal renewRateShort2;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 删除时间
     */
    private Date deleteTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建人id
     */
    private String createId;

    /**
     * 修改人id
     */
    private String updateId;

    /**
     * 删除人id
     */
    private String deleteId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReportRenewId() {
        return reportRenewId;
    }

    public void setReportRenewId(String reportRenewId) {
        this.reportRenewId = reportRenewId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getHouseTypeId() {
        return houseTypeId;
    }

    public void setHouseTypeId(String houseTypeId) {
    	if (houseTypeId == null) {
    		this.houseTypeId = HouseTypeConstant.NULL_TYPE;
    	} else if (houseTypeId.isEmpty()) {
    		this.houseTypeId = HouseTypeConstant.EMPTY_TYPE;
    	} else {
    		this.houseTypeId = houseTypeId;
    	}
    }

    public Integer getExpireRoomTotal() {
    	if (expireRoomTotal == null) {
    		return 0;
    	} else {
    		return expireRoomTotal;
    	}
    }

    public void setExpireRoomTotal(Integer expireRoomTotal) {
        this.expireRoomTotal = expireRoomTotal;
    }

    public Integer getExpireRoomLong() {
    	if (expireRoomLong == null) {
    		return 0;
    	} else {
    		return expireRoomLong;
    	}
    }

    public void setExpireRoomLong(Integer expireRoomLong) {
        this.expireRoomLong = expireRoomLong;
    }

    public Integer getExpireRoomShort1() {
    	if (expireRoomShort1 == null) {
    		return 0;
    	} else {
    		return expireRoomShort1;
    	}
    }

    public void setExpireRoomShort1(Integer expireRoomShort1) {
        this.expireRoomShort1 = expireRoomShort1;
    }

    public Integer getExpireRoomShort2() {
    	if (expireRoomShort2 == null) {
    		return 0;
    	} else {
    		return expireRoomShort2;
    	}
    }

    public void setExpireRoomShort2(Integer expireRoomShort2) {
        this.expireRoomShort2 = expireRoomShort2;
    }

    public Integer getRenewTotal() {
    	if (renewTotal == null) {
    		return 0;
    	} else {
    		return renewTotal;
    	}
    }

    public void setRenewTotal(Integer renewTotal) {
        this.renewTotal = renewTotal;
    }

    public Integer getRenewLong() {
    	if (renewLong == null) {
    		return 0;
    	} else {
    		return renewLong;
    	}
    }

    public void setRenewLong(Integer renewLong) {
        this.renewLong = renewLong;
    }

    public Integer getRenewShort1() {
    	if (renewShort1 == null) {
    		return 0;
    	} else {
    		return renewShort1;
    	}
    }

    public void setRenewShort1(Integer renewShort1) {
        this.renewShort1 = renewShort1;
    }

    public Integer getRenewShort2() {
    	if (renewShort2 == null) {
    		return 0;
    	} else {
    		return renewShort2;
    	}
    }

    public void setRenewShort2(Integer renewShort2) {
        this.renewShort2 = renewShort2;
    }

    public BigDecimal getRenewRateTotal() {
    	if (renewRateTotal == null) {
    		return new BigDecimal(0);
    	} else {
    		return renewRateTotal;
    	}
    }

    public void setRenewRateTotal(BigDecimal renewRateTotal) {
        this.renewRateTotal = renewRateTotal;
    }

    public BigDecimal getRenewRateLong() {
        if (renewRateLong == null) {
    		return new BigDecimal(0);
    	} else {
    		return renewRateLong;
    	}
    }

    public void setRenewRateLong(BigDecimal renewRateLong) {
        this.renewRateLong = renewRateLong;
    }

    public BigDecimal getRenewRateShort1() {
        if (renewRateShort1 == null) {
    		return new BigDecimal(0);
    	} else {
    		return renewRateShort1;
    	}
    }

    public void setRenewRateShort1(BigDecimal renewRateShort1) {
        this.renewRateShort1 = renewRateShort1;
    }

    public BigDecimal getRenewRateShort2() {
        if (renewRateShort2 == null) {
    		return new BigDecimal(0);
    	} else {
    		return renewRateShort2;
    	}
    }

    public void setRenewRateShort2(BigDecimal renewRateShort2) {
        this.renewRateShort2 = renewRateShort2;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getDeleteId() {
        return deleteId;
    }

    public void setDeleteId(String deleteId) {
        this.deleteId = deleteId;
    }

	@Override
	public String toString() {
		return "ReportRenewEntity [id=" + id + ", reportRenewId=" + reportRenewId + ", projectId=" + projectId
				+ ", recordDate=" + recordDate + ", houseTypeId=" + houseTypeId + ", expireRoomTotal=" + expireRoomTotal
				+ ", expireRoomLong=" + expireRoomLong + ", expireRoomShort1=" + expireRoomShort1
				+ ", expireRoomShort2=" + expireRoomShort2 + ", renewTotal=" + renewTotal + ", renewLong=" + renewLong
				+ ", renewShort1=" + renewShort1 + ", renewShort2=" + renewShort2 + ", renewRateTotal=" + renewRateTotal
				+ ", renewRateLong=" + renewRateLong + ", renewRateShort1=" + renewRateShort1 + ", renewRateShort2="
				+ renewRateShort2 + ", isDel=" + isDel + ", createTime=" + createTime + ", deleteTime=" + deleteTime
				+ ", updateTime=" + updateTime + ", createId=" + createId + ", updateId=" + updateId + ", deleteId="
				+ deleteId + "]";
	}
}