package com.ziroom.zrp.service.houses.dto.smartplatform;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.zrp.service.houses.valenum.HouseTypeEnum;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>新增智能平台出房合同</p>
 * wiki-http://wiki.ziroom.com/pages/viewpage.action?pageId=337674251
 * <p>
 * <PRE>
 * <BR>    修改记录 
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2017年12月12日
 * @version 1.0
 * @since 1.0
 */
@Data
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class SmartPlatformAddRentContractDto extends BaseEntity {

    /**
     * 出房合同号
     */
    private String rentContractCode;

    /**
     * 收房合同/项目id
     */
    private String hireContractCode;

    /**
     * 出房合同/生效日期
     */
    private String rentContractStartDate;

    /**
     * 出房合同/失效日期
     */
    private String rentContractEndDate;

    /**
     * 用户为一般标识
     */
    private String uid;

    /**
     * 项目名
     */
    private String village;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户电话
     */
    private String userPhone;

    /**
     * 公司code
     */
    private String cityCode;

    /**
     * 公司name
     */
    private String cityName;

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

    /**
     * 房间号
     */
    private String roomCode;

}
