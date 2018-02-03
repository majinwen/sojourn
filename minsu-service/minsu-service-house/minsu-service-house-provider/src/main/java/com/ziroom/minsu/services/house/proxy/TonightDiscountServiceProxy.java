package com.ziroom.minsu.services.house.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.send.RabbitMqSendClient;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.TonightDiscountEntity;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.mq.HouseMq;
import com.ziroom.minsu.services.common.utils.JsonTransform;
import com.ziroom.minsu.services.common.utils.ZkUtil;
import com.ziroom.minsu.services.house.api.inner.HouseTonightDiscountService;
import com.ziroom.minsu.services.house.constant.HouseMessageConst;
import com.ziroom.minsu.services.house.service.HouseTonightDiscountServiceImpl;
import com.ziroom.minsu.valenum.house.RentWayEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author wangwentao 2017/5/10
 * @version 1.0
 * @since 1.0
 */
@Component("house.tonightDiscountServiceProxy")
public class TonightDiscountServiceProxy implements HouseTonightDiscountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TonightDiscountServiceProxy.class);

    @Resource(name = "house.messageSource")
    private MessageSource messageSource;

    @Resource(name = "house.houseTonightDiscountServiceImpl")
    private HouseTonightDiscountServiceImpl houseTonightDiscountServiceImpl;

    @Resource(name = "house.rabbitSendClient")
    private RabbitMqSendClient rabbitMqSendClient;

    @Resource(name = "house.queueName")
    private QueueName queueName;

    /**
     * 按条件查询今夜特价
     *
     * @param
     * @return
     * @author wangwentao
     * @created 2017/5/11 13:25
     */
    @Override
    public String findTonightDiscountByCondition(String paramJson) {
        LogUtil.info(LOGGER, "参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            TonightDiscountEntity tonightDiscountEntity = JsonTransform.json2Object(paramJson, TonightDiscountEntity.class);
            List<TonightDiscountEntity> list = houseTonightDiscountServiceImpl.findTonightDiscountByCondition(tonightDiscountEntity);
            dto.putValue("list", list);
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }

    /**
     * 按租住类型查询今夜特价
     *
     * @param
     * @return
     * @author wangwentao
     * @created 2017/5/11 13:26
     */
    @Override
    public String findTonightDiscountByRentway(String paramJson) {
        LogUtil.info(LOGGER, "参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            TonightDiscountEntity tonightDiscountEntity = JsonTransform.json2Object(paramJson, TonightDiscountEntity.class);
            if (Check.NuNObj(tonightDiscountEntity)) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                return dto.toJsonString();
            }
            if (Check.NuNObj(tonightDiscountEntity.getRentWay())) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_NULL));
                return dto.toJsonString();
            } else {
                if (RentWayEnum.ROOM.getCode() == tonightDiscountEntity.getRentWay() &&
                        Check.NuNStr(tonightDiscountEntity.getRoomFid())) {
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
                    return dto.toJsonString();
                }
                if (RentWayEnum.HOUSE.getCode() == tonightDiscountEntity.getRentWay() &&
                        Check.NuNStr(tonightDiscountEntity.getHouseFid())) {
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
                    return dto.toJsonString();
                }
            }
            TonightDiscountEntity result = houseTonightDiscountServiceImpl.findTonightDiscountByRentway(tonightDiscountEntity);
            dto.putValue("data", result);
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }

    /* (non-Javadoc)
     * @see com.ziroom.minsu.services.house.api.inner.HouseTonightDiscountService#setHouseTodayDiscount(java.lang.String)
     */
    @Override
    public String setHouseTodayDiscount(String paramJson) {
        LogUtil.info(LOGGER, "参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(paramJson)) {
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto.toJsonString();
        }
        try {
            TonightDiscountEntity tonightDiscountEntity = JsonTransform.json2Object(paramJson, TonightDiscountEntity.class);
            if (Check.NuNStr(tonightDiscountEntity.getHouseFid())) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
                return dto.toJsonString();
            }
            if (Check.NuNObj(tonightDiscountEntity.getRentWay())) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_NULL));
                return dto.toJsonString();
            }
            if (Check.NuNObj(tonightDiscountEntity.getCreateUid())) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.LANDLORD_UID_NULL));
                return dto.toJsonString();
            }
            if (Check.NuNObj(tonightDiscountEntity.getDiscount()) || tonightDiscountEntity.getDiscount() < 4 || tonightDiscountEntity.getDiscount() > 9.5) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("请正确输入折扣值！");
                return dto.toJsonString();
            }
            if (Check.NuNObj(tonightDiscountEntity.getEndTime())) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("今夜特价结束时间不能为空");
                return dto.toJsonString();
            }
            //获取今夜特价开始时间
            String stime = ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_todayDiscountStartTime.getType(), EnumMinsuConfig.minsu_todayDiscountStartTime.getCode());
            LogUtil.info(LOGGER, "zk取出的开始时间{}", stime);
            Date startTime = DateUtil.parseDate(DateUtil.dateFormat(new Date(), "yyyy-MM-dd") + " " + stime + ":00", "yyyy-MM-dd HH:mm:ss");
            LogUtil.info(LOGGER, "组装后的开始时间{}", DateUtil.dateFormat(startTime, "yyyy-MM-dd HH:mm:ss"));
            if (tonightDiscountEntity.getEndTime().getTime() <= startTime.getTime()) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("结束时间不能早于开始时间！");
                return dto.toJsonString();
            }
            tonightDiscountEntity.setStartTime(startTime);
            //判断是否已设置今夜特价
            TonightDiscountEntity result = houseTonightDiscountServiceImpl.findTonightDiscountByRentway(tonightDiscountEntity);
            if (!Check.NuNObj(result)) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("此房源已设置过今日特价！");
                return dto.toJsonString();
            }
            //未设置今夜特价设置
            if (Check.NuNObj(tonightDiscountEntity.getDiscountSource())) {
                tonightDiscountEntity.setDiscountSource(1);
            }
            if (Check.NuNObj(tonightDiscountEntity.getDiscountDate())) {
                tonightDiscountEntity.setDiscountDate(new Date());
            }
            tonightDiscountEntity.setFid(UUIDGenerator.hexUUID());
            tonightDiscountEntity.setDiscount(BigDecimalUtil.div(tonightDiscountEntity.getDiscount(), 10));
            houseTonightDiscountServiceImpl.insertTonightDiscount(tonightDiscountEntity);
            dto.putValue("ok", 1);
            dto.putValue("hintMsg", "设置成功");
            LogUtil.info(LOGGER, "刷新房源成功,推送队列消息开始...");
            String pushContent = JsonEntityTransform.Object2Json(new HouseMq(tonightDiscountEntity.getHouseFid(), null,
                    tonightDiscountEntity.getRentWay(), null, null));
            // 推送队列消息
            rabbitMqSendClient.sendQueue(queueName, pushContent);
            LogUtil.info(LOGGER, "刷新房源成功,推送队列消息结束,推送内容:{}", pushContent);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseTonightDiscountService#findRemindLandlordUid(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String findRemindLandlordUid(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            Map<String, Object> paramMap=(Map<String, Object>) JsonEntityTransform.json2Map(paramJson);
            PagingResult<HouseBaseMsgEntity> result=houseTonightDiscountServiceImpl.findRemindLandlordUid(paramMap);
            dto.putValue("count", result.getTotal());
            dto.putValue("list", result.getRows());
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
	}
}
