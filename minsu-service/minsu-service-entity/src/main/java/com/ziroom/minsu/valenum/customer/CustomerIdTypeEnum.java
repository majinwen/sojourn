
package com.ziroom.minsu.valenum.customer;

import com.asura.framework.base.util.Check;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * (0=其他 1=身份证 2=护照 3=军官证 4=通行证 5=驾驶证 6=台胞证 7=社保卡 8=省份证 9=社保卡 10=学生证 11=回乡证 12=营业执照 13=港澳通行证 14户口本 15=居住证)
 * <p>客户学历枚举</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public enum CustomerIdTypeEnum {

	OTHER(0,"其他",""),
	ID(1,"身份证","CUSTOMER_AUTH_ID_PIC_EXPLAIN"),
	PASSPORT(2,"护照","CUSTOMER_AUTH_PASSPORT_PIC_EXPLAIN"),
	CHARTER(12,"营业执照","CUSTOMER_AUTH_CHARTER_PIC_EXPLAIN"),
	GA_PASSPORT(13,"港澳居民来往通行证","CUSTOMER_AUTH_MACAO_PIC_EXPLAIN"),
	tw_PASSPORT(6,"台湾居民来往通行证","CUSTOMER_AUTH_TAIWAN_PIC_EXPLAIN"),
	GAT_ID(16,"港,澳,台居民身份证","CUSTOMER_AUTH_GAT_ID_PIC_EXPLAIN");
	
	CustomerIdTypeEnum(int code,String name,String explainKey){
		this.code  = code;
		this.name = name;
		this.explainKey=explainKey;
	}
	
	/**
	 * code值
	 */
	private  int code;
	
	/**
	 * z中文含义
	 */
	private String name;
	
	/**
	 * 说明的key值
	 */
	private String explainKey;

	/**
	 * @return the explainKey
	 */
	public String getExplainKey() {
		return explainKey;
	}

	/**
	 * @param explainKey the explainKey to set
	 */
	public void setExplainKey(String explainKey) {
		this.explainKey = explainKey;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public static CustomerIdTypeEnum getCustomerIdTypeByCode(int code) {
        for (CustomerIdTypeEnum statu : CustomerIdTypeEnum.values()) {
            if (statu.getCode() == code) {
                return statu;
            }
        }
        return null;
    }
    
    public static List<CustomerIdTypeEnum> getCustomerIdTypeEnums(){
    	List<CustomerIdTypeEnum> idTypeList = new ArrayList<CustomerIdTypeEnum>();
    	for (CustomerIdTypeEnum statu : CustomerIdTypeEnum.values()) {
    		if(statu.getCode() != 0 && statu.getCode()!=CustomerIdTypeEnum.CHARTER.getCode()){
    			idTypeList.add(statu);	
    		}
    	}
       return idTypeList;	
    }

	public static List<CustomerIdTypeEnum> getALLCustomerIdTypeEnums(){
		List<CustomerIdTypeEnum> idTypeList = new ArrayList<CustomerIdTypeEnum>();
		for (CustomerIdTypeEnum statu : CustomerIdTypeEnum.values()) {
			if(statu.getCode() != 0){
				idTypeList.add(statu);
			}
		}
		return idTypeList;
	}

    public static Map<Integer, String> getCustomerIdTypeMap(){
    	Map<Integer, String> map = new HashMap<Integer, String>();
    	for (CustomerIdTypeEnum statu : CustomerIdTypeEnum.values()) {
    		if(statu.getCode() != 0){
    			map.put(statu.getCode(), statu.getName());
    		}
    	}
       return map;	
    }
    
    public static Map<Integer, String> getCustomerIdExplainMap(){
    	Map<Integer, String> map = new HashMap<Integer, String>();
    	for (CustomerIdTypeEnum statu : CustomerIdTypeEnum.values()) {
    		if(statu.getCode() != 0){
    			map.put(statu.getCode(), statu.explainKey);
    		}
    	}
       return map;	
    }

	/**
	 * 校验身份证
	 * @param idNo
	 * @return
	 */
	public static boolean  checkId(String idNo){
		boolean isId = false;
		if (!Check.NuNStr(idNo) && idNo.length() >= 15){
			isId = true;
		}
		return isId;
	}
}
