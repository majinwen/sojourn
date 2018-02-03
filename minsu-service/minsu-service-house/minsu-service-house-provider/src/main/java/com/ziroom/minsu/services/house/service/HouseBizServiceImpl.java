package com.ziroom.minsu.services.house.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.house.HouseBizMsgEntity;
import com.ziroom.minsu.services.house.dao.HouseBizMsgDao;
import com.ziroom.minsu.services.house.dao.HouseOperateLogDao;

/**
 * 
 * <p>房源业务service</p>
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
@Service("house.houseBizServiceImpl")
public class HouseBizServiceImpl {
	
	@Resource(name="house.houseBizMsgDao")
	private HouseBizMsgDao houseBizMsgDao;
	
	@Resource(name="house.houseOperateLogDao")
	private HouseOperateLogDao houseOperateLogDao;
	
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
		return houseBizMsgDao.insertHouseBizMsg(houseBizMsg);
	}
	
	/**
	 * 
	 * 查询房源业务信息
	 *
	 * @author liujun
	 * @created 2017年2月27日
	 *
	 * @param houseBaseFid 
	 * @return
	 */
	public HouseBizMsgEntity getHouseBizMsgByHouseFid(String houseBaseFid) {
		if (Check.NuNStrStrict(houseBaseFid)) {
			return null;
		}
		return houseBizMsgDao.getHouseBizMsgByHouseFid(houseBaseFid);
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
		if (Check.NuNStrStrict(roomFid)) {
			return null;
		}
		return houseBizMsgDao.getHouseBizMsgByRoomFid(roomFid);
	}
	
	/**
	 * 
	 * 更新房源业务信息-整套出租
	 *
	 * @author liujun
	 * @created 2017年2月27日
	 *
	 * @param roomFid 
	 * @return
	 */
	public int updateHouseBizMsgByHouseFid(HouseBizMsgEntity houseBizMsg) {
		return houseBizMsgDao.updateHouseBizMsgByHouseFid(houseBizMsg);
	}
	
	/**
	 * 
	 * 更新房间业务信息-独立房间
	 *
	 * @author liujun
	 * @created 2017年2月27日
	 *
	 * @param roomFid 
	 * @return
	 */
	public int updateHouseBizMsgByRoomFid(HouseBizMsgEntity houseBizMsg) {
		return houseBizMsgDao.updateHouseBizMsgByRoomFid(houseBizMsg);
	}
	
	/**
	 * 
	 * 查询此房源在biz表中的记录数
	 *
	 * @author bushujie
	 * @created 2017年6月30日 下午4:17:02
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public int getHouseBizNumByHouseBaseFid(String houseBaseFid){
		return houseBizMsgDao.getHouseBizNumByHouseBaseFid(houseBaseFid);
	}
	
	/**
	 * 
	 * 查询到某一个状态的次数
	 *
	 * @author bushujie
	 * @created 2017年7月12日 下午12:01:20
	 *
	 * @param houseBaseFid
	 * @param toStatus
	 * @return
	 */
	public int findToStatusNum(String houseBaseFid,Integer toStatus){
		return houseOperateLogDao.findToStatusNum(houseBaseFid, toStatus);
	}
}
