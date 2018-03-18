package com.ziroom.zrp.service.houses.service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dto.RentRoomDto;
import com.ziroom.zrp.service.trading.entity.CloseContractNotifyVo;
import com.ziroom.zrp.service.trading.service.RentContractServiceImpl;
import com.ziroom.zrp.service.trading.valenum.BussTypeEnum;
import com.ziroom.zrp.service.trading.valenum.ContractStatusEnum;
import com.ziroom.zrp.trading.entity.RentContractEntity;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年12月08日 14:42
 * @since 1.0
 */
public class RentContractServiceTest extends BaseTest{

    @Resource(name="trading.rentContractServiceImpl")
    private RentContractServiceImpl rentContractServiceImpl;

    @Test
    public void testupdateBaseContractById(){
        RentContractEntity updateRentcontract = new RentContractEntity();
        updateRentcontract.setContractId("8a90a3ab5867b6ca015867b813820002");
        updateRentcontract.setPreConStatusCode("yqy");
        updateRentcontract.setConStatusCode(ContractStatusEnum.YDQ.getStatus());
        rentContractServiceImpl.updateBaseContractById(updateRentcontract);
    }

    @Test
    public void TestfindFirstBillPayBeforeOvertimeForPage(){
        Map<String ,Object> paramMap = new HashMap<>();
        paramMap.put("beforeOverTimeHours",Integer.valueOf("3"));
        paramMap.put("payOvertimeConditionMoney",Integer.valueOf("50000"));
        paramMap.put("lessMoneyOvertimeHours",Integer.valueOf("48"));
        paramMap.put("moreMoneyOvertimeHours",Integer.valueOf("72"));
        PagingResult<CloseContractNotifyVo> closeContractNotifyVoPagingResult=  rentContractServiceImpl.findFirstBillPayBeforeOvertimeForPage(paramMap);
        System.err.print(closeContractNotifyVoPagingResult);
        List<CloseContractNotifyVo> closeContractNotifyVos= closeContractNotifyVoPagingResult.getRows();
        for(CloseContractNotifyVo closeContractNotifyVo:closeContractNotifyVos){
            System.err.println(111111);
        }
    }
    @Test
    public void testFindRentDetailsByRentRoomDto(){
    	String parentId = "8a908e106092b36e016092c20f3c0044";
        RentRoomDto rentRoomDto = new RentRoomDto(parentId, BussTypeEnum.RENEWALSIGN.getCode());
    	System.out.println(rentContractServiceImpl.findRentDetailsByRentRoomDto(rentRoomDto));
    }

}
