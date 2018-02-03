package com.ziroom.minsu.services.order.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderActivityEntity;
import com.ziroom.minsu.entity.order.OrderRemarkEntity;
import com.ziroom.minsu.services.order.dao.OrderActivityDao;
import com.ziroom.minsu.services.order.dao.OrderRemarkDao;
import com.ziroom.minsu.services.order.dto.RemarkRequest;
import com.ziroom.minsu.valenum.order.OrderAcTypeEnum;

/**
 * <p>订单备注service</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author liyingjie on 2016年6月23日
 * @since 1.0
 * @version 1.0
 */
@Service("order.orderRemarkServiceImpl")
public class OrderRemarkServiceImpl {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(OrderActivityDao.class);
	
	@Resource(name = "order.orderRemarkDao")
    private OrderRemarkDao orderRemarkDao;


    /**
     * 获取备注列表 
     * @param orderSn
     * @return
     */
    public List<OrderRemarkEntity> getRemarkList(String orderSn, String uid){
       return orderRemarkDao.getOrderInfoListByCondiction(orderSn, uid);
    }
    
    /**
     * 删除备注 
     * @param orderSn
     * @return
     */
    public int delRemark(String fid, String uid){
    	 return orderRemarkDao.delRemarkByFid(fid, uid);
    }
    
    
    /**
     * 增加备注 
     * @param remarkEntity
     * @return
     */
    public int saveRemark(OrderRemarkEntity remarkEntity){
    	 return orderRemarkDao.insertRemark(remarkEntity);
    }
    
    /**
     * 计算备注 
     * @param remarkEntity
     * @return
     */
    public Long countRemark(String orderSn, String uid){
    	 return orderRemarkDao.countRemarkByOrderSn(orderSn, uid);
    }
}
