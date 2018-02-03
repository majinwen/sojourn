package com.ziroom.minsu.services.basedata.service;

import java.util.List;

import javax.annotation.Resource;

import com.ziroom.minsu.services.basedata.entity.RegionExtVo;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.conf.HotRegionEntity;
import com.ziroom.minsu.services.basedata.dao.HotRegionDao;
import com.ziroom.minsu.services.basedata.dto.HotRegionRequest;

/**
 * 
 * <p>景点商圈管理操作业务层</p>
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
@Service("basedata.hotRegionServiceImpl")
public class HotRegionServiceImpl {

	@Resource(name = "basedata.hotRegionDao")
	private HotRegionDao hotRegionDao;

	/**
	 * 分页查询景点商圈列表
	 *
	 * @author liujun
	 * @created 2016-3-21 下午8:17:07
	 *
	 * @param hotRegionRequest
	 * @return
	 */
	public PagingResult<HotRegionEntity> findHotRegionPageList(
			HotRegionRequest hotRegionRequest) {
		return hotRegionDao.findHotRegionPageList(hotRegionRequest);
	}

	/**
	 * 保存景点商圈
	 *
	 * @author liujun
	 * @created 2016-3-22 上午10:29:33
	 *
	 * @param hotRegion
	 */
	public int saveHotRegion(HotRegionEntity hotRegion) {
		return hotRegionDao.saveHotRegion(hotRegion);
	}

	/**
	 * 修改景点商圈
	 *
	 * @author liujun
	 * @created 2016-3-22 上午10:56:43
	 *
	 * @param hotRegion
	 */
	public int updateHotRegion(HotRegionEntity hotRegion) {
		return hotRegionDao.updateHotRegion(hotRegion);
	}

	/**
	 * 根据景点商圈业务fid查询景点商圈
	 *
	 * @author liujun
	 * @created 2016-3-22 上午11:07:04
	 *
	 * @param hotRegionFid
	 * @return
	 */
	public HotRegionEntity findHotRegionByFid(String hotRegionFid) {
		return hotRegionDao.findHotRegionByFid(hotRegionFid);
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
		return hotRegionDao.getListWithFileByCityCode(cityCode);
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
		return hotRegionDao.findHotRegionsWithValidRadii();
	}

	/**
	 * 查询景点商圈以及其内容和描述
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/16 17:57
	 */
	public RegionExtVo getRegionExtVoByRegionFid(String regionFid){
		return hotRegionDao.getRegionExtVoByRegionFid(regionFid);
	}

}
