package com.zra.repair.service;

import com.zra.repair.entity.ZryRepairOrderLog;

import java.util.List;

/**
 * <p>自如寓报修日志处理</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author fengbo.yue
 * @Date Created in 2017年09月28日 15:32
 * @since 1.0
 * @version 1.0
 */
public interface ZryRepairOrderLogService {

    /**
     * 查询操作日志根据维修单Fid
     * @param rFid
     * @return
     */
    List<ZryRepairOrderLog> selectByRepairFid(String rFid) throws Exception;

    /***
     * 报修日志记录入库
     * @param zryRepairOrderLog
     * @return
     */
    boolean save(ZryRepairOrderLog zryRepairOrderLog);
}
