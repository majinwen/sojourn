/**
 * @FileName: ActivityGroupReceiveLogDao.java
 * @Package com.ziroom.minsu.services.cms.dao
 * 
 * @author bushujie
 * @created 2017年5月25日 下午2:33:11
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.cms.ActivityGroupReceiveLogEntity;

/**
 * <p>活动组领取记录DAO</p>
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
@Repository("cms.activityGroupReceiveLogDao")
public class ActivityGroupReceiveLogDao {
	
	private String SQLID = "cms.activityGroupReceiveLogDao.";

	@Autowired
	@Qualifier("cms.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 插入活动组领取记录
	 *
	 * @author bushujie
	 * @created 2017年5月25日 下午2:44:11
	 *
	 * @param activityGroupReceiveLogEntity
	 */
	public int insertActivityGroupReceiveLog(ActivityGroupReceiveLogEntity activityGroupReceiveLogEntity){
		
		if(Check.NuNObj(activityGroupReceiveLogEntity)){
			throw new BusinessException("活动记录表插入对应为空");
		}
		
		if(Check.NuNStr(activityGroupReceiveLogEntity.getFid())){
			activityGroupReceiveLogEntity.setFid(UUIDGenerator.hexUUID());
		}
		
		return mybatisDaoContext.save(SQLID+"insertActivityGroupReceiveLog", activityGroupReceiveLogEntity);
	}
	
	/**
	 * 根据uid和活动组 查询当前用户 领取活动组的次数
	 * @param groupSn
	 * @param uid
	 * @return
	 */
	public Long countGroupReceiveLogByUidAndGroupSn(String groupSn ,String uid){
		
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("groupSn", groupSn);
		paramsMap.put("uid", uid);
		return this.mybatisDaoContext.count(SQLID+"countGroupReceiveLogByUidAndGroupSn", paramsMap);
	}
	
	/**
	 * 根据手机号和活动组 查询当前用户 领取活动组的次数
	 * @param groupSn
	 * @param uid
	 * @return
	 */
	public Long countGroupReceiveLogByMobileAndGroupSn(String groupSn ,String mobile){
		
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("groupSn", groupSn);
		paramsMap.put("mobile", mobile);
		return this.mybatisDaoContext.count(SQLID+"countGroupReceiveLogByMobileAndGroupSn", paramsMap);
	}
	

	/**
	 * 插入 领取记录日志
	 * @param groupSn
	 * @param mobile
	 * @param uid
	 * @return
	 */
	public int insertActivityGroupReceiveLog(String groupSn,String mobile,String uid){

		if(Check.NuNStr(groupSn)
				&&Check.NuNStr(mobile)
				&&Check.NuNStr(uid)){
			throw new BusinessException("插入领取记录异常,参数为空");
		}
		ActivityGroupReceiveLogEntity activityGroupReceiveLogEntity=new ActivityGroupReceiveLogEntity();
		activityGroupReceiveLogEntity.setFid(UUIDGenerator.hexUUID());
		activityGroupReceiveLogEntity.setGroupSn(groupSn);
		activityGroupReceiveLogEntity.setMobile(mobile);
		activityGroupReceiveLogEntity.setUid(uid);
		return insertActivityGroupReceiveLog(activityGroupReceiveLogEntity);
	}
	
    /**
     * 
     * 查询手机号领取活动组排名
     *
     * @author bushujie
     * @created 2017年8月31日 下午4:20:19
     *
     * @param groupSn
     * @param customerMobile
     * @return
     */
    public Integer getMobileRank(String groupSn,String customerMobile,String uid){
        Map<String,Object> par = new HashMap<>();
        par.put("groupSn",groupSn);
        if(!Check.NuNStr(customerMobile)){
        	par.put("mobile",customerMobile);
        }
        if(!Check.NuNStr(uid)){
        	par.put("uid",uid);
        }
        List<Integer> list=mybatisDaoContext.findAllByMaster(SQLID+"getMobileRank", Integer.class, par);
        if(Check.NuNCollection(list)){
        	return 0;
        }
        return list.get(0);
    }
}

