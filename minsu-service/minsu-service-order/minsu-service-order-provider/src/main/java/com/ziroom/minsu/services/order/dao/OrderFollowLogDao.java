package com.ziroom.minsu.services.order.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderFollowLogEntity;
import com.ziroom.minsu.entity.order.OrderLogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>订单跟进记录</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/12/14.
 * @version 1.0
 * @since 1.0
 */
@Repository("order.orderFollowLogDao")
public class OrderFollowLogDao {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(OrderFollowLogDao.class);

    private String SQLID = "order.orderFollowLogDao.";

    @Autowired
    @Qualifier("order.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 获取跟进表的操作记录
     * @author afi
     * @created 2016年12月18日 下午20:22:38
     * @return
     */
    public List<OrderFollowLogEntity> getOrderFollowLogListByOrderSn(String orderSn) {
        if (Check.NuNStr(orderSn)){
            return null;
        }
        return mybatisDaoContext.findAll(SQLID + "getOrderFollowLogListByOrderSn", OrderFollowLogEntity.class,orderSn);
    }


    /**
     * 保存订单的操作记录
     * @author afi
     * @created 2016年3月31日 下午20:22:38
     * @param log
     * @return
     */
    public int saveOrderFollowLog(OrderFollowLogEntity log) {
        if(Check.NuNObj(log)){
            LogUtil.info(logger,"current log is null on insertOrderFollowLog");
            throw new BusinessException("current log is null on insertOrderFollowLog");
        }
        if(Check.NuNStr(log.getOrderSn())){
            LogUtil.info(logger,"orderSn is null on insertOrderFollowLog");
            throw new BusinessException("orderSn is null on insertOrderFollowLog");
        }
        if(Check.NuNStr(log.getFollowFid())){
            LogUtil.info(logger,"follow_fid is null on insertOrderFollowLog");
            throw new BusinessException("follow_fid is null on insertOrderFollowLog");
        }


        if(Check.NuNObj(log.getOrderStatus())){
            LogUtil.info(logger,"orderStatus is null on insertOrderFollowLog");
            throw new BusinessException("orderStatus is null on insertOrderFollowLog");
        }

        if(Check.NuNStr(log.getFid())){
            log.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID + "saveOrderFollowLog", log);
    }

}


