package com.ziroom.minsu.troy.photographer.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgPicEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.house.api.inner.PhotogMgtService;
import com.ziroom.minsu.services.house.photog.dto.PhotogDetailDto;
import com.ziroom.minsu.services.house.photog.dto.PhotogDto;
import com.ziroom.minsu.services.house.photog.dto.PhotogPicDto;
import com.ziroom.minsu.services.house.photog.dto.PhotogRequestDto;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.CustomerPicTypeEnum;
import com.ziroom.minsu.valenum.customer.CustomerSexEnum;
import com.ziroom.minsu.valenum.photographer.JobTypeEnum;
import com.ziroom.minsu.valenum.photographer.PhotographerGradeEnum;
import com.ziroom.minsu.valenum.photographer.PhotographerIdTypeEnum;
import com.ziroom.minsu.valenum.photographer.PhotographerSourceEnum;
import com.ziroom.minsu.valenum.photographer.PhotographerStatuEnum;
import com.ziroom.tech.storage.client.domain.FileInfoResponse;
import com.ziroom.tech.storage.client.service.StorageService;

/**
 * 
 * <p>摄影师管理</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("photog")
public class PhotogMgtController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PhotogMgtController.class);
	
	@Resource(name="photog.photogMgtService")
	private PhotogMgtService photogMgtService;

    @Resource(name="basedata.confCityService")
    private ConfCityService confCityService;

	@Resource(name="storageService")
	private StorageService storageService;
	
	@Value("#{'${storage_key}'.trim()}")
	private String storageKey;
	
	@Value("#{'${storage_limit}'.trim()}")
	private String storageLimit;
    
	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;

	@Value("#{'${pic_size}'.trim()}")
	private String picSize;
	
	/**
	 * 
	 * 摄影师管理-跳转摄影师列表
	 *
	 * @author liujun
	 * @created 2016年11月7日
	 *
	 * @param request
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("listPhotog")
	public void listPhotog(HttpServletRequest request) {
		// 开通城市
		String resultJson =  confCityService.getOpenCity();
		List<Map> cityList = null;
		try {
			cityList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", Map.class);
		} catch (SOAParseException e) {
			LogUtil.error(LOGGER, "confCityService.getOpenCity error:{}", resultJson);
		}
		request.setAttribute("cityList", cityList);
		// 性别
		request.setAttribute("sexMap", CustomerSexEnum.getEnumMap());
		// 摄影师等级
		request.setAttribute("photogGradeMap", PhotographerGradeEnum.getEnumMap());
		// 摄影师来源
		request.setAttribute("photogSourceMap", PhotographerSourceEnum.getEnumMap());
		// 摄影师状态
		request.setAttribute("photogStatuMap", PhotographerStatuEnum.getEnumMap());
		// 摄影师来源
		request.setAttribute("photogSourceJson", JsonEntityTransform.Object2Json(PhotographerSourceEnum.getEnumMap()));
		// 证件类型
		request.setAttribute("idTypeMap", PhotographerIdTypeEnum.getCustomerIdTypeMap());
		// 工作类型
		request.setAttribute("jobTypeMap", JobTypeEnum.getEnumMap());
		
		request.setAttribute("picBaseAddrMona", picBaseAddrMona);
		request.setAttribute("picSize", picSize);
	}
	
	/**
	 * 
	 * 摄影师管理-查询房源信息列表页
	 *
	 * @author liujun
	 * @created 2016年4月11日 下午9:43:25
	 *
	 * @param houseRequest
	 * @return
	 */
	@RequestMapping("showPhotog")
	@ResponseBody
	public PageResult showPhotog(PhotogRequestDto photogDto) {
		try {
			String resultJson = photogMgtService.findPhotographerListByPage(JsonEntityTransform.Object2Json(photogDto));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if(resultDto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "photogMgtService.findPhotographerListByPage error:{}", JsonEntityTransform.Object2Json(photogDto));
				return new PageResult();
			}
			
			List<PhotographerBaseMsgEntity> photogList = SOAResParseUtil
					.getListValueFromDataByKey(resultJson, "list", PhotographerBaseMsgEntity.class);

			PageResult pageResult = new PageResult();
			pageResult.setRows(photogList);
			pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
			return pageResult;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "showPhotog error:{}", e);
			return new PageResult();
		}
	}
	
	/**
	 * 
	 * 摄影师管理-新增摄影师信息
	 *
	 * @author liujun
	 * @created 2016年11月7日
	 *
	 * @param activityGiftEntity
	 * @return
	 */
	@RequestMapping("addPhotog")
	@ResponseBody
	public DataTransferObject addPhotog(String jsonStr) {
		DataTransferObject dto = new DataTransferObject();
		try {
			PhotogDto photogDto = JsonEntityTransform.json2Object(jsonStr, PhotogDto.class);
			String resultJson = photogMgtService.insertPhotographerMsg(JsonEntityTransform.Object2Json(photogDto));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(dto));
				return dto;
			}
			
			int upNum = SOAResParseUtil.getIntFromDataByKey(resultJson, "upNum");
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "addPhotog error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}

	/**
	 * 
	 * 摄影师管理-根据逻辑id查询摄影师信息
	 *
	 * @author liujun
	 * @created 2016年11月8日
	 *
	 * @param actGiftRequest
	 * @return
	 */
	@RequestMapping("findPhotog")
	@ResponseBody
	public DataTransferObject findPhotog(String photographerUid) {
		DataTransferObject dto = new DataTransferObject();
		try {
			String resultJson = photogMgtService.findPhotographerMsgByUid(photographerUid);
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(dto));
				return dto;
			}
			
			PhotogDetailDto photogDetailDto = SOAResParseUtil
					.getValueFromDataByKey(resultJson, "obj", PhotogDetailDto.class);
			dto.putValue("obj", photogDetailDto);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "findPhotog error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}

	/**
	 * 
	 * 摄影师管理-修改摄影师信息
	 *
	 * @author liujun
	 * @created 2016年11月8日
	 *
	 * @param activityGiftEntity
	 * @return
	 */
	@ResponseBody
	@RequestMapping("editPhotog")
	public DataTransferObject editPhotog(String jsonStr) {
		DataTransferObject dto = new DataTransferObject();
		try {
			PhotogDto photogDto = JsonEntityTransform.json2Object(jsonStr, PhotogDto.class);
			String resultJson = photogMgtService.updatePhotographerMsg(JsonEntityTransform.Object2Json(photogDto));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(dto));
				return dto;
			}
			
			int upNum = SOAResParseUtil.getIntFromDataByKey(resultJson, "upNum");
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "editPhotog error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}

	/**
	 * 
	 * 摄影师管理-逻辑删除摄影师信息
	 *
	 * @author liujun
	 * @created 2016年11月8日
	 *
	 * @param actGiftRequest
	 * @return
	 */
	@RequestMapping("delPhotog")
	@ResponseBody
	public DataTransferObject delPhotog(String photographerUid) {
		DataTransferObject dto = new DataTransferObject();
		try {
			PhotographerBaseMsgEntity base = new PhotographerBaseMsgEntity();
			base.setPhotographerUid(photographerUid);
			base.setPhotographerStatu(PhotographerStatuEnum.DELETE.getCode());
			base.setLastModifyDate(new Date());
			
			PhotogDto photogDto = new PhotogDto();
			photogDto.setBase(base);
			String resultJson = photogMgtService.updatePhotographerMsg(JsonEntityTransform.Object2Json(photogDto));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(dto));
				return dto;
			}
			
			int upNum = SOAResParseUtil.getIntFromDataByKey(resultJson, "upNum");
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "delActGroup error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}
	
	/**
	 * 摄影师管理-删除客户照片信息
	 * @author jixd
	 * @created 2016年4月26日 上午11:01:37
	 * @param picdto
	 * @return
	 */
	@RequestMapping("delPhotogPic")
	@ResponseBody
	public DataTransferObject delPhotogPic(PhotogPicDto picDto){
		DataTransferObject dto = new DataTransferObject();
		try {
			String resultJson = photogMgtService.findPhotogPicByUidAndType(JsonEntityTransform.Object2Json(picDto));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if (resultDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "photogMgtService.findPhotogPicByUidAndType error:{}", resultJson);
				return resultDto;
			}
			PhotographerBaseMsgPicEntity picEntity = SOAResParseUtil
					.getValueFromDataByKey(resultJson, "obj", PhotographerBaseMsgPicEntity.class);
			if(!Check.NuNObj(picEntity)){
				//删除该图片
				picEntity.setIsDel(YesOrNoEnum.YES.getCode());
				picEntity.setLastModifyDate(new Date());
				resultJson = photogMgtService.updatePhotographerPicMsg(JsonEntityTransform.Object2Json(picEntity));
				resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
				if (resultDto.getCode() == DataTransferObject.ERROR) {
					LogUtil.info(LOGGER, "photogMgtService.updatePhotographerPicMsg error:{}", resultJson);
					return resultDto;
				}
//				boolean del = storageService.delete(picEntity.getPicServerUuid());
//				LogUtil.info(LOGGER, "storageService.delete:{}", del);
			}else{
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("图片不存在");
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "delPhotogPic error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		} 
		return dto;
	}
	
	/**
	 * 
	 * 摄影师管理-上传摄影师图像
	 *
	 * @author liujun
	 * @created 2016年11月9日
	 *
	 * @param file
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="uploadPhotogPic",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String uploadPhotogPic(@RequestParam MultipartFile[] file, HttpServletRequest request) {
		DataTransferObject dto = new DataTransferObject();
		String picType = request.getParameter("picType");
		String photographerUid = request.getParameter("photographerUid");
		
		if (Check.NuNStr(picType)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("图片类型不能为空");
			return dto.toJsonString();
		}
		try {
			int picTypeInt = Integer.valueOf(picType).intValue();

			PhotogPicDto picDto = new PhotogPicDto();
			picDto.setPhotographerUid(photographerUid);
			picDto.setPicType(picTypeInt);
			
			String resultJson = photogMgtService.findPhotogPicByUidAndType(JsonEntityTransform.Object2Json(picDto));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "photogMgtService.findPhotogPicByUidAndType error:{}", resultJson);
				return dto.toJsonString();
			}
			PhotographerBaseMsgPicEntity picEntity = SOAResParseUtil
					.getValueFromDataByKey(resultJson, "obj", PhotographerBaseMsgPicEntity.class);
			String oldServerPic = ""; 
			if(Check.NuNObj(picEntity)){
				picEntity = new PhotographerBaseMsgPicEntity();
			}else{
				//删除图片服务器图片
				oldServerPic = picEntity.getPicServerUuid();
			}
			
			if(file.length >0){
				//上传图片服务
				FileInfoResponse fileResponse = storageService.upload(storageKey, storageLimit,
						file[0].getOriginalFilename(), file[0].getBytes(), CustomerPicTypeEnum.getEnumMap().get(picTypeInt),
						0l, file[0].getOriginalFilename());
				picEntity.setPhotographerUid(photographerUid);
				picEntity.setPicType(picTypeInt);
				picEntity.setPicBaseUrl(fileResponse.getFile().getUrlBase());
				picEntity.setPicName(fileResponse.getFile().getOriginalFilename());
				picEntity.setPicSuffix(fileResponse.getFile().getUrlExt());
				picEntity.setPicServerUuid(fileResponse.getFile().getUuid());
				picEntity.setIsDel(YesOrNoEnum.NO.getCode());
			}
			
			if(Check.NuNStr(picEntity.getFid())){
				picEntity.setFid(UUIDGenerator.hexUUID());
				String saveJson = photogMgtService.insertPhotographerPicMsg(JsonEntityTransform.Object2Json(picEntity));
				dto = JsonEntityTransform.json2DataTransferObject(saveJson);
				if(dto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "photogMgtService.insertPhotographerPicMsg error:{}", saveJson);
					return dto.toJsonString();
				}
				dto.putValue("pic", picEntity);
			}else{
//				boolean del = storageService.delete(oldServerPic);
//				LogUtil.info(LOGGER, "storageService.delete:{}", del);
				String updateJson = photogMgtService.updatePhotographerPicMsg(JsonEntityTransform.Object2Json(picEntity));
				dto=JsonEntityTransform.json2DataTransferObject(updateJson);
				if(dto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "photogMgtService.updatePhotographerPicMsg error:{}", updateJson);
					return dto.toJsonString();
				}
				dto.putValue("pic", picEntity);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "uploadPhotogPic error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto.toJsonString();
	}
	
}
