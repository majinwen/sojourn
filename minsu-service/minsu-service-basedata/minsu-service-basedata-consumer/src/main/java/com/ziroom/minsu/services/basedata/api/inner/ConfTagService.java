/**
 * @FileName: ConfTagService.java
 * @Package com.ziroom.minsu.services.basedata.api.inner
 * 
 * @author zl 
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.api.inner;

 
/**
 * 
 * <p>标签</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public interface ConfTagService {
	
	/**
	 * 查询标签列表
	 * TODO
	 *
	 * @author zl
	 * @created 2017年1月10日 下午3:16:08
	 *
	 * @param paramJson
	 * @return
	 */
	public String findByConfTagRequest(String paramJson);

	/**
	 * 查询标签列表
	 *
	 * @author lunan
	 * @created 2017年3月17日
	 *
	 * @param paramJson
	 * @return
	 */
	public String findByConfTagRequestList(String paramJson);

	/**
	 * 添加标签
	 * TODO
	 *
	 * @author zl
	 * @created 2017年1月10日 下午3:17:02
	 *
	 * @param paramJson
	 * @return
	 */
	public String  addConfTag(String paramJson);
	
	/**
	 * 修改标签名称
	 * TODO
	 *
	 * @author zl
	 * @created 2017年1月10日 下午3:17:45
	 *
	 * @param paramJson
	 * @return
	 */
	public String modifyTagName(String paramJson);
	
	
	/**
	 * 修改标签状态
	 * TODO
	 *
	 * @author zl
	 * @created 2017年1月10日 下午3:18:24
	 *
	 * @param paramJson
	 * @return
	 */
	public String modifyTagStatus(String paramJson);
	
	
	
}
