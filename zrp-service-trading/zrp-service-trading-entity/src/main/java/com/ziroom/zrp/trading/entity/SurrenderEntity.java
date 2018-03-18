package com.ziroom.zrp.trading.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 *
 *
 * @Author: wangxm113
 * @Date: 2017年11月04日 17时52分29秒
 */
public class SurrenderEntity extends BaseEntity {
	private static final long serialVersionUID = -7559009479397395699L;

	/**
     * 主键
     */
    private String surrenderId;

    /**
     * 合同ID
     */
    private String contractId;

    /**
     * 物业交割单ID
     */
    private String surrendercostId;

    /**
     * 解约日期：实际退租日期
     */
    private Date freleasedate;

    /**
     * 创建ID
     */
    private String createrid;

    /**
     * 修改ID
     */
    private String updaterid;

    /**
     * 创建时间
     */
    private Date fcreatetime;

    /**
     * 修改时间
     */
    private Date fupdatetime;

    /**
     * 是否有效
     */
    private Integer fvalid;

    /**
     * 是否删除
     */
    private Integer fisdel;

    /**
     * 退租类型  [0 正常退租,1 非正常退租,2 客户单方面解约, 3 三天不满意退租,4 换租,5 转租,6 短租退租]
     */
    private String fsurtype;

    /**
     * 退租原因枚举 [0 同地工作变动,1 异地工作变动,2 个人生活原因（婚恋/结束婚恋/买房）,3 不满意房间硬件设施（家具/家电/配置物品）,4 不满意公寓公共设施（公共区域/健身房/电梯）,5 不满意自如寓服务（维修/保洁/ZO）,6 其他原因]
     */
    private String fsurreason;

    /**
     * 退租原因（存储其它原因信息）
     */
    private String fsurreasonother;

    /**
     * 租务工单截图路径
     */
    private String frentalorderspic;

    /**
     * 退租申请日期
     */
    private Date fapplicationdate;

    /**
     * (实际退租日期)-->此字段作废
     */
    private Date factualdate;

    /**
     * 预计退租日期
     */
    private Date fexpecteddate;

    /**
     * 解约协议编号
     */
    private String fsurrendercode;

    /**
     * 父解约协议编号
     */
    private String surParentCode;

    /**
     * 父解约协议id(防止编号生成出问题)
     */
    private String surParentId;

    /**
     * 解约房间id
     */
    private String roomId;

    /**
     * 新签合同编号(换租)
     */
    private String fnewrentcode;

    /**
     * 转租新签客户(用于转租)
     */
    private String fsubletname;

    /**
     * 转租客户身份证号
     */
    private String fsubletpersonid;

    /**
     * 解约方姓名
     */
    private String ftenantname;

    /**
     * 是否作废(0:已作废；1：未作废)
     */
    private String fiscancel;

    /**
     * 提交状态 [0 未提交,1 已提交]
     */
    private String fsubmitstatus;

    /**
     * 租务审核状态 [0 待审核,1 审核未通过,2 审核通过]
     */
    private String frentauditstatus;

    /**
     * 操作zo
     */
    private String fhandlezo;

    /**
     * 租务审核人
     */
    private String fauditor;

    /**
     * 审核日期
     */
    private Date frentauditdate;

    /**
     * 房屋交付日期
     */
    private Date fdeliverydate;

    /**
     * 已缴租金到期日
     */
    private Date frentenddate;

    /**
     * 解约申请状态 0 解约申请 1解约
     */
    private Integer fapplystatus;

    /**
     * 操作ZO名称
     */
    private String fhandlezoname;

    /**
     * 租务审核人name
     */
    private String fauditorname;

    /**
     * 关联城市表
     */
    private String cityid;

    /**
     * 系统来源
     */
    private String fsource;

    /**
     * 1出租方式是房间2出租方式是床位
     */
    private Integer rentType;

    /**
     * 租屋 首次审核日期
     */
    private Date zwFirstAuditDate;

    /**
     * 租屋审核通过日期
     */
    private Date zwApproveDate;

