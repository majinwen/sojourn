package com.ziroom.zrp.service.houses.entity.smartplatform.waterwatt;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.zrp.houses.entity.RoomInfoExtEntity;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>智能水电信息vo</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2018年3月5日 20:40
 * @since 1.0
 */
@Data
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class WaterElectricityInfoVo extends BaseEntity {

    /**
     * 房间扩展信息
     */
    private RoomInfoExtEntity roomInfoExtEntity;

    /**
     * 水表详情vo
     */
    private WaterMeterStateVo waterMeterStateVo;

    /**
     * 水表示数
     */
    private Double waterReading;

}
