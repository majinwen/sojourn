package com.ziroom.zrp.houses.entity;

import com.asura.framework.base.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class IntellectSmartLockEntity extends BaseEntity{


    private static final long serialVersionUID = -5976069380966918785L;
    /**
     * 主键自增
     */
    private Integer id;

    /**
     * 业务主键
     */
    private String fid;

    /**
     * 合同标识
     */
    private String contractId;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 房间标识
     */
    private String roomId;

    /**
     * 0=其他 1=zo管家（内部员工） 2=第三方（保洁 没有系统号的使用人员）4=系统（用户）
     */
    private Integer userType;

    /**
     * 使用人系统号 （根据使用人类型确认 类型为系统为用户uid）
     */
    private String userCode;

    /**
     * 使用人姓名
     */
    private String userName;

    /**
     * 智能锁回调标识
     */
    private String serviceId;

    /**
     * 更新密码（旧密码）
     */
    private String updatePwd;

    /**
     * 密码类型 1=普通密码 2=动态密码
     */
    private  Integer pwdType;

    /**
     * 当前密码
     */
    private String pwd;

    /**
     * 当前发送人手机号
     */
    private String phone;

    /**
     * 操作人类型 0=内部员工 1=用户
     */
    private Integer handlerType;

    /**
     * 操作人系统号
     */
    private String handlerCode;

    /**
     * 操作人姓名
     */
    private String handlerName;

    /**
     * 请求智能平台的参数
     */
    private String paramStr;

    /**
     * 来源类型 0-ZO管家 1-新增智能平台出房合同 2-退租 3-续约 4-更换入住人
     */
    private Integer sourceType;

    /**
     * 回调状态	0 获取中，1 成功，2 失败 3 失效
     */
    private Integer status;

    /**
     * 生效时间
     */
    private Date startTime;

    /**
     * 失效时间
     */
    private Date endTime;

    /**
     * 回调时间
     */
    private Date callbackTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0:未删除；1:已删除)
     */
    private Integer isDel;

}