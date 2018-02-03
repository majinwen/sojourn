package com.ziroom.minsu.entity.order;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>活动返现操作日志表实体</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年9月7日
 * @since 1.0
 * @version 1.0
 */
public class FinanceCashbackLogEntity extends BaseEntity {
    /**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 7425025441379552589L;

	/**
     * 主键id
     */
    private Integer id;

    /**
     * 逻辑id
     */
    private String fid;

    /**
     * 返现单号
     */
    private String cashbackSn;

    /**
     * 返现单状态 操作前 10：初始 20：已审核 30：已驳回 
     */
    private Integer fromStatus;

    /**
     * 返现单状态 操作后 10：初始 20：已审核 30：已驳回 
     */
    private Integer toStatus;

    /**
     * 创建人id
     */
    private String createId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 备注
     */
    private String remark;

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

    public String getCashbackSn() {
        return cashbackSn;
    }

    public void setCashbackSn(String cashbackSn) {
        this.cashbackSn = cashbackSn == null ? null : cashbackSn.trim();
    }

    public Integer getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(Integer fromStatus) {
        this.fromStatus = fromStatus;
    }

    public Integer getToStatus() {
        return toStatus;
    }

    public void setToStatus(Integer toStatus) {
        this.toStatus = toStatus;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}