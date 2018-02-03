package com.ziroom.minsu.services.basedata.dao;

import com.ziroom.minsu.services.basedata.entity.StaticResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.base.StaticResourceEntity;
import com.ziroom.minsu.services.basedata.dto.StaticResourceRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <p>静态资源到</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@Repository("basedata.staticResourceDao")
public class StaticResourceDao {

	private String SQLID = "basedata.staticResourceDao.";

	@Autowired
	@Qualifier("basedata.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 
	 * 插入静态资源
	 *
	 * @author liujun
	 * @created 2017年3月16日
	 *
	 * @param staticResourceEntity
	 */
	public int insertStaticResource(StaticResourceEntity staticResourceEntity) {
		if (Check.NuNObj(staticResourceEntity)) {
			return 0;
		}
		return mybatisDaoContext.save(SQLID + "insertStaticResource", staticResourceEntity);
	}
	
	/**
	 * 
	 * 查询静态资源
	 *
	 * @author liujun
	 * @created 2017年3月16日
	 *
	 * @param staticResourceFid
	 */
	public StaticResourceEntity findStaticResourceByFid(String staticResourceFid) {
		if (Check.NuNObj(staticResourceFid)) {
			return null;
		}
		return mybatisDaoContext.findOneSlave(SQLID + "findStaticResourceByFid", StaticResourceEntity.class, staticResourceFid);
	}

	/**
	 *
	 * 查询静态资源集合
	 *
	 * @author lunan
	 * @created 2017年3月21日
	 *
	 * @param resCode
	 */
	public List<StaticResourceVo> findStaticResListByResCode(String resCode){
		if (Check.NuNObj(resCode)) {
			return null;
		}
		return mybatisDaoContext.findAll(SQLID+"findStaticResListByResCode",StaticResourceVo.class,resCode);
	}
	
	/**
	 * 
	 * 分页查询静态资源列表
	 *
	 * @author liujun
	 * @created 2017年3月17日
	 *
	 * @param paramRequest
	 * @return
	 */
	public PagingResult<StaticResourceEntity> findStaticResourceListByPage(StaticResourceRequest paramRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(paramRequest.getPage());
		pageBounds.setLimit(paramRequest.getLimit());
		return mybatisDaoContext.findForPage(SQLID + "findStaticResourceList", StaticResourceEntity.class, paramRequest.toMap(), pageBounds);
	}
	
	/**
	 * 
	 * 更新静态资源
	 *
	 * @author liujun
	 * @created 2017年3月16日
	 *
	 * @param staticResourceEntity
	 */
	public int updateStaticResourceByFid(StaticResourceEntity staticResourceEntity) {
		if (Check.NuNObj(staticResourceEntity)) {
			return 0;
		}
		return mybatisDaoContext.update(SQLID + "updateStaticResourceByFid", staticResourceEntity);
	}

	/**
	 * 查询单个静态资源 根据code
	 * @author jixd
	 * @created 2017年06月21日 14:04:34
	 * @param
	 * @return
	 */
	public StaticResourceEntity findStaticResourceByCode(String resCode){
		Map<String,String> paramMap = new HashMap<>();
		paramMap.put("resCode",resCode);
		return mybatisDaoContext.findOneSlave(SQLID + "findStaticResourceByCode",StaticResourceEntity.class,paramMap);
	}

	/**
	 * 根据父code查询列表
	 * @author jixd
	 * @created 2017年06月22日 09:08:36
	 * @param
	 * @return
	 */
	public List<StaticResourceEntity> listStaticResourceByParentCode(String parentCode){
		return mybatisDaoContext.findAll(SQLID + "listStaticResourceByParentCode",StaticResourceEntity.class,parentCode);
	}
	
	
	/**
	 * 
	 * 查询最新的一个静态资源
	 *
	 * @author bushujie
	 * @created 2017年7月5日 下午2:16:42
	 *
	 * @param resCode
	 * @return
	 */
	public StaticResourceVo findStaticResByResCode(String resCode){
		Map<String,String> paramMap = new HashMap<>();
		paramMap.put("resCode",resCode);
		return mybatisDaoContext.findOneSlave(SQLID + "findStaticResByResCode",StaticResourceVo.class,paramMap);
	}
}
