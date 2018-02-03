package com.ziroom.minsu.services.house.dao;


import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.base.AuthMenuEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.services.house.dto.HouseBaseExtDto;
import com.ziroom.minsu.services.house.dto.HouseBaseListDto;
import com.ziroom.minsu.services.house.dto.HouseBaseMsgDto;
import com.ziroom.minsu.services.house.dto.HouseBaseParamsDto;
import com.ziroom.minsu.services.house.dto.HouseDetailDto;
import com.ziroom.minsu.services.house.dto.HouseDfbNoticeDto;
import com.ziroom.minsu.services.house.dto.HouseRequestDto;
import com.ziroom.minsu.services.house.dto.HouseRoomListPcDto;
import com.ziroom.minsu.services.house.entity.HouseBaseDetailVo;
import com.ziroom.minsu.services.house.entity.HouseBaseListVo;
import com.ziroom.minsu.services.house.entity.HouseCityVo;
import com.ziroom.minsu.services.house.entity.HouseDetailVo;
import com.ziroom.minsu.services.house.entity.HouseMsgVo;
import com.ziroom.minsu.services.house.entity.HousePicAuditVo;
import com.ziroom.minsu.services.house.entity.HouseResultNewVo;
import com.ziroom.minsu.services.house.entity.HouseResultVo;
import com.ziroom.minsu.services.house.entity.HouseRoomVo;
import com.ziroom.minsu.services.house.entity.NeedPhotogHouseVo;
import com.ziroom.minsu.services.house.entity.OrderNeedHouseVo;
import com.ziroom.minsu.services.house.entity.SearchTerm;
import com.ziroom.minsu.services.house.entity.TenantHouseDetailVo;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <p>房源基本信息dao</p>
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
@Repository("house.houseBaseMsgDao")
public class HouseBaseMsgDao {


    private String SQLID="house.houseBaseMsgDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    @Resource(name = "house.housePhyMsgDao")
	private HousePhyMsgDao housePhyMsgDao;

    /**
     * 
     * 新增房源基础信息
     *
     * @author liujun
     * @created 2016年4月9日 上午11:51:23
     *
     * @param houseBaseMsg
     */
    public int insertHouseBaseMsg(HouseBaseMsgEntity houseBaseMsg) {
    	
    	if(!Check.NuNObj(houseBaseMsg)){
    		if(Check.NuNStr(houseBaseMsg.getFid())) houseBaseMsg.setFid(UUIDGenerator.hexUUID());
    	}
		return mybatisDaoContext.save(SQLID+"insertHouseBaseMsg", houseBaseMsg);
	}
    

    /**
     * 
     * 更新房源基础信息
     *
     * @author liujun
     * @created 2016年4月9日 上午11:51:38
     *
     * @param houseBaseMsg
     * @return
     */
    public int updateHouseBaseMsg(HouseBaseMsgEntity houseBaseMsg) {
    	
    	return mybatisDaoContext.update(SQLID + "updateHouseBaseMsgByFid", houseBaseMsg);
    }
    
    /**
	 * 
	 * 根据房源fid逻辑删除该房源
	 *
	 * @author jixd
	 * @created 2016年6月13日 下午5:24:39
	 *
	 * @param houseFid
	 * @return
	 */
	public int deleteHouseBaseMsgByFid(String houseFid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fid", houseFid);
		return mybatisDaoContext.update(SQLID + "deleteHouseBaseMsgByFid", map);
	}
    
    /**
     * 根据fid和房东uid更新房源基础信息
     * @author lishaochuan
     * @create 2016年5月26日下午5:28:06
     * @param houseBaseMsg
     * @return
     */
    public int updateHouseBaseMsgByUid(HouseBaseMsgEntity houseBaseMsg) {
    	
    	return mybatisDaoContext.update(SQLID+"updateHouseBaseMsgByUid", houseBaseMsg);
    }
    
