package com.ziroom.zrp.service.houses.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.zrp.houses.entity.BuildingInfoEntity;
import com.ziroom.zrp.service.houses.dto.AddHouseGroupDto;
import com.ziroom.zrp.service.houses.entity.AddHouseGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p>楼栋DAO</p>
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
@Repository("houses.buildingInfoDao")
public class BuildingInfoDao {

	private String SQLID = "houses.buildingInfoDao.";

	@Autowired
	@Qualifier("houses.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * @description: 分页查询楼栋列表
	 * @author: lusp
	 * @date: 2017/11/1 下午 19:21
	 * @params: addHouseGroupDto
	 * @return: PagingResult<AddHouseGroupVo>
	 */
	public PagingResult<AddHouseGroupVo> findBuildingListForPage(AddHouseGroupDto addHouseGroupDto){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(addHouseGroupDto.getLimit());
		pageBounds.setPage(addHouseGroupDto.getPage());
		return mybatisDaoContext.findForPage(SQLID+"findBuildingListForPage",AddHouseGroupVo.class,addHouseGroupDto,pageBounds);
	}


	/**
	 * 跟据fid查询
	 * @author yd
	 * @created
	 * @param
	 * @return
	 */
	public BuildingInfoEntity findBuildingInfoByFid(String fid){
		return  mybatisDaoContext.findOneSlave(SQLID+"selectByPrimaryKey",BuildingInfoEntity.class,fid);
	}
}
