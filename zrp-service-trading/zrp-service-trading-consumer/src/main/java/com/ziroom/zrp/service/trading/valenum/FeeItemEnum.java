package com.ziroom.zrp.service.trading.valenum;

/**
 * <p>应收账单费用项枚举（texpenseitem表）</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年10月16日 10:58
 * @since 1.0
 */

public enum FeeItemEnum {

    RENT_FEE(1,"C01", "房租","khfz"),
    SERVICE_FEE(2,"C02", "服务费","khfwf"),
    DEPOSIT_FEE(3,"C03", "押金","khyj"),
    OVERDUE_FEE(5,"C05", "逾期违约金","khyqwyj"),
    WATER_FEE(8,"S01","水费","zrysf"),
    POWER_FEE(9,"S02","电费","zrydf"),
    CLEAN_FEE(10,"S03","保洁费","tybj"),
    REPAIR_FEE(11,"S04","维修费","zrywx"),
    PARK_FEE(12,"S05","停车费","tcf"),
    WASH_FEE(13,"S06","洗衣费","xyf");



    FeeItemEnum(int itemFid,String itemCode,String itemName,String costCodeEnumCode){
        this.itemFid = itemFid;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.costCodeEnumCode = costCodeEnumCode;
    }

    private int itemFid;

    private String itemCode;

    private String itemName;

    private String costCodeEnumCode;

    public int getItemFid() {
        return itemFid;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public String getCostCodeEnumCode() {
        return costCodeEnumCode;
    }

    /**
      * @description: 根据costCodeEnumCode获取相应枚举
      * @author: lusp
      * @date: 2017/10/16 上午 11:30
      * @params: costCodeEnumCode
      * @return: FeeItemEnum
      */
    public static FeeItemEnum getByCostCodeEnumCode(String costCodeEnumCode){
        for(final FeeItemEnum feeItemEnum : FeeItemEnum.values()){
            if(feeItemEnum.getCostCodeEnumCode().equals(costCodeEnumCode)){
                return feeItemEnum;
            }
        }
        return getByCostCodeEnumCode("khfz");
    }
}
