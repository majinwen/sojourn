package com.ziroom.zrp.service.trading.entity;

import com.asura.framework.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zra.common.utils.DateUtilFormate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@ApiModel(value = "抄表管理-列表页-对象")
public class IntellectWatermeterReadVo extends BaseEntity {

    @ApiModelProperty(value = "抄表记录fid")
    private String readingFid;

    @ApiModelProperty(value = "项目id")
    private String projectFid;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "楼栋")
    private String buildingName;

    @ApiModelProperty(value = "楼层")
    private Integer floorNumber;

    @ApiModelProperty(value = "房间号")
    private String roomNumber;

    @ApiModelProperty(value = "房间id")
    private String roomFid;

    @ApiModelProperty(value = "设备类型：0水 1电")
    private Integer deivceType;

    @ApiModelProperty(value = "设备类型名称")
    private String deviceTypeCn;

    @ApiModelProperty(value = "抄表示数")
    private Double meterReading;

    @ApiModelProperty(value = "抄表时间")
    @JsonFormat(pattern = DateUtilFormate.DATEFORMAT_5)
    private Date readTime;

    @ApiModelProperty(value = "处理状态：0已处理 1未处理")
    private Integer readStatus;

    @ApiModelProperty(value = "处理状态名称")
    private String readStatusCn;

    @ApiModelProperty(value = "上期抄表示数")
    private double preMeterReading;

    @ApiModelProperty(value = "上期抄表时间")
    @JsonFormat(pattern = DateUtilFormate.DATEFORMAT_5)
    private Date preReadTime;

    @ApiModelProperty(value = "处理人")
    private String handleName;

    @ApiModelProperty(value = "处理时间")
    @JsonFormat(pattern = DateUtilFormate.DATEFORMAT_5)
    private Date handleTime;



}
