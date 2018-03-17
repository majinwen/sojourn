/*
 * Copyright (c) 2016. Copyright (c) 2016. ziroom.com.
 */
package com.ziroom.minsu.ups.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.services.basedata.api.inner.EmployeeService;
import com.ziroom.minsu.ups.service.ICurrentUserService;
import com.ziroom.minsu.ups.service.impl.CurrentUserServiceImpl;

import org.jasig.cas.client.validation.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.common.utils.SentinelJedisUtil;
import com.ziroom.minsu.ups.common.constant.Constant;
import com.ziroom.minsu.ups.common.util.UserUtil;

/**
 * 
 * <p>自动根据单点登录系统的信息设置本系统的用户信息</p>
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
public class AutoSetUserAdapterFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(AutoSetUserAdapterFilter.class);

	private WebApplicationContext webApplicationContext;
	
	private SentinelJedisUtil sentinelJedisClient;


	public AutoSetUserAdapterFilter() {
	}

	public void destroy() {
	}

	/**
	 * 过滤逻辑：首先判断单点登录的账户是否已经存在本系统中，
	 * 如果不存在使用用户查询接口查询出用户对象并设置在Session中
	 *
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		// 当前登录用户
		CurrentuserEntity currentuserEntity= UserUtil.getCurrentUser();
		// currentLoginUserName为空时是未登录状态，否则为登录状态
		if (null == currentuserEntity) {
			// _const_cas_assertion_是CAS中存放登录用户名的session标志
			Object object = httpRequest.getSession().getAttribute(Constant.CONST_CAS_ASSERTION);
			// 判断CAS存到客户端session中的用户名是否为空
			if (null != object) {
				Assertion assertion = (Assertion) object;
				String loginName = assertion.getPrincipal().getName();
				//存入缓存登录信息
				LogUtil.info(logger,"首次存入cach登录信息："+loginName);
				sentinelJedisClient.setex(Constant.USER_NAME_CACHE_KEY+loginName, Constant.USER_NAME_TIME, loginName);
				//服务查询登录用户信息
				ICurrentUserService currentUserService = webApplicationContext.getBean(CurrentUserServiceImpl.class);
				EmployeeService employeeService = webApplicationContext.getBean(EmployeeService.class);
				CurrentuserVo currentuserVo=currentUserService.getCurrentuserByUserAccount(loginName);
				try{
					if(!Check.NuNObj(currentuserVo)){
						String employeeJson=employeeService.findEmployeByEmpFid(currentuserVo.getEmployeeFid());
						EmployeeEntity employeeEntity=SOAResParseUtil.getValueFromDataByKey(employeeJson, "employee", EmployeeEntity.class);
						if(!Check.NuNObj(employeeEntity)){
							currentuserVo.setEmpCode(employeeEntity.getEmpCode());
							currentuserVo.setFullName(employeeEntity.getEmpName());
						}
					}
				}catch (Exception e){
					LogUtil.error(logger,"登陆错误={}",e);
				}
				// 第一次登录系统
				// 保存用户信息到Session
				UserUtil.setUser2Session(currentuserVo);
			}
		}else{
			LogUtil.info(logger, "更新登录信息缓存时间："+currentuserEntity.getUserAccount());
			sentinelJedisClient.setex(Constant.USER_NAME_CACHE_KEY, Constant.USER_NAME_TIME, currentuserEntity.getUserAccount());
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
		try {
			sentinelJedisClient = (SentinelJedisUtil) webApplicationContext.getBean("sentinelJedisClient");
		} catch (Exception e) {
			LogUtil.error(logger,"redis错误e={}", e);
		}

	}

}