    /**
     * 
     * 条件查询房源列表(房东端)
     *
     * @author liujun
     * @created 2016年4月9日 上午11:51:50
     *
     * @param paramDto
     * @return
     */
    public PagingResult<HouseBaseListVo> findHouseBaseList(HouseBaseListDto paramDto){
    	PageBounds pageBounds=new PageBounds();
    	pageBounds.setPage(paramDto.getPage());
    	pageBounds.setLimit(paramDto.getLimit());
    	return mybatisDaoContext.findForPage(SQLID + "findHouseBaseList", HouseBaseListVo.class, paramDto, pageBounds);
    }


	/**
	 * 根据houseSn查询房源信息
	 * @author lisc
	 * @param houseSn
	 * @return
     */
	public HouseBaseMsgEntity findHouseBaseByHouseSn(String houseSn) {
		return mybatisDaoContext.findOne(SQLID + "findHouseBaseByHouseSn", HouseBaseMsgEntity.class, houseSn);
	}
    
    /**
     * 
     * 根据fid查询房源基础信息
     *
     * @author liujun
     * @created 2016年4月9日 上午11:52:24
     *
     * @param fid
     * @return
     */
    public HouseBaseMsgEntity getHouseBaseMsgEntityByFid(String fid){
    	return mybatisDaoContext.findOne(SQLID + "getHouseBaseByFid", HouseBaseMsgEntity.class, fid);
    }

	/**
	 * 根据房源编号集合查询房源信息集合
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/21 11:32
	 */
	public List<HouseBaseMsgEntity> getHouseBaseListByHouseSns(List<String> houseSns){
		return mybatisDaoContext.getWriteSqlSessionTemplate().selectList(SQLID+"getHouseBaseListByHouseSns",houseSns);
	}

    /**
     * 通过房间fid 获取房源信息
     * @param roomFid
     * @return
     */
    public HouseBaseMsgEntity getHouseBaseMsgEntityByRoomFid(String roomFid){
        return mybatisDaoContext.findOne(SQLID + "getHouseBaseMsgEntityByRoomFid", HouseBaseMsgEntity.class, roomFid);

    }
    
    /**
     * 
     * 通过房东uid查询整租已上架房源
     *
     * @author lunan
     * @created 2016年10月9日 下午2:31:42
     *
     * @param landlordUid
     * @return
     */
    public HouseBaseMsgEntity findHouseWholeByLandlordUid(String landlordUid){
    	return mybatisDaoContext.findOne(SQLID + "getHouseWholeByLandlordUid", HouseBaseMsgEntity.class, landlordUid);
    }
    
    /**
     * 
     * 通过房东uid查询分租已上架房源
     * @author lunan
     * @created 2016年10月9日 下午2:31:42
     *
     * @param landlordUid
     * @return
     */
    public HouseBaseMsgEntity findHouseSubletByLandlordUid(String landlordUid){
    	return mybatisDaoContext.findOne(SQLID + "getHouseSubletByLandlordUid", HouseBaseMsgEntity.class, landlordUid);
    }

	/**
	 * 
	 * 查询房源基础信息与房源描述信息
	 *
	 * @author liujun
	 * @created 2016年4月9日 上午11:52:52
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HouseBaseMsgDto findHouseBaseMsgAndHouseDesc(String houseBaseFid) {
		return mybatisDaoContext.findOne(SQLID + "findHouseBaseMsgAndHouseDesc", HouseBaseMsgDto.class, houseBaseFid);
	}
	
	/**
	 * 
	 * 查询房源详情
	 *
	 * @author liujun
	 * @created 2016年4月9日 上午11:53:25
	 *
	 * @param houseBaseFid
	 * @param landlordUid
	 * @return
	 */
	public HouseDetailVo findHouseDetail(String houseBaseFid,String landlordUid){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("houseBaseFid", houseBaseFid);
		paramMap.put("landlordUid", landlordUid);
		return mybatisDaoContext.findOne(SQLID+"getHouseDetailByFid", HouseDetailVo.class, paramMap);
	}
	
