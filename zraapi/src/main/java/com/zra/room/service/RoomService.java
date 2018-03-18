package com.zra.room.service;

import com.zra.common.constant.RoomStateEnum;
import com.zra.common.dto.contract.ContractDto;
import com.zra.rentcontract.dao.RentContractMapper;
import com.zra.room.dao.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 合同service
 */
@Service
public class RoomService {
    @Autowired
    RoomMapper roomMapper;

    /**
     * 更新room状态
     */
    public int updateCanRenewRoom(String roomId) {
        return roomMapper.updateCanRenewRoom(RoomStateEnum.BOOKABLE.getCode().toString(), roomId);
    }

}
