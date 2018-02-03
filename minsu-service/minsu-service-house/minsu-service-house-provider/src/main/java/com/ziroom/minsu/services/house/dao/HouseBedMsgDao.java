package com.ziroom.minsu.services.house.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseBedMsgEntity;
import com.ziroom.minsu.services.house.entity.HouseBedNumVo;
import com.ziroom.minsu.services.house.entity.OrderNeedHouseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <p>房源床位信息dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@Repository("house.houseBedMsgDao")
public class HouseBedMsgDao {


    private String SQLID="house.houseBedMsgDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 新增房源床位信息
     *
     * @author liujun
     * @created 2016年4月1日 下午3:56:44
     *
     * @param houseBedMsg
     * @return
     */
    public void insertHouseBedMsg(HouseBedMsgEntity houseBedMsg) {
		mybatisDaoContext.save(SQLID+"insertHouseBedMsg", houseBedMsg);
	}
    
    /**
     * 
     * 更新房源床位信息
     *
     * @author liujun
     * @created 2016年4月1日 下午3:56:44
     *
     * @param houseBedMsg
     * @return
     */
    public int updateHouseBedMsg(HouseBedMsgEntity houseBedMsg) {
    	return mybatisDaoContext.update(SQLID+"updateHouseBedMsgByFid", houseBedMsg);
    }

	/**
	 * 根据房源床位逻辑id查询详情信息
	 *
	 * @author liujun
	 * @created 2016年4月2日 下午9:19:56
	 *
	 * @param houseBedFid
	 * @return
	 */
	public HouseBedMsgEntity findHouseBedMsgByFid(String houseBedFid) {
		return mybatisDaoContext.findOne(SQLID + "findHouseBedMsgByFid", HouseBedMsgEntity.class, houseBedFid);
	}

	/**
	 * 根据房源房间逻辑id查询床位集合
	 *
	 * @author liujun
	 * @created 2016年4月2日 下午10:09:06
	 *
	 * @param houseRoomFid
	 * @return
	 */
	public List<HouseBedMsgEntity> findBedListByRoomFid(String houseRoomFid) {
		return mybatisDaoContext.findAll(SQLID + "findBedListByRoomFid", HouseBedMsgEntity.class, houseRoomFid);
	}

    /**
     * 通过roomFid 获取当前的床数量
     * @author afi
     * @param houseRoomFid
     * @return
     */
    public Long countBedByRoomFid(String houseRoomFid){
        Map<String,Object> par = new HashMap<>();
        par.put("room_fid",houseRoomFid);
        return mybatisDaoContext.count(SQLID + "countBedByRoomFid", par);
    }

	/**
	 * 根据房源床位逻辑id逻辑删除床位信息
	 *
	 * @author liujun
	 * @created 2016年4月5日 下午6:33:06
	 *
	 * @param houseBedFid
	 */
	public int deleteHouseBedMsgByFid(String houseBedFid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fid", houseBedFid);
		return mybatisDaoContext.update(SQLID + "deleteHouseBedMsgByFid", map);
	}
	/**
	 * 
	 * 根据房间fid删除床位信息
	 *
	 * @author jixd
	 * @created 2016年6月13日 下午5:22:20
	 *
	 * @param roomFid
	 * @return
	 */
	public int deleteHouseBedMsgByRoomFid(String roomFid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fid", roomFid);
		return mybatisDaoContext.update(SQLID + "deleteHouseBedMsgByRoomFid", map);
	}
	
	/**
	 * 
	 * 根据房源fid删除床位信息
	 *
	 * @author jixd
	 * @created 2016年6月13日 下午5:22:03
	 *
	 * @param houseFid
	 * @return
	 */
	public int deleteHouseBedMsgByHouseFid(String houseFid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fid", houseFid);
		return mybatisDaoContext.update(SQLID + "deleteHouseBedMsgByHouseFid", map);
	}
	
	/**
	 * 根据房源房间逻辑id查询房间下床位数量
	 *
	 * @author liujun
	 * @created 2016年4月5日 下午9:59:05
	 *
	 * @param roomFid
	 * @return
	 */
	public int getRoomBedCount(String roomFid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("room_fid", roomFid);
		return (int) mybatisDaoContext.count(SQLID + "getRoomBedCount", map);
	}
	
	/**
	 * 
	 * 根据床位查询订单需要房源信息
	 *
	 * @author bushujie
	 * @created 2016年4月6日 下午6:03:15
	 *
	 * @param bedFid
	 * @return
	 */
	public OrderNeedHouseVo getOrderNeedHouseVoByBedFid(String bedFid){
		return mybatisDaoContext.findOne(SQLID+"getOrderNeedHouseVoByBedFid", OrderNeedHouseVo.class, bedFid);
	}
	/**
	 * 
	 * 查询房间最大编号
	 *
	 * @author bushujie
	 * @created 2016年4月9日 上午11:00:39
	 *
	 * @param roomFid
	 * @return
	 */
	public Integer getMaxBedSnByRoomFid(String roomFid){
		return mybatisDaoContext.findOne(SQLID+"getBedMaxSnByRoomFid", Integer.class, roomFid);
	}
	
	/**
	 * 
	 * 查询房间或者房源床信息列表
	 *
	 * @author bushujie
	 * @created 2016年5月5日 下午10:03:40
	 *
	 * @param paramMap
	 * @return
	 */
	public List<HouseBedNumVo> getBedNumByHouseFid(Map<String, Object> paramMap){
		return mybatisDaoContext.findAll(SQLID+"getBedNumByHouseFid", HouseBedNumVo.class, paramMap);
	}
	
	/**
	 * 
	 * 根据房源fid或房间fid查询床位数量
	 *
	 * @author yd
	 * @created 2016年8月24日 下午2:03:55
	 *
	 * @param paramMap
	 * @return
	 */
	public Long countBedNumByHouseFid(Map<String, Object> paramMap){
		return  mybatisDaoContext.count(SQLID+"countBedNumByHouseFid", paramMap);
	}
	
	/**
	 * 
	 * 查询一种类型床的数量
	 *
	 * @author bushujie
	 * @created 2017年6月28日 下午8:10:31
	 *
	 * @param paramMap
	 * @return
	 */
	public List<HouseBedMsgEntity> getBedNumByType(Map<String, Object> paramMap){
		return mybatisDaoContext.findAll(SQLID+"getBedNumByType", HouseBedMsgEntity.class, paramMap);
	}

}