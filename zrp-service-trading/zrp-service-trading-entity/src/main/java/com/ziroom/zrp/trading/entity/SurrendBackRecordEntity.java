package com.ziroom.zrp.trading.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
/**
 * <p>退租审核不通过原因</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年11月21日
 * @since 1.0
 */
public class SurrendBackRecordEntity extends BaseEntity{
	
	private static final long serialVersionUID = -1228809266228752005L;

	/**
     * 主键
     */
    private String fid;

    /**
     * 解约协议id
     */
    private String surrenderid;

    /**
     * 退回人id
     */
    private String fsender;

    /**
     * 退回人名称
     */
    private String fsendername;

    /**
     * 退回时间
     */
    private Date fsenddate;

    /**
     * 退回原因
     */
    private String fsendreason;

    /**
     * 退回类型
     */
    private String fbacktype;

    /**
     * 是否删除
     */
    private Integer fisdel;

    /**
     * 是否有效
     */
    private Integer fvalid;

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

    public String getSurrenderid() {
        return surrenderid;
    }

    public void setSurrenderid(String surrenderid) {
        this.surrenderid = surrenderid == null ? null : surrenderid.trim();
    }

    public String getFsender() {
        return fsender;
    }

    public void setFsender(String fsender) {
        this.fsender = fsender == null ? null : fsender.trim();
    }

    public String getFsendername() {
        return fsendername;
    }

    public void setFsendername(String fsendername) {
        this.fsendername = fsendername == null ? null : fsendername.trim();
    }

    public Date getFsenddate() {
        return fsenddate;
    }

    public void setFsenddate(Date fsenddate) {
        this.fsenddate = fsenddate;
    }

    public String getFsendreason() {
        return fsendreason;
    }

    public void setFsendreason(String fsendreason) {
        this.fsendreason = fsendreason == null ? null : fsendreason.trim();
    }

    public String getFbacktype() {
        return fbacktype;
    }

    public void setFbacktype(String fbacktype) {
        this.fbacktype = fbacktype == null ? null : fbacktype.trim();
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

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }
}