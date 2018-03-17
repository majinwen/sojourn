/**
 * @FileName: CityFileController.java
 * @Package com.ziroom.minsu.activity.controller
 * 
 * @author bushujie
 * @created 2016年11月17日 下午2:59:55
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.activity.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSON;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.minsu.activity.common.encrypt.EncryptFactory;
import com.ziroom.minsu.activity.common.encrypt.IEncrypt;
import com.ziroom.minsu.entity.cms.ColumnCityEntity;
import com.ziroom.minsu.entity.cms.ColumnRegionItemEntity;
import com.ziroom.minsu.entity.cms.ColumnRegionPicEntity;
import com.ziroom.minsu.entity.cms.ColumnTemplateEntity;
import com.ziroom.minsu.entity.file.FileRegionItemsEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityArchiveService;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.HotRegionService;
import com.ziroom.minsu.services.basedata.entity.RegionExtVo;
import com.ziroom.minsu.services.cms.api.inner.CityFileService;
import com.ziroom.minsu.services.cms.entity.ColumnRegionUpVo;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.HtmlGenerator;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import com.ziroom.minsu.services.search.dto.HouseInfoRequest;
import com.ziroom.minsu.services.search.dto.HouseListRequset;
import com.ziroom.minsu.services.search.dto.HouseSearchRequest;
import com.ziroom.minsu.services.search.entity.HouseInfoEntity;
import com.ziroom.minsu.valenum.city.CityRulesEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * <p>城市档案性格接口</p>
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
@RequestMapping("/cityfileCp")
@Controller
public class CityFileController {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(CityFileController.class);

	public static String APIHOSTS = "http://bnb.api.ziroom.com";   //API接口根地址
	//收藏与取消收藏
	public static final String COLLECTHOUSE = APIHOSTS+"/customerColl/ea61d2/collectHouse";  //房源收藏
	public static final String UNCOLLECTHOUSE = APIHOSTS+"/customerColl/ea61d2/unCollectHouse";  //房源取消收藏

	public static final String DESKEY = "2y5QfvAy";  //接口key
	public static final String SIGNKEY = "hPtJ39Xs"; //接口key



	//最大收藏数量
	public static final int CUSTOMER_COLL_MAX_NUM = 30;

	@Resource(name="cms.cityFileService")
	private CityFileService cityFileService;

	@Resource(name="basedata.hotRegionService")
	private HotRegionService hotRegionService;

	@Resource(name="basedata.cityArchiveService")
	private CityArchiveService cityArchiveService;

	@Resource(name = "house.houseManageService")
	private HouseManageService houseManageService;

	@Resource(name="search.searchServiceApi")
	private SearchService searchService;

	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;

	@Value("#{'${pic_size}'.trim()}")
	private String picSize;

	@Value("#{'${CREAT_HTML_URL}'.trim()}")
	private String creatHtmlUrl;

	@Value("#{'${ITEM_HTML_TPL}'.trim()}")
	private String itemHtmlTpl;

	@Value("#{'${pic_base_addr}'.trim()}")
	private String picBaseAddr;

	@Value("#{'${PIC_JUMP_URL}'.trim()}")
	private String PIC_JUMP_URL;

	@Value("#{'${CITY_FILE_HOUSE_API}'.trim()}")
	private String CITY_FILE_HOUSE_API;

	@Value("#{'${CITY_FILE_REGION_HOUSE_API}'.trim()}")
	private String CITY_FILE_REGION_HOUSE_API;

	@Resource(name="basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

	@Value("#{'${AC_INTERTFACES_URL}'.trim()}")
	private String AC_INTERTFACES_URL;

	/**
	 * 
	 * 静态页面生成
	 *
	 * @author bushujie
	 * @created 2016年11月16日 下午6:28:00
	 *
	 * @param fid
	 * @throws Exception 
	 */
	@RequestMapping(value="createColumnRegionHtml",headers = "Accept=*/*",produces="application/json" )
	@ResponseBody
	public DataTransferObject createColumnRegionHtml(String fid,HttpServletRequest request) throws Exception{
		DataTransferObject dto=new DataTransferObject();
		String staticResourceUrl=(String) request.getAttribute("staticResourceUrl");
		try{
			//查询城市专栏
			String cityJson=cityFileService.getColumnCityByFid(fid);
			ColumnCityEntity columnCityEntity=SOAResParseUtil.getValueFromDataByKey(cityJson, "columnCity", ColumnCityEntity.class);
			//查询专栏模板
			String templateJson=cityFileService.getColumnTemplateByFid(columnCityEntity.getTempFid());
			ColumnTemplateEntity template=SOAResParseUtil.getValueFromDataByKey(templateJson, "template", ColumnTemplateEntity.class);
			//查询专栏下景点商圈列表
			String regionJson=cityFileService.findColumnRegionUpVoListByCityFid(columnCityEntity.getFid());
			List<ColumnRegionUpVo> regionList=SOAResParseUtil.getListValueFromDataByKey(regionJson, "list", ColumnRegionUpVo.class);
			//生成景点商圈页面
			for(ColumnRegionUpVo vo:regionList){
				String pageHtml=HtmlGenerator.createHtmlPage(template.getTempUrl());
				//替换参数
				StringBuffer carouselHtml=new StringBuffer();
				for(ColumnRegionPicEntity pic: vo.getPicList()){
					if(pic.getPicType()==1){
						carouselHtml.append("<div><img src='");
						carouselHtml.append(picBaseAddrMona).append(pic.getPicBaseUrl()).append(pic.getPicSuffix()).append(picSize).append(pic.getPicSuffix());
						carouselHtml.append("'></div>");
					}
				}
				//替换轮播图
				pageHtml=pageHtml.replace("$carousel", carouselHtml.toString());
				//查询景点商圈基础信息
				String baseRegionJson=hotRegionService.getRegionExtVoByRegionFid(vo.getRegionFid());
				RegionExtVo regionExtVo=SOAResParseUtil.getValueFromDataByKey(baseRegionJson, "regionExtVo", RegionExtVo.class);
				Integer radii=regionExtVo.getRadii();
				if(radii==null||radii==0){
					String radiiJson=cityTemplateService.getTextValue(null, CityRulesEnum.CityRulesEnum001.getValue());
					radii=SOAResParseUtil.getIntFromDataByKey(radiiJson, "textValue");
				}
				//替换商圈景点名称
				pageHtml=pageHtml.replace("$radii", radii+"");
				pageHtml=pageHtml.replace("$regionName", regionExtVo.getRegionName());
				pageHtml=pageHtml.replace("$regionContent", regionExtVo.getHotRegionContent());
				//替换推荐项目
				StringBuffer itemsHtml=new StringBuffer();
				StringBuffer itemBriefHtml=new StringBuffer();
				for(ColumnRegionItemEntity item:vo.getItemList()){
					//查询推荐项目基础信息
					String itemJson=cityArchiveService.getRegionItem(item.getRegionItemsFid());
					FileRegionItemsEntity itemsEntity=JsonEntityTransform.json2Entity(itemJson, FileRegionItemsEntity.class);
					//生成推荐项目内容页面
					String itemHtml=HtmlGenerator.createHtmlPage(itemHtmlTpl);
					itemHtml=itemHtml.replaceAll("\\$itemBrief", itemsEntity.getItemBrief());
					itemHtml=itemHtml.replace("$itemContent", itemsEntity.getItemContent());
					itemHtml=itemHtml.replace("$shareUrl", PIC_JUMP_URL+columnCityEntity.getFid()+"/"+vo.getFid()+"/"+item.getFid()+".html");
					itemHtml=itemHtml.replace("$sharePic", picBaseAddr+item.getIconBaseUrl()+item.getIconSuffix());
					itemHtml=itemHtml.replace("$shareTitle", columnCityEntity.getColShareTitle());
					itemHtml=itemHtml.replaceAll("\\$staticResourceUrl", staticResourceUrl);
					itemHtml=itemHtml.replaceAll("\\$version", DateUtil.dateFormat(new Date(), "yyyyMMddHHmmss"));
					HtmlGenerator.writeHtml(creatHtmlUrl+columnCityEntity.getFid()+"/"+vo.getFid()+"/"+item.getFid()+".html", itemHtml);
					//生成推荐项目内容页面结束
					itemsHtml.append("<li><img src='").append(picBaseAddr).append(item.getIconBaseUrl()).append(item.getIconSuffix());
					itemsHtml.append("'><span>").append(itemsEntity.getItemName()).append("</span></li>");
					itemBriefHtml.append(" <ul><li><p>").append(itemsEntity.getItemAbstract()).append("</p><div class='btns'>");
					itemBriefHtml.append("<a href='").append(PIC_JUMP_URL).append(columnCityEntity.getFid()).append("/").append(vo.getFid()).append("/").append(item.getFid()).append(".html'");
					itemBriefHtml.append(" class='more'>查看详情</a></div>");
					itemBriefHtml.append("</li></ul>");
				}
				//替换推荐项目
				pageHtml=pageHtml.replace("$regionItems", itemsHtml.toString());
				pageHtml=pageHtml.replace("$itemBriefHtml", itemBriefHtml.toString());
				pageHtml=pageHtml.replaceAll("\\$staticResourceUrl", staticResourceUrl);
				pageHtml=pageHtml.replaceAll("\\$version", DateUtil.dateFormat(new Date(), "yyyyMMddHHmmss"));
				//替换页面上的ajax参数
				pageHtml=pageHtml.replace("$hotRegionName", regionExtVo.getRegionName());
				pageHtml=pageHtml.replace("$houseSnList", vo.getHouseFids());
				pageHtml=pageHtml.replace("$houseNum", vo.getShowHouseNum().toString());
				pageHtml=pageHtml.replace("$findHouseUrl", CITY_FILE_HOUSE_API);
				pageHtml=pageHtml.replace("$findRegionHouseUrl", CITY_FILE_REGION_HOUSE_API);
				//生成静态页面
				HtmlGenerator.writeHtml(creatHtmlUrl+columnCityEntity.getFid()+"/"+vo.getFid()+".html", pageHtml);
			}
			return dto;
		}catch(Exception e){
			e.printStackTrace();
			dto.setErrCode(1);
			dto.setMsg("生成页面失败");
			return dto;
		}
	}


	/**
	 * 根据景点商圈查询附近房源
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/24 18:33
	 */
	@RequestMapping("findHouseListByRegion")
	@ResponseBody
	public DataTransferObject findHouseListByRegion(String hotRegion,Integer page,Integer size){
		DataTransferObject dto=new DataTransferObject();
		//查询景点商圈附近的房源
		HouseInfoRequest houseInfo = new HouseInfoRequest();
		houseInfo.setQ("*:*");
		houseInfo.setHotReginBusiness(hotRegion);//商圈
		houseInfo.setHotReginScenic(hotRegion);//景点
		houseInfo.setPage(page);
		houseInfo.setLimit(size);
		try{
			String regionJson = searchService.getHouseListInfoAndSuggest(null, JsonEntityTransform.Object2Json(houseInfo), null);
			List<HouseInfoEntity> houseList = SOAResParseUtil.getListValueFromDataByKey(regionJson,"list",HouseInfoEntity.class);
			dto.putValue("pageList",houseList);
		}catch (Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
		}
		return dto;
	}

	/**
	 * 收藏房源
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/24 19:23
	 */
	@RequestMapping("collectionHouse")
	@ResponseBody
	public DataTransferObject collectionHouse(String uid,String fid,String rentWay){
		DataTransferObject dto=new DataTransferObject();
		try{
			Map<String, String> map = new HashMap<>();
			map.put("houseFid", fid);
			map.put("rentWay", rentWay);
			map.put("uid",uid);
			Map<String, String> signMap = signMethod(map);
			Map<String, String> headerMap = new HashMap<>();
			headerMap.put("Client-Version", "1.0");
			headerMap.put("Client-Type","application-x-www-form-urlencoded");
			headerMap.put("User-Agent", "MozillaGoogle");
			String result = CloseableHttpUtil.sendPost(COLLECTHOUSE, signMap, headerMap);
			dto = JsonEntityTransform.json2DataTransferObject(result);
		}catch (Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
		}
		return dto;
	}

	/**
	 * 取消收藏房源
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/24 19:23
	 */
	@RequestMapping("uncollectionHouse")
	@ResponseBody
	public DataTransferObject uncollectionHouse(String uid,String fid,String rentWay){
		DataTransferObject dto=new DataTransferObject();
		try{
			Map<String, String> map = new HashMap<>();
			map.put("houseFid", fid);
			map.put("rentWay", rentWay);
			map.put("uid",uid);
			Map<String, String> signMap = signMethod(map);
			Map<String, String> headerMap = new HashMap<>();
			headerMap.put("Client-Version", "1.0");
			headerMap.put("Client-Type","application-x-www-form-urlencoded");
			headerMap.put("User-Agent", "MozillaGoogle");
			String result = CloseableHttpUtil.sendPost(UNCOLLECTHOUSE, signMap, headerMap);
			dto = JsonEntityTransform.json2DataTransferObject(result);
		}catch (Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
		}
		return dto;
	}

	/** 加密*/
	private  Map<String, String> signMethod(Map map){
		IEncrypt ie = EncryptFactory.createEncryption(EncryptFactory.ENCRYPT_DES);
		String des = ie.encrypt(JSON.toJSONString(map));
		String sign = MD5Util.MD5Encode((JSON.toJSONString(map)));
		Map<String, String> json = new HashMap<String, String>();
		json.put(DESKEY, des);
		json.put(SIGNKEY, sign);
		return json;
	}

	/**
	 * 根据你专栏配置的房源或者房间编号查询房源或者房间的信息
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/21 15:02
	 */
	@RequestMapping("findHouseListBySn")
	@ResponseBody
	public DataTransferObject findHouseListBySn(HttpServletRequest request,String snReg,Integer num){
		DataTransferObject dto=new DataTransferObject();
		try{
			//参数中接收到houseSn或者roomSn的集合
			List<String> roomSns = new ArrayList<String>();
			List<String> houseSns = new ArrayList<String>();
			String[] regs = snReg.split(",");
			List<String> snS = new ArrayList<String>();
			Collections.addAll(snS,regs);
			String regF=".*F.*";  //判断字符串中是否含有F
			for(String sn : snS){
				if(sn.matches(regF)){
					roomSns.add(sn);
				}else{
					houseSns.add(sn);
				}
			}
			//拼装查询接口参数
			HouseListRequset hlr = new HouseListRequset();
			List<HouseSearchRequest> searchList = new ArrayList<HouseSearchRequest>();
			if(roomSns.size()>0){
				//根据roomSn查询房间信息
				String room = houseManageService.getRoomListByRoomSns(JsonEntityTransform.Object2Json(roomSns));
				List<HouseRoomMsgEntity> roomList = SOAResParseUtil.getListValueFromDataByKey(room, "roomList", HouseRoomMsgEntity.class);
				if(!Check.NuNCollection(roomList)){
					for(HouseRoomMsgEntity roomMsg : roomList){
						HouseSearchRequest searchRequest = new HouseSearchRequest();
						searchRequest.setFid(roomMsg.getFid());
						searchRequest.setRentWay(RentWayEnum.ROOM.getCode());
						//将房间的fid和rentWay放到参数中
						searchList.add(searchRequest);
					}
				}
			}
			if(houseSns.size()>0){
				//根据houseSn查询房源信息
				String house = houseManageService.getHouseListByHouseSns(JsonEntityTransform.Object2Json(houseSns));
				List<HouseBaseMsgEntity> houseList = SOAResParseUtil.getListValueFromDataByKey(house, "houseList", HouseBaseMsgEntity.class);
				if(!Check.NuNCollection(houseList)){
					for(HouseBaseMsgEntity houseMsg :houseList){
						HouseSearchRequest searchRequest = new HouseSearchRequest();
						searchRequest.setFid(houseMsg.getFid());
						searchRequest.setRentWay(houseMsg.getRentWay());
						//将房源fid和rentway放到参数中
						searchList.add(searchRequest);
					}
				}
			}
			//调用solr接口查询
			hlr.setHouseList(searchList);
			hlr.setPicSize(picSize);
			String resultJson=searchService.getHouseListByListInfo(JsonEntityTransform.Object2Json(hlr));
			List<HouseInfoEntity> houseList = SOAResParseUtil.getListValueFromDataByKey(resultJson,"list",HouseInfoEntity.class);
			dto.putValue("list", houseList);
		}catch (Exception e){
			e.printStackTrace();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("查询失败");
			return dto;
		}
		return dto;
	}


}
