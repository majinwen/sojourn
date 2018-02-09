package com.ziroom.minsu.troy.config.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.google.gson.JsonObject;
import com.ziroom.minsu.entity.conf.SubwayLineEntity;
import com.ziroom.minsu.entity.conf.SubwayOutEntity;
import com.ziroom.minsu.entity.conf.SubwayStationEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.SubwayLineService;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.basedata.dto.SubwayLineRequest;
import com.ziroom.minsu.services.basedata.entity.SubwayLineVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.valenum.base.MapTypeEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 地铁配置管理
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 */
@Controller
@RequestMapping("config/subway")
public class SubwayController {
	
	@Value("#{'${minsu.static.resource.url}'.trim()}")
	private String staticResourceUrl;
	
	@Resource(name = "basedata.confCityService")
	private ConfCityService confCityService;
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(SubwayController.class);
	
	@Resource(name = "basedata.subwayLineService")
	private SubwayLineService subwayLineService;
	
	
	@Resource(name="basedata.zkSysService")
	private ZkSysService zkSysService;
	
	/**
	 * 地铁信息管理页面
	 * @author lishaochuan
	 * @create 2016年5月19日下午3:57:56
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("listSubway")
	public String listSubway(HttpServletRequest request,HttpServletResponse response) {
		cascadeDistricts(request);
		request.setAttribute("staticResourceUrl", staticResourceUrl);
		return "/config/subway/listSubway";
	}
	
	/**
	 * 查询地铁信息
	 * @author lishaochuan
	 * @create 2016年5月19日下午3:59:11
	 * @param subwayLineRequest
	 * @return
	 */
	@RequestMapping("showSubway")
	@ResponseBody
	public PageResult showSubway(SubwayLineRequest subwayLineRequest) {
		String resultJson = subwayLineService.findSubwayLinePage(JsonEntityTransform.Object2Json(subwayLineRequest));
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<SubwayLineVo> subwayList = resultDto.parseData("list", new TypeReference<List<SubwayLineVo>>() {});
		PageResult pageResult = new PageResult();
		pageResult.setRows(subwayList);
		pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
		return pageResult;
	}
	
	/**
	 * 跳转添加地铁信息页面
	 * @author lishaochuan
	 * @create 2016年5月19日下午3:59:34
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("addSubway")
	public String addSubway(HttpServletRequest request,HttpServletResponse response) {
		cascadeDistricts(request);
		request.setAttribute("staticResourceUrl", staticResourceUrl);
		return "/config/subway/addSubway";
	}
	

	/**
	 * 保存地铁信息
	 * @author lishaochuan
	 * @create 2016年5月19日下午4:00:35
	 * @param subway
	 * @return
	 */
	@RequestMapping("saveSubway")
	@ResponseBody
	public DataTransferObject saveSubway(String subway) {
		String resultJson = subwayLineService.saveSubwayLine(subway);
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}
	
	
	/**
	 * 跳转编辑地铁信息页面
	 * @author lishaochuan
	 * @create 2016年5月19日下午4:01:23
	 * @param request
	 * @param lineFid
	 * @param stationFid
	 * @param outFid
	 * @return
	 */
	@RequestMapping("toEditSubway")
	public String toEditSubway(HttpServletRequest request, String lineFid, String stationFid, String outFid) {
		JsonObject json = new JsonObject();
		json.addProperty("lineFid", lineFid);
		json.addProperty("stationFid", stationFid);
		json.addProperty("outFid", outFid);
		
		
		
		String subwayJson = subwayLineService.findSubwayInfo(json.toString());
		
		DataTransferObject subwayDto = JsonEntityTransform.json2DataTransferObject(subwayJson);
		SubwayLineEntity subwayLine = subwayDto.parseData("subwayLine", new TypeReference<SubwayLineEntity>() {});
		request.setAttribute("subwayLine", subwayLine);
		SubwayStationEntity subwayStation = subwayDto.parseData("subwayStation", new TypeReference<SubwayStationEntity>() {});
		request.setAttribute("subwayStation", subwayStation);
		SubwayOutEntity subwayOut = subwayDto.parseData("subwayOut", new TypeReference<SubwayOutEntity>() {});
		request.setAttribute("subwayOut", subwayOut);
		
		cascadeDistricts(request);
		
		return "/config/subway/editSubway";
	}
	
	/**
	 * 保存修改地铁信息
	 * @author lishaochuan
	 * @create 2016年5月19日下午4:02:37
	 * @param subway
	 * @return
	 */
	@RequestMapping("updateSubway")
	@ResponseBody
	public DataTransferObject updateSubway(String subway) {
		String resultJson = subwayLineService.updateSubwayLine(subway);
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}
	
	
	/**
	 * 跳转到地图页面
	 * @author lishaochuan
	 * @create 2016年5月19日下午4:04:25
	 * @param request
	 * @return
	 */
	@RequestMapping("toMap")
	public String toMap(HttpServletRequest request) {
		String stationName = request.getParameter("stationName");
		try {
			String mapType = zkSysService.getZkSysValue(EnumMinsuConfig.minsu_mapType.getType(), EnumMinsuConfig.minsu_mapType.getCode());
			/*byte[]  bytes = stationName.getBytes("ISO-8859-1");
			String name=new String(bytes,"utf-8");*/
			LogUtil.info(logger, "跳转到地图页面stationName={}",stationName);
			request.setAttribute("stationName", stationName);
			if(MapTypeEnum.GOOGLE.getCode().equals(mapType)
					||MapTypeEnum.PC_GOOLGE_M_BAIDU.getCode().equals(mapType)){
				return "/config/subway/mapGoogle";
			}
		} catch (Exception e) {
			LogUtil.error(logger, "跳转到地图页面异常stationName={},e={}",stationName, e);
		}
		return "/config/subway/map";
	}

	/**
	 * 跳转到地图找房页面
	 * @author lishaochuan
	 * @create 2016年5月19日下午4:04:25
	 * @param request
	 * @return
	 */
	@RequestMapping("toHouseMap")
	public String toHouseMap(HttpServletRequest request) {
		String stationName = "";
		try {
			String mapType = zkSysService.getZkSysValue(EnumMinsuConfig.minsu_mapType.getType(), EnumMinsuConfig.minsu_mapType.getCode());
			/*byte[]  bytes = stationName.getBytes("ISO-8859-1");
			String name=new String(bytes,"utf-8");*/
			LogUtil.info(logger, "跳转到地图页面stationName={}",stationName);
			request.setAttribute("stationName", stationName);
//			if(MapTypeEnum.GOOGLE.getCode().equals(mapType)
//					||MapTypeEnum.PC_GOOLGE_M_BAIDU.getCode().equals(mapType)){
//				return "/config/subway/mapGoogle";
//			}
		} catch (Exception e) {
			LogUtil.error(logger, "跳转到地图页面异常stationName={},e={}",stationName, e);
		}
		return "/house/houseSearch/houseSearchList";
	}

	/**
	 * 
	 * 获取行政区域列表
	 *
	 * @author liujun
	 * @created 2016年5月7日
	 *
	 * @param request
	 */
	private void cascadeDistricts(HttpServletRequest request) {
		String resultJson = confCityService.getConfCitySelect();
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<TreeNodeVo> cityTreeList = resultDto.parseData("list", new TypeReference<List<TreeNodeVo>>() {});
		request.setAttribute("cityTreeList", JsonEntityTransform.Object2Json(cityTreeList));
	};
}
