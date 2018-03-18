package com.ziroom.zrp.service.houses.service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.zrp.houses.entity.RoomRentInfoEntity;
import com.ziroom.zrp.service.houses.dao.RoomInfoDao;
import com.ziroom.zrp.service.houses.dao.RoomRentInfoDao;
import com.ziroom.zrp.service.houses.dto.RentRoomInfoDto;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>房间信息实现类</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author lusp
 * @Date Created in 2017年09月13日 20:35
 * @version 1.0
 * @since 1.0
 */
@Service("houses.roomRentInfoServiceImpl")
public class RoomRentInfoServiceImpl {


	@Resource(name = "houses.roomRentInfoDao")
	private RoomRentInfoDao roomRentInfoDao;

	/**
	 * @description: 保存房间出租记录
	 * @author: lusp
	 * @date: 2017/9/13 20:52
	 * @params: roomRentInfoEntity
	 * @return: int
	 */
	public int saveRoomRentInfo(RoomRentInfoEntity roomRentInfoEntity){
		return roomRentInfoDao.saveRoomRentInfo(roomRentInfoEntity);
	}

}
