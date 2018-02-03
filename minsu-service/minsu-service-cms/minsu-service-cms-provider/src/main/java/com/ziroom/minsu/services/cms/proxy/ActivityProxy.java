package com.ziroom.minsu.services.cms.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityEntity;
import com.ziroom.minsu.entity.cms.ActivityFullEntity;
import com.ziroom.minsu.entity.cms.ActivityGiftItemEntity;
import com.ziroom.minsu.entity.cms.ActivityVo;
import com.ziroom.minsu.services.cms.api.inner.ActivityService;
import com.ziroom.minsu.services.cms.constant.CmsMessageConst;
import com.ziroom.minsu.services.cms.dto.*;
import com.ziroom.minsu.services.cms.entity.ZrpActFeeVo;
import com.ziroom.minsu.services.cms.service.*;
import com.ziroom.minsu.services.cms.utils.EnableGiftActivityThread;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.SnUtil;
import com.ziroom.minsu.valenum.cms.*;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>活动相关proxy</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月23日
 * @since 1.0
 * @version 1.0
 */
@Service("cms.activityProxy")
public class ActivityProxy implements ActivityService {
	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityProxy.class);
	
	@Resource(name = "cms.messageSource")
	private MessageSource messageSource;
	
	@Resource(name = "cms.activityServiceImpl")
	private ActivityServiceImpl activityServiceImpl ;
	
	@Resource(name = "cms.actCouponExtServiceImpl")
	private ActCouponExtServiceImpl actCouponExtServiceImpl ;
	
	@Resource(name = "cms.activityGiftItemServiceImpl")
	private ActivityGiftItemServiceImpl activityGiftItemServiceImpl;
	
	@Resource(name = "cms.activityRecordServiceImpl")
	private ActivityRecordServiceImpl activityRecordServiceImpl;

	@Resource(name = "cms.groupServiceImpl")
	private GroupServiceImpl groupServiceImpl;

	@Resource(name="cms.inviteCreateOrderCmsImpl")
	private InviteCreateOrderCmsImpl inviteCreateOrderCmsImpl;

	/**
	 * 获取当前的翻新活动
	 * @author
	 * @param cachType
	 * @return
     */
	@Override
	public String getCashbackList(Integer cachType) {
		DataTransferObject dto = new DataTransferObject();
		try {
			List<ActivityVo> activityEntities = activityServiceImpl.getCashbackList(cachType);
			dto.putValue("list", activityEntities);

		} catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}


	/**
	 * 获取最后一条种子房东免佣金的活动
	 * @author afi
	 * @create 2016年10月23日
	 * @return
	 */
	public String getSeedActivityLast(){
		DataTransferObject dto = new DataTransferObject();
		try {
			ActivityEntity activity = activityServiceImpl.getSeedActivityLast();
			dto.putValue("activity", activity);
		} catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 根据活动sn查询活动
	 * @author lishaochuan
	 * @create 2016年6月24日下午8:07:16
	 * @return
	 */
	@Override
	public String getActivityBySn(String activitySn) {
		DataTransferObject dto = new DataTransferObject();
        try {
        	ActivityEntity activity = activityServiceImpl.selectByActSn(activitySn);
        	dto.putValue("activity", activity);
        	
        } catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	
	/**
	 * 根据活动组查询活动
	 * @author lunan
	 * @create 2016年10月10日
	 * @return
	 */
	@Override
	public String getActivityByGroupSn(String groupSn) {
		DataTransferObject dto = new DataTransferObject();
		try {
			ActivityEntity activity = activityServiceImpl.selectByGroupSn(groupSn);
			dto.putValue("activity", activity);
			
		} catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	
	/**
	 * 查询有效的活动列表
	 * @author lishaochuan
	 * @create 2016年6月23日下午8:15:16
	 * @return
	 */
	@Override
	public String getUnderwayActivityList(){
		DataTransferObject dto = new DataTransferObject();
        try {
        	List<ActivityEntity> list = new ArrayList<>();
        	dto.putValue("list", list);
        } catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}



    /**
     * 查询有效的活动列表
     * @author lishaochuan
     * @create 2016年6月23日下午8:15:16
     * @return
     */
    @Override
    public String getRealUnderwayActivityList(){
        DataTransferObject dto = new DataTransferObject();
        try {
            List<ActivityVo> list = activityServiceImpl.getUnderwayActivityList();
            dto.putValue("list", list);
        } catch(Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }


	/**
	 * 分页查询活动信息
	 * @author lishaochuan
	 * @create 2016年6月23日下午8:12:27
	 * @param paramJson
	 * @return
	 */
	@Override
	public String getActivityVoListByCondiction(String paramJson){
    	LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
        try {
        	ActivityInfoRequest request = JsonEntityTransform.json2Object(paramJson, ActivityInfoRequest.class);
        	PagingResult<ActivityVo> pagingResult = activityServiceImpl.getActivityVoListByCondiction(request);
        	dto.putValue("total", pagingResult.getTotal());
        	dto.putValue("list", pagingResult.getRows());
        } catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
    }
	

	/**
	 * 保存活动信息
	 * @author lishaochuan
	 * @create 2016年6月23日下午3:53:26
	 * @param paramJson
	 * @return
	 */
	@Override
	public String saveActivity(String paramJson, String cityCode) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
        try {
        	ActivityEntity activity = JsonEntityTransform.json2Object(paramJson, ActivityEntity.class);
        	if(Check.NuNObj(activity)){
        		LogUtil.error(LOGGER, "保存活动信息参数为空，activity:{}", activity);
    			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
    			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
    			return dto.toJsonString();
        	}
        	activityServiceImpl.saveActivityInfo(activity, cityCode);
        	
        } catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
		
	}
	
	
	/**
	 * 
	 * 保存礼品活动 
	 *
	 * @author yd
	 * @created 2016年10月9日 下午9:14:44
	 *
	 * @param paramJson
	 * @param cityCode
	 * @return
	 */
	@Override
	public String saveGiftActivity(String paramJson, String cityCode){
		
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
        try {
        	ActivityGiftInfoRequest activityGiftInfoRe = JsonEntityTransform.json2Object(paramJson, ActivityGiftInfoRequest.class);
        	
        	if(Check.NuNObj(activityGiftInfoRe)){
        		LogUtil.error(LOGGER, "参数不存在", activityGiftInfoRe);
    			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
    			dto.setMsg("参数不存在");
    			return dto.toJsonString();
        	}
        	
        	ActivityEntity activity = activityGiftInfoRe.getAc();
        	if(Check.NuNObj(activity)){
        		LogUtil.error(LOGGER, "保存活动信息参数为空，activity:{}", activity);
    			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
    			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
    			return dto.toJsonString();
        	}
        	
        	List<ActivityGiftItemEntity> listGiftItem = activityGiftInfoRe.getListAcGiftItems();
        	int result = activityServiceImpl.saveActivityInfo(activity, cityCode);
        	if(result>0&&!Check.NuNCollection(listGiftItem)){
        		activityGiftItemServiceImpl.bachSaveGiftItem(listGiftItem);
        	}
		} catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 保存活动信息
	 * @author lishaochuan
	 * @create 2016年6月23日下午3:53:26
	 * @param paramJson
	 * @return
	 */
	@Override
	public String saveActCoupon(String paramJson, String cityCode,String houseSns) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
        try {
            ActivityFullEntity activityVo = JsonEntityTransform.json2Object(paramJson, ActivityFullEntity.class);
        	if(Check.NuNObj(activityVo)){
        		LogUtil.error(LOGGER, "saveActCoupon 保存活动信息参数为空，activityVo:{}", activityVo);
    			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
    			dto.setMsg("参数为空");
    			return dto.toJsonString();
        	}
        	String actSn = activityVo.getActSn();
        	if(Check.NuNStr(actSn)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("活动码不能为空");
				return dto.toJsonString();
			}
			if(!SnUtil.checkActSn(actSn)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("不合法的活动码");
				return dto.toJsonString();
			}
            ActivityEntity has = activityServiceImpl.selectByActSn(activityVo.getActSn());
			if (Check.NuNObj(has)){
				activityServiceImpl.addActCoupon(activityVo, cityCode,houseSns);
			}else {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("当前活动码已经被占用");
			}

        } catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
		
	}



	/**
	 * 修改活动信息
	 * @author afi
	 * @create 2016年9月13日下午3:53:26
	 * @param paramJson
	 * @return
	 */
	@Override
	public String updateActCoupon(String paramJson, String cityCode) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			ActivityFullEntity activityVo = JsonEntityTransform.json2Object(paramJson, ActivityFullEntity.class);
			if(Check.NuNObj(activityVo)){
				LogUtil.error(LOGGER, "保存活动信息参数为空，activityVo:{}", activityVo);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
				return dto.toJsonString();
			}
			ActivityEntity has = activityServiceImpl.selectByActSn(activityVo.getActSn());
			if (Check.NuNObj(has)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("当前活动不存在");
			}
			activityServiceImpl.updateActCoupon(activityVo, cityCode);
		} catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	
	
	/**
	 * 修改活动信息
	 * @author lishaochuan
	 * @create 2016年6月23日下午3:53:40
	 * @param paramJson
	 * @return
	 */
	@Override
	public String updateByActivity(String paramJson,String cityCode) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
        try {
        	ActivityEntity activity = JsonEntityTransform.json2Object(paramJson, ActivityEntity.class);
        	if(Check.NuNObj(activity)){
        		LogUtil.info(LOGGER, "修改活动信息参数为空，activity:{}", activity);
    			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
    			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
    			return dto.toJsonString();
        	}
        	if(Check.NuNObj(activity.getActSn())){
        		LogUtil.info(LOGGER, "活动码为空，activity:{}", activity);
    			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
    			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
    			return dto.toJsonString();
        	}
			if (Check.NuNStr(cityCode)){
				LogUtil.info(LOGGER, "城市为空，activity:{}，cityCode：{}", activity,cityCode);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
				return dto.toJsonString();
			}
        	
        	ActivityEntity queryActivity = activityServiceImpl.selectByActSn(activity.getActSn());
        	if(queryActivity.getActStatus() != ActStatusEnum.DISABLE.getCode()){
        		LogUtil.info(LOGGER, "已启用的活动不允许修改，activity:{}", activity);
    			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
    			dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.CMS_ACTIVITY_ENABLE_UPDATE));
    			return dto.toJsonString();
        	}
        	activityServiceImpl.updateActivityInfo(activity,cityCode);
        } catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}



    /**
     * 终止活动
     * @author afi
     * @param avtivitySn
     * @return
     */
    @Override
    public String endActivity(String avtivitySn) {
        LogUtil.info(LOGGER, "参数avtivitySn:{}", avtivitySn);
        DataTransferObject dto = new DataTransferObject();
        try {
            if(Check.NuNObj(avtivitySn)){
                LogUtil.error(LOGGER, "启动参数为空，avtivitySn:{}", avtivitySn);
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
                return dto.toJsonString();
            }

            ActivityEntity activity = activityServiceImpl.selectByActSn(avtivitySn);
            if(activity.getActStatus() != ActStatusEnum.ABLE.getCode()){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("只有进行中的活动才能被终止");
                return dto.toJsonString();
            }

            activityServiceImpl.endActivity(avtivitySn);

        } catch(Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }




    /**
	 * 启动活动
	 * 
	 * 说明： 如果普通活动 是礼品活动需要 异步 插入 礼品记录
	 * 
	 * @author lishaochuan
	 * @create 2016年6月23日下午6:06:20
	 * @param avtivitySn
	 * @return
	 */
	@Override
	public String enableActivity(String avtivitySn) {
		LogUtil.info(LOGGER, "参数avtivitySn:{}", avtivitySn);
		DataTransferObject dto = new DataTransferObject();
        try {
        	if(Check.NuNObj(avtivitySn)){
        		LogUtil.error(LOGGER, "启动参数为空，avtivitySn:{}", avtivitySn);
    			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
    			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
    			return dto.toJsonString();
        	}
        	
        	ActivityEntity activity = activityServiceImpl.selectByActSn(avtivitySn);
        	if(activity.getActStatus() != ActStatusEnum.DISABLE.getCode()){
        		dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
    			dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.CMS_ACTIVITY_ENABLE));
    			return dto.toJsonString();
        	}
        	
        	activityServiceImpl.enableActivity(avtivitySn);
        	
        	//添加礼品领取记录
        	SendThreadPool.execute(new EnableGiftActivityThread(activityGiftItemServiceImpl, activityServiceImpl, activityRecordServiceImpl, avtivitySn));
        	
        } catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	
	

	/**
	 * 修改 活动 (兼容 礼品的修改)
	 * @author yd
	 * @create 2016年6月23日下午3:53:40
	 * @param paramJson
	 * @return
	 */
	@Override
	public String updateGiftAcByActivity(String paramJson,String cityCode) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
        try {
        	
              ActivityGiftInfoRequest activityGiftInfoRe = JsonEntityTransform.json2Object(paramJson, ActivityGiftInfoRequest.class);
        	
        	if(Check.NuNObj(activityGiftInfoRe)){
        		LogUtil.error(LOGGER, "参数不存在", activityGiftInfoRe);
    			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
    			dto.setMsg("参数不存在");
    			return dto.toJsonString();
        	}
        	ActivityEntity activity =activityGiftInfoRe.getAc();
        	if(Check.NuNObj(activity)){
        		LogUtil.info(LOGGER, "修改活动信息参数为空，activity:{}", activity);
    			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
    			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
    			return dto.toJsonString();
        	}
        	if(Check.NuNObj(activity.getActSn())){
        		LogUtil.info(LOGGER, "活动码为空，activity:{}", activity);
    			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
    			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
    			return dto.toJsonString();
        	}
			if (Check.NuNStr(cityCode)){
				LogUtil.info(LOGGER, "城市为空，activity:{}，cityCode：{}", activity,cityCode);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
				return dto.toJsonString();
			}
        	
        	ActivityEntity queryActivity = activityServiceImpl.selectByActSn(activity.getActSn());
        	
        	if(Check.NuNObj(queryActivity)){
        		dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
    			dto.setMsg("当前活动已不存在");
    			return dto.toJsonString();
        	}
        	if(queryActivity.getActStatus() != ActStatusEnum.DISABLE.getCode()){
        		LogUtil.info(LOGGER, "已启用的活动不允许修改，activity:{}", activity);
    			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
    			dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.CMS_ACTIVITY_ENABLE_UPDATE));
    			return dto.toJsonString();
        	}
			activityServiceImpl.updateGiftActivityInfo(activityGiftInfoRe, cityCode);
		} catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * @Description: 获取首单立减活动详情
	 * @Author:lusp
	 * @Date: 2017/6/5 11:24
	 * @Params:
	 */
	@Override
	public String getSDLJActivityInfo() {
		DataTransferObject dto = new DataTransferObject();
		try {
			ActivityEntity activity = activityServiceImpl.getSDLJActivityInfo();
			dto.putValue("activity", activity);
		} catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String listActivityByGroupSn(String groupSn) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(groupSn)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("活动组号为空");
			return dto.toJsonString();
		}
		List<ActivityEntity> activityEntities = activityServiceImpl.listActivityByGroupSn(groupSn);
		dto.putValue("list",activityEntities);
		return dto.toJsonString();
	}

	@Override
	public String listActFeeConditionForZrp(String paramJson) {
		LogUtil.info(LOGGER, "【listActFeeConditionForZrp】参数paramJson={}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		ZrpActRequest zrpActRequest = JsonEntityTransform.json2Object(paramJson, ZrpActRequest.class);
		if (Check.NuNObj(zrpActRequest)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		if (Check.NuNObj(zrpActRequest.getUid())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("uid为空");
			return dto.toJsonString();
		}
		if (Check.NuNObj(zrpActRequest.getCommonFee())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务费为空");
			return dto.toJsonString();
		}
		if (Check.NuNObj(zrpActRequest.getCustomerType())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("客户类型为空");
			return dto.toJsonString();
		}
		if (Check.NuNObj(zrpActRequest.getSignType())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("签约类型为空");
			return dto.toJsonString();
		}
		if (Check.NuNObj(zrpActRequest.getProjectId())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("项目ID为空");
			return dto.toJsonString();
		}
		if (Check.NuNObj(zrpActRequest.getLayoutId())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("户型ID为空");
			return dto.toJsonString();
		}
		if (Check.NuNObj(zrpActRequest.getRoomId())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间ID为空");
			return dto.toJsonString();
		}

        try {
			zrpActRequest.setOriginCommonFee(zrpActRequest.getCommonFee());

            List<ActivityEntity> activityEntities = activityServiceImpl.listActConditionForZrp(zrpActRequest);
			List<ZrpActFeeVo> resultActVo = new ArrayList<>();

            if (Check.NuNCollection(activityEntities)) {
				dto.putValue("actList", resultActVo);
				LogUtil.info(LOGGER, "【listActFeeConditionForZrp】没有有效活动,直接返回str={}", dto.toJsonString());
                return dto.toJsonString();
            }
            //过滤可以不可以叠加的活动
            List<ActivityEntity> stackActList = activityEntities.stream().filter(p -> p.getIsStack() == IsStackEnum.NO.getCode()).collect(Collectors.toList());
            ActivityEntity bestAct = null;
            if (!Check.NuNCollection(stackActList)) {
                //不为空选中其中一个  最优惠的活动
                double maxMoney = 0.0;
                for (ActivityEntity activityEntity : stackActList) {
                    Integer actType = activityEntity.getActType();
                    if (actType == ActTypeEnum.CUT.getCode()) {
                        double discountVal = calcualteDiscountFee(zrpActRequest, activityEntity);
                        if (maxMoney < discountVal) {
                            maxMoney = discountVal;
                            bestAct = activityEntity;
                        }

                    } else if (actType == ActivityTypeEnum.CUT.getCode()) {
                        double discountVal = calcualteCutFee(zrpActRequest, activityEntity);
						if (discountVal == 0.0) {
							continue;
						}
						if (maxMoney < discountVal) {
                            maxMoney = discountVal;
                            bestAct = activityEntity;
                        }
                    }
                }
            }
			//取最优惠得一个活动
			activityEntities.removeAll(stackActList);
            if (!Check.NuNObj(bestAct)) {
                activityEntities.add(bestAct);
            }

            if (!Check.NuNCollection(activityEntities)) {
				//满减活动列表
				List<ActivityEntity> cutActList = activityEntities.stream().filter(p -> p.getActType() == ActivityTypeEnum.CUT.getCode()).collect(Collectors.toList());
				//折扣活动列表
				activityEntities.removeAll(cutActList);
				//满减活动计算开始
				if (!Check.NuNCollection(cutActList)) {
					//满减计算
                    Map actCutMap = permutaAndCombinBestList(zrpActRequest, cutActList, ActivityTypeEnum.CUT.getCode());
                    int index = (int) actCutMap.get("index");
                    double cutMax = (double) actCutMap.get("cutMax");
                    zrpActRequest.setCommonFee(zrpActRequest.getCommonFee().subtract(BigDecimal.valueOf(cutMax)));
                    List<ZrpActFeeVo> bestActCutFees = (List<ZrpActFeeVo>) actCutMap.get(index);
                    LogUtil.info(LOGGER, "计算满减组合结束,最佳组合bestActList={},cutMax={}", JsonEntityTransform.Object2Json(bestActCutFees), cutMax);
					resultActVo.addAll(bestActCutFees);
				}

                if (!Check.NuNCollection(activityEntities) && zrpActRequest.getCommonFee().doubleValue() != 0.0) {
                    Map actDiscountMap = permutaAndCombinBestList(zrpActRequest, activityEntities, ActTypeEnum.CUT.getCode());
                    int index = (int) actDiscountMap.get("index");
                    double cutMax = (double) actDiscountMap.get("cutMax");
                    List<ZrpActFeeVo> bestActLimtFees = (List<ZrpActFeeVo>) actDiscountMap.get(index);
                    resultActVo.addAll(bestActLimtFees);
					LogUtil.info(LOGGER, "计算满减组合结束,最佳组合bestActList={},cutMax={}", JsonEntityTransform.Object2Json(bestActLimtFees), cutMax);
				}
			}
			dto.putValue("actList", resultActVo);
			LogUtil.info(LOGGER, "【listActFeeConditionForZrp】返回结果dto={}", dto.toJsonString());
            return dto.toJsonString();
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【listActFeeConditionForZrp】异常错误e={}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务错误");
            return dto.toJsonString();
        }
    }

	/**
	 * 排列组合 计算优惠力度最大得排列组合结果
	 *
	 * @param zrpActRequest
	 * @param actList
	 * @return
	 */
    public Map permutaAndCombinBestList(ZrpActRequest zrpActRequest, List<ActivityEntity> actList, int actType) {
        //出租时间
		//计算排列组合
		List<List<ActivityEntity>> allList = new ArrayList<>();
		perm(allList, actList, 0, actList.size() - 1);
        int index = 0;
        double cutMax = 0.0;
        //排列组合后 在依次循环
		BigDecimal orgialCommonFee = zrpActRequest.getCommonFee();//原始服务费
        Map cashMap = new HashMap();//组装数据 可以少遍历一次
        for (int i = 0; i < allList.size(); i++) {
            int rentDay = zrpActRequest.getRentDay();
            List<ZrpActFeeVo> cashActList = new ArrayList<>();
			List<ActivityEntity> eachList = allList.get(i);
			double zomCut = 0.0;
			zrpActRequest.setCommonFee(orgialCommonFee);
			for (ActivityEntity activityEntity : eachList) {
				if (actType == ActivityTypeEnum.CUT.getCode() && rentDay < activityEntity.getActLimit()) {
					LogUtil.info(LOGGER, "自如寓满减活动不满足最小条件,actSn={}", activityEntity.getActSn());
					continue;
				}
                //金额减到0直接跳出循环
                if (zrpActRequest.getCommonFee().doubleValue() == 0.0) {
                    break;
                }

                double cutVal = 0.0;
                if (actType == ActivityTypeEnum.CUT.getCode()) {
                    //满减
                    cutVal = calcualteCutFee(zrpActRequest, activityEntity);
                } else if (actType == ActTypeEnum.CUT.getCode()) {
                    //折扣
                    cutVal = calcualteDiscountFee(zrpActRequest, activityEntity);
                }

				//组装出参数据
				ZrpActFeeVo zrpActFeeVo = new ZrpActFeeVo();
				zrpActFeeVo.setActSn(activityEntity.getActSn());
				zrpActFeeVo.setActName(activityEntity.getActName());
				zrpActFeeVo.setDiscountAmount(cutVal);
				zrpActFeeVo.setExpendCostCode(ActCostCodeEnum.getByCode(activityEntity.getActCostType()).getCodeStr());
				cashActList.add(zrpActFeeVo);
				if (actType == ActivityTypeEnum.CUT.getCode()) {
					//出租天数减少累加计算
					rentDay = rentDay - activityEntity.getActCut();
				}
				zrpActRequest.setCommonFee(zrpActRequest.getCommonFee().subtract(BigDecimal.valueOf(cutVal)));
				zomCut += cutVal;
                //如果已经是0 则退出循环

			}
            cashMap.put(i, cashActList);
            LogUtil.info(LOGGER, "组合计算排序计算金额index={} money={}", i, zomCut);
			if (zomCut > cutMax) {
				cutMax = zomCut;
				index = i;
			}
		}
        cashMap.put("index", index);
        cashMap.put("cutMax", cutMax);
        zrpActRequest.setCommonFee(orgialCommonFee);
		return cashMap;
	}


	/**
	 * 计算折扣金额
	 *
	 * @param
	 * @return
	 * @author jixd
	 * @created 2017年10月16日 18:05:47
	 */
	private double calcualteDiscountFee(ZrpActRequest zrpActRequest, ActivityEntity activityEntity) {
		//折扣    计算折扣金额
		Integer actCut = activityEntity.getActCut();
		BigDecimal commonFee = zrpActRequest.getCommonFee();
		BigDecimal subtract = BigDecimal.valueOf(100).subtract(BigDecimal.valueOf(actCut));
        BigDecimal discountMoney = commonFee.multiply(subtract)
				.divide(BigDecimal.valueOf(100), 0, BigDecimal.ROUND_HALF_UP);
		//数据库存入的分
		Integer actMax = activityEntity.getActMax();
		double discountVal = discountMoney.doubleValue();
		if (discountVal > actMax) {
			discountVal = actMax;
		}
		return discountVal;
	}

	/**
	 * 计算满减金额
	 *
	 * @param
	 * @return
	 * @author jixd
	 * @created 2017年10月16日 18:06:03
	 */
	private double calcualteCutFee(ZrpActRequest zrpActRequest, ActivityEntity activityEntity) {
		//满减
		Integer rentDay = zrpActRequest.getRentDay();
		Integer actLimit = activityEntity.getActLimit();
		Integer actCut = activityEntity.getActCut();
		if (rentDay >= actLimit) {
            BigDecimal originCommonFee = zrpActRequest.getOriginCommonFee();
            BigDecimal discountMoney = originCommonFee.divide(BigDecimal.valueOf(rentDay), 2, BigDecimal.ROUND_HALF_UP)
                    .multiply(BigDecimal.valueOf(actCut))
					.setScale(0, BigDecimal.ROUND_HALF_UP);
            BigDecimal commonFee = zrpActRequest.getCommonFee();
            double discountVal = discountMoney.doubleValue();
            if (commonFee.doubleValue() <= discountMoney.doubleValue()) {
                discountVal = commonFee.doubleValue();
            }
            return discountVal;
		}
		return 0.0;
	}

	/**
	 * 排列组合
	 *
	 * @param allList
	 * @param actList
	 * @param from
	 * @param to
	 */
	public void perm(List<List<ActivityEntity>> allList, List<ActivityEntity> actList, int from, int to) {
		if (from == to) {
			List<ActivityEntity> addList = new ArrayList<>();
			addList.addAll(actList);
			allList.add(addList);
			return;
		}
		for (int j = from; j <= to; j++) {
			swap(actList, from, j);
			perm(allList, actList, from + 1, to);
			swap(actList, from, j);
		}
	}


	/**
	 * 更改节点
	 *
	 * @param
	 * @return
	 * @author jixd
	 * @created 2017年12月06日 13:23:36
	 */
	private void swap(List<ActivityEntity> actList, int a, int b) {
		ActivityEntity temp = actList.get(a);
		actList.set(a, actList.get(b));
		actList.set(b,temp);
	}


    /**
     * 查询用户是否参与过邀请活动
     * @param paramJson
     * @return  0.未参加活动 1.已参加活动
     * @author yanb
     */
	@Override
	public String checkUserInviteStateByUid(String paramJson) {
		LogUtil.info(LOGGER,"[checkUserInviteStateByUid]参数{}",paramJson);
		DataTransferObject dto = new DataTransferObject();
        try {
			InviteStateUidRequest inviteStateUidRequest = JsonEntityTransform.json2Object(paramJson, InviteStateUidRequest.class);
            if (Check.NuNObj(inviteStateUidRequest)) {
                LogUtil.error(LOGGER, "参数为空");
                throw new BusinessException("参数为空");
            }
            Integer userInviteState = activityServiceImpl.checkUserInviteStateByUid(inviteStateUidRequest);
            //如果参加过,则查询邀请人的uid
			if (userInviteState.equals(YesOrNoEnum.YES.getCode())) {
				String inviteUid = inviteCreateOrderCmsImpl.getInviteUidByUid(inviteStateUidRequest);
				dto.putValue("inviteUid",inviteUid);
			}
			dto.putValue("userInviteState", userInviteState);
            return dto.toJsonString();
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【checkUserInviteStateByUid】异常错误e={}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务错误");
            return dto.toJsonString();
        }
    }

	/**
	 * 新版邀请好友下单活动
	 * @author yanb
	 * @created 2017年12月13日 15:11:33
	 * @param  * @param null
	 * @return
	 */
	@Override
	public String checkUserInviteStateByList(String paramJson) {
		LogUtil.info(LOGGER,"【checkUserInviteStateByList】遍历查询是否订单的uid符合增加积分的要求，参数={}",paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			//转换paramJson为list
			List<InviteOrderRequest> orderList = JsonEntityTransform.json2ObjectList(paramJson, InviteOrderRequest.class);
			if (Check.NuNCollection(orderList)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("没有订单");
				return dto.toJsonString();
			}
			//new出一个用于返回的list
			List<InviteOrderRequest> inviteOrderList = new ArrayList<InviteOrderRequest>();
			//查询的条件是是: uid + 邀请码来源为1.新版邀请好友活动 + 是否给邀请人增加积分的状态为:0.尚未给邀请人增加积分
			InviteStateUidRequest inviteStateUidRequest = new InviteStateUidRequest();
			inviteStateUidRequest.setInviteSource(InviteSourceEnum.NEW_INVITE.getCode());
			inviteStateUidRequest.setIsGiveInviterPoints(IsGiveInviterPointsEnum.NOT_YET_ADD.getCode());

			if (!Check.NuNCollection(orderList)) {
				for (InviteOrderRequest ioq : orderList) {
					inviteStateUidRequest.setUid(ioq.getUserUid());
					inviteStateUidRequest.setLastModifyDate(ioq.getLastModifyDate());
					Integer result = null;
					try {
						result = activityServiceImpl.checkUserInviteStateByUid(inviteStateUidRequest);
					} catch (Exception e) {
						LogUtil.error(LOGGER, "【checkUserInviteStateByList】遍历出现异常,参数={},e={}", inviteStateUidRequest.toJsonStr(),e);
						continue;
					}
					if (result.equals(YesOrNoEnum.YES.getCode())) {
						//为iov对象增加活动类型
						ioq.setInviteSource(InviteSourceEnum.NEW_INVITE.getCode());
						inviteOrderList.add(ioq);
					}
				}
			}

			dto.putValue("inviteOrderList",inviteOrderList);
			LogUtil.info(LOGGER,"【checkUserInviteStateByList】结果dto={}",dto.toJsonString());

		} catch (Exception e) {
			LogUtil.error(LOGGER, "【checkUserInviteStateByList】异常错误e={}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务错误");
		}
		return dto.toJsonString();
	}

	/**
	 * 根据ActSn更新活动信息
	 * @author yanb
	 * @created 2018年01月23日 20:58:14
	 * @param  * @param null
	 * @return
	 */
	@Override
	public String updateActivityByActSn(String paramJson) {
		LogUtil.info(LOGGER,"【updateActivityByActSn】根据ActSn更新活动信息，参数={}",paramJson);
		DataTransferObject dto = new DataTransferObject();

		try {
			ActivityEntity activityEntity = JsonEntityTransform.json2Entity(paramJson, ActivityEntity.class);
			if (Check.NuNObj(activityEntity)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("活动信息不能为空");
				return dto.toJsonString();
			}
			if (Check.NuNStr(activityEntity.getActSn())) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("活动编号不能为空");
				return dto.toJsonString();
			}

			activityServiceImpl.updateByActSn(activityEntity);

		} catch (Exception e) {
			LogUtil.error(LOGGER, "【updateActivityByActSn】异常错误e={}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务错误");
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

}
