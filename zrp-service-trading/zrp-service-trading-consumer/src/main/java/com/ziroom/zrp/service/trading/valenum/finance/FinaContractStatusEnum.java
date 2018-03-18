package com.ziroom.zrp.service.trading.valenum.finance;

/**
 * <p>财务合同状态枚举</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年11月07日 10:51
 * @since 1.0
 */

public enum FinaContractStatusEnum {

    /**
     * 物业交割状态10为0和1组合状态，审核状态"210" 为组合状态
     */
    DZF_WJG_DSH("dzf",0,"0","dzf","待支付-未交割-待审核"),
    DZF_YJG_DSH("dzf",1,"0","dzf","待支付-已交割-待审核"),
    YQY_WJG_DSH("yqy",0,"0","yzf","已签约-未交割-待审核"),
    YQY_YJG_DSH("yqy",1,"0","dsh","已签约-已交割-待审核"),
    YQY_YJG_YTG("yqy",1,"2","ysh","已签约-已交割-已通过"),
    YQY_YJG_YHBH("yqy",1,"1","ybh","已签约-已交割-审核驳回"),

    YTZ("ytz",10,"210","ytz","已退租"),
    YDQ("ydq",10,"210","ydq","已到期"),
    JYZ("jyz",10,"210","ysh","解约中"),
    YZF("yzf",10,"210","ygb","已作废"),
    YGB("ygb",10,"210","ygb","已关闭"),
    XYZ("xyz",10,"210","ysh","续约中"),
    YXY("yxy",10,"210","ysh","续约中");

    /**
     * 本地合同状态
     */
    private String contractStatus;

    /**
     * 本地物业交割状态
     */
    private int deliveryStatusCode;

    /**
     * 本地合同审核状态
     */
    private String auditStatus;

    /**
     * 财务状态
     */
    private String finaStatus;

    /**
     * 描述
     */
    private String desc;

    FinaContractStatusEnum(String contractStatus, int deliveryStatusCode, String auditStatus, String finaStatus, String desc) {
        this.contractStatus = contractStatus;
        this.deliveryStatusCode = deliveryStatusCode;
        this.auditStatus = auditStatus;
        this.finaStatus = finaStatus;
        this.desc = desc;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public int getDeliveryStatusCode() {
        return deliveryStatusCode;
    }

    public void setDeliveryStatusCode(int deliveryStatusCode) {
        this.deliveryStatusCode = deliveryStatusCode;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getFinaStatus() {
        return finaStatus;
    }

    public void setFinaStatus(String finaStatus) {
        this.finaStatus = finaStatus;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
      * @description: 根据合同状态、物业交割状态、审核状态获取财务状态
      * @author: lusp
      * @date: 2017/11/7 上午 11:24
      * @params: contractStatus，deliveryStatusCode，auditStatus
      * @return: String
      */
    public static String getByStatusCodeAndAuditState(String contractStatus,int deliveryStatusCode,String auditStatus){
        if(contractStatus.equals("dzf")||contractStatus.equals("yqy")){
            for(final FinaContractStatusEnum finaContractStatusEnum : FinaContractStatusEnum.values()){
                if(finaContractStatusEnum.getContractStatus().equals(contractStatus) && finaContractStatusEnum.getDeliveryStatusCode()==deliveryStatusCode && finaContractStatusEnum.getAuditStatus().equals(auditStatus)){
                    return finaContractStatusEnum.getFinaStatus();
                }
            }
        }else{
            for(final FinaContractStatusEnum finaContractStatusEnum : FinaContractStatusEnum.values()){
                if(finaContractStatusEnum.getContractStatus().equals(contractStatus)){
                    return finaContractStatusEnum.getFinaStatus();
                }
            }
        }
        return null;
    }
}
