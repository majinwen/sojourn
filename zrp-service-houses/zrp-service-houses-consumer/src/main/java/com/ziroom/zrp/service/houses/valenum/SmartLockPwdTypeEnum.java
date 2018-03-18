package com.ziroom.zrp.service.houses.valenum;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * <p>密码锁类型</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author phil
 * @Date Created in 2017年12月11日 20:09
 * @Version 1.0
 * @Since 1.0
 */

@AllArgsConstructor
public enum SmartLockPwdTypeEnum{

    PWD_NORMAL(1,"普通密码"),
    PWD_DYNAMIC(2,"动态密码");

    @Getter
    private int code;

    @Getter
    private String val;

    public static SmartLockPwdTypeEnum valueOf(Integer type) {
        for (SmartLockPwdTypeEnum e : SmartLockPwdTypeEnum.values()
             ) {
            if (e.getCode() == type) {
                return e;
            }
        }
        return null;
    }

}
