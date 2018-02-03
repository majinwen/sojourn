/**
 * @FileName: CustomerPicTypeEnum.java
 * @Package com.ziroom.minsu.valenum.customer
 * 
 * @author bushujie
 * @created 2016年4月23日 下午5:12:46
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.customer;

import com.asura.framework.base.util.Check;

import java.util.LinkedHashMap;
import java.util.Map;
/**
 * <p>客户照片类型</p>
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
public enum CustomerPicTypeEnum {


    // 图片类型(0：证件正面照，1：证件反面照，2：手持证件照，3：用户头像)
    ZJZM(0,"证件正面照"),
    ZJFM(1,"证件反面照"),
    ZJSC(2,"手持证件照"),
    YHTX(3,"用户头像"),
    YYZZ(4,"营业执照");

    /** code */
    private int code;

    /** 名称 */
    private String name;

    private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();

    static {
        for (CustomerPicTypeEnum customerPicTypeEnum : CustomerPicTypeEnum.values()) {
            enumMap.put(customerPicTypeEnum.getCode(), customerPicTypeEnum.getName());
        }
    }

    /**
     * 获取
     * @param code
     * @return
     */
    public static CustomerPicTypeEnum getByCode(Integer code) {
        if(Check.NuNObj(code)){
            return null;
        }
        for (final CustomerPicTypeEnum customerPicTypeEnum : CustomerPicTypeEnum.values()) {
            if (customerPicTypeEnum.getCode() == code) {
                return customerPicTypeEnum;
            }
        }
        return null;
    }



    CustomerPicTypeEnum(int code,String name){
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
