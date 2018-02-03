package com.ziroom.minsu.services.basedata.api.inner;



/**
 * 
 * <p>员工操作接口</p>
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
public interface EmployeeService {
	
	/**
	 * 
	 * 根据员工编码查询员工信息
	 *
	 * @author liujun
	 * @created 2016年7月7日
	 *
	 * @param empCode
	 * @return
	 */
	public String findEmployeeByEmpCode(String empCode);
	
	/**
	 * 
	 * 根据fid查询员工信息
	 *
	 * @author lunan
	 * @created 2016年8月19日 上午9:35:59
	 *
	 * @param empFid
	 * @return
	 */
	public String findEmployeByEmpFid(String empFid);
	/**
	 * 
	 * 根据条件查询员工信息
	 *
	 * @author liujun
	 * @created 2016年7月7日
	 *
	 * @param paramJson
	 * @return
	 */
	public String findEmployeeByCondition(String paramJson);
}