	/**
	 * 
	 * 查询订单所需房源信息
	 *
	 * @author liujun
	 * @created 2016年4月9日 上午11:54:03
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public OrderNeedHouseVo findOrderNeedHouseVo(String houseBaseFid){
		return mybatisDaoContext.findOne(SQLID+"getOrderNeedHouseVo", OrderNeedHouseVo.class, houseBaseFid);
	}

	/**
	 * 
	 * 后台查询房源信息列表
	 *
	 * @author liujun
	 * @created 2016年4月9日 上午11:55:02
	 *
	 * @param houseRequest
	 * @return
	 */
	public PagingResult<HouseResultVo> findHouseMsgListByHouse(HouseRequestDto houseRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseRequest.getPage());
		pageBounds.setLimit(houseRequest.getLimit());
		
		return mybatisDaoContext.findForPage(SQLID+"findHouseMsgListByHouse", HouseResultVo.class, houseRequest.toMap(), pageBounds);
	}
	
	

	/**
	 *  
	 *  后台查询房源修改信息列表
	 * 
	 *
	 * @author yd
	 * @created 2017年11月2日 下午4:50:24
	 *
	 * @param houseRequest
	 * @return
	 */
	public PagingResult<HouseResultVo> findUpdateHouseMsgListByHouse(HouseRequestDto houseRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseRequest.getPage());
		pageBounds.setLimit(houseRequest.getLimit());
		
		return mybatisDaoContext.findForPage(SQLID+"findUpdateHouseMsgListByHouse", HouseResultVo.class, houseRequest.toMap(), pageBounds);
	}
	
	/**
	 * 
	 * 后台查询房源信息列表(区分houseFid和roomFid)
	 *
	 * @author zl
     * @created 2016年11月21日
	 *
	 * @param houseRequest
	 * @return
	 */
	public PagingResult<HouseResultNewVo> findHouseMsgListByHouseNew(HouseRequestDto houseRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseRequest.getPage());
		pageBounds.setLimit(houseRequest.getLimit());
		
		return mybatisDaoContext.findForPage(SQLID+"findHouseMsgListByHouseNew", HouseResultNewVo.class, houseRequest.toMap(), pageBounds);
	}

	/**
	 * 
	 * 后台查询房间信息列表
	 *
	 * @author liujun
	 * @created 2016年4月9日 上午11:55:16
	 *
	 * @param houseRequest
	 * @return
	 */
	public PagingResult<HouseResultVo> findHouseMsgListByRoom(HouseRequestDto houseRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseRequest.getPage());
		pageBounds.setLimit(houseRequest.getLimit());
		return mybatisDaoContext.findForPage(SQLID+"findHouseMsgListByRoom", HouseResultVo.class, houseRequest.toMap(), pageBounds);
	}
	
	
	/**
	 * 
	 * 后台查询房间信息列表  区分houseFid和roomFid
	 *
	 * @author zl
     * @created 2016年11月21日
	 *
	 * @param houseRequest
	 * @return
	 */
	public PagingResult<HouseResultNewVo> findHouseMsgListByRoomNew(HouseRequestDto houseRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseRequest.getPage());
		pageBounds.setLimit(houseRequest.getLimit());
		return mybatisDaoContext.findForPage(SQLID+"findHouseMsgListByRoomNew", HouseResultNewVo.class, houseRequest.toMap(), pageBounds);
	}

	/**
	 * 
	 * 根据房源逻辑id查询房源详情
	 *
	 * @author liujun
	 * @created 2016年4月9日 上午11:55:33
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HouseMsgVo findHouseDetailByFid(String houseBaseFid) {
		return mybatisDaoContext.findOne(SQLID+"findHouseDetailByFid", HouseMsgVo.class, houseBaseFid);
	}

	/**
	 * 根据房源基础信息逻辑id查询基础信息与基础信息扩展
	 *
	 * @author liujun
	 * @created 2016年4月9日 下午4:41:21
	 *
	 * @param houseBaseFid
	 * @return 
	 */
	public HouseBaseExtDto findHouseBaseExtDtoByHouseBaseFid(String houseBaseFid) {
		return mybatisDaoContext.findOne(SQLID+"findHouseBaseExtDtoByHouseBaseFid", HouseBaseExtDto.class, houseBaseFid);
	}
	
	/**
	 * 
	 * 查询超时未审核房源列表
	 *
	 * @author bushujie
	 * @created 2016年4月9日 下午9:19:14
	 *
	 * @param paramMap
	 * @return
	 */
	public List<HouseBaseMsgEntity> findOverAuditLimitHouse(Map<String, Object> paramMap){
		return mybatisDaoContext.findAll(SQLID+"findOverAuditLimitHouse", HouseBaseMsgEntity.class, paramMap);
	}
	
	/**
	 * 
	 * 更新房源物理楼盘fid
	 *
	 * @author bushujie
	 * @created 2016年4月12日 下午3:33:43
	 *
	 * @param newPhyFid
	 * @param oldPhyFid
	 * @return
	 */
	public int updateHousePhyFid(String newPhyFid,String oldPhyFid){
		Map<String, Object> paramMap=new HashMap<String,Object>();
		paramMap.put("newPhyFid", newPhyFid);
		paramMap.put("oldPhyFid", oldPhyFid);
		return mybatisDaoContext.update(SQLID+"updateHousePhyFid", paramMap);
	}
	
	/**
	 * 
	 * 查询房源图片审核详情
	 *
	 * @author bushujie
	 * @created 2016年4月13日 下午10:51:00
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HousePicAuditVo findHousePicAuditVo(String houseBaseFid){
		return mybatisDaoContext.findOne(SQLID+"findHousePicAuditVo", HousePicAuditVo.class, houseBaseFid);
	}
	/**
	 * 查询照片修改后审核未通过上架房源列表
	 * 增加查询上架房源中房东修改需要审核字段的整租房源信息. @Author:lusp  @Date:2017/8/11
	 * @author liujun
	 * @param houseRequest 
	 * @created 2016年4月14日 上午12:52:40
	 *
	 * @return
	 */
	public PagingResult<HouseResultVo> findPicUnapproveedHouseList(HouseRequestDto houseRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseRequest.getPage());
		pageBounds.setLimit(houseRequest.getLimit());
		return mybatisDaoContext.findForPage(SQLID+"findPicUnapproveedHouseList", HouseResultVo.class, houseRequest.toMap(), pageBounds);
	}

	/**
	 * 查询照片修改后审核未通过上架房间列表
	 * 增加查询上架房源中房东修改需要审核字段的分租房源信息. @Author:lusp  @Date:2017/8/11
	 * @author liujun
	 * @created 2016年4月14日 上午11:47:21
	 *
	 * @param houseRequest
	 * @return
	 */
	public PagingResult<HouseResultVo> findPicUnapproveedRoomList(HouseRequestDto houseRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseRequest.getPage());
		pageBounds.setLimit(houseRequest.getLimit());
		return mybatisDaoContext.findForPage(SQLID+"findPicUnapproveedRoomList", HouseResultVo.class, houseRequest.toMap(), pageBounds);
	}
	
	/**
	 * 
	 * 房东房源包含的小区列表
	 *
	 * @author bushujie
	 * @created 2016年4月18日 下午6:55:41
	 *
	 * @param landlordUid
	 * @return
	 */
	public List<SearchTerm> getCommunityListByLandlordUid(String landlordUid){
		return mybatisDaoContext.findAll(SQLID+"getCommunityListByLandlordUid", SearchTerm.class, landlordUid);
	}
	
	/**
	 * 
	 * 查询客端房源详情
	 *
	 * @author bushujie
	 * @created 2016年4月30日 下午10:17:00
	 *
	 * @param houseDetailDto
	 * @return
	 */
	public TenantHouseDetailVo getHouseDetail(HouseDetailDto houseDetailDto){
		return mybatisDaoContext.findOneSlave(SQLID+"getHouseDetail", TenantHouseDetailVo.class, houseDetailDto);
	}
	
	/**
	 * 
	 * 更新房源是否有图片
	 *
	 * @author bushujie
	 * @created 2016年5月11日 下午3:52:24
	 *
	 * @param fid
	 */
	public int updateHousePic(String fid){
		Map<String, Object> paramMap=new HashMap<String,Object>();
		paramMap.put("fid", fid);
		return mybatisDaoContext.update(SQLID+"updateHousePic", paramMap);
	}
	
	/**
	 * 
	 * 房源fid查询房源详情信息
	 *
	 * @author bushujie
	 * @created 2016年5月26日 上午11:30:01
	 *
	 * @param fid
	 * @return
	 */
	public HouseBaseDetailVo getHouseBaseDetailVoByFid(String fid){
		return mybatisDaoContext.findOne(SQLID+"getHouseBaseDetail", HouseBaseDetailVo.class, fid);
	}
	
	/**
	 * 
	 * 房东端房源列表
	 *
	 * @author bushujie
	 * @created 2016年6月14日 下午1:59:25
	 *
	 * @param houseBaseListDto
	 * @return
	 */
	public PagingResult<HouseRoomVo> getLandlordHouseList(HouseBaseListDto houseBaseListDto) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseBaseListDto.getPage());
		pageBounds.setLimit(houseBaseListDto.getLimit());
		return mybatisDaoContext.findForPage(SQLID + "getLandlordHouseList", HouseRoomVo.class, houseBaseListDto,
				pageBounds);
	}
	
	/**
	 * 
	 * 房源PC列表
	 *
	 * @author jixd
	 * @created 2016年8月1日 下午2:28:31
	 *
	 * @param houseBaseListDto
	 * @return
	 */
	public PagingResult<HouseRoomVo> getHousePCList(HouseBaseListDto houseBaseListDto){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseBaseListDto.getPage());
		pageBounds.setLimit(houseBaseListDto.getLimit());
		return mybatisDaoContext.findForPage(SQLID + "getHousePCList", HouseRoomVo.class, houseBaseListDto,
				pageBounds);
	}
	
	/**
	 * 
	 * 获取日历上房源列表
	 *
	 * @author busj
	 * @created 2017年10月28日 下午2:28:31
	 *
	 * @param houseBaseListDto
	 * @return
	 */
	public PagingResult<HouseRoomVo> getCalendarHousePCList(HouseBaseListDto houseBaseListDto){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseBaseListDto.getPage());
		pageBounds.setLimit(houseBaseListDto.getLimit());
		return mybatisDaoContext.findForPage(SQLID + "getCalendarHousePCList", HouseRoomVo.class, houseBaseListDto,
				pageBounds);
	}
	
	/**
	 * 
	 * 判断当前houseSn是否存在
	 * 返回数0，代表没有  大于0代表存在
	 *
	 * @author yd
	 * @created 2016年6月20日 下午8:44:27
	 *
	 * @param houseSn
	 * @return
	 */
	public Long countByHouseSn(String houseSn){
		
		if(Check.NuNStr(houseSn)){
			return 1L;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseSn", houseSn);
    	return mybatisDaoContext.count(SQLID+"countByHouseSn", params);
	}
	
	/**
	 * 
	 * 计算房源数目
	 *
	 * @author liyingjie
	 * @created 2016年6月20日 下午8:44:27
	 *
	 * @param uid
	 * @return
	 */
	public Long countHouseNum(String uid){
		
		if(Check.NuNStr(uid)){
			return 0L;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("landlordUid", uid);
    	return mybatisDaoContext.count(SQLID+"countHouseNum", params);
	}
	
	/**
	 * 
	 * 计算房间数目
	 *
	 * @author liyingjie
	 * @created 2016年6月20日 下午8:44:27
	 *
	 * @param uid
	 * @return
	 */
	public Long countRoomNum(String uid){
		
		if(Check.NuNStr(uid)){
			return 0L;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("landlordUid", uid);
    	return mybatisDaoContext.count(SQLID+"countRoomNum", params);
	}
	
	/**
	 * 
	 * 查询房源列表
	 *
	 * @author liyingjie
	 * @created 2016年4月9日 下午9:19:14
	 *
	 * @param paramMap
	 * @return
	 */
	public List<HouseBaseMsgEntity> findHouseByPhy(Map<String, Object> paramMap){
		return mybatisDaoContext.findAll(SQLID+"findHouseByPhy", HouseBaseMsgEntity.class, paramMap);
	}
	
	/**
	 * 
	 * 查询房东uid对应房源数量
	 *
	 * @author bushujie
	 * @created 2016年7月22日 下午2:41:23
	 *
	 * @param uid
	 * @return
	 */
	public int findHouseCountByUid(String uid){
		return mybatisDaoContext.findOne(SQLID+"findHouseCountByUid", Integer.class, uid);
	}




	/**
	 * 查询房源信息和所在城市信息
	 * @author lishaochuan
	 * @create 2016年8月4日下午5:03:22
	 * @param houseFidList
	 * @return
	 */
	public List<HouseCityVo> getHouseCityVoByFids(List<String> houseFidList){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("houseFidList", houseFidList);
		return mybatisDaoContext.findAll(SQLID+"getHouseCityVoByFids", HouseCityVo.class, paramMap);
	}


	/**
	 *
	 * 楼盘物理地址fid查询房源数量
	 *
	 * @author bushujie
	 * @created 2016年8月8日 下午3:01:03
	 *
	 * @param phyFid
	 * @return
	 */
	public int findHouseCountByPhyFid(String phyFid,String houseBaseFid){
		Map<String, Object> paramMap=new HashMap<String,Object>();
		paramMap.put("phyFid", phyFid);
		paramMap.put("houseBaseFid", houseBaseFid);
		return mybatisDaoContext.findOne(SQLID+"findHouseCountByPhyFid", Integer.class, paramMap);
	}

	/**
	 *
	 * 查询房源房间信息
	 *
	 * @author bushujie
	 * @created 2016年8月11日 下午5:33:31
	 *
	 * @param fid
	 * @return
	 */
	public HouseRoomListPcDto findHouseRoomList(String fid){
		return mybatisDaoContext.findOne(SQLID+"findHouseRoomList", HouseRoomListPcDto.class, fid);
	}
	
	/**
	 * 
	 * 特殊权限房源列表查询
	 *
	 * @author bushujie
	 * @created 2016年10月26日 下午6:00:26
	 *
	 * @param houseRequest
	 * @return
	 */
	public PagingResult<HouseResultVo> findSpecialHouseMsgListByHouse(HouseRequestDto houseRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseRequest.getPage());
		pageBounds.setLimit(houseRequest.getLimit());
		
		return mybatisDaoContext.findForPage(SQLID+"findSpecialHouseMsgListByHouse", HouseResultVo.class, houseRequest.toMap(), pageBounds);
	}
	
	
	
	/**
	 * 
	 * 特殊权限:房源信息修改列表
	 *
	 * @author yd
	 * @created 2017年11月2日 下午5:01:43
	 *
	 * @param houseRequest
	 * @return
	 */
	public PagingResult<HouseResultVo> findSpecialUpateHouseMsgListByHouse(HouseRequestDto houseRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseRequest.getPage());
		pageBounds.setLimit(houseRequest.getLimit());
		
		return mybatisDaoContext.findForPage(SQLID+"findSpecialUpateHouseMsgListByHouse", HouseResultVo.class, houseRequest.toMap(), pageBounds);
	}
	
	/**
	 * 
	 * 特殊权限房源列表查询(区分houseFid和roomFid)
	 *
	 * @author zl
     * @created 2016年11月21日
	 *
	 * @param houseRequest
	 * @return
	 */
	public PagingResult<HouseResultNewVo> findSpecialHouseMsgListByHouseNew(HouseRequestDto houseRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseRequest.getPage());
		pageBounds.setLimit(houseRequest.getLimit());
		
		return mybatisDaoContext.findForPage(SQLID+"findSpecialHouseMsgListByHouseNew", HouseResultNewVo.class, houseRequest.toMap(), pageBounds);
	}
	
	 /**
	  * 
	  * 特殊权限合租房源列表查询
	  *
	  * @author bushujie
	  * @created 2016年10月27日 下午3:25:13
	  *
	  * @param houseRequest
	  * @return
	  */
	public PagingResult<HouseResultVo> findSpecialHouseMsgListByRoom(HouseRequestDto houseRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseRequest.getPage());
		pageBounds.setLimit(houseRequest.getLimit());
		return mybatisDaoContext.findForPage(SQLID+"findSpecialHouseMsgListByRoom", HouseResultVo.class, houseRequest.toMap(), pageBounds);
	}
	
	 /**
	  * 
	  * 特殊权限合租房源列表查询 区分houseFid和roomFid
	  *
	  * @author zl
      * @created 2016年11月21日
	  *
	  * @param houseRequest
	  * @return
	  */
	public PagingResult<HouseResultNewVo> findSpecialHouseMsgListByRoomNew(HouseRequestDto houseRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseRequest.getPage());
		pageBounds.setLimit(houseRequest.getLimit());
		return mybatisDaoContext.findForPage(SQLID+"findSpecialHouseMsgListByRoomNew", HouseResultNewVo.class, houseRequest.toMap(), pageBounds);
	}
	
	/**
	 * 
	 * 特殊权限查询照片修改后审核未通过上架房源列表
	 *
	 * @author bushujie
	 * @created 2016年10月28日 下午3:01:40
	 *
	 * @param houseRequest
	 * @return
	 */
	public PagingResult<HouseResultVo> findSpecialPicUnapproveedHouseList(HouseRequestDto houseRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseRequest.getPage());
		pageBounds.setLimit(houseRequest.getLimit());
		return mybatisDaoContext.findForPage(SQLID+"findSpecialPicUnapproveedHouseList", HouseResultVo.class, houseRequest.toMap(), pageBounds);
	}
	
	/**
	 * 
	 * 特殊查询照片修改后审核未通过上架房间列表
	 *
	 * @author bushujie
	 * @created 2016年10月28日 下午4:11:50
	 *
	 * @param houseRequest
	 * @return
	 */
	public PagingResult<HouseResultVo> findSpecialPicUnapproveedRoomList(HouseRequestDto houseRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseRequest.getPage());
		pageBounds.setLimit(houseRequest.getLimit());
		return mybatisDaoContext.findForPage(SQLID+"findSpecialPicUnapproveedRoomList", HouseResultVo.class, houseRequest.toMap(), pageBounds);
	}
	
	/**
	 * 
	 *  根据权限类型 查询房源fid集合
	 *  说明：如果返回集合 为null 说明当前用户 无任何房源的权限
	 *  
	 *  1. 有区域权限2，但无区域，则无权限
	 *
	 * @author yd
	 * @created 2016年10月31日 上午11:52:58
	 *
	 * @param authMenu
	 * @return
	 */
	public List<String> findHouseFidByAuth(AuthMenuEntity authMenu){
		
		if((Check.NuNObj(authMenu)||Check.NuNObj(authMenu.getRoleType())
				||authMenu.getRoleType() == 0)
				||(authMenu.getRoleType() == 2 &&Check.NuNCollection(authMenu.getUserCityList()))){
			return null;
		}
		return mybatisDaoContext.findAll(SQLID+"findHouseFidByAuth", String.class, authMenu);
	}
	
	/**
	 * 查询需要拍照的房源信息
	 * @param houseRequest
	 * @return
	 */
	public PagingResult<NeedPhotogHouseVo> findNeedPhotographerHouse(HouseRequestDto houseRequest){
		
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseRequest.getPage());
		pageBounds.setLimit(houseRequest.getLimit());
		
		return mybatisDaoContext.findForPage(SQLID+"findNeedPhotographerHouse", NeedPhotogHouseVo.class, houseRequest.toMap(), pageBounds);
	}
	
    /**
     * 
     * 查询需要 发送短信和极光消息的短信 的房源
     *
     * @author yd
     * @created 2016年11月22日 下午4:46:06
     *
     * @param cutTime 当前时间
     * @return
     */
	public List<HouseDfbNoticeDto>  findNoticeLanDfbHouseMsg(String cutTime,Integer day){
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cutTime", cutTime);
		paramMap.put("day", day);
		paramMap.put("dayPre", day-1);
		return mybatisDaoContext.findAll(SQLID+"findNoticeLanDfbHouseMsg", HouseDfbNoticeDto.class, paramMap);
	}


	/**
	 * 根据房源名称 ， 国家， 城市三个维度获取整租房源houseFidList
	 *
	 * @author loushuai
	 * @created 2017年5月25日 下午5:39:47
	 *
	 * @param paramMap
	 * @return
	 */
	public List<String> getHoseFidListForIMFollow(Map<String, Object> paramMap) {
		return mybatisDaoContext.findAll(SQLID+"getHoseFidListForIMFollow", String.class, paramMap);
	}
	
	/**
	 * 根据房间名称 ， 国家， 城市三个维度获取分租roomFidList
	 *
	 * @author loushuai
	 * @created 2017年5月25日 下午5:39:47
	 *
	 * @param paramMap
	 * @return
	 */
	public List<String> getRoomFidListForIMFollow(Map<String, Object> paramMap) {
		return mybatisDaoContext.findAll(SQLID+"getRoomFidListForIMFollow", String.class, paramMap);
	}


	/**
	 *  根据housefid获取房源名称 城市
	 *
	 * @author loushuai
	 * @created 2017年5月26日 下午4:31:27
	 *
	 * @param paramMap
	 * @return
	 */
	public HouseCityVo getHouseInfoForImFollow(Map<String, Object> paramMap) {
		return mybatisDaoContext.findOne(SQLID+"getHouseInfoForImFollow", HouseCityVo.class, paramMap);
	}


	/**
	 * 根据roomfid获取房源名称 城市
	 *
	 * @author loushuai
	 * @created 2017年5月26日 下午4:47:09
	 *
	 * @param paramMap
	 * @return
	 */
	public HouseCityVo getRoomInfoForImFollow(Map<String, Object> paramMap) {
		return mybatisDaoContext.findOne(SQLID+"getRoomInfoForImFollow", HouseCityVo.class, paramMap);
	}

	/**
	 * 获取房源或房间名称集合
	 *
	 * @author lusp
	 * @created 2017年6月30日 下午4:47:09
	 * @param houseBaseParamsDto
	 * @return
	 */
	public List<String> getHouseOrRoomNameList(HouseBaseParamsDto houseBaseParamsDto){
		if(houseBaseParamsDto.getRentWay() == RentWayEnum.HOUSE.getCode()){
			return mybatisDaoContext.findAll(SQLID+"getHouseNameListByHouseFid", String.class, houseBaseParamsDto);
		}else if(houseBaseParamsDto.getRentWay() == RentWayEnum.ROOM.getCode() && !Check.NuNStr(houseBaseParamsDto.getRoomFid())){
			return mybatisDaoContext.findAll(SQLID+"getRoomNameListByRoomFid", String.class, houseBaseParamsDto);
		}else{
			return mybatisDaoContext.findAll(SQLID+"getRoomNameListByHouseFid", String.class, houseBaseParamsDto);
		}
	}
}
