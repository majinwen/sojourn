package com.ziroom.zrp.houses.entity;

import java.util.Date;

public class BuildingInfoEntity {
    /**
     * 主键
     */
    private String fid;

    /**
     * 项目ID
     */
    private String projectid;

    /**
     * 楼栋名称
     */
    private String fbuildingname;

    /**
     * 朝向
     */
    private String fdirection;

    /**
     * 楼栋层数
     */
    private Integer ffloornumber;

    /**
     * 楼栋房间数
     */
    private Integer froomnumber;

    /**
     * 楼栋建筑面积
     */
    private Double fbuildingarea;

    /**
     * 楼栋外墙面积
     */
    private Double foutsidearea;

    /**
     * 电梯数
     */
    private Integer felevatornumber;

    /**
     * 公司ID
     */
    private String cityid;

    private Integer fvalid;

    private Date fcreatetime;

    private String createrid;

    private Date fupdatetime;

    private String updaterid;

    private Integer fisdel;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid == null ? null : projectid.trim();
    }

    public String getFbuildingname() {
        return fbuildingname;
    }

    public void setFbuildingname(String fbuildingname) {
        this.fbuildingname = fbuildingname == null ? null : fbuildingname.trim();
    }

    public String getFdirection() {
        return fdirection;
    }

    public void setFdirection(String fdirection) {
        this.fdirection = fdirection == null ? null : fdirection.trim();
    }

    public Integer getFfloornumber() {
        return ffloornumber;
    }

    public void setFfloornumber(Integer ffloornumber) {
        this.ffloornumber = ffloornumber;
    }

    public Integer getFroomnumber() {
        return froomnumber;
    }

    public void setFroomnumber(Integer froomnumber) {
        this.froomnumber = froomnumber;
    }

    public Double getFbuildingarea() {
        return fbuildingarea;
    }

    public void setFbuildingarea(Double fbuildingarea) {
        this.fbuildingarea = fbuildingarea;
    }

    public Double getFoutsidearea() {
        return foutsidearea;
    }

    public void setFoutsidearea(Double foutsidearea) {
        this.foutsidearea = foutsidearea;
    }

    public Integer getFelevatornumber() {
        return felevatornumber;
    }

    public void setFelevatornumber(Integer felevatornumber) {
        this.felevatornumber = felevatornumber;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
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
}