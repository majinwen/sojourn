package com.ziroom.minsu.services.cms.api.inner;

/**
 * <p>组相关功能</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月12日 16:38
 * @since 1.0
 */
public interface GroupService {

    /**
      * @description: 分页查询分组列表
      * @author: lusp
      * @date: 2017/10/17 下午 18:21
      * @params: paramJson
      * @return: String
      */
    String listGroupByType(String paramJson);

    String listGroupActRelByActSn(String actSn);

    String listUserRelByPage(String paramJson);

    String deleteUserRelBatch(String paramJson);

    String saveUserRel(String paramJson);

    /**
      * @description: 新增分组信息
      * @author: lusp
      * @date: 2017/10/17 下午 18:22
      * @params: paramJson
      * @return: String
      */
    String saveGroup(String paramJson);

    /**
     * @description: 删除组
     * @author: lusp
     * @date: 2017/10/18 下午 10:22
     * @params: paramJson
     * @return: String
     */
    String deleteGroup(String paramJson);

    /**
     * @description: 删除组成员
     * @author: lusp
     * @date: 2017/10/18 下午 10:25
     * @params: paramJson
     * @return: String
     */
    String deleteHouseRel(String paramJson);

    /**
     * @description: 分页查询房源组关系
     * @author: lusp
     * @date: 2017/10/18 下午 16:25
     * @params: paramJson
     * @return: String
     */
    String listHouseRelByPage(String paramJson);

    /**
      * @description: 批量添加房源组
      * @author: lusp
      * @date: 2017/10/20 上午 11:32
      * @params: paramJson
      * @return: String
      */
    String addHouseRelBatch(String paramJson);

    /**
     * 用户参加活动 添加活动组
     *
     * @param
     * @return
     * @author jixd
     * @created 2018年01月26日 13:33:38
     */
    String userAddGroupAct(String paramJson);

}
