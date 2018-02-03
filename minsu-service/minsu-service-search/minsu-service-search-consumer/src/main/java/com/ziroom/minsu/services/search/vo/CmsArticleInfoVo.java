package com.ziroom.minsu.services.search.vo;

import com.asura.framework.base.entity.BaseEntity;

import java.util.List;

/**
 * <p>cms数据同步模型</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zl
 * @version 1.0
 * @Date Created in 2017年07月28日 10:18
 * @since 1.0
 */
public class CmsArticleInfoVo extends BaseEntity {

    private static final long serialVersionUID = -9893318254823672L;

    private String id;
    private String title;
    private String subTitle;
    private String summary;
    private String shareTitle;
    private String shareSubTitle;
    private String shareImg;
    private String cityCode;
    private String cityName;
    private String category;
    private String subCategorys;
    private String targetUrl;
    private String businessAreas;
    private String location;
    private String address;
    private String tel;
    private String openingHours;
    private String price;
    private String tags;
    private String body;
    private List<CmsArticleImgVo> bannerImg;
    private List<CmsArticleImgVo> bodyImg;
    private String updateTime;
    private String articleSN;
    private String created_at;
    private String updated_at;
    private String backgroundColor;
    private String column1;
    private String column2;
    private String column3;
    private String column4;
    private String column5;

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public String getColumn2() {
        return column2;
    }

    public void setColumn2(String column2) {
        this.column2 = column2;
    }

    public String getColumn3() {
        return column3;
    }

    public void setColumn3(String column3) {
        this.column3 = column3;
    }

    public String getColumn4() {
        return column4;
    }

    public void setColumn4(String column4) {
        this.column4 = column4;
    }

    public String getColumn5() {
        return column5;
    }

    public void setColumn5(String column5) {
        this.column5 = column5;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareSubTitle() {
        return shareSubTitle;
    }

    public void setShareSubTitle(String shareSubTitle) {
        this.shareSubTitle = shareSubTitle;
    }

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategorys() {
        return subCategorys;
    }

    public void setSubCategorys(String subCategorys) {
        this.subCategorys = subCategorys;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getBusinessAreas() {
        return businessAreas;
    }

    public void setBusinessAreas(String businessAreas) {
        this.businessAreas = businessAreas;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<CmsArticleImgVo> getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(List<CmsArticleImgVo> bannerImg) {
        this.bannerImg = bannerImg;
    }

    public List<CmsArticleImgVo> getBodyImg() {
        return bodyImg;
    }

    public void setBodyImg(List<CmsArticleImgVo> bodyImg) {
        this.bodyImg = bodyImg;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getArticleSN() {
        return articleSN;
    }

    public void setArticleSN(String articleSN) {
        this.articleSN = articleSN;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
