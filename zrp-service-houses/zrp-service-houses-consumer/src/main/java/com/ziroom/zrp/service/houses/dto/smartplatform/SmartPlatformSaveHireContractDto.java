package com.ziroom.zrp.service.houses.dto.smartplatform;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.zrp.service.houses.valenum.HouseTypeEnum;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>智能平台新增收房合同信息及设备列表信息接口</p>
 * wiki-http://wiki.ziroom.com/pages/viewpage.action?pageId=333348876
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
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SmartPlatformSaveHireContractDto extends BaseEntity {

    /**
     * 收房合同/项目id
     */
    private String hireContractCode;

    /**
     * 房屋编号/项目id
     */
    private String houseCode;

    /**
     * 房源编号/项目名称
     */
    private String houseSourceCode;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 城市代码
     */
    private String cityCode;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 来源
     */
    private Integer houseType = HouseTypeEnum.ZYU.getCode();

    /**
     * 合同起始时间
     * "yyyy-MM-dd hh:mm:ss"
     */
    private String hireContractStartTime;

    /**
     * 合同中止时间
     * "yyyy-MM-dd hh:mm:ss"
     */
    private String hireContractEndTime;

    /**
     * 设备列表
     */
    private List<Device> deviceList = new ArrayList<>();

    public boolean addDevice(Device device) {
        return deviceList.add(device);
    }

    @Data
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public class Device extends BaseEntity {
        /**
         * 设备类型
         */
        private String deviceType;

        /**
         * 设备详细位置
         */
        private String positionName;

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
         * 设备所属区域名称
         * 如:201房间
         */
        private String positionRankName;

    }

}
