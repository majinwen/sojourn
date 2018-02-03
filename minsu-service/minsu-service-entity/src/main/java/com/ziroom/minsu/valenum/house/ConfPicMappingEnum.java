/**
 * @FileName: HouseStatusEnum.java
 * @Package com.ziroom.minsu.valenum.house
 * 
 * @author bushujie
 * @created 2016年4月2日 下午8:56:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.house;

import java.util.LinkedHashMap;
import java.util.Map;

import com.asura.framework.base.util.Check;


/**
 * <p></p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public enum ConfPicMappingEnum {
	ELECTRIC("ProductRulesEnum002001", "电器") {
		private Map<String, String> map = new LinkedHashMap<String, String>();

		@Override
		public Map<String, String> getMapping() {
			if (Check.NuNMap(map)) {
				map.put(ConfPicMappingEnum.ELECTRIC.getMappingCode(1), "dianshi");//电视
				map.put(ConfPicMappingEnum.ELECTRIC.getMappingCode(2), "kongtiao");//空调
				map.put(ConfPicMappingEnum.ELECTRIC.getMappingCode(3), "yinshuishebei");//饮水设备
				map.put(ConfPicMappingEnum.ELECTRIC.getMappingCode(4), "bingxiang");//冰箱
				map.put(ConfPicMappingEnum.ELECTRIC.getMappingCode(5), "xiyiji");//洗衣机
				map.put(ConfPicMappingEnum.ELECTRIC.getMappingCode(6), "hongganji");//烘干机
				map.put(ConfPicMappingEnum.ELECTRIC.getMappingCode(7), "chuifengji");//电吹风
			}
			return map;
		}
	},
	BATHROOM("ProductRulesEnum002002", "卫浴") {
		private Map<String, String> map = new LinkedHashMap<String, String>();

		@Override
		public Map<String, String> getMapping() {
			if (Check.NuNMap(map)) {
				map.put(ConfPicMappingEnum.BATHROOM.getMappingCode(1), "linyu");//热水淋浴
				map.put(ConfPicMappingEnum.BATHROOM.getMappingCode(2), "yugang");//热水浴缸
				map.put(ConfPicMappingEnum.BATHROOM.getMappingCode(3), "yaju");//牙具
				map.put(ConfPicMappingEnum.BATHROOM.getMappingCode(4), "xiangzao");//香皂
				map.put(ConfPicMappingEnum.BATHROOM.getMappingCode(5), "tuoxie");//拖鞋
				map.put(ConfPicMappingEnum.BATHROOM.getMappingCode(6), "weishengzhi");//卫生纸
				map.put(ConfPicMappingEnum.BATHROOM.getMappingCode(7), "maojin");//毛巾
				map.put(ConfPicMappingEnum.BATHROOM.getMappingCode(8), "muyulu");//沐浴露
			}
			return map;
		}
	},
	FACILITY("ProductRulesEnum002003", "设施") {
		private Map<String, String> map = new LinkedHashMap<String, String>();

		@Override
		public Map<String, String> getMapping() {
			if (Check.NuNMap(map)) {
				map.put(ConfPicMappingEnum.FACILITY.getMappingCode(1), "shafa");//沙发
				map.put(ConfPicMappingEnum.FACILITY.getMappingCode(2), "wifi");//无线网络
				map.put(ConfPicMappingEnum.FACILITY.getMappingCode(3), "youxianwangluo");//有线网络
				map.put(ConfPicMappingEnum.FACILITY.getMappingCode(4), "nuanqi");//暖气
				map.put(ConfPicMappingEnum.FACILITY.getMappingCode(5), "dianti");//电梯
				map.put(ConfPicMappingEnum.FACILITY.getMappingCode(6), "menjin");//门禁系统
				map.put(ConfPicMappingEnum.FACILITY.getMappingCode(7), "shuzhuo");//书桌
			}
			return map;
		}
	},
	SERVICE("ProductRulesEnum0015", "服务") {
		private Map<String, String> map = new LinkedHashMap<String, String>();

		@Override
		public Map<String, String> getMapping() {
			if (Check.NuNMap(map)) {
				map.put(ConfPicMappingEnum.SERVICE.getMappingCode(1), "zaocan");//早餐
				map.put(ConfPicMappingEnum.SERVICE.getMappingCode(2), "wancan");//家庭晚餐
				map.put(ConfPicMappingEnum.SERVICE.getMappingCode(3), "jijiubao");//急救包
				map.put(ConfPicMappingEnum.SERVICE.getMappingCode(4), "tingche");//停车位
				map.put(ConfPicMappingEnum.SERVICE.getMappingCode(5), "xiyan");//允许吸烟
				map.put(ConfPicMappingEnum.SERVICE.getMappingCode(6), "zuofan");//可以做饭
				map.put(ConfPicMappingEnum.SERVICE.getMappingCode(7), "chongwu");//可以携带宠物
				map.put(ConfPicMappingEnum.SERVICE.getMappingCode(8), "jieji");//免费接机
			}
			return map;
		}
	};

	/** code */
	private String code;

	/** 名称 */
	private String name;
	
	private static final Map<String, String> totalMap = new LinkedHashMap<String, String>();
	
	static {
		for (ConfPicMappingEnum enumeration : ConfPicMappingEnum.values()) {  
			totalMap.putAll(enumeration.getMapping());
        }  
	}

	ConfPicMappingEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getMappingCode(int value) {
		return new StringBuilder(code).append(value).toString();
	}
	
	public static Map<String, String> getTotalMap() {
    	return totalMap;
    }

	public abstract Map<String, String> getMapping();
}
