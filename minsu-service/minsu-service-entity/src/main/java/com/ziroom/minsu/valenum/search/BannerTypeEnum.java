package com.ziroom.minsu.valenum.search;

/**
 * <p>Banner类型</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl
 * @version 1.0
 * @Date Created in 2017年09月08日 16:45
 * @since 1.0
 */
public enum BannerTypeEnum {

    TonightDiscountGuideBanner001("TonightDiscountGuideBanner001", "今夜特价引导BANNER", 3);

    /**
     * 类型
     */
    private String type;

    /**
     * 描述
     */
    private String desc;

    /**
     * 在目标列表中的默认位置，从0开始
     */
    private Integer index;

    BannerTypeEnum(String type, String desc, Integer index) {
        this.type = type;
        this.desc = desc;
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
