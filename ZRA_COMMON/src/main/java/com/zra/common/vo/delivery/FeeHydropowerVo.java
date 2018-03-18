package com.zra.common.vo.delivery;

import com.zra.common.vo.base.BaseFieldVo;

/**
 * <p>水电费用项</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月25日 15:42
 * @since 1.0
 */
public class FeeHydropowerVo extends PayFieldVo{

    private String unit;

    private String number;

    private String picUrl;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
