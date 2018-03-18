package com.ziroom.zrp.service.trading.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.ziroom.zrp.service.trading.api.ShortUrlService;
import com.ziroom.zrp.service.trading.service.ShortUrlServiceImpl;
import com.ziroom.zrp.service.trading.utils.ShortUrlUtils;
import com.ziroom.zrp.trading.entity.ShortUrlEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>短链proxy类</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年09月26日 13:57
 * @since 1.0
 */
@Component("trading.shortUrlServiceProxy")
public class ShortUrlServiceProxy implements ShortUrlService {

    @Resource(name="trading.shortUrlServiceImpl")
    private ShortUrlServiceImpl shortUrlServiceImpl;

    /**
     * 生成短链对象
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public String saveShortUrlEntity(String longUrl) {
        String suid = ShortUrlUtils.shortUrl(longUrl);
        ShortUrlEntity shortUrlEntity = new ShortUrlEntity(suid, longUrl, new Date());
        shortUrlServiceImpl.saveShortUrlEntity(shortUrlEntity);
        return null;
    }
}
