/**
 * @FileName: IntellectWatermeterReadServiceImpl.java
 * @Package com.ziroom.zrp.service.trading.service
 * 
 * @author bushujie
 * @created 2018年1月19日 下午4:47:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.zrp.service.trading.service;

import javax.annotation.Resource;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.zrp.service.trading.dto.waterwatt.IntellectWatermeterReadDto;
import com.ziroom.zrp.service.trading.entity.IntellectWatermeterReadVo;
import org.springframework.stereotype.Service;

import com.ziroom.zrp.service.trading.dao.IntellectWatermeterReadDao;
import com.ziroom.zrp.trading.entity.IntellectWatermeterReadEntity;

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
@Service("trading.intellectWatermeterReadServiceImpl")
public class IntellectWaterMeterReadServiceImpl {
	
	@Resource(name="trading.IntellectWatermeterReadDao")
	private IntellectWatermeterReadDao intellectWatermeterReadDao;


    /**
     *
     * 插入水表抄表记录
     *
     * @author zhangyl2
     * @created 2018年02月24日 10:52
     * @param
     * @return
     */
    public void insertIntellectWatermeterRead(IntellectWatermeterReadEntity intellectWatermeterReadEntity){
        intellectWatermeterReadDao.insertIntellectWatermeterRead(intellectWatermeterReadEntity);
    }
	
	/**
	 * 
	 * 查询房间最后一条定时抄水表记录
	 *
	 * @author bushujie
	 * @created 2018年1月19日 下午4:52:40
	 *
	 * @param intellectWatermeterReadEntity
	 * @return
	 */
	public  IntellectWatermeterReadEntity getLastIntellectWatermeterReadByRoomId(IntellectWatermeterReadEntity intellectWatermeterReadEntity){
		return intellectWatermeterReadDao.getLastIntellectWatermeterReadByRoomId(intellectWatermeterReadEntity);
	}

    /**
     *
     * 查询最新抄表记录
     *
     * @author zhangyl2
     * @created 2018年02月01日 18:33
     * @param
     * @return
     */
    public IntellectWatermeterReadEntity getNewestWatermeterRead(String projectId, String roomId){
        return intellectWatermeterReadDao.getNewestWatermeterRead(projectId, roomId);
    }

    /**
     *
     * 分页查询定时任务的水表抄表记录
     *
     * @author zhangyl2
     * @created 2018年02月24日 15:37
     * @param
     * @return
     */
    public PagingResult<IntellectWatermeterReadVo> getIntellectWatermeterReadByPage(IntellectWatermeterReadDto intellectWatermeterReadDto){
        return intellectWatermeterReadDao.getIntellectWatermeterReadByPage(intellectWatermeterReadDto);
    }

    /**
     *
     * 更新水表抄表记录
     *
     * @author bushujie
     * @created 2018年1月12日 下午4:32:04
     *
     * @param intellectWatermeterReadEntity
     * @return
     */
    public int updateIntellectWatermeterRead(IntellectWatermeterReadEntity intellectWatermeterReadEntity){
        return intellectWatermeterReadDao.updateIntellectWatermeterRead(intellectWatermeterReadEntity);
    }
	
}
