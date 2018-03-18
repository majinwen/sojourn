package com.ziroom.zrp.service.houses.api;

/**
 * <p>City</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月16日 15:06
 * @since 1.0
 */
public interface CityService {

    /**
     * 根据城市id查询城市实体
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    String findById(String id);

    /**
      * @description: 查询城市集合
      * @author: lusp
      * @date: 2017/10/19 下午 16:04
      * @params:
      * @return: String
      */
    String findCityList();
}
