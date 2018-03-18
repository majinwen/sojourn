package com.zra.cms.dao;

import java.util.List;

import com.zra.cms.entity.CmsProjectZspaceImg;

public interface CmsProjectZspaceImgMapper {

    List<CmsProjectZspaceImg> findByProjectId(String projectId);


}