package com.ziroom.minsu.services.cms.proxy;

import javax.annotation.Resource;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActCouponEntity;
import com.ziroom.minsu.entity.cms.ActivityFreeEntity;
import com.ziroom.minsu.entity.cms.ActivityGiftEntity;
import com.ziroom.minsu.entity.customer.CustomerRoleEntity;
import com.ziroom.minsu.services.cms.constant.CmsMessageConst;
import com.ziroom.minsu.services.cms.dto.ActivityRecordRequest;
import com.ziroom.minsu.services.cms.dto.BindActivityRequest;
import com.ziroom.minsu.services.cms.dto.BindCouponRequest;
import com.ziroom.minsu.services.cms.dto.MobileCouponRequest;
import com.ziroom.minsu.services.cms.entity.ActRecordVo;
import com.ziroom.minsu.services.cms.service.ActivityFreeServiceImpl;
import com.ziroom.minsu.services.cms.service.ActivityGiftServiceImpl;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.cms.FreeTypeEnum;
import com.ziroom.minsu.valenum.cms.GiftTypeEnum;
import com.ziroom.minsu.valenum.common.UserTypeEnum;

import org.apache.naming.java.javaURLContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.ziroom.minsu.entity.cms.ActivityRecordEntity;
import com.ziroom.minsu.services.cms.api.inner.ActivityRecordService;
import com.ziroom.minsu.services.cms.service.ActivityRecordServiceImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;

import java.util.Date;
import java.util.List;

