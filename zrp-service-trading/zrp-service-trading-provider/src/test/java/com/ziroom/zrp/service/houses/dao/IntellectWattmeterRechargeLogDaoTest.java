/**
 * @FileName: IntellectWattmeterRechargeLogDaoTest.java
 * @Package com.ziroom.zrp.service.houses.dao
 * @author bushujie
 * @created 2018年1月12日 下午7:18:14
 * <p>
 * Copyright 2011-2015 asura
 */
package com.ziroom.zrp.service.houses.dao;

import javax.annotation.Resource;

import com.ziroom.zrp.service.trading.dao.IntellectWattMeterSnapshotDao;
import com.ziroom.zrp.trading.entity.IntellectWattMeterSnapshotEntity;
import com.zra.common.dto.base.BasePageParamDto;
import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.zrp.service.houses.base.BaseTest;

/**
 * <p>TODO</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class IntellectWattmeterRechargeLogDaoTest extends BaseTest {

    @Resource(name = "trading.intellectWattMeterSnapshotDao")
    private IntellectWattMeterSnapshotDao intellectWattMeterSnapshotDao;

    @Test
    public void insertIntellectWattmeterRechargeLog() {
        IntellectWattMeterSnapshotEntity intellectWattMeterSnapshotEntity = new IntellectWattMeterSnapshotEntity();
        intellectWattMeterSnapshotEntity.setBillFid(UUIDGenerator.hexUUID());
        intellectWattMeterSnapshotEntity.setCreateId(UUIDGenerator.hexUUID());
        intellectWattMeterSnapshotEntity.setCreateType(0);
        intellectWattMeterSnapshotEntity.setEndReading(258d);
        intellectWattMeterSnapshotEntity.setFid(UUIDGenerator.hexUUID());

        intellectWattMeterSnapshotEntity.setProjectId(UUIDGenerator.hexUUID());
        intellectWattMeterSnapshotEntity.setRoomId(UUIDGenerator.hexUUID());
        intellectWattMeterSnapshotEntity.setStartReading(200d);
        intellectWattMeterSnapshotEntity.setStatu(0);
        intellectWattMeterSnapshotDao.insertIntellectWattMeterSnapshot(intellectWattMeterSnapshotEntity);
    }

    @Test
    public void findIntellectWattmeterRechargeLog() {
        IntellectWattMeterSnapshotEntity intellectWattMeterSnapshotEntity = intellectWattMeterSnapshotDao.findIntellectWattMeterSnapshot("8a9e9ad260ea25570160ea2557750002");
        System.err.println(intellectWattMeterSnapshotEntity);
    }

    @Test
    public void updateIntellectWattmeterRechargeLog() {
        IntellectWattMeterSnapshotEntity intellectWattMeterSnapshotEntity = intellectWattMeterSnapshotDao.findIntellectWattMeterSnapshot("8a9e9ad260ea25570160ea2557750002");
        intellectWattMeterSnapshotEntity.setBillFid("ddddddddddddddd");
        intellectWattMeterSnapshotDao.updateIntellectWattMeterSnapshot(intellectWattMeterSnapshotEntity);
    }

    @Test
    public void testlistRetryWattMeterPage(){
        BasePageParamDto basePageParamDto = new BasePageParamDto();
        basePageParamDto.setPage(1);
        basePageParamDto.setRows(5);
        intellectWattMeterSnapshotDao.listRetryWattMeterPage(basePageParamDto);
    }

}
