package com.ziroom.minsu.services.cms.proxy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityApplyDescEntity;
import com.ziroom.minsu.entity.cms.ActivityApplyEntity;
import com.ziroom.minsu.entity.cms.ActivityApplyExtEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.cms.api.inner.ActivityApplyService;
import com.ziroom.minsu.services.cms.constant.CmsMessageConst;
import com.ziroom.minsu.services.cms.dto.ActivityApplyRequest;
import com.ziroom.minsu.services.cms.dto.LanApplayRequest;
import com.ziroom.minsu.services.cms.entity.ActivityApplyVo;
import com.ziroom.minsu.services.cms.service.ActivityApplyServiceImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.valenum.cms.ApplyExtType;
import com.ziroom.minsu.valenum.customer.ApplyStatusEnum;
import com.ziroom.minsu.valenum.customer.CustomerRoleEnum;

/**
 * <p>活动申请信息</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月29日
 * @since 1.0
 * @version 1.0
 */
@Service("cms.activityApplyProxy")
public class ActivityApplyProxy implements ActivityApplyService{

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityApplyProxy.class);
	
	@Resource(name = "cms.messageSource")
	private MessageSource messageSource;
	
	@Resource(name = "cms.activityApplyServiceImpl")
	private ActivityApplyServiceImpl activityApplyServiceImpl;
	
	@Resource(name ="basedata.confCityService")
	private ConfCityService confCityService;
	
	/**
	 * 保存种子房东申请信息
	 * @author lishaochuan
	 * @create 2016年6月29日下午5:50:51
	 * @param paramJson
	 * @return
	 */
	@Override
	public String saveApply(String paramJson) {
		long t1 = System.currentTimeMillis();// 开始时间
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
        try {
        	ActivityApplyRequest request = JsonEntityTransform.json2Object(paramJson, ActivityApplyRequest.class);
        	
//        	ActivityApplyEntity queryApply = activityApplyServiceImpl.getApplyByMobile(request.getCustomerMoblie());
//        	long t2 = System.currentTimeMillis();
//			LogUtil.info(LOGGER, "queryApply耗时{}ms", t2-t1);
//			
//        	if(!Check.NuNObj(queryApply)){
//        		LogUtil.info(LOGGER, "此手机号已经申请过,queryApply:{}", queryApply);
//    			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
//    			dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.CMS_APPLY_HAVE_MOBILE));
//    			return dto.toJsonString();
//        	}
        	
        	ActivityApplyEntity apply = new ActivityApplyEntity();
        	BeanUtils.copyProperties(request, apply);
        	String fid = UUIDGenerator.hexUUID();
        	apply.setFid(fid);
        	apply.setRoleCode(CustomerRoleEnum.SEED.getCode());
        	apply.setApplyStatus(ApplyStatusEnum.APPLY.getCode());
        	
        	ActivityApplyDescEntity applyDesc = new ActivityApplyDescEntity();
        	BeanUtils.copyProperties(request, applyDesc);
        	applyDesc.setFid(UUIDGenerator.hexUUID());
        	applyDesc.setActivityApplyFid(fid);
        	
        	
        	List<ActivityApplyExtEntity> applyExtList = new ArrayList<ActivityApplyExtEntity>();
        	List<String> houseUrlList = request.getHouseUrlList();
        	for (String houserUrl : houseUrlList) {
        		ActivityApplyExtEntity applyExt = new ActivityApplyExtEntity();
        		applyExt.setApplyFid(apply.getFid());
        		applyExt.setExtType(ApplyExtType.URL.getCode());
        		applyExt.setContent(houserUrl);
        		applyExtList.add(applyExt);
			}
        	long t3 = System.currentTimeMillis();
			LogUtil.info(LOGGER, "before save耗时{}ms", t3-t1);
        	
        	activityApplyServiceImpl.save(apply,applyDesc, applyExtList);
        	
        	long t4 = System.currentTimeMillis();
			LogUtil.info(LOGGER, "after save耗时{}ms", t4-t3);
        } catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	
	
	/**
	 * 保存种子房东申请信息
	 * @author liyingjie
	 * @create 2016年6月29日下午5:50:51
	 * @param paramJson
	 * @return
	 */
	@Override
	public String applyList(String paramJson) {
		
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
        try {
        	LanApplayRequest request = JsonEntityTransform.json2Object(paramJson, LanApplayRequest.class);
    		PagingResult<ActivityApplyVo> pagingResult = activityApplyServiceImpl.queryApplayList(request);
    		if (!Check.NuNCollection(pagingResult.getRows())) {
				List<ActivityApplyVo> list = new ArrayList<ActivityApplyVo>();
				for (ActivityApplyVo activityApplyVo : pagingResult.getRows()) {
					activityApplyVo = transferActivityApplyVo(activityApplyVo);
					list.add(activityApplyVo);
				}
				pagingResult.setRows(list);
			}
    		
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
	 * 转化名称
	 * @author zl
	 * @param applyVo
	 * @return
	 */
	private ActivityApplyVo transferActivityApplyVo(ActivityApplyVo applyVo){
		if (Check.NuNObj(applyVo)) {
			return null;
		}
		if (!Check.NuNObj(applyVo.getApplyStatus())) {
			applyVo.setApplyStatusName(ApplyStatusEnum.getNameByCode(applyVo.getApplyStatus()));
		}
		if (!Check.NuNObj(applyVo.getRoleCode())) {
			CustomerRoleEnum roleEnum = CustomerRoleEnum.getCustomerRoleByCode(applyVo.getRoleCode());
			if (!Check.NuNObj(roleEnum)) {				
				applyVo.setRoleCodeName(roleEnum.getName());
			}
		}
		if (!Check.NuNStr(applyVo.getCityCode())) {
			String cityJson = confCityService.getCityNameByCode(applyVo.getCityCode());
			DataTransferObject dto  = JsonEntityTransform.json2DataTransferObject(cityJson);
			if(dto.getCode() == DataTransferObject.SUCCESS){
				String cityName = dto.parseData("cityName", new TypeReference<String>() {});
				applyVo.setCityName(cityName);
            }
			
		}
		
		if (!Check.NuNStr(applyVo.getAreaCode())) {
			String cityJson = confCityService.getCityNameByCode(applyVo.getAreaCode());
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(cityJson);
			if(dto.getCode() == DataTransferObject.SUCCESS){
				String name = dto.parseData("cityName", new TypeReference<String>() {});
				applyVo.setAreaName(name);
            }
		}
		
		return applyVo;	
	}
	


	@Override
	public String getApplyDetailWithBLOBs(String applyFid) {
		LogUtil.info(LOGGER, "参数:{}", applyFid);
		DataTransferObject dto = new DataTransferObject();
		
		if(Check.NuNObj(applyFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.PARAM_NULL));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.PARAM_NULL));
			return dto.toJsonString();
    	}
		
        try {         	
        	ActivityApplyVo vo = activityApplyServiceImpl.getApplyDetailWithBLOBs(applyFid);
        	if (!Check.NuNObj(vo)) {
        		vo = transferActivityApplyVo(vo);
			}
        	dto.putValue("result", vo);
        	
        } catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
}
