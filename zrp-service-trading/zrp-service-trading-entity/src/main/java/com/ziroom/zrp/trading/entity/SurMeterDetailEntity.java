package com.ziroom.zrp.trading.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 *
 *
 * @Author: wangxm113
 * @Date: 2017年11月04日 17时52分15秒
 */
public class SurMeterDetailEntity extends BaseEntity {
    private String fid;

    /**
     * 合同ID
     */
    private String fcontractid;

    /**
     * 解约协议ID
     */
    private String fsurrenderid;

    /**
     * 解约房间id
     */
    private String roomId;

    /**
     * 水表类型（0 预付费;1 后付费 By ElectricityTypeEnum）
     */
    private String fwatermetertype;

    /**
     * 电表类型（0 预付费;1 后付费 By ElectricityTypeEnum）
     */
    private String felectricmetertype;

    /**
     * 水表上次示数
     */
    private Double fwaterprenumber;

    /**
     * 水表本次示数
     */
    private Double fwaternumber;

    /**
     * 电表上次示数
     */
    private Double felectricprenumber;

    /**
     * 电表本次示数
     */
    private Double felectricnumber;

    /**
     * 解约时水费
     */
    private Double fsurwaterprice;

    /**
     * 解约时电费
     */
    private Double fsurelecprice;

    /**
     * 水表照片
     */
    private String fwatermeterpic;

    /**
     * 电表照片
     */
    private String felectricmeterpic;

    /**
     * 是否删除
     */
    private Integer fisdel;

    /**
     * 是否有效
     */
    private Integer fvalid;

    /**
     * 城市ID
     */
    private String fcityid;

    /**
     * 修改时间
     */
    private Date fupdatetime;

    /**
     * 修改人
     */
    private String fupdaterid;

    /**
     * 创建时间
     */
    private Date fcreatetime;

    /**
     * 创建人
     */
    private String fcreaterid;

    /**
     * 关联城市表
     */
    private String cityid;
    
    //新加的
    private String roomConfigInfo; // 用于存储退租物业交割时的房间物品信息数据到后台
	
	private String roomNewConfigInfo;	// 用于存储退租物业交割时的新增房间物品信息数据到后台
	
	private String roomDecorateInfo; // 用于存储退租物业交割时的房间装修信息数据到后台

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getFcontractid() {
        return fcontractid;
    }

    public void setFcontractid(String fcontractid) {
        this.fcontractid = fcontractid == null ? null : fcontractid.trim();
    }

    public String getFsurrenderid() {
        return fsurrenderid;
    }

    public void setFsurrenderid(String fsurrenderid) {
        this.fsurrenderid = fsurrenderid == null ? null : fsurrenderid.trim();
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId == null ? null : roomId.trim();
    }

    public String getFwatermetertype() {
        return fwatermetertype;
    }

    public void setFwatermetertype(String fwatermetertype) {
        this.fwatermetertype = fwatermetertype == null ? null : fwatermetertype.trim();
    }

    public String getFelectricmetertype() {
        return felectricmetertype;
    }

    public void setFelectricmetertype(String felectricmetertype) {
        this.felectricmetertype = felectricmetertype == null ? null : felectricmetertype.trim();
    }

    public Double getFwaterprenumber() {
        return fwaterprenumber;
    }

    public void setFwaterprenumber(Double fwaterprenumber) {
        this.fwaterprenumber = fwaterprenumber;
    }

    public Double getFwaternumber() {
        return fwaternumber;
    }

    public void setFwaternumber(Double fwaternumber) {
        this.fwaternumber = fwaternumber;
    }

    public Double getFelectricprenumber() {
        return felectricprenumber;
    }

    public void setFelectricprenumber(Double felectricprenumber) {
        this.felectricprenumber = felectricprenumber;
    }

    public Double getFelectricnumber() {
        return felectricnumber;
    }

    public void setFelectricnumber(Double felectricnumber) {
        this.felectricnumber = felectricnumber;
    }

    public Double getFsurwaterprice() {
        return fsurwaterprice;
    }

    public void setFsurwaterprice(Double fsurwaterprice) {
        this.fsurwaterprice = fsurwaterprice;
    }

    public Double getFsurelecprice() {
        return fsurelecprice;
    }

    public void setFsurelecprice(Double fsurelecprice) {
        this.fsurelecprice = fsurelecprice;
    }

    public String getFwatermeterpic() {
        return fwatermeterpic;
    }

    public void setFwatermeterpic(String fwatermeterpic) {
        this.fwatermeterpic = fwatermeterpic == null ? null : fwatermeterpic.trim();
    }

    public String getFelectricmeterpic() {
        return felectricmeterpic;
    }

    public void setFelectricmeterpic(String felectricmeterpic) {
        this.felectricmeterpic = felectricmeterpic == null ? null : felectricmeterpic.trim();
    }

    public Integer getFisdel() {
        return fisdel;
    }

    public void setFisdel(Integer fisdel) {
        this.fisdel = fisdel;
    }

    public Integer getFvalid() {
        return fvalid;
    }

    public void setFvalid(Integer fvalid) {
        this.fvalid = fvalid;
    }

    public String getFcityid() {
        return fcityid;
    }

    public void setFcityid(String fcityid) {
        this.fcityid = fcityid == null ? null : fcityid.trim();
    }

    public Date getFupdatetime() {
        return fupdatetime;
    }

    public void setFupdatetime(Date fupdatetime) {
        this.fupdatetime = fupdatetime;
    }

    public String getFupdaterid() {
        return fupdaterid;
    }

    public void setFupdaterid(String fupdaterid) {
        this.fupdaterid = fupdaterid == null ? null : fupdaterid.trim();
    }

    public Date getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public String getFcreaterid() {
        return fcreaterid;
    }

    public void setFcreaterid(String fcreaterid) {
        this.fcreaterid = fcreaterid == null ? null : fcreaterid.trim();
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }

	public String getRoomConfigInfo() {
		return roomConfigInfo;
	}

	public void setRoomConfigInfo(String roomConfigInfo) {
		this.roomConfigInfo = roomConfigInfo;
	}

	public String getRoomNewConfigInfo() {
		return roomNewConfigInfo;
	}

	public void setRoomNewConfigInfo(String roomNewConfigInfo) {
		this.roomNewConfigInfo = roomNewConfigInfo;
	}

	public String getRoomDecorateInfo() {
		return roomDecorateInfo;
	}

	public void setRoomDecorateInfo(String roomDecorateInfo) {
		this.roomDecorateInfo = roomDecorateInfo;
	}
    
}