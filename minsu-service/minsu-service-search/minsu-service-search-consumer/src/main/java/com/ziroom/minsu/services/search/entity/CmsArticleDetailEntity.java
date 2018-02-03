package com.ziroom.minsu.services.search.entity;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.util.DateUtil;
import com.ziroom.minsu.services.search.vo.CmsArticleImgVo;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zl
 * @version 1.0
 * @Date Created in 2017年08月02日 16:28
 * @since 1.0
 */
public class CmsArticleDetailEntity  extends BaseEntity {


    private static final long serialVersionUID = 1563893258029249529L;

    private String id;
    private String title;
    private String subTitle;
    private String summary;
    private String shareTitle;
    private String shareSubTitle;
    private String shareImg;
    private String targetUrl;
    private String cityCode;
    private String cityName;
    private String category;
    private String subCategorys;
    private List<String> businessAreas;
    private String location;
    private String address;
    private String tel;
    private String openingHours;
    private String price;
    private List<String> tags;
    private String body;
    private List<CmsArticleImgVo> bannerImg;
    private List<CmsArticleImgVo> bodyImg;
    private String articleSN;
    private String createdTime;
    private String backgroundColor;

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
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

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
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

    public List<String> getBusinessAreas() {
        return businessAreas;
    }

    public void setBusinessAreas(List<String> businessAreas) {
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
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

    public String getArticleSN() {
        return articleSN;
    }

    public void setArticleSN(String articleSN) {
        this.articleSN = articleSN;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
