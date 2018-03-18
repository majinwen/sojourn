package com.ziroom.zrp.service.trading.service;

import com.asura.framework.base.util.Check;
import com.ziroom.zrp.service.trading.dao.FinReceiptRelDao;
import com.ziroom.zrp.service.trading.dao.ReceiptDao;
import com.ziroom.zrp.trading.entity.FinReceiptRelEntity;
import com.ziroom.zrp.trading.entity.ReceiptEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>合同相关操作</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年11月09日 20:03
 * @since 1.0
 */
@Service("trading.receiptServiceImpl")
public class ReceiptServiceImpl {

    @Resource(name = "trading.receiptDao")
	private ReceiptDao receiptDao;

	@Resource(name = "trading.finReceiptRelDao")
	private FinReceiptRelDao finReceiptRelDao;

	/**
	 * @description: 根据收款单号批量更新收款单为已收款
	 * @author: lusp
	 * @date: 2017/11/9 下午 14:25
	 * @params: tradingReceiptEntity
	 * @return: int
	 */
	public int updateBatchByBillNum(List<ReceiptEntity> tradingReceiptEntities){
		int num = 0;
		for (ReceiptEntity receiptEntity :tradingReceiptEntities){
			num += receiptDao.updateByBillNum(receiptEntity);
		}
		return num;
	}

	public int saveBatchReceipt(List<ReceiptEntity> receiptEntityList) {
		int affect = 0;
		for (ReceiptEntity receiptEntity : receiptEntityList){
			affect += receiptDao.saveReceipt(receiptEntity);
		}
		return affect;
	}

	/**
	 * @description: 保存收款单并返回fid（保存收款单主记录时使用）
	 * @author: lusp
	 * @date: 2017/12/1 下午 15:50
	 * @params: receiptEntity
	 * @return: String
	 */
	public String saveReceiptAndReturnFid(ReceiptEntity receiptEntity){
		return receiptDao.saveReceiptAndReturnFid(receiptEntity);
	}

	/**
	 * 批量保存单个收款
	 * @author jixd
	 * @created 2017年11月22日 17:30:58
	 * @param
	 * @return
	 */
	public int saveBatchReceiptRel(List<FinReceiptRelEntity> receiptRelEntities){
		int count = 0;
		if (!Check.NuNCollection(receiptRelEntities)){
			for (FinReceiptRelEntity receiptRelEntity : receiptRelEntities){
				count += finReceiptRelDao.saveFinReceiptRel(receiptRelEntity);
			}
		}
		return count;
	}

	/**
	 * 批量保存收款单 和关系
	 * @author jixd
	 * @created 2017年11月22日 17:38:07
	 * @param
	 * @return
	 */
	public int saveBatchReceiptAndRel(List<ReceiptEntity> receiptEntityList,List<FinReceiptRelEntity> receiptRelEntities){
		int count = saveBatchReceipt(receiptEntityList);
		count += saveBatchReceiptRel(receiptRelEntities);
		return count;
	}

	/**
	 * 计数
	 * @author jixd
	 * @created 2017年11月28日 13:40:53
	 * @param
	 * @return
	 */
	public long countReceiptByContractId(String contractId){
		return receiptDao.countReceiptByContractId(contractId);
	}

	/**
	 * @description: 根据应收账单编号获取对应的首款单编号
	 * @author: lusp
	 * @date: 2017/12/6 下午 14:55
	 * @params: finReceiptRelEntity
	 * @return: List<String>
	 */
	public List<String> findReceiNumsByBillNum(String receiptBillNum){
		return finReceiptRelDao.findReceiNumsByBillNum(receiptBillNum);
	}

	/**
	  * @description: 根据收款单编号查询收款单
	  * @author: lusp
	  * @date: 2017/12/15 上午 11:29
	  * @params: receiptBillNum
	  * @return: ReceiptEntity
	  */
	public ReceiptEntity getReceiptByBillNum(String receiptBillNum){
		return receiptDao.getReceiptByBillNum(receiptBillNum);
	}

	/**
	  * @description: 根据收款单编号查询对应的应收账单编号
	  * @author: lusp
	  * @date: 2017/12/15 上午 11:31
	  * @params: receiptBillNum
	  * @return: List<String>
	  */
	public List<String> getReceivableBillNumsByBillNum(String receiptBillNum){
		return receiptDao.getReceivableBillNumsByBillNum(receiptBillNum);
	}

	/**
	  * @description: 根据收款单号逻辑删除收款单记录以及对应的应收账单记录
	  * @author: lusp
	  * @date: 2017/12/19 上午 10:45
	  * @params: receiptBillNum
	  * @return:
	  */
	public void deleteReceiptAndRel(String receiptBillNum){
		ReceiptEntity receiptEntity = new ReceiptEntity();
		receiptEntity.setBillNum(receiptBillNum);
		receiptEntity.setIsDel(1);
		receiptEntity.setIsValid(0);
		receiptDao.updateByBillNum(receiptEntity);
		FinReceiptRelEntity finReceiptRelEntity = new FinReceiptRelEntity();
		finReceiptRelEntity.setReceiptedNo(receiptBillNum);
		finReceiptRelEntity.setIsDel(1);
		finReceiptRelDao.updateByReceiptedNo(finReceiptRelEntity);
	}

}
