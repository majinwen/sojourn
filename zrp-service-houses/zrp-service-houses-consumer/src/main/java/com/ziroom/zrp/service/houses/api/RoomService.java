package com.ziroom.zrp.service.houses.api;


/**
 * <p>房间接口</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @Date Created in 2017年09月13日 17:30
 * @version 1.0
 * @since 1.0
 */
public interface RoomService {
	/**
	 * 根据房间标识查询房间信息
	 * @author cuigh6 created on 2017年9月13号
	 * @param fid 房间标识
	 * @return
	 */
	String getRoomByFid(String fid);

	/**
	 * 查询多个房间信息
	 * @author cuiyuhui
	 * @param
	 * @return
	 */
	String getRoomListByRoomIds(String roomIds);

	/**
	 * @description: 提交合同时保存出房记录
	 * @author: lusp
	 * @date: 2017/9/13 20:25
	 * @params: paramJson
	 * @return: String
	 */
	String saveRoomRentInfo(String paramJson);

	/**
	 * @description: 提交合同时锁定房间以及保存房间记录
	 * @author: lusp
	 * @date: 2017/9/13 20:22
	 * @params: paramJson
	 * @return: String
	 */
	String updateRoomInfoAndSaveRentInfo(String paramJson);

	/**
	 * @description: 释放房间以及逻辑删除房间出租记录
	 * @author: lusp
	 * @date: 2017/10/11 11:22
	 * @params: paramJson
	 * @return: String
	 */
	String updateRoomInfoAndDeleteRentInfo(String roomId);
	
	/**
	 * <p>根据房间ID查询房间信息，房型信息，项目信息</p>
	 * @author xiangb
	 * @created 2017年9月24日
	 * @param
	 * @return
	 */
	String getRentRoomInfoByRoomId(String roomId);

	/**
	 * @description: 分页查询房间列表
	 * @author: lusp
	 * @date: 2017/10/19 下午 20:13
	 * @params: paramJson
	 * @return: String
	 */
	String findRoomListForPage(String paramJson);

	/**
	 * 更新房间信息
	 * @param paramJson 房间对象
	 * @return
	 * @author cuigh6
	 * @Date 2017年10月
	 */
	String updateRoomInfo(String paramJson);

	/**
	 * 批量释放房间
	 *
	 * @Author: wangxm113
	 * @Date: 2017年10月24日 15时17分15秒
	 */
    String updateRoomInfoForRelease(String paramStr);
    
    /**
     * 批量回滚释放的房间
     * @author tianxf9
     * @param paramStr
     * @return
     */
    String updateRoomStateRent(String paramStr);

    /**
     * 将房间状态由签约进行中改为已出租
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    String updateRoomStateFromSigningToRental(String paramStr);

    /**
     * 获取密码列表
     * @author yd
     * @created
     * @param
     * @return
     */
    String findRoomContractSmartByPage(String paramStr);
    
    /**
     * 
     * roomId查询房间扩展信息
     *
     * @author bushujie
     * @created 2018年1月18日 下午3:29:02
     *
     * @param roomId
     * @return
     */
    String getRoomInfoExtByRoomId(String roomId);

    /**
     *
     * 项目下所有房间
     *
     * @author zhangyl2
     * @created 2018年02月07日 14:50
     * @param
     * @return
     */
    String findAllRoom(String projectid);

	/**
	 * 查询房间信息 通过父房间标识
	 * @param roomId 房间标识
	 * @return
	 * @author cuigh6
	 * @Date 2018年2月7日
	 */
	String getRoomInfoByParentId(String roomId);

	/**
	 * 查询绑定水表的所有房间
	 * @return
	 * @author cuigh6
	 * @Date 2018年2月28日
	 */
	String getAllRoomOfBindingWaterMeter();
}
