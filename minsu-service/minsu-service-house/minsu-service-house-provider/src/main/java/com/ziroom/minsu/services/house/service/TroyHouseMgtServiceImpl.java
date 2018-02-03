package com.ziroom.minsu.services.house.service;


import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.AuthMenuEntity;
import com.ziroom.minsu.entity.house.*;
import com.ziroom.minsu.services.basedata.entity.entityenum.GoogleBaiduCoordinateEnum;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.utils.CoordinateTransforUtils;
import com.ziroom.minsu.services.common.utils.Gps;
import com.ziroom.minsu.services.common.utils.StringUtils;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.services.house.dao.*;
import com.ziroom.minsu.services.house.dto.*;
import com.ziroom.minsu.services.house.entity.*;
import com.ziroom.minsu.services.house.issue.vo.HouseHallVo;
import com.ziroom.minsu.services.house.utils.HouseUtils;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.AuditStatusEnum;
import com.ziroom.minsu.valenum.house.*;
import com.ziroom.minsu.valenum.houseaudit.HouseAuditCauseEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0019;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * <p>房源管理业务层</p>
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
@Service("house.troyHouseMgtServiceImpl")
public class TroyHouseMgtServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(TroyHouseMgtServiceImpl.class);

	@Resource(name="house.houseBaseMsgDao")
	private HouseBaseMsgDao houseBaseMsgDao;

	@Resource(name="house.houseRoomMsgDao")
	private HouseRoomMsgDao houseRoomMsgDao;

	@Resource(name="house.houseRoomExtDao")
	private HouseRoomExtDao houseRoomExtDao;

	@Resource(name="house.houseOperateLogDao")
	private HouseOperateLogDao houseOperateLogDao;

	@Resource(name="house.houseFollowDao")
	private HouseFollowDao houseFollowDao;

	@Resource(name="house.housePhyMsgDao")
	private HousePhyMsgDao housePhyMsgDao;

	@Resource(name="house.houseConfMsgDao")
	private HouseConfMsgDao houseConfMsgDao;

	@Resource(name="house.housePicMsgDao")
	private HousePicMsgDao housePicMsgDao;

	@Resource(name="house.houseBedMsgDao")
	private HouseBedMsgDao houseBedMsgDao;

	@Resource(name="house.houseDescDao")
	private HouseDescDao houseDescDao;

	@Resource(name="house.houseBaseExtDao")
	private HouseBaseExtDao houseBaseExtDao;

	@Resource(name = "house.houseGuardRelDao")
	private HouseGuardRelDao houseGuardRelDao;

	@Resource(name = "house.houseCommonLogicDao")
	private HouseCommonLogicDao houseCommonLogicDao;

	@Resource(name = "house.houseUpdateHistoryLogDao")
	private HouseUpdateHistoryLogDao houseUpdateHistoryLogDao;

	@Resource(name = "house.houseUpdateHistoryExtLogDao")
	private HouseUpdateHistoryExtLogDao houseUpdateHistoryExtLogDao;

	@Resource(name = "house.houseUpdateFieldAuditNewlogDao")
	private HouseUpdateFieldAuditNewlogDao houseUpdateFieldAuditNewlogDao;

	@Resource(name = "house.houseUpdateFieldAuditManagerDao")
	private HouseUpdateFieldAuditManagerDao houseUpdateFieldAuditManagerDao;

	@Resource(name = "house.houseBizMsgDao")
	private HouseBizMsgDao houseBizMsgDao;

	@Resource(name = "house.houseIssueServiceImpl")
	private HouseIssueServiceImpl houseIssueServiceImpl;

	/**
	 * 根据houseSn查询房源信息
	 * @author lisc
	 * @param houseSn
	 * @return
	 */
	public HouseBaseMsgEntity findHouseBaseByHouseSn(String houseSn) {
		return houseBaseMsgDao.findHouseBaseByHouseSn(houseSn);
	}

	/**
	 * 根据房间编号查找房间信息
	 * @author jixd
	 * @created 2016年11月21日 10:38:48
	 * @param
	 * @return
	 */
	public HouseRoomMsgEntity findHouseRoomMsgByRoomSn(String roomSn){
		return houseRoomMsgDao.findHouseRoomMsgByRoomSn(roomSn);
	}


	/**
	 * 后台查询房源信息列表
	 *
	 * 说明： 1. 先查房源基本信息列表
	 *      2. 在查询房源最新审核信息
	 * @author liujun
	 * @created 2016年4月6日 下午4:00:05
	 *
	 * @param houseRequest
	 * @return
	 */
	public PagingResult<HouseResultVo> findHouseMsgListByHouse(HouseRequestDto houseRequest) {

		PagingResult<HouseResultVo>  pageRe = null;
		if(houseRequest.getRoleType()>0){
			return houseBaseMsgDao.findSpecialHouseMsgListByHouse(houseRequest);
		}
		pageRe = houseBaseMsgDao.findHouseMsgListByHouse(houseRequest);

		//填充房源审核信息
		if(!Check.NuNObj(pageRe)){
			List<HouseResultVo> list = pageRe.getRows();
			if(!Check.NuNCollection(list)){
				for (HouseResultVo houseResultVo : list) {
					HouseBizMsgEntity houseBizMsg = this.houseBizMsgDao.getHouseBizMsgByHouseFid(houseResultVo.getFid());
					if(!Check.NuNObj(houseBizMsg)){
						houseResultVo.setOnlineDate(houseBizMsg.getLastUpDate());
						houseResultVo.setAuditCause(houseBizMsg.getRefuseReason());
						houseResultVo.setLastDeployDate(houseBizMsg.getLastDeployDate());
					}
				}
			}
		}
		return pageRe;
	}



	/**
	 * 
	 * 后台查询:房源修改信息列表
	 *
	 * @author yd
	 * @created 2017年11月2日 下午4:54:59
	 *
	 * @param houseRequest
	 * @return
	 */
	public PagingResult<HouseResultVo> findUpateHouseMsgListByHouse(HouseRequestDto houseRequest) {
		if(houseRequest.getRoleType()>0){
			return houseBaseMsgDao.findSpecialUpateHouseMsgListByHouse(houseRequest);
		}
		return houseBaseMsgDao.findUpdateHouseMsgListByHouse(houseRequest);
	}
	/**
	 * 后台查询房源信息列表(区分houseFid和roomFid)
	 *
	 * @author zl
	 * @created 2016年11月21日
	 *
	 * @param houseRequest
	 * @return
	 */
	public PagingResult<HouseResultNewVo> findHouseMsgListByHouseNew(HouseRequestDto houseRequest) {
		
		if(houseRequest.getRoleType()>0){
			return houseBaseMsgDao.findSpecialHouseMsgListByHouseNew(houseRequest);
		}

		PagingResult<HouseResultNewVo> pageRe = houseBaseMsgDao.findHouseMsgListByHouseNew(houseRequest);

		//填充房源审核信息
		if(!Check.NuNObj(pageRe)){
			List<HouseResultNewVo> list = pageRe.getRows();
			if(!Check.NuNCollection(list)){
				for (HouseResultNewVo houseResultNewVo : list) {
					HouseBizMsgEntity houseBizMsg = this.houseBizMsgDao.getHouseBizMsgByHouseFid(houseResultNewVo.getHouseFid());
					if(!Check.NuNObj(houseBizMsg)){
						houseResultNewVo.setOnlineDate(houseBizMsg.getLastUpDate());
						houseResultNewVo.setAuditCause(houseBizMsg.getRefuseReason());
						houseResultNewVo.setLastDeployDate(houseBizMsg.getLastDeployDate());
					}
				}
			}
		}
		return pageRe;
	}



	/**
	 * 后台查询房间信息列表
	 *
	 * @author liujun
	 * @created 2016年4月6日 下午11:40:51
	 *
	 * @param houseRequest
	 * @return
	 */
	public PagingResult<HouseResultVo> findHouseMsgListByRoom(HouseRequestDto houseRequest) {
		if(houseRequest.getRoleType()>0){
			return houseBaseMsgDao.findSpecialHouseMsgListByRoom(houseRequest);
		}
		return houseBaseMsgDao.findHouseMsgListByRoom(houseRequest);
	}

	/**
	 * 后台查询房间信息列表(区分houseFid和roomFid)
	 *
	 * @author zl
	 * @created 2016年11月21日
	 *
	 * @param houseRequest
	 * @return
	 */
	public PagingResult<HouseResultNewVo> findHouseMsgListByRoomNew(HouseRequestDto houseRequest) {
		if(houseRequest.getRoleType()>0){
			return houseBaseMsgDao.findSpecialHouseMsgListByRoomNew(houseRequest);
		}
		return houseBaseMsgDao.findHouseMsgListByRoomNew(houseRequest);
	}

	/**
	 * 强制下架房源
	 *
	 * @author liujun
	 * @created 2016年4月7日 下午3:36:40
	 *
	 * @param houseBaseMsg
	 * @param paramMap
	 * @return
	 */
	public int forceDownHouse(HouseBaseMsgEntity houseBaseMsg, Map<String, Object> paramMap) {
		int toStatus = HouseStatusEnum.QZXJ.getCode();
		return this.handleHouse(houseBaseMsg, paramMap, toStatus);
	}

	/**
	 * 房源操作
	 *
	 * @author liujun
	 * @created 2016年4月20日 下午5:47:09
	 *
	 * @param houseBaseMsg
	 * @param paramMap
	 * @param toStatus @HouseStatusEnum
	 * @return 
	 */
	private int handleHouse(HouseBaseMsgEntity houseBaseMsg, Map<String, Object> paramMap, int toStatus) {
		// 保存房源审核补充信息
		if (!Check.NuNObj(paramMap.get("addtionalInfo"))) {
			HouseDescEntity houseDescEntity = new HouseDescEntity();
			houseDescEntity.setHouseBaseFid(houseBaseMsg.getFid());
			houseDescEntity.setAddtionalInfo((String) paramMap.get("addtionalInfo"));
			houseDescDao.updateHouseDescByHouseBaseFid(houseDescEntity);
		}

		//如果品质审核未通过 保存跟进记录
		if (toStatus == HouseStatusEnum.ZPSHWTG.getCode()){
			String auditStr = (String) paramMap.get("auditCause");
			if (!Check.NuNStr(auditStr)){
				String[] auditArr = auditStr.split(",");
				for (String stat : auditArr){
					if (stat.equals(String.valueOf(HouseAuditCauseEnum.REJECT.HOUSE_QUALITY.getCode()))){
						saveHouseFollow(houseBaseMsg,null,paramMap);
					}
				}
			}
		}

		// 保存房源操作日志
		houseCommonLogicDao.saveHouseOperateLogByBiz(houseBaseMsg, paramMap, toStatus);

		// 级联更新房间/床位状态
		houseCommonLogicDao.cascadingHouseStatus(houseBaseMsg, toStatus);

		/*修改前
		// 更新房源状态
		houseBaseMsg.setHouseStatus(toStatus);
		houseBaseMsg.setLastModifyDate(new Date());
		return houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);*/


		//修改后     @Author:lusp  @Date:2017/8/10
		HouseBaseMsgEntity houseBaseMsgEntity = new HouseBaseMsgEntity();
		houseBaseMsgEntity.setFid(houseBaseMsg.getFid());
		houseBaseMsgEntity.setHouseStatus(toStatus);
		return houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsgEntity);

	}

	/**
	 * 保存跟进信息
	 * @author jixd
	 * @created 2017年03月03日 16:03:44
	 * @param
	 * @return
	 */
	public void saveHouseFollow(HouseBaseMsgEntity houseBaseMsg,HouseRoomMsgEntity houseRoomMsg, Map<String, Object> paramMap){
		HouseFollowEntity houseFollowEntity = new HouseFollowEntity();
		houseFollowEntity.setFid(UUIDGenerator.hexUUID());
		if (Check.NuNObj(houseRoomMsg)){
			houseFollowEntity.setHouseBaseFid(houseBaseMsg.getFid());
			houseFollowEntity.setRentWay(RentWayEnum.HOUSE.getCode());
		}else{
			houseFollowEntity.setHouseBaseFid(houseRoomMsg.getHouseBaseFid());
			houseFollowEntity.setRoomFid(houseRoomMsg.getFid());
			houseFollowEntity.setRentWay(RentWayEnum.ROOM.getCode());
		}
		houseFollowEntity.setAuditStatusTime(new Date());
		houseFollowEntity.setAuditCause((String) paramMap.get("auditCause"));
		houseFollowEntity.setFollowType(FollowTypeEnum.FYWSHTG.getCode());
		houseFollowEntity.setFollowStatus(FollowStatusEnum.ZYDGJ.getCode());
		houseFollowEntity.setCreateFid((String) paramMap.get("operaterFid"));
		houseFollowDao.insertHouseFollow(houseFollowEntity);
	}

	/**
	 * 强制下架房间
	 *
	 * @author liujun
	 * @created 2016年4月7日 下午3:47:47
	 *
	 * @param houseRoomMsg
	 * @param paramMap
	 * @return
	 */
	public int forceDownRoom(HouseRoomMsgEntity houseRoomMsg, Map<String, Object> paramMap) {
		int toStatus = HouseStatusEnum.QZXJ.getCode();
		return this.handleRoom(houseRoomMsg, paramMap, toStatus);
	}

	/**
	 * 重新发布房源
	 *
	 * @author liujun
	 * @created 2016年5月25日
	 *
	 * @param houseBaseMsg
	 * @param paramMap
	 * @return
	 */
	public int reIssueHouse(HouseBaseMsgEntity houseBaseMsg, Map<String, Object> paramMap) {
		int toStatus = HouseStatusEnum.YFB.getCode();
		return this.handleHouse(houseBaseMsg, paramMap, toStatus);
	}

	/**
	 * 重新发布房间
	 *
	 * @author liujun
	 * @created 2016年5月25日
	 *
	 * @param houseRoomMsgEntity
	 * @param paramMap
	 * @return
	 */
	public int reIssueRoom(HouseRoomMsgEntity houseRoomMsg, Map<String, Object> paramMap) {
		int toStatus = HouseStatusEnum.YFB.getCode();
		return this.handleRoom(houseRoomMsg, paramMap, toStatus);
	}

	/**
	 * 房间操作
	 *
	 * @author liujun
	 * @created 2016年4月20日 下午5:50:29
	 *
	 * @param houseRoomMsg
	 * @param paramMap
	 * @param toStatus @HouseStatusEnum
	 * @return
	 */
	private int handleRoom(HouseRoomMsgEntity houseRoomMsg, Map<String, Object> paramMap, int toStatus) {
		// 保存房源审核补充信息
		if (!Check.NuNObj(paramMap.get("addtionalInfo"))) {
			HouseDescEntity houseDescEntity = new HouseDescEntity();
			houseDescEntity.setHouseBaseFid(houseRoomMsg.getHouseBaseFid());
			houseDescEntity.setAddtionalInfo((String) paramMap.get("addtionalInfo"));
			houseDescDao.updateHouseDescByHouseBaseFid(houseDescEntity);
		}

		//如果品质审核未通过 保存跟进记录
		String auditStr = (String) paramMap.get("auditCause");
		if (!Check.NuNStr(auditStr)){
			String[] auditArr = auditStr.split(",");
			for (String stat : auditArr){
				if (stat.equals(String.valueOf(HouseAuditCauseEnum.REJECT.HOUSE_QUALITY.getCode()))){
					saveHouseFollow(null,houseRoomMsg,paramMap);
				}
			}
		}
		// 保存房间操作日志
		houseCommonLogicDao.saveRoomOperateLogByBiz(houseRoomMsg, paramMap, toStatus);

		//级联更新床位状态
		houseCommonLogicDao.cascadingRoomStatus(houseRoomMsg, toStatus);

		/*修改前
		// 更新房间状态
		houseRoomMsg.setRoomStatus(toStatus);
		houseRoomMsg.setLastModifyDate(new Date());
		return houseRoomMsgDao.updateHouseRoomMsg(houseRoomMsg);*/

		//修改后     @Author:lusp  @Date:2017/8/10
		HouseRoomMsgEntity houseRoomMsgEntity = new HouseRoomMsgEntity();
		houseRoomMsgEntity.setFid(houseRoomMsg.getFid());
		houseRoomMsgEntity.setRoomStatus(toStatus);
		return houseRoomMsgDao.updateHouseRoomMsg(houseRoomMsgEntity);
	}

	/**
	 * 房源信息审核通过
	 *
	 * @author liujun
	 * @created 2016年4月7日 下午4:20:42
	 *
	 * @param houseBaseMsg
	 * @param paramMap 
	 * @return
	 */
	public int approveHouseInfo(HouseBaseMsgEntity houseBaseMsg, Map<String, Object> paramMap) {
		int toStatus = HouseStatusEnum.XXSHTG.getCode();
		return this.handleHouse(houseBaseMsg, paramMap, toStatus);
	}

	/**
	 * 房源信息审核未通过
	 *
	 * @author liujun
	 * @created 2016年4月7日 下午4:32:04
	 *
	 * @param houseBaseMsg
	 * @param paramMap 
	 * @return
	 */
	public int unApproveHouseInfo(HouseBaseMsgEntity houseBaseMsg, Map<String, Object> paramMap) {
		int toStatus = HouseStatusEnum.XXSHWTG.getCode();
		return this.handleHouse(houseBaseMsg, paramMap, toStatus);
	}

	/**
	 * 房间信息审核通过
	 *
	 * @author liujun
	 * @created 2016年4月7日 下午5:01:17
	 *
	 * @param houseRoomMsg
	 * @param paramMap
	 * @return
	 */
	public int approveRoomInfo(HouseRoomMsgEntity houseRoomMsg, Map<String, Object> paramMap) throws Exception{
		int toStatus = HouseStatusEnum.XXSHTG.getCode();
		return this.handleRoom(houseRoomMsg, paramMap, toStatus);
	}

	/**
	 * 房间信息审核未通过
	 *
	 * @author liujun
	 * @created 2016年4月7日 下午5:04:41
	 *
	 * @param houseRoomMsg
	 * @param paramMap 
	 * @return
	 */
	public int unApproveRoomInfo(HouseRoomMsgEntity houseRoomMsg, Map<String, Object> paramMap) {
		int toStatus = HouseStatusEnum.XXSHWTG.getCode();
		return this.handleRoom(houseRoomMsg, paramMap, toStatus);
	}

	/**
	 * @description: 组装审核通过信息，并保存到相应的表中
	 * @author: lusp
	 * @date: 2017/8/2 15:41
	 * @params: houseFieldAuditLogVoList,houseBaseMsg,houseRoomMsg
	 * @return:
	 */
	private HouseAuditVo packageAuditFieldList(List<HouseFieldAuditLogVo> houseFieldAuditLogVoList) throws Exception{
		HouseAuditVo houseAuditVo = new HouseAuditVo();
		for (HouseFieldAuditLogVo houseFieldAuditLogVo:houseFieldAuditLogVoList){
			String fieldPath = houseFieldAuditLogVo.getFieldPath();//com.ziroom.minsu.entity.house.HouseBaseMsgEntity.houseName
			String classFullName = fieldPath.substring(0,fieldPath.lastIndexOf(".")).trim();//com.ziroom.minsu.entity.house.HouseBaseMsgEntity
			String className = classFullName.substring(classFullName.lastIndexOf(".")+1);//HouseBaseMsgEntity
			String fieldName = fieldPath.substring(fieldPath.lastIndexOf(".")+1);//houseName
			String methodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);//setHouseName
			String houseAuditVoFieldName = className.substring(0,1).toLowerCase()+className.substring(1);//setHouseBaseMsgEntity
			String houseAuditVoMethodName = "get"+houseAuditVoFieldName.substring(0,1).toUpperCase()+houseAuditVoFieldName.substring(1);//getHouseBaseMsgEntity
			Class clazz = Class.forName(classFullName);//Class:HouseBaseMsgEntity
			Class houseAuditVoClazz = HouseAuditVo.class;//Class:HouseAuditVo
			if(!Check.NuNObj(clazz)){
				Field field = clazz.getDeclaredField(fieldName);//Class:houseName、、   String Double  ..
				Class<?> type = field.getType();//String
				Method method = clazz.getMethod(methodName,type);  //set(String houseName)
				Method houseAuditVoMethod = houseAuditVoClazz.getMethod(houseAuditVoMethodName); //getHouseBaseMsgEntity()
				method.invoke(houseAuditVoMethod.invoke(houseAuditVo),StringUtils.string2Object(houseFieldAuditLogVo.getNewValue(),type));
			}
		}
		return houseAuditVo;
	}

	/**
	 * @description: 判断对象中的属性是否全部为null
	 * @author: lusp
	 * @date: 2017/8/2 15:40
	 * @params:
	 * @return:
	 */
	private boolean CheckFieldIsNull(Object obj) throws Exception{
		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field:fields){
			if("serialVersionUID".equals(field.getName())){
				continue;
			}
			String getter = "get"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
			Method method = clazz.getMethod(getter);
			if(method.invoke(obj) != null){
				return false;
			}
		}
		return true;
	}

	/**
	 * 房源照片审核通过
	 *
	 * @author liujun
	 * @created 2016年4月7日 下午5:30:05
	 *
	 * @param houseBaseMsgEntity
	 * @param paramMap
	 * @return
	 */
	public int approveHousePic(HouseBaseMsgEntity houseBaseMsg, Map<String, Object> paramMap) throws Exception{

		/******把审核记录表中的待审核数据写入相应的表中，同时把该房源审核字段审核状态改为审核通过 @Author:lusp @Date:2017/8/2*******/
		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = new HouseUpdateFieldAuditNewlogEntity();
		houseUpdateFieldAuditNewlogEntity.setHouseFid(houseBaseMsg.getFid());
		houseUpdateFieldAuditNewlogEntity.setRentWay(RentWayEnum.HOUSE.getCode());
		houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(0);

		List<HouseFieldAuditLogVo> houseFieldAuditLogVoList = houseUpdateFieldAuditNewlogDao.findHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlogEntity);
		HouseAuditVo houseAuditVo = packageAuditFieldList(houseFieldAuditLogVoList);

		if(!CheckFieldIsNull(houseAuditVo.getHouseBaseMsgEntity())){
			houseAuditVo.getHouseBaseMsgEntity().setFid(houseBaseMsg.getFid());
			houseBaseMsgDao.updateHouseBaseMsg(houseAuditVo.getHouseBaseMsgEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHouseBaseExtEntity())){
			houseAuditVo.getHouseBaseExtEntity().setHouseBaseFid(houseBaseMsg.getFid());
			houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseAuditVo.getHouseBaseExtEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHouseDescEntity())){
			houseAuditVo.getHouseDescEntity().setHouseBaseFid(houseBaseMsg.getFid());
			houseDescDao.updateHouseDescByHouseBaseFid(houseAuditVo.getHouseDescEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHouseConfMsgEntity())){
			houseAuditVo.getHouseConfMsgEntity().setHouseBaseFid(houseBaseMsg.getFid());
			houseConfMsgDao.updateHouseConfMsgByHouseBaseFid(houseAuditVo.getHouseConfMsgEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHousePhyMsgEntity())){
			houseAuditVo.getHousePhyMsgEntity().setFid(houseBaseMsg.getPhyFid());
			housePhyMsgDao.updateHousePhyMsg(houseAuditVo.getHousePhyMsgEntity());
		}

		houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(1);
		houseUpdateFieldAuditNewlogDao.updateHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlogEntity);
		/******把审核记录表中的待审核数据写入相应的表中，同时把该房源审核字段审核状态改为审核通过*******/

		// 更新照片审核状态
		this.cascadingHousePicStatus(houseBaseMsg);

		int toStatus = HouseStatusEnum.SJ.getCode();
		return this.handleHouse(houseBaseMsg, paramMap, toStatus);
	}

	/**
	 * 级联更新房源照片状态
	 *
	 * @author liujun
	 * @created 2016年4月20日 下午6:08:57
	 *
	 * @param houseBaseMsg
	 */
	private void cascadingHousePicStatus(HouseBaseMsgEntity houseBaseMsg) {
		List<HousePicMsgEntity> picList = housePicMsgDao.findHousePicList(houseBaseMsg.getFid());
		for (HousePicMsgEntity housePicMsg : picList) {
			housePicMsg.setAuditStatus(HouseConstant.IS_TRUE);
			housePicMsg.setLastModifyDate(new Date());
			housePicMsgDao.updateHousePicMsg(housePicMsg);
		}
	}

	/**
	 * 房源照片审核未通过
	 *
	 * @author liujun
	 * @created 2016年4月7日 下午5:31:26
	 *
	 * @param houseBaseMsgEntity
	 * @param paramMap
	 * @return
	 * @throws Exception 
	 */
	public int unApproveHousePic(HouseBaseMsgEntity houseBaseMsg, Map<String, Object> paramMap) throws Exception {

		//		/***********把审核记录表中的待审核数据审核状态改为审核不通过***********/
		//		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = new HouseUpdateFieldAuditNewlogEntity();
		//		houseUpdateFieldAuditNewlogEntity.setHouseFid(houseBaseMsg.getFid());
		//		houseUpdateFieldAuditNewlogEntity.setRentWay(RentWayEnum.HOUSE.getCode());
		//		houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(2);
		//		houseUpdateFieldAuditNewlogDao.updateHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlogEntity);
		//		/***********把审核记录表中的待审核数据审核状态改为审核不通过***********/

		//修改审核未通过，把所有审核字段记录入库并改为审核通过 bushujie按产品要求修改20171018

		/******把审核记录表中的待审核数据写入相应的表中，同时把该房源审核字段审核状态改为审核通过 @Author:lusp @Date:2017/8/2*******/
		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = new HouseUpdateFieldAuditNewlogEntity();
		houseUpdateFieldAuditNewlogEntity.setHouseFid(houseBaseMsg.getFid());
		houseUpdateFieldAuditNewlogEntity.setRentWay(RentWayEnum.HOUSE.getCode());
		houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(0);

		List<HouseFieldAuditLogVo> houseFieldAuditLogVoList = houseUpdateFieldAuditNewlogDao.findHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlogEntity);
		HouseAuditVo houseAuditVo = packageAuditFieldList(houseFieldAuditLogVoList);

		if(!CheckFieldIsNull(houseAuditVo.getHouseBaseMsgEntity())){
			houseAuditVo.getHouseBaseMsgEntity().setFid(houseBaseMsg.getFid());
			houseBaseMsgDao.updateHouseBaseMsg(houseAuditVo.getHouseBaseMsgEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHouseBaseExtEntity())){
			houseAuditVo.getHouseBaseExtEntity().setHouseBaseFid(houseBaseMsg.getFid());
			houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseAuditVo.getHouseBaseExtEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHouseDescEntity())){
			houseAuditVo.getHouseDescEntity().setHouseBaseFid(houseBaseMsg.getFid());
			houseDescDao.updateHouseDescByHouseBaseFid(houseAuditVo.getHouseDescEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHouseConfMsgEntity())){
			houseAuditVo.getHouseConfMsgEntity().setHouseBaseFid(houseBaseMsg.getFid());
			houseConfMsgDao.updateHouseConfMsgByHouseBaseFid(houseAuditVo.getHouseConfMsgEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHousePhyMsgEntity())){
			houseAuditVo.getHousePhyMsgEntity().setFid(houseBaseMsg.getPhyFid());
			housePhyMsgDao.updateHousePhyMsg(houseAuditVo.getHousePhyMsgEntity());
		}

		houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(1);
		houseUpdateFieldAuditNewlogDao.updateHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlogEntity);
		/******把审核记录表中的待审核数据写入相应的表中，同时把该房源审核字段审核状态改为审核通过*******/


		int toStatus = HouseStatusEnum.ZPSHWTG.getCode();
		return this.handleHouse(houseBaseMsg, paramMap, toStatus);
	}

	/**
	 * 房间照片审核通过
	 *
	 * @author liujun
	 * @created 2016年4月7日 下午5:33:03
	 *
	 * @param houseRoomMsg
	 * @param paramMap
	 * @return
	 */
	public int approveRoomPic(HouseRoomMsgEntity houseRoomMsg, Map<String, Object> paramMap) throws Exception{

		/******把审核记录表中的待审核数据写入相应的表中，同时把该房源审核字段审核状态改为审核通过 @Author:lusp @Date:2017/8/2*******/
		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = new HouseUpdateFieldAuditNewlogEntity();
		houseUpdateFieldAuditNewlogEntity.setHouseFid(houseRoomMsg.getHouseBaseFid());
		houseUpdateFieldAuditNewlogEntity.setRentWay(RentWayEnum.ROOM.getCode());
		houseUpdateFieldAuditNewlogEntity.setRoomFid(houseRoomMsg.getFid());
		houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(0);

		List<HouseFieldAuditLogVo> houseFieldAuditLogVoList = houseUpdateFieldAuditNewlogDao.findHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlogEntity);
		HouseAuditVo houseAuditVo = packageAuditFieldList(houseFieldAuditLogVoList);

		if(!CheckFieldIsNull(houseAuditVo.getHouseBaseMsgEntity())){
			houseAuditVo.getHouseBaseMsgEntity().setFid(houseRoomMsg.getHouseBaseFid());
			houseBaseMsgDao.updateHouseBaseMsg(houseAuditVo.getHouseBaseMsgEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHouseBaseExtEntity())){
			houseAuditVo.getHouseBaseExtEntity().setHouseBaseFid(houseRoomMsg.getHouseBaseFid());
			houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseAuditVo.getHouseBaseExtEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHouseDescEntity())){
			houseAuditVo.getHouseDescEntity().setHouseBaseFid(houseRoomMsg.getHouseBaseFid());
			houseDescDao.updateHouseDescByHouseBaseFid(houseAuditVo.getHouseDescEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHouseConfMsgEntity())){
			houseAuditVo.getHouseConfMsgEntity().setHouseBaseFid(houseRoomMsg.getHouseBaseFid());
			houseConfMsgDao.updateHouseConfMsgByHouseBaseFid(houseAuditVo.getHouseConfMsgEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHouseRoomMsgEntity())){
			houseAuditVo.getHouseRoomMsgEntity().setFid(houseRoomMsg.getFid());
			houseRoomMsgDao.updateHouseRoomMsg(houseAuditVo.getHouseRoomMsgEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHouseRoomExtEntity())){
			houseAuditVo.getHouseRoomExtEntity().setRoomFid(houseRoomMsg.getFid());
			houseRoomExtDao.updateByRoomfid(houseAuditVo.getHouseRoomExtEntity());
		}

		houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(1);
		houseUpdateFieldAuditNewlogDao.updateHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlogEntity);
		/******把审核记录表中的待审核数据写入相应的表中，同时把该房源审核字段审核状态改为审核通过*******/

		// 更新照片审核状态
		this.cascadingRoomPicStatus(houseRoomMsg);

		int toStatus = HouseStatusEnum.SJ.getCode();
		return this.handleRoom(houseRoomMsg, paramMap, toStatus);
	}

	/**
	 * 级联更新房间照片状态
	 *
	 * @author liujun
	 * @created 2016年4月20日 下午6:31:33
	 *
	 * @param houseRoomMsg
	 */
	private void cascadingRoomPicStatus(HouseRoomMsgEntity houseRoomMsg) {
		//查询房间和公共区域图片并审核通过
		List<HousePicMsgEntity> picList = housePicMsgDao.findHousePicListByRoomFid(houseRoomMsg.getFid(), houseRoomMsg.getHouseBaseFid());
		for (HousePicMsgEntity housePicMsg : picList) {
			housePicMsg.setAuditStatus(HouseConstant.IS_TRUE);
			housePicMsg.setLastModifyDate(new Date());
			housePicMsgDao.updateHousePicMsg(housePicMsg);
		}
	}

	/**
	 * 房间照片审核未通过
	 *
	 * @author liujun
	 * @created 2016年4月7日 下午5:34:04
	 *
	 * @param houseRoomMsg
	 * @param paramMap
	 * @return
	 * @throws Exception 
	 */
	public int unApproveRoomPic(HouseRoomMsgEntity houseRoomMsg, Map<String, Object> paramMap) throws Exception {

		//		/***********把审核记录表中的待审核数据审核状态改为审核不通过***********/
		//		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = new HouseUpdateFieldAuditNewlogEntity();
		//		houseUpdateFieldAuditNewlogEntity.setHouseFid(houseRoomMsg.getHouseBaseFid());
		//		houseUpdateFieldAuditNewlogEntity.setRentWay(RentWayEnum.ROOM.getCode());
		//		houseUpdateFieldAuditNewlogEntity.setRoomFid(houseRoomMsg.getFid());
		//		houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(2);
		//		houseUpdateFieldAuditNewlogDao.updateHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlogEntity);
		//		/***********把审核记录表中的待审核数据审核状态改为审核不通过***********/

		//修改审核未通过，把所有审核字段记录入库并改为审核通过 bushujie按产品要求修改20171018
		/******把审核记录表中的待审核数据写入相应的表中，同时把该房源审核字段审核状态改为审核通过 @Author:lusp @Date:2017/8/2*******/
		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = new HouseUpdateFieldAuditNewlogEntity();
		houseUpdateFieldAuditNewlogEntity.setHouseFid(houseRoomMsg.getHouseBaseFid());
		houseUpdateFieldAuditNewlogEntity.setRentWay(RentWayEnum.ROOM.getCode());
		houseUpdateFieldAuditNewlogEntity.setRoomFid(houseRoomMsg.getFid());
		houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(0);

		List<HouseFieldAuditLogVo> houseFieldAuditLogVoList = houseUpdateFieldAuditNewlogDao.findHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlogEntity);
		HouseAuditVo houseAuditVo = packageAuditFieldList(houseFieldAuditLogVoList);

		if(!CheckFieldIsNull(houseAuditVo.getHouseBaseMsgEntity())){
			houseAuditVo.getHouseBaseMsgEntity().setFid(houseRoomMsg.getHouseBaseFid());
			houseBaseMsgDao.updateHouseBaseMsg(houseAuditVo.getHouseBaseMsgEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHouseBaseExtEntity())){
			houseAuditVo.getHouseBaseExtEntity().setHouseBaseFid(houseRoomMsg.getHouseBaseFid());
			houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseAuditVo.getHouseBaseExtEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHouseDescEntity())){
			houseAuditVo.getHouseDescEntity().setHouseBaseFid(houseRoomMsg.getHouseBaseFid());
			houseDescDao.updateHouseDescByHouseBaseFid(houseAuditVo.getHouseDescEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHouseConfMsgEntity())){
			houseAuditVo.getHouseConfMsgEntity().setHouseBaseFid(houseRoomMsg.getHouseBaseFid());
			houseConfMsgDao.updateHouseConfMsgByHouseBaseFid(houseAuditVo.getHouseConfMsgEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHouseRoomMsgEntity())){
			houseAuditVo.getHouseRoomMsgEntity().setFid(houseRoomMsg.getFid());
			houseRoomMsgDao.updateHouseRoomMsg(houseAuditVo.getHouseRoomMsgEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHouseRoomExtEntity())){
			houseAuditVo.getHouseRoomExtEntity().setRoomFid(houseRoomMsg.getFid());
			houseRoomExtDao.updateByRoomfid(houseAuditVo.getHouseRoomExtEntity());
		}

		houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(1);
		houseUpdateFieldAuditNewlogDao.updateHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlogEntity);
		/******把审核记录表中的待审核数据写入相应的表中，同时把该房源审核字段审核状态改为审核通过*******/


		int toStatus = HouseStatusEnum.ZPSHWTG.getCode();
		return this.handleRoom(houseRoomMsg, paramMap, toStatus);
	}

	/**
	 * 根据房源逻辑id查询房源详情
	 *
	 * @author liujun
	 * @created 2016年4月7日 下午8:37:01
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HouseMsgVo findHouseDetailByFid(String houseBaseFid) {
		return houseBaseMsgDao.findHouseDetailByFid(houseBaseFid);
	}

	/**
	 * 根据房间逻辑id查询房间详情
	 *
	 * @author liujun
	 * @created 2016年4月7日 下午11:17:12
	 *
	 * @param houseRoomFid
	 * @return
	 */
	public HouseMsgVo findRoomDetailByFid(String houseRoomFid) {
		HouseMsgVo houseMsgVo=houseRoomMsgDao.findRoomDetailByFid(houseRoomFid);
		if(!Check.NuNObj(houseMsgVo)&&!Check.NuNCollection(houseMsgVo.getRoomList())){
			for(RoomMsgVo vo:houseMsgVo.getRoomList()){
				vo.setRoomExtEntity(houseRoomExtDao.getByRoomfid(vo.getFid()));
			}
		}
		return houseMsgVo;
	}

	/**
	 * 查询房源操作日志
	 *
	 * @author liujun
	 * @created 2016年4月8日 下午2:03:50
	 *
	 * @param houseBaseFid
	 * @param fromList
	 * @return
	 */
	public PagingResult<HouseOperateLogEntity> findHouseOperateLogList(HouseOpLogDto houseOpLogDto) {
		return houseOperateLogDao.findHouseOperateLogList(houseOpLogDto);
	}

	/**
	 * 查询房源操作日志
	 *
	 * @author liujun
	 * @created 2016年4月8日 下午2:03:50
	 *
	 * @param houseBaseFid
	 * @param fromList
	 * @return
	 */
	public PagingResult<HouseOperateLogEntity> findRoomOperateLogList(HouseOpLogDto houseOpLogDto) {
		return houseOperateLogDao.findRoomOperateLogList(houseOpLogDto);
	}

	/**
	 * 
	 * 更新房源对应楼盘信息
	 *
	 * @author bushujie
	 * @created 2016年4月12日 下午3:46:24
	 *
	 * @param newPhyFid
	 * @param oldPhyFid
	 */
	public void updateHouseBasePhyFid(String newPhyFid,String oldPhyFid ){
		HousePhyMsgEntity housePhyMsgEntity=new HousePhyMsgEntity();
		housePhyMsgEntity.setFid(oldPhyFid);
		housePhyMsgEntity.setIsDel(1);
		housePhyMsgDao.updateHousePhyMsg(housePhyMsgEntity);
		houseBaseMsgDao.updateHousePhyFid(newPhyFid, oldPhyFid);
	}
	/**
	 * 
	 *  查询楼盘信息列表
	 *
	 * @author bushujie
	 * @created 2016年4月12日 下午3:52:57
	 *
	 * @param housePhyMsgEntity
	 * @return
	 */
	public  PagingResult<HousePhyMsgEntity> findHousePhyMsgListByCondition(HousePhyListDto housePhyListDto){
		return housePhyMsgDao.findHousePhyMsgListByCondition(housePhyListDto);
	}

	/**
	 * 
	 * 更新楼盘信息
	 *
	 * @author bushujie
	 * @created 2016年4月12日 下午4:17:12
	 *
	 * @param housePhyMsgEntity
	 * @return
	 */
	public int updateHousePhyMsg(HousePhyMsgEntity housePhyMsgEntity){
		return housePhyMsgDao.updateHousePhyMsg(housePhyMsgEntity);
	}

	/**
	 * 根据房源基础信息逻辑id与枚举code查询房源配置集合
	 *
	 * @author liujun
	 * @created 2016年4月13日 上午11:17:28
	 *
	 * @param houseBaseFid
	 * @param enumCode
	 * @return
	 */
	@Deprecated
	public List<String> findHouseConfListByFidAndCode(String houseBaseFid, String enumCode) {
		return houseConfMsgDao.findHouseConfListByFidAndCode(houseBaseFid, enumCode);
	}

	/**
	 * 
	 * 房源查询审核图片详情
	 *
	 * @author bushujie
	 * @created 2016年4月14日 上午12:57:04
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HousePicAuditVo findHousePicAuditVoByHouseBaseFid(String houseBaseFid,String roomFid){
		//查询房源图片详情
		HousePicAuditVo housePicAuditVo=houseBaseMsgDao.findHousePicAuditVo(houseBaseFid);
		housePicAuditVo.setRoomPicList(houseRoomMsgDao.findRoomPicVoList(houseBaseFid,roomFid));
		/**
		 * yanb修改
		 * 加入共享客厅验证 roomType默认值为0,若为1就是共享客厅
		 * 若为共享客厅,将roomPicList清空
		 */
		Integer roomType = houseIssueServiceImpl.isHall(houseBaseFid);
		if (roomType != null && roomType == RoomTypeEnum.HALL_TYPE.getCode()) {
			housePicAuditVo.setRoomPicList(Collections.EMPTY_LIST);
		}

		Map<Integer, String> housePicTypeMap=HousePicTypeEnum.getEnumMap();
		//循环放入类型图片集合
		for(Integer key:housePicTypeMap.keySet()){
			if(key==HousePicTypeEnum.KT.getCode()&&housePicAuditVo.getHallNum()==0){
				continue;
			}
			if(key==HousePicTypeEnum.WSJ.getCode()&&housePicAuditVo.getToiletNum()==0){
				continue;
			}
			if(key==HousePicTypeEnum.CF.getCode()&&housePicAuditVo.getKitchenNum()==0){
				continue;
			}
			if(key!=HousePicTypeEnum.WS.getCode()){
				HousePicVo picVo=new HousePicVo();
				picVo.setPicType(key);
				picVo.setPicTypeName(housePicTypeMap.get(key));
				picVo.setPicMaxNum(HousePicTypeEnum.getEnumByCode(key).getMax());
				picVo.setPicMinNum(HousePicTypeEnum.getEnumByCode(key).getMin());
				picVo.setPicList(housePicMsgDao.getHousePicByType(houseBaseFid, key));
				/**
				 * yanb修改 加入共享客厅验证
				 * 若为共享客厅,则把roomName赋值给picTypeName
				 */
				if (key == HousePicTypeEnum.KT.getCode() && roomType != null && roomType == RoomTypeEnum.HALL_TYPE.getCode()) {
					String roomName = houseRoomMsgDao.getRoomNameByHouseBaseFid(houseBaseFid);
					picVo.setPicTypeName(roomName);
				}
				housePicAuditVo.getHousePicList().add(picVo);
			}
		}

		return housePicAuditVo;
	}

	/**
	 * 
	 * 房源图片审核列表(troy专用！！！！！)
	 *
	 * @author zl
	 * @created 2017年7月10日 下午2:30:15
	 *
	 * @param houseBaseFid
	 * @param roomFid
	 * @return
	 */
	public HousePicAuditVo findHousePicAuditVoForTroy(String houseBaseFid,String roomFid){
		//查询房源图片详情
		HousePicAuditVo housePicAuditVo=houseBaseMsgDao.findHousePicAuditVo(houseBaseFid);
		housePicAuditVo.setRoomPicList(houseRoomMsgDao.findRoomPicVoList(houseBaseFid,roomFid));
		Map<Integer, String> housePicTypeMap=HousePicTypeEnum.getEnumMap();
		//循环放入类型图片集合
		for(Integer key:housePicTypeMap.keySet()){
			if(key!=HousePicTypeEnum.WS.getCode()){
				HousePicVo picVo=new HousePicVo();
				picVo.setPicType(key);
				picVo.setPicTypeName(housePicTypeMap.get(key));
				picVo.setPicMaxNum(HousePicTypeEnum.getEnumByCode(key).getMax());
				picVo.setPicMinNum(HousePicTypeEnum.getEnumByCode(key).getMin());
				picVo.setPicList(housePicMsgDao.getHousePicByType(houseBaseFid, key));
				housePicAuditVo.getHousePicList().add(picVo);
			}
		}
		return housePicAuditVo;
	}


	/**
	 * 查询照片修改后审核未通过上架房源列表
	 *
	 * @author liujun
	 * @param houseRequest 
	 * @created 2016年4月14日 上午12:51:55
	 *
	 * @return
	 */
	public PagingResult<HouseResultVo> findPicUnapproveedHouseList(HouseRequestDto houseRequest) {
		if(houseRequest.getRoleType()>0){
			return houseBaseMsgDao.findSpecialPicUnapproveedHouseList(houseRequest);
		}
		return houseBaseMsgDao.findPicUnapproveedHouseList(houseRequest);
	}

	/**
	 * 查询照片修改后审核未通过上架房间列表
	 *
	 * @author liujun
	 * @created 2016年4月14日 上午11:45:49
	 *
	 * @param houseRequest
	 * @return
	 */
	public PagingResult<HouseResultVo> findPicUnapproveedRoomList(HouseRequestDto houseRequest) {
		if(houseRequest.getRoleType()>0){
			return houseBaseMsgDao.findSpecialPicUnapproveedRoomList(houseRequest);
		}
		return houseBaseMsgDao.findPicUnapproveedRoomList(houseRequest);
	}

	/**
	 * 房源照片修改审核通过
	 *
	 * @author liujun
	 * @created 2016年4月15日 上午10:16:58
	 *
	 * @param houseFid
	 * @param operaterFid
	 * @param remark
	 * @return
	 */
	public int approveHouseModifiedPic(String houseFid, String operaterFid, String remark) {
		HouseOperateLogEntity houseOperateLogEntity = new HouseOperateLogEntity();
		houseOperateLogEntity.setFid(UUIDGenerator.hexUUID());
		houseOperateLogEntity.setHouseBaseFid(houseFid);
		houseOperateLogEntity.setFromStatus(HouseConstant.IS_NOT_TRUE);
		houseOperateLogEntity.setToStatus(HouseConstant.IS_TRUE);
		houseOperateLogEntity.setRemark(remark);
		houseOperateLogEntity.setCreateFid(operaterFid);
		houseOperateLogEntity.setCreateDate(new Date());
		houseOperateLogDao.insertHouseOperateLog(houseOperateLogEntity);

		int upNum = 0;
		List<HousePicMsgEntity> picList = housePicMsgDao.findHouseUnapproveedPicList(houseFid);
		for (HousePicMsgEntity housePicMsg : picList) {
			// 更新照片状态
			housePicMsg.setAuditStatus(HouseConstant.IS_TRUE);
			housePicMsg.setLastModifyDate(new Date());
			int upLine = housePicMsgDao.updateHousePicMsg(housePicMsg);
			upNum += upLine;
		}
		return upNum;
	}

	/**
	 * 房间照片修改审核通过
	 *
	 * @author liujun
	 * @created 2016年4月15日 上午10:16:58
	 *
	 * @param houseFid
	 * @param operaterFid
	 * @param remark
	 * @return
	 */
	public int approveRoomModifiedPic(String houseFid, String operaterFid, String remark) {
		HouseOperateLogEntity houseOperateLogEntity = new HouseOperateLogEntity();
		houseOperateLogEntity.setFid(UUIDGenerator.hexUUID());
		houseOperateLogEntity.setRoomFid(houseFid);
		houseOperateLogEntity.setFromStatus(HouseConstant.IS_NOT_TRUE);
		houseOperateLogEntity.setToStatus(HouseConstant.IS_TRUE);
		houseOperateLogEntity.setRemark(remark);
		houseOperateLogEntity.setCreateFid(operaterFid);
		houseOperateLogEntity.setCreateDate(new Date());
		houseOperateLogDao.insertHouseOperateLog(houseOperateLogEntity);

		int upNum = 0;
		List<HousePicMsgEntity> picList = housePicMsgDao.findRoomUnapproveedPicList(houseFid);
		for (HousePicMsgEntity housePicMsg : picList) {
			// 更新照片状态
			housePicMsg.setAuditStatus(1);
			housePicMsg.setLastModifyDate(new Date());
			int upLine = housePicMsgDao.updateHousePicMsg(housePicMsg);
			upNum += upLine;
		}
		return upNum;
	}

	/**
	 * 更新房源基本信息
	 *
	 * @author liujun
	 * @created 2016年4月25日 下午8:37:00
	 *
	 * @param houseBaseMsg
	 */
	public int updateHouseBaseMsg(HouseBaseMsgEntity houseBaseMsg) {
		return houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);
	}

	/**
	 * 更新房源房间信息
	 *
	 * @author liujun
	 * @created 2016年4月25日 下午8:41:47
	 *
	 * @param houseRoomMsg
	 * @return
	 */
	public int updateHouseRoomMsg(HouseRoomMsgEntity houseRoomMsg) {
		return houseRoomMsgDao.updateHouseRoomMsg(houseRoomMsg);
	}

	/**
	 * 
	 * 录入房源信息
	 *
	 * @author bushujie
	 * @created 2016年5月4日 下午11:14:44
	 *
	 * @param houseInputDto
	 * @throws ParseException 
	 */
	public String houseInput(HouseInputDto houseInputDto) throws ParseException{
		//保存物理信息地址
		HousePhyMsgEntity housePhyMsgEntity=houseInputDto.getHousePhy();
		housePhyMsgEntity.setFid(UUIDGenerator.hexUUID());
		housePhyMsgEntity.setBuildingCode(DateUtil.dateFormat(new Date(), "yyyyMMddHHmmss"));

		GoogleBaiduCoordinateEnum.HousePhyMsgEntity.googleBaiduCoordinateTranfor(housePhyMsgEntity,housePhyMsgEntity.getGoogleLatitude(),housePhyMsgEntity.getGoogleLongitude(),houseInputDto.getMapType());
		housePhyMsgDao.insertHousePhyMsg(housePhyMsgEntity);
		//保存房源基础信息
		HouseBaseMsgEntity houseBaseMsgEntity=houseInputDto.getHouseBase();
		houseBaseMsgEntity.setFid(UUIDGenerator.hexUUID());
		houseBaseMsgEntity.setPhyFid(housePhyMsgEntity.getFid());
		houseBaseMsgEntity.setHouseStatus(HouseStatusEnum.YFB.getCode());
		houseBaseMsgEntity.setTillDate(DateUtil.parseDate(SysConst.House.TILL_DATE, "yyyy-MM-dd"));
		//房源价格转换
		if(!Check.NuNObj(houseBaseMsgEntity.getLeasePrice())){
			BigDecimal priceBig=new BigDecimal(houseBaseMsgEntity.getLeasePrice().toString());
			houseBaseMsgEntity.setLeasePrice(priceBig.multiply(new BigDecimal("100")).intValue());
		} else {
			houseBaseMsgEntity.setLeasePrice(9999);
		}
		String houseSn = HouseUtils.getHouseOrRoomSn(housePhyMsgEntity.getNationCode(), housePhyMsgEntity.getCityCode(),RentWayEnum.HOUSE.getCode(), null);
		if(!Check.NuNStr(houseSn)){
			int i = 0;
			while (i<3) {
				Long count = 	this.houseBaseMsgDao.countByHouseSn(houseSn);
				if(count>0){
					i++;
					houseSn = HouseUtils.getHouseOrRoomSn(housePhyMsgEntity.getNationCode(), housePhyMsgEntity.getCityCode(),RentWayEnum.HOUSE.getCode(), null);
					continue;
				}
				break;
			}
		}
		houseBaseMsgEntity.setHouseSn(houseSn);
		houseBaseMsgEntity.setOperateSeq(HouseIssueStepEnum.SEVEN.getCode());
		houseBaseMsgEntity.setIntactRate(HouseIssueStepEnum.SEVEN.getValue());
		houseBaseMsgDao.insertHouseBaseMsg(houseBaseMsgEntity);

		//灵活定价,折扣设置-房源
		SpecialPriceDto sp = houseInputDto.getSp();
		List<HouseConfMsgEntity> gapPriceList = houseInputDto.getGapPriceList();
		if (HouseConstant.HOUSE_PRICE_SWITCH && houseBaseMsgEntity.getRentWay().intValue() == RentWayEnum.HOUSE.getCode()) {
			this.savePriceSet(sp, gapPriceList, houseBaseMsgEntity.getFid(), null);
		}

		int toStatus = HouseStatusEnum.YFB.getCode();

		if (Check.NuNObj(houseBaseMsgEntity.getRentWay()) || houseBaseMsgEntity.getRentWay().intValue() == RentWayEnum.HOUSE.getCode()) {
			// 整套出租保存房源操作日志
			houseBaseMsgEntity.setHouseStatus(HouseStatusEnum.DFB.getCode());
			houseCommonLogicDao.saveHouseOperateLogByLandlord(houseBaseMsgEntity, toStatus);
		}

		//保存房源维护管家
		HouseGuardRelEntity houseGuardRelEntity = houseInputDto.getHouseGuardRel();
		if(!Check.NuNObj(houseGuardRelEntity)){
			houseGuardRelEntity.setHouseFid(houseBaseMsgEntity.getFid());
			this.houseGuardRelDao.insertHouseGuardRel(houseGuardRelEntity);
		}

		//保存房源描述信息
		HouseDescEntity houseDescEntity=houseInputDto.getHouseDesc();
		houseDescEntity.setFid(UUIDGenerator.hexUUID());
		houseDescEntity.setHouseBaseFid(houseBaseMsgEntity.getFid());
		houseDescDao.insertHouseDesc(houseDescEntity);
		//保存房源扩展信息
		HouseBaseExtEntity houseBaseExtEntity=houseInputDto.getHouseExt();
		houseBaseExtEntity.setFid(UUIDGenerator.hexUUID());
		houseBaseExtEntity.setHouseBaseFid(houseBaseMsgEntity.getFid());
		houseBaseExtEntity.setFacilityCode(ProductRulesEnum.ProductRulesEnum002.getValue());
		houseBaseExtEntity.setServiceCode(ProductRulesEnum.ProductRulesEnum0015.getValue());
		houseBaseExtEntity.setDiscountRulesCode(ProductRulesEnum.ProductRulesEnum0012.getValue());
		houseBaseExtDao.insertHouseBaseExt(houseBaseExtEntity);
		//保存配套设施
		List<String> facilityList=houseInputDto.getFacilityList();
		if(!Check.NuNCollection(facilityList)){
			for (String fa : facilityList) {
				HouseConfMsgEntity confMsgEntity=new HouseConfMsgEntity();
				confMsgEntity.setFid(UUIDGenerator.hexUUID());
				confMsgEntity.setHouseBaseFid(houseBaseMsgEntity.getFid());
				confMsgEntity.setDicCode(fa.split("-")[0]);
				confMsgEntity.setDicVal(fa.split("-")[1]);
				houseConfMsgDao.insertHouseConfMsg(confMsgEntity);
			}
		}

		//保存服务
		List<String> serviceList=houseInputDto.getServiceList();
		if(!Check.NuNCollection(serviceList)){
			for (String service : serviceList) {
				HouseConfMsgEntity confMsgEntity=new HouseConfMsgEntity();
				confMsgEntity.setFid(UUIDGenerator.hexUUID());
				confMsgEntity.setHouseBaseFid(houseBaseMsgEntity.getFid());
				confMsgEntity.setDicCode(ProductRulesEnum.ProductRulesEnum0015.getValue());
				confMsgEntity.setDicVal(service);
				houseConfMsgDao.insertHouseConfMsg(confMsgEntity);
			}
		}

		//保存优惠规则
		List<String> discountList=houseInputDto.getDiscountList();
		if(!Check.NuNCollection(discountList)){
			for (String dis : discountList) {
				HouseConfMsgEntity confMsgEntity=new HouseConfMsgEntity();
				confMsgEntity.setFid(UUIDGenerator.hexUUID());
				confMsgEntity.setHouseBaseFid(houseBaseMsgEntity.getFid());
				confMsgEntity.setDicCode(dis.split("-")[0]);
				confMsgEntity.setDicVal(dis.split("-")[1]);
				houseConfMsgDao.insertHouseConfMsg(confMsgEntity);
			}
		}

		//保存押金规则
		List<String> depositList=houseInputDto.getDepositList();
		for (String dep : depositList) {
			if(houseBaseExtEntity.getDepositRulesCode().equals(dep.split("-")[0])){
				HouseConfMsgEntity confMsgEntity=new HouseConfMsgEntity();
				confMsgEntity.setFid(UUIDGenerator.hexUUID());
				confMsgEntity.setHouseBaseFid(houseBaseMsgEntity.getFid());
				confMsgEntity.setDicCode(dep.split("-")[0]);
				confMsgEntity.setDicVal(dep.split("-")[1]);
				houseConfMsgDao.insertHouseConfMsg(confMsgEntity);
				break;
			}
		}
		//		//保存退订政策值    不保存退订政策的值
		//		for(String unreg:houseInputDto.getUnregPolicy()){
		//			HouseConfMsgEntity confMsgEntity=new HouseConfMsgEntity();
		//			confMsgEntity.setFid(UUIDGenerator.hexUUID());
		//			confMsgEntity.setHouseBaseFid(houseBaseMsgEntity.getFid());
		//			confMsgEntity.setDicCode(unreg.split("-")[0]);
		//			confMsgEntity.setDicVal(unreg.split("-")[1]);
		//			houseConfMsgDao.insertHouseConfMsg(confMsgEntity);
		//		}
		//保存房间
		List<String> roomName=houseInputDto.getRoomName();
		List<String> roomFid=houseInputDto.getRoomFid();
		List<SpecialPriceDto> spList = houseInputDto.getSpList();
		for (int i = 0; i < roomName.size(); i++) {
			HouseRoomMsgEntity room= new HouseRoomMsgEntity();
			room.setRoomName(roomName.get(i));
			room.setRoomArea(Double.valueOf(houseInputDto.getRoomArea().get(i)));

			String roomSn = HouseUtils.getHouseOrRoomSn(housePhyMsgEntity.getNationCode(), housePhyMsgEntity.getCityCode(),RentWayEnum.ROOM.getCode(), null);
			if(!Check.NuNStr(roomSn)){
				int j=0;
				while (j<3) {
					Long count = 	this.houseBaseMsgDao.countByHouseSn(roomSn);
					if(count>0){
						j++;
						roomSn = HouseUtils.getHouseOrRoomSn(housePhyMsgEntity.getNationCode(), housePhyMsgEntity.getCityCode(),RentWayEnum.ROOM.getCode(), null);
						continue;
					}
					break;
				}
			}
			room.setRoomSn(roomSn);
			//房间价格转换
			if(!Check.NuNStr(houseInputDto.getRoomPrice().get(i))){
				BigDecimal priceBig=new BigDecimal(houseInputDto.getRoomPrice().get(i));
				room.setRoomPrice(priceBig.multiply(new BigDecimal("100")).intValue());
			} else {
				room.setRoomPrice(9999);
			}
			room.setCheckInLimit(houseInputDto.getRoomLimit().get(i));
			room.setIsToilet(houseInputDto.getIsToilet().get(i));
			room.setFid(UUIDGenerator.hexUUID());
			room.setHouseBaseFid(houseBaseMsgEntity.getFid());
			room.setRoomStatus(HouseStatusEnum.YFB.getCode());
			//如果整租有一个默认房间，如果合租都是默认房间
			if(houseInputDto.getHouseBase().getRentWay()==RentWayEnum.HOUSE.getCode()){
				if(i==0){
					room.setIsDefault(1);
				}
			} else if(houseInputDto.getHouseBase().getRentWay()==RentWayEnum.ROOM.getCode()){
				room.setIsDefault(1);
			}
			//添加床铺
			Integer bedSn=101;
			Integer bedNum=0;
			for (int j=0; j<houseInputDto.getBedType().size();j++) {
				if(roomFid.get(i).equals(houseInputDto.getBedType().get(j).split("-")[0])){
					HouseBedMsgEntity bed=new HouseBedMsgEntity();
					bed.setBedType(Integer.valueOf(houseInputDto.getBedType().get(j).split("-")[1]));
					bed.setBedSize(Integer.valueOf(houseInputDto.getBedSize().get(j).split("-")[1]));
					bed.setFid(UUIDGenerator.hexUUID());
					bed.setHouseBaseFid(houseBaseMsgEntity.getFid());
					bed.setRoomFid(room.getFid());
					bed.setBedSn(bedSn);
					bed.setBedStatus(HouseStatusEnum.YFB.getCode());
					houseBedMsgDao.insertHouseBedMsg(bed);
					bedSn++;
					bedNum++;
				}
			}
			room.setBedNum(bedNum);
			houseRoomMsgDao.insertHouseRoomMsg(room);

			//灵活定价,折扣设置-房间
			if (HouseConstant.HOUSE_PRICE_SWITCH && houseBaseMsgEntity.getRentWay().intValue() == RentWayEnum.ROOM.getCode()) {
				this.savePriceSet(spList.get(i), gapPriceList, houseBaseMsgEntity.getFid(), room.getFid());
			}

			if (!Check.NuNObj(houseBaseMsgEntity.getRentWay()) && houseBaseMsgEntity.getRentWay().intValue() == RentWayEnum.ROOM.getCode()) {
				//独立房间保存房间操作日志
				room.setRoomStatus(HouseStatusEnum.DFB.getCode());
				houseCommonLogicDao.saveRoomOperateLogByLandlord(room, toStatus, houseBaseMsgEntity.getLandlordUid());
			}
		}
		return houseBaseMsgEntity.getFid();
	}

	/**
	 * 灵活定价,折扣设置
	 *
	 * @author liujun
	 * @created 2016年12月28日
	 *
	 * @param sp
	 * @param gapPriceList
	 * @param houseBaseFid
	 * @param houseRoomFid
	 */
	private void savePriceSet(SpecialPriceDto sp, List<HouseConfMsgEntity> gapPriceList, String houseBaseFid, String houseRoomFid) {
		// 灵活定价开关判断(0：关闭 1：打开)
		Integer gapFlexIsDel = IsDelEnum.PRIORITY.getCode();// 默认开关灵活定价以及间隙价格开关是关闭的
		if (sp.getGapAndFlexiblePrice() == YesOrNoEnum.YES.getCode()) {
			gapFlexIsDel = IsDelEnum.NOT_DEL.getCode();
		}

		for (HouseConfMsgEntity priceConf : gapPriceList) {
			HouseConfMsgEntity confEntity = new HouseConfMsgEntity();
			confEntity.setFid(UUIDGenerator.hexUUID());
			confEntity.setHouseBaseFid(houseBaseFid);
			confEntity.setRoomFid(houseRoomFid);
			confEntity.setDicCode(priceConf.getDicCode());
			confEntity.setDicVal(priceConf.getDicVal());
			confEntity.setIsDel(gapFlexIsDel);
			houseConfMsgDao.insertHouseConfMsg(confEntity);
		}

		// 折扣设置
		Integer fullDayIsDel = IsDelEnum.PRIORITY.getCode();// 默认满天折扣率的开关也是关闭的
		if (sp.getFullDayRate() == YesOrNoEnum.YES.getCode()) {
			fullDayIsDel = IsDelEnum.NOT_DEL.getCode();// 用户打开了开关
		}

		// 满七天的判断处理
		HouseConfMsgEntity sevenEntity = new HouseConfMsgEntity();
		sevenEntity.setFid(UUIDGenerator.hexUUID());
		sevenEntity.setHouseBaseFid(houseBaseFid);
		sevenEntity.setRoomFid(houseRoomFid);
		sevenEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019001.getValue());
		if (!Check.NuNObj(sp.getSevenDiscountRate())) {
			sevenEntity.setDicVal(String.valueOf(sp.getSevenDiscountRate()));
		} else {
			sevenEntity.setDicVal(String.valueOf(-1));
		}
		sevenEntity.setIsDel(fullDayIsDel);
		houseConfMsgDao.insertHouseConfMsg(sevenEntity);

		// 满30天的判断处理
		HouseConfMsgEntity thirtyEntity = new HouseConfMsgEntity();
		thirtyEntity.setFid(UUIDGenerator.hexUUID());
		thirtyEntity.setHouseBaseFid(houseBaseFid);
		thirtyEntity.setRoomFid(houseRoomFid);
		thirtyEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019002.getValue());
		if (!Check.NuNObj(sp.getThirtyDiscountRate())) {
			thirtyEntity.setDicVal(String.valueOf(sp.getThirtyDiscountRate()));
		} else {
			thirtyEntity.setDicVal(String.valueOf(-1));
		}
		thirtyEntity.setIsDel(fullDayIsDel);
		houseConfMsgDao.insertHouseConfMsg(thirtyEntity);
	}

	/**
	 * 
	 * 更新房源信息
	 *
	 * @author bushujie
	 * @created 2016年6月22日 下午3:37:37
	 *
	 * @param houseInputDto
	 */
	public void upHouseMsg(HouseInputDto houseInputDto){

		//保存房源修改 日志记录
		saveTroyUpdateHouseLog(houseInputDto);
		//更新楼号地址信息
		houseBaseExtDao.updateHouseBaseExt(houseInputDto.getHouseExt());

		String detailAddress = houseInputDto.getHouseExt().getDetailAddress();
		//更新房源名称
		if(!Check.NuNStr(detailAddress)){
			if(!Check.NuNStr(houseInputDto.getHouseBase().getHouseAddr())){
				houseInputDto.getHouseBase().setHouseAddr(houseInputDto.getHouseBase().getHouseAddr().split(" ")[0]+" "+detailAddress);
			}
		}

		// 整套出租且房源状态为品质审核不通过修改后状态改为已发布
		HouseBaseMsgEntity houseBase = houseInputDto.getHouseBase();
		boolean isModified = houseCommonLogicDao.updateHouseStatus(houseBase.getFid());
		if (isModified) {
			houseBase.setHouseStatus(HouseStatusEnum.YFB.getCode());
		}
		//更新设置出租截止日期
		if(!Check.NuNStr(houseInputDto.getTillDate())){
			try {
				houseBase.setTillDate(DateUtil.parseDate(houseInputDto.getTillDate(), "yyyy-MM-dd"));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		houseBaseMsgDao.updateHouseBaseMsg(houseBase);

		//更新房源描述和周边情况
		houseDescDao.updateHouseDesc(houseInputDto.getHouseDesc());
		//更新小区名称
		HousePhyMsgEntity housePhyMsgEntity=new HousePhyMsgEntity();
		if(!Check.NuNStr(houseInputDto.getHousePhy().getCommunityName())){
			housePhyMsgEntity.setCommunityName(houseInputDto.getHousePhy().getCommunityName());
		}
		if(!Check.NuNStr(houseInputDto.getCoordinate())){
			//更新百度坐标
			housePhyMsgEntity.setLatitude(Double.valueOf(houseInputDto.getCoordinate().split(",")[1]));
			housePhyMsgEntity.setLongitude(Double.valueOf(houseInputDto.getCoordinate().split(",")[0]));
			//google地图坐标
			Gps googleGps = CoordinateTransforUtils.bd09_To_Gps84(housePhyMsgEntity.getLatitude(), housePhyMsgEntity.getLongitude());
			housePhyMsgEntity.setGoogleLatitude(googleGps.getWgLat());
			housePhyMsgEntity.setGoogleLongitude(googleGps.getWgLon());
		}
		housePhyMsgEntity.setFid(houseInputDto.getHousePhy().getFid());
		housePhyMsgDao.updateHousePhyMsg(housePhyMsgEntity);
		//更新房源名称
		if(!Check.NuNCollection(houseInputDto.getRoomFid())){
			for(int i=0;i<houseInputDto.getRoomFid().size();i++){
				List<String> roomNames = houseInputDto.getRoomName();
				List<String> roomAreas = houseInputDto.getRoomArea();

				HouseRoomMsgEntity houseRoomMsgEntity=new HouseRoomMsgEntity();
				String roomFid = houseInputDto.getRoomFid().get(i);
				houseRoomMsgEntity.setFid(roomFid);

				if(!Check.NuNCollection(roomNames)){
					if(roomNames.size()-1 >= i && !Check.NuNStr(roomNames.get(i))){
						houseRoomMsgEntity.setRoomName(roomNames.get(i));
					} 
				}
				if(!Check.NuNCollection(roomAreas) && roomAreas.size()-1 >= i && !Check.NuNStr(roomAreas.get(i))){
					try {
						Double roomArea = Double.valueOf(roomAreas.get(i));
						houseRoomMsgEntity.setRoomArea(roomArea);
					} catch (Exception e) {
						LogUtil.info(LOGGER, "修改房间面积异常，参数：{}，e={}",roomAreas.get(i),e);
					}
				}

				// 独立房间且房间状态为品质审核不通过修改后状态改为已发布
				isModified = houseCommonLogicDao.updateRoomStatus(roomFid);
				if (isModified) {
					houseRoomMsgEntity.setRoomStatus(HouseStatusEnum.YFB.getCode());
				}
				houseRoomMsgDao.updateHouseRoomMsg(houseRoomMsgEntity);
				//更新房间守则
				HouseRoomExtEntity houseRoomExtEntity=new HouseRoomExtEntity();
				houseRoomExtEntity.setRoomFid(roomFid);
				houseRoomExtEntity.setRoomRules(houseInputDto.getHouseDesc().getHouseRules());
				houseRoomExtDao.updateByRoomfid(houseRoomExtEntity);
			}
		}
		//更新床信息
		if(!Check.NuNCollection(houseInputDto.getBedFidList())){
			for (int i = 0; i < houseInputDto.getBedFidList().size(); i++) {
				HouseBedMsgEntity bedMsgEntity=new HouseBedMsgEntity();
				bedMsgEntity.setFid(houseInputDto.getBedFidList().get(i));
				bedMsgEntity.setBedType(Integer.valueOf(houseInputDto.getBedType().get(i)));
				bedMsgEntity.setBedSize(Integer.valueOf(houseInputDto.getBedSize().get(i)));
				houseBedMsgDao.updateHouseBedMsg(bedMsgEntity);
			}
		}
	}


	/**
	 * 
	 * 保存 troy 后台修改房源  记录日志
	 * 
	 *
	 * @author yd
	 * @created 2017年7月13日 下午2:31:14
	 *
	 * @param houseInputDto
	 */
	private void saveTroyUpdateHouseLog(HouseInputDto houseInputDto){

		if(!Check.NuNObjs(houseInputDto,houseInputDto.getHouseBase())&&!Check.NuNStr(houseInputDto.getHouseBase().getFid())){

			if(!Check.NuNObj(houseInputDto.getHouseExt())){
				String detailAddress = houseInputDto.getHouseExt().getDetailAddress();
				//更新房源名称
				if(!Check.NuNStr(detailAddress)){
					if(!Check.NuNStr(houseInputDto.getHouseBase().getHouseAddr())){
						houseInputDto.getHouseBase().setHouseAddr(houseInputDto.getHouseBase().getHouseAddr().split(" ")[0]+" "+detailAddress);
					}
				}
			}

			//基本信息
			HouseBaseMsgEntity newHouseBase = houseInputDto.getHouseBase();

			HouseBaseMsgEntity oldHouseBaseMsgEntity = houseBaseMsgDao.getHouseBaseMsgEntityByFid(newHouseBase.getFid());
			if(Check.NuNObj(oldHouseBaseMsgEntity)){
				LogUtil.error(LOGGER, "【保存修改日志-房源不存在】houseBaseFid={}", newHouseBase.getFid());
				throw new BusinessException("【保存修改日志-房源不存在】houseBaseFid="+newHouseBase.getFid()+"");
			}
			int rentWay = oldHouseBaseMsgEntity.getRentWay();
			if(rentWay == RentWayEnum.HOUSE.getCode()&&oldHouseBaseMsgEntity.getHouseStatus() == HouseStatusEnum.DFB.getCode()){
				LogUtil.info(LOGGER, "【保存修改日志-整租待发布状态不做保存】houseBaseFid={},houseStatu={}", newHouseBase.getFid(),oldHouseBaseMsgEntity.getHouseStatus());
				return ;
			}
			List<HouseUpdateHistoryLogEntity> list = new ArrayList<HouseUpdateHistoryLogEntity>();

			HouseUtils.contrastHouseBaseMsgObj(newHouseBase, oldHouseBaseMsgEntity, list);

			//房源扩展信息
			HouseBaseExtEntity newHouseBaseExt = houseInputDto.getHouseExt();
			HouseBaseExtEntity oldHouseBaseExt = houseBaseExtDao.getHouseBaseExtByHouseBaseFid(newHouseBase.getFid());
			if(!Check.NuNObj(oldHouseBaseExt)){
				HouseUtils.contrastHouseBaseExtObj(newHouseBaseExt, oldHouseBaseExt, list);
			}

			//房源描述
			HouseDescEntity  newHouseDesc = 	houseInputDto.getHouseDesc();
			HouseDescEntity oldHouseDesc = houseDescDao.findHouseDescByHouseBaseFid(newHouseBase.getFid());

			if(!Check.NuNObj(oldHouseDesc)){
				HouseUtils.contrastHouseDescObj(newHouseDesc, oldHouseDesc, list);
			}

			HousePhyMsgEntity newHousePhy = null;
			//小区名称
			if(!Check.NuNObj(houseInputDto.getHousePhy())&&!Check.NuNStr(houseInputDto.getHousePhy().getCommunityName())){
				newHousePhy = houseInputDto.getHousePhy();
				HousePhyMsgEntity oldHousePhy = housePhyMsgDao.findHousePhyMsgByHouseBaseFid(newHouseBase.getFid());
				if(!Check.NuNObj(oldHousePhy)){
					HouseUtils.contrastHousePhyMsgObj(newHousePhy, oldHousePhy, list);
				}

			}


			List<HouseRoomMsgEntity> newListRooms = new ArrayList<HouseRoomMsgEntity>();

			//房源名称
			if(!Check.NuNCollection(houseInputDto.getRoomFid())){
				for(int i=0;i<houseInputDto.getRoomFid().size();i++){
					List<String> roomNames = houseInputDto.getRoomName();
					List<String> roomAreas = houseInputDto.getRoomArea();

					HouseRoomMsgEntity houseRoomMsgEntity=new HouseRoomMsgEntity();
					String roomFid = houseInputDto.getRoomFid().get(i);
					houseRoomMsgEntity.setFid(roomFid);

					if(!Check.NuNCollection(roomNames)){
						if(roomNames.size()-1 >= i && !Check.NuNStr(roomNames.get(i))){
							houseRoomMsgEntity.setRoomName(roomNames.get(i));
						} 
					}
					if(!Check.NuNCollection(roomAreas) && roomAreas.size()-1 >= i && !Check.NuNStr(roomAreas.get(i))){
						try {
							Double roomArea = Double.valueOf(roomAreas.get(i));
							houseRoomMsgEntity.setRoomArea(roomArea);
						} catch (Exception e) {
							LogUtil.info(LOGGER, "【保存修改日志-修改房间面积异常】，参数：{}，e={}",roomAreas.get(i),e);
						}
					}
					newListRooms.add(houseRoomMsgEntity);

				}
			}

			//获取房源状态
			Integer houseStatus = null;
			if(rentWay==RentWayEnum.HOUSE.getCode()){
				houseStatus = oldHouseBaseMsgEntity.getHouseStatus();
			}

			if(!Check.NuNCollection(newListRooms)){
				for (HouseRoomMsgEntity roomMsg : newListRooms) {
					if(!Check.NuNObj(roomMsg)&&!Check.NuNStr(roomMsg.getFid())){
						HouseRoomMsgEntity oldHouseRoom = houseRoomMsgDao.findHouseRoomMsgByFid(roomMsg.getFid());
						if(rentWay==RentWayEnum.ROOM.getCode()){
							houseStatus=oldHouseRoom.getRoomStatus();
						}
						if(Check.NuNObj(oldHouseRoom)){
							throw new BusinessException("【保存修改日志-房间不存在】roomFid="+roomMsg.getFid()+"");
						}
						HouseUtils.contrastHouseRoomMsgObj(roomMsg, oldHouseRoom, list,rentWay);
					}
				}
			}

			if(!Check.NuNCollection(list)){
				for (HouseUpdateHistoryLogEntity houseUpdateHistoryLog : list) {
					houseUpdateHistoryLog.setCreaterFid(houseInputDto.getEmpCode());
					houseUpdateHistoryLog.setCreaterType(CreaterTypeEnum.GUARD.getCode());
					houseUpdateHistoryLog.setHouseFid(oldHouseBaseMsgEntity.getFid());
					houseUpdateHistoryLog.setRentWay(oldHouseBaseMsgEntity.getRentWay());
					String fieldPathKey = MD5Util.MD5Encode(houseUpdateHistoryLog.getHouseFid()+houseUpdateHistoryLog.getRoomFid()+houseUpdateHistoryLog.getRentWay()
							+houseUpdateHistoryLog.getFieldPath(), "UTF-8");
					houseUpdateHistoryLog.setFieldPathKey(fieldPathKey);
					houseUpdateHistoryLog.setSourceType(HouseSourceEnum.TROY.getCode());
					saveHouseUpdateHistoryLog(houseUpdateHistoryLog,houseStatus);
				}
			}

		}


	}



	/**
	 * 
	 * 保存 
	 * 1. 保存 修改历史 HouseUpdateHistoryLog 如果 是大字段 则存入大字段表
	 * 2. 更新 最新记录表
	 *
	 * @author yd
	 * @created 2017年6月30日 下午4:34:42
	 *
	 * @param houseUpdateHistoryLog
	 */
	public int saveHouseUpdateHistoryLog(HouseUpdateHistoryLogEntity houseUpdateHistoryLog,Integer houseStatus){

		if(!Check.NuNObj(houseUpdateHistoryLog)){

			if(Check.NuNStr(houseUpdateHistoryLog.getFid())) houseUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
			//此字段 必传 在上层做校验
			int isTest = houseUpdateHistoryLog.getIsText();
			int i =  0;
			if(isTest == IsTextEnum.IS_TEST.getCode()){

				LogUtil.info(LOGGER, "【保存房源修改大字段】isTest={},houseFid={},roomFid={},rentWay={},fieldPathKey={},", isTest,houseUpdateHistoryLog.getHouseFid(),houseUpdateHistoryLog.getRoomFid()
						,houseUpdateHistoryLog.getRentWay(),houseUpdateHistoryLog.getFieldPathKey());
				HouseUpdateHistoryExtLogEntity houseUpdateHistoryExtLog = new HouseUpdateHistoryExtLogEntity();
				houseUpdateHistoryExtLog.setFid(houseUpdateHistoryLog.getFid());
				houseUpdateHistoryExtLog.setNewValue(houseUpdateHistoryLog.getNewValue());
				houseUpdateHistoryExtLog.setOldValue(houseUpdateHistoryLog.getOldValue());
				houseUpdateHistoryLog.setNewValue("");
				houseUpdateHistoryLog.setOldValue("");

				this.houseUpdateHistoryExtLogDao.saveHouseUpdateHistoryExtLog(houseUpdateHistoryExtLog);
			}
			i = this.houseUpdateHistoryLogDao.saveHouseUpdateHistoryLog(houseUpdateHistoryLog);
			//troy修改不需要审核，bushujie 20171025
			//			//t_house_update_field_audit_newlog 审核字段的最新记录 只做第一次插入  这里状态更改在 审核时候 才会状态更改  
			//			if(i>0){
			//
			//				/*********处理审核未通过和上架情况下的审核记录的插入和更新。@Author:lusp  @Date:2017/8/8*********/
			//				i = this.saveHouseAuditField(houseUpdateHistoryLog,houseStatus,i);
			//				/*********处理审核未通过和上架情况下的审核记录的插入和更新。@Author:lusp  @Date:2017/8/8*********/
			//			}

			return i;
		}


		return 0;
	}

	/**
	 * @description: 根据房源状态以及房源基础信息保存审核记录表
	 * @author: lusp
	 * @date: 2017/8/9 17:12
	 * @params: houseUpdateHistoryLog,houseStatus,i
	 * @return:
	 */
	private int saveHouseAuditField(HouseUpdateHistoryLogEntity houseUpdateHistoryLog,Integer houseStatus,int i){
		HouseUpdateFieldAuditManagerEntity houseUpdateFieldAuditManagerEntity = new HouseUpdateFieldAuditManagerEntity();
		houseUpdateFieldAuditManagerEntity.setFid(MD5Util.MD5Encode(houseUpdateHistoryLog.getFieldPath(), "UTF-8"));
		if(houseStatus == HouseStatusEnum.ZPSHWTG.getCode()){
			houseUpdateFieldAuditManagerEntity.setType(0);
		}else if(houseStatus == HouseStatusEnum.SJ.getCode()){
			houseUpdateFieldAuditManagerEntity.setType(1);
		}
		houseUpdateFieldAuditManagerEntity = this.houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByFidAndType(houseUpdateFieldAuditManagerEntity);
		if(Check.NuNObj(houseUpdateFieldAuditManagerEntity)){
			LogUtil.info(LOGGER, "【保存房源修改审核字段】当前字段非审核字段：fieldPath={}", houseUpdateHistoryLog.getFieldPath());
			return i;
		}
		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlog = this.houseUpdateFieldAuditNewlogDao.findHouseUpdateFieldAuditNewlogByFid(houseUpdateHistoryLog.getFieldPathKey());
		if(Check.NuNObj(houseUpdateFieldAuditNewlog)){
			houseUpdateFieldAuditNewlog = new HouseUpdateFieldAuditNewlogEntity();
			houseUpdateFieldAuditNewlog.setFieldPath(houseUpdateHistoryLog.getFieldPath());
			houseUpdateFieldAuditNewlog.setFieldDesc(houseUpdateHistoryLog.getFieldDesc());
			houseUpdateFieldAuditNewlog.setFid(houseUpdateHistoryLog.getFieldPathKey());
			houseUpdateFieldAuditNewlog.setCreaterFid(houseUpdateHistoryLog.getCreaterFid());
			houseUpdateFieldAuditNewlog.setCreaterType(houseUpdateHistoryLog.getCreaterType());
			houseUpdateFieldAuditNewlog.setFieldAuditStatu(AuditStatusEnum.SUBMITAUDIT.getCode());
			houseUpdateFieldAuditNewlog.setHouseFid(houseUpdateHistoryLog.getHouseFid());
			houseUpdateFieldAuditNewlog.setRoomFid(houseUpdateHistoryLog.getRoomFid());
			houseUpdateFieldAuditNewlog.setRentWay(houseUpdateHistoryLog.getRentWay());
			i = this.houseUpdateFieldAuditNewlogDao.saveHouseUpdateFieldAuditNewlog(houseUpdateFieldAuditNewlog);
		}else{
			houseUpdateFieldAuditNewlog.setFieldAuditStatu(0);
			i = this.houseUpdateFieldAuditNewlogDao.updateHouseUpdateFieldAuditNewlogByFid(houseUpdateFieldAuditNewlog);
		}

		return i;

	}

	/**
	 * 
	 * 查询整租或者合租未审核图片列表
	 *
	 * @author bushujie
	 * @created 2016年6月23日 下午6:01:04
	 *
	 * @param roomFid
	 * @param houseBaseFid
	 * @return
	 */
	public List<HousePicMsgEntity> findNoAuditHousePicList(String roomFid,String houseBaseFid){
		return housePicMsgDao.findNoAuditHousePicList(roomFid, houseBaseFid);
	}

	/**
	 * 
	 * 更新房源图片信息
	 *
	 * @author bushujie
	 * @created 2016年6月24日 下午2:52:13
	 *
	 * @param housePicMsg
	 * @return
	 */
	public int updateHousePicMsg(HousePicMsgEntity housePicMsg){
		return housePicMsgDao.updateHousePicMsg(housePicMsg);
	}


	/**
	 * 1. 查询审核记录表  判断是否需要审核  只针对整租房源来说  
	 * 2. 更新房源扩展表信息
	 *
	 * @author bushujie
	 * @created 2016年6月25日 下午1:29:08
	 *
	 * @param houseBaseExtEntity
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 */
	public int updateHouseBaseExtByHouseBaseFid(HouseBaseExtEntity houseBaseExtEntity,Integer defaultPicFlag) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		//如果是troy修改默认图片不需要审核
		String defaultPicFid=houseBaseExtEntity.getDefaultPicFid();

		if(!Check.NuNObj(houseBaseExtEntity)&&!Check.NuNStr(houseBaseExtEntity.getHouseBaseFid())){
			HouseBaseMsgEntity house = this.houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseBaseExtEntity.getHouseBaseFid());
			if(Check.NuNObj(house)){
				throw new BusinessException("【更新房源扩展信息异常】房源不存houseFid:{"+houseBaseExtEntity.getHouseBaseFid()+"}");
			}
			if(house.getRentWay() == RentWayEnum.HOUSE.getCode()){
				if(house.getHouseStatus()==HouseStatusEnum.ZPSHWTG.getCode()
						||house.getHouseStatus()==HouseStatusEnum.SJ.getCode()){
					int code = 0;
					if(house.getHouseStatus()==HouseStatusEnum.SJ.getCode()){
						code = 1;
					}
					List<HouseUpdateFieldAuditManagerEntity> houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(code);
					houseBaseExtEntity = HouseUtils.FilterAuditField(houseBaseExtEntity,houseUpdateFieldAuditManagerEntities);
				}
			}
		}
		//如果是troy修改默认图片不需要审核
		if(defaultPicFlag==1){
			houseBaseExtEntity.setDefaultPicFid(defaultPicFid);
		}
		return houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseBaseExtEntity);
	}

	/**
	 * 查询房源信息和所在城市信息
	 * @author lishaochuan
	 * @create 2016年8月4日下午5:19:57
	 * @param houseFidList
	 * @return
	 */
	public List<HouseCityVo> getHouseCityVoByFids(List<String> houseFidList){
		return houseBaseMsgDao.getHouseCityVoByFids(houseFidList);
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
		return this.houseBaseMsgDao.findHouseFidByAuth(authMenu);
	}

	/**
	 * 
	 * 查询需要拍照的房源信息
	 * @param houseRequest
	 * @return
	 */
	public PagingResult<NeedPhotogHouseVo> findNeedPhotographerHouse(HouseRequestDto houseRequest){
		return houseBaseMsgDao.findNeedPhotographerHouse(houseRequest);
	}

	/**
	 * 根据房源名称 ， 国家， 城市三个维度获取整租房源houseFidList
	 *
	 * @author loushuai
	 * @created 2017年5月25日 下午5:37:28
	 *
	 * @param paramMap
	 * @return
	 */
	public List<String> getHoseFidListForIMFollow(Map<String, Object> paramMap) {
		return houseBaseMsgDao.getHoseFidListForIMFollow(paramMap);
	}

	/**
	 * 根据房间名称 ， 国家， 城市三个维度获取分租roomFidList
	 *
	 * @author loushuai
	 * @created 2017年5月25日 下午5:37:28
	 *
	 * @param paramMap
	 * @return
	 */
	public List<String> getRoomFidListForIMFollow(Map<String, Object> paramMap) {
		return houseBaseMsgDao.getRoomFidListForIMFollow(paramMap);
	}

	/**
	 * 根据housefid获取房源名称 城市
	 *
	 * @author loushuai
	 * @created 2017年5月26日 下午4:30:37
	 *
	 * @param paramMap
	 * @return
	 */
	public HouseCityVo getHouseInfoForImFollow(Map<String, Object> paramMap) {
		return houseBaseMsgDao.getHouseInfoForImFollow(paramMap);
	}

	/**
	 * 根据roomfid获取房源名称 城市
	 *
	 * @author loushuai
	 * @created 2017年5月26日 下午4:46:36
	 *
	 * @param paramMap
	 * @return
	 */
	public HouseCityVo getRoomInfoForImFollow(Map<String, Object> paramMap) {
		return  houseBaseMsgDao.getRoomInfoForImFollow(paramMap);
	}

	/**
	 * 获取房源或房间名称集合
	 *
	 * @author lusp
	 * @created 2017年6月30日 下午4:50:09
	 * @param houseBaseParamsDto
	 * @return
	 */
	public List<String> getHouseOrRoomNameList(HouseBaseParamsDto houseBaseParamsDto){
		return houseBaseMsgDao.getHouseOrRoomNameList(houseBaseParamsDto);
	}

	/**
	 * @description: 根据条件查询（houseFid 、roomFid、rentWay等）
	 * @author: lusp
	 * @date: 2017/7/31 11:13
	 * @params: houseUpdateFieldAuditNewlog
	 * @return:
	 */
	public List<HouseFieldAuditLogVo> findHouseUpdateFieldAuditNewlogByCondition(HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlog){
		return houseUpdateFieldAuditNewlogDao.findHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlog);
	}

	/**
	 * @description: 根据id 更新审核记录表数据
	 * @author: lusp
	 * @date: 2017/8/3 14:41
	 * @params: houseUpdateFieldAuditNewlogEntity
	 * @return:
	 */
	public int updateHouseUpdateFieldAuditNewlogById(HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity){
		return houseUpdateFieldAuditNewlogDao.updateHouseUpdateFieldAuditNewlogById(houseUpdateFieldAuditNewlogEntity);
	}

	/**
	 * @description: 上架房源待审核信息审核通过
	 * @author: lusp
	 * @date: 2017/8/4 17:55
	 * @params: houseUpdateFieldAuditNewlogEntity
	 * @return: 审核通过的字段信息集合（2017/8/31 增加返回值，供发送短信给房东通知审核通过字段时使用）
	 */
	public List<HouseFieldAuditLogVo> approveGroundingHouseInfo(HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity,List<String> picFids)throws Exception{

		houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(0);

		List<HouseFieldAuditLogVo> houseFieldAuditLogVoList = houseUpdateFieldAuditNewlogDao.findHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlogEntity);
		HouseAuditVo houseAuditVo = packageAuditFieldList(houseFieldAuditLogVoList);

		if(!CheckFieldIsNull(houseAuditVo.getHouseBaseMsgEntity())){
			houseAuditVo.getHouseBaseMsgEntity().setFid(houseUpdateFieldAuditNewlogEntity.getHouseFid());
			houseBaseMsgDao.updateHouseBaseMsg(houseAuditVo.getHouseBaseMsgEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHouseBaseExtEntity())){
			houseAuditVo.getHouseBaseExtEntity().setHouseBaseFid(houseUpdateFieldAuditNewlogEntity.getHouseFid());
			houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseAuditVo.getHouseBaseExtEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHouseDescEntity())){
			houseAuditVo.getHouseDescEntity().setHouseBaseFid(houseUpdateFieldAuditNewlogEntity.getHouseFid());
			houseDescDao.updateHouseDescByHouseBaseFid(houseAuditVo.getHouseDescEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHouseConfMsgEntity())){
			houseAuditVo.getHouseConfMsgEntity().setHouseBaseFid(houseUpdateFieldAuditNewlogEntity.getHouseFid());
			houseConfMsgDao.updateHouseConfMsgByHouseBaseFid(houseAuditVo.getHouseConfMsgEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHouseRoomMsgEntity())){
			houseAuditVo.getHouseRoomMsgEntity().setFid(houseUpdateFieldAuditNewlogEntity.getRoomFid());
			houseRoomMsgDao.updateHouseRoomMsg(houseAuditVo.getHouseRoomMsgEntity());
		}
		if(!CheckFieldIsNull(houseAuditVo.getHouseRoomExtEntity())){
			houseAuditVo.getHouseRoomExtEntity().setRoomFid(houseUpdateFieldAuditNewlogEntity.getRoomFid());
			houseRoomExtDao.updateByRoomfid(houseAuditVo.getHouseRoomExtEntity());
		}

		for (HouseFieldAuditLogVo houseFieldAuditLogVo : houseFieldAuditLogVoList) {
			houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(1);
			houseUpdateFieldAuditNewlogEntity.setFid(houseFieldAuditLogVo.getFid());
			houseUpdateFieldAuditNewlogDao.updateHouseUpdateFieldAuditNewlogByFid(houseUpdateFieldAuditNewlogEntity);
			//houseUpdateFieldAuditNewlogDao.updateHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlogEntity);
		}

		if(!Check.NuNCollection(picFids)){
			LogUtil.info(LOGGER, "审核照片参数:picFids={}",picFids);
			for (String fid : picFids) {
				HousePicMsgEntity housePicMsgEntity=new HousePicMsgEntity();
				housePicMsgEntity.setFid(fid);
				housePicMsgEntity.setAuditStatus(1);
				housePicMsgDao.updateHousePicMsg(housePicMsgEntity);
			}
		}

		return houseFieldAuditLogVoList;
	}

	/**
	 * 
	 * 查询未审核通过时添加图片列表
	 *
	 * @author bushujie
	 * @created 2017年9月21日 下午7:27:12
	 *
	 * @param houseBaseFid
	 * @param roomFid
	 * @return
	 */
	public List<HousePicMsgEntity> findNoAuditAddHousePicList(String houseBaseFid,String roomFid){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("houseBaseFid", houseBaseFid);
		paramMap.put("roomFid", roomFid);
		paramMap.put("auditStatus", 3);
		return housePicMsgDao.findPicByAuditStatus(paramMap);
	}
}
