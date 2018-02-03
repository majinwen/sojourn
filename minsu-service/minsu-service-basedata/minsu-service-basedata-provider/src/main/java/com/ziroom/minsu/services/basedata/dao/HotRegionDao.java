package com.ziroom.minsu.services.basedata.dao;

import java.util.List;

import com.ziroom.minsu.services.basedata.entity.RegionExtVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.conf.HotRegionEntity;
import com.ziroom.minsu.services.basedata.dto.HotRegionRequest;

/**
 * <p>景点商圈测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/19.
 * @version 1.0
 * @since 1.0
 */
@Repository("basedata.hotRegionDao")
public class HotRegionDao {


    private String SQLID="basedata.hotRegionDao.";

    @Autowired
    @Qualifier("basedata.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 
     * 分页查询景点商圈列表
     *
     * @author liujun
     * @created 2016-3-21 下午8:27:34
     *
     * @param hotRegionRequest
     * @return
     */
    public PagingResult<HotRegionEntity> findHotRegionPageList(HotRegionRequest hotRegionRequest) {
    	PageBounds pageBounds=new PageBounds();
		pageBounds.setLimit(hotRegionRequest.getLimit());
		pageBounds.setPage(hotRegionRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID+"findHotRegionListByPage", HotRegionEntity.class, hotRegionRequest, pageBounds);
    }


	/**
	 * 保存景点商圈
	 *
	 * @author liujun
	 * @created 2016-3-22 上午10:30:29
	 *
	 * @param hotRegion
	 */
	public int saveHotRegion(HotRegionEntity hotRegion) {
		return mybatisDaoContext.save(SQLID+"insertHotRegion", hotRegion);
	}


	/**
	 * 修改景点商圈
	 *
	 * @author liujun
	 * @created 2016-3-22 上午10:57:31
	 *
	 * @param hotRegion
	 * @return
	 */
	public int updateHotRegion(HotRegionEntity hotRegion) {
		return mybatisDaoContext.update(SQLID+"updateHotRegionByFid", hotRegion);
	}


	/**
	 * 根据景点商圈业务fid查询景点商圈
	 *
	 * @author liujun
	 * @created 2016-3-22 上午11:08:56
	 *
	 * @param hotRegionFid
	 * @return
	 */
	public HotRegionEntity findHotRegionByFid(String hotRegionFid) {
		return mybatisDaoContext.findOne(SQLID+"findHotRegionByFid", HotRegionEntity.class, hotRegionFid);
	}


	/**
	 * 根据城市code查询已建立档案的景点商圈列表
	 *
	 * @author liujun
	 * @created 2016年11月10日
	 *
	 * @param cityCode
	 * @return
	 */
	public List<HotRegionEntity> getListWithFileByCityCode(String cityCode) {
		return mybatisDaoContext.findAll(SQLID+"getListWithFileByCityCode", HotRegionEntity.class, cityCode);
	}


	/**
	 * 查询含有有效半径的景点商圈列表(radii>0)
	 *
	 * @author liujun
	 * @created 2016年11月12日
	 *
	 * @return
	 */
	public List<HotRegionEntity> findHotRegionsWithValidRadii() {
		return mybatisDaoContext.findAll(SQLID+"findHotRegionsWithValidRadii", HotRegionEntity.class);
	}

	/**
	 * 查询景点商圈以及其内容和描述
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/16 17:57
	 */
	public RegionExtVo getRegionExtVoByRegionFid(String regionFid){
		return mybatisDaoContext.findOne(SQLID+"getRegionExtVoByRegionFid",RegionExtVo.class,regionFid);
	}
    

}
