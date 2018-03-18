package com.ziroom.zrp.houses.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
/**
 * <p>项目信息实体类</p>
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
public class ProjectEntity extends BaseEntity{
	
	private static final long serialVersionUID = -6122657899556337102L;

	public ProjectEntity(){
    	
    }
	/**
     * 主键
     */
    private String fid;

    /**
     * 项目编号
     */
    private String fcode;

    /**
     * 公司ID
     */
    private String fcompanyid;

    /**
     * 项目名称
     */
    private String fname;

    /**
     * 副标题
     */
    private String ftitle;

    /**
     * 项目介绍
     */
    private String fdescribe;

    /**
     * 项目地址
     */
    private String faddress;

    /**
     * 经度
     */
    private Double flon;

    /**
     * 纬度
     */
    private Double flat;

    /**
     * 项目所属商圈
     */
    private Integer fbusinessdistrict;

    /**
     * 项目所属区域
     */
    private String fregion;

    /**
     * 项目面积
     */
    private Double farea;

    /**
     * 楼栋数
     */
    private Integer fbuildingnum;

    /**
     * 车位数量
     */
    private Integer fcarportnum;

    /**
     * 楼体结构
     */
    private String fbuildingstructure;

    /**
     * 房间总数
     */
    private Integer froomnum;

    /**
     * 户型数
     */
    private Integer fhousetypenum;

    /**
     * 公区面积
     */
    private Integer fpublicarea;

    /**
     * 用电类别
     */
    private String felectricitytype;

    /**
     * 用水类别
     */
    private String fwatertype;

    /**
     * 项目合作模式
     */
    private String fcooperationmodel;

    /**
     * 业主类型
     */
    private String fownertype;

    /**
     * 业主
     */
    private String fownername;

    /**
     * 产权类型
     */
    private String fpropertytype;

    /**
     * 建成年代
     */
    private Integer ffinishyear;

    /**
     * 项目收楼日期
     */
    private Date fcontractbegin;

    /**
     * 项目租赁年限
     */
    private Integer fcontractcycle;

    /**
     * 项目到期日期
     */
    private Date fcontractend;

    /**
     * 项目开业日期
     */
    private Date fopeningtime;

    /**
     * 项目空置总天数
     */
    private Integer fvacancyday;

    /**
     * 项目运营负责人/项目经理
     */
    private String fprojectmanager;

    /**
     * 销售电话
     */
    private String fmarkettel;

    /**
     * 门锁类型
     */
    private String flocktype;

    /**
     * 是否删除
     */
    private Integer fisdel;

    /**
     * 关联城市表
     */
    private String cityid;

    /**
     * AFA项目编码
     */
    private String fafapronum;

    /**
     * 风采展示项目图片
     */
    private String fprojectshowimage;

    /**
     * 全景看房
     */
    private String fPanoramicUrl;

    /**
     * 周边链接
     */
    private String fPeripheralUrl;

    /**
     * 分享链接
     */
    private String fShareUrl;

    /**
     * 项目头图
     */
    private String fHeadFigureUrl;

    /**
     * 周边模块的名称
     */
    private String fPeripheralName;

    /**
     * 项目收房合同号
     */
    private String fContractNumber;

    /**
     * 简介地址
     */
    private String fAddressDesc;

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

    public String getFcompanyid() {
        return fcompanyid;
    }

    public void setFcompanyid(String fcompanyid) {
        this.fcompanyid = fcompanyid == null ? null : fcompanyid.trim();
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname == null ? null : fname.trim();
    }

    public String getFtitle() {
        return ftitle;
    }

    public void setFtitle(String ftitle) {
        this.ftitle = ftitle == null ? null : ftitle.trim();
    }

    public String getFdescribe() {
        return fdescribe;
    }

    public void setFdescribe(String fdescribe) {
        this.fdescribe = fdescribe == null ? null : fdescribe.trim();
    }

    public String getFaddress() {
        return faddress;
    }

    public void setFaddress(String faddress) {
        this.faddress = faddress == null ? null : faddress.trim();
    }

    public Double getFlon() {
        return flon;
    }

    public void setFlon(Double flon) {
        this.flon = flon;
    }

    public Double getFlat() {
        return flat;
    }

    public void setFlat(Double flat) {
        this.flat = flat;
    }

    public Integer getFbusinessdistrict() {
        return fbusinessdistrict;
    }

    public void setFbusinessdistrict(Integer fbusinessdistrict) {
        this.fbusinessdistrict = fbusinessdistrict;
    }

    public String getFregion() {
        return fregion;
    }

    public void setFregion(String fregion) {
        this.fregion = fregion == null ? null : fregion.trim();
    }

    public Double getFarea() {
        return farea;
    }

    public void setFarea(Double farea) {
        this.farea = farea;
    }

    public Integer getFbuildingnum() {
        return fbuildingnum;
    }

    public void setFbuildingnum(Integer fbuildingnum) {
        this.fbuildingnum = fbuildingnum;
    }

    public Integer getFcarportnum() {
        return fcarportnum;
    }

    public void setFcarportnum(Integer fcarportnum) {
        this.fcarportnum = fcarportnum;
    }

    public String getFbuildingstructure() {
        return fbuildingstructure;
    }

    public void setFbuildingstructure(String fbuildingstructure) {
        this.fbuildingstructure = fbuildingstructure == null ? null : fbuildingstructure.trim();
    }

    public Integer getFroomnum() {
        return froomnum;
    }

    public void setFroomnum(Integer froomnum) {
        this.froomnum = froomnum;
    }

    public Integer getFhousetypenum() {
        return fhousetypenum;
    }

    public void setFhousetypenum(Integer fhousetypenum) {
        this.fhousetypenum = fhousetypenum;
    }

    public Integer getFpublicarea() {
        return fpublicarea;
    }

    public void setFpublicarea(Integer fpublicarea) {
        this.fpublicarea = fpublicarea;
    }

    public String getFelectricitytype() {
        return felectricitytype;
    }

    public void setFelectricitytype(String felectricitytype) {
        this.felectricitytype = felectricitytype == null ? null : felectricitytype.trim();
    }

    public String getFwatertype() {
        return fwatertype;
    }

    public void setFwatertype(String fwatertype) {
        this.fwatertype = fwatertype == null ? null : fwatertype.trim();
    }

    public String getFcooperationmodel() {
        return fcooperationmodel;
    }

    public void setFcooperationmodel(String fcooperationmodel) {
        this.fcooperationmodel = fcooperationmodel == null ? null : fcooperationmodel.trim();
    }

    public String getFownertype() {
        return fownertype;
    }

    public void setFownertype(String fownertype) {
        this.fownertype = fownertype == null ? null : fownertype.trim();
    }

    public String getFownername() {
        return fownername;
    }

    public void setFownername(String fownername) {
        this.fownername = fownername == null ? null : fownername.trim();
    }

    public String getFpropertytype() {
        return fpropertytype;
    }

    public void setFpropertytype(String fpropertytype) {
        this.fpropertytype = fpropertytype == null ? null : fpropertytype.trim();
    }

    public Integer getFfinishyear() {
        return ffinishyear;
    }

    public void setFfinishyear(Integer ffinishyear) {
        this.ffinishyear = ffinishyear;
    }

    public Date getFcontractbegin() {
        return fcontractbegin;
    }

    public void setFcontractbegin(Date fcontractbegin) {
        this.fcontractbegin = fcontractbegin;
    }

    public Integer getFcontractcycle() {
        return fcontractcycle;
    }

    public void setFcontractcycle(Integer fcontractcycle) {
        this.fcontractcycle = fcontractcycle;
    }

    public Date getFcontractend() {
        return fcontractend;
    }

    public void setFcontractend(Date fcontractend) {
        this.fcontractend = fcontractend;
    }

    public Date getFopeningtime() {
        return fopeningtime;
    }

    public void setFopeningtime(Date fopeningtime) {
        this.fopeningtime = fopeningtime;
    }

    public Integer getFvacancyday() {
        return fvacancyday;
    }

    public void setFvacancyday(Integer fvacancyday) {
        this.fvacancyday = fvacancyday;
    }

    public String getFprojectmanager() {
        return fprojectmanager;
    }

    public void setFprojectmanager(String fprojectmanager) {
        this.fprojectmanager = fprojectmanager == null ? null : fprojectmanager.trim();
    }

    public String getFmarkettel() {
        return fmarkettel;
    }

    public void setFmarkettel(String fmarkettel) {
        this.fmarkettel = fmarkettel == null ? null : fmarkettel.trim();
    }

    public String getFlocktype() {
        return flocktype;
    }

    public void setFlocktype(String flocktype) {
        this.flocktype = flocktype == null ? null : flocktype.trim();
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

    public String getFafapronum() {
        return fafapronum;
    }

    public void setFafapronum(String fafapronum) {
        this.fafapronum = fafapronum == null ? null : fafapronum.trim();
    }

    public String getFprojectshowimage() {
        return fprojectshowimage;
    }

    public void setFprojectshowimage(String fprojectshowimage) {
        this.fprojectshowimage = fprojectshowimage == null ? null : fprojectshowimage.trim();
    }



    public String getFPanoramicUrl() {
        return fPanoramicUrl;
    }

    public void setFPanoramicUrl(String fPanoramicUrl) {
        this.fPanoramicUrl = fPanoramicUrl == null ? null : fPanoramicUrl.trim();
    }

    public String getFPeripheralUrl() {
        return fPeripheralUrl;
    }

    public void setFPeripheralUrl(String fPeripheralUrl) {
        this.fPeripheralUrl = fPeripheralUrl == null ? null : fPeripheralUrl.trim();
    }

    public String getFShareUrl() {
        return fShareUrl;
    }

    public void setFShareUrl(String fShareUrl) {
        this.fShareUrl = fShareUrl == null ? null : fShareUrl.trim();
    }

    public String getFHeadFigureUrl() {
        return fHeadFigureUrl;
    }

    public void setFHeadFigureUrl(String fHeadFigureUrl) {
        this.fHeadFigureUrl = fHeadFigureUrl == null ? null : fHeadFigureUrl.trim();
    }

    public String getFPeripheralName() {
        return fPeripheralName;
    }

    public void setFPeripheralName(String fPeripheralName) {
        this.fPeripheralName = fPeripheralName == null ? null : fPeripheralName.trim();
    }

    public String getFContractNumber() {
        return fContractNumber;
    }

    public void setFContractNumber(String fContractNumber) {
        this.fContractNumber = fContractNumber == null ? null : fContractNumber.trim();
    }

    public String getFAddressDesc() {
        return fAddressDesc;
    }

    public void setFAddressDesc(String fAddressDesc) {
        this.fAddressDesc = fAddressDesc == null ? null : fAddressDesc.trim();
    }
}