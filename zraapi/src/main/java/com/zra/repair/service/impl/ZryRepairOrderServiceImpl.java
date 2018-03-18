package com.zra.repair.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zra.repair.dao.ZryRepairOrderLogMapper;
import com.zra.repair.dao.ZryRepairOrderMapper;
import com.zra.repair.entity.ZryRepairOrder;
import com.zra.repair.entity.ZryRepairOrderLog;
import com.zra.repair.entity.dto.ZryRepairOrderPageDto;
import com.zra.repair.service.ZryRepairOrderService;
import org.apache.commons.beanutils.BeanUtils;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>自如寓报修单处理</p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author fengbo.yue
 * @Date Created in 2017年09月28日 15:34
 * @since 1.0
 * @version 1.0
 */
@Service
public class ZryRepairOrderServiceImpl implements ZryRepairOrderService {

    private static final Logger log = LoggerFactoryProxy.getLogger(ZryRepairOrderServiceImpl.class.getName());

    @Autowired
    private ZryRepairOrderMapper zryRepairOrderMapper;

    @Autowired
    private ZryRepairOrderLogMapper zryRepairOrderLogMapper;

    @Override
    public PageInfo<ZryRepairOrder> findByPaging(ZryRepairOrderPageDto repairOrderPage) {

        // 条件查询
        Map<String, String> searchMap = null;
        try {
            searchMap = BeanUtils.describe(repairOrderPage);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        searchMap.put("isDel", "0");

        // 分页查询
        PageHelper.startPage(repairOrderPage.getPageNum(),repairOrderPage.getPageSize());
        List<ZryRepairOrder> zryRepairOrders = this.zryRepairOrderMapper.selectByCondition(searchMap);

        PageInfo<ZryRepairOrder> pageInfo = new PageInfo<>(zryRepairOrders);
        long totalSize = pageInfo.getTotal();// 总条目数

        return pageInfo;
    }

    @Override
    public boolean insertAndLog(ZryRepairOrder zryRepairOrder, ZryRepairOrderLog zryRepairOrderLog) throws Exception {
        boolean result;
        try {
            int iorder = zryRepairOrderMapper.insertSelective(zryRepairOrder);
            int ilog = zryRepairOrderLogMapper.insertSelective(zryRepairOrderLog);
            result = (iorder > 0 && (ilog > 0));
        } catch (Exception e) {
            String message = "";
            log.error("Ziroom Apartment saveAndLog repairOrder error : \n ZryRepairOrder : {}; \n" +
                    " ZryRepairOrderLog : {};", JSONObject.toJSONString(zryRepairOrder) , JSONObject.toJSONString(zryRepairOrderLog),e);

            message = "saveAndLog repairOrder error, cause by : ";

            if (e instanceof SQLException) {
                message += String.format("SQL excute error : %s", e.getMessage());
            } else {
                message += e.getMessage();
            }

            throw new Exception(message, e);
        }
        return result;
    }

    @Override
    public boolean updateCancelAndLogByFId(ZryRepairOrderLog orderLog) throws Exception {
        boolean result = false;
        String fid = orderLog.getRepairOrder();

        ZryRepairOrder zryRepairOrder;
        try {
            zryRepairOrder = zryRepairOrderMapper.selectByFId(fid);
            orderLog.setFromStatus(zryRepairOrder.getOrderStatus());// 当前状态为日志表的fromstatus

            if (zryRepairOrder == null) {
                throw new Exception(String.format("no any order for fid : %s", fid));
            }
            if (zryRepairOrder.cancelled()) {
                //throw new Exception(String.format("the repair order has been cancelled, fid : %s", fid));
                return true;
            }
            if (zryRepairOrder.cancelOrCannot()){
                int statusFlag = zryRepairOrderMapper.updateStatusByFId(zryRepairOrder.getFid(), ZryRepairOrder.OrderStatus.CANCEL.getStatus());

                int logFlag = zryRepairOrderLogMapper.insertSelective(orderLog);
                if (statusFlag > 0 && logFlag > 0) {
                    result = true;
                }
            } else {
                throw new Exception(String.format("the repaid order can not be cancelled ! current status : %s", ZryRepairOrder.OrderStatus.of(zryRepairOrder.getOrderStatus())));
            }
        } catch (MyBatisSystemException | DataIntegrityViolationException e) {
            log.error("Ziroom Apartment repairOrder of method cancelAndLogByFId error, ZryRepairOrderLog : \n{}", JSONObject.toJSONString(orderLog), e);
            throw new Exception("cancelAndLogByFId sql has error , cause by : " + e.getMessage());
        } catch (Exception e) {
            log.error("Ziroom Apartment repairOrder of method cancelAndLogByFId error, ZryRepairOrderLog : \n{}", JSONObject.toJSONString(orderLog), e);
            throw e;
        }

        return result;
    }

    @Override
    public boolean deleteByFId(String fid) {
        boolean result = false;

        ZryRepairOrder zryRepairOrder;
        try {
            zryRepairOrder = zryRepairOrderMapper.selectByFId(fid);

            if (zryRepairOrder.cancelOrCannot()){
                int statusFlag = zryRepairOrderMapper.updateStatusByFId(zryRepairOrder.getFid(), ZryRepairOrder.OrderStatus.CANCEL.getStatus());
                if (statusFlag > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return result;
    }

    @Override
    public Collection<ZryRepairOrder> findAll() {
        return zryRepairOrderMapper.selectAll();
    }

    @Override
    public ZryRepairOrder findByOrderSn(String orderSn) throws Exception {
        return zryRepairOrderMapper.selectByOrderSn(orderSn);
    }

    @Override
    public ZryRepairOrder findByFId(String fid) {
        return zryRepairOrderMapper.selectByFId(fid);
    }

	/* (non-Javadoc)
	 * @see com.zra.repair.service.ZryRepairOrderService#updateStatusByOrderSn(com.zra.repair.entity.ZryRepairOrder)
	 */
	@Override
	public boolean updateStatusByOrderSn(ZryRepairOrder zryRepairOrder) {
		boolean result = false;
		int statusFlag=zryRepairOrderMapper.updateStatusByOrderSn(zryRepairOrder.getOrderSn(), zryRepairOrder.getOrderStatus());
		if(statusFlag>0){
			result=true;
		}
		return result;
	}

}
