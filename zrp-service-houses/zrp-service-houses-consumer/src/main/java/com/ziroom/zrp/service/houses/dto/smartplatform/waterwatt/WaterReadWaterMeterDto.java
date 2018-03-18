package com.ziroom.zrp.service.houses.dto.smartplatform.waterwatt;

import com.ziroom.zrp.service.houses.valenum.SmartPlatformWaterWattDeivceTypeEnum;
import com.ziroom.zrp.service.houses.valenum.SmartPlatformWaterWattOperationMarkedEnum;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>B端水表抄表(异步)</p>
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
public class WaterReadWaterMeterDto extends WaterWattCommonDto {

    /**
     * B端水表抄表(异步)
     */
    public WaterReadWaterMeterDto(){
        this.setDeviceType(SmartPlatformWaterWattDeivceTypeEnum.WATER_METER.getCode());
        this.setOperationMarked(SmartPlatformWaterWattOperationMarkedEnum.READWATERMETER.getCode());
    }

}
