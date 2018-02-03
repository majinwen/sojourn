package com.ziroom.minsu.services.basedata.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * Created with IntelliJ IDEA
 * User: lunan
 * Date: 2016/11/7
 */
public class CityArchivesRequest  extends PageRequest {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 246355445559952589L;

    /** 城市档案请求参数 */

    //城市档案fid
    private String fid;

    //城市名称
    private String showName;

    // 城市
    private String cityCode;

    //是否有档案 0:否 1:是
    private Integer isFile;

    //操作人
    private String fullName;

    //城市档案内容
    private String cityFileContent;

    /** 景点商圈属性开始*/
    // 名称
    private String regionName;

    //景点商圈fid
    private String hotRegionFid;

    //景点商圈内容fid
    private String regionContentFid;

    //商圈景点描述
    private String hotRegionBrief;

    //商圈景点档案
    private String hotRegionContent;

    //创建人fid
    private String createFid;

    /** 推荐项开始*/
    //项目fid
    private String itemFid;

    //项目名称
    private String itemName;

    //项目描述
    private String itemBrief;

    //项目摘要
    private String itemAbstract;

    //项目内容
    private String itemContent;

    public String getItemFid() {
        return itemFid;
    }

    public void setItemFid(String itemFid) {
        this.itemFid = itemFid;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemBrief() {
        return itemBrief;
    }

    public void setItemBrief(String itemBrief) {
        this.itemBrief = itemBrief;
    }

    public String getItemAbstract() {
        return itemAbstract;
    }

    public void setItemAbstract(String itemAbstract) {
        this.itemAbstract = itemAbstract;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }

    public String getRegionContentFid() {
        return regionContentFid;
    }

    public void setRegionContentFid(String regionContentFid) {
        this.regionContentFid = regionContentFid;
    }

    public String getCreateFid() {
        return createFid;
    }

    public void setCreateFid(String createFid) {
        this.createFid = createFid;
    }

    public String getCityFileContent() {
        return cityFileContent;
    }

    public void setCityFileContent(String cityFileContent) {
        this.cityFileContent = cityFileContent;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getHotRegionFid() {
        return hotRegionFid;
    }

    public void setHotRegionFid(String hotRegionFid) {
        this.hotRegionFid = hotRegionFid;
    }

    public String getHotRegionBrief() {
        return hotRegionBrief;
    }

    public void setHotRegionBrief(String hotRegionBrief) {
        this.hotRegionBrief = hotRegionBrief;
    }

    public String getHotRegionContent() {
        return hotRegionContent;
    }

    public void setHotRegionContent(String hotRegionContent) {
        this.hotRegionContent = hotRegionContent;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getIsFile() {
        return isFile;
    }

    public void setIsFile(Integer isFile) {
        this.isFile = isFile;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
