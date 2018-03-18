package com.ziroom.zrp.service.houses.dto;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;
import java.util.Map;

/**
 * <p>签约邀请Dto</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年09月25日 10:42
 * @since 1.0
 */
public class SignInviteDto extends BaseEntity implements  Cloneable {

    //项目ID
    private String projectId;

    //项目名称
    private String projectName;

    //房间id
    private String roomIds;

    //签约类型：新签;续约;
    private String signContractType;

    //用户手机号
    private String phone;

    //出租方式:年租;月租;日租
    private String conType;

    //起租日期
    private Date conStartDate;

    //到期日期 在get 方法中自动计算
    private Date conEndDate;

    //出租周期
    private int rentPeriod;

    //客户uid
    private String customerUid;

    //处理zo名字
    private String handZo;

    //处理zo 员工号
    private String handZoCode;

    //处理zo employeeId
    private String employeeId;

    //生成的签约合同id
    private String contractId;

    //前一个合同id
    private String preContractId;

    //前一个合同号
    private String preConRentCode;

    //来源：app;ZRAMS
    private int source;

    //key:房间id, value:合同id;
    private Map<String, String> roomContractMap;

    public SignInviteDto() {

    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(String roomIds) {
        this.roomIds = roomIds;
    }

    public String getSignContractType() {
        return signContractType;
    }

    public void setSignContractType(String signContractType) {
        this.signContractType = signContractType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getConStartDate() {
        return conStartDate;
    }

    public void setConStartDate(Date conStartDate) {
        this.conStartDate = conStartDate;
    }

    public int getRentPeriod() {
        return rentPeriod;
    }

    public void setRentPeriod(int rentPeriod) {
        this.rentPeriod = rentPeriod;
    }

    public String getCustomerUid() {
        return customerUid;
    }

    public void setCustomerUid(String customerUid) {
        this.customerUid = customerUid;
    }

    public String getHandZo() {
        return handZo;
    }

    public void setHandZo(String handZo) {
        this.handZo = handZo;
    }

    public String getHandZoCode() {
        return handZoCode;
    }

    public void setHandZoCode(String handZoCode) {
        this.handZoCode = handZoCode;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public Map<String, String> getRoomContractMap() {
        return roomContractMap;
    }

    public void setRoomContractMap(Map<String, String> roomContractMap) {
        this.roomContractMap = roomContractMap;
    }

    public String getPreContractId() {
        return preContractId;
    }

    public void setPreContractId(String preContractId) {
        this.preContractId = preContractId;
    }

    public String getPreConRentCode() {
        return preConRentCode;
    }

    public void setPreConRentCode(String preConRentCode) {
        this.preConRentCode = preConRentCode;
    }

    public String getConType() {
        return conType;
    }

    public void setConType(String conType) {
        this.conType = conType;
    }

    public Date getConEndDate() {
        return conEndDate;
    }

    public void setConEndDate(Date conEndDate) {
        this.conEndDate = conEndDate;
    }
}
