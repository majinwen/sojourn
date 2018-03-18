package com.ziroom.zrp.houses.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
/**
 * <p>房间信息实体类</p>
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
public class RoomInfoEntity extends BaseEntity{
	
	private static final long serialVersionUID = -5785923106372181545L;

	private String fid;

    /**
     * 项目ID
     */
    private String projectid;

    /**
     * 楼栋ID
     */
    private String buildingid;

    /**
     * 层数
     */
    private Integer ffloornumber;

    /**
     * 房间号
     */
    private String froomnumber;

    /**
     * 房间面积
     */
    private Double froomarea;

    /**
     * 户型ID
     */
    private String housetypeid;

    /**
     * 房间实际居住表id
     */
    private String roomliveid;

    /**
     * 基础价格
     */
    private Double fbaseprice;

    /**
     * 当前长租房价
     */
    private Double flongprice;

    /**
     * 当前短租房价(1-3个月，不包括3个月)
     */
    private Double fshortprice;

    /**
     * 当前短租房价(3-6个月，包括3个月)
     */
    private Double fshortprice2;

    /**
     * 床类型
     */
    private String fbedtype;

    /**
     * 装修风格
     */
    private String fdecorationstyle;

    /**
     * 朝向
     */
    private String fdirection;

    /**
     * 是否可短租
     */
    private String fshortrent;

    /**
     * 房间当前状态(0：待租中；1：已出租；2：配置中；3已下定；4：锁定；5：已下架；6：预定进行中；7：签约进行中；8：可预订；)
     */
    private String fcurrentstate;

    /**
     * 网站是否显示
     */
    private Integer fisnetshow;

    /**
     * 公司ID
     */
    private String companyid;

    private Integer fvalid;

    private Date fcreatetime;

    private String createrid;

    private Date fupdatetime;

    private String updaterid;

    private Integer fisdel;

    /**
     * 锁定时间
     */
    private Date flocktime;

    /**
     * 禁止时间
     */
    private Date fdisabletime;

    /**
     * 房间配置完成日期
     */
    private Date configcompletedate;

    private Integer fisused;

    /**
     * 关联城市表
     */
    private String cityid;

    /**
     * 锁定房间来源:空或1为自如寓管理后台锁定;2为M站锁定
     */
    private String flockroomsource;

    /**
     * 开放预订日期
     */
    private Date fopenbookdate;

    /**
     * 可签约日期(只有状态为可预订时，此字段才会有意义）
     */
    private Date favasigndate;

    /**
     * 类型：0:-房间；1-床位
     */
    private Integer ftype;

    /**
     * 房间无需，若是床位，指定其父房间
     */
    private String parentId;

    /**
     * 出租方式：1按房间2按床位
     */
    private Integer rentType;

    /**
     * 房间中床位的数目
     */
    private Integer bednum;

    /**
     * 短租价格，由priceinfo中的短租价格拼接而成
     */
    private String shortpricestr;

    /**
     * 房间描述
     */
    private String froomdesc;

    /**
     * 是否绑定智能锁 0-未绑定 1-已绑定
     */
    private Integer isBindLock;

    /**
     * 原房间状态（供更新房间信息时使用）
     */
    private transient String preRoomState;

    public Integer getIsBindLock() {
        return isBindLock;
    }

    public void setIsBindLock(Integer isBindLock) {
        this.isBindLock = isBindLock;
    }

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

    public String getBuildingid() {
        return buildingid;
    }

    public void setBuildingid(String buildingid) {
        this.buildingid = buildingid == null ? null : buildingid.trim();
    }

    public Integer getFfloornumber() {
        return ffloornumber;
    }

    public void setFfloornumber(Integer ffloornumber) {
        this.ffloornumber = ffloornumber;
    }

    public String getFroomnumber() {
        return froomnumber;
    }

    public void setFroomnumber(String froomnumber) {
        this.froomnumber = froomnumber == null ? null : froomnumber.trim();
    }

    public Double getFroomarea() {
        return froomarea;
    }

    public void setFroomarea(Double froomarea) {
        this.froomarea = froomarea;
    }

    public String getHousetypeid() {
        return housetypeid;
    }

    public void setHousetypeid(String housetypeid) {
        this.housetypeid = housetypeid == null ? null : housetypeid.trim();
    }

    public String getRoomliveid() {
        return roomliveid;
    }

    public void setRoomliveid(String roomliveid) {
        this.roomliveid = roomliveid == null ? null : roomliveid.trim();
    }

    public Double getFbaseprice() {
        return fbaseprice;
    }

    public void setFbaseprice(Double fbaseprice) {
        this.fbaseprice = fbaseprice;
    }

    public Double getFlongprice() {
        return flongprice;
    }

    public void setFlongprice(Double flongprice) {
        this.flongprice = flongprice;
    }

    public Double getFshortprice() {
        return fshortprice;
    }

    public void setFshortprice(Double fshortprice) {
        this.fshortprice = fshortprice;
    }

    public Double getFshortprice2() {
        return fshortprice2;
    }

    public void setFshortprice2(Double fshortprice2) {
        this.fshortprice2 = fshortprice2;
    }

    public String getFbedtype() {
        return fbedtype;
    }

    public void setFbedtype(String fbedtype) {
        this.fbedtype = fbedtype == null ? null : fbedtype.trim();
    }

    public String getFdecorationstyle() {
        return fdecorationstyle;
    }

    public void setFdecorationstyle(String fdecorationstyle) {
        this.fdecorationstyle = fdecorationstyle == null ? null : fdecorationstyle.trim();
    }

    public String getFdirection() {
        return fdirection;
    }

    public void setFdirection(String fdirection) {
        this.fdirection = fdirection == null ? null : fdirection.trim();
    }

    public String getFshortrent() {
        return fshortrent;
    }

    public void setFshortrent(String fshortrent) {
        this.fshortrent = fshortrent == null ? null : fshortrent.trim();
    }

    public String getFcurrentstate() {
        return fcurrentstate;
    }

    public void setFcurrentstate(String fcurrentstate) {
        this.fcurrentstate = fcurrentstate == null ? null : fcurrentstate.trim();
    }

    public Integer getFisnetshow() {
        return fisnetshow;
    }

    public void setFisnetshow(Integer fisnetshow) {
        this.fisnetshow = fisnetshow;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid == null ? null : companyid.trim();
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

    public Date getFlocktime() {
        return flocktime;
    }

    public void setFlocktime(Date flocktime) {
        this.flocktime = flocktime;
    }

    public Date getFdisabletime() {
        return fdisabletime;
    }

    public void setFdisabletime(Date fdisabletime) {
        this.fdisabletime = fdisabletime;
    }

    public Date getConfigcompletedate() {
        return configcompletedate;
    }

    public void setConfigcompletedate(Date configcompletedate) {
        this.configcompletedate = configcompletedate;
    }

    public Integer getFisused() {
        return fisused;
    }

    public void setFisused(Integer fisused) {
        this.fisused = fisused;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }

    public String getFlockroomsource() {
        return flockroomsource;
    }

    public void setFlockroomsource(String flockroomsource) {
        this.flockroomsource = flockroomsource == null ? null : flockroomsource.trim();
    }

    public Date getFopenbookdate() {
        return fopenbookdate;
    }

    public void setFopenbookdate(Date fopenbookdate) {
        this.fopenbookdate = fopenbookdate;
    }

    public Date getFavasigndate() {
        return favasigndate;
    }

    public void setFavasigndate(Date favasigndate) {
        this.favasigndate = favasigndate;
    }

    public Integer getFtype() {
        return ftype;
    }

    public void setFtype(Integer ftype) {
        this.ftype = ftype;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Integer getRentType() {
        return rentType;
    }

    public void setRentType(Integer rentType) {
        this.rentType = rentType;
    }

    public Integer getBednum() {
        return bednum;
    }

    public void setBednum(Integer bednum) {
        this.bednum = bednum;
    }

    public String getShortpricestr() {
        return shortpricestr;
    }

    public void setShortpricestr(String shortpricestr) {
        this.shortpricestr = shortpricestr == null ? null : shortpricestr.trim();
    }

    public String getFroomdesc() {
        return froomdesc;
    }

    public void setFroomdesc(String froomdesc) {
        this.froomdesc = froomdesc == null ? null : froomdesc.trim();
    }

    public String getPreRoomState() {
        return preRoomState;
    }

    public void setPreRoomState(String preRoomState) {
        this.preRoomState = preRoomState;
    }
}