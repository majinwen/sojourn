package com.ziroom.minsu.report.common.service;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/6.
 * @version 1.0
 * @since 1.0
 */
public interface ReportService<P extends BaseEntity,T extends PageRequest> {

    public PagingResult<P> getPageInfo(T par);
    public Long countDataInfo(T par);
   
}
