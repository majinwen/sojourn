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
import com.ziroom.minsu.entity.order.FinancePayVouchersDetailEntity;


@Repository("order.financePayVouchersDetailDao")
public class FinancePayVouchersDetailDao {
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(FinancePayVouchersDao.class);
	private String SQLID = "order.financePayVouchersDetailDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 根据付款单号，查询付款单明细
	 * @author lishaochuan
	 * @create 2016年4月21日
	 * @param pvSn
	 * @return
	 */
	public List<FinancePayVouchersDetailEntity> findPayVouchersDetailByPvSn(String pvSn) {
		if (Check.NuNObj(pvSn)) {
			LogUtil.error(logger, "pvSn is null on findPayVouchersDetailByPvSn");
			throw new BusinessException("pvSn is null on findPayVouchersDetailByPvSn");
		}
		return mybatisDaoContext.findAll(SQLID + "findPayVouchersDetailByPvSn", FinancePayVouchersDetailEntity.class, pvSn);
	}
	
	/**
	 * 保存付款记录详情表信息
	 * @author lishaochuan
	 * @create 2016年4月19日
	 * @param payVouchersDetailEntity
	 * @return
	 */
	public int insertPayVouchersDetail(FinancePayVouchersDetailEntity payVouchersDetailEntity) {
		if (Check.NuNObj(payVouchersDetailEntity)) {
			LogUtil.error(logger, "payVouchersDetailEntity is null on insertPayVouchersDetail");
			throw new BusinessException("payVouchersDetailEntity is null on insertPayVouchersDetail");
		}
		return mybatisDaoContext.save(SQLID + "insertSelective", payVouchersDetailEntity);
	}



}
