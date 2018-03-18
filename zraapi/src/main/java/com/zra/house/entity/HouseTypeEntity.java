package com.zra.house.entity;

import java.util.Date;

/**
 * <p>房型实体</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/8 9:35
 * @since 1.0
 */
public class HouseTypeEntity {

    /**
     * 主键
     */
    private String id;

    /**
     * 户型名称
     */
    private String houseTypeName;

    /**
     * 面积
     */
    private Double area;

    /**
     * 总间数
     */
    private Integer totalRoom;

    /**
     * 0:开间 1:套间
     */
    private String roomType;

    /**
     * 户型说明
     */
    private String houseTypeDesc;

    /**
     * 户型图
     */
    private String houseTypeImg;

    /**
     * 户型对应的二维码图片
     */
    private String qrcodeImg;

    /**
     * 显示顺序
     */
    private Integer showOrder;

    /**
     * 户型全景Id(腾讯提供)
     */
    private String panoId;

    /**
     * 房型介绍
     */
    private String roomIntroduction;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 城市ID
     */
    private String cityId;

    /**
     * 1是网站显示,0禁止网站显示
     */
    private Integer valid;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createrId;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updaterId;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 全景看房
     */
    private String panoramicUrl;

    /**
     * 分享链接
     */
    private String shareUrl;

    /**
     * 户型头图
     */
    private String headFigureUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHouseTypeName() {
        return houseTypeName;
    }

    public void setHouseTypeName(String houseTypeName) {
        this.houseTypeName = houseTypeName;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Integer getTotalRoom() {
        return totalRoom;
    }

    public void setTotalRoom(Integer totalRoom) {
        this.totalRoom = totalRoom;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getHouseTypeDesc() {
        return houseTypeDesc;
    }

    public void setHouseTypeDesc(String houseTypeDesc) {
        this.houseTypeDesc = houseTypeDesc;
    }

    public String getHouseTypeImg() {
        return houseTypeImg;
    }

    public void setHouseTypeImg(String houseTypeImg) {
        this.houseTypeImg = houseTypeImg;
    }

    public String getQrcodeImg() {
        return qrcodeImg;
    }

    public void setQrcodeImg(String qrcodeImg) {
        this.qrcodeImg = qrcodeImg;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public String getPanoId() {
        return panoId;
    }

    public void setPanoId(String panoId) {
        this.panoId = panoId;
    }

    public String getRoomIntroduction() {
        return roomIntroduction;
    }

    public void setRoomIntroduction(String roomIntroduction) {
        this.roomIntroduction = roomIntroduction;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getPanoramicUrl() {
        return panoramicUrl;
    }

    public void setPanoramicUrl(String panoramicUrl) {
        this.panoramicUrl = panoramicUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getHeadFigureUrl() {
        return headFigureUrl;
    }

    public void setHeadFigureUrl(String headFigureUrl) {
        this.headFigureUrl = headFigureUrl;
    }
}
