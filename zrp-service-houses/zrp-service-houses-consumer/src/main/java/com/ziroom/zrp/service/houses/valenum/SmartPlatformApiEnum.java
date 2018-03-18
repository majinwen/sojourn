package com.ziroom.zrp.service.houses.valenum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>智能平台接口枚举</p>
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
@AllArgsConstructor
public enum SmartPlatformApiEnum {
    SAVEHIRECONTRACT("/api/spaceInfo/saveHireContract", "新增智能房源"),
    ADDRENTCONTRACT("/api/rentContract/addRentContract", "新增智能平台出房合同"),
    BACKRENT("/api/rentContract/backRent", "退租"),
    CONTINUEABOUT("/api/rentContract/continueAbout", "续约"),
    CHANGEOCCUPANTS("/api/rentContract/changeOccupants", "更换入住人信息"),
    GETLOCKINFO("/api/intelligentLockB/getLockInfo", "获取智能锁设备状态"),
    ADDPASSWORD("/api/intelligentLockB/addPassword", "下发有效期密码"),
    TEMPORARYPASSWORD("/api/intelligentLockB/temporaryPassword", "获取临时密码"),
    EQUIPMENT("/api/operation/equipment", "水电表接口");

    @Getter
    private String api;

    @Getter
    private String desc;

}
