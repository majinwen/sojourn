package com.ziroom.zrp.houses.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
/**
 * <p>ZO信息实体类</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月18日
 * @since 1.0
 */
public class EmployeeEntity extends BaseEntity{
	
	private static final long serialVersionUID = 7256787054800883766L;

	private String fid;

    private String fcode;

    private String fname;

    private Integer fsex;

    private String fidcardno;

    private Date fbirthday;

    private String femail;

    private String fmobile;

    private String ftelno;

    private String ffaxno;

    private String fqqno;

    private String fmsnno;

    private String faddress;

    private String fzipcode;

    private String fmemo;

    private Integer fcsrtype;

    private String fcicuserid;

    private String fcicpwd;

    private String departmentid;

    private Integer fvalid;

    private Date fcreatetime;

    private String createrid;

    private Date fupdatetime;

    private String updaterid;

    private Integer fisdel;

    /**
     * 关联城市表
     */
    private String cityid;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getFcode() {
        return fcode;
    }

    public void setFcode(String fcode) {
        this.fcode = fcode == null ? null : fcode.trim();
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname == null ? null : fname.trim();
    }

    public Integer getFsex() {
        return fsex;
    }

    public void setFsex(Integer fsex) {
        this.fsex = fsex;
    }

    public String getFidcardno() {
        return fidcardno;
    }

    public void setFidcardno(String fidcardno) {
        this.fidcardno = fidcardno == null ? null : fidcardno.trim();
    }

    public Date getFbirthday() {
        return fbirthday;
    }

    public void setFbirthday(Date fbirthday) {
        this.fbirthday = fbirthday;
    }

    public String getFemail() {
        return femail;
    }

    public void setFemail(String femail) {
        this.femail = femail == null ? null : femail.trim();
    }

    public String getFmobile() {
        return fmobile;
    }

    public void setFmobile(String fmobile) {
        this.fmobile = fmobile == null ? null : fmobile.trim();
    }

    public String getFtelno() {
        return ftelno;
    }

    public void setFtelno(String ftelno) {
        this.ftelno = ftelno == null ? null : ftelno.trim();
    }

    public String getFfaxno() {
        return ffaxno;
    }

    public void setFfaxno(String ffaxno) {
        this.ffaxno = ffaxno == null ? null : ffaxno.trim();
    }

    public String getFqqno() {
        return fqqno;
    }

    public void setFqqno(String fqqno) {
        this.fqqno = fqqno == null ? null : fqqno.trim();
    }

    public String getFmsnno() {
        return fmsnno;
    }

    public void setFmsnno(String fmsnno) {
        this.fmsnno = fmsnno == null ? null : fmsnno.trim();
    }

    public String getFaddress() {
        return faddress;
    }

    public void setFaddress(String faddress) {
        this.faddress = faddress == null ? null : faddress.trim();
    }

    public String getFzipcode() {
        return fzipcode;
    }

    public void setFzipcode(String fzipcode) {
        this.fzipcode = fzipcode == null ? null : fzipcode.trim();
    }

    public String getFmemo() {
        return fmemo;
    }

    public void setFmemo(String fmemo) {
        this.fmemo = fmemo == null ? null : fmemo.trim();
    }

    public Integer getFcsrtype() {
        return fcsrtype;
    }

    public void setFcsrtype(Integer fcsrtype) {
        this.fcsrtype = fcsrtype;
    }

    public String getFcicuserid() {
        return fcicuserid;
    }

    public void setFcicuserid(String fcicuserid) {
        this.fcicuserid = fcicuserid == null ? null : fcicuserid.trim();
    }

    public String getFcicpwd() {
        return fcicpwd;
    }

    public void setFcicpwd(String fcicpwd) {
        this.fcicpwd = fcicpwd == null ? null : fcicpwd.trim();
    }

    public String getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid == null ? null : departmentid.trim();
    }

    public Integer getFvalid() {
        return fvalid;
    }

    public void setFvalid(Integer fvalid) {
        this.fvalid = fvalid;
    }

    public Date getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public String getCreaterid() {
        return createrid;
    }

    public void setCreaterid(String createrid) {
        this.createrid = createrid == null ? null : createrid.trim();
    }

    public Date getFupdatetime() {
        return fupdatetime;
    }

    public void setFupdatetime(Date fupdatetime) {
        this.fupdatetime = fupdatetime;
    }

    public String getUpdaterid() {
        return updaterid;
    }

    public void setUpdaterid(String updaterid) {
        this.updaterid = updaterid == null ? null : updaterid.trim();
    }

    public Integer getFisdel() {
        return fisdel;
    }

    public void setFisdel(Integer fisdel) {
        this.fisdel = fisdel;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }
}