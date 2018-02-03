/**
 * @FileName: HouseMessageConst.java
 * @Package com.ziroom.minsu.services.house.constant
 * 
 * @author bushujie
 * @created 2016年4月3日 上午11:53:36
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.constant;

import com.ziroom.minsu.services.common.constant.MessageConst;

/**
 * <p>房源相关静态配置常量</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class CustomerMessageConst extends MessageConst{
	
	//手机验证失败
	public static final String MOBILENO_AUTH_ERROR="mobileno.auth.error";
	
	//uid不能为空
	public static final String UID_NULL_ERROR="uid.null";

    //电话不能为空
    public static final String PHONE_NULL_ERROR="phone.null";

    //当前用户不存在
    public static final String USER_NULL_ERROR="user.null";

    //状态不能为空
    public static final String STATUS_NULL_ERROR="status.null";
    
    //用户默认图片地址
    public static final String USER_DEFAULT_PIC_URL="/mapp/images/user.png";
    
    /**
     * 客户房源收藏唯一标示不能为空
     */
    public static final String CUSTOMER_COLLECTION_FID_NULL="customer.collection.fid.null";
    
    /**
     * 房源出租方式不能为空
     */
    public static final String HOUSE_RENTWAY_NULL="house.rentWay.null";
    
    /**
     * 房源或房间唯一标示不能为空
     */
    public static final String HOUSE_HOUSEFID_NULL="house.houseFid.null";
	
	/**
	 * 私有构造方法
	 */
	private CustomerMessageConst(){
		
	}

}
