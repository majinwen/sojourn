package com.ziroom.zrp.service.houses.proxy;

import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.proxy.ShortUrlServiceProxy;
import org.junit.Test;

import javax.annotation.Resource;

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
 * @Date Created in 2017年09月27日 10:29
 * @since 1.0
 */
public class ShortUrlServiceProxyTest extends BaseTest {

    @Resource(name="trading.shortUrlServiceProxy")
    private ShortUrlServiceProxy shortUrlServiceProxy;

    @Test
    public void testSaveShortUrlEntity() {
        String longUrl = "";
        String result = shortUrlServiceProxy.saveShortUrlEntity(longUrl);
        System.out.println("result:" + result);

    }
}
