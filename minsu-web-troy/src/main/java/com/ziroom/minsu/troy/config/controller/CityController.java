package com.ziroom.minsu.troy.config.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.basedata.dto.ConfCityRequest;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.CoordinateTransforUtils;
import com.ziroom.minsu.services.common.utils.GoogleApiUtils;
import com.ziroom.minsu.services.common.utils.Gps;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.troy.constant.Constant;
import com.ziroom.minsu.valenum.base.MapTypeEnum;

/**
 *
 * <p>
 * 后台国家、省份、城市、区域配置controller
 * </p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("config/city")
public class CityController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CityController.class);

	@Resource(name = "basedata.confCityService")
	private ConfCityService confCityService;

	@Value("#{'${BaiDu_Geocoding_api}'.trim()}")
	private String BaiDu_Geocoding_api;

	@Autowired
	private RedisOperations redisOperations;


	@Resource(name="basedata.zkSysService")
	private ZkSysService zkSysService;

	// =======================配置资源 操作相关===========================//

	/**
	 * 进入--配置资源--列表页面
	 * 
	 * @created 2016年3月22日 
	 * @author liyingjie
	 */
	@RequestMapping(value = "/cityTree", method = RequestMethod.GET)
	public void toResourceList(HttpServletRequest request) {

		List<TreeNodeVo> treeList = getTreeVoList();
		request.setAttribute("treeview", JsonEntityTransform.Object2Json(treeList));
		//查询最顶层菜单 
		ConfCityRequest paramRequest = new ConfCityRequest();
		paramRequest.setLevel(0);
		String resultJson = confCityService.searchNodeListByFid(JsonEntityTransform.Object2Json(paramRequest));
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<ConfCityEntity> confCityList = resultDto.parseData("list", new TypeReference<List<ConfCityEntity>>() {
		});
		if(CollectionUtils.isNotEmpty(confCityList)){
			request.setAttribute("confCity", confCityList.get(0));
		}
	}

	/**
	 *
	 * 后台菜单--右侧--列表
	 *
	 * @author liyingjie
	 * @created 2016年3月9日 下午4:59:06
	 * @param request
	 */
	@RequestMapping("/confCityDatalist")
	public @ResponseBody
	PageResult dataList(@ModelAttribute("paramRequest") ConfCityRequest paramRequest,HttpServletRequest request) {


		String resultJson = confCityService.searchNodeListByFid(JsonEntityTransform.Object2Json(paramRequest));

		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<ConfCityEntity> confCityList = resultDto.parseData("list", new TypeReference<List<ConfCityEntity>>() {
		});
		//返回结果
		PageResult pageResult = new PageResult();
		pageResult.setRows(confCityList);
		pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));

		return pageResult;

	}

	/**
	 * 添加--资源--操作
	 * 
	 * @author liyingjie
	 * @created 2016年3月22日 
	 * @param request
	 */
	@RequestMapping("/addConfCityRes")
	@ResponseBody
	public DataTransferObject addData(@ModelAttribute ConfCityEntity confCity, HttpServletRequest request) {
		DataTransferObject dto = new DataTransferObject();
		try {
			//保存 实体
			ConfCityEntity cce = new ConfCityEntity();
			cce.setFid(UUIDGenerator.hexUUID());
			cce.setLevel(confCity.getLevel()+1);

			if (StringUtils.isNotBlank(confCity.getPcode())) {
				cce.setPcode(confCity.getPcode());;
			}
			if (StringUtils.isNotBlank(confCity.getShowName())) {
				cce.setShowName(confCity.getShowName());
				cce.setRegionName(confCity.getShowName());
			}
			if (StringUtils.isNotBlank(confCity.getCode())) {
				cce.setCode(confCity.getCode());
			}

			//向数据库保存数据
			String paramJson = JsonEntityTransform.Object2Json(cce);
			confCityService.insertConfCityRes(paramJson);

			try {
				//清除左侧城市树缓存
				redisOperations.del(RedisKeyConst.getCityConfKey("cityTree"));
			} catch (Exception e) {
				LogUtil.error(LOGGER,"redis错误e={}", e);
			}

			//保存后  更新列表左侧树
			List<TreeNodeVo> treeList = getTreeVoList();
			request.setAttribute("treeview", JsonEntityTransform.Object2Json(treeList));

			dto.putValue("treeview", JsonEntityTransform.Object2Json(treeList));

			return dto;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
	}

	/**
	 * 
	 * 获取菜单树--左侧树--方法封装
	 *
	 * @author liyingjie
	 * @created 2016年3月15日 下午9:57:23
	 *
	 */
	private List<TreeNodeVo> getTreeVoList(){
		String resultJson = confCityService.confCityTreeVo();
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<TreeNodeVo> treeList = resultDto.parseData("list", new TypeReference<List<TreeNodeVo>>() {
		});
		return treeList;
	}



	/**
	 * 资源--停用操作--导航
	 * 
	 * @author liyingjie
	 * @created 2016年3月9日 下午4:59:06
	 * @param request
	 * @return
	 */
	@RequestMapping("/changeStatus")
	@ResponseBody
	public DataTransferObject changeStatus(HttpServletRequest request,String showName,String selectedId,String selectedCode,Integer resStatus, Integer resLevel) {
		try {

			ConfCityEntity resourceEntity = new ConfCityEntity();
			resourceEntity.setFid(selectedId);
			resourceEntity.setCode(selectedCode);
			resourceEntity.setCityStatus(resStatus);
			resourceEntity.setLevel(resLevel);
			LogUtil.info(LOGGER, "【改变状态的参数】={}", JsonEntityTransform.Object2Json(resourceEntity));
			//如果开通状态 是关闭状态，并且开通层级是城市 且城市名字不为空，才取城市经纬度
			if(resStatus != 0 && resLevel == 3 && !Check.NuNStr(showName)){

				String mapType = zkSysService.getZkSysValue(EnumMinsuConfig.minsu_mapType.getType(), EnumMinsuConfig.minsu_mapType.getCode());
				if(MapTypeEnum.GOOGLE.getCode().equals(mapType)
						||MapTypeEnum.PC_GOOLGE_M_BAIDU.getCode().equals(mapType)){
					Gps gps = GoogleApiUtils.geocode(showName, 1);
					if(!Check.NuNObj(gps)){
						resourceEntity.setGoogleLatitude(gps.getWgLat());
						resourceEntity.setGoogleLongitude(gps.getWgLon());
						gps = CoordinateTransforUtils.wgs84_To_bd09(gps.getWgLat(), gps.getWgLon());
						if(!Check.NuNObj(gps)){
							resourceEntity.setLatitude(gps.getWgLat());
							resourceEntity.setLongitude(gps.getWgLon());
						}

					}
				}
				if(MapTypeEnum.BAIDU.getCode().equals(mapType)){
					String url = BaiDu_Geocoding_api.replace("CITYNAME", showName);
					String result = CloseableHttpUtil.sendGet(url, null);
					LogUtil.info(LOGGER, "【开通城市获取经纬度url】={}",url);
					LogUtil.info(LOGGER, "【开通城市获取经纬度结果】={}", result);
					JSONObject jsonObj = JSONObject.parseObject(result);
					Integer status = jsonObj.getInteger("status");
					if(status == 0){
						JSONObject resultJson = jsonObj.getJSONObject("result");
						JSONObject locationJson = resultJson.getJSONObject("location");
						//经度值
						String lng = locationJson.getString("lng");
						//纬度值
						String lat = locationJson.getString("lat");
						resourceEntity.setLatitude(Double.parseDouble(lat));
						resourceEntity.setLongitude(Double.parseDouble(lng));
						Gps gps = CoordinateTransforUtils.bd09_To_Gps84(resourceEntity.getLatitude(), resourceEntity.getLongitude());
						if(!Check.NuNObj(gps)){
							resourceEntity.setGoogleLatitude(gps.getWgLat());
							resourceEntity.setGoogleLongitude(gps.getWgLon());
						}
					}
				}
			}
			String resultJson = confCityService.updateConfCityByFid(JsonEntityTransform.Object2Json(resourceEntity));
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}

	}

	/**
	 *
	 * 后台菜单--右侧--列表
	 *
	 * @author liyingjie
	 * @created 2016年3月9日 下午4:59:06
	 * @param request
	 */
	@RequestMapping("/checkCodeUnique")
	@ResponseBody
	public DataTransferObject checkCodeUnique(@ModelAttribute("paramRequest") ConfCityRequest paramRequest,HttpServletRequest request) {
		try {
			//根据code 查询  是否有记录
			String resultJson = confCityService.searchNodeListByFid(JsonEntityTransform.Object2Json(paramRequest));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(resultDto.getCode() == DataTransferObject.ERROR){
				return resultDto;
			}

			List<ConfCityEntity> confCityList = resultDto.parseData("list", new TypeReference<List<ConfCityEntity>>(){});
			if(!Check.NuNCollection(confCityList)){
				resultDto.setErrCode(DataTransferObject.ERROR);
				resultDto.setMsg("您新增的城市配置项  code值已经存在");
			}
			return resultDto;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}

	}


	/**
	 * 
	 * 获取所有国家
	 *
	 * @author yd
	 * @created 2016-3-21 下午7:06:07
	 *
	 * @param request
	 */
	@RequestMapping("getNationInfo")
	public @ResponseBody List<ConfCityEntity>  getNationInfo(HttpServletRequest request) {

		String pcode = request.getParameter("pcode");
		//默认查询国家列表
		if(Check.NuNStr(pcode)){
			pcode = Constant.T_CONF_CITY_ROOT_CODE;
		}
		//初始化国家列表
		String resultJson = confCityService.searchDistricts(pcode);
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<ConfCityEntity> cityList = resultDto.parseData("list", new TypeReference<List<ConfCityEntity> >() {
		});
		return cityList;
	}


}
