package com.ziroom.minsu.report.cms.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.report.cms.dto.CouponInfoRequest;
import com.ziroom.minsu.report.cms.vo.CouponInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2017/3/15 10:32
 * @version 1.0
 * @since 1.0
 */
@Repository("report.couponInfoDao")
public class CouponInfoDao {

    private String SQLID = "report.couponInfoDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 优惠券信息报表
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2017/3/15 11:04
     */
    public PagingResult<CouponInfoVo> getCouponInfo(CouponInfoRequest request) {
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(request.getLimit());
        pageBounds.setPage(request.getPage());
        return mybatisDaoContext.findForPage(SQLID + "getCouponInfo", CouponInfoVo.class, request.toMap(), pageBounds);
    }


}
