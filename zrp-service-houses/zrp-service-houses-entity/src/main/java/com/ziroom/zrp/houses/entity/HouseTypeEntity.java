package com.ziroom.zrp.houses.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
/**
 * <p>房型信息实体类</p>
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
public class HouseTypeEntity extends BaseEntity{
	
	private static final long serialVersionUID = 1614681106238579513L;

	public HouseTypeEntity(){
		
	}
	/**
     * 主键
     */
    private String fid;

    /**
     * 户型名称
     */
    private String fhousetypename;

    /**
     * 面积
     */
    private Double farea;

    /**
     * 总间数
     */
    private Integer ftotalroom;

    /**
     * 0:开间 1:套间
     */
    private String froomtype;

    /**
     * 户型说明
     */
    private String fhousetypedesc;

    /**
     * 户型图
     */
    private String fhousetypeimg;

    /**
     * 户型对应的二维码图片
     */
    private String fqrcodeimg;

    /**
     * 显示顺序
     */
    private Integer fshoworder;

    /**
     * 户型全景Id(腾讯提供)
     */
    private String fpanoid;

    /**
     * 房型介绍
     */
    private String froomintroduction;

    /**
     * 项目id
     */
    private String projectid;

    /**
     * 城市ID
     */
    private String cityid;

    /**
     * 1是网站显示,0禁止网站显示
     */
    private Integer fvalid;

    private Date fcreatetime;

    private String createrid;

    private Date fupdatetime;

    private String updaterid;

    private Integer fisdel;

    /**
     * 全景看房
     */
    private String fPanoramicUrl;

    /**
     * 分享链接
     */
    private String fShareUrl;

    /**
     * 户型头图
     */
    private String fHeadFigureUrl;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getFhousetypename() {
        return fhousetypename;
    }

    public void setFhousetypename(String fhousetypename) {
        this.fhousetypename = fhousetypename == null ? null : fhousetypename.trim();
    }

    public Double getFarea() {
        return farea;
    }

    public void setFarea(Double farea) {
        this.farea = farea;
    }

    public Integer getFtotalroom() {
        return ftotalroom;
    }

    public void setFtotalroom(Integer ftotalroom) {
        this.ftotalroom = ftotalroom;
    }

    public String getFroomtype() {
        return froomtype;
    }

    public void setFroomtype(String froomtype) {
        this.froomtype = froomtype == null ? null : froomtype.trim();
    }

    public String getFhousetypedesc() {
        return fhousetypedesc;
    }

    public void setFhousetypedesc(String fhousetypedesc) {
        this.fhousetypedesc = fhousetypedesc == null ? null : fhousetypedesc.trim();
    }

    public String getFhousetypeimg() {
        return fhousetypeimg;
    }

    public void setFhousetypeimg(String fhousetypeimg) {
        this.fhousetypeimg = fhousetypeimg == null ? null : fhousetypeimg.trim();
    }

    public String getFqrcodeimg() {
        return fqrcodeimg;
    }

    public void setFqrcodeimg(String fqrcodeimg) {
        this.fqrcodeimg = fqrcodeimg == null ? null : fqrcodeimg.trim();
    }

    public Integer getFshoworder() {
        return fshoworder;
    }

    public void setFshoworder(Integer fshoworder) {
        this.fshoworder = fshoworder;
    }

    public String getFpanoid() {
        return fpanoid;
    }

    public void setFpanoid(String fpanoid) {
        this.fpanoid = fpanoid == null ? null : fpanoid.trim();
    }

    public String getFroomintroduction() {
        return froomintroduction;
    }

    public void setFroomintroduction(String froomintroduction) {
        this.froomintroduction = froomintroduction == null ? null : froomintroduction.trim();
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid == null ? null : projectid.trim();
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

    public String getfPanoramicUrl() {
        return fPanoramicUrl;
    }

    public void setfPanoramicUrl(String fPanoramicUrl) {
        this.fPanoramicUrl = fPanoramicUrl == null ? null : fPanoramicUrl.trim();
    }

    public String getfShareUrl() {
        return fShareUrl;
    }

    public void setfShareUrl(String fShareUrl) {
        this.fShareUrl = fShareUrl == null ? null : fShareUrl.trim();
    }

    public String getfHeadFigureUrl() {
        return fHeadFigureUrl;
    }

    public void setfHeadFigureUrl(String fHeadFigureUrl) {
        this.fHeadFigureUrl = fHeadFigureUrl == null ? null : fHeadFigureUrl.trim();
    }
}