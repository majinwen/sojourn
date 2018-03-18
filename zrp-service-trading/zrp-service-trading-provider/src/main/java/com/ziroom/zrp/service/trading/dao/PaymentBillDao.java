package com.ziroom.zrp.service.trading.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.trading.entity.PaymentBillEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>付款单dao类</p>
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
@Repository("trading.paymentBillDao")
public class PaymentBillDao {

    private String SQLID = "trading.paymentBillDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 保存付款单 主表
     * @param entities 列表
     * @return int
     * @author cuigh6
     * @Date 2017年10月19日
     */
    public Integer savePaymentBill(List<PaymentBillEntity> entities) {
        int affect = 0;
        for (PaymentBillEntity entity : entities) {
            affect = mybatisDaoContext.save(SQLID + "savePaymentBill", entity);
        }
        return affect;
    }

    /**
     * 更改付款单同步状态
     * @param v 主表参数
     * @return int
     * @author cuigh6
     * @Date 2017年10月19日
     */
    public Integer updatePaymentSyncStatus(PaymentBillEntity v) {
        return this.mybatisDaoContext.update(SQLID + "updatePaymentSyncStatus", v);
    }

    /**
     * 生成付款单
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时13分10秒
     */
    public Integer savePaymentBillSingle(PaymentBillEntity entity) {
        return mybatisDaoContext.save(SQLID + "savePaymentBill", entity);
    }

    /**
     * 删除付款单以及对应的明细
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时13分24秒
     */
    public int deleteBySurrenderId(String surrenderId) {
        Map map = new HashMap();
        map.put("surrenderId", surrenderId);
        return mybatisDaoContext.update(SQLID + "deleteBySurrenderId", map);
    }
}