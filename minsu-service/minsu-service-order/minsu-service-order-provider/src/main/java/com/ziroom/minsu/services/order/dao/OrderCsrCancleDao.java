package com.ziroom.minsu.services.order.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinancePenaltyPayRelEntity;
import com.ziroom.minsu.entity.order.OrderCsrCancleEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * 客服取消订单记录
 * @author jixd
 * @created 2017年05月10日 11:08:07
 * @param
 * @return
 */
@Repository("order.orderCsrCancleDao")
public class OrderCsrCancleDao {

    private static Logger logger = LoggerFactory.getLogger(OrderCsrCancleDao.class);

    private String SQLID = "order.orderCsrCancleDao.";

    @Autowired
    @Qualifier("order.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 保存记录
     * @author jixd
     * @created 2017年05月10日 10:52:17
     * @param
     * @return
     */
    public int saveOrderCsrCancle(OrderCsrCancleEntity orderCsrCancleEntity) {
        if (Check.NuNObj(orderCsrCancleEntity)){
            LogUtil.error(logger, "orderCsrCancleEntity is null on saveOrderCsrCancle");
            throw new BusinessException("orderCsrCancleEntity is null on saveOrderCsrCancle");
        }
        if(Check.NuNStr(orderCsrCancleEntity.getFid())){
        	orderCsrCancleEntity.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID + "insertSelective", orderCsrCancleEntity);
    }
    
	/**
	 * 查询该房东6个月内，申请取消订单的总数
	 *
	 * @author loushuai
	 * @created 2017年5月10日 下午7:16:41
	 *
	 * @param landlordUid
	 * @return
	 */
	public long getCountInTimes(String landlordUid, Date beginTime, Date endTime) {
		 Map<String,Object> paramMap = new HashMap<>();
		 paramMap.put("landlordUid",landlordUid);
		 paramMap.put("beginTime",beginTime);
		 paramMap.put("endTime",endTime);
		return mybatisDaoContext.count(SQLID + "getCountInTimes", paramMap);
	}

	/**
	 * 
	 * 根据orderSn更新punishStatu
	 * @author loushuai
	 * @created 2017年5月12日 下午2:34:54
	 *
	 * @param orderSn 
	 * @param punishStatu
	 */
	public int updateOrderCsrCancle(String orderSn, Integer punishStatu) {
		 Map<String,Object> paramMap = new HashMap<>();
		 paramMap.put("orderSn",orderSn);
		 paramMap.put("punishStatu",punishStatu);
		 return mybatisDaoContext.update(SQLID + "updateOrderCsrCancle", paramMap);
	}

	/**
	 * 查询t_order_csr_cancle（订单取消表）所有cancle_type=38（房东取消订单）punish_statu=10（处理未完成）is_del=0（未失效）
	 *
	 * @author loushuai
	 * @created 2017年5月16日 下午5:34:37
	 *
	 * @return
	 */
	public List<OrderCsrCancleEntity> getDoFailLandQXOrderPunish(Integer cancleType, Integer cancleReasonCode, Integer punishStatu) {
		if(Check.NuNObj(cancleType) && Check.NuNObj(cancleReasonCode) && Check.NuNObj(punishStatu)){
			LogUtil.error(logger,"all param is null on getDoFailLandQXOrderPunish");
			throw new BusinessException("all param is null on getDoFailLandQXOrderPunish");
		}
		 Map<String,Object> paramMap = new HashMap<>();
		 paramMap.put("cancleType",cancleType);
		 paramMap.put("cancleReasonCode",cancleReasonCode);
		 paramMap.put("punishStatu",punishStatu);
		return mybatisDaoContext.findAll(SQLID+"getDoFailLandQXOrderPunish", OrderCsrCancleEntity.class, paramMap);
	}

}
