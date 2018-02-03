package com.ziroom.minsu.services.order.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinancePenaltyPayRelEntity;
import com.ziroom.minsu.services.common.utils.StringUtils;
import com.ziroom.minsu.services.order.entity.FinancePenaltyPayRelVo;

/**
 * 付款单与罚款单关系
 * @author jixd
 * @created 2017年05月10日 11:11:26
 * @param
 * @return
 */
@Repository("order.financePenaltyPayRelDao")
public class FinancePenaltyPayRelDao {
    private static Logger logger = LoggerFactory.getLogger(FinancePenaltyPayRelDao.class);

    private String SQLID = "order.financePenaltyPayRelDao.";

    @Autowired
    @Qualifier("order.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 保存关系
     * @author jixd
     * @created 2017年05月10日 10:52:17
     * @param
     * @return
     */
    public int saveFinancePenaltyPayRel(FinancePenaltyPayRelEntity financePenaltyPayRelEntity) {
        if (Check.NuNObj(financePenaltyPayRelEntity)){
            LogUtil.error(logger, "financePenaltyPayRelEntity is null on saveFinancePenaltyPayRel");
            throw new BusinessException("financePenaltyPayRelEntity is null on saveFinancePenaltyPayRel");
        }
        return mybatisDaoContext.save(SQLID + "insertSelective", financePenaltyPayRelEntity);
    }
    
    /**
     * 
     * 根据 付款单订单编号 查询当前订单的 被罚款信息
     *
     * @author yd
     * @created 2017年5月15日 下午5:56:57
     *
     * @param pvOrderSn
     * @return
     */
    public List<FinancePenaltyPayRelVo> findFinancePenaltyPayRelVoByPvOrderSn(String pvOrderSn){
    	
    	List<FinancePenaltyPayRelVo> list = mybatisDaoContext.findAll(SQLID+"findFinancePenaltyPayRelVoByPvOrderSn", FinancePenaltyPayRelVo.class, pvOrderSn);
    	if(!Check.NuNCollection(list)){
    		for (FinancePenaltyPayRelVo financePenaltyPayRelVo : list) {
    			financePenaltyPayRelVo.setTotalFeeStr(StringUtils.getPriceFormat(financePenaltyPayRelVo.getTotalFee()));
			}
    	}
    	return list;
    }
}
