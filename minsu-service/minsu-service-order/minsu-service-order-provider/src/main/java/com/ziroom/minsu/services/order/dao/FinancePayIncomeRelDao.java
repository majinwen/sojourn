package com.ziroom.minsu.services.order.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinancePayIncomeRelEntity;
import com.ziroom.minsu.entity.order.FinancePenaltyEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * 付款单收入关系
 * @author jixd
 * @created 2017年05月10日 10:59:11
 * @param
 * @return
 */
@Repository("order.financePayIncomeRelDao")
public class FinancePayIncomeRelDao {

    private static Logger logger = LoggerFactory.getLogger(FinancePayIncomeRelDao.class);

    private String SQLID = "order.financePayIncomeRelDao.";

    @Autowired
    @Qualifier("order.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 保存付款单收入关系
     * @author jixd
     * @created 2017年05月10日 11:01:35
     * @param
     * @return
     */
    public int saveFinancePayIncomeRel(FinancePayIncomeRelEntity financePayIncomeRelEntity) {
        if (Check.NuNObj(financePayIncomeRelEntity)){
            LogUtil.error(logger, "financePayIncomeRelEntity is null on saveFinancePayIncomeRel");
            throw new BusinessException("financePayIncomeRelEntity is null on saveFinancePayIncomeRel");
        }
        return mybatisDaoContext.save(SQLID + "insertSelective", financePayIncomeRelEntity);
    }


}
