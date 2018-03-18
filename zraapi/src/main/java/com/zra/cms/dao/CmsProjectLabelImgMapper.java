package com.zra.cms.dao;

import java.util.List;

import com.zra.cms.entity.CmsProjectLabelImg;

public interface CmsProjectLabelImgMapper {

    List<CmsProjectLabelImg> findByProjectLabelFids(String[] projectLabelFids);

}