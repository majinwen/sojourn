package com.ziroom.minsu.report.order.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.report.order.dto.OrderContactRequest;
import com.ziroom.minsu.report.order.vo.OrderContactVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>订单联系人</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2017/3/5.
 * @version 1.0
 * @since 1.0
 */
@Repository("report.orderContactDao")
public class OrderContactDao {

    private String SQLID="report.orderContactDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 分页查询
     * @author afi
     * @param orderContactRequest
     * @return
     */
    public PagingResult<OrderContactVo> getOrderContactByPage(OrderContactRequest orderContactRequest){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(orderContactRequest.getLimit());
        pageBounds.setPage(orderContactRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID + "getOrderContactList", OrderContactVo.class, orderContactRequest.toMap(), pageBounds);
    }

    /**
     * 获取订单联系列表
	 * @author afi
     * @param orderContactRequest
     * @return
     */
    public List<OrderContactVo> getOrderContactList(OrderContactRequest orderContactRequest){
		return mybatisDaoContext.findAll(SQLID+"getOrderContactList", OrderContactVo.class, orderContactRequest);
    }
}
