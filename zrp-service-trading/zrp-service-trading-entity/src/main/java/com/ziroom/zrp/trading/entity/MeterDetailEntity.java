package com.ziroom.zrp.trading.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>水电费录入明细</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年9月15日
 * @since 1.0
 */
public class MeterDetailEntity extends BaseEntity{

    private static final long serialVersionUID = -9076381469825176247L;
    private String fid;

    /**
     * 合同ID
     */
    private String fcontractid;

    /**
     * 上次客户退水费
     */
    private Double freturnwaterprice;

    /**
     * 上次客户退电费
     */
    private Double freturnelectricprice;

    /**
     * 水表类型（0 预付费;1 后付费 By ElectricityTypeEnum）
     */
    private String fwatermetertype;

    /**
     * 电表类型（0 预付费;1 后付费 By ElectricityTypeEnum）
     */
    private String felectricmetertype;

    /**
     * 水表示数
     */
    private Double fwatermeternumber;

    /**
     * 电表示数
     */
    private Double felectricmeternumber;

    /**
     * 本次充值水费
     */
    private Double fpaywaterprice;

    /**
     * 本次充值电费
     */
    private Double fpayelectricprice;

    /**
     * 备注
     */
    private String fcomment;

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

    /**
     * 房间或者床位id
     */
    private String roomId;

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

    public Double getFreturnwaterprice() {
        return freturnwaterprice;
    }

    public void setFreturnwaterprice(Double freturnwaterprice) {
        this.freturnwaterprice = freturnwaterprice;
    }

    public Double getFreturnelectricprice() {
        return freturnelectricprice;
    }

    public void setFreturnelectricprice(Double freturnelectricprice) {
        this.freturnelectricprice = freturnelectricprice;
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

    public Double getFwatermeternumber() {
        return fwatermeternumber;
    }

    public void setFwatermeternumber(Double fwatermeternumber) {
        this.fwatermeternumber = fwatermeternumber;
    }

    public Double getFelectricmeternumber() {
        return felectricmeternumber;
    }

    public void setFelectricmeternumber(Double felectricmeternumber) {
        this.felectricmeternumber = felectricmeternumber;
    }

    public Double getFpaywaterprice() {
        return fpaywaterprice;
    }

    public void setFpaywaterprice(Double fpaywaterprice) {
        this.fpaywaterprice = fpaywaterprice;
    }

    public Double getFpayelectricprice() {
        return fpayelectricprice;
    }

    public void setFpayelectricprice(Double fpayelectricprice) {
        this.fpayelectricprice = fpayelectricprice;
    }

    public String getFcomment() {
        return fcomment;
    }

    public void setFcomment(String fcomment) {
        this.fcomment = fcomment == null ? null : fcomment.trim();
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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId == null ? null : roomId.trim();
    }
}