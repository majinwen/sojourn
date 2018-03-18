package com.zra.cms.factory;

import com.zra.cms.entity.CmsProjectTraffic;
import com.zra.cms.entity.dto.CmsAppProjectTrafficDto;
import com.zra.cms.entity.dto.CmsAppProjectZspaceImgDto;

public enum CmsAppProjectTrafficDtoFactory {

    INSTANCE;

    /**
     * 将CmsProjectTraffic(数据库实体)转为调用法真正关心的CmsAppProjectTrafficDto对象.
     * @return
     */
    public CmsAppProjectTrafficDto build(CmsProjectTraffic traffic) {
        return new CmsAppProjectTrafficDto(traffic.getTrafficDes(), traffic.getTrafficOrder());
    }
   
}
