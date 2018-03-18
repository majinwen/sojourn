package com.ziroom.zrp.service.trading.proxy;

import javax.annotation.Resource;

import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.service.trading.dep.RoomDep;
import com.ziroom.zrp.service.trading.dto.EnterpriseCustomerDto;
import com.ziroom.zrp.service.trading.dto.PersonAndSharerDto;
import com.ziroom.zrp.service.trading.dto.PersonalInfoDto;
import com.ziroom.zrp.service.trading.dto.customer.Cert;
import com.ziroom.zrp.service.trading.dto.customer.Extend;
import com.ziroom.zrp.service.trading.service.RentCheckinPersonServiceImpl;
import com.ziroom.zrp.service.trading.service.RentContractServiceImpl;
import com.ziroom.zrp.service.trading.utils.CustomerLibraryUtil;
import com.ziroom.zrp.service.trading.utils.builder.DataTransferObjectBuilder;
import com.ziroom.zrp.trading.entity.RentCheckinPersonEntity;
import com.ziroom.zrp.trading.entity.RentContractEntity;

import com.ziroom.zrp.trading.entity.SharerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.service.trading.api.RentCustomerService;
import com.ziroom.zrp.service.trading.service.RentEpsCustomerServiceImpl;
import com.ziroom.zrp.trading.entity.RentEpsCustomerEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>客户相关信息接口</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年10月23日
 * @since 1.0
 */
