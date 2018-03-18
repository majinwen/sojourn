package com.ziroom.zrp.service.trading.proxy.commonlogic;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.utils.ZkUtil;
import com.ziroom.zrp.houses.entity.IntellectSmartLockEntity;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.service.houses.api.IntellectSmartLockService;
import com.ziroom.zrp.service.houses.api.ProjectService;
import com.ziroom.zrp.service.houses.api.SmartPlatformService;
import com.ziroom.zrp.service.houses.dto.smartplatform.SmartPlatformAddRentContractDto;
import com.ziroom.zrp.service.houses.dto.smartplatform.SmartPlatformContinueAboutDto;
import com.ziroom.zrp.service.houses.valenum.*;
import com.ziroom.zrp.service.trading.utils.DateConvertUtils;
import com.ziroom.zrp.service.trading.valenum.ZKConfigEnum;
import com.ziroom.zrp.trading.entity.RentContractEntity;
import com.zra.common.utils.KeyGenUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <p>智能锁公共逻辑类</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2018年01月26日 14:43
 * @since 1.0
 */
@Component("trading.smartLockLogic")
public class SmartLockLogic {

	private static final Logger LOGGER = LoggerFactory.getLogger(SmartLockLogic.class);

	@Resource(name = "houses.smartPlatformService")
	private SmartPlatformService smartLockService;

	@Resource(name = "houses.intellectSmartLockService")
	private IntellectSmartLockService roomSmartLockService;

