/**
 * @FileName: GuardAreaServiceImpl.java
 * @Package com.ziroom.minsu.services.basedata.service
 * 
 * @author yd
 * @created 2016年7月5日 下午5:21:16
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.conf.GuardAreaEntity;
import com.ziroom.minsu.entity.conf.GuardAreaLogEntity;
import com.ziroom.minsu.services.basedata.dao.GuardAreaDao;
import com.ziroom.minsu.services.basedata.dao.GuardAreaLogDao;
import com.ziroom.minsu.services.basedata.dto.GuardAreaLogRequest;
import com.ziroom.minsu.services.basedata.dto.GuardAreaRequest;
import com.ziroom.minsu.valenum.msg.IsDelEnum;

/**
 * <p>区域管家业务实现</p>
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
@Service("basedata.guardAreaServiceImpl")
public class GuardAreaServiceImpl {

	/**
	 * 日志对象
	 */
	private static Logger  logger = LoggerFactory.getLogger(GuardAreaServiceImpl.class);


	@Resource(name = "basedata.guardAreaDao")
	private  GuardAreaDao guardAreaDao;
	
	@Resource(name = "basedata.guardAreaLogDao")
	private GuardAreaLogDao guardAreaLogDao;


	/**
	 * 
	 * 添加实体
	 *
	 * @author yd
	 * @created 2016年7月5日 下午3:05:17
	 *
	 * @param guardAreaEntity
	 * @return
	 */
	public int saveGuardArea(GuardAreaEntity guardAreaEntity){
		return this.guardAreaDao.saveGuardArea(guardAreaEntity);
	}

	/**
	 * 
	 * 根据fid修改
	 *
	 * @author yd
	 * @created 2016年7月5日 下午3:09:49
	 *
	 * @param guardAreaEntity
	 * @return
	 */
	public int updateGuardAreaByFid(GuardAreaEntity guardAreaEntity){
		return this.guardAreaDao.updateGuardAreaByFid(guardAreaEntity);
	}

	/**
	 * 
	 * 条件分页查询 区域管家
	 *
	 * @author yd
	 * @created 2016年7月5日 下午3:31:05
	 *
	 * @param guardAreaR
	 * @return
	 */
	public PagingResult<GuardAreaEntity> findGaurdAreaByPage(GuardAreaRequest guardAreaR) {

		return guardAreaDao.findGaurdAreaByPage(guardAreaR);
	}


	/**
	 * 
	 * 条件查询
	 * 当条件为null的时候 返回空集合
	 *
	 * @author yd
	 * @created 2016年7月5日 下午3:42:14
	 *
	 * @param guardAreaR
	 * @return
	 */
	public List<GuardAreaEntity> findGaurdAreaByCondition(GuardAreaRequest guardAreaR){
		return this.guardAreaDao.findGaurdAreaByCondition(guardAreaR);
	}
	
	  /**	
     * 
     * 根据fid 获取GuardAreaEntity
     *
     * @author yd
     * @created 2016年7月5日 下午7:12:07
     *
     * @param fid
     * @return
     */
	public GuardAreaEntity findGuardAreaByFid(String fid){
		return guardAreaDao.findGuardAreaByFid(fid);
	}

	/**
	 * 
	 * 根据区域code查询 维护管家
	 * 
	 * 算法:
	 * 1.入参 必须 有 国家 省 市 区
	 * 2. 查询区下的维护管家   有管家，取第一位管家，返回
	 * 3. 区下无管家 取市下的维护管家 有管家，取第一位管家，返回
	 * 4. 市下无管家 取省下的维护管家 有管家，取第一位管家，返回
	 * 5. 省下无管家 取国家下的维护管家，取第一位管家，返回
	 * 6. 国家下无管家 直接返回
	 * 7. 返回前 更新当前记录的更新时间
	 *
	 * @author yd
	 * @created 2016年7月5日 下午5:42:08
	 *
	 * @param areaCode
	 * @return
	 */
	public  GuardAreaEntity findGuardAreaByCode(GuardAreaRequest guardAreaR){

		GuardAreaEntity guardAreaEntity = null;
		if(Check.NuNObj(guardAreaR)){
			return guardAreaEntity;
		}
		logger.info("查询维护管家的请求参数guardAreaR={}",guardAreaR.toJsonStr());
		if(Check.NuNStr(guardAreaR.getAreaCode())||Check.NuNStr(guardAreaR.getCityCode())
				||Check.NuNStr(guardAreaR.getProvinceCode())||Check.NuNStr(guardAreaR.getNationCode())){
			return guardAreaEntity;
		}
		guardAreaR.setIsDel(IsDelEnum.NOT_DEL.getCode());
		List<GuardAreaEntity> listAreaEntities =null;

		int i = 0;
		do {
			listAreaEntities = this.findGaurdAreaByCondition(guardAreaR);
			if(!Check.NuNCollection(listAreaEntities)){
				guardAreaEntity = listAreaEntities.get(0);
			}
			if(i == 0){
				guardAreaR.setAreaCode("0");
			}
			if(i == 1){
				guardAreaR.setCityCode("0");
			}
			if(i == 2){
				guardAreaR.setProvinceCode("0");
			}
			if(i == 3){
				guardAreaR.setNationCode("0");
			}
			i++;

		} while (i<4&&Check.NuNObj(guardAreaEntity));
		
		return guardAreaEntity;
	}
	
	/**
	 * 
	 * 添加实体
	 *
	 * @author yd
	 * @created 2016年7月5日 下午3:05:17
	 *
	 * @param guardAreaLog
	 * @return
	 */
	public int saveGuardAreaLog(GuardAreaLogEntity guardAreaLog){
		return guardAreaLogDao.saveGuardAreaLog(guardAreaLog);
	}
	
	/**
	 * 
	 * 条件查询
	 * 空条件 不让查询
	 *
	 * @author yd
	 * @created 2016年7月5日 下午4:37:52
	 *
	 * @return
	 */
	public List<GuardAreaLogEntity> queryGuardAreaLogByCondition(GuardAreaLogRequest guardAreaLogRe){
		return guardAreaLogDao.queryGuardAreaLogByCondition(guardAreaLogRe);
	}
	
	/**
	 * 
	 * 条件查询
	 * 当条件为null的时候 返回空集合
	 *
	 * @author yd
	 * @created 2016年7月5日 下午3:42:14
	 *
	 * @param guardAreaR
	 * @return
	 */
	public List<GuardAreaEntity> findByPhyCondition(GuardAreaRequest guardAreaR){
		if(Check.NuNObj(guardAreaR)){
			return null;
		}
		if (Check.NuNStr(guardAreaR.getProvinceCode())
				|| Check.NuNStr(guardAreaR.getNationCode())
				|| Check.NuNStr(guardAreaR.getCityCode())
				|| Check.NuNStr(guardAreaR.getAreaCode())) {
			return null;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("nationCode", guardAreaR.getNationCode());
		paramMap.put("provinceCode", guardAreaR.getProvinceCode());
		paramMap.put("cityCode", guardAreaR.getCityCode());
		paramMap.put("areaCode", guardAreaR.getAreaCode());
		return this.guardAreaDao.findByPhy(paramMap);
	}
}
