package com.ziroom.minsu.ups.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.sys.SystemsEntity;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.ups.dto.SystemsRequest;
import com.ziroom.minsu.ups.service.ISystemService;

/**
 * 
 * <p>系统信息controller</p>
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
@RequestMapping("/system")
public class SystemController {
	
	@Resource(name="ups.systemService")
	private ISystemService systemService;
	
	/**
	 * 
	 * 添加系统信息页
	 *
	 * @author bushujie
	 * @created 2016年12月1日 下午5:44:55
	 *
	 * @param request
	 */
	@RequestMapping("insertSystemPage")
	public void  insertSystemPage(HttpServletRequest request){
		
	}
	
	/**
	 * 
	 * 插入系统信息
	 *
	 * @author bushujie
	 * @created 2016年12月2日 上午9:52:12
	 *
	 * @param systemsEntity
	 */
	@RequestMapping("insertSystem")
	@ResponseBody
	public DataTransferObject insertSystem(SystemsEntity systemsEntity){
		DataTransferObject dto=new DataTransferObject();
		systemsEntity.setFid(UUIDGenerator.hexUUID());
		systemService.insertSystem(systemsEntity);
		return dto;
	}
	
	/**
	 * 
	 * 系统信息列表页
	 *
	 * @author bushujie
	 * @created 2016年12月2日 上午9:59:16
	 *
	 * @param request
	 */
	@RequestMapping("systemList")
	public void systemList(HttpServletRequest request){
		
	}
	
	/**
	 * 
	 * 分页查询系统信息
	 *
	 * @author bushujie
	 * @created 2016年12月2日 上午10:34:06
	 *
	 * @param systemsRequest
	 * @return
	 */
	@RequestMapping("findSystemList")
	@ResponseBody
	public PageResult findSystemList(SystemsRequest systemsRequest){
		PagingResult<SystemsEntity> pageList=systemService.findSystemsByPage(systemsRequest);
		PageResult pageResult=new PageResult();
		pageResult.setRows(pageList.getRows());
		pageResult.setTotal(pageList.getTotal());
		return pageResult;
	}
	
	/**
	 * 
	 * 更新系统信息
	 *
	 * @author bushujie
	 * @created 2016年12月2日 下午4:28:51
	 *
	 * @param fid
	 * @param request
	 */
	@RequestMapping("updateSystemPage")
	public void updateSystemPage(String fid,HttpServletRequest request){
		SystemsEntity systemsEntity=systemService.getSystemsEntityByFid(fid);
		request.setAttribute("system", systemsEntity);
	}
	
	/**
	 * 
	 * 更新系统信息
	 *
	 * @author bushujie
	 * @created 2016年12月2日 下午5:01:18
	 *
	 * @param systemsEntity
	 * @return
	 */
	@RequestMapping("updateSystem")
	@ResponseBody
	public DataTransferObject updateSystem(SystemsEntity systemsEntity){
		DataTransferObject dto=new DataTransferObject();
		int upNum=systemService.updateSystem(systemsEntity);
		dto.putValue("upNum", upNum);
		return dto;
	}
}
