package com.ziroom.minsu.services.search.vo;

import com.asura.framework.base.entity.BaseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>房源的图片信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/6.
 * @version 1.0
 * @since 1.0
 */
public class HousePicVo extends BaseEntity {

    /** 序列化id */
    private static final long serialVersionUID = -45648971345456L;


    /** 房源图片 */
    private String picUrl;


    /** 房源图片 后缀 */
    private String picSuffix;


    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicSuffix() {
        return picSuffix;
    }

    public void setPicSuffix(String picSuffix) {
        this.picSuffix = picSuffix;
    }
}
