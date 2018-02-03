package com.ziroom.minsu.services.search.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

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
 * @Date Created in 2017年08月09日 16:54
 * @since 1.0
 */
public class HouseListSimpleVo extends BaseEntity {


    private static final long serialVersionUID = -8478481986918260323L;


    /** 房源或者房间fid */
    private String fid;

    /** 名称 */
    private String houseName;

    /** 房屋类型 整租 合租 */
    private Integer rentWay;

    /** 图片 */
    private String picUrl;

    /** 价格 */
    private Integer price;

    /** 真实评价得分 */
    private Double realEvaluateScore;

    /**
     * 是否top50已经上线的房源
     */
    private Integer isTop50Online= YesOrNoEnum.NO.getCode();


    public Integer getIsTop50Online() {
        return isTop50Online;
    }

    public void setIsTop50Online(Integer isTop50Online) {
        this.isTop50Online = isTop50Online;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Double getRealEvaluateScore() {
        return realEvaluateScore;
    }

    public void setRealEvaluateScore(Double realEvaluateScore) {
        this.realEvaluateScore = realEvaluateScore;
    }
}
