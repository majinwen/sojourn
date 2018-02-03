/**
 * @FileName: CustomerInfoService.java
 * @Package com.ziroom.minsu.services.customer.api.inner
 * 
 * @author bushujie
 * @created 2016年4月25日 上午10:15:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.api.inner;

import java.util.Map;

/**
 * <p>客户中心相关业务接口</p>
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
public interface CustomerInfoService {


	/**
	 * 获取当前用户的基本信息和角色信息
	 * @author afi
	 * @param uid
	 * @return
	 */
	public String getCustomerRoleInfoByUid(String uid);
	/**
	 * 
	 * 插入客户基本信息
	 *
	 * @author bushujie
	 * @created 2016年4月25日 上午11:38:10
	 *
	 * @param paramJson
	 * @return
	 */
	public String insertCustomerInfo(String paramJson);



    /**
     * 更新房东的电话信息
     * @author afi
     * @param phone
     * @param uid
     * @return
     */
    String updateLandPhone(String phone,String uid, String areaCode);

	
	/**
	 * 
	 * 更新客户基本信息
	 *
	 * @author bushujie
	 * @created 2016年4月25日 上午11:38:28
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateCustomerInfo(String paramJson);
	
	/**
	 * 
	 * 插入客户发票抬头信息
	 *
	 * @author bushujie
	 * @created 2016年4月25日 上午11:38:47
	 *
	 * @param paramJson
	 * @return
	 */
	public String insertCustomerInvoice(String paramJson);
	
	/**
	 * 
	 * 插入客户银行卡信息
	 *
	 * @author bushujie
	 * @created 2016年4月25日 上午11:48:33
	 *
	 * @param paramJson
	 * @return
	 */
	public String insertCustomerBankcard(String paramJson);


    /**
     * 从数据库获取银行卡信息
     * @author afi
     * @param uid
     * @return
     */
    String getCustomerBankCardDbByUid(String uid);
    
    
    /**
     * 从数据库获取银行卡信息
     * 通过fid,uid
     * @author lishaochuan
     * @create 2016年8月16日下午6:58:41
     * @param fid
     * @param uid
     * @return
     */
    String getCustomerBankCardDbByFid(String fid, String uid);
	
	 /**
	  * 
	  * 获取客户银行卡信息
	  *
	  * @author bushujie
	  * @created 2016年4月25日 上午11:57:05
	  *
	  * @param uid
	  * @return
	  */
	public String getCustomerBankcard(String uid);
	
	/**
	 * 
	 * 更新客户银行卡信息
	 *
	 * @author bushujie
	 * @created 2016年4月25日 下午5:18:21
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateCustomerBankcard(String paramJson);
	
	/**
	 * 
	 * uid查询客户基本信息
	 *
	 * @author bushujie
	 * @created 2016年4月26日 下午5:01:04
	 *
	 * @param uid
	 * @return
	 */
	public String getCustomerInfoByUid(String uid);
	
	
	/**
	 * 根据uidList查询客户基本信息List
	 * @author lishaochuan
	 * @create 2016年8月9日下午6:08:24
	 * @param uidList
	 * @return
	 */
	public String getCustomerListByUidList(String json);


    /**
     * 获取当前用户的基本信息和角色信息
     * @author afi
     * @param uid
     * @return
     */
    String getCustomerAndRoleInfoByUid(String uid);

	
	/**
	 * 
	 * 条件查询用户基本信息（条件不让为null 为null后就直接查全库了）
	 * 
	 * @author yd
	 * @created 2016年5月10日 下午5:36:54
	 *
	 * @param customerDto
	 * @return
	 */
	public String selectByCondition(String customerBaseDtoStr);
	  /**
     * 
     * 条件查询 返回数量
     *
     * @author yd
     * @created 2016年5月10日 下午3:55:36
     *
     * @param smsAuthLogDto
     * @return
     */
    public String getSmsAuthLogCountByCondition(String smsAuthLogDtoStr);
    
    /**
     * 
     * 手机号查询用户信息
     *
     * @author bushujie
     * @created 2016年7月11日 下午4:42:30
     *
     * @param mobile
     * @return
     */
    public String getCustomerByMobile(String mobile);
    
    /**
     * 
     * 手机号查询所有用户的信息
     *
     * @author lunan
     * @created 2016年10月8日 下午9:55:49
     *
     * @param mobile
     * @return
     */
    public String getCustomerListByMobile(String mobile);


    /**
     * 分页获取业主列表
     * @author liyingjie
     * @created 2016年7月11日 下午4:42:30
     * @param param
     * @return
     */
	public String staticsGetLandlordList(String param);


	/**
     * 统计业主数量
     * @author liyingjie
     * @created 2016年7月11日 下午4:42:30
     * @param param
     * @return
     */
	public String countLanlordNum(String reuest);
	
	/**
	 * 
	 * 更新房东手机号码（新接口）
	 *
	 * @author bushujie
	 * @created 2017年4月13日 上午11:39:46
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateLandPhoneNew(String paramJson);
	
	/**
	 * 
	 * 根据手机号和名称获取用户uid
	 *
	 * @author loushuai
	 * @created 2017年5月25日 下午1:49:48
	 *
	 * @return
	 */
	
	public String getByCustomNameAndTel(String paramJson);
	/**
	 * 根据条件获取所有需要审核的字段最新信息
	 *
	 * @author loushuai
	 * @created 2017年8月8日 下午3:33:30
	 *
	 * @param object2Json
	 * @return
	 */
	public String getFieldAuditNewLogByParam(String paramJson);
	/**
	 * 
	 * 获取最新修改且尚未审核的照片
	 *
	 * @author loushuai
	 * @created 2017年8月8日 下午5:08:40
	 *
	 * @param headPicMap
	 * @return
	 */
	public String getLatestUnAuditHeadPic(String paramJson);
	
	/**
	 * 获取所有需要审核的房东列表
	 *
	 * @author loushuai
	 * @created 2017年8月29日 下午9:51:23
	 *
	 * @return
	 */
	public String getAllNeedAuditLand(String paramJson);
	/**
	 * 获取当前用户的基本信息及是否是在30天内审核通过的
	 *
	 * @author loushuai
	 * @created 2017年10月29日 下午12:35:26
	 *
	 * @param landlordUid
	 * @return
	 */
	public String getCustomerSearchVoByUid(String landlordUid);
}
