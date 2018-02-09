/**
 * @FileName: Photographer.java
 * @Package com.ziroom.minsu.troy.photographer.controller
 * 
 * @author yd
 * @created 2016年11月7日 上午11:31:24
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.troy.photographer.controller;

import com.alibaba.fastjson.JSON;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBookLogEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBookOrderEntity;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.EmployeeService;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.dto.CustomerBaseMsgDto;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;
import com.ziroom.minsu.services.house.api.inner.TroyPhotogBookService;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.services.house.dto.BookHousePhotogDto;
import com.ziroom.minsu.services.house.dto.HouseRequestDto;
import com.ziroom.minsu.services.house.entity.NeedPhotogHouseVo;
import com.ziroom.minsu.services.house.photog.dto.PhotoOrderDto;
import com.ziroom.minsu.services.house.photog.dto.PhotogLogDto;
import com.ziroom.minsu.services.house.photog.dto.PhotogOrderUpdateDto;
import com.ziroom.minsu.services.house.photog.vo.PhotographerBookOrderVo;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.minsu.valenum.photographer.BookOrderStatuEnum;
import com.ziroom.minsu.valenum.photographer.CreaterTypeEnum;
import com.ziroom.minsu.valenum.photographer.PhotographerSourceEnum;
import com.ziroom.minsu.valenum.photographer.UpdateTypeEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>摄影师预约管理</p>
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
@RequestMapping("/photog")
public class PhotographerBookController {


	private  static Logger LOGGER =LoggerFactory.getLogger(PhotographerBookController.class);

	@Resource(name="house.troyHouseMgtService")
	private TroyHouseMgtService troyHouseMgtService;

	@Resource(name="house.houseIssueService")
	private HouseIssueService houseIssueService;

	@Resource(name="customer.customerInfoService")
	private CustomerInfoService customerInfoService;

	@Resource(name = "photographer.troyPhotogBookService")
	private TroyPhotogBookService troyPhotogBookService;

	@Resource(name = "basedata.employeeService")
	private EmployeeService  employeeService;
	
	@Resource(name="basedata.confCityService")
    private ConfCityService confCityService;

	@Resource(name="basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;

	/**
	 * 
	 * 到 需要拍照的房源列表
	 *
	 * @author yd
	 * @created 2016年11月7日 上午11:53:21
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/goToPhotographerHouse")
	public String goToPhotographerHouse(HttpServletRequest request){
		return "/photog/listPhotogHouse";
	}

	/**
	 * 
	 * 查询需要拍摄房源的列表信息
	 *
	 * @author yd
	 * @created 2016年11月7日 下午5:51:33
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryPhotographerHouse")
	public @ResponseBody PageResult  queryPhotographerHouse(HttpServletRequest request,HouseRequestDto houseRequest){

		PageResult pageResult = new PageResult();
		try {

			// 房东姓名或房东手机不为空,调用用户库查询房东uid
			if(!Check.NuNStr(houseRequest.getLandlordName()) || !Check.NuNStr(houseRequest.getLandlordMobile())){
				CustomerBaseMsgDto paramDto = new CustomerBaseMsgDto();
				paramDto.setRealName(houseRequest.getLandlordName());
				paramDto.setCustomerMobile(houseRequest.getLandlordMobile());
				paramDto.setIsLandlord(HouseConstant.IS_TRUE);

				String customerJsonArray = customerInfoService.selectByCondition(JsonEntityTransform.Object2Json(paramDto));
				DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJsonArray);
				// 判断调用状态
				if(customerDto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(paramDto));
					return new PageResult();
				}
				List<CustomerBaseMsgEntity> customerList = customerDto.parseData("listCustomerBaseMsg",
						new TypeReference<List<CustomerBaseMsgEntity>>() {});
				// 如果查询结果为空,直接返回数据
				if(Check.NuNCollection(customerList)){
					LogUtil.info(LOGGER, "返回客户信息为空,参数:{}", JsonEntityTransform.Object2Json(paramDto));
					return new PageResult();
				}
				List<String> landlordUidList = new ArrayList<String>();
				for (CustomerBaseMsgEntity customerBaseMsg : customerList) {
					landlordUidList.add(customerBaseMsg.getUid());
				}
				houseRequest.setLandlordUidList(landlordUidList);
			}
			String resultJson = troyPhotogBookService.findNeedPhotographerHouse(JsonEntityTransform.Object2Json(houseRequest));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if(resultDto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(houseRequest));
				return new PageResult();
			}

			List<NeedPhotogHouseVo> listPhotogVo = SOAResParseUtil.getListValueFromDataByKey(resultJson, "listPhotogVo", NeedPhotogHouseVo.class);
			for (NeedPhotogHouseVo needPhotogHouseVo : listPhotogVo) {
				CustomerBaseMsgEntity customer = null;
				String customerJson = customerInfoService.getCustomerInfoByUid(needPhotogHouseVo.getLandlordUid());
				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(customerJson);
				if (dto.getCode() == DataTransferObject.ERROR) {
					LogUtil.info(LOGGER, "调用接口失败,landlordUid={}", needPhotogHouseVo.getLandlordUid());
				} else {
					customer = SOAResParseUtil.getValueFromDataByKey(customerJson, "customerBase", CustomerBaseMsgEntity.class);
				}

				if(!Check.NuNObj(customer)){
					needPhotogHouseVo.setLandlordName(customer.getRealName());
					needPhotogHouseVo.setLandlordMobile(customer.getCustomerMobile());
				}
			}

			pageResult.setRows(listPhotogVo);
			pageResult.setTotal(Long.valueOf(resultDto.getData().get("count").toString()));
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}

		return pageResult;
	}

	/**
	 * 
	 * 申请预约(业务变更，此入口关闭)
	 *
	 * @author yd
	 * @created 2016年11月8日 下午3:38:40
	 *
	 * @param request
	 * @return
	 *//*
	@RequestMapping("/bookHousePhotographer")
	public @ResponseBody DataTransferObject bookHousePhotographer(HttpServletRequest request,BookHousePhotogDto bookHousePhotogDto){

		DataTransferObject dto = null;
		if(Check.NuNStrStrict(bookHousePhotogDto.getHouseFid())
				||Check.NuNStrStrict(bookHousePhotogDto.getLandlordUid())){

			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源异常,请联系技术处理");
			return dto;
		}

		CurrentuserVo custVo = (CurrentuserVo) UserUtil.getCurrentUser();

		dto = JsonEntityTransform.json2DataTransferObject(employeeService.findEmployeByEmpFid(custVo.getEmployeeFid()));

		if(dto.getCode() == DataTransferObject.ERROR){
			return dto;
		}
		*//** 业务发生改变，房东预约入口*//*
		*//*EmployeeEntity emp = dto.parseData("employee", new TypeReference<EmployeeEntity>() {
		});
		if(!Check.NuNObj(emp)){
			bookHousePhotogDto.setBookerUid(custVo.getEmployeeFid());
			bookHousePhotogDto.setBookerName(emp.getEmpName());
			bookHousePhotogDto.setBookerMobile(emp.getEmpMobile());
		}*//*

		dto = JsonEntityTransform.json2DataTransferObject(customerInfoService.getCustomerInfoByUid(bookHousePhotogDto.getLandlordUid()));

		if(dto.getCode() == DataTransferObject.ERROR){
			return dto;
		}

		CustomerBaseMsgEntity customerBase = dto.parseData("customerBase", new TypeReference<CustomerBaseMsgEntity>() {
		});

		if(!Check.NuNObj(customerBase)){


			bookHousePhotogDto.setCustomerUid(customerBase.getUid());
			bookHousePhotogDto.setCustomerName(customerBase.getRealName());
			bookHousePhotogDto.setCustomerMobile(customerBase.getCustomerMobile());

			bookHousePhotogDto.setBookerUid(customerBase.getUid());
			bookHousePhotogDto.setBookerName(customerBase.getRealName());
			bookHousePhotogDto.setBookerMobile(customerBase.getCustomerMobile());
		}

		try {
			if(!Check.NuNStrStrict(bookHousePhotogDto.getBookStartTimeStr())){
				bookHousePhotogDto.setBookStartTime(DateUtil.parseDate(bookHousePhotogDto.getBookStartTimeStr(), "yyyy-MM-dd HH:mm:ss"));
			}
			
			if(!Check.NuNStrStrict(bookHousePhotogDto.getBookEndTimeStr())){
				bookHousePhotogDto.setBookEndTime(DateUtil.parseDate(bookHousePhotogDto.getBookEndTimeStr(), "yyyy-MM-dd HH:mm:ss"));
			}
		} catch (ParseException e) {
			LogUtil.info(LOGGER, "预约摄影师时间格式异常e={}", e);
		}

		dto = JsonEntityTransform.json2DataTransferObject(this.troyPhotogBookService.bookHousePhotographer(JsonEntityTransform.Object2Json(bookHousePhotogDto)));
		return dto;
	}*/
	
	/**
	 * 
	 * TODO
	 *
	 * @author zl
	 * @created 2016年11月9日 上午11:19:04
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/photographerOrderList")
	public String photographerOrderList(HttpServletRequest request){
		// 开通城市
		String resultJson =  confCityService.getOpenCity();
		List<Map> cityList = null;
		try {
			cityList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", Map.class);
		} catch (SOAParseException e) {
			LogUtil.error(LOGGER, "confCityService.getOpenCity error:{}", resultJson);
		}
		request.setAttribute("cityList", cityList);
		request.setAttribute("bookOrderStatusMap", BookOrderStatuEnum.getEnummap());
		// 摄影师来源
		request.setAttribute("photogSourceJson", JsonEntityTransform.Object2Json(PhotographerSourceEnum.getEnumMap()));
		return "/photog/photographerOrderList";
	}
	
	/**
	 * 
	 * 查询预约摄影师列表
	 *
	 * @author zl
	 * @created 2016年11月9日 上午11:19:12
	 *
	 * @return
	 */
	@RequestMapping("/findPhotographerOrderList")
	@ResponseBody
	public PageResult findPhotographerOrderList(BookHousePhotogDto bookOrderDto){
		try {
			 
			String resultJson = troyPhotogBookService.findPhotographerBookOrder(JSON.toJSONString(bookOrderDto));
			PageResult pageResult = new PageResult();
			pageResult.setRows(SOAResParseUtil.getListValueFromDataByKey(resultJson, "result", PhotographerBookOrderVo.class));
			pageResult.setTotal(SOAResParseUtil.getLongFromDataByKey(resultJson, "count"));
			return pageResult;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "查询摄影师预约订单异常，e={}", e);
			return new PageResult();
		}
		
	}

	/**
	 * 查找分租房间列表
	 * @author jixd
	 * @created 2017年03月28日 17:32:17
	 * @param
	 * @return
	 */
	@RequestMapping("/listRooms")
	@ResponseBody
	public String listRooms(String houseFid){
		return houseIssueService.searchRoomListByHouseBaseFid(houseFid);
	}

	/**
	 * 查询摄影单详情
	 * @author lunan
	 * @created 2017年2月27日
	 *
	 * @param
	 * @return
	 */
	@RequestMapping("/findPhotoOrder")
	@ResponseBody
	public DataTransferObject findPhotoOrder(PhotoOrderDto photoDto){
		DataTransferObject dto = new DataTransferObject();
		try{
			String resultJson = troyPhotogBookService.findPhotogOrderDetail(JsonEntityTransform.Object2Json(photoDto));
			if(!Check.NuNStrStrict(resultJson)){
				PhotographerBookOrderEntity bookOrder = SOAResParseUtil.getValueFromDataByKey(resultJson, "bookOrder", PhotographerBookOrderEntity.class);
				dto.putValue("bookOrder",bookOrder);
				dto.putValue("createTime", DateUtil.dateFormat(bookOrder.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
				dto.putValue("startTime", DateUtil.dateFormat(bookOrder.getBookStartTime(),"yyyy-MM-dd HH:mm:ss"));
				dto.putValue("homeTime", DateUtil.dateFormat(bookOrder.getDoorHomeTime(),"yyyy-MM-dd HH:mm:ss"));
				dto.putValue("receiveTime", DateUtil.dateFormat(bookOrder.getReceivePictureTime(),"yyyy-MM-dd HH:mm:ss"));

			}else{
				dto.setErrCode(DataTransferObject.ERROR);
			}
		}catch (Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
		}
		return dto;
	}

	/**
	 * 根据houseFid判断整租分租，分租返回房间集合
	 * @author lunan
	 * @created 2017年3月2日
	 *
	 * @param
	 * @return
	 */
	@RequestMapping("/findHouseOrRoomDetail")
	@ResponseBody
	public DataTransferObject findHouseOrRoomDetail(String houseFid){
		DataTransferObject dto = new DataTransferObject();
		try{
			String houseBase = houseIssueService.searchHouseBaseMsgByFid(houseFid);
			dto = JsonEntityTransform.json2DataTransferObject(houseBase);
			// 判断调用状态
			if(dto.getCode() == DataTransferObject.ERROR){
				dto.setErrCode(DataTransferObject.ERROR);
				return dto;
			}
			HouseBaseMsgEntity houseMsg = SOAResParseUtil.getValueFromDataByKey(houseBase, "obj", HouseBaseMsgEntity.class);
			if(!Check.NuNObj(houseMsg) && houseMsg.getRentWay()== RentWayEnum.ROOM.getCode()){
				//是分租
				dto.putValue("ok", YesOrNoEnum.YES.getCode());
				//获取房间列表
				String roomsJson =  houseIssueService.searchRoomListByHouseBaseFid(houseFid);
				DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(roomsJson);
				// 判断调用状态
				if(resultDto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "错误的分租房源fid:{}", houseFid);
					dto.setErrCode(DataTransferObject.ERROR);
					return dto;
				}
				List<HouseRoomMsgEntity> roomList =  SOAResParseUtil.getListValueFromDataByKey(roomsJson,"list",HouseRoomMsgEntity.class);
				dto.putValue("rooms", roomList);
			}else if(!Check.NuNObj(houseMsg) && houseMsg.getRentWay() == RentWayEnum.HOUSE.getCode()){
				//不是分租
				dto.putValue("ok", YesOrNoEnum.NO.getCode());
			}
		}catch (Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
		}
		return dto;
	}

	/**
	 * 返回日志
	 * @author lunan
	 * @created 2017年3月2日
	 *
	 * @param
	 * @return
	 */
	@RequestMapping("/findLogsByBookOrderSn")
	@ResponseBody
	public DataTransferObject findLogs(PhotoOrderDto photoDto){
		DataTransferObject dto = new DataTransferObject();
		try{
			String logsJson = troyPhotogBookService.findLogs(JsonEntityTransform.Object2Json(photoDto));
			List<PhotographerBookLogEntity> logs = null;
			List<PhotogLogDto> formatLogs = new ArrayList<PhotogLogDto>();
			if(!Check.NuNStr(logsJson)){
				logs = SOAResParseUtil.getListValueFromDataByKey(logsJson, "logs", PhotographerBookLogEntity.class);
				for(PhotographerBookLogEntity log : logs){
					PhotogLogDto logDto = new PhotogLogDto();
					dto = JsonEntityTransform.json2DataTransferObject(employeeService.findEmployeByEmpFid(log.getCreaterFid()));
					if(dto.getCode() != DataTransferObject.ERROR){
						EmployeeEntity emp = dto.parseData("employee", new TypeReference<EmployeeEntity>() {});
						if(!Check.NuNObj(emp)){
							logDto.setCreateName(emp.getEmpName());
						}
						logDto.setCreateDateStr(DateUtil.dateFormat(log.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
						logDto.setFromStatu(log.getFromStatu());
						logDto.setToStatu(log.getToStatu());
						formatLogs.add(logDto);
					}
				}
			}
			dto.putValue("logs",formatLogs);
		}catch (Exception e){
			LogUtil.error(LOGGER, "showPhotog error:{}", e);
			return dto;
		}
		return dto;
	}


	/**
	 * 收图时间
	 * @author lunan
	 * @created 2017年2月24日
	 *
	 * @param
	 * @return
 	*/
	@RequestMapping("/receOrCancelPhoto")
	@ResponseBody
	public DataTransferObject receOrCancelPhoto(PhotoOrderDto photoDto){
		DataTransferObject dto = new DataTransferObject();
		try{
			CurrentuserVo custVo = UserUtil.getFullCurrentUser();
			if (Check.NuNObj(custVo)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("请重新登录");
				return dto;
			}
			/** 更新预约单信息*/
			PhotographerBookOrderEntity  bookOrder = new PhotographerBookOrderEntity();
			/*bookOrder.setPhotographerMobile(photoDto.getPhotographerMobile());
			bookOrder.setPhotographerName(photoDto.getPhotographerName());
			bookOrder.setPhotographerUid(photoDto.getPhotographerUid());*/
			bookOrder.setBookOrderSn(photoDto.getBookOrderSn());
			/** 其他存入参数*/
			PhotogOrderUpdateDto photogOrderUpdateDto = new PhotogOrderUpdateDto();
			photogOrderUpdateDto.setCreaterFid(custVo.getEmployeeFid());
			photogOrderUpdateDto.setCreaterType(CreaterTypeEnum.ZIROOM_PHOTODEPARTMENT.getCode());
			photogOrderUpdateDto.setPhotographerBookOrder(bookOrder);
			if(Integer.valueOf(photoDto.getUpdateType()) == UpdateTypeEnum.UPDATE_RECE_PHOTO.getCode()){
				//存入收图时间
				bookOrder.setReceivePictureTime(DateUtil.parseDate(photoDto.getReceivePhotoTimeStr(), "yyyy-MM-dd HH:mm:ss"));
				photogOrderUpdateDto.setUpdateType(UpdateTypeEnum.UPDATE_RECE_PHOTO.getCode());

			}else if(Integer.valueOf(photoDto.getUpdateType()) == BookOrderStatuEnum.ORDER_BOOK_CANCEL.getCode() || Integer.valueOf(photoDto.getUpdateType()) == BookOrderStatuEnum.DOOR_NOT_PHOTO.getCode() || Integer.valueOf(photoDto.getUpdateType()) == BookOrderStatuEnum.NOT_DOORANDPHOTO.getCode()){
				//作废图片操作
				photogOrderUpdateDto.setUpdateType(Integer.valueOf(photoDto.getUpdateType()));
				photogOrderUpdateDto.setRemark(photoDto.getBookOrderRemark());
			}else{
				dto.setErrCode(DataTransferObject.ERROR);
				return dto;
			}
			dto = JsonEntityTransform.json2DataTransferObject(troyPhotogBookService.updatePhotographerBookOrderBySn(JSON.toJSONString(photogOrderUpdateDto)));
		}catch (Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务错误");
			return dto;
		}

		return dto;
	}
	/**
	 * 指定或者重新指定摄影师
	 * @author zl
	 * @created 2016年11月9日 上午11:19:24
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/assignPhotographer")
	@ResponseBody
	public  DataTransferObject assignPhotographer(HttpServletRequest request){
		DataTransferObject dtObject = new DataTransferObject();
		
		try {
			CurrentuserVo custVo = UserUtil.getFullCurrentUser();
			if (Check.NuNObj(custVo)) {
				dtObject.setErrCode(DataTransferObject.ERROR);
				dtObject.setMsg("请重新登录");
				return dtObject;			
			}
			
			String photographerUid = request.getParameter("photographerUid");
			String realName = request.getParameter("photographerName");
			String mobile = request.getParameter("photographerMobile");
			String bookOrderSn = request.getParameter("bookOrderSn");
			String updateType = request.getParameter("updateType");
			/** 收取实际上门时间 */
			PhotographerBookOrderEntity  photographerBookOrder = new PhotographerBookOrderEntity();
			String realTime = request.getParameter("realTime");
			if(!Check.NuNStrStrict(realTime)){
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date reslDate = dateFormat.parse(realTime);
				//存入实际上门时间
				photographerBookOrder.setDoorHomeTime(reslDate);
			}else{
				dtObject.setErrCode(DataTransferObject.ERROR);
				dtObject.setMsg("请填写上门时间");
				return dtObject;
			}
			//存入摄影师信息
			photographerBookOrder.setPhotographerMobile(mobile);
			photographerBookOrder.setPhotographerName(realName);
			photographerBookOrder.setPhotographerUid(photographerUid);
			photographerBookOrder.setBookOrderSn(bookOrderSn);
			if (Check.NuNStr(photographerUid) || Check.NuNStr(realName)|| Check.NuNStr(mobile)|| Check.NuNStr(bookOrderSn)) {
				dtObject.setErrCode(DataTransferObject.ERROR);
				dtObject.setMsg("参数错误，请刷新页面再试");
				return dtObject;	
			}
			//调用接口参数准备
			PhotogOrderUpdateDto photogOrderUpdateDto = new PhotogOrderUpdateDto();
			photogOrderUpdateDto.setCreaterFid(custVo.getEmployeeFid());
			photogOrderUpdateDto.setCreaterType(CreaterTypeEnum.ZIROOM_PHOTODEPARTMENT.getCode());
			photogOrderUpdateDto.setPhotographerBookOrder(photographerBookOrder);
			photogOrderUpdateDto.setUpdateType(UpdateTypeEnum.UPDATE_APPOINTED_PHOTOG.getCode());
			
			try {
				if (!Check.NuNStr(updateType)) {
					if (UpdateTypeEnum.UPDATE_APPOINTED_PHOTOG_MODIFY.getCode()==Integer.valueOf(updateType)) {
						photogOrderUpdateDto.setUpdateType(UpdateTypeEnum.UPDATE_APPOINTED_PHOTOG_MODIFY.getCode());
					} 
				}
			} catch (Exception e) {
				dtObject.setErrCode(DataTransferObject.ERROR);
				dtObject.setMsg("参数错误");
				return dtObject;
			}
			
			dtObject = JsonEntityTransform.json2DataTransferObject(troyPhotogBookService.updatePhotographerBookOrderBySn(JSON.toJSONString(photogOrderUpdateDto)));
			
		} catch (Exception e) {
			dtObject.setErrCode(DataTransferObject.ERROR);
			dtObject.setMsg("服务错误");
			return dtObject;	
		}
		
		return dtObject;
	}
	
	/**
	 * 
	 * 完成操作
	 *
	 * @author zl
	 * @created 2016年11月9日 上午11:19:37
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/assignPhotographerComplate")
	@ResponseBody
	public  DataTransferObject assignPhotographerComplate(HttpServletRequest request){
		DataTransferObject dtObject = new DataTransferObject();
		
		try {
			
			CurrentuserVo custVo = UserUtil.getFullCurrentUser();
			if (Check.NuNObj(custVo)) {
				dtObject.setErrCode(DataTransferObject.ERROR);
				dtObject.setMsg("请重新登录");
				return dtObject;			
			}
			
			String bookOrderSn = request.getParameter("bookOrderSn");
			
			if (Check.NuNStr(bookOrderSn)) {
				dtObject.setErrCode(DataTransferObject.ERROR);
				dtObject.setMsg("参数错误，请刷新页面再试");
				return dtObject;	
			}
			
			PhotographerBookOrderEntity  photographerBookOrder = new PhotographerBookOrderEntity();
			photographerBookOrder.setBookOrderSn(bookOrderSn);
			
			PhotogOrderUpdateDto photogOrderUpdateDto = new PhotogOrderUpdateDto();
			
			photogOrderUpdateDto.setCreaterFid(custVo.getEmployeeFid());
			photogOrderUpdateDto.setCreaterType(CreaterTypeEnum.ZIROOM_PHOTODEPARTMENT.getCode());
			photogOrderUpdateDto.setPhotographerBookOrder(photographerBookOrder);
			photogOrderUpdateDto.setUpdateType(UpdateTypeEnum.UPDATE_PHOTO_FINISHED.getCode());

			dtObject = JsonEntityTransform.json2DataTransferObject(troyPhotogBookService.updatePhotographerBookOrderBySn(JSON.toJSONString(photogOrderUpdateDto)));

			//点击完成后给房东发送短信
			if (dtObject.getCode() == DataTransferObject.SUCCESS){
				PhotoOrderDto photoOrderDto = new PhotoOrderDto();
				photoOrderDto.setBookOrderSn(bookOrderSn);
				DataTransferObject photoDto = JsonEntityTransform.json2DataTransferObject(troyPhotogBookService.findPhotogOrderDetail(JsonEntityTransform.Object2Json(photoOrderDto)));
				if (photoDto.getCode() == DataTransferObject.SUCCESS){
					PhotographerBookOrderEntity bookOrder = photoDto.parseData("bookOrder", new TypeReference<PhotographerBookOrderEntity>() {});
					if (!Check.NuNObj(bookOrder)){
						SmsRequest smsRequest = new SmsRequest();
						smsRequest.setMobile(bookOrder.getCustomerMobile());
						Map<String, String> paramsMap=new HashMap<String, String>();
						smsRequest.setParamsMap(paramsMap);
						String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.PHOTOGRAPHER_FINISH_NOTIFY_LANDLORD.getCode());
						smsRequest.setSmsCode(String.valueOf(msgCode));
						smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
					}
				}
			}


		} catch (Exception e) {
			dtObject.setErrCode(DataTransferObject.ERROR);
			dtObject.setMsg("服务错误");
			return dtObject;	
		}
		return dtObject;
	}

	
	
}
