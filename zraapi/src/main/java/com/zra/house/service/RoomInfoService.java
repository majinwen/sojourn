package com.zra.house.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.common.constant.DirectionEnum;
import com.zra.common.constant.RoomStateEnum;
import com.zra.common.utils.DateUtil;
import com.zra.house.dao.RoomInfoMapper;
import com.zra.house.entity.RoomInfoEntity;
import com.zra.house.entity.dto.AreaDto;
import com.zra.house.entity.dto.PriceDto;
import com.zra.house.entity.dto.RoomDetailDto;
import com.zra.house.entity.dto.SearchRoomReqDto;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/1 17:26
 * @since 1.0
 */
@Service
public class RoomInfoService {

    private static final Logger LOG = LoggerFactoryProxy.getLogger(RoomInfoService.class);

    @Autowired
    private RoomInfoMapper roomInfoMapper;

    /**
     * 查询最高房价和最低房价
     * @param projectId
     * @param houseTypeId
     * @return
     */
    public PriceDto findMaxAndMinRoomPrice(String projectId, String houseTypeId, String roomNumber,
                                           Double minPrice, Double maxPrice,Double minArea, Double maxArea,
                                           String checkInTime,String floor,String direction){
        PriceDto priceDto = roomInfoMapper.findMaxAndMinRoomPrice(projectId,houseTypeId,roomNumber,minPrice,maxPrice,
                minArea,maxArea,checkInTime,floor,direction);
        if(priceDto == null){
            priceDto = new PriceDto();
        }
        return priceDto;
    }

    /**
     * 查询最高房价和最低房价
     * @param projectId
     * @param houseTypeId
     * @return
     */
    public PriceDto findMaxAndMinRoomPriceOther(String houseTypeId){
        PriceDto priceDto = roomInfoMapper.findMaxAndMinRoomPriceOther(houseTypeId);
        if(priceDto == null){
            priceDto = new PriceDto();
        }
        return priceDto;
    }

    /**
     * 查询最高和最低面积
     * @param projectId
     * @param houseTypeId
     * @return
     */
    public AreaDto findMaxAndMinRoomArea(String projectId, String houseTypeId, String roomNumber,
                                         Double minPrice, Double maxPrice,Double minArea, Double maxArea,
                                         String checkInTime,String floor,String direction){
        AreaDto areaDto = roomInfoMapper.findMaxAndMinRoomArea(projectId,houseTypeId,roomNumber,minPrice,maxPrice,
                minArea,maxArea,checkInTime,floor,direction);
        if(null == areaDto){
            areaDto = new AreaDto();
        }
        return areaDto;
    }

    /**
     * 条件查询房屋信息
     * @param dto
     * @return
     */
    public List<RoomInfoEntity> findRoomInfoByCondition(SearchRoomReqDto dto){
        return roomInfoMapper.findRoomInfoByCondition(
                dto.getRoomNumber(), dto.getHouseTypeId(),
                dto.getPrice() == null ? null : dto.getPrice().getMinPrice(),
                dto.getPrice() == null ? null : dto.getPrice().getMaxPrice(),
                dto.getArea() == null ? null : dto.getArea().getMinArea(),
                dto.getArea() == null ? null : dto.getArea().getMaxArea(),
                dto.getCheckInTime(), dto.getFloor(), dto.getDirection());
    }

    /**
     * 条件查询房屋信息
     * @param dto
     * @return
     */
    public List<RoomInfoEntity> findRoomInfoByConditionOther(SearchRoomReqDto dto){
        return roomInfoMapper.findRoomInfoByConditionOther(dto.getHouseTypeId());
    }

    /**
     * 数据处理
     * @param roomInfoEntityList
     * @return
     */
    public List<RoomDetailDto> convert(List<RoomInfoEntity> roomInfoEntityList){
        List<RoomDetailDto> roomDetailDtoList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(roomInfoEntityList)){
            for (RoomInfoEntity roomInfoEntity: roomInfoEntityList){
                roomDetailDtoList.add(convert(roomInfoEntity));
            }
        }
        return roomDetailDtoList;
    }

    /**
     * 数据处理
     * @param roomInfoEntity
     * @return
     */
    public RoomDetailDto convert(RoomInfoEntity roomInfoEntity){
        RoomDetailDto roomDetailDto = new RoomDetailDto();
        BeanUtils.copyProperties(roomInfoEntity, roomDetailDto);
        if(roomInfoEntity.getAvaSignDate() != null){
            roomDetailDto.setAvaSignDate(DateUtil.DateToStr(roomInfoEntity.getAvaSignDate(), DateUtil.DATE_FORMAT));
        }else{
            roomDetailDto.setAvaSignDate("");
        }
        if (roomInfoEntity.getDirection() != null){
            roomDetailDto.setDirection(DirectionEnum.getByCode(Integer.parseInt(roomInfoEntity.getDirection())).getName());
        }else{
            roomDetailDto.setDirection("");
        }
        if (roomInfoEntity.getCurrentState() != null){
            if ("0".equals(roomInfoEntity.getCurrentState())) {//户型页房间状态做一层映射关系，“待租中”－>“可签约”
                roomDetailDto.setStateName("可签约");
                roomDetailDto.setAvaSignDate("");
            } else {
                roomDetailDto.setStateName(RoomStateEnum.getByCode(Integer.parseInt(roomInfoEntity.getCurrentState())).getName());
            }
        }else{
            roomDetailDto.setStateName("");
        }
        return roomDetailDto;
    }

    /**
     * 修改为可预订状态.
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 成功修改数量
     */
    public int modifyPreStatus(final Date endDate) {
        List<RoomInfoEntity> roomList = roomInfoMapper.queryPreStatus(endDate);
        if (roomList == null || roomList.size() == 0) {
            return 0;
        }
        for (RoomInfoEntity room:roomList) {
            String roomId =  room.getId();
            int size = roomInfoMapper.modifyPreStatus(roomId);
            LOG.info("modifyPreStatus:{},{}", roomId, size);
        }
        return roomList.size();
    }
    
    public int modifyPreInfo(String roomId, Date avaSignDate) {
        return roomInfoMapper.modifyPreInfo(roomId, avaSignDate);
    }
    
    public RoomInfoEntity selectRoomInfoById(String id){
        return roomInfoMapper.selectRoomInfoById(id);
    }
    
    /**
     * @author tianxf9
     * @param projectId
     * @return
     */
    public  List<RoomInfoEntity> getRoomEntitys(String projectId) {
    	return roomInfoMapper.getRoomsEntityByProId(projectId);
    }
    
    public Double getRoomMaxPrice() {
    	return this.roomInfoMapper.getRoomMaxPrice();
    }
}
