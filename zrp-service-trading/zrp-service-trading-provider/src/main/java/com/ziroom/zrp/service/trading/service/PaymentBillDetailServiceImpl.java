package com.ziroom.zrp.service.trading.service;

import com.ziroom.zrp.service.trading.dao.PaymentBillDetailDao;
import com.ziroom.zrp.trading.entity.PaymentBillDetailEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年10月19日 18时37分
 * @Version 1.0
 * @Since 1.0
 */
@Service("trading.paymentBillDetailServiceImpl")
public class PaymentBillDetailServiceImpl {

    @Resource(name = "trading.paymentBillDetailDao")
    private PaymentBillDetailDao paymentBillDetailDao;

    public int savePaymentBillDetail(List<PaymentBillDetailEntity> paymentBillDetailEntityList) {
        return paymentBillDetailDao.savePaymentBillDetail(paymentBillDetailEntityList);
    }
}
