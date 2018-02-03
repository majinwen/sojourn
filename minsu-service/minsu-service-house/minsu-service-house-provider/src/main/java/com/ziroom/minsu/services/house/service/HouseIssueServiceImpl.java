package com.ziroom.minsu.services.house.service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseBedMsgEntity;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.entity.house.HouseDescEntity;
import com.ziroom.minsu.entity.house.HouseFollowEntity;
import com.ziroom.minsu.entity.house.HouseFollowLogEntity;
import com.ziroom.minsu.entity.house.HouseGuardRelEntity;
import com.ziroom.minsu.entity.house.HouseOperateLogEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.entity.house.HousePicMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomExtEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditManagerEntity;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.services.house.dao.HouseBaseExtDao;
import com.ziroom.minsu.services.house.dao.HouseBaseMsgDao;
import com.ziroom.minsu.services.house.dao.HouseBedMsgDao;
import com.ziroom.minsu.services.house.dao.HouseCommonLogicDao;
import com.ziroom.minsu.services.house.dao.HouseConfMsgDao;
import com.ziroom.minsu.services.house.dao.HouseDescDao;
import com.ziroom.minsu.services.house.dao.HouseFollowDao;
import com.ziroom.minsu.services.house.dao.HouseFollowLogDao;
import com.ziroom.minsu.services.house.dao.HouseGuardRelDao;
import com.ziroom.minsu.services.house.dao.HouseOperateLogDao;
import com.ziroom.minsu.services.house.dao.HousePhyMsgDao;
import com.ziroom.minsu.services.house.dao.HousePicMsgDao;
import com.ziroom.minsu.services.house.dao.HousePriceConfDao;
import com.ziroom.minsu.services.house.dao.HouseRoomExtDao;
import com.ziroom.minsu.services.house.dao.HouseRoomMsgDao;
import com.ziroom.minsu.services.house.dao.HouseUpdateFieldAuditManagerDao;
import com.ziroom.minsu.services.house.dto.HouseBaseExtDescDto;
import com.ziroom.minsu.services.house.dto.HouseBaseExtDto;
import com.ziroom.minsu.services.house.dto.HouseBaseMsgDto;
import com.ziroom.minsu.services.house.dto.HouseBaseParamsDto;
import com.ziroom.minsu.services.house.dto.HouseConfParamsDto;
import com.ziroom.minsu.services.house.dto.HouseDescDto;
import com.ziroom.minsu.services.house.dto.HouseFollowListDto;
import com.ziroom.minsu.services.house.dto.HouseOpLogSpDto;
import com.ziroom.minsu.services.house.dto.HousePhyMsgDto;
import com.ziroom.minsu.services.house.dto.HousePicDto;
import com.ziroom.minsu.services.house.dto.HouseRoomListDto;
import com.ziroom.minsu.services.house.dto.HouseRoomMsgDto;
import com.ziroom.minsu.services.house.dto.RoomBedZDto;
import com.ziroom.minsu.services.house.entity.HouseBaseDetailVo;
import com.ziroom.minsu.services.house.entity.HouseBaseExtVo;
import com.ziroom.minsu.services.house.entity.HouseConfVo;
import com.ziroom.minsu.services.house.entity.HouseDefaultPicInfoVo;
import com.ziroom.minsu.services.house.entity.HouseMsgVo;
import com.ziroom.minsu.services.house.entity.RoomMsgVo;
import com.ziroom.minsu.services.house.issue.vo.HouseHallVo;
import com.ziroom.minsu.services.house.utils.HouseUtils;
import com.ziroom.minsu.valenum.house.FollowStatusEnum;
import com.ziroom.minsu.valenum.house.HouseIssueStepEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0012Enum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum008Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005Enum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>房东端-房源发布service</p>
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
@Service("house.houseIssueServiceImpl")
public class HouseIssueServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(HouseIssueServiceImpl.class);

	@Resource(name = "house.houseBaseMsgDao")
	private HouseBaseMsgDao houseBaseMsgDao;

	@Resource(name = "house.housePhyMsgDao")
	private HousePhyMsgDao housePhyMsgDao;

	@Resource(name = "house.houseDescDao")
	private HouseDescDao houseDescDao;

	@Resource(name = "house.houseRoomMsgDao")
	private HouseRoomMsgDao houseRoomMsgDao;

	@Resource(name = "house.houseBedMsgDao")
	private HouseBedMsgDao houseBedMsgDao;

	@Resource(name = "house.houseConfMsgDao")
	private HouseConfMsgDao houseConfMsgDao;

	@Resource(name = "house.housePriceConfDao")
	private HousePriceConfDao housePriceConfDao;

	@Resource(name = "house.housePicMsgDao")
	private HousePicMsgDao housePicMsgDao;

	@Resource(name = "house.houseBaseExtDao")
	private HouseBaseExtDao houseBaseExtDao;

	@Resource(name = "house.houseOperateLogDao")
	private HouseOperateLogDao houseOperateLogDao;

	@Autowired
	private RedisOperations redisOperations;

	@Resource(name = "house.houseGuardRelDao")
	private HouseGuardRelDao houseGuardRelDao;
	
	@Resource(name = "house.houseCommonLogicDao")
	private HouseCommonLogicDao houseCommonLogicDao;

	@Resource(name = "house.houseFollowDao")
	private HouseFollowDao houseFollowDao;

	@Resource(name = "house.houseFollowLogDao")
	private HouseFollowLogDao houseFollowLogDao;
	
	@Resource(name = "house.houseRoomExtDao")
	private HouseRoomExtDao houseRoomExtDao;
	
	@Resource(name="basedata.zkSysService")
	private ZkSysService zkSysService;

	@Resource(name = "house.houseUpdateFieldAuditManagerDao")
	private HouseUpdateFieldAuditManagerDao houseUpdateFieldAuditManagerDao;

	/**
	 * saveHouseConfList 插入配置项集合到配置项表
	 * updateHouseConfList 更新配置项集合表
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/12/5 18:10
	 */
	public void saveHouseConfList(List<HouseConfMsgEntity> confList){
		for(HouseConfMsgEntity houseConf : confList){
			houseConfMsgDao.insertHouseConfMsg(houseConf);
		}
	}

	public void updateHouseConfdicvalByfid(HouseConfMsgEntity confEntity){
		houseConfMsgDao.updateHouseConfMsg(confEntity);
	}

	public void updateHouseConfList(List<HouseConfMsgEntity> confList){
		for(HouseConfMsgEntity houseConf : confList){
			houseConfMsgDao.updateHouseConfMsgByother(houseConf);
		}
	}

	/**
	 * 校验当前的房源是否存在
	 * 来验证是否插入或者修改
	 * @author afi
	 * @param houseRoomMsg
	 */
	public void mergeCheckHouseRoomMsg(HouseRoomMsgDto houseRoomMsg) {
		//房源房间信息表逻辑id
		String houseRoomFid = houseRoomMsg.getFid();
		//是否插入的标记
		boolean insertFlag = true;
		if(!Check.NuNStr(houseRoomFid)){
			HouseRoomMsgEntity room = houseRoomMsgDao.getHouseRoomByFid(houseRoomFid);
			if(!Check.NuNObj(room)){
				insertFlag = false;
			}
		}
		if(insertFlag){
			// 1.新增房源房间信息
			// 注意:此处必须保证 HouseRoomMsgDto#houseBaseFid字段存在有效值
			houseRoomMsg.setFid(houseRoomFid);
			houseRoomMsgDao.insertHouseRoomMsg(houseRoomMsg);

			// 2.更新录入房源操作步骤
			HouseBaseMsgEntity houseBaseMsg = new HouseBaseMsgEntity();
			houseBaseMsg.setFid(houseRoomMsg.getHouseBaseFid());
			houseBaseMsg.setOperateSeq(houseRoomMsg.getOperateSeq());
			houseBaseMsg.setIntactRate(houseRoomMsg.getIntactRate());
			houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);
		}else {
			// 1.更新房源房间信息
			houseRoomMsgDao.updateHouseRoomMsg(houseRoomMsg);
		}

	}


	/**
	 * (新增|更新)房源房间信息
	 *
	 * @author liujun
	 * @created 2016年4月1日 下午9:41:10
	 *
	 * @param houseRoomMsg
	 */
	public void mergeHouseRoomMsg(HouseRoomMsgDto houseRoomMsg) {
		String houseRoomFid = houseRoomMsg.getFid();//房源房间信息表逻辑id

		if(Check.NuNStr(houseRoomFid)){
			// 1.新增房源房间信息
			// 注意:此处必须保证 HouseRoomMsgDto#houseBaseFid字段存在有效值
			houseRoomFid = UUIDGenerator.hexUUID();
			houseRoomMsg.setFid(houseRoomFid);
			houseRoomMsgDao.insertHouseRoomMsg(houseRoomMsg);

			// 2.更新录入房源操作步骤
			HouseBaseMsgEntity houseBaseMsg = new HouseBaseMsgEntity();
			houseBaseMsg.setFid(houseRoomMsg.getHouseBaseFid());
			houseBaseMsg.setOperateSeq(houseRoomMsg.getOperateSeq());
			houseBaseMsg.setIntactRate(houseRoomMsg.getIntactRate());
			houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);
		}else{
			// 1.更新房源房间信息
			houseRoomMsgDao.updateHouseRoomMsg(houseRoomMsg);
		}
	}

	/**
	 * (新增|更新)房源床位信息
	 *
	 * @author liujun
	 * @created 2016年4月2日 下午5:07:00
	 *
	 * @param houseBedMsg
	 */
	public void mergeHouseBedMsg(HouseBedMsgEntity houseBedMsg) {
		String houseBedFid = houseBedMsg.getFid();
		if(Check.NuNStr(houseBedFid)){//新增
			// 注意:此处必须保证 HouseBedMsgEntity#roomFid,HouseBedMsgEntity#houseBaseFid字段存在有效值
			// 1.保存房源床位信息
			houseBedMsg.setFid(UUIDGenerator.hexUUID());
			Integer bedSn=houseBedMsgDao.getMaxBedSnByRoomFid(houseBedMsg.getRoomFid());
			if(!Check.NuNObj(bedSn)){
				houseBedMsg.setBedSn(bedSn+1);
			}
			houseBedMsgDao.insertHouseBedMsg(houseBedMsg);

			// 2.更新房源房间床位数量字段 HouseRoomMsgEntity.bedNum
			int bedNum = houseBedMsgDao.getRoomBedCount(houseBedMsg.getRoomFid());
			HouseRoomMsgEntity houseRoomMsg = new HouseRoomMsgEntity();
			houseRoomMsg.setFid(houseBedMsg.getRoomFid());
			houseRoomMsg.setBedNum(bedNum);
			houseRoomMsgDao.updateHouseRoomMsg(houseRoomMsg);
		}else{//更新
			houseBedMsgDao.updateHouseBedMsg(houseBedMsg); 
		}
	}


	/**
	 * (新增|更新)房源床位信息
	 *
	 * @author liujun
	 * @created 2016年4月2日 下午5:07:00
	 *
	 * @param houseBedMsg
	 */
	public void mergeCheckHouseBedMsg(HouseBedMsgEntity houseBedMsg,DataTransferObject dto) {
		if(dto.getCode() == DataTransferObject.SUCCESS){
			String houseBedFid = houseBedMsg.getFid();
			if(Check.NuNStr(houseBedFid)){//新增
				// 1.保存房源床位信息;
				// 注意:此处必须保证 HouseBedMsgEntity#roomFid,HouseBedMsgEntity#houseBaseFid字段存在有效值
				houseBedMsg.setFid(UUIDGenerator.hexUUID());
				Integer bedSn=houseBedMsgDao.getMaxBedSnByRoomFid(houseBedMsg.getRoomFid());

				int bedNum = houseBedMsgDao.getRoomBedCount(houseBedMsg.getRoomFid());

				if(bedNum > 7){
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("超过床铺最大数量");
					return;
				}
				if(!Check.NuNObj(bedSn)){
					houseBedMsg.setBedSn(bedSn+1);
				}
				houseBedMsgDao.insertHouseBedMsg(houseBedMsg);

				// 2.更新房源房间床位数量字段 HouseRoomMsgEntity.bedNum

				HouseRoomMsgEntity houseRoomMsg = new HouseRoomMsgEntity();
				houseRoomMsg.setFid(houseBedMsg.getRoomFid());
				houseRoomMsg.setBedNum(bedNum +1);
				houseRoomMsgDao.updateHouseRoomMsg(houseRoomMsg);
			}else{//更新
				houseBedMsgDao.updateHouseBedMsg(houseBedMsg);
			}
		}
	}


	/**
	 * 根据房源房间逻辑id查询详情信息
	 *
	 * @author liujun
	 * @created 2016年4月2日 下午8:32:05
	 *
	 * @param houseRoomFid
	 * @return 
	 */
	public HouseRoomMsgEntity findHouseRoomMsgByFid(String houseRoomFid) {
		return houseRoomMsgDao.findHouseRoomMsgByFid(houseRoomFid);
	}

	/**
	 * 根据房源床位逻辑id查询详情信息
	 *
	 * @author liujun
	 * @created 2016年4月2日 下午9:18:46
	 *
	 * @param houseBedFid
	 * @return
	 */
	public HouseBedMsgEntity findHouseBedMsgByFid(String houseBedFid) {
		return houseBedMsgDao.findHouseBedMsgByFid(houseBedFid);
	}

	/**
	 * 根据房源房间逻辑id查询床位集合
	 *
	 * @author liujun
	 * @created 2016年4月2日 下午10:08:00
	 *
	 * @param houseRoomFid
	 * @return
	 */
	public List<HouseBedMsgEntity> findBedListByRoomFid(String houseRoomFid) {
		return houseBedMsgDao.findBedListByRoomFid(houseRoomFid);
	}

	/**
	 * 通过roomFid 获取当前的床数量
	 * @author afi
	 * @param houseRoomFid
	 * @return
	 */
	public Long countBedByRoomFid(String houseRoomFid){
		return houseBedMsgDao.countBedByRoomFid(houseRoomFid);

	}

	/**
	 * 根据房源Fid查询房间集合
	 *
	 * @author liujun
	 * @created 2016年4月2日 下午10:33:08
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public List<HouseRoomMsgEntity> findRoomListByHouseBaseFid(String houseBaseFid) {
		return houseRoomMsgDao.findRoomListByHouseBaseFid(houseBaseFid);
	}

	/**
	 * 更新房源基础信息与(新增|更新)房源配置集合
	 *
	 * @author liujun
	 * @created 2016年4月3日 下午3:33:35
	 *
	 * @param houseBaseExtVo
	 */
	public void mergeHouseBaseExtAndHouseConfList(HouseBaseExtVo houseBaseExtVo) {
		String houseBaseFid = houseBaseExtVo.getHouseBaseFid();//房源基本信息逻辑id

		// 1.更新房源基础信息

		if(!Check.NuNObj(houseBaseExtVo.getOperateSeq())&&houseBaseExtVo.getOperateSeq() == HouseIssueStepEnum.SEVEN.getCode()){
			HouseBaseMsgEntity houseBaseMsg = new HouseBaseMsgEntity();
			houseBaseMsg.setFid(houseBaseExtVo.getHouseBaseFid());
			houseBaseMsg.setOperateSeq(houseBaseExtVo.getOperateSeq());
			houseBaseMsg.setIntactRate(HouseIssueStepEnum.SEVEN.getValue());
			houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);
		}


		// 2.更新房源基础信息扩展
		houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseBaseExtVo);

		// 3.新增或更新房源配置信息集合(理论上应同时新增或更新)
		List<HouseConfMsgEntity> houseConfList = houseBaseExtVo.getHouseConfList();
		for (HouseConfMsgEntity houseConfMsg : houseConfList) {
			if(Check.NuNStr(houseConfMsg.getFid())){//新增
				houseConfMsg.setHouseBaseFid(houseBaseFid);
				houseConfMsg.setFid(UUIDGenerator.hexUUID());
				houseConfMsgDao.insertHouseConfMsg(houseConfMsg);
			}else{//更新
				houseConfMsgDao.updateHouseConfMsg(houseConfMsg);
			}
		}
	}

	/**
	 * 通过房源基础信息逻辑id查询房源基础信息&通过房源基础信息逻辑id和房源配置code集合查询房源配置集合
	 *
	 * @author liujun
	 * @created 2016年4月3日 下午5:29:48
	 *
	 * @param houseBaseExt
	 * @return
	 */
	public HouseBaseExtVo findHouseBaseExtAndHouseConfList(HouseBaseExtVo houseBaseExt) {
		// 1.通过房源基础信息逻辑id查询房源基础信息扩展
		HouseBaseExtVo baseExtVo = houseBaseExtDao.findBaseExtVoByHouseBaseFid(houseBaseExt.getHouseBaseFid());

		// 2.通过房源基础信息逻辑id和房源配置code集合查询房源配置集合
		List<HouseConfMsgEntity> houseConfList = houseConfMsgDao.findConfListByHouseFidAndCodeList(houseBaseExt);

		baseExtVo.setHouseConfList(houseConfList);
		return baseExtVo;

	}

	/**
	 * 更新房源基础信息与房源描述信息
	 *
	 * @author liujun
	 * @created 2016年4月3日 下午9:47:03
	 *
	 * @param houseBaseMsg
	 */
	public void updateHouseBaseMsgAndHouseDesc(HouseBaseMsgDto houseBaseMsg) {
		HouseOperateLogEntity houseOperateLogEntity = new HouseOperateLogEntity();
		houseOperateLogEntity.setFid(UUIDGenerator.hexUUID());
		houseOperateLogEntity.setHouseBaseFid(houseBaseMsg.getFid());
		houseOperateLogEntity.setOperateType(HouseConstant.HOUSE_LOG_OPERATE_TYPE_LANDLORD);//房东
		houseOperateLogEntity.setFromStatus(houseBaseMsg.getHouseStatus());
		houseOperateLogEntity.setToStatus(HouseStatusEnum.YFB.getCode());
		houseOperateLogEntity.setCreateFid(houseBaseMsg.getLandlordUid());
		houseOperateLogEntity.setCreateDate(new Date());
		houseOperateLogDao.insertHouseOperateLog(houseOperateLogEntity);

		// 1.更新房源基础信息
		houseBaseMsg.setHouseStatus(HouseStatusEnum.YFB.getCode());
		houseBaseMsg.setLastModifyDate(new Date());
		houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);

		List<HouseRoomMsgEntity> roomList = houseRoomMsgDao.findRoomListByHouseBaseFid(houseBaseMsg.getFid());
		int num = 0;
		for (HouseRoomMsgEntity houseRoomMsg : roomList) {
			List<HouseBedMsgEntity> bedList = houseBedMsgDao.findBedListByRoomFid(houseRoomMsg.getFid());
			for (HouseBedMsgEntity houseBedMsg : bedList) {
				houseBedMsg.setBedStatus(HouseStatusEnum.YFB.getCode());
				houseBedMsg.setLastModifyDate(new Date());
				houseBedMsgDao.updateHouseBedMsg(houseBedMsg);
			}

			if(num == 0){
				num++;
				houseRoomMsg.setIsDefault(HouseConstant.IS_TRUE);//设置默认房间
			}
			houseRoomMsg.setRoomStatus(HouseStatusEnum.YFB.getCode());
			houseRoomMsg.setLastModifyDate(new Date());
			houseRoomMsgDao.updateHouseRoomMsg(houseRoomMsg);
		}

		// 2.更新房源描述信息
		HouseDescEntity houseDesc = new HouseDescEntity();
		houseDesc.setFid(houseBaseMsg.getHouseDescFid());
		houseDesc.setHouseDesc(houseBaseMsg.getHouseDesc());
		houseDescDao.updateHouseDesc(houseDesc);

		//TODO 从redis中获取房源物理信息进行保存
	}

	/**
	 * 查询房源基础信息与房源描述信息
	 *
	 * @author liujun
	 * @created 2016年4月3日 下午10:32:23
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HouseBaseMsgDto findHouseBaseMsgAndHouseDesc(String houseBaseFid) {
		return houseBaseMsgDao.findHouseBaseMsgAndHouseDesc(houseBaseFid);
	}

	/**
	 * 保存房源图片信息
	 *
	 * @author liujun
	 * @created 2016年4月5日 下午5:24:42
	 *
	 * @param housePicMsg
	 */
	public void saveHousePicMsg(HousePicMsgEntity housePicMsg) {
		housePicMsgDao.insertHousePicMsg(housePicMsg);
		houseBaseMsgDao.updateHousePic(housePicMsg.getHouseBaseFid());
	}

	/**
	 * 根据房源房间逻辑id逻辑删除房间信息
	 *
	 * @author liujun
	 * @created 2016年4月5日 下午6:09:18
	 *
	 * @param houseRoomFid
	 */
	public void deleteHouseRoomMsgByFid(String houseRoomFid) {
		houseRoomMsgDao.deleteHouseRoomMsgByFid(houseRoomFid);

		List<HousePicMsgEntity> picList = housePicMsgDao.findRoomPicList(houseRoomFid);
		for (HousePicMsgEntity housePicMsgEntity : picList) {
			housePicMsgDao.deleteHousePicMsgByFid(housePicMsgEntity.getFid());
		}

		List<HouseBedMsgEntity> bedList = houseBedMsgDao.findBedListByRoomFid(houseRoomFid);
		for (HouseBedMsgEntity houseBedMsgEntity : bedList) {
			houseBedMsgDao.deleteHouseBedMsgByFid(houseBedMsgEntity.getFid());
		}
	}


	public void deleteHouseRoomMsgByFidAndDefaultNext(String houseRoomFid,String defaultFid) {
		//1.将当前的房间删除
		houseRoomMsgDao.deleteHouseRoomMsgByFid(houseRoomFid);
		//2. 将第二个房间设置为默认房间
		houseRoomMsgDao.defaultHouseRoomMsgByFid(defaultFid);
	}

	/**
	 * 根据房源床位逻辑id逻辑删除床位信息
	 * 1.删除床位信息
	 * 2.更新当前房间的床数量
	 *
	 * @author liujun
	 * @created 2016年4月5日 下午6:31:39
	 *
	 * @param houseBedFid
	 */
	public void deleteHouseBedMsgByFid(String houseBedFid) {

		if(!Check.NuNStr(houseBedFid)){
			HouseBedMsgEntity bed = this.houseBedMsgDao.findHouseBedMsgByFid(houseBedFid);

			if(!Check.NuNObj(bed)){
				int num = 	houseBedMsgDao.deleteHouseBedMsgByFid(houseBedFid);
				if(num>0){
					HouseRoomMsgEntity houseRoom = this.houseRoomMsgDao.findHouseRoomMsgByFid(bed.getRoomFid());
					if(Check.NuNObj(houseRoom)||houseRoom.getBedNum()<0){
						LogUtil.error(LOGGER, "删除床位fid={}，删除失败，当前房间不存在,或者当前房间床位数量错误", houseBedFid);
						throw new BusinessException();
					}
					LogUtil.info(LOGGER, "删除床位的房间信息houseRoom={}", houseRoom.toJsonStr());
					HouseRoomMsgEntity houseR = new HouseRoomMsgEntity();
					houseR.setBedNum(houseRoom.getBedNum()-1);
					houseR.setFid(houseRoom.getFid());
					houseR.setLastModifyDate(new Date());
					this.houseRoomMsgDao.updateHouseRoomMsg(houseR);
				}
			}
		}




	}

	/**
	 * 根据房源图片逻辑id逻辑删除图片信息
	 *
	 * @author liujun
	 * @created 2016年4月5日 下午7:05:46
	 *
	 * @param housePicFid
	 */
	public void deleteHousePicMsgByFid(String housePicFid) {
		housePicMsgDao.deleteHousePicMsgByFid(housePicFid);
	}

	/**
	 * 
	 * 根据图片fid集合删除逻辑删除图片信息
	 *
	 * @author lunan
	 * @created 2016年10月28日 下午8:43:26
	 *
	 * @param picFids
	 */
	public void delAllHousePicMsgByFids(String picFids){
		housePicMsgDao.delAllHousePicMsgByFid(picFids);
	}

	/**
	 * 
	 * 逻辑删除整个房源
	 * 1.删除房源下所有房间，房间下所有床铺信息
	 *2.删除房源所有图片
	 *3.删除房源所有配置信息
	 *4.删除房源描述信息
	 * @author jixd
	 * @created 2016年6月13日 下午4:36:41
	 *
	 * @param houseFid
	 */
	public int deleteHouseByFid(String houseFid){
		//删除房源基本信息
		int count = houseBaseMsgDao.deleteHouseBaseMsgByFid(houseFid);
		if(count > 0){
			//删除床位信息
			houseBedMsgDao.deleteHouseBedMsgByHouseFid(houseFid);
			//删除房间信息
			houseRoomMsgDao.deleteHouseRoomMsgByHouseFid(houseFid);
			//删除房源图片信息
			housePicMsgDao.deleteHousePicMsgByHouseFid(houseFid);
			//删除房源配置信息
			houseConfMsgDao.deleteHouseConfMsgByHouseFid(houseFid);
			//删除房源描述信息
			houseDescDao.deleteHouseDescByHouseFid(houseFid);
		}
		return count;
	}

	/**
	 * 
	 * 删除房间所有信息
	 * 1 房源下只有该房间，则直接删除该房源
	 * 2.删除房间下的所有床铺信息，图片信息，规则等信息
	 * @author jixd
	 * @created 2016年6月13日 下午4:40:21
	 *
	 * @param houseFid
	 * @param roomFid
	 */
	public int deleteRoomByFid(String houseFid,String roomFid){
		//先判断改房源下是否只有要删除的一个房间，如果只有一个房间，则删除房源，否则删除房间,如果房间fid为空，则删除整个房源
		long roomCount = houseRoomMsgDao.getHouseRoomCount(houseFid);
		int count = 0;
		if(Check.NuNStr(roomFid) || roomCount <= 1){
			count = deleteHouseByFid(houseFid);
		}else{
			//删除房间信息
			count = houseRoomMsgDao.deleteHouseRoomMsgByFid(roomFid);
			if(count > 0){
				HouseBaseExtEntity houseBaseExtEntity = new HouseBaseExtEntity();
				houseBaseExtEntity.setHouseBaseFid(houseFid);
                houseBaseExtEntity.setRentRoomNum((int) (roomCount - count));
                //删除房间 更新可出租数量
				houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseBaseExtEntity);
				//删除房间的床铺信息
				houseBedMsgDao.deleteHouseBedMsgByRoomFid(roomFid);
				//删除房间图片信息
				housePicMsgDao.deleteHousePicMsgByRoomFid(roomFid);
				//删除房间价格信息
				housePriceConfDao.deleteHousePriceConfByRoomFid(roomFid);
                //删除房间配置信息
                houseConfMsgDao.delHouseRoomConf(houseFid, roomFid);
            }

		}
		return count;
	}

	/**
	 * 根据房源图片逻辑id查询图片信息
	 *
	 * @author liujun
	 * @created 2016年4月5日 下午8:33:19
	 *
	 * @param housePicFid
	 * @return
	 */
	public HousePicMsgEntity findHousePicMsgByFid(String housePicFid) {
		return housePicMsgDao.findHousePicMsgByFid(housePicFid);
	}

	/**
	 * 更新房源房间表信息
	 *
	 * @author liujun
	 * @created 2016年4月5日 下午11:28:01
	 *
	 * @param houseRoomMsg
	 */
	public void updateHouseRoomMsg(HouseRoomMsgEntity houseRoomMsg) {
		houseRoomMsgDao.updateHouseRoomMsg(houseRoomMsg);
	}

	/**
	 * (新增|更新)房源基础信息表
	 *
	 * @author liujun
	 * @created 2016年4月9日 下午4:08:51
	 *
	 * @param houseBaseMsg
	 * @return 
	 */
	public String mergeHouseBaseMsg(HouseBaseMsgEntity houseBaseMsg) {
		String houseBaseFid = houseBaseMsg.getFid();
		if(Check.NuNStr(houseBaseFid)){//新增
			// TODO 1.Controller添加houseSource 2.operateSeq,intactRate通过前台传递
			houseBaseFid = UUIDGenerator.hexUUID();
			houseBaseMsg.setFid(houseBaseFid);
			houseBaseMsg.setCreateDate(new Date());
			houseBaseMsgDao.insertHouseBaseMsg(houseBaseMsg);
		}else {
			houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);
		}
		return houseBaseFid;
	}

	/**
	 * 根据房源基础信息逻辑id查询基础信息与基础信息扩展
	 *
	 * @author liujun
	 * @created 2016年4月9日 下午4:40:06
	 *
	 * @param houseBaseFid
	 * @return 
	 */
	public HouseBaseExtDto findHouseBaseExtDtoByHouseBaseFid(String houseBaseFid) {
		return houseBaseMsgDao.findHouseBaseExtDtoByHouseBaseFid(houseBaseFid);
	};

	/**
	 * 
	 * 查询图片数量
	 * 
	 * @author bushujie
	 * @created 2016年4月9日 下午4:41:27
	 *
	 * @param houseBaseFid
	 * @param houseRoomFid
	 * @param picType
	 */
	public int getHousePicCount(String houseBaseFid,String houseRoomFid,Integer picType){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("houseBaseFid", houseBaseFid);
		paramMap.put("picType", picType);
		paramMap.put("roomFid", houseRoomFid);
		return housePicMsgDao.getHousePicCount(paramMap);
	}

	/**
	 * 查询图片集合
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/22 10:49
	 */
	public List<HousePicMsgEntity> getHousePicList(String houseBaseFid,String houseRoomFid,Integer picType){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("houseBaseFid", houseBaseFid);
		paramMap.put("picType", picType);
		paramMap.put("roomFid", houseRoomFid);
		return housePicMsgDao.getHousePicList(paramMap);
	}

	/**
	 * 根据房源基础信息逻辑id更新基础信息与基础信息扩展
	 *
	 * @author liujun
	 * @created 2016年4月9日 下午8:40:04
	 *
	 * @param houseBase
	 */
	public void updateHouseBaseAndExt(HouseBaseExtDto houseBase) throws Exception{

		HouseBaseMsgEntity houseBaseMsgEntity = new HouseBaseMsgEntity();
		BeanUtils.copyProperties(houseBase,houseBaseMsgEntity);
		HouseBaseExtEntity houseBaseExtEntity = houseBase.getHouseBaseExt();

		/*审核未通过和上架房源的需要审核字段的修改暂不入库，后台人员审核后统一入库，@Author:lusp  @Date:2017/8/7*/
		List<HouseUpdateFieldAuditManagerEntity> houseUpdateFieldAuditManagerEntities=null;
		HouseBaseMsgEntity houseBaseMsg=houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseBase.getFid());
		if(!Check.NuNObj(houseBaseMsg)){
			if(houseBaseMsg.getHouseStatus()==HouseStatusEnum.ZPSHWTG.getCode()){
				houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(0);
				houseBaseMsgEntity = HouseUtils.FilterAuditField(houseBaseMsgEntity,houseUpdateFieldAuditManagerEntities);
				houseBaseExtEntity = HouseUtils.FilterAuditField(houseBaseExtEntity,houseUpdateFieldAuditManagerEntities);
			}
			if(houseBaseMsg.getHouseStatus()==HouseStatusEnum.SJ.getCode()){
				houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(1);
				houseBaseMsgEntity = HouseUtils.FilterAuditField(houseBaseMsgEntity,houseUpdateFieldAuditManagerEntities);
				houseBaseExtEntity = HouseUtils.FilterAuditField(houseBaseExtEntity,houseUpdateFieldAuditManagerEntities);
			}
		}
		/*审核未通过和上架房源的需要审核字段的修改暂不入库，后台人员审核后统一入库，@Author:lusp  @Date:2017/8/7*/

		// 1.更新房源基础信息
		houseBaseMsgDao.updateHouseBaseMsg(houseBase);
		// 2.更新房源基础信息扩展
		houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseBaseExtEntity);
	}

	/**
	 * 根据房源基础信息逻辑id查询房间数量
	 *
	 * @author liujun
	 * @created 2016年4月9日 下午10:06:34
	 *
	 * @param houseBaseFid 
	 * @return
	 */
	public long getHouseRoomCount(String houseBaseFid) {
		return houseRoomMsgDao.getHouseRoomCount(houseBaseFid);
	}

	/**
	 * 获取当前房源下的默认房间数量
	 * @author afi
	 * @param houseBaseFid
	 * @return
	 */
	public long getHouseDefaultRoomCount(String houseBaseFid) {
		return houseRoomMsgDao.getHouseDefaultRoomCount(houseBaseFid);
	}

	/**
	 * 获取当前房间下的房源的默认房间数量
	 * @author afi
	 * @param roomFid
	 * @return
	 */
	public long getHouseDefaultRoomCountByRoomFid(String roomFid) {
		return houseRoomMsgDao.getHouseDefaultRoomCountByRoomFid(roomFid);
	}

	/**
	 * 根据图片类型查询默认图片
	 *
	 * @author liujun
	 * @created 2016年4月20日 下午4:32:43
	 *
	 * @param houseBaseFid
	 * @param roomFid
	 * @param picType
	 * @return
	 */
	public HousePicMsgEntity findDefaultPicByType(String houseBaseFid, String roomFid, Integer picType) {
		return housePicMsgDao.findDefaultPicByType(houseBaseFid, roomFid, picType);
	}

	/**
	 * 根据房源基础逻辑id查询房源物理信息
	 *
	 * @author liujun
	 * @created 2016年4月29日 下午7:03:07
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HousePhyMsgEntity findHousePhyMsgByHouseBaseFid(String houseBaseFid) {
		return housePhyMsgDao.findHousePhyMsgByHouseBaseFid(houseBaseFid);
	}

	/**
	 * (新增|更新)房源物理信息表
	 *
	 * @author liujun
	 * @created 2016年4月29日 下午9:26:28
	 *
	 * @param housePhyMsgDto
	 */
	public void mergeHousePhyMsg(HousePhyMsgDto housePhyMsgDto) {
		// 房源逻辑id
		String houseBaseFid = housePhyMsgDto.getHouseBaseFid();

		// 房源基本信息实体
		HouseBaseMsgEntity houseBaseMsg = new HouseBaseMsgEntity();

		// 1.根据小区名称与城市判断房源物理信息是否已存在
		HousePhyMsgEntity housePhyMsg = housePhyMsgDao.findHousePhyMsgByCommuNameAndCityCode(
				housePhyMsgDto.getCommunityName(), housePhyMsgDto.getCityCode());
		// 房源物理逻辑id
		String oldPhyFid = housePhyMsgDto.getFid();
		String newPhyFid;
		if (!Check.NuNObj(housePhyMsg)) {
			newPhyFid = housePhyMsg.getFid();
		} else {
			// 新增房源物理信息
			newPhyFid = UUIDGenerator.hexUUID();
			housePhyMsgDto.setFid(newPhyFid);
			housePhyMsgDto.setCreateDate(new Date());
			// TODO 1.增加民宿楼盘cod 2.保存到redis,最后一步保存,减少无效楼盘保存
			housePhyMsgDao.insertHousePhyMsg(housePhyMsgDto);
		}

		if (Check.NuNStr(oldPhyFid)) {
			// 2.更新房源基础信息
			houseBaseMsg.setFid(houseBaseFid);
			houseBaseMsg.setPhyFid(newPhyFid);
			houseBaseMsg.setOperateSeq(housePhyMsgDto.getOperateSeq());
			houseBaseMsg.setLastModifyDate(new Date());
			houseBaseMsg.setIntactRate(housePhyMsgDto.getIntactRate());
			houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);
		} else {
			// 2.更新房源基础信息
			houseBaseMsg.setFid(houseBaseFid);
			houseBaseMsg.setLastModifyDate(new Date());
			houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);
		}

		if (!newPhyFid.equals(oldPhyFid)) {
			// TODO 删除redis中旧数据
		}

	}

	/**
	 * 组合条件查询图片集合
	 *
	 * @author liujun
	 * @created 2016年4月30日 下午5:35:04
	 *
	 * @param housePicDto
	 * @return
	 */
	public List<HousePicMsgEntity> findHousePicMsgList(HousePicDto housePicDto) {
		return housePicMsgDao.findHousePicMsgList(housePicDto);
	}

	/**
	 * 更新房源基础信息及房间信息集合
	 *
	 * @author liujun
	 * @created 2016年5月4日
	 *
	 * @param houseBaseMsg
	 * @param houseRoomListDto
	 */
	public void updateHouseBaseAndRoomList(HouseBaseMsgEntity houseBaseMsg, HouseRoomListDto houseRoomListDto) {
		//1.更新房源基础信息(操作步骤,信息完整率)
		houseBaseMsg.setOperateSeq(houseRoomListDto.getOperateSeq());
		houseBaseMsg.setIntactRate(houseRoomListDto.getIntactRate());
		houseBaseMsg.setLastModifyDate(new Date());
		houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);

		//2.更新房间信息集合
		List<HouseRoomMsgEntity> roomList = houseRoomListDto.getRoomList();
		for (HouseRoomMsgEntity houseRoomMsg : roomList) {
			houseRoomMsg.setLastModifyDate(new Date());
			houseRoomMsgDao.updateHouseRoomMsg(houseRoomMsg);
		}
	}

	/**
	 * 更新房源基础信息及合并房源描述信息
	 *
	 * @author liujun
	 * @created 2016年5月4日
	 *
	 * @param houseBaseMsg
	 */
	public void updateHouseBaseMsgAndMergeHouseDesc(HouseBaseMsgDto houseBaseMsg) {
		// 1.更新房源基础信息(操作步骤,信息完整率)
		houseBaseMsg.setLastModifyDate(new Date());
		houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);

		// 2.合并房源描述信息
		HouseDescEntity houseDesc = new HouseDescEntity();
		if (Check.NuNStr(houseBaseMsg.getHouseDescFid())) {
			// 新增
			houseDesc.setFid(UUIDGenerator.hexUUID());
			houseDesc.setHouseBaseFid(houseBaseMsg.getFid());
			houseDesc.setHouseDesc(houseBaseMsg.getHouseDesc());
			houseDesc.setCreateDate(new Date());
			houseDescDao.insertHouseDesc(houseDesc);
		} else {
			// 更新
			houseDesc.setFid(houseBaseMsg.getHouseDescFid());
			houseDesc.setHouseDesc(houseBaseMsg.getHouseDesc());
			houseDesc.setLastModifyDate(new Date());
			houseDescDao.updateHouseDesc(houseDesc);
		}
	}

	/**
	 * 发布房源(房东点击确认按钮)
	 *
	 * @author liujun
	 * @created 2016年5月4日
	 *
	 */
	public void issueHouse(HouseBaseMsgEntity houseBaseMsg) {
		int toStatus = HouseStatusEnum.YFB.getCode();
		// 1.保存房源操作日志
		houseCommonLogicDao.saveHouseOperateLogByLandlord(houseBaseMsg, toStatus);

		// 2.级联更新房源下房间及房间下床位状态
		houseCommonLogicDao.cascadingHouseStatus(houseBaseMsg, toStatus);

		// 3.更新房源基础信息
		houseBaseMsg.setHouseStatus(HouseStatusEnum.YFB.getCode());
		houseBaseMsg.setLastModifyDate(new Date());
		houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);

		// 4.保存默认房源信息扩展
		this.saveDefaultHouseBaseExt(houseBaseMsg.getFid());

		// 5.保存默认优惠规则
		this.saveDefaultDiscountRules(houseBaseMsg.getFid());

		// 6.保存默认押金规则
		this.saveDefaultcheckOutRule(houseBaseMsg.getFid());
	}

	/**
	 * 保存默认押金规则
	 *
	 * @author liujun
	 * @created 2016年5月5日
	 *
	 * @param houseBaseFid
	 */
	private void saveDefaultcheckOutRule(String houseBaseFid) {
		List<HouseConfMsgEntity> houseConfMsgList = houseConfMsgDao.findHouseConfList(houseBaseFid);
		boolean checkOutFlag = false;
		for(HouseConfMsgEntity houseConfMsg : houseConfMsgList){
			if(ProductRulesEnum008Enum.ProductRulesEnum008001.getValue().equals(houseConfMsg.getDicCode())){
				checkOutFlag = true;
			}
		}
		//如果没有插入默认的押金规则
		if(!checkOutFlag){
			HouseConfMsgEntity checkOutRules = new HouseConfMsgEntity();
			checkOutRules.setFid(UUIDGenerator.hexUUID());
			checkOutRules.setHouseBaseFid(houseBaseFid);
			checkOutRules.setDicCode(ProductRulesEnum008Enum.ProductRulesEnum008001.getValue());
			checkOutRules.setDicVal(HouseConstant.DEFAULT_DEPOSIT_BY_RENT);
			checkOutRules.setCreateDate(new Date());
			houseConfMsgDao.insertHouseConfMsg(checkOutRules);
		}
	}

	/**
	 * 保存默认优惠规则配置
	 *
	 * @author liujun
	 * @created 2016年5月5日
	 *
	 * @param houseBaseFid
	 */
	private void saveDefaultDiscountRules(String houseBaseFid) {
		List<HouseConfMsgEntity> houseConfList = houseConfMsgDao.findHouseConfList(houseBaseFid);
		boolean threeflag = false;
		boolean sevenflag = false;
		boolean thirtyflag = false;
		for(HouseConfMsgEntity houseConfMsg : houseConfList){
			String dicCode = houseConfMsg.getDicCode();
			if(ProductRulesEnum0012Enum.ProductRulesEnum0012001.getValue().equals(dicCode)){
				threeflag = true;
			}
			if(ProductRulesEnum0012Enum.ProductRulesEnum0012002.getValue().equals(dicCode)){
				sevenflag = true;
			}
			if(ProductRulesEnum0012Enum.ProductRulesEnum0012003.getValue().equals(dicCode)){
				thirtyflag = true;
			}
		}
		if(!threeflag){
			// 3天折扣率
			HouseConfMsgEntity threeDaysDiscount = new HouseConfMsgEntity();
			threeDaysDiscount.setFid(UUIDGenerator.hexUUID());
			threeDaysDiscount.setHouseBaseFid(houseBaseFid);
			threeDaysDiscount.setDicCode(ProductRulesEnum0012Enum.ProductRulesEnum0012001.getValue());
			threeDaysDiscount.setDicVal(HouseConstant.DEFAULT_THREE_DAYS_DISCOUNT);
			threeDaysDiscount.setCreateDate(new Date());
			houseConfMsgDao.insertHouseConfMsg(threeDaysDiscount);
		}
		if(!sevenflag){
			// 7天优折扣率
			HouseConfMsgEntity sevenDaysDiscount = new HouseConfMsgEntity();
			sevenDaysDiscount.setFid(UUIDGenerator.hexUUID());
			sevenDaysDiscount.setHouseBaseFid(houseBaseFid);
			sevenDaysDiscount.setDicCode(ProductRulesEnum0012Enum.ProductRulesEnum0012002.getValue());
			sevenDaysDiscount.setDicVal(HouseConstant.DEFAULT_SEVEN_DAYS_DISCOUNT);
			sevenDaysDiscount.setCreateDate(new Date());
			houseConfMsgDao.insertHouseConfMsg(sevenDaysDiscount);
		}
		if(!thirtyflag){
			// 30天优折扣率
			HouseConfMsgEntity thirtyDaysDiscount = new HouseConfMsgEntity();
			thirtyDaysDiscount.setFid(UUIDGenerator.hexUUID());
			thirtyDaysDiscount.setHouseBaseFid(houseBaseFid);
			thirtyDaysDiscount.setDicCode(ProductRulesEnum0012Enum.ProductRulesEnum0012003.getValue());
			thirtyDaysDiscount.setDicVal(HouseConstant.DEFAULT_THIRTY_DAYS_DISCOUNT);
			thirtyDaysDiscount.setCreateDate(new Date());
			houseConfMsgDao.insertHouseConfMsg(thirtyDaysDiscount);
		}

	}

	/**
	 * 保存默认房源基础信息扩展
	 *
	 * @author liujun
	 * @created 2016年5月5日
	 *
	 * @param houseBaseFid
	 */
	private void saveDefaultHouseBaseExt(String houseBaseFid) {

		HouseBaseExtEntity houseBaseExt = houseBaseExtDao.getHouseBaseExtByHouseBaseFid(houseBaseFid);
		//HouseBaseExtEntity houseBaseExt = new HouseBaseExtEntity();
		houseBaseExt.setHouseBaseFid(houseBaseFid);

		/** 交易信息 **/
		// 下单类型-申请预订
		if(Check.NuNObj(houseBaseExt.getOrderType())){
			houseBaseExt.setOrderType(HouseConstant.DEFAULT_ORDER_TYPE);
		}
		if(Check.NuNObj(houseBaseExt.getHomestayType())){
			// 民宿类型
			houseBaseExt.setHomestayType(HouseConstant.DEFAULT_HOMESTAY_TYPE);
		}
		if(Check.NuNStr(houseBaseExt.getDiscountRulesCode())){
			// 优惠规则
			houseBaseExt.setDiscountRulesCode(ProductRulesEnum.ProductRulesEnum0012.getValue());
		}
		if(Check.NuNStr(houseBaseExt.getDepositRulesCode())){
			// 押金规则
			houseBaseExt.setDepositRulesCode(ProductRulesEnum008Enum.ProductRulesEnum008001.getValue());
		}
		if(Check.NuNStr(houseBaseExt.getCheckOutRulesCode())){
			// 退订政策
			houseBaseExt.setCheckOutRulesCode(TradeRulesEnum005Enum.TradeRulesEnum005002.getValue());
		}
		/** 交易信息 **/

		/** 入住信息 **/
		if(Check.NuNObj(houseBaseExt.getMinDay())){
			// 最小入住天数
			houseBaseExt.setMinDay(HouseConstant.DEFAULT_MIN_DAY);
		}
		if(Check.NuNStr(houseBaseExt.getCheckInTime()) || houseBaseExt.getCheckInTime().equals("0")){
			// 入住时间
			houseBaseExt.setCheckInTime(HouseConstant.DEFAULT_CHECKIN_TIME);
		}
		if(Check.NuNStr(houseBaseExt.getCheckOutTime()) || houseBaseExt.getCheckOutTime().equals("0")){
			// 退房时间
			houseBaseExt.setCheckOutTime(HouseConstant.DEFAULT_CHECKOUT_TIME);
		}
		/** 入住信息 **/

		/** 配套设施与服务项 **/
		if(Check.NuNStr(houseBaseExt.getFacilityCode())){
			// 配套设施
			houseBaseExt.setFacilityCode(ProductRulesEnum.ProductRulesEnum002.getValue());
		}
		if(Check.NuNStr(houseBaseExt.getServiceCode())){
			// 服务
			houseBaseExt.setServiceCode(ProductRulesEnum.ProductRulesEnum0015.getValue());
		}
		/** 配套设施与服务项 **/
		houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseBaseExt);
	}

	/**
	 * 根据房源逻辑id查询房源描述及房源基础扩展信息
	 *
	 * @author liujun
	 * @created 2016年5月5日
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HouseDescDto findHouseDescDtoByHouseBaseFid(String houseBaseFid) {
		return houseDescDao.findHouseDescDtoByHouseBaseFid(houseBaseFid);
	}

	/**
	 * 更新房源房源描述及房源基础信息扩展
	 *
	 * @author liujun
	 * @created 2016年5月5日
	 *
	 * @param houseDescDto
	 */
	public void updateHouseDescAndBaseExt(HouseDescDto houseDescDto) {
		//更新房源周边信息
		if(!Check.NuNStr(houseDescDto.getHouseAroundDesc()) || !Check.NuNStr(houseDescDto.getHouseRules())){
			HouseDescEntity houseDescEntity=houseDescDao.findHouseDescByHouseBaseFid(houseDescDto.getHouseBaseFid());
			if(!Check.NuNObj(houseDescEntity)){
				houseDescDao.updateHouseDescByHouseBaseFid(houseDescDto);
			} else {
				houseDescEntity=new HouseDescEntity();
				houseDescEntity.setHouseBaseFid(houseDescDto.getHouseBaseFid());
				houseDescEntity.setFid(UUIDGenerator.hexUUID());
				houseDescEntity.setHouseAroundDesc(houseDescDto.getHouseAroundDesc() == null ? "":houseDescDto.getHouseAroundDesc());
				houseDescEntity.setHouseRules(houseDescDto.getHouseRules());
				houseDescEntity.setIsDel(0);
				houseDescDao.insertHouseDesc(houseDescEntity);
			}
		}

		// 2.展更新房源房源基础信息扩
		HouseBaseExtEntity houseBaseExtEntity = new HouseBaseExtEntity();
		BeanUtils.copyProperties(houseDescDto, houseBaseExtEntity);
		//避免将houseBaseExtEntity实体中的默认值错误更新，所以用null值替换原有默认值
		houseBaseExtEntity.setCheckOutRulesCode(null);
		houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseBaseExtEntity);
	}

	/**
	 * 
	 * 更新默认房源图片
	 *
	 * @author jixd
	 * @created 2016年5月12日 下午9:50:04
	 *
	 * @param housePicDto
	 * @return
	 */
	public int updateHouseDefaultPic(HousePicDto housePicDto){
		return housePicMsgDao.updateHouseDefaultPic(housePicDto);
	}

	/**
	 * 
	 * 更新房源图片第一张为默认图片
	 *
	 * @author bushujie
	 * @created 2016年5月19日 下午11:20:05
	 *
	 * @param houseBaseFid
	 * @param roomFid
	 * @param picType
	 * @return
	 */
	public int updateDefaultPicLimit(String houseBaseFid,String roomFid,Integer picType){
		Map<String, Object> paramMap=new HashMap<String,Object>();
		paramMap.put("houseBaseFid", houseBaseFid);
		paramMap.put("roomFid", roomFid);
		paramMap.put("picType", picType);
		return housePicMsgDao.updateDefaultPicLimit(paramMap);
	}
	/**
	 * 
	 * 查询是否默认图片
	 *
	 * @author bushujie
	 * @created 2016年5月20日 上午4:06:18
	 *
	 * @param fid
	 * @return
	 */
	public int getDefaultPicByFid(String fid){
		return housePicMsgDao.getDefaultPicByFid(fid);
	}

	/**
	 * 
	 * 查询录入房源房源详情
	 *
	 * @author bushujie
	 * @created 2016年5月26日 上午11:43:54
	 *
	 * @param fid
	 * @return
	 */
	public HouseBaseDetailVo getHouseBaseDetailVoByFid(String fid){
		return houseBaseMsgDao.getHouseBaseDetailVoByFid(fid);
	}

	/**
	 * 
	 * 更新录入房源房源详情
	 *
	 * @author bushujie
	 * @created 2016年5月26日 下午9:07:19
	 *
	 * @param houseBaseDetailVo
	 */
	public void upHouseBaseDetailVoByFid(HouseBaseDetailVo houseBaseDetailVo){
		HouseBaseMsgEntity houseBaseMsgEntity=new HouseBaseMsgEntity();
		BeanUtils.copyProperties(houseBaseDetailVo, houseBaseMsgEntity);
		//设置步骤
		if(houseBaseDetailVo.getIsIssue()==1){
			houseBaseMsgEntity.setOperateSeq(4);
			houseBaseMsgEntity.setHouseStatus(HouseStatusEnum.YFB.getCode());
		}
		//房源fid和房东fid更新房源基本信息
		int upNum=houseBaseMsgDao.updateHouseBaseMsgByUid(houseBaseMsgEntity);
		if(upNum>0){
			//更新房源扩展表信息
			if(!Check.NuNObj(houseBaseDetailVo.getCheckInLimit())||!Check.NuNObj(houseBaseDetailVo.getIsTogetherLandlord())){
				HouseBaseExtEntity houseBaseExtEntity=new HouseBaseExtEntity();
				houseBaseExtEntity.setHouseBaseFid(houseBaseDetailVo.getFid());
				houseBaseExtEntity.setCheckInLimit(houseBaseDetailVo.getCheckInLimit());
				houseBaseExtEntity.setIsTogetherLandlord(houseBaseDetailVo.getIsTogetherLandlord());
				houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseBaseExtEntity);
			}
			//更新房源描述信息和周边情况
			if(!Check.NuNStr(houseBaseDetailVo.getHouseDesc()) || !Check.NuNStr(houseBaseDetailVo.getHouseAroundDesc())){
				HouseDescEntity houseDescEntity=houseDescDao.findHouseDescByHouseBaseFid(houseBaseDetailVo.getFid());
				if(!Check.NuNObj(houseDescEntity)){
					houseDescEntity.setHouseBaseFid(houseBaseDetailVo.getFid());
					houseDescEntity.setHouseDesc(houseBaseDetailVo.getHouseDesc()); 
					houseDescEntity.setHouseAroundDesc(houseBaseDetailVo.getHouseAroundDesc());
					houseDescDao.updateHouseDescByHouseBaseFid(houseDescEntity);
				} else {
					houseDescEntity=new HouseDescEntity();
					houseDescEntity.setHouseBaseFid(houseBaseDetailVo.getFid());
					houseDescEntity.setFid(UUIDGenerator.hexUUID());
					houseDescEntity.setHouseDesc(houseBaseDetailVo.getHouseDesc());
					String houseAroundDesc = houseBaseDetailVo.getHouseAroundDesc();
					houseAroundDesc=Check.NuNStr(houseAroundDesc)?"  ":houseAroundDesc;
					houseDescEntity.setHouseAroundDesc(houseAroundDesc);
					houseDescDao.insertHouseDesc(houseDescEntity);
				}
			}
			//如果发布设置默认值
			if(houseBaseDetailVo.getIsIssue()==1){
				HouseBaseMsgEntity baseMsgEntity=houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseBaseDetailVo.getFid());
				//判断房源是否发布
				if(baseMsgEntity.getHouseStatus()==HouseStatusEnum.YFB.getCode()){
					//房源默认值设置
					saveDefaultHouseBaseExt(houseBaseMsgEntity.getFid());
					//保存默认优惠规则
					saveDefaultDiscountRules(houseBaseMsgEntity.getFid());
					//保存默认押金规则
					saveDefaultcheckOutRule(houseBaseMsgEntity.getFid());
					int toStatus = HouseStatusEnum.YFB.getCode();
					houseBaseMsgEntity.setHouseStatus(HouseStatusEnum.DFB.getCode());
					//保存房源操作日志
					houseCommonLogicDao.saveHouseOperateLogByLandlord(houseBaseMsgEntity, toStatus);
					houseCommonLogicDao.cascadingHouseStatus(houseBaseMsgEntity, toStatus);
				}
			}
		}
	}

	/**
	 * 新增或更新房源基础信息、房源物理信息、房源扩展信息
	 * @author lishaochuan
	 * @create 2016年5月26日下午3:19:50
	 * @param houseMsgVo
	 */
	public String mergeHouseBaseAndPhyAndExt(HouseMsgVo houseMsgVo) {
		// 房源逻辑id
		String houseBaseFid = houseMsgVo.getFid();

		// 房源物理逻辑id
		String phyFid = "";
		HousePhyMsgEntity housePhyMsgNew = houseMsgVo.getHousePhyMsg();

		/*		HousePhyMsgEntity housePhyMsgOld = housePhyMsgDao.findHousePhyMsgByCommuNameAndCityCode(housePhyMsgNew.getCommunityName(), housePhyMsgNew.getCityCode());
		LogUtil.info(LOGGER, "根据小区名称与城市判断房源物理信息是否已存在,housePhyMsgOld={}", housePhyMsgOld);

		if (!Check.NuNObj(housePhyMsgOld)) {
			phyFid = housePhyMsgOld.getFid();
			LogUtil.info(LOGGER, "房源物理信息已存在");
		} else {
			phyFid = UUIDGenerator.hexUUID();
			String buildingCode = DateUtil.dateFormat(new Date(), "yyyyMMddHHmmssSSS");
			housePhyMsgNew.setFid(phyFid);
			housePhyMsgNew.setBuildingCode(buildingCode);
			housePhyMsgNew.setCreateDate(new Date());
			LogUtil.info(LOGGER, "房源物理信息不存在，新增房源物理信息,housePhyMsgNew={}", housePhyMsgNew.toJsonStr());

			// TODO 1.增加民宿楼盘cod 2.保存到redis,最后一步保存,减少无效楼盘保存
			housePhyMsgDao.insertHousePhyMsg(housePhyMsgNew);

		}*/


		if (Check.NuNStr(houseBaseFid)) {

			//房源编号第一次生成之后，不在做修改
			String houseSn = HouseUtils.getHouseOrRoomSn(housePhyMsgNew.getNationCode(), housePhyMsgNew.getCityCode(),RentWayEnum.HOUSE.getCode(), null);
			if(!Check.NuNStr(houseSn)){
				int i = 0;
				while (i<3) {
					Long count = 	this.houseBaseMsgDao.countByHouseSn(houseSn);
					if(count>0){
						i++;
						houseSn = HouseUtils.getHouseOrRoomSn(housePhyMsgNew.getNationCode(), housePhyMsgNew.getCityCode(),RentWayEnum.HOUSE.getCode(), null);
						continue;
					}
					break;
				}
			}
			houseMsgVo.setHouseSn(houseSn);
			phyFid = UUIDGenerator.hexUUID();
			String buildingCode = DateUtil.dateFormat(new Date(), "yyyyMMddHHmmssSSS");
			housePhyMsgNew.setFid(phyFid);
			housePhyMsgNew.setBuildingCode(buildingCode);
			housePhyMsgNew.setCreateDate(new Date());
			LogUtil.info(LOGGER, "新增房源物理信息,housePhyMsgNew={}", housePhyMsgNew.toJsonStr());
			housePhyMsgDao.insertHousePhyMsg(housePhyMsgNew);


			LogUtil.info(LOGGER, "新增房源基础信息表，扩展表，房间表,houseBaseFid={}", houseBaseFid);

			houseBaseFid = UUIDGenerator.hexUUID();
			houseMsgVo.setFid(houseBaseFid);
			houseMsgVo.setPhyFid(phyFid);
			houseMsgVo.setCreateDate(new Date());
			houseMsgVo.setLastModifyDate(new Date());

			int num = houseBaseMsgDao.insertHouseBaseMsg(houseMsgVo);
			if(num != 1){
				LogUtil.error(LOGGER, "房源基础信息插入失败,num:{},houseMsgVo:{}", num, houseMsgVo);
				throw new BusinessException("房源基础信息插入失败");
			}

			HouseBaseExtEntity houseBaseExt = houseMsgVo.getHouseBaseExt();
			houseBaseExt.setFid(UUIDGenerator.hexUUID());
			houseBaseExt.setHouseBaseFid(houseBaseFid);
			num = houseBaseExtDao.insertHouseBaseExt(houseBaseExt);
			if(num != 1){
				LogUtil.error(LOGGER, "房源扩展信息插入失败,num:{},houseBaseExt:{}", num, houseBaseExt);
				throw new BusinessException("房源扩展信息插入失败");
			}

			//添加房源管家
			HouseGuardRelEntity houseGuardRel =houseMsgVo.getHouseGuardRel();
			if(!Check.NuNObj(houseGuardRel)){
				houseGuardRel.setHouseFid(houseBaseFid);
				this.houseGuardRelDao.insertHouseGuardRel(houseGuardRel);
			}



		} else {

			int phyHouseCount=houseBaseMsgDao.findHouseCountByPhyFid(housePhyMsgNew.getFid(),houseBaseFid);
			if(phyHouseCount>0){//有其他房源用
				//保存房源物理信息表
				HousePhyMsgEntity housePhyMsgEntity=new HousePhyMsgEntity();
				BeanUtils.copyProperties(housePhyMsgNew, housePhyMsgEntity);
				phyFid = UUIDGenerator.hexUUID();
				housePhyMsgEntity.setFid(phyFid);
				housePhyMsgEntity.setBuildingCode(DateUtil.dateFormat(new Date(), "yyyyMMddHHmmssSSS"));
				housePhyMsgDao.insertHousePhyMsg(housePhyMsgEntity);
			} else {
				//更新房源物理信息表
				HousePhyMsgEntity housePhyMsgEntity=new HousePhyMsgEntity();
				BeanUtils.copyProperties(housePhyMsgNew, housePhyMsgEntity);
				housePhyMsgDao.updateHousePhyMsg(housePhyMsgEntity);
				phyFid=housePhyMsgEntity.getFid();
			}

			LogUtil.info(LOGGER, "修改房源基础信息表，扩展表,houseBaseFid={}", houseBaseFid);

			houseMsgVo.setPhyFid(phyFid);
			houseMsgVo.setLastModifyDate(new Date());
			int num = houseBaseMsgDao.updateHouseBaseMsgByUid(houseMsgVo);
			if (num != 1) {
				LogUtil.error(LOGGER, "房源基础信息更新失败,num:{},houseMsgVo:{}", num, houseMsgVo);
				throw new BusinessException("房源基础信息更新失败");
			}

			HouseBaseExtEntity houseBaseExt = houseMsgVo.getHouseBaseExt();
			houseBaseExt.setHouseBaseFid(houseBaseFid);
			num = houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseBaseExt);

			if(num != 1){
				LogUtil.error(LOGGER, "房源扩展信息更新失败,num:{},houseBaseExt:{}", num, houseBaseExt);
				throw new BusinessException("房源扩展信息更新失败");
			}
			/*HousePhyMsgEntity phyMsg = houseMsgVo.getHousePhyMsg();
			phyMsg.setFid(phyFid);
			int phyNum = housePhyMsgDao.updateHousePhyMsg(phyMsg);
			if(phyNum != 1){
				LogUtil.error(LOGGER, "房源物理信息,phyNum:{},housePhyMsg:{}", phyNum, phyMsg);
				throw new BusinessException("房源扩展信息更新失败");
			}*/

			HouseGuardRelEntity houseGuardRelEntity=houseGuardRelDao.findHouseGuardRelByHouseBaseFid(houseBaseFid);
			LOGGER.info("管家信息是否存在："+JsonEntityTransform.Object2Json(houseGuardRelEntity));
			if(!Check.NuNObj(houseMsgVo.getHouseGuardRel())&&!Check.NuNObj(houseGuardRelEntity)){
				LOGGER.info("更新管家信息："+JsonEntityTransform.Object2Json(houseMsgVo.getHouseGuardRel()));
				houseGuardRelEntity.setEmpGuardCode(houseMsgVo.getHouseGuardRel().getEmpGuardCode());
				houseGuardRelEntity.setEmpGuardName(houseMsgVo.getHouseGuardRel().getEmpGuardName());
				houseGuardRelEntity.setEmpPushCode(houseMsgVo.getHouseGuardRel().getEmpPushCode());
				houseGuardRelEntity.setEmpPushName(houseMsgVo.getHouseGuardRel().getEmpPushName());
				houseGuardRelDao.updateHouseGuardRelByFid(houseGuardRelEntity);
			}else if(!Check.NuNObj(houseMsgVo.getHouseGuardRel())&&Check.NuNObj(houseGuardRelEntity)){
				LOGGER.info("插入管家信息："+JsonEntityTransform.Object2Json(houseMsgVo.getHouseGuardRel()));
				houseMsgVo.getHouseGuardRel().setHouseFid(houseBaseFid);
				houseGuardRelDao.insertHouseGuardRel(houseMsgVo.getHouseGuardRel());
			}		

		}
		return houseBaseFid;
	}

	/**
	 * 
	 * 更新房源配套设施和服务
	 *
	 * @author bushujie
	 * @created 2016年5月28日 下午4:43:29
	 *
	 * @param list
	 */
	public void updateHouseConf(List<HouseConfMsgEntity> list) {
		//删除配套设施
		houseConfMsgDao.delHouseConfByCode(list.get(0).getHouseBaseFid(), ProductRulesEnum.ProductRulesEnum002.getValue()+"0");//加0防止删除ProductRulesEnum0024
		//删除服务
		houseConfMsgDao.delHouseConfByCode(list.get(0).getHouseBaseFid(), ProductRulesEnum.ProductRulesEnum0015.getValue());
		for(HouseConfMsgEntity houseConfMsgEntity:list){
			houseConfMsgEntity.setFid(UUIDGenerator.hexUUID());
			houseConfMsgDao.insertHouseConfMsg(houseConfMsgEntity);
		}
	}
	
	/**
	 * 
	 * 更新房屋守则中的可选守则
	 *
	 * @author baiwei
	 * @created 2017年4月7日 下午2:06:56
	 *
	 * @param list
	 * @return
	 */
	public void updateSelectHouseRules(List<HouseConfMsgEntity> list,String roomFid){
		//删除可选房屋守则
		houseConfMsgDao.delHouseConfByLikeCode(list.get(0).getHouseBaseFid(),roomFid,ProductRulesEnum.ProductRulesEnum0024.getValue());
		
		if(!Check.NuNCollection(list) && !Check.NuNStr(list.get(0).getDicVal())){
			for(HouseConfMsgEntity houseConfMsgEntity : list){
				houseConfMsgEntity.setFid(UUIDGenerator.hexUUID());
				houseConfMsgEntity.setCreateDate(new Date());
				houseConfMsgEntity.setLastModifyDate(new Date());
				houseConfMsgDao.insertHouseConfMsg(houseConfMsgEntity);
			}
		}
	}
	/**
	 * 
	 * 查询房源或者房间默认图片
	 *
	 * @author bushujie
	 * @created 2016年6月2日 上午3:38:00
	 *
	 * @param fid
	 * @param rentWay
	 * @return
	 */
	public String findDefaultPic(String fid,Integer rentWay){
		HousePicMsgEntity housePicMsgEntity=null;
		if(RentWayEnum.ROOM.getCode()==rentWay){
			housePicMsgEntity=housePicMsgDao.findLandlordRoomDefaultPic(fid);
		}else if(RentWayEnum.HOUSE.getCode()==rentWay){
			housePicMsgEntity=housePicMsgDao.findLandlordHouseDefaultPic(fid);
		}else{
			throw new BusinessException("房源出租方式错误");
		}

		if(Check.NuNObj(housePicMsgEntity)){
			return null;
		}
		return housePicMsgEntity.getPicBaseUrl()+housePicMsgEntity.getPicSuffix();
	}

	/**
	 * 
	 * 详情页发布房源
	 *
	 * @author jixd
	 * @created 2016年6月15日 下午1:26:46
	 *
	 * @return
	 */
	public int issueHouseInDetail(HouseBaseMsgEntity houseBaseMsg, String operaterFid){
		int oldStatus = houseBaseMsg.getHouseStatus();
		int toStatus = HouseStatusEnum.YFB.getCode();
		houseBaseMsg.setHouseStatus(toStatus);
		houseBaseMsg.setLastModifyDate(new Date());
		int count = houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);
		//已发布状态
		if(count > 0){

			//如果发布之前状态为待发布，则更新默认配置，如果用户已有配置则不更改
			if(oldStatus == HouseStatusEnum.DFB.getCode()){
				//房源默认值设置
				saveDefaultHouseBaseExt(houseBaseMsg.getFid());
				//保存默认优惠规则
				saveDefaultDiscountRules(houseBaseMsg.getFid());
				//保存默认押金规则
				saveDefaultcheckOutRule(houseBaseMsg.getFid());
			}
			//更新跟进状态已终结
			if (oldStatus == HouseStatusEnum.ZPSHWTG.getCode()){
				upHouseFollow(houseBaseMsg,null);
			}

			//日志保存之前状态
			houseBaseMsg.setHouseStatus(oldStatus);
			// 保存房源操作日志
			int rentWay  = houseBaseMsg.getRentWay();

			if(rentWay == RentWayEnum.HOUSE.getCode()){
				//保存房源操作日志
				houseCommonLogicDao.saveHouseOperateLogByLandlord(houseBaseMsg, toStatus);

				// 级联更新房间/床位状态
				houseCommonLogicDao.cascadingHouseStatus(houseBaseMsg, toStatus);

			}else{
				// 分租级联更新房间/床位状态
				cascadingHouseStatusF(houseBaseMsg, toStatus);
			}
		}
		//更新新增图片状态为未审核
		Map<String, Object> paramMap=new HashMap<String,Object>();
		paramMap.put("houseBaseFid", houseBaseMsg.getFid());
		housePicMsgDao.updatePicAuditStatusToNo(paramMap);
		return count;
	}
	/**
	 * 
	 * 详情页发布房间
	 *
	 * @author jixd
	 * @created 2016年6月15日 下午1:27:12
	 *
	 * @return
	 */
	public int issueRoomInDetail(HouseRoomMsgEntity houseRoomMsg, String operaterFid){
		int oldStatus = houseRoomMsg.getRoomStatus();
		int toStatus = HouseStatusEnum.YFB.getCode();
		// 更新房间状态
		houseRoomMsg.setRoomStatus(toStatus);
		houseRoomMsg.setLastModifyDate(new Date());
		int count = houseRoomMsgDao.updateHouseRoomMsg(houseRoomMsg);	
		if(count > 0){
			//级联更新床位状态
			houseCommonLogicDao.cascadingRoomStatus(houseRoomMsg, toStatus);
			if(oldStatus == HouseStatusEnum.DFB.getCode()){
				//房源默认值设置
				saveDefaultHouseBaseExt(houseRoomMsg.getHouseBaseFid());
				//保存默认优惠规则
				saveDefaultDiscountRules(houseRoomMsg.getHouseBaseFid());
				//保存默认押金规则
				saveDefaultcheckOutRule(houseRoomMsg.getHouseBaseFid());
			}

			//更新跟进状态已终结
			if (oldStatus == HouseStatusEnum.ZPSHWTG.getCode()){
				HouseBaseMsgEntity houseBaseMsg = houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseRoomMsg.getHouseBaseFid());
				upHouseFollow(houseBaseMsg,houseRoomMsg);
			}

			houseRoomMsg.setRoomStatus(oldStatus);
			houseCommonLogicDao.saveRoomOperateLogByLandlord(houseRoomMsg, toStatus, operaterFid);
		}
		//更新新增图片状态为未审核
		Map<String, Object> paramMap=new HashMap<String,Object>();
		paramMap.put("houseBaseFid", houseRoomMsg.getHouseBaseFid());
		paramMap.put("roomFid", houseRoomMsg.getFid());
		housePicMsgDao.updatePicAuditStatusToNo(paramMap);
		return count;
	}

	/**
	 * 
	 * 查询房源操作日志
	 *
	 * @author jixd
	 * @created 2016年6月15日 下午9:52:47
	 *
	 * @param houseLogSpDto
	 * @return
	 */
	public PagingResult<HouseOperateLogEntity> findOperateLogList(HouseOpLogSpDto houseLogSpDto){
		return houseOperateLogDao.findOperateLogList(houseLogSpDto);
	}

	/**
	 * 为价格不同设定提供的配置项集合查询
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/12/3 12:50
	 */
	public List<HouseConfMsgEntity> findGapAndFlexPrice(HouseConfMsgEntity confMsgEntity){
		return houseConfMsgDao.findGapFlexPriceList(confMsgEntity);
	}

	/**
	 * 
	 * 根据房源fid查询房源描述
	 *
	 * @author jixd
	 * @created 2016年6月16日 下午5:36:22
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HouseDescEntity findhouseDescEntityByHouseFid(String houseBaseFid){
		return houseDescDao.findHouseDescByHouseBaseFid(houseBaseFid);
	}

	/**
	 * 
	 * 查询房源图片，不考虑删除
	 *
	 * @author bushujie
	 * @created 2016年7月12日 下午5:24:33
	 *
	 * @param housePicFid
	 * @return
	 */
	public HousePicMsgEntity findHousePicByFid(String housePicFid){
		return housePicMsgDao.findHousePicByFid(housePicFid);
	}



	/**
	 * 新增或更新房源房源扩展信息、房源描述信息、房源配置信息
	 * @author zl
	 * @created 2016年8月15日 18:30:45
	 * @param dto
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 */
	public int mergeHouseExtAndDesc(HouseBaseExtDescDto dto) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		int num=0;
		LogUtil.info(LOGGER, "参数{}", dto);
		if (dto != null) {
			String houseBaseFid = dto.getHouseBaseFid();
			if (!Check.NuNStr(houseBaseFid)) {
				HouseBaseMsgEntity houseBaseMsgEntity= houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseBaseFid);
				if(!Check.NuNObj(houseBaseMsgEntity)){
					LogUtil.info(LOGGER, "HouseIssueServiceImpl类， mergeHouseExtAndDesc方法，查询房源结果{}", JsonEntityTransform.Object2Json(houseBaseMsgEntity));
					String zkSysValue = zkSysService.getZkSysValue(EnumMinsuConfig.minsu_isOpenNewVersion.getType(), EnumMinsuConfig.minsu_isOpenNewVersion.getCode());
					//如果是分租
					LogUtil.info(LOGGER, "zkIsOpen:{}", zkSysValue);
					if(!Check.NuNObj(houseBaseMsgEntity.getRentWay()) && RentWayEnum.ROOM.getCode()==houseBaseMsgEntity.getRentWay().intValue() && "1".equals(zkSysValue)){	
						//保存或者修改roomEXT
						LogUtil.info(LOGGER, "走分租roomFid:{}", dto.getRoomFid());
						saveOrUpdateRoomExtAndDeposit(dto, houseBaseFid,num);
					}else{
						HouseDescEntity houseDescEntity=new HouseDescEntity();
						houseDescEntity.setHouseBaseFid(houseBaseFid);
						houseDescEntity.setHouseRules(dto.getHouseDescEntity().getHouseRules());
							
						//根据分租和整租查询房源状态
						Integer houseStatus=HouseStatusEnum.DFB.getCode();
						if(!Check.NuNObj(houseBaseMsgEntity)){
							houseStatus=houseBaseMsgEntity.getHouseStatus();
						}
						int code = 0;
						List<HouseUpdateFieldAuditManagerEntity>  houseUpdateFieldAuditManagerEntities = null;
						if(houseStatus == HouseStatusEnum.ZPSHWTG.getCode()||houseStatus == HouseStatusEnum.SJ.getCode()){
							if(houseStatus == HouseStatusEnum.SJ.getCode()) {
								code = 1;
							}
							houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(code);
						}
						//处理审核信息
						houseDescEntity = HouseUtils.FilterAuditField(houseDescEntity, houseUpdateFieldAuditManagerEntities);
						num += houseDescDao.updateHouseDescByHouseBaseFid(houseDescEntity);
						//更新房源扩展信息
						num += houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(dto);
						//更新押金规则
						if (!Check.NuNObj(dto.getHouseConfMsgEntity())) {
							houseConfMsgDao.delHouseConfByCode(houseBaseFid, ProductRulesEnum.ProductRulesEnum008.getValue());
							HouseConfMsgEntity houseConfMsgEntity=new HouseConfMsgEntity();
							houseConfMsgEntity.setFid(UUIDGenerator.hexUUID());
							houseConfMsgEntity.setDicCode(dto.getHouseConfMsgEntity().getDicCode());
							houseConfMsgEntity.setDicVal(dto.getHouseConfMsgEntity().getDicVal());
							houseConfMsgEntity.setHouseBaseFid(houseBaseFid);
							num += houseConfMsgDao.insertHouseConfMsg(houseConfMsgEntity);
						}
					}
					
					/**添加一个判断押金是否修改的判断**/
					Integer houseStatus=HouseStatusEnum.DFB.getCode();
					HouseConfParamsDto confDto=new HouseConfParamsDto();
					confDto.setRentWay(houseBaseMsgEntity.getRentWay());
					confDto.setHouseBaseFid(houseBaseMsgEntity.getFid());
					StringBuilder flagKey=new StringBuilder();
					if (houseBaseMsgEntity.getRentWay()==RentWayEnum.HOUSE.getCode()) {
						houseStatus=houseBaseMsgEntity.getHouseStatus();
						flagKey.append(houseBaseMsgEntity.getFid());
					} else if(houseBaseMsgEntity.getRentWay()==RentWayEnum.ROOM.getCode()&&!Check.NuNStr(dto.getRoomFid())) {
						HouseRoomMsgEntity roomMsgEntity=houseRoomMsgDao.findHouseRoomMsgByFid(dto.getRoomFid());
						houseStatus=roomMsgEntity.getRoomStatus();
						confDto.setRoomFid(dto.getRoomFid());
						flagKey.append(dto.getRoomFid());
					}
					flagKey.append("issue");
					if(houseStatus==HouseStatusEnum.ZPSHWTG.getCode()){
						confDto.setDicCode(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue());
						List<HouseConfMsgEntity> confList=houseConfMsgDao.findHouseConfValidList(confDto);
						if(!Check.NuNCollection(confList)){
							//如果押金有修改
							if(!dto.getHouseConfMsgEntity().getDicVal().equals(confList.get(0).getDicVal())){
								try {
									redisOperations.setex(flagKey.toString(), 24*60*60, "1");
								} catch (Exception e){
									LogUtil.error(LOGGER, "redis保持修改押金标识错误key{},{}",flagKey, e);
								}
							}
						}
					}
					/**添加一个判断押金是否修改的判断**/
					
					if(!Check.NuNObj(dto.getStep()) && !Check.NuNObj(HouseIssueStepEnum.getNameByCode(dto.getStep())) 
							&& !Check.NuNObj(houseBaseMsgEntity.getOperateSeq()) && dto.getStep() > houseBaseMsgEntity.getOperateSeq()){
						houseBaseMsgEntity.setOperateSeq(dto.getStep());
						houseBaseMsgEntity.setIntactRate(HouseIssueStepEnum.getValueByCode(dto.getStep()));
					}
					num += houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsgEntity);
			    }
			}
		}
		return num;
	}

	/**
	 * 
	 * 获取房源基本信息
	 *
	 * @author jixd
	 * @created 2016年8月16日 上午11:08:41
	 *
	 * @param houseFid
	 * @return
	 */
	public HouseBaseMsgEntity findHouseBaseMsgByFid(String houseFid){
		return houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseFid);
	}

	/**
	 * 
	 * pc端发布整租房源
	 *
	 * @author bushujie
	 * @created 2016年8月22日 上午9:20:17
	 *
	 * @param houseBaseMsg
	 * @param operaterFid
	 * @return
	 */
	public int issueHouseForPc(HouseBaseMsgEntity houseBaseMsg, String operaterFid){
		//当前操作步奏 与已操作步奏为1 才更新步奏
		/*if(HouseIssueStepEnum.SIX.getCode()- houseBaseMsg.getOperateSeq() == 1){
			houseBaseMsg.setOperateSeq(HouseIssueStepEnum.SIX.getCode());
			houseBaseMsg.setIntactRate(HouseIssueStepEnum.SIX.getValue());
		}*/
		houseBaseMsg.setOperateSeq(HouseIssueStepEnum.SEVEN.getCode());
		houseBaseMsg.setIntactRate(HouseIssueStepEnum.SEVEN.getValue());
		int oldStatus = houseBaseMsg.getHouseStatus();
		int toStatus = HouseStatusEnum.YFB.getCode();
		houseBaseMsg.setHouseStatus(toStatus);
		houseBaseMsg.setLastModifyDate(new Date());
		int count = houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);
		//已发布状态
		if(count > 0){
			// 级联更新房间/床位状态
			houseCommonLogicDao.cascadingHouseStatus(houseBaseMsg, toStatus);
			//如果发布之前状态为待发布，则更新默认配置，如果用户已有配置则不更改
			if(oldStatus == HouseStatusEnum.DFB.getCode()){
				//房源默认值设置
				saveDefaultHouseBaseExt(houseBaseMsg.getFid());
				//保存默认优惠规则
				saveDefaultDiscountRules(houseBaseMsg.getFid());
				//保存默认押金规则
				saveDefaultcheckOutRule(houseBaseMsg.getFid());
			}

			//更新跟进状态已终结
			if (oldStatus == HouseStatusEnum.ZPSHWTG.getCode()){
				upHouseFollow(houseBaseMsg,null);
			}
			//日志保存之前状态
			houseBaseMsg.setHouseStatus(oldStatus);
			// 保存房源操作日志
			houseCommonLogicDao.saveHouseOperateLogByLandlord(houseBaseMsg, toStatus);
		}
		//更新新增图片状态为未审核
		Map<String, Object> paramMap=new HashMap<String,Object>();
		paramMap.put("houseBaseFid", houseBaseMsg.getFid());
		housePicMsgDao.updatePicAuditStatusToNo(paramMap);
		return count;
	}

	/**
	 * 
	 * pc端发布合租房源
	 *
	 * @author bushujie
	 * @created 2016年8月22日 上午9:19:41
	 *
	 * @param houseRoomMsg
	 * @param operaterFid
	 * @return
	 */
	public int issueRoomForPc(HouseRoomMsgEntity houseRoomMsg, String operaterFid,HouseBaseMsgEntity houseBaseMsg){
		//当前操作步奏 与已操作步奏为1 才更新步奏
	/*	if(HouseIssueStepEnum.SIX.getCode()- houseBaseMsg.getOperateSeq() == 1){
			houseBaseMsg.setOperateSeq(HouseIssueStepEnum.SIX.getCode());
			houseBaseMsg.setIntactRate(HouseIssueStepEnum.SIX.getValue());
			houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);
		}*/
		houseBaseMsg.setOperateSeq(HouseIssueStepEnum.SEVEN.getCode());
		houseBaseMsg.setIntactRate(HouseIssueStepEnum.SEVEN.getValue());
		houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);
		int oldStatus = houseRoomMsg.getRoomStatus();
		int toStatus = HouseStatusEnum.YFB.getCode();
		// 更新房间状态
		houseRoomMsg.setRoomStatus(toStatus);
		houseRoomMsg.setLastModifyDate(new Date());
		int count = houseRoomMsgDao.updateHouseRoomMsg(houseRoomMsg);	
		if(count > 0){
			//级联更新床位状态
			houseCommonLogicDao.cascadingRoomStatus(houseRoomMsg, toStatus);
			if(oldStatus == HouseStatusEnum.DFB.getCode()){
				//房源默认值设置
				saveDefaultHouseBaseExt(houseRoomMsg.getHouseBaseFid());
				//保存默认优惠规则
				saveDefaultDiscountRules(houseRoomMsg.getHouseBaseFid());
				//保存默认押金规则
				saveDefaultcheckOutRule(houseRoomMsg.getHouseBaseFid());
			}
			//更新跟进状态已终结
			if (oldStatus == HouseStatusEnum.ZPSHWTG.getCode()){
				upHouseFollow(houseBaseMsg,houseRoomMsg);
			}

			houseRoomMsg.setRoomStatus(oldStatus);
			houseCommonLogicDao.saveRoomOperateLogByLandlord(houseRoomMsg, toStatus, operaterFid);
		}
		//更新新增图片状态为未审核
		Map<String, Object> paramMap=new HashMap<String,Object>();
		paramMap.put("houseBaseFid", houseBaseMsg.getFid());
		paramMap.put("roomFid", houseRoomMsg.getFid());
		housePicMsgDao.updatePicAuditStatusToNo(paramMap);
		return count;
	}

	/**
	 * 
	 * 更新房源户型信息，以及删除多余的房间（删除房间同时删除床位信息）
	 * 整租房间可全部删除
	 * 分租必须包含一个房间
	 *   说明：
	 * 1.初始化，房源不存在，直接提示不让添加房间
	 * 2.房源存在，带出户型，户型情况：A. 0室   B. 大于 0 室
	 * 3. A.0室处理   房间默认没有  当选择户型，保存入库，  并带出相应的房间展示位置（页面dom操作）
	 * 4. B.大于0室，选择户型，保存入库
	 *       效果： 选择X室， 下面展示相应X个房间（当前已存在且未删除房间数量Y<=X， 若Y<X,dom展示Z = X-Y个房间出来）
	 *     设 数据库当前已保存且未删除房间数量为 M(X>=M>=0)
	 *     4.1 选择X室，数据库查询M
	 *         若M<=X,展示M个房间，展示X-M个dom（房间数量不够，用dom元素展示，不保存入库）的房间数量
	 *         若M>X, 按照房间创建时间排序，删除M-X个房间，查询返回聊表FID,取前M-X个fid逻辑删除
	 *
	 * @author yd
	 * @created 2016年8月20日 下午12:19:28
	 *
	 * @param oldRoomNum
	 * @param rentWay
	 * @param houseBaseMsg
	 * @return  返回未被删除的房源fid集合，可能是空集合
	 */
	public List<String> updateHouseAndRoom(int oldRoomNum,int rentWay,HouseBaseMsgEntity houseBaseMsg){


		List<String> listFid = null;
		if(!Check.NuNObj(houseBaseMsg)){
			LogUtil.info(LOGGER, "房源户型更新开始：oldRoomNum={}，rentWay={}，待更新实体houseBaseMsg={}", oldRoomNum,rentWay,houseBaseMsg.toJsonStr());

			//查询改房源下的房间数量
			listFid = houseRoomMsgDao.getRooFidListByHouseFid(houseBaseMsg.getFid());

			int roomCurNum = 0;
			if(!Check.NuNCollection(listFid)){
				roomCurNum = listFid.size();
				if(roomCurNum>oldRoomNum){
					LogUtil.error(LOGGER, "当前房源实际房间数量多于房源表保存的数量，房源fid={}，roomCurNum={}，oldRoomNum={}",houseBaseMsg.getFid(), roomCurNum,oldRoomNum);
				}
			} 

			//库中房间数多余更新房间数，删除多余房间
			int newRoomNum = houseBaseMsg.getRoomNum().intValue();

			//分租必须保存一个房间
			if(rentWay == RentWayEnum.ROOM.getCode()&&newRoomNum == 0){
				houseBaseMsg.setRoomNum(1);
			}
			//更新房源户型
			int upNum = this.houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);

			LogUtil.info(LOGGER, "更新房间逻辑开始......roomCurNum={},updateRoomNum={}，upNum={}",roomCurNum,houseBaseMsg.getRoomNum().intValue(),upNum);


			List<String> deleteFid = new ArrayList<String>();
			if(roomCurNum>newRoomNum){
				if(upNum>0){
					Collections.reverse(listFid);
					int deleNum  = roomCurNum-newRoomNum;
					//分租必须包含一个房间
					if(rentWay == RentWayEnum.ROOM.getCode()){

						if(roomCurNum>1){
							if(newRoomNum == 0){
								deleNum = roomCurNum-1;
							}
						}else{
							deleNum =  0;
						}

					}
					if(deleNum>0){
						for (String roomfid : listFid) {
							deleteFid.add(roomfid);
							if(deleteFid.size() == deleNum ){
								break;
							}
						}
					}

				}
			}

			LogUtil.info(LOGGER, "待删除房间fids={}", deleteFid);

			//删除房间和当前房间床位信息
			if(!Check.NuNCollection(deleteFid)){
				Long t1 = System.currentTimeMillis();
				LogUtil.info(LOGGER, "删除开始....");
				for (String deFid : deleteFid) {
					deleteHouseRoomMsgByFid(deFid);
				}
				Long t2 = System.currentTimeMillis();
				LogUtil.info(LOGGER, "删除结束....,用时t2-t1={}",t2-t1);
			}
			if(roomCurNum>0){
				listFid.removeAll(deleteFid);
			}
		}

		return  listFid;
	}


	/**
	 * 1.新增房间时新增床铺集合
	 * 2.更新房间时新增(更新)床铺集合
	 *
	 * @author liujun
	 * @created 2016年8月22日
	 *
	 * @param roomMsgVo
	 */
	public void mergeRoomAndBedList(RoomMsgVo roomMsgVo) throws Exception{
		String createUid = roomMsgVo.getCreateUid();
		//bushujie 20170519 添加houseRoomFid是否已添加到数据库的校验
		HouseRoomMsgEntity rme=houseRoomMsgDao.getHouseRoomByFid(roomMsgVo.getHouseRoomFid());
		if(!Check.NuNObj(rme)){
			roomMsgVo.setFid(rme.getFid());
		}
		List<HouseBedMsgEntity> bedList = roomMsgVo.getBedList();
		if(Check.NuNStr(roomMsgVo.getFid())){
			HousePhyMsgEntity housePhyMsg = this.housePhyMsgDao.findHousePhyMsgByHouseBaseFid(roomMsgVo.getHouseBaseFid());
			if (Check.NuNObj(housePhyMsg)) {
				throw new BusinessException("房间物理信息不存在");
			}

			String roomSn = HouseUtils.getHouseOrRoomSn(housePhyMsg.getNationCode(), housePhyMsg.getCityCode(),RentWayEnum.ROOM.getCode(), null);

			if(!Check.NuNStr(roomSn)){
				int i = 0;
				while (i<3) {
					Long count = 	this.houseRoomMsgDao.countByRoomSn(roomSn);
					if(count>0){
						i++;
						roomSn = HouseUtils.getHouseOrRoomSn(housePhyMsg.getNationCode(), housePhyMsg.getCityCode(),RentWayEnum.ROOM.getCode(), null);
						continue;
					}
					break;
				}
			}
//			String roomFid = UUIDGenerator.hexUUID();
			//房间fid在价格设置出生成,独立房间未创建时先设置的价格情境
			String roomFid = roomMsgVo.getHouseRoomFid();
			roomMsgVo.setFid(roomFid);
			roomMsgVo.setRoomSn(roomSn);
			roomMsgVo.setBedNum(bedList.size());
			roomMsgVo.setHouseBaseFid(roomMsgVo.getHouseBaseFid());
			roomMsgVo.setRoomStatus(HouseStatusEnum.DFB.getCode());
			roomMsgVo.setCreateDate(new Date());
			//判断房间数量是否大于或等于可出租数量
			HouseBaseExtEntity houseBaseExtEntity=houseBaseExtDao.getHouseBaseExtByHouseBaseFid(roomMsgVo.getHouseBaseFid());
			int roomNum=(int) houseRoomMsgDao.getHouseRoomCount(roomMsgVo.getHouseBaseFid());
			if(houseBaseExtEntity.getRentRoomNum()>roomNum){
				houseRoomMsgDao.insertHouseRoomMsg(roomMsgVo);
				int bedSn = HouseConstant.DEFAULT_BEDSN;
				for (HouseBedMsgEntity houseBedMsgEntity : bedList) {
					houseBedMsgEntity.setFid(UUIDGenerator.hexUUID());
					houseBedMsgEntity.setBedSn(bedSn++);
					houseBedMsgEntity.setBedPrice(roomMsgVo.getRoomPrice());
					houseBedMsgEntity.setBedStatus(HouseStatusEnum.DFB.getCode());
					houseBedMsgEntity.setRoomFid(roomFid);
					houseBedMsgEntity.setHouseBaseFid(roomMsgVo.getHouseBaseFid());
					houseBedMsgEntity.setCreateUid(createUid);
					houseBedMsgEntity.setCreateDate(new Date());
					this.houseBedMsgDao.insertHouseBedMsg(houseBedMsgEntity);
				}
	
				//更新房源步骤
				HouseBaseMsgEntity houseBase = this.houseBaseMsgDao.getHouseBaseMsgEntityByFid(roomMsgVo.getHouseBaseFid());
	
				if(!Check.NuNObj(houseBase)&&houseBase.getOperateSeq() == HouseIssueStepEnum.FOUR.getCode()){
	
					HouseBaseMsgEntity houseBaseMsgEntity = new HouseBaseMsgEntity();
					houseBaseMsgEntity.setOperateSeq(HouseIssueStepEnum.FIVE.getCode());
					houseBaseMsgEntity.setIntactRate(HouseIssueStepEnum.FIVE.getValue());
					houseBaseMsgEntity.setLastModifyDate(new Date());
					houseBaseMsgEntity.setFid(houseBase.getFid());
					this.houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsgEntity);
	
				}
			}
		} else {
			roomMsgVo.setCreateUid(null);
			roomMsgVo.setLastModifyDate(new Date());

			/****审核未通过的房间修改需要审核字段时暂不入库，审核过后再入库，@Author:lusp @Date:2017/8/7****/
			if(!Check.NuNObj(rme)){
				if(rme.getRoomStatus()==HouseStatusEnum.ZPSHWTG.getCode()
						||rme.getRoomStatus()==HouseStatusEnum.SJ.getCode()){
					int code = 0;
					if(rme.getRoomStatus()==HouseStatusEnum.SJ.getCode()){
						code = 1;
					}
					LogUtil.info(LOGGER, "【待保存房间信息-处理前】roomMsgVo={}", JsonEntityTransform.Object2Json(roomMsgVo));
					List<HouseUpdateFieldAuditManagerEntity> houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(code);
					
					HouseRoomMsgEntity houseRoNew = new HouseRoomMsgEntity();
					BeanUtils.copyProperties(roomMsgVo, houseRoNew);
					houseRoNew = HouseUtils.FilterAuditField(houseRoNew,houseUpdateFieldAuditManagerEntities);
					BeanUtils.copyProperties(houseRoNew, roomMsgVo);
					LogUtil.info(LOGGER, "【待保存房间信息-处理后】roomMsgVo={}", JsonEntityTransform.Object2Json(roomMsgVo));
				}
			}
			/****审核未通过的房间修改需要审核字段时暂不入库，审核过后再入库，@Author:lusp @Date:2017/8/7****/

			houseRoomMsgDao.updateHouseRoomMsg(roomMsgVo);

			List<HouseBedMsgEntity> insertList = new ArrayList<>();
			List<HouseBedMsgEntity> updateList = new ArrayList<>();
			for (HouseBedMsgEntity houseBedMsgEntity : bedList) {
				if(Check.NuNStr(houseBedMsgEntity.getFid())){
					insertList.add(houseBedMsgEntity);
				} else {
					updateList.add(houseBedMsgEntity);
				}
			}

			if(!Check.NuNCollection(updateList)){
				for (HouseBedMsgEntity houseBedMsgEntity : updateList) {
					houseBedMsgDao.updateHouseBedMsg(houseBedMsgEntity);
				}
			}

			if(!Check.NuNCollection(insertList)){
				HouseRoomMsgEntity houseRoomMsgEntity =  this.houseRoomMsgDao.findHouseRoomMsgByFid(roomMsgVo.getFid());
				if(Check.NuNObj(houseRoomMsgEntity)){
					throw new BusinessException("房间信息不存在");
				}
				Integer bedSn = this.houseBedMsgDao.getMaxBedSnByRoomFid(roomMsgVo.getFid());
				if(Check.NuNObj(bedSn)){
					bedSn = HouseConstant.DEFAULT_BEDSN;
				}
				for (HouseBedMsgEntity houseBedMsgEntity : insertList) {
					houseBedMsgEntity.setFid(UUIDGenerator.hexUUID());
					houseBedMsgEntity.setBedSn(bedSn++);
					houseBedMsgEntity.setBedPrice(roomMsgVo.getRoomPrice());
					houseBedMsgEntity.setBedStatus(houseRoomMsgEntity.getRoomStatus());
					houseBedMsgEntity.setRoomFid(roomMsgVo.getFid());
					houseBedMsgEntity.setHouseBaseFid(roomMsgVo.getHouseBaseFid());
					houseBedMsgEntity.setCreateUid(createUid);
					houseBedMsgEntity.setCreateDate(new Date());
					this.houseBedMsgDao.insertHouseBedMsg(houseBedMsgEntity);
				}
			}
			
			//判断床位有没有修改过
			String flagKey=roomMsgVo.getFid()+"issue";
			if(rme.getRoomStatus()==HouseStatusEnum.ZPSHWTG.getCode()&&(!Check.NuNCollection(updateList)||!Check.NuNCollection(insertList))){
				try {
					redisOperations.setex(flagKey.toString(), 24*60*60, "1");
				} catch (Exception e){
					LogUtil.error(LOGGER, "redis合租保存床修改标志key{},{}",flagKey, e.getMessage());
				}
			}
		}
	}



	/**
	 * 
	 * 保存房源床位信息
	 *
	 * @author yd
	 * @created 2016年8月23日 下午10:00:10
	 *
	 * @param roomBed
	 */
	public void saveRoomBedZ(RoomBedZDto roomBed,HouseBaseMsgEntity houseBase){

		if(!Check.NuNObj(roomBed)&&!Check.NuNCollection(roomBed.getListBeds())&&!Check.NuNStr(roomBed.getHouseBaseFid())){

			String roomFid = roomBed.getRoomFid();

			List<HouseBedMsgEntity> listBedMsg = roomBed.getListBeds();

			HousePhyMsgEntity housePhyMsgNew = this.housePhyMsgDao.findHousePhyMsgByHouseBaseFid(roomBed.getHouseBaseFid());
			int bedSn = 100;
			HouseRoomMsgEntity houseRoom = null;
			//重新添加房间以及床位信息
			if(Check.NuNStr(roomFid)){
				houseRoom = new HouseRoomMsgEntity();
				roomFid = UUIDGenerator.hexUUID();
				houseRoom.setFid(roomFid);
				houseRoom.setBedNum(listBedMsg.size());
				houseRoom.setHouseBaseFid(roomBed.getHouseBaseFid());

				String roomSn = HouseUtils.getHouseOrRoomSn(housePhyMsgNew.getNationCode(), housePhyMsgNew.getCityCode(),RentWayEnum.ROOM.getCode(), null);
				if(!Check.NuNStr(roomSn)){
					int i = 0;
					while (i<3) {
						Long count = 	this.houseRoomMsgDao.countByRoomSn(roomSn);
						if(count>0){
							i++;
							roomSn = HouseUtils.getHouseOrRoomSn(housePhyMsgNew.getNationCode(), housePhyMsgNew.getCityCode(),RentWayEnum.ROOM.getCode(), null);
							continue;
						}
						break;
					}
				}
				houseRoom.setRoomSn(roomSn);
				houseRoom.setRoomStatus(houseBase.getHouseStatus());
				houseRoom.setRoomPrice(roomBed.getRoomPrice());
				houseRoom.setCreateDate(new Date());
				houseRoom.setLastModifyDate(houseRoom.getCreateDate());
				houseRoom.setCreateUid(roomBed.getCreatedFid());
				houseRoom.setIsDel(IsDelEnum.NOT_DEL.getCode());

				this.houseRoomMsgDao.insertHouseRoomMsg(houseRoom);

			}else{
				//更新床位信息
				houseRoom =  this.houseRoomMsgDao.findHouseRoomMsgByFid(roomFid);
				if(!Check.NuNObj(houseRoom)){
					houseRoom.setBedNum(listBedMsg.size());
					this.houseRoomMsgDao.updateHouseRoomMsg(houseRoom);
				}
				bedSn = this.houseBedMsgDao.getMaxBedSnByRoomFid(houseRoom.getFid());
				if(Check.NuNObj(bedSn)) bedSn = 99;
				bedSn++;
			}

			if(!Check.NuNObj(houseRoom)){
				LogUtil.info(LOGGER, "保存床位信息开始,当前房间信息houseRoom={}",houseRoom.toJsonStr());
				for (HouseBedMsgEntity houseBedMsgEntity : listBedMsg) {
					if(Check.NuNStr(houseBedMsgEntity.getFid())){
						houseBedMsgEntity.setFid(UUIDGenerator.hexUUID());
						houseBedMsgEntity.setRoomFid(roomFid);
						houseBedMsgEntity.setHouseBaseFid(roomBed.getHouseBaseFid());
						houseBedMsgEntity.setBedStatus(houseRoom.getRoomStatus());
						houseBedMsgEntity.setBedPrice(houseRoom.getRoomPrice());
						houseBedMsgEntity.setBedSn(bedSn);
						houseBedMsgEntity.setCreateDate(new Date());
						houseBedMsgEntity.setLastModifyDate(houseBedMsgEntity.getCreateDate());
						houseBedMsgEntity.setCreateUid(roomBed.getCreatedFid());
						houseBedMsgEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
						this.houseBedMsgDao.insertHouseBedMsg(houseBedMsgEntity);
						bedSn++;

					}
				}
				LogUtil.info(LOGGER, "保存床位信息结束");
			}
		}
	}

	/**
	 * 
	 * 整租保存roomNum个房间
	 *
	 * @author yd
	 * @created 2016年8月26日 下午9:06:02
	 *
	 * @param roomNum
	 * @param houseBase
	 * @return
	 */
	public boolean saveRoomS(int roomNum,HouseBaseMsgEntity houseBase){
		boolean flag = false;

		try {
			if(!Check.NuNObj(houseBase)&&!Check.NuNStr(houseBase.getFid())){
				HousePhyMsgEntity housePhyMsgNew = this.housePhyMsgDao.findHousePhyMsgByHouseBaseFid(houseBase.getFid());

				if(!Check.NuNObj(housePhyMsgNew)) {
					for (int j = 0; j < roomNum; j++) {
						HouseRoomMsgEntity houseRoom = new HouseRoomMsgEntity();
						String roomFid = UUIDGenerator.hexUUID();
						houseRoom.setFid(roomFid);
						houseRoom.setBedNum(0);
						houseRoom.setHouseBaseFid(houseBase.getFid());

						String roomSn = HouseUtils.getHouseOrRoomSn(housePhyMsgNew.getNationCode(), housePhyMsgNew.getCityCode(),RentWayEnum.ROOM.getCode(), null);
						if(!Check.NuNStr(roomSn)){
							int i = 0;
							while (i<3) {
								Long count = 	this.houseRoomMsgDao.countByRoomSn(roomSn);
								if(count>0){
									i++;
									roomSn = HouseUtils.getHouseOrRoomSn(housePhyMsgNew.getNationCode(), housePhyMsgNew.getCityCode(),RentWayEnum.ROOM.getCode(), null);
									continue;
								}
								break;
							}
						}
						houseRoom.setRoomSn(roomSn);
						houseRoom.setRoomStatus(houseBase.getHouseStatus());
						houseRoom.setRoomPrice(0);
						houseRoom.setCreateDate(new Date());
						houseRoom.setLastModifyDate(houseRoom.getCreateDate());
						houseRoom.setCreateUid(houseBase.getLandlordUid());
						houseRoom.setIsDel(IsDelEnum.NOT_DEL.getCode());

						this.houseRoomMsgDao.insertHouseRoomMsg(houseRoom);
					}
					flag = true;
				}

			}
		} catch (Exception e) {
			flag = false;
			LogUtil.error(LOGGER, "整租第5步，点击下一步，保存房间异常e={}", e);
		}

		return flag;
	}
	/**
	 * 
	 * 说明：这里是 M站发布整租房源 第5步，选择户型，添加完房间后，点击下一步的功能
	 * 根据房源fid或房间fid查询床位数量
	 * 
	 *
	 * @author yd
	 * @created 2016年8月24日 下午2:03:55
	 *
	 * @param fid
	 * @param rentWay
	 * @return
	 */
	public Long countBedNumByHouseFid(String fid,int rentWay){

		if(Check.NuNStr(fid)) return 0L;

		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(rentWay == RentWayEnum.HOUSE.getCode()){
			paramMap.put("houseBaseFid", fid);
		}else{
			paramMap.put("roomFid", fid);
		}
		return this.houseBedMsgDao.countBedNumByHouseFid(paramMap);
	}

	/**
	 * 
	 * 发布房源
	 * 发布流程：
	 * 1. 修改房源基础信息
	 * 2. 房源默认值设置
	 * 3. 保存默认优惠规则
	 * 4. 保存默认押金规则
	 * 5. 保存房源操作日志 （ 整租保存房源  分租保存每个房间 ）
	 * 6. 级联更新房间 以及 床位的状态 （整租 和 分租  一样）
	 *
	 * @author yd
	 * @created 2016年8月25日 下午12:01:34
	 *
	 * @param fid
	 *  是否发布 0=不发布  1=发布
	 */
	public boolean  releaseHouse(String fid){


		boolean releaseStatus = false; //发布标识 默认失败
		if(Check.NuNStr(fid)) return releaseStatus;


		HouseBaseMsgEntity houseBaseMsgEntity=houseBaseMsgDao.getHouseBaseMsgEntityByFid(fid);
		if(Check.NuNObj(houseBaseMsgEntity)){
			LogUtil.error(LOGGER, "整租发布房源：根据房源fid={}，查询，房源不存在", fid);
			return releaseStatus;
		}
		int rentWay =  houseBaseMsgEntity.getRentWay();
		if(rentWay != RentWayEnum.HOUSE.getCode()&&rentWay != RentWayEnum.ROOM.getCode()){
			LogUtil.error(LOGGER, "发布房源：出租类型错误，当前房源信息houseBaseExtDto={}", houseBaseMsgEntity.toJsonStr());
			return releaseStatus;
		}

		if(houseBaseMsgEntity.getHouseStatus() != HouseStatusEnum.DFB.getCode()){
			LogUtil.info(LOGGER, "发布房源：房源状态错误,当前房源状态为houseSatus={}，fid={}", houseBaseMsgEntity.getHouseStatus(),houseBaseMsgEntity.getFid());
			return releaseStatus;
		}
		HouseBaseMsgEntity houseBaseMsg=new HouseBaseMsgEntity();
		//设置步骤
		houseBaseMsg.setOldStatus(houseBaseMsgEntity.getHouseStatus());
		houseBaseMsg.setOperateSeq(6);
		houseBaseMsg.setIntactRate(HouseIssueStepEnum.SIX.getValue());
		houseBaseMsg.setHouseStatus(HouseStatusEnum.YFB.getCode());
		houseBaseMsg.setFid(fid);
		houseBaseMsg.setLandlordUid(houseBaseMsgEntity.getLandlordUid());
		houseBaseMsg.setLastModifyDate(new Date());
		houseBaseMsg.setCameramanName(HouseConstant.HOUSE_CAMERAMAN_NAME);
		houseBaseMsg.setCameramanMobile(HouseConstant.HOUSE_CAMERAMAN_MOBILE);

		//房源fid和房东fid更新房源基本信息
		int upNum=houseBaseMsgDao.updateHouseBaseMsgByUid(houseBaseMsg);
		if(upNum>0){

			//分租的话 只要发布 房源状态也切换成 已发布  ，只有在 待发布状态下，才走默认
			if(houseBaseMsg.getOldStatus()==HouseStatusEnum.DFB.getCode()){
				//如果发布设置默认值
				//房源默认值设置
				saveDefaultHouseBaseExt(houseBaseMsgEntity.getFid());
				//保存默认优惠规则
				saveDefaultDiscountRules(houseBaseMsgEntity.getFid());
				//保存默认押金规则
				saveDefaultcheckOutRule(houseBaseMsgEntity.getFid());
				int toStatus = HouseStatusEnum.YFB.getCode();
				houseBaseMsgEntity.setHouseStatus(HouseStatusEnum.DFB.getCode());

				if(rentWay == RentWayEnum.HOUSE.getCode()){
					//保存房源操作日志
					houseCommonLogicDao.saveHouseOperateLogByLandlord(houseBaseMsgEntity, toStatus);
					//级联更新房间 以及 床位的状态 
					houseCommonLogicDao.cascadingHouseStatus(houseBaseMsgEntity, toStatus);
					releaseStatus = true;
				}else{
					//分租发布
					cascadingHouseStatusF(houseBaseMsgEntity, toStatus);
					releaseStatus = true;
				}
			}

		}

		return releaseStatus;

	}

	/**
	 * 分租发布：1. 级联更新房源下房间及房间下床位状态 2.记录操作日志
	 *
	 * @author liujun
	 * @created 2016年4月20日 下午5:17:30
	 *
	 * @param houseBaseMsg
	 * @param toStatus
	 */
	private void cascadingHouseStatusF(HouseBaseMsgEntity houseBaseMsg, int toStatus) {
		List<HouseRoomMsgEntity> roomList = houseRoomMsgDao.findRoomListByHouseBaseFid(houseBaseMsg.getFid());
		if (!Check.NuNCollection(roomList)) {
			LogUtil.info(LOGGER, "当前房源下houseBaseMsg={}，房间集合是roomList={}", houseBaseMsg.toJsonStr(), roomList);
			for (HouseRoomMsgEntity houseRoomMsg : roomList) {
				if (houseRoomMsg.getRoomStatus() == HouseStatusEnum.DFB.getCode()) {
					List<HouseBedMsgEntity> bedList = houseBedMsgDao.findBedListByRoomFid(houseRoomMsg.getFid());
					if (!Check.NuNCollection(bedList)) {
						for (HouseBedMsgEntity houseBedMsg : bedList) {
							houseBedMsg.setBedStatus(toStatus);
							houseBedMsg.setLastModifyDate(new Date());
							houseBedMsgDao.updateHouseBedMsg(houseBedMsg);
						}
						houseCommonLogicDao.saveRoomOperateLogByLandlord(houseRoomMsg, toStatus, houseBaseMsg.getLandlordUid());
						houseRoomMsg.setRoomStatus(toStatus);
						houseRoomMsg.setLastModifyDate(new Date());
						houseRoomMsgDao.updateHouseRoomMsg(houseRoomMsg);
					}

				}
			}
		}
	}

	/**
	 * 
	 * 获取当前房源的押金
	 * 
	 * 1. 固定收取，查询房源配置项，返回，如果没有，返回0元 （分租 整租处理相同）
	 * 2. 按天收取  
	 *    整租 ：查询当前配置项目值，计算押金（按照当前房源基本价格计算）
	 *    分租 ：查询当前配置项目值，计算押金（按照当前房间基本价格计算  返回所有房间最大价格）
	 *
	 * @author yd
	 * @created 2016年11月16日 下午4:23:20
	 *
	 * @param houseFid 房源基本fid
	 * @return
	 */
	@Deprecated
	public  HouseConfMsgEntity findHouseDepositConfByHouseFid(String houseFid,DataTransferObject dto){

		HouseConfVo houseConfVo = this.houseConfMsgDao.findHouseDepositConfByHouseFid(houseFid);
		HouseMsgVo house = this.houseBaseMsgDao.findHouseDetailByFid(houseFid);

		HouseConfMsgEntity houseConfMsg = new HouseConfMsgEntity();
		//默认按固定收取
		houseConfMsg.setDicCode(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue());
		dto.putValue("checkOutRulesCode",TradeRulesEnum005Enum.TradeRulesEnum005002.getValue());

		if(!Check.NuNObj(house)){
			HouseBaseExtEntity ext = house.getHouseBaseExt();
			if(!Check.NuNObj(ext)) dto.putValue("exchange", ext);
		}
		//默认  押金0  适中退订政策
		if(Check.NuNObj(houseConfVo)){
			houseConfVo = new HouseConfVo();
			houseConfVo.setDicCode(ProductRulesEnum008Enum.ProductRulesEnum008001.getValue());
			houseConfVo.setDicValue("1");
		}

		if (Check.NuNStr(houseConfVo.getDicValue())) {
			houseConfVo.setDicValue("0");
		}
		houseConfMsg.setFid(houseConfVo.getFid());
		if(houseConfVo.getDicCode().equals(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue())){
			houseConfMsg.setDicVal(houseConfVo.getDicValue());				
			return houseConfMsg;
		}

		//处理按天收取
		if(houseConfVo.getDicCode().equals(ProductRulesEnum008Enum.ProductRulesEnum008001.getValue())
				&&!Check.NuNObj(house)){

			int rentWay = house.getRentWay();
			int val = Integer.valueOf(houseConfVo.getDicValue());
			if(rentWay == RentWayEnum.HOUSE.getCode()){
				houseConfMsg.setDicVal(Check.NuNObj(house.getLeasePrice())?"0":String.valueOf(house.getLeasePrice()*val));
				return houseConfMsg;
			}
			//分租获取当前房间的最大价格
			if(rentWay == RentWayEnum.ROOM.getCode()){
				HouseRoomMsgEntity rom =  this.houseRoomMsgDao.findMaxRoomPriceByHouseFid(houseFid);
				LogUtil.info(LOGGER, "当前房间信息room={}", Check.NuNObj(rom)?rom:JsonEntityTransform.Object2Json(rom));
				houseConfMsg.setDicVal(Check.NuNObj(rom)?"0":Check.NuNObj(rom.getRoomPrice())?"0":String.valueOf(rom.getRoomPrice()*val));
				return houseConfMsg;
			}
		}

		return houseConfMsg;
	}
	
	/**
	 * 
	 * 查询当前房源押金配置
	 *
	 * @author yd
	 * @created 2016年11月18日 上午10:35:42
	 *
	 * @param houseFid
	 * @param rentWay
	 * @param dto
	 * @return
	 */
	@Deprecated
	public  HouseConfMsgEntity findHouseDepositConfByHouseFid(String houseFid,int rentWay,DataTransferObject dto){
		
		if(rentWay == RentWayEnum.ROOM.getCode()){
			HouseRoomMsgEntity room = this.houseRoomMsgDao.findHouseRoomMsgByFid(houseFid);
			houseFid = "";
			if(!Check.NuNObj(room)) houseFid = room.getHouseBaseFid();
		}
		return findHouseDepositConfByHouseFid(houseFid, dto);
	}
	
	/**
	 * 
	 * 获取当前房源的押金
	 * 
	 * 1. 固定收取，查询房源配置项，返回，如果没有，返回0元 （分租 整租处理相同）
	 * 2. 按天收取  
	 *    整租 ：查询当前配置项目值，计算押金（按照当前房源基本价格计算）
	 *    分租 ：查询当前配置项目值，计算押金（按照当前房间基本价格计算  返回所有房间最大价格）
	 *
	 * @author zl
	 * @created 2017年06月26日 上午11:03:09
	 *
	 * @param houseFid 房源基本fid
	 * @param roomFid  
	 * @param rentWay  
	 * @return
	 */
	public  HouseConfMsgEntity findHouseDepositConfByHouseFid(String houseFid,String roomFid,int rentWay,DataTransferObject dto){

		//查询新版本开关
		Integer isOpenNew=0;
		String isOpenNewS =zkSysService.getZkSysValue(EnumMinsuConfig.minsu_isOpenNewVersion.getType(), EnumMinsuConfig.minsu_isOpenNewVersion.getCode());
		if(!Check.NuNStr(isOpenNewS)){
			isOpenNew=Integer.valueOf(isOpenNewS);
		}
		if(isOpenNew==0 && !Check.NuNStr(roomFid)){//兼容老版本去房源查
			rentWay=0;
		}

		HouseConfVo houseConfVo = houseConfMsgDao.findHouseDepositConfByHouseFid( houseFid, roomFid,rentWay);
		HouseMsgVo house = this.houseBaseMsgDao.findHouseDetailByFid(houseFid);

		HouseConfMsgEntity houseConfMsg = new HouseConfMsgEntity();
		//默认按固定收取
		houseConfMsg.setDicCode(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue());
		dto.putValue("checkOutRulesCode",TradeRulesEnum005Enum.TradeRulesEnum005002.getValue());

		if(!Check.NuNObj(house)){
			HouseBaseExtEntity ext = house.getHouseBaseExt();
			if(!Check.NuNObj(ext)) dto.putValue("exchange", ext);
		}
		//默认  押金0  适中退订政策
		if(Check.NuNObj(houseConfVo)){
			houseConfVo = new HouseConfVo();
			houseConfVo.setDicCode(ProductRulesEnum008Enum.ProductRulesEnum008001.getValue());
			houseConfVo.setDicValue("1");
		}

		if (Check.NuNStr(houseConfVo.getDicValue())) {
			houseConfVo.setDicValue("0");
		}
		houseConfMsg.setFid(houseConfVo.getFid());
		if(houseConfVo.getDicCode().equals(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue())){
			houseConfMsg.setDicVal(houseConfVo.getDicValue());				
			return houseConfMsg;
		}

		//处理按天收取
		if(houseConfVo.getDicCode().equals(ProductRulesEnum008Enum.ProductRulesEnum008001.getValue())
				&&!Check.NuNObj(house)){

			int val = Integer.valueOf(houseConfVo.getDicValue());
			if(rentWay == RentWayEnum.HOUSE.getCode()){
				houseConfMsg.setDicVal(Check.NuNObj(house.getLeasePrice())?"0":String.valueOf(house.getLeasePrice()*val));
				return houseConfMsg;
			}
			//查找分租最大房间价格 
			if(rentWay == RentWayEnum.ROOM.getCode()){
				HouseRoomMsgEntity rom =  this.houseRoomMsgDao.findMaxRoomPriceByHouseFid(houseFid);
				LogUtil.info(LOGGER, "当前房间信息room={}", Check.NuNObj(rom)?rom:JsonEntityTransform.Object2Json(rom));
				houseConfMsg.setDicVal(Check.NuNObj(rom)?"0":Check.NuNObj(rom.getRoomPrice())?"0":String.valueOf(rom.getRoomPrice()*val));
				return houseConfMsg;
			}
		}

		return houseConfMsg;
	}
	
	
	

	/**
	 * 跟新跟进状态已系统终止
	 * @param houseBaseMsgEntity
	 * @param houseRoomMsgEntity
	 */
	public void upHouseFollow(HouseBaseMsgEntity houseBaseMsgEntity,HouseRoomMsgEntity houseRoomMsgEntity){
		HouseFollowListDto houseFollowListDto = new HouseFollowListDto();
		List<Integer> list = new ArrayList<>();
		list.add(FollowStatusEnum.KFDGJ.getCode());
		list.add(FollowStatusEnum.KFGJZ.getCode());
		list.add(FollowStatusEnum.KFWLXSFD.getCode());
		list.add(FollowStatusEnum.ZYDGJ.getCode());
		list.add(FollowStatusEnum.ZYGJZ.getCode());
		houseFollowListDto.setStatusList(list);
		if (Check.NuNObj(houseRoomMsgEntity)){
			//整租
			houseFollowListDto.setHouseBaseFid(houseBaseMsgEntity.getFid());
			houseFollowListDto.setRentWay(RentWayEnum.HOUSE.getCode());
		}else{
			houseFollowListDto.setHouseBaseFid(houseRoomMsgEntity.getHouseBaseFid());
			houseFollowListDto.setRoomFid(houseRoomMsgEntity.getFid());
			houseFollowListDto.setRentWay(RentWayEnum.ROOM.getCode());
		}
		List<HouseFollowEntity> houseFollowEntities = houseFollowDao.listHouseFollowAll(houseFollowListDto);
		if (Check.NuNCollection(houseFollowEntities)){
			return;
		}
		for (HouseFollowEntity followEntity : houseFollowEntities){
			HouseFollowEntity upFollow = new HouseFollowEntity();
			upFollow.setFid(followEntity.getFid());
			upFollow.setFollowStatus(FollowStatusEnum.XTJS.getCode());
			houseFollowDao.updateHouseFollow(upFollow);
			HouseFollowLogEntity houseFollowLogEntity = new HouseFollowLogEntity();
			houseFollowLogEntity.setFid(UUIDGenerator.hexUUID());
			houseFollowLogEntity.setFollowFid(followEntity.getFid());
			houseFollowLogEntity.setFromStatus(followEntity.getFollowStatus());
			houseFollowLogEntity.setToStatus(FollowStatusEnum.XTJS.getCode());
			houseFollowLogEntity.setFollowUserType(1);
			houseFollowLogEntity.setFollowUserFid(houseBaseMsgEntity.getLandlordUid());
			houseFollowLogDao.insertHouseFollowLog(houseFollowLogEntity);
		}
	}
	
	/**
	 * 
	 * 房源扩展信息查询
	 *
	 * @author bushujie
	 * @created 2017年4月5日 下午2:21:34
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HouseBaseExtEntity getHouseBaseExtByHouseBaseFid(String houseBaseFid){
		return houseBaseExtDao.getHouseBaseExtByHouseBaseFid(houseBaseFid);
	}
	
	/**
	 * 
	 * 查询审核未通过次数
	 *
	 * @author baiwei
	 * @created 2017年4月13日 下午8:07:15
	 *
	 * @param houseOperateLogEntity
	 * @return
	 */
	public int findHouseAuditNoLogTime(HouseOperateLogEntity houseOperateLogEntity){
		return houseOperateLogDao.findHouseAuditNoLogTime(houseOperateLogEntity);
	}
	
	/**
	 * 保存或者修改roomExt
	 *
	 * @author loushuai
	 * @created 2017年6月19日 下午7:09:38
	 *
	 * @param dto
	 * @param houseBaseFid
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 */
	
	public int saveOrUpdateRoomExtAndDeposit(HouseBaseExtDescDto dto,String houseBaseFid, int num) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		LogUtil.info(LOGGER, "saveOrUpdateRoomExtAndDeposit方法，发布分租房源，修改roomExt，参数{}", dto);
		if(!Check.NuNStr(dto.getRoomFid())){//此时是从列表页进去
			HouseRoomExtEntity houseRoomExt = houseRoomExtDao.getByRoomfid(dto.getRoomFid());
			if(!Check.NuNObj(houseRoomExt)){
				this.exchangeRoomExt(dto, houseRoomExt);
				num += houseRoomExtDao.updateByRoomfid(houseRoomExt);
				//更新houseConf对象
				this.saveCheckOutRulesConfMsg(dto, houseBaseFid, dto.getRoomFid());
			}else{
				HouseRoomExtEntity newHouseRoomExt = new HouseRoomExtEntity();
				this.exchangeRoomExt(dto, newHouseRoomExt);
				newHouseRoomExt.setFid(UUIDGenerator.hexUUID());
				num += houseRoomExtDao.insertHouseRoomExtSelective(newHouseRoomExt);
				//更新houseConf对象
				this.saveCheckOutRulesConfMsg(dto, houseBaseFid, dto.getRoomFid());
			}
		}else{
			List<HouseRoomMsgEntity> roomMsgList = houseRoomMsgDao.findRoomListByHouseBaseFid(houseBaseFid);
			for (HouseRoomMsgEntity houseRoomMsgEntity : roomMsgList) {
				HouseRoomExtEntity byRoomfid = houseRoomExtDao.getByRoomfid(houseRoomMsgEntity.getFid());
				if(Check.NuNObj(byRoomfid)){//数据库中没有改房间的配置信息
					HouseRoomExtEntity houseRoomExt = new HouseRoomExtEntity();
					this.exchangeRoomExt(dto, houseRoomExt);
					houseRoomExt.setFid(UUIDGenerator.hexUUID());
					houseRoomExt.setRoomFid(houseRoomMsgEntity.getFid());
					num += houseRoomExtDao.insertHouseRoomExtSelective(houseRoomExt);
					//更新houseConf对象
					this.saveCheckOutRulesConfMsg(dto, houseBaseFid, houseRoomMsgEntity.getFid());
				}else{
					this.exchangeRoomExt(dto, byRoomfid);
					byRoomfid.setRoomFid(houseRoomMsgEntity.getFid());
					num += 	houseRoomExtDao.updateByRoomfid(byRoomfid);
					//更新houseConf对象
					this.saveCheckOutRulesConfMsg(dto, houseBaseFid, houseRoomMsgEntity.getFid());
				}
			}
		}
		return num;
	}

	/**
	 * 根据roomFID获取房间扩展信息
	 *
	 * @author loushuai
	 * @created 2017年6月20日 上午10:26:00
	 *
	 * @param roomFid
	 * @return
	 */
	public HouseRoomExtEntity getRoomExtByRoomFid(String roomFid) {
		return  houseRoomExtDao.getByRoomfid(roomFid);
	}
	
	/**
	 * 
	 * 将HouseBaseExtDescDto的相同字段值赋给HouseRoomExtEntity实体对象
	 *
	 * @author loushuai
	 * @created 2017年6月22日 下午6:36:56
	 *
	 * @param dto
	 * @param houseRoomExt
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 */
	public void exchangeRoomExt(HouseBaseExtDescDto dto, HouseRoomExtEntity houseRoomExt) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		BeanUtils.copyProperties(dto, houseRoomExt);
		houseRoomExt.setRoomRules(dto.getHouseDescEntity().getHouseRules());
		if(!Check.NuNStr(dto.getRoomFid())){
			HouseRoomMsgEntity room = houseRoomMsgDao.getHouseRoomByFid(dto.getRoomFid());
			Integer houseStatus=HouseStatusEnum.DFB.getCode();
			if(!Check.NuNObj(room)){
				houseStatus=room.getRoomStatus();
			}
			int code = 0;
			List<HouseUpdateFieldAuditManagerEntity>  houseUpdateFieldAuditManagerEntities = null;
			if(houseStatus == HouseStatusEnum.ZPSHWTG.getCode()||houseStatus == HouseStatusEnum.SJ.getCode()){
				if(houseStatus == HouseStatusEnum.SJ.getCode()) {
					code = 1;
				}
				houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(code);
				houseRoomExt = HouseUtils.FilterAuditField(houseRoomExt, houseUpdateFieldAuditManagerEntities);
			}
		}
		LogUtil.info(LOGGER, "处理完roomExt结果：{}", JsonEntityTransform.Object2Json(houseRoomExt));
	}
	
	/**
	 * 
	 * 保存退订政策枚举
	 *
	 * @author loushuai
	 * @created 2017年6月22日 下午6:42:45
	 *
	 * @param dto
	 * @param houseBaseFid
	 * @return
	 */
	public void saveCheckOutRulesConfMsg(HouseBaseExtDescDto dto,String houseBaseFid, String roomFid){
		Map<String, Object> map = new HashMap<>();
		map.put("houseBaseFid", houseBaseFid);
		map.put("roomFid", roomFid);
		map.put("dicCode", ProductRulesEnum.ProductRulesEnum008.getValue());
		houseConfMsgDao.delHouseConfByParmas(map);
		HouseConfMsgEntity houseConfMsgEntity=new HouseConfMsgEntity();
		houseConfMsgEntity.setFid(UUIDGenerator.hexUUID());
		houseConfMsgEntity.setDicCode(dto.getHouseConfMsgEntity().getDicCode());
		houseConfMsgEntity.setDicVal(dto.getHouseConfMsgEntity().getDicVal());
		houseConfMsgEntity.setHouseBaseFid(houseBaseFid);
		houseConfMsgEntity.setRoomFid(roomFid);
		houseConfMsgDao.insertHouseConfMsg(houseConfMsgEntity);
	}


    /**
     *
     * 发布房源时查询封面图片相关信息
     *
     * @author lusp
     * @created 2017年6月22日 下午22:38:00
     * @param houseBaseParamsDto
     * @return
     */
    public List<HouseDefaultPicInfoVo> findDefaultPicListInfo(HouseBaseParamsDto houseBaseParamsDto){
        List<HouseDefaultPicInfoVo> resultList = new ArrayList<HouseDefaultPicInfoVo>();
        String houseFid = houseBaseParamsDto.getHouseBaseFid();
        String roomFid = houseBaseParamsDto.getRoomFid();
        Integer rentWay = houseBaseParamsDto.getRentWay();
        HouseDefaultPicInfoVo houseDefaultPicInfoVo = null;

        if(RentWayEnum.HOUSE.getCode()==rentWay){
            houseDefaultPicInfoVo = housePicMsgDao.findDefaultPicListInfoByHouseFid(houseFid);
            if(!Check.NuNObj(houseDefaultPicInfoVo)){
                resultList.add(houseDefaultPicInfoVo);
            }
        }else if(RentWayEnum.ROOM.getCode()==rentWay&&Check.NuNStr(roomFid)){
            //此时为分租且没有传roomFid ,此时把每一个房间的默认图片都查出来返回
            //先根据房源fid查询所有的roomFid
            List<String> roomFidList = housePicMsgDao.findRoomfidByHouseFid(houseFid);
            for (int i=0;i<roomFidList.size();i++){
                houseDefaultPicInfoVo = housePicMsgDao.findDefaultPicListInfoByRoomFid(roomFidList.get(i));
                if(!Check.NuNObj(houseDefaultPicInfoVo)){
                    resultList.add(houseDefaultPicInfoVo);
                }
            }
        }else if(RentWayEnum.ROOM.getCode()==rentWay&&!Check.NuNStr(roomFid)){
            houseDefaultPicInfoVo = housePicMsgDao.findDefaultPicListInfoByRoomFid(roomFid);
            if(!Check.NuNObj(houseDefaultPicInfoVo)){
                resultList.add(houseDefaultPicInfoVo);
            }
        }

        return resultList;
    }
    
	/**
	 * 
	 * 查询有效配置项
	 *
	 * @author zl
	 * @created 2017年6月27日 下午2:10:15
	 *
	 * @param paramsDto
	 * @return
	 */
    public List<HouseConfMsgEntity> findHouseConfValidList(HouseConfParamsDto paramsDto){
    	return houseConfMsgDao.findHouseConfValidList(paramsDto);
    }
    
	/**
	 * 查询最后一个品质审核未通过原因
	 * @param paramMap
	 * @return
	 */
	public HouseOperateLogEntity findLastHouseLog(Map<String, Object> paramMap){
		return houseOperateLogDao.findLastHouseLog(paramMap);
	}

	/**
	 * 根据houseBaseFid 查询客厅/房间的fid和roomType
	 * @author yanb
	 * @created 2017年11月19日 23:43:13
	 * @param  * @param houseBaseFid
	 * @return com.ziroom.minsu.services.house.issue.vo.HouseHallVo
	 */
	public HouseHallVo findHall(String houseBaseFid) {
		return houseRoomMsgDao.getHallByHouseBaseFid(houseBaseFid);
	}

	/**
	 * 根据houseBaseFid校验是否为共享客厅
	 * 查询是否存在有效的共享客厅记录
	 * 也可用于给roomType取值(0:为非客厅的roomtype, null也会返回0)
	 * 0:否 1:是
	 */
	public int isHall(String houseBaseFid) {
		Integer roomType=houseRoomMsgDao.getRoomTypeByHouseBaseFid(houseBaseFid);
		if (roomType != null) {
			return roomType;
		}
		return 0;
	}

}

