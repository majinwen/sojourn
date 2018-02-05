package com.ziroom.minsu.api.evaluate.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.*;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.abs.AbstractController;
import com.ziroom.minsu.api.common.constant.ApiMessageConst;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.common.util.ApiDateUtil;
import com.ziroom.minsu.api.common.util.ApiStringUtil;
import com.ziroom.minsu.api.evaluate.dto.LandlordEvaListRequest;
import com.ziroom.minsu.api.evaluate.dto.LanlordEvaApiRequest;
import com.ziroom.minsu.api.evaluate.service.EvalOrderService;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.LandlordEvaluateEntity;
import com.ziroom.minsu.entity.evaluate.LandlordReplyEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.entity.order.OrderHouseSnapshotEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.constant.BaseDataConstant;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerDetailImageVo;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.evaluate.entity.EvaluateItemVo;
import com.ziroom.minsu.services.evaluate.entity.EvaluateTwoContentItemVo;
import com.ziroom.minsu.services.evaluate.entity.TenantEvaItemVo;
import com.ziroom.minsu.services.evaluate.entity.TenantEvaluateVo;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.api.inner.OrderUserService;
import com.ziroom.minsu.services.order.dto.OrderEvalRequest;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import com.ziroom.minsu.valenum.common.RequestTypeEnum;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.customer.CustomerPicTypeEnum;
import com.ziroom.minsu.valenum.evaluate.*;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.order.OrderEvaStatusEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import org.apache.commons.collections.map.HashedMap;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * <p>房东评价</p>
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
@RequestMapping("/landlordEva")
@Controller
public class LandlordEvaController extends AbstractController {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(LandlordEvaController.class);

	@Resource(name = "evaluate.evaluateOrderService")
	private EvaluateOrderService evaluateOrderService;

	@Resource(name = "api.messageSource")
	private MessageSource messageSource;

	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	@Resource(name = "order.orderUserService")
	private OrderUserService orderUserService;

	@Resource(name = "order.orderCommonService")
	private OrderCommonService orderCommonService;
	@Resource(name = "api.evalOrderService")
	private EvalOrderService evalOrderService;

