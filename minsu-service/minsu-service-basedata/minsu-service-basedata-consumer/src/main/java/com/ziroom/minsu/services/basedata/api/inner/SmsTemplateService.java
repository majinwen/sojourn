/**
 * @FileName: SmsTemplateService.java
 * @Package com.ziroom.minsu.services.basedata.api.inner
 * 
 * @author yd
 * @created 2016年4月1日 下午3:07:51
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.api.inner;


/**
 * <p>短信模板服务层</p>
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
public interface SmsTemplateService {



    /**
     * 通过模板id获取模板
     * @author afi
     * @param templateCode
     * @return
     */
    String getTemplateByCode(String templateCode);

	/**
	 * 
	 * 条件分页查询
	 *
	 * @author yd
	 * @created 2016年4月1日 下午3:08:31
	 *
	 * @param smsTemplateRequest
	 * @return
	 */
	public String findEntityByCondition(String smsTemplateRequest);
	
	/**
	 * 
	 * 按id查询
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:29:55
	 *
	 * @param id
	 * @return
	 */
	public String findEntityById(String fid);
	/**
	 * 
	 * 按id删除
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:34:18
	 *
	 * @param id
	 */
	public String deleteEntityById(String fid);
	/**
	 * 
	 * 按fid查询
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:29:55
	 *
	 * @param fid
	 * @return
	 */
	public String findEntityByFid(String fid);
	/**
	 * 
	 * 按fid删除
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:34:18
	 *
	 * @param id
	 */
	public String deleteEntityByFid(String fid);
	/**
	 * 
	 * 按Fid更新
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:45:17
	 *
	 * @param Fid
	 */
	public String updateEntityById(String smsTemplateEntity);
	/**
	 * 
	 * 按id更新
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:45:17
	 *
	 * @param id
	 */
	public String updateEntityByFid(String smsTemplateEntity);
	
	
	/**
	 * 
	 * 保存实体
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:47:47
	 *
	 * @param smsTemplateEntity
	 */
	public String saveEntity(String smsTemplateEntity);	
	/**
	 * 
	 * 按fid查询
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:29:55
	 *
	 * @param id
	 * @returnfindEntityBySmsCode
	 */
	public String findEntityBySmsCode(String smsCode);
	/**
	 * 
	 * 按模板code发送(说明：要到消息模板中添加模板，并在枚举中添加code值，code值不能重复)
	 * 1.校验
	 * 2.按code查询模板，并获取模板内容
	 * 3.发送
	 *
	 * @author yd
	 * @created 2016年5月10日 上午12:29:03
	 *
	 * @param smsRequest
	 * @return
	 */
	public String sendSmsByCode(String smsRequestStr);
	/**
	 * 
	 * 按照模板code推送
	 *
	 * @author yd
	 * @created 2016年5月11日 下午1:55:47
	 *
	 * @param jpushRequestStr
	 * @return
	 */
	public String jpushByCode(String jpushRequestStr);
	
	/**
	 * 
	 * 按模板发送邮件
	 *
	 * @author bushujie
	 * @created 2017年4月22日 下午4:55:20
	 *
	 * @param emailRequestStr
	 * @return
	 */
	public String sendEmailByCode(String emailRequestStr);

}