@Component("trading.rentCustomerServiceProxy")
public class RentCustomerServiceProxy implements RentCustomerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RentCustomerServiceProxy.class);

	@Resource(name="trading.rentEpsCustomerServiceImpl")
	private RentEpsCustomerServiceImpl rentEpsCustomerServiceImpl;

	@Resource(name="trading.rentContractServiceImpl")
	private RentContractServiceImpl rentContractServiceImpl;

	@Resource(name = "trading.rentCheckinPersonServiceImpl")
	private RentCheckinPersonServiceImpl rentCheckinPersonServiceImpl;

	@Resource(name = "trading.roomDep")
	private RoomDep roomDep;
	/**
	 * <p>保存企业签约人信息</p>
	 * @author xiangb
	 * @created 2017年9月14日
	 * @param
	 * @return
	 */
	@Override
	public String saveRentEpsCustomerService(String epsCustomerJson) {
		LogUtil.info(LOGGER,"【saveRentEpsCustomerService】参数={}",epsCustomerJson);
        DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(epsCustomerJson)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
		
		RentEpsCustomerEntity rentEpsCustomerEntity = JsonEntityTransform
				.json2Object(epsCustomerJson, RentEpsCustomerEntity.class);
		
//		RentEpsCustomerEntity rentEpsCustomerEntity = new RentEpsCustomerEntity();
		int saveSuccess = 0;
		//TODO 需要对重复插入数据做判断
		try{
			RentEpsCustomerEntity rentEpsCustomerEntityCheck = rentEpsCustomerServiceImpl.findRentEpsCustomerByCustomer(rentEpsCustomerEntity);
			if(Check.NuNObj(rentEpsCustomerEntityCheck)){
				saveSuccess = rentEpsCustomerServiceImpl.saveRentEpsCustomer(rentEpsCustomerEntity);
			}else{
				dto.setErrCode(DataTransferObject.ERROR);
	            dto.setMsg("企业签约人信息已存在");
	            return dto.toJsonString();
			}
		}catch(Exception e){
			LogUtil.info(LOGGER, "写入企业签约人信息异常={}", e);
		}
		if(saveSuccess == 1){
			return dto.toJsonString();
		}else{
			dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("企业签约人信息已存在");
            return dto.toJsonString();
		}
	}


	/**
	 * 选从合同中查询是否存在企业客户uid,
	 *   存在：从rent_eps_customer表中查询出客户信息数据返回
	 *   不存在: 使用用户uid 从友家中查询客户信息
	 *
	 * 该方法使用场景:
	 * 企业新签(包括修改功能)
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	@Override
	public String findTempRentEpsCustomerInfo(String paramJson) {
		LogUtil.info(LOGGER,"findTempRentEpsCustomerInfo {}", paramJson);
		if (Check.NuNStrStrict(paramJson)) {
			return DataTransferObjectBuilder.buildErrorJsonStr("参数为空");
		}


		EnterpriseCustomerDto enterpriseCustomerDto = JsonEntityTransform.json2Entity(paramJson, EnterpriseCustomerDto.class);

		RentContractEntity rentContractEntity = rentContractServiceImpl.findOneRentContractByParentId(enterpriseCustomerDto.getSurParentRentId());

		String customerUid =  enterpriseCustomerDto.getCustomerUid();

		String surParentRentId = enterpriseCustomerDto.getSurParentRentId();

		EnterpriseCustomerDto enterpriseCustomerDtoReturn  = new EnterpriseCustomerDto();

		//合同表中存储的是customerUid,对企业签约用户
//		if (!Check.NuNStrStrict(rentContractEntity.getCustomerId()) && !Check.NuNStrStrict(rentContractEntity.getCustomerUid())) {
		if (!Check.NuNStrStrict(rentContractEntity.getCustomerId())) {
			RentEpsCustomerEntity rentEpsCustomerEntity = rentEpsCustomerServiceImpl.findRentEpsCustomerById(rentContractEntity.getCustomerId());
			enterpriseCustomerDtoReturn = transferByRentEpsCustomerEntity(rentEpsCustomerEntity, surParentRentId);
		} else if (!Check.NuNStrStrict(customerUid))  {
			PersonalInfoDto personalInfoDto = CustomerLibraryUtil.findAuthInfoFromCustomer(customerUid);
			//BUG 19882 可以查询出已经保存企业客户的信息
			RentEpsCustomerEntity rentEpsCustomerEntity = rentEpsCustomerServiceImpl.findRentEpsCustomerByCustomerUid(customerUid);
			enterpriseCustomerDtoReturn = transferByPersonalInfoDto(personalInfoDto, enterpriseCustomerDto.getCustomerUid(), surParentRentId);
			if (rentEpsCustomerEntity != null) {
				enterpriseCustomerDtoReturn.setEmail(rentEpsCustomerEntity.getEmail());
			}

		}

		return DataTransferObjectBuilder.buildOkJsonStr("data", enterpriseCustomerDtoReturn);
	}

	/**
	 * 构建
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	private EnterpriseCustomerDto transferByRentEpsCustomerEntity(RentEpsCustomerEntity rentEpsCustomerEntity, String surParentRentId) {
		EnterpriseCustomerDto enterpriseCustomerDto = new EnterpriseCustomerDto();
		enterpriseCustomerDto.setId(rentEpsCustomerEntity.getId());
		enterpriseCustomerDto.setSurParentRentId(surParentRentId);
		enterpriseCustomerDto.setCode(rentEpsCustomerEntity.getCode());
		enterpriseCustomerDto.setName(rentEpsCustomerEntity.getName());
		enterpriseCustomerDto.setAddress(rentEpsCustomerEntity.getAddress());
		enterpriseCustomerDto.setEmail(rentEpsCustomerEntity.getEmail());
		enterpriseCustomerDto.setBusinessLicense(rentEpsCustomerEntity.getBusinessLicense());
		enterpriseCustomerDto.setContacterNum(rentEpsCustomerEntity.getContacterNum());
		enterpriseCustomerDto.setContacter(rentEpsCustomerEntity.getContacter());
		enterpriseCustomerDto.setContacterTel(rentEpsCustomerEntity.getContacterTel());
		enterpriseCustomerDto.setProxyPicurl(rentEpsCustomerEntity.getProxyPicurl());
		enterpriseCustomerDto.setLicensePicurl01(rentEpsCustomerEntity.getLicensePicurl01());
		enterpriseCustomerDto.setLicensePicurl02(rentEpsCustomerEntity.getLicensePicurl02());
		enterpriseCustomerDto.setLicensePicurl03(rentEpsCustomerEntity.getLicensePicurl03());
		enterpriseCustomerDto.setCustomerUid(rentEpsCustomerEntity.getCustomerUid());
		return enterpriseCustomerDto;
	}

	/**
	 *
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	private EnterpriseCustomerDto transferByPersonalInfoDto(PersonalInfoDto personalInfoDto, String customerUid, String surParentRentId) {
		EnterpriseCustomerDto enterpriseCustomerDto = new EnterpriseCustomerDto();
		enterpriseCustomerDto.setSurParentRentId(surParentRentId);

		Extend extend = personalInfoDto.getExtend();
		if (extend != null) {
			enterpriseCustomerDto.setName(extend.getWork_name());
			enterpriseCustomerDto.setAddress(extend.getWork_address());
		}

		Cert cert = personalInfoDto.getCert();
		if (cert != null) {
			enterpriseCustomerDto.setContacter(cert.getReal_name());
			enterpriseCustomerDto.setContacterNum(cert.getCert_num());
		}

		enterpriseCustomerDto.setCustomerUid(customerUid);
		return enterpriseCustomerDto;
	}


	@Override
	public String findRentEpsCustomerByUid(String customerUid) {
		LogUtil.info(LOGGER, "【findRentEpsCustomerByUid】入参：{}", customerUid);
		DataTransferObject dto  = new DataTransferObject();
		if(Check.NuNStr(customerUid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		try{
			RentEpsCustomerEntity rentEpsCustomerEntity = rentEpsCustomerServiceImpl.findRentEpsCustomerByCustomerUid(customerUid);
			if(!Check.NuNObj(rentEpsCustomerEntity)){
				dto.putValue("rentEpsCustomerEntity", rentEpsCustomerEntity);
				return dto.toJsonString();	
			}else{
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("未查询到用户信息");
				return dto.toJsonString();
			}
		}catch(Exception e){
			LogUtil.info(LOGGER, "【findRentEpsCustomerByUid】出错：{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统异常");
			return dto.toJsonString();
		}
	}

	/**
	 * <p>根据id查询企业客户信息</p>
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	@Override
	public String findRentEpsCustomerById(String customerId) {

		LogUtil.info(LOGGER, "【findRentEpsCustomerById】入参：{}", customerId);
		if(Check.NuNStr(customerId)) {
			return DataTransferObjectBuilder.buildErrorJsonStr("参数为空");
		}
		RentEpsCustomerEntity rentEpsCustomerEntity = this.rentEpsCustomerServiceImpl.findRentEpsCustomerById(customerId);
		return DataTransferObjectBuilder.buildOkJsonStr(rentEpsCustomerEntity);
	}


	@Override
	public String saveOrUpdateEnterpriseContractCustomerInfo(String paramInfo) {
		LogUtil.info(LOGGER,"saveOrUpdateEnterpriseContractCustomerInfo {}", paramInfo);
		if (Check.NuNStrStrict(paramInfo)) {
			return DataTransferObjectBuilder.buildErrorJsonStr("参数为空");
		}

		EnterpriseCustomerDto enterpriseCustomerDto = JsonEntityTransform.json2Entity(paramInfo, EnterpriseCustomerDto.class);

		//根据合同中是否存在customerId来判断是修改还是操作.
		List<RentContractEntity> rentContractEntityList = rentContractServiceImpl.findContractListByParentId(enterpriseCustomerDto.getSurParentRentId());
		if (rentContractEntityList == null || rentContractEntityList.size() == 0) {
			return DataTransferObjectBuilder.buildErrorJsonStr("无法查询到合同信息,sur_parent_rent+id:" + enterpriseCustomerDto.getSurParentRentId());
		}
		//修改房间价格 --
		DataTransferObject updateDto = this.updateWqyContractRoomSalesPrice(rentContractEntityList);
		if (updateDto.getCode() == DataTransferObject.ERROR) {
			return updateDto.toJsonString();
		}

		RentContractEntity rentContractEntity = rentContractEntityList.get(0);

		boolean operFlag = false;
		//保存客户信息同时更新合同
		if (Check.NuNStrStrict(rentContractEntity.getCustomerId())) {

			// 测试环境曾经出现过customer id 重复的情况，但不知道是什么情况下出现的，为了兼容问题
			if (!Check.NuNStrStrict(enterpriseCustomerDto.getId())) {
				LogUtil.error(LOGGER, "Duplicate customer errror {}", paramInfo);
				enterpriseCustomerDto.setId("");
			}

			operFlag = rentContractServiceImpl.saveEnterpriseContractCustomerInfo(enterpriseCustomerDto);
		} else {
			//更新客户信息同时更新合同
			operFlag = rentContractServiceImpl.updateEnterpriseContractCustomerInfo(enterpriseCustomerDto);

		}

		if (operFlag) {
			return DataTransferObjectBuilder.buildOkJsonStr("data","保存或更新客户信息成功");
		} else {
			return DataTransferObjectBuilder.buildErrorJsonStr("保存或更新客户信息失败");
		}

	}

	/**
	 * 修改合同表中房间出房价格,合同状态必须为way
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public DataTransferObject updateWqyContractRoomSalesPrice(List<RentContractEntity> rentContractEntityList) {
		String roomIds = rentContractEntityList.stream().map(RentContractEntity::getRoomId).collect(Collectors.joining(","));
		Map<String, RoomInfoEntity> roomMap = roomDep.getRoomMapByRoomIds(roomIds);
		for (RentContractEntity rentContractEntity : rentContractEntityList) {
			RoomInfoEntity roomInfoEntity = roomMap.get(rentContractEntity.getRoomId());
			rentContractEntity.setRoomSalesPrice(roomInfoEntity.getFlongprice());
			boolean flag = this.rentContractServiceImpl.updateWqyContractRoomSalesPrice(rentContractEntity);
			if (!flag ) {
				return DataTransferObjectBuilder.buildError("updateWqyContractRoomSalesPrice error, contractId:" + rentContractEntity.getContractId());
			}
		}

		return DataTransferObjectBuilder.buildOk("修改价格成功");
	}


	/**
	 * 保存或者更新个人用户信息
	 *
	 * @param paramJson {"sharers":"","checkInPerson":""}
	 * @return
	 * @author cuigh6
	 * @Date 2017年10月
	 */
	@Override
	public String saveOrUpdatePersonCustomerInfo(String paramJson) {
		LogUtil.info(LOGGER,"【saveOrUpdatePersonCustomerInfo】入参={}",paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			PersonAndSharerDto param = JsonEntityTransform.json2Object(paramJson,PersonAndSharerDto.class);
			List<SharerEntity> sharers = param.getSharerEntities();
			RentCheckinPersonEntity checkInPerson = param.getRentCheckinPersonEntity();
			if (Check.NuNObj(checkInPerson)) {
				dto.setMsg("入住人参数为空");
				dto.setErrCode(DataTransferObject.ERROR);
				return dto.toJsonString();
			}
			this.rentCheckinPersonServiceImpl.saveOrUpdatePersonCustomerInfo(checkInPerson, sharers);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【saveOrUpdatePersonCustomerInfo】保存失败error:", e);
			dto.setMsg("系统错误");
			dto.setErrCode(DataTransferObject.ERROR);
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

}
