package com.ziroom.minsu.valenum.productrules;

/**
 * <p>优惠政策</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/9.
 * @version 1.0
 * @since 1.0
 */
public enum ProductRulesEnum0012Enum {

    ProductRulesEnum0012001("ProductRulesEnum0012001","三天折扣率"){
        @Override
        public Integer getDay() {
            return 3;
        }
    },
    ProductRulesEnum0012002("ProductRulesEnum0012002","七天折扣率"){
        @Override
        public Integer getDay() {
            return 7;
        }
    },
    ProductRulesEnum0012003("ProductRulesEnum0012003","30天折扣率"){
        @Override
        public Integer getDay() {
            return 30;
        }
    };

    private final String value;

    private final String name;

    ProductRulesEnum0012Enum(String value,String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }


    public Integer getDay(){
        return null;
    }
}
