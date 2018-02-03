package com.ziroom.minsu.services.order.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.order.FinancePunishEntity;
import com.ziroom.minsu.services.finance.dto.PunishListRequest;
import com.ziroom.minsu.services.order.dao.FinancePunishDao;

/**
 * 
 * <p>扣款接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年4月25日
 * @since 1.0
 * @version 1.0
 */
@Service("order.financePunishServicesImpl")
public class FinancePunishServicesImpl {
	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FinancePunishServicesImpl.class);
	
	@Resource(name = "order.financePunishDao")
    private FinancePunishDao financePunishDao;

	
	/**
	 * 根据punishSn获取扣款单
	 * @author lishaochuan
	 * @create 2016年4月27日
	 * @param punishSn
	 * @return
	 */
	public FinancePunishEntity getPunishByPunishSn(String punishSn){
		return financePunishDao.getPunishByPunishSn(punishSn);
	}
	
	/**
	 * 分页获取扣款单列表
	 * @author lishaochuan
	 * @create 2016年4月25日
	 * @param punishRequest
	 * @return
	 */
	public PagingResult<FinancePunishEntity> getPunishListByCondition(PunishListRequest punishRequest) {
		return financePunishDao.getPunishListByCondition(punishRequest);
	}
	
	/**
	 * 更新扣款单表数据
	 * @author lishaochuan
	 * @create 2016年4月27日
	 * @param financePunishEntity
	 * @return
	 */
	public int updateByPunishSn(FinancePunishEntity financePunishEntity) {
		return financePunishDao.updateByPunishSn(financePunishEntity);
	}


    /**
     * 获取当前订单的罚款记录
     * @author afi
     * @create 2016年4月26日
     * @param orderSn
     * @return
     */
    public FinancePunishEntity getPunishListByOrderSn(String orderSn) {
        return financePunishDao.getPunishListByOrderSn(orderSn);
    }

    /**
     * 校验当前的房东是否有过期的的罚款信息
     * @param landlordUid
     * @return
     */
    public boolean checkLandPunishOverTime(String landlordUid){
        return financePunishDao.countPunishOverTime(landlordUid)>0?true:false;
    }
}
