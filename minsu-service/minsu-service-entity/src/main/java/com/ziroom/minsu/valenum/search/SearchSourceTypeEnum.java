package com.ziroom.minsu.valenum.search;

import com.asura.framework.base.util.Check;

/**
 * 
 * <p>搜索入口</p>
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
public enum  SearchSourceTypeEnum {

    search_list(1,"搜索列表"),
    today_article(2,"今日特惠专栏"),
    activity_list(3,"活动列表"),
    troy_search_list(4,"后台搜索"),
    troy_search_one(5,"后台搜索");

    SearchSourceTypeEnum(int code,String name) {
        this.code = code;
        this.name = name;
    }


    /**
     * 通过code过去当前的
     * @param code
     * @return
     */
    public static SearchSourceTypeEnum getByCode(Integer code){
        if (Check.NuNObj(code)){
            return search_list;
        }
        for(final SearchSourceTypeEnum iconPicTypeEnum : SearchSourceTypeEnum.values()){
            if(iconPicTypeEnum.getCode() == code){
                return iconPicTypeEnum;
            }
        }
        return search_list;
    }




    /** code */
    private int code;

    /** 名称 */
    private String name; 

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    } 
}
