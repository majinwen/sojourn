package com.ziroom.minsu.services.house.dao;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseBizMsgEntity;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * 
 * <p>房源业务信息dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改时间	修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@Repository("house.houseBizMsgDao")
public class HouseBizMsgDao {


    private String SQLID="house.houseBizMsgDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 
     * 新增房源业务信息
     *
     * @author liujun
     * @created 2017年2月27日
     *
     * @param houseBizMsg
     * @return
     */
	public int insertHouseBizMsg(HouseBizMsgEntity houseBizMsg) {
		if (Check.NuNObj(houseBizMsg) || Check.NuNObj(houseBizMsg.getRentWay())) {
			return 0;
		}

		if (houseBizMsg.getRentWay().intValue() == RentWayEnum.HOUSE.getCode()) {
			//确保houseBaseFid roomFid bedFid rentWay唯一性
			houseBizMsg.setRoomFid("");
			houseBizMsg.setBedFid("");
		} else if (houseBizMsg.getRentWay().intValue() == RentWayEnum.ROOM.getCode()) {
			if (Check.NuNStr(houseBizMsg.getRoomFid())) {
				throw new BusinessException("房间fid不能为空");
			}
			//确保houseBaseFid roomFid bedFid rentWay唯一性
			houseBizMsg.setBedFid("");
		} else if (houseBizMsg.getRentWay().intValue() == RentWayEnum.BED.getCode() && Check.NuNStr(houseBizMsg.getBedFid())) {
			throw new BusinessException("床位fid不能为空");
		}
		
		if (Check.NuNStr(houseBizMsg.getFid())) {
			houseBizMsg.setFid(UUIDGenerator.hexUUID());
		}
		return mybatisDaoContext.save(SQLID + "insertHouseBizMsg", houseBizMsg);
	}
	
	/**
	 * 
	 * 查询房源业务信息
	 *
	 * @author liujun
	 * @created 2017年2月27日
	 *
	 * @param houseBaseMsg
	 * @return
	 */
	public HouseBizMsgEntity getHouseBizMsgByHouseFid(String houseBaseFid) {
		return mybatisDaoContext.findOne(SQLID + "getHouseBizMsgByHouseFid", HouseBizMsgEntity.class, houseBaseFid);
	}
	
	/**
	 * 
	 * 查询房间业务信息
	 *
	 * @author liujun
	 * @created 2017年2月27日
	 *
	 * @param roomFid
	 * @return
	 */
	public HouseBizMsgEntity getHouseBizMsgByRoomFid(String roomFid) {
		return mybatisDaoContext.findOne(SQLID + "getHouseBizMsgByRoomFid", HouseBizMsgEntity.class, roomFid);
	}

    /**
     * 
     * 更新房源业务信息
     *
     * @author liujun
     * @created 2017年2月27日
     *
     * @param houseBaseMsg
     * @return
     */
    public int updateHouseBizMsgByHouseFid(HouseBizMsgEntity houseBizMsg) {
    	return mybatisDaoContext.update(SQLID + "updateHouseBizMsgByHouseFid", houseBizMsg);
    }
    
    /**
     * 
     * 更新房间业务信息
     *
     * @author liujun
     * @created 2017年2月27日
     *
     * @param houseBaseMsg
     * @return
     */
    public int updateHouseBizMsgByRoomFid(HouseBizMsgEntity houseBizMsg) {
    	return mybatisDaoContext.update(SQLID + "updateHouseBizMsgByRoomFid", houseBizMsg);
    }
    /**
     * 
     * 查询此房源在biz表中的记录数
     *
     * @author bushujie
     * @created 2017年6月30日 上午10:22:48
     *
     * @param houseBaseFid
     * @return
     */
    public int getHouseBizNumByHouseBaseFid(String houseBaseFid){
    	Map<String, Object> paramMap=new HashMap<String, Object>();
    	paramMap.put("houseBaseFid", houseBaseFid);
    	return mybatisDaoContext.findOne(SQLID+"getHouseBizNumByHouseBaseFid", Integer.class, paramMap);
    }
}
