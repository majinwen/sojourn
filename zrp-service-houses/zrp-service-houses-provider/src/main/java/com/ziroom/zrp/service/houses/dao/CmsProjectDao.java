package com.ziroom.zrp.service.houses.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.houses.entity.CmsProjectEntity;
import com.ziroom.zrp.houses.entity.CmsProjectLabelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("houses.cmsProjectDao")
public class CmsProjectDao {


    private String SQLID = "houses.cmsProjectDao.";

    @Autowired
    @Qualifier("houses.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 查询项目标签信息
     * @param projectId 项目标识
     * @return
     * @author cuigh6
     * @Date 2018年3月5日
     */
    public CmsProjectEntity getCmsProject(String projectId){
        return this.mybatisDaoContext.findOneSlave(SQLID + "getCmsProject", CmsProjectEntity.class, projectId);
    }



}