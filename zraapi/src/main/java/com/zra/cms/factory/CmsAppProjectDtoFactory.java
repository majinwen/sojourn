package com.zra.cms.factory;

import java.util.ArrayList;
import java.util.List;

import com.zra.cms.entity.CmsProject;
import com.zra.cms.entity.CmsProjectLabel;
import com.zra.cms.entity.CmsProjectLabelImg;
import com.zra.cms.entity.CmsProjectTraffic;
import com.zra.cms.entity.CmsProjectZspaceImg;
import com.zra.cms.entity.dto.CmsAppProjectDto;
import com.zra.cms.entity.dto.CmsAppProjectLabelDto;
import com.zra.cms.entity.dto.CmsAppProjectTrafficDto;
import com.zra.cms.entity.dto.CmsAppProjectZspaceImgDto;
import static com.zra.common.utils.PicUtils.*;
public enum CmsAppProjectDtoFactory {

    INSTANCE;

    /**
     * 构建项目配置信息实体.
     * @return
     */
    public CmsAppProjectDto buildCmsAppProjectDto(CmsProject cmsProject, List<CmsProjectLabel> cmsProjectLabelList,
            List<CmsProjectLabelImg> cmsProjectLabelImgList, List<CmsProjectTraffic> cmsProjectTrafficList,
            List<CmsProjectZspaceImg> cmsProjectZspaceImgList) {

        List<CmsAppProjectLabelDto> appProjectLabelDtoList = CmsAppProjectLabelDtoFactory.INSTANCE.build(cmsProjectLabelList, cmsProjectLabelImgList);

        List<CmsAppProjectTrafficDto> appProjectTrafficDtoList = copyProjectTrafficDtoList(cmsProjectTrafficList);

        List<CmsAppProjectZspaceImgDto> appProjectZspaceImgDtoList = this.copyProjectZspaceImg(cmsProjectZspaceImgList);

        CmsAppProjectDto cmsAppProjectDto = new CmsAppProjectDto(cmsProject.getProjectId(), cmsProject.getProjectTitle(), cmsProject.getProjectDescribe(), wrapPicUrl(cmsProject.getShowImg()),
                cmsProject.getPanoramicUrl(), cmsProject.getPeripheralUrl(), cmsProject.getShareUrl(),
                wrapPicUrl(cmsProject.getHeadImg()), cmsProject.getZspaceDesc(), cmsProject.getZoDesc(), wrapPicUrl(cmsProject.getZoImgUrl()),
                cmsProject.getZoServiceDesc(), appProjectLabelDtoList, appProjectTrafficDtoList,
                appProjectZspaceImgDtoList);

        return cmsAppProjectDto;

    }


    /**
     * @param cmsProjectTrafficList
     * @return
     */
    private List<CmsAppProjectTrafficDto> copyProjectTrafficDtoList(List<CmsProjectTraffic> cmsProjectTrafficList) {
        
        if (cmsProjectTrafficList == null) {
            return null;
        }

        List<CmsAppProjectTrafficDto> trafficDtoList = new ArrayList<CmsAppProjectTrafficDto>();
        for (CmsProjectTraffic cmsProjectTraffic : cmsProjectTrafficList) {
            CmsAppProjectTrafficDto trafficDto = CmsAppProjectTrafficDtoFactory.INSTANCE.build(cmsProjectTraffic);
            trafficDtoList.add(trafficDto);
        }

        return trafficDtoList;
    }

    /**
     * @param cmsProjectZspaceImgList
     * @return
     */
    private List<CmsAppProjectZspaceImgDto> copyProjectZspaceImg(List<CmsProjectZspaceImg> cmsProjectZspaceImgList) {
        if (cmsProjectZspaceImgList == null) {
            return null;
        }

        List<CmsAppProjectZspaceImgDto> zspaceImgDtoList = new ArrayList<CmsAppProjectZspaceImgDto>();
        for (CmsProjectZspaceImg cmsProjectZspaceImg : cmsProjectZspaceImgList) {
            CmsAppProjectZspaceImgDto zspaceImgDto = CmsAppProjectZspaceImgDtoFactory.INSTANCE
                    .build(cmsProjectZspaceImg);
            zspaceImgDtoList.add(zspaceImgDto);
        }
        return zspaceImgDtoList;
    }

}