    /**
     * 财务首次审核日期
     */
    private Date cwFirstAuditDate;

    /**
     * 财务审核通过日期
     */
    private Date cwApproveDate;

    private String conRentCode;
    private String conStatuCode;

    public String getConRentCode() {
        return conRentCode;
    }

    public void setConRentCode(String conRentCode) {
        this.conRentCode = conRentCode;
    }

    public String getConStatuCode() {
        return conStatuCode;
    }

    public void setConStatuCode(String conStatuCode) {
        this.conStatuCode = conStatuCode;
    }

    public String getSurrenderId() {
        return surrenderId;
    }

    public void setSurrenderId(String surrenderId) {
        this.surrenderId = surrenderId == null ? null : surrenderId.trim();
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getSurrendercostId() {
        return surrendercostId;
    }

    public void setSurrendercostId(String surrendercostId) {
        this.surrendercostId = surrendercostId == null ? null : surrendercostId.trim();
    }

    public Date getFreleasedate() {
        return freleasedate;
    }

    public void setFreleasedate(Date freleasedate) {
        this.freleasedate = freleasedate;
    }

    public String getCreaterid() {
        return createrid;
    }

    public void setCreaterid(String createrid) {
        this.createrid = createrid == null ? null : createrid.trim();
    }

    public String getUpdaterid() {
        return updaterid;
    }

    public void setUpdaterid(String updaterid) {
        this.updaterid = updaterid == null ? null : updaterid.trim();
    }

    public Date getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public Date getFupdatetime() {
        return fupdatetime;
    }

    public void setFupdatetime(Date fupdatetime) {
        this.fupdatetime = fupdatetime;
    }

    public Integer getFvalid() {
        return fvalid;
    }

    public void setFvalid(Integer fvalid) {
        this.fvalid = fvalid;
    }

    public Integer getFisdel() {
        return fisdel;
    }

    public void setFisdel(Integer fisdel) {
        this.fisdel = fisdel;
    }

    public String getFsurtype() {
        return fsurtype;
    }

    public void setFsurtype(String fsurtype) {
        this.fsurtype = fsurtype == null ? null : fsurtype.trim();
    }

    public String getFsurreason() {
        return fsurreason;
    }

    public void setFsurreason(String fsurreason) {
        this.fsurreason = fsurreason == null ? null : fsurreason.trim();
    }

    public String getFsurreasonother() {
        return fsurreasonother;
    }

    public void setFsurreasonother(String fsurreasonother) {
        this.fsurreasonother = fsurreasonother == null ? null : fsurreasonother.trim();
    }

    public String getFrentalorderspic() {
        return frentalorderspic;
    }

    public void setFrentalorderspic(String frentalorderspic) {
        this.frentalorderspic = frentalorderspic == null ? null : frentalorderspic.trim();
    }

    public Date getFapplicationdate() {
        return fapplicationdate;
    }

    public void setFapplicationdate(Date fapplicationdate) {
        this.fapplicationdate = fapplicationdate;
    }

    public Date getFactualdate() {
        return factualdate;
    }

    public void setFactualdate(Date factualdate) {
        this.factualdate = factualdate;
    }

    public Date getFexpecteddate() {
        return fexpecteddate;
    }

    public void setFexpecteddate(Date fexpecteddate) {
        this.fexpecteddate = fexpecteddate;
    }

    public String getFsurrendercode() {
        return fsurrendercode;
    }

    public void setFsurrendercode(String fsurrendercode) {
        this.fsurrendercode = fsurrendercode == null ? null : fsurrendercode.trim();
    }

    public String getSurParentCode() {
        return surParentCode;
    }

    public void setSurParentCode(String surParentCode) {
        this.surParentCode = surParentCode == null ? null : surParentCode.trim();
    }

    public String getSurParentId() {
        return surParentId;
    }

    public void setSurParentId(String surParentId) {
        this.surParentId = surParentId == null ? null : surParentId.trim();
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId == null ? null : roomId.trim();
    }

    public String getFnewrentcode() {
        return fnewrentcode;
    }

    public void setFnewrentcode(String fnewrentcode) {
        this.fnewrentcode = fnewrentcode == null ? null : fnewrentcode.trim();
    }

    public String getFsubletname() {
        return fsubletname;
    }

    public void setFsubletname(String fsubletname) {
        this.fsubletname = fsubletname == null ? null : fsubletname.trim();
    }

    public String getFsubletpersonid() {
        return fsubletpersonid;
    }

    public void setFsubletpersonid(String fsubletpersonid) {
        this.fsubletpersonid = fsubletpersonid == null ? null : fsubletpersonid.trim();
    }

    public String getFtenantname() {
        return ftenantname;
    }

    public void setFtenantname(String ftenantname) {
        this.ftenantname = ftenantname == null ? null : ftenantname.trim();
    }

    public String getFiscancel() {
        return fiscancel;
    }

    public void setFiscancel(String fiscancel) {
        this.fiscancel = fiscancel == null ? null : fiscancel.trim();
    }

    public String getFsubmitstatus() {
        return fsubmitstatus;
    }

    public void setFsubmitstatus(String fsubmitstatus) {
        this.fsubmitstatus = fsubmitstatus == null ? null : fsubmitstatus.trim();
    }

    public String getFrentauditstatus() {
        return frentauditstatus;
    }

    public void setFrentauditstatus(String frentauditstatus) {
        this.frentauditstatus = frentauditstatus == null ? null : frentauditstatus.trim();
    }

    public String getFhandlezo() {
        return fhandlezo;
    }

    public void setFhandlezo(String fhandlezo) {
        this.fhandlezo = fhandlezo == null ? null : fhandlezo.trim();
    }

    public String getFauditor() {
        return fauditor;
    }

    public void setFauditor(String fauditor) {
        this.fauditor = fauditor == null ? null : fauditor.trim();
    }

    public Date getFrentauditdate() {
        return frentauditdate;
    }

    public void setFrentauditdate(Date frentauditdate) {
        this.frentauditdate = frentauditdate;
    }

    public Date getFdeliverydate() {
        return fdeliverydate;
    }

    public void setFdeliverydate(Date fdeliverydate) {
        this.fdeliverydate = fdeliverydate;
    }

    public Date getFrentenddate() {
        return frentenddate;
    }

    public void setFrentenddate(Date frentenddate) {
        this.frentenddate = frentenddate;
    }

    public Integer getFapplystatus() {
        return fapplystatus;
    }

    public void setFapplystatus(Integer fapplystatus) {
        this.fapplystatus = fapplystatus;
    }

    public String getFhandlezoname() {
        return fhandlezoname;
    }

    public void setFhandlezoname(String fhandlezoname) {
        this.fhandlezoname = fhandlezoname == null ? null : fhandlezoname.trim();
    }

    public String getFauditorname() {
        return fauditorname;
    }

    public void setFauditorname(String fauditorname) {
        this.fauditorname = fauditorname == null ? null : fauditorname.trim();
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }

    public String getFsource() {
        return fsource;
    }

    public void setFsource(String fsource) {
        this.fsource = fsource == null ? null : fsource.trim();
    }

    public Integer getRentType() {
        return rentType;
    }

    public void setRentType(Integer rentType) {
        this.rentType = rentType;
    }

    public Date getZwFirstAuditDate() {
        return zwFirstAuditDate;
    }

    public void setZwFirstAuditDate(Date zwFirstAuditDate) {
        this.zwFirstAuditDate = zwFirstAuditDate;
    }

    public Date getZwApproveDate() {
        return zwApproveDate;
    }

    public void setZwApproveDate(Date zwApproveDate) {
        this.zwApproveDate = zwApproveDate;
    }

    public Date getCwFirstAuditDate() {
        return cwFirstAuditDate;
    }

    public void setCwFirstAuditDate(Date cwFirstAuditDate) {
        this.cwFirstAuditDate = cwFirstAuditDate;
    }

    public Date getCwApproveDate() {
        return cwApproveDate;
    }

    public void setCwApproveDate(Date cwApproveDate) {
        this.cwApproveDate = cwApproveDate;
    }
}