package com.ziroom.zrp.service.trading.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.trading.entity.PaymentBillDetailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>付款单详情dao类</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年10月17日 19:55
 * @since 1.0
 */
@Repository("trading.paymentBillDetailDao")
public class PaymentBillDetailDao {

    private String SQLID = "trading.paymentBillDetailDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 插入付款单子表
     * @param paymentBillDetailEntityList 付款单子表
     * @return int
     * @author cuigh6
     * @Date 2017年10月19日
     */
    public int savePaymentBillDetail(List<PaymentBillDetailEntity> paymentBillDetailEntityList) {
        int affect = 0;
        for (PaymentBillDetailEntity entity : paymentBillDetailEntityList) {
            affect = mybatisDaoContext.save(SQLID + "savePaymentBillDetail", entity);
        }
        return affect;
    }

    /**
     * 生成付款单明细
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时13分58秒
     */
    public int savePaymentBillDetailSingle(PaymentBillDetailEntity paymentBillDetailEntityList) {
        return mybatisDaoContext.save(SQLID + "savePaymentBillDetail", paymentBillDetailEntityList);
    }
}