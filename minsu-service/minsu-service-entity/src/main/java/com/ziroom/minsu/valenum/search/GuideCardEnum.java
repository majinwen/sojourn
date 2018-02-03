/**
 * @FileName: GuideCardEnum.java
 * @Package com.ziroom.minsu.valenum.search
 * 
 * @author zl
 * @created 2016年10月31日 下午5:34:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.search;

import java.util.HashMap;
import java.util.Map;



/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public enum GuideCardEnum {
	
	city_card(1,"选择城市","去哪儿旅行？","选择城市可以帮助您更快地找到合适的房子。",true),
	date_card(2,"选择日期","何时入住？","选择日期可以帮助您更快地找到合适的房子。",true),
	guests_num_card(3,"选择入住人数","几人同行？","选择人数可以帮助您更快地找到合适的房子。",true),
	rent_way_card(4,"选择房间类型","想要拥有专属空间或是与人同住？","选择房间类型可以帮助您更快找到合适的房子。",true),
	price_card(5,"选择价格区间","预算是多少？","设置价格区间可以更快地选到预算内的房子。",true);
	
	private GuideCardEnum(	Integer code, String title,String subTitle, String describe,
			Boolean isShow) {
		this.code = code;
		this.title = title;
		this.subTitle = subTitle;
		this.describe = describe;
		this.isShow = isShow;
	}
	
	private  Integer code;

    private  String title;
    
    private  String subTitle;

    private  String describe;
    
    private  Boolean isShow;
    
    //放在列表哪個位置
    public static Integer index=3;
    //卡片數量
    public static Integer size=3;

	public Integer getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public String getDescribe() {
		return describe;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}
	
	public static Map<String, Object> getCardEnumMap(GuideCardEnum cardEnum){
		if (cardEnum==null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", index);
		map.put("code", cardEnum.getCode());
		map.put("title", cardEnum.getTitle());
		map.put("subTitle", cardEnum.getSubTitle());
		map.put("describe", cardEnum.getDescribe());
		map.put("isShow", cardEnum.getIsShow());
		return map;
	}
 	
	
}
