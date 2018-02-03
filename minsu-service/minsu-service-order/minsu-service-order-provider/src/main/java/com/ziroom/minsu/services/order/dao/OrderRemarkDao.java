package com.ziroom.minsu.services.order.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderRemarkEntity;
import com.ziroom.minsu.services.order.dto.RemarkRequest;

/**
 * <p> 订单备注的操作 </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie on 2016/6/23.
 * @version 1.0
 * @since 1.0
 */
@Repository("order.orderRemarkDao")
public class OrderRemarkDao {

	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(OrderRemarkDao.class);
	private String SQLID = "order.orderRemarkDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

    
    /**
	 * 查询 备注列表
	 * @author liyingjie
	 * @created 2016年6月23日 
	 *
	 * @return
	 */
    public List<OrderRemarkEntity> getOrderInfoListByCondiction(String orderSn, String uid){
    	Map<String, String> queryMap = new HashMap<String,String>();
    	queryMap.put("orderSn", orderSn);
    	queryMap.put("uid", uid);
    	return this.mybatisDaoContext.findAll(SQLID+"selectByOrderSn", OrderRemarkEntity.class, queryMap);
    }
    
    
    /**
     * 删除备注
     * @author liyingjie
     * @created 2016年6月23日 
     *
     * @param fid
     * @return
     */
    public int delRemarkByFid(String fid, String uid){
    	if(Check.NuNStr(fid)){
    		LogUtil.info(logger, "fid:{}", fid);
    		return -1;
    	}
    	Map<String, Object> map = new HashMap<String, Object>(1);
    	map.put("fid", fid);
    	map.put("uid", uid);
    	return this.mybatisDaoContext.update(SQLID+"deleteByFid", map);
    }
    
    /**
	 * 保存备注
	 * @author liyingjie
	 * @created 2016年6月23日 
	 * @param remarkEntity
	 * @return
	 */
	public int insertRemark(OrderRemarkEntity remarkEntity) {
		if(Check.NuNObj(remarkEntity)){
			LogUtil.info(logger,"current remarkEntity is null on insertRemark");
			throw new BusinessException("current remarkEntity is null on insertRemark");
		}
		
		if(Check.NuNStr(remarkEntity.getFid())){
			remarkEntity.setFid(UUIDGenerator.hexUUID());
		}
		
		return mybatisDaoContext.save(SQLID + "insertRemarkEntity", remarkEntity);
	}
	
	/**
	 * 统计单个订单备注数量
	 * @author liyingjie
	 * @created 2016年6月23日 
	 * @param orderSn
	 * @return
	 */
	public Long countRemarkByOrderSn(String orderSn, String uid) {
		if(Check.NuNStr(orderSn) || Check.NuNStr(uid)){
    		LogUtil.info(logger, "orderSn:{},uid:{}", orderSn, uid);
    		throw new BusinessException("参数为空");
    	}
		Map<String, Object> params = new HashMap<String,Object>(1);
		params.put("orderSn", orderSn);
		params.put("uid", uid);
		return mybatisDaoContext.count(SQLID + "countByOrderSn", params);
	}
    
}
