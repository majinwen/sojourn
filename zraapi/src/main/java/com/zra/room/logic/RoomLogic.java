package com.zra.room.logic;

import com.zra.common.dto.contract.ContractDto;
import com.zra.rentcontract.dao.RentContractMapper;
import com.zra.rentcontract.service.RentContractService;
import com.zra.room.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 合同logic
 * 该类废弃，不允许使用.请使用
 * {@link com.zra.house.logic.RoomInfoLogic}
 * 
 */
@Deprecated
@Component
public class RoomLogic {

    @Autowired
    RoomService roomService;

    /**
     * 更新房屋状态
     */
    public int updateCanRenewRoom(String roomId) {
        return roomService.updateCanRenewRoom(roomId);
    }


}
