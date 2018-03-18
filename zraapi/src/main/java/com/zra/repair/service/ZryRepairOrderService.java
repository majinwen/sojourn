package com.zra.repair.service;

import com.github.pagehelper.PageInfo;
import com.zra.repair.entity.ZryRepairOrder;
import com.zra.repair.entity.ZryRepairOrderLog;
import com.zra.repair.entity.dto.ZryRepairOrderPageDto;

import java.util.Collection;

/**
 * <p>自如寓报修单处理</p>
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
public interface ZryRepairOrderService {

    /***
     * 分页查询报修单
     * @param repairOrderPage
     * @return
     */
    PageInfo<ZryRepairOrder> findByPaging(ZryRepairOrderPageDto repairOrderPage);

    /**
     * 保存报修单 和 操作日志
     * @param zryRepairOrder
     * @param zryRepairOrderLog
     * @return
     */
    boolean insertAndLog(ZryRepairOrder zryRepairOrder, ZryRepairOrderLog zryRepairOrderLog) throws Exception;

    /**
     * 查询报修单详情
     * @param fid
     * @return
     */
    ZryRepairOrder findByFId(String fid);

    /**
     * 废弃报修单并记录日志
     * @param orderLog 报修单操作日志
     * @return
     */
    boolean updateCancelAndLogByFId(ZryRepairOrderLog orderLog) throws Exception;

    /**
     * 删除报修单
     * is_del : 0=否, 1=是
     * @param fid
     * @return
     */
    boolean deleteByFId(String fid);

    /**
     * 查询全部报修单
     * @return
     */
    Collection<ZryRepairOrder> findAll();

    /**
     * 查询维修单，根据维修单单号
     * @param orderSn
     * @return
     * @throws Exception
     */
    ZryRepairOrder findByOrderSn(String orderSn) throws Exception;
    
    /**
     * 
     * 同步更新工单信息
     *
     * @author bushujie
     * @created 2017年10月12日 下午4:25:04
     *
     * @param orderSn
     * @param status
     * @return
     */
    boolean updateStatusByOrderSn(ZryRepairOrder zryRepairOrder);
}
