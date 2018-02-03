package com.ziroom.minsu.services.order.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinancePenaltyLogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * 罚款单保存
 * @author jixd
 * @created 2017年05月10日 10:50:07
 * @param
 * @return
 */
@Repository("order.financePenaltyLogDao")
public class FinancePenaltyLogDao {

    private static Logger logger = LoggerFactory.getLogger(FinancePenaltyLogDao.class);

    private String SQLID = "order.financePenaltyLogDao.";

    @Autowired
    @Qualifier("order.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 保存日志
     * @author jixd
     * @created 2017年05月10日 10:52:17
     * @param
     * @return
     */
    public int saveFinancePenaltyLog(FinancePenaltyLogEntity financePenaltyLogEntity) {
        if (Check.NuNObj(financePenaltyLogEntity)){
            LogUtil.error(logger, "financePenaltyLogEntity is null on saveFinancePenaltyLog");
            throw new BusinessException("financePenaltyLogEntity is null on saveFinancePenaltyLog");
        }
        if (Check.NuNStr(financePenaltyLogEntity.getFid())){
            financePenaltyLogEntity.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID + "insertSelective", financePenaltyLogEntity);
    }

}
