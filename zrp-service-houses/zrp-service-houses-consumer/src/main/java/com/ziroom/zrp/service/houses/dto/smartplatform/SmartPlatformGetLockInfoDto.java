package com.ziroom.zrp.service.houses.dto.smartplatform;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.zrp.service.houses.valenum.HouseTypeEnum;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>获取智能锁设备状态</p>
 * wiki-http://wiki.ziroom.com/pages/viewpage.action?pageId=337674251
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @version 1.0
 * @Date Created in 2017年12月12日
 * @since 1.0
 */
@Data
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class SmartPlatformGetLockInfoDto extends BaseEntity {

    /**
     * 收房合同/项目id
     */
    private String hireContractCode;

    /**
     * 操作人姓名
     * 操作人，内部员工，名称
     */
    private String opsUser;

    /**
     * 操作人标识
     * 操作人，内部员工，工号
     */
    private String opsUserIdentifier;

    /**
     * 操作人类型
     * 操作人类型(1.自如客 2.自如员工 3.第三方)
     */
    private String opsUserType;

    /**
     * 房屋类型
     * 1.友家 4.自如寓
     */
    private Integer houseType = HouseTypeEnum.ZYU.getCode();

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


}
