/**
 * @FileName: ProductRulesEnum.java
 * @Package com.ziroom.minsu.valenum.productrules
 * 
 * @author bushujie
 * @created 2016年4月3日 下午3:27:51
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.house;

/**
 * <p>产品属性规则枚举</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public enum HouseConfStyleEnum {
	
	DIANSHI("ProductRulesEnum0020011","S_item_dianshi","电视"),
	KONGTIAO("ProductRulesEnum0020012","S_item_kongtiao","空调"),
    YINSHUI("ProductRulesEnum0020013","S_item_yinshui","饮水设备"),
    BINGXIANG("ProductRulesEnum0020014","S_item_bingxiang","冰箱"),
    XIYIJI("ProductRulesEnum0020015","S_item_xiyiji","洗衣机"),
    HONGGANJI("ProductRulesEnum0020016","S_item_hongganji","烘干机"),
    DIANCHUIFENG("ProductRulesEnum0020017","S_item_dianchuifeng","电吹风"),
    LINYU("ProductRulesEnum0020021","S_item_linyu","热水淋浴"),
    YUGANG("ProductRulesEnum0020022","S_item_yugang","热水浴缸"),
    YAJU("ProductRulesEnum0020023","S_item_yaju","牙具"),
    XIANGZAO("ProductRulesEnum0020024","S_item_xiangzao","香皂"),
    TUOXIE("ProductRulesEnum0020025","S_item_tuoxie","拖鞋"),
    SHOUZHI("ProductRulesEnum0020026","S_item_shouzhi","手纸"),
    MAOJIN("ProductRulesEnum0020027","S_item_maojin","毛巾"),
    MUYULU("ProductRulesEnum0020028","S_item_xifalu","沐浴露/洗发露"),
    SHAFA("ProductRulesEnum0020031","S_item_shafa","沙发"),
    WUXIAN("ProductRulesEnum0020032","S_item_wuxian","无线网络"),
    YOUXIAN("ProductRulesEnum0020033","S_item_youxian","有线网络"),
    NUANQI("ProductRulesEnum0020034","S_item_nuanqi","暖气"),
    DIANTI("ProductRulesEnum0020035","S_item_dianti","电梯"),
    MENJIN("ProductRulesEnum0020036","S_item_menjin","门禁系统"),
    SHUZHUO("ProductRulesEnum0020037","S_item_shuzhuo","书桌"),
    ZAOCAN("ProductRulesEnum00151","S_item_zaocan","早餐"),
    WANCAN("ProductRulesEnum00152","S_item_wancan","家庭晚餐"),
    JIJIUBAO("ProductRulesEnum00153","S_item_jijiubao","急救包"),
    TINGCHEWEI("ProductRulesEnum00154","S_item_tingchewei","停车位"),
	XIYAN("ProductRulesEnum00155","S_item_xiyan","允许吸烟"),
	ZUOFAN("ProductRulesEnum00156","S_item_zuofan","可以做饭"),
	CHONGWU("ProductRulesEnum00157","S_item_chongwu","可以携带宠物"),
	JIEJI("ProductRulesEnum00158","S_item_xifalu","免费接机/接站");

	private final String value;
	
	private final String styleCss;

    private final String name;

    HouseConfStyleEnum(String value,String styleCss, String name) {
        this.value = value;
        this.name = name;
        this.styleCss=styleCss;
    }




	public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
    
	/**
	 * @return the styleCss
	 */
	public String getStyleCss() {
		return styleCss;
	}
	
    public static String getStyleCssByValue(String value){
    	for(final HouseConfStyleEnum ose : HouseConfStyleEnum.values()){
    		if(ose.getValue().equals(value)){
    			return ose.getStyleCss();
    		}
    	}
    	return null;
    }
}
