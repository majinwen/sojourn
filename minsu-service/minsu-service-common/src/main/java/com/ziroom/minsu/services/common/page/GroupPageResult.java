package com.ziroom.minsu.services.common.page;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.common.utils.ValueUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/1/19.
 * @version 1.0
 * @since 1.0
 */
public class GroupPageResult extends PageResult{

    private int currentPage = 1;

    private int totalpages = 0;

    private int subTotal = 0;

    public GroupPageResult(PagingResult pagingResult, PageRequest request) {
        setRows(pagingResult.getRows());
        this.totalpages = ValueUtil.getPage(ValueUtil.getintValue(pagingResult.getTotal()),request.getLimit());
        this.currentPage = request.getPage();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalpages() {
        return totalpages;
    }

    public void setTotalpages(int totalpages) {
        this.totalpages = totalpages;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }
}
