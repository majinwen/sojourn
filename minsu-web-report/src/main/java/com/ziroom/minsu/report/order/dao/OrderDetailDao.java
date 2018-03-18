package com.ziroom.minsu.report.order.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.report.order.dto.OrderFinanceRequest;
import com.ziroom.minsu.report.order.dto.OrderFreshRequest;
import com.ziroom.minsu.report.order.dto.OrderInformationRequest;
import com.ziroom.minsu.report.order.dto.OrderRequest;
import com.ziroom.minsu.report.order.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p>OrderDetailDao</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/5.
 * @version 1.0
 * @since 1.0
 */
@Repository("report.orderDetailDao")
public class OrderDetailDao {

    private String SQLID="report.orderDetailDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 分页查询
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public PagingResult<HouseOrderInfoVo> getHouseOrderInfo(OrderRequest afiRequest,int type){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(afiRequest.getLimit());
        pageBounds.setPage(afiRequest.getPage());
        
        if(type == 1){
        	return mybatisDaoContext.findForPage(SQLID + "entireHouseOrderInfo", HouseOrderInfoVo.class, afiRequest, pageBounds);
        }else if(type == 2){
        	return mybatisDaoContext.findForPage(SQLID + "subHouseOrderInfo", HouseOrderInfoVo.class, afiRequest, pageBounds);
        }
        return null;
    }
    
    /**
     * 订单明细信息
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public PagingResult<OrderDetailVo> getOrderStaticsInfo(OrderRequest afiRequest){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(afiRequest.getLimit());
        pageBounds.setPage(afiRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID + "orderStaticsInfo", OrderDetailVo.class, afiRequest, pageBounds);
    }



    /**
     * 订单信息报表
     * @author lishaochuan
     * @create 2017/3/7 16:27
     * @param 
     * @return 
     */
    public PagingResult<OrderInformationVo> getOrderInformation(OrderInformationRequest request){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(request.getLimit());
        pageBounds.setPage(request.getPage());
        return mybatisDaoContext.findForPage(SQLID + "getOrderInformation", OrderInformationVo.class, request.toMap(), pageBounds);
    }


    /**
     * 订单财务报表
     * @author lishaochuan
     * @create 2017/3/9 14:46
     * @param
     * @return
     */
    public PagingResult<OrderFinanceVo> getOrderFinance(OrderFinanceRequest request){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(request.getLimit());
        pageBounds.setPage(request.getPage());
        return mybatisDaoContext.findForPage(SQLID + "getOrderFinance", OrderFinanceVo.class, request.toMap(), pageBounds);
    }



    /**
     * 刷单信息报表
     *
     * @author lishaochuan
     * @create 2017/3/14 9:22
     * @param 
     * @return 
     */
    public PagingResult<OrderFreshVo> getOrderFresh(OrderFreshRequest request){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(request.getLimit());
        pageBounds.setPage(request.getPage());
        return mybatisDaoContext.findForPage(SQLID + "getOrderFresh", OrderFreshVo.class, request.toMap(), pageBounds);
    }
}
