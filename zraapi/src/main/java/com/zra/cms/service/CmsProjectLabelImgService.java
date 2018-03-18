package com.zra.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.cms.dao.CmsProjectLabelImgMapper;
import com.zra.cms.entity.CmsProjectLabelImg;

/**
 * @author cuiyh9
 *
 */
@Component
public class CmsProjectLabelImgService {
    
    @Autowired
    private CmsProjectLabelImgMapper cmsProjectLabelImgMapper;
    
    public List<CmsProjectLabelImg> findByProjectLabelFids(String[] projectLabelFids) {
        return cmsProjectLabelImgMapper.findByProjectLabelFids(projectLabelFids);
    }
}

