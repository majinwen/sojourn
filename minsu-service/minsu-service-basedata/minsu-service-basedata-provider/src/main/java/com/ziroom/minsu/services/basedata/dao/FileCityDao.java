package com.ziroom.minsu.services.basedata.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.file.FileCityEntity;
import com.ziroom.minsu.services.basedata.dto.CityArchivesRequest;
import com.ziroom.minsu.services.basedata.entity.CityArchivesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * User: lunan
 * Date: 2016/11/8
 */
@Repository("basedata.fileCityDao")
public class FileCityDao {

    private String SQLID = "basedata.fileCityDao.";

    @Autowired
    @Qualifier("basedata.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 分页查询城市档案列表
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/10 18:56
     */
    public PagingResult<CityArchivesVo> searchArchivesList(CityArchivesRequest cityArchivesRequest){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setPage(cityArchivesRequest.getPage());
        pageBounds.setLimit(cityArchivesRequest.getLimit());
        return mybatisDaoContext.findForPage(SQLID+"searchArchivesList", CityArchivesVo.class, cityArchivesRequest.toMap(), pageBounds);
    }

    /**
     * 保存城市档案
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/10 18:56
     */
    public void saveArchive(FileCityEntity fileCityEntity){
        mybatisDaoContext.save(SQLID+"saveArchive",fileCityEntity);
    }

    /**
     * 查询一条城市档案
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/10 19:06
     */
    public FileCityEntity getFile(String fid){
        return mybatisDaoContext.findOne(SQLID+"getFile",FileCityEntity.class,fid);
    }

    public void updateFile(FileCityEntity fileCityEntity){
        mybatisDaoContext.update(SQLID+"updateFile",fileCityEntity);
    }

    /**
     * 更新t_conf_city表
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/10 15:56
     */
    public void upCityIsFile(String cityCode){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("cityCode",cityCode);
        mybatisDaoContext.update(SQLID+"upCityIsFile",map);
    }

    /**
     * 更新t_hot_conf_region表
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/10 15:56
     */
    public void updateHotConfRegion(String hotRegionFid){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("hotRegionFid",hotRegionFid);
        mybatisDaoContext.update(SQLID+"updateHotConfRegion",map);
    }
}
