package com.ziroom.zrp.service.trading.entity;

/**
 * <p>合同房间信息</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月13日 15:44
 * @since 1.0
 */
public class RentContractRoomVo {

    // 父合同id
    private String surParentRentId;

    // 合同id
    private String contractId;

    //合同号
    private String conRentCode;

    //项目id
    private String projectId;

    //房间id
    private String roomId;

    //房间号
    private String roomNumber;

    //房间面积
    private Double roomArea;

    //房间长租价格
    private Double longPrice;


    //是房间还是床位
    private int type;


    public RentContractRoomVo() {

    }

    public RentContractRoomVo(String surParentRentId, String contractId, String roomId, String roomNumber) {
        this.surParentRentId = surParentRentId;
        this.contractId = contractId;
        this.roomId = roomId;
        this.roomNumber = roomNumber;
    }

    public String getSurParentRentId() {
        return surParentRentId;
    }

    public void setSurParentRentId(String surParentRentId) {
        this.surParentRentId = surParentRentId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getConRentCode() {
        return conRentCode;
    }

    public void setConRentCode(String conRentCode) {
        this.conRentCode = conRentCode;
    }

    public Double getRoomArea() {
        return roomArea;
    }

    public void setRoomArea(Double roomArea) {
        this.roomArea = roomArea;
    }

    public Double getLongPrice() {
        return longPrice;
    }

    public void setLongPrice(Double longPrice) {
        this.longPrice = longPrice;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
