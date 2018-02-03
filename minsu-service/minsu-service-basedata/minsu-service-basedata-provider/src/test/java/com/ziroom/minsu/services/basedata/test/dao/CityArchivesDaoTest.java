package com.ziroom.minsu.services.basedata.test.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.file.FileRegionEntity;
import com.ziroom.minsu.entity.file.FileRegionItemsEntity;
import com.ziroom.minsu.services.basedata.dao.FileCityDao;
import com.ziroom.minsu.services.basedata.dao.FileRegionDao;
import com.ziroom.minsu.services.basedata.dao.FileRegionItemsDao;
import com.ziroom.minsu.services.basedata.dto.CityArchivesRequest;
import com.ziroom.minsu.services.basedata.entity.CityArchivesVo;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User: lunan
 * Date: 2016/11/8
 */
public class CityArchivesDaoTest extends BaseTest {
    @Resource(name = "basedata.fileCityDao")
    private FileCityDao fileCityDao;

    @Resource(name = "basedata.fileRegionDao")
    private FileRegionDao fileRegionDao;

    @Resource(name = "basedata.fileRegionItemsDao")
    private FileRegionItemsDao fileRegionItemsDao;

    @Test
    public void getCityArchivesListTest(){
        CityArchivesRequest cityArchivesRequest = new CityArchivesRequest();
        PagingResult<CityArchivesVo> result = fileCityDao.searchArchivesList(cityArchivesRequest);
        System.err.println(result);
    }

    @Test
    public void getRegionListTest(){
        CityArchivesRequest cityArchivesRequest = new CityArchivesRequest();
        cityArchivesRequest.setCityCode("110100");
        //cityArchivesRequest.setIsFile(1);
        List<CityArchivesVo> regionList = fileRegionDao.getRegionList(cityArchivesRequest);
        System.err.println(regionList.size());
    }

    @Test
    public void getRegionTest(){
        FileRegionEntity fileRegionEntity = new FileRegionEntity();
        fileRegionEntity.setHotRegionFid("18a9e9aa856126a5b0156126a5bd30000");
        FileRegionEntity region = fileRegionDao.getRegion(fileRegionEntity);
        if(Check.NuNObj(region)){
            System.err.println("插入记录");
        }else{
            System.err.println("修改记录");
        }
    }

    @Test
    public void getRegionItemTest(){
        String itemFid = "18a9e9aa856126a5b0156126a5bd30000";
        FileRegionItemsEntity regionItem = fileRegionItemsDao.getRegionItem(itemFid);
        if(Check.NuNObj(regionItem)){
            System.err.println("插入记录");
        }else{
            System.err.println("修改记录");
        }
    }

    @Test
    public void getRegionItemsTest(){
        CityArchivesRequest cityArchivesRequest = new CityArchivesRequest();
        cityArchivesRequest.setCityCode("110100");
        List<CityArchivesVo> regionItems = fileRegionDao.getRegionItems(cityArchivesRequest);
        System.err.println(regionItems.size());
    }
}
