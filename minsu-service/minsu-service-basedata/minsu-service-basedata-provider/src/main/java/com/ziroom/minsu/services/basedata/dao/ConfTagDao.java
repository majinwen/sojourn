package com.ziroom.minsu.services.basedata.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.conf.ConfTagEntity;
import com.ziroom.minsu.services.basedata.dto.ConfTagRequest;
import com.ziroom.minsu.services.basedata.dto.ConfTagVo;

import java.util.List;


/**
 * <p>
 * 标签
 * </p>
 * <p/>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zl
 * @version 1.0
 * @since 1.0
 */
@Repository("basedata.confTagDao")
public class ConfTagDao {

	private String SQLID = "basedata.confTagDao.";

	@Autowired
	@Qualifier("basedata.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	

	/**
	 * 新增标签
	 * @author zl
	 * @param entity
	 * @return
	 */
	public Integer addConfTag(ConfTagEntity entity){
		if(Check.NuNObj(entity) || Check.NuNStr(entity.getFid())|| Check.NuNStr(entity.getTagName())){
			return 0;
		}		
		return mybatisDaoContext.save(SQLID+"insertSelectiveConfTag", entity);
	}
	
	/**
	 * 修改标签名称
	 * @author zl
	 * @param entity
	 * @return
	 */
	public Integer modifyTagName(ConfTagEntity entity){
		if(Check.NuNObj(entity) || Check.NuNStr(entity.getFid()) || Check.NuNStr(entity.getTagName())){
			return 0;
		}		
		return mybatisDaoContext.save(SQLID+"updateByFid", entity);
	}
	
	/**
	 * 修改标签有效状态
	 * @author zl
	 * @param entity
	 * @return
	 */
	public Integer modifyTagStatus(ConfTagEntity entity){
		if(Check.NuNObj(entity) || Check.NuNStr(entity.getFid()) || Check.NuNObj(entity.getIsValid())){
			return 0;
		}		
		return mybatisDaoContext.save(SQLID+"updateByFid", entity);
	}
	
	/**
	 * 查询标签
	 * @author zl
	 * @param entity
	 * @return
	 */
	public PagingResult<ConfTagVo> findByConfTagRequest(ConfTagRequest params){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(params.getPage());
		pageBounds.setLimit(params.getLimit());
		
		return mybatisDaoContext.findForPage(SQLID+"findByConfTagRequest", ConfTagVo.class, params.toMap(), pageBounds);
	}

	/**
	 * 查询标签(返回list集合)
	 * @author lunan
	 * @param params
	 * @return
	 */
	public List<ConfTagVo> findByConfTagRequestList(ConfTagRequest params){
		return mybatisDaoContext.findAll(SQLID+"findByConfTagRequest", ConfTagVo.class, params.toMap());
	}

}
