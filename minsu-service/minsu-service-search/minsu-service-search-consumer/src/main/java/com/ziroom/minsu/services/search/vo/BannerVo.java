package com.ziroom.minsu.services.search.vo;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>Banner对象</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl
 * @version 1.0
 * @Date Created in 2017年09月08日 16:49
 * @since 1.0
 */
public class BannerVo extends BaseEntity {

    private static final long serialVersionUID = -7874596355395150397L;
    /**
     * banner类型
     *
     * @see com.ziroom.minsu.valenum.search.BannerTypeEnum
     */
    private String bannerType;

    /**
     * banner文案Title
     */
    private String bannerTitle;

    /**
     * banner文案Subtitle
     */
    private String bannerSubtitle;

    /**
     * banner图片
     */
    private String bannerImg;

    /**
     * 在目标列表中的位置，从0开始
     *
     * @see com.ziroom.minsu.valenum.search.BannerTypeEnum
     */
    private Integer bannerIndex;

    public String getBannerType() {
        return bannerType;
    }

    public void setBannerType(String bannerType) {
        this.bannerType = bannerType;
    }

    public String getBannerTitle() {
        return bannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    public String getBannerSubtitle() {
        return bannerSubtitle;
    }

    public void setBannerSubtitle(String bannerSubtitle) {
        this.bannerSubtitle = bannerSubtitle;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public Integer getBannerIndex() {
        return bannerIndex;
    }

    public void setBannerIndex(Integer bannerIndex) {
        this.bannerIndex = bannerIndex;
    }
}
