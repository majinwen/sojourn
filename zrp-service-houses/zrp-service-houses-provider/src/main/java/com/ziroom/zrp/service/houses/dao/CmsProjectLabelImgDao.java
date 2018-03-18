package com.ziroom.zrp.service.houses.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.houses.entity.CmsProjectLabelEntity;
import com.ziroom.zrp.houses.entity.CmsProjectLabelImgEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("houses.cmsProjectLabelImgDao")
public class CmsProjectLabelImgDao {


    private String SQLID = "houses.cmsProjectLabelImgDao.";

    @Autowired
    @Qualifier("houses.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 查询项目标签信息
     * @param projectLabelFid 项目标签标识
     * @return
     * @author cuigh6
     * @Date 2018年3月5日
     */
    public List<CmsProjectLabelImgEntity> getCmsProjectLabelImgs(String projectLabelFid) {
        return this.mybatisDaoContext.findAll(SQLID + "getCmsProjectLabelImgs", projectLabelFid);

    }
}