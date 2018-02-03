/**
 * @FileName: HouseBusinessServiceProxy.java
 * @Package com.ziroom.minsu.services.house.proxy
 * 
 * @author bushujie
 * @created 2016年7月6日 下午10:31:04
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.proxy;


import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseBusinessMsgExtEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.house.api.inner.HouseBusinessService;
import com.ziroom.minsu.services.house.dto.HouseBusinessDto;
import com.ziroom.minsu.services.house.dto.HouseBusinessInputDto;
import com.ziroom.minsu.services.house.dto.HouseBusinessMsgExtDto;
import com.ziroom.minsu.services.house.entity.HouseBusinessListVo;
import com.ziroom.minsu.services.house.service.HouseBusinessServiceImpl;

/**
 * <p>房源商机接口代理层</p>
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
@Component("house.houseBusinessServiceProxy")
public class HouseBusinessServiceProxy implements HouseBusinessService{
	/**
	 * 日志工具
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseIssueServiceProxy.class);
	
	@Resource(name="house.houseBusinessServiceImpl")
	private HouseBusinessServiceImpl houseBusinessServiceImpl;
	
	@Resource(name="house.messageSource")
	private MessageSource messageSource;

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseBusinessService#houseBusinessList(java.lang.String)
	 */
	@Override
	public String houseBusinessList(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		HouseBusinessDto houseBusinessDto=JsonEntityTransform.json2Object(paramJson, HouseBusinessDto.class);
	    DataTransferObject dto=new DataTransferObject();
        try {
        	PagingResult<HouseBusinessListVo> pagingResult=houseBusinessServiceImpl.findBusinessList(houseBusinessDto);
            dto.putValue("list", pagingResult.getRows());
            dto.putValue("total", pagingResult.getTotal());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "返回结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseBusinessService#insertHouseBusiness(java.lang.String)
	 */
	@Override
	public String insertHouseBusiness(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		HouseBusinessInputDto houseBusinessInputDto=JsonEntityTransform.json2Object(paramJson, HouseBusinessInputDto.class);
		DataTransferObject dto=new DataTransferObject();
		try{
			houseBusinessServiceImpl.insertHouseBusiness(houseBusinessInputDto);
		}catch(Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseBusinessService#findDtGuardCodeByLandlord(java.lang.String)
	 */
	@Override
	public String findDtGuardCodeByLandlord(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		HouseBusinessDto houseBusinessDto=JsonEntityTransform.json2Object(paramJson, HouseBusinessDto.class);
	    DataTransferObject dto=new DataTransferObject();
        try {
        	String dtGuardCode=houseBusinessServiceImpl.findDtGuardCodeByLandlord(houseBusinessDto.getLandlordMobile());
            dto.putValue("dtGuardCode", dtGuardCode);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "返回结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseBusinessService#findHouseBusinessVoByFid(java.lang.String)
	 */
	@Override
	public String findHouseBusinessDetailByFid(String businessFid) {
		LogUtil.info(LOGGER, "参数:{}", businessFid);
	    DataTransferObject dto=new DataTransferObject();
        try {
        	HouseBusinessInputDto houseBusinessInputDto=houseBusinessServiceImpl.findHouseBusinessDetailByFid(businessFid);
            dto.putValue("businessInfo", houseBusinessInputDto);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "返回结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseBusinessService#updateHouseBusiness(java.lang.String)
	 */
	@Override
	public String updateHouseBusiness(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		HouseBusinessInputDto houseBusinessInputDto=JsonEntityTransform.json2Object(paramJson, HouseBusinessInputDto.class);
		DataTransferObject dto=new DataTransferObject();
		try{
			houseBusinessServiceImpl.updateHouseBusiness(houseBusinessInputDto);
		}catch(Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseBusinessService#delHouseBusiness(java.lang.String)
	 */
	@Override
	public String delHouseBusiness(String businessFid) {
		LogUtil.info(LOGGER, "参数:{}", businessFid);
		DataTransferObject dto=new DataTransferObject();
		try{
			houseBusinessServiceImpl.delHouseBusiness(businessFid);
		}catch(Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	
	 /**
     * 
     * 条件查询 商机扩展信息
     * 
     * 查询对象不能为null
     *
     * @author yd
     * @created 2016年7月9日 下午2:27:25
     *
     * @param houseBusinessMsgExtDto
     * @return
     */
	@Override
    public String findHouseBusExtByCondition(String houseBusinessMsgExtDto){
    	
    	HouseBusinessMsgExtDto houseBusinessMsgExt=JsonEntityTransform.json2Object(houseBusinessMsgExtDto, HouseBusinessMsgExtDto.class);
	    DataTransferObject dto=new DataTransferObject();
	    
	    List<HouseBusinessMsgExtEntity> listExtEntities = this.houseBusinessServiceImpl.findHouseBusExtByCondition(houseBusinessMsgExt);
    	
	    dto.putValue("listExtEntities", listExtEntities);
	    return dto.toJsonString();
    }

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseBusinessService#findHouseCountByUid(java.lang.String)
	 */
	@Override
	public String findHouseCountByUid(String uid) {
		DataTransferObject dto=new DataTransferObject();
		int count=houseBusinessServiceImpl.findHouseCountByUid(uid);
		dto.putValue("count", count);
		return dto.toJsonString();
	}
}
