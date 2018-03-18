package com.zra.common.constant;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/8 15:54
 * @since 1.0
 */
public enum SurTypeEnum {

	NORMAL("0", "正常退租"),
	ABNORMAL("1", "非正常退租"),
	ONE_SIDED("2", "客户单方面解约"),
	THREE_DAYS_NOT_SATISFIED("3", "三天不满意退租"),
	IN_RENT("4", "换租"),
	SUBLET("5", "转租");

    private String code;
    private String name;

    SurTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static SurTypeEnum getByCode(String state){
        for(SurTypeEnum floorEnum: SurTypeEnum.values()){
            if(floorEnum.getCode().equals(state)){
                return floorEnum;
            }
        }
        return null;
    }

}
