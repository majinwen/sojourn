package com.ziroom.minsu.services.order.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderParamEntity;


/**
 * <p>
 * 订单参数信息
 * </p>
 * <p/>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie on 2016/4/1.
 * @version 1.0
 * @since 1.0
 */
@Repository("order.paramDao")
public class OrderParamDao {

	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(OrderParamDao.class);
	private String SQLID = "order.paramDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	
	/**
	 *
	 * 插入资源记录
	 *
	 * @author liyingjie
	 * @created 2016年4月1日 
	 *
	 * @param paramEntity
	 */
	public void insertParamRes(OrderParamEntity paramEntity) {
        if(Check.NuNObj(paramEntity)){
        	LogUtil.info(logger,"paramEntity is null on insertParamRes");
            throw new BusinessException("paramEntity is null on insertParamRes");
        }
        if(Check.NuNStr(paramEntity.getFid())){
            paramEntity.setFid(UUIDGenerator.hexUUID());
        }
		mybatisDaoContext.save(SQLID + "insertParam", paramEntity);
	}
	
	
	
	/**
	 *
	 * 查询 资源记录
	 *
	 * @author liyingjie
	 * @created 2016年4月1日 
	 *
	 * @param 
	 */
	public List<OrderParamEntity> findParamByCondiction(Map<String,Object> paramMap){
		return mybatisDaoContext.findAll(SQLID + "selectByCondiction", OrderParamEntity.class, paramMap);
	}


    /**
     * 通过orderSn获取订单的参数信息
     * @author afi
     * @param orderSn
     * @return
     */
    public List<OrderParamEntity> findParamByOrderSn(String orderSn){
        return mybatisDaoContext.findAll(SQLID + "findParamByOrderSn", OrderParamEntity.class, orderSn);
    }

    /**
     * 通过orderSn获取订单的参数信息
     * @author afi
     * @param orderSn
     * @return
     */
    public OrderParamEntity findParamByCode(String orderSn,String code){
        Map<String,String> par = new HashMap<>();
        par.put("orderSn",orderSn);
        par.put("code",code);
        return mybatisDaoContext.findOne(SQLID + "findParamByCode", OrderParamEntity.class, par);
    }

    /**
     * 跟新订单参数
     * @author afi
     * @param parValue
     * @return
     */
    public OrderParamEntity updateParamByFid(String fid,String parValue){
        Map<String,String> par = new HashMap<>();
        par.put("fid",fid);
        par.put("parValue",parValue);
        return mybatisDaoContext.findOne(SQLID + "updateParamByFid", OrderParamEntity.class, par);
    }

    /**
     * 获取订单的参数信息
     * @author afi
     * @param orderSn
     * @return
     */
    public Map<String,Object> getOrderPar(String orderSn){
        Map<String,Object> orderParMap = new HashMap<>();
        List<OrderParamEntity> orderParamList = findParamByOrderSn(orderSn);
        if(!Check.NuNObj(orderParamList)){
            for(OrderParamEntity par: orderParamList){
                orderParMap.put(par.getParCode(),par.getParValue());
            }
        }
        return orderParMap;
    }
}
