/**
 * @FileName: HouseTopServiceProxy.java
 * @Package com.ziroom.minsu.services.house.proxy
 * 
 * @author bushujie
 * @created 2017年3月17日 下午4:22:56
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.proxy;

import javax.annotation.Resource;

import com.asura.framework.base.util.Check;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.send.RabbitMqSendClient;
import com.ziroom.minsu.entity.house.HouseTopColumnEntity;
import com.ziroom.minsu.entity.house.HouseTopEntity;
import com.ziroom.minsu.services.common.mq.HouseMq;
import com.ziroom.minsu.services.house.entity.HouseTopSortVo;
import com.ziroom.minsu.valenum.house.HouseTopEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.house.api.inner.HouseTopService;
import com.ziroom.minsu.services.house.dto.HouseTopDto;
import com.ziroom.minsu.services.house.dto.HouseTopSaveDto;
import com.ziroom.minsu.services.house.entity.HouseTopDetail;
import com.ziroom.minsu.services.house.entity.HouseTopListVo;
import com.ziroom.minsu.services.house.service.HouseTopServiceImpl;

/**
 * <p></p>
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
@Component("house.houseTopServiceProxy")
public class HouseTopServiceProxy implements HouseTopService{
	
	/**
	 * 日志工具
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseTopServiceProxy.class);
	
	@Resource(name="house.houseTopServiceImpl")
	private HouseTopServiceImpl houseTopServiceImpl;
	
	@Resource(name="house.messageSource")
	private MessageSource messageSource;

	@Resource(name = "house.rabbitSendClient")
	private RabbitMqSendClient rabbitMqSendClient;

	@Resource(name="house.queueName")
	private QueueName queueName ;

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseTopService#topHouseListData(java.lang.String)
	 */
	@Override
	public String topHouseListData(String paramJson) {
		LogUtil.info(LOGGER, "topHouseListData参数:{}", paramJson);
		DataTransferObject dto=new DataTransferObject();
		try {
			HouseTopDto houseTopDto=JsonEntityTransform.json2Object(paramJson, HouseTopDto.class);
			PagingResult<HouseTopListVo> pageResult=houseTopServiceImpl.findTopHouseListPage(houseTopDto);
			dto.putValue("dataList", pageResult.getRows());
			dto.putValue("count", pageResult.getTotal());
		} catch(Exception e) {
            LogUtil.error(LOGGER, "topHouseListData error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseTopService#insertTopHouse(java.lang.String)
	 */
	@Override
	public String insertTopHouse(String paramJson) {
		LogUtil.info(LOGGER, "insertTopHouse参数:{}", paramJson);
		DataTransferObject dto=new DataTransferObject();
		try {
			HouseTopSaveDto houseTopSaveDto=JsonEntityTransform.json2Object(paramJson, HouseTopSaveDto.class);
			houseTopServiceImpl.insertHouseTop(houseTopSaveDto);
		} catch(Exception e) {
            LogUtil.error(LOGGER, "insertTopHouse error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseTopService#houseTopDetail(java.lang.String)
	 */
	@Override
	public String houseTopDetail(String fid) {
		LogUtil.info(LOGGER, "houseTopDetail参数:{}", fid);
		DataTransferObject dto=new DataTransferObject();
		try {
			HouseTopDetail houseTopDetail=houseTopServiceImpl.findHouseTopDetail(fid);
			dto.putValue("houseTopDetail", houseTopDetail);
		} catch(Exception e) {
            LogUtil.error(LOGGER, "houseTopDetail error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/**
	 * 交换两个序号
	 * @param paramJson
	 * @return
     */
	@Override
	public String houseTopSortExchange(String paramJson) {
		LogUtil.info(LOGGER, "houseTopSortExchange参数:{}", paramJson);
		DataTransferObject dto=new DataTransferObject();
		try {
			HouseTopSortVo houseTopSortVo = JsonEntityTransform.json2Object(paramJson, HouseTopSortVo.class);
			if(Check.NuNObj(houseTopSortVo)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			if(Check.NuNObj(houseTopSortVo.getNewTopSort()) || Check.NuNStr(houseTopSortVo.getFid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			dto.putValue("result",houseTopServiceImpl.updateHouseTopSortExchange(houseTopSortVo));
		} catch(Exception e) {
			LogUtil.error(LOGGER, "houseTopSortExchange error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();

	}

	/**
	 * 上浮或者下沉
	 * @param paramJson
	 * @return
	 */
	@Override
	public String houseTopSortFloatOrSink(String paramJson) {
		LogUtil.info(LOGGER, "houseTopSortFloatOrSink参数:{}", paramJson);
		DataTransferObject dto=new DataTransferObject();
		try {
			HouseTopSortVo houseTopSortVo = JsonEntityTransform.json2Object(paramJson, HouseTopSortVo.class);
			if(Check.NuNObj(houseTopSortVo)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			if(Check.NuNObj(houseTopSortVo.getNewTopSort()) || Check.NuNStr(houseTopSortVo.getFid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			//输入的新的序号
			Integer newTopSort = houseTopSortVo.getNewTopSort();
			//原来的序号
			Integer oldTopSort = houseTopSortVo.getTopSort();
			if(oldTopSort>newTopSort){
				//原来的比现在的要大，则选择下沉
				dto.putValue("result",houseTopServiceImpl.updateHouseTopSortSink(houseTopSortVo));
			}else{
				dto.putValue("result",houseTopServiceImpl.updateHouseTopSortFloat(houseTopSortVo));
			}

		} catch(Exception e) {
			LogUtil.error(LOGGER, "houseTopSortFloatOrSink error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();

	}

	/**
	 * 上线下线
	 * @param fid
	 * @return
     */
	@Override
	public String houseTopOnlineOrDownline(String paramJson) {
		LogUtil.info(LOGGER, "houseTopOnlineOrDownline参数:{}", paramJson);
		DataTransferObject dto=new DataTransferObject();
		try{
			HouseTopSaveDto houseTopSaveDto=JsonEntityTransform.json2Object(paramJson, HouseTopSaveDto.class);
			if(Check.NuNStr(houseTopSaveDto.getFid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			HouseTopEntity houseTop = houseTopServiceImpl.findHouseTopByFid(houseTopSaveDto.getFid());
			if(Check.NuNObj(houseTop)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.NOT_FOUND));
				return dto.toJsonString();
			}
			houseTopSaveDto.setTopStatus(houseTop.getTopStatus());
			if(houseTop.getTopStatus()== HouseTopEnum.ONLINE_TOP.getValue()){
				houseTop.setTopStatus(HouseTopEnum.DOWNLINE_TOP.getValue());
			}else if(houseTop.getTopStatus() == HouseTopEnum.DOWNLINE_TOP.getValue()||HouseTopEnum.NEW_TOP.getValue()==houseTop.getTopStatus()){
				houseTop.setTopStatus(HouseTopEnum.ONLINE_TOP.getValue());
			}

			int result = houseTopServiceImpl.updateHouseTop(houseTop,houseTopSaveDto);
			dto.putValue("result",result);
			//上下线成功，发送mq
			if(result>0 && !Check.NuNStr(houseTop.getHouseBaseFid())){
				LogUtil.info(LOGGER, "上线或者下线成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseTop.getHouseBaseFid(), null,
						houseTop.getRentWay(), null, null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "上线或者下线成功,推送队列消息结束,推送内容:{}", pushContent);
			}
		}catch (Exception e){
			LogUtil.error(LOGGER, "houseTopOnlineOrDownline error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseTopService#updateHouseTop(java.lang.String)
	 */
	@Override
	public String updateHouseTop(String paramJson) {
		LogUtil.info(LOGGER, "updateHouseTop参数:{}", paramJson);
		DataTransferObject dto=new DataTransferObject();
		try {
			HouseTopSaveDto houseTopSaveDto=JsonEntityTransform.json2Object(paramJson, HouseTopSaveDto.class);
			houseTopServiceImpl.updateHouseTop(houseTopSaveDto);
		} catch(Exception e) {
            LogUtil.error(LOGGER, "updateHouseTop error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseTopService#updateHouseTopColumn(java.lang.String)
	 */
	@Override
	public String updateHouseTopColumn(String paramJson) {
		LogUtil.info(LOGGER, "updateHouseTopColumn参数:{}", paramJson);
		DataTransferObject dto=new DataTransferObject();
		try {
			HouseTopColumnEntity houseTopColumnEntity=JsonEntityTransform.json2Object(paramJson, HouseTopColumnEntity.class);
			houseTopServiceImpl.updateHouseTopColumn(houseTopColumnEntity);
		} catch(Exception e) {
            LogUtil.error(LOGGER, "updateHouseTopColumn error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
}
