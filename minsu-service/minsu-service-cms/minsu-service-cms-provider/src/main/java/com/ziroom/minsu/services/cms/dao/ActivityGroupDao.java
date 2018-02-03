/**
 * @FileName: ActivityGroupDao.java
 * @Package com.ziroom.minsu.services.cms.dao
 * 
 * @author yd
 * @created 2016年10月9日 上午11:45:53
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.services.cms.dto.GroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.cms.ActivityGroupEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>TODO</p>
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
@Repository("cms.activityGroupDao")
public class ActivityGroupDao {


	private String SQLID = "cms.activityGroupDao.";

	@Autowired
	@Qualifier("cms.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;


	/**
	 * 分页查询活动信息
	 * @author afi
	 * @param request
	 * @return
	 */
	public PagingResult<ActivityGroupEntity> getGroupByPage(GroupRequest request){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(request.getLimit());
		pageBounds.setPage(request.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getGroupByPage", ActivityGroupEntity.class, request, pageBounds);
	}
	
	
	/**
	 * 
	 * 保存活动组
	 *
	 * @author yd
	 * @created 2016年10月9日 上午11:48:16
	 *
	 * @param activityGroup
	 * @return
	 */
	public int saveActivityGroup(ActivityGroupEntity activityGroup){
		
		if(!Check.NuNObj(activityGroup)){
			if(Check.NuNStr(activityGroup.getFid())) activityGroup.setFid(UUIDGenerator.hexUUID());
			return this.mybatisDaoContext.save(SQLID+"insertSelective", activityGroup);
		}
		return 0;
	}


	/**
	 * 获取当前的所有的组
	 * @author afi
	 * @return
	 */
	public List<ActivityGroupEntity> getAllGroup(){

		return this.mybatisDaoContext.findAll(SQLID+"getAllGroup");
	}

	/**
	 * 通过组号获取但前的组
	 * @author afi
	 * @return
	 */
	public ActivityGroupEntity getGroupBySN(String groupSn){
		if (Check.NuNStr(groupSn)){
			return null;
		}
		Map<String,Object> par = new HashMap<>();
		par.put("groupSn",groupSn);
		return this.mybatisDaoContext.findOne(SQLID+"getGroupBySN",ActivityGroupEntity.class,par);
	}

	/**
	 * 修改活动组信息
	 *
	 * @author liujun
	 * @created 2016年10月19日
	 *
	 * @param activityGroupEntity
	 * @return
	 */
	public int updateActivityGroupEntityByFid(ActivityGroupEntity activityGroupEntity) {
		return this.mybatisDaoContext.update(SQLID+"updateActivityGiftEntityByFid", activityGroupEntity);
	}

}
