package com.ziroom.minsu.valenum.productrules;


/**
 * <p>TODO</p>
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
public enum  ProductRulesEnum0019 {

    ProductRulesEnum0019001("ProductRulesEnum0019001","满七天配置天数",7,"满七天折扣"),
    ProductRulesEnum0019002("ProductRulesEnum0019002","满30天配置天数",30,"满30天折扣"),
    ProductRulesEnum0019003("ProductRulesEnum0019003","间隙日期的最大配置天数",2,"");

    private final String value;

    private final String name;
    /**
     * 天数
     */
    private final int dayNum;
    
    /**
     * troy 订单详情展示名称
     */
    private final String troyOrderShowName;

    ProductRulesEnum0019(String value,String name,int dayNum,String troyOrderShowName) {
        this.value = value;
        this.name = name;
        this.dayNum = dayNum;
        this.troyOrderShowName = troyOrderShowName;
    }
    
    
    /**
	 * @return the troyOrderShowName
	 */
	public String getTroyOrderShowName() {
		return troyOrderShowName;
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
        return ProductRulesEnum.ProductRulesEnum0019.getValue();
    }
    
	public static ProductRulesEnum0019 getEnumByCode(String value) {
		for (final ProductRulesEnum0019 productRulesEnum0019 : ProductRulesEnum0019.values()) {
			if (productRulesEnum0019.getValue().equals(value)) {
				return productRulesEnum0019;
			}
		}
		return null;
	}

}
