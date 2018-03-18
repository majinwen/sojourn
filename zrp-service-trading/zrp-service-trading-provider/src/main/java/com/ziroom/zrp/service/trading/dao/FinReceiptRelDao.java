package com.ziroom.zrp.service.trading.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.trading.entity.FinReceiptRelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>收款单与应收账单关系</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年11月21日 11:44
 * @since 1.0
 */
@Repository("trading.finReceiptRelDao")
public class FinReceiptRelDao {

    private String SQLID = "trading.finReceiptRelDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 保存收款关系
     * @author jixd
     * @created 2017年11月21日 11:47:18
     * @param
     * @return
     */
    public int saveFinReceiptRel(FinReceiptRelEntity finReceiptRelEntity){
        if (Check.NuNStr(finReceiptRelEntity.getFid())){
            finReceiptRelEntity.setFid(UUIDGenerator.hexUUID());
        }
       return mybatisDaoContext.save(SQLID + "insert",finReceiptRelEntity);
    }

    /**
      * @description: 根据应收账单编号获取对应的首款单编号
      * @author: lusp
      * @date: 2017/12/6 下午 14:53
      * @params: finReceiptRelEntity
      * @return: List<String>
      */
    public List<String> findReceiNumsByBillNum(String receiptBillNum){
        return mybatisDaoContext.findAll(SQLID + "findReceiNumsByBillNum",String.class,receiptBillNum);
    }

    /**
     * @description: 根据收款单编号更新
     * @author: lusp
     * @date: 2017/11/9 下午 14:23
     * @params: finReceiptRelEntity
     * @return: int
     */
    public int updateByReceiptedNo(FinReceiptRelEntity finReceiptRelEntity){
        return mybatisDaoContext.update(SQLID + "updateByReceiptedNo", finReceiptRelEntity);
    }

}
