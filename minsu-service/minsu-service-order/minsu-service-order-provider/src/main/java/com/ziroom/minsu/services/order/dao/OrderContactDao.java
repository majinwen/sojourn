package com.ziroom.minsu.services.order.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderContactEntity;

/**
 * <p>常用入住人</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/31.
 * @version 1.0
 * @since 1.0
 */
@Repository("order.orderContactDao")
public class OrderContactDao {

	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(OrderContactDao.class);
    private String SQLID = "order.orderContactDao.";

    @Autowired
    @Qualifier("order.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;




    /**
     * 订单常住人关系表
     * @author afi
     * @created 2016年3月31日 下午20:22:38
     * @param contact
     * @return
     */
    public int insertOrderContact(OrderContactEntity contact) {
        if(Check.NuNObj(contact)){
        	LogUtil.info(logger, "current contact is null on insertOrderContact");
            throw new BusinessException("current contact is null on insertOrderContact");
        }
        if(Check.NuNStr(contact.getOrderSn())){
        	LogUtil.info(logger, "orderSn is null on insertOrderContact");
            throw new BusinessException("orderSn is null on insertOrderContact");
        }
        if(Check.NuNStr(contact.getContactFid())){
        	LogUtil.info(logger,"contactFid is null on insertOrderContact");
            throw new BusinessException("contactFid is null on insertOrderContact");
        }
        return mybatisDaoContext.save(SQLID + "insertOrderContact", contact);
    }
    
    /**
     * 
     * 查询订单入住人数
     *
     * @author jixd
     * @created 2016年5月4日 下午10:44:34
     *
     * @param orderSn
     * @return
     */
    public long selectCountContactByOrderSn(String orderSn){
    	if(Check.NuNStr(orderSn)){
    		 throw new BusinessException("orderSn is null");
    	}
    	Map<String,Object> map =new HashMap<String,Object>();
    	map.put("orderSn", orderSn);
    	return mybatisDaoContext.countBySlave(SQLID + "selectCountContactByOrderSn",map);
    }
}