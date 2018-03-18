package com.zra.common.enums;

/**
 * <p>物业交割枚举</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月14日 16:11
 * @since 1.0
 */
public enum ItemDeliveryMsgEnum {

    PZWP(1,"配置物品","查看清单"),
    SHFW(2,"生活费用","查看详细清单"){
        @Override
        public String getDesc(int isPay) {
            if (isPay == 0){
                return "待支付";
            }else{
                return super.getDesc(isPay);
            }
        }

        @Override
        public String getColor(int isPay) {
            if (isPay == 0){
                return "#FFA000";
            }else{
                return super.getColor(isPay);
            }
        }
    },
    HZXX(3,"合租人信息（可填）","编辑");


    ItemDeliveryMsgEnum(int code, String name,String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    private int code;

    private String name;

    private String desc;

    public String getDesc() {
        return desc;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDesc(int isPay) {
        return desc;
    }

    public String getColor(int isPay){
        return "";
    }

}
