package com.ziroom.zrp.service.houses.dao;

import com.ziroom.zrp.houses.entity.RoomItemsConfigEntity;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.houses.entity.ExtRoomItemsConfigVo;
import org.junit.Test;

import javax.annotation.Resource;

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
 * @Date Created in 2017年10月31日 18:34
 * @since 1.0
 */

public class HouseItemsConfigDaoTest extends BaseTest{

    @Resource(name="houses.roomItemsConfigDao")
    private RoomItemsConfigDao roomItemsConfigDao;

    @Resource(name="houses.houseItemsConfigDao")
    private HouseItemsConfigDao houseItemsConfigDao;
    @Test
    public void testlistHouseItemsByHouseType(){
        houseItemsConfigDao.listHouseItemsByHouseType("ff80808148f9ac210148f9f06e900024");
    }

    @Test
    public void testselectRoomItemsConfigByParams(){
        RoomItemsConfigEntity ff80808148f280400148f2d63188001b = roomItemsConfigDao.getItemByRoomIdAndItemId("1002821", "ff80808148f280400148f2d63188001b");
    }

    @Test
    public void testlistHouseItemsExtByHouseTypeId(){
        houseItemsConfigDao.listHouseItemsExtByHouseTypeId("ff80808148f9ac210148f9c2820f001a");
    }

    @Test
    public void getItemByRoomIdAndItemId(){
        RoomItemsConfigEntity itemByRoomIdAndItemId = roomItemsConfigDao.getItemByRoomIdAndItemId("2c908d194f6e5e5f014f885fc5d70290", "2c908d194f6e5e5f014f88b0793402af");
    }
}
