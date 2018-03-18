package com.ziroom.zrp.service.houses.api;

import com.ziroom.zrp.houses.entity.HouseTypeEntity;
import java.util.Collection;

/**
 * <p>房间类型接口</p>
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
public interface HouseTypeService {
	
	String saveHouseType(HouseTypeEntity houseTypeEntity);
	
	String findHouseTypeById(String fid);

	/**
	 * @description: 分页查询户型列表
	 * @author: lusp
	 * @date: 2017/10/19 下午 19:13
	 * @params: paramJson
	 * @return: String
	 */
	String findLayoutListForPage(String paramJson);

	/**
	 * 根据houseTypeIds 查询多个房型
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	String findHouseTypeListByIds(String houseTypeIds);

	/**
	 * 查询房屋类型By项目Ids
	 * @param projectIds
	 * @return
	 */
	String findHouseTypeByProjectIds(Collection<String> projectIds);
}
