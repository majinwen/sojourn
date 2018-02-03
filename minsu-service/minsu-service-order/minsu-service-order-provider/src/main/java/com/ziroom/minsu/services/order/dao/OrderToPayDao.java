package com.ziroom.minsu.services.order.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderPayEntity;
import com.ziroom.minsu.entity.order.OrderToPayEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>订单去支付</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/9/22.
 * @version 1.0
 * @since 1.0
 */
@Repository("order.toPayDao")
public class OrderToPayDao {


    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(OrderToPayDao.class);

    private String SQLID = "order.toPayDao.";

    @Autowired
    @Qualifier("order.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 保存去支付对象
     * @author afi
     * @param orderToPayEntity
     * @return
     */
    public int insertToPay(OrderToPayEntity orderToPayEntity) {
        if(Check.NuNObj(orderToPayEntity)){
            LogUtil.info(logger,"current orderToPayEntity is null on insertOrderToPay");
        }
        return mybatisDaoContext.save(SQLID + "insertToPay", orderToPayEntity);
    }


    /**
     * 获取当前的支付code
     * @author afi
     * @param payCode
     * @return
     */
    public OrderToPayEntity selectByPayCode(String payCode){
        return mybatisDaoContext.findOne(SQLID + "selectByPayCode", OrderToPayEntity.class, payCode);
    }


    /**
     * 获取当前的支付code
     * @author afi
     * @param orderSn
     * @return
     */
    public OrderToPayEntity selectByOrderSn(String orderSn){
        return mybatisDaoContext.findOne(SQLID + "selectByOrderSn", OrderToPayEntity.class, orderSn);
    }

}
