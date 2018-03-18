package com.ziroom.zrp.service.trading.dto.finance;

import java.io.Serializable;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年11月27日 10:59
 * @since 1.0
 */

public class CmsCommonRequestDto {

    /**
     * 被授权使用卡券系统接口系统编号（卡券系统系统统一提供）
     */
    private Integer auth_code;

    /**
     * 签名字符串
     */
    private String sign;

    /**
     * 客户端当前时间戳（长度10位，毫秒13位的前10位）
     */
    private Integer timestamp;

    public Integer getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(Integer auth_code) {
        this.auth_code = auth_code;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }
}
