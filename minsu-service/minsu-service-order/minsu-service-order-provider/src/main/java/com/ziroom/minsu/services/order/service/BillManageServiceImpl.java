package com.ziroom.minsu.services.order.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinancePayVouchersDetailEntity;
import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.services.finance.dto.FinanceIncomeRequest;
import com.ziroom.minsu.services.finance.dto.FinancePayVosRequest;
import com.ziroom.minsu.services.finance.dto.PaymentVouchersRequest;
import com.ziroom.minsu.services.finance.entity.FinanceIncomeVo;
import com.ziroom.minsu.services.finance.entity.FinancePayVouchersVo;
import com.ziroom.minsu.services.finance.entity.FinancePaymentVo;
import com.ziroom.minsu.services.order.dao.FinanceIncomeDao;
import com.ziroom.minsu.services.order.dao.FinancePayVouchersDao;
import com.ziroom.minsu.services.order.dao.FinancePayVouchersDetailDao;
import com.ziroom.minsu.services.order.dao.FinancePaymentVouchersDao;
import com.ziroom.minsu.services.order.entity.FinancePayDetailInfoVo;


/**
 * <p>后台账单关联接口
 *   说明：付款单  收款单  收入
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@Service("order.billManageServiceImpl")
public class BillManageServiceImpl {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(BillManageServiceImpl.class);
	
	@Resource(name = "order.financePayVouchersDao")
	private FinancePayVouchersDao financePayVouchersDao;
	
	@Resource(name = "order.financePayVouchersDetailDao")
	private FinancePayVouchersDetailDao financePayVouchersDetailDao;
	
	@Resource(name = "order.financeIncomeDao")
	private FinanceIncomeDao financeIncomeDao;
	
	@Resource(name = "order.financePaymentVouchersDao")
	private FinancePaymentVouchersDao financePaymentVouchersDao;


    /**
     * 获取当前的收入明细
     * @param incomeSn
     * @return
     */
    public FinanceIncomeVo getFinanceIncomeDetail(String incomeSn){
        if(Check.NuNStr(incomeSn)){
            return null;
        }
        return this.financeIncomeDao.getFinanceIncomeDetail(incomeSn);
    }


    /**
	 * 
	 * 按条件分页查询收入记录
	 *
	 * @author yd
	 * @created 2016年4月28日 下午9:54:48
	 *
	 * @param financeIncomeRequest
	 * @return
	 */
	public PagingResult<FinanceIncomeVo> queryFinanceIncomeByPage(FinanceIncomeRequest financeIncomeRequest){
		
		if(Check.NuNObj(financeIncomeRequest)) financeIncomeRequest = new FinanceIncomeRequest();
		LogUtil.info(logger, "按条件分页查询收入记录,条件为financeIncomeRequest={}", financeIncomeRequest.toJsonStr());
		return this.financeIncomeDao.queryFinanceIncomeByPage(financeIncomeRequest);
	}
	
	/**
	 * 
	 * 显示收款单详情
	 *
	 * @author jixd
	 * @created 2016年5月12日 下午5:46:19
	 *
	 * @param fid
	 * @return
	 */
	public FinancePaymentVo getPaymentDetailVo(String fid){
		return financePaymentVouchersDao.getPaymentVoById(fid);
	}
	
	/**
	 * 
	 * 按条件分页查询收款单记录
	 *
	 * @author yd
	 * @created 2016年4月28日 下午9:54:48
	 *
	 * @param financeIncomeRequest
	 * @return
	 */
	public PagingResult<FinancePaymentVo> queryPaymentVoByPage(PaymentVouchersRequest paymentVouchersRequest){
		
		if(Check.NuNObj(paymentVouchersRequest)) paymentVouchersRequest = new PaymentVouchersRequest();
		LogUtil.info(logger, "按条件分页查询收入记录,条件为financeIncomeRequest={}", paymentVouchersRequest.toJsonStr());
		return this.financePaymentVouchersDao.queryPaymentVoByPage(paymentVouchersRequest);
	}
	

	/**
	 * 
	 * 按条件分页查询付款单记录
	 *
	 * @author yd
	 * @created 2016年4月28日 下午9:54:48
	 *
	 * @param financeIncomeRequest
	 * @return
	 */
	public PagingResult<FinancePayVouchersVo> queryFinancePayVosByPage(FinancePayVosRequest financePayVosRequest){
		
		if(Check.NuNObj(financePayVosRequest)) financePayVosRequest = new FinancePayVosRequest();
		LogUtil.info(logger, "按条件分页查询收入记录,条件为financeIncomeRequest={}", financePayVosRequest.toJsonStr());
		return this.financePayVouchersDao.queryFinancePayVosByPage(financePayVosRequest);
	}
	
	/**
	 * 显示付款单详情
	 * @author lishaochuan
	 * @create 2016年5月17日上午11:27:44
	 * @param pvSn
	 * @return
	 */
	public FinancePayDetailInfoVo getFinancePayInfoVo(String pvSn){
		return financePayVouchersDao.getFinancePayDetailInfoVo(pvSn);
	}
	
	
	/**
	 * 查询重新生成的付款单数量
	 * @author lishaochuan
	 * @create 2016年8月16日下午6:02:34
	 * @param parentPvSn
	 * @return
	 */
	public long countReCreatePvs(String parentPvSn){
		return financePayVouchersDao.countReCreatePvs(parentPvSn);
	}
	
	/**
	 * 重新生成付款单
	 * @author lishaochuan
	 * @create 2016年8月17日下午2:25:29
	 * @param newPayVouchersEntity
	 * @param newDetailList
	 * @param pvSn
	 */
	public void reCreatePvs(FinancePayVouchersEntity newPayVouchersEntity, List<FinancePayVouchersDetailEntity> newDetailList, String pvSn){
		int num = financePayVouchersDao.updateReCreatePvsOldStatus(pvSn);
		if (num != 1) {
			throw new BusinessException("重新生成付款单，更新旧付款单状失败！");
		}
		num = financePayVouchersDao.insertPayVouchers(newPayVouchersEntity);
		if (num != 1) {
			throw new BusinessException("保存付款单数据失败！");
		}
		for (FinancePayVouchersDetailEntity payVouchersDetailEntity : newDetailList) {
			num = financePayVouchersDetailDao.insertPayVouchersDetail(payVouchersDetailEntity);
			if (num != 1) {
				throw new BusinessException("保存付款单明细数据失败！");
			}
		}
	}
}
