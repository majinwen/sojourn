package com.ziroom.zrp.service.trading.service;

import com.ziroom.zrp.service.trading.dao.ShortUrlDao;
import com.ziroom.zrp.trading.entity.ShortUrlEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>短链</p>
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
@Service("trading.shortUrlServiceImpl")
public class ShortUrlServiceImpl {

    @Resource(name="trading.shortUrlDao")
    private ShortUrlDao shortUrlDao;

    /**
     * 保存短链
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public boolean saveShortUrlEntity(ShortUrlEntity shortUrl) {
        return true;
    }

}
