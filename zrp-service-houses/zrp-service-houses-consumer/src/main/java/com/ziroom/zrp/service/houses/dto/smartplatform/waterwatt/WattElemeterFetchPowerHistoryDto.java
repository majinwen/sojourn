package com.ziroom.zrp.service.houses.dto.smartplatform.waterwatt;

import com.ziroom.zrp.service.houses.valenum.SmartPlatformWaterWattDeivceTypeEnum;
import com.ziroom.zrp.service.houses.valenum.SmartPlatformWaterWattOperationMarkedEnum;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>B端获取智能电表设备充值记录</p>
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
public class WattElemeterFetchPowerHistoryDto extends WaterWattCommonDto {

    /**
     * B端获取智能电表设备充值记录
     */
    public WattElemeterFetchPowerHistoryDto(){
        this.setDeviceType(SmartPlatformWaterWattDeivceTypeEnum.WATT_METER.getCode());
        this.setOperationMarked(SmartPlatformWaterWattOperationMarkedEnum.ELEMETERFETCHPOWERHISTORY.getCode());
    }

    /**
     * 开始时间
     * yyyy-mm-dd hh:mm:ss
     */
    private String permissionBegin;

    /**
     * 结束时间
     * yyyy-mm-dd hh:mm:ss
     */
    private String permissionEnd;

}
