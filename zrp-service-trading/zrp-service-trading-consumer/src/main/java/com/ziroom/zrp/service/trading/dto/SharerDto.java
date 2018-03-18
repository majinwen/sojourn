package com.ziroom.zrp.service.trading.dto;

/**
 * <p>合租人</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月15日 14:48
 * @since 1.0
 */
public class SharerDto {
    /**
     * 合租人fid
     */
    private String fid;

    /**
     * 合同号
     */
    private String contractId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 证件类型
     */
    private String certType;
    /**
     * 证件号
     */
    private String certNo;
    /**
     * 电话号码
     */
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
}
