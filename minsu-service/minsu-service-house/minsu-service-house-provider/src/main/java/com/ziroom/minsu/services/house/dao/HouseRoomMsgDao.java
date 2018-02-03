package com.ziroom.minsu.services.house.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.services.house.dto.HouseBaseListDto;
import com.ziroom.minsu.services.house.dto.HouseDetailDto;
import com.ziroom.minsu.services.house.dto.HouseRoomMsgDto;
import com.ziroom.minsu.services.house.entity.*;
import com.ziroom.minsu.services.house.issue.vo.HouseHallVo;
import com.ziroom.minsu.valenum.house.HousePicTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * <p>房源房间信息Dao</p>
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
@Repository("house.houseRoomMsgDao")
public class HouseRoomMsgDao {


	private String SQLID="house.houseRoomMsgDao.";
	
	private static Logger logger = LoggerFactory.getLogger(HouseRoomMsgDao.class);

	@Autowired
	@Qualifier("house.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	@Resource(name = "house.housePhyMsgDao")
	private HousePhyMsgDao housePhyMsgDao;

	@Resource(name = "house.houseBaseMsgDao")
	private HouseBaseMsgDao houseBaseMsgDao;

	/**
	 * 
	 * 新增房源房间信息
	 *
	 * @author bushujie
	 * @created 2016年4月9日 上午11:47:45
	 *
	 * @param houseRoomMsg
	 */
	public int insertHouseRoomMsg(HouseRoomMsgEntity houseRoomMsg) {
		return mybatisDaoContext.save(SQLID+"insertHouseRoomMsg", houseRoomMsg);
	}

	/**
	 * 
	 * 更新房源房间信息
	 *
	 * @author bushujie
	 * @created 2016年4月9日 上午11:47:58
	 *
	 * @param houseRoomMsg
	 * @return
	 */
	public int updateHouseRoomMsg(HouseRoomMsgEntity houseRoomMsg) {
		return mybatisDaoContext.update(SQLID + "updateHouseRoomMsgByFid", houseRoomMsg);
	}

	/**
	 * 根据你房间编号集合查询房间信息集合
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/21 14:18
	 */
	public List<HouseRoomMsgEntity> getRoomBaseListByRoomSns(List<String> roomSns){
		return mybatisDaoContext.getWriteSqlSessionTemplate().selectList(SQLID+"getRoomBaseListByRoomSns",roomSns);
	}

	/**
	 * 
	 * 房东端房间列表查询
	 *
	 * @author bushujie
	 * @created 2016年4月9日 上午11:48:21
	 *
	 * @param paramDto
	 * @return
	 */
	public PagingResult<HouseRoomListVo> findHouseRoomList(HouseBaseListDto paramDto){
		PageBounds pageBounds=new PageBounds();
		pageBounds.setPage(paramDto.getPage());
		pageBounds.setLimit(paramDto.getLimit());
		return mybatisDaoContext.findForPage(SQLID + "findHouseRoomList", HouseRoomListVo.class, paramDto, pageBounds);
	}

	/**
	 *
	 * PC端获取获取房间列表
	 *
	 * @author jixd
	 * @created 2016年8月1日 下午4:10:10
	 *
	 * @param paramDto
	 * @return
	 */
	public PagingResult<HouseRoomVo> getRoomPCList(HouseBaseListDto paramDto){
		PageBounds pageBounds=new PageBounds();
		pageBounds.setPage(paramDto.getPage());
		pageBounds.setLimit(paramDto.getLimit());
		return mybatisDaoContext.findForPage(SQLID + "getRoomPCList", HouseRoomVo.class, paramDto, pageBounds);
	}
	
	/**
	 *
	 * PC端获取日历房间列表
	 *
	 * @author busj
	 * @created 2017年10月28日 下午4:10:10
	 *
	 * @param paramDto
	 * @return
	 */
	public PagingResult<HouseRoomVo> getCalendarRoomPCList(HouseBaseListDto paramDto){
		PageBounds pageBounds=new PageBounds();
		pageBounds.setPage(paramDto.getPage());
		pageBounds.setLimit(paramDto.getLimit());
		return mybatisDaoContext.findForPage(SQLID + "getCalendarRoomPCList", HouseRoomVo.class, paramDto, pageBounds);
	}

	/**
	 * 通过房源获取当前房源下的房间列表
	 * @author afi
	 * @param houseFid
	 * @return
	 */
	public List<HouseRoomListVo>  getRoomListByHouseFid(String houseFid){
		return mybatisDaoContext.findAll(SQLID + "getRoomListByHouseFid", HouseRoomListVo.class, houseFid);
	}


	/**
	 * 
	 * 查询房间详细信息
	 *
	 * @author bushujie
	 * @created 2016年4月9日 上午11:48:49
	 *
	 * @param fid
	 * @return
	 */
	public HouseRoomMsgEntity getHouseRoomByFid(String fid){
		return mybatisDaoContext.findOne(SQLID + "getHouseRoomByFid", HouseRoomMsgEntity.class, fid);
	}

	/**
	 * 
	 * 
	 *
	 * @author bushujie
	 * @created 2016年4月9日 上午11:51:07
	 *
	 * @param houseRoomFid
	 * @return
	 */
	public HouseRoomMsgEntity findHouseRoomMsgByFid(String houseRoomFid) {
		return mybatisDaoContext.findOne(SQLID + "findHouseRoomMsgByFid",
				HouseRoomMsgDto.class, houseRoomFid);
	}

	/**
	 * 根据房间编号查找房间基本信息
	 * @author jixd
	 * @created 2016年11月21日 10:37:13
	 * @param
	 * @return
	 */
	public HouseRoomMsgEntity findHouseRoomMsgByRoomSn(String roomSn){
		return mybatisDaoContext.findOne(SQLID + "findHouseRoomMsgByRoomSn",
				HouseRoomMsgDto.class, roomSn);
	}

	/**
	 * 
	 * 根据房源逻辑id查询房间集合
	 *
	 * @author bushujie
	 * @created 2016年4月9日 上午11:51:44
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public List<HouseRoomMsgEntity> findRoomListByHouseBaseFid(String houseBaseFid) {
		return mybatisDaoContext.findAll(SQLID + "findRoomListByHouseBaseFid", HouseRoomMsgEntity.class, houseBaseFid);
	}

	/**
	 * 
	 * 根据房源房间逻辑id逻辑删除房间信息
	 *
	 * @author bushujie
	 * @created 2016年4月9日 上午11:52:06
	 *
	 * @param houseRoomFid
	 * @return
	 */
	public int deleteHouseRoomMsgByFid(String houseRoomFid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fid", houseRoomFid);
		return mybatisDaoContext.update(SQLID + "deleteHouseRoomMsgByFid", map);
	}
	/**
	 * 
	 * 根据房源fid删除房源下的所有房间
	 *
	 * @author jixd
	 * @created 2016年6月13日 下午5:24:39
	 *
	 * @param houseFid
	 * @return
	 */
	public int deleteHouseRoomMsgByHouseFid(String houseFid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fid", houseFid);
		return mybatisDaoContext.update(SQLID + "deleteHouseRoomMsgByHouseFid", map);
	}

	/**
	 * 将当前的房间设置为默认房间
	 * @author afi
	 * @param houseRoomFid
	 * @return
	 */
	public int defaultHouseRoomMsgByFid(String houseRoomFid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fid", houseRoomFid);
		return mybatisDaoContext.update(SQLID + "defaultHouseRoomMsgByFid", map);
	}

	/**
	 * 
	 * 房间id查询订单所需房源信息
	 *
	 * @author bushujie
	 * @created 2016年4月9日 上午11:52:19
	 *
	 * @param houseRoomFid
	 * @return
	 */
	public OrderNeedHouseVo getOrderNeedHouseVoByRoomFid(String houseRoomFid){
		return mybatisDaoContext.findOne(SQLID + "getOrderNeedHouseVoByRoomFid", OrderNeedHouseVo.class, houseRoomFid);
	}

	/**
	 * 
	 * 根据房间逻辑id查询房间详情
	 *
	 * @author bushujie
	 * @created 2016年4月9日 上午11:53:06
	 *
	 * @param houseRoomFid
	 * @return
	 */
	public HouseMsgVo findRoomDetailByFid(String houseRoomFid) {
		return mybatisDaoContext.findOne(SQLID + "findRoomDetailByFid", HouseMsgVo.class, houseRoomFid);
	}

	/**
	 * 
	 * 查询超时未审核房间fid
	 *
	 * @author bushujie
	 * @created 2016年4月9日 下午10:56:12
	 *
	 * @param paramMap
	 * @return
	 */
	public List<RoomLandlordVo> findOverAuditLimitRoom(Map<String, Object> paramMap){
		return mybatisDaoContext.findAll(SQLID + "findOverAuditLimitRoom", RoomLandlordVo.class, paramMap);
	}

	/**
	 * 根据房源基础信息逻辑id查询房间数量
	 *
	 * @author liujun
	 * @created 2016年4月9日 下午10:09:58
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public long getHouseRoomCount(String houseBaseFid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("house_base_fid", houseBaseFid);
		return mybatisDaoContext.count(SQLID + "getHouseRoomCount", map);
	}


	/**
	 * 获取当前房源下的默认房间数量
	 * @author afi
	 * @param houseBaseFid
	 * @return
	 */
	public long getHouseDefaultRoomCount(String houseBaseFid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("house_base_fid", houseBaseFid);
		return mybatisDaoContext.countBySlave(SQLID + "getHouseDefaultRoomCount", map);
	}

	/**
	 * 获取当前房间下的房源的默认房间数量
	 * @author afi
	 * @param roomFid
	 * @return
	 */
	public long getHouseDefaultRoomCountByRoomFid(String roomFid) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("room_fid", roomFid);
		return mybatisDaoContext.countBySlave(SQLID + "getHouseDefaultRoomCountByRoomFid", map);
	}

