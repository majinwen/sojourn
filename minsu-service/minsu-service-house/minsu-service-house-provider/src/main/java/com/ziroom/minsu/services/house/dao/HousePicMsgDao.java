package com.ziroom.minsu.services.house.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HousePicMsgEntity;
import com.ziroom.minsu.services.house.dto.HousePicDto;
import com.ziroom.minsu.services.house.entity.HouseDefaultPicInfoVo;
import com.ziroom.minsu.services.house.pc.dto.HousePicTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <p>房源图片信息dao</p>
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
@Repository("house.housePicMsgDao")
public class HousePicMsgDao {


    private String SQLID="house.housePicMsgDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 
     * 新增房源图片信息
     *
     * @author liujun
     * @created 2016年4月1日 下午3:56:44
     *
     * @param housePicMsg
     * @return
     */
    public int insertHousePicMsg(HousePicMsgEntity housePicMsg) {
		return mybatisDaoContext.save(SQLID+"insertHousePicMsg", housePicMsg);
	}
    
    /**
     * 
     * 更新房源图片信息
     *
     * @author liujun
     * @created 2016年4月1日 下午3:56:44
     *
     * @param housePicMsg
     * @return
     */
    public int updateHousePicMsg(HousePicMsgEntity housePicMsg) {
    	return mybatisDaoContext.update(SQLID+"updateHousePicMsgByFid", housePicMsg);
    }

	/**
	 * 根据房源图片逻辑id逻辑删除图片信息
	 *
	 * @author liujun
	 * @created 2016年4月5日 下午7:07:04
	 *
	 * @param housePicFid
	 */
	public int deleteHousePicMsgByFid(String housePicFid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fid", housePicFid);
		return mybatisDaoContext.update(SQLID + "deleteHousePicMsgByFid", map);
	}
	
	/**
	 * 
	 * 房源图片fid集合逻辑删除所有图片信息
	 *
	 * @author lunan
	 * @created 2016年10月28日 下午9:45:40
	 *
	 * @param picFids
	 * @return
	 */
	public int delAllHousePicMsgByFid(String picFids) {
		List<String> picList = JsonEntityTransform.json2ObjectList(picFids, String.class);
		//mybatisDaoContext.update(SQLID + "delAllHousePicMsgByFid", picList);
		return mybatisDaoContext.getWriteSqlSessionTemplate().update(SQLID +"delAllHousePicMsgByFid", picList);
	}
	
	
	/**
	 * 
	 * 根据房间fid逻辑删除房间所有图片
	 *
	 * @author jixd
	 * @created 2016年6月13日 下午5:37:31
	 *
	 * @param roomFid
	 * @return
	 */
	public int deleteHousePicMsgByRoomFid(String roomFid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fid", roomFid);
		return mybatisDaoContext.update(SQLID + "deleteHousePicMsgByRoomFid", map);
	}
	/**
	 * 
	 * 根据房源fid逻辑删除房源下所有图片
	 *
	 * @author jixd
	 * @created 2016年6月13日 下午5:38:11
	 *
	 * @param roomFid
	 * @return
	 */
	public int deleteHousePicMsgByHouseFid(String houseFid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fid", houseFid);
		return mybatisDaoContext.update(SQLID + "deleteHousePicMsgByHouseFid", map);
	}

	/**
	 * 根据房源图片逻辑id查询图片信息
	 *
	 * @author liujun
	 * @created 2016年4月5日 下午8:33:51
	 *
	 * @param housePicFid
	 * @return
	 */
	public HousePicMsgEntity findHousePicMsgByFid(String housePicFid) {
		return mybatisDaoContext.findOne(SQLID + "findHousePicMsgByFid", HousePicMsgEntity.class, housePicFid);
	}
	
	/**
	 * 
	 * 类型图片数量查询
	 *
	 * @author bushujie
	 * @created 2016年4月9日 下午2:05:22
	 *
	 * @param paramMap
	 * @return
	 */
	public int getHousePicCount(Map<String, Object> paramMap){
		return mybatisDaoContext.findOne(SQLID+"getHousePicCountByPicType", Integer.class, paramMap);
	}

	/**
	 * 查询图片集合
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/22 10:50
	 */
	public List<HousePicMsgEntity> getHousePicList(Map<String, Object> paramMap){
		return mybatisDaoContext.findAll(SQLID+"getHousePicListByPicType",HousePicMsgEntity.class,paramMap);
	}
	
