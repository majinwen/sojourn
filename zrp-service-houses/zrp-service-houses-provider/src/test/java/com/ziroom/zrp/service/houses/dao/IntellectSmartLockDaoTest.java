package com.ziroom.zrp.service.houses.dao;

import com.alibaba.fastjson.JSON;
import com.asura.framework.base.paging.PagingResult;
import com.ziroom.zrp.houses.entity.IntellectSmartLockEntity;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.houses.dto.RoomSmartLockDto;
import com.ziroom.zrp.service.houses.entity.IntellectSmartLockVo;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.springframework.util.Assert;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2017年12月06日 20:28
 * @since 1.0
 */
@Slf4j
public class IntellectSmartLockDaoTest extends BaseTest {

    @Resource(name="houses.intellectSmartLockDao")
    private IntellectSmartLockDao intellectSmartLockDao;


    @Test
    public void saveRoomSmartLockTest(){

        IntellectSmartLockEntity roomSmartLock = new IntellectSmartLockEntity();

        roomSmartLock.setRoomId("1001497");
        roomSmartLock.setServiceId("FGJDSFJDSFJDS1564564564564");
        roomSmartLock.setPwd("123456");
        roomSmartLock.setPhone("18701482472");
        roomSmartLock.setHandlerCode("20223709");
        roomSmartLock.setHandlerName("杨东");
        roomSmartLock.setStatus(1);
        roomSmartLock.setStartTime( new Date());
        roomSmartLock.setEndTime(new Date(roomSmartLock.getStartTime().getTime()+24*60*60*1000));
        roomSmartLock.setCreateTime(roomSmartLock.getStartTime());
        roomSmartLock.setPwdType(2);
        roomSmartLock.setProjectId("20");
        roomSmartLock.setUserType(1);
        roomSmartLock.setSourceType(0);
        roomSmartLock.setUserCode("20223709");
        roomSmartLock.setHandlerType(0);
        int i = intellectSmartLockDao.saveRoomSmartLock(roomSmartLock);
        System.out.println(i);
    }

    @Test
    public  void getRoomSmartLockByFidTest(){

        IntellectSmartLockEntity roomSmartLock = intellectSmartLockDao.getRoomSmartLockByFid("8a9e989c602bd35d01602bd35db90000");

        System.out.println(roomSmartLock);
    }

    @Test
    public void pagingTest() {

        RoomSmartLockDto roomStmartDto = RoomSmartLockDto.builder().build();

        PagingResult<IntellectSmartLockVo> result = intellectSmartLockDao.findByPaging(roomStmartDto);

        Assert.notNull(result, "result is null");

        Assert.isTrue(result.getTotal() >= 0, "paging test fail");

        result.getRows().stream().forEach(intellectSmartLockVo -> {
            log.info(JSON.toJSON(intellectSmartLockVo).toString());
        });
    }
    
    @Test
    public void upSmartLockStatusByServiceIdLimitTest(){
    	Map<String, Object> pMap=new HashMap<String, Object>();
    	pMap.put("serviceId", "8a9e989c607c0d5001607c0d508d0007");
    	pMap.put("status", 2);//失效状态
    	intellectSmartLockDao.upSmartLockStatusByServiceIdLimit(pMap);
    }
}
