package com.ziroom.zrp.service.houses.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.io.Serializable;
import java.sql.Date;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2017年12月04日 15:56
 * @since 1.0
 */
public class RoomContractSmartVo extends BaseEntity {


    private static final long serialVersionUID = -8825969914645098330L;
    /**
     *  合同id
     */
    private String contractId;

    /**
     * 房间id
     */
    private  String roomId;

    /**
     * 楼层号
     */
    private String floorNumber;
    /**
     * 楼栋
     */
    private String buildiNgname;

    /**
     * 用户uid
     */
    private  String personUid;

    /**
     * 入住人信息录入状态
     */
    private  Integer personDataStatus;


    /**
     * 入住人姓名
     */
    private String userName;

    /**
     * 合同号
     */
    private String conRentCode;

    /**
     * 合同生效日期：起租日期
     */
    private Date conStartDate;
    /**
     * 合同截止日期：到期日期
     */
    private Date conEndDate;

    /**
     * 承租合同签署日期：签约时间
     */
    private Date conSignDate;

    /**
     * 解约日期：退租日期
     */
    private Date conReleaseDate;

    /**
     * 入住人姓名
     */
    private String customerName;

    /**
     * 房间号
     */
    private String roomNumber;

    /**
     * 房间当前状态(0：待租中；1：已出租；2：配置中；3已下定；4：锁定；5：已下架；6：预定进行中；7：签约进行中；8：可预订；)
     */

    private String currentstate;

    /**
     * 户型名称
     */
    private String houseTypeName;

    /**
     * 项目名
     */
    private String projectName;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 合同状态：(wqy：未签约；dzf：待支付； yqy：已签约；jyz：解约中；ygb：已关闭；ytz：已退租； yzf：已作废；xyz：续约中；yxy：已续约；ydq：已到期；yqx：已取消（不用）；wrz：未入住（不用）；
     */
    private  String conStatusCode;

    public String getConStatusCode() {
        return conStatusCode;
    }

    public void setConStatusCode(String conStatusCode) {
        this.conStatusCode = conStatusCode;
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getBuildiNgname() {
        return buildiNgname;
    }

    public void setBuildiNgname(String buildiNgname) {
        this.buildiNgname = buildiNgname;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPersonUid() {
        return personUid;
    }

    public void setPersonUid(String personUid) {
        this.personUid = personUid;
    }

    public Integer getPersonDataStatus() {
        return personDataStatus;
    }

    public void setPersonDataStatus(Integer personDataStatus) {
        this.personDataStatus = personDataStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getConRentCode() {
        return conRentCode;
    }

    public void setConRentCode(String conRentCode) {
        this.conRentCode = conRentCode;
    }

    public Date getConStartDate() {
        return conStartDate;
    }

    public void setConStartDate(Date conStartDate) {
        this.conStartDate = conStartDate;
    }

    public Date getConEndDate() {
        return conEndDate;
    }

    public void setConEndDate(Date conEndDate) {
        this.conEndDate = conEndDate;
    }

    public Date getConSignDate() {
        return conSignDate;
    }

    public void setConSignDate(Date conSignDate) {
        this.conSignDate = conSignDate;
    }

    public Date getConReleaseDate() {
        return conReleaseDate;
    }

    public void setConReleaseDate(Date conReleaseDate) {
        this.conReleaseDate = conReleaseDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getCurrentstate() {
        return currentstate;
    }

    public void setCurrentstate(String currentstate) {
        this.currentstate = currentstate;
    }

    public String getHouseTypeName() {
        return houseTypeName;
    }

    public void setHouseTypeName(String houseTypeName) {
        this.houseTypeName = houseTypeName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
