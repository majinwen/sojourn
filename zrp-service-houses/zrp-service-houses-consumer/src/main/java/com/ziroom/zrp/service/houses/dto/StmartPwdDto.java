package com.ziroom.zrp.service.houses.dto;

import java.io.Serializable;

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
 * @Date Created in 2017年12月06日 21:10
 * @since 1.0
 */
public class StmartPwdDto implements Serializable {


    private static final long serialVersionUID = -6146799227901892123L;

    /**
     * 使用人
     */
    private  String userName;

    /**
     * 手机号
     */
    private  String phone;

    /**
     * 有效时间
     */
    private  Integer effectiveTime;

    /**
     * 房间id  多个roomId 以“_”分割开
     */
    private String roomId;

    /**
     * 使用人类型 0=其他 1=zo管家（内部员工） 2=第三方（保洁 没有系统号的使用人员）4=系统（用户）
     */
    private Integer userType;

    /**
     * 下发密码
     */
    private String pwd;

    /**
     * 房间号集合
     */
    private String roomIds;

    /**
     * 操作人系统号
     */
    private String handlerCode;

    /**
     * 操作人姓名
     */
    private String handlerName;

    /**
     * 操作人类型 0=内部员工 1=用户
     */
    private  Integer handlerType;

    /**
     * 使用人系统号 （根据使用人类型确认 类型为系统为用户uid）
     */
    private  String userCode;


    /**
     * 是否发送短信 0=不发  1=发送
     */
    private  Integer isSendMsg = 0;

    public Integer getIsSendMsg() {
        return isSendMsg;
    }

    public void setIsSendMsg(Integer isSendMsg) {
        this.isSendMsg = isSendMsg;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Integer getHandlerType() {
        return handlerType;
    }

    public void setHandlerType(Integer handlerType) {
        this.handlerType = handlerType;
    }

    public String getHandlerCode() {
        return handlerCode;
    }

    public void setHandlerCode(String handlerCode) {
        this.handlerCode = handlerCode;
    }


    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public String getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(String roomIds) {
        this.roomIds = roomIds;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Integer effectiveTime) {
        this.effectiveTime = effectiveTime;
    }
}
