package com.ziroom.minsu.services.basedata.entity.entityenum;

import com.ziroom.minsu.valenum.top.StaticResourceTypeEnum;

/**
 * <p>业务线枚举</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月22日 19:47
 * @since 1.0
 */
public enum ServiceLineEnum {

    MINSU(1, "1", "民宿", "8a9e9aaf53c6a9df0153c6a9df880000"),
    ZRP(2, "2", "公寓", "8a9e989a5e6eb04d015e6ebaca9f0003"),
    ZRY(3, "3", "驿站", "8a9084df5ea7a852015ea8172238001a");

    ServiceLineEnum(int orcode, String code, String name, String template) {
        this.orcode = orcode;
        this.code = code;
        this.name = name;
        this.template = template;
    }

    private int orcode;

    private String code;

    private String name;

    private String template;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getTemplate() {
        return template;
    }

    public int getOrcode() {
        return orcode;
    }

    public static ServiceLineEnum getEnumByCode(String code) {
        for (final ServiceLineEnum enumeration : ServiceLineEnum.values()) {
            if (enumeration.getCode().equals(code)) {
                return enumeration;
            }
        }
        return null;
    }
}
