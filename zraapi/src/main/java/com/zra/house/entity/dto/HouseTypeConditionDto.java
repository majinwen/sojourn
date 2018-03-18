package com.zra.house.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/1 15:27
 * @since 1.0
 */
public class HouseTypeConditionDto {

    /**
     * 面积区间
     */
    @ApiModelProperty(value = "面积区间")
    private AreaDto area;
    /**
     * 价格
     */
    @ApiModelProperty(value = "价格区间")
    private PriceDto price;

    /**
     * 入住时间
     */
    @ApiModelProperty(value = "入住时间")
    private List<Map> checkInTime;

    /**
     * 朝向
     */
    @ApiModelProperty(value = "朝向")
    private List<Map> directionList;

    /**
     * 层数
     */
    @ApiModelProperty(value = "层数")
    private List<Map> floorNumberList;
    /**
     * 条件排序
     */
    @ApiModelProperty(value = "条件排序")
    private List<Map> sortList;

    public List<Map> getSortList() {
        return sortList;
    }

    public void setSortList(List<Map> sortList) {
        this.sortList = sortList;
    }

    public AreaDto getArea() {
        return area;
    }

    public void setArea(AreaDto area) {
        this.area = area;
    }

    public PriceDto getPrice() {
        return price;
    }

    public void setPrice(PriceDto price) {
        this.price = price;
    }

    public List<Map> getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(List<Map> checkInTime) {
        this.checkInTime = checkInTime;
    }

    public List<Map> getDirectionList() {
        return directionList;
    }

    public void setDirectionList(List<Map> directionList) {
        this.directionList = directionList;
    }

    public List<Map> getFloorNumberList() {
        return floorNumberList;
    }

    public void setFloorNumberList(List<Map> floorNumberList) {
        this.floorNumberList = floorNumberList;
    }
}
