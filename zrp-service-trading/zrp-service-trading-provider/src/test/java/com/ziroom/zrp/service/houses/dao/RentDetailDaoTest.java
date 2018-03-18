package com.ziroom.zrp.service.houses.dao;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.paging.PagingResult;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.RentContractDao;
import com.ziroom.zrp.service.trading.dao.RentDetailDao;
import com.ziroom.zrp.service.trading.dto.ContractPageDto;
import com.ziroom.zrp.service.trading.dto.ContractRoomDto;
import com.ziroom.zrp.service.trading.entity.DeliveryContractNotifyVo;
import com.ziroom.zrp.trading.entity.RentDetailEntity;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
 * @Date Created in 2017年09月22日 17:21
 * @since 1.0
 */
public class RentDetailDaoTest extends BaseTest{

    @Resource(name = "trading.rentDetailDao")
    private RentDetailDao rentDetailDao;

    @Test
    public void testfindRentDetailById(){
        ContractRoomDto contractRoomDto = new ContractRoomDto();
        contractRoomDto.setContractId("8a90a3ab58241322015824c5240f0069");
        contractRoomDto.setRoomId("1001513");
        rentDetailDao.findRentDetailById(contractRoomDto);
    }

    @Test
    public void testlistUnDeliveryContract(){
        ContractPageDto contractPageDto = new ContractPageDto();
        PagingResult<DeliveryContractNotifyVo> list = rentDetailDao.listUnDeliveryContract(contractPageDto);
        System.err.println(JSONObject.toJSONString(list));
    }

    @Test
    public void testFindRentDetailByContractIds() {
        List<String> contractIds = new ArrayList<>();
        contractIds.add("8a9099cb57d21f2e0157d23a345b000b");
        contractIds.add("8a9099cb5b1e7f0e015b24f1e8840e5f");

        List<RentDetailEntity>  rentDetailEntityList = rentDetailDao.findRentDetailByContractIds(contractIds);
        System.out.println("size:" + (rentDetailEntityList == null? "NULL" : rentDetailEntityList.size()) );
    }

}
