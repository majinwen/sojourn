/**
 * @FileName: GuardAreaController.java
 * @Package com.ziroom.minsu.troy.guard.controller
 * 
 * @author yd
 * @created 2016年7月6日 下午2:08:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.troy.guard.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.entity.conf.GuardAreaEntity;
import com.ziroom.minsu.entity.conf.GuardAreaLogEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseGuardRelEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.GuardAreaService;
import com.ziroom.minsu.services.basedata.dto.GuardAreaLogRequest;
import com.ziroom.minsu.services.basedata.dto.GuardAreaRequest;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.house.api.inner.HouseGuardService;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;
import com.ziroom.minsu.services.house.dto.HouseGuardDto;
import com.ziroom.minsu.services.house.dto.HousePhyListDto;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.valenum.msg.IsDelEnum;

/**
 * <p>区域管家视图处理</p>
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
@Controller
@RequestMapping("guard")
public class GuardAreaController {
	private static final Logger LOGGER = LoggerFactory.getLogger(GuardAreaController.class);

	@Resource(name = "basedata.guardAreaService")
	private GuardAreaService guardAreaService;

	@Resource(name = "basedata.confCityService")
	private ConfCityService confCityService;
	
	@Resource(name = "house.houseGuardService")
	private HouseGuardService  houseGuardService;

	@Resource(name="house.troyHouseMgtService")
	private TroyHouseMgtService troyHouseMgtService;
	
	
	
	/**
	 * 
	 * 到区域管家列表
	 *
	 * @author yd
	 * @created 2016年7月6日 下午2:23:53
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/guardAreaIndex")
	public String guardAreaIndex(HttpServletRequest request){
		cascadeDistricts(request);
		return "/guard/guardList";
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
		String resultJson = confCityService.getConfCitySelectForAll();
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if(dto.getCode() == DataTransferObject.ERROR){
			LogUtil.error(LOGGER, "confCityService.getConfCitySelect接口调用失败,结果:{}", resultJson);
		}
		List<TreeNodeVo> cityTreeList = dto.parseData("list", new TypeReference<List<TreeNodeVo>>(){});
		System.err.println(JsonEntityTransform.Object2Json(cityTreeList));
		request.setAttribute("cityTreeList", JsonEntityTransform.Object2Json(cityTreeList));
	};

	/**
	 * 
	 * 区域管家列表
	 *
	 * @author yd
	 * @created 2016年7月6日 下午2:41:53
	 *
	 * @param request
	 * @param guardAreaRequest
	 * @returns
	 */
	@RequestMapping("/queryGaurdAreaByPage")
	public @ResponseBody PageResult queryGaurdAreaByPage(HttpServletRequest request,@ModelAttribute("guardAreaRequest") GuardAreaRequest guardAreaRequest){

		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(this.guardAreaService.findGaurdAreaByPage(JsonEntityTransform.Object2Json(guardAreaRequest)));
		List<GuardAreaEntity> listGuardArea = resultDto.parseData("listGuardArea", new TypeReference<List<GuardAreaEntity>>() {
		});
		//返回结果	
		PageResult pageResult = new PageResult();
		if(!Check.NuNObj(resultDto.getData().get("count"))){
			pageResult.setTotal(Long.valueOf(resultDto.getData().get("count").toString()));
		}
		listGuardArea = getListGuardArea(listGuardArea);
		pageResult.setRows(listGuardArea);
		return pageResult;
	}


	/**
	 * 
	 * 获取区域的集合
	 *
	 * @author yd
	 * @created 2016年7月7日 下午9:58:25
	 *
	 * @param listGuardArea
	 * @return
	 */
	private List<GuardAreaEntity>  getListGuardArea(List<GuardAreaEntity> listGuardArea ){
		if(!Check.NuNCollection(listGuardArea)){
			for (GuardAreaEntity guardAreaEntity : listGuardArea) {

				List<String> codeList = new ArrayList<String>();
				codeList.add(guardAreaEntity.getNationCode());
				codeList.add(guardAreaEntity.getProvinceCode());
				codeList.add(guardAreaEntity.getCityCode());
				codeList.add(guardAreaEntity.getAreaCode());
				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(confCityService.getCityNameByCodeList(JsonEntityTransform.Object2Json(codeList)));
				if(dto.getCode() == DataTransferObject.SUCCESS){
					List<ConfCityEntity> cityList = dto.parseData("cityList", new TypeReference<List<ConfCityEntity>>() {
					});
					if(!Check.NuNCollection(cityList)){
						Map<String, String> confMap = new HashMap<String, String>();
						for (ConfCityEntity confCityEntity : cityList) {
							confMap.put(confCityEntity.getCode(), confCityEntity.getShowName());
						}
						guardAreaEntity.setNationCode(confMap.get(guardAreaEntity.getNationCode()));
						guardAreaEntity.setProvinceCode(confMap.get(guardAreaEntity.getProvinceCode()));
						guardAreaEntity.setCityCode(confMap.get(guardAreaEntity.getCityCode()));
						guardAreaEntity.setAreaCode(confMap.get(guardAreaEntity.getAreaCode()));
					}
				}
			}
		}

		return listGuardArea;
	}
	/**
	 * 
	 * 保存区域管家信息
	 *
	 * @author yd
	 * @created 2016年7月7日 下午3:49:46
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveGuardArea")
	public @ResponseBody  DataTransferObject saveGuardArea(HttpServletRequest request,@ModelAttribute("guardAreaEntity") GuardAreaEntity guardAreaEntity){

		String usreFid = UserUtil.getCurrentUserFid();
		guardAreaEntity.setCreateFid(usreFid);
		if(Check.NuNStr(guardAreaEntity.getAreaCode())){
			guardAreaEntity.setAreaCode("0");
		}
		if(Check.NuNStr(guardAreaEntity.getProvinceCode())){
			guardAreaEntity.setProvinceCode("0");
		}
		if(Check.NuNStr(guardAreaEntity.getCityCode())){
			guardAreaEntity.setCityCode("0");
		}
		guardAreaEntity.setFid(UUIDGenerator.hexUUID());
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.guardAreaService.saveGuardArea(JsonEntityTransform.Object2Json(guardAreaEntity)));
		return dto;
	}

	/**
	 * 
	 * 根据fid修改区域管家
	 *
	 * 说明:这块在做修改时候要校验  当前管家 在否已经分配房源
	 * 1.如果 该管家已经管理房源了 不能修改  提示先解绑房源管家关系
	 * 2.如果该区域已经有此管家  提示该管家已经存在
	 * 2.否则可以修改成功
	 * @author yd
	 * @created 2016年7月7日 下午3:55:23
	 *
	 * @param request
	 * @param guardAreaEntitys
	 * @return
	 */
	@RequestMapping("/updateGuardArea")
	public @ResponseBody DataTransferObject updateGuardArea(HttpServletRequest request,@ModelAttribute("guardAreaEntity") GuardAreaEntity guardAreaEntity){

		String usreFid = UserUtil.getCurrentUserFid();
		DataTransferObject dto  = null;
		
		if(Check.NuNObj(guardAreaEntity)||Check.NuNStr(guardAreaEntity.getEmpCode())){
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("待修改对象不存在");
			return dto;
		}
		String flag = request.getParameter("flag")==null?"1":request.getParameter("flag");//1代表删除，2代表修改
		//删除 不做校验 当前维护管家是否有房源  （由国兰 提出  造成问题： 当前有该维护管家的房源  查不到该管家  需要国兰手动修改）
		if(flag.equals("1")){
			guardAreaEntity.setIsDel(IsDelEnum.DEL.getCode());
		}
		if(flag.equals("2")){
			
			//校验管家是否已经维护房源
			HouseGuardDto houseGuardDto  = new HouseGuardDto();
			List<String> listZoCode = new ArrayList<String>();
			listZoCode.add(guardAreaEntity.getEmpCode());
			//houseGuardDto.setZoCode(guardAreaEntity.getEmpCode());
			houseGuardDto.setListZoCode(listZoCode);
			dto = JsonEntityTransform.json2DataTransferObject(this.houseGuardService.findHouseGuardByCondition(JsonEntityTransform.Object2Json(houseGuardDto)));
			
			if(dto.getCode() == DataTransferObject.ERROR){
				return dto;
			}
			
			Object total = dto.getData().get("total");
			if(Check.NuNObj(total)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("查询房源维护管家异常");
				return dto;
			}
			if((int)total>0){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("编号为"+guardAreaEntity.getEmpCode()+"的管家已经维护有房源，不能修改");
				return dto;
			}
			if(Check.NuNStr(guardAreaEntity.getNationCode())){
				dto = new DataTransferObject();
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("国家不存在");
				return dto;
			}
			if(Check.NuNStr(guardAreaEntity.getAreaCode())){
				guardAreaEntity.setAreaCode("0");
			}
			if(Check.NuNStr(guardAreaEntity.getProvinceCode())){
				guardAreaEntity.setProvinceCode("0");
			}
			if(Check.NuNStr(guardAreaEntity.getCityCode())){
				guardAreaEntity.setCityCode("0");
			}
			guardAreaEntity.setIsDel(null);
		}
		 dto = JsonEntityTransform.json2DataTransferObject(this.guardAreaService.updateGuardAreaByFid(JsonEntityTransform.Object2Json(guardAreaEntity), usreFid));
		return dto;
	}


	/**
	 * 
	 * 条件查询区域管家信息历史记录
	 *
	 * @author yd
	 * @created 2016年7月7日 下午8:41:59
	 *
	 * @param request
	 * @param guardAreaRequest
	 * @return
	 */
	@RequestMapping("/findGaurdAreaLog")
	public @ResponseBody DataTransferObject findGaurdAreaLog(HttpServletRequest request){

		String empCode = request.getParameter("empCode");
		DataTransferObject resultDto  = null;
		if(!Check.NuNStr(empCode)){
			GuardAreaLogRequest guardAreaLRequest = new GuardAreaLogRequest();
			guardAreaLRequest.setGuardCode(empCode);
			resultDto = JsonEntityTransform.json2DataTransferObject(this.guardAreaService.queryGuardAreaLogByCondition(JsonEntityTransform.Object2Json(guardAreaLRequest)));
			List<GuardAreaLogEntity> listAreaEntities =  resultDto.parseData("listAreaEntities", new TypeReference<List<GuardAreaLogEntity>>() {
			});

			if(!Check.NuNCollection(listAreaEntities)){
				for (GuardAreaLogEntity guardAreaEntity : listAreaEntities) {

					List<String> codeList = new ArrayList<String>();
					codeList.add(guardAreaEntity.getOldNationCode());
					codeList.add(guardAreaEntity.getOldProvinceCode());
					codeList.add(guardAreaEntity.getOldCityCode());
					codeList.add(guardAreaEntity.getOldAreaCode());
					DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(confCityService.getCityNameByCodeList(JsonEntityTransform.Object2Json(codeList)));
					if(dto.getCode() == DataTransferObject.SUCCESS){
						List<ConfCityEntity> cityList = dto.parseData("cityList", new TypeReference<List<ConfCityEntity>>() {
						});
						if(!Check.NuNCollection(cityList)){
							Map<String, String> confMap = new HashMap<String, String>();
							for (ConfCityEntity confCityEntity : cityList) {
								confMap.put(confCityEntity.getCode(), confCityEntity.getShowName());
							}
							guardAreaEntity.setOldNationCode(confMap.get(guardAreaEntity.getOldNationCode()));
							guardAreaEntity.setOldProvinceCode(confMap.get(guardAreaEntity.getOldProvinceCode()));
							guardAreaEntity.setOldCityCode(confMap.get(guardAreaEntity.getOldCityCode()));
							guardAreaEntity.setOldAreaCode(confMap.get(guardAreaEntity.getOldAreaCode()));
						}
					}
				}
				resultDto.putValue("listGuardArea", listAreaEntities);
			}
		}else{
			resultDto = new DataTransferObject();
			resultDto.setErrCode(DataTransferObject.ERROR);
			resultDto.setMsg("参数不存在");
		}
		return resultDto;
	}

	/**
	 * 
	 * 根据fid 查询
	 *
	 * @author yd
	 * @created 2016年7月8日 下午2:37:19
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/findGaurdAreaByFid")
	public @ResponseBody DataTransferObject findGaurdAreaByFid(HttpServletRequest request){

		String fid = request.getParameter("fid");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.guardAreaService.findGuardAreaByFid(fid));
		return dto;
	}

	
	/**
	 * 处理老数据
	 * @author liyingjie
	 * @created 2016年7月8日 下午2:37:19
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateOldData")
	public @ResponseBody void updateOldData(HttpServletRequest request){

		String resultJson = confCityService.confCityTreeDataVo();
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<TreeNodeVo> nationList = resultDto.parseData("list", new TypeReference<List<TreeNodeVo>>() {
		});
		LogUtil.info(LOGGER, "nationList:{}", JsonEntityTransform.Object2Json(nationList));
		if(Check.NuNCollection(nationList)){
			return ;
		}
		TreeNodeVo chinaNation = nationList.get(0);
		LogUtil.info(LOGGER, "chinaNation:{}", JsonEntityTransform.Object2Json(chinaNation));
		List<Map<Integer, String>> result = new ArrayList<>();
		this.getInfo(result, new HashMap<Integer, String>(), chinaNation, 0);
		
		for (Map<Integer, String> map : result) {
			if(Check.NuNStr(map.get(new Integer(0)))){
				continue;
			}
			if(Check.NuNStr(map.get(new Integer(1)))){
				continue;
			}
			if(Check.NuNStr(map.get(new Integer(2)))){
				continue;
			}
			if(Check.NuNStr(map.get(new Integer(3)))){
				continue;
			}
			
			//获取数据list
			List<HouseBaseMsgEntity> dataList = new ArrayList<>();
			dataList = this.getHouseBaseInfo(map);
			LogUtil.info(LOGGER, "area:{},dataList:{}", map.get(new Integer(3)),JsonEntityTransform.Object2Json(dataList));
			//获取人list
			List<GuardAreaEntity> perList = new ArrayList<>();
			perList = this.getGuardPerInfo(map);
			LogUtil.info(LOGGER, "area:{},perList:{}", map.get(new Integer(3)),JsonEntityTransform.Object2Json(perList));
			//判断如果为空，则进行下一轮循环
			if(Check.NuNCollection(dataList) || Check.NuNCollection(perList)){
				continue;
			}
			
			//计算
			int dataSize = dataList.size();
			int perSize = perList.size();
			int m = dataList.size()/perList.size();
			int k = dataList.size()%perList.size();
			if(k>=1){
				m += 1;
			}
			for(int j=0;j<m;j++){//4次
				 for(int l=0;l<perSize;l++){
					  if(j*perSize+l < dataSize){
						  //如果fid还未被绑定，则执行插入操作
						 if(!checkIsBind(dataList.get(j*perSize+l).getFid())){
							//保存入库
							this.saveInfo(dataList.get(j*perSize+l),perList.get(l));
							LogUtil.info(LOGGER, "area:{},data:{},per:{}", map.get(new Integer(3)),JsonEntityTransform.Object2Json(dataList.get(j*perSize+l)),JsonEntityTransform.Object2Json(perList.get(l)));
						 }else{//执行更新操作
							 this.updateGuardInfo(dataList.get(j*perSize+l),perList.get(l));
							 LogUtil.info(LOGGER, "area:{},data:{},per:{}", map.get(new Integer(3)),dataList.get(j*perSize+l).getFid(),JsonEntityTransform.Object2Json(perList.get(l)));
						 }
					}
				 }
		     }
			

		}
		return ;
	}
	
	/**
	 * 递归获取城市
	 * @param str
	 * @param info
	 * @param obj
	 * @param depth
	 */
	private static void getInfo(List<Map<Integer, String>> str,Map<Integer, String> info, TreeNodeVo obj, Integer depth) {
		 Map<Integer, String> infoLocal = new HashMap<Integer, String>();
		 infoLocal.putAll(info);
		 infoLocal.put(depth, obj.getCode());
		
		if (Check.NuNCollection(obj.getNodes())) {
			str.add(infoLocal);
			return;
		}

		for (TreeNodeVo objB : obj.getNodes()) {
			getInfo(str, infoLocal, objB, depth + 1);
		}
		return;
	}
	
	/**
	 * 获取房源列表
	 * @param paramMap
	 * @return
	 */
	private List<HouseBaseMsgEntity> getHouseBaseInfo(Map<Integer, String> paramMap){
		HousePhyListDto request = new HousePhyListDto();
		request.setNationCode(paramMap.get(0));
		request.setProvinceCode(paramMap.get(1));
		request.setCityCode(paramMap.get(2));
		request.setAreaCode(paramMap.get(3));
		String resultJson = troyHouseMgtService.findHouseListByPhy(JsonEntityTransform.Object2Json(request));
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<HouseBaseMsgEntity> nationList = resultDto.parseData("list", new TypeReference<List<HouseBaseMsgEntity>>() {
		});
		return nationList;
	}
	
	/**
	 * 获取人员、区域列表
	 * @param paramMap
	 * @return
	 */
	private List<GuardAreaEntity> getGuardPerInfo(Map<Integer, String> paramMap){
		GuardAreaRequest guardAreaLRequest = new GuardAreaRequest();
		guardAreaLRequest.setNationCode(paramMap.get(0));
		guardAreaLRequest.setProvinceCode(paramMap.get(1));
		guardAreaLRequest.setCityCode(paramMap.get(2));
		guardAreaLRequest.setAreaCode(paramMap.get(3));
		
		String resultJson = guardAreaService.findByPhyCondition(JsonEntityTransform.Object2Json(guardAreaLRequest));
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<GuardAreaEntity> nationList = resultDto.parseData("list", new TypeReference<List<GuardAreaEntity>>() {
		});
		return nationList;
	}
	
	/**
	 * 保存维护信息
	 * @param paramMap
	 * @return
	 */
	private void saveInfo(HouseBaseMsgEntity hb,GuardAreaEntity ga){
		HouseGuardRelEntity guardAreaEntity = new HouseGuardRelEntity();
		
		guardAreaEntity.setFid(UUIDGenerator.hexUUID());
		guardAreaEntity.setHouseFid(hb.getFid());
		guardAreaEntity.setCreateFid("system");
		guardAreaEntity.setEmpGuardCode(ga.getEmpCode());
		guardAreaEntity.setEmpGuardName(ga.getEmpName());
		houseGuardService.saveHouseGuardRel(JsonEntityTransform.Object2Json(guardAreaEntity));
		
	}
	
	/**
	 * 保存维护信息
	 * @param paramMap
	 * @return
	 */
	private void updateGuardInfo(HouseBaseMsgEntity hb,GuardAreaEntity ga){
		HouseGuardRelEntity guardAreaEntity = new HouseGuardRelEntity();
		guardAreaEntity.setHouseFid(hb.getFid());
		guardAreaEntity.setEmpGuardCode(ga.getEmpCode());
		guardAreaEntity.setEmpGuardName(ga.getEmpName());
		houseGuardService.updateHouseGuardByHouseFid(JsonEntityTransform.Object2Json(guardAreaEntity));
		
	}
	
	
	/**
	 * 校验是否绑定过
	 * @param houseFid
	 * @return
	 */
	private boolean checkIsBind(String houseFid){
		String resultJson = houseGuardService.findHouseGuardRelByHouseBaseFid(houseFid);
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		HouseGuardRelEntity houseGuardRel = resultDto.parseData("houseGuardRel", new TypeReference<HouseGuardRelEntity>() {
		});
		boolean result = false;
		if(!Check.NuNObj(houseGuardRel)){
			result = true;
		}
		return result;
	}
}
