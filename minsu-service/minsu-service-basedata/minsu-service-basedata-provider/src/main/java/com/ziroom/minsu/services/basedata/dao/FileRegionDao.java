package com.ziroom.minsu.services.basedata.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.file.FileRegionEntity;
import com.ziroom.minsu.services.basedata.dto.CityArchivesRequest;
import com.ziroom.minsu.services.basedata.entity.CityArchivesVo;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User: lunan
 * Date: 2016/11/9
 */
@Repository("basedata.fileRegionDao")
public class FileRegionDao {

    private String SQLID = "basedata.fileRegionDao.";

    @Autowired
    @Qualifier("basedata.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 查询城市景点商圈
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/9 10:55
     */
    public List<CityArchivesVo> getRegionList(CityArchivesRequest cityArchivesRequest){
        return mybatisDaoContext.findAll(SQLID+"getRegionList",CityArchivesVo.class,cityArchivesRequest);
    }

    /**
     * 查询景点商圈及下辖推荐项
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/11 14:39
     */
    public List<CityArchivesVo> getRegionItems(CityArchivesRequest cityArchivesRequest){
        return mybatisDaoContext.findAll(SQLID+"getRegionItems",CityArchivesVo.class,cityArchivesRequest);
    }

    /**
     * 根据景点商圈fid查询一条景点商圈内容记录
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/9 15:18
     */
    public FileRegionEntity getRegion(FileRegionEntity fileRegionEntity){
        return mybatisDaoContext.findOne(SQLID+"getRegion",FileRegionEntity.class,fileRegionEntity);
    }

    /**
     * 插入一条景点商圈内容记录
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/9 17:17
     */
    public void saveRegion(FileRegionEntity fileRegionEntity){
        mybatisDaoContext.save(SQLID+"saveRegion",fileRegionEntity);
    }

    /**
     * 修改景点商圈内容和描述
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/9 17:17
     */
    public void updateRegion(FileRegionEntity fileRegionEntity){
        mybatisDaoContext.update(SQLID+"upRegion",fileRegionEntity);
    }


}
