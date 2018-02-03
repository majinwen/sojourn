package com.ziroom.minsu.services.basedata.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.conf.SubwayLineEntity;
import com.ziroom.minsu.services.basedata.dto.SubwayLineRequest;
import com.ziroom.minsu.services.basedata.entity.SubwayLineVo;

/**
 * 
 * @author homelink
 *
 */
@Repository("basedata.subwayLineDao")
public class SubwayLineDao {

	private String SQLID = "basedata.subwayLineDao.";

	@Autowired
	@Qualifier("basedata.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 分页查询地铁线路
	 * 
	 * @param subwayQueryRequest
	 * @return
	 */
	public PagingResult<SubwayLineVo> findSubwayLinePageList(SubwayLineRequest subwayLineRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(subwayLineRequest.getLimit());
		pageBounds.setPage(subwayLineRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "findSubwayLineInfoByPage", SubwayLineVo.class, subwayLineRequest, pageBounds);
	}

	/**
	 * 按Fid查询地铁线路
	 * @param subwayLine
	 * @return
	 */
	public SubwayLineEntity findSubwayLineByFid(SubwayLineEntity subwayLine) {
		return mybatisDaoContext.findOne(SQLID + "findSubwayLineByFid", SubwayLineEntity.class, subwayLine);
	}
	
	/**
	 * 按条件查询地铁线路
	 * @param subwayLine
	 * @return
	 */
	public List<SubwayLineEntity> findSubwayLine(SubwayLineEntity subwayLine) {
		return mybatisDaoContext.findAll(SQLID + "findSubwayLine", subwayLine);
	}

	/**
	 * 保存地铁线路
	 * 
	 * @param subwayLine
	 * @return
	 */
	public int saveSubwayLine(SubwayLineEntity subwayLine) {
		return mybatisDaoContext.save(SQLID + "insertSubwayLine", subwayLine);
	}

	/**
	 * 修改地铁线路
	 * 
	 * @param subwayLine
	 * @return
	 */
	public int updateSubwayLine(SubwayLineEntity subwayLine) {
		return mybatisDaoContext.save(SQLID + "updateSubwayLineByFid", subwayLine);
	}

}
