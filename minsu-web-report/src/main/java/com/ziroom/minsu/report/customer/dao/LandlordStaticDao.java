/**
 * @FileName: LandlordStaticDao.java
 * @Package com.ziroom.minsu.report.customer.dao
 * 
 * @author zl
 * @created 2017年5月18日 上午10:28:35
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
import com.ziroom.minsu.report.customer.dto.LandlordRequest;
import com.ziroom.minsu.report.customer.vo.LandlordStaticVo;

/**
 * <p>房东粘性</p>
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
@Repository("report.landlordStaticDao")
public class LandlordStaticDao {
	
	
	private String SQLID="report.landlordStaticDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /** 
     * 
     * 房东粘性统计信息
     *
     * @author zl
     * @created 2017年5月18日 上午10:38:38
     *
     * @param request
     * @return
     */
    public PagingResult<LandlordStaticVo> getList(LandlordRequest request){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(request.getLimit());
        pageBounds.setPage(request.getPage());
        return mybatisDaoContext.findForPage(SQLID + "landlordStatic", LandlordStaticVo.class, request.toMap(), pageBounds);
    }
	

}