	/**
	 * 
	 * 根据房源fid和图片类型查询图片列表
	 *
	 * @author bushujie
	 * @created 2016年4月13日 下午11:58:58
	 *
	 * @param houseBaseFid
	 * @param picType
	 * @return
	 */
	public List<HousePicMsgEntity> getHousePicByType(String houseBaseFid,Integer picType){
		Map<String, Object> paramMap=new HashMap<String,Object>();
		paramMap.put("houseBaseFid", houseBaseFid);
		paramMap.put("picType", picType);
		return mybatisDaoContext.findAll(SQLID+"getHousePicByType", HousePicMsgEntity.class, paramMap);
	}
	
	/**
	 * 查询上架房源未审核通过照片集合
	 *
	 * @author liujun
	 * @created 2016年4月15日 上午10:23:14
	 *
	 * @param houseFid
	 * @return 
	 */
	public List<HousePicMsgEntity> findHouseUnapproveedPicList(String houseFid) {
		return mybatisDaoContext.findAll(SQLID+"findHouseUnapproveedPicList", HousePicMsgEntity.class, houseFid);
	}

	/**
	 * 查询上架房间未审核通过照片集合
	 *
	 * @author liujun
	 * @created 2016年4月15日 上午11:12:27
	 *
	 * @param houseFid
	 * @return
	 */
	public List<HousePicMsgEntity> findRoomUnapproveedPicList(String houseFid) {
		return mybatisDaoContext.findAll(SQLID+"findRoomUnapproveedPicList", HousePicMsgEntity.class, houseFid);
	}

	/**
	 * 根据图片类型查询默认图片
	 *
	 * @author liujun
	 * @created 2016年4月20日 下午4:34:13
	 *
	 * @param paramMap
	 * @return
	 */
	public HousePicMsgEntity findDefaultPicByType(String houseBaseFid, String roomFid, Integer picType) {
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("houseBaseFid", houseBaseFid);
		paramMap.put("roomFid", roomFid);
		paramMap.put("picType", picType);
		return mybatisDaoContext.findOne(SQLID+"findDefaultPicByType", HousePicMsgEntity.class, paramMap);
	}

	/**
	 * 查询房源照片列表
	 *
	 * @author liujun
	 * @created 2016年4月20日 下午6:24:00
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public List<HousePicMsgEntity> findHousePicList(String houseBaseFid) {
		return mybatisDaoContext.findAll(SQLID+"findHousePicList", HousePicMsgEntity.class, houseBaseFid);
	}
	
	/**
	 * 查询房源照片列表
	 *
	 * @author liujun
	 * @created 2016年4月20日 下午6:24:00
	 *
	 * @param houseRoomFid
	 * @return
	 */
	public List<HousePicMsgEntity> findRoomPicList(String houseRoomFid) {
		return mybatisDaoContext.findAll(SQLID+"findRoomPicList", HousePicMsgEntity.class, houseRoomFid);
	}

	/**
	 * 获取房间默认图片
	 *
	 * @author liujun
	 * @created 2016年4月20日 下午9:59:11
	 *
	 * @param houseRoomFid
	 * @return
	 */
	public HousePicMsgEntity findRoomDefaultPic(String houseRoomFid) {
		return mybatisDaoContext.findOne(SQLID+"findRoomDefaultPic", HousePicMsgEntity.class, houseRoomFid);
	}
	
	/**
	 * 获取房间旧默认图片
	 *
	 * @author liujun
	 * @created 2016年4月20日 下午9:59:11
	 *
	 * @param houseRoomFid
	 * @return
	 */
	public HousePicMsgEntity findOldRoomDefaultPic(String houseRoomFid) {
		return mybatisDaoContext.findOne(SQLID+"findOldRoomDefaultPic", HousePicMsgEntity.class, houseRoomFid);
	}
	
	/**
	 * 获取房间默认图片（房东端）
	 *
	 * @author liujun
	 * @created 2016年4月20日 下午9:59:11
	 *
	 * @param houseRoomFid
	 * @return
	 */
	public HousePicMsgEntity findLandlordRoomDefaultPic(String houseRoomFid) {
		return mybatisDaoContext.findOne(SQLID+"findLandlordRoomDefaultPic", HousePicMsgEntity.class, houseRoomFid);
	}

	/**
	 * 组合条件查询图片集合
	 *
	 * @author liujun
	 * @created 2016年4月30日 下午5:44:05
	 *
	 * @param housePicDto
	 * @return
	 */
	public List<HousePicMsgEntity> findHousePicMsgList(HousePicDto housePicDto) {
		return mybatisDaoContext.findAll(SQLID+"findHousePicMsgList", HousePicMsgEntity.class, housePicDto);
	}
	
