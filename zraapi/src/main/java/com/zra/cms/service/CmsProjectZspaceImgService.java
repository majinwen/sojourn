package com.zra.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.cms.dao.CmsProjectZspaceImgMapper;
import com.zra.cms.entity.CmsProjectZspaceImg;

/**
 * @author cuiyh9
 *
 */
@Component
public class CmsProjectZspaceImgService {
    
    @Autowired
    private CmsProjectZspaceImgMapper cmsProjectZspaceImgMapper;
 
    public List<CmsProjectZspaceImg> findByProjectId(String projectId) {
        return cmsProjectZspaceImgMapper.findByProjectId(projectId);
    }
}

