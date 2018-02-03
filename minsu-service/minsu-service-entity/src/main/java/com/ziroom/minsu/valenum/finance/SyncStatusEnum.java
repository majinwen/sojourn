package com.ziroom.minsu.valenum.finance;

/**
 * <p>收款单状态</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie
 * @version 1.0
 * @since 1.0
 */
public enum SyncStatusEnum {
    
	unsync(1,"未同步"),
	success(2,"同步成功"),
	fail(3,"同步失败");

	SyncStatusEnum(int code ,String name) {
        this.code = code;
        this.name = name;
    }

    public static SyncStatusEnum getByCode(int code){
    	for(final SyncStatusEnum ose : SyncStatusEnum.values()){
    		if(ose.getCode() == code){
    			return ose;
    		}
    	}
    	return null;
    }

    /** code */
    private int code;
    /** 名称 */
    private String name;
       
    public String getName() {
        return name;
    }

	public int getCode() {
		return code;
	}
    
}
