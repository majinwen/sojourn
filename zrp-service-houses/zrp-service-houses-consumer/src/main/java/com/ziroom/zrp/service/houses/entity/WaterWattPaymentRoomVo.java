package com.ziroom.zrp.service.houses.entity;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.zrp.service.houses.valenum.MeterTypeEnum;
import com.ziroom.zrp.service.houses.valenum.RoomStatusEnum;
import com.ziroom.zrp.service.houses.valenum.SmartPlatformWaterWattPayTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>智能水电表Vo</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author phil
 * @Date Created in 2018年02月04日 19:36
 * @Version 1.0
 * @Since 1.0
 */
@Data
public class WaterWattPaymentRoomVo extends BaseEntity {


    private static final long serialVersionUID = 1365560359943281706L;
    /**
     * 项目id
     */
    @ApiModelProperty(notes = "项目Id")
    private String projectId;

    /**
     * 项目名
     */
    @ApiModelProperty(notes = "项目名")
    private String projectName;

    /**
     * 房间id
     */
    @ApiModelProperty(notes = "房间Id")
    private String roomId;

    /**
     * 付费模式值
     */
    @ApiModelProperty(value = "收费/付费模式")
    private int payType;

    /**
     * 付费模式名称
     */
    @ApiModelProperty(value = "付费模式名称, 1:预付费, 2:后付费")
    private String payTypeCn;

    /**
     * 楼栋id
     */
    @ApiModelProperty(notes = "楼栋Id")
    private String buildingId;

    /**
     * 楼栋名
     */
    @ApiModelProperty(notes = "楼栋名称")
    private String buildingName;

    /**
     * 楼层数
     */
    @ApiModelProperty(notes = "楼层数")
    private String floorNumber;

    /**
     * 房间号
     */
    @ApiModelProperty(notes = "房间号")
    private String roomNumber;

    /**
     * 房间状态
     */
    @ApiModelProperty(notes = "房间状态:0：待租中；1：已出租；2：配置中；3已下定；4：锁定；5：已下架；6：预定进行中；7：签约进行中；8：可预订")
    private Integer roomStatus;

    /**
     * 房间状态中文
     */
    @ApiModelProperty(notes = "房间状态中文")
    private String roomStatusCn;

    /**
     * 设备类型
     */
    @ApiModelProperty(value = "设备类型名称, 0:水表, 1:电表")
    private Integer deviceType;

    /**
     * 设备类型名称
     */
    @ApiModelProperty(value = "设备类型")
    private String deviceTypeCn;

    /**
     * 支付类型中文
     * @return
     */
    public String getPayTypeCn() {
        return payTypeCn = SmartPlatformWaterWattPayTypeEnum.getNameByZyuCode(payType);
    }

    public String getRoomStatusCn() {
        RoomStatusEnum statusEnum = RoomStatusEnum.getRoomStatusEnumByCode(String.valueOf(roomStatus));
        return statusEnum != null? statusEnum.getName() : "";
    }

    public String getDeviceTypeCn() {
        MeterTypeEnum typeEnum = MeterTypeEnum.valueOf(deviceType);
        return typeEnum != null? typeEnum.getMachineName() : "";
    }
}
