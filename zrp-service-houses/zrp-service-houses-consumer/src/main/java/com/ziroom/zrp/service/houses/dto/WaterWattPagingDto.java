package com.ziroom.zrp.service.houses.dto;

import com.ziroom.zrp.houses.entity.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>智能水电表充值列表</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author phil
 * @Date Created in 2018年02月04日 19:21
 * @Version 1.0
 * @Since 1.0
 */
@Data
public class WaterWattPagingDto extends PageRequest {

    private static final long serialVersionUID = -7015332716650358951L;

    @ApiModelProperty(notes = "项目Id")
    @ApiParam(value = "项目Id")
    private String projectId;

    @ApiModelProperty(notes = "房间Id")
    @ApiParam("房间Id")
    private String roomId;

    @ApiModelProperty(notes = "设备类型：0-水、1-电")
    @ApiParam(value = "设备类型：0-水、1-电", defaultValue = "0")
    private Integer deviceType;

    @ApiModelProperty(notes = "房间状态值")
    @ApiParam("房间状态值")
    private Integer roomStatus;

    @ApiModelProperty(notes = "房间号")
    @ApiParam("房间号")
    private String roomNumber;

    @ApiModelProperty(hidden = true)
    private List<String> projectIds = new ArrayList<>();

}
