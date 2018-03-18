package com.zra.cms.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zra.cms.entity.CmsProjectLabel;
import com.zra.cms.entity.CmsProjectLabelImg;
import com.zra.cms.entity.dto.CmsAppProjectLabelDto;
import com.zra.cms.entity.dto.CmsAppProjectLabelImgDto;
import com.zra.cms.entity.dto.CmsAppProjectTrafficDto;

import static com.zra.common.utils.PicUtils.wrapPicUrl;

public enum CmsAppProjectLabelDtoFactory {

    INSTANCE;

    /**
     * 构建项目标签实体.
     * @return
     */
    public List<CmsAppProjectLabelDto> build(List<CmsProjectLabel> cmsProjectLabelList,
            List<CmsProjectLabelImg> cmsProjectLabelImgList) {
        if (cmsProjectLabelList == null) {
            return null;
        }
        
        Map<String, List<CmsProjectLabelImg>> labelImgMap = buildLabelImgMap(cmsProjectLabelImgList);
       
        List<CmsAppProjectLabelDto> cmsAppProjectLabelDtoList = new ArrayList<CmsAppProjectLabelDto>();
        
        for (CmsProjectLabel label : cmsProjectLabelList) {
            List<CmsProjectLabelImg> labelImgList = labelImgMap.get(label.getFid());
            List<CmsAppProjectLabelImgDto> cmsAppProjectLabelImgDtoList = buildCmsAppProjectLabelImgDto(labelImgList);
            
            CmsAppProjectLabelDto cmsAppProjectLabelDto = new CmsAppProjectLabelDto(label.getModuleName(), label.getModuleOrder(),
                    cmsAppProjectLabelImgDtoList);
            cmsAppProjectLabelDtoList.add(cmsAppProjectLabelDto);
        }
        
        return cmsAppProjectLabelDtoList;
    }
    
    /**
     * 将项目标签图片的列表转为为Map形式.
     * Map中的key为项目标签Id,Value为项目标签对应的目标签图片列表
     * @param cmsProjectLabelImgList
     * @return
     */
    private Map<String, List<CmsProjectLabelImg>> buildLabelImgMap(List<CmsProjectLabelImg> cmsProjectLabelImgList) {
        //转换成标签ID和标签图片列表
        Map<String, List<CmsProjectLabelImg>> labelImgMap = new HashMap<String, List<CmsProjectLabelImg>>();
        if (cmsProjectLabelImgList != null) {
            
            for (CmsProjectLabelImg cmsProjectLabelImg : cmsProjectLabelImgList) {
                String projectLabelFid = cmsProjectLabelImg.getProjectLabelFid();
                cmsProjectLabelImg.setImgUrl(wrapPicUrl(cmsProjectLabelImg.getImgUrl()));
                List<CmsProjectLabelImg> labelImgList = labelImgMap.get(projectLabelFid);
                if (labelImgList == null) {
                    labelImgList = new ArrayList<CmsProjectLabelImg>();
                }
                labelImgList.add(cmsProjectLabelImg);
                labelImgMap.put(projectLabelFid, labelImgList);
            }
            
        }
        
        return labelImgMap;
    }
    
    /**
     * 构建项目标签图片实体.
     * @param labelImgList
     * @return
     */
    public List<CmsAppProjectLabelImgDto> buildCmsAppProjectLabelImgDto(List<CmsProjectLabelImg> labelImgList) {
        if (labelImgList == null) {
            return null;
        }
        List<CmsAppProjectLabelImgDto> cmsAppProjectLabelImgDtoList= new ArrayList<CmsAppProjectLabelImgDto>();
        for (CmsProjectLabelImg cmsProjectLabelImg : labelImgList) {
            CmsAppProjectLabelImgDto cmsAppProjectLabelImgDto = new CmsAppProjectLabelImgDto(cmsProjectLabelImg.getImgUrl(), cmsProjectLabelImg.getImgOrder());
            cmsAppProjectLabelImgDtoList.add(cmsAppProjectLabelImgDto);
        }
        return cmsAppProjectLabelImgDtoList;
    }
    
   
}
