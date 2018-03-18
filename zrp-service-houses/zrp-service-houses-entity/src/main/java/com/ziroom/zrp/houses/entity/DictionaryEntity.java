package com.ziroom.zrp.houses.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
/**
 * <p>字典表实体类</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月27日
 * @since 1.0
 */
public class DictionaryEntity extends BaseEntity{
    /**
     * 主键
     */
    private String fid;

    private String fkey;

    private String fvalue;

    /**
     * 对应枚举类名
     */
    private String fenumName;

    private Integer fisDel;

    private Integer findex;

    private Date fcreateTime;

    private Date fupdateTime;

    private String fparentId;

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

    public String getFkey() {
        return fkey;
    }

    public void setFkey(String fkey) {
        this.fkey = fkey == null ? null : fkey.trim();
    }

    public String getFvalue() {
        return fvalue;
    }

    public void setFvalue(String fvalue) {
        this.fvalue = fvalue == null ? null : fvalue.trim();
    }

    public String getFenumName() {
        return fenumName;
    }

    public void setFenumName(String fenumName) {
        this.fenumName = fenumName == null ? null : fenumName.trim();
    }

    public Integer getFisDel() {
        return fisDel;
    }

    public void setFisDel(Integer fisDel) {
        this.fisDel = fisDel;
    }

    public Integer getFindex() {
        return findex;
    }

    public void setFindex(Integer findex) {
        this.findex = findex;
    }

    public Date getFcreateTime() {
        return fcreateTime;
    }

    public void setFcreateTime(Date fcreateTime) {
        this.fcreateTime = fcreateTime;
    }

    public Date getFupdateTime() {
        return fupdateTime;
    }

    public void setFupdateTime(Date fupdateTime) {
        this.fupdateTime = fupdateTime;
    }

    public String getFparentId() {
        return fparentId;
    }

    public void setFparentId(String fparentId) {
        this.fparentId = fparentId == null ? null : fparentId.trim();
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