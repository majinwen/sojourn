/**
 * @FileName: ColumnTemplateDao.java
 * @Package com.ziroom.minsu.services.cms.dao
 * 
 * @author bushujie
 * @created 2016年11月7日 下午5:23:12
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.cms.ColumnTemplateEntity;
import com.ziroom.minsu.services.cms.dto.ColumnTemplateRequest;
import com.ziroom.minsu.services.cms.entity.ColumnTemplateVo;

/**
 * <p>专栏模板DAO</p>
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
@Repository("cms.columnTemplateDao")
public class ColumnTemplateDao {
	
	private String SQLID = "cms.columnTemplateDao.";

	@Autowired
	@Qualifier("cms.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 分页查询专栏模板列表
	 *
	 * @author bushujie
	 * @created 2016年11月7日 下午5:54:43
	 *
	 * @param templateRequest
	 * @return
	 */
	public PagingResult<ColumnTemplateVo> findColumnTemplateList(ColumnTemplateRequest templateRequest){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(templateRequest.getLimit());
        pageBounds.setPage(templateRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID+"findColumnTemplateList", ColumnTemplateVo.class, templateRequest, pageBounds);
	}
	
	/**
	 * 
	 * 插入专栏模板
	 *
	 * @author bushujie
	 * @created 2016年11月7日 下午7:59:23
	 *
	 * @param columnTemplateEntity
	 */
	public void insertColumnTemplate(ColumnTemplateEntity columnTemplateEntity){
		mybatisDaoContext.save(SQLID+"insertColumnTemplate", columnTemplateEntity);
	}
	
	
	/**
	 * 
	 * fid查询专栏模板
	 *
	 * @author bushujie
	 * @created 2016年11月8日 下午6:10:18
	 *
	 * @param tempFid
	 * @return
	 */
	public ColumnTemplateEntity getColumnTemplateEntityByFid(String tempFid){
		return mybatisDaoContext.findOneSlave(SQLID+"findColumnTemplateByFid", ColumnTemplateEntity.class,tempFid);
	}
	
	/**
	 * 
	 * 更新专栏模板
	 *
	 * @author bushujie
	 * @created 2016年11月8日 下午9:03:30
	 *
	 * @param columnTemplateEntity
	 */
	public int updateColumTemplate(ColumnTemplateEntity columnTemplateEntity){
		return mybatisDaoContext.update(SQLID+"updateColumTemplate", columnTemplateEntity);
	}
	
	/**
	 * 
	 * 查询所有注册模板
	 *
	 * @author bushujie
	 * @created 2016年11月9日 下午7:46:50
	 *
	 * @return
	 */
	public List<ColumnTemplateEntity> findAllRegTemplate(){
		return mybatisDaoContext.findAll(SQLID+"findAllRegTemplate");
	}
}
