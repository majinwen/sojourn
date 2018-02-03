package com.ziroom.minsu.valenum.order;

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
public enum OrderSourceEnum {
    PC(1,"PC","PC"),
    IOS(2,"ios","ios"){
        @Override
        public boolean checkMobile() {
            return true;
        }
    },
    ANDROID(3,"android","ad"){
        @Override
        public boolean checkMobile() {
            return true;
        }
    },
    MSIT(5,"APP","app");

    OrderSourceEnum(int code, String name,String clientName) {
        this.code = code;
        this.name = name;
        this.clientName = clientName;
    }

    /**
     * 通过code获取支付的值
     * @param code
     * @return
     */
    public static OrderSourceEnum getByCode(int code){
        for(final OrderSourceEnum ose : OrderSourceEnum.values()){
            if(ose.getCode() ==code){
                return ose;
            }
        }
        return null;
    }


    public static OrderSourceEnum getByClientName(String clientName){
    	for(final OrderSourceEnum ose : OrderSourceEnum.values()){
    		if(ose.getClientName().equals(clientName)){
    			return ose;
    		}
    	}
    	return null;
    }


    public boolean checkMobile(){
        return false;
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
        OrderSourceEnum sourceEnum = OrderSourceEnum.getByCode(3);
        System.out.println(sourceEnum.clientName);

    }
}