	@Resource(name="houses.projectService")
	private ProjectService projectService;
	/**
	 * 续约延长密码
	 * @param rentContractEntity
	 */
	public void renewAddSmartLockPwd(RentContractEntity rentContractEntity) {
		try {

			SmartPlatformContinueAboutDto continueAboutDto = new SmartPlatformContinueAboutDto();
			continueAboutDto.setHireContractCode(rentContractEntity.getProjectId());
			continueAboutDto.setLogRentContractCode(rentContractEntity.getPreConRentCode());
			continueAboutDto.setRentContractCode(rentContractEntity.getConRentCode());
			String startTime1 = DateConvertUtils.getTodayMin(rentContractEntity.getConStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			String endTime1 =DateConvertUtils.getTodayMax(rentContractEntity.getConEndDate(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			continueAboutDto.setRentContractStartDate(startTime1);
			continueAboutDto.setRentContractEndDate(endTime1);

			boolean isSuccess = true;
			try {
				//调用智能锁接口
				String s1 = this.smartLockService.continueAbout(JsonEntityTransform.Object2Json(continueAboutDto));
				LogUtil.info(LOGGER, "【continueAbout】延长智能锁密码返回:{}", s1);
				DataTransferObject dataTransferObject1 = JsonEntityTransform.json2DataTransferObject(s1);
				if (dataTransferObject1.getCode()== DataTransferObject.ERROR) {
					isSuccess = false;
					LogUtil.error(LOGGER, "【continueAbout】延长智能锁密码失败，contractId：{}",rentContractEntity.getContractId());
				}
			} catch (Exception e) {
				isSuccess = false;
				LogUtil.error(LOGGER, "【continueAbout】延长智能锁dubbo调用失败，contractId：{}",rentContractEntity.getContractId());
			}
			// 保存发送记录
			IntellectSmartLockEntity roomSmartLockEntity = getRoomSmartLockEntity(rentContractEntity, startTime1, endTime1,continueAboutDto.toJsonStr(), IntellectSmartlockSourceTypeEnum.CONTINUEABOUT.getCode(),isSuccess);
			String s = this.roomSmartLockService.saveRoomSmartLock(JsonEntityTransform.Object2Json(roomSmartLockEntity));
			LogUtil.info(LOGGER, "【saveRoomSmartLock】保存智能锁记录返回:{}", s);
			DataTransferObject dataTransferObject = JsonEntityTransform.json2DataTransferObject(s);
			if (dataTransferObject.getCode()== DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "【saveRoomSmartLock】保存智能锁记录失败，contractId：{}",rentContractEntity.getContractId());
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【saveRoomSmartLock】生成智能锁密码失败，contractId：{},e:{}", rentContractEntity.getContractId(), e);
		}
	}

	/**
	 * 添加智能锁密码
	 * @param rentContractEntity
	 */
	public void addSmartLockPwd(RentContractEntity rentContractEntity,RoomInfoEntity roomInfoEntity) {
		try {
			String projectStr = this.projectService.findProjectById(rentContractEntity.getProjectId());
			DataTransferObject projectObj = JsonEntityTransform.json2DataTransferObject(projectStr);
			ProjectEntity projectEntity = projectObj.parseData("projectEntity", new TypeReference<ProjectEntity>() {
			});
			String startTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			String endTime = DateConvertUtils.getTodayMax(rentContractEntity.getConEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			SmartPlatformAddRentContractDto addRentContractDto = new SmartPlatformAddRentContractDto();
			addRentContractDto.setHireContractCode(rentContractEntity.getProjectId());
			addRentContractDto.setRentContractCode(rentContractEntity.getConRentCode());
			addRentContractDto.setVillage(rentContractEntity.getProName());
			addRentContractDto.setUid(rentContractEntity.getCustomerUid());
			addRentContractDto.setUserName(rentContractEntity.getCustomerName());
			addRentContractDto.setUserPhone(rentContractEntity.getCustomerMobile());
			addRentContractDto.setPositionRank1(rentContractEntity.getProjectId());
			if (Check.NuNStr(roomInfoEntity.getParentId())) {
				addRentContractDto.setPositionRank2(rentContractEntity.getRoomId());
			}else{
				addRentContractDto.setPositionRank2(roomInfoEntity.getParentId());
				addRentContractDto.setPositionRank3(roomInfoEntity.getFid());
			}
			addRentContractDto.setCityCode(projectEntity.getFcompanyid());
			addRentContractDto.setCityName(CompanyEnum.getCompanyEnumByCode(projectEntity.getFcompanyid()).getName());
			addRentContractDto.setRoomCode(rentContractEntity.getHouseRoomNo());
			addRentContractDto.setRentContractStartDate(startTime);
			addRentContractDto.setRentContractEndDate(endTime);

			boolean isSuccess = true;
			try {
				// 调用智能锁接口
				String s1 = smartLockService.addRentContract(JsonEntityTransform.Object2Json(addRentContractDto));
				LogUtil.info(LOGGER, "【addRentContract】新增智能锁密码返回:{}", s1);
				DataTransferObject dataTransferObject1 = JsonEntityTransform.json2DataTransferObject(s1);
				if (dataTransferObject1.getCode() == DataTransferObject.ERROR) {
					isSuccess = false;
					LogUtil.error(LOGGER, "【addRentContract】新增智能锁密码失败，contractId：{}", rentContractEntity.getContractId());
				}
			} catch (Exception e) {
				isSuccess = false;
				LogUtil.error(LOGGER, "【addRentContract】调用dubbo服务失败，contractId：{}",rentContractEntity.getContractId());
			}
			// 保存发送记录
			IntellectSmartLockEntity roomSmartLockEntity1 = getRoomSmartLockEntity(rentContractEntity, startTime, endTime, addRentContractDto.toJsonStr(), IntellectSmartlockSourceTypeEnum.ADDRENTCONTRACT.getCode(),isSuccess);
			String s = this.roomSmartLockService.saveRoomSmartLock(JsonEntityTransform.Object2Json(roomSmartLockEntity1));
			LogUtil.info(LOGGER, "【saveRoomSmartLock】保存智能锁记录返回:{}", s);
			DataTransferObject dataTransferObject = JsonEntityTransform.json2DataTransferObject(s);
			if (dataTransferObject.getCode()== DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "【saveRoomSmartLock】保存智能锁记录失败，contractId：{}",rentContractEntity.getContractId());
			}

		} catch (Exception e) {
			LogUtil.error(LOGGER, "【saveRoomSmartLock】生成智能锁密码失败，contractId：{},e:{}", rentContractEntity.getContractId(), e);
		}
	}


	/**
	 * 构建密码锁数据
	 * @param rentContractEntity
	 * @return
	 */
	private IntellectSmartLockEntity getRoomSmartLockEntity(RentContractEntity rentContractEntity,String startTime,String endTime,String paramStr,Integer sourceType,boolean status) throws Exception {
		String serviceId = KeyGenUtils.genKey();
		IntellectSmartLockEntity roomSmartLockEntity = new IntellectSmartLockEntity();
		roomSmartLockEntity.setContractId(rentContractEntity.getContractId());
		roomSmartLockEntity.setCreateTime(new Date());
		roomSmartLockEntity.setPhone(rentContractEntity.getCustomerMobile());
		roomSmartLockEntity.setUserName(rentContractEntity.getCustomerName());
		roomSmartLockEntity.setUserType(SmartUserTypeEnum.SYS.getCode());
		roomSmartLockEntity.setServiceId(serviceId);
		roomSmartLockEntity.setProjectId(rentContractEntity.getProjectId());
		roomSmartLockEntity.setPwdType(SmartLockPwdTypeEnum.PWD_NORMAL.getCode());
		roomSmartLockEntity.setRoomId(rentContractEntity.getRoomId());
		roomSmartLockEntity.setStatus(status? SmartStatusEnum.SEND_SUCCESS.getCode():SmartStatusEnum.SEND_FAILED.getCode());
		roomSmartLockEntity.setUserCode(rentContractEntity.getCustomerUid());
		roomSmartLockEntity.setHandlerType(1);// 用户
		roomSmartLockEntity.setHandlerCode(rentContractEntity.getCustomerUid());
		roomSmartLockEntity.setStartTime(DateConvertUtils.asUtilDate(startTime.replaceAll("\\s+","T")));
		roomSmartLockEntity.setEndTime(DateConvertUtils.asUtilDate(endTime.replaceAll("\\s+", "T")));
		roomSmartLockEntity.setParamStr(paramStr);
		roomSmartLockEntity.setSourceType(sourceType);
		return roomSmartLockEntity;
	}

	/**
	 * 是否开启智能锁
	 * @return
	 * @author cuigh6
	 * @Date 2018年1月
	 */
	public boolean isOpenSmartLock() {
		String result = ZkUtil.getZkSysValue(EnumMinsuConfig.zyu_isOpenSmart.getType(), EnumMinsuConfig.zyu_isOpenSmart.getCode());
		LogUtil.info(LOGGER, "是否开启智能锁ZK配置查询结果:{}", result);
		if ("0".equals(result)) {
			return false;
		}
		return true;
	}
}
