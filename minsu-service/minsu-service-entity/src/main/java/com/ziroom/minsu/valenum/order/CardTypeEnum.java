package com.ziroom.minsu.valenum.order;

/**
 * <p>证件类型</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/9/18.
 * @version 1.0
 * @since 1.0
 */
public enum CardTypeEnum {

    //0=其他 1=身份证 2=护照 3=军官证 4=通行证 5=驾驶证 6=台胞证 7=社保卡 8=省份证 9=社保卡 10=学生证 11=回乡证 12=营业执照 13=港澳通行证 14户口本 15=居住证 16=港,澳,台居民身份证
    SHENFENZHENG(1,"身份证"),
    HUZHAO(2,"护照"),
    JUNGUAN(3,"军官证"),
    TONGXING(4,"通行证"),
    JIASHI(5,"驾驶证"),
    TAIBAO(6,"台胞证"),
    SHEBAO(7,"社保卡"),
    SHENGFEN(8,"省份证"),
    SHEBAOKA(9,"社保卡"),
    XUESHENG(10,"学生证"),
    HUIXIANG(11,"回乡证"),
    YENGYEZHIZHAO(12,"营业执照"),
    GANGAO(13,"港澳通行证"),
    HUKOUBEN(14,"户口本"),
    JUZHUZHENG(15,"居住证"),
    GAT_ID(16,"港,澳,台居民身份证");
    CardTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;

    }

    /**
     * 通过code获取值
     * @param code
     * @return
     */
    public static CardTypeEnum getByCode(int code){
        for(final CardTypeEnum cardTypeEnum : CardTypeEnum.values()){
            if(cardTypeEnum.getCode() == code){
                return cardTypeEnum;
            }
        }
        return null;
    }

    /** code */
    private int code;

    /** 名称 */
    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
