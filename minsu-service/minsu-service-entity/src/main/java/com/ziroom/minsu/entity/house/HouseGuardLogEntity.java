package com.ziroom.minsu.entity.house;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>房源维护管家关系日志记录表</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class HouseGuardLogEntity extends BaseEntity{
	
	
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -4843918904072339955L;

	/**
	 * 主键
	 */
	private Integer id;

    /**
     * 逻辑id
     */
    private String fid;

    /**
     * 房源管家关系fid
     */
    private String guardRelFid;

    /**
     * 原维护管家系统号
     */
    private String oldGuardCode;

    /**
     * 原维护管家姓名
     */
    private String oldGuardName;
    
    /**
     * 原维护管家手机号
     */
    transient private String oldGuardMobile;
    
    /**
     * 原地推管家系统号
     */
    private String oldPushCode;
    
    /**
     * 原地推管家姓名
     */
    private String oldPushName;
    
    /**
     * 原地推管家手机号
     */
    transient private String oldPushMobile;

    /**
     * 创建人id
     */
    private String createrFid;

    /**
     * 创建人时间
     */
    private Date createDate;

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

    public String getGuardRelFid() {
        return guardRelFid;
    }

    public void setGuardRelFid(String guardRelFid) {
        this.guardRelFid = guardRelFid == null ? null : guardRelFid.trim();
    }

    public String getOldGuardCode() {
        return oldGuardCode;
    }

    public void setOldGuardCode(String oldGuardCode) {
        this.oldGuardCode = oldGuardCode == null ? null : oldGuardCode.trim();
    }

    public String getOldGuardName() {
        return oldGuardName;
    }

    public void setOldGuardName(String oldGuardName) {
        this.oldGuardName = oldGuardName == null ? null : oldGuardName.trim();
    }
    public String getCreaterFid() {
        return createrFid;
    }

    public void setCreaterFid(String createrFid) {
        this.createrFid = createrFid == null ? null : createrFid.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	public String getOldGuardMobile() {
		return oldGuardMobile;
	}

	public void setOldGuardMobile(String oldGuardMobile) {
		this.oldGuardMobile = oldGuardMobile;
	}

	public String getOldPushCode() {
		return oldPushCode;
	}

	public void setOldPushCode(String oldPushCode) {
		this.oldPushCode = oldPushCode;
	}

	public String getOldPushName() {
		return oldPushName;
	}

	public void setOldPushName(String oldPushName) {
		this.oldPushName = oldPushName;
	}

	public String getOldPushMobile() {
		return oldPushMobile;
	}

	public void setOldPushMobile(String oldPushMobile) {
		this.oldPushMobile = oldPushMobile;
	}
}