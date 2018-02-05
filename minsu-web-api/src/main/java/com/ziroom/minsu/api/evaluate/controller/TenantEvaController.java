package com.ziroom.minsu.api.evaluate.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.*;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.abs.AbstractController;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.constant.EvaluateConst;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.common.util.ApiDateUtil;
import com.ziroom.minsu.api.evaluate.dto.HouseSnapshotApiRequest;
import com.ziroom.minsu.api.evaluate.dto.TenantEvaApiRequest;
import com.ziroom.minsu.api.evaluate.entity.EvaluateInfoVo;
import com.ziroom.minsu.api.evaluate.entity.TenantEvaVos;
import com.ziroom.minsu.api.evaluate.enumvalue.EvaTypeEnum;
import com.ziroom.minsu.api.evaluate.service.EvalOrderService;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.LandlordEvaluateEntity;
import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.entity.order.OrderHouseSnapshotEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.constant.BaseDataConstant;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.dto.CustomerPicDto;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.evaluate.entity.EvaluateOrderShowVo;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.dto.OrderEvalRequest;
import com.ziroom.minsu.services.order.dto.OrderRequest;
import com.ziroom.minsu.services.order.entity.HouseSnapshotVo;
import com.ziroom.minsu.services.order.entity.OrderHouseVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import com.ziroom.minsu.valenum.common.RequestTypeEnum;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.PicTypeEnum;
import com.ziroom.minsu.valenum.evaluate.*;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.order.OrderEvaStatusEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import java.util.*;

/**
 * <p>房客评价相关接口API
 *   说明： 把房东 和 房客的评价分开写，主要为了 以后能分开考虑 
 * </p>
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
@RequestMapping("tenantEva/")
@Controller
public class TenantEvaController extends AbstractController{

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(TenantEvaController.class);

	@Resource(name = "evaluate.evaluateOrderService")
	private EvaluateOrderService evaluateOrderService;

	@Resource(name = "api.messageSource")
	private MessageSource messageSource;

	@Resource(name = "order.orderCommonService")
	private OrderCommonService orderCommonService;

	@Resource(name="basedata.confCityService")
	private ConfCityService confCityService;

	@Resource(name = "search.searchServiceApi")
	private SearchService searchService;
	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;

	@Value("#{'${detail_big_pic}'.trim()}")
	private String detail_big_pic;

	@Value("#{'${list_small_pic}'.trim()}")
	private String list_small_pic;

	@Value("#{'${ZIROOM_BASE_URL}'.trim()}")
	private String ZIROOM_BASE_URL;


	@Resource(name = "basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

	@Resource(name = "api.evalOrderService")
	private EvalOrderService evalOrderService;

	private String EVA_SHARE_URL = "zhuanti/2017/minsu/minsuEvaShare/index.html?orderSn=";


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
            String resultJson = orderCommonService.countWaitEvaNumAll(uid,UserTypeEnum.TENANT.getUserType());
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
	 * 房客评价（入口 点击评价按钮）
	 * <p>
	 * 业务说明：
	 * 1.分别校验两个实体参数  校验订单是否存在
	 * 2.订单房客是否已评价
	 * 3.该订单未评价，查看当前房客当前订单的评价记录 ，查询没有插入评价记录
	 * 4.插入成功后，修改订单状态为房客已评价，若此时房东已评价，修改为都已评价
	 * 5.修改订单状态成功后，修改此房客的评价记录标识为1，代表已评价
	 * 6.修改成功后，返回。
	 * 7.第2步订单房客为已评价，修改评价标识为1，成功返回。
	 * 8.第4步，插入失败后，返回失败结果
	 * 9.第5步，修改失败后，返回失败结果，此时，评论类容已插入，直接返回评论成功。
	 * 10.第5步中，修改评论标识错误后，直接返回评论成功。
	 *
	 * @param request
	 * @return
	 * @author yd
	 * @created 2016年5月1日 上午10:46:39
	 */
	@RequestMapping("${LOGIN_AUTH}/tenEvaluate")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> tenEvaluate(HttpServletRequest request) {
		DataTransferObject dto = new DataTransferObject();
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "参数：" + paramJson);
			TenantEvaApiRequest tenantEvaApiRequest = JsonEntityTransform.json2Object(paramJson, TenantEvaApiRequest.class);

			dto = checkEvaluateOrder(tenantEvaApiRequest, dto);

			if (dto.getCode() != 0) {
				return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
			}

			//查询当前订单
			dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.getOrderInfoByOrderSn(tenantEvaApiRequest.getOrderSn()));

			if (dto.getCode() == 0) {
				OrderInfoVo order = SOAResParseUtil.getValueFromDataByKey(dto.toJsonString(), "orderInfoVo", OrderInfoVo.class);
				//用户非法
				if (!tenantEvaApiRequest.getUid().equals(order.getUserUid())) {
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("当前评价用户非法");
					return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
				}
				//校验订单状态
				int orderStatu = order.getOrderStatus().intValue();
				if (orderStatu < OrderStatusEnum.CHECKED_IN.getOrderStatus()) {
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("订单状态非法，不能评价");
					return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
				}
				//查询订单快照
				dto = JsonEntityTransform.json2DataTransferObject(orderCommonService.findHouseSnapshotByOrderSn(tenantEvaApiRequest.getOrderSn()));
				//订单快照无
				if (dto.getCode() == 1) {
					return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
				}
				OrderHouseSnapshotEntity orderHouseSnapshot = dto.parseData("orderHouseSnapshot", new TypeReference<OrderHouseSnapshotEntity>() {
				});
				//评论内容过滤
				dto = JsonEntityTransform.json2DataTransferObject(this.searchService.getIkList(tenantEvaApiRequest.getContent()));

				List<String> listStrings = dto.parseData("ikList", new TypeReference<List<String>>() {
				});

				if (!Check.NuNCollection(listStrings)){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("评论包含敏感词，不能包含" + listStrings);
					return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
				}

				//校验订单房客是否已评价
				int evaStatus = order.getEvaStatus().intValue();
				if (evaStatus == OrderEvaStatusEnum.WAITINT_EVA.getCode() || evaStatus == OrderEvaStatusEnum.LANLORD_EVA.getCode()) {
					//保存房客信息
					EvaluateOrderEntity evaluateOrder = saveTenantEvaluate(order, orderHouseSnapshot, dto, tenantEvaApiRequest, order);
					if (dto.getCode() == 0) {
						if (!Check.NuNObj(order)) {
							//更新订单状态
							OrderEntity upOrder = new OrderEntity();
							upOrder.setOrderSn(order.getOrderSn());
							upOrder.setOldStatus(order.getOrderStatus());
							upOrder.setOrderStatus(order.getOrderStatus());
							if (evaStatus == OrderEvaStatusEnum.WAITINT_EVA.getCode()) {
								upOrder.setEvaStatus(OrderEvaStatusEnum.UESR_HVA_EVA.getCode());
							} else {
								upOrder.setEvaStatus(OrderEvaStatusEnum.ALL_EVA.getCode());
							}
							//修改评价状态
							dto= JsonEntityTransform.json2DataTransferObject(this.orderCommonService.updateOrderBaseByOrderSn(JsonEntityTransform.Object2Json(upOrder)));
							if (dto.getCode() == 0) {
								evaluateOrder.setOrderEvaFlag(OrderEvaFlagEnum.ORDE_HAVE_EVA.getCode());
								evaluateOrder.setLastModifyDate(new Date());
								dto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.updateEvaluateOrderByCondition(JsonEntityTransform.Object2Json(evaluateOrder)));

								//设置分享 参数
								dto = evaShare(dto,order);

								return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
							}
						}
					}
				}else{
					return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncryptFail("状态非法，不能评价"), HttpStatus.OK);
				}
			}
			return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务器异常-房客评价（入口 点击评价按钮）"), HttpStatus.OK);
		}


	}

	/**
	 * 分享参数
	 * @author jixd
	 * @created 2017年02月13日 16:00:20
	 * @param
	 * @return
	 */
	private DataTransferObject evaShare(DataTransferObject dto,OrderInfoVo order){
		//设置评价成功后的分享
		DataTransferObject cityNameDto = JsonEntityTransform.json2DataTransferObject(confCityService.getCityNameByCode(order.getCityCode()));
		if (cityNameDto.getCode() == DataTransferObject.SUCCESS){
			String cityName = cityNameDto.parseData("cityName", new TypeReference<String>() {});
			String mainTitle = String.format(EvaluateConst.EVA_SHARE_TITLE_MAIN_NAME,cityName);
			String shareUrl = ZIROOM_BASE_URL + EVA_SHARE_URL + order.getOrderSn();
			dto.putValue("mainTitle",mainTitle);
			dto.putValue("subTitle",EvaluateConst.EVA_SHARE_TITLE_SUB_NAME);
			dto.putValue("shareUrl",shareUrl);
		}
		return dto;
	}

	private DataTransferObject limitContent(int len){
		DataTransferObject dto = new DataTransferObject();
		DataTransferObject limitDto = JsonEntityTransform.json2DataTransferObject(cityTemplateService.getTextValue(null, EvaluateRulesEnum.EvaluateRulesEnum002.getValue()));
		if (limitDto.getCode() == DataTransferObject.SUCCESS){
			String limitStr = limitDto.parseData("textValue", new TypeReference<String>() {});
			int index = limitStr.indexOf(BaseDataConstant.EVAL_SPLIT);
			int min = ValueUtil.getintValue(limitStr.substring(0,index));
			int max = ValueUtil.getintValue(ValueUtil.getintValue(limitStr.substring(index+1)));
			if (len < min){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("内容太少");
			}
			if (len > max){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("内容太多");
			}
		}
		return dto;
	}

	/**
	 * 保存房客评价信息
	 *
	 * @param order
	 * @param orderHouseSnapshot
	 * @param dto
	 * @author yd
	 * @created 2016年5月1日 下午5:31:15
	 */
	private EvaluateOrderEntity saveTenantEvaluate(OrderHouseVo order, OrderHouseSnapshotEntity orderHouseSnapshot, DataTransferObject dto, TenantEvaApiRequest tenantEvaApiRequest, OrderEntity orderEntity) {

		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(cityTemplateService.getTextValue(null, String.valueOf(EvaluateRulesEnum.EvaluateRulesEnum001.getValue())));
		//房客未评价
		EvaluateOrderEntity evaluateOrder = new EvaluateOrderEntity();
		evaluateOrder.setCreateTime(new Date());
		evaluateOrder.setEvaStatu(EvaluateStatuEnum.AUDIT.getEvaStatuCode());
		evaluateOrder.setEvaUserType(UserTypeEnum.TENANT.getUserType());
		evaluateOrder.setEvaUserUid(order.getUserUid());
		evaluateOrder.setFid(UUIDGenerator.hexUUID());
		evaluateOrder.setHouseFid(orderHouseSnapshot.getHouseFid());
		evaluateOrder.setLastModifyDate(new Date());
		evaluateOrder.setOrderEvaFlag(OrderEvaFlagEnum.ORDER_NOT_HAVE_EVA.getCode());
		evaluateOrder.setOrderSn(order.getOrderSn());
		evaluateOrder.setRatedUserUid(order.getLandlordUid());
		evaluateOrder.setRentWay(order.getRentWay());
		evaluateOrder.setRoomFid(orderHouseSnapshot.getRoomFid());
		evaluateOrder.setBedFid(orderHouseSnapshot.getBedFid());


		if (resultDto.getCode() == DataTransferObject.SUCCESS
				&& !Check.NuNObj(resultDto.getData().get("textValue"))
				&& Integer.valueOf(resultDto.getData().get("textValue").toString()).intValue() == IsReleaseEnum.Release.getCode()) {
			evaluateOrder.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
		}

		TenantEvaluateEntity tenantEvaluate = new TenantEvaluateEntity();
		tenantEvaluate.setContent(tenantEvaApiRequest.getContent());
		tenantEvaluate.setCostPerformance(tenantEvaApiRequest.getCostPerformance());
		tenantEvaluate.setCreateTime(new Date());
		tenantEvaluate.setDescriptionMatch(tenantEvaApiRequest.getDescriptionMatch());
		tenantEvaluate.setEvaOrderFid(evaluateOrder.getFid());
		tenantEvaluate.setFid(UUIDGenerator.hexUUID());
		tenantEvaluate.setHouseClean(tenantEvaApiRequest.getHouseClean());
		tenantEvaluate.setIsDel(IsDelEnum.NOT_DEL.getCode());
		tenantEvaluate.setLastModifyDate(new Date());
		tenantEvaluate.setSafetyDegree(tenantEvaApiRequest.getSafetyDegree());
		tenantEvaluate.setTrafficPosition(tenantEvaApiRequest.getTrafficPosition());

		dto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.saveTenantEvaluate(JsonEntityTransform.Object2Json(tenantEvaluate), JsonEntityTransform.Object2Json(evaluateOrder), JsonEntityTransform.Object2Json(orderEntity)));

		return evaluateOrder;
	}

	/**
	 * 校验评价订单关联实体
	 *
	 * @param evaluateOrder
	 * @author yd
	 * @created 2016年5月1日 上午11:16:37
	 */
	private DataTransferObject checkEvaluateOrder(TenantEvaApiRequest evaluateOrder, DataTransferObject dto) {

		if (dto == null) dto = new DataTransferObject();

		if (Check.NuNObj(evaluateOrder) || Check.NuNStr(evaluateOrder.getOrderSn())
				|| Check.NuNStr(evaluateOrder.getContent())
				|| Check.NuNObj(evaluateOrder.getHouseClean())
				|| Check.NuNObj(evaluateOrder.getDescriptionMatch())
				|| Check.NuNObj(evaluateOrder.getSafetyDegree())
				|| Check.NuNObj(evaluateOrder.getTrafficPosition())
				|| Check.NuNObj(evaluateOrder.getCostPerformance())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("评价内容不能为空");
			return dto;
		}
		if (evaluateOrder.getHouseClean() == 0 || evaluateOrder.getDescriptionMatch() == 0
				|| evaluateOrder.getSafetyDegree() ==0 || evaluateOrder.getTrafficPosition()==0 || evaluateOrder.getCostPerformance() ==0){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请选择星级");
			return dto;
		}

		dto = limitContent(evaluateOrder.getContent().trim().length());
		return dto;
	}

	/**
	 * 2017-02-28版本之后不再使用,房东和房客统一一个列表
	 * 
	 * 分页查询房客的评价列表（包括已评价和未评价的）
	 * <p>
	 * 在未评价状态下：需要查出 当前的评价信息
	 * 条件：1.用户uid 2.评价状态 ：房客待评价  或 房客已评价  3.订单状态 必须为  正常退房完成 或 提前退房完成订单
	 * <p>
	 * 注意：此时 还应捞出评价相对应的审核状态，看当前评价是否已发布
	 * <p>
	 * 房客端：对于房东的评价，只有发布后才能展示，故判断房东待评价条件：
	 * A.房东未评价，以及房东评价不是发布状态
	 * B.房东已评价，房东的评价是发布状态
	 * <p>
	 * 故要封装房东评价的审核状态lanEvaStatu
	 *
	 * @param request
	 * @return
	 * @author yd
	 * @created 2016年5月2日 下午3:36:05
	 */
	@RequestMapping("${LOGIN_AUTH}/queryEvaluate")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> queryEvaluate(HttpServletRequest request) {

		DataTransferObject dto = new DataTransferObject();
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "参数：" + paramJson);
			HouseSnapshotApiRequest houseSnapshotApiRe = JsonEntityTransform.json2Object(paramJson, HouseSnapshotApiRequest.class);
			//默认待评价
			if (Check.NuNObj(houseSnapshotApiRe.getEvaType())) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("评价类型不存在");
				return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
			}
			//待返回实体
			List<TenantEvaVos> listTenantEvaVos = new ArrayList<TenantEvaVos>();
			Long total = 0L;
			PageResult pageResult = new PageResult();
			//查询参数
			OrderRequest orderRe = new OrderRequest();
			orderRe.setUserUid(houseSnapshotApiRe.getUid());
