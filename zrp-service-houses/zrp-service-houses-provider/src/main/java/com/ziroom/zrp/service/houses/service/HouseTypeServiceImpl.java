package com.ziroom.zrp.service.houses.service;

import javax.annotation.Resource;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.service.houses.dto.AddHouseGroupDto;
import com.ziroom.zrp.service.houses.entity.AddHouseGroupVo;
import org.springframework.stereotype.Service;

import com.ziroom.zrp.houses.entity.HouseTypeEntity;
import com.ziroom.zrp.service.houses.dao.HouseTypeDao;

import java.util.Collection;
import java.util.List;

/**
 * <p>房间类型service</p>
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
@Service("houses.houseTypeServiceImpl")
public class HouseTypeServiceImpl {
	
	@Resource(name="houses.houseTypeDao")
	private HouseTypeDao houseTypeDao;
	
	public HouseTypeEntity findHouseTypeById(String fid){
		return houseTypeDao.findHouseTypeById(fid);
		
	}

	public List<HouseTypeEntity> findHouseTypeListByIds(List<String> houseTypeIds) {
		return houseTypeDao.findHouseTypeListByIds(houseTypeIds);
	}
	
	public int saveHouseType(HouseTypeEntity houseTypeEntity){
		return houseTypeDao.saveHouseType(houseTypeEntity);
		
	}

	/**
	 * @description: 分页查询户型列表
	 * @author: lusp
	 * @date: 2017/10/19 下午 19:18
	 * @params: addHouseGroupDto
	 * @return: PagingResult<AddHouseGroupVo>
	 */
	public PagingResult<AddHouseGroupVo> findLayoutListForPage(AddHouseGroupDto addHouseGroupDto){
		return houseTypeDao.findLayoutListForPage(addHouseGroupDto);
	}

	/**
	 * 条件查询
	 * @author yd
	 * @created
	 * @param
	 * @return
	 */
	public List<AddHouseGroupVo>  findHouseTypeByCondition(AddHouseGroupDto addHouseGroupDto){
		return houseTypeDao.findHouseTypeByCondition(addHouseGroupDto);
	}

	/**
	 * 查询房屋类型By 项目Ids
	 *
	 * @param projectIds
	 * @return
	 */
	public List<HouseTypeEntity> findHouseTypeByProjectIds(Collection<String> projectIds) {

		List<HouseTypeEntity> entities = null;

		try{
			entities = houseTypeDao.findHouseTypeByProjectIds(projectIds);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}

		return entities;
	}
}