	/**
	 * 
	 * 查询房源默认图片
	 *
	 * @author bushujie
	 * @created 2016年5月1日 上午10:42:57
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HousePicMsgEntity findHouseDefaultPic(String houseBaseFid ){
		return mybatisDaoContext.findOne(SQLID+"findHouseDefaultPic", HousePicMsgEntity.class, houseBaseFid);
	}
	
	/**
	 * 
	 * 查询房源旧默认图片
	 *
	 * @author bushujie
	 * @created 2016年5月1日 上午10:42:57
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HousePicMsgEntity findOldHouseDefaultPic(String houseBaseFid ){
		return mybatisDaoContext.findOne(SQLID+"findOldHouseDefaultPic", HousePicMsgEntity.class, houseBaseFid);
	}
	
	/**
	 * 
	 * 查询房东端房源默认图片
	 *
	 * @author bushujie
	 * @created 2016年5月1日 上午10:42:57
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HousePicMsgEntity findLandlordHouseDefaultPic(String houseBaseFid ){
		return mybatisDaoContext.findOne(SQLID+"findLandlordHouseDefaultPic", HousePicMsgEntity.class, houseBaseFid);
	}
	
	/**
	 * 
	 * 更新默认图片
	 *
	 * @author jixd
	 * @created 2016年5月12日 下午9:32:10
	 *
	 * @return
	 */
	public int updateHouseDefaultPic(HousePicDto housePicDto){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("houseBaseFid", housePicDto.getHouseBaseFid());
		paramMap.put("houseRoomFid", housePicDto.getHouseRoomFid());
		paramMap.put("picType", housePicDto.getPicType());
		paramMap.put("housePicFid", housePicDto.getHousePicFid());
		paramMap.put("isDefault", housePicDto.getIsDefault());
		return mybatisDaoContext.update(SQLID+"updateDefaultPic", paramMap);
	}
	
	/**
	 * 
	 * 查询房间和公共区域图片集合
	 *
	 * @author bushujie
	 * @created 2016年5月19日 上午3:27:24
	 *
	 * @param roomFid
	 * @return
	 */
	public List<HousePicMsgEntity> findHousePicListByRoomFid(String roomFid,String houseBaseFid){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("roomFid", roomFid);
		paramMap.put("houseBaseFid", houseBaseFid);
		return mybatisDaoContext.findAllByMaster(SQLID+"findHousePicListByRoomFid", HousePicMsgEntity.class, paramMap);
	}
	
	/**
	 * 
	 * 更新房源图片第一条未默认图片
	 *
	 * @author bushujie
	 * @created 2016年5月19日 下午11:16:03
	 *
	 * @param paramMap
	 * @return
	 */
	public int updateDefaultPicLimit(Map<String, Object> paramMap){
		return mybatisDaoContext.update(SQLID+"updateDefaultPicLimit", paramMap);
	}
	
	/**
	 * 
	 * 查询是否默认图片
	 *
	 * @author bushujie
	 * @created 2016年5月20日 上午4:04:20
	 *
	 * @param fid
	 * @return
	 */
	public int getDefaultPicByFid(String fid){
		return mybatisDaoContext.findOne(SQLID+"getDefaultPicByFid", Integer.class, fid);
	}
	
	/**
	 * 前端房源详情图片列表
	 *
	 * @author bushujie
	 * @created 2016年4月30日 下午5:44:05
	 *
	 * @param housePicDto
	 * @return
	 */
	public List<HousePicMsgEntity> findHouseDetailPicMsgList(HousePicDto housePicDto) {
		return mybatisDaoContext.findAll(SQLID+"findHouseDetailPicMsgList", HousePicMsgEntity.class, housePicDto);
	}
	
	/**
	 * 
	 * 查询房间和公共区域图片集合（详情）
	 *
	 * @author bushujie
	 * @created 2016年5月19日 上午3:27:24
	 *
	 * @param roomFid
	 * @return
	 */
	public List<HousePicMsgEntity> findHouseDetailPicListByRoomFid(String roomFid,String houseBaseFid){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("roomFid", roomFid);
		paramMap.put("houseBaseFid", houseBaseFid);
		return mybatisDaoContext.findAllByMaster(SQLID+"findHouseDetailPicListByRoomFid", HousePicMsgEntity.class, paramMap);
	}
	
