package com.ziroom.minsu.report.customer.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.report.customer.dto.LandlordRequest;
import com.ziroom.minsu.report.customer.dto.TenantRequest;
import com.ziroom.minsu.report.customer.vo.UserLandlordInfoVo;
import com.ziroom.minsu.report.customer.vo.UserTenantInfoVo;
import com.ziroom.minsu.report.order.dto.OrderEvaluate2Request;
import com.ziroom.minsu.report.order.vo.OrderEvaluateVo;

/**
 * <p>TenantInfoDao</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp on 2017/5/2.
 * @version 1.0
 * @since 1.0
 */
@Repository("report.tenantInfoDao")
public class TenantInfoDao {

    private String SQLID="report.tenantInfoDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 分页查询
     * @author lusp
     * @param request
     * @return
     */
    public PagingResult<UserTenantInfoVo> getTenantInfo(TenantRequest request){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(request.getLimit());
        pageBounds.setPage(request.getPage());
        return mybatisDaoContext.findForPage(SQLID + "tenantList", UserTenantInfoVo.class, request, pageBounds);
    }
}
