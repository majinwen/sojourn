package com.ziroom.zrp.service.houses.service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.zrp.houses.entity.BuildingInfoEntity;
import com.ziroom.zrp.service.houses.dao.BuildingInfoDao;
import com.ziroom.zrp.service.houses.dto.AddHouseGroupDto;
import com.ziroom.zrp.service.houses.entity.AddHouseGroupVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>楼栋service</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年11月1日
 * @since 1.0
 */
@Service("houses.buildingInfoServiceImpl")
public class BuildingInfoServiceImpl {
	
	@Resource(name="houses.buildingInfoDao")
	private BuildingInfoDao buildingInfoDao;

	/**
	 * @description: 分页查询楼栋列表
	 * @author: lusp
	 * @date: 2017/11/1 下午 19:18
	 * @params: addHouseGroupDto
	 * @return: PagingResult<AddHouseGroupVo>
	 */
	public PagingResult<AddHouseGroupVo> findBuildingListForPage(AddHouseGroupDto addHouseGroupDto){
		return buildingInfoDao.findBuildingListForPage(addHouseGroupDto);
	}


	/**
	 *  根据 fid查询
	 * @author yd
	 * @created
	 * @param 
	 * @return 
	 */
	public BuildingInfoEntity findBuildingInfoByFid(String fid){
		return  buildingInfoDao.findBuildingInfoByFid(fid);
	}

}
