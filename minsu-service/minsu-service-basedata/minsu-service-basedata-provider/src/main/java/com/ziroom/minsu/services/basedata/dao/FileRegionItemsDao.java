package com.ziroom.minsu.services.basedata.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.file.FileRegionItemsEntity;
import com.ziroom.minsu.services.basedata.dto.CityArchivesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User: lunan
 * Date: 2016/11/9
 */
@Repository("basedata.fileRegionItemsDao")
public class FileRegionItemsDao {

    private String SQLID = "basedata.fileRegionItemsDao.";

    @Autowired
    @Qualifier("basedata.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    public FileRegionItemsEntity getRegionItem(String itemFid){
        return mybatisDaoContext.findOne(SQLID+"getRegionItem",FileRegionItemsEntity.class,itemFid);
    }

    public void saveRegionItem(FileRegionItemsEntity fileRegionItemsEntity){
        mybatisDaoContext.save(SQLID+"saveRegionItem",fileRegionItemsEntity);
    }

    public void updateRegionItem(FileRegionItemsEntity fileRegionItemsEntity){
        mybatisDaoContext.update(SQLID+"upRegionItem",fileRegionItemsEntity);
    }

    public List<FileRegionItemsEntity> getItemsByHotRegionFid(CityArchivesRequest cityArchivesRequest){
        return mybatisDaoContext.findAll(SQLID+"getItemsByHotRegionFid",FileRegionItemsEntity.class,cityArchivesRequest);
    }

}
