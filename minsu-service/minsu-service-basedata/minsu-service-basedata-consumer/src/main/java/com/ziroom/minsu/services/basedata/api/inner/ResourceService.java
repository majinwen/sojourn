/**
 * @FileName: MenuOperService.java
 * @Package com.ziroom.minsu.services.basedata.api.inner
 * 
 * @author liyingjie
 * @created 2016年3月9日 上午11:22:55
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.api.inner;


/**
 * <p>
 * 后台菜单操作接口
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
public interface ResourceService {



	/**
	 * 更新菜单信息
	 * @param paramJson
	 * @return
	 */
	String updateMenuByFid(String paramJson);


	/**
	 * 
	 * 条件查询菜单列表
	 *
	 * @author liyingjie
	 * @created 2016年3月9日 上午11:26:09
	 *
	 * @param paramJson
	 * @return
	 */
	public String searchMenuResList(String paramJson);

	/**
	 * 
	 * 新增 菜单 数据
	 *
	 * @author liyingjie
	 * @created 2016年3月11日 下午8:26:21
	 *
	 * @param paramJson
	 */
	public void insertMenuResource(String paramJson);

	/**
	 * 查询所有的菜单和其子节点
	 *
	 * @author liyingjie
	 * @created 2016年3月11日 下午10:48:13
	 *
	 * @return
	 */
	public String searchAllMenuChildResList();
	
	/**
	 * 
	 * 资源树结构查询
	 *
	 * @author bushujie
	 * @created 2016年3月13日 下午7:11:16
	 *
	 * @return
	 */
	public String menuTreeVo();
	
}
