package com.ziroom.minsu.services.basedata.test.service;

import com.ziroom.minsu.entity.file.FileRegionEntity;
import com.ziroom.minsu.services.basedata.service.CityArchiveServiceImpl;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA
 * User: lunan
 * Date: 2016/11/9
 */
public class CityArchiveServiceImplTest extends BaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CityArchiveServiceImplTest.class);

    @Resource(name = "basedata.cityArchiveServiceImpl")
    private CityArchiveServiceImpl cityArchiveServiceImpl;

    @Test
    public void saveRegionTest(){
        FileRegionEntity fileRegionEntity = new FileRegionEntity();
        String cityCode = "110100";
        fileRegionEntity.setFid("8a9e9cd955c9c3490155ca3c86660019");
        fileRegionEntity.setHotRegionFid("18a9e9aa856126a5b0156126a5bd30000");
        fileRegionEntity.setHotRegionBrief("北京，北京路商圈描述--测试修改");
        fileRegionEntity.setHotRegionContent("欢迎来北京路玩，只是为了测试，不要太多字哦");
        fileRegionEntity.setCreateFid("8a9e9aaf5456d812015456de97d406aa");
//        cityArchiveServiceImpl.saveRegion(fileRegionEntity,cityCode);
        cityArchiveServiceImpl.updateRegion(fileRegionEntity);
    }
}
