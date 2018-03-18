package com.ziroom.zrp.service.houses.api;

/**
 * <p>智能水电接口</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author phil
 * @Date Created in 2018年02月04日 19:18
 * @Version 1.0
 * @Since 1.0
 */
public interface WaterWattSmartService {

    /**
     * 智能水电分页接口
     *
     * @param paramJson
     * @return
     */
    String pagingWaterWatt(String paramJson);
}
