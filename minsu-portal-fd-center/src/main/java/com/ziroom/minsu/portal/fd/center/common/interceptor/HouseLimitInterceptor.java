/**
 * @FileName: HouseLimitInterceptor.java
 * @Package com.ziroom.minsu.portal.fd.center.common.interceptor
 * 
 * @author bushujie
 * @created 2016年8月25日 下午6:06:33
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.portal.fd.center.common.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.portal.fd.center.common.utils.UserUtils;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;

/**
 * <p>房源访问权限拦截器</p>
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
public class HouseLimitInterceptor extends HandlerInterceptorAdapter{
	
	@Resource(name="house.houseIssueService")
	private HouseIssueService houseIssueService;
	
	/**
	 * 拦截请求
	 * @throws SOAParseException 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		String uri=request.getRequestURI();
		String uid=UserUtils.getCurrentUid();
		if(!Check.NuNStr(uri)){
			String[] uriArr=uri.split("/");
			String resultJson=houseIssueService.searchHouseBaseMsgByFid(uriArr[3]);
			HouseBaseMsgEntity houseBaseMsg=SOAResParseUtil.getValueFromDataByKey(resultJson, "obj", HouseBaseMsgEntity.class);
			if(Check.NuNObj(houseBaseMsg)||!uid.equals(houseBaseMsg.getLandlordUid())){
				response.sendRedirect("/house/lanHouseList");
				return false;
			}
		}
		return true;
	}
}
