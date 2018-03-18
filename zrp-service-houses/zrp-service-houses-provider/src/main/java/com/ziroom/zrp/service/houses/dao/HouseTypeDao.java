package com.ziroom.zrp.service.houses.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.service.houses.dto.AddHouseGroupDto;
import com.ziroom.zrp.service.houses.entity.AddHouseGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.houses.entity.HouseTypeEntity;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>房屋类型DAO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月17日
 * @since 1.0
 */
@Repository("houses.houseTypeDao")
public class HouseTypeDao {

	private String SQLID = "houses.houseTypeDao.";

	@Autowired
	@Qualifier("houses.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	public int saveHouseType(HouseTypeEntity houseTypeEntity){
		if (Check.NuNStr(houseTypeEntity.getFid())){
			houseTypeEntity.setFid(UUIDGenerator.hexUUID());
		}
		return mybatisDaoContext.save(SQLID+"saveHouseType", houseTypeEntity);
	}
	
	public HouseTypeEntity findHouseTypeById(String fid){
		return mybatisDaoContext.findOne(SQLID+"findHouseTypeById", HouseTypeEntity.class, fid);
	}

	public List<HouseTypeEntity> findHouseTypeListByIds(List<String> houseTypeIds) {
		Map<String, Object> map = new HashMap<>();
		map.put("houseTypeIds", houseTypeIds);
		return mybatisDaoContext.findAll(SQLID+"findHouseTypeListByIds", HouseTypeEntity.class, map);
	}

	/**
	 * @description: 分页查询户型列表
	 * @author: lusp
	 * @date: 2017/10/19 下午 19:21
	 * @params: addHouseGroupDto
	 * @return: PagingResult<AddHouseGroupVo>
	 */
	public PagingResult<AddHouseGroupVo> findLayoutListForPage(AddHouseGroupDto addHouseGroupDto){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(addHouseGroupDto.getLimit());
		pageBounds.setPage(addHouseGroupDto.getPage());
		return mybatisDaoContext.findForPage(SQLID+"findLayoutListForPage",AddHouseGroupVo.class,addHouseGroupDto,pageBounds);
	}


    /**
    * 条件查询项目类型
    * @author yd
    * @created
    * @param
    * @return
    */
	public List<AddHouseGroupVo>  findHouseTypeByCondition(AddHouseGroupDto addHouseGroupDto){
		return mybatisDaoContext.findAll(SQLID+"findLayoutListForPage",AddHouseGroupVo.class,addHouseGroupDto);
	}

	/**
	 * 查询房屋类型By 项目Ids
	 * @param projectIds
	 * @return
	 */
	public List<HouseTypeEntity> findHouseTypeByProjectIds(Collection<String> projectIds) {
		Map<String, Object> map = new HashMap<>();
		map.put("projectIds", projectIds);
		return mybatisDaoContext.findAll(SQLID+"findHouseTypeByProjectIds", HouseTypeEntity.class, map);
	}
}
