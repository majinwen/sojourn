package com.ziroom.minsu.api.customer.entity;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.entity.base.MinsuShowEntity;
import java.util.List;

/**
 * <p>类型对象封装</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/9/8.
 * @version 1.0
 * @since 1.0
 */
public class TypeVo extends BaseEntity{


    /** 序列化ID */
    private static final long serialVersionUID = 774312321957371L;

    //标题
    private String title;

    //列表
    private List<MinsuShowEntity> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MinsuShowEntity> getList() {
        return list;
    }

    public void setList(List<MinsuShowEntity> list) {
        this.list = list;
    }
}
