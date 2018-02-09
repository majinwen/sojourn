/**
 * @FileName: HouseTopController.java
 * @Package com.ziroom.minsu.troy.house.controller
 * 
 * @author bushujie
 * @created 2017年3月17日 下午5:30:50
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.troy.house.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseTopColumnEntity;
import com.ziroom.minsu.entity.house.HouseTopEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.ConfTagService;
import com.ziroom.minsu.services.basedata.api.inner.StaticResourceService;
import com.ziroom.minsu.services.basedata.dto.ConfTagRequest;
import com.ziroom.minsu.services.basedata.dto.ConfTagVo;
import com.ziroom.minsu.services.basedata.entity.StaticResourceVo;
import com.ziroom.minsu.services.common.constant.StaticResConst;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.house.api.inner.HouseTopService;
import com.ziroom.minsu.services.house.dto.HouseTopDto;
import com.ziroom.minsu.services.house.dto.HouseTopSaveDto;
import com.ziroom.minsu.services.house.entity.HouseTopDetail;
import com.ziroom.minsu.services.house.entity.HouseTopListVo;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.valenum.house.ColumnStyleEnum;
import com.ziroom.minsu.valenum.house.ColumnTypeEnum;
import com.ziroom.minsu.valenum.house.IsValidEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0022Enum;

/**
 * <p>top房源管理</p>
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
@RequestMapping("house/houseTop")
public class HouseTopController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseTopController.class);
	
	@Resource(name="house.houseTopService")
	private HouseTopService houseTopService;
	
	@Resource(name = "basedata.confCityService")
	private ConfCityService confCityService;
	
	@Resource(name ="basedata.confTagService")
	private ConfTagService confTagService;
	
	@Resource(name="basedata.staticResourceService")
	private StaticResourceService staticResourceService;
	
	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;
	
	@Value("#{'${pic_size}'.trim()}")
	private String picSize;
	
	@Value("#{'${sample_top_house_url}'.trim()}")
	private String sample_top_house_url;
	
	/**
	 * 
	 * top房源列表
	 *
	 * @author bushujie
	 * @created 2017年3月17日 下午5:49:04
	 *
	 * @param request
	 * @throws SOAParseException 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("houseTopList")
	public void houseTopList(HttpServletRequest request) throws SOAParseException{
		String openCityJson=confCityService.getOpenCity();
		List<Map> openCityList=SOAResParseUtil.getListValueFromDataByKey(openCityJson, "list", Map.class);
		request.setAttribute("openCityList", openCityList);
		request.setAttribute("sample_top_house_url", sample_top_house_url);
	}
	
	/**
	 * 
	 * 获取top房源数据
	 *
	 * @author bushujie
	 * @created 2017年3月17日 下午6:00:48
	 *
	 * @param houseTopDto
	 * @return
	 * @throws SOAParseException
	 */
	@RequestMapping("houseTopListData")
	@ResponseBody
	public PageResult houseTopListData(HouseTopDto houseTopDto) throws SOAParseException{
		String resultJson=houseTopService.topHouseListData(JsonEntityTransform.Object2Json(houseTopDto));
		List<HouseTopListVo> list=SOAResParseUtil.getListValueFromDataByKey(resultJson, "dataList", HouseTopListVo.class);
		PageResult pageResult=new PageResult();
		pageResult.setRows(list);
		pageResult.setTotal(SOAResParseUtil.getLongFromDataByKey(resultJson, "count"));
		return pageResult;
	}
	
	/**
	 * 
	 * top房源添加页
	 *
	 * @author bushujie
	 * @created 2017年3月18日 下午3:13:39
	 *
	 * @param houseTopEntity
	 * @throws SOAParseException 
	 */
	@RequestMapping("topHouseAdd")
	public void topHouseAdd(HouseTopEntity houseTopEntity,HttpServletRequest request) throws SOAParseException{
		ConfTagRequest tagRequest=new ConfTagRequest();
		tagRequest.setTagType(ProductRulesEnum0022Enum.TAG_TOP50_HOUSE.getValue());
		tagRequest.setIsValid(IsValidEnum.WEEK_OPEN.getCode());
		String tagJson=confTagService.findByConfTagRequestList(JsonEntityTransform.Object2Json(tagRequest));
		List<ConfTagVo> tagList=SOAResParseUtil.getListValueFromDataByKey(tagJson, "list", ConfTagVo.class);
		Map<Integer, String> columnTypeEnum=ColumnTypeEnum.getEnumMap();
		Map<Integer, String> columnStyleEnum=ColumnStyleEnum.getEnumMap();
		request.setAttribute("tagList", tagList);
		request.setAttribute("columnTypeEnum", columnTypeEnum);
		request.setAttribute("columnStyleEnum", columnStyleEnum);
		request.setAttribute("rentWay", houseTopEntity.getRentWay());
		if(RentWayEnum.ROOM.getCode()==houseTopEntity.getRentWay()){
			request.setAttribute("houseBaseFid", houseTopEntity.getHouseBaseFid().split("\\,")[0]);
			request.setAttribute("roomFid", houseTopEntity.getHouseBaseFid().split("\\,")[1]);
		}else {
			request.setAttribute("houseBaseFid", houseTopEntity.getHouseBaseFid());
			request.setAttribute("roomFid", houseTopEntity.getRoomFid());
		}
		//查询标题集合
		String titleJson=staticResourceService.findStaticResListByResCode(StaticResConst.HosueTopCode.TOP_HOUSE_TITLE);
		List<StaticResourceVo> resList=SOAResParseUtil.getListValueFromDataByKey(titleJson, "staticResList", StaticResourceVo.class);
		request.setAttribute("resList", resList);
		//查询标题图片集合
		String titlePicJson=staticResourceService.findStaticResListByResCode(StaticResConst.HosueTopCode.TOP_HOUSE_TITLE_PIC);
		List<StaticResourceVo> titlePicList=SOAResParseUtil.getListValueFromDataByKey(titlePicJson, "staticResList", StaticResourceVo.class);
		LogUtil.info(LOGGER, "标题图片{}", JsonEntityTransform.Object2Json(titlePicList));
		request.setAttribute("titlePicList", titlePicList);
		//查询中部图片集合
		String middlePicJson=staticResourceService.findStaticResListByResCode(StaticResConst.HosueTopCode.TOP_HOUSE_MIDDLE_PIC);
		List<StaticResourceVo> middlePicList=SOAResParseUtil.getListValueFromDataByKey(middlePicJson, "staticResList", StaticResourceVo.class);
		LogUtil.info(LOGGER, "中部图片{}", JsonEntityTransform.Object2Json(titlePicList));
		request.setAttribute("middlePicList", middlePicList);
		//图片访问地址和大小
		request.setAttribute("picBaseAddrMona", picBaseAddrMona);
		request.setAttribute("picSize", picSize);
	}
	
	/**
	 * 
	 * top房源内容修改
	 *
	 * @author bushujie
	 * @created 2017年3月18日 下午3:33:31
	 *
	 * @param fid
	 * @param request
	 * @throws SOAParseException 
	 */
	@RequestMapping("topHouseUpdate")
	public void topHouseUpdate(String fid,HttpServletRequest request) throws SOAParseException{
		String resultJson=houseTopService.houseTopDetail(fid);
		HouseTopDetail houseTopDetail=SOAResParseUtil.getValueFromDataByKey(resultJson, "houseTopDetail", HouseTopDetail.class);
		request.setAttribute("houseTopDetail", houseTopDetail);
		//类型、样式、标签
		ConfTagRequest tagRequest=new ConfTagRequest();
		tagRequest.setTagType(ProductRulesEnum0022Enum.TAG_TOP50_HOUSE.getValue());
		tagRequest.setIsValid(IsValidEnum.WEEK_OPEN.getCode());
		String tagJson=confTagService.findByConfTagRequestList(JsonEntityTransform.Object2Json(tagRequest));
		List<ConfTagVo> tagList=SOAResParseUtil.getListValueFromDataByKey(tagJson, "list", ConfTagVo.class);
		Map<Integer, String> columnTypeEnum=ColumnTypeEnum.getEnumMap();
		Map<Integer, String> columnStyleEnum=ColumnStyleEnum.getEnumMap();
		request.setAttribute("tagList", tagList);
		request.setAttribute("columnTypeEnum", columnTypeEnum);
		request.setAttribute("columnStyleEnum", columnStyleEnum);
		//查询标题集合
		String titleJson=staticResourceService.findStaticResListByResCode(StaticResConst.HosueTopCode.TOP_HOUSE_TITLE);
		List<StaticResourceVo> resList=SOAResParseUtil.getListValueFromDataByKey(titleJson, "staticResList", StaticResourceVo.class);
		request.setAttribute("resList", resList);
		//查询标题图片集合
		String titlePicJson=staticResourceService.findStaticResListByResCode(StaticResConst.HosueTopCode.TOP_HOUSE_TITLE_PIC);
		List<StaticResourceVo> titlePicList=SOAResParseUtil.getListValueFromDataByKey(titlePicJson, "staticResList", StaticResourceVo.class);
		request.setAttribute("titlePicList", titlePicList);
		//查询中部图片集合
		String middlePicJson=staticResourceService.findStaticResListByResCode(StaticResConst.HosueTopCode.TOP_HOUSE_MIDDLE_PIC);
		List<StaticResourceVo> middlePicList=SOAResParseUtil.getListValueFromDataByKey(middlePicJson, "staticResList", StaticResourceVo.class);
		request.setAttribute("middlePicList", middlePicList);
		//图片访问地址和大小
		request.setAttribute("picBaseAddrMona", picBaseAddrMona);
		request.setAttribute("picSize", picSize);
		StringBuilder sb= new StringBuilder();
		for(int i=0;i<houseTopDetail.getTagFidList().size();i++){
			if(i==0){
				sb.append(houseTopDetail.getTagFidList().get(i));
			}else{
				sb.append(",").append(houseTopDetail.getTagFidList().get(i));
			}
		}
		request.setAttribute("tagArray", sb.toString());
		if(!Check.NuNCollection(houseTopDetail.getTopColumnList())){
			request.setAttribute("sortStart", houseTopDetail.getTopColumnList().get(houseTopDetail.getTopColumnList().size()-1).getColumnSort());
		} else {
			request.setAttribute("sortStart", 0);
		}
	}
	
	/**
	 * 
	 * top房源上下线操作
	 *
	 * @author bushujie
	 * @created 2017年3月18日 下午3:38:39
	 *
	 * @param fid
	 */
	@RequestMapping("upDownTopHouse")
	@ResponseBody
	public DataTransferObject upDownTopHouse(String fid){
		HouseTopSaveDto houseTopSaveDto=new HouseTopSaveDto();
		houseTopSaveDto.setFid(fid);
		houseTopSaveDto.setCreateFid(UserUtil.getCurrentUserFid());
		houseTopSaveDto.setEmpCode(UserUtil.getFullCurrentUser().getEmpCode());
		houseTopSaveDto.setEmpName(UserUtil.getFullCurrentUser().getFullName());
		String resultJson=houseTopService.houseTopOnlineOrDownline(JsonEntityTransform.Object2Json(houseTopSaveDto));
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}
	
	/**
	 * 
	 * 保存top房源内容
	 *
	 * @author bushujie
	 * @created 2017年3月20日 下午2:09:04
	 *
	 * @return
	 */
	@RequestMapping("saveTopHouse")
	@ResponseBody
	public DataTransferObject saveTopHouse(HouseTopSaveDto houseTopSaveDto){
		houseTopSaveDto.setCreateFid(UserUtil.getCurrentUserFid());
		houseTopSaveDto.setEmpCode(UserUtil.getFullCurrentUser().getEmpCode());
		houseTopSaveDto.setEmpName(UserUtil.getFullCurrentUser().getFullName());
		String resultJson =houseTopService.insertTopHouse(JsonEntityTransform.Object2Json(houseTopSaveDto));
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}
	
	/**
	 * 
	 * 更新top房源内容
	 *
	 * @author bushujie
	 * @created 2017年3月20日 下午2:12:35
	 *
	 * @return
	 */
	@RequestMapping("updateTopHouse")
	@ResponseBody
	public DataTransferObject updateTopHouse(HouseTopSaveDto houseTopSaveDto){
		houseTopSaveDto.setCreateFid(UserUtil.getCurrentUserFid());
		houseTopSaveDto.setEmpCode(UserUtil.getFullCurrentUser().getEmpCode());
		houseTopSaveDto.setEmpName(UserUtil.getFullCurrentUser().getFullName());
		String resultJson =houseTopService.updateHouseTop(JsonEntityTransform.Object2Json(houseTopSaveDto));
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}
	
	/**
	 * 
	 * 删除top房源条目
	 *
	 * @author bushujie
	 * @created 2017年3月23日 下午9:08:05
	 *
	 * @param fid
	 * @return
	 */
	@RequestMapping("deleteTopHouseColumn")
	@ResponseBody
	public DataTransferObject deleteTopHouseColumn(String fid){
		HouseTopColumnEntity houseTopColumnEntity =new HouseTopColumnEntity();
		houseTopColumnEntity.setFid(fid);
		houseTopColumnEntity.setIsDel(1);
		String resultJson=houseTopService.updateHouseTopColumn(JsonEntityTransform.Object2Json(houseTopColumnEntity));
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}
}
