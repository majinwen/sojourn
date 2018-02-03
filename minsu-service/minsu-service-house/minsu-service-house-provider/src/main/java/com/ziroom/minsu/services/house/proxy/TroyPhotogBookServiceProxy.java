package com.ziroom.minsu.services.house.proxy;

import java.awt.CheckboxGroup;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ziroom.minsu.entity.house.HouseBizMsgEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBookLogEntity;
import com.ziroom.minsu.services.house.dto.HousePicDto;
import com.ziroom.minsu.services.house.photog.dto.PhotoOrderDto;
import com.ziroom.minsu.services.house.photog.vo.PhotographerBookOrderVo;
import com.ziroom.minsu.services.house.service.HouseBizServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.photographer.PhotographerBookOrderEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.house.api.inner.TroyPhotogBookService;
import com.ziroom.minsu.services.house.dto.BookHousePhotogDto;
import com.ziroom.minsu.services.house.dto.HouseRequestDto;
import com.ziroom.minsu.services.house.entity.NeedPhotogHouseVo;
import com.ziroom.minsu.services.house.photog.dto.PhotogOrderUpdateDto;
import com.ziroom.minsu.services.house.service.PhotographerBookOrderServiceImpl;
import com.ziroom.minsu.services.house.service.TroyHouseMgtServiceImpl;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.minsu.valenum.photographer.BookOrderStatuEnum;
import com.ziroom.minsu.valenum.photographer.UpdateTypeEnum;

