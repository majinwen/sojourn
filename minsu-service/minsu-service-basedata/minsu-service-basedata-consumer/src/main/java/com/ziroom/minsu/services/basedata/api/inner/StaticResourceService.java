package com.ziroom.minsu.services.basedata.api.inner;


/**
 * 
 * <p>静态资源服务接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public interface StaticResourceService {

	/**
	 * 分页查询静态资源列表
	 *
	 * @author liujun
	 * @created 2017年3月17日
	 *
	 * @param paramJson
	 * @return
	 */
	String findStaticResourceListByPage(String paramJson);

	/**
	 * 通过资源码查询资源集合
	 *
	 * @author lunan
	 * @created 2017年3月21日
	 *
	 * @param resCode
	 * @return
	 */
	String findStaticResListByResCode(String resCode);

	/**
	 * 添加静态资源主题
	 *
	 * @author lunan
	 * @created 2017年3月20日
	 *
	 * @param paramJson
	 * @return
	 */
    String saveStaticEntity(String paramJson);

    /**
     * 根据资源code查询静态资源内容
     * @author jixd
     * @created 2017年06月21日 14:08:26
     * @param
     * @return
     */
    String findStaticResourceByCode(String resCode);

    /**
     * 获取提示信息内容 包含二级标题和内容
     * @author jixd
     * @created 2017年06月22日 09:12:30
     * @param
     * @return
     */
    String getTipsMsgHasSubTitleByCode(String code);
    
    
    /**
     * 
     * 查询最新一个静态资源 根据code
     *
     * @author bushujie
     * @created 2017年7月5日 下午2:52:52
     *
     * @param resCode
     * @return
     */
    String findStaticResByResCode(String resCode);
    
    /**
     * 
     * 更新静态资源信息
     *
     * @author bushujie
     * @created 2017年7月6日 上午11:46:22
     *
     * @param paramJson
     * @return
     */
    String updateStaticResource(String paramJson);
	
}
