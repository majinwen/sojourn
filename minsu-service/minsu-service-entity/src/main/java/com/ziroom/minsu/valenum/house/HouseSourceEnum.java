package com.ziroom.minsu.valenum.house;

/**
 * <p>订单来源</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/5.
 * @version 1.0
 * @since 1.0
 */
public enum HouseSourceEnum {
	TROY(0,"troy","troy"),
    PC(1,"PC","PC"),
    IOS(2,"ios","ios"),
    ANDROID(3,"android","ad"),
    MSIT(5,"APP","app");

    HouseSourceEnum(int code, String name,String clientName) {
        this.code = code;
        this.name = name;
        this.clientName = clientName;
    }

    /**
     * 通过code获取支付的值
     * @param code
     * @return
     */
    public static HouseSourceEnum getByCode(int code){
        for(final HouseSourceEnum ose : HouseSourceEnum.values()){
            if(ose.getCode() ==code){
                return ose;
            }
        }
        return null;
    }


    public static HouseSourceEnum getByClientName(String clientName){
    	for(final HouseSourceEnum ose : HouseSourceEnum.values()){
    		if(ose.getClientName().equals(clientName)){
    			return ose;
    		}
    	}
    	return null;
    }

    /** code */
    private int code;

    /** 名称 */
    private String name;
    
    /** 客户端传递名称 */
    private String clientName;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    
    public String getClientName() {
        return clientName;
    }


    public static void main(String[] args) {
        HouseSourceEnum sourceEnum = HouseSourceEnum.getByCode(3);
        System.out.println(sourceEnum.clientName);

    }
}
