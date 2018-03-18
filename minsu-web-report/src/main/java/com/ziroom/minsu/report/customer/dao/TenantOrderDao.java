/**
 * @FileName: TenantOrderDao.java
 * @Package com.ziroom.minsu.report.customer.dao
 * 
 * @author zl
 * @created 2017年5月8日 上午10:28:35
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.report.customer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.report.customer.dto.TenantRequest;
import com.ziroom.minsu.report.customer.vo.TenantOrderVo;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
@Repository("report.tenantOrderDao")
public class TenantOrderDao {
	
	
	private String SQLID="report.tenantOrderDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /** 
     * 
     * 房客订单统计信息
     *
     * @author zl
     * @created 2017年5月8日 上午10:38:38
     *
     * @param request
     * @return
     */
    public PagingResult<TenantOrderVo> getTenantOrderList(TenantRequest request){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(request.getLimit());
        pageBounds.setPage(request.getPage());
        return mybatisDaoContext.findForPage(SQLID + "tenantOrder", TenantOrderVo.class, request.toMap(), pageBounds);
    }
	

}
