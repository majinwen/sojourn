/**
 * @FileName: TenantHouseController.java
 * @Package com.ziroom.minsu.api.house.controller
 * @author bushujie
 * @created 2016年5月1日 下午4:55:41
 * <p>
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.house.controller;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.ApiMessageConst;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.common.logic.ParamCheckLogic;
import com.ziroom.minsu.api.common.logic.ValidateResult;
import com.ziroom.minsu.api.house.service.HouseService;
import com.ziroom.minsu.api.house.service.HouseUpdateLogService;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HousePicMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.StaticResourceService;
import com.ziroom.minsu.services.basedata.entity.StaticResourceVo;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.api.inner.HouseUpdateHistoryLogService;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;
import com.ziroom.minsu.services.house.dto.HouseBaseDto;
import com.ziroom.minsu.services.house.dto.HouseBaseExtDto;
import com.ziroom.minsu.services.house.dto.HouseBaseParamsDto;
import com.ziroom.minsu.services.house.dto.HouseIssueParamsDto;
import com.ziroom.minsu.services.house.dto.HousePicBaseParamsDto;
import com.ziroom.minsu.services.house.dto.HousePicDto;
import com.ziroom.minsu.services.house.dto.HouseUpdateHistoryLogDto;
import com.ziroom.minsu.services.house.entity.HouseDefaultPicInfoVo;
import com.ziroom.minsu.services.house.entity.HouseFieldAuditLogVo;
import com.ziroom.minsu.services.house.entity.HousePicAuditVo;
import com.ziroom.minsu.services.house.entity.HousePicVo;
import com.ziroom.minsu.valenum.house.*;
import com.ziroom.minsu.valenum.msg.IsDelEnum;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>房源照片接口</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lunan
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("housePic")
public class HousePicController {

    @Resource(name = "basedata.cityTemplateService")
    private CityTemplateService cityTemplateService;

    @Resource(name = "api.paramCheckLogic")
    private ParamCheckLogic paramCheckLogic;

    @Resource(name="house.troyHouseMgtService")
    private TroyHouseMgtService troyHouseMgtService;

    @Resource(name="house.houseIssueService")
    private HouseIssueService houseIssueService;

    @Resource(name="basedata.staticResourceService")
    private StaticResourceService staticResourceService;

    @Value("#{'${upper_limit_rate}'.trim()}")
    private String upper_limit_rate;

    @Value("#{'${lower_limit_rate}'.trim()}")
    private String lower_limit_rate;

    @Value("#{'${pic_base_addr_mona}'.trim()}")
    private String picBaseAddrMona;

    @Value("#{'${list_small_pic}'.trim()}")
    private String list_small_pic;

    @Value("#{'${detail_big_pic}'.trim()}")
    private String detail_big_pic;
    
	@Resource(name = "api.houseService")
	private HouseService houseService;
	
	@Resource(name = "house.houseUpdateHistoryLogService")
	private HouseUpdateHistoryLogService houseUpdateHistoryLogService;
	
	@Resource(name="api.houseUpdateLogService")
	private HouseUpdateLogService houseUpdateLogService;

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HousePicController.class);

    /**
     *
     * 房源照片规则
     *
     * @author lunan
     * @created 2016年10月17日 下午8:27:05
     *
     * @param request
     * @return
     */
    @RequestMapping("${UNLOGIN_AUTH}/limitNum")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> limitNum(HttpServletRequest request) {
        try {
            JSONObject pic = new JSONObject();
            for (HousePicTypeEnum valEnum : HousePicTypeEnum.values()) {
                JSONObject obj1 = new JSONObject();
                obj1.put("name", valEnum.getName());
                obj1.put("min", valEnum.getMin());
                obj1.put("max", valEnum.getMax());
                pic.put(String.valueOf(valEnum.getCode()), obj1);
            }

            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(pic), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }
    }

    /**
     *
     * 房源照片规则
     *
     * @author jixd
     * @created 2016年10月17日 下午8:27:05
     * @param request
     * @return
     */
    @RequestMapping("${UNLOGIN_AUTH}/limitRules")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> limitRules(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        try {
            String params = cityTemplateService.getPicValidParams();
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(params);
            if (dto.getCode() == DataTransferObject.ERROR){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
            }
            Map<String, Integer> validMap = dto.parseData("validMap", new TypeReference<Map<String, Integer>>() {});

            resultJson.put("minPixel",validMap.get("minPixel"));
            resultJson.put("minDpi",validMap.get("minDpi"));
            resultJson.put("widthScale",validMap.get("widthScale"));
            resultJson.put("heightScale",validMap.get("heightScale"));
            resultJson.put("maxSize",validMap.get("maxSize"));
            resultJson.put("widthPixel",validMap.get("widthPixel"));
            resultJson.put("heightPixel",validMap.get("heightPixel"));
            resultJson.put("upperLimitRate",Float.parseFloat(upper_limit_rate));
            resultJson.put("lowerLimitRate",Float.parseFloat(lower_limit_rate));
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultJson), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }
    }

    /**
     * @Description: 房源列表照片 回显接口（上传限制一起返回,发布房源5-1）
     * @Author: lusp
     * @Date: 2017/6/19 16:44
     * @Params: request
     */
    @RequestMapping("/${LOGIN_AUTH}/housePhotosList")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> housePhotosList(HttpServletRequest request){
        JSONObject resultJson = new JSONObject();
        String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
        LogUtil.info(LOGGER, "[housePhotosList]参数：" + paramJson);
        try {
            ValidateResult<HouseBaseParamsDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,HouseBaseParamsDto.class);
            if (!validateResult.isSuccess()) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),HttpStatus.OK);
            }
            HouseBaseParamsDto requestDto = validateResult.getResultObj();
            String houseBaseFid = requestDto.getHouseBaseFid();
            Integer rentWay = requestDto.getRentWay();
            String houseRoomFid = requestDto.getRoomFid();
            /**
             * yanb 加入共享客厅校验
             * 如果是共享客厅,则根据houseBaseFid去数据库中取roomFid,赋值给houseRoomFid
             */
            if (!Check.NuNObj(rentWay) && rentWay.equals(RentWayEnum.ROOM.getCode())) {
                String isHallJson = houseIssueService.isHallByHouseBaseFid(houseBaseFid);
                Integer isHall = SOAResParseUtil.getIntFromDataByKey(isHallJson, "isHall");
                if (isHall.equals(RoomTypeEnum.HALL_TYPE.getCode())) {
                    String roomFidJson = houseIssueService.getHallRoomFidByHouseBaseFid(houseBaseFid);
                    houseRoomFid=SOAResParseUtil.getStrFromDataByKey(roomFidJson, "roomFid");
                }
            }

            HousePicDto housePicDto=new HousePicDto();
            housePicDto.setHouseBaseFid(houseBaseFid);
            if(!Check.NuNStr(houseRoomFid)){
                housePicDto.setHouseRoomFid(houseRoomFid);
            }
            //获取房源图片审核列表
            /**
             * yanb 调用方法业务修改
             */
            String housePicAuditVoJson=troyHouseMgtService.housePicAuditList(JsonEntityTransform.Object2Json(housePicDto));
            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(housePicAuditVoJson);
            if(resultDto.getCode() == DataTransferObject.ERROR){
                LogUtil.error(LOGGER, "获取房源图片审核列表失败",resultDto.getMsg());
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("系统异常"),HttpStatus.OK);
            }
            HousePicAuditVo housePicAuditVo = null;
            try {
                housePicAuditVo = SOAResParseUtil.getValueFromDataByKey(housePicAuditVoJson,"housePicAuditVo",HousePicAuditVo.class);
            } catch (SOAParseException e) {
                LogUtil.error(LOGGER, "SOAResParseUtil 转换异常",e);
                return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("系统异常"),
                        HttpStatus.OK);
            }

            List<HousePicVo> roomPicList = housePicAuditVo.getRoomPicList();
            List<HousePicVo> housePicList = housePicAuditVo.getHousePicList();

            //统计房间图片数量，合租房间图片数量在页面判断，供设置封面图片时使用
            Integer houseAllPicCount = 0;
            if(rentWay == RentWayEnum.HOUSE.getCode()){
                for(HousePicVo pic:roomPicList){
                    houseAllPicCount += pic.getPicList().size();
                }
                for(HousePicVo pic:housePicList){
                    houseAllPicCount += pic.getPicList().size();
                }
            }
            //查询要审核字段map
			Map<String , HouseFieldAuditLogVo> houseFieldAuditMap= houseService.houseFieldAuditLogVoConvertMap(houseBaseFid, houseRoomFid, rentWay, 0);
			
            //房源或者房间默认图片查询
            String picJson = null;
            //获取房源的审核状态，来提示上架后的房源图片需要审核
            Integer houseStatus = null;
            //默认图片baseUrl
            String defaultPic = null;
            //默认图片fid
            String defaultPicFid=null;

            if(rentWay == RentWayEnum.HOUSE.getCode()){
                // 整租获取房源状态
                DataTransferObject houseBaseMsgDto = JsonEntityTransform.json2DataTransferObject(houseIssueService.searchHouseBaseMsgByFid(houseBaseFid));
                HouseBaseMsgEntity houseMsgEntity = houseBaseMsgDto.parseData("obj", new TypeReference<HouseBaseMsgEntity>() {});
                houseStatus = houseMsgEntity.getHouseStatus();
                //整租获取房源默认图片
                picJson=houseIssueService.findDefaultPic(houseBaseFid, RentWayEnum.HOUSE.getCode());
                if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Ext_Default_Pic_Fid.getFieldPath())&&HouseStatusEnum.SJ.getCode()==houseStatus){
                	defaultPicFid=houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Ext_Default_Pic_Fid.getFieldPath()).getNewValue();
                	resultJson.put("defaultPicAuditMsg", ApiMessageConst.FIELD_AUDIT_MSG);
                }
            }

            if(rentWay == RentWayEnum.ROOM.getCode()&&!Check.NuNStr(houseRoomFid)&&!"null".equals(houseRoomFid)){
                // 分租获取房源状态
                DataTransferObject roomBaseMsgDto = JsonEntityTransform.json2DataTransferObject(houseIssueService.searchHouseRoomMsgByFid(houseRoomFid));
                HouseRoomMsgEntity roomMsgEntity = roomBaseMsgDto.parseData("obj", new TypeReference<HouseRoomMsgEntity>() {});
                houseStatus = roomMsgEntity.getRoomStatus();
                //分租获取默认图片
                picJson=houseIssueService.findDefaultPic(houseRoomFid, RentWayEnum.ROOM.getCode());
                if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Default_Pic_Fid.getFieldPath())&&HouseStatusEnum.SJ.getCode()==houseStatus){
                	defaultPicFid=houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Default_Pic_Fid.getFieldPath()).getNewValue();
                	resultJson.put("defaultPicAuditMsg", ApiMessageConst.FIELD_AUDIT_MSG);
                }
            }
            
            // 处理图片的大图和缩略图，同时处理默认图片，排序图片列表
            if(!Check.NuNStr(picJson)){
                defaultPic = SOAResParseUtil.getStrFromDataByKey(picJson, "picBaseUrl");
            }
            
            LogUtil.info(LOGGER, "查询审核的默认图片fid{}",defaultPicFid);
            HousePicMsgEntity housePicMsgEntity=null;
            //如果有未审核通过的默认图片，处理默认图片
            if(!Check.NuNStr(defaultPicFid)){
            	String defaultPicJson=houseIssueService.searchHousePicMsgByFid(defaultPicFid);
            	housePicMsgEntity=SOAResParseUtil.getValueFromDataByKey(defaultPicJson, "obj", HousePicMsgEntity.class);
            	if(!Check.NuNObj(housePicMsgEntity)){
            		defaultPic=housePicMsgEntity.getPicBaseUrl()+housePicMsgEntity.getPicSuffix();
            	}
            }
            
            if (!Check.NuNCollection(roomPicList)) {
                for (HousePicVo picVo : roomPicList) {
                    if (!Check.NuNCollection(picVo.getPicList())) {
                        for (int i = 0; i < picVo.getPicList().size(); i++) {
                            HousePicMsgEntity housePicMsg = picVo.getPicList().get(i);
                            housePicMsg.setMaxPicUrl(PicUtil.getFullPic(picBaseAddrMona, housePicMsg.getPicBaseUrl(), housePicMsg.getPicSuffix(), detail_big_pic));
                            housePicMsg.setMinPicUrl(PicUtil.getFullPic(picBaseAddrMona, housePicMsg.getPicBaseUrl(), housePicMsg.getPicSuffix(), list_small_pic));
                            //判断是否显示图片审核信息
                            if(!Check.NuNObj(houseStatus)&&HouseStatusEnum.SJ.getCode()==houseStatus&&(housePicMsg.getAuditStatus()==IsDelEnum.NOT_DEL.getCode()||housePicMsg.getAuditStatus()==3)){
                            	housePicMsg.setPicAuditMsg(ApiMessageConst.FIELD_AUDIT_MSG);
                            }
                            if(!Check.NuNStr(defaultPic)){
                                if (defaultPic.contains(housePicMsg.getPicBaseUrl())
                                        || housePicMsg.getPicBaseUrl().contains(defaultPic)) {
                                    Collections.swap(picVo.getPicList(), 0, i);
                                }
                            }
                        }
                    }
                }
            }
            if (!Check.NuNCollection(housePicList)) {
                for (HousePicVo picVo : housePicList) {
                    if (!Check.NuNCollection(picVo.getPicList())) {
                        for (int i = 0; i < picVo.getPicList().size(); i++) {
                            HousePicMsgEntity housePicMsg = picVo.getPicList().get(i);
                            housePicMsg.setMaxPicUrl(PicUtil.getFullPic(picBaseAddrMona, housePicMsg.getPicBaseUrl(), housePicMsg.getPicSuffix(), detail_big_pic));
                            housePicMsg.setMinPicUrl(PicUtil.getFullPic(picBaseAddrMona, housePicMsg.getPicBaseUrl(), housePicMsg.getPicSuffix(), list_small_pic));
                            if(!Check.NuNObj(houseStatus)&&HouseStatusEnum.SJ.getCode()==houseStatus&&(housePicMsg.getAuditStatus()==IsDelEnum.NOT_DEL.getCode()||housePicMsg.getAuditStatus()==3)){
                            	housePicMsg.setPicAuditMsg(ApiMessageConst.FIELD_AUDIT_MSG);
                            }
                            if(!Check.NuNStr(defaultPic)){
                                if (defaultPic.contains(housePicMsg.getPicBaseUrl())
                                        || housePicMsg.getPicBaseUrl().contains(defaultPic)) {
                                    Collections.swap(picVo.getPicList(), 0, i);
                                }
                            }
                        }
                    }
                }
            }


            //查询封面照片集合
            String  CoverPicListJson= houseIssueService.findDefaultPicListInfo(JsonEntityTransform.Object2Json(requestDto));
            DataTransferObject CoverPicListDto = JsonEntityTransform.json2DataTransferObject(CoverPicListJson);
            if(CoverPicListDto.getCode() == DataTransferObject.ERROR){
                LogUtil.error(LOGGER, "获取房源封面照片集合失败",CoverPicListDto.getMsg());
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("系统异常"),HttpStatus.OK);
            }
            List<HouseDefaultPicInfoVo> coverPicList = null;
            try {
                coverPicList = SOAResParseUtil.getListValueFromDataByKey(CoverPicListJson,"coverPicList",HouseDefaultPicInfoVo.class);
                /**
                 *
                 */
            } catch (SOAParseException e) {
                LogUtil.error(LOGGER, "SOAResParseUtil 转换异常",e);
                return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("系统异常"),
                        HttpStatus.OK);
            }
            
            //有要审核默认图片，处理先默认图片显示
            if(!Check.NuNObj(housePicMsgEntity)){
            	if(!Check.NuNCollection(coverPicList)){
            		coverPicList.get(0).setDefaultPicFid(housePicMsgEntity.getFid());
            		coverPicList.get(0).setPicBaseUrl(housePicMsgEntity.getPicBaseUrl());
            		coverPicList.get(0).setPicSuffix(housePicMsgEntity.getPicSuffix());
            		coverPicList.get(0).setPicType(housePicMsgEntity.getPicType());
            	}
            }
            
            //处理封面照片中含有已经设置为零的区域的照片，比如封面照片为客厅照片，而客厅现在数量为零，则该封面照片不再展示给用户，
            //并且如果用户没有重新设置封面照片，则在发布房源时给出提示：请设置封面照片
            String HouseBaseAndExtJson = houseIssueService.searchHouseBaseAndExtByFid(houseBaseFid);
            DataTransferObject HouseBaseAndExtDto = JsonEntityTransform.json2DataTransferObject(HouseBaseAndExtJson);
            if(HouseBaseAndExtDto.getCode() == DataTransferObject.ERROR){
                LogUtil.error(LOGGER, "获取房源基础和扩展信息失败，",HouseBaseAndExtDto.getMsg());
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("系统异常"),HttpStatus.OK);
            }
            HouseBaseExtDto houseBaseExt = null;
            try {
                houseBaseExt = SOAResParseUtil.getValueFromDataByKey(HouseBaseAndExtJson,"obj",HouseBaseExtDto.class);
            } catch (SOAParseException e) {
                LogUtil.error(LOGGER, "SOAResParseUtil 转换异常",e);
                return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("系统异常"),
                        HttpStatus.OK);
            }
            if(houseBaseExt.getHallNum() == 0 && !Check.NuNCollection(coverPicList)){
                for (HouseDefaultPicInfoVo houseDefaultPicInfoVo:coverPicList){
                    if(!Check.NuNObj(houseDefaultPicInfoVo)){
                        if(!Check.NuNObj(houseDefaultPicInfoVo.getPicType())){
                            if(houseDefaultPicInfoVo.getPicType() == HousePicTypeEnum.KT.getCode()){
                                houseDefaultPicInfoVo.setDefaultPicFid(null);
                                houseDefaultPicInfoVo.setPicBaseUrl(null);
                                houseDefaultPicInfoVo.setPicSuffix(null);
                                houseDefaultPicInfoVo.setPicType(null);
                            }
                        }
                    }
                }
            }

            /***封面照片现在是从房间、客厅、室外中选择的，暂时没有厨房区域，所以下段代码，暂时没用***/
        /*if(houseBaseExt.getKitchenNum() == 0){
            for (HouseDefaultPicInfoVo houseDefaultPicInfoVo:coverPicList){
                if(houseDefaultPicInfoVo.getPicType() == HousePicTypeEnum.CF.getCode()){
                    houseDefaultPicInfoVo.setDefaultPicFid(null);
                    houseDefaultPicInfoVo.setPicBaseUrl(null);
                    houseDefaultPicInfoVo.setPicSuffix(null);
                    houseDefaultPicInfoVo.setPicType(null);
                }
            }
        }*/


            //处理封面图片集合中的大图和缩略图
            if (!Check.NuNCollection(coverPicList)) {
                for (HouseDefaultPicInfoVo houseDefaultPicInfoVo : coverPicList) {
                    if(!Check.NuNStr(houseDefaultPicInfoVo.getDefaultPicFid())){
                        houseDefaultPicInfoVo.setMaxPicUrl(PicUtil.getFullPic(picBaseAddrMona, houseDefaultPicInfoVo.getPicBaseUrl(), houseDefaultPicInfoVo.getPicSuffix(), detail_big_pic));
                        houseDefaultPicInfoVo.setMinPicUrl(PicUtil.getFullPic(picBaseAddrMona, houseDefaultPicInfoVo.getPicBaseUrl(), houseDefaultPicInfoVo.getPicSuffix(), list_small_pic));
                    }
                }
            }
            String remindMsg = null;
            try {
                String remindMsgJson = staticResourceService.findStaticResListByResCode("HOUSE_ISSUE_UPLOADPIC_REMIND_CONTENT");
                List<StaticResourceVo> resList=SOAResParseUtil.getListValueFromDataByKey(remindMsgJson, "staticResList", StaticResourceVo.class);
                if(!Check.NuNCollection(resList)){
                    remindMsg = resList.get(resList.size()-1).getResContent();
                }
            } catch (SOAParseException e) {
                LogUtil.error(LOGGER, "获取上传房源照片提示信息失败",e);
            }
            resultJson.put("remindMsg",remindMsg);
            resultJson.put("houseBaseFid", houseBaseFid);
            resultJson.put("rentWay", rentWay);
            resultJson.put("houseRoomFid", houseRoomFid);
            resultJson.put("houseAllPicCount", houseAllPicCount);
            resultJson.put("houseStatus",houseStatus);
            resultJson.put("roomPicList", roomPicList);
            resultJson.put("picTypeList", housePicList);
            resultJson.put("roomNum", roomPicList==null?0:roomPicList.size());
            resultJson.put("coverPicList",coverPicList);
            LogUtil.info(LOGGER, "房源图片列表：{}",resultJson);
        }catch (Exception e){
            LogUtil.error(LOGGER,"housePhotosList(),获取房源图片列表出错，errorMsg:{}",e);
        }
        return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultJson), HttpStatus.OK);
    }

    /**
     * @Description: 删除房源图片
     * @Author: lusp
     * @Date: 2017/6/23 15:25
     * @Params: request
     */
    @RequestMapping("/${LOGIN_AUTH}/deleteHousePic")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> deleteHousePic(HttpServletRequest request) {
        try {
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(LOGGER, "删除房源图片参数：" + paramJson);
            DataTransferObject dto = new DataTransferObject();

            ValidateResult<HousePicBaseParamsDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,HousePicBaseParamsDto.class);
            if (!validateResult.isSuccess()) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),HttpStatus.OK);
            }
            HousePicBaseParamsDto housePicBaseParamsDto = validateResult.getResultObj();

            HousePicDto housePicDto = new HousePicDto();
            housePicDto.setHouseBaseFid(housePicBaseParamsDto.getHouseBaseFid());
            if(!Check.NuNStr(housePicBaseParamsDto.getHouseRoomFid())){
                housePicDto.setHouseRoomFid(housePicBaseParamsDto.getHouseRoomFid());
            }
            housePicDto.setHousePicFid(housePicBaseParamsDto.getHousePicFid());
            housePicDto.setPicType(housePicBaseParamsDto.getPicType());
            housePicDto.setPicSource(0);
            String deletePicJson = houseIssueService.deleteHousePicMsgByFid(JsonEntityTransform.Object2Json(housePicDto));
            dto = JsonEntityTransform.json2DataTransferObject(deletePicJson);
            if(dto.getCode() == DataTransferObject.ERROR){
                LogUtil.error(LOGGER, "删除房源图片失败,param:{},msg:{}",JsonEntityTransform.Object2Json(housePicDto),dto.getMsg());
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()),HttpStatus.OK);
            }
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }
    }

    /**
     *
     * 设置房源封面照片
     *
     * @author lusp
     * @created 2017年6月23日 下午10:41:09
     * @param request
     * @return
     */
    @RequestMapping("/${LOGIN_AUTH}/setDefaultPic")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> setDefaultPic(HttpServletRequest request) {
        try {
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(LOGGER, "设置房源封面照片：" + paramJson);
            DataTransferObject dto = new DataTransferObject();

            ValidateResult<HousePicBaseParamsDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,HousePicBaseParamsDto.class);
            if (!validateResult.isSuccess()) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),HttpStatus.OK);
            }
            HousePicBaseParamsDto housePicBaseParamsDto = validateResult.getResultObj();

            HousePicDto housePicDto = new HousePicDto();
            housePicDto.setHouseBaseFid(housePicBaseParamsDto.getHouseBaseFid());
            if(!Check.NuNStr(housePicBaseParamsDto.getHouseRoomFid())){
                housePicDto.setHouseRoomFid(housePicBaseParamsDto.getHouseRoomFid());
            }
            housePicDto.setHousePicFid(housePicBaseParamsDto.getHousePicFid());
            if(!Check.NuNObj(housePicBaseParamsDto.getPicType())){
                housePicDto.setPicType(housePicBaseParamsDto.getPicType());
            }
			String houseBaseExtJson=houseIssueService.searchHouseBaseAndExtByFid(housePicDto.getHouseBaseFid());
			HouseBaseExtDto houseBaseExtDto=SOAResParseUtil.getValueFromDataByKey(houseBaseExtJson, "obj", HouseBaseExtDto.class);
			//查询默认图片旧值
            HouseUpdateHistoryLogDto houseUpdateHistoryLogDto = houseUpdateLogService.findWaitUpdateHouseInfo(housePicDto.getHouseBaseFid(),housePicDto.getHouseRoomFid(), houseBaseExtDto.getRentWay());
            if(!Check.NuNObj(houseUpdateHistoryLogDto)){
    			houseUpdateHistoryLogDto.setSourceType(houseService.getUserSourceType(request));
    			houseUpdateHistoryLogDto.setCreateType(CreaterTypeEnum.LANLORD.getCode());
    			houseUpdateHistoryLogDto.setCreaterFid(houseService.getLandlordUid(request));
            }
			//更新房源封面照片
            String updateDefaultPicJson = houseIssueService.updateHouseDefaultPic(JsonEntityTransform.Object2Json(housePicDto));
            LogUtil.info(LOGGER, "设置默认图片结果：" + updateDefaultPicJson);
            dto = JsonEntityTransform.json2DataTransferObject(updateDefaultPicJson);
            if(dto.getCode() == DataTransferObject.ERROR){
                LogUtil.info(LOGGER, "参数:{}", JsonEntityTransform.Object2Json(housePicDto));
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
            }
            if(!Check.NuNObj(houseUpdateHistoryLogDto)){
				//添加默认照片修改记录
				if(houseBaseExtDto.getRentWay()==RentWayEnum.HOUSE.getCode()){
					HouseBaseExtEntity newHouseBaseExtEntity=new HouseBaseExtEntity();
					newHouseBaseExtEntity.setDefaultPicFid(housePicDto.getHousePicFid());
					houseUpdateHistoryLogDto.setHouseBaseExt(newHouseBaseExtEntity);
				} else if(houseBaseExtDto.getRentWay()==RentWayEnum.ROOM.getCode()) {
					HouseRoomMsgEntity newHouseRoomMsgEntity =new HouseRoomMsgEntity();
					newHouseRoomMsgEntity.setDefaultPicFid(housePicDto.getHousePicFid());
					houseUpdateHistoryLogDto.setHouseRoomMsg(newHouseRoomMsgEntity);
				}
				LogUtil.info(LOGGER, "保存修改默认图片历史结果：{}",JsonEntityTransform.Object2Json(houseUpdateHistoryLogDto));
				houseUpdateHistoryLogService.saveHistoryLog(JsonEntityTransform.Object2Json(houseUpdateHistoryLogDto));
            }
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }
    }

    /**
     * @Description: 发布房源操作
     * @Author: lusp
     * @Date: 2017/6/26 17:37
     * @Params: request
     */
    @RequestMapping("/${LOGIN_AUTH}/issueHouse")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> issueHouse(HttpServletRequest request) {
        try {
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(LOGGER, "[issueHouse]app发布房源参数：" + paramJson);
            DataTransferObject dto = new DataTransferObject();

            ValidateResult<HouseIssueParamsDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,HouseIssueParamsDto.class);
            if (!validateResult.isSuccess()) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),HttpStatus.OK);
            }
            HouseIssueParamsDto houseIssueParamsDto = validateResult.getResultObj();

            HouseBaseDto houseBaseDto = new HouseBaseDto();
            houseBaseDto.setHouseFid(houseIssueParamsDto.getHouseBaseFid());
            houseBaseDto.setRentWay(houseIssueParamsDto.getRentWay());
            houseBaseDto.setLandlordUid(houseIssueParamsDto.getLandlordUid());
            if(!Check.NuNStr(houseIssueParamsDto.getRoomFid())){
                houseBaseDto.setRoomFid(houseIssueParamsDto.getRoomFid());
            }

            Map<Integer, String> errorMsgMap = new TreeMap<Integer, String>();

            /**
             * yanb 调用方法业务修改
             */
            boolean isUploadable = this.validatePicNumByType(houseBaseDto.getHouseFid(), houseBaseDto.getRentWay(),
                    houseBaseDto.getRoomFid(), errorMsgMap);
            if (!isUploadable) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg(assembleErrorMsg(errorMsgMap));
                return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto),HttpStatus.OK);
            }

            /****************校验是否已经设置默认照片**************/
            //查询要审核字段map
			Map<String , HouseFieldAuditLogVo> houseFieldAuditMap= houseService.houseFieldAuditLogVoConvertMap(houseBaseDto.getHouseFid(), houseBaseDto.getRoomFid(), houseBaseDto.getRentWay(), 0);
            HouseBaseParamsDto houseBaseParamsDto = new HouseBaseParamsDto();
            houseBaseParamsDto.setHouseBaseFid(houseBaseDto.getHouseFid());
            houseBaseParamsDto.setRentWay(houseBaseDto.getRentWay());
            houseBaseParamsDto.setRoomFid(houseBaseDto.getRoomFid());
	        if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Ext_Default_Pic_Fid.getFieldPath())&&RentWayEnum.HOUSE.getCode()==houseBaseDto.getRentWay()){
            	houseBaseParamsDto.setAuditDefaultPic(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Ext_Default_Pic_Fid.getFieldPath()).getNewValue());
            }
	        if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Default_Pic_Fid.getFieldPath())&&RentWayEnum.ROOM.getCode()==houseBaseDto.getRentWay()){
            	houseBaseParamsDto.setAuditDefaultPic(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Default_Pic_Fid.getFieldPath()).getNewValue());
            }
	        if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_Hall_Num.getFieldPath())){
	        	houseBaseParamsDto.setAuditHallNum(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_Hall_Num.getFieldPath()).getNewValue()));
	        }
            String isSetDefaultPicJson = houseIssueService.isSetDefaultPic(JsonEntityTransform.Object2Json(houseBaseParamsDto));
            DataTransferObject isSetDefaultPicDto = JsonEntityTransform.json2DataTransferObject(isSetDefaultPicJson);
            if (isSetDefaultPicDto.getCode() == DataTransferObject.ERROR) {
                LogUtil.error(LOGGER, "issueHouse(),获取房源或房间是否设置了封面照片失败,msg:{}",isSetDefaultPicDto.getMsg());
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
            }
            Boolean hasDefault = SOAResParseUtil.getBooleanFromDataByKey(isSetDefaultPicJson,"hasDefault");
            if(!hasDefault){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请设置封面照片"), HttpStatus.OK);
            }
            /****************校验是否已经设置默认照片**************/

            String resultJson = houseIssueService.issueHouseInDetail(JsonEntityTransform.Object2Json(houseBaseDto));
            LogUtil.info(LOGGER, "app照片上传列表页进入，发布房源,结果:{}",resultJson);
            DataTransferObject issueDto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if(issueDto.getCode() == DataTransferObject.ERROR){
                LogUtil.info(LOGGER, "发布房源失败,msg:{}",issueDto.getMsg());
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(issueDto.getMsg()),HttpStatus.OK);
            }
            String remindMsg = null;
            List<String> nameList = null;
            //获取房源发布成功的提示消息
            try {
                String remindMsgJson = staticResourceService.findStaticResListByResCode("HOUSE_ISSUE_SUCCESS_REMIND_CONTENT");
                List<StaticResourceVo> resList=SOAResParseUtil.getListValueFromDataByKey(remindMsgJson, "staticResList", StaticResourceVo.class);
                if(!Check.NuNCollection(resList)){
                    remindMsg = resList.get(resList.size()-1).getResContent();
                }
            } catch (SOAParseException e) {
                LogUtil.error(LOGGER, "获取发布房源成功后提示信息失败",e);
            }
            //获取房源名称或房间名称集合
            HouseBaseParamsDto houseBaseParamDto = new HouseBaseParamsDto();
            BeanUtils.copyProperties(houseIssueParamsDto,houseBaseParamDto);
            String nameListJson = troyHouseMgtService.getHouseOrRoomNameList(JsonEntityTransform.Object2Json(houseBaseParamDto));
            DataTransferObject nameListDto = JsonEntityTransform.json2DataTransferObject(nameListJson);
            if(nameListDto.getCode() == DataTransferObject.SUCCESS){
                try {
                    nameList = SOAResParseUtil.getListValueFromDataByKey(nameListJson,"nameList",String.class);
                } catch (SOAParseException e) {
                    LogUtil.error(LOGGER, "SOAResParseUtil 转换异常",e);
                    return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("系统异常"),
                            HttpStatus.OK);
                }
            }else{
                LogUtil.error(LOGGER, "获取发布房源成功后提示信息失败,msg:{}",issueDto.getMsg());
            }
            dto.putValue("remindMsg",remindMsg);
            dto.putValue("nameList",nameList);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.info(LOGGER, "error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }


    }

    /**
     * 类型校验是否上传照片且照片数量达到最少限制数量
     *
     * @author lusp
     * @created 2017年6月27日
     * @param houseBaseFid
     * @param rentWay
     * @param houseRoomFid
     * @param errorMsgMap
     * @throws SOAParseException
     * @return
     */
    private boolean validatePicNumByType(String houseBaseFid, Integer rentWay, String houseRoomFid, Map<Integer, String> errorMsgMap) throws SOAParseException {
        String picJson = houseIssueService.searchHousePicMsgListByHouseFid(houseBaseFid);
        String houseBaseJsonString=houseIssueService.searchHouseBaseMsgByFid(houseBaseFid);
        HouseBaseMsgEntity houseBaseMsgEntity=SOAResParseUtil.getValueFromDataByKey(houseBaseJsonString, "obj", HouseBaseMsgEntity.class);
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(picJson);
        /****************兼容老版本0卫生间数据检验***************/
        if(houseBaseMsgEntity.getToiletNum() == 0){
            errorMsgMap.put(HousePicTypeEnum.WSJ.getCode(), "至少一个卫生间，请添加卫生间！");
            return false;
        }
        /****************兼容老版本0卫生间数据检验***************/
        if (dto.getCode() == DataTransferObject.SUCCESS) {
            //不算的照片数量
            int subNum=0;
            List<HousePicMsgEntity> picList = SOAResParseUtil.getListValueFromDataByKey(picJson, "list", HousePicMsgEntity.class);
            Map<String, List<HousePicMsgEntity>> wsMap = new HashMap<String, List<HousePicMsgEntity>>();
            List<HousePicMsgEntity> ktList = new ArrayList<HousePicMsgEntity>();
            List<HousePicMsgEntity> cfList = new ArrayList<HousePicMsgEntity>();
            List<HousePicMsgEntity> wsjList = new ArrayList<HousePicMsgEntity>();
            List<HousePicMsgEntity> swList = new ArrayList<HousePicMsgEntity>();
            for (HousePicMsgEntity housePicMsgEntity : picList) {
                Integer picType = housePicMsgEntity.getPicType();
                if (!Check.NuNObj(picType)) {
                    if (picType.intValue() == HousePicTypeEnum.WS.getCode()) {
                        this.filterRoomPic(wsMap, houseBaseFid, rentWay, houseRoomFid, housePicMsgEntity);
                    } else if (picType.intValue() == HousePicTypeEnum.KT.getCode()) {
                        ktList.add(housePicMsgEntity);
                    } else if (picType.intValue() == HousePicTypeEnum.CF.getCode()) {
                        cfList.add(housePicMsgEntity);
                    } else if (picType.intValue() == HousePicTypeEnum.WSJ.getCode()) {
                        wsjList.add(housePicMsgEntity);
                    } else if (picType.intValue() == HousePicTypeEnum.SW.getCode()) {
                        swList.add(housePicMsgEntity);
                    }
                }
            }

            boolean flag = true;
            int totalPicNum = 0;// 整套出租:所有图片 独立房间: 房间图片 + 公区图片
            int ktPicNum = ktList.size();
            //如果客厅为0 不进行判断
            if(houseBaseMsgEntity.getHallNum()!=0){
                totalPicNum += ktPicNum;
                if (ktPicNum < HousePicTypeEnum.KT.getMin()) {
                    flag = false;
                    errorMsgMap.put(HousePicTypeEnum.KT.getCode(), "客厅至少" + HousePicTypeEnum.KT.getMin() + "张照片");
                }
            } else {
                subNum+=HousePicTypeEnum.KT.getMin();
            }
            int cfPicNum = cfList.size();
            //如果厨房为0 不进行判断
            if(houseBaseMsgEntity.getKitchenNum()!=0){
                totalPicNum += cfPicNum;
                if (cfPicNum < HousePicTypeEnum.CF.getMin()) {
                    flag = false;
                    errorMsgMap.put(HousePicTypeEnum.CF.getCode(), "厨房至少" + HousePicTypeEnum.CF.getMin() + "张照片");
                }
            }else{
                subNum+=HousePicTypeEnum.CF.getMin();
            }
            int wsjPicNum = wsjList.size();
            //如果卫生间为0 不进行判断
            if(houseBaseMsgEntity.getToiletNum()!=0){
                totalPicNum += wsjPicNum;
                if (wsjPicNum < HousePicTypeEnum.WSJ.getMin()) {
                    flag = false;
                    errorMsgMap.put(HousePicTypeEnum.WSJ.getCode(), "卫生间至少" + HousePicTypeEnum.WSJ.getMin() + "张照片");
                }
            } else {
                subNum+=HousePicTypeEnum.WSJ.getMin();
            }
            int swPicNum = swList.size();
            totalPicNum += swPicNum;
            if (swPicNum < HousePicTypeEnum.SW.getMin()) {
                flag = false;
                errorMsgMap.put(HousePicTypeEnum.SW.getCode(), "室外至少" + HousePicTypeEnum.SW.getMin() + "张照片");
            }
            /**
             * 校验是否为共享客厅
             * 若是共享客厅便没有卧室照片
             * yanb
             */
            int roomType=0;
            if (rentWay == RentWayEnum.ROOM.getCode()) {
                String roomTypeJson = houseIssueService.isHallByHouseBaseFid(houseBaseFid);
                roomType = SOAResParseUtil.getIntFromDataByKey(roomTypeJson, "isHall");
            }
            if (roomType != 1) {
                if (Check.NuNMap(wsMap)) {
                    flag = false;
                    errorMsgMap.put(HousePicTypeEnum.WS.getCode(), "卧室至少" + HousePicTypeEnum.WS.getMin() + "张照片");
                }
            }
            /**
             * 校验规则修改，共享客厅 roomType=1 单独校验
             * yanb
             */
            if (rentWay == RentWayEnum.HOUSE.getCode()) {
                for (Map.Entry<String, List<HousePicMsgEntity>> entry : wsMap.entrySet()) {
                    int wsPicNum = entry.getValue().size();
                    totalPicNum += wsPicNum;
                    if (wsPicNum < HousePicTypeEnum.WS.getMin()) {
                        flag = false;
                        errorMsgMap.put(HousePicTypeEnum.WS.getCode(), "卧室至少" + HousePicTypeEnum.WS.getMin() + "张照片");
                    }
                }
                if (totalPicNum < (HousePicTypeEnum.MINIMUM_NUM_OF_HOUSE_PIC -subNum)) {
                    flag = false;
                    errorMsgMap.put(Integer.MAX_VALUE, "照片总数不能少于" + (HousePicTypeEnum.MINIMUM_NUM_OF_HOUSE_PIC -subNum)+ "张");
                }
            } else if (rentWay == RentWayEnum.ROOM.getCode() && roomType != 1) {
                for (Map.Entry<String, List<HousePicMsgEntity>> entry : wsMap.entrySet()) {
                    int wsPicNum = entry.getValue().size();
                    if (wsPicNum < HousePicTypeEnum.WS.getMin()) {
                        flag = false;
                        errorMsgMap.put(HousePicTypeEnum.WS.getCode(), "卧室至少" + HousePicTypeEnum.WS.getMin() + "张照片");
                    }
                    if (totalPicNum + wsPicNum < (HousePicTypeEnum.MINIMUM_NUM_OF_HOUSE_PIC - subNum)) {
                        flag = false;
                        errorMsgMap.put(Integer.MAX_VALUE, "每个房间照片+公共区域照片总数都不能少于" + (HousePicTypeEnum.MINIMUM_NUM_OF_HOUSE_PIC - subNum) + "张");
                    }
                }
            } else if (rentWay == RentWayEnum.ROOM.getCode() && roomType == 1) {
                    if (totalPicNum < (HousePicTypeEnum.MINIMUM_NUM_OF_HOUSE_PIC - subNum)) {
                        flag = false;
                        errorMsgMap.put(Integer.MAX_VALUE, "客厅+公共区域照片总数都不能少于" + (HousePicTypeEnum.MINIMUM_NUM_OF_HOUSE_PIC - subNum) + "张");
                }
            }

            return flag;
        }
        return false;
    }

    /**
     * 筛选指定房间照片
     *
     * @author lusp
     * @created 2017年6月27日
     * @param wsMap
     * @param houseBaseFid
     * @param houseRoomFid
     * @param housePicMsgEntity
     * @param isEmpty
     * @throws SOAParseException
     */
    private void filterRoomPic(Map<String, List<HousePicMsgEntity>> wsMap, String houseBaseFid, Integer rentWay, String houseRoomFid,
                               HousePicMsgEntity housePicMsgEntity) throws SOAParseException {
        if (!Check.NuNStrStrict(housePicMsgEntity.getRoomFid())) {
            if (rentWay == RentWayEnum.HOUSE.getCode() || (rentWay == RentWayEnum.ROOM.getCode() && Check.NuNStrStrict(houseRoomFid))) {
                if (Check.NuNMap(wsMap)) {
                    String resultJson = houseIssueService.searchRoomListByHouseBaseFid(houseBaseFid);
                    DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
                    if (dto.getCode() == DataTransferObject.SUCCESS) {
                        List<HouseRoomMsgEntity> roomList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseRoomMsgEntity.class);
                        for (HouseRoomMsgEntity houseRoomMsgEntity : roomList) {
                            List<HousePicMsgEntity> wsList = new ArrayList<HousePicMsgEntity>();
                            wsMap.put(houseRoomMsgEntity.getFid(), wsList);
                        }
                    }
                }
                if (wsMap.containsKey(housePicMsgEntity.getRoomFid())) {
                    List<HousePicMsgEntity> wsList = wsMap.get(housePicMsgEntity.getRoomFid());
                    wsList.add(housePicMsgEntity);
                }
            } else if (rentWay == RentWayEnum.ROOM.getCode() && !Check.NuNStrStrict(houseRoomFid)) {
                if (Check.NuNMap(wsMap)) {
                    List<HousePicMsgEntity> wsList = new ArrayList<HousePicMsgEntity>();
                    wsMap.put(houseRoomFid, wsList);
                }
                if (houseRoomFid.equals(housePicMsgEntity.getRoomFid())) {
                    List<HousePicMsgEntity> wsList = wsMap.get(houseRoomFid);
                    wsList.add(housePicMsgEntity);
                }
            }
        }
    }

    /**
     * 拼接错误信息字符串
     * @author lusp
     * @created 2017年6月27日
     * @param errorMsgMap
     * @return
     */
    private String assembleErrorMsg(Map<Integer, String> errorMsgMap) {
        int temp = 0;
        StringBuilder sb = new StringBuilder();
        for (String errorMsg : errorMsgMap.values()) {
            if (temp == 0) {
                sb.append(errorMsg);
            } else {
                sb.append("、").append(errorMsg);
            }
            temp++;
        }
        return sb.toString();
    }


}
