package com.ziroom.minsu.services.house.api.inner;


/**
 * 
 * <p>摄影师管理操作接口</p>
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
public interface PhotogMgtService {
	
	/**
	 * 
	 * 分页查询摄影师信息
	 *
	 * @author liujun
	 * @created 2016年11月7日
	 *
	 * @param paramJson
	 * @return
	 */
	public String findPhotographerListByPage(String paramJson);

	/**
	 * 新增摄影师信息
	 * 1.摄影师基本信息(非空)
	 * 2.摄影师扩展信息
	 *
	 * @author liujun
	 * @created 2016年11月7日
	 *
	 * @param paramJson
	 * @return
	 */
	public String insertPhotographerMsg(String paramJson);

	/**
	 * 根据摄影师uid查询信息
	 * 1.摄影师基本信息
	 * 2.摄影师扩展信息
	 * 3.摄影师图片信息
	 *
	 * @author liujun
	 * @created 2016年11月8日
	 *
	 * @param photographerUid
	 * @return
	 */
	public String findPhotographerMsgByUid(String photographerUid);

	/**
	 * 修改摄影师信息
	 * 1.摄影师基本信息(非空)
	 * 2.摄影师扩展信息(为空则不修改)
	 *
	 * @author liujun
	 * @created 2016年11月8日
	 *
	 * @param paramJson
	 * @return
	 */
	public String updatePhotographerMsg(String paramJson);

	/**
	 * 根据uid和picType查询摄影师图片
	 *
	 * @author liujun
	 * @created 2016年11月9日
	 *
	 * @param paramJson
	 * @return
	 */
	public String findPhotogPicByUidAndType(String paramJson);

	/**
	 * 修改摄影师图片信息
	 *
	 * @author liujun
	 * @created 2016年11月9日
	 *
	 * @param paramJson
	 * @return
	 */
	public String updatePhotographerPicMsg(String paramJson);

	/**
	 * 新增摄影师图片信息
	 *
	 * @author liujun
	 * @created 2016年11月9日
	 *
	 * @param paramJson
	 * @return
	 */
	public String insertPhotographerPicMsg(String paramJson);
}
