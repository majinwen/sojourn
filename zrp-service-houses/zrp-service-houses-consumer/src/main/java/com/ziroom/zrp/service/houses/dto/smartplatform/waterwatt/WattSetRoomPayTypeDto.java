package com.ziroom.zrp.service.houses.dto.smartplatform.waterwatt;

import com.ziroom.zrp.service.houses.valenum.SmartPlatformWaterWattDeivceTypeEnum;
import com.ziroom.zrp.service.houses.valenum.SmartPlatformWaterWattOperationMarkedEnum;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>B端设置房间电表付费模式</p>
 * wiki-http://wiki.ziroom.com/pages/viewpage.action?pageId=341835936
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2018年01月15日
 * @version 1.0
 * @since 1.0
 */
@Data
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class WattSetRoomPayTypeDto extends WaterWattCommonDto {

    /**
     * B端设置房间电表付费模式
     */
    public WattSetRoomPayTypeDto(){
        this.setDeviceType(SmartPlatformWaterWattDeivceTypeEnum.WATT_METER.getCode());
        this.setOperationMarked(SmartPlatformWaterWattOperationMarkedEnum.SETROOMPAYTYPE.getCode());
    }

    /**
     * 透支额度
     * 设置成预付费时想要设置的透支额度（大于0），
     * 未传的话则按如下优先级取值：
     * 房源设备设置里的透支额度 > 商户设备设置里的透支额度 > 10
     */
    private Integer overdraft;

    /**
     * 付费模式
     * 付费模式，预付费1，后付费2
     * @see com.ziroom.zrp.service.houses.valenum.SmartPlatformWaterWattPayTypeEnum
     */
    private Integer payType;

}
