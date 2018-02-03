package com.ziroom.minsu.services.house.test.dao;

import com.ziroom.minsu.entity.house.AbHouseStatusEntity;
import com.ziroom.minsu.services.house.airbnb.dto.AbHouseDto;
import com.ziroom.minsu.services.house.dao.AbHouseStatusDao;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by homelink on 2017/4/15.
 */
public class AbHouseStatusDaoTest extends BaseTest{

    @Resource(name="house.abHouseStatusDao")
    private AbHouseStatusDao abHouseStatusDao;

    @Test
    public void testSaveEntity(){
        AbHouseStatusEntity abHouseStatusEntity = new AbHouseStatusEntity();
        abHouseStatusEntity.setAbSn("sdfsfsdf");
        abHouseStatusEntity.setEmail("sdfs@qq.com");
        abHouseStatusEntity.setLockTime(new Date());
        abHouseStatusEntity.setSummary("dsfsf");
        abHouseStatusEntity.setUid("sdfsfsd");
        abHouseStatusEntity.setSummaryStatus(1);
        abHouseStatusDao.save(abHouseStatusEntity);
    }

    @Test
    public void testdeleteByLockTime(){
        AbHouseDto abHouseDto = new AbHouseDto();
        abHouseDto.setAbSn("sdfsfsdf");
        abHouseDto.setStartDate("2017-04-14 14:46:47");
        abHouseStatusDao.deleteByLockTime(abHouseDto);
    }

}
