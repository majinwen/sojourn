package com.ziroom.zrp.trading.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
/**
 * <p>排班表从表--值班人</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年11月2日
 * @since 1.0
 */
public class SchedulePersonEntity extends BaseEntity{
	
	private static final long serialVersionUID = -7078040545367751774L;

	/**
     * 自增ID
     */
    private Integer id;

    /**
     * 业务id,以后 用来 获取关联资源
     */
    private String bid;

    /**
     * 排班信息id,即 排班信息表中的 业务id
     */
    private String scheduleid;

    /**
     * 关联人员id,employ表中的fid(根据这个字段，可以查到用户的员工号、与手机号，进而与CC库中数据交互)
     */
    private String employid;

    /**
     * 创建时间
     */
    private Date fcreatetime;

    /**
     * 更新时间，对排班进行修改或是删除的时候更新该字段
     */
    private Date fupdatetime;

    /**
     * 是否删除 0：否，1：是
     */
    private Integer fisdel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid == null ? null : bid.trim();
    }

    public String getScheduleid() {
        return scheduleid;
    }

    public void setScheduleid(String scheduleid) {
        this.scheduleid = scheduleid == null ? null : scheduleid.trim();
    }

    public String getEmployid() {
        return employid;
    }

    public void setEmployid(String employid) {
        this.employid = employid == null ? null : employid.trim();
    }

    public Date getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public Date getFupdatetime() {
        return fupdatetime;
    }

    public void setFupdatetime(Date fupdatetime) {
        this.fupdatetime = fupdatetime;
    }

    public Integer getFisdel() {
        return fisdel;
    }

    public void setFisdel(Integer fisdel) {
        this.fisdel = fisdel;
    }
}