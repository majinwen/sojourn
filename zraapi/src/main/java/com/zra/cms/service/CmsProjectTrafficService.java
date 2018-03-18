package com.zra.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.cms.dao.CmsProjectTrafficMapper;
import com.zra.cms.entity.CmsProjectTraffic;

/**
 * @author cuiyh9
 *
 */
@Component
public class CmsProjectTrafficService {
    
    @Autowired
    private CmsProjectTrafficMapper cmsProjectTrafficMapper;
    
    public List<CmsProjectTraffic> findByProjectId(String projectId) {
        return cmsProjectTrafficMapper.findByProjectId(projectId);
    }
    
}

