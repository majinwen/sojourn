package com.ziroom.zrp.service.houses.dep;

import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dep.RoomDep;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月27日 18:45
 * @since 1.0
 */
public class RoomDepTest extends BaseTest {

    @Resource(name = "trading.roomDep")
    private RoomDep roomDep;

    @Test
    public void testGetRoomListByRoomIds() {
        String roomIds = "1001238,1001239";
        List<RoomInfoEntity> roomInfoEntityList = roomDep.getRoomListByRoomIds(roomIds);
        System.out.println("result:" + roomInfoEntityList.size());
    }
}
