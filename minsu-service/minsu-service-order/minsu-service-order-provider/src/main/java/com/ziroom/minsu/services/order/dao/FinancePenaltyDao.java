package com.ziroom.minsu.services.order.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinancePenaltyEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.order.dto.PenaltyRequest;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.entity.PenaltyRelVo;
import com.ziroom.minsu.services.order.entity.PenaltyVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 罚款单
 * @author jixd
 * @created 2017年05月10日 11:11:54
 * @param
 * @return
 */
@Repository("order.financePenaltyDao")
public class FinancePenaltyDao {

    private static Logger logger = LoggerFactory.getLogger(FinancePenaltyDao.class);

    private String SQLID = "order.financePenaltyDao.";

    @Autowired
    @Qualifier("order.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 保存实体
     * @author jixd
     * @created 2017年05月10日 10:46:59
     * @param
     * @return
     */
    public int saveFinancePenalty(FinancePenaltyEntity financePenaltyEntity) {
        if (Check.NuNObj(financePenaltyEntity)){
            LogUtil.error(logger, "financePenaltyEntity is null on saveFinancePenalty");
            throw new BusinessException("financePenaltyEntity is null on saveFinancePenalty");
        }
        return mybatisDaoContext.save(SQLID + "insertSelective", financePenaltyEntity);
    }

    /**
     * 根据uid查询所有的罚款单
     * @author jixd
     * @created 2017年05月10日 13:56:29
     * @param
     * @return
     */
    public List<FinancePenaltyEntity> listAvaliableFinancePenaltyByUid(String uid){
        return mybatisDaoContext.findAll(SQLID + "listAvaliableFinancePenaltyByUid",FinancePenaltyEntity.class,uid);
    }

    /**
     * 根据uid查询有效的罚款单数量
     * @author jixd
     * @created 2017年05月10日 16:09:30
     * @param
     * @return
     */
    public long countAvaliableFinancePenaltyByUid(String uid){
        Map<String,Object> parMap = new HashMap<>();
        parMap.put("uid",uid);
        return mybatisDaoContext.count(SQLID + "countAvaliableFinancePenaltyByUid",parMap);
    }

    /**
     * 更新罚款单状态根据订单号
     * @author jixd
     * @created 2017年05月10日 14:37:30
     * @param
     * @return
     */
    public int updateFinancePenaltyByOrderSn(FinancePenaltyEntity financePenaltyEntity){
        return mybatisDaoContext.update(SQLID + "updateFinancePenaltyByOrderSn",financePenaltyEntity);
    }
    /**
     * 更新罚款单状态
     * @author jixd
     * @created 2017年05月15日 15:41:53
     * @param
     * @return
     */
    public int updatePenaltyByPenaltySnAndStatus(FinancePenaltyEntity financePenaltyEntity){
        return mybatisDaoContext.update(SQLID + "updatePenaltyByPenaltySnAndStatus",financePenaltyEntity);
    }
    /**
     * 查询单个罚款单
     * @author jixd
     * @created 2017年05月15日 15:46:22
     * @param
     * @return
     */
    public FinancePenaltyEntity findPenaltyByPenaltySn(String penaltySn){
        return mybatisDaoContext.findOne(SQLID + "findPenaltyByPenaltySn",FinancePenaltyEntity.class,penaltySn);
    }

	/**
	 * 根据orderSn获取实体对象
	 *
	 * @author loushuai
	 * @created 2017年5月16日 上午11:08:53
	 *
	 * @param orderSn
	 * @return
	 */
	public FinancePenaltyEntity getPenaltyEntityByOrderSn(String orderSn) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderSn", orderSn);
		FinancePenaltyEntity financePenaltyEntity = mybatisDaoContext.findOne(SQLID + "getPenaltyEntityByOrderSn", FinancePenaltyEntity.class, params);
		return financePenaltyEntity;
	}

    /**
     * 查询列表
     * @author jixd
     * @created 2017年05月15日 11:52:55
     * @param
     * @return
     */
    public PagingResult<PenaltyVo> listPenaltyPageByCondition(PenaltyRequest penaltyRequest){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(penaltyRequest.getLimit());
        pageBounds.setPage(penaltyRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID + "listPenaltyByCondition", PenaltyVo.class, penaltyRequest, pageBounds);
    }


    /**
     *
     * 查询当前订单 的房东罚款单(目前唯一)
     *
     * @author yd
     * @created 2017年5月15日 下午3:22:18
     *
     * @param orderSn
     * @return
     */
    public FinancePenaltyEntity findLanFinancePenaltyByOrderSn(String orderSn){
    	 return mybatisDaoContext.findOneSlave(SQLID+"findLanFinancePenaltyByOrderSn", FinancePenaltyEntity.class, orderSn);
    }
    /**
     * 查询罚款单与付款单和收入关系
     * @author jixd
     * @created 2017年05月16日 11:58:11
     * @param
     * @return
     */
    public List<PenaltyRelVo> listPenaltyPayAndIncomeRel(String penaltySn){
        return mybatisDaoContext.findAll(SQLID + "listPenaltyPayAndIncomeRel",PenaltyRelVo.class,penaltySn);
    }


}