	/**
	 * 
	 * 查询审核房间图片列表
	 *
	 * @author bushujie
	 * @created 2016年4月13日 下午11:27:36
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public List<HousePicVo> findRoomPicVoList(String houseBaseFid,String roomFid){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("houseBaseFid", houseBaseFid);
		paramMap.put("roomFid", roomFid);
		List<HousePicVo> roomPicList = mybatisDaoContext.findAll(SQLID + "findRoomPicVo", HousePicVo.class, paramMap);
		for (HousePicVo housePicVo : roomPicList) {
			housePicVo.setPicType(HousePicTypeEnum.WS.getCode());
			housePicVo.setPicTypeName(HousePicTypeEnum.WS.getName());
			housePicVo.setPicMaxNum(HousePicTypeEnum.WS.getMax());
			housePicVo.setPicMinNum(HousePicTypeEnum.WS.getMin());
		}
		return roomPicList;
	}

	/**
	 * 获取房东房间(含房源默认房间)列表
	 *
	 * @author liujun
	 * @created 2016年4月19日 上午11:23:05
	 *
	 * @param houseBaseListDto
	 * @return
	 */
	public PagingResult<HouseRoomVo> findHouseRoomVoList(HouseBaseListDto houseBaseListDto) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseBaseListDto.getPage());
		pageBounds.setLimit(houseBaseListDto.getLimit());
		return mybatisDaoContext.findForPage(SQLID + "findHouseRoomVoList", HouseRoomVo.class, houseBaseListDto,
				pageBounds);
	}

	/**
	 * 
	 * 查询客端独立房间详情
	 *
	 * @author bushujie
	 * @created 2016年4月30日 下午11:10:28
	 *
	 * @param houseDetailDto
	 * @return
	 */
	public TenantHouseDetailVo getHouseRoomDetail(HouseDetailDto houseDetailDto){
		return mybatisDaoContext.findOneSlave(SQLID+"getHouseRoomDetail", TenantHouseDetailVo.class, houseDetailDto);
	}
	
	
	/**
	 * 
	 * 判断当前roomSn是否存在
	 * 返回数0，代表没有  大于0代表存在
	 *
	 * @author yd
	 * @created 2016年6月20日 下午8:44:27
	 *
	 * @param roomSn
	 * @return
	 */
	public Long countByRoomSn(String roomSn){
		
		if(Check.NuNStr(roomSn)){
			return 1L;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roomSn", roomSn);
    	return mybatisDaoContext.count(SQLID+"countByRoomSn", params);
	}
	/**
	 *
	 * 根据房源fid获取房间的数量
	 *
	 * @author jixd
	 * @created 2016年8月1日 下午6:18:41
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public Long countRoomNumByHouseFid(String houseBaseFid){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseBaseFid", houseBaseFid);
    	return mybatisDaoContext.count(SQLID+"countRoomNumByHouseFid", params);
	}
	/**
	 * 条件查询 返回数量
	 * @param houseRoomMsgDto
	 * @return
	 */
	public Long countByCondition(HouseRoomMsgEntity houseRoomMsg){
		Map<String, Object> params = houseRoomMsg.toMap();
		return mybatisDaoContext.count(SQLID+"countByCondition",params);
	}
	/**
	 * 
	 * 通过房源fid 查询当前房源下房间fid集合，按创建id正序
	 *
	 * @author yd
	 * @created 2016年8月20日 下午1:55:17
	 *
	 * @param houseFid
	 * @return
	 */
	public List<String>  getRooFidListByHouseFid(String houseFid){
		if(Check.NuNStr(houseFid)){
			LogUtil.error(logger, "房源编号为null，houseFid={}", houseFid);
			throw new BusinessException();
		}
		return mybatisDaoContext.findAllByMaster(SQLID+"getRooFidListByHouseFid", String.class, houseFid);
	}
	
	/**
	 * 条件查询 返回数量
	 * @param params
	 * @return
	 */
	public Long countByRoomInfo(Map<String, Object> params ){
		return mybatisDaoContext.count(SQLID+"countByRoomInfo",params);
	}
	
	/**
	 * 
	 * 查找分租最大房间价格 作为押金
	 *
	 * @author yd
	 * @created 2016年11月16日 下午6:29:52
	 *
	 * @param fid
	 * @return
	 */
	public HouseRoomMsgEntity findMaxRoomPriceByHouseFid(String houseBaseFid){
		return mybatisDaoContext.findOne(SQLID+"findMaxRoomPriceByHouseFid", HouseRoomMsgEntity.class, houseBaseFid);
	}

	/**
	 * @Description: 根据houseDefaultPicFid 查询roomFid 集合
	 * @Author: lusp
	 * @Date: 2017/7/17 16:59
	 * @Params: defaultPicFid
	 */
	public List<String>  getRoomFidListByDefaultPicFid(String defaultPicFid){
		return mybatisDaoContext.findAllByMaster(SQLID+"getRoomFidListByDefaultPicFid", String.class, defaultPicFid);
	}

	/**
	 * @Description: 根据图片fid 查询对应的roomFid
	 * @Author: lusp
	 * @Date: 2017/7/17 19:37
	 * @Params: PicFid
	 */
	public String  getRoomFidByPicFid(String picFid){
		return mybatisDaoContext.findOne(SQLID+"getRoomFidByPicFid", String.class, picFid);
	}

	/**
	 * 根据houseBaseFid 查询客厅/房间的fid和roomType
	 * 共享客厅专用，只能查room_type=1的记录
	 * @author yanb
	 * @created 2017年11月20日 14:33:35
	 * @param  * @param houseBaseFid
	 * @return com.ziroom.minsu.services.house.issue.vo.HouseHallVo
	 */
	public HouseHallVo getHallByHouseBaseFid(String houseBaseFid) {
		return mybatisDaoContext.findOne(SQLID + "getHallByHouseBaseFid", HouseHallVo.class, houseBaseFid);
	}

	/**
	 * 根据houseBaseFid 查询客厅/房间的名字
	 * 共享客厅或唯一房间专用
	 * * @author yanb
	 * @param houseBaseFid
	 * @return
	 */
	public String getRoomNameByHouseBaseFid(String houseBaseFid) {
		return mybatisDaoContext.findOne(SQLID + "getRoomNameByHouseBaseFid", String.class, houseBaseFid);
	}

	/**
	 * 共享客厅校验
	 * 根据houseBaseFid 查询客厅/房间的roomType
	 * 共享客厅或唯一房间专用
	 * * @author yanb
	 * @param houseBaseFid
	 * @return
	 */
	public Integer getRoomTypeByHouseBaseFid(String houseBaseFid) {
		return mybatisDaoContext.findOne(SQLID + "getRoomTypeByHouseBaseFid",Integer.class,houseBaseFid);
	}

	/**
	 *
	 * 根据houseBaseFid逻辑删除共享客厅的房间信息
	 *
	 * @author yanb
	 */
	public int deleteHallMsgByhouseBaseFid(String houseBaseFid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("houseBaseFid", houseBaseFid);
		return mybatisDaoContext.update(SQLID + "deleteHallMsgByhouseBaseFid", map);
	}

	/**
	 * 根据roomFid获取roomType
	 * @param roomFid
	 * @return
	 */
	public Integer getRoomTypeByRoomFid(String roomFid) {
		return mybatisDaoContext.findOne(SQLID + "getRoomTypeByRoomFid", Integer.class, roomFid);
	}
}
