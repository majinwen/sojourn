package com.ziroom.zrp.service.trading.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.trading.entity.ReceiptEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>收款单dao</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年11月09日 09:51
 * @since 1.0
 */
@Repository("trading.receiptDao")
public class ReceiptDao {

    private String SQLID = "trading.receiptDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
      * @description: 根据收款单编号更新
      * @author: lusp
      * @date: 2017/11/9 下午 14:23
      * @params: receiptEntity
      * @return: int
      */
    public int updateByBillNum(ReceiptEntity receiptEntity){
        return mybatisDaoContext.update(SQLID + "updateByBillNum", receiptEntity);
    }

    /**
     * 保存收款单
     * @param receiptEntity 收款单对象
     * @return affect
     * @author cuigh6
     * @Date 2017年11月11日
     *
     */
    public int saveReceipt(ReceiptEntity receiptEntity) {
        if (Check.NuNStr(receiptEntity.getFid())){
            receiptEntity.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID + "saveReceipt", receiptEntity);
    }

    /**
      * @description: 保存收款单并返回fid（保存收款单主记录时使用）
      * @author: lusp
      * @date: 2017/12/1 下午 15:50
      * @params: receiptEntity
      * @return: String
      */
    public String saveReceiptAndReturnFid(ReceiptEntity receiptEntity) {
        String fid = UUIDGenerator.hexUUID();
        if (Check.NuNStr(receiptEntity.getFid())){
            receiptEntity.setFid(fid);
        }
        mybatisDaoContext.save(SQLID + "saveReceipt", receiptEntity);
        return fid;
    }

    /**
     * 计数 根据合同号查询的
     * @author jixd
     * @created 2017年11月28日 13:38:25
     * @param
     * @return
     */
    public long countReceiptByContractId(String contractId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("contractId",contractId);
        return mybatisDaoContext.count(SQLID + "countSyncReceiptByContractId",paramMap);
    }

    /**
      * @description: 根据收款单编号查询收款单
      * @author: lusp
      * @date: 2017/12/13 下午 17:31
      * @params: receiptEntity
      * @return: ReceiptEntity
      */
    public ReceiptEntity getReceiptByBillNum(String receiptBillNum){
        return mybatisDaoContext.findOne(SQLID + "getReceiptByBillNum",ReceiptEntity.class,receiptBillNum);
    }

    /**
      * @description: 根据收款单编号查询对应的应收账单编号
      * @author: lusp
      * @date: 2017/12/13 下午 17:31
      * @params: receiptEntity
      * @return: ReceiptEntity
      */
    public List<String> getReceivableBillNumsByBillNum(String receiptBillNum){
        return mybatisDaoContext.findAll(SQLID + "getReceivableBillNumsByBillNum",String.class,receiptBillNum);
    }
}
