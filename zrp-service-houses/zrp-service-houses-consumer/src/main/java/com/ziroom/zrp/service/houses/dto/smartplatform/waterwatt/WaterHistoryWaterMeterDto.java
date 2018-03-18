package com.ziroom.zrp.service.houses.dto.smartplatform.waterwatt;

import com.ziroom.zrp.service.houses.valenum.SmartPlatformWaterTypeEnum;
import com.ziroom.zrp.service.houses.valenum.SmartPlatformWaterWattDeivceTypeEnum;
import com.ziroom.zrp.service.houses.valenum.SmartPlatformWaterWattOperationMarkedEnum;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>B端水表抄表历史</p>
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
public class WaterHistoryWaterMeterDto extends WaterWattCommonDto {

    /**
     * B端水表抄表历史
     */
    public WaterHistoryWaterMeterDto(){
        this.setDeviceType(SmartPlatformWaterWattDeivceTypeEnum.WATER_METER.getCode());
        this.setOperationMarked(SmartPlatformWaterWattOperationMarkedEnum.HISTORYWATERMETER.getCode());
    }

    /**
     * 水表类型（1冷水表，2热水表）
     * 目前默认冷水表
     * @see com.ziroom.zrp.service.houses.valenum.SmartPlatformWaterTypeEnum
     */
    private Integer type = SmartPlatformWaterTypeEnum.COLD_WATER_METER.getCode();

    /**
     * 获取个数，默认为10
     */
    private Integer count;

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
