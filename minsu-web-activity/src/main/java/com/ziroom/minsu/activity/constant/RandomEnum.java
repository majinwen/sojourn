package com.ziroom.minsu.activity.constant;

/**
 * <p>随机活动配置</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/12/9.
 * @version 1.0
 * @since 1.0
 */
public enum RandomEnum {


    YUANDAN("CJHD17", "17年春节活动组号",20,100);


    RandomEnum(String code, String name, int chance,int base){
        this.code = code;
        this.name = name;
        this.chance = chance;
        this.base = base;
    }
    /** code */
    private String code;

    /** 名称 */
    private String name;

    /** 机会分子 */
    private int chance;

    /** 机会分母 */
    private int base;



    public String getCode() {
        return code;
    }


    public String getName() {
        return name;
    }


    public int getChance() {
        return chance;
    }

    public int getBase() {
        return base;
    }

    public static RandomEnum getByCode(String msgCode) {
        for (final RandomEnum code : RandomEnum.values()) {
            if (code.getCode().equals(msgCode)) {
                return code;
            }
        }
        return null;
    }



    /**
     * 获取当前的概率
     * @return
     */
    public  boolean checkRandomOk(){
        if (this.base < 1){
            return false;
        }
        int x=(int)(Math.random()*this.base);
        if (x < this.chance){
            return true;
        }
        return false;
    }


}
