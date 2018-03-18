package com.zra.rentcontract.logic;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.common.dto.contract.ContractDto;
import com.zra.common.utils.DateUtil;
import com.zra.rentcontract.dto.RentContractDto;
import com.zra.rentcontract.service.RentContractService;

/**
 * 合同logic
 */
@Component
public class RentContractLogic {

    @Autowired
    RentContractService rentContractService;

    /**
     * 查询未续约的合同
     */
    public List<ContractDto> queryNotRenewContract() {
        return rentContractService.queryNotRenewContract();
    }


    /**
     * 更新合同续约状态
     */
    public int updateNotRenewContract(ContractDto dto) {
        return rentContractService.updateNotRenewContract(dto);
    }


    /**
     * 查询可预订时间
     */
    public List<String> queryCanRenewContract() {
        return rentContractService.queryCanRenewContract();
    }

    /**
     * @return
     */
    public List<ContractDto> queryNeedModifyPreStatusContract() {
        return rentContractService.queryNeedModifyPreStatusContract();
    }
    
    /**
     * 是否存在根据roomid获取当前rentDate时间签约该房间的并且从rentDate时间起租的合同
     * @param roomId
     * @param rentDate
     * @return
     */
    public int isExistRentContract(String roomId,Date rentDate) {
    	String rentDateStr = DateUtil.DateToStr(rentDate, DateUtil.DATE_FORMAT);
    	return this.rentContractService.getRentContractByRoomId(roomId, rentDateStr);
    }
    
    /**
     * 根据roomID获取该房间最近一次解约协议
     * @author tianxf9
     * @param roomId
     * @return
     */
    public RentContractDto getSurContractInfoByRoomId(String roomId,String isConThree) {
    	return this.rentContractService.getSurContractInfo(roomId,isConThree);
    			
    }
    
    /**
     * 根据roomid和今天获取今天解约的合同信息
     * @author tianxf9
     * @param roomId
     * @param today
     * @return
     */
    public List<RentContractDto> getCurrentSurContractInfo(String roomId,String today) {
    	return this.rentContractService.getCurrentSurContractInfo(roomId, today);
    }
    
    /**
     * 根据roomid获取今天签约的合同
     * @param roomId
     * @param today
     * @return
     */
    public RentContractDto getCurrentContractInfo(String roomId,String today) {
    	return this.rentContractService.getCurrentContractInfo(roomId, today);
    }
    
    /**
     * 根据roomId和rentDate时间获取该房间在该时间的所有已签约的合同和在该时间没有到期的已续约的合同的签约日期最小值
     * @author tianxf9
     * @param roomId
     * @return
     */
    public RentContractDto getContractInfo(String roomId,String today) {
    	return this.rentContractService.getContractInfoByRoomId(roomId, today);
    }

    public Integer getIfEvaluate(String phone) {
        return rentContractService.getIfEvaluate(phone);
    }

}
