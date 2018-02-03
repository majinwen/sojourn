package com.ziroom.minsu.services.search.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.util.DateUtil;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;
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
 * @Date Created in 2017年07月31日 09:30
 * @since 1.0
 */
public class CmsArticleInfo extends BaseEntity {

    @Field
    private String id;

    /** 文章编号 */
    @Field
    private String articleSn;

    /** 城市code */
    @Field
    private String cityCode;

    /** 城市名称 */
    @Field
    private String cityName;

    /** 区域code */
    @Field
    private String areaCode;

    /** 区域名称 */
    @Field
    private String areaName;

    /** 文章标题 */
    @Field
    private String title;

    /** 文章副标题 */
    @Field
    private String subTitle;

    /** 摘要 */
    @Field
    private String summary;

    /** 分享标题 */
    @Field
    private String shareTitle;

    /** 分享副标题 */
    @Field
    private String shareSubTitle;

    /** 分享图片 */
    @Field
    private String shareImg;

    /** 分类 */
    @Field
    private String category;

    /** 子分类 */
    @Field
    private String subCategorys;

    /** 跳转url */
    @Field
    private String targetUrl;

    /** 坐标 */
    @Field
    private String location;

    /** 地址 */
    @Field
    private String address;

    /** 电话 */
    @Field
    private String tel;

    /** 营业时间 */
    @Field
    private String openingHours;

    /** 价格 */
    @Field
    private String price;

    /** 内容 */
    @Field
    private String body;


    /** 文章板式 */
    @Field
    private String articleType;

    /** 内容标签 */
    @Field
    private Set<String> contentTags;

    /** 精选标签 */
    @Field
    private Set<String> tags;

    /** 商圈 */
    @Field
    private Set<String> businessAreas;

    /** 主图 */
    @Field
    private Set<String> bannerImg;

    /** 其他图片 */
    @Field
    private Set<String> bodyImg;

    /** 更新时间 */
    @Field
    private Long updateTime;

    /** 创建时间 */
    @Field
    private Long createdTime;

    /** 权重 */
    @Field
    private Long weights;

    /** 创建时间 */
    @Field
    private String createTime = DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");

    /** 随机数 */
    @Field
    private Integer randNum;

    /** 背景色 */
    @Field
    private String backgroundColor;

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Integer getRandNum() {
        return randNum;
    }

    public void setRandNum(Integer randNum) {
        this.randNum = randNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticleSn() {
        return articleSn;
    }

    public void setArticleSn(String articleSn) {
        this.articleSn = articleSn;
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

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public Set<String> getContentTags() {
        return contentTags;
    }

    public void setContentTags(Set<String> contentTags) {
        this.contentTags = contentTags;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Set<String> getBusinessAreas() {
        return businessAreas;
    }

    public void setBusinessAreas(Set<String> businessAreas) {
        this.businessAreas = businessAreas;
    }

    public Set<String> getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(Set<String> bannerImg) {
        this.bannerImg = bannerImg;
    }

    public Set<String> getBodyImg() {
        return bodyImg;
    }

    public void setBodyImg(Set<String> bodyImg) {
        this.bodyImg = bodyImg;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getWeights() {
        return weights;
    }

    public void setWeights(Long weights) {
        this.weights = weights;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
