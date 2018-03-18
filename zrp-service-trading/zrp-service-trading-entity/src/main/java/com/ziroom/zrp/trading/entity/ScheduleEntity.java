package com.ziroom.zrp.trading.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
/**
 * <p>排班记录表主表</p>
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
public class ScheduleEntity extends BaseEntity{
	
	private static final long serialVersionUID = -622889829774676219L;

	/**
     * 自增ID
     */
    private Integer id;

    /**
     * 业务id,以后 用来 关联排班人员的关系表中
     */
    private String bid;

    /**
     * 项目ID
     */
    private String projectid;

    /**
     * 1:周一;2周二;..7：周天
     */
    private String fweek;

    /**
     * 创建人
     */
    private String createid;

    /**
     * 修改人
     */
    private String updateid;

    /**
     * 创建时间
     */
    private Date fcreatetime;

    /**
     * 修改时间
     */
    private Date fupdatetime;

    /**
     * 是否删除 0：否，1：是
     */
    private Integer fisdel;

    /**
     * 班信息是否有效  0：否，1：是，注：只有当天的排班信息是有效的，CC库中哪个电话对应哪位管家
     */
    private String fstatus;

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

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid == null ? null : projectid.trim();
    }

    public String getFweek() {
        return fweek;
    }

    public void setFweek(String fweek) {
        this.fweek = fweek == null ? null : fweek.trim();
    }

    public String getCreateid() {
        return createid;
    }

    public void setCreateid(String createid) {
        this.createid = createid == null ? null : createid.trim();
    }

    public String getUpdateid() {
        return updateid;
    }

    public void setUpdateid(String updateid) {
        this.updateid = updateid == null ? null : updateid.trim();
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

    public String getFstatus() {
        return fstatus;
    }

    public void setFstatus(String fstatus) {
        this.fstatus = fstatus == null ? null : fstatus.trim();
    }
}