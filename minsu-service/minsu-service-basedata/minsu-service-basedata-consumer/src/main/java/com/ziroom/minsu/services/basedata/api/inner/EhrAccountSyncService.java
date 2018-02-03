/**
 * @FileName: EhrAccountSyncService.java
 * @Package com.ziroom.minsu.services.basedata.api.inner
 * 
 * @author jixd
 * @created 2016年4月23日 下午3:09:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.api.inner;

/**
 * <p>Ehr系统员工导入及tory系统账号创建</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public interface EhrAccountSyncService {
	/**
	 * 
	 * 同步ehr员工数据任务
	 *
	 * @author jixd
	 * @created 2016年4月23日 下午3:13:54
	 *
	 */
	void syncEmployeeTask(int diffday);
	/**
	 * 
	 * 根据员工号同步员工信息
	 *
	 * @author jixd
	 * @created 2016年6月6日 下午3:00:48
	 *
	 * @param empCode
	 */
	String syncSingleEmployee(String empCode);
	
	//void doEmployeeSync(String startDate,String endDate);
}
