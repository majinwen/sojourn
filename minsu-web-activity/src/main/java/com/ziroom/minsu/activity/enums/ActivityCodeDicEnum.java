package com.ziroom.minsu.activity.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>活动字典枚举，加随机码</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author JIXD on on 2016/12/9.
 * @version 1.0
 * @since 1.0
 */
public enum ActivityCodeDicEnum {

    /**
     * 五一活动
     */
    GROUP_51_ACT("WUYI", "五一活动码","WUYIEnum"){
        @Override
        public Map<String,Integer> getActRateMap() {
            HashMap<String,Integer> map = new HashMap<>();
            for (WUYIEnum wuyiEnum:WUYIEnum.values()){
                map.put(wuyiEnum.getCode(),wuyiEnum.getRate());
            }
            return map;
        }
    },
    
    DUANWU2017("DUANWU2017", "端午活动","DUANWU"){
    	 @Override
         public Map<String,Integer> getActRateMap() {
             HashMap<String,Integer> map = new HashMap<>();
             map.put(DUANWU2017.getCode(),100);
             return map;
         }
    };

    ActivityCodeDicEnum(String code, String name,String enumName){
        this.code = code;
        this.name = name;
        this.enumName = enumName;
    }
    /** code */
    private String code;

    /** 名称 */
    private String name;

    private String enumName;


    public String getCode() {
        return code;
    }


    public String getName() {
        return name;
    }

    public String getEnumName() {
        return enumName;
    }

    public static ActivityCodeDicEnum getByCode(String msgCode) {
        for (final ActivityCodeDicEnum code : ActivityCodeDicEnum.values()) {
            if (code.getCode().equals(msgCode)) {
                return code;
            }
        }
        return null;
    }

    public Map<String,Integer> getActRateMap(){
        return null;
    }


}

