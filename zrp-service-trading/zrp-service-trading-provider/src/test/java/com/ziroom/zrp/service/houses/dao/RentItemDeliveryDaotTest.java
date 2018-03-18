package com.ziroom.zrp.service.houses.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.RentItemDeliveryDao;
import com.ziroom.zrp.service.trading.dao.RentLifeItemDetailDao;
import com.ziroom.zrp.trading.entity.RentItemDeliveryEntity;

import org.junit.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

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
 * @Date Created in 2017年09月21日 15:06
 * @since 1.0
 */
public class RentItemDeliveryDaotTest extends BaseTest{

    @Resource(name="trading.rentItemDeliveryDao")
    private RentItemDeliveryDao rentItemDeliveryDao;

    @Test
    public void testlistValidItemByContractIdAndRoomId(){
//        List<RentItemDeliveryEntity> rentItemDeliveryEntities = rentItemDeliveryDao.listValidItemByContractIdAndRoomId("8a908ead5e990104015e993438e70053", "8a908ead5e703616015e89923ce904cb");
    }

    @Test
    public void listValidItemByContractIds() {
        List<String> contractIdList = new ArrayList<>();
        contractIdList.add("2c908d174e05edef014e3840b2b202ca");
        contractIdList.add("2c908d174e05edef014e3840b2b202ca");
        contractIdList.add("2c908d174e4d9a9c014e4e06ecf3004d");
        List<RentItemDeliveryEntity> rentItemDeliveryEntityList = rentItemDeliveryDao.listValidItemByContractIds(contractIdList);
        System.out.println("listValidItemByContractIds:" + rentItemDeliveryEntityList.size());
    }
    @Test
    public void testlistItemDeliverysByItemIds(){
    	String itemIds = "ff80808149090c570149090c68bd0005,ff80808149090c570149090c68bd0005";
    	List<String> ids = new ArrayList<String>();
    	ids.add("ff80808149090c570149090c68bd0005");
    	ids.add("ff80808149090c570149090c68bd0005");
    	List<RentItemDeliveryEntity> rentItemDeliveryEntityList = rentItemDeliveryDao.listItemDeliverysByItemIds(ids);
    	System.out.println(JsonEntityTransform.Object2Json(rentItemDeliveryEntityList));
    }
}
