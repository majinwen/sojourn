package com.ziroom.minsu.report.board.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.report.board.dto.CityTargetRequest;
import com.ziroom.minsu.report.board.entity.CityTargetEntity;
import com.ziroom.minsu.report.board.vo.CityTargetItem;
import com.ziroom.minsu.report.board.vo.RegionItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 大区信息
 * </p>
 * <p/>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd on 2017/1/19.
 * @version 1.0
 * @since 1.0
 */
@Repository("report.cityTargetDao")
public class CityTargetDao {

	private String SQLID = "report.cityTargetDao.";

	@Autowired
	@Qualifier("minsuReport.report.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	/**
	 *  跨库使用
	 */
	@Autowired
	@Qualifier("minsuReport.all.MybatisDaoContext")
	private MybatisDaoContext unionDaoContext;

	/**
	 * 插入
	 * @author jixd
	 * @created 2017年01月09日 14:34:55
	 * @param
	 * @return
	 */
	public int insertCityTarget(CityTargetEntity cityTargetEntity){
		if (Check.NuNStr(cityTargetEntity.getFid())){
			cityTargetEntity.setFid(UUIDGenerator.hexUUID());
		}
		return mybatisDaoContext.save(SQLID + "insert",cityTargetEntity);
	}

	/**
	 * 更新
	 * @author jixd
	 * @created 2017年01月09日 14:35:39
	 * @param
	 * @return
	 */
	public int updateByFid(CityTargetEntity cityTargetEntity){
		return mybatisDaoContext.update(SQLID + "updateByFid",cityTargetEntity);
	}

	/**
	 * 查询列表
	 * @author jixd
	 * @created 2017年01月11日 14:16:45
	 * @param
	 * @return
	 */
	public List<CityTargetItem> findTargetCityList(CityTargetRequest cityTargetRequest){
		return unionDaoContext.findAll(SQLID + "findTargetCityList",CityTargetItem.class,cityTargetRequest);
	}

	/**
	 * 分页查询 大区fid 分页列表
	 * @author jixd
	 * @created 2017年01月11日 19:57:45
	 * @param
	 * @return
	 */
	public PagingResult<RegionItem> groupByRegionFidList(CityTargetRequest cityTargetRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(cityTargetRequest.getLimit());
		pageBounds.setPage(cityTargetRequest.getPage());
		return unionDaoContext.findForPage(SQLID + "groupByRegionFidList",RegionItem.class,cityTargetRequest,pageBounds);
	}

	/**
	 * 查询城市目标实体
	 * @author jixd
	 * @created 2017年01月12日 19:42:52
	 * @param
	 * @return
	 */
	public List<CityTargetEntity> findTargetCityEntityList(CityTargetRequest cityTargetRequest){
		return mybatisDaoContext.findAll(SQLID + "findTargetCityEntityList",CityTargetEntity.class,cityTargetRequest);
	}
}
