package com.ziroom.zrp.service.houses.entity;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.zrp.service.houses.valenum.SmartLockPwdTypeEnum;
import com.ziroom.zrp.service.houses.valenum.SmartStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Data
public class IntellectSmartLockVo extends BaseEntity implements Serializable{

    private static final long serialVersionUID = -1694388407733258143L;

    /**
     * 主键自增
     */
    private Integer id;

    /**
     * 项目Id
     */
    private String projectId;

    /**
     * 业务主键
     */
    private String fid;

    /**
     * 合同标识
     */
    private String contractId;

    /**
     * 房间标识
     */
    private String roomId;
    private String roomNumber;
    private Integer floorNumber;

    /**
     * 建筑标示
     */
    private String buildingName;

    /**
     * 智能锁回调标识
     */
    private String serviceId;

    /**
     * 更新密码（旧密码）
     */
    private String updatePwd;

    /**
     * 当前密码
     */
    private String pwd;

    /**
     * 当前发送人手机号
     */
    private String phone;

    /**
     * 操作人系统号
     */
    private String handlerCode;

    /**
     * 操作人姓名
     */
    private String handlerName;

    /**
     * 回调状态	0 获取中，1 成功，2 失败
     */
    private Integer status;
    private String statusCn;

    /**
     * 生效时间
     */
    private Timestamp startTime;
    private String startTimeStr;

    /**
     * 失效时间
     */
    private Timestamp endTime;
    private String endTimeStr;

    /**
     * 回调时间
     */
    private Timestamp callbackTime;
    private String callbackTimeStr;

    /**
     * 创建时间
     */
    private Timestamp createTime;
    private String createTimeStr;

    /**
     * 更新时间
     */
    private Timestamp updateTime;
    private String updateTimeStr;

    /**
     * 是否删除(0:未删除；1:已删除)
     */
    private Integer isDel;

    /**
     * 使用人类型0=其他 1=管家 2=保洁 3=维修
     */
    private Integer userType;

    /**
     * 使用人姓名
     */
    private String userName;

    /**
     * 密码锁类型
     */
    private Integer pwdType;
    private String pwdTypeCn;

    public String getPwdTypeCn() {
        SmartLockPwdTypeEnum pwdTypeEnum = SmartLockPwdTypeEnum.valueOf(pwdType);
        if (pwdTypeEnum != null) {
            return pwdTypeEnum.getVal();
        }
        return "";
    }

    public String getStatusCn() {
        SmartStatusEnum smartStatusEnum = SmartStatusEnum.valueOf(status);
        if (smartStatusEnum != null) {
            return smartStatusEnum.getVal();
        }
        return "";
    }

    public String getCreateTimeStr() {
        return getTimeStr(createTime);
    }
    public String getCallbackTimeStr() {
        return getTimeStr(callbackTime);
    }
    public String getEndTimeStr() {
        return getTimeStr(endTime);
    }
    public String getStartTimeStr() {
        return getTimeStr(startTime);
    }

    public String getTimeStr(Timestamp time) {
        if (time == null){
            return "";
        }
        // todo mv /==>
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(time);
        // fix ==》 TimeZone.getDefault()
        //return time.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }
}