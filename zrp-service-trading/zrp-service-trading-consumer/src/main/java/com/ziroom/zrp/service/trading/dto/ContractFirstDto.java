package com.ziroom.zrp.service.trading.dto;

import com.asura.framework.base.entity.BaseEntity;

import java.io.Serializable;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月16日 12:01
 * @since 1.0
 */
public class ContractFirstDto extends BaseEntity implements Serializable {

    // 客户类型
    private String customerType;

    //签约类型: 新签 续约
    private String signType;

    //前合同id(多个以",分隔"),preContractIds只有在续约时使用，传入preContractIds时，不再以roomIds为准
    private String preContractIds;

    //项目id --此字段废弃 从房间中选择一个查询
    @Deprecated
    private String projectId;

    //房间id(多个以",分隔")
    private String roomIds;

    //操作管家的id
    private String employeeId;
    // 客户标识
    private String customerUid;

    //客户手机号
    private String phone;

    // 操作管家姓名
    private String handleZO;
    // 操作人系统号
    private String handleZOCode;

    // 签合同来源:1：自如寓管理后台；2：为M站；3：APP在线签约
    private String source;
    // 用户姓名
    private String customerName;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getHandleZO() {
        return handleZO;
    }

    public void setHandleZO(String handleZO) {
        this.handleZO = handleZO;
    }

    public String getHandleZOCode() {
        return handleZOCode;
    }

    public void setHandleZOCode(String handleZOCode) {
        this.handleZOCode = handleZOCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ContractFirstDto() {

    }

    public String getCustomerUid() {
        return customerUid;
    }

    public void setCustomerUid(String customerUid) {
        this.customerUid = customerUid;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPreContractIds() {
        return preContractIds;
    }

    public void setPreContractIds(String preContractIds) {
        this.preContractIds = preContractIds;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(String roomIds) {
        this.roomIds = roomIds;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
