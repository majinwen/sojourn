/**
 * @FileName: FileUploadController.java
 * @Package com.ziroom.minsu.api.common.controller
 * 
 * @author jixd
 * @created 2016年5月26日 下午10:48:28
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.common.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.asura.framework.base.util.JsonEntityTransform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.services.customer.dto.ImgDto;
import com.ziroom.tech.storage.client.domain.FileInfoResponse;
import com.ziroom.tech.storage.client.domain.FileInfoResponse.InternalFile;
import com.ziroom.tech.storage.client.service.StorageService;

/**
 * <p>图片上传</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@RequestMapping("/fileUpload")
@Controller
public class FileUploadController {
	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);
	
	@Resource(name="storageService")
	private StorageService storageService;
	
	@Value("#{'${storage_key}'.trim()}")
	private String storageKey;
	
	@Value("#{'${storage_limit}'.trim()}")
	private String storageLimit;
	/**
	 * 
	 * 图片上传,民宿用户图片
	 *
	 * @author jixd
	 * @created 2016年5月26日 下午10:52:20
	 *
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/picUpload")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> picUpload(@RequestParam MultipartFile file,HttpServletRequest request){
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "上传图片参数：{}", paramJson);
			LogUtil.info(LOGGER,"图片名字={},图片大小={}",file.getOriginalFilename(),file.getSize());
			JSONObject jsonObj = JSONObject.parseObject(paramJson);
			ImgDto imgDto = new ImgDto();
			if(jsonObj.containsKey("picType")){
				Integer picType = jsonObj.getInteger("picType");
				imgDto.setPicType(picType);
			}else{
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("图片类型不存在"), HttpStatus.OK);
			}
			if(Check.NuNObj(file)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("文件不存在"), HttpStatus.OK);
			}

            String fileName = file.getOriginalFilename();
            if (!fileName.contains(".jpg") && !fileName.contains(".JPG")){
                fileName += ".jpg";
            }
			FileInfoResponse fileResponse=storageService.upload(storageKey, storageLimit, fileName,file.getBytes(),"民宿用户图片", 0l,fileName);
			if(Check.NuNObj(fileResponse)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("图片返回错误"), HttpStatus.OK);
			}
			LogUtil.info(LOGGER,"上传图片返回值result={}", JsonEntityTransform.Object2Json(fileResponse));
			InternalFile internalFile = fileResponse.getFile();
			imgDto.setOriginalFilename(internalFile.getOriginalFilename());
			imgDto.setUrl(internalFile.getUrl());
			imgDto.setUrlBase(internalFile.getUrlBase());
			imgDto.setUrlExt(internalFile.getUrlExt());
			imgDto.setUuid(internalFile.getUuid());
			
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(imgDto), HttpStatus.OK);
		} catch (IOException e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}
}