	@Resource(name = "search.searchServiceApi")
	private SearchService searchService;
	@Resource(name = "basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;

	@Value("#{'${detail_big_pic}'.trim()}")
	private String detail_big_pic;

	@Value("#{'${default_head_size}'.trim()}")
	private String default_head_size;

	@Value("#{'${USER_DEFAULT_PIC_URL}'.trim()}")
	private String USER_DEFAULT_PIC_URL;






	/**
	 * 获取当前的待评价的数量
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/${LOGIN_AUTH}/waitnum",method= RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> waitnum(HttpServletRequest request){
		try{
			/*** 获取当前的头信息 */
			String uid = getUserId(request);
			String resultJson = orderCommonService.countWaitEvaNumAll(uid,UserTypeEnum.LANDLORD.getUserType());
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(resultDto.getCode() != DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(resultDto.getMsg()), HttpStatus.OK);
			}
			Integer count = SOAResParseUtil.getValueFromDataByKey(resultJson, "count", Integer.class);
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("count", count);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultMap),HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(LOGGER, "waitnum is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}



	/**
	 * 
	 * 查询当前房东被评价的列表
	 *
	 * @author jixd
	 * @created 2016年5月28日 下午5:58:18
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${UNLOGIN_AUTH}/tenEvaList")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> queryEvaluateInfo(HttpServletRequest request) {
		DataTransferObject dto  = new DataTransferObject();
		try{
			String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			
			LandlordEvaListRequest landlordRequest = JsonEntityTransform.json2Object(paramJson, LandlordEvaListRequest.class);
			
			if(Check.NuNStr(landlordRequest.getLandlordUid())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房东uid不能为空"), HttpStatus.OK);
			}
			EvaluateRequest evaluateRequest = new EvaluateRequest();
			evaluateRequest.setPage(landlordRequest.getPage());
			evaluateRequest.setLimit(landlordRequest.getLimit());
			//被评人房东uid
			evaluateRequest.setRatedUserUid(landlordRequest.getLandlordUid());
			//评价人类型房客
			evaluateRequest.setEvaUserType(UserTypeEnum.TENANT.getUserType());
			
			String landlordEvaPage = evaluateOrderService.queryTenantEvaluateByPage(JsonEntityTransform.Object2Json(evaluateRequest));
			dto = JsonEntityTransform.json2DataTransferObject(landlordEvaPage);
			if(dto.getCode() != DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()),HttpStatus.OK);
			}
			int total = (int) dto.getData().get("total");
			List<TenantEvaluateVo> tenantEvalList = SOAResParseUtil.getListValueFromDataByKey(landlordEvaPage, "listTenantEvaluateVo", TenantEvaluateVo.class);
			List<TenantEvaItemVo> evaList = new ArrayList<TenantEvaItemVo>();
			for(TenantEvaluateVo tenantEva : tenantEvalList){
				TenantEvaItemVo itemVo = new TenantEvaItemVo();
				itemVo.setContent(tenantEva.getContent());
//				itemVo.setCreateTime(DateUtil.dateFormat(tenantEva.getCreateTime()));
				itemVo.setCreateTime(DateUtil.dateFormat(tenantEva.getCreateTime(), "yyyy年MM月"));
				//获取评价人uid
				String userUid = tenantEva.getEvaUserUid();
				setCustomerInfo(userUid,itemVo,UserTypeEnum.TENANT.getUserCode());
				evaList.add(itemVo);
			}
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("total", total);
			map.put("evaList", evaList);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(map),HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
		
	}
	
	
	/**
	 * 
	 * 1.查询房东被房客评价列表
	 * 2.遍历房客评价列表查询房东对房客评价
	 *
	 * @author liujun
	 * @created 2016年7月21日
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${UNLOGIN_AUTH}/evaList")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> queryEvaluateList(HttpServletRequest request) {
		DataTransferObject dto  = new DataTransferObject();
		try{
			String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			if (Check.NuNStr(paramJson)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(
						MessageSourceUtil.getChinese(messageSource, ApiMessageConst.PARAM_NULL)), HttpStatus.OK);
			}
			LogUtil.info(LOGGER, "queryEvaluateList参数:{}", paramJson);
			LandlordEvaListRequest landlordRequest = JsonEntityTransform.json2Object(paramJson, LandlordEvaListRequest.class);
			
			if(Check.NuNStr(landlordRequest.getLandlordUid())){
				LogUtil.info(LOGGER, "queryEvaluateList错误:{}", MessageSourceUtil.getChinese(messageSource, ApiMessageConst.UID_NULL));
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(
						MessageSourceUtil.getChinese(messageSource, ApiMessageConst.UID_NULL)), HttpStatus.OK);
			}
			
			EvaluateRequest evaluateRequest = new EvaluateRequest();
			evaluateRequest.setPage(landlordRequest.getPage());
			evaluateRequest.setLimit(landlordRequest.getLimit());
			//被评人房东uid
			evaluateRequest.setRatedUserUid(landlordRequest.getLandlordUid());
			//评价人类型房客
			evaluateRequest.setEvaUserType(UserTypeEnum.TENANT.getUserType());
			
			String landlordEvaPage = evaluateOrderService.queryTenantEvaluateByPage(JsonEntityTransform.Object2Json(evaluateRequest));
			dto = JsonEntityTransform.json2DataTransferObject(landlordEvaPage);
			if(dto.getCode() != DataTransferObject.SUCCESS){
				LogUtil.info(LOGGER, "evaluateOrderService.queryTenantEvaluateByPage错误,参数:{},结果{}", 
						JsonEntityTransform.Object2Json(evaluateRequest), landlordEvaPage);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()),HttpStatus.OK);
			}
			int total = Integer.valueOf(dto.getData().get("total").toString());
			List<TenantEvaluateVo> tenantEvalList = SOAResParseUtil.getListValueFromDataByKey(landlordEvaPage, "listTenantEvaluateVo", TenantEvaluateVo.class);
			List<EvaluateItemVo> evaList = new ArrayList<EvaluateItemVo>();
			for(TenantEvaluateVo tenantEva : tenantEvalList){
				EvaluateItemVo vo = new EvaluateItemVo();
				
				OrderHouseSnapshotEntity houseSnapshot = null;
				if(!Check.NuNStr(tenantEva.getOrderSn())){
					String snapshotJson = orderUserService.findHouseSnapshotByOrderSn(tenantEva.getOrderSn());
					DataTransferObject snapshotDto = JsonEntityTransform.json2DataTransferObject(snapshotJson);
					if(snapshotDto.getCode() == DataTransferObject.ERROR){
						LogUtil.info(LOGGER, "orderUserService.findHouseSnapshotByOrderSn错误,参数:{},结果{}", 
								tenantEva.getOrderSn(), snapshotJson);
					} else {
						houseSnapshot = SOAResParseUtil.getValueFromDataByKey(snapshotJson, "houseSnapshot", OrderHouseSnapshotEntity.class);
					}
				}
				
				if (!Check.NuNObj(tenantEva.getRentWay())
						&& tenantEva.getRentWay().intValue() == RentWayEnum.HOUSE.getCode()
						&& !Check.NuNStr(tenantEva.getOrderSn())) {
					if(!Check.NuNObj(houseSnapshot)){
						vo.setHouseName(houseSnapshot.getHouseName());
					}
					vo.setHouseFid(tenantEva.getHouseFid());
				} else if (!Check.NuNObj(tenantEva.getRentWay())
						&& tenantEva.getRentWay().intValue() == RentWayEnum.ROOM.getCode()
						&& !Check.NuNStr(tenantEva.getOrderSn())) {
					if(!Check.NuNObj(houseSnapshot)){
						vo.setHouseName(houseSnapshot.getRoomName());
					}
					vo.setHouseFid(tenantEva.getRoomFid());
				} else {
					// TODO
				}
				vo.setRentWay(tenantEva.getRentWay());
				
				TenantEvaItemVo tenantItem = new TenantEvaItemVo();
				tenantItem.setContent(tenantEva.getContent());
				tenantItem.setCreateTime(DateUtil.dateFormat(tenantEva.getCreateTime(), "yyyy年MM月"));
				//获取评价人uid
				String userUid = tenantEva.getEvaUserUid();
				setCustomerInfo(userUid,tenantItem,UserTypeEnum.TENANT.getUserCode());
				vo.setTenantEvaItem(tenantItem);
				
				//查询订单关联房东对房客的评价
				EvaluateRequest evaRequest = new EvaluateRequest();
				evaRequest.setOrderSn(tenantEva.getOrderSn());
				evaRequest.setEvaUserType(UserTypeEnum.LANDLORD.getUserType());
//				evaRequest.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
				String landlordEvalJson = evaluateOrderService.queryEvaluateByOrderSn(JsonEntityTransform.Object2Json(evaRequest));
				LogUtil.info(LOGGER, "landlordEvalJson:{}",landlordEvalJson);
				Map<String, Object> landlordEvalMap = null;
				DataTransferObject landlordEvalDto = JsonEntityTransform.json2DataTransferObject(landlordEvalJson);
				if(landlordEvalDto.getCode() == DataTransferObject.ERROR){
					LogUtil.info(LOGGER, "evaluateOrderService.queryEvaluateByOrderSn错误,参数:{},结果{}", 
							JsonEntityTransform.Object2Json(evaRequest), landlordEvalJson);
				}else{
					landlordEvalMap = landlordEvalDto.parseData("evaluateMap", new TypeReference<Map<String, Object>>() {});
				}
				
				List<EvaluateOrderEntity>  listOrderEvaluateOrderEntities = null;
				if(!Check.NuNMap(landlordEvalMap) && landlordEvalMap.get("listOrderEvaluateOrder") !=null){
					listOrderEvaluateOrderEntities = JsonEntityTransform.json2ObjectList(
							JsonEntityTransform.Object2Json(landlordEvalMap.get("listOrderEvaluateOrder")), EvaluateOrderEntity.class);
					
					if(!Check.NuNCollection(listOrderEvaluateOrderEntities)){
						for (EvaluateOrderEntity evaluateOrderEntity : listOrderEvaluateOrderEntities) {
							
							//不返回评价，只返回房东回复（2017-02-09）
//							if (evaluateOrderEntity.getEvaUserType().intValue() == UserTypeEnum.LANDLORD.getUserType()
//									&& evaluateOrderEntity.getEvaStatu().intValue() == EvaluateStatuEnum.ONLINE.getEvaStatuCode() 
//									&& !Check.NuNObj(landlordEvalMap.get("landlordEvaluate"))) {
//								LandlordEvaluateEntity landlordEvaluateEntity = JsonEntityTransform.json2Object(
//										JsonEntityTransform.Object2Json(landlordEvalMap.get("landlordEvaluate")), LandlordEvaluateEntity.class);
//								TenantEvaItemVo landlordItem = new TenantEvaItemVo();
//								landlordItem.setContent(landlordEvaluateEntity.getContent());
//								landlordItem.setCreateTime(DateUtil.dateFormat(landlordEvaluateEntity.getCreateTime(), "yyyy年MM月dd日"));
//								//获取评价人uid
//								String landlordUid = landlordRequest.getLandlordUid();
//								setCustomerInfo(landlordUid,landlordItem);
//								vo.setLandlordEvaItem(landlordItem);
//								break;
//							}
							
							if (evaluateOrderEntity.getEvaUserType() == EvaluateUserEnum.LAN_REPLY.getCode()
									&& !Check.NuNObj(landlordEvalMap.get("landlordReplyEntity"))) {
	                            
								//房东回复 
								LandlordReplyEntity evaluateReplyEntity = JsonEntityTransform.json2Object(JsonEntityTransform.Object2Json(landlordEvalMap.get("landlordReplyEntity")), LandlordReplyEntity.class);
                                
                                TenantEvaItemVo landlordItem = new TenantEvaItemVo();
								landlordItem.setContent(evaluateReplyEntity.getContent());
								landlordItem.setCreateTime(DateUtil.dateFormat(evaluateReplyEntity.getCreateDate(), "yyyy年MM月"));
								//获取评价人uid
								String landlordUid = landlordRequest.getLandlordUid();
								setCustomerInfo(landlordUid,landlordItem,UserTypeEnum.LANDLORD.getUserCode());
								vo.setLandlordEvaItem(landlordItem);
								break;
	                        }
							
							
						}

					} 
				}
				
				evaList.add(vo);
			}
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("total", total);
			map.put("evaList", evaList);
			LogUtil.info(LOGGER, "返回结果:{}", JsonEntityTransform.Object2Json(map));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(map),HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
		
	}


	/**
	 *
	 * 获取用户昵称和头像
	 *
	 * @author jixd
	 * @created 2016年5月28日 下午10:05:17
	 *
	 * @param uid
	 * @return
	 */
	private void setCustomerInfo(String uid,TenantEvaItemVo itemVo,int userType){
		try {
			String customerJson = customerMsgManagerService.getCustomerDetailImage(uid);
			
			UserTypeEnum userTypeEnum = UserTypeEnum.getUserTypeByCode(userType);

			DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
			if(customerDto.getCode() == DataTransferObject.SUCCESS){
				CustomerDetailImageVo vo = SOAResParseUtil.getValueFromDataByKey(customerJson, "customerImageVo", CustomerDetailImageVo.class);
				if(Check.NuNObj(vo)){
					itemVo.setUserHeadPic(USER_DEFAULT_PIC_URL);
					itemVo.setNickName(userTypeEnum==null?"":userTypeEnum.getUserTypeName());
					return;
				}				
				
				List<CustomerPicMsgEntity> customerPicList = vo.getCustomerPicList();
				String nickName = vo.getNickName();
				//昵称为空 使用真实姓名
				itemVo.setNickName(Check.NuNStr(nickName)?(userTypeEnum==null?"":userTypeEnum.getUserTypeName()):nickName);

				itemVo.setUserHeadPic(USER_DEFAULT_PIC_URL);
				for(CustomerPicMsgEntity picMsg : customerPicList){
					if(picMsg.getPicType() == CustomerPicTypeEnum.YHTX.getCode()){
						if(!Check.NuNStr(picMsg.getPicServerUuid())){
							String headPicUrl = PicUtil.getFullPic(picBaseAddrMona, picMsg.getPicBaseUrl(), picMsg.getPicSuffix(), default_head_size);
							itemVo.setUserHeadPic(headPicUrl);
						}
						if(!Check.NuNStr(picMsg.getPicBaseUrl()) && picMsg.getPicBaseUrl().startsWith("http")){
							itemVo.setUserHeadPic(picMsg.getPicBaseUrl());
						}
					}
				}

			}else{
				itemVo.setNickName("");
				itemVo.setUserHeadPic(USER_DEFAULT_PIC_URL);
			}
		} catch (SOAParseException e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}
	}


	/**
	 * 房东对房客公开回复
	 *
	 * @param
	 * @return
	 * @author jixd
	 * @created 2016年11月10日 18:11:18
	 */
	@RequestMapping("${LOGIN_AUTH}/saveEvaOrReply")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> saveEvaOrReply(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "房东评价参数或回复：" + paramJson);
			LanlordEvaApiRequest lanEvaApiRequest = JsonEntityTransform.json2Object(paramJson, LanlordEvaApiRequest.class);
			String orderSn = lanEvaApiRequest.getOrderSn();
			Integer evaUserType = lanEvaApiRequest.getEvaUserType();
			if (Check.NuNStr(orderSn)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("订单号为空"), HttpStatus.OK);
			}
			if (Check.NuNStr(lanEvaApiRequest.getContent())) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("评论内容为空"), HttpStatus.OK);
			}

			if (!(evaUserType == EvaluateUserEnum.LAN.getCode() || evaUserType == EvaluateUserEnum.LAN_REPLY.getCode())) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("评价类型错误"), HttpStatus.OK);
			}
			if (evaUserType == EvaluateUserEnum.LAN.getCode() && Check.NuNObj(lanEvaApiRequest.getLandlordSatisfied())) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("星级为空"), HttpStatus.OK);
			}
			//评论字数限制
			DataTransferObject limitDto = JsonEntityTransform.json2DataTransferObject(cityTemplateService.getTextValue(null, EvaluateRulesEnum.EvaluateRulesEnum002.getValue()));
			if (limitDto.getCode() == DataTransferObject.SUCCESS){
				String limitStr = limitDto.parseData("textValue", new TypeReference<String>() {});
				int index = limitStr.indexOf(BaseDataConstant.EVAL_SPLIT);
				int min = ValueUtil.getintValue(limitStr.substring(0,index));
				int max = ValueUtil.getintValue(ValueUtil.getintValue(limitStr.substring(index+1)));
				int len = lanEvaApiRequest.getContent().length();
				if (len < min){
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("填写内容太少"), HttpStatus.OK);
				}
				if (len > max){
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("填写内容过多"), HttpStatus.OK);
				}
			}

			//查询订单信息
			DataTransferObject orderDto = JsonEntityTransform.json2DataTransferObject(orderCommonService.getOrderByOrderSn(orderSn));
			LogUtil.info(LOGGER,"评价订单信息={}",orderDto.toJsonString());
			if (orderDto.getCode() == DataTransferObject.ERROR) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(orderDto.getMsg()), HttpStatus.OK);
			}
			OrderEntity orderEntity = orderDto.parseData("order", new TypeReference<OrderEntity>() {
			});
			//判断是否可以公开回复
			if (!lanEvaApiRequest.getUid().equals(orderEntity.getLandlordUid())) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("没有权限评价"), HttpStatus.OK);
			}
			//10 - 41 订单状态都不能填写回复
			Integer orderStatus = orderEntity.getOrderStatus();
			if (orderStatus < OrderStatusEnum.CHECKING_OUT.getOrderStatus()){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("状态不对，不能评价"), HttpStatus.OK);
			}


			if (!canYouDo(orderEntity,lanEvaApiRequest)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("状态非法，不能评价"), HttpStatus.OK);
			}


			//评论内容过滤
			DataTransferObject wordDto = JsonEntityTransform.json2DataTransferObject(this.searchService.getIkList(lanEvaApiRequest.getContent()));
			List<String> listStrings = wordDto.parseData("ikList", new TypeReference<List<String>>() {
			});
			if (!Check.NuNCollection(listStrings)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("评论包含敏感词，不能包含" + listStrings), HttpStatus.OK);
			}

			//查询订单快照获取房源信息
			DataTransferObject snapShotDto = JsonEntityTransform.json2DataTransferObject(orderCommonService.findHouseSnapshotByOrderSn(orderSn));
			if (snapShotDto.getCode() == DataTransferObject.ERROR) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(snapShotDto.getMsg()), HttpStatus.OK);
			}
			OrderHouseSnapshotEntity orderHouseSnapshot = snapShotDto.parseData("orderHouseSnapshot", new TypeReference<OrderHouseSnapshotEntity>() {
			});

			//获取评价是否需要审核的开关值
			DataTransferObject auditDto = JsonEntityTransform.json2DataTransferObject(cityTemplateService.getTextValue(null, String.valueOf(EvaluateRulesEnum.EvaluateRulesEnum001.getValue())));
			if (auditDto.getCode() == DataTransferObject.ERROR) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(auditDto.getMsg()), HttpStatus.OK);
			}
			Integer auditStatus = Integer.parseInt(auditDto.getData().get("textValue").toString());

			//保存处理逻辑
			DataTransferObject dataTransferObject = saveLandlordContent(lanEvaApiRequest, orderEntity, orderHouseSnapshot, auditStatus);

			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dataTransferObject), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}

	/**
	 * 保存房东填写内容   评价or回复
	 *
	 * @param
	 * @return
	 * @author jixd
	 * @created 2017年02月09日 15:24:49
	 */
	private DataTransferObject saveLandlordContent(LanlordEvaApiRequest lanEvaApiRequest, OrderEntity orderEntity, OrderHouseSnapshotEntity orderHouseSnapshot, Integer auditStatus) {
		DataTransferObject dto = new DataTransferObject();
		EvaluateOrderEntity evaluateOrderEntity = new EvaluateOrderEntity();
		evaluateOrderEntity.setFid(UUIDGenerator.hexUUID());
		evaluateOrderEntity.setHouseFid(orderHouseSnapshot.getHouseFid());
		evaluateOrderEntity.setRoomFid(orderHouseSnapshot.getRoomFid());
		evaluateOrderEntity.setRentWay(orderHouseSnapshot.getRentWay());
		evaluateOrderEntity.setEvaUserUid(orderEntity.getLandlordUid());
		evaluateOrderEntity.setRatedUserUid(orderEntity.getUserUid());
		evaluateOrderEntity.setOrderSn(orderEntity.getOrderSn());
		if (auditStatus == IsReleaseEnum.Release.getCode()) {
			evaluateOrderEntity.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
		} else {
			evaluateOrderEntity.setEvaStatu(EvaluateStatuEnum.AUDIT.getEvaStatuCode());
		}
		if (lanEvaApiRequest.getEvaUserType() == EvaluateUserEnum.LAN.getCode()) {
			evaluateOrderEntity.setEvaUserType(EvaluateUserEnum.LAN.getCode());
			LandlordEvaluateEntity landlordEvaluateEntity = new LandlordEvaluateEntity();
			landlordEvaluateEntity.setContent(lanEvaApiRequest.getContent());
			landlordEvaluateEntity.setLandlordSatisfied(lanEvaApiRequest.getLandlordSatisfied());
			dto = JsonEntityTransform.json2DataTransferObject(evaluateOrderService.saveLandlordEvaluate(JsonEntityTransform.Object2Json(landlordEvaluateEntity),
					JsonEntityTransform.Object2Json(evaluateOrderEntity), JsonEntityTransform.Object2Json(orderEntity)));
			LogUtil.info(LOGGER, "房东评价返回结果={}", dto.toJsonString());

			//评价保存成功 更新订单评价状态
			OrderEntity upOrderEntity = new OrderEntity();
			upOrderEntity.setOldStatus(orderEntity.getOrderStatus());
			upOrderEntity.setOrderSn(orderEntity.getOrderSn());
			upOrderEntity.setOrderStatus(orderEntity.getOrderStatus());
			if (orderEntity.getEvaStatus().intValue() == OrderEvaStatusEnum.WAITINT_EVA.getCode()) {
				upOrderEntity.setEvaStatus(OrderEvaStatusEnum.LANLORD_EVA.getCode());
			} else if (orderEntity.getEvaStatus().intValue() == OrderEvaStatusEnum.UESR_HVA_EVA.getCode()) {
				upOrderEntity.setEvaStatus(OrderEvaStatusEnum.ALL_EVA.getCode());
			}
			LogUtil.info(LOGGER,"更新订单状态info={}",JsonEntityTransform.Object2Json(upOrderEntity));
			DataTransferObject upDto = JsonEntityTransform.json2DataTransferObject(orderCommonService.updateOrderBaseByOrderSn(JsonEntityTransform.Object2Json(upOrderEntity)));
			LogUtil.info(LOGGER,"更新结果result={}",upDto.toJsonString());
			if (upDto.getCode() == DataTransferObject.SUCCESS) {
				//更新订单状态成功以后更新同步状态
				evaluateOrderEntity.setOrderEvaFlag(OrderEvaFlagEnum.ORDE_HAVE_EVA.getCode());
				String upeva = evaluateOrderService.updateEvaluateOrderByCondition(JsonEntityTransform.Object2Json(evaluateOrderEntity));
				LogUtil.info(LOGGER,"评价同步结果result={}",upeva);
			}
		} else if (lanEvaApiRequest.getEvaUserType() == EvaluateUserEnum.LAN_REPLY.getCode()) {
			evaluateOrderEntity.setEvaUserType(EvaluateUserEnum.LAN_REPLY.getCode());
			evaluateOrderEntity.setOrderEvaFlag(OrderEvaFlagEnum.ORDE_HAVE_EVA.getCode());
			LandlordReplyEntity landlordReplyEntity = new LandlordReplyEntity();
			landlordReplyEntity.setContent(lanEvaApiRequest.getContent());
			dto = JsonEntityTransform.json2DataTransferObject(evaluateOrderService.saveLandlordReply(JsonEntityTransform.Object2Json(landlordReplyEntity),
					JsonEntityTransform.Object2Json(evaluateOrderEntity)));
			LogUtil.info(LOGGER, "房东回复保存结果={}", dto.toJsonString());
		}
		return dto;
	}

	/**
	 * 是否房东是否可以评价 或者 回复
	 * @return
	 */
	public boolean canYouDo(OrderEntity orderEntity,LanlordEvaApiRequest lanEvaApiRequest){
		boolean youCan = false;
		int pjTime = evalOrderService.getEvalShowLimitDay();
		Integer orderEvaStatus = orderEntity.getEvaStatus();
		if (lanEvaApiRequest.getEvaUserType() == EvaluateUserEnum.LAN.getCode()){
			//是否可以评价
			if ((orderEvaStatus == OrderEvaStatusEnum.WAITINT_EVA.getCode() || orderEvaStatus == OrderEvaStatusEnum.UESR_HVA_EVA.getCode())
					&& !Check.NuNObj(orderEntity.getRealEndTime()) && !ApiDateUtil.isEvaExpire(orderEntity.getRealEndTime(), pjTime)){
				youCan = true;
			}else{
				youCan = false;
			}
		}

		if (lanEvaApiRequest.getEvaUserType() == EvaluateUserEnum.LAN_REPLY.getCode()){
			//是否可以回复
			OrderInfoVo orderInfoVo = new OrderInfoVo();
			EvaluateRequest evaluateRequest = new EvaluateRequest();
			evaluateRequest.setOrderSn(lanEvaApiRequest.getOrderSn());
			DataTransferObject evaDto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.queryEvaluateByOrderSn(JsonEntityTransform.Object2Json(evaluateRequest)));
			if (evaDto.getCode() == DataTransferObject.SUCCESS) {
				Map<String, Object> evaluateMap = evaDto.parseData("evaluateMap", new TypeReference<Map<String, Object>>() {});
				List<EvaluateOrderEntity> listOrderEvaluateOrderEntities = null;
				if (evaluateMap.get("listOrderEvaluateOrder") != null) {
					listOrderEvaluateOrderEntities = JsonEntityTransform.json2ObjectList(JsonEntityTransform.Object2Json(evaluateMap.get("listOrderEvaluateOrder")), EvaluateOrderEntity.class);
				}
				if (!Check.NuNCollection(listOrderEvaluateOrderEntities)) {
					for (EvaluateOrderEntity evaluateOrderEntity : listOrderEvaluateOrderEntities) {
						Integer evaUserType = evaluateOrderEntity.getEvaUserType();
						Integer evaStatus = evaluateOrderEntity.getEvaStatu();
						if (evaUserType == EvaluateUserEnum.LAN.getCode()) {
							orderInfoVo.setLandEvaStatu(evaStatus);
							orderInfoVo.setLandEvalTime(evaluateOrderEntity.getLastModifyDate());
						}
						if (evaUserType == EvaluateUserEnum.TEN.getCode()) {
							orderInfoVo.setTenantEvaStatu(evaStatus);
							orderInfoVo.setTenantEvalTime(evaluateOrderEntity.getLastModifyDate());

						}
					}
				}
			}
			orderInfoVo.setRealEndTime(orderEntity.getRealEndTime());
			orderInfoVo.setEvaStatus(orderEntity.getEvaStatus());
			youCan = evalOrderService.isLandCanRepply(orderInfoVo);
		}
		return youCan;
	}




}
