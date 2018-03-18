package com.zra.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.cms.dao.CmsProjectLabelMapper;
import com.zra.cms.entity.CmsProjectLabel;

/**
 * @author cuiyh9
 *
 */
@Component
public class CmsProjectLabelService {
    
    @Autowired
    private CmsProjectLabelMapper cmsProjectLabelMapper;
 
    public List<CmsProjectLabel> findByProjectId(String projectId) {
        return cmsProjectLabelMapper.selectByProjectId(projectId);
    }
}

