package com.zra.cms.dao;

import java.util.List;

import com.zra.cms.entity.CmsProjectLabel;

public interface CmsProjectLabelMapper {
   
    List<CmsProjectLabel> selectByProjectId(String projectId);
}