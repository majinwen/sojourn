package com.ziroom.minsu.report.customer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.report.house.dto.HouseRequest;
import com.ziroom.minsu.report.customer.vo.UserCusInfoVo;

/**
 * <p>CusInfoDao</p>
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
@Repository("report.cusInfoDao")
public class CusInfoDao {

    private String SQLID="report.cusInfoDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 分页查询
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public PagingResult<UserCusInfoVo> getUserCusInfo(HouseRequest afiRequest){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(afiRequest.getLimit());
        pageBounds.setPage(afiRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID + "userCusInfo", UserCusInfoVo.class, afiRequest, pageBounds);
    }
}
