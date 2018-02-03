package com.ziroom.minsu.services.cms.entity;

import com.ziroom.minsu.entity.cms.ColumnRegionEntity;

/**
 * Created with IntelliJ IDEA
 * User: lunan
 * Date: 2016/11/17
 */
public class ColumnRegionPicVo extends ColumnRegionEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -2024571439267771051L;

    //图片访问地址
    private String picBaseUrl;

    //图片后缀
    private String picSuffix;

    //专栏名称
    private String regionName;

    //专栏描述
    private String regionBrief;

    //图片跳转路径
    private String jumpUrl;

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public String getRegionBrief() {
        return regionBrief;
    }

    public void setRegionBrief(String regionBrief) {
        this.regionBrief = regionBrief;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getPicSuffix() {
        return picSuffix;
    }

    public void setPicSuffix(String picSuffix) {
        this.picSuffix = picSuffix;
    }

    public String getPicBaseUrl() {
        return picBaseUrl;
    }

    public void setPicBaseUrl(String picBaseUrl) {
        this.picBaseUrl = picBaseUrl;
    }
}
