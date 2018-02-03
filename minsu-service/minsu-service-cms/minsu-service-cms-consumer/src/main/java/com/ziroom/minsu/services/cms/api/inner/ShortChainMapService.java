package com.ziroom.minsu.services.cms.api.inner;


/**
 * 
 * <p>短链操作接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public interface ShortChainMapService {
	
	/**
	 * 
	 * 根据给定长链接返回短链
	 *
	 * @author liujun
	 * @created 2016年10月27日
	 *
	 * @param originalLink
	 * @param createId
	 * @return
	 */
	public String generateShortLink(String originalLink, String createId);
	
	
	/**
	 * 
	 * 根据短链编号查询短链信息
	 *
	 * @author liujun
	 * @created 2016年10月27日
	 *
	 * @param uniqueCode
	 * @return
	 */
	public String findShortChainMapByUniqueCode(String uniqueCode);


	/**
	 * 获取短信跳转到房源列表的短连接
	 *
	 * @author loushuai
	 * @created 2017年12月22日 下午3:47:40
	 *
	 * @return
	 */
	public String getMinsuHomeJump();
}

