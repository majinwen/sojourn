package com.zra.room.dao;

import com.zra.common.dto.contract.ContractDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * ROOM mapper
 */
@Repository
public interface RoomMapper {


    /**
     * 更新房屋状态
     */
    int updateCanRenewRoom(@Param("currentState") String currentState, @Param("roomId") String roomId);


}
