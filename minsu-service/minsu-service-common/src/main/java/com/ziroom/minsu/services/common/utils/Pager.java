package com.ziroom.minsu.services.common.utils;

import com.asura.framework.base.util.Check;

/**
 * <p>分页参数信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/16.
 * @version 1.0
 * @since 1.0
 */
public class Pager {

    public final static int FIRST_PAGE = 0;

    public final static int MAX_PAGE_SIZE = 100;

    private int pageIndex;

    private int pageSize;

    public Pager() {
        this(FIRST_PAGE, MAX_PAGE_SIZE);
    }

    public Pager(final int pageIndex, final int pageSize) {
        this.pageIndex = (pageIndex - 1) * pageSize;
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(final int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    public static Pager rebuildPager(final int pageIndex, final int pageSize) {
        return rebuildPager(new Pager(pageIndex, pageSize));
    }

    /**
     * 重建分页
     *
     * @param pager
     */
    public static Pager rebuildPager(final Pager pager) {
        Pager newPager = pager;
        if (Check.NuNObj(newPager)) {
            newPager = new Pager();
        }
        if (newPager.pageIndex < 0) {
            newPager.pageIndex = FIRST_PAGE;
        }

        if (newPager.pageSize <= 0 || newPager.pageSize > MAX_PAGE_SIZE) {
            newPager.pageSize = MAX_PAGE_SIZE;
        }

        //newPager.pageIndex = (newPager.pageIndex - 1) * newPager.pageSize;

        return newPager;
    }
}
