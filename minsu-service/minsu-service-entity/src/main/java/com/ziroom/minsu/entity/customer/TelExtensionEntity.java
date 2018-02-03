package com.ziroom.minsu.entity.customer;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>房东400电话绑定实体</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/6.
 * @version 1.0
 * @since 1.0
 */
public class TelExtensionEntity extends BaseEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 255631656456565665L;

    /** 自增id */
    private Integer id;

    /**逻辑id */
    private String fid;

    /** 房东uid */
    private String uid;

    /** 绑定状态
     * @see com.ziroom.minsu.valenum.customer.ExtStatusEnum
     * */
    private Integer extStatus;

    /** 分机号 */
    private String ziroomPhone;

    /** 创建人 */
    private String createUid;

    /** 创建时间 */
    private Date createDate;

    /** 最后更新时间 */
    private Date lastModifyDate;

    /** 是否删除 0：否，1：是 */
    transient private Integer isDel;


    /** 错误码 */
    private Integer errorCode;


    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

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
        this.fid = fid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getZiroomPhone() {
        return ziroomPhone;
    }

    public void setZiroomPhone(String ziroomPhone) {
        this.ziroomPhone = ziroomPhone;
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public Integer getExtStatus() {
        return extStatus;
    }

    public void setExtStatus(Integer extStatus) {
        this.extStatus = extStatus;
    }
}
