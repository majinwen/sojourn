package com.ziroom.minsu.valenum.search;

import com.asura.framework.base.util.Check;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zl
 * @version 1.0
 * @Date Created in 2017年07月28日 12:53
 * @since 1.0
 */
public enum SearchDataSourceTypeEnum {

    minsu(1,"民宿"),
    ziruyi(2,"自如驿"),
    cms(3,"攻略");

    SearchDataSourceTypeEnum(int code,String name) {
        this.code = code;
        this.name = name;
    }


    /**
     * 通过code过去当前的
     * @param code
     * @return
     */
    public static SearchDataSourceTypeEnum getByCode(Integer code){
        if (Check.NuNObj(code)){
            return null;
        }
        for(final SearchDataSourceTypeEnum iconPicTypeEnum : SearchDataSourceTypeEnum.values()){
            if(iconPicTypeEnum.getCode() == code){
                return iconPicTypeEnum;
            }
        }
        return null;
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
