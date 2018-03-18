package com.zra.repair.logic;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.github.pagehelper.PageInfo;
import com.zra.repair.entity.ZryRepairOrder;
import com.zra.repair.entity.ZryRepairOrderLog;
import com.zra.repair.entity.dto.ZryRepairOrderPageDto;
import com.zra.repair.service.ZryRepairOrderLogService;
import com.zra.repair.service.ZryRepairOrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * <p>报修单业务处理</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author fengbo.yue
 * @Date Created in 2017年09月28日 16:23
 * @since 1.0
 * @version 1.0
 */
@Component
public class ZryRepairOrderLogic {

    private final static Logger log = LoggerFactoryProxy.getLogger(ZryRepairOrderLogic.class);

    @Autowired
    private ZryRepairOrderService zryRepairOrderService;

    @Autowired
    private ZryRepairOrderLogService zryRepairOrderLogService;

    /**
     * 分页条件查询
     * @param repairOrderPage
     * @return
     */
    public PageInfo<ZryRepairOrder> pagingRepairOrder(ZryRepairOrderPageDto repairOrderPage) {
        return zryRepairOrderService.findByPaging(repairOrderPage);
    }

    /**
     * 保存报修单，记录报修日志
     * @param zryRepairOrder 报修单
     * @param zryRepairOrderLog 报修操作日志
     * @return
     */
    public boolean saveAndLog(ZryRepairOrder zryRepairOrder, ZryRepairOrderLog zryRepairOrderLog) throws Exception {

        boolean result;

        if (zryRepairOrder == null || zryRepairOrderLog == null) {
            throw new Exception("repairOrder and orderLog must be not null!");
        }

        try {
            result = zryRepairOrderService.insertAndLog(zryRepairOrder, zryRepairOrderLog);
        } catch (Exception e) {
            log.error("Ziroom apartment repairOrder saveAndLog error, \n ZryRepairOrder:[{}],\n" +
                    " ZryRepairOrderLog:[{}]", zryRepairOrder, zryRepairOrderLog, e);
            throw e;
        }

        return result;
    }

    /***
     * 查询全部报修单
     * @return
     */
    public Collection<ZryRepairOrder> listAll() {
        return zryRepairOrderService.findAll();
    }

    /***
     * 查看详情
     * @param fid
     * @return
     */
    public ZryRepairOrder findByFId(String fid) {
        return zryRepairOrderService.findByFId(fid);
    }

    /***
     * 废弃报修单并且记录废弃日志
     * @param orderLog
     * @return
     */
    public boolean cancelAndLogByFId(ZryRepairOrderLog orderLog) throws Exception{
        boolean cancel;

        if (orderLog != null && StringUtils.isBlank(orderLog.getRepairOrder())) {
            throw new Exception("Miss required parameter!");
        }

        try {
            cancel = zryRepairOrderService.updateCancelAndLogByFId(orderLog);
        } catch (Exception e) {
            log.error("Ziroom apartment repairOrder cancelAndLogByFId error, \n ZryRepairOrder:[{}]" , orderLog, e);
            throw e;
        }
        return cancel;
    }

    /**
     * 删除报修单
     * @param fid
     * @return
     */
    public boolean deleteByFId(String fid) {
        return zryRepairOrderService.deleteByFId(fid);
    }

    /***
     * 查看报修单下的操作日志
     * @param fid
     * @return
     */
    public List<ZryRepairOrderLog> findLogByFid(String fid) throws Exception {

        List<ZryRepairOrderLog> zryRepairOrders = null;

        if (StringUtils.isBlank(fid)) {
            throw new Exception("Miss required parameter!");
        }

        try {
            zryRepairOrders = zryRepairOrderLogService.selectByRepairFid(fid);
        } catch (Exception e) {
            log.error("Ziroom apartment repairOrder findLogByFid error, \n fid:[{}]" , fid, e);
            throw e;
        }

        return zryRepairOrders;
    }

    public ZryRepairOrder findByOrderSn(String orderSn) {

        ZryRepairOrder zryRepairOrder = null;

        try {
            zryRepairOrder = this.zryRepairOrderService.findByOrderSn(orderSn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zryRepairOrder;
    }
}
