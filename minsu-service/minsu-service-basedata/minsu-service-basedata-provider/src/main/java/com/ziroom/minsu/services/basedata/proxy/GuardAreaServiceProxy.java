/**
 * @FileName: GuardAreaServiceProxy.java
 * @Package com.ziroom.minsu.services.basedata.proxy
 * 
 * @author yd
 * @created 2016年7月5日 下午6:29:51
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.proxy;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.GuardAreaEntity;
import com.ziroom.minsu.entity.conf.GuardAreaLogEntity;
import com.ziroom.minsu.services.basedata.api.inner.GuardAreaService;
import com.ziroom.minsu.services.basedata.dto.GuardAreaLogRequest;
import com.ziroom.minsu.services.basedata.dto.GuardAreaRequest;
import com.ziroom.minsu.services.basedata.service.GuardAreaServiceImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.valenum.msg.IsDelEnum;

/**
 * <p>区域管家 代理层业务封装</p>
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
@Component("basedata.guardAreaServiceProxy")
public class GuardAreaServiceProxy  implements GuardAreaService{

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(GuardAreaServiceProxy.class);

	@Resource(name = "basedata.guardAreaServiceImpl")
	private GuardAreaServiceImpl guardAreaServiceImpl;


	@Resource(name = "basedata.messageSource")
	private MessageSource messageSource;


	/**
	 * 
	 *  保存实体
	 *  1.保存前提条件：该管家不在该区域
	 *
	 * @author yd
	 * @created 2016年7月5日 下午6:37:19
	 *
	 * @param guardAreaEntity
	 * @return
	 */
	@Override
	public String saveGuardArea(String guardAreaEntity) {
		DataTransferObject dto = new DataTransferObject();

		GuardAreaEntity guardArea = JsonEntityTransform.json2Object(guardAreaEntity, GuardAreaEntity.class);

		if(Check.NuNObj(guardArea)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("实体不存在");
			return dto.toJsonString();
		}
		
		if(Check.NuNStr(guardArea.getNationCode())||Check.NuNStr(guardArea.getCreateFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("国家不存在");
			return dto.toJsonString();
		}
		if(Check.NuNStr(guardArea.getEmpCode())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请选择管家");
			return dto.toJsonString();
		}
		//校验待修改的区域是否已经存在此管家
		GuardAreaRequest guardAreaR = new GuardAreaRequest();
		guardAreaR.setAreaCode(guardArea.getAreaCode());
		guardAreaR.setCityCode(guardArea.getCityCode());
		guardAreaR.setProvinceCode(guardArea.getProvinceCode());
		guardAreaR.setNationCode(guardArea.getNationCode());
		guardAreaR.setEmpCode(guardArea.getEmpCode());
		guardAreaR.setIsDel(IsDelEnum.NOT_DEL.getCode());
	    List<GuardAreaEntity>	list = guardAreaServiceImpl.findGaurdAreaByCondition(guardAreaR);
	    if(!Check.NuNCollection(list)){
	    	dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("当前管家已经在该区域");
			return dto.toJsonString();
	    }
		LogUtil.info(logger, "当前待保存的实体guardArea={}", guardArea.toJsonStr());

		dto.putValue("result", this.guardAreaServiceImpl.saveGuardArea(guardArea));
		return dto.toJsonString();
	}

	/**
	 * 
	 * 按 fid 修改 区域管家实体
	 * 说明：
	 * 1.更新只是更新区域管家的 区域信息 故此处需要保存 日志，但是删除的时候，就不用保存日志
	 * 2.更新管家区域时候，要校验此管家是否已经在待修改的区域，如果有，则不让修改
	 * 
	 *
	 * @author yd
	 * @created 2016年7月5日 下午6:39:19
	 * @param guardAreaEntity
	 * @return
	 */
	@Override
	public String updateGuardAreaByFid(String guardAreaEntity,String logCreaterFid) {
		DataTransferObject dto = new DataTransferObject();

		GuardAreaEntity guardArea = JsonEntityTransform.json2Object(guardAreaEntity, GuardAreaEntity.class);

		if(Check.NuNObj(guardArea)||Check.NuNStr(guardArea.getFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("实体不存在");
			return dto.toJsonString();
		}
		LogUtil.info(logger, "当前待更新的实体guardArea={}", guardArea.toJsonStr());
		
		GuardAreaLogEntity guardAreaLog = null;
		if(Check.NuNObj(guardArea.getIsDel())&&!Check.NuNStr(logCreaterFid)){
			//校验待修改的区域是否已经存在此管家
			GuardAreaRequest guardAreaR = new GuardAreaRequest();
			guardAreaR.setAreaCode(guardArea.getAreaCode());
			guardAreaR.setCityCode(guardArea.getCityCode());
			guardAreaR.setProvinceCode(guardArea.getProvinceCode());
			guardAreaR.setNationCode(guardArea.getNationCode());
			guardAreaR.setEmpCode(guardArea.getEmpCode());
		    List<GuardAreaEntity>	list = guardAreaServiceImpl.findGaurdAreaByCondition(guardAreaR);
		    
		    if(!Check.NuNCollection(list)){
		    	dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("当前管家已经在该区域");
				return dto.toJsonString();
		    }
			GuardAreaEntity cur  = this.guardAreaServiceImpl.findGuardAreaByFid(guardArea.getFid());
			if(!Check.NuNObj(cur)){
				guardAreaLog = new GuardAreaLogEntity();
				guardAreaLog.setCreateDate(new Date());
				guardAreaLog.setCreateFid(logCreaterFid);
				guardAreaLog.setFid(UUIDGenerator.hexUUID());
				guardAreaLog.setGuardCode(cur.getEmpCode());
				guardAreaLog.setGuardName(cur.getEmpName());
				guardAreaLog.setGuradAreaFid(cur.getFid());
				guardAreaLog.setOldAreaCode(cur.getAreaCode());
				guardAreaLog.setOldCityCode(cur.getCityCode());
		        guardAreaLog.setOldNationCode(cur.getNationCode());
		        guardAreaLog.setOldProvinceCode(cur.getProvinceCode());
			}
		}
		
		int result =this.guardAreaServiceImpl.updateGuardAreaByFid(guardArea);
		
		if(result>0&&!Check.NuNObj(guardAreaLog)){
			this.guardAreaServiceImpl.saveGuardAreaLog(guardAreaLog);
		}
		dto.putValue("result", result);
		return dto.toJsonString();
	}

	/**
	 * 
	 * 分页查询区域管家
	 *
	 * @author yd
	 * @created 2016年7月5日 下午6:41:20
	 *
	 * @param guardAreaR
	 * @return
	 */
	@Override
	public String findGaurdAreaByPage(String guardAreaR) {
		
		DataTransferObject dto = new DataTransferObject();
		GuardAreaRequest guardAreaRequest = JsonEntityTransform.json2Object(guardAreaR, GuardAreaRequest.class);
		PagingResult<GuardAreaEntity> pageResult = this.guardAreaServiceImpl.findGaurdAreaByPage(guardAreaRequest);
		
		dto.putValue("listGuardArea", pageResult.getRows());
		dto.putValue("count", pageResult.getTotal());
		
		return dto.toJsonString();
	}

	/**
	 * 
	 * 条件查询 区域管家
	 *
	 * @author yd
	 * @created 2016年7月5日 下午6:43:27
	 *
	 * @param guardAreaR
	 * @return
	 */
	@Override
	public String findGaurdAreaByCondition(String guardAreaR) {
		DataTransferObject dto = new DataTransferObject();
		GuardAreaRequest guardAreaRequest = JsonEntityTransform.json2Object(guardAreaR, GuardAreaRequest.class);
		
		List<GuardAreaEntity> listAreaEntities = this.guardAreaServiceImpl.findGaurdAreaByCondition(guardAreaRequest);
		dto.putValue("listGuardArea",listAreaEntities);
		return dto.toJsonString();
	}

	/**
	 * 
	 * 录入房源 查询维护管家
	 *  算法:
	 * 1.入参 必须 有 国家 省 市 区
	 * 2. 查询区下的维护管家   有管家，取第一位管家，返回
	 * 3. 区下无管家 取市下的维护管家 有管家，取第一位管家，返回
	 * 4. 市下无管家 取省下的维护管家 有管家，取第一位管家，返回
	 * 5. 省下无管家 取国家下的维护管家，取第一位管家，返回
	 * 6. 国家下无管家 直接返回
	 * 7. 返回前 更新当前记录的更新时间
	 *
	 *
	 * @author yd
	 * @created 2016年7月5日 下午6:44:03
	 *
	 * @param guardAreaR
	 * @return
	 */
	@Override
	public String findGuardAreaByCode(String guardAreaR) {
		
		
		DataTransferObject dto = new DataTransferObject();
		GuardAreaRequest guardAreaRequest = JsonEntityTransform.json2Object(guardAreaR, GuardAreaRequest.class);
		
		
		if(Check.NuNObj(guardAreaRequest)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}
		logger.info("查询维护管家的请求参数guardAreaRequest={}",guardAreaRequest.toJsonStr());
		if(Check.NuNStr(guardAreaRequest.getAreaCode())||Check.NuNStr(guardAreaRequest.getCityCode())
				||Check.NuNStr(guardAreaRequest.getProvinceCode())||Check.NuNStr(guardAreaRequest.getNationCode())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}
		Long t1 = System.currentTimeMillis();
		GuardAreaEntity guardAreaEntity = this.guardAreaServiceImpl.findGuardAreaByCode(guardAreaRequest);
		Long t2 = System.currentTimeMillis();
		
		LogUtil.info(logger, "查询区域管家返回结果guardAreaEntity={}，保存用时t2-t1={}ms", guardAreaEntity,t2-t1);
		if(!Check.NuNObj(guardAreaEntity)){
			t1 = System.currentTimeMillis();
			guardAreaEntity.setLastModifyDate(new Date());
			this.guardAreaServiceImpl.updateGuardAreaByFid(guardAreaEntity);
			t2 = System.currentTimeMillis();
			
			LogUtil.info(logger, "查询区域管家返回结果guardAreaEntity={}，更新用时t2-t1={}ms", guardAreaEntity.toJsonStr(),t2-t1);
		}
		dto.putValue("guardArea", guardAreaEntity);
		
		return dto.toJsonString();
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
	@Override
	public String findGuardAreaByFid(String fid){
		
		DataTransferObject dto = new DataTransferObject();
		
		if(Check.NuNStr(fid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}
		
		GuardAreaEntity guardAreaEntity  = this.guardAreaServiceImpl.findGuardAreaByFid(fid);
		dto.putValue("guardArea", guardAreaEntity);
		return dto.toJsonString();
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
	@Override
	public String queryGuardAreaLogByCondition(String guardAreaLogRe){
		
		DataTransferObject dto = new DataTransferObject();
		GuardAreaLogRequest guardAreaLRequest = JsonEntityTransform.json2Object(guardAreaLogRe, GuardAreaLogRequest.class);
		
		if(Check.NuNObj(guardAreaLogRe)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}
		
		List<GuardAreaLogEntity> listAreaEntities = this.guardAreaServiceImpl.queryGuardAreaLogByCondition(guardAreaLRequest);
		dto.putValue("listAreaEntities",listAreaEntities);
		return dto.toJsonString();
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
	@Override
	public String findByPhyCondition(String guardAreaLogRe){
		
		DataTransferObject dto = new DataTransferObject();
		GuardAreaRequest guardAreaLRequest = JsonEntityTransform.json2Object(guardAreaLogRe, GuardAreaRequest.class);
		
		if(Check.NuNObj(guardAreaLogRe)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}
		
		List<GuardAreaEntity> listAreaEntities = this.guardAreaServiceImpl.findByPhyCondition(guardAreaLRequest);
		dto.putValue("list",listAreaEntities);
		return dto.toJsonString();
	}
}
