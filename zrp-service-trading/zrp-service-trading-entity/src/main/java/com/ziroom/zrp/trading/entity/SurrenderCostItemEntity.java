package com.ziroom.zrp.trading.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 *
 * @Author: wangxm113
 * @Date: 2017年11月04日 17时52分24秒
 */
public class SurrenderCostItemEntity extends BaseEntity {
    /**
     * 主键
     */
    private String fid;

    /**
     * 费用项目id
     */
    private String expenseitemid;

    /**
     * 合同id
     */
    private String contractid;

    /**
     * 费用单id
     */
    private String surrendercostid;

    /**
     * 应缴金额
     */
    private BigDecimal foriginalnum;

    /**
     * 已交金额
     */
    private BigDecimal factualnum;

    /**
     * 应退金额
     */
    private BigDecimal frefundnum;

    /**
     * 是否删除
     */
    private Integer fisdel;

    /**
     * 是否有效
     */
    private Integer fvalid;

    /**
     * 城市id
     */
    private String fcity;

    /**
     * 创建时间
     */
    private Date fcreatetime;

    /**
     * 创建人
     */
    private String createrid;

    /**
     * 更新时间
     */
    private Date fupdatetime;

    /**
     * 更新人
     */
    private String updaterid;

    /**
     * 费用名称
     */
    private String expenseItemName;

    /**
     * 关联城市表
     */
    private String cityid;

    /**
     * 房间id
     */
    private String roomId;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getExpenseitemid() {
        return expenseitemid;
    }

    public void setExpenseitemid(String expenseitemid) {
        this.expenseitemid = expenseitemid == null ? null : expenseitemid.trim();
    }

    public String getContractid() {
        return contractid;
    }

    public void setContractid(String contractid) {
        this.contractid = contractid == null ? null : contractid.trim();
    }

    public String getSurrendercostid() {
        return surrendercostid;
    }

    public void setSurrendercostid(String surrendercostid) {
        this.surrendercostid = surrendercostid == null ? null : surrendercostid.trim();
    }

    public BigDecimal getForiginalnum() {
        return foriginalnum;
    }

    public void setForiginalnum(BigDecimal foriginalnum) {
        this.foriginalnum = foriginalnum;
    }

    public BigDecimal getFactualnum() {
        return factualnum;
    }

    public void setFactualnum(BigDecimal factualnum) {
        this.factualnum = factualnum;
    }

    public BigDecimal getFrefundnum() {
        return frefundnum;
    }

    public void setFrefundnum(BigDecimal frefundnum) {
        this.frefundnum = frefundnum;
    }

    public Integer getFisdel() {
        return fisdel;
    }

    public void setFisdel(Integer fisdel) {
        this.fisdel = fisdel;
    }

    public Integer getFvalid() {
        return fvalid;
    }

    public void setFvalid(Integer fvalid) {
        this.fvalid = fvalid;
    }

    public String getFcity() {
        return fcity;
    }

    public void setFcity(String fcity) {
        this.fcity = fcity == null ? null : fcity.trim();
    }

    public Date getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public String getCreaterid() {
        return createrid;
    }

    public void setCreaterid(String createrid) {
        this.createrid = createrid == null ? null : createrid.trim();
    }

    public Date getFupdatetime() {
        return fupdatetime;
    }

    public void setFupdatetime(Date fupdatetime) {
        this.fupdatetime = fupdatetime;
    }

    public String getUpdaterid() {
        return updaterid;
    }

    public void setUpdaterid(String updaterid) {
        this.updaterid = updaterid == null ? null : updaterid.trim();
    }

    public String getExpenseItemName() {
        return expenseItemName;
    }

    public void setExpenseItemName(String expenseItemName) {
        this.expenseItemName = expenseItemName == null ? null : expenseItemName.trim();
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId == null ? null : roomId.trim();
    }
}