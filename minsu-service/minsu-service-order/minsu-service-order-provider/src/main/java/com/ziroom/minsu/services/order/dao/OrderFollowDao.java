package com.ziroom.minsu.services.order.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderFollowEntity;
import com.ziroom.minsu.entity.order.OrderFollowLogEntity;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.order.dto.OrderFollowRequest;
import com.ziroom.minsu.services.order.dto.OrderRequest;
import com.ziroom.minsu.services.order.entity.OrderFollowVo;
import com.ziroom.minsu.services.order.entity.OrderHouseVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.entity.UidVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>订单跟进</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/12/14.
 * @version 1.0
 * @since 1.0
 */
@Repository("order.orderFollowDao")
public class OrderFollowDao {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(OrderFollowDao.class);
    private String SQLID = "order.orderFollowDao.";

    @Autowired
    @Qualifier("order.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 更新前的跟进表
     * @param orderSn
     * @return
     */
    public int updateOrderFollowOverByOrderSn(String  orderSn){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderSn", orderSn);
        return this.mybatisDaoContext.update(SQLID+"updateOrderFollowOverByOrderSn", map);
    }



    /**
     * 保存订单的操作记录
     * @author afi
     * @created 2016年3月31日 下午20:22:38
     * @param log
     * @return
     */
    public int saveOrderFollow(OrderFollowEntity log) {
        if(Check.NuNObj(log)){
            LogUtil.info(logger,"current log is null on saveOrderFollow");
            throw new BusinessException("current log is null on saveOrderFollow");
        }
        if(Check.NuNStr(log.getOrderSn())){
            LogUtil.info(logger,"orderSn is null on saveOrderFollow");
            throw new BusinessException("orderSn is null on saveOrderFollow");
        }
        if(Check.NuNObj(log.getOrderStatus())){
            LogUtil.info(logger,"orderStatus is null on saveOrderFollow");
            throw new BusinessException("orderStatus is null on saveOrderFollow");
        }

        if(Check.NuNStr(log.getFid())){
            log.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID + "saveOrderFollow", log);
    }


    /**
     * 获取跟进表
     * @author afi
     * @created 2016年12月18日 下午20:22:38
     * @return
     */
    public OrderFollowEntity getOrderFollowByOrderSn(String orderSn) {
        if (Check.NuNStr(orderSn)){
            return null;
        }
        return mybatisDaoContext.findOne(SQLID + "getOrderFollowByOrderSn", OrderFollowEntity.class,orderSn);
    }



    public PagingResult<UidVo> getOrderFollowUidByPage(OrderFollowRequest request){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(request.getLimit());
        pageBounds.setPage(request.getPage());
        // request.setLimitTime(DateSplitUtil.jumpHours(new Date(),-24));
        return mybatisDaoContext.findForPage(SQLID + "getOrderFollowUidByPage", UidVo.class, request, pageBounds);
    }


    /**
     * 分页查询24小时之内的信息
     * @author afi
     * @param request
     * @return
     */
    public List<OrderFollowVo> getOrderFollow(OrderFollowRequest request){
        // request.setLimitTime(DateSplitUtil.jumpHours(new Date(),-24));
        return mybatisDaoContext.findAll(SQLID + "getOrderFollow", OrderFollowVo.class, request.toMap());
    }


}


