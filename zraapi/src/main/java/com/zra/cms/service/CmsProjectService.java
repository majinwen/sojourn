package com.zra.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.cms.dao.CmsProjectMapper;
import com.zra.cms.entity.CmsProject;

/**
 * @author cuiyh9
 *
 */
@Component
public class CmsProjectService {
    
    @Autowired
    private CmsProjectMapper cmsProjectMapper;
 
    public CmsProject getByProjectId(String projectId) {
        return cmsProjectMapper.selectByProjectId(projectId);
    }
    
}

