package com.ziroom.minsu.valenum.search;

import com.asura.framework.base.util.Check;

/**
 * <p>图标尺寸大小</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/23.
 * @version 1.0
 * @since 1.0
 */
public enum  IconPicTypeEnum {

    small(1,"small", "小图"),
    middle(2,"middle", "中图"),
    big(3,"big", "大图");

    IconPicTypeEnum(int code,  String path,String name) {
        this.code = code;
        this.path = path;
        this.name = name;
    }


    /**
     * 通过code过去当前的
     * @param code
     * @return
     */
    public static IconPicTypeEnum getByCode(Integer code){
        if (Check.NuNObj(code)){
            return middle;
        }
        for(final IconPicTypeEnum iconPicTypeEnum : IconPicTypeEnum.values()){
            if(iconPicTypeEnum.getCode() == code){
                return iconPicTypeEnum;
            }
        }
        return middle;
    }




    /** code */
    private int code;

    /** 名称 */
    private String name;

    /** 路径 */
    private String path;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
