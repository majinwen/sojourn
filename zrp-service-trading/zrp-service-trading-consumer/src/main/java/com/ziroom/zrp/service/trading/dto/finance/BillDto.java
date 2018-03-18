package com.ziroom.zrp.service.trading.dto.finance;

/*
 * <P></P>
 * <P>
 * <PRE>
 * <BR> 修改记录
 * <BR>------------------------------------------
 * <BR> 修改日期       修改人        修改内容
 * </PRE>
 * 
 * @Author lusp
 * @Date Create in 2017年09月 26日 17:48
 * @Version 1.0
 * @Since 1.0
 */

import java.io.Serializable;

public class BillDto implements Serializable{

    private static final long serialVersionUID = 2835893149497153247L;

    /**
     * 账单编号
     */
    private String billNum;

    /**
     * 单据类型 必填（1001:外部账单；1002: 内部账单(内部为员工和成本费用)；1004: 内部业务组赔付；1007：友家收款计划；1008：友家预定收款计划；1009：友家出房违约金）
     */
    private String documentType;

    /**
     * 客户uid 必填
     */
    private String uid;

    /**
     * 用户姓名 必填
     */
    private String username;

    /**
     * 预计收款日 yyyy-MM-dd 必填
     */
    private String preCollectionDate;

    /**
     * 账单周期开始时间 yyyy-MM-dd 必填
     */
    private String startTime;

    /**
     * 账单周期结束时间 yyyy-MM-dd 必填
     */
    private String endTime;

    /**
     * 房源编号(无传“00000“) 必填  房间或床位code
     */
    private String houseCode;

    /**
     * 房屋ID [项目ID（project_id）（或项目表中的fId）] 非必填
     */
    private String houseId;

    /**
     * 创建账单的管家账号  非必填
     */
    private String houseKeeperCode;

    /**
     * 费用项编号 必填
     */
    private String costCode;

    /**
     * 费用金额（单位分） 必填
     */
    private Integer documentAmount;

    /**
     * 期数 非必填
     */
    private Integer periods;

    /**
     * 非必填，修改应收账单时使用（0未删除，1逻辑删除 不填不对该字段状态进行修改）
     */
    private Integer isDel;

    public String getBillNum() {
        return billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPreCollectionDate() {
        return preCollectionDate;
    }

    public void setPreCollectionDate(String preCollectionDate) {
        this.preCollectionDate = preCollectionDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseKeeperCode() {
        return houseKeeperCode;
    }

    public void setHouseKeeperCode(String houseKeeperCode) {
        this.houseKeeperCode = houseKeeperCode;
    }

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public Integer getDocumentAmount() {
        return documentAmount;
    }

    public void setDocumentAmount(Integer documentAmount) {
        this.documentAmount = documentAmount;
    }

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
