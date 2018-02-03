/**
 * @FileName: ActivityRecordDao.java
 * @Package com.ziroom.minsu.services.cms.dao
 * 
 * @author yd
 * @created 2016年10月9日 下午1:59:16
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ziroom.minsu.services.common.utils.SnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.cms.ActivityRecordEntity;
import com.ziroom.minsu.services.cms.dto.ActivityRecordRequest;
import com.ziroom.minsu.services.cms.entity.ActRecordVo;

/**
 * <p>活动礼品领取记录表</p>
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
@Repository("cms.activityRecordDao")
public class ActivityRecordDao {


	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(ActivityRecordDao.class);

	private String SQLID = "cms.activityRecordDao.";

	@Autowired
	@Qualifier("cms.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;


	/**
	 * 更新当前的礼品地址
	 * @author afi
	 * @create 2016年10月22日下午7:59:04
	 * @param recordFid
	 * @param name
	 * @param address
	 * @return
	 */
	public int updateAddress(String recordFid,String name,String address){
		Map<String,Object> par = new HashMap<>();
		par.put("recordFid",recordFid);
		par.put("name",name);
		par.put("address",address);
		return mybatisDaoContext.update(SQLID+"updateAddress", par);
	}

	/**
	 * 
	 * 保存活动礼物领取记录
	 *
	 * @author yd
	 * @created 2016年10月9日 下午2:01:14
	 *
	 * @return
	 */
	public int saveActivityRecord(ActivityRecordEntity activityRecord){

		if(!Check.NuNObj(activityRecord)){

			if(Check.NuNStr(activityRecord.getFid())) activityRecord.setFid(UUIDGenerator.hexUUID());
			//生成随机数
			if (Check.NuNStr(activityRecord.getRandSn())){
				activityRecord.setRandSn(SnUtil.getChar(25));
			}
			return mybatisDaoContext.save(SQLID+"insertSelective", activityRecord);
		}
		return 0;
	}


	/**
	 * 锁定并占有当前的活动信息
	 * @auyhor afi
	 * @param recordFid
	 * @param uid
	 * @param mobile
	 * @return
	 */
	public int updateLockUserRecord(String recordFid,String uid,String mobile){
		if (Check.NuNStr(recordFid) || Check.NuNStr(uid) ){
			return 0;
		}
		Map<String,Object> par = new HashMap<>();
		par.put("recordFid",recordFid);
		par.put("uid",uid);
		par.put("mobile",mobile);
		return mybatisDaoContext.update(SQLID+"updateLockUserRecord", par);

	}
	/**
	 * 
	 * 批量保存活动 礼物记录
	 *
	 * @author yd
	 * @created 2016年10月9日 下午2:20:46
	 *
	 * @param listAcRes
	 */
	public void bachSaveActivityRecord(List<ActivityRecordEntity> listAcRes){

		if(!Check.NuNCollection(listAcRes)){
			for (ActivityRecordEntity activityRecordEntity : listAcRes) {
				this.saveActivityRecord(activityRecordEntity);
			}
		}
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
	public PagingResult<ActivityRecordEntity> queryActivityRecordByPage(ActivityRecordRequest activityRecordRequest){

		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(activityRecordRequest.getLimit());
		pageBounds.setPage(activityRecordRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "queryActivityRecordByPage", ActivityRecordEntity.class, activityRecordRequest, pageBounds);
	}

	/**
	 * 
	 * 条件查询
	 *
	 * @author yd
	 * @created 2016年10月9日 下午2:24:25
	 *
	 * @param activityRecordRequest
	 * @return
	 */
	public List<ActivityRecordEntity> queryActivityRecordByCon(ActivityRecordRequest activityRecordRequest){

		return this.mybatisDaoContext.findAll(SQLID + "queryActivityRecordByPage", ActivityRecordEntity.class, activityRecordRequest);
	}



	/**
	 * 获取但前未领取的数量
	 * @author afi
	 * @param groupSn
	 * @return
	 */
	public Long getNoRecordCountByGroupSn(String groupSn){
		if (Check.NuNStr(groupSn)){
			return null;
		}
		return mybatisDaoContext.findOne(SQLID + "getNoRecordCountByGroupSn", Long.class, groupSn);
	}

	/**
	 * 当前用户已经领取的数量
	 * @author afi
	 * @param groupSn
	 * @return
	 */
	public Long getHasRecordCountByGroupSnUid(String groupSn,String uid){
		if (Check.NuNStr(groupSn) || Check.NuNStr(uid)){
			return null;
		}
		Map<String,Object> par = new HashMap<>();
		par.put("groupSn",groupSn);
		par.put("uid",uid);
		return mybatisDaoContext.findOne(SQLID + "getHasRecordCountByGroupSnUid", Long.class, par);
	}

	/**
	 * 当前电话已经领取的数量
	 * @author afi
	 * @param groupSn
	 * @return
	 */
	public Long getHasRecordCountByGroupSnMobile(String groupSn,String mobile){
		if (Check.NuNStr(groupSn) || Check.NuNStr(mobile)){
			return null;
		}
		Map<String,Object> par = new HashMap<>();
		par.put("groupSn",groupSn);
		par.put("mobile",mobile);
		return mybatisDaoContext.findOne(SQLID + "getHasRecordCountByGroupSnMobile", Long.class, par);
	}


	/**
	 * 获取记录表
	 * @author afi
	 * @param recordFid
	 * @return
	 */
	public ActivityRecordEntity getRecordByFid(String recordFid){
		if (Check.NuNStr(recordFid)){
			return null;
		}
		return this.mybatisDaoContext.findOne(SQLID + "getRecordByFid", ActivityRecordEntity.class, recordFid);
	}
	/**
	 * 通过组随机获取一条活动记录
	 * @author afi
	 * @param groupSn
	 * @return
	 */
	public ActivityRecordEntity selectOneRecordByGroupSn(String groupSn){
		if (Check.NuNStr(groupSn)){
			return null;
		}
		return this.mybatisDaoContext.findOne(SQLID + "selectOneRecordByGroupSn", ActivityRecordEntity.class, groupSn);
	}


	/**
	 * 
	 * 根据fid 修改
	 *
	 * @author yd
	 * @created 2016年10月9日 下午2:25:31
	 *
	 * @param activityRecord
	 * @return
	 */
	public  int  updateActivityRecordByFid(ActivityRecordEntity activityRecord){

		if(!Check.NuNObj(activityRecord)&&!Check.NuNStr(activityRecord.getFid())){
			return this.mybatisDaoContext.update(SQLID+"updateActivityRecordByFid", activityRecord);
		}
		return 0;
	}

	/**
	 * 
	 * 根据actSn 删除 当前活动礼物项 （未领取的）
	 *
	 * @author yd
	 * @created 2016年10月11日 上午12:05:09
	 *
	 * @param actSn
	 * @return 
	 */
	public int deleteAcRecord(String actSn){

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("actSn", actSn);
		return mybatisDaoContext.delete(SQLID+"deleteAcRecord", params);
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
	public PagingResult<ActRecordVo> queryAcRecordInfoByPage(ActivityRecordRequest activityRecordRequest){

		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(activityRecordRequest.getLimit());
		pageBounds.setPage(activityRecordRequest.getPage());
		
		return mybatisDaoContext.findForPage(SQLID + "queryAcRecordInfoByPage", ActRecordVo.class, activityRecordRequest, pageBounds);
	}

}
