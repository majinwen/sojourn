package com.ziroom.zrp.service.houses.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.service.houses.dto.AddHouseGroupDto;
import com.ziroom.zrp.service.houses.dto.RentRoomInfoDto;

import com.ziroom.zrp.service.houses.dto.RoomStmartDto;
import com.ziroom.zrp.service.houses.dto.WaterWattPagingDto;
import com.ziroom.zrp.service.houses.entity.AddHouseGroupVo;
import com.ziroom.zrp.service.houses.entity.RoomContractSmartVo;
import com.ziroom.zrp.service.houses.entity.WaterWattPaymentRoomVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>房间信息类</p>
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
@Repository("houses.roomInfoDao")
public class RoomInfoDao {

	private String SQLID = "houses.roomInfoDao.";

	@Autowired
	@Qualifier("houses.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * @description: 根据fid查询房间信息
	 * @author: lusp
	 * @date: 2017/9/13 19:30
	 * @params: fid
	 * @return: RoomInfoEntity
	 */
	public RoomInfoEntity getRoomInfoByFid(String fid) {
		return mybatisDaoContext.findOneSlave(SQLID + "selectByPrimaryKey", RoomInfoEntity.class, fid);
	}

	public List<RoomInfoEntity> getRoomListByRoomIds(List<String> roomIdList) {
		Map<String, Object> map = new HashMap<>();
		map.put("roomIds", roomIdList);
		List<RoomInfoEntity> list = mybatisDaoContext.findAll(SQLID + "getRoomListByRoomIds", RoomInfoEntity.class, map);
		return list;
	}

	/**
	 * @description: 根据房间fid 更新房间信息
	 * @author: lusp
	 * @date: 2017/9/13 19:51
	 * @params: roomInfoEntity
	 * @return: int
	 */
	public int updateRoomInfoByFid(RoomInfoEntity roomInfoEntity){
		return mybatisDaoContext.update(SQLID+"updateRoomInfoByFid",roomInfoEntity);
	}

	/**
	 * @description: 根据房间fid 以及房间当前状态 更新房间信息
	 * @author: lusp
	 * @date: 2018/1/10 19:51
	 * @params: roomInfoEntity
	 * @return: int
	 */
	public int updateRoomInfoByFidAndPreState(RoomInfoEntity roomInfoEntity){
		return mybatisDaoContext.update(SQLID+"updateRoomInfoByFidAndPreState",roomInfoEntity);
	}

	/**
	 * <p>查询合同相关的房间信息，房型信息，项目信息</p>.
	 * <p>连接查询troominfo，thousetype，tproject</p>
	 * @author xiangb
	 * @created 2017年9月24日
	 * @param
	 * @return
	 */
	public RentRoomInfoDto getRentRoomInfoByRoomId(String roomId){
		return mybatisDaoContext.findOne(SQLID + "findRentRoomInfoByRoomId",RentRoomInfoDto.class,roomId);
	}

	/**
	 * @description: 分页查询房间列表
	 * @author: lusp
	 * @date: 2017/10/19 下午 20:25
	 * @params: addHouseGroupDto
	 * @return: PagingResult<AddHouseGroupVo>
	 */
	public PagingResult<AddHouseGroupVo> findRoomListForPage(AddHouseGroupDto addHouseGroupDto){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(addHouseGroupDto.getLimit());
		pageBounds.setPage(addHouseGroupDto.getPage());
		return mybatisDaoContext.findForPage(SQLID+"findRoomListForPage",AddHouseGroupVo.class,addHouseGroupDto,pageBounds);
	}

    public int updateRoomInfoForRelease(List<String> list) {
        Map<String, Object> map = new HashMap<>();
        int i = 0;
        for (String roomId : list) {
            map.put("roomId", roomId);
            int j = mybatisDaoContext.update(SQLID + "updateRoomInfoForRelease", map);
            i += j;
        }
        return i;
    }
    
    /**
     * 修改房间为已出租
     * @author tianxf9
     * @param list
     * @return
     */
    public int updateRoomStateRental(List<String> list) {
    	
        Map<String, Object> map = new HashMap<>();
        int i = 0;
        for (String roomId : list) {
            map.put("roomId", roomId);
            int j = mybatisDaoContext.update(SQLID + "updateRoomStateRental", map);
            i += j;
        }
        return i;
    }

    /**
     * 修改房间状态为已出租
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public int updateRoomStateFromSigningToRental(List<String> roomIdList) {
		Map<String, Object> map = new HashMap<>();
		map.put("roomIds", roomIdList);
		int size = mybatisDaoContext.update(SQLID + "updateRoomStateFromSigningToRental", map);
		return size;
	}

	/**
	 *
	 * @author yd
	 * @created
	 * @param
	 * @return
	 */
	public PagingResult<RoomContractSmartVo> findRoomContractSmartByPage(RoomStmartDto roomStmartDto){

		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(roomStmartDto.getLimit());
		pageBounds.setPage(roomStmartDto.getPage());

		return mybatisDaoContext.findForPage(SQLID+"findRoomContractSmartByPage",RoomContractSmartVo.class,roomStmartDto.toMap(),pageBounds);
	}

	/**
	 *
	 * 项目下所有房间
	 *
	 * @author zhangyl2
	 * @created 2018年02月07日 15:02
	 * @param
	 * @return
	 */
	public List<RoomInfoEntity> findAllRoom(String projectid){
	    Map<String, Object> map = new HashMap<>();
	    map.put("projectid", projectid);
	    return mybatisDaoContext.findAll(SQLID+"findAllRoom", RoomInfoEntity.class, map);
    }

	/**
	 * 查询房间信息 通过父房间标识
	 * @param roomId 房间标识
	 * @return
	 * @author cuigh6
	 * @Date 2018年2月7日
	 */
	public List<RoomInfoEntity> getRoomInfoByParentId(String roomId) {
		Map<String, Object> map = new HashMap<>();
		map.put("roomId", roomId);
		return this.mybatisDaoContext.findAll(SQLID + "getRoomInfoByParentId", map);
	}
	/**
	 * 获取智能水表房间列表
	 * @param pagingDto
	 * @return
	 */
	public PagingResult<WaterWattPaymentRoomVo> findRoomWaterPaging(WaterWattPagingDto pagingDto) {

		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(pagingDto.getLimit());
		pageBounds.setPage(pagingDto.getPage());

		return mybatisDaoContext.findForPage(SQLID+"findRoomWaterPaging", WaterWattPaymentRoomVo.class, pagingDto.toMap(), pageBounds);
	}

    /**
     * 获取智能电表房间列表
     * @param pagingDto
     * @return
     */
    public PagingResult<WaterWattPaymentRoomVo> findRoomWattPaging(WaterWattPagingDto pagingDto) {

        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(pagingDto.getLimit());
        pageBounds.setPage(pagingDto.getPage());

		return mybatisDaoContext.findForPage(SQLID+"findRoomWattPaging", WaterWattPaymentRoomVo.class, pagingDto.toMap(), pageBounds);
    }
}
