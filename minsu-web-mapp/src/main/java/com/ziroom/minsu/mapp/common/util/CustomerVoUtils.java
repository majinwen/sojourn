
package com.ziroom.minsu.mapp.common.util;

import javax.servlet.http.HttpServletRequest;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.mapp.common.constant.MappMessageConst;
import com.ziroom.minsu.services.customer.entity.CustomerVo;

/**
 * <p>处理用户信息的工具类</p>
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
public class CustomerVoUtils {

	
	/**
	 * 
	 * 从session中获取CustomerVo
	 *
	 * @author yd
	 * @created 2016年5月14日 上午11:18:13
	 *
	 * @param request
	 * @return
	 */
	public static CustomerVo getCusotmerVoFromSesstion(HttpServletRequest request){
		
		CustomerVo customerVo = null;
		if(Check.NuNObj(request)){
			return customerVo;
		}
		Object object = request.getSession().getAttribute(MappMessageConst.SESSION_USER_KEY);
	
		if(object!=null){
			customerVo = (CustomerVo) object;
			return customerVo;
		}
		return customerVo;
	}
}