/**
 * 
 * <p>预约摄影接口实现</p>
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
@Component("photographer.troyPhotogBookServiceProxy")
public class TroyPhotogBookServiceProxy implements TroyPhotogBookService {


	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(TroyPhotogBookServiceProxy.class);

	@Resource(name = "house.troyHouseMgtServiceImpl")
	private TroyHouseMgtServiceImpl troyHouseMgtServiceImpl;

	@Resource(name = "photographer.photographerBookOrderServiceImpl")
	private PhotographerBookOrderServiceImpl photographerBookOrderServiceImpl;

	@Resource(name = "house.houseBizServiceImpl")
	private HouseBizServiceImpl houseBizServiceImpl;

	@Resource(name = "basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;

	/**
	 * 
	 *  查询需要拍照的房源信息
	 *  
	 *  说明： 
	 *   1.摄影师针对房源
	 *   2.分租，只要有有一间房间被预约摄影，整个房源就算已预约摄影（特殊情况 线下处理）
	 *   3.分租： 多个房间编号以逗号隔开  房间名称 以分号隔开
	 *   
	 *   问题：（此处查询用的 是联合查询  数据量大了之后，会有性能影响，但平台房源达不到大数据量的级别）
	 *
	 * @author yd
	 * @created 2016年11月8日 上午9:07:57
	 *
	 * @param dtoVoJson
	 * @return
	 */
	@Override
	public String findNeedPhotographerHouse(String dtoVoJson) {

		DataTransferObject dto = new DataTransferObject();

		HouseRequestDto houseRequest = JsonEntityTransform.json2Object(dtoVoJson, HouseRequestDto.class);

		if(Check.NuNObj(houseRequest)) houseRequest = new HouseRequestDto();

		PagingResult<NeedPhotogHouseVo> listPag = this.troyHouseMgtServiceImpl.findNeedPhotographerHouse(houseRequest);

		dto.putValue("listPhotogVo", listPag.getRows());
		dto.putValue("count", listPag.getTotal());
		return dto.toJsonString();
	}

	/**
	 * 
	 * 预约房源摄影师
	 * 
	 * 说明：
	 * 参数：BookHousePhotogDto
	 * 1. 此处房源信息 从web端传过来(不在dubbo层 查询，防止以后拆分出去)
	 * 2. 当前第一次预约，无摄影师（故摄影师不做校验）
	 * 3. 分租  不做房源名称的保存 保存的房源的编号
	 *
	 * @author yd
	 * @created 2016年11月8日 上午9:44:37
	 *
	 * @param dtoVoJson
	 * @return
	 */
	@Override
	public String bookHousePhotographer(String dtoVoJson) {

		DataTransferObject dto =  new DataTransferObject();
		BookHousePhotogDto bookHousePhotogDto = JsonEntityTransform.json2Object(dtoVoJson, BookHousePhotogDto.class);
		dto = checkBookHousePhotographer(dto, bookHousePhotogDto);

		if(dto.getCode() == DataTransferObject.SUCCESS){
			int i = this.photographerBookOrderServiceImpl.saveBookHousePhotographer(bookHousePhotogDto);
			if(i<=0){
				LogUtil.error(logger, "预约摄影师失败,参数bookHousePhotogDto={}", dtoVoJson);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("预约摄影师失败");
			}
		}
		return dto.toJsonString();
	}


	/**
	 * 
	 * 预约摄影参数校验
	 *
	 * @author yd
	 * @created 2016年11月8日 上午10:30:04
	 *
	 * @param dto
	 * @param bookHousePhotogDto
	 * @return
	 */
	private DataTransferObject checkBookHousePhotographer(DataTransferObject dto,BookHousePhotogDto bookHousePhotogDto){

		if(Check.NuNObj(dto))  dto = new DataTransferObject();

		if(Check.NuNObj(bookHousePhotogDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数不存在");
		}

		if(Check.NuNStrStrict(bookHousePhotogDto.getHouseFid())
				||Check.NuNStrStrict(bookHousePhotogDto.getHouseSn())
				||Check.NuNStrStrict(bookHousePhotogDto.getShotAddr())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源信息错误");
		}

		if(Check.NuNStrStrict(bookHousePhotogDto.getBookerUid())
				||Check.NuNStrStrict(bookHousePhotogDto.getBookerMobile())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("预约人手机号或uid不存在");
		}

		if(Check.NuNStrStrict(bookHousePhotogDto.getCustomerUid())
				||Check.NuNStrStrict(bookHousePhotogDto.getCustomerMobile())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房东手机号或uid不存在");
		}

		if(Check.NuNObj(bookHousePhotogDto.getBookStartTime())){
			LogUtil.error(logger, "预约摄影下单,下单时间错误：bookStartTime={},bookEndTime={}", bookHousePhotogDto.getBookStartTime(),bookHousePhotogDto.getBookEndTime());
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("预约人时间错误");
		}

		//校验当前房源是否已经被预约

		PhotographerBookOrderEntity bookOrder = this.photographerBookOrderServiceImpl.findBookOrderByHouseFid(bookHousePhotogDto.getHouseFid());

		if(!Check.NuNObj(bookOrder)){
			if(bookOrder.getBookOrderStatu() != BookOrderStatuEnum.DOOR_NOT_PHOTO.getCode() && bookOrder.getBookOrderStatu() != BookOrderStatuEnum.NOT_DOORANDPHOTO.getCode()){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("当前房源已经申请预约");
			}
		}
		return dto;
	}

	/**
	 * 
	 *  指定摄影师  或  摄影师摄影完成
	 *  
	 *  说明：
	 * 第一种：
	 *   1. 校验摄影师信息
	 *   2. 校验订单是否存在
	 *   3. 校验订单状态
	 *   4. 校验当前订单的预定时间  （一旦 预约开始时间超过当前时间 给出警告）
	 * 第二中：
	 *   1. 校验订单是否存在
	 *   2. 校验订单状态
	 *   3. 校验时间  （一旦 开始预约时间  在当前时间 之后  不让点击完成）
	 * @author yd
	 * @created 2016年11月8日 下午9:40:37
	 *
	 * @return
	 */
	@Override
	public String updatePhotographerBookOrderBySn(String dtoVoJson) {

		DataTransferObject dto = new DataTransferObject();

		PhotogOrderUpdateDto photogOrderUpdateDto = JsonEntityTransform.json2Object(dtoVoJson, PhotogOrderUpdateDto.class);

		if(Check.NuNObj(photogOrderUpdateDto)
				||Check.NuNObj(photogOrderUpdateDto.getPhotographerBookOrder())
				||Check.NuNStrStrict(photogOrderUpdateDto.getPhotographerBookOrder().getBookOrderSn())){

			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请求参数非法");

			return dto.toJsonString();
		}

		PhotographerBookOrderEntity updateObj = photogOrderUpdateDto.getPhotographerBookOrder();

		PhotographerBookOrderEntity dbCurObj = this.photographerBookOrderServiceImpl.queryPhotographerBookOrderBySn(updateObj.getBookOrderSn());


		if(Check.NuNObj(dbCurObj)){

			LogUtil.error(logger, "预约订单修改：根据预约订单号bookOrderSn={},查询预约订单已经不存在", updateObj.getBookOrderSn());
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("预约订单已经不存在");
			return dto.toJsonString();
		}
		/** 改变之前的订单状态*/
		photogOrderUpdateDto.setOldStatu(dbCurObj.getBookOrderStatu());
		/** 改变之前的摄影师uid*/
		photogOrderUpdateDto.setPreGrapherUid(dbCurObj.getPhotographerUid());

		if(!Check.NuNObjs(photogOrderUpdateDto.getUpdateType())){

			Long curTimeLong = System.currentTimeMillis();
			Long bookStartTimeLong = dbCurObj.getBookStartTime().getTime();
			//Long bookEndTimeLong = dbCurObj.getBookEndTime().getTime();
			/** 指定摄影师*/
			if(photogOrderUpdateDto.getUpdateType().intValue() == UpdateTypeEnum.UPDATE_APPOINTED_PHOTOG.getCode()){
				//摄影师校验
				if(Check.NuNStrStrict(updateObj.getPhotographerUid())){
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("请指定摄影师");
					return dto.toJsonString();
				}
				//状态校验
				if(Check.NuNObj(dbCurObj.getBookOrderStatu())){
					if(dbCurObj.getBookOrderStatu().intValue() == BookOrderStatuEnum.ORDER_BOOK_FINISHED.getCode() || dbCurObj.getBookOrderStatu().intValue() == BookOrderStatuEnum.ORDER_BOOK_CANCEL.getCode()){
						LogUtil.error(logger, "当前预约摄影订单状态不可修改curStatu={}",  dbCurObj.getBookOrderStatu());
						dto.setErrCode(DataTransferObject.ERROR);
						dto.setMsg("待修改预约订单状态错误");
						return dto.toJsonString();
					}
				}
				//预约时间校验  1. 当前时间 在预约开始时间之前 都是正常的  2. 当前时间在预约开始时间 和 预约结束时间之前，非正常  但让预约成功  3. 当前时间在预约结束时间之后，给出错误提示，也让预约成功
				if(curTimeLong>=bookStartTimeLong){
					LogUtil.error(logger, "警告：指定摄影师时间异常,当前时间已经超过预约开始时间,当前时间curTime={},预约订单号bookOrderSn={},业务正常进行", new Date(curTimeLong),dbCurObj.getBookOrderSn());
				}
				updateObj.setBookOrderStatu(BookOrderStatuEnum.ORDER_BOOK_SUCCESS.getCode());
				updateObj.setAppointPhotogDate(new Date(curTimeLong));
			}
			/** 重新指定摄影师*/
			if(photogOrderUpdateDto.getUpdateType().intValue() == UpdateTypeEnum.UPDATE_APPOINTED_PHOTOG_MODIFY.getCode()){
				//状态校验
				if(Check.NuNObj(dbCurObj.getBookOrderStatu())){
					if(dbCurObj.getBookOrderStatu().intValue() == BookOrderStatuEnum.ORDER_BOOK_FINISHED.getCode() || dbCurObj.getBookOrderStatu().intValue() == BookOrderStatuEnum.ORDER_BOOK_CANCEL.getCode()){
						LogUtil.error(logger, "当前预约摄影订单状态不可修改curStatu={}",  dbCurObj.getBookOrderStatu());
						dto.setErrCode(DataTransferObject.ERROR);
						dto.setMsg("待修改预约订单状态错误");
						return dto.toJsonString();
					}
				}
				//摄影师校验
				if(Check.NuNStrStrict(updateObj.getPhotographerUid())){
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("请指定摄影师");
					return dto.toJsonString();
				}
				
				/*if(curTimeLong>=bookStartTimeLong){
					LogUtil.error(logger, "警告：指定摄影师时间异常,当前时间已经超过预约开始时间,当前时间curTime={},预约订单号bookOrderSn={},业务正常进行", new Date(curTimeLong),dbCurObj.getBookOrderSn());
				}*/
				updateObj.setBookOrderStatu(BookOrderStatuEnum.ORDER_BOOK_SUCCESS.getCode());
				updateObj.setAppointPhotogDate(new Date(curTimeLong));
			}
			/** 收图更新时间*/
			if(photogOrderUpdateDto.getUpdateType().intValue() == UpdateTypeEnum.UPDATE_RECE_PHOTO.getCode()){
				//如果收图时间小于预约开始时间，实际上是错误的时间，也允许写入，日志提示错误
				if(updateObj.getReceivePictureTime().getTime() < bookStartTimeLong){
					LogUtil.error(logger, "警告：指定摄影师时间异常,收图时间receiveTime={},预约订单号bookOrderSn={},业务正常进行", new Date(updateObj.getReceivePictureTime().getTime()),dbCurObj.getBookOrderSn());
				}
				updateObj.setBookOrderStatu(BookOrderStatuEnum.ORDER_RECEIVE_PHOTO.getCode());
			}
			/** 作废操作*/
			if(photogOrderUpdateDto.getUpdateType().intValue() == BookOrderStatuEnum.ORDER_BOOK_CANCEL.getCode() || photogOrderUpdateDto.getUpdateType().intValue() == BookOrderStatuEnum.DOOR_NOT_PHOTO.getCode() || photogOrderUpdateDto.getUpdateType().intValue() == BookOrderStatuEnum.NOT_DOORANDPHOTO.getCode()){
				if(!Check.NuNStrStrict(photogOrderUpdateDto.getRemark())){
					if(!Check.NuNStrStrict(dbCurObj.getBookOrderRemark())){
						updateObj.setBookOrderRemark(dbCurObj.getBookOrderRemark()+","+photogOrderUpdateDto.getRemark());
					}else{
						updateObj.setBookOrderRemark(photogOrderUpdateDto.getRemark());
					}
				}
				updateObj.setBookOrderStatu(photogOrderUpdateDto.getUpdateType().intValue());
				updateObj.setBookEndTime(new Date(curTimeLong));
			}
			/** 完成操作*/
			if(photogOrderUpdateDto.getUpdateType().intValue() == UpdateTypeEnum.UPDATE_PHOTO_FINISHED.getCode()){
				//状态校验
				if(Check.NuNObj(dbCurObj.getBookOrderStatu())){
					LogUtil.error(logger, "预约订单状态修改：不存在状态，无法修改为完成,当前状态不存在curStatu={}",  dbCurObj.getBookOrderStatu());
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("待修改预约订单状态错误");
					return dto.toJsonString();
				}
				//预约时间校验
				if(curTimeLong<bookStartTimeLong){
					LogUtil.error(logger, "警告：摄影师完成时间异常,当前时间小于预约开始时间,当前时间curTime={},预约订单号bookOrderSn={},业务正常进行", new Date(curTimeLong),dbCurObj.getBookOrderSn());
				}
				updateObj = new PhotographerBookOrderEntity();
				updateObj.setBookOrderSn(dbCurObj.getBookOrderSn());
				updateObj.setBookOrderStatu(BookOrderStatuEnum.ORDER_BOOK_FINISHED.getCode());
				updateObj.setBookEndTime(new Date(curTimeLong));
			}
		}
		int i = this.photographerBookOrderServiceImpl.updatePhotographerBookOrderBySn(updateObj, photogOrderUpdateDto);
		dto.putValue("result", i);
		if(i>0){
			dto.setMsg("修改成功");
			//TODO 发送短信，需要新增文案，
			try {
				SimpleDateFormat fmtDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//发送短信  派单，重新派单，作废 都发送短信
				if(photogOrderUpdateDto.getUpdateType().intValue() == UpdateTypeEnum.UPDATE_APPOINTED_PHOTOG.getCode()){
					//给房东发送短信
					String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.PHOTOGRAPHER_BOOK_ASSIGN.getCode());
					SmsRequest smsRequest  = new SmsRequest();
					Map<String, String> paramsMap = new HashMap<String, String>();
					paramsMap.put("{1}", updateObj.getPhotographerName());
					paramsMap.put("{2}", fmtDateFormat.format(updateObj.getDoorHomeTime()));
					smsRequest.setParamsMap(paramsMap);
					smsRequest.setMobile(dbCurObj.getBookerMobile());
					smsRequest.setSmsCode(msgCode);
					smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
					//给摄影师发送短信
					String msgCode2 = ValueUtil.getStrValue(MessageTemplateCodeEnum.PHOTOGRAPHER_BOOK_GRAPHER.getCode());
					SmsRequest smsRequest2  = new SmsRequest();
					Map<String, String> paramsMap2 = new HashMap<String, String>();
					paramsMap2.put("{1}", updateObj.getPhotographerName());
					paramsMap2.put("{2}", fmtDateFormat.format(updateObj.getDoorHomeTime()));
					paramsMap2.put("{3}", dbCurObj.getShotAddr());
					paramsMap2.put("{4}", dbCurObj.getHouseSn());
					paramsMap2.put("{5}", dbCurObj.getBookerMobile());
					paramsMap2.put("{6}", dbCurObj.getBookerName());
					smsRequest2.setParamsMap(paramsMap2);
					smsRequest2.setMobile(updateObj.getPhotographerMobile());
					smsRequest2.setSmsCode(msgCode2);
					smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest2));
					
				}else if(photogOrderUpdateDto.getUpdateType().intValue() == UpdateTypeEnum.UPDATE_APPOINTED_PHOTOG_MODIFY.getCode()){
					//重新派单
					//给房东发送短信
					String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.PHOTOGRAPHER_BOOK_ASSIGN.getCode());
					SmsRequest smsRequest  = new SmsRequest();
					Map<String, String> paramsMap = new HashMap<String, String>();
					paramsMap.put("{1}", updateObj.getPhotographerName());
					paramsMap.put("{2}", fmtDateFormat.format(updateObj.getDoorHomeTime()));
					smsRequest.setParamsMap(paramsMap);
					smsRequest.setMobile(dbCurObj.getBookerMobile());
					smsRequest.setSmsCode(msgCode);
					smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
					//给摄影师发送短信
					String msgCode2 = ValueUtil.getStrValue(MessageTemplateCodeEnum.PHOTOGRAPHER_BOOK_GRAPHER.getCode());
					SmsRequest smsRequest2  = new SmsRequest();
					Map<String, String> paramsMap2 = new HashMap<String, String>();
					paramsMap2.put("{1}", updateObj.getPhotographerName());
					paramsMap2.put("{2}", fmtDateFormat.format(updateObj.getDoorHomeTime()));
					paramsMap2.put("{3}", dbCurObj.getShotAddr());
					paramsMap2.put("{4}", dbCurObj.getHouseSn());
					paramsMap2.put("{5}", dbCurObj.getBookerMobile());
					paramsMap2.put("{6}", dbCurObj.getBookerName());
					smsRequest2.setParamsMap(paramsMap2);
					smsRequest2.setMobile(updateObj.getPhotographerMobile());
					smsRequest2.setSmsCode(msgCode2);
					smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest2));
				}else if(photogOrderUpdateDto.getUpdateType().intValue() == BookOrderStatuEnum.ORDER_BOOK_CANCEL.getCode()){

					//作废不可再预约
					//发送给房东
					String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.PHOTOGRAPHER_CANCEL_NOTBOOK.getCode());
					SmsRequest smsRequest  = new SmsRequest();
					Map<String, String> paramsMap = new HashMap<String, String>();
					paramsMap.put("{1}", dbCurObj.getShotAddr());
					if(!Check.NuNObj(dbCurObj.getDoorHomeTime())){
						paramsMap.put("{2}", fmtDateFormat.format(dbCurObj.getDoorHomeTime()));
					}else{
						paramsMap.put("{2}", fmtDateFormat.format(dbCurObj.getBookStartTime()));
					}
					smsRequest.setParamsMap(paramsMap);
					smsRequest.setMobile(dbCurObj.getBookerMobile());
					smsRequest.setSmsCode(msgCode);
					smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));

					if(dbCurObj.getBookOrderStatu() != BookOrderStatuEnum.ORDER_BOOKING.getCode()){
						//发送给摄影师  派单后作废才去通知摄影师
						String msgGrapher = ValueUtil.getStrValue(MessageTemplateCodeEnum.PHOTOGRAPHER_CANCEL_NOTBOOK.getCode());
						SmsRequest smsGrapher  = new SmsRequest();
						Map<String, String> paramGrapher = new HashMap<String, String>();
						paramGrapher.put("{1}", dbCurObj.getShotAddr());
						paramGrapher.put("{2}", fmtDateFormat.format(dbCurObj.getDoorHomeTime()));
						smsGrapher.setParamsMap(paramGrapher);
						smsGrapher.setMobile(dbCurObj.getPhotographerMobile());
						smsGrapher.setSmsCode(msgGrapher);
						smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsGrapher));
					}


				}else if(photogOrderUpdateDto.getUpdateType().intValue() == BookOrderStatuEnum.DOOR_NOT_PHOTO.getCode() || photogOrderUpdateDto.getUpdateType().intValue() == BookOrderStatuEnum.NOT_DOORANDPHOTO.getCode() ){
					//作废可再预约
					//发送给房东
					String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.PHOTOGRAPHER_CANCEL_REBOOK.getCode());
					SmsRequest smsRequest  = new SmsRequest();
					Map<String, String> paramsMap = new HashMap<String, String>();
					paramsMap.put("{1}", dbCurObj.getShotAddr());
					if(!Check.NuNObj(dbCurObj.getDoorHomeTime())){
						paramsMap.put("{2}", fmtDateFormat.format(dbCurObj.getDoorHomeTime()));
					}else{
						paramsMap.put("{2}", fmtDateFormat.format(dbCurObj.getBookStartTime()));
					}
					smsRequest.setParamsMap(paramsMap);
					smsRequest.setMobile(dbCurObj.getBookerMobile());
					smsRequest.setSmsCode(msgCode);
					smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
					if(dbCurObj.getBookOrderStatu() != BookOrderStatuEnum.ORDER_BOOKING.getCode()){
						//发送给摄影师 PHOTOGRAPHER_CANCEL_REBOOKG
						String msgGrapher = ValueUtil.getStrValue(MessageTemplateCodeEnum.PHOTOGRAPHER_CANCEL_REBOOKG.getCode());
						SmsRequest smsGrapher  = new SmsRequest();
						Map<String, String> paramGrapher = new HashMap<String, String>();
						paramGrapher.put("{1}", dbCurObj.getShotAddr());
						paramGrapher.put("{2}", fmtDateFormat.format(dbCurObj.getDoorHomeTime()));
						smsGrapher.setParamsMap(paramGrapher);
						smsGrapher.setMobile(dbCurObj.getPhotographerMobile());
						smsGrapher.setSmsCode(msgGrapher);
						smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsGrapher));
					}
				}
			 }catch (Exception e){
				 LogUtil.error(logger,"预约摄影师发送短信失败：e：{}",e);
			 }
		}

		return dto.toJsonString();
	}


	@Override
	public String findPhotographerBookOrder(String paramJson) {
		DataTransferObject dto =  new DataTransferObject();
		try {
			
			BookHousePhotogDto bookOrderDto = JsonEntityTransform.json2Object(paramJson, BookHousePhotogDto.class);
			
			PagingResult<PhotographerBookOrderVo> list = photographerBookOrderServiceImpl.findPhotographerBookOrder(bookOrderDto);
			dto.putValue("result", list.getRows()); 
			dto.putValue("count", list.getTotal());
		} catch (Exception e) {
			LogUtil.error(logger, "查询摄影师预约订单异常，e={}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统异常");
		}
		return dto.toJsonString();
	}

	/**
	 *
	 * 查询预约单详情
	 * @author lunan
	 * @param paramJson
	 * @return
	 */
	@Override
	public String findPhotogOrderDetail(String paramJson) {
		DataTransferObject dto =  new DataTransferObject();
		if(Check.NuNStrStrict(paramJson)){
			dto.setErrCode(DataTransferObject.ERROR);
			return dto.toJsonString();
		}
		PhotoOrderDto photoOrderDto = JsonEntityTransform.json2Object(paramJson, PhotoOrderDto.class);
		/** 如果房源 bookOrderSn 不为空，通过 bookOrderSn 查询bookPhotoOrder*/
		if(!Check.NuNStrStrict(photoOrderDto.getBookOrderSn())){
			PhotographerBookOrderEntity bookOrder = photographerBookOrderServiceImpl.queryPhotographerBookOrderBySn(photoOrderDto.getBookOrderSn());
			dto.putValue("bookOrder", bookOrder);
		}
		/** 如果房源fid不为空，通过houseFid查询bookPhotoOrder*/
		if(!Check.NuNStrStrict(photoOrderDto.getHouseFid())){
			PhotographerBookOrderEntity bookOrderForAPI = photographerBookOrderServiceImpl.findBookOrderByHouseFid(photoOrderDto.getHouseFid());
			dto.putValue("bookOrderForAPI", bookOrderForAPI);
		}
		return dto.toJsonString();
	}

	/**
	 *
	 * 查询品质审核未通过原因
	 * @author lunan
	 * @param param
	 * @return
	 */
	@Override
	public String findHouseBizReason(String param) {
		DataTransferObject dto = new DataTransferObject();
		if(!Check.NuNStrStrict(param)){
			HousePicDto houseBiz = JsonEntityTransform.json2Object(param, HousePicDto.class);
			HouseBizMsgEntity bizEntity = null;
			if(!Check.NuNStrStrict(houseBiz.getHouseRoomFid())){
				bizEntity = houseBizServiceImpl.getHouseBizMsgByRoomFid(houseBiz.getHouseRoomFid());
			}else{
				bizEntity = houseBizServiceImpl.getHouseBizMsgByHouseFid(houseBiz.getHouseBaseFid());
			}
			dto.putValue("biz",bizEntity);
		}else{
			dto.setErrCode(DataTransferObject.ERROR);
		}
		return dto.toJsonString();
	}

	/**
	 * 查询预约摄影单的日志记录
	 * @param paramJson
	 * @return
	 */
	@Override
	public String findLogs(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try{
			if(!Check.NuNStrStrict(paramJson)){
				PhotoOrderDto photoOrderDto = JsonEntityTransform.json2Object(paramJson, PhotoOrderDto.class);
				if(Check.NuNStrStrict(photoOrderDto.getBookOrderSn())){
					dto.setErrCode(DataTransferObject.ERROR);
				}else{
					List<PhotographerBookLogEntity> logs = photographerBookOrderServiceImpl.findLogs(photoOrderDto.getBookOrderSn());
					dto.putValue("logs",logs);
				}
			}else{
				dto.setErrCode(DataTransferObject.ERROR);
			}
		}catch (Exception e){
			LogUtil.error(logger, "查询摄影师预约订单日志异常，e={}", e);
			dto.setErrCode(DataTransferObject.ERROR);
		}
		return dto.toJsonString();
	}
	
	/**
	 * 
	 * 通过房源fid查询摄影师预约订单集合,取最新预约订单状态
	 *
	 * @author baiwei
	 * @created 2017年4月12日 下午9:09:16
	 * 
	 * @param houseFid
	 * @return
	 */
	@Override
	public String findBookOrderByHouseFid(String houseFid) {
		LogUtil.info(logger, "参数:{}", houseFid);
		DataTransferObject dto = new DataTransferObject();
		if(!Check.NuNObj(houseFid)){
			List<PhotographerBookOrderEntity> listPhOrderEntities = photographerBookOrderServiceImpl.queryPhotographerBookOrderByHouseFid(houseFid);
			if(!Check.NuNCollection(listPhOrderEntities)){
				PhotographerBookOrderEntity photographerBookOrderEntity = listPhOrderEntities.get(listPhOrderEntities.size()-1);
				dto.putValue("photographerBookOrderEntity", photographerBookOrderEntity);
			}
		}else{
			dto.putValue("photographerBookOrderEntity", null);
		}
		LogUtil.info(logger, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}


}
