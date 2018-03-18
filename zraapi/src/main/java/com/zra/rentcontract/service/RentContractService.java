package com.zra.rentcontract.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.zra.common.utils.Check;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.common.dto.contract.ContractDto;
import com.zra.common.enums.ContractTypeEnum;
import com.zra.common.utils.ZraConst;
import com.zra.rentcontract.dao.RentContractMapper;
import com.zra.rentcontract.dto.RentContractDto;

/**
 * 合同service
 */
@Service
public class RentContractService {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(RentContractService.class);

    @Autowired
    RentContractMapper rentContractMapper;

    /**
     * 查询未续约的合同
     */
    public List<ContractDto> queryNotRenewContract() {
        return rentContractMapper.queryNotRenewContract();
    }


    /**
     * 更新合同续约状态
     */
    public int updateNotRenewContract(ContractDto dto) {
        long d = 1000 * 60 * 60 * 24L;
        //可预定时间  TODO 二期改为可配置
        Date canPreDate = dto.getContractType() == 2 ? new Date(dto.getEndDate().getTime() - 7 * d) : new Date(dto.getEndDate().getTime() - 14 * d);
        //可续约时间  TODO 二期改为可配置
        Date canRenewDate = dto.getContractType() == 2 ? new Date(dto.getEndDate().getTime() - 7 * d) : new Date(dto.getEndDate().getTime() - 14 * d);
        return rentContractMapper.updateNotRenewContract(dto.getId(), canPreDate, canRenewDate);
    }


    /**
     * 查询可预订时间
     */
    public List<String> queryCanRenewContract() {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date uDate = new java.util.Date();
            Date d = df.parse(df.format(uDate));
            return rentContractMapper.queryCanRenewContract(d);
        } catch (Exception e) {
            LOGGER.error("查询可预订时间", e);
            return null;
        }

    }
    
    /**
     * 查询需要
     * @param contractType
     * @param startDate
     * @return
     */
    public List<ContractDto> queryNeedModifyPreStatusContract() {
        List<ContractDto> list = new ArrayList<ContractDto>();
        Date shortDate =  new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, ZraConst.DELAY_SHORT_OPEN_ORDER_DAYS);
        shortDate = cal.getTime();
        List<ContractDto> shortList = rentContractMapper.queryNeedModifyPreStatusContract(String.valueOf(ContractTypeEnum.SHORT.getType()), shortDate);
        
        Date longDate =  new Date();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, ZraConst.DELAY_LONG_OPEN_ORDER_DAYS);
        longDate = cal.getTime();
        List<ContractDto> longList = rentContractMapper.queryNeedModifyPreStatusContract(String.valueOf(ContractTypeEnum.LONG.getType()), longDate);
        if (shortList != null) {
            list.addAll(shortList);
        }
        if (shortList != null) {
            list.addAll(longList);
        }
        return list; 
    }
    
    /**
     * 根据roomId获取rentDate时间签约的该房间的合同
     * @author tianxf9
     * @param roomId
     * @param rentDate
     * @return
     */
    public int getRentContractByRoomId(String roomId,String rentDate) {
    	return this.rentContractMapper.getRentContractByRoomId(roomId, rentDate);
    }
    
    /**
     * 根据房间获取最近一次解约信息
     * @author tianxf9
     * @param roomId
     * @return
     */
    public RentContractDto  getSurContractInfo(String roomId,String isConThree) {
    	return this.rentContractMapper.getSurrenderInfoByRoomId(roomId,isConThree);
    }
    
    /**
     * 根据roomid和今天获取今天解约的合同信息
     * @author tianxf9
     * @param roomId
     * @param today
     * @return
     */
    public List<RentContractDto> getCurrentSurContractInfo(String roomId,String today) {
    	return this.rentContractMapper.getCurrentSurrenderInfoByRoomId(roomId, today);
    }
    
    /**
     * 根据roomid获取今天签约的合同
     * @author tianxf9
     * @param roomId
     * @param today
     * @return
     */
    public RentContractDto getCurrentContractInfo(String roomId,String today) {
    	return this.rentContractMapper.getCurrentRentContract(roomId, today);
    }
    
    /**
     * 根据roomId和rentDate时间获取该房间在该时间的所有已签约的合同和在该时间没有到期的已续约的合同
     * @param roomId
     * @param rentDate
     * @return
     */
    public RentContractDto getContractInfoByRoomId(String roomId,String today) {
    	return this.rentContractMapper.getContractInfoByRoomId(roomId, today);
    }


    public Integer getIfEvaluate(String phone) {
        return rentContractMapper.getIfEvaluate(phone);
    }
}
