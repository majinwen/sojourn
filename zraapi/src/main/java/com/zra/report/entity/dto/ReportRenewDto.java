package com.zra.report.entity.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

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
public class ReportRenewDto extends BaseReportRequest {

	/**
	 * 业务id
	 */
	@JsonIgnore
    private String reportRenewId;

	/**
	 * 项目id
	 */
	@JsonIgnore
    private String projectId;

	/**
	 * 记录时间
	 */
	@JsonIgnore
    private String recordDate;

	/**
	 * 户型id
	 */
	@JsonIgnore
    private String houseTypeId;

	@ApiModelProperty("到期房源数---总量")
	@JsonProperty("expireRoomTotal")
    private Integer expireRoomTotal;

	@ApiModelProperty("到期房源数---长租")
	@JsonProperty("expireRoomLong")
    private Integer expireRoomLong;

	@ApiModelProperty("到期房源数---短租1-3个月")
	@JsonProperty("expireRoomShort1")
    private Integer expireRoomShort1;

	@ApiModelProperty("到期房源数---短租4-6个月")
	@JsonProperty("expireRoomShort2")
    private Integer expireRoomShort2;

	@ApiModelProperty("续约量---总数")
	@JsonProperty("renewTotal")
    private Integer renewTotal;

	@ApiModelProperty("续约量---长租")
	@JsonProperty("renewLong")
    private Integer renewLong;

	@ApiModelProperty("续约量---短租1-3个月")
	@JsonProperty("renewShort1")
    private Integer renewShort1;

	@ApiModelProperty("续约量---短租4-6个月")
	@JsonProperty("renewShort2")
    private Integer renewShort2;

	@ApiModelProperty("续约率---总数")
	@JsonProperty("renewRateTotal")
    private String renewRateTotal;

	@ApiModelProperty("续约率---长租")
	@JsonProperty("renewRateLong")
    private String renewRateLong;

	@ApiModelProperty("续约率---短租1-3个月")
	@JsonProperty("renewRateShort1")
    private String renewRateShort1;

	@ApiModelProperty("续约率---短租4-6个月")
	@JsonProperty("renewRateShort2")
    private String renewRateShort2;

    /**
     * 是否删除
     */
	@JsonIgnore
    private Integer isDel;

	/**
	 * 创建时间
	 */
	@JsonIgnore
    private String createTime;

	/**
	 * 删除时间
	 */
	@JsonIgnore
    private String deleteTime;

	/**
	 * 更新时间
	 */
	@JsonIgnore
    private String updateTime;

	/**
	 * 创建人
	 */
    @JsonIgnore
    private String createId;

	/**
	 * 更新人
	 */
    @JsonIgnore
    private String updateId;
	
	/**
	 * 删除人
	 */
    @JsonIgnore
    private String deleteId;

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

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getHouseTypeId() {
        return houseTypeId;
    }

    public void setHouseTypeId(String houseTypeId) {
        this.houseTypeId = houseTypeId;
    }

    public Integer getExpireRoomTotal() {
        return expireRoomTotal;
    }

    public void setExpireRoomTotal(Integer expireRoomTotal) {
        this.expireRoomTotal = expireRoomTotal;
    }

    public Integer getExpireRoomLong() {
        return expireRoomLong;
    }

    public void setExpireRoomLong(Integer expireRoomLong) {
        this.expireRoomLong = expireRoomLong;
    }

    public Integer getExpireRoomShort1() {
        return expireRoomShort1;
    }

    public void setExpireRoomShort1(Integer expireRoomShort1) {
        this.expireRoomShort1 = expireRoomShort1;
    }

    public Integer getExpireRoomShort2() {
        return expireRoomShort2;
    }

    public void setExpireRoomShort2(Integer expireRoomShort2) {
        this.expireRoomShort2 = expireRoomShort2;
    }

    public Integer getRenewTotal() {
        return renewTotal;
    }

    public void setRenewTotal(Integer renewTotal) {
        this.renewTotal = renewTotal;
    }

    public Integer getRenewLong() {
        return renewLong;
    }

    public void setRenewLong(Integer renewLong) {
        this.renewLong = renewLong;
    }

    public Integer getRenewShort1() {
        return renewShort1;
    }

    public void setRenewShort1(Integer renewShort1) {
        this.renewShort1 = renewShort1;
    }

    public Integer getRenewShort2() {
        return renewShort2;
    }

    public void setRenewShort2(Integer renewShort2) {
        this.renewShort2 = renewShort2;
    }

    public String getRenewRateTotal() {
        return renewRateTotal;
    }

    public void setRenewRateTotal(String renewRateTotal) {
        if (renewRateTotal != null) {
			this.renewRateTotal = renewRateTotal + "%";
		} else {
			this.renewRateTotal = renewRateTotal;
		}
    }

    public String getRenewRateLong() {
        return renewRateLong;
    }

    public void setRenewRateLong(String renewRateLong) {
        if (renewRateLong != null) {
			this.renewRateLong = renewRateLong + "%";
		} else {
			this.renewRateLong = renewRateLong;
		}
    }

    public String getRenewRateShort1() {
        return renewRateShort1;
    }

    public void setRenewRateShort1(String renewRateShort1) {
        if (renewRateShort1 != null) {
			this.renewRateShort1 = renewRateShort1 + "%";
		} else {
			this.renewRateShort1 = renewRateShort1;
		}
    }

    public String getRenewRateShort2() {
        return renewRateShort2;
    }

    public void setRenewRateShort2(String renewRateShort2) {
        if (renewRateShort2 != null) {
			this.renewRateShort2 = renewRateShort2 + "%";
		} else {
			this.renewRateShort2 = renewRateShort2;
		}
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
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
		return "ReportRenewDto [reportRenewId=" + reportRenewId + ", projectId=" + projectId + ", recordDate="
				+ recordDate + ", houseTypeId=" + houseTypeId + ", expireRoomTotal=" + expireRoomTotal + ", expireRoomLong=" + expireRoomLong + ", expireRoomShort1="
				+ expireRoomShort1 + ", expireRoomShort2=" + expireRoomShort2 + ", renewTotal=" + renewTotal
				+ ", renewLong=" + renewLong + ", renewShort1=" + renewShort1 + ", renewShort2=" + renewShort2
				+ ", renewRateTotal=" + renewRateTotal + ", renewRateLong=" + renewRateLong + ", renewRateShort1="
				+ renewRateShort1 + ", renewRateShort2=" + renewRateShort2 + ", isDel=" + isDel + ", createTime="
				+ createTime + ", deleteTime=" + deleteTime + ", updateTime=" + updateTime + ", createId=" + createId
				+ ", updateId=" + updateId + ", deleteId=" + deleteId + "]";
	}
}