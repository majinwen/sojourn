package com.zra.house.logic;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.zra.house.entity.dto.PriceDto;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.common.constant.RoomStateEnum;
import com.zra.common.dto.contract.ContractDto;
import com.zra.common.utils.ZraConst;
import com.zra.house.entity.RoomInfoEntity;
import com.zra.house.service.RoomInfoService;
import com.zra.rentcontract.logic.RentContractLogic;

/**
 * Author:cuiyh9
 * CreateDate: 2016/7/29.
 */
@Component
public class RoomInfoLogic {
    
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(RoomInfoLogic.class);
    
    @Autowired
    private RoomInfoService roomInfoService;
    
    @Autowired
    private RentContractLogic rentContractLogic;
    
    /**
     * 修改房间状态为可预订.
     * 查询开始时间到当前时间需要进行处理的房间
     * @return
     */
    public int  modifyPreStatus() {
        Date endDate = new Date();
        int num = roomInfoService.modifyPreStatus(endDate);
        LOGGER.info("roomInfoService.modifyPreStatus(endDate):"+num+","+endDate);
        List<ContractDto> contractList = rentContractLogic.queryNeedModifyPreStatusContract();
        if (contractList == null || contractList.size() == 0 ) {
            return num;    
        }
        LOGGER.info("rentContractLogic.queryNeedModifyPreStatusContract size:"+contractList.size());
        for (ContractDto contractDto: contractList) {
            String roomId = contractDto.getRoomId();
            RoomInfoEntity room = roomInfoService.selectRoomInfoById(roomId);
            if(room == null){
                continue;
            }
            
            //已经是可预订了，进行修改
            if (room.getCurrentState().equals(String.valueOf(RoomStateEnum.BOOKABLE.getCode()))) {
                LOGGER.info("roomId:"+roomId+",RoomStateEnum.BOOKABLE.getCode():"+RoomStateEnum.BOOKABLE.getCode());
                continue;
            }
            //如果房间状态不是已出租，不进行操作
            if (!room.getCurrentState().equals(String.valueOf(RoomStateEnum.RENTAL.getCode()))) {
                LOGGER.info("roomId:"+roomId+",RoomStateEnum.RENTAL.getCode():"+RoomStateEnum.RENTAL.getCode());
                continue;
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(contractDto.getEndDate());
            cal.add(Calendar.DAY_OF_MONTH, ZraConst.DELAY_RENT_DAYS);//暂定2天
            Date avaSignDate = cal.getTime(); 
            int count = roomInfoService.modifyPreInfo(roomId,avaSignDate);
            LOGGER.info("modifyPreInfo:{},{}", roomId, count);
            num += count;
        }
        return num;
    }
    
    
    /**
     * 根据roomid获取room信息
     * @author tianxf9
     * @param roomId
     * @return
     */
    public RoomInfoEntity getRoomEntity(String roomId) {
    	return this.roomInfoService.selectRoomInfoById(roomId);
    }
    
    /**
     * 根据项目Id获取该项目下的所有房间
     * @author tianxf9
     * @param projectId
     * @return
     */
    public List<RoomInfoEntity> getRoomEntitys(String projectId) {
    	return this.roomInfoService.getRoomEntitys(projectId);
    }

    public PriceDto findMaxAndMinRoomPrice(String projectId, String houseTypeId, String roomNumber,
                                           Double minPrice, Double maxPrice, Double minArea, Double maxArea,
                                           String checkInTime, String floor, String direction) {
        return roomInfoService.findMaxAndMinRoomPrice(projectId, houseTypeId, roomNumber,
                minPrice, maxPrice, minArea, maxArea, checkInTime, floor, direction);
    }

    public PriceDto findMaxAndMinRoomPriceOther(String houseTypeId) {
        return roomInfoService.findMaxAndMinRoomPriceOther(houseTypeId);
    }
    
    /**得到所有房间价格的最大值加100元
     * @author tianxf9
     * @return
     */
    public Double getRoomMaxPrice() {
    	return Double.sum(roomInfoService.getRoomMaxPrice(), 100);
    }
}
