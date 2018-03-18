package com.ziroom.zrp.service.houses.dao;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.houses.dto.AddHouseGroupDto;
import com.ziroom.zrp.service.houses.dto.RentRoomInfoDto;

import com.ziroom.zrp.service.houses.dto.RoomStmartDto;
import com.ziroom.zrp.service.houses.dto.WaterWattPagingDto;
import com.ziroom.zrp.service.houses.entity.AddHouseGroupVo;
import com.ziroom.zrp.service.houses.entity.RoomContractSmartVo;
import com.ziroom.zrp.service.houses.entity.WaterWattPaymentRoomVo;
import com.ziroom.zrp.service.houses.valenum.MeterTypeEnum;
import com.ziroom.zrp.service.houses.valenum.RoomLockSource;
import com.ziroom.zrp.service.houses.valenum.RoomStatusEnum;
import org.junit.Test;
import org.springframework.util.Assert;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年09月22日 11:38
 * @since 1.0
 */
public class RoomInfoDaoTest extends BaseTest {

    @Resource(name="houses.roomInfoDao")
    private RoomInfoDao roomInfoDao;

    @Test
    public void testGetRoomListByRoomIds(){
        List<String> roomIdList = new ArrayList<>();
        roomIdList.add("1001255");
        roomIdList.add("1001257");
        List<RoomInfoEntity> list = roomInfoDao.getRoomListByRoomIds(roomIdList);
        System.out.println(list.size());
    }
    
    @Test
	public void testfindRentRoomInfo(){
		RentRoomInfoDto roomDto = roomInfoDao.getRentRoomInfoByRoomId("8a8ac42651429cf5015148249d8500cd");
		System.err.println(JSONObject.toJSON(roomDto));
	}

    @Test
    public void testfindRoomListForPage(){
        AddHouseGroupDto addHouseGroupDto = new AddHouseGroupDto();
        addHouseGroupDto.setProjectid("8a9099cb576ba5c101576ea29c8a0027");
        addHouseGroupDto.setHousetypeid("8a90a3ab57e1c0760157e68aa88200c2");
        PagingResult<AddHouseGroupVo> pagingResult = roomInfoDao.findRoomListForPage(addHouseGroupDto);
        System.err.print(pagingResult.toString());
    }

    @Test
    public void testUpdateRoomStateFromSigningToRental() {
        List<String> roomIdList = new ArrayList<>();
        roomIdList.add("1001239");
        roomIdList.add("1001240");
        int size = roomInfoDao.updateRoomStateFromSigningToRental(roomIdList);
        System.out.println("testUpdateRoomStateFromSigningToRental size:" + size);
    }

    @Test
    public  void testFindRoomContractSmartByPage(){
        RoomStmartDto roomStmartDto = new RoomStmartDto();
        PagingResult<RoomContractSmartVo> page =   roomInfoDao.findRoomContractSmartByPage(roomStmartDto);
        System.out.println(page);
    }

    @Test
    public void testupdateRoomInfoByFidAndPreState(){
        RoomInfoEntity roomInfoEntity = new RoomInfoEntity();
        roomInfoEntity.setFid("1001265");
        roomInfoEntity.setFcurrentstate(RoomStatusEnum.SD.getCode());
        roomInfoEntity.setFlocktime(new Date());
        roomInfoEntity.setFlockroomsource(RoomLockSource.APP.getCode());
        roomInfoEntity.setPreRoomState(RoomStatusEnum.DZZ.getCode());
        roomInfoEntity.setFopenbookdate(null);
        roomInfoEntity.setFavasigndate(null);
        roomInfoDao.updateRoomInfoByFidAndPreState(roomInfoEntity);
    }

    @Test
    public void testGetRoomInfoByParentId() {
        String roomId = "8a9099cb57a270320157a85bd7010046";
        List<RoomInfoEntity> roomList = roomInfoDao.getRoomInfoByParentId(roomId);
        System.err.println(JsonEntityTransform.Object2Json(roomList));
    }

    @Test
    public void testfindAllRoom(){
        System.out.println(roomInfoDao.findAllRoom("8a9099cb576ba5c101576ea29c8a0027"));
    }

    /**
     * 查询智能水表分页
     */
    @Test
    public void waterPayPaging() {

        WaterWattPagingDto waterWattPagingDto = new WaterWattPagingDto();
        waterWattPagingDto.setDeviceType(MeterTypeEnum.WATER.getCode());

        PagingResult<WaterWattPaymentRoomVo> waterWattVoPagingResult = roomInfoDao.findRoomWaterPaging(waterWattPagingDto);
        Assert.notNull(waterWattVoPagingResult, "roomInfoDao.findRoomWaterPaging fail!");
        Assert.isTrue(waterWattVoPagingResult.getTotal() > 0, "roomInfoDao.findRoomWaterPaging fail!");
        Assert.notEmpty(waterWattVoPagingResult.getRows(), "roomInfoDao.findRoomWaterPaging fail!");
    }

    /**
     * 查询智能电表分页
     */
    @Test
    public void wattPayPaging() {

        WaterWattPagingDto waterWattPagingDto = new WaterWattPagingDto();
        waterWattPagingDto.setDeviceType(MeterTypeEnum.ELECTRICITY.getCode());

        PagingResult<WaterWattPaymentRoomVo> waterWattVoPagingResult = roomInfoDao.findRoomWattPaging(waterWattPagingDto);
        Assert.notNull(waterWattVoPagingResult, "roomInfoDao.findRoomWaterPaging fail!");
        Assert.isTrue(waterWattVoPagingResult.getTotal() > 0, "roomInfoDao.findRoomWaterPaging fail!");
        Assert.notEmpty(waterWattVoPagingResult.getRows(), "roomInfoDao.findRoomWaterPaging fail!");
    }
}
