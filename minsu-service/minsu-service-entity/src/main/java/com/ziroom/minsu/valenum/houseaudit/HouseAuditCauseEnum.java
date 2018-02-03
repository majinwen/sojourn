package com.ziroom.minsu.valenum.houseaudit;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.asura.framework.base.util.Check;

/**
 * 
 * <p>房源品质审核</p>
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
public class HouseAuditCauseEnum {
	/**
	 * 全部枚举map
	 */
	private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();
	
	/**
	 * 有效枚举集合
	 */
	private static final Map<Integer,String> validEnumMap = new LinkedHashMap<Integer,String>();
	
	static {
		//enumMap.putAll(HouseAuditCauseEnum.Approve.getEnumMap());
		enumMap.putAll(HouseAuditCauseEnum.REJECT.getEnumMap());
		validEnumMap.putAll(HouseAuditCauseEnum.REJECT.getValidEnumMap());
	}
	
	public static Map<Integer,String> getEnumMap() {
		return enumMap;
	}
	
	public static Map<Integer,String> getValidEnumMap() {
		return validEnumMap;
	}
	
	/**
	 * 
	 * 将审核原因转换为code集合
	 *
	 * @author liujun
	 * @created 2016年11月3日
	 *
	 * @param auditCause
	 * @return
	 */
	public static List<Integer> getCodeList(String auditCause) {
		List<Integer> list = new ArrayList<Integer>();
		if (Check.NuNStrStrict(auditCause)) {
			return list;
		}
		String[] array = auditCause.split("\\,");
		for (String str : array) {
			list.add(Integer.valueOf(str));
		}
		return list;
	}
	
	/**
	 * 
	 * 将审核原因code字符串转换为name集合
	 *
	 * @author liujun
	 * @created 2016年11月3日
	 *
	 * @param auditCause
	 * @return
	 */
	public static List<String> getNameList(String auditCause) {
		List<String> nameList = new ArrayList<String>();
		List<Integer> list = getCodeList(auditCause);
		for (Integer integer : list) {
			if (enumMap.containsKey(integer)) {
				nameList.add(enumMap.get(integer));
			}
		}
		return nameList;
	}
	
	/**
	 * 
	 * 将审核原因code字符串转换为name字符串
	 *
	 * @author liujun
	 * @created 2016年11月3日
	 *
	 * @param auditCause
	 * @return
	 */
	public static String getNameStr(String auditCause) {
		String split = " ";
		StringBuilder sb = new StringBuilder();
		List<Integer> list = getCodeList(auditCause);
		int i = 0;
		int j = 1;
		String z = "、";
		for (Integer integer : list) {
			if (enumMap.containsKey(integer)) {
				if(i==0){
					sb.append(j).append(z).append(enumMap.get(integer));
				}else {
					++j;
					sb.append(split).append(j).append(z).append(enumMap.get(integer));
				}
			}
			i++;
		}
		return sb.toString();
	}
	
	/**
	 * 审核报表中解析审核未通过原因
	 * @param auditCause
	 * @return
	 */
	public static String getNameString(String auditCause){
		String split = " ";
		StringBuilder sb = new StringBuilder();
		List<Integer> list = getCodeList(auditCause);
		int i = 0;
		String z = ",";
		for(Integer integer : list){
			if(enumMap.containsKey(integer)){
				if(i == 0){
					sb.append(enumMap.get(integer));
				}else {
					sb.append(split).append(z).append(split).append(enumMap.get(integer));
				}
			}
			i++;
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.err.println(getNameStr("10,14"));
	}
	
	public enum Approve{
		UNNEED(0,"不需要拍照"),
		NEED(1,"需要拍照");
		
		private int code;
		
		private String name;
		
		private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();
		
		static {
			for (Approve valEnum : Approve.values()) {
				enumMap.put(valEnum.getCode(), valEnum.getName());  
	        }  
		}
		
		Approve(int code,String name) {
			this.code = code;
			this.name = name;
		}
		
		public int getCode() {
			return code;
		}
		
		public String getName() {
			return name;
		}
		
		public static Map<Integer,String> getEnumMap() {
	    	return enumMap;
	    }
    }
	 
	public enum REJECT {
		HOUSE_QUALITY(13, "房源品质不满足平台要求", true),
		PHOTO(10, "照片不符合平台要求，需要预约摄影师", true),
		PHOTO_NONEED(16,"部分照片需要更换", true),
		LANDLORD_INFO(11, "房东信息需要修改", true), 
		@Deprecated
		HOUSE_INFO(12, "房源信息原因", false),
		@Deprecated
		UNSURVEY(14, "实勘原因", false),
		HOUSE_PHY(17,"房源基本信息需要修改", true),
		HOUSE_DESC(18,"房源描述、周边情况等描述信息需要修改", true),
		OTHER(15,"其他原因", true);

		private int code;

		private String name;
		
		/** 是否初始化进入Map标志 */
		private boolean isPushToMap;

		private static final Map<Integer, String> enumMap = new LinkedHashMap<Integer, String>();
		
		private static final Map<Integer,String> validEnumMap = new LinkedHashMap<Integer,String>();

		static {
			for (REJECT valEnum : REJECT.values()) {
				enumMap.put(valEnum.getCode(), valEnum.getName());
				
				if (valEnum.getIsPushToMap()) {
					validEnumMap.put(valEnum.getCode(), valEnum.getName());
				}
			}
		}

		REJECT(int code, String name, boolean isPushToMap) {
			this.code = code;
			this.name = name;
			this.isPushToMap = isPushToMap;
		}

		public int getCode() {
			return code;
		}

		public String getName() {
			return name;
		}

	    public boolean getIsPushToMap() {
	    	return isPushToMap;
	    }

		public static Map<Integer, String> getEnumMap() {
			return enumMap;
		}
		
		public static Map<Integer,String> getValidEnumMap() {
			return validEnumMap;
		}
	}
}
