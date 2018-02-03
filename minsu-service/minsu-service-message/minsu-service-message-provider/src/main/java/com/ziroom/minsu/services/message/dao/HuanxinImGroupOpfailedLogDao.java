/**
 * @FileName: HuanxinImGroupOpfailedLogDao.java
 * @Package com.ziroom.minsu.services.message.dao
 * 
 * @author yd
 * @created 2017年7月28日 下午1:52:39
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.HuanxinImGroupOpfailedLogEntity;
import com.ziroom.minsu.services.message.dto.GroupOpfailedLogDto;

/**
 * <p>组操作失败记录</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */

@Repository("message.huanxinImGroupOpfailedLogDao")
public class HuanxinImGroupOpfailedLogDao extends BaseEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -4680088107138868598L;
	

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(HuanxinImGroupOpfailedLogDao.class);

	private String SQLID = "message.huanxinImGroupOpfailedLog.";
	
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
	public int saveHuanxinImGroupOpfailedLog(HuanxinImGroupOpfailedLogEntity huanxinImGroupOpfailedLog){
		return mybatisDaoContext.save(SQLID+"insertSelective", huanxinImGroupOpfailedLog);
	}
	
	/**
	 * 
	 * 修改
	 *
	 * @author yd
	 * @created 2017年7月28日 下午1:50:56
	 *
	 * @param huanxinImGroupMember
	 * @return
	 */
	public int updateHuanxinImGroupOpfailedLog(HuanxinImGroupOpfailedLogEntity huanxinImGroupOpfailedLog){
		return mybatisDaoContext.update(SQLID+"updateHuanxinImGroupOpfailedLog", huanxinImGroupOpfailedLog);
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
	public PagingResult<HuanxinImGroupOpfailedLogEntity>  queryGroupOpfailedByPage(GroupOpfailedLogDto groupOpfailedLogDto){
		if(Check.NuNObj(groupOpfailedLogDto)){
			LogUtil.error(logger, "groupOpfailedLogDto  is null");
		}
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(groupOpfailedLogDto.getLimit());
		pageBounds.setPage(groupOpfailedLogDto.getPage());
		return mybatisDaoContext.findForPage(SQLID + "queryGroupOpfailedByPage", HuanxinImGroupOpfailedLogEntity.class, groupOpfailedLogDto, pageBounds);
	}
}
