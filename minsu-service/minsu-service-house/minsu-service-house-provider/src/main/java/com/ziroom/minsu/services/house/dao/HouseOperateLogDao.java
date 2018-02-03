package com.ziroom.minsu.services.house.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.house.HouseOperateLogEntity;
import com.ziroom.minsu.services.house.dto.HouseOpLogDto;
import com.ziroom.minsu.services.house.dto.HouseOpLogSpDto;

/**
 * 
 * <p>房源状态操作日志</p>
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
@Repository("house.houseOperateLogDao")
public class HouseOperateLogDao {


    private String SQLID="house.houseOperateLogDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 
     * 新增房源日志
     *
     * @author bushujie
     * @created 2016年4月9日 下午10:04:53
     *
     * @param houseOperateLog
     */
    public void insertHouseOperateLog(HouseOperateLogEntity houseOperateLog) {
		mybatisDaoContext.save(SQLID+"insertHouseOperateLog", houseOperateLog);
	}
    
    
    /**
     * 
     * 查询房源操作日志
     *
     * @author bushujie
     * @created 2016年4月9日 下午10:05:47
     *
     * @param houseBaseFid
     * @param fromList
     * @return
     */
	public PagingResult<HouseOperateLogEntity> findHouseOperateLogList(HouseOpLogDto houseOpLogDto) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseOpLogDto.getPage());
		pageBounds.setLimit(houseOpLogDto.getLimit());
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("houseBaseFid", houseOpLogDto.getHouseFid());
		paramMap.put("fromList", houseOpLogDto.getFromList());
		return mybatisDaoContext.findForPage(SQLID + "findHouseOperateLogList", HouseOperateLogEntity.class, 
				paramMap, pageBounds);
	}
	
	/**
	 * 
	 * 查询房间操作日志
	 *
	 * @author bushujie
	 * @created 2016年4月9日 下午10:06:13
	 *
	 * @param houseRoomFid
	 * @param fromList
	 * @return
	 */
	public PagingResult<HouseOperateLogEntity> findRoomOperateLogList(HouseOpLogDto houseOpLogDto) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseOpLogDto.getPage());
		pageBounds.setLimit(houseOpLogDto.getLimit());
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("houseRoomFid", houseOpLogDto.getHouseFid());
		paramMap.put("fromList", houseOpLogDto.getFromList());
		return mybatisDaoContext.findForPage(SQLID+"findRoomOperateLogList", HouseOperateLogEntity.class, 
				paramMap, pageBounds);
	}
	
	/**
	 * 
	 * 查询房源日志
	 *
	 * @author jixd
	 * @created 2016年6月15日 下午9:51:16
	 *
	 * @param houseLogSpDto
	 * @return
	 */
	public PagingResult<HouseOperateLogEntity> findOperateLogList(HouseOpLogSpDto houseLogSpDto){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseLogSpDto.getPage());
		pageBounds.setLimit(houseLogSpDto.getLimit());
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("houseFid", houseLogSpDto.getHouseFid());
		paramMap.put("roomFid", houseLogSpDto.getRoomFid());
		paramMap.put("toStatus", houseLogSpDto.getToStatus());
		paramMap.put("operateType", houseLogSpDto.getOperateType());
		return mybatisDaoContext.findForPage(SQLID + "findOperateLogList", HouseOperateLogEntity.class,paramMap, pageBounds);
	}
    
	/**
	 * 
	 * 查询审核未通过次数
	 *
	 * @author baiwei
	 * @created 2017年4月13日 下午8:09:46
	 *
	 * @param houseOperateLogEntity
	 * @return
	 */
	public int findHouseAuditNoLogTime(HouseOperateLogEntity houseOperateLogEntity){
		return mybatisDaoContext.findOne(SQLID+"findHouseAuditNoLogTime",Integer.class, houseOperateLogEntity);
	}
	
	/**
	 * 
	 * 查询首次一个状态到另一状态的时间（fromStatus,toStatus,houseFid,roomFid）
	 *
	 * @author bushujie
	 * @created 2017年6月22日 下午2:39:22
	 *
	 * @param paramMap
	 * @return
	 */
	public HouseOperateLogEntity findFirstChangeStauts(Map<String, Object> paramMap){
		return mybatisDaoContext.findOne(SQLID+"findFirstChangeStauts", HouseOperateLogEntity.class, paramMap);
	}
	
	/**
	 * 
	 * 查询到某一个状态的次数
	 *
	 * @author bushujie
	 * @created 2017年7月12日 上午11:41:05
	 *
	 * @param houseBaseFid
	 * @param toStatus
	 * @return
	 */
	public int findToStatusNum(String houseBaseFid,Integer toStatus){
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("houseBaseFid", houseBaseFid);
		paramMap.put("toStatus", toStatus);
		return mybatisDaoContext.findOne(SQLID+"findToStatusNum", Integer.class,paramMap);
	}
	
	/**
	 * 查询最后一个品质审核未通过原因
	 * @param paramMap
	 * @return
	 */
	public HouseOperateLogEntity findLastHouseLog(Map<String, Object> paramMap){
		return mybatisDaoContext.findOne(SQLID+"findLastHouseLog", HouseOperateLogEntity.class, paramMap);
	}
}