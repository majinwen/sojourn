package com.ziroom.minsu.entity.order;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class OrderSmartLockEntity extends BaseEntity{
	
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = 4031287964210112554L;

	/**
	 * 主键
	 */
    private Integer id;

    /**
     * 逻辑id
     */
    private String fid;

    /**
     * 订单编号
     */
    private String orderSn;
    
    /**
     * 服务序号
     */
    private String serviceId;

    /**
     * 智能锁临时密码
     */
    private String tempPswd;
    
    /**
     * 智能锁临时密码id
     */
    private String passwordId;

    /**
     * 智能锁临时密码开始时间
     */
    private Date tempStartTime;
    
    /**
     * 智能锁临时密码失效时间
     */
    private Date tempExpiredTime;
    
    /**
     * 智能锁密码状态(0:下发中,1:下发失败,2:下发成功,3:关闭,4:异常，)
     */
    private Integer tempPswdStatus;

    /**
     * 动态密码
     */
    private String dynaPswd;

    /**
     * 动态密码获取次数
     */
    private Integer dynaNum;

    /**
     * 动态密码失效时间
     */
    private Date dynaExpiredTime;

    /**
     * 创建时间
     */
    private Date createDate;
    
    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除(0:否,1:是)
     */
    private Integer isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    public String getDynaPswd() {
        return dynaPswd;
    }

    public void setDynaPswd(String dynaPswd) {
        this.dynaPswd = dynaPswd == null ? null : dynaPswd.trim();
    }

    public Integer getDynaNum() {
        return dynaNum;
    }

    public void setDynaNum(Integer dynaNum) {
        this.dynaNum = dynaNum;
    }

    public Date getDynaExpiredTime() {
        return dynaExpiredTime;
    }

    public void setDynaExpiredTime(Date dynaExpiredTime) {
        this.dynaExpiredTime = dynaExpiredTime;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

	public String getPasswordId() {
		return passwordId;
	}

	public void setPasswordId(String passwordId) {
		this.passwordId = passwordId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getTempPswd() {
		return tempPswd;
	}

	public void setTempPswd(String tempPswd) {
		this.tempPswd = tempPswd;
	}

	public Date getTempStartTime() {
		return tempStartTime;
	}

	public void setTempStartTime(Date tempStartTime) {
		this.tempStartTime = tempStartTime;
	}

	public Date getTempExpiredTime() {
		return tempExpiredTime;
	}

	public void setTempExpiredTime(Date tempExpiredTime) {
		this.tempExpiredTime = tempExpiredTime;
	}

	public Integer getTempPswdStatus() {
		return tempPswdStatus;
	}

	public void setTempPswdStatus(Integer tempPswdStatus) {
		this.tempPswdStatus = tempPswdStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
