package com.ziroom.minsu.services.order.service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseDayRevenueEntity;
import com.ziroom.minsu.entity.order.FinanceIncomeEntity;
import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.services.order.dao.FinanceIncomeDao;
import com.ziroom.minsu.services.order.dao.FinancePayVouchersDao;
import com.ziroom.minsu.services.order.dao.FinancePayVouchersDetailDao;
import com.ziroom.minsu.services.order.dao.VirtualPayVouchersDao;
import com.ziroom.minsu.services.order.entity.FinancePayAndDetailVo;
import com.ziroom.minsu.services.order.utils.OrderSnUtil;
import com.ziroom.minsu.valenum.order.IncomeStatusEnum;
import com.ziroom.minsu.valenum.order.PaymentStatusEnum;

/**
 * 
 * <p></p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年4月19日
 * @since 1.0
 * @version 1.0
 */
@Service("order.financePayServiceImpl")
public class FinancePayServiceImpl {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FinancePayServiceImpl.class);

    @Resource(name = "order.financePayVouchersDao")
    private FinancePayVouchersDao financePayVouchersDao;
    
    @Resource(name = "order.financePayVouchersDetailDao")
    private FinancePayVouchersDetailDao financePayVouchersDetailDao;
    
    @Resource(name = "order.financeIncomeDao")
    private FinanceIncomeDao financeIncomeDao;

    @Resource(name = "order.virtualPayVouchersDao")
    private VirtualPayVouchersDao virtualPayVouchersDao;



    /**
     * 生成付款单
     * @author lishaochuan
     * @create 2016年4月20日
     * @param financePayAndDetailVo
     * @return
     */
	public boolean saveFinancePayVouchers(FinancePayAndDetailVo financePayAndDetailVo) {
		virtualPayVouchersDao.saveFinancePayVouchers(financePayAndDetailVo);
		return true;
	}
    
    
	/**
     * 保存收入信息
     * @author afi
     * @param financeIncomeEntity
     * @return
     */
    public int insertFinanceIncome(FinanceIncomeEntity financeIncomeEntity){
        if(Check.NuNObj(financeIncomeEntity)){
            LogUtil.info(LOGGER, "financeIncomeEntity为空");
            throw new BusinessException("financeIncomeEntity is null");
        }
        if(Check.NuNStr(financeIncomeEntity.getIncomeSn())){
            financeIncomeEntity.setIncomeSn(OrderSnUtil.getIncomeSn());
        }
        if(Check.NuNObj(financeIncomeEntity.getGenerateFeeTime())){
            financeIncomeEntity.setGenerateFeeTime(new Date());
        }

        if(Check.NuNObj(financeIncomeEntity.getRunTime())){
            financeIncomeEntity.setRunTime(new Date());
        }
        //设置默认状态
        if(Check.NuNObj(financeIncomeEntity.getIncomeStatus())){
            financeIncomeEntity.setIncomeStatus(IncomeStatusEnum.NO.getCode()); //未收款
        }
        return financeIncomeDao.insertFinanceIncome(financeIncomeEntity);
    }
    

	
	/**
	 * 
	 * 查询指定日期前一天房东收益列表
	 *
	 * @author bushujie
	 * @created 2016年4月26日 下午4:37:01
	 *
	 * @param runDate
	 * @return
	 * @throws ParseException 
	 */
	public List<HouseDayRevenueEntity> findLandlordDayRevenueList(Date runDate) throws ParseException{
		Map<String, Object> paramMap=new HashMap<String, Object>();
		String beforDate=DateUtil.getDayBeforeDate(runDate);
		paramMap.put("startDate", DateUtil.parseDate(beforDate+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		paramMap.put("endDate", DateUtil.parseDate(beforDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		return financePayVouchersDao.findLandlordDayRevenueList(paramMap);
	}
	
	
	/**
	 * 
	 * 根据pvSn 查询付款单
	 *
	 * @author liyingjie
	 * @created 2016年5月12日 下午4:37:01
	 * @param pvSn
	 * @return
	 */
	public FinancePayVouchersEntity findPayVouchersByPvSn(String pvSn) {
		return financePayVouchersDao.findPayVouchersByPvSn(pvSn);
	}
	
	
	
	/**
	 * 修改付款单状态，未生效=>未付款
	 * @author lishaochuan
	 * @create 2016年9月12日下午8:38:59
	 * @param pvSn
	 * @return
	 */
	public int updateEffectiveStatus(String pvSn) {
		return financePayVouchersDao.updateEffectiveStatus(pvSn);
	}



	/**
	 * 查询用户结算生成的付款单
	 * @author lishaochuan
	 * @create 2017/1/10 17:22
	 * @param 
	 * @return 
	 */
	public List<FinancePayVouchersEntity> findSettlementByOrderSn(String orderSn){
		return financePayVouchersDao.findSettlementByOrderSn(orderSn);
	}

}
