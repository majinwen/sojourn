/**
 * @FileName: TableauController.java
 * @Package com.ziroom.minsu.tableau
 * 
 * @author bushujie
 * @created 2017年12月19日 上午10:27:50
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.tableau;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ziroom.minsu.tableau.common.TableauTrustedUtil;

/**
 * <p>自如寓报表</p>
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
@Controller
@RequestMapping("tableau/zru")
public class ZruTableauController {
	
	@Value("#{'${tableau_user}'.trim()}")
	private String tableauUser;
	
	@Value("#{'${tableau_wgserver}'.trim()}")
	private String tableauWgserver;
	
	/**
	 * 
	 * 运营统计报表
	 *
	 * @author bushujie
	 * @throws ServletException 
	 * @created 2017年12月19日 上午10:32:50
	 *
	 */
	@RequestMapping("operationTable")
	public String operationTable(HttpServletRequest request) throws ServletException{
		String baseUrl="http://10.16.25.13/#/views/_1/1?:iid=1";
		String ticket=TableauTrustedUtil.getTrustedTicket(tableauWgserver, tableauUser, request.getRemoteAddr());
		String targetUrl=baseUrl.replaceAll("#", "trusted/"+ticket);
		return "redirect:"+targetUrl; 
	}
	
	/**
	 * 
	 * 财务统计
	 *
	 * @author bushujie
	 * @created 2017年12月20日 下午3:14:28
	 *
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	@RequestMapping("financeTable")
	public String financeTable(HttpServletRequest request) throws ServletException{
		String baseUrl="http://10.16.25.13/#/views/_1/1?:iid=1";
		String ticket=TableauTrustedUtil.getTrustedTicket(tableauWgserver, tableauUser, request.getRemoteAddr());
		String targetUrl=baseUrl.replaceAll("#", "trusted/"+ticket);
		return "redirect:"+targetUrl; 
	}
	
	/**
	 * 
	 * 出房统计
	 *
	 * @author bushujie
	 * @created 2017年12月20日 下午3:15:48
	 *
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	@RequestMapping("outHouseTable")
	public String outHouseTable(HttpServletRequest request) throws ServletException{
		String baseUrl="http://10.16.25.13/#/views/_1/1?:iid=1";
		String ticket=TableauTrustedUtil.getTrustedTicket(tableauWgserver, tableauUser, request.getRemoteAddr());
		String targetUrl=baseUrl.replaceAll("#", "trusted/"+ticket);
		return "redirect:"+targetUrl; 
	}
	
	
	/**
	 * 
	 * 新签分布
	 *
	 * @author bushujie
	 * @created 2017年12月20日 下午3:17:12
	 *
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	@RequestMapping("newSignTable")
	public String newSignTable(HttpServletRequest request) throws ServletException{
		String baseUrl="http://10.16.25.13/#/views/_1/1?:iid=1";
		String ticket=TableauTrustedUtil.getTrustedTicket(tableauWgserver, tableauUser, request.getRemoteAddr());
		String targetUrl=baseUrl.replaceAll("#", "trusted/"+ticket);
		return "redirect:"+targetUrl; 
	}
	
	/**
	 * 
	 * 续约分布
	 *
	 * @author bushujie
	 * @created 2017年12月20日 下午3:19:15
	 *
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	@RequestMapping("renewalTable")
	public String renewalTable(HttpServletRequest request) throws ServletException{
		String baseUrl="http://10.16.25.13/#/views/_1/1?:iid=1";
		String ticket=TableauTrustedUtil.getTrustedTicket(tableauWgserver, tableauUser, request.getRemoteAddr());
		String targetUrl=baseUrl.replaceAll("#", "trusted/"+ticket);
		return "redirect:"+targetUrl; 
	}
	
	/**
	 * 
	 * 退租分布
	 *
	 * @author bushujie
	 * @created 2017年12月20日 下午3:23:24
	 *
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	@RequestMapping("surrenderTable")
	public String surrenderTable(HttpServletRequest request) throws ServletException{
		String baseUrl="http://10.16.25.13/#/views/_1/1?:iid=1";
		String ticket=TableauTrustedUtil.getTrustedTicket(tableauWgserver, tableauUser, request.getRemoteAddr());
		String targetUrl=baseUrl.replaceAll("#", "trusted/"+ticket);
		return "redirect:"+targetUrl; 
	}
	
	/**
	 * 
	 *销控总揽
	 *
	 * @author bushujie
	 * @created 2017年12月20日 下午3:25:36
	 *
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	@RequestMapping("pinControlOverview")
	public String pinControlOverview(HttpServletRequest request) throws ServletException{
		String baseUrl="http://10.16.25.13/#/views/_1/1?:iid=1";
		String ticket=TableauTrustedUtil.getTrustedTicket(tableauWgserver, tableauUser, request.getRemoteAddr());
		String targetUrl=baseUrl.replaceAll("#", "trusted/"+ticket);
		return "redirect:"+targetUrl; 
	}
	
	/**
	 * 
	 *  空置明细
	 *
	 * @author bushujie
	 * @created 2017年12月20日 下午3:28:29
	 *
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	@RequestMapping("vacancyDetail")
	public String vacancyDetail(HttpServletRequest request) throws ServletException{
		String baseUrl="http://10.16.25.13/#/views/_1/1?:iid=1";
		String ticket=TableauTrustedUtil.getTrustedTicket(tableauWgserver, tableauUser, request.getRemoteAddr());
		String targetUrl=baseUrl.replaceAll("#", "trusted/"+ticket);
		return "redirect:"+targetUrl; 
	}
	
	/**
	 * 
	 * 商机分布和转化
	 *
	 * @author bushujie
	 * @created 2017年12月20日 下午3:31:14
	 *
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	@RequestMapping("businessDisTable")
	public String businessDisTable(HttpServletRequest request) throws ServletException{
		String baseUrl="http://10.16.25.13/#/views/_1/1?:iid=1";
		String ticket=TableauTrustedUtil.getTrustedTicket(tableauWgserver, tableauUser, request.getRemoteAddr());
		String targetUrl=baseUrl.replaceAll("#", "trusted/"+ticket);
		return "redirect:"+targetUrl; 
	}
	
	/**
	 * 
	 * 客服数据
	 *
	 * @author bushujie
	 * @created 2017年12月20日 下午3:33:39
	 *
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	@RequestMapping("serviceDataTable")
	public String Table(HttpServletRequest request) throws ServletException{
		String baseUrl="http://10.16.25.13/#/views/_1/1?:iid=1";
		String ticket=TableauTrustedUtil.getTrustedTicket(tableauWgserver, tableauUser, request.getRemoteAddr());
		String targetUrl=baseUrl.replaceAll("#", "trusted/"+ticket);
		return "redirect:"+targetUrl; 
	}
	
	/**
	 * 
	 *  入住人数据
	 *
	 * @author bushujie
	 * @created 2017年12月20日 下午3:36:06
	 *
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	@RequestMapping("checkinPersonTable")
	public String checkinPersonTable(HttpServletRequest request) throws ServletException{
		String baseUrl="http://10.16.25.13/#/views/_1/1?:iid=1";
		String ticket=TableauTrustedUtil.getTrustedTicket(tableauWgserver, tableauUser, request.getRemoteAddr());
		String targetUrl=baseUrl.replaceAll("#", "trusted/"+ticket);
		return "redirect:"+targetUrl; 
	}
	
	/**
	 * 
	 * 价格统计
	 *
	 * @author bushujie
	 * @created 2017年12月20日 下午3:37:06
	 *
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	@RequestMapping("priceTable")
	public String priceTable(HttpServletRequest request) throws ServletException{
		String baseUrl="http://10.16.25.13/#/views/_1/1?:iid=1";
		String ticket=TableauTrustedUtil.getTrustedTicket(tableauWgserver, tableauUser, request.getRemoteAddr());
		String targetUrl=baseUrl.replaceAll("#", "trusted/"+ticket);
		return "redirect:"+targetUrl; 
	}
	
	/**
	 * 
	 * 账款统计
	 *
	 * @author bushujie
	 * @created 2017年12月20日 下午3:39:15
	 *
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	@RequestMapping("creditTable")
	public String creditTable(HttpServletRequest request) throws ServletException{
		String baseUrl="http://10.16.25.13/#/views/_1/1?:iid=1";
		String ticket=TableauTrustedUtil.getTrustedTicket(tableauWgserver, tableauUser, request.getRemoteAddr());
		String targetUrl=baseUrl.replaceAll("#", "trusted/"+ticket);
		return "redirect:"+targetUrl; 
	}
}
