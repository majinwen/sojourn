/**
 * @FileName: LandlordStaticsServiceImpl.java
 * @Package com.ziroom.minsu.services.customer.service
 * 
 * @author liyingjie
 * @created 2016年4月22日 下午5:07:26
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.service;


import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import com.ziroom.minsu.services.customer.dto.CustomerExtDto;
import com.ziroom.minsu.services.customer.entity.CustomerExt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity;
import com.ziroom.minsu.entity.customer.LandlordStatisticsEntity;
import com.ziroom.minsu.services.customer.dao.CustomerBaseMsgDao;
import com.ziroom.minsu.services.customer.dao.CustomerBaseMsgExtDao;
import com.ziroom.minsu.services.customer.dao.LandlordStaticsDao;
import com.ziroom.minsu.services.customer.dto.CustomerBaseMsgDto;
import com.ziroom.minsu.services.customer.entity.CustomerDetailImageVo;
import com.ziroom.minsu.services.customer.entity.CustomerDetailVo;

/**
 * <p>TODO</p>
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
@Service("customer.landlordStaticsServiceImpl")
public class LandlordStaticsServiceImpl {
	private static Logger logger = LoggerFactory.getLogger(LandlordStaticsDao.class);
	@Resource(name="customer.landlordStaticsDao")
	private LandlordStaticsDao landlordStaticsDao;
	
	/**
	 * 更新房东行为统计信息
	 * @author liyingjie
	 * @created 2016年7月29日 下午4:15:46
	 * @param customerBaseMsgEntity
	 * @return
	 */
	public int staticsUpdateLanActAssociationImp(LandlordStatisticsEntity landlordStatistics){
		if(Check.NuNObj(landlordStatistics)){
    		LogUtil.info(logger, "staticsUpdateLanActAssociationImp 参数对象为空");
			throw new BusinessException("staticsUpdateLanActAssociationImp 参数对象为空");
    	}
    	if(Check.NuNStr(landlordStatistics.getLandlordUid())){
    		LogUtil.info(logger, "staticsUpdateLanActAssociationImp landlordUid为空");
			throw new BusinessException("staticsUpdateLanActAssociationImp landlordUid为空");
    	}
    	
    	return landlordStaticsDao.staticsUpdateLanActAssociation(landlordStatistics);
    	
	}

	/**
	 * 新增房东行为统计信息
	 * @author liyingjie
	 * @created 2016年7月29日 下午4:15:46
	 * @param customerBaseMsgEntity
	 * @return
	 */
	public void staticsInsertLanActAssociationImp(LandlordStatisticsEntity landlordStatistics){
		if(Check.NuNObj(landlordStatistics)){
    		LogUtil.info(logger, "staticsInsertLanActAssociationImp 参数对象为空");
			throw new BusinessException("staticsInsertLanActAssociationImp 参数对象为空");
    	}
    	if(Check.NuNStr(landlordStatistics.getLandlordUid())){
    		LogUtil.info(logger, "staticsInsertLanActAssociationImp landlordUid为空");
			throw new BusinessException("staticsInsertLanActAssociationImp landlordUid为空");
    	}
    	
    	if(Check.NuNStr(landlordStatistics.getFid())){
    		landlordStatistics.setFid(UUIDGenerator.hexUUID());
    	}
        landlordStaticsDao.staticsInsertLanActAssociation(landlordStatistics);
	}
    
	/**
	 * 查询 房东统计信息 
	 * @author liyingjie
	 * @created 2016年7月29日 下午4:15:46
	 * @param customerBaseMsgEntity
	 * @return
	 */
	public LandlordStatisticsEntity findLandlordStatisticsByUid(String landlordUid){
		
    	if(Check.NuNStr(landlordUid)){
    		LogUtil.info(logger, "findLandlordStatisticsByUid landlordUid为空");
			throw new BusinessException("findLandlordStatisticsByUid landlordUid为空");
    	}
        return landlordStaticsDao.findLandlordStatisticsByUid(landlordUid);
	}
	
}
