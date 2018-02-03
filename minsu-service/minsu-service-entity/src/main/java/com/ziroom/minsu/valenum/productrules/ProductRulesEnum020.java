package com.ziroom.minsu.valenum.productrules;

import com.asura.framework.base.util.Check;

import java.util.List;

/**
 * <p>折扣</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/12/2.
 * @version 1.0
 * @since 1.0
 */
public enum ProductRulesEnum020 {

    ProductRulesEnum020001("ProductRulesEnum020001","当天空置间夜自动定价折扣",0),
    ProductRulesEnum020002("ProductRulesEnum020002","间隔一天空置间夜自动定价折扣",1),
    ProductRulesEnum020003("ProductRulesEnum020003","间隔两天空置间夜自动定价折扣",2);

    private final String value;

    private final String name;

    /**
     * 间隔天数
     */
    private final int dayNum;

    ProductRulesEnum020(String value, String name, int dayNum) {
        this.value = value;
        this.name = name;
        this.dayNum = dayNum;
    }
    /**
	 * @return the dayNum
	 */
	public int getDayNum() {
		return dayNum;
	}


	public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getParentValue(){
        return ProductRulesEnum.ProductRulesEnum020.getValue();
    }



    /**
     * 获取 ProductRulesEnum020
     * @param dayNum
     * @return
     */
    public static ProductRulesEnum020 getByDayNum(int dayNum) {
      
        for (final ProductRulesEnum020 rule : ProductRulesEnum020.values()) {
            if (rule.getDayNum() == dayNum) {
                return rule;
            }
        }
        return null;
    }
    
    
    /**
     * 获取
     * @param code
     * @return
     */
    public static ProductRulesEnum020 getByCode(String code) {
        if (Check.NuNObj(code)){
            return null;
        }
        for (final ProductRulesEnum020 rule : ProductRulesEnum020.values()) {
            if (rule.getValue().equals(code)) {
                return rule;
            }
        }
        return null;
    }

    /**
     * 最大的夹缝价格天数
     * @author afi
     * @return
     */
    public static Integer getMaxDay(List<String> codes){
        Integer maxDay = null;
        if (Check.NuNCollection(codes)){
            return maxDay;
        }
        for (String code:codes){
            ProductRulesEnum020 rule = ProductRulesEnum020.getByCode(code);
            if (Check.NuNObj(rule)){
                continue;
            }
            if (Check.NuNObj(maxDay)){
                maxDay = rule.getDayNum();
            }else if (rule.getDayNum() > maxDay){
                maxDay = rule.getDayNum();
            }
        }
        return maxDay;
    }

    /**
     * 获取第一天的夹缝价格
     * @author afi
     * @return
     */
    public static ProductRulesEnum020 getFristRule(List<String>  codes){

        if (Check.NuNCollection(codes)){
            return null;
        }
        for (String code:codes){
            ProductRulesEnum020 rule = ProductRulesEnum020.getByCode(code);
            if (Check.NuNObj(rule)){
                continue;
            }
            if (rule.getDayNum() == 0){
                return rule;
            }
        }
        return null;
    }

}
