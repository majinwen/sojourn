package com.ziroom.zrp.service.houses.api;


/**
 * <p>项目接口</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月06日 10:34
 * @since 1.0
 */
public interface ProjectService {

    /**
     * 根据编号查找项目信息
     * @author jixd
     * @created 2017年09月07日 09:21:13
     * @param
     * @return
     */
    String findProjectByCode(String paramJson);

	/**
	 * 根据项目标识查询项目信息
	 * @author xiangb
	 * @created 2017年9月13日
	 * @param
	 * @return
	 */
    String findProjectById(String projectId);

    /**
     * 查询基本水电费信息
     * @author jixd
     * @created 2017年09月25日 18:11:02
     * @param
     * @return
     */
    String findCostStandardByProjectId(String projectId);

    /**
     * 
     * 根据项目id和水电类型 更新水电费标准
     * 
     * @author zhangyl2
     * @created 2018年02月09日 16:19
     * @param 
     * @return 
     */
    String updateCostStandard(String paramJson);

    /**
     *
     * 更新项目表
     *
     * @author zhangyl2
     * @created 2018年02月09日 17:27
     * @param
     * @return
     */
    String updateProjectByFid(String paramJson);

    /**
      * @description: 组装活动分组中的各种名称
      * @author: lusp
      * @date: 2017/10/18 下午 18:10
      * @params: paramJson
      * @return: String
      */
    String assembleGroupHouseRelEntitys(String paramJson);

    /**
      * @description: 分页查询项目列表
      * @author: lusp
      * @date: 2017/10/19 下午 17:11
      * @params: paramJson
      * @return: String
      */
    String findProjectListForPage(String paramJson);

    /**
     * @description: 分页查询楼栋列表
     * @author: lusp
     * @date: 2017/11/1 下午 19:13
     * @params: paramJson
     * @return: String
     */
    String findBuildingListForPage(String paramJson);

    /**
     *  查询对应项目的对应户型
     *  说明 ：此处 稍后可以加上缓存
     * @author yd
     * @created
     * @param
     * @return
     */
    public String queryAllPro(String paramJson);
    
    /**
     * 
     * 查询用户项目权限列表
     *
     * @author bushujie
     * @created 2017年12月8日 下午2:58:24
     *
     * @param paramJson
     * @return
     */
    public String userProjectList(String empCode);

    String getProjectInfoForApp(String projectId);
}
