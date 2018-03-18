package com.ziroom.zrp.service.trading.valenum;

/**
 * <p>学历枚举</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2017年12月06日
 * @version 1.0
 * @since 1.0
 */
public enum DegreeEnum {
    OTHER(0,"其他"),
    PRIMARY(1,"小学"),
    JUNIOR(2,"初中"),
    SENIOR(3,"高中"),
    POLITECHNIC(4,"中技"),
    TECHNICAL(5,"中专"),
    JUNIORCOLLEGE(6,"大专"),
    BACHELOR(7,"本科"),
    MBA(8,"MBA"),
    MASTER(9,"硕士"),
    DOCTOR(10,"博士"),
    Adult(11,"成人教育");

    DegreeEnum(int code, String name) {
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

    public static DegreeEnum getByCode(int code){
        for(final DegreeEnum certTypeEnum : DegreeEnum.values()){
            if(certTypeEnum.getCode()== code){
                return certTypeEnum;
            }
        }
        return null;
    }

    public static DegreeEnum getCodeMapping(int code){
        switch (code) {
            case 1:// 博士研究生
                return DOCTOR;
            case 2://硕士研究生
                return MASTER;
            case 3://本科
                return BACHELOR;
            case 4://专科
                return JUNIORCOLLEGE;
            case 5://成人
                return Adult;
            default:
                return OTHER;
        }
    }
}
