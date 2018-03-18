package com.zra.m.entity.dto;

import com.zra.house.entity.dto.HouseTypeDetailDto;
import com.zra.house.entity.dto.RoomPriceDetailDto;
import io.swagger.annotations.ApiModelProperty;

/**
 * Author: wangxm113
 * CreateDate: 2017/6/20.
 */
public class MRoomDto {
    @ApiModelProperty(value = "配置信息")
    private HouseTypeDetailDto houseTypeDetailDto;
    @ApiModelProperty(value = "房间信息")
    private RoomPriceDetailDto roomPriceDetailDto;

    public HouseTypeDetailDto getHouseTypeDetailDto() {
        return houseTypeDetailDto;
    }

    public void setHouseTypeDetailDto(HouseTypeDetailDto houseTypeDetailDto) {
        this.houseTypeDetailDto = houseTypeDetailDto;
    }

    public RoomPriceDetailDto getRoomPriceDetailDto() {
        return roomPriceDetailDto;
    }

    public void setRoomPriceDetailDto(RoomPriceDetailDto roomPriceDetailDto) {
        this.roomPriceDetailDto = roomPriceDetailDto;
    }
}
