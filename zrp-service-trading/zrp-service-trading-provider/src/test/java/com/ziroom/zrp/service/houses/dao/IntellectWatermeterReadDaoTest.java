/**
 * @FileName: IntellectWatermeterReadDaoTest.java
 * @Package com.ziroom.zrp.service.houses.dao
 * 
 * @author bushujie
 * @created 2018年1月12日 下午6:02:52
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.zrp.service.houses.dao;

import javax.annotation.Resource;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.zrp.service.trading.dao.IntellectWatermeterReadDao;
import com.ziroom.zrp.service.trading.dto.waterwatt.IntellectWatermeterReadDto;
import com.ziroom.zrp.service.trading.entity.IntellectWatermeterReadVo;
import com.ziroom.zrp.trading.entity.IntellectWatermeterReadEntity;
import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.zrp.service.houses.base.BaseTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class IntellectWatermeterReadDaoTest extends BaseTest{
	
	@Resource(name="trading.IntellectWatermeterReadDao")
	private IntellectWatermeterReadDao intellectWatermeterReadDao;
	
	
	@Test
	public void insertIntellectWatermeterRead(){
		IntellectWatermeterReadEntity intellectWatermeterReadEntity=new IntellectWatermeterReadEntity();
		intellectWatermeterReadEntity.setFid(UUIDGenerator.hexUUID());
		intellectWatermeterReadEntity.setRoomId(UUIDGenerator.hexUUID());
		intellectWatermeterReadEntity.setProjectId(UUIDGenerator.hexUUID());
		intellectWatermeterReadEntity.setMeterReading(789d);
		intellectWatermeterReadEntity.setReadStatus(0);
		intellectWatermeterReadEntity.setReadType(0);
		intellectWatermeterReadDao.insertIntellectWatermeterRead(intellectWatermeterReadEntity);
	}
	
	@Test
	public void IntellectWatermeterReadEntity(){
		IntellectWatermeterReadEntity intellectWatermeterReadEntity=intellectWatermeterReadDao.findIntellectWatermeterReadByFid("8a9e9ad260e9d7fe0160e9d7fedc0000");
		System.err.println(JsonEntityTransform.Object2Json(intellectWatermeterReadEntity));
	}
	
	@Test
	public void updateIntellectWatermeterRead(){
		IntellectWatermeterReadEntity intellectWatermeterReadEntity=intellectWatermeterReadDao.findIntellectWatermeterReadByFid("8a9e9ad260e9d7fe0160e9d7fedc0000");
		intellectWatermeterReadEntity.setMeterReading(900d);
		int upNum=intellectWatermeterReadDao.updateIntellectWatermeterRead(intellectWatermeterReadEntity);
		System.err.println(upNum);
	}

    @Test
    public void getNewestWatermeterRead(){
        IntellectWatermeterReadEntity entity = intellectWatermeterReadDao.getNewestWatermeterRead("8a9e9ad260e9d7fe0160e9d7fedc0002", "8a9e9ad260e9d7fe0160e9d7fedc0001");
        System.err.println(entity);
    }

    @Test
    public void getIntellectWatermeterReadByPage(){
        IntellectWatermeterReadDto intellectWatermeterReadDto = new IntellectWatermeterReadDto();
        intellectWatermeterReadDto.setPage(1);
        intellectWatermeterReadDto.setRows(20);
        List<String> projectIds = new ArrayList<>();
        projectIds.add("8a9099cb576ba5c101576ea29c8a0027");

        intellectWatermeterReadDto.setProjectIds(projectIds);
        PagingResult<IntellectWatermeterReadVo> list = intellectWatermeterReadDao.getIntellectWatermeterReadByPage(intellectWatermeterReadDto);
        System.err.println(list.getRows());
        System.err.println(list.getTotal());
    }
}
