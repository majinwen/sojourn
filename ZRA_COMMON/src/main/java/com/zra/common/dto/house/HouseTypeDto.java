package com.zra.common.dto.house;

public class HouseTypeDto {
	    /*主键*/
	    private String id;
	    /*户型名称*/
	    private String houseTypeName;
	    /*面积*/
	    private Double area;
	    /*总间数*/
	    private Integer totalRoom;
	    /*0:开间 1:套间*/
	    private String roomType;
	    /*户型说明*/
	    private String houseTypeDesc;
	    /*户型图*/
	    private String houseTypeImg;
	    /*户型对应的二维码图片*/
	    private String qrCodeImg;
	    /*显示顺序*/
	    private Integer showOrder;
	    /*户型全景Id(腾讯提供)*/
	    private String panoId;
	    /*房型介绍*/
	    private String roomiIntroduction;
	    /*项目id*/
	    private String projectId;
	    /*城市ID*/
	    private String cityId;
	    /*1是网站显示,0禁止网站显示*/
	    private Integer valid;
	    /*全景看房*/
	    private String panoramicUrl;
	    /*分享链接*/
	    private String shareUrl;
	    /*户型头图*/
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
		public String getQrCodeImg() {
			return qrCodeImg;
		}
		public void setQrCodeImg(String qrCodeImg) {
			this.qrCodeImg = qrCodeImg;
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
		public String getRoomiIntroduction() {
			return roomiIntroduction;
		}
		public void setRoomiIntroduction(String roomiIntroduction) {
			this.roomiIntroduction = roomiIntroduction;
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
