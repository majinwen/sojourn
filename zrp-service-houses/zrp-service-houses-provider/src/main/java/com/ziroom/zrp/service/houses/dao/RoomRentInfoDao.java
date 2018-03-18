package com.ziroom.zrp.service.houses.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.houses.entity.RoomRentInfoEntity;
import com.ziroom.zrp.service.houses.dto.RentRoomInfoDto;

/**
 * <p>房间出租记录类</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author lusp
 * @Date Created in 2017年09月13日 11:36
 * @version 1.0
 * @since 1.0
 */
@Repository("houses.roomRentInfoDao")
public class RoomRentInfoDao {

	private String SQLID = "houses.roomRentInfoDao.";

	@Autowired
	@Qualifier("houses.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * @description: 根据fid查询房间出租记录信息
	 * @author: lusp
	 * @date: 2017/9/13 20:30
	 * @params: fid
	 * @return: RoomRentInfoEntity
	 */
	public RoomRentInfoEntity getRoomRentInfoByFid(String fid) {
		return mybatisDaoContext.findOneSlave(SQLID + "selectByPrimaryKey", RoomRentInfoEntity.class, fid);
	}

	/**
	 * @description: 保存房间出租记录
	 * @author: lusp
	 * @date: 2017/9/13 20:32
	 * @params: roomRentInfoEntity
	 * @return: int
	 */
	public int saveRoomRentInfo(RoomRentInfoEntity roomRentInfoEntity){
		if (Check.NuNStr(roomRentInfoEntity.getFid())){
			roomRentInfoEntity.setFid(UUIDGenerator.hexUUID());
		}
		return mybatisDaoContext.save(SQLID + "saveRoomRentInfo",roomRentInfoEntity);
	}

	/**
	  * @description: 更新出房记录
	  * @author: lusp
	  * @date: 2017/10/11 上午 10:45
	  * @params: roomRentInfoEntity
	  * @return: int
	  */
	public int updateRoomRentInfoByFid(RoomRentInfoEntity roomRentInfoEntity){
		return mybatisDaoContext.update(SQLID + "updateRoomRentInfoByFid",roomRentInfoEntity);
	}


	
}
