package com.ziroom.minsu.services.cms.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.cms.ActivityGroupReceiveLogEntity;
import com.ziroom.minsu.services.cms.dao.ActivityGroupReceiveLogDao;

/**
 * 
 * <p>活动记录 事务层
 * </p>
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
@Service("cms.activityGroupReceiveLogServiceImpl")
public class ActivityGroupReceiveLogServiceImpl {


	/**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityGroupReceiveLogServiceImpl.class);
    
    @Resource(name="cms.activityGroupReceiveLogDao")
    private  ActivityGroupReceiveLogDao activityGroupReceiveLogDao;
    
    /**
	 * 根据uid和活动组 查询当前用户 领取活动组的次数
	 * @param groupSn
	 * @param uid
	 * @return
	 */
	public int countGroupReceiveLogByUidAndGroupSn(String groupSn ,String uid){
		
		Long num = activityGroupReceiveLogDao.countGroupReceiveLogByUidAndGroupSn(groupSn, uid);
		if(num == null){
			throw new BusinessException("查询领取记录数异常groupSn="+groupSn+",uid="+uid);
		}
		return num.intValue();
	}
	

	/**
	 * 根据手机号和活动组 查询当前用户 领取活动组的次数
	 * @param groupSn
	 * @param uid
	 * @return
	 */
	public int countGroupReceiveLogByMobileAndGroupSn(String groupSn ,String mobile){
		Long num = activityGroupReceiveLogDao.countGroupReceiveLogByMobileAndGroupSn(groupSn, mobile);
		if(num == null){
			throw new BusinessException("查询领取记录数异常groupSn="+groupSn+",mobile="+mobile);
		}
		return num.intValue();
	}
	
	/**
	 * 插入 领取记录日志
	 * @param groupSn
	 * @param mobile
	 * @param uid
	 * @return
	 */
	public int insertActivityGroupReceiveLog(String groupSn,String mobile,String uid){
		return activityGroupReceiveLogDao.insertActivityGroupReceiveLog(groupSn,mobile,uid);
	}
	
	/**
	 * 
	 * 查询领取排名
	 *
	 * @author bushujie
	 * @created 2017年8月31日 下午5:39:19
	 *
	 * @param groupSn
	 * @param mobile
	 * @param uid
	 * @return
	 */
    public Integer getMobileRank(String groupSn,String mobile,String uid ){
    	return activityGroupReceiveLogDao.getMobileRank(groupSn, mobile, uid);
    }
}
