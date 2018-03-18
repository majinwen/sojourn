package com.zra.cms.dao;

import java.util.List;

import com.zra.cms.entity.CmsProjectTraffic;

public interface CmsProjectTrafficMapper {
 
    List<CmsProjectTraffic> findByProjectId(String projectId);
}