package com.ziroom.minsu.entity.house;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

 /**
  * 
  * <p>房源商机信息实体类</p>
  * 
  * <PRE>
  * <BR>	修改记录
  * <BR>-----------------------------------------------
  * <BR>	修改日期			修改人			修改内容
  * </PRE>
  * 
  * @author bushujie
  * @since 1.0
  * @version 1.0
  */
public class HouseBusinessMsgEntity extends BaseEntity {
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -8537984927187618377L;

	/**
     * 自增id
     * This field corresponds to the database column t_house_business_msg.id
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private Integer id;

    /**
     * 逻辑fid
     * This field corresponds to the database column t_house_business_msg.fid
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private String fid;

    /**
     * 商机编号
     * This field corresponds to the database column t_house_business_msg.busniess_sn
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private String busniessSn;

    /**
     * 房源fid
     * This field corresponds to the database column t_house_business_msg.house_base_fid
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private String houseBaseFid;

    /**
     * 国家code
     * This field corresponds to the database column t_house_business_msg.nation_code
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private String nationCode;

    /**
     * 省份code
     * This field corresponds to the database column t_house_business_msg.province_code
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private String provinceCode;

    /**
     * 城市code
     * This field corresponds to the database column t_house_business_msg.city_code
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private String cityCode;

    /**
     * 区域code
     * This field corresponds to the database column t_house_business_msg.area_code
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private String areaCode;

    /**
     * 小区名称
     * This field corresponds to the database column t_house_business_msg.community_name
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private String communityName;

    /**
     * 房源地址
     * This field corresponds to the database column t_house_business_msg.house_addr
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private String houseAddr;

    /**
     * 出租方式 0：整租，1：合租，2：可整可分
     * This field corresponds to the database column t_house_business_msg.rent_way
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private Integer rentWay;

    /**
     * 商圈
     * This field corresponds to the database column t_house_business_msg.business_area
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private String businessArea;

    /**
     * 商机备注
     * This field corresponds to the database column t_house_business_msg.business_remark
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private String businessRemark;

    /**
     * 发布时间
     * This field corresponds to the database column t_house_business_msg.release_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private Date releaseDate;

    /**
     * 上架时间
     * This field corresponds to the database column t_house_business_msg.putaway_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private Date putawayDate;

    /**
     * 预约线下验房时间
     * This field corresponds to the database column t_house_business_msg.make_check_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private Date makeCheckDate;

    /**
     * 实际线下验房时间
     * This field corresponds to the database column t_house_business_msg.real_check_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private Date realCheckDate;

    /**
     * 预约拍照时间
     * This field corresponds to the database column t_house_business_msg.make_photo_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private Date makePhotoDate;

    /**
     * 实际拍照时间
     * This field corresponds to the database column t_house_business_msg.real_photo_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private Date realPhotoDate;

    /**
     * 登记时间
     * This field corresponds to the database column t_house_business_msg.register_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private Date registerDate;

    /**
     * 创建时间
     * This field corresponds to the database column t_house_business_msg.create_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private Date createDate;

    /**
     * 创建人fid
     * This field corresponds to the database column t_house_business_msg.create_fid
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private String createFid;

    /**
     * 最后修改时间
     * This field corresponds to the database column t_house_business_msg.last_modify_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private Date lastModifyDate;

    /**
     * 是否删除 0：否，1：是
     * This field corresponds to the database column t_house_business_msg.is_del
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    private Integer isDel;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.id
     *
     * @return the value of t_house_business_msg.id
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.id
     *
     * @param id the value for t_house_business_msg.id
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.fid
     *
     * @return the value of t_house_business_msg.fid
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public String getFid() {
        return fid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.fid
     *
     * @param fid the value for t_house_business_msg.fid
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.busniess_sn
     *
     * @return the value of t_house_business_msg.busniess_sn
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public String getBusniessSn() {
        return busniessSn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.busniess_sn
     *
     * @param busniessSn the value for t_house_business_msg.busniess_sn
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setBusniessSn(String busniessSn) {
        this.busniessSn = busniessSn == null ? null : busniessSn.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.house_base_fid
     *
     * @return the value of t_house_business_msg.house_base_fid
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public String getHouseBaseFid() {
        return houseBaseFid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.house_base_fid
     *
     * @param houseBaseFid the value for t_house_business_msg.house_base_fid
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setHouseBaseFid(String houseBaseFid) {
        this.houseBaseFid = houseBaseFid == null ? null : houseBaseFid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.nation_code
     *
     * @return the value of t_house_business_msg.nation_code
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public String getNationCode() {
        return nationCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.nation_code
     *
     * @param nationCode the value for t_house_business_msg.nation_code
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setNationCode(String nationCode) {
        this.nationCode = nationCode == null ? null : nationCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.province_code
     *
     * @return the value of t_house_business_msg.province_code
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public String getProvinceCode() {
        return provinceCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.province_code
     *
     * @param provinceCode the value for t_house_business_msg.province_code
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.city_code
     *
     * @return the value of t_house_business_msg.city_code
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.city_code
     *
     * @param cityCode the value for t_house_business_msg.city_code
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.area_code
     *
     * @return the value of t_house_business_msg.area_code
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.area_code
     *
     * @param areaCode the value for t_house_business_msg.area_code
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.community_name
     *
     * @return the value of t_house_business_msg.community_name
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public String getCommunityName() {
        return communityName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.community_name
     *
     * @param communityName the value for t_house_business_msg.community_name
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setCommunityName(String communityName) {
        this.communityName = communityName == null ? null : communityName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.house_addr
     *
     * @return the value of t_house_business_msg.house_addr
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public String getHouseAddr() {
        return houseAddr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.house_addr
     *
     * @param houseAddr the value for t_house_business_msg.house_addr
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setHouseAddr(String houseAddr) {
        this.houseAddr = houseAddr == null ? null : houseAddr.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.rent_way
     *
     * @return the value of t_house_business_msg.rent_way
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public Integer getRentWay() {
        return rentWay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.rent_way
     *
     * @param rentWay the value for t_house_business_msg.rent_way
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.business_area
     *
     * @return the value of t_house_business_msg.business_area
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public String getBusinessArea() {
        return businessArea;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.business_area
     *
     * @param businessArea the value for t_house_business_msg.business_area
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea == null ? null : businessArea.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.business_remark
     *
     * @return the value of t_house_business_msg.business_remark
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public String getBusinessRemark() {
        return businessRemark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.business_remark
     *
     * @param businessRemark the value for t_house_business_msg.business_remark
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setBusinessRemark(String businessRemark) {
        this.businessRemark = businessRemark == null ? null : businessRemark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.release_date
     *
     * @return the value of t_house_business_msg.release_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.release_date
     *
     * @param releaseDate the value for t_house_business_msg.release_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.putaway_date
     *
     * @return the value of t_house_business_msg.putaway_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public Date getPutawayDate() {
        return putawayDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.putaway_date
     *
     * @param putawayDate the value for t_house_business_msg.putaway_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setPutawayDate(Date putawayDate) {
        this.putawayDate = putawayDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.make_check_date
     *
     * @return the value of t_house_business_msg.make_check_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public Date getMakeCheckDate() {
        return makeCheckDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.make_check_date
     *
     * @param makeCheckDate the value for t_house_business_msg.make_check_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setMakeCheckDate(Date makeCheckDate) {
        this.makeCheckDate = makeCheckDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.real_check_date
     *
     * @return the value of t_house_business_msg.real_check_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public Date getRealCheckDate() {
        return realCheckDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.real_check_date
     *
     * @param realCheckDate the value for t_house_business_msg.real_check_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setRealCheckDate(Date realCheckDate) {
        this.realCheckDate = realCheckDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.make_photo_date
     *
     * @return the value of t_house_business_msg.make_photo_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public Date getMakePhotoDate() {
        return makePhotoDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.make_photo_date
     *
     * @param makePhotoDate the value for t_house_business_msg.make_photo_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setMakePhotoDate(Date makePhotoDate) {
        this.makePhotoDate = makePhotoDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.real_photo_date
     *
     * @return the value of t_house_business_msg.real_photo_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public Date getRealPhotoDate() {
        return realPhotoDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.real_photo_date
     *
     * @param realPhotoDate the value for t_house_business_msg.real_photo_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setRealPhotoDate(Date realPhotoDate) {
        this.realPhotoDate = realPhotoDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.register_date
     *
     * @return the value of t_house_business_msg.register_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public Date getRegisterDate() {
        return registerDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.register_date
     *
     * @param registerDate the value for t_house_business_msg.register_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.create_date
     *
     * @return the value of t_house_business_msg.create_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.create_date
     *
     * @param createDate the value for t_house_business_msg.create_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.create_fid
     *
     * @return the value of t_house_business_msg.create_fid
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public String getCreateFid() {
        return createFid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.create_fid
     *
     * @param createFid the value for t_house_business_msg.create_fid
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setCreateFid(String createFid) {
        this.createFid = createFid == null ? null : createFid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.last_modify_date
     *
     * @return the value of t_house_business_msg.last_modify_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.last_modify_date
     *
     * @param lastModifyDate the value for t_house_business_msg.last_modify_date
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg.is_del
     *
     * @return the value of t_house_business_msg.is_del
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg.is_del
     *
     * @param isDel the value for t_house_business_msg.is_del
     *
     * @mbggenerated Tue Jul 05 16:34:09 CST 2016
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}