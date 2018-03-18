package com.ziroom.zrp.service.houses.proxy;

import com.ziroom.zrp.service.houses.base.BaseTest;
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
 * @Date Created in 2017年10月31日 18:16
 * @since 1.0
 */
public class ItemServiceProxyTest extends BaseTest{

    @Resource(name = "houses.itemsServiceProxy")
    private ItemsServiceProxy itemsServiceProxy;

    @Test
    public void testlistItems(){
        String s = itemsServiceProxy.listItems("", "");
        System.err.println(s);
    }

    @Test
    public void testlistItemsByRoomIdAndHouseType(){

        String s = itemsServiceProxy.listItemsByRoomIdAndHouseType("1002650");
        System.err.println(s);
    }

}
