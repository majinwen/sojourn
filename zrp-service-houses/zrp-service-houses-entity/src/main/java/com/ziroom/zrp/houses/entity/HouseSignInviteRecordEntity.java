package com.ziroom.zrp.houses.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;
/**
 * <p>签约邀请记录</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年9月20日
 * @since 1.0
 */
public class HouseSignInviteRecordEntity extends BaseEntity{

    /**
     * 自增ID
     */
    private Long id;

    /**
     * 签约邀请id
     */
    private String signInviteId;

    /**
     * 分组id
     */
    private String groupId;

    /**
     * 客户uid
     */
    private String customerUid;

    /**
     * 客户手机号
     */
    private String customerPhone;

    /**
     * 签约类型,0:新签;1:续约;与合同表中的类据类型及标识一致辞
     */
    private String signType;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 房间id
     */
    private String roomId;

    /**
     * 合同租期开始时间
     */
    private Date conStartDate;

    /**
     * 邀请发送时间
     */
    private Date inviteDate;

    /**
     * 房间出租方式,日租;月租;年租;
     */
    private String conType;

    /**
     * 出租周期;与con_type配合使用
     */
    private Integer rentPeriod;

    /**
     * 生成的合同id
     */
    private String contractId;

    /**
     * 操作人id
     */
    private String zoId;

    /**
     * 操作人姓名
     */
    private String zoName;

    /**
     * 记录创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date lastModifyTime;

    /**
     * 是否删除;0:未删除;1删除
     */
    private Integer isDel;

    /**
     * 上一个合同id
     */
    private String preContractId;

    /**
     * 是否成交;0:未成交;1成交
     */
    private Integer isDeal;

    public HouseSignInviteRecordEntity() {

    }

    public HouseSignInviteRecordEntity(String signInviteId, String groupId, String customerUid, String customerPhone, String signType, String projectId, String roomId, Date conStartDate, Date inviteDate, String conType, Integer rentPeriod, String contractId, String zoId, String zoName, Date createTime, Integer isDel, String preContractId, Integer isDeal) {
        this.signInviteId = signInviteId;
        this.groupId = groupId;
        this.customerUid = customerUid;
        this.customerPhone = customerPhone;
        this.signType = signType;
        this.projectId = projectId;
        this.roomId = roomId;
        this.conStartDate = conStartDate;
        this.inviteDate = inviteDate;
        this.conType = conType;
        this.rentPeriod = rentPeriod;
        this.contractId = contractId;
        this.zoId = zoId;
        this.zoName = zoName;
        this.createTime = createTime;
        this.isDel = isDel;
        this.preContractId = preContractId;
        this.isDeal = isDeal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSignInviteId() {
        return signInviteId;
    }

    public void setSignInviteId(String signInviteId) {
        this.signInviteId = signInviteId == null ? null : signInviteId.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getCustomerUid() {
        return customerUid;
    }

    public void setCustomerUid(String customerUid) {
        this.customerUid = customerUid == null ? null : customerUid.trim();
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone == null ? null : customerPhone.trim();
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType == null ? null : signType.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId == null ? null : roomId.trim();
    }

    public Date getConStartDate() {
        return conStartDate;
    }

    public void setConStartDate(Date conStartDate) {
        this.conStartDate = conStartDate;
    }

    public Date getInviteDate() {
        return inviteDate;
    }

    public void setInviteDate(Date inviteDate) {
        this.inviteDate = inviteDate;
    }

    public String getConType() {
        return conType;
    }

    public void setConType(String conType) {
        this.conType = conType;
    }

    public Integer getRentPeriod() {
        return rentPeriod;
    }

    public void setRentPeriod(Integer rentPeriod) {
        this.rentPeriod = rentPeriod;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getZoId() {
        return zoId;
    }

    public void setZoId(String zoId) {
        this.zoId = zoId == null ? null : zoId.trim();
    }

    public String getZoName() {
        return zoName;
    }

    public void setZoName(String zoName) {
        this.zoName = zoName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getPreContractId() {
        return preContractId;
    }

    public void setPreContractId(String preContractId) {
        this.preContractId = preContractId == null ? null : preContractId.trim();
    }

    public Integer getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(Integer isDeal) {
        this.isDeal = isDeal;
    }
}