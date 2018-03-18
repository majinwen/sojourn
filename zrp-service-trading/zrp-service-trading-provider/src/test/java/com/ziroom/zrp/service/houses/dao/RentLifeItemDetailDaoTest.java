package com.ziroom.zrp.service.houses.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.service.trading.dto.ContractRoomDto;
import com.ziroom.zrp.trading.entity.RentLifeItemDetailEntity;
import org.junit.Test;

import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.RentLifeItemDetailDao;
import com.ziroom.zrp.service.trading.entity.LifeItemVo;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;
import com.ziroom.zrp.service.trading.entity.LifeItemVo;

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
 * @Date Created in 2017年09月20日 20:12
 * @since 1.0
 */
public class RentLifeItemDetailDaoTest extends BaseTest{

    @Resource(name="trading.rentLifeItemDetailDao")
    private RentLifeItemDetailDao rentLifeItemDetailDao;

    @Test
    public void testListLifeItemByContractIds() {

        List<String> contractIdList = new ArrayList<>();
        contractIdList.add("8a9091bd5f9439c7015f9439c76c0001");
        contractIdList.add("8a9e988f5ea28f9d015ea34f43910010");
        contractIdList.add("8a9091bd5f817475015f8174757d0000");

        List<LifeItemVo> list = rentLifeItemDetailDao.listLifeItemByContractIds(contractIdList);
        System.out.println("list:"+ list.size());
    }

    @Test
    public void testlistLifeItemEntityByContractIdAndRoomId(){
        ContractRoomDto build = ContractRoomDto.builder().contractId("8a9e989060547e8a0160547e8a6e0001").roomId("8a90a3ab57f4410a0157fbbc78451981").build();
        List<RentLifeItemDetailEntity> rentLifeItemDetailEntities = rentLifeItemDetailDao.listLifeItemEntityByContractIdAndRoomId(build);
        System.out.printf(JsonEntityTransform.Object2Json(rentLifeItemDetailEntities));
    }

}
