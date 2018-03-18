package com.zra.house.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zra.house.entity.RoomInfoEntity;
import com.zra.house.entity.dto.AreaDto;
import com.zra.house.entity.dto.PriceDto;

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
 * @date 2016/8/1 17:21
 * @since 1.0
 */
@Repository
public interface RoomInfoMapper {

    /**
     * 查询最高房价和最低房价
     *
     * @param projectId
     * @param houseTypeId
     * @return
     */
    PriceDto findMaxAndMinRoomPrice(@Param("projectId") String projectId, @Param("houseTypeId") String houseTypeId,
                                    @Param("roomNumber") String roomNumber,
                                    @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice,
                                    @Param("minArea") Double minArea, @Param("maxArea") Double maxArea,
                                    @Param("checkInTime") String checkInTime,
                                    @Param("floor") String floor, @Param("direction") String direction);

    /**
     * 查询最高房价和最低房价
     *
     * @param houseTypeId
     * @return
     */
    PriceDto findMaxAndMinRoomPriceOther(@Param("houseTypeId") String houseTypeId);

    /**
     * 查询最高和最低面积
     *
     * @param projectId
     * @param houseTypeId
     * @return
     */
    AreaDto findMaxAndMinRoomArea(@Param("projectId") String projectId, @Param("houseTypeId") String houseTypeId,
                                  @Param("roomNumber") String roomNumber,
                                  @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice,
                                  @Param("minArea") Double minArea, @Param("maxArea") Double maxArea,
                                  @Param("checkInTime") String checkInTime,
                                  @Param("floor") String floor, @Param("direction") String direction);

    /**
     * 条件查询房屋信息
     * @param roomNumber
     * @param houseTypeId
     * @param minPrice
     * @param maxPrice
     * @param minArea
     * @param maxArea
     * @param checkInTime
     * @param floor
     * @param direction
     * @return
     */
    List<RoomInfoEntity> findRoomInfoByCondition(@Param("roomNumber") String roomNumber, @Param("houseTypeId") String houseTypeId,
                                                 @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice,
                                                 @Param("minArea") Double minArea, @Param("maxArea") Double maxArea,
                                                 @Param("checkInTime") String checkInTime,
                                                 @Param("floor") String floor, @Param("direction") String direction);

    /**
     * 条件查询房屋信息
     * @param houseTypeId
     * @return
     */
    List<RoomInfoEntity> findRoomInfoByConditionOther(@Param("houseTypeId") String houseTypeId);
    
    /**
     * 查询需要修改为可预订状态的数据
     * @param startDate
     * @param endDate
     * @return
     */
    List<RoomInfoEntity> queryPreStatus(Date endDate);
    
    int modifyPreStatus(String roomId);
    
    int modifyPreInfo(String roomId, Date avaSignDate);
    
    RoomInfoEntity selectRoomInfoById(String id);
    
    /**
     * 根据项目id获取该项目下所有房间
     * @author tianxf9
     * @param projectId
     * @return
     */
    List<RoomInfoEntity> getRoomsEntityByProId(@Param("projectId")String projectId);
    
    /**
     * 查询所有房间的价格最大值
     * @author tianxf9
     * @return
     */
    Double getRoomMaxPrice();
}
