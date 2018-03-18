package com.ziroom.zrp.service.houses.valenum;

import com.asura.framework.base.util.Check;

/**
 * <p>公司枚举</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2018年01月08日
 * @version 1.0
 * @since 1.0
 */
public enum CompanyEnum {

    ZIROOM_GUANGZHOU("440100","广州自如"),
    ZIROOM_CHENGDU("510100","成都自如"),
    ZIROOM_WUHAN("420100","武汉自如"),
    ZIROOM_TIANJIN("120000","天津自如"),
    ZIROOM_NANJING("320100","南京自如"),
    ZIROOM_TEC("111111","自如科技"),
    ZIROOM_HANGZHOU("330100","杭州自如"),
    ZIROOM_BJ_ZRA_HOTEL("801500","北京自如寓酒店"),
    ZIROOM_MINSU("801300","自如民宿"),
    ZIROOM_LIFE("801000","自如生活"),
    ZIROOM_SHENZHEN("440300","深圳自如"),
    ZIROOM_BEIJING("110000","北京自如"),
    ZIROOM_SHANGHAI("310000","上海自如");

    CompanyEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    /**
     * 类型
     */
    private String code;
    /**
     * 名字
     */
    private String name;


    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static CompanyEnum getCompanyEnumByCode(String code){
        if(Check.NuNStr(code)){
            return null;
        }
        for (CompanyEnum companyEnum: CompanyEnum.values()){
            if (companyEnum.getCode().equals(code)){
                return companyEnum;
            }
        }
        return  null;
    }

}