//			orderRe.setListEvaStatus(houseSnapshotApiRe.returnEvastatus());
//			orderRe.setListOrderStatus(houseSnapshotApiRe.returnOrderStatus());
			orderRe.setEvaType(houseSnapshotApiRe.getEvaType());
			orderRe.setRequestType(UserTypeEnum.TENANT.getUserCode());			
			orderRe.setLimitDay(getEvalTimeDay());

			String evaTips = "";
			if (houseSnapshotApiRe.getEvaType().intValue() == EvaTypeEnum.WAITING_EVA.getCode()) {
				evaTips = EvaTypeEnum.WAITING_EVA.getValue();
			} else {
				evaTips = EvaTypeEnum.HAD_EVA.getValue();
			}

			LogUtil.info(LOGGER, "房客列表查询参数orderRequest", orderRe.toJsonStr());

			dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.findHouseSnapshotByOrder(JsonEntityTransform.Object2Json(orderRe)));
			if (dto.getCode() == MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE)) {
				return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
			}
			List<HouseSnapshotVo> lisHouseSnapshot = dto.parseData("listSnapshot", new TypeReference<List<HouseSnapshotVo>>() {
			});
			total = Long.valueOf(dto.getData().get("total").toString());

			if (!Check.NuNCollection(lisHouseSnapshot)) {
				for (HouseSnapshotVo orderHouseSnapshot : lisHouseSnapshot) {
					TenantEvaVos tenantEvaVos = new TenantEvaVos();
					BeanUtils.copyProperties(orderHouseSnapshot, tenantEvaVos);
					tenantEvaVos.setStartTimeStr(DateUtil.dateFormat(orderHouseSnapshot.getStartTime(), "yyyy-MM-dd"));
					tenantEvaVos.setEndTimeStr(DateUtil.dateFormat(orderHouseSnapshot.getEndTime(), "yyyy-MM-dd"));
					tenantEvaVos.setPicUrl(PicUtil.getSpecialPic(picBaseAddrMona, tenantEvaVos.getPicUrl(), list_small_pic));
					tenantEvaVos.setFid(tenantEvaVos.returnFid(orderHouseSnapshot.getHouseFid(), orderHouseSnapshot.getRoomFid(), orderHouseSnapshot.getBedFid()));

					LogUtil.debug(LOGGER, "计算入住几晚，起始时间startTime={},结束时间", orderHouseSnapshot.getStartTime(), orderHouseSnapshot.getEndTime());
					int housingDay = DateSplitUtil.countDateSplit(orderHouseSnapshot.getStartTime(), orderHouseSnapshot.getEndTime());
					tenantEvaVos.setHousingDay(housingDay);
					tenantEvaVos.setEvaTips(evaTips);
					//添加房东评价的审核状态
					EvaluateRequest evaluateRequest = new EvaluateRequest();
					evaluateRequest.setOrderSn(orderHouseSnapshot.getOrderSn());
					evaluateRequest.setEvaUserType(UserTypeEnum.LANDLORD.getUserType());
					evaluateRequest.setEvaUserUid(orderHouseSnapshot.getLandlordUid());
					dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.findOrderContactsByOrderSn(orderHouseSnapshot.getOrderSn()));

					if (dto.getCode() == 0) {
						if (!Check.NuNObj(dto.getData().get("size"))) {
							tenantEvaVos.setPeopleNum(Integer.valueOf(dto.getData().get("size").toString()));
						}
					}
					dto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.queryEvaluateByOrderSn(JsonEntityTransform.Object2Json(evaluateRequest)));
					tenantEvaVos.setLanEvaStatu(0);

					if (dto.getCode() == 0) {
						Map<String, Object> map = dto.parseData("evaluateMap", new TypeReference<Map<String, Object>>() {
						});
						List<EvaluateOrderEntity> listOrderEvaluateOrderEntities = null;
						if (map.get("listOrderEvaluateOrder") != null) {
							listOrderEvaluateOrderEntities = JsonEntityTransform.json2ObjectList(JsonEntityTransform.Object2Json(map.get("listOrderEvaluateOrder")), EvaluateOrderEntity.class);
						}
						if (!Check.NuNCollection(listOrderEvaluateOrderEntities)) {
							for (EvaluateOrderEntity evaluateOrderEntity : listOrderEvaluateOrderEntities) {
								if (evaluateOrderEntity.getEvaUserType().intValue() == UserTypeEnum.LANDLORD.getUserType()) {
									tenantEvaVos.setLanEvaStatu(evaluateOrderEntity.getEvaStatu());
									if (tenantEvaVos.getLanEvaStatu().intValue() == EvaluateStatuEnum.ONLINE.getEvaStatuCode()) {
										if (houseSnapshotApiRe.getEvaType().intValue() == EvaTypeEnum.HAD_EVA.getCode()) {
											tenantEvaVos.setEvaTips("房客、房东均已评价");
										}
									} else {
										tenantEvaVos.setEvaTips(tenantEvaVos.getEvaTips() + "、房东未评价");
									}
								}
							}
						}
					}
					listTenantEvaVos.add(tenantEvaVos);
				}

			}
			pageResult.setRows(listTenantEvaVos);
			pageResult.setTotal(total);
			return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncryptOK(pageResult), HttpStatus.OK);

		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误-查询异常"), HttpStatus.OK);
		}

	}
	
	
	/**
	 * 获取评价时间
	 * @return
	 */
	private int  getEvalTimeDay(){
		/** 获取评价时间*/
		String timeStrJson = cityTemplateService.getTextValue(null, String.valueOf(EvaluateRulesEnum.EvaluateRulesEnum003.getValue()));
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(timeStrJson);
		if(resultDto.getCode() != DataTransferObject.SUCCESS){
			LogUtil.error(LOGGER, "获取评价时间,timeStrJson:{}", timeStrJson);
			throw new BusinessException("获取评价时间");
		}
		int limitDay = ValueUtil.getintValue(resultDto.getData().get("textValue"));
		return limitDay;
	}

	/**
	 * 查询 评价详情
	 *
	 * @param request
	 * @return
	 * @author yd
	 * @created 2016年5月2日 下午3:36:05
	 */
	@RequestMapping("${LOGIN_AUTH}/queryEvaluateInfo")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> queryEvaluateInfo(HttpServletRequest request) {
		DataTransferObject dto = new DataTransferObject();
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "参数：" + paramJson);
			TenantEvaApiRequest tenantEvaApiRequest = JsonEntityTransform.json2Object(paramJson, TenantEvaApiRequest.class);

			EvaluateInfoVo evaluateInfoVo = new EvaluateInfoVo();

			//查询评价的订单信息
			dto = JsonEntityTransform.json2DataTransferObject(orderCommonService.getOrderInfoByOrderSn(tenantEvaApiRequest.getOrderSn()));
			//订单快照无
			if (dto.getCode() == 1) {
				return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
			}
			OrderInfoVo oe = SOAResParseUtil.getValueFromDataByKey(dto.toJsonString(), "orderInfoVo", OrderInfoVo.class);

			//查询房东照片
			CustomerPicDto picDto = new CustomerPicDto();
			picDto.setPicType(PicTypeEnum.USER_PHOTO.getCode());
			picDto.setUid(oe.getLandlordUid());
			dto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.getCustomerPicByType(JsonEntityTransform.Object2Json(picDto)));
			if (dto.getCode() == 0) {
				CustomerPicMsgEntity customerPicMsg = dto.parseData("customerPicMsgEntity", new TypeReference<CustomerPicMsgEntity>() {
				});
				if (!Check.NuNObj(customerPicMsg) && !Check.NuNStr(customerPicMsg.getPicBaseUrl())) {
					if (customerPicMsg.getPicBaseUrl().contains("http")) {
						evaluateInfoVo.setLandlordPicUrl(customerPicMsg.getPicBaseUrl());
					} else {
						String fullPic = PicUtil.getFullPic(picBaseAddrMona, customerPicMsg.getPicBaseUrl(), customerPicMsg.getPicSuffix(), list_small_pic);
						evaluateInfoVo.setLandlordPicUrl(fullPic);
					}

				}
			}
			evaluateInfoVo.setLandlordName(oe.getLandlordName());
			evaluateInfoVo.setHouseName(oe.getHouseName());
			if (!Check.NuNObj(oe.getRentWay()) && oe.getRentWay().intValue() == RentWayEnum.ROOM.getCode()) {
				evaluateInfoVo.setHouseName(oe.getRoomName());
			}

			evaluateInfoVo.setUserName(oe.getUserName());
			evaluateInfoVo.setCheckInTime(oe.getCheckInTime());
			evaluateInfoVo.setCheckOutTime(oe.getCheckOutTime());
			evaluateInfoVo.setSumMoney(oe.getSumMoney());
			LogUtil.debug(LOGGER, "计算入住几晚，起始时间startTime={},结束时间", oe.getStartTime(), oe.getEndTime());
			int housingDay = DateSplitUtil.countDateSplit(oe.getStartTime(), oe.getEndTime());
			evaluateInfoVo.setHousingDay(housingDay);
			//评价详情房源图片 90 * 60
			evaluateInfoVo.setPicUrl(PicUtil.getSpecialPic(picBaseAddrMona, oe.getPicUrl(), list_small_pic));
			evaluateInfoVo.setStartTimeStr(DateUtil.dateFormat(oe.getStartTime(), "yyyy-MM-dd"));
			evaluateInfoVo.setEndTimeStr(DateUtil.dateFormat(oe.getEndTime(), "yyyy-MM-dd"));
			EvaluateRequest evaluateRequest = new EvaluateRequest();
			evaluateRequest.setOrderSn(tenantEvaApiRequest.getOrderSn());
			//TODO 是否要加评价状态 审核未通过的给不给 展示  evaluateRequest.setEvaStatu(evaStatu);
			evaluateRequest.setEvaUserType(UserTypeEnum.All.getUserType());
			evaluateRequest.setEvaUserUid(tenantEvaApiRequest.getUid());
			dto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.queryEvaluateByOrderSn(JsonEntityTransform.Object2Json(evaluateRequest)));
			Integer evaStatus = oe.getEvaStatus();
			Date realEndTime = oe.getRealEndTime();
			if (dto.getCode() != MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE)) {
				Map<String, Object> map = dto.parseData("evaluateMap", new TypeReference<Map<String, Object>>() {
				});
				List<EvaluateOrderShowVo> listOrderEvaluateOrderEntities = null;
				if (map.get("listOrderEvaluateOrder") != null) {
					listOrderEvaluateOrderEntities = JsonEntityTransform.json2ObjectList(JsonEntityTransform.Object2Json(map.get("listOrderEvaluateOrder")), EvaluateOrderShowVo.class);
				}
				if (!Check.NuNCollection(listOrderEvaluateOrderEntities)) {
					for (EvaluateOrderShowVo evaluateOrderEntity : listOrderEvaluateOrderEntities) {
						if (evaluateOrderEntity.getEvaUserType().intValue() == EvaluateUserEnum.TEN.getCode()) {
							evaluateInfoVo.setEvaluateOrder(evaluateOrderEntity);
							if (map.get("tenantEvaluate") != null){
								evaluateInfoVo.setTenantEvaluate(JsonEntityTransform.json2Object(JsonEntityTransform.Object2Json(map.get("tenantEvaluate")), TenantEvaluateEntity.class));
							}
						}

						//房东评价显示时机
						if ((evaluateOrderEntity.getEvaStatu().intValue() == EvaluateStatuEnum.ONLINE.getEvaStatuCode() && evaluateOrderEntity.getEvaUserType().intValue() == UserTypeEnum.LANDLORD.getUserType())) {
							int limitDay = evalOrderService.getEvalShowLimitDay();
							if (evaStatus == OrderEvaStatusEnum.ALL_EVA.getCode() || (evaStatus == OrderEvaStatusEnum.LANLORD_EVA.getCode() && !Check.NuNObj(realEndTime) && ApiDateUtil.isEvaExpire(realEndTime,limitDay))){
								if (map.get("landlordEvaluate") != null){
									evaluateInfoVo.setLandlordEvaluate(JsonEntityTransform.json2Object(JsonEntityTransform.Object2Json(map.get("landlordEvaluate")), LandlordEvaluateEntity.class));
								}
							}
						}

					}
				}

			} else {
				LogUtil.error(LOGGER, "查询房客的已评价列表时，封装房客的评价信息出现异常dto={},信息为", dto.toJsonString(), dto.getMsg());
			}
			return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncryptOK(evaluateInfoVo), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误-查询房客评价异常"), HttpStatus.OK);
		}
	}




    /**
     * 
     * 2017-02-28版本之后不再使用,房东和房客统一一个列表
     * 房客的待评价列表
     * @param request
     * @return
     */
    @RequestMapping("${LOGIN_AUTH}/queryEvaluateNew")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> queryEvaluateNew(HttpServletRequest request) {

        DataTransferObject dto = new DataTransferObject();
        try {
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(LOGGER, "参数：" + paramJson);
			OrderEvalRequest orderEvalRequest = JsonEntityTransform.json2Object(paramJson, OrderEvalRequest.class);
            //校验当前的列表类型
            if (Check.NuNObj(orderEvalRequest.getOrderEvalType())) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("当前评价类型不存在");
                return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
            }
			orderEvalRequest.setRequestType(RequestTypeEnum.TENANT.getRequestType());
            PageResult pageResult = new PageResult();
            LogUtil.debug(LOGGER, "房客列表查询参数orderRequest", orderEvalRequest.toJsonStr());
			//获取当前的评价列表
            dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.getOrderEavlList(JsonEntityTransform.Object2Json(orderEvalRequest)));
            if (dto.getCode() == MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE)) {
                return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
            }
            List<OrderInfoVo> orderList = dto.parseData("list", new TypeReference<List<OrderInfoVo>>() {
            });
			Long total = Long.valueOf(dto.getData().get("total").toString());
            pageResult.setRows(evalOrderService.tranOrder2Eval(orderList,orderEvalRequest));
            pageResult.setTotal(total);
            return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncryptOK(pageResult), HttpStatus.OK);

        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误-查询异常"), HttpStatus.OK);
        }
    }


}
