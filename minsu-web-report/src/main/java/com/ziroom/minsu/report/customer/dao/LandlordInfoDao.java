package com.ziroom.minsu.report.customer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.report.customer.dto.LandlordRequest;
import com.ziroom.minsu.report.customer.vo.UserLandlordInfoVo;

/**
 * <p>LandlordInfoDao</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp on 2017/4/28.
 * @version 1.0
 * @since 1.0
 */
@Repository("report.landlordInfoDao")
public class LandlordInfoDao {

    private String SQLID="report.landlordInfoDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 分页查询
     * @author lusp
     * @param landlordRequest
     * @return
     */
    public PagingResult<UserLandlordInfoVo> findLandlordItemList(LandlordRequest landlordRequest){
    	PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(landlordRequest.getLimit());
		pageBounds.setPage(landlordRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID+"LandlordList", UserLandlordInfoVo.class,landlordRequest,pageBounds);
    }
}