/**
 * <p>活动领取记录实现</p>
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
@Service("cms.activityRecordProxy")
public class ActivityRecordProxy implements ActivityRecordService {

	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityRecordProxy.class);
	
	@Resource(name = "cms.messageSource")
	private MessageSource messageSource;
	
	@Resource(name = "cms.activityRecordServiceImpl")
	private ActivityRecordServiceImpl activityRecordServiceImpl;


	@Resource(name = "cms.activityGiftServiceImpl")
	private ActivityGiftServiceImpl activityGiftService;

	@Resource(name = "cms.activityFreeServiceImpl")
	private ActivityFreeServiceImpl activityFreeService;


	/**
	 * 免佣金的数据
	 * @athor afi
	 * @param json
	 * @return
	 */
	public String saveFreeComm(String json,Integer dayTime) {
		LogUtil.info(LOGGER, "saveFreeComm 参数json :{}", json);
		DataTransferObject dto = new DataTransferObject();
		try {
			ActivityFreeEntity request = JsonEntityTransform.json2Object(json, ActivityFreeEntity.class);
			if (Check.NuNObj(request)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.CMS_BIND_REQUEST_NULL));
				return dto.toJsonString();
			}
			if(Check.NuNStr(request.getActCode())
					|| Check.NuNStr(request.getActName())
					|| Check.NuNStr(request.getCreateId())
					|| Check.NuNStr(request.getUid())
					|| Check.NuNObj(request.getFreeType())
					|| Check.NuNObj(dayTime)
					|| dayTime <= 0
					){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("参数异常!");
				return dto.toJsonString();
			}
			//设置当前的有效期
			ActivityFreeEntity old = activityFreeService.getByUidAndCode(request.getUid(),request.getActCode());
			if (!Check.NuNObj(old)){
				//如果当前的用户已经执行完毕，直接幂等成功
				return dto.toJsonString();
			}
			//设置当前的有效期
			ActivityFreeEntity has = activityFreeService.getLastByUidFuther(request.getUid());
			Date start = new Date();
			if (!Check.NuNObj(has)){
				start = has.getEndTime();
			}
			request.setStartTime(start);
			request.setEndTime(DateSplitUtil.jumpDate(start,dayTime));
			activityFreeService.saveFreeComm(request);
		} catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}

		return dto.toJsonString();
	}



	/**
	 * 校验当前房东是否参加免佣金的活动
	 * @param uid
	 * @return
	 */
	public String getFive(String uid) {

		LogUtil.info(LOGGER, "checkFive 参数json :{}", uid);
		DataTransferObject dto = new DataTransferObject();

		ActivityFreeEntity activityFreeEntity = activityRecordServiceImpl.getByUidAndType(uid, FreeTypeEnum.ACTIVITY.getCode());
		dto.putValue("obj",activityFreeEntity);

		return dto.toJsonString();
	}


	/**
	 * 校验当前房东是否免佣金
	 * @param uid
	 * @return
	 */
	public String checkFree(String uid) {
		LogUtil.info(LOGGER, "checkFree 参数json :{}", uid);
		DataTransferObject dto = new DataTransferObject();
		ActivityFreeEntity activityFreeEntity = activityRecordServiceImpl.getByUid(uid);
		dto.putValue("obj",activityFreeEntity);

		return dto.toJsonString();
	}



	/**
	 * 更新当前的礼品地址
	 * @author afi
	 * @create 2016年10月22日下午7:59:04
	 * @param recordFid
	 * @param name
	 * @param address
	 * @return
	 */
	public String updateAddress(String recordFid,String name,String address) {

		LogUtil.info(LOGGER, "updateAddress 参数recordFid :{} name :{} address :{}", recordFid,name,address);
		DataTransferObject dto = new DataTransferObject();

		ActivityRecordEntity activityRecordEntity = activityRecordServiceImpl.getRecordByFid(recordFid);
		if (Check.NuNObj(activityRecordEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("异常礼品信息!");
			return dto.toJsonString();
		}
		activityRecordServiceImpl.updateAddress(recordFid,name,address);

		return dto.toJsonString();
	}

	/**
	 * 校验当前的活动
	 * @author afi
	 * @create 2016年10月22日下午7:59:04
	 * @param paramJson
	 * @return
	 */
	public String checkActivity4Record(String paramJson) {
		LogUtil.info(LOGGER, "checkActivity4Record 参数:{}", paramJson);

		DataTransferObject dto = new DataTransferObject();
		try {
			BindActivityRequest request = JsonEntityTransform.json2Object(paramJson, BindActivityRequest.class);
			if (Check.NuNObj(request)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.CMS_BIND_REQUEST_NULL));
				return dto.toJsonString();
			}

			if(Check.NuNStr(request.getGroupSn())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("活动码为空!");
				return dto.toJsonString();
			}
			if (!Check.NuNStr(request.getUid())){
				if (ValueUtil.getintValue(activityRecordServiceImpl.getHasRecordCountByGroupSnUid(request.getGroupSn(),request.getUid())) > 0){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("4");
					return dto.toJsonString();
				}
			}

			if (!Check.NuNStr(request.getMobile())){
                Long count = activityRecordServiceImpl.getHasRecordCountByGroupSnMobile(request.getGroupSn(),request.getMobile());
				if (ValueUtil.getintValue(count) > 0){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("4");
					return dto.toJsonString();
				}
			}
			dto.putValue("last",activityRecordServiceImpl.getNoRecordCountByGroupSn(request.getGroupSn()));
		} catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}


	/**
	 * 参加活动
	 * @author afi
	 * @create 2016年10月22日下午7:59:04
	 * @param paramJson
	 * @return
	 */
	public String exchangeActivity4Record(String paramJson) {
		LogUtil.info(LOGGER, "exchangeActivity4Record 参数:{}", paramJson);

		DataTransferObject dto = new DataTransferObject();
		try {
			BindActivityRequest request = JsonEntityTransform.json2Object(paramJson, BindActivityRequest.class);
			if (Check.NuNObj(request)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.CMS_BIND_REQUEST_NULL));
				return dto.toJsonString();
			}
			if( Check.NuNStr(request.getUid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("6");
				return dto.toJsonString();
			}
			if(Check.NuNStr(request.getActSn()) && Check.NuNStr(request.getGroupSn())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("6");
				return dto.toJsonString();
			}
			if (!Check.NuNStr(request.getGroupSn())){
				if (ValueUtil.getintValue(activityRecordServiceImpl.getHasRecordCountByGroupSnUid(request.getGroupSn(),request.getUid())) > 0 ){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("4");
					return dto.toJsonString();
				}
			}else {
				//TODO 未以后的扩展做准备
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("暂不支持当前的活动");
				return dto.toJsonString();
			}
			//随机获取一个活动
			ActivityRecordEntity recordEntity = activityRecordServiceImpl.getOneRecordByGroupSn(request.getGroupSn());
			if(Check.NuNObj(recordEntity)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("7");
				return dto.toJsonString();
			}
			request.setRecordSn(recordEntity.getFid());
			this.forceUserRecord(request,dto, SysConst.RETRIES);

		} catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}


	/**
	 * 强制给当用户分配一个活动
	 * @author afi
	 * @param request
	 * @param dto
	 * @param num
	 */
	private void forceUserRecord(BindActivityRequest request, DataTransferObject dto, int num){
		if (dto.getCode() != DataTransferObject.SUCCESS){
			return;
		}
		if (num < 0){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("网络超时请重试");
			return;
		}
		if (Check.NuNStr(request.getUid()) && Check.NuNStr(request.getGroupSn())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数异常");
			return ;
		}

		ActivityRecordEntity activityRecordEntity = null;
		if (!Check.NuNStr(request.getGroupSn())){
			activityRecordEntity = activityRecordServiceImpl.getOneRecordByGroupSn(request.getGroupSn());
		}
		if (Check.NuNObj(activityRecordEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("来晚了，优惠券已经被领完啦");
			return;
		}
		ActivityGiftEntity activityGiftEntity = activityGiftService.getGiftByFid(activityRecordEntity.getGiftFid());
		if (Check.NuNObj(activityGiftEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("异常的活动数据");
			return;
		}
		Date statTime = null;
		Date endTime = null;
		ActivityFreeEntity activityFreeEntity = null;
		if (activityGiftEntity.getGiftType() == GiftTypeEnum.NO_LOAD.getCode()){
			activityFreeEntity = new ActivityFreeEntity();
			statTime = new Date();
			List<CustomerRoleEntity> roles = request.getRoles();
			if (!Check.NuNCollection(roles)){
				CustomerRoleEntity role = roles.get(0);
				//暂时写死 天使房东免180天
				statTime = DateSplitUtil.jumpDate(role.getCreateDate(),180);
			}
			endTime = DateSplitUtil.jumpDate(statTime,ValueUtil.getintValue(activityGiftEntity.getGiftValue()));
			activityFreeEntity.setUid(request.getUid());
			activityFreeEntity.setActCode(activityGiftEntity.getFid());
			activityFreeEntity.setActName(activityGiftEntity.getGiftName());
			activityFreeEntity.setStartTime(statTime);
			activityFreeEntity.setEndTime(endTime);
			activityFreeEntity.setFreeType(UserTypeEnum.LANDLORD.getUserType());
		}

		String recordFid = activityRecordServiceImpl.forceUserRecord4Free(request,activityRecordEntity.getFid(),activityFreeEntity );
		if (Check.NuNStr(recordFid)){
			//领取失败 再次领取
			forceUserRecord(request,dto,num-1);
		}else {
			dto.putValue("recordFid",recordFid);
			dto.putValue("gift",activityGiftEntity);
            if(Check.NuNObj(statTime)){
                dto.putValue("statTime","");
            }else {
                dto.putValue("statTime",DateUtil.dateFormat(statTime));
            }

            if(Check.NuNObj(endTime)){
                dto.putValue("endTime","");
            }else {
                dto.putValue("endTime",DateUtil.dateFormat(endTime));
            }
		}
	}


















	/**
	 * 
	 * 保存活动记录实体
	 *
	 * @author yd
	 * @created 2016年10月9日 下午4:28:01
	 *
	 * @param activityRecord
	 * @return
	 */
	@Override
	public String saveActivityRecord(String activityRecord) {
		
		DataTransferObject dto = new DataTransferObject();
		
		ActivityRecordEntity activityRe= JsonEntityTransform.json2Object(activityRecord, ActivityRecordEntity.class);
		
		if(Check.NuNObj(activityRe)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("保存对象不存在");
			
			return dto.toJsonString();
		}
		int result = this.activityRecordServiceImpl.saveActivityRecord(activityRe);
		
		dto.putValue("result", result);
		return dto.toJsonString();
	}
	
	/**
	 * 
	 * 活动记录 分页查询
	 *
	 * @author yd
	 * @created 2016年10月9日 下午2:11:48
	 *
	 * @param activityRecordRequest
	 * @return
	 */
	@Override
	public String  queryAcRecordInfoByPage(String activityRecordRequest){
		
		DataTransferObject dto = new DataTransferObject();
		
		ActivityRecordRequest activityRecordRe = JsonEntityTransform.json2Object(activityRecordRequest, ActivityRecordRequest.class);
		
		LogUtil.info(LOGGER, "活动记录查询请求参数activityRecordRequest={}", activityRecordRequest);
		
		PagingResult<ActRecordVo> listActRecordVo = this.activityRecordServiceImpl.queryAcRecordInfoByPage(activityRecordRe);
		
		dto.putValue("count", listActRecordVo.getTotal());
		dto.putValue("listActRecordVo", listActRecordVo.getRows());
		return dto.toJsonString();
	}

}
