package com.ziroom.minsu.services.basedata.api.inner;

/**
 * <p>大区服务</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd on 2017年01月09日 10:50:11
 * @since 1.0
 * @version 1.0
 */
public interface CityRegionService {

    /**
     * 插入城市大区表
     * @author jixd
     * @created 2017年01月09日 10:52:30
     * @param
     * @return
     */
    String insertCityRegion(String paramJson);
    /**
     * 插入大区省份关联表
     * @author jixd
     * @created 2017年01月09日 10:52:44
     * @param
     * @return
     */
    String insertCityRegionRel(String paramJson);
    /**
     * 更新城市大区
     * @author jixd
     * @created 2017年01月09日 10:53:02
     * @param
     * @return
     */
    String updateCityRegion(String paramJson);
    /**
     * 更新城市大区关联表
     * @author jixd
     * @created 2017年01月09日 10:53:17
     * @param
     * @return
     */
    String updateCityRegionRel(String paramJson);

    /**
     * 大区fid
     * @author jixd
     * @created 2017年01月10日 11:51:53
     * @param
     * @return
     */
    String delCityRegion(String fid);

    /**
     * 查找所有大区
     * @author jixd
     * @created 2017年01月10日 14:34:07
     * @param
     * @return
     */
    String fillAllRegion();

}
