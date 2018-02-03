/**
 * @FileName: ConfTagServiceImpl.java
 * @Package com.ziroom.minsu.services.basedata.service
 * 
 * @author zl
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.conf.ConfTagEntity;
import com.ziroom.minsu.services.basedata.dao.ConfTagDao;
import com.ziroom.minsu.services.basedata.dto.ConfTagRequest;
import com.ziroom.minsu.services.basedata.dto.ConfTagVo;

import java.util.List;

/**
 * <p>
 * 标签
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
@Service("basedata.confTagServiceImpl")
public class ConfTagServiceImpl {

	@Resource(name = "basedata.confTagDao")
	private ConfTagDao confTagDao;
	
	/**
	 * 新增标签
	 * @author zl
	 * @param entity
	 * @return
	 */
	public Integer addConfTag(ConfTagEntity entity){
		return confTagDao.addConfTag(entity);
	}


	/**
	 * 修改标签名称
	 * @author zl
	 * @param entity
	 * @return
	 */
	public Integer modifyTagName(ConfTagEntity entity){
		return confTagDao.modifyTagName(entity);
	}

	/**
	 * 修改标签有效状态
	 * @author zl
	 * @param entity
	 * @return
	 */
	public Integer modifyTagStatus(ConfTagEntity entity){
		return confTagDao.modifyTagStatus(entity);
	} 
	
	/**
	 * 查询标签
	 * @author zl
	 * @param entity
	 * @return
	 */
	public PagingResult<ConfTagVo> findByConfTagRequest(ConfTagRequest params){
		return confTagDao.findByConfTagRequest(params);
	}

	/**
	 * 查询标签(返回所有)
	 * @author lunan
	 * @param params
	 * @return
	 */
	public List<ConfTagVo> findByConfTagRequestList(ConfTagRequest params){
		return confTagDao.findByConfTagRequestList(params);
	}
	
	
}
