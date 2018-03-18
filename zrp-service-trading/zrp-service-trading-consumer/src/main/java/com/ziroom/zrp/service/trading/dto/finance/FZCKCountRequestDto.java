package com.ziroom.zrp.service.trading.dto.finance;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年11月10日 16时33分
 * @Version 1.0
 * @Since 1.0
 */
public class FZCKCountRequestDto {
    /**
     * hireServiceCode : 20262261
     * hireServiceName :
     * directorCode : 20031170
     * directorName : 刘恒
     * supervisorCode :
     * flowNode : 3
     * flowOpen :
     * overDateFlag : null
     * customerName :
     * customerPhone :
     * contractCode :
     */
    private String hireServiceCode;
    private String hireServiceName;
    private String directorCode;
    private String directorName;
    private String supervisorCode;
    private int flowNode;
    private String flowOpen;
    private Object overDateFlag;
    private String customerName;
    private String customerPhone;
    private String contractCode;

    public String getHireServiceCode() {
        return hireServiceCode;
    }

    public void setHireServiceCode(String hireServiceCode) {
        this.hireServiceCode = hireServiceCode;
    }

    public String getHireServiceName() {
        return hireServiceName;
    }

    public void setHireServiceName(String hireServiceName) {
        this.hireServiceName = hireServiceName;
    }

    public String getDirectorCode() {
        return directorCode;
    }

    public void setDirectorCode(String directorCode) {
        this.directorCode = directorCode;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getSupervisorCode() {
        return supervisorCode;
    }

    public void setSupervisorCode(String supervisorCode) {
        this.supervisorCode = supervisorCode;
    }

    public int getFlowNode() {
        return flowNode;
    }

    public void setFlowNode(int flowNode) {
        this.flowNode = flowNode;
    }

    public String getFlowOpen() {
        return flowOpen;
    }

    public void setFlowOpen(String flowOpen) {
        this.flowOpen = flowOpen;
    }

    public Object getOverDateFlag() {
        return overDateFlag;
    }

    public void setOverDateFlag(Object overDateFlag) {
        this.overDateFlag = overDateFlag;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }
}
