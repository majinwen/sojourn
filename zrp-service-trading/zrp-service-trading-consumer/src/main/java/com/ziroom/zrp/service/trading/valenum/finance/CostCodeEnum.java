package com.ziroom.zrp.service.trading.valenum.finance;

import java.util.*;

/**
 * <p>财务费编码</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月22日 18:10
 * @since 1.0
 */
public enum CostCodeEnum {
    KHFZ("khfz","C01",1,"房租"),
    KHFWF("khfwf","C02",2,"服务费"),
    KHYJ("khyj","C03",3,"押金"),
    KHYQWYJ("khyqwyj","C05",5,"逾期违约金"),

    ZRYDF("zrydf","S02",9,"电费"),
    ZRYSF("zrysf","S01",8,"水费"),
    XYF("xyf","S06",13,"洗衣费"),
    FK("fk","Z04",57,"房卡"),
    TCF("tcf","S05",12,"停车费"),
    TYBJ("tybj","S03",10,"特约保洁费"),

    GSWYJ("gswyj","C11",67,"公司违约金"),
    KHWYJ("khwyj","C09",65,"客户违约金");


    CostCodeEnum(String code,String zraCode,int zraId,String name) {
        this.code = code;
        this.zraCode = zraCode;
        this.zraId = zraId;
        this.name = name;
    }
    //财务费用项code
    private String code;
    //自如寓费用项code
    private String zraCode;
    //idcode
    private int zraId;
    //费用项名称
    private String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getZraCode() {
        return zraCode;
    }

    public int getZraId() {
        return zraId;
    }

    public void setZraId(int zraId) {
        this.zraId = zraId;
    }

    public static CostCodeEnum getByCode(String code){
        for(final CostCodeEnum costCodeEnum : CostCodeEnum.values()){
            if(costCodeEnum.getCode().equals(code)){
                return costCodeEnum;
            }
        }
        return null;
    }
    /**
     * 获取对应的枚举
     * @author jixd
     * @created 2017年11月05日 13:49:53
     * @param
     * @return
     */
    public static CostCodeEnum getById(int id){
        for(final CostCodeEnum costCodeEnum : CostCodeEnum.values()){
            if(costCodeEnum.getZraId() == id){
                return costCodeEnum;
            }
        }
        return null;
    }

    /**
     * 获取生活费用项
     * @author jixd
     * @created 2017年10月30日 17:53:12
     * @param
     * @return
     */
    public static List<String> getLifeFeeCode(){
        List<String> list = new ArrayList<>();
        List<CostCodeEnum> costCodeEnums = Arrays.asList(XYF, FK, TCF);
        for (CostCodeEnum costCodeEnum : costCodeEnums){
            list.add(costCodeEnum.getZraCode());
        }
        return list;
    }

    public static List<String> getFinLifeFeeCode(){
        List<String> list = new ArrayList<>();
        List<CostCodeEnum> costCodeEnums = Arrays.asList(ZRYDF, ZRYSF,XYF, FK, TCF);
        for (CostCodeEnum costCodeEnum : costCodeEnums){
            list.add(costCodeEnum.getCode());
        }
        return list;
    }

    /**
     * 获取生活费用项 带水电
     * @return
     */
    public static List<String> getLifeFeeCodeWithSD(){
        List<String> list = new ArrayList<>();
        List<CostCodeEnum> costCodeEnums = Arrays.asList(ZRYDF, ZRYSF, XYF, FK, TCF);
        for (CostCodeEnum costCodeEnum : costCodeEnums){
            list.add(costCodeEnum.getZraCode());
        }
        return list;
    }
}
