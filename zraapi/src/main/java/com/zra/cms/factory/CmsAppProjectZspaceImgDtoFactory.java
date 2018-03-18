package com.zra.cms.factory;

import com.zra.cms.entity.CmsProjectZspaceImg;
import com.zra.cms.entity.dto.CmsAppProjectZspaceImgDto;
import static com.zra.common.utils.PicUtils.*;
public enum CmsAppProjectZspaceImgDtoFactory {

    INSTANCE;

    /**
     * 将CmsProjectZspaceImg(数据库中实体)转为调用方真正关心的CmsAppProjectTrafficDto对象.
     * @return
     */
    public CmsAppProjectZspaceImgDto build(CmsProjectZspaceImg zspaceImg) {
        return new CmsAppProjectZspaceImgDto(wrapPicUrl(zspaceImg.getImgUrl()), zspaceImg.getImgOrder(), zspaceImg.getImgDesc());
    }
   
    
}
