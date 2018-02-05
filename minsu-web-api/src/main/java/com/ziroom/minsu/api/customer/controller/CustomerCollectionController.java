package com.ziroom.minsu.api.customer.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.house.RoomTypeEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.abs.AbstractController;
import com.ziroom.minsu.api.common.constant.ApiConst;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.common.header.Header;
import com.ziroom.minsu.api.common.logic.ParamCheckLogic;
import com.ziroom.minsu.api.common.logic.ValidateResult;
import com.ziroom.minsu.api.customer.entity.HouseInfoVo;
import com.ziroom.minsu.api.house.service.HouseService;
import com.ziroom.minsu.entity.customer.CustomerCollectionEntity;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.customer.api.inner.CustomerCollectionService;
import com.ziroom.minsu.services.customer.dto.CollectionConditionDto;
import com.ziroom.minsu.services.customer.dto.CustomerCollectionDto;
import com.ziroom.minsu.services.customer.entity.CustomerCollectionVo;
import com.ziroom.minsu.services.house.dto.HouseDetailDto;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import com.ziroom.minsu.services.search.dto.HouseSearchRequest;
import com.ziroom.minsu.services.search.entity.HouseInfoEntity;
import com.ziroom.minsu.valenum.customer.LocationTypeEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;

/**
 * 
 * <p>客户房源收藏接口</p>
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
@RequestMapping("/customerColl")
@Controller
public class CustomerCollectionController extends AbstractController{
	
	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerCollectionController.class);
	
	@Resource(name = "customer.customerCollectionService")
	private CustomerCollectionService customerCollectionService;
	
	@Resource(name = "search.searchServiceApi")
	private SearchService searchService;
	
	@Resource(name="api.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;
	
	@Resource(name="api.houseService")
	private HouseService houseService;

    @Resource(name="house.houseIssueService")
    private HouseIssueService houseIssueService;
	
    @Autowired
    private RedisOperations redisOperations;
	
	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;
	
	@Value("#{'${detail_big_pic}'.trim()}")
	private String detailBigPic;
	
	@Value("#{'${default_head_size}'.trim()}")
	private String defaultHeadSize;
	
	/**
	 * 
	 * 用户收藏房源接口
	 * 
	 * 1.校验houseFid,rentWay,uid参数不能为空
	 * 2.根据uid查询用户收藏房源数量是否超过上限,超过上限直接返回
	 * 3.根据houseFid,rentWay调用搜索接口查询房源信息
	 * 4.根据houseFid,rentWay,uid查询是否存在房源收藏信息
	 * 5.不存在:新增房源收藏信息,存在:更新房源收藏信息
	 * 6.房源收藏信息变动之后,删除redis缓存
	 *
	 * @author liujun
	 * @created 2016年8月1日
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/collectHouse")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> collectHouse(HttpServletRequest request){
		try{
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "参数:{}", paramJson);

			ValidateResult<CollectionConditionDto> validateResult = paramCheckLogic
					.checkParamValidate(paramJson, CollectionConditionDto.class);
			if (!validateResult.isSuccess()) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),HttpStatus.OK);
			}
			CollectionConditionDto paramDto = validateResult.getResultObj();
			
			String countJson = customerCollectionService.countByUid(paramDto.getUid());
			DataTransferObject countDto = JsonEntityTransform.json2DataTransferObject(countJson);
			if(countDto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(LOGGER, "customerCollectionService.countByUid错误,参数:{},结果:{}", paramDto.getUid(), countJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(countDto.getMsg()),HttpStatus.OK);
			} 
			long num = SOAResParseUtil.getLongFromDataByKey(countJson, "num");
			if (num >= ApiConst.CUSTOMER_COLL_MAX_NUM) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("收藏夹已满"),HttpStatus.OK);
			}
			
			List<HouseSearchRequest> params = new ArrayList<>();
			HouseSearchRequest searchRequest = new HouseSearchRequest();
			searchRequest.setFid(paramDto.getHouseFid());
			searchRequest.setRentWay(paramDto.getRentWay());
			params.add(searchRequest);
			
			String houseJson = searchService.getHouseListByList(detailBigPic, JsonEntityTransform.Object2Json(params));
			DataTransferObject houseDto = JsonEntityTransform.json2DataTransferObject(houseJson);
			if(houseDto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(LOGGER, "searchService.getHouseListByList错误,参数:{}", JsonEntityTransform.Object2Json(params));
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(houseDto.getMsg()),HttpStatus.OK);
			}
			List<HouseInfoEntity> houseInfoList = SOAResParseUtil.getListValueFromDataByKey(houseJson, "list", HouseInfoEntity.class);
			if(Check.NuNCollection(houseInfoList) || Check.NuNObj(houseInfoList.get(0))){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源或房间信息不存在"),HttpStatus.OK);
			}
			
			CustomerCollectionEntity entity = new CustomerCollectionEntity();
			entity.setHouseFid(paramDto.getHouseFid());
			entity.setRentWay(paramDto.getRentWay());
			entity.setUid(paramDto.getUid());
			
			HouseInfoEntity houseInfoEntity = houseInfoList.get(0);
			entity.setHouseName(houseInfoEntity.getHouseName());
			entity.setLandlordUid(houseInfoEntity.getLandlordUid());
			this.spiltPicUrl(houseInfoEntity, entity);
			
			String collJson = customerCollectionService
					.findCustomerCollectionEntityByCondition(JsonEntityTransform.Object2Json(paramDto));
			DataTransferObject collDto = JsonEntityTransform.json2DataTransferObject(collJson);
			if(collDto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "customerCollectionService.findCustomerCollectionEntityByCondition错误,fid:{},结果:{}",
						JsonEntityTransform.Object2Json(paramDto), collJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(collDto.getMsg()),HttpStatus.OK);
			}
			CustomerCollectionEntity oldEntity = SOAResParseUtil.getValueFromDataByKey(collJson, "obj", CustomerCollectionEntity.class);

			String collResult = "0"; //0=收藏成功（默认）  1=收藏失败
			String resultJson = null;
			DataTransferObject dto = null;
			if(Check.NuNObj(oldEntity)){
				resultJson = customerCollectionService.saveCustomerCollectionEntity(JsonEntityTransform.Object2Json(entity));
				dto = JsonEntityTransform.json2DataTransferObject(resultJson);
				if(dto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "customerCollectionService.saveCustomerCollectionEntity错误,参数:{},结果:{}",JsonEntityTransform.Object2Json(entity), resultJson);
					collResult = "1";
				}
			} else {
				entity.setFid(oldEntity.getFid());
				entity.setIsDel(SysConst.IS_NOT_DEL);
				entity.setLastModifyDate(new Date());
				resultJson = customerCollectionService.updateCustomerCollectionByFid(JsonEntityTransform.Object2Json(entity));
				dto = JsonEntityTransform.json2DataTransferObject(resultJson);
				if(dto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "customerCollectionService.updateCustomerCollectionByFid错误,参数:{},结果:{}",JsonEntityTransform.Object2Json(entity), resultJson);
					collResult = "1";
				}
			}
			
			dto = new DataTransferObject();
			dto.putValue("collResult", collResult);
			if("0".equals(collResult)){
				//清除缓存
				String key = RedisKeyConst.getCollectKey(paramDto.getUid());
				try {
					redisOperations.del(key);
				} catch (Exception e) {
					LogUtil.error(LOGGER, "redis删除CollectKey错误", e);
				}
				
				LogUtil.debug(LOGGER, "返回结果:{}", resultJson);
			}
			
			try {
				Header header = getHeader(request);
				String uid = getUserId(request);
				houseService.saveLocation(uid,header,getIpAddress(request), LocationTypeEnum.COLLECT,paramDto.getHouseFid(),paramDto.getRentWay());
			}catch (Exception e){
				LogUtil.error(LOGGER, "收藏房源保存用户位置信息异常，error = {}", e);
			}
			
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()),HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(LOGGER, "collectHouse error, e:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(e.getMessage()), HttpStatus.OK);
		}
	}
	
	/**
	 * 切分图片访问基础地址及图片扩展名
	 *
	 * @author liujun
	 * @created 2016年8月5日
	 *
	 * @param houseInfoEntity
	 * @param entity
	 */
	private void spiltPicUrl(HouseInfoEntity houseInfoEntity, CustomerCollectionEntity entity) {
		if(!Check.NuNStr(houseInfoEntity.getPicUrl())){
			String basePicUrl = PicUtil.getBasePic(houseInfoEntity.getPicUrl(), picBaseAddrMona, detailBigPic);
			if(basePicUrl.contains(".")){
				if (basePicUrl.indexOf(".") == basePicUrl.lastIndexOf(".")) {
					entity.setPicBaseUrl(basePicUrl.substring(0, basePicUrl.indexOf(".")));
					entity.setPicSuffix(basePicUrl.substring(basePicUrl.indexOf(".")));
				} else {
					LogUtil.info(LOGGER, "basePicUrl error, basePicUrl={}", basePicUrl);
				}
			} else {
                LogUtil.info(LOGGER, "basePicUrl error, basePicUrl={}", basePicUrl);
            }
		}
	}

	/**
	 * 
	 * 用户删除收藏房源接口
	 * 
	 * 1.校验houseFid,rentWay,uid参数不能为空
	 * 2.根据houseFid,rentWay,uid查询是否存在房源收藏信息
	 * 3.不存在:返回错误信息,存在:如果房源收藏信息有效(isDel=0).删除房源收藏信息
	 *
	 * @author liujun
	 * @created 2016年8月2日
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/unCollectHouse")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> unCollectHouse(HttpServletRequest request){
		
		String delCollResult = "1"; //收藏移除标示  0=移除成功  1=移除失败（默认）
		DataTransferObject dtoRe = new DataTransferObject();
		try{
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "参数:{}", paramJson);
			
			ValidateResult<CollectionConditionDto> validateResult = paramCheckLogic
					.checkParamValidate(paramJson, CollectionConditionDto.class);
			if (!validateResult.isSuccess()) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),HttpStatus.OK);
			}
			CollectionConditionDto paramDto = validateResult.getResultObj();
			
			CustomerCollectionEntity oldEntity = null;
			String resultJson = customerCollectionService
					.findCustomerCollectionEntityByCondition(JsonEntityTransform.Object2Json(paramDto));
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(LOGGER, "customerCollectionService.findCustomerCollectionEntityByFid错误,参数:{},结果:{}",
						JsonEntityTransform.Object2Json(paramDto), resultJson);
				dtoRe.putValue("delCollResult", delCollResult);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dtoRe.getData()),HttpStatus.OK);
			} else {
				oldEntity = SOAResParseUtil.getValueFromDataByKey(resultJson, "obj", CustomerCollectionEntity.class);
			}
			
			
			if(Check.NuNObj(oldEntity)){
				LogUtil.info(LOGGER, "customerCollectionEntity is not exist,参数:{},结果:{}",
						JsonEntityTransform.Object2Json(paramDto), resultJson);
			}
			if(!Check.NuNObj(oldEntity)){
				delCollResult = "0";
				if (Check.NuNObj(oldEntity.getIsDel()) || oldEntity.getIsDel().intValue() == SysConst.IS_NOT_DEL) {
					CustomerCollectionEntity entity = new CustomerCollectionEntity();
					entity.setFid(oldEntity.getFid());
					entity.setIsDel(SysConst.IS_DEL);
					entity.setLastModifyDate(new Date());
					resultJson = customerCollectionService.updateCustomerCollectionByFid(JsonEntityTransform.Object2Json(entity));
					dto = JsonEntityTransform.json2DataTransferObject(resultJson);
					if(dto.getCode() == DataTransferObject.ERROR){
						LogUtil.error(LOGGER, "customerCollectionService.updateCustomerCollectionByFid错误,参数:{},结果:{}",
								JsonEntityTransform.Object2Json(entity), resultJson);
						delCollResult = "1";
						dtoRe.putValue("delCollResult", delCollResult);
						return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dtoRe.getData()),HttpStatus.OK);
					}
					//清除缓存
					String key = RedisKeyConst.getCollectKey(paramDto.getUid());
					try {
						redisOperations.del(key);
					} catch (Exception e) {
						LogUtil.error(LOGGER, "redis删除CollectKey错误", e);
					}
				} 
			}
			
			dtoRe.putValue("delCollResult", delCollResult);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dtoRe.getData()),HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(LOGGER, "unCollectHouse error, e:{}", e);
			delCollResult ="1";
			dtoRe.putValue("delCollResult", delCollResult);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dtoRe.getData()),HttpStatus.OK);
		}
	}
	
	/**
	 * 
	 * 查询用户收藏房源列表接口
	 * 
	 * 1.校验uid参数不能为空
	 * 2.根据uid查询房源收藏信息列表,列表为空直接返回
	 * 3.列表不为空,遍历拼接搜索接口参数
	 * 4.调用搜索接口查询房源列表信息
	 * 5.遍历组装房源返回信息
	 *
	 * @author liujun
	 * @created 2016年8月2日
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/collHouseList")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> collHouseList(HttpServletRequest request){
		try{
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "参数:{}", paramJson);
			ValidateResult<CustomerCollectionDto> validateResult = paramCheckLogic
					.checkParamValidate(paramJson, CustomerCollectionDto.class);
			if (!validateResult.isSuccess()) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),HttpStatus.OK);
			}
			CustomerCollectionDto entity = validateResult.getResultObj();
			
			String collJson = customerCollectionService.findCustomerCollectionVoListByUid(JsonEntityTransform.Object2Json(entity));
			DataTransferObject collDto = JsonEntityTransform.json2DataTransferObject(collJson);
			if(collDto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(LOGGER, "customerCollectionService.findCustomerCollectionEntityListByUid错误,参数:{},结果:{}",
						entity.getUid(), collJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(collDto.getMsg()),HttpStatus.OK);
			} 
			List<CustomerCollectionVo> collList = SOAResParseUtil
					.getListValueFromDataByKey(collJson, "rows",CustomerCollectionVo.class);
			int total = SOAResParseUtil.getIntFromDataByKey(collJson, "total");
			
			if(Check.NuNCollection(collList)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(collDto.getData()),HttpStatus.OK);
			}
			
			List<HouseSearchRequest> params = new ArrayList<HouseSearchRequest>();
			for (CustomerCollectionVo vo : collList) {
				HouseSearchRequest searchRequest = new HouseSearchRequest();
				searchRequest.setFid(vo.getHouseFid());
				searchRequest.setRentWay(vo.getRentWay());
				params.add(searchRequest);
			}
			//HouseInfoEntity
			String houseJson = searchService.getHouseListByList(detailBigPic, JsonEntityTransform.Object2Json(params));
			DataTransferObject houseDto = JsonEntityTransform.json2DataTransferObject(houseJson);
			if(houseDto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(LOGGER, "searchService.getHouseListByList错误,参数:{}", JsonEntityTransform.Object2Json(params));
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(houseDto.getMsg()),HttpStatus.OK);
			}
			
			List<HouseInfoVo> houseInfoList = SOAResParseUtil.getListValueFromDataByKey(houseJson, "list", HouseInfoVo.class);

			List<HouseInfoVo> list = new ArrayList<>();
			if (!Check.NuNCollection(houseInfoList)) {
				Map<String, HouseInfoVo> houseInfoMap = new HashMap<>();
				for (HouseInfoVo HouseInfoVo : houseInfoList) {
					houseInfoMap.put(HouseInfoVo.getFid(), HouseInfoVo);
				}

				for (CustomerCollectionVo vo : collList) {
					HouseInfoVo houseInfoVo = new HouseInfoVo();
					if (houseInfoMap.containsKey(vo.getHouseFid())) {
						houseInfoVo = houseInfoMap.get(vo.getHouseFid());
						houseInfoVo.setHouseStatus(HouseStatusEnum.SJ.getCode());
					} else {
                        // 设置rentWayName，如果是分租，查询数据库获取roomType，确定是否共享客厅

                        //出租方式
                        RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(vo.getRentWay());
                        houseInfoVo.setRentWayName(rentWayEnum.getName());

                        // 是独立房间，并且是共享客厅
                        if (RentWayEnum.ROOM.equals(rentWayEnum)) {
                            DataTransferObject roomDto = JsonEntityTransform.json2DataTransferObject(houseIssueService.searchRoomByRoomFid(vo.getHouseFid()));
                            if (DataTransferObject.SUCCESS == roomDto.getCode()) {
                                HouseRoomMsgEntity houseRoomMsgEntity = roomDto.parseData("houseRoomMsg", new TypeReference<HouseRoomMsgEntity>() {
                                });
                                //房间类型
                                RoomTypeEnum roomTypeEnum = Check.NuNObj(houseRoomMsgEntity) ? RoomTypeEnum.ROOM_TYPE : RoomTypeEnum.getEnumByCode(houseRoomMsgEntity.getRoomType());
                                if (RoomTypeEnum.ROOM_TYPE.equals(roomTypeEnum)) {
                                    houseInfoVo.setRentWayName(rentWayEnum.getName());
                                }else if(RoomTypeEnum.HALL_TYPE.equals(roomTypeEnum)){
                                    houseInfoVo.setRentWayName(RentWayEnum.HALL.getName());
                                }
                            }
                        }

						houseInfoVo.setHouseName(vo.getHouseName());
						houseInfoVo.setPicUrl(PicUtil.getFullPic(picBaseAddrMona,
								vo.getPicBaseUrl(), vo.getPicSuffix(), detailBigPic));
						houseInfoVo.setHouseStatus(HouseStatusEnum.XJ.getCode());
					}
					houseInfoVo.setFid(vo.getHouseFid());
					houseInfoVo.setRentWay(vo.getRentWay());
					houseInfoVo.setLandlordUid(vo.getLandlordUid());
					houseInfoVo.setLandlordUrl(PicUtil.getFullPic(picBaseAddrMona,
							vo.getLandPicBaseUrl(), vo.getLandPicSuffix(), defaultHeadSize));
					list.add(houseInfoVo);
				}

			}
			
			DataTransferObject dto = new DataTransferObject();
			dto.putValue("rows", list);
			dto.putValue("total", total);
			LogUtil.debug(LOGGER, "返回结果:{}", dto.toJsonString());
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()),HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(LOGGER, "collHouseList error, e:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(e.getMessage()), HttpStatus.OK);
		}
	}
	
	/**
	 * 
	 * 判断房源是否收藏
	 *
	 * @author yd
	 * @created 2016年8月9日 上午10:48:41
	 *
	 * @return  成功返回 说明已经收藏 ; 返回失败, 认为未被收藏
	 */
	@RequestMapping("/${LOGIN_AUTH}/isCollection")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> isCollection(HttpServletRequest request){
		
		String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
		
		ValidateResult<HouseDetailDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,
				HouseDetailDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.info(LOGGER, "校验房源是否收藏失败，参数不对");
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
					HttpStatus.OK);
		}
		//房源师傅收藏参数对象
		HouseDetailDto houseDetailDto = JsonEntityTransform.json2Object(paramJson, HouseDetailDto.class);
		
		String uid = (String) request.getAttribute("uid");
		if(Check.NuNStr(uid)){
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("用户不存在"),
					HttpStatus.OK);
		}
		
		CollectionConditionDto collectionConditionDto = new CollectionConditionDto();
		collectionConditionDto.setHouseFid(houseDetailDto.getFid());
		collectionConditionDto.setUid(uid);
		collectionConditionDto.setRentWay(houseDetailDto.getRentWay());
		
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.customerCollectionService.findCustomerCollectionEntityByCondition(JsonEntityTransform.Object2Json(collectionConditionDto)));
		
		if(dto.getCode() == DataTransferObject.ERROR){
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
					HttpStatus.OK);
		}
		String isCollection="0";//是否收藏  默认0=未收藏 1=已收藏
		CustomerCollectionEntity customerCollectionEntity = dto.parseData("obj",new TypeReference<CustomerCollectionEntity>() {
		} );
		if(!Check.NuNObj(customerCollectionEntity)&&IsDelEnum.NOT_DEL.getCode() == customerCollectionEntity.getIsDel()){
			isCollection = "1";
		}
		dto = new DataTransferObject();
		
		dto.putValue("isCollection", isCollection);
		return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()),
				HttpStatus.OK);
		
	}
	
}

