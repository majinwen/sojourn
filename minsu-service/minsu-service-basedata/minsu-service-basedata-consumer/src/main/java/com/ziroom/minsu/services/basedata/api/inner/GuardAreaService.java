/**
 * @FileName: GuardAreaService.java
 * @Package com.ziroom.minsu.services.basedata.api.inner
 * 
 * @author yd
 * @created 2016年7月5日 下午6:36:17
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.api.inner;



/**
 * <p>区域管家服务提供</p>
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
public interface GuardAreaService {

	/**
	 * 
	 *  保存实体
	 *
	 * @author yd
	 * @created 2016年7月5日 下午6:37:19
	 *
	 * @param guardAreaEntity
	 * @return
	 */
	public String saveGuardArea(String guardAreaEntity);
	
	/**
	 * 
	 * 按 fid 修改 区域管家实体
	 *
	 * @author yd
	 * @created 2016年7月5日 下午6:39:19
	 * @param guardAreaEntity
	 * @return
	 */
	public String updateGuardAreaByFid(String guardAreaEntity,String logCreaterFid);
	
	/**
	 * 
	 * 分页查询区域管家
	 *
	 * @author yd
	 * @created 2016年7月5日 下午6:41:20
	 *
	 * @param guardAreaR
	 * @return
	 */
	public String findGaurdAreaByPage(String guardAreaR);
	
	/**
	 * 
	 * 条件查询 区域管家
	 *
	 * @author yd
	 * @created 2016年7月5日 下午6:43:27
	 *
	 * @param guardAreaR
	 * @return
	 */
	public String findGaurdAreaByCondition(String guardAreaR);
	
	/**
	 * 
	 * 录入房源 查询维护管家
	 *  算法:
	 * 1.入参 必须 有 国家 省 市 区
	 * 2. 查询区下的维护管家   有管家，取第一位管家，返回
	 * 3. 区下无管家 取市下的维护管家 有管家，取第一位管家，返回
	 * 4. 市下无管家 取省下的维护管家 有管家，取第一位管家，返回
	 * 5. 省下无管家 取国家下的维护管家，取第一位管家，返回
	 * 6. 国家下无管家 直接返回
	 * 7. 返回前 更新当前记录的更新时间
	 *
	 *
	 * @author yd
	 * @created 2016年7月5日 下午6:44:03
	 *
	 * @param guardAreaR
	 * @return
	 */
	public  String findGuardAreaByCode(String guardAreaR);
	
	 /**	
     * 
     * 根据fid 获取GuardAreaEntity
     *
     * @author yd
     * @created 2016年7月5日 下午7:12:07
     *
     * @param fid
     * @return
     */
	public String findGuardAreaByFid(String fid);
	/**
	 * 
	 * 条件查询
	 * 空条件 不让查询
	 *
	 * @author yd
	 * @created 2016年7月5日 下午4:37:52
	 *
	 * @return
	 */
	public String queryGuardAreaLogByCondition(String guardAreaLogRe);

	/**
	 * 
	 * 条件查询
	 * 空条件 不让查询
	 * @author liyingjie
	 * @return
	 */
	public String findByPhyCondition(String guardAreaLogRe);
}
