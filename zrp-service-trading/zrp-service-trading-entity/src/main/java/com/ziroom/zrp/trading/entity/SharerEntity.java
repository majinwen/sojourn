package com.ziroom.zrp.trading.entity;

import com.asura.framework.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * 合租人信息
 * @author jixd
 * @created 2017年09月18日 13:55:30
 * @param
 * @return
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SharerEntity extends BaseEntity{

    private static final long serialVersionUID = 7034257556572695703L;

    private String fid;

    /**
     * 合同ID
     */
    private String fcontractid;

    /**
     * 姓名
     */
    private String fname;

    /**
     * 学历：0其他,1小学,2初中,3高中,4中技,5中专,6大专,7本科,8MBA,9硕士,10博士
     */
    private Integer fdegree;

    /**
     * 性别：2男 1女
     */
    private String fgender;

    /**
     * 婚姻：1未婚 2已婚 3离异
     */
    private Integer fmarriage;

    /**
     * 手机号
     */
    private String fmobile;

    /**
     * 身份证件类型：1身份证,2护照,3军官证,5驾驶证,6台胞证,10学生证,11 毕业证,12营业执照, 0其他
     */
    private String fcerttype;

    /**
     * 身份证件号
     */
    private String fcertnum;

    /**
     * 证件照片
     */
    private String fcertpic;

    /**
     * 通讯地址
     */
    private String faddress;

    /**
     * 邮编
     */
    private String fpostcode;

    /**
     * 电子邮箱
     */
    private String femail;

    /**
     * 是否删除
     */
    private Integer fisdel;

    /**
     * 是否有效
     */
    private Integer fvalid;

    /**
     * 城市id
     */
    private String fcityid;

    /**
     * 创建人ID
     */
    private String fcreatorid;

    /**
     * 创建人NAME
     */
    private String fcreatorname;

    /**
     * 创建时间
     */
    private Date fcreatetime;

    /**
     * 修改人ID
     */
    private String fupdaterid;

    /**
     * 修改人NAME
     */
    private String fupdatername;

    /**
     * 修改时间
     */
    private Date fupdatetime;

    /**
     * 关联城市表
     */
    private String cityid;

    /**
     * 国籍
     */
    private String fnationality;

    /**
     * 籍贯
     */
    private String fhometown;

    /**
     * 年龄
     */
    private Integer fage;

    /**
     * 紧急联系人
     */
    private String femergencycontact;

    /**
     * 紧急联系人电话
     */
    private String femcycntphone;

    /**
     * 紧急联系人关系
     */
    private String frelationship;

    /**
     * 职业
     */
    private String fjob;

    /**
     * 社会资质证明
     */
    private Integer fsocialproof;

    /**
     * 入住人标识
     */
    private String uid;

    /**
     * 入住人表主键标识
     */
    private Integer rentId;

    /**
     * 证件照地址一
     */
    private String fcertpic1;

    /**
     * 社会资质证明照
     */
    private String fsocialproofpic;

    /**
     * 单位/学校
     */
    private String forganization;

    /**
     * 操作时间
     */
    private Date flogtime;

    /**
     * 房间id
     */
    private String froomid;

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

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname == null ? null : fname.trim();
    }

    public Integer getFdegree() {
        return fdegree;
    }

    public void setFdegree(Integer fdegree) {
        this.fdegree = fdegree;
    }

    public String getFgender() {
        return fgender;
    }

    public void setFgender(String fgender) {
        this.fgender = fgender == null ? null : fgender.trim();
    }

    public Integer getFmarriage() {
        return fmarriage;
    }

    public void setFmarriage(Integer fmarriage) {
        this.fmarriage = fmarriage;
    }

    public String getFmobile() {
        return fmobile;
    }

    public void setFmobile(String fmobile) {
        this.fmobile = fmobile == null ? null : fmobile.trim();
    }

    public String getFcerttype() {
        return fcerttype;
    }

    public void setFcerttype(String fcerttype) {
        this.fcerttype = fcerttype == null ? null : fcerttype.trim();
    }

    public String getFcertnum() {
        return fcertnum;
    }

    public void setFcertnum(String fcertnum) {
        this.fcertnum = fcertnum == null ? null : fcertnum.trim();
    }

    public String getFcertpic() {
        return fcertpic;
    }

    public void setFcertpic(String fcertpic) {
        this.fcertpic = fcertpic == null ? null : fcertpic.trim();
    }

    public String getFaddress() {
        return faddress;
    }

    public void setFaddress(String faddress) {
        this.faddress = faddress == null ? null : faddress.trim();
    }

    public String getFpostcode() {
        return fpostcode;
    }

    public void setFpostcode(String fpostcode) {
        this.fpostcode = fpostcode == null ? null : fpostcode.trim();
    }

    public String getFemail() {
        return femail;
    }

    public void setFemail(String femail) {
        this.femail = femail == null ? null : femail.trim();
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

    public String getFcreatorid() {
        return fcreatorid;
    }

    public void setFcreatorid(String fcreatorid) {
        this.fcreatorid = fcreatorid == null ? null : fcreatorid.trim();
    }

    public String getFcreatorname() {
        return fcreatorname;
    }

    public void setFcreatorname(String fcreatorname) {
        this.fcreatorname = fcreatorname == null ? null : fcreatorname.trim();
    }

    public Date getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public String getFupdaterid() {
        return fupdaterid;
    }

    public void setFupdaterid(String fupdaterid) {
        this.fupdaterid = fupdaterid == null ? null : fupdaterid.trim();
    }

    public String getFupdatername() {
        return fupdatername;
    }

    public void setFupdatername(String fupdatername) {
        this.fupdatername = fupdatername == null ? null : fupdatername.trim();
    }

    public Date getFupdatetime() {
        return fupdatetime;
    }

    public void setFupdatetime(Date fupdatetime) {
        this.fupdatetime = fupdatetime;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }

    public String getFnationality() {
        return fnationality;
    }

    public void setFnationality(String fnationality) {
        this.fnationality = fnationality == null ? null : fnationality.trim();
    }

    public String getFhometown() {
        return fhometown;
    }

    public void setFhometown(String fhometown) {
        this.fhometown = fhometown == null ? null : fhometown.trim();
    }

    public Integer getFage() {
        return fage;
    }

    public void setFage(Integer fage) {
        this.fage = fage;
    }

    public String getFemergencycontact() {
        return femergencycontact;
    }

    public void setFemergencycontact(String femergencycontact) {
        this.femergencycontact = femergencycontact == null ? null : femergencycontact.trim();
    }

    public String getFemcycntphone() {
        return femcycntphone;
    }

    public void setFemcycntphone(String femcycntphone) {
        this.femcycntphone = femcycntphone == null ? null : femcycntphone.trim();
    }

    public String getFrelationship() {
        return frelationship;
    }

    public void setFrelationship(String frelationship) {
        this.frelationship = frelationship == null ? null : frelationship.trim();
    }

    public String getFjob() {
        return fjob;
    }

    public void setFjob(String fjob) {
        this.fjob = fjob == null ? null : fjob.trim();
    }

    public Integer getFsocialproof() {
        return fsocialproof;
    }

    public void setFsocialproof(Integer fsocialproof) {
        this.fsocialproof = fsocialproof;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Integer getRentId() {
        return rentId;
    }

    public void setRentId(Integer rentId) {
        this.rentId = rentId;
    }

    public String getFcertpic1() {
        return fcertpic1;
    }

    public void setFcertpic1(String fcertpic1) {
        this.fcertpic1 = fcertpic1 == null ? null : fcertpic1.trim();
    }

    public String getFsocialproofpic() {
        return fsocialproofpic;
    }

    public void setFsocialproofpic(String fsocialproofpic) {
        this.fsocialproofpic = fsocialproofpic == null ? null : fsocialproofpic.trim();
    }

    public String getForganization() {
        return forganization;
    }

    public void setForganization(String forganization) {
        this.forganization = forganization == null ? null : forganization.trim();
    }

    public Date getFlogtime() {
        return flogtime;
    }

    public void setFlogtime(Date flogtime) {
        this.flogtime = flogtime;
    }

    public String getFroomid() {
        return froomid;
    }

    public void setFroomid(String froomid) {
        this.froomid = froomid == null ? null : froomid.trim();
    }
}