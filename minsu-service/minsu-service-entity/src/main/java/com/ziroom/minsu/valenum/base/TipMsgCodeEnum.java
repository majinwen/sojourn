package com.ziroom.minsu.valenum.base;

/**
 * <p>提示信息枚举  获取真正查询内容</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年06月21日 11:40
 * @since 1.0
 */
public enum TipMsgCodeEnum {

    WHY_AUTH("TIP_WHY_AUTH","为什么资质认证","TIP_WHY_AUTH_1,TIP_WHY_AUTH_2,TIP_WHY_AUTH_3"),
    HOW_CHOOSE_RENT_WAY("TIP_HOW_CHOOSE_RENT_WAY","如何选择出租方式","TIP_HOW_CHOOSE_RENT_WAY_1,TIP_HOW_CHOOSE_RENT_WAY_2,TIP_HOW_CHOOSE_RENT_WAY_3"),
    HOW_WRITE_LOCATION("TIP_HOW_WRITE_LOCATION","如何填写位置信息","TIP_HOW_WRITE_LOCATION_1,TIP_HOW_WRITE_LOCATION_2"),
    HOW_WRITE_DESC("TIP_HOW_WRITE_DESC","如何填写描述信息","TIP_HOW_WRITE_DESC_1"),
    HOW_WRITE_ROOM_MSG("TIP_WRITE_ROOM_MSG","如何填写户型和房间信息","TIP_WRITE_ROOM_MSG_1"),
    ORDER_TYPE_EFFECT("TIP_ORDER_TYPE_EFFECT","预订类型对房源排名和订单影响","TIP_ORDER_TYPE_EFFECT_2"),
    HOW_UPLOAD_HOUSE_PIC("TIP_HOW_UPLOAD_HOUSE_PIC","如何上传房源照片","TIP_HOW_UPLOAD_HOUSE_PIC_1"),
    HOW_PUBLISH_MULTI_ROOM("TIP_HOW_PUBLISH_MULTI_ROOM","如何选择房间数量以便快速发布多个房间","TIP_HOW_PUBLISH_MULTI_ROOM_1");

    TipMsgCodeEnum(String code, String name,String subCodes) {
        this.code = code;
        this.name = name;
        this.subCodes = subCodes;
    }


    /** code */
    private String code;

    /** 名称 */
    private String name;

    private String subCodes;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getSubCodes() {
        return subCodes;
    }

    public String[] getSubCodeArray(){
        return subCodes.split(",");
    }
}
