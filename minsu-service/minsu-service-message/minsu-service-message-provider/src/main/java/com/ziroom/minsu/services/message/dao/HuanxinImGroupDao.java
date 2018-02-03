/**
 * @FileName: HuanxinImGroupDao.java
 * @Package com.ziroom.minsu.services.message.dao
 * 
 * @author yd
 * @created 2017年7月28日 上午11:21:50
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.HuanxinImGroupEntity;
import com.ziroom.minsu.entity.message.MsgBaseEntity;
import com.ziroom.minsu.services.message.dto.GroupDto;
import com.ziroom.minsu.services.message.entity.GroupVo;

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

@Repository("message.huanxinImGroupDao")
public class HuanxinImGroupDao {
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(HuanxinImGroupDao.class);

	private String SQLID = "message.huanxinImGroupDao.";
	
	
	@Autowired
	@Qualifier("message.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	/**
	 * 
	 * 保存
	 *
	 * @author yd
	 * @created 2016年9月9日 下午3:20:18
	 *
	 * @param imRecord
	 * @return
	 * 
	 */
	public int saveHuanxinImGroup(HuanxinImGroupEntity huanxinImGroup){
		return mybatisDaoContext.save(SQLID+"insertSelective", huanxinImGroup);
	}
	
	/**
	 * 
	 * 修改
	 *
	 * @author yd
	 * @created 2017年7月28日 上午11:29:01
	 *
	 * @param huanxinImGroup
	 * @return
	 */
	public int updateHuanxinImGroup(HuanxinImGroupEntity huanxinImGroup){
		return mybatisDaoContext.update(SQLID+"updateByGroupId", huanxinImGroup);
	}
	
	/**
	 * 
	 * 分页查询群组信息
	 *
	 * @author yd
	 * @created 2017年8月1日 下午4:16:13
	 *
	 * @param groupDto
	 * @return
	 */
	public PagingResult<GroupVo>  queryGroupByPage(GroupDto groupDto){
		if(Check.NuNObj(groupDto)){
			LogUtil.error(logger, "groupDto  is null");
		}
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(groupDto.getLimit());
		pageBounds.setPage(groupDto.getPage());
		return mybatisDaoContext.findForPage(SQLID + "queryGroupByPage", GroupVo.class, groupDto, pageBounds);
	}
	
	/**
	 * 
	 * 查询默认群组
	 *
	 * @author yd
	 * @created 2017年8月3日 下午5:59:18
	 *
	 * @param projectBid
	 * @return
	 */
	public HuanxinImGroupEntity queryDefaultGroupByProBid(String projectBid){
		return mybatisDaoContext.findOne(SQLID+"queryDefaultGroupByProBid", HuanxinImGroupEntity.class, projectBid);
	}
	
	/**
	 * 
	 * 查询默认群组
	 *
	 * @author yd
	 * @created 2017年8月3日 下午5:59:18
	 *
	 * @param projectBid
	 * @return
	 */
	public HuanxinImGroupEntity queryGroupByGroupId(String groupId){
		return mybatisDaoContext.findOne(SQLID+"queryGroupByGroupId", HuanxinImGroupEntity.class, groupId);
	}
}
