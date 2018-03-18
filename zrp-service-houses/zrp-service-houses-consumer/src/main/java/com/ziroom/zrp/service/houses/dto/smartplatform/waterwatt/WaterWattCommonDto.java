package com.ziroom.zrp.service.houses.dto.smartplatform.waterwatt;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.zrp.service.houses.valenum.HouseTypeEnum;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>智能平台水电基础dto</p>
 * wiki-http://wiki.ziroom.com/pages/viewpage.action?pageId=341835936
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @version 1.0
 * @Date Created in 2018年01月15日
 * @since 1.0
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WaterWattCommonDto extends BaseEntity {

    /**
     * 收房合同/项目id
     */
    private String hireContractCode;

    /**
     * 设备维度1
     * 友家就是houseId 自如寓是项目id
     */
    private String positionRank1;

    /**
     * 设备维度2
     * 友家roomId 自如寓房间id
     */
    private String positionRank2;

    /**
     * 设备维度3
     */
    private String positionRank3;

    /**
     * 设备维度4
     */
    private String positionRank4;

    /**
     * 设备维度5
     */
    private String positionRank5;

    /**
     * 设备类型
     *
     * @see com.ziroom.zrp.service.houses.valenum.SmartPlatformWaterWattDeivceTypeEnum
     */
    private String deviceType;

    /**
     * 操作指令
     *
     * @see com.ziroom.zrp.service.houses.valenum.SmartPlatformWaterWattOperationMarkedEnum
     */
    private String operationMarked;

    /**
     * 房租类型
     * 1.友家 4.自如寓
     */
    private Integer houseType = HouseTypeEnum.ZYU.getCode();

}
