package com.ziroom.zrp.service.trading.dto.waterwatt;

import com.asura.framework.base.entity.BaseEntity;
import lombok.Data;

/**
 * <p>修改房间内的智能电表付费方式dto</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author lusp
 * @Date Created in 2018年03月14日
 * @version 1.0
 * @since 1.0
 */
@Data
public class ModifyWattPayTypeDto extends BaseEntity{

    /**
     * 房间id
     */
    private String roomId;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 付费模式
     * 付费模式，预付费1，后付费2
     * @see com.ziroom.zrp.service.houses.valenum.SmartPlatformWaterWattPayTypeEnum
     */
    private Integer payType;
}
