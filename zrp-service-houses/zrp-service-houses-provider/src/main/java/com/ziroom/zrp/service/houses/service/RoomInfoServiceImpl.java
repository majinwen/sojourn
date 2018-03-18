package com.ziroom.zrp.service.houses.service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.houses.entity.RoomRentInfoEntity;
import com.ziroom.zrp.service.houses.dao.RoomInfoDao;
import com.ziroom.zrp.service.houses.dao.RoomRentInfoDao;
import com.ziroom.zrp.service.houses.dto.AddHouseGroupDto;
import com.ziroom.zrp.service.houses.dto.RentRoomInfoDto;
import com.ziroom.zrp.service.houses.dto.RoomStmartDto;
import com.ziroom.zrp.service.houses.entity.AddHouseGroupVo;
import com.ziroom.zrp.service.houses.entity.RoomContractSmartVo;
import com.ziroom.zrp.service.houses.valenum.RoomLockSource;
import com.ziroom.zrp.service.houses.valenum.RoomStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
@Service("houses.roomInfoServiceImpl")
public class RoomInfoServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomInfoServiceImpl.class);

	@Resource(name = "houses.roomInfoDao")
	private RoomInfoDao roomInfoDao;

	@Resource(name = "houses.roomRentInfoDao")
	private RoomRentInfoDao roomRentInfoDao;

	/**
	 * @description: 根据fid查询房间信息
	 * @author: lusp
	 * @date: 2017/9/13 19:32
	 * @params: fid
	 * @return: RoomInfoEntity
	 */
	public RoomInfoEntity getRoomInfoByFid(String fid){
		return roomInfoDao.getRoomInfoByFid(fid);
	}

	/**
	 * 查询多个房间信息
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public List<RoomInfoEntity> getRoomListByRoomIds(String roomIds) {
		List<String> roomIdList = Arrays.asList(roomIds.split(","));
		List<RoomInfoEntity> list = roomInfoDao.getRoomListByRoomIds(roomIdList);
		return list;
	}

	/**
	 * @description: 根据房间fid 更新房间信息
	 * @author: lusp
	 * @date: 2017/9/13 20:51
	 * @params: roomInfoEntity
	 * @return: int
	 */
	public int updateRoomInfoByFid(RoomInfoEntity roomInfoEntity){
		return roomInfoDao.updateRoomInfoByFid(roomInfoEntity);
	}

	/**
	 * @description: 锁房以及保存出房记录
	 * @author: lusp
	 * @date: 2017/9/13 20:58
	 * @params: roomRentInfoEntity
	 * @return:
	 */
	public void updateRoomInfoAndSaveRentInfo(RoomRentInfoEntity roomRentInfoEntity) throws BusinessException{
		RoomInfoEntity roomInfoEntity = new RoomInfoEntity();
		roomInfoEntity.setFid(roomRentInfoEntity.getFroomid());
		roomInfoEntity.setFcurrentstate(RoomStatusEnum.SD.getCode());
		roomInfoEntity.setFlocktime(new Date());
		roomInfoEntity.setFlockroomsource(RoomLockSource.APP.getCode());
		roomInfoEntity.setPreRoomState(RoomStatusEnum.DZZ.getCode());
		roomInfoEntity.setFopenbookdate(null);
		roomInfoEntity.setFavasigndate(null);
		int num = roomInfoDao.updateRoomInfoByFidAndPreState(roomInfoEntity);
		if(num==0){
			throw new BusinessException("房间状态异常,该房间状态不是待租中");
		}
		roomRentInfoDao.saveRoomRentInfo(roomRentInfoEntity);
	}

	/**
	  * @description: 保存出房记录
	  * @author: lusp
	  * @date: 2018/1/10 下午 20:51
	  * @params: roomRentInfoEntity
	  * @return:
	  */
	public void saveRoomRentInfo(RoomRentInfoEntity roomRentInfoEntity){
		roomRentInfoDao.saveRoomRentInfo(roomRentInfoEntity);
	}

	/**
	  * @description:  释放房间以及逻辑删除出房记录
	  * @author: lusp
	  * @date: 2017/10/11 上午 10:40
	  * @params: roomRentInfoEntity
	  * @return:
	  */
	public void updateRoomInfoAndDeleteRentInfo(RoomRentInfoEntity roomRentInfoEntity){
		RoomInfoEntity roomInfoEntity = new RoomInfoEntity();
		roomInfoEntity.setFid(roomRentInfoEntity.getFroomid());
		roomInfoEntity.setFcurrentstate(RoomStatusEnum.DZZ.getCode());
		roomInfoDao.updateRoomInfoByFid(roomInfoEntity);
		roomRentInfoEntity.setFvalid(0);
		roomRentInfoEntity.setFisdel(1);
		roomRentInfoDao.updateRoomRentInfoByFid(roomRentInfoEntity);
	}

	/**
	 * <p>查询合同相关的房间信息，房型信息，项目信息</p>.
	 * <p>连接查询troominfo，thousetype，tproject</p>
	 * @author xiangb
	 * @created 2017年9月24日
	 * @param roomId 房间ID
	 * @return
	 */
	public RentRoomInfoDto getRentRoomInfoByRoomId(String roomId){
		return roomInfoDao.getRentRoomInfoByRoomId(roomId);
	}

	/**
	 * @description: 分页查询房间列表
	 * @author: lusp
	 * @date: 2017/10/19 下午 20:23
	 * @params: addHouseGroupDto
	 * @return: PagingResult<AddHouseGroupVo>
	 */
	public PagingResult<AddHouseGroupVo> findRoomListForPage(AddHouseGroupDto addHouseGroupDto){
		return roomInfoDao.findRoomListForPage(addHouseGroupDto);
	}

    public void updateRoomInfoForRelease(List<String> list) {
        int i = roomInfoDao.updateRoomInfoForRelease(list);
        if (i != list.size()) {
            LogUtil.info(LOGGER, "[updateRoomInfoForRelease]应当更新行数={},实际更新行数={}", list.size(), i);
            throw new RuntimeException("更新失败！");
        }
    }
    
    /**
     * 修改房间状态为已出租
     * @author tianxf9
     * @param list
     */
    public void updateRoomStateRental(List<String> list) {
        int i = roomInfoDao.updateRoomStateRental(list);
        if (i != list.size()) {
            LogUtil.info(LOGGER, "[updateRoomStateRental]应当更新行数={},实际更新行数={}", list.size(), i);
            throw new RuntimeException("更新失败！");
        }
    }

	/**
	 * 修改房间状态
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public boolean updateRoomStateFromSigningToRental(List<String> roomIdList) throws Exception {
		int size = roomIdList.size();
		int resultSize = roomInfoDao.updateRoomStateFromSigningToRental(roomIdList);
		//部分房间状态不为签约进行中，回退
		if (size != resultSize) {
			throw  new Exception("房间状态不一致,不能更新成功");
		} else {
			return true;
		}
	}

	/**
	 * 查询智能锁房间信息
	 * @author yd
	 * @created  
	 * @param
	 * @return 
	 */
	public PagingResult<RoomContractSmartVo> findRoomContractSmartByPage(RoomStmartDto roomStmartDto){
		return this.roomInfoDao.findRoomContractSmartByPage(roomStmartDto);
	}
	/**
	 * 查询房间信息 通过父房间标识
	 * @param roomId 房间标识
	 * @return
	 * @author cuigh6
	 * @Date 2018年2月7日
	 */
	public List<RoomInfoEntity> getRoomInfoByParentId(String roomId) {
		return this.roomInfoDao.getRoomInfoByParentId(roomId);
	}

    /**
     *
     * 项目下所有房间
     *
     * @author zhangyl2
     * @created 2018年02月07日 15:04
     * @param
     * @return
     */
    public List<RoomInfoEntity> findAllRoom(String projectid){
        return roomInfoDao.findAllRoom(projectid);
    }

}
