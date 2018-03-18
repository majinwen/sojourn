package com.zra.house.entity.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/9 10:52
 * @since 1.0
 */
public class PageDto {

    /**
     * 当前页码
     */
    private Integer pageNum = 1;
    /**
     * 每页大小
     */
    private Integer pageSize = 10;
    /**
     * 总数
     */
    @ApiModelProperty(value = "总数")
    private Long total = 0L;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
    /**
     * 获取任一页第一条数据的位置,startIndex从0开始
     */
    public Integer getPageStart() {
        return (pageNum - 1) * pageSize;
    }
}
