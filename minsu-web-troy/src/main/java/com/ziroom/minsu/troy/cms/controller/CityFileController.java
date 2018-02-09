/**
 * @FileName: CityFileController.java
 * @Package com.ziroom.minsu.troy.cms.controller
 * 
 * @author bushujie
 * @created 2016年11月8日 上午11:09:40
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.troy.cms.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ColumnCityEntity;
import com.ziroom.minsu.entity.cms.ColumnTemplateEntity;
import com.ziroom.minsu.entity.conf.HotRegionEntity;
import com.ziroom.minsu.entity.file.FileRegionItemsEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityArchiveService;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.HotRegionService;
import com.ziroom.minsu.services.basedata.api.inner.PermissionOperateService;
import com.ziroom.minsu.services.basedata.dto.CityArchivesRequest;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.cms.api.inner.CityFileService;
import com.ziroom.minsu.services.cms.dto.ColumnCityRequest;
import com.ziroom.minsu.services.cms.dto.ColumnRegionAddRequest;
import com.ziroom.minsu.services.cms.dto.ColumnRegionRequest;
import com.ziroom.minsu.services.cms.dto.ColumnTemplateRequest;
import com.ziroom.minsu.services.cms.dto.OrderSortUpRequest;
import com.ziroom.minsu.services.cms.entity.ColumnCityVo;
import com.ziroom.minsu.services.cms.entity.ColumnRegionUpVo;
import com.ziroom.minsu.services.cms.entity.ColumnRegionVo;
import com.ziroom.minsu.services.cms.entity.ColumnTemplateVo;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.HtmlGenerator;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.tech.storage.client.domain.FileInfoResponse;
import com.ziroom.tech.storage.client.service.StorageService;

/**
 * <p>城市档案相关</p>
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
@RequestMapping("cityFile")
public class CityFileController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CityFileController.class);

	@Resource(name="cms.cityFileService")
	private CityFileService cityFileService;
	
	@Resource(name="basedata.hotRegionService")
	private HotRegionService hotRegionService;
	
	@Value("#{'${storage_key}'.trim()}")
	private String storageKey;
	
	@Value("#{'${storage_limit}'.trim()}")
	private String storageLimit;
	
	@Resource(name="storageService")
	private StorageService storageService; 
	
	@Resource(name="basedata.permissionOperateService")
	private PermissionOperateService permissionOperateService;
	
	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;
	
	@Value("#{'${pic_size}'.trim()}")
	private String picSize;
	
	@Resource(name="basedata.cityArchiveService")
	private CityArchiveService cityArchiveService;
	
	@Resource(name="basedata.confCityService")
	private ConfCityService confCityService;
	
	@Value("#{'${CREAT_HTML_URL}'.trim()}")
	private String creatHtmlUrl;

	@Value("#{'${PIC_JUMP_URL}'.trim()}")
	private String pageUrl;

		
	/**
	 * 
	 * 专栏模板页
	 *
	 * @author bushujie
	 * @created 2016年11月8日 下午1:59:54
	 *
	 */
	@RequestMapping("templatelist")
	public void toTemplateList(){
		
	}
	
	/**
	 * 
	 * 专栏模板列表数据查询
	 *
	 * @author bushujie
	 * @created 2016年11月8日 下午2:00:20
	 *
	 * @param templateRequest
	 * @return
	 * @throws SOAParseException
	 */
	@RequestMapping("findTemplateList")
	@ResponseBody
	public PageResult findTemplateList(ColumnTemplateRequest templateRequest) throws SOAParseException{
		String resultJson=cityFileService.columnTemplateList(JsonEntityTransform.Object2Json(templateRequest));
		List<ColumnTemplateVo> tList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "tempList", ColumnTemplateVo.class);
		if(!Check.NuNCollection(tList)){
			for(ColumnTemplateVo vo:tList){
				String userJson=permissionOperateService.initSaveUserInfo(vo.getCreateFid());
				CurrentuserVo currentuserVo=SOAResParseUtil.getValueFromDataByKey(userJson, "userInfo", CurrentuserVo.class);
				if(!Check.NuNObj(currentuserVo)){
					vo.setEmpName(currentuserVo.getFullName());
				}
			}
		}
		PageResult pageResult=new PageResult();
		pageResult.setRows(tList);
		pageResult.setTotal(SOAResParseUtil.getLongFromDataByKey(resultJson, "count"));
		return pageResult;
	}
	
	 /**
	  * 
	  * 专栏模板添加页
	  *
	  * @author bushujie
	  * @created 2016年11月8日 下午4:58:58
	  *
	  */
	@RequestMapping("toAddTemplate")
	public void toAddTemplate(){
		
	}
	
	/**
	 * 
	 * 插入专栏模板
	 *
	 * @author bushujie
	 * @created 2016年11月8日 下午5:32:44
	 *
	 * @param columnTemplateEntity
	 * @return
	 */
	@RequestMapping("insertTemplate")
	@ResponseBody
	public DataTransferObject insertTemplate(ColumnTemplateEntity columnTemplateEntity){
		columnTemplateEntity.setFid(UUIDGenerator.hexUUID());
		columnTemplateEntity.setCreateFid(UserUtil.getCurrentUserFid());
		String resultJson =cityFileService.insertColumnTemplate(JsonEntityTransform.Object2Json(columnTemplateEntity));
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}
	
	/**
	 * 
	 * 更新专栏模板页
	 *
	 * @author bushujie
	 * @created 2016年11月8日 下午9:12:46
	 *
	 * @param tempFid
	 * @param request
	 * @throws SOAParseException
	 */
	@RequestMapping("updateTemplate")
	public void updateTemplate(String tempFid,HttpServletRequest request) throws SOAParseException{
		String resultJson=cityFileService.getColumnTemplateByFid(tempFid);
		ColumnTemplateEntity template=SOAResParseUtil.getValueFromDataByKey(resultJson, "template", ColumnTemplateEntity.class);
		request.setAttribute("template", template);
	}
	
	/**
	 * 
	 * 更新专栏模板
	 *
	 * @author bushujie
	 * @created 2016年11月8日 下午9:22:09
	 *
	 * @param columnTemplateEntity
	 * @return
	 */
	@RequestMapping("updateTemplateOk")
	@ResponseBody
	public DataTransferObject updateTemplateOk(ColumnTemplateEntity columnTemplateEntity){
		String resultJson=cityFileService.updateColumnTemplate(JsonEntityTransform.Object2Json(columnTemplateEntity));
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}
	
	/**
	 * 
	 * 城市专栏列表页
	 *
	 * @author bushujie
	 * @throws SOAParseException 
	 * @created 2016年11月8日 下午1:59:54
	 *
	 */
	@RequestMapping("columnCityList")
	public void tocolumnCityList(HttpServletRequest request) throws SOAParseException{
		String resultJson=confCityService.getOpenCity();
		List<Map> openCityList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", Map.class);
		request.setAttribute("openCityList", openCityList);
	}

	/**
	 * 根据cityCode转化城市名称
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2017/1/9 11:20
	 */
	@RequestMapping("findCityNameByCode")
	@ResponseBody
	public DataTransferObject findCityNameByCode(String cityCode){
		DataTransferObject dto = new DataTransferObject();
		String cityNameDto =  confCityService.getCityNameByCode(cityCode);
		if(!Check.NuNObj(cityNameDto)){
			dto = JsonEntityTransform.json2DataTransferObject(cityNameDto);
		}else{
			dto.setErrCode(DataTransferObject.ERROR);
		}
		return dto;
	}

	/**
	 * 
	 * 城市专栏列表数据查询
	 *
	 * @author bushujie
	 * @created 2016年11月8日 下午2:00:20
	 *
	 * @param templateRequest
	 * @return
	 * @throws SOAParseException
	 */
	@RequestMapping("findColumnCityList")
	@ResponseBody
	public PageResult findColumnCityList(ColumnCityRequest columnCityRequest) throws SOAParseException{
		String resultJson=cityFileService.columnCityList(JsonEntityTransform.Object2Json(columnCityRequest));
		List<ColumnCityVo> tList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", ColumnCityVo.class);
		if(!Check.NuNCollection(tList)){
			for(ColumnCityVo vo:tList){
				String userJson=permissionOperateService.initSaveUserInfo(vo.getCreateFid());
				CurrentuserVo currentuserVo=SOAResParseUtil.getValueFromDataByKey(userJson, "userInfo", CurrentuserVo.class);
				if(!Check.NuNObj(currentuserVo)){
					vo.setEmpName(currentuserVo.getFullName());
				}
			}
		}
		PageResult pageResult=new PageResult();
		pageResult.setRows(tList);
		pageResult.setTotal(SOAResParseUtil.getLongFromDataByKey(resultJson, "count"));
		return pageResult;
	}
	
	 /**
	  * 
	  * 城市专栏添加页
	  *
	  * @author bushujie
	 * @throws SOAParseException 
	  * @created 2016年11月8日 下午4:58:58
	  *
	  */
	@SuppressWarnings("rawtypes")
	@RequestMapping("columnCityAdd")
	public void toAddColumnCity(HttpServletRequest request) throws SOAParseException{
		String resultJson=cityFileService.findAllRegTemplate();
		List<ColumnTemplateEntity> tempList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", ColumnTemplateEntity.class);
		String cityJson=confCityService.getOpenCityWithFile();
		List<Map> cityList=SOAResParseUtil.getListValueFromDataByKey(cityJson, "list", Map.class);
		request.setAttribute("cityList", cityList);
		request.setAttribute("tempList", tempList);
	}
	
	/**
	 * 
	 * 插入城市专栏
	 *
	 * @author bushujie
	 * @created 2016年11月8日 下午5:32:44
	 *
	 * @param columnTemplateEntity
	 * @return
	 */
	@RequestMapping("insertColumnCity")
	@ResponseBody
	public DataTransferObject insertColumnCity(ColumnCityEntity columnCityEntity){
		columnCityEntity.setFid(UUIDGenerator.hexUUID());
		columnCityEntity.setCreateFid(UserUtil.getCurrentUserFid());
		String resultJson =cityFileService.insertColumnCity(JsonEntityTransform.Object2Json(columnCityEntity));
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}
	
	/**
	 * 
	 * 更新城市专栏页
	 *
	 * @author bushujie
	 * @created 2016年11月8日 下午9:12:46
	 *
	 * @param tempFid
	 * @param request
	 * @throws SOAParseException
	 */
	@RequestMapping("updateColumnCity")
	public void updateColumnCity(String fid,HttpServletRequest request) throws SOAParseException{
		String resultJson=cityFileService.getColumnCityByFid(fid);
		ColumnCityEntity columnCityEntity=SOAResParseUtil.getValueFromDataByKey(resultJson, "columnCity", ColumnCityEntity.class);
		request.setAttribute("columnCityEntity", columnCityEntity);
		//所有注册模板
		String tempJson=cityFileService.findAllRegTemplate();
		List<ColumnTemplateEntity> tempList=SOAResParseUtil.getListValueFromDataByKey(tempJson, "list", ColumnTemplateEntity.class);
		String cityJson=confCityService.getOpenCityWithFile();
		List<Map> cityList=SOAResParseUtil.getListValueFromDataByKey(cityJson, "list", Map.class);
		request.setAttribute("cityList", cityList);
		request.setAttribute("tempList", tempList);
	}
	
	/**
	 * 
	 * 更新城市专栏
	 *
	 * @author bushujie
	 * @created 2016年11月8日 下午9:22:09
	 *
	 * @param columnTemplateEntity
	 * @return
	 */
	@RequestMapping("updateColumnCityOk")
	@ResponseBody
	public DataTransferObject updateColumnCityOk(ColumnCityEntity columnCityEntity){
		String resultJson=cityFileService.updateColumnCity(JsonEntityTransform.Object2Json(columnCityEntity));
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}
	
	/**
	 * 
	 * 上线下线城市专栏
	 *
	 * @author bushujie
	 * @created 2016年11月10日 上午11:55:54
	 *
	 * @param fid
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("upDownColumnCity")
	@ResponseBody
	public DataTransferObject upDownColumnCity(String fid) throws Exception{
		String resultJson=cityFileService.getColumnCityByFid(fid);
		ColumnCityEntity columnCityEntity=SOAResParseUtil.getValueFromDataByKey(resultJson, "columnCity", ColumnCityEntity.class);
		if(columnCityEntity.getColStatus()==0||columnCityEntity.getColStatus()==2){
			columnCityEntity.setColStatus(1);
			/*//上线城市专栏生成页面 
			HtmlGenerator.createHtmlPage(creatHtmlUrl+"?fid="+fid);*/
		} else if(columnCityEntity.getColStatus()==1) {
			columnCityEntity.setColStatus(2);
		}
		String upJson=cityFileService.updateColumnCity(JsonEntityTransform.Object2Json(columnCityEntity));
		return JsonEntityTransform.json2DataTransferObject(upJson);
	}

	/**
	 * 生成页面
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/25 10:19
	 */
	@RequestMapping("createPage")
	@ResponseBody
	public DataTransferObject createPage(String fid){
		DataTransferObject dto = new DataTransferObject();
		try{
			//上线城市专栏生成页面
			Map<String, String> param=new HashMap<String, String>();
			param.put("fid",fid);
	        Map<String,String> map = new HashMap<>();
	        map.put("Accept","application/json");
			String resultJson=CloseableHttpUtil.sendFormPost(creatHtmlUrl, param,map);
			dto=JsonEntityTransform.json2DataTransferObject(resultJson);
		}catch (Exception e){
			LogUtil.error(LOGGER, "城市档案页面生成错误{}",e);
			dto.setErrCode(DataTransferObject.ERROR);
		}
		return dto;
	}
	
	/**
	 * 
	 *  城市专栏商圈景点列表
	 *
	 * @author bushujie
	 * @created 2016年11月11日 上午10:47:56
	 *
	 * @param columnCityFid
	 * @param request
	 */
	@RequestMapping("columnRegionList")
	public void toColumnRegionList(String columnCityFid,HttpServletRequest request){
		request.setAttribute("pageUrl",pageUrl);
		request.setAttribute("columnCityFid", columnCityFid);
		request.setAttribute("cityCode", request.getParameter("cityCode"));
	}
	
	/**
	 * 
	 * 查询专栏景点商圈
	 *
	 * @author bushujie
	 * @created 2016年11月11日 上午11:29:52
	 *
	 * @param columnRegionRequest
	 * @return
	 * @throws SOAParseException
	 */
	@RequestMapping("findColumRegionList")
	@ResponseBody
	public PageResult findColumRegionList(ColumnRegionRequest columnRegionRequest) throws SOAParseException{
		String resultJson =cityFileService.columnRegionList(JsonEntityTransform.Object2Json(columnRegionRequest));
		List<ColumnRegionVo> list=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", ColumnRegionVo.class);
		if(!Check.NuNCollection(list)){
			for(ColumnRegionVo vo:list){
				String userJson=permissionOperateService.initSaveUserInfo(vo.getCreateFid());
				CurrentuserVo currentuserVo=SOAResParseUtil.getValueFromDataByKey(userJson, "userInfo", CurrentuserVo.class);
				if(!Check.NuNObj(currentuserVo)){
					vo.setEmpName(currentuserVo.getFullName());
				}
				String regionJson=hotRegionService.searchHotRegionByFid(vo.getRegionFid());
				HotRegionEntity hotRegionEntity=SOAResParseUtil.getValueFromDataByKey(regionJson, "hotRegion", HotRegionEntity.class);
				if(!Check.NuNObj(hotRegionEntity)){
					vo.setRegionName(hotRegionEntity.getRegionName());
				}
			}
		}
		long count=SOAResParseUtil.getLongFromDataByKey(resultJson, "count");
		PageResult pageResult=new PageResult();
		pageResult.setRows(list);
		pageResult.setTotal(count);
		return pageResult;
	}
	
	/**
	 * 
	 * 专栏商圈景点添加页
	 *
	 * @author bushujie
	 * @created 2016年11月11日 下午3:08:08
	 *
	 * @param cityCode
	 * @param columnCityFid
	 * @param request
	 * @throws SOAParseException
	 */
	@RequestMapping("columnRegionAdd")
	public void columnRegionAdd(String cityCode,String columnCityFid,HttpServletRequest request) throws SOAParseException{
		String resultJson=hotRegionService.getListWithFileByCityCode(cityCode);
		List<HotRegionEntity> regionList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HotRegionEntity.class);
		request.setAttribute("regionList", regionList);
		request.setAttribute("columnCityFid", columnCityFid);
		request.setAttribute("cityCode", cityCode);
	}
	
	/**
	 * 
	 * 专栏景点商圈图片上传
	 *
	 * @author bushujie
	 * @created 2016年11月12日 下午2:56:27
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/uploadColumnPic",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String uploadColumnPic(@RequestParam MultipartFile file) throws IOException{
		DataTransferObject dto=new DataTransferObject();
		try {
		   //获取上传图片的宽高
		   BufferedImage bi =ImageIO.read(file.getInputStream());
			//上传图片
			FileInfoResponse fileResponse=storageService.upload(storageKey, storageLimit, file.getOriginalFilename(),file.getBytes(),"城市专栏图片", 0l,file.getOriginalFilename());
			if(!"0".equals(fileResponse.getResponseCode())){
				LogUtil.info(LOGGER, "图片全名称:{},图片类型:{}, 图片文件是否为空:{},图片名称:{}",file.getOriginalFilename(),file.getContentType(),file.isEmpty(),file.getName());
			}
			StringBuffer sb=new StringBuffer();
			sb.append(fileResponse.getFile().getUuid());
			sb.append("|").append(fileResponse.getFile().getUrlBase());
			sb.append("|").append(fileResponse.getFile().getUrlExt());
			sb.append("|").append(bi.getWidth());
			sb.append("|").append(bi.getHeight());
			dto.putValue("picUid", fileResponse.getFile().getUuid());
			dto.putValue("picUrlBase", fileResponse.getFile().getUrlBase());
			dto.putValue("picExt", fileResponse.getFile().getUrlExt());
			dto.putValue("picUrl", fileResponse.getFile().getUrl());
			dto.putValue("picParam", sb.toString());
			return dto.toJsonString();
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto.toJsonString();
		} 
	}

	/**
	 * 富文本上传图片处理
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/15 10:45
	 */
	@RequestMapping(value="/uploadCkeditorPic",produces="text/html;charset=UTF-8")
	@ResponseBody
	public void uploadCkeditorPic(@RequestParam("upload") MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IOException{
		DataTransferObject dto=new DataTransferObject();
		try {
			String cKEditorFuncNum = request.getParameter("CKEditorFuncNum");
			PrintWriter out = response.getWriter();
			//上传图片
			FileInfoResponse fileResponse=storageService.upload(storageKey, storageLimit, file.getOriginalFilename(),file.getBytes(),"城市专栏图片", 0l,file.getOriginalFilename());
			if(!"0".equals(fileResponse.getResponseCode())){
				LogUtil.info(LOGGER, "图片全名称:{},图片类型:{}, 图片文件是否为空:{},图片名称:{}",file.getOriginalFilename(),file.getContentType(),file.isEmpty(),file.getName());
			}

//			dto.putValue("picUrl", fileResponse.getFile().getUrl());
			String picUrl = fileResponse.getFile().getUrl();
			out.println("<script type=\"text/javascript\">");
			out.println("window.parent.CKEDITOR.tools.callFunction("
					+ cKEditorFuncNum + ",'" + picUrl
					+ "','')");
			out.println("</script>");
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
	}

	/**
	 * 
	 * 插入城市专栏商圈景点
	 *
	 * @author bushujie
	 * @created 2016年11月14日 上午10:34:04
	 *
	 * @param columnRegionAddRequest
	 * @return
	 */
	@RequestMapping("insertColumnRegion")
	@ResponseBody
	public DataTransferObject insertColumnRegion(ColumnRegionAddRequest columnRegionAddRequest){
		columnRegionAddRequest.setCreateFid(UserUtil.getCurrentUserFid());
		String resultJson=cityFileService.insertColumnRegion(JsonEntityTransform.Object2Json(columnRegionAddRequest));
		return JsonEntityTransform.json2DataTransferObject(resultJson) ;
	}
	
	/**
	 * 
	 * 更新专栏景点商圈
	 *
	 * @author bushujie
	 * @created 2016年11月14日 下午6:34:36
	 *
	 * @param fid
	 * @param request
	 * @throws SOAParseException 
	 */
	@RequestMapping("columnRegionUpdate")
	public void columnRegionUpdate(String fid,HttpServletRequest request) throws SOAParseException{
		String regionJson=hotRegionService.getListWithFileByCityCode(request.getParameter("cityCode"));
		List<HotRegionEntity> regionList=SOAResParseUtil.getListValueFromDataByKey(regionJson, "list", HotRegionEntity.class);
		request.setAttribute("regionList", regionList);
		String resultJson=cityFileService.initUpColumnRegion(fid);
		ColumnRegionUpVo columnRegionUpVo=SOAResParseUtil.getValueFromDataByKey(resultJson, "regionUpVo", ColumnRegionUpVo.class);
		request.setAttribute("columnRegionUpVo", columnRegionUpVo);
		request.setAttribute("cityCode", request.getParameter("cityCode"));
		request.setAttribute("picBaseAddrMona", picBaseAddrMona);
		request.setAttribute("picSize", picSize);
		
		//查询景点商圈推荐项目
		CityArchivesRequest cityArchivesRequest=new CityArchivesRequest();
		cityArchivesRequest.setHotRegionFid(columnRegionUpVo.getRegionFid());
		String itemJson=cityArchiveService.getItemsByHotRegionFid(JsonEntityTransform.Object2Json(cityArchivesRequest));
		List<FileRegionItemsEntity> items=SOAResParseUtil.getListValueFromDataByKey(itemJson, "items", FileRegionItemsEntity.class);
		request.setAttribute("items", items);
	}
	
	/**
	 * 
	 * 根据商圈景点查询推荐项目
	 *
	 * @author bushujie
	 * @created 2016年11月14日 下午8:43:04
	 *
	 * @param regionFid
	 * @return
	 */
	@RequestMapping("getItemsByHotRegionFid")
	@ResponseBody
	public DataTransferObject getItemsByHotRegionFid(String regionFid){
		CityArchivesRequest cityArchivesRequest=new CityArchivesRequest();
		cityArchivesRequest.setHotRegionFid(regionFid);
		String resultJson=cityArchiveService.getItemsByHotRegionFid(JsonEntityTransform.Object2Json(cityArchivesRequest));
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}
	
	/**
	 * 
	 * 更新专栏景点商圈
	 *
	 * @author bushujie
	 * @created 2016年11月15日 下午3:16:29
	 *
	 * @param columnRegionAddRequest
	 * @return
	 */
	@RequestMapping("updateColumnRegion")
	@ResponseBody
	public DataTransferObject updateColumnRegion(ColumnRegionAddRequest columnRegionAddRequest){
		columnRegionAddRequest.setCreateFid(UserUtil.getCurrentUserFid());
		String resultJson=cityFileService.updateColumnRegion(JsonEntityTransform.Object2Json(columnRegionAddRequest));
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}
	
	
	/**
	 * 
	 * 删除专栏商圈景点
	 *
	 * @author bushujie
	 * @created 2016年11月15日 下午9:11:44
	 *
	 * @param fid
	 * @return
	 */
	@RequestMapping("delColumnRegion")
	@ResponseBody
	public DataTransferObject delColumnRegion(String fid){
		String resultJson=cityFileService.delColumnRegion(fid);
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}
	
	/**
	 * 
	 * 调整专栏景点顺序
	 *
	 * @author bushujie
	 * @created 2017年1月6日 下午2:52:43
	 *
	 * @param orderSortUpRequest
	 * @return
	 */
	@RequestMapping("upColumnRegionOrderSort")
	@ResponseBody
	public DataTransferObject upColumnRegionOrderSort(OrderSortUpRequest orderSortUpRequest){
		String resultJson=cityFileService.upColumnRegionOrderSort(JsonEntityTransform.Object2Json(orderSortUpRequest));
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}
}
