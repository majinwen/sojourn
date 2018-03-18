package com.zra.house.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

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
 * @date 2016/8/5 15:46
 * @since 1.0
 */
public class RoomPriceDetailDto extends PageDto {
    /**
     * 价格区间
     */
    @ApiModelProperty(value = "价格区间")
    private PriceDto priceDto;

    /**
     * 房间信息列表
     */
    @ApiModelProperty(value = "房间信息列表")
    private List<RoomDetailDto> roomDetailDtoList;

    //-----------------------for预约看房------------------------
    @ApiModelProperty(value = "项目名称")
    private String proName;
//    @ApiModelProperty(value = "项目经度")
//    private Double lng;
//    @ApiModelProperty(value = "项目纬度")
//    private Double lat;
    @ApiModelProperty(value = "项目地址")
    private String proAddr;
    @ApiModelProperty(value = "项目头图-for预约看房")
    private String proHeadPic;
    @ApiModelProperty(value = "币种标签")
    private String priceTag = "￥";
//    @ApiModelProperty(value = "项目最低价格-for预约看房")
//    private String minPrice;


    public String getPriceTag() {
        return priceTag;
    }

    public void setPriceTag(String priceTag) {
        this.priceTag = priceTag;
    }

    public PriceDto getPriceDto() {
        return priceDto;
    }

    public void setPriceDto(PriceDto priceDto) {
        this.priceDto = priceDto;
    }

    public List<RoomDetailDto> getRoomDetailDtoList() {
        return roomDetailDtoList;
    }

    public void setRoomDetailDtoList(List<RoomDetailDto> roomDetailDtoList) {
        this.roomDetailDtoList = roomDetailDtoList;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProAddr() {
        return proAddr;
    }

    public void setProAddr(String proAddr) {
        this.proAddr = proAddr;
    }

    public String getProHeadPic() {
        return proHeadPic;
    }

    public void setProHeadPic(String proHeadPic) {
        this.proHeadPic = proHeadPic;
    }
}
