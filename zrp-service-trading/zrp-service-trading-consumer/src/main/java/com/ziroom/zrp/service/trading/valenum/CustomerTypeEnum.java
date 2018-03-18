package com.ziroom.zrp.service.trading.valenum;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>合同客户类型</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月14日 16:54
 * @since 1.0
 */
public enum CustomerTypeEnum {

    PERSON(1,"个人客户"),
    QYHYG(2, "企悦会员工"),//aaded by wangxm113
    ENTERPRICE(3,"企业客户"),
    EPS(4,"eps");//add by xiangb 也代表企业客户

    CustomerTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;

    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByStatus(int code){
        for(final CustomerTypeEnum customerTypeEnum : CustomerTypeEnum.values()){
            if(customerTypeEnum.getCode() == code){
                return customerTypeEnum.getName();
            }
        }
        return null;
    }

    public static Map<Integer,String> getSelectMap(){
        Map<Integer,String> map = new LinkedHashMap<>();
        for (CustomerTypeEnum customerTypeEnum : CustomerTypeEnum.values()){
            if (customerTypeEnum == CustomerTypeEnum.EPS){
                continue;
            }
            map.put(customerTypeEnum.getCode(),customerTypeEnum.getName());
        }
        return map;
    }
}
