package com.ziroom.minsu.valenum.order;

/**
 * <p>后台订单管理，订单的操作类型  
 * 
 * 作用：现在 权限没有控制到每个按钮，只能每类操作给出此类订单然后去操作。
 *    因为订单公用信息很多，没有必要给每类订单都给出一个列表，给出一个操作标识，然后进行逻辑判断即可，这就是此枚举的作用
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public enum OrderOprationType {

	//1.全订单列表  2.强制取消操作列表
	ALL_ORDER_LIST(1,"全订单列表"),
	FORCECAN_ORDER_LIST(2,"强制取消订单列表");
	
	OrderOprationType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	/** code */
	private int code;

	/** 名称 */
	private String name;

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
