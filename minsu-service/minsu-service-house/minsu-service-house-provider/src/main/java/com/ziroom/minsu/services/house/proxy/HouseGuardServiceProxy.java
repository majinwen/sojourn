package com.ziroom.minsu.services.house.proxy;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseGuardLogEntity;
import com.ziroom.minsu.entity.house.HouseGuardRelEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.house.api.inner.HouseGuardService;
import com.ziroom.minsu.services.house.constant.HouseMessageConst;
import com.ziroom.minsu.services.house.dto.HouseGuardDto;
import com.ziroom.minsu.services.house.dto.HouseGuardParam;
import com.ziroom.minsu.services.house.entity.HouseGuardVo;
import com.ziroom.minsu.services.house.logic.ParamCheckLogic;
import com.ziroom.minsu.services.house.service.HouseGuardServiceImpl;
import com.ziroom.minsu.services.house.service.HouseManageServiceImpl;

/**
 * <p>房源管家关系proxy</p>
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
@Component("house.houseGuardServiceProxy")
public class HouseGuardServiceProxy implements HouseGuardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseGuardServiceProxy.class);
    
    @Resource(name="house.houseGuardServiceImpl")
    private HouseGuardServiceImpl houseGuardServiceImpl;

    @Resource(name="house.messageSource")
    private MessageSource messageSource;

    @Resource(name="house.paramCheckLogic")
    private ParamCheckLogic paramCheckLogic;
    
    @Resource(name = "house.houseManageServiceImpl")
	private HouseManageServiceImpl houseManageServiceImpl;

	@Override
	public String searchHouseGuardList(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		HouseGuardDto houseGuardDto = JsonEntityTransform.json2Object(paramJson, HouseGuardDto.class);
        DataTransferObject dto = new DataTransferObject();
        
        if(Check.NuNObj(houseGuardDto)){
        	dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
        	dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
        	LogUtil.error(LOGGER, dto.toJsonString());
        	return dto.toJsonString();
        }

        try {
        	PagingResult<HouseGuardVo> pageResult = houseGuardServiceImpl.findHouseGuardList(houseGuardDto);
        	dto.putValue("total", pageResult.getTotal());
        	dto.putValue("rows", pageResult.getRows());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{},params:{}", e, paramJson);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(e.getMessage());
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
	}

	@Override
	public String searchHouseGuardDetail(String houseBaseFid) {
		LogUtil.info(LOGGER, "houseBaseFid:{}", houseBaseFid);
        DataTransferObject dto = new DataTransferObject();
        
        if(Check.NuNStr(houseBaseFid)){
        	dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
        	dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
        	LogUtil.error(LOGGER, dto.toJsonString());
        	return dto.toJsonString();
        }

        try {
        	HouseGuardVo vo = houseGuardServiceImpl.findHouseGuardVoByHouseBaseFid(houseBaseFid);
        	dto.putValue("obj", vo);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{},houseBaseFid:{}", e, houseBaseFid);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(e.getMessage());
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
	}

	@Override
	public String searchHouseGuardLogList(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		HouseGuardDto houseGuardDto = JsonEntityTransform.json2Object(paramJson, HouseGuardDto.class);
        DataTransferObject dto = new DataTransferObject();
        
        if(Check.NuNObj(houseGuardDto)){
        	dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
        	dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
        	LogUtil.error(LOGGER, dto.toJsonString());
        	return dto.toJsonString();
        }
        
        if(Check.NuNStr(houseGuardDto.getHouseGuardFid())){
        	dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
        	dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_GUARD_FID_NULL));
        	LogUtil.error(LOGGER, dto.toJsonString());
        	return dto.toJsonString();
        }

        try {
        	PagingResult<HouseGuardLogEntity> pageResult = houseGuardServiceImpl.findHouseGuardLogList(houseGuardDto);
        	dto.putValue("total", pageResult.getTotal());
        	dto.putValue("rows", pageResult.getRows());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{},params:{}", e, paramJson);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(e.getMessage());
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
	}

	@Override
	public String batchMergeHouseGuardRel(String paramJson) {
		LogUtil.info(LOGGER, "batchMergeHouseGuardRel参数:{}", paramJson);
		HouseGuardParam houseGuardParam = JsonEntityTransform.json2Object(paramJson, HouseGuardParam.class);
        DataTransferObject dto = new DataTransferObject();
        
        if(Check.NuNObj(houseGuardParam)){
        	dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
        	dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
        	LogUtil.error(LOGGER, dto.toJsonString());
        	return dto.toJsonString();
        }
        
        if(Check.NuNCollection(houseGuardParam.getListGuard())){
        	dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
        	dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
        	LogUtil.error(LOGGER, dto.toJsonString());
        	return dto.toJsonString();
        }
        
        for (HouseGuardRelEntity guardRelEntity : houseGuardParam.getListGuard()) {
        	if(Check.NuNStr(guardRelEntity.getHouseFid())){
        		dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
        		dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
        		LogUtil.error(LOGGER, dto.toJsonString());
        		return dto.toJsonString();
        	}
        }

        try {
        	for (HouseGuardRelEntity guardRelEntity : houseGuardParam.getListGuard()) {
        		HouseGuardRelEntity houseGuardRel = houseGuardServiceImpl.findHouseGuardRelByHouseBaseFid(guardRelEntity.getHouseFid());
        		if(Check.NuNObj(houseGuardRel)){
        			houseGuardRel = new HouseGuardRelEntity();
        			houseGuardRel.setHouseFid(guardRelEntity.getHouseFid());
        		}
        		BeanUtils.copyProperties(houseGuardParam, houseGuardRel);
        		houseGuardServiceImpl.mergeHouseGuardRel(houseGuardRel,houseGuardParam.getHouseChannel());
        		
        		// modified by liujun 2017-02-23 地推管家岗位已取消,维护管家变更为运营专员
        		/*if (!Check.NuNStr(houseGuardRel.getEmpPushCode())) {
        			//保存房源渠道信息
        			HouseBaseMsgEntity houseBaseMsg = houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseGuardRel.getHouseFid());
        			if (Check.NuNObj(houseBaseMsg.getHouseChannel()) || HouseChannelEnum.CH_DITUI.getCode()!=houseBaseMsg.getHouseChannel()) {
        				houseBaseMsg.setHouseChannel(HouseChannelEnum.CH_DITUI.getCode());
        				houseManageServiceImpl.updateHouseBaseMsg(houseBaseMsg);
					}
				}*/
			}
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{},params:{}", e, paramJson);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(e.getMessage());
        }
        LogUtil.info(LOGGER, "batchMergeHouseGuardRel结果:{}", dto.toJsonString());
        return dto.toJsonString();
	}
	
	/**
	 * 
	 * 根据逻辑id查询房源维护管家关系
	 *
	 * @author liujun
	 * @created 2016年7月5日
	 *
	 * @param fid
	 * @return
	 */
	@Override
	public String findHouseGuardRelByHouseBaseFid(String houseBaseFid){
		
		DataTransferObject dto = new DataTransferObject();
		
		if(Check.NuNStr(houseBaseFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("查询房源维护管家,房源fid不存在");
			return dto.toJsonString();
		}
		HouseGuardRelEntity houseGuardRel = this.houseGuardServiceImpl.findHouseGuardRelByHouseBaseFid(houseBaseFid);
		dto.putValue("houseGuardRel", houseGuardRel);
		
		return dto.toJsonString();
	}
	
	/**
	 * 
	 * 根据逻辑id查询房源维护管家关系
	 *
	 * @author liujun
	 * @created 2016年7月5日
	 *
	 * @param fid
	 * @return
	 */
	@Override
	public String saveHouseGuardRel(String paramJson){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(paramJson)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		HouseGuardRelEntity houseGuardRel = JsonEntityTransform.json2Object(paramJson, HouseGuardRelEntity.class);
		
		this.houseGuardServiceImpl.insertHouseGuardRel(houseGuardRel);
		return dto.toJsonString();
	}
	
	
	
	
	/**
	 * 更新维护管家
	 * @author liyingjie
	 * @param paramStr
	 * @return
	 */
	@Override
	public String updateHouseGuardByHouseFid(String paramStr) {
		LogUtil.info(LOGGER, "paramStr:{}", paramStr);
        DataTransferObject dto = new DataTransferObject();
        
        if(Check.NuNStr(paramStr)){
        	dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
        	dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
        	LogUtil.error(LOGGER, dto.toJsonString());
        	return dto.toJsonString();
        }

        try {
        	HouseGuardRelEntity houseGuard = JsonEntityTransform.json2Object(paramStr, HouseGuardRelEntity.class);
        	
        	if(Check.NuNObj(houseGuard)){
            	dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            	dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
            	LogUtil.error(LOGGER, dto.toJsonString());
            	return dto.toJsonString();
            }
        	if(Check.NuNStr(houseGuard.getHouseFid())){
            	dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            	dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
            	LogUtil.error(LOGGER, dto.toJsonString());
            	return dto.toJsonString();
            }

        	houseGuardServiceImpl.updateHouseGuardByHouseBaseFid(houseGuard);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{},paramStr:{}", e, paramStr);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(e.getMessage());
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
	}
	
	/**
	 * 
	 * 条件查询 房源和维护管家  内联
	 *
	 * @author yd
	 * @created 2016年7月12日 下午7:51:39
	 *
	 * @param houseGuardDto
	 * @return
	 */
	@Override
	public String findHouseGuardByCondition(String paramJson){
		
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		HouseGuardDto houseGuardDto = JsonEntityTransform.json2Object(paramJson, HouseGuardDto.class);
        DataTransferObject dto = new DataTransferObject();
        
        if(Check.NuNObj(houseGuardDto)){
        	dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
        	dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
        	LogUtil.error(LOGGER, dto.toJsonString());
        	return dto.toJsonString();
        }
        
        List<HouseGuardVo> list = houseGuardServiceImpl.findHouseGuardByCondition(houseGuardDto);
        dto.putValue("list", list);
        dto.putValue("total",0);
        if(!Check.NuNCollection(list)){
        	 dto.putValue("total", list.size());
        }
        
        return dto.toJsonString();
	}


	/**
	 * 根据管家姓名查询，房源维护管家关联表
	 * @author lisc
	 * @param paramsJson
	 * @return
	 */
	@Override
	public String findHouseGuardRelByCondition(String paramsJson) {
		LogUtil.info(LOGGER, "参数:{}", paramsJson);
		HouseGuardRelEntity houseGuardRelEntity = JsonEntityTransform.json2Object(paramsJson, HouseGuardRelEntity.class);

		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(houseGuardRelEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		List<HouseGuardRelEntity> houseGuardRels = houseGuardServiceImpl.findHouseGuardRelByCondition(houseGuardRelEntity);
		dto.putValue("list", houseGuardRels);
		return dto.toJsonString();
	}
}
