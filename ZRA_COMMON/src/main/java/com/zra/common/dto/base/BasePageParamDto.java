package com.zra.common.dto.base;

/**
 * @author wangws21 2016年9月9日
 * 分页参数基础Dto 用于继承
 */
public class BasePageParamDto {
    /**
     * 页数,当前第几页
     */
    private Integer page;
    
    /**
     * 每页显示条数
     */
    private Integer rows;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }


}
