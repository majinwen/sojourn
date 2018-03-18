package com.zra.rentcontract.dao;

import com.zra.common.dto.contract.ContractDto;
import com.zra.rentcontract.dto.RentContractDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 合同mapper
 */
@Repository
public interface RentContractMapper {

    /**
     * 查询未续约的合同
     */
    List<ContractDto> queryNotRenewContract();

    /**
     * 更新未续约的合同
     */
    int updateNotRenewContract(@Param("contractId") String id, @Param("canPreDate") Date canPreDate, @Param("canRenewDate") Date canRenewDate);

    /**
     * 查询可预订时间
     */
    List<String> queryCanRenewContract(Date canRenewDate);
    
    /**
     * 查询需要修改房间状态的合同
     */
    List<ContractDto> queryNeedModifyPreStatusContract(String contractType,Date startDate);
    
    /**
     * 根据roomid获取当前rentDate时间签约该房间的并且从rentDate时间起租合同信息
     * @author tianxf9
     * @param roomId
     * @return
     */
    int getRentContractByRoomId(@Param("roomId")String roomId,@Param("rentDate")String rentDate);
    
    /**
     * 根据roomId获取最近一次解约信息 isConThree='Y' 表示包含三天不满意，isConThree='N'表示不包含三天不满意
     * @author tianxf9
     * @param roomId
     * @return
     */
    RentContractDto getSurrenderInfoByRoomId(@Param("roomId")String roomId,@Param("isConThree")String isConThree);
    
    
    /**
     * 获取房间roomid在today的解约合同
     * @author tianxf9
     * @param roomId
     * @param today
     * @return
     */
    List<RentContractDto> getCurrentSurrenderInfoByRoomId(@Param("roomId")String roomId,@Param("today")String today);
    
    /**
     * 获取roomid在today签约的合同
     * @author tianxf9
     * @param roomId
     * @param today
     * @return
     */
    RentContractDto getCurrentRentContract(@Param("roomId")String roomId,@Param("today")String today);
    
    /**
     * 根据roomId和rentDate时间获取该房间在该时间的所有已签约的合同和在该时间没有到期的已续约的合同
     * @author tianxf9
     * @param roomId
     * @param rentDate
     * @return
     */
    RentContractDto getContractInfoByRoomId(@Param("roomId")String roomId,@Param("rentDate")String rentDate);

    Integer getIfEvaluate(String phone);
}
