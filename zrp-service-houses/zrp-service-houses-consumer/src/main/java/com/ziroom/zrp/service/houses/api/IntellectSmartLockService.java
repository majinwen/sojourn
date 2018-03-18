package com.ziroom.zrp.service.houses.api;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2017年12月06日 20:41
 * @since 1.0
 */
public interface IntellectSmartLockService {

    /**
     * 保存
     * @author yd
     * @created
     * @param
     * @return
     */
    String saveRoomSmartLock(String paramsJson);

    /**
     *
     * 条件删除智能锁密码
     *
     * @author bushujie
     * @created 2017年12月7日 上午10:49:00
     *
     * @param paramsJson
     * @return
     */
    String delRoomSmartLock(String paramsJson);


    /**
     * 分页获取密码锁记录
     * @param paramsJson
     * @return
     */
    String pagingSmartLock(String paramsJson);
    
    /**
     * 
     * 业务fid更新智能锁密码下发状态
     *
     * @author bushujie
     * @created 2017年12月12日 上午11:43:15
     *
     * @param paramJson
     * @return
     */
    String upSmartLockStatusByServiceId(String paramJson);

    /**
     *
     * 获取失败记录
     *
     * @author zhangyl2
     * @created 2018年01月05日 11:30
     * @param
     * @return
     */
    String getFailSmartLockRecord(String paramJson);

    /**
     *
     * 根据fid更新智能锁
     *
     * @author zhangyl2
     * @created 2017年12月20日 14:45
     * @param
     * @return
     */
    String updateIntellectSmartLockEntity(String paramJson);
}
