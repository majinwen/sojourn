package com.ziroom.minsu.ups.common.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.basedata.api.inner.OpLogService;
import com.ziroom.minsu.services.basedata.dto.OpLogRequest;
import com.ziroom.minsu.services.basedata.entity.OpLogVo;
import com.ziroom.minsu.services.common.page.PageResult;

/**
 * <p>操作记录</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp on 2017/5/9.
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/log")
public class OpLogController {

	@Resource(name = "basedata.opLogApi")
	private OpLogService opLogService;

	/**
	 *
	 * 到日志列表
	 *
	 * @author lusp
	 * @created 2017年5月9日 
	 *
	 * @param request
	 */
	@RequestMapping("/logList")
	public void toLogList(HttpServletRequest request) {
	}
	
	/**
	 *
	 * 获取日志列表 数据
	 *
	 * @author lusp
	 * @created 2017年5月9日  下午4:59:06
	 *
	 * @param request
	 */
	@RequestMapping("/logResDataList")
	@ResponseBody
	public PageResult getLogResDataList(@ModelAttribute("paramRequest") OpLogRequest paramRequest,HttpServletRequest request) {
		String resultJson = opLogService.findOpLogList(JsonEntityTransform.Object2Json(paramRequest));

		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<OpLogVo> opLogList = resultDto.parseData("list", new TypeReference<List<OpLogVo>>() {
		});
       //返回结果
		PageResult pageResult = new PageResult();
		pageResult.setRows(opLogList);
		pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
		return pageResult;
	}
	
	
}
