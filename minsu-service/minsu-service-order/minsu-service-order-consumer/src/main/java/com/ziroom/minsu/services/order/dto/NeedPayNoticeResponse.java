package com.ziroom.minsu.services.order.dto;

/**
 * <p>注意事项守则</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/12/5 18:26
 * @version 1.0
 * @since 1.0
 */
public class NeedPayNoticeResponse {

    /**
     * 注意事项code
     */
    Integer code;

    /**
     * 注意事项名称
     */
    String name;

    /**
     * 是否可点击
     */
    Integer clickType = 0;

    /**
     * 排序
     */
    Integer index;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }


    public Integer getClickType() {
        return clickType;
    }

    public void setClickType(Integer clickType) {
        this.clickType = clickType;
    }
}
