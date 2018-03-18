package com.ziroom.zrp.service.trading.api;

/**
 * <p>短链服务</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年09月26日 17:19
 * @since 1.0
 */
public interface ShortUrlService {

    /**
     *  生成短链对象
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    String saveShortUrlEntity(String longUrl);

}
