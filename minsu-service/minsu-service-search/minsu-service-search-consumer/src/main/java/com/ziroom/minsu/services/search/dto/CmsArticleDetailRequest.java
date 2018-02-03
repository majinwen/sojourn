package com.ziroom.minsu.services.search.dto;

import com.asura.framework.base.entity.BaseEntity;

import java.io.Serializable;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zl
 * @version 1.0
 * @Date Created in 2017年08月02日 16:09
 * @since 1.0
 */
public class CmsArticleDetailRequest extends BaseSearchRequest {

    private static final long serialVersionUID = -5353602090077120298L;
    private  String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