	/**
	 * 
	 * 查询房源下未审核房源图片
	 *
	 * @author bushujie
	 * @created 2016年6月23日 下午5:34:57
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public List<HousePicMsgEntity> findNoAuditHousePicList(String roomFid,String houseBaseFid){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("roomFid", roomFid);
		paramMap.put("houseBaseFid", houseBaseFid);
		return mybatisDaoContext.findAllByMaster(SQLID+"findNoAuditHousePicList", HousePicMsgEntity.class, paramMap);
	}
	
	/**
	 * 
	 * 查询房源图片，不考虑删除
	 *
	 * @author bushujie
	 * @created 2016年7月12日 下午5:21:24
	 *
	 * @param housePicFid
	 * @return
	 */
	public HousePicMsgEntity findHousePicByFid(String housePicFid){
		return mybatisDaoContext.findOne(SQLID+"findHousePicByFid", HousePicMsgEntity.class, housePicFid);
	}
	
	/**
	 * 
	 * 查询房源照片
	 *
	 * @author jixd
	 * @created 2016年8月17日 下午1:07:46
	 *
	 * @return
	 */
	public List<HousePicMsgEntity> findHousePicByTypeAndFid(HousePicTypeDto housePicType){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("houseFid", housePicType.getHouseFid());
		paramMap.put("roomFid", housePicType.getRoomFid());
		paramMap.put("picTypes", housePicType.getPicTypes());
		return mybatisDaoContext.findAllByMaster(SQLID+"findHousePicByTypeAndFid", HousePicMsgEntity.class, paramMap);
	}
	
	/**
	 * 当前房源 无默认照片 查询卧室、客厅、室外三个区域中第一张上传的照片
	 *
	 * @author yd
	 * @created 2016年10月18日 下午2:55:25
	 *
	 * @param houseFid
	 * @param roomFid
	 * @param rentWay
	 * @return
	 */
	public HousePicMsgEntity findHousePicFirstByHouseFid(String houseFid,String roomFid,int rentWay){
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("houseFid", houseFid);
		params.put("roomFid", roomFid);
		params.put("rentWay", rentWay);
		return mybatisDaoContext.findOne(SQLID+"findHousePicFirstByHouseFid", HousePicMsgEntity.class,params);
	}

	/**
	 * @Description: 整租时查询房源默认图片相关信息
	 * @Author: lusp
	 * @Date: 2017/6/22 21:43
	 * @Params: houseFid
	 */
	public HouseDefaultPicInfoVo findDefaultPicListInfoByHouseFid(String houseFid) {
		return mybatisDaoContext.findOne(SQLID+"findDefaultPicListInfoByHouseFid", HouseDefaultPicInfoVo.class, houseFid);
	}

	/**
	 * @Description: 分租时查询房源默认图片相关信息
	 * @Author: lusp
	 * @Date: 2017/6/22 21:44
	 * @Params: roomFid
	 */
	public HouseDefaultPicInfoVo findDefaultPicListInfoByRoomFid(String roomFid) {
		return mybatisDaoContext.findOne(SQLID+"findDefaultPicListInfoByRoomFid", HouseDefaultPicInfoVo.class, roomFid);
	}

	/**
	 * @Description: 根据houseFid 查询对应的roomFid
	 * @Author: lusp
	 * @Date: 2017/6/22 21:50
	 * @Params: houseFid
	 */
	public List<String> findRoomfidByHouseFid(String houseFid) {
		return mybatisDaoContext.findAllByMaster(SQLID+"findRoomfidByHouseFid", String.class, houseFid);
	}
	
	/**
	 * 
	 * 根据状态查询图片列表
	 *
	 * @author bushujie
	 * @created 2017年9月21日 下午7:09:15
	 *
	 * @param paramMap
	 * @return
	 */
	public List<HousePicMsgEntity> findPicByAuditStatus(Map<String, Object> paramMap){
		return mybatisDaoContext.findAll(SQLID+"findPicByAuditStatus", HousePicMsgEntity.class, paramMap);
	}
	
	/**
	 * 
	 * 更新新增照片为未审核
	 *
	 * @author bushujie
	 * @created 2017年9月21日 下午7:59:03
	 *
	 * @param paramMap
	 */
	public void updatePicAuditStatusToNo(Map<String, Object> paramMap){
		mybatisDaoContext.update(SQLID+"updatePicAuditStatusToNo", paramMap);
	}
}