package com.ziroom.minsu.valenum.productrules;


/**
 * 
 * <p>照片规则</p>
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
public enum ProductRulesEnum0017Enum {

    ProductRulesEnum0017001("ProductRulesEnum0017001","照片最小像素"),//宽*高（例如：3000*2000）; 值为0表示无限制
    ProductRulesEnum0017002("ProductRulesEnum0017002","照片最小分辨率"),//属性值为非负整数，单位是dpi; 值为0表示无限制
    ProductRulesEnum0017003("ProductRulesEnum0017003","照片比率"),//宽:高（例如：3:2）; 值为0表示无限制
    ProductRulesEnum0017004("ProductRulesEnum0017004","照片最大上传大小");//属性值为非负整数，单位是KB; 值为0表示无限制

    private final String value;

    private final String name;

    ProductRulesEnum0017Enum(String value,String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static ProductRulesEnum0017Enum getEnumByValue(String value) {
        for (final ProductRulesEnum0017Enum enumration : ProductRulesEnum0017Enum.values()) {
            if (enumration.getValue().equals(value)) {
                return enumration;
            }
        }
        return null;
    }

}
