package com.ziroom.zrp.service.trading.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.houses.entity.CityEntity;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.service.houses.dto.RoomStmartDto;
import com.ziroom.zrp.service.houses.dto.SignInviteDto;
import com.ziroom.zrp.service.houses.entity.RoomContractSmartVo;
import com.ziroom.zrp.service.trading.dao.*;
import com.ziroom.zrp.service.trading.dto.*;
import com.ziroom.zrp.service.trading.dto.contract.ContractManageDto;
import com.ziroom.zrp.service.trading.dto.contract.ContractSearchPageDto;
import com.ziroom.zrp.service.trading.entity.CloseContractNotifyVo;
import com.ziroom.zrp.service.trading.entity.RentContractRoomVo;
import com.ziroom.zrp.service.trading.entity.SyncContractVo;
import com.ziroom.zrp.service.trading.pojo.ContractFirstVo;
import com.ziroom.zrp.service.trading.valenum.*;
import com.ziroom.zrp.service.trading.valenum.delivery.DeliveryStateEnum;
import com.ziroom.zrp.trading.entity.*;
import com.zra.common.constant.ContractValueConstant;
import com.zra.common.dto.base.BasePageParamDto;
import com.zra.common.utils.DateUtilFormate;
import com.zra.common.utils.ZraConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>合同相关操作</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月07日 20:03
 * @since 1.0
 */
@Service("trading.rentContractServiceImpl")
public class RentContractServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(RentContractServiceImpl.class);
    @Resource(name = "trading.rentContractDao")
    private RentContractDao rentContractDao;

    @Resource(name = "trading.rentDetailDao")
	private RentDetailDao rentDetailDao;

    @Resource(name="trading.rentEpsCustomerDao")
    private RentEpsCustomerDao rentEpsCustomerDao;

	@Resource(name = "trading.finReceiBillDao")
	private FinReceiBillDao finReceiBillDao;

	@Resource(name = "trading.finReceiBillDetailDao")
	private FinReceiBillDetailDao finReceiBillDetailDao;

	@Resource(name = "trading.rentContractActivityDao")
	private RentContractActivityDao rentContractActivityDao;
	
	@Resource(name = "trading.surrenderDao")
    private SurrenderDao surrenderDao;

    /**
     * 查询合同号
     * @author jixd
     * @created 2017年09月07日 20:05:30
     * @param
     * @return
     */
    public RentContractEntity findValidContractByRentCode(String rentCode){
        return rentContractDao.findValidContractByRentCode(rentCode);
    }

	/**
	 * 查找合同
	 * @param rentCode
	 * @return
	 */
	public RentContractEntity findContractByRentCode(String rentCode){
    	return rentContractDao.findContractByRentCode(rentCode);
	}
    /**
     * <p>查询合同详情</p>
    	 * 
    	 * @author xiangb
    	 * @created 2017年9月12日
    	 * @param contractId
    	 * @return
     */
	public RentContractEntity findContractBaseByContractId(String contractId) {
		return rentContractDao.findContractBaseByContractId(contractId);
	}

	/**
	 * 查询合同列表
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public List<RentContractEntity> findContractListByContractIds(List<String> contractIds) {
		return rentContractDao.findContractListByContractIds(contractIds);
	}
	/**
	 * 更新付款方式
	 * @author jixd
	 * @created 2017年09月13日 17:53:24
	 * @param
	 * @return
	 */
	public int updatePayCodeByContractId(String contractId,String payCode){
		return rentContractDao.updatePayCodeByContractId(contractId,payCode);

	}

	/**
	 * 查询合同详情
	 * @author jixd
	 * @created 2017年09月22日 17:33:08
	 * @param
	 * @return
	 */
	public RentDetailEntity findRentDetailById(ContractRoomDto contractRoomDto){
		return rentDetailDao.findRentDetailById(contractRoomDto);
	}

	/**
	 * @description: 根据合同id查询合同详情集合
	 * @author: lusp
	 * @date: 2018/1/3 下午 17:47
	 * @params: contractId
	 * @return: List<RentDetailEntity>
	 */
	public List<RentDetailEntity> findRentDetailByContractId(String contractId){
		return rentDetailDao.findRentDetailByContractId(contractId);
	}
	/**
	 * @description: 查询同步合同到财务的相关信息
	 * @author: lusp
	 * @date: 2017/9/22 15:28
	 * @params: contractId
	 * @return: SyncContractVo
	 */
	public SyncContractVo findSyncContractVoById(String contractId){
		return rentContractDao.findSyncContractVoById(contractId);
	}

	/**
	 * 批量保存合同信息
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public boolean saveContractFromSignInvite(List<RentContractEntity> rentContractList,Map<String,RoomInfoEntity> roomMap, SignInviteDto signInviteDto){

		//保存合同主表
		rentContractList.forEach(rentContract -> {
			rentContractDao.saveRentContract(rentContract);
		});

		//将合同主表转为明细表
		List<RentDetailEntity> rentDetailList  = transferToRentDetail(rentContractList, roomMap);

		//保存合同明细表
		rentDetailList.forEach( rentDetail -> {
			rentDetailDao.save(rentDetail);
		});

		//续约修改合同状态
		if (!Check.NuNStr(signInviteDto.getPreContractId())) {
			rentContractDao.updateXyzByContractIds(signInviteDto.getPreContractId());
		}
		return true;
	}

	/**
	 * 将合同对象转为RentDetail,因为这个时间合同与detail是一一对应的，所以不会存在问题
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	private List<RentDetailEntity> transferToRentDetail(List<RentContractEntity> rentContractList, Map<String,RoomInfoEntity> roomMap) {
		List<RentDetailEntity> rentDetailEntityList = new ArrayList<RentDetailEntity>();
		rentContractList.forEach( rentContract -> {

			RentDetailEntity rentDetail = new RentDetailEntity();
			rentDetail.setId(UUIDGenerator.hexUUID());
			rentDetail.setContractId(rentContract.getContractId());
			rentDetail.setRoomId(rentContract.getRoomId());
			rentDetail.setDeliveryState(DeliveryStateEnum.WJG.getCode());
			rentDetail.setRoomCode(rentContract.getHouseRoomNo());
			rentDetail.setIsDeleted(ZraConst.NOT_DEL_INT);
			rentDetail.setCreaterId(rentContract.getCreaterid());
			rentDetail.setCreatedTime(new Date());
			rentDetail.setUpdaterId(rentContract.getCreaterid());
			RoomInfoEntity roomInfo = roomMap.get(rentContract.getRoomId());
			rentDetail.setFtype(roomInfo.getFtype());
			rentDetail.setFvalid(ZraConst.VALID_INT);
			rentDetailEntityList.add(rentDetail);
		});

		return rentDetailEntityList;
	}


	/**
	 * 更新物业交割状态
	 * @author jixd
	 * @created 2017年09月26日 14:12:30
	 * @param
	 * @return
	 */
	public int updateHasDelivery(ContractRoomDto contractRoomDto){
		return rentDetailDao.updateHasDelivery(contractRoomDto);
	}
	/**
	 * <p>根据uid查询所有合同</p>
	 * @author xiangb
	 * @created 2017年10月3日
	 * @param
	 * @return
	 */
	public List<RentContractEntity> listContractByUid(String uid){
		return rentContractDao.listContractByUid(uid);
	}
	/**
	 * <p>根据uid查询所有合同联合物业交割表</p>
	 * @author xiangb
	 * @created 2017年10月3日
	 * @param
	 * @return
	 */
	public List<RentContractAndDetailEntity> listContractAndDetailByUid(String uid){
		return rentContractDao.listContractAndDetailByUid(uid);
	}

	/**
	 * @description: 根据合同id查询合同信息以及物业交割状态
	 * @author: lusp
	 * @date: 2017/12/20 下午 17:22
	 * @params: contractId
	 * @return: List<RentContractAndDetailEntity>
	 */
	public List<RentContractAndDetailEntity> findContractAndDetailsByContractId(String contractId){
		return rentContractDao.findContractAndDetailsByContractId(contractId);
	}

	/**
	 * @description: 此方法为app提交合同失败回滚使用,将合同状态由待支付更新为未签约,,同时删除合同号和合同的价格、提交时间等等数据以及合同子表中的数据
	 * @author: lusp
	 * @date: 2017/10/11 11:18
	 * @params: contractId
	 * @return: int
	 */
	public void rollBackContractInfoForAppSubmit(String contractId){
		rentContractDao.updateContractRollBackForAppSubmit(contractId);//回滚合同主表数据
		rentDetailDao.updateRentDetailRollBackForSubmit(contractId);//回滚合同子表数据
	}

	/**
	 * @description: 提交合同时更新合同以及合同子表信息以及后台提交合同时修改物业交割状态
	 * @author: lusp
	 * @date: 2017/10/11 上午 20:22
	 * @params: rentContractEntity
	 * @return: int
	 */
	public void updateFinReceiBillFields(RentContractEntity rentContractEntity,RentContractEntity updateRentContractEntity,RentDetailEntity rentDetailEntity){
		rentContractDao.updateBaseContractById(updateRentContractEntity);
		rentDetailDao.updateRentDetailByContractId(rentDetailEntity);
		if(ContractSourceEnum.ZRAMS.getCode()==Integer.valueOf(rentContractEntity.getFsource())){
			ContractRoomDto contractRoomDto = new ContractRoomDto();
			contractRoomDto.setContractId(rentContractEntity.getContractId());
			contractRoomDto.setRoomId(rentContractEntity.getRoomId());
			rentDetailDao.updateHasDelivery(contractRoomDto);
		}
	}

	/**
	 * @description: 分页查询需要关闭的首笔账单超时未支付的合同
	 * @author: lusp
	 * @date: 2017/10/13 上午 11:12
	 * @params: paramMap
	 * @return: PagingResult<CloseContractNotifyVo>
	 */
	public PagingResult<CloseContractNotifyVo> findFirstBillPayOvertimeForPage(Map<String ,Object> paramMap){
		return rentContractDao.findFirstBillPayOvertimeForPage(paramMap);
	}

	/**
	 * @description: 分页查询首笔账单距离超时n小时未支付的合同
	 * @author: lusp
	 * @date: 2017/12/11 上午 11:12
	 * @params: paramMap
	 * @return: PagingResult<CloseContractNotifyVo>
	 */
	public PagingResult<CloseContractNotifyVo> findFirstBillPayBeforeOvertimeForPage(Map<String ,Object> paramMap){
		return rentContractDao.findFirstBillPayBeforeOvertimeForPage(paramMap);
	}


	/**
	 * @description: 分页查询需要关闭的当天未签约的合同
	 * @author: lusp
	 * @date: 2017/10/20 上午 11:12
	 * @params: paramMap
	 * @return: PagingResult<CloseContractNotifyVo>
	 */
	public PagingResult<CloseContractNotifyVo> findSameUnsignedForPage(Map<String ,Object> paramMap){
		return rentContractDao.findSameUnsignedForPage(paramMap);
	}

	/**
	 * @description: 分页查询距离签约有效期小于n小时的未签约合同
	 * @author: lusp
	 * @date: 2017/10/20 上午 11:19
	 * @params: paramMap
	 * @return: PagingResult<CloseContractNotifyVo>
	 */
	public PagingResult<CloseContractNotifyVo> findSameDayBeforeOvertimeForPage(Map<String ,Object> paramMap){
		return rentContractDao.findSameDayBeforeOvertimeForPage(paramMap);
	}

	/**
	 * @description: 分页查询合同状态是续约中同时合同到期日后一天没有续约的合同
	 * @author: lusp
	 * @date: 2017/10/20 上午 11:16
	 * @params: paramMap
	 * @return: PagingResult<CloseContractNotifyVo>
	 */
	public PagingResult<CloseContractNotifyVo> findSameUnrenewedForPage(Map<String ,Object> paramMap){
		return rentContractDao.findSameUnrenewedForPage(paramMap);
	}

	/**
	 * 更改合同状态
	 *
	 * @param contractId 合同标识
	 * @param status     状态
	 * @param closeType  关闭类型
	 * @return
	 * @author cuigh6
	 * @Date 2017年10月13日
	 */
	public int updateContractStatus(String contractId, String status, Integer closeType) {
		return this.rentContractDao.updateContractStatus(contractId, status, closeType);
	}

	/**
	 * 根据父合同id 查询子合同信息
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public List<RentDetailEntity> findRentDetailsByRentRoomDto(RentRoomDto rentRoomDto) {
		List<RentContractEntity> rentContractEntityList = null;
		if (BussTypeEnum.isRenewalSign(rentRoomDto.getBussType())) {
			rentContractEntityList = rentContractDao.findCanRenewContractByParentId(rentRoomDto.getSurParentRentId());
		} else if (BussTypeEnum.isSurrender(rentRoomDto.getBussType())) {
			rentContractEntityList = rentContractDao.findCanSurrenderContractByParentId(rentRoomDto.getSurParentRentId());
		} else {
			LogUtil.error(LOGGER, "findRentDetailsByRentRoomDto 入参错误:{}", rentRoomDto.toJsonStr());
		}


		if (rentContractEntityList == null || rentContractEntityList.size() == 0) {
			return null;
		}
		List<String> contractIds = new ArrayList<>();
		//已签约,已到期且已审核状态的合同才可以进行续约操作。目前解约操作一致
//		rentContractEntityList = rentContractEntityList.stream().filter(rentContractEntity ->
//				ContractStatusEnum.YQY.getStatus().equals(rentContractEntity.getConStatusCode()) ||
// 				ContractStatusEnum.YDQ.getStatus().equals(rentContractEntity.getConStatusCode())
//		).filter(rentContractEntity ->
//				ContractAuditStatusEnum.YTG.getStatus().equals(rentContractEntity.getConAuditState())
//		).collect(Collectors.toList());

		rentContractEntityList.forEach(rentContractEntity -> {
			contractIds.add(rentContractEntity.getContractId());
		});


		List<RentDetailEntity> rentDetailEntityList = rentDetailDao.findRentDetailByContractIds(contractIds);
		return rentDetailEntityList;
	}


	/**
	 *
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public RentContractEntity findOneRentContractByParentId(String surParentRentId) {
		List<RentContractEntity>  rentContractEntityList = rentContractDao.findContractListByParentId(surParentRentId);
		if (rentContractEntityList == null || rentContractEntityList.size() == 0) {
			return null;
		}
		return rentContractEntityList.get(0);
	}

	/**
	 * 查询合同列表
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public List<RentContractEntity> findContractListByParentId(String surParentRentId) {
		List<RentContractEntity>  rentContractEntityList = rentContractDao.findContractListByParentId(surParentRentId);
		if (rentContractEntityList == null || rentContractEntityList.size() == 0) {
			return null;
		}
		return rentContractEntityList;
	}



	/**
	 * 第一步保存
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public ContractFirstVo saveContractFist(ProjectEntity projectEntity, List<RentContractRoomVo> rentContractRoomVoList, ContractFirstDto contractFirstDto, CityEntity cityEntity, Map<String, Date> preContractEndDate) throws Exception {

		boolean isRenewal = false;
		boolean isEnterprise = false;
		if (ContractSignTypeEnum.RENEW.getValue().equals(contractFirstDto.getSignType())) {
			isRenewal =  true;
		}
		if (String.valueOf(CustomerTypeEnum.ENTERPRICE.getCode()).equals(contractFirstDto.getCustomerType())) {
			isEnterprise = true;
		}

		Map<String, RentContractRoomVo> rentContractRoomVoMap = new HashMap<>();
		StringJoiner sjPreContractIds = new  StringJoiner(",");
		rentContractRoomVoList.forEach( rentContractRoomVo -> {
			rentContractRoomVoMap.put(rentContractRoomVo.getRoomId(),rentContractRoomVo);
			sjPreContractIds.add(rentContractRoomVo.getContractId());
		});

		String surParentRentId = null;
		String surParentRentCode = null;
		if (isEnterprise) {
			surParentRentId = UUIDGenerator.hexUUID();
//			surParentRentCode = this.surParentContractCode(contractFirstDto.getSignType(),Integer.valueOf(contractFirstDto.getCustomerType()), projectEntity.getFcode(), cityEntity);
		}



		List<String> contractIdList = new ArrayList<>();
		List<RentContractEntity> rentContractEntityList = new ArrayList<>();
		for (RentContractRoomVo rentContractRoomVo : rentContractRoomVoList) {
			RentContractEntity rentContractEntity = new RentContractEntity();
			String tempContractId = UUIDGenerator.hexUUID();
			contractIdList.add(tempContractId);
			rentContractEntity.setContractId(tempContractId);
			//续约
			if (isRenewal) {
				Date conEndDate = preContractEndDate.get(rentContractRoomVo.getConRentCode());
				//设置前合同号
				rentContractEntity.setPreConRentCode(rentContractRoomVo.getConRentCode());
				rentContractEntity.setPreSurParentRentId(rentContractRoomVo.getSurParentRentId());
				rentContractEntity.setConStartDate(DateUtilFormate.addDate(conEndDate, 1));
			}
			rentContractEntity.setConStatusCode(ContractStatusEnum.WQY.getStatus());
			rentContractEntity.setConAuditState(ContractAuditStatusEnum.DSH.getStatus());
//			rentContractEntity.setConAuditer("5");
			rentContractEntity.setCustomerType(Integer.valueOf(contractFirstDto.getCustomerType()));
			rentContractEntity.setFsigntype(contractFirstDto.getSignType());
			rentContractEntity.setFsource(contractFirstDto.getSource());
			rentContractEntity.setConSignDate(new Date());
			rentContractEntity.setFcreatetime( new Date());
			rentContractEntity.setCreaterid(contractFirstDto.getEmployeeId());
			rentContractEntity.setProjectId(projectEntity.getFid());
			rentContractEntity.setProName(projectEntity.getFname());
			// add by cuigh6
			rentContractEntity.setRoomSalesPrice(rentContractRoomVo.getLongPrice());
			rentContractEntity.setCustomerUid(contractFirstDto.getCustomerUid());
			rentContractEntity.setCustomerType(Integer.parseInt(contractFirstDto.getCustomerType()));
			rentContractEntity.setCustomerMobile(contractFirstDto.getPhone());
			rentContractEntity.setCityid(projectEntity.getCityid());
			rentContractEntity.setHouseRoomNo(rentContractRoomVo.getRoomNumber());
			rentContractEntity.setUpdaterid(contractFirstDto.getEmployeeId());
			rentContractEntity.setFupdatetime(new Date());
			rentContractEntity.setFhandletime(new Date());
			rentContractEntity.setFhandlezo(contractFirstDto.getHandleZO());
			rentContractEntity.setFhandlezocode(contractFirstDto.getHandleZOCode());
			rentContractEntity.setCustomerName(contractFirstDto.getCustomerName());
			//TODO
			rentContractEntity.setDataVersion(1);
			rentContractEntity.setIsTransferPdf(0);


			//项目地址不写
			rentContractEntity.setFisdel(0);
			rentContractEntity.setConTplVersion("1");
			//个人不可以生成父子合同
			if (isEnterprise) {
				rentContractEntity.setSurParentRentId(surParentRentId);
				rentContractEntity.setSurParentRentCode(surParentRentCode);
			}
			rentContractEntity.setRoomId(rentContractRoomVo.getRoomId());
			rentContractEntityList.add(rentContractEntity);
		}

		List<RentDetailEntity> rentDetailEntityList = new ArrayList<>();

		for (RentContractEntity rentContractEntity : rentContractEntityList) {
			RentContractRoomVo rentContractRoomVo = rentContractRoomVoMap.get(rentContractEntity.getRoomId());
			RentDetailEntity rentDetailEntity = new RentDetailEntity();
			rentDetailEntity.setContractId(rentContractEntity.getContractId());
			rentDetailEntity.setRoomArea(rentContractRoomVo.getRoomArea());
			rentDetailEntity.setRoomCode(rentContractRoomVo.getRoomNumber());
			rentDetailEntity.setRoomId(rentContractEntity.getRoomId());
			rentDetailEntity.setBasePrice(rentContractRoomVo.getLongPrice());
			rentDetailEntity.setCreatedTime(new Date());
			rentDetailEntity.setCreaterId(contractFirstDto.getEmployeeId());
			rentDetailEntity.setIsDeleted(0);
			rentDetailEntity.setDeliveryState(0);
			rentDetailEntity.setFtype(rentContractRoomVo.getType());
			rentDetailEntity.setPersonUid(contractFirstDto.getCustomerUid());
			if (isEnterprise) {//未录入
				rentDetailEntity.setPersonDataStatus(1);
			}else{// 已录入
				rentDetailEntity.setPersonDataStatus(2);
			}
			rentDetailEntityList.add(rentDetailEntity);
		}
		rentContractEntityList.forEach( rentContractEntity -> {
			rentContractDao.saveRentContract(rentContractEntity);
		});

		rentDetailEntityList.forEach( rentDetailEntity -> {
			rentDetailDao.save(rentDetailEntity);
		});

		//续约 -- 需要修改原合同为续约中
		if (isRenewal) {
			int needUpdateNum = rentContractRoomVoList.size();
			int actUpdateNum = rentContractDao.updateXyzByContractIds(sjPreContractIds.toString());
			if (needUpdateNum != actUpdateNum) {
				throw  new Exception("前合同信息有变化，请重新操作或查看再操作");
			}
		}
		ContractFirstVo contractFirstVo = new ContractFirstVo();
		contractFirstVo.setSurParentRentId(surParentRentId);
		contractFirstVo.setContractIdList(contractIdList);
		return contractFirstVo;
	}



	/**
	 * 生成合同号方法
	 * @author cuiyuhui
	 * @created
	 * @param conType:长短租;customerType:客户类型;pCode:项目编号;cityEntity:城市
	 * @return
	 */
	public String contractCode(String conType, int customerType, String pCode, CityEntity cityEntity) {
		SimpleDateFormat sdf = new SimpleDateFormat("yy");
		StringBuilder sb = new StringBuilder();
		if (LeaseCycleEnum.YEAR.getCode().equals(conType) ) {
			sb.append(cityEntity.getFlongcode());
		} else {
			sb.append(cityEntity.getFshortcode());
		}
		if (CustomerTypeEnum.ENTERPRICE.getCode() == customerType) {
			sb.append("D");
		} else {
			sb.append("S");
		}
		sb.append(pCode);
		sb.append(sdf.format(new Date()));
		sb.append(rentContractDao.getContractSeq(cityEntity.getFnextvalname()));
		return sb.toString();
	}

	/**
	 * 生成企业合同编号
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public String surParentContractCode(String conType, int customerType, String pCode, CityEntity cityEntity) {
		return this.contractCode(conType, customerType, pCode, cityEntity) + "Z";
	}
	/**
	 * <p>更新合同状态</p>
	 * @author xiangb
	 * @created 2017年10月17日
	 * @param rentContractEntity
	 * @return
	 */
	public int updateContractToTargetStatus(RentContractEntity rentContractEntity){
		return rentContractDao.updateContractToTargetStatus(rentContractEntity);
	}

	/**
	 * 保存存企业客户信息
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public boolean saveEnterpriseContractCustomerInfo(EnterpriseCustomerDto enterpriseCustomerDto) {
		RentEpsCustomerEntity rentEpsCustomerEntity = transferByEnterpriseCustomerDto(enterpriseCustomerDto);
		int size = rentEpsCustomerDao.saveRentEpsCustomer(rentEpsCustomerEntity);
		if (size <= 0 ) {
			return false;
		}

		size = rentContractDao.updateContractCustomerInfoByParentId(enterpriseCustomerDto.getSurParentRentId(), rentEpsCustomerEntity);
		return size > 0;
	}

	/**
	 * 修改企业客户信息
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public boolean updateEnterpriseContractCustomerInfo(EnterpriseCustomerDto enterpriseCustomerDto) {
		RentEpsCustomerEntity rentEpsCustomerEntity = transferByEnterpriseCustomerDto(enterpriseCustomerDto);
		//修改客户表客户信息
		int size = rentEpsCustomerDao.updateRentEpsCustomer(rentEpsCustomerEntity);
		if (size <=0 ) {
			return false;
		}

		//修改合同客户信息
		size = rentContractDao.updateContractCustomerInfoByParentId(enterpriseCustomerDto.getSurParentRentId(), rentEpsCustomerEntity);
		return size > 0;
	}

	/**
	 * 将 EnterpriseCustomerDto 转为 RentEpsCustomerEntity
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	private RentEpsCustomerEntity transferByEnterpriseCustomerDto(EnterpriseCustomerDto enterpriseCustomerDto) {
		String customerId = enterpriseCustomerDto.getId();

		if (Check.NuNStrStrict(customerId)) {
			customerId = UUIDGenerator.hexUUID();
		}

		RentEpsCustomerEntity rentEpsCustomerEntity = new RentEpsCustomerEntity();
		rentEpsCustomerEntity.setId(customerId);
		rentEpsCustomerEntity.setCode(enterpriseCustomerDto.getCode());
		rentEpsCustomerEntity.setName(enterpriseCustomerDto.getName());
		rentEpsCustomerEntity.setAddress(enterpriseCustomerDto.getAddress());
		rentEpsCustomerEntity.setEmail(enterpriseCustomerDto.getEmail());
		rentEpsCustomerEntity.setBusinessLicense(enterpriseCustomerDto.getBusinessLicense());
		rentEpsCustomerEntity.setContacterNum(enterpriseCustomerDto.getContacterNum());
		rentEpsCustomerEntity.setContacter(enterpriseCustomerDto.getContacter());
		rentEpsCustomerEntity.setContacterTel(enterpriseCustomerDto.getContacterTel());
		rentEpsCustomerEntity.setProxyPicurl(enterpriseCustomerDto.getProxyPicurl());
		rentEpsCustomerEntity.setLicensePicurl01(enterpriseCustomerDto.getLicensePicurl01());
		rentEpsCustomerEntity.setLicensePicurl02(enterpriseCustomerDto.getLicensePicurl02());
		rentEpsCustomerEntity.setLicensePicurl03(enterpriseCustomerDto.getLicensePicurl03());
		rentEpsCustomerEntity.setCustomerUid(enterpriseCustomerDto.getCustomerUid());
		rentEpsCustomerEntity.setCreaterId(enterpriseCustomerDto.getCreaterId());
		rentEpsCustomerEntity.setCreatedTime(new Date());
		rentEpsCustomerEntity.setUpdaterId(enterpriseCustomerDto.getUpdaterId());
		rentEpsCustomerEntity.setUpdateTime(new Date());
		return rentEpsCustomerEntity;
	}

	/**
	  * @description: 1.保存合同优惠活动
	  * 			  2.保存合同应收账单
	  * 			  3.保存合同应收账单明细
	  * @author: lusp
	  * @date: 2017/10/20 下午 17:15
	  * @params: rentContractActivityEntities,finReceiBillEntities,finReceiBillDetailEntities
	  * @return:
	  */
	public void updateContractAndSaveActivityFinReceiInfo(List<RentContractActivityEntity> rentContractActivityEntities,
			List<FinReceiBillEntity> finReceiBillEntities,List<FinReceiBillDetailEntity> finReceiBillDetailEntities) {

		if(!Check.NuNCollection(rentContractActivityEntities)){
			rentContractActivityDao.deleteActivityByContractId(rentContractActivityEntities.get(0).getContractId());
			for (RentContractActivityEntity rentContractActivityEntity:rentContractActivityEntities){
				rentContractActivityDao.insertSelective(rentContractActivityEntity);
			}
		}
		if(!Check.NuNCollection(finReceiBillEntities)){
			for (FinReceiBillEntity finReceiBillEntity:finReceiBillEntities){
				finReceiBillDao.saveFinReceiBill(finReceiBillEntity);
			}
		}
		if(!Check.NuNCollection(finReceiBillDetailEntities)){
			for (FinReceiBillDetailEntity finReceiBillDetailEntity:finReceiBillDetailEntities){
				finReceiBillDetailDao.saveFinReceiBillDetail(finReceiBillDetailEntity);
			}
		}
	}


	/**
	 * 查询合同参加的活动列表
	 * @param contractId 合同标识
	 * @return
	 * @author cuigh6
	 * @Date 2017年10月21日
	 */
	public List<RentContractActivityEntity> getContractActivityList(String contractId) {
		return this.rentContractActivityDao.getContractActivityList(contractId);
	}
	/**
	 * <p>更新合同签约人姓名和手机号</p>
	 * @author xiangb
	 * @created 2017年10月23日
	 * @param
	 * @return
	 */
	public int updateContractCustomerName(String contractId, String customerName, String customerMobile){
		return this.rentContractDao.updateContractCustomerName(contractId, customerName, customerMobile);
	}

	/**
	 * @description: 更新同步财务状态
	 * @author: lusp
	 * @date: 2017/10/23 上午 10:23
	 * @params: rentContractEntity
	 * @return: int
	 */
	public int updateSyncToFinByContractId(RentContractEntity rentContractEntity){
		return rentContractDao.updateSyncToFinByContractId(rentContractEntity);
	}

	/**
	 * 修改合同出租周期，付款方式之类的相关信息
	 * 同时生成合同号
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public boolean updateContractInfoByContractParamDto(ContractParamDto contractParamDto, ProjectEntity projectEntity, CityEntity cityEntity, List<RentContractEntity> rentContractEntityList) {
		RentContractEntity tempRentContractEntity = rentContractEntityList.get(0);
		String surParentRentCode = this.surParentContractCode(contractParamDto.getConType(), tempRentContractEntity.getCustomerType(), projectEntity.getFcode(), cityEntity);
		for (RentContractEntity rentContractEntity : rentContractEntityList) {
			rentContractEntity.setSurParentRentId(contractParamDto.getSurParentRentId());
			rentContractEntity.setSurParentRentCode(surParentRentCode);
			String conRentCode = this.contractCode(contractParamDto.getConType(), tempRentContractEntity.getCustomerType(), projectEntity.getFcode(), cityEntity);
			rentContractEntity.setConRentCode(conRentCode);
			rentContractEntity.setConType(contractParamDto.getConType());
			rentContractEntity.setConCycleCode(contractParamDto.getConCycleCode());
			rentContractEntity.setConStartDate(contractParamDto.getConStartDate());
			rentContractEntity.setConEndDate(contractParamDto.getConEndDate());
			rentContractEntity.setConRentYear(contractParamDto.getConRentYear());
			rentContractEntity.setUpdaterid(contractParamDto.getUpdateId());
			rentContractDao.updateContractByContractId(rentContractEntity);
		}
		return true;
	}
	/**
	 * 根据父合同号更新子合同
	 * @author jixd
	 * @created 2017年11月15日 20:30:43
	 * @param
	 * @return
	 */
	public int updateContractBySurParentRentId(RentContractEntity rentContractEntity){
		return rentContractDao.updateContractByParentId(rentContractEntity);
	}

	/**
	 * @description: 此方法为zrams提交合同失败回滚使用,将合同状态由待支付更新为未签约,修改不可修改标志,同时删除合同号和合同的价格、提交时间等等数据
	 * @author: lusp
	 * @date: 2017/10/25 上午 10:29
	 * @params: rentContractEntity
	 * @return:
	 */
	public void rollBackContractInfoForZramsSubmit(RentContractEntity rentContractEntity){
		rentContractDao.updateContractRollBackForZramsSubmit(rentContractEntity.getContractId());//回滚合同主表数据
		rentDetailDao.updateRentDetailRollBackForSubmit(rentContractEntity.getContractId());//回滚合同子表数据
		ContractRoomDto contractRoomDto = new ContractRoomDto();
		contractRoomDto.setContractId(rentContractEntity.getContractId());
		contractRoomDto.setRoomId(rentContractEntity.getRoomId());
		rentDetailDao.updateHaveNotDelivery(contractRoomDto);
	}

	/**
	 * 更新合同基本信息
	 * @author jixd
	 * @created 2017年10月27日 10:25:37
	 * @param
	 * @return
	 */
	public int updateBaseContractById(RentContractEntity rentContractEntity){
		return rentContractDao.updateBaseContractById(rentContractEntity);
	}

	/**
	  * @description: 修改合同状态为已签约和前合同状态为已续约
	  * @author: lusp
	  * @date: 2017/12/6 上午 10:50
	  * @params: contractId,preConRentCode
	  * @return: int
	  */
	public int updateContractAndPreStatus(String contractId,String roomId,String preConRentCode){
		RentContractEntity rentContractEntity = new RentContractEntity();
		rentContractEntity.setContractId(contractId);
		rentContractEntity.setConStatusCode(ContractStatusEnum.YQY.getStatus());
		rentContractEntity.setPreConStatusCode(ContractStatusEnum.DZF.getStatus());
		rentContractEntity.setFirstPayTime(new Date());
		rentContractEntity.setConSignDate(new Date());
		rentContractDao.updateContractStatusYxyByCode(preConRentCode);
		ContractRoomDto contractRoomDto = new ContractRoomDto();
        contractRoomDto.setContractId(contractId);
        contractRoomDto.setRoomId(roomId);
		rentDetailDao.updateHasDelivery(contractRoomDto);
		return rentContractDao.updateBaseContractById(rentContractEntity);
	}

	/**
	  * @description: 回滚合同状态为待支付，签合同状态为续约中
	  * @author: lusp
	  * @date: 2017/12/6 上午 10:50
	  * @params: contractId,preConRentCode
	  * @return: int
	  */
	public int rollBackContractAndPreStatus(String contractId,String preConRentCode,String roomId){
		RentContractEntity rentContractEntity = new RentContractEntity();
		rentContractEntity.setContractId(contractId);
		rentContractEntity.setConStatusCode(ContractStatusEnum.DZF.getStatus());
		rentContractEntity.setPreConStatusCode(ContractStatusEnum.YQY.getStatus());
//		rentContractEntity.setFirstPayTime(null);
//		rentContractEntity.setConSignDate(null);
		rentContractDao.updateContractStatusXyzByCode(preConRentCode);
		ContractRoomDto contractRoomDto = new ContractRoomDto();
        contractRoomDto.setContractId(contractId);
        contractRoomDto.setRoomId(roomId);
		rentDetailDao.updateHaveNotDelivery(contractRoomDto);
		return rentContractDao.updateBaseContractById(rentContractEntity);
	}


	/**
	 * @description: 更具合同id更新合同子表
	 * @author: lusp
	 * @date: 2017/10/27 下午 14:09
	 * @params: rentDetailEntity
	 * @return: int
	 */
	public int updateRentDetailByContractId(RentDetailEntity rentDetailEntity){
		return rentDetailDao.updateRentDetailByContractId(rentDetailEntity);
	}

	/**
	 * 修改合同状态为待支付
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public boolean updateContractStatusDzfByParentId(String surParentRentId, List<RentContractEntity> rentContractEntityList, boolean isRenew) throws Exception {

		int expectSize = rentContractEntityList.size();
		int size = rentContractDao.updateContractStatusDzfByParentId(surParentRentId);

		if (isRenew) {
			for (RentContractEntity  rentContractEntity : rentContractEntityList) {
				int size2 = rentContractDao.updateContractStatusYxyByCode(rentContractEntity.getPreConRentCode());
				if (size2 < 1) {
					throw new Exception("updateContractStatusYxyByCode fail!surParentRentId:" + surParentRentId + " ,conRentCode:" + rentContractEntity.getPreConRentCode());
				}
			}
		}


		if (expectSize == size) {
			return true;
		} else {
			throw new Exception("更新合同状态失败!surParentRentId:" + surParentRentId + " ,expectsize:" + expectSize + ",updateSize:" + size);
		}
	}
	/**
	 *
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public List<RentContractEntity> findWqyContractInviteByRoomIds(List<String> roomIdList) throws Exception {
		Date currentDate = new Date();

		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String cDate = df.format(currentDate);
			currentDate = df.parse(cDate);
		} catch (Exception e) {
			throw e;
		}
		List<RentContractEntity> rentContractEntityList = rentContractDao.findWqyContractInviteByRoomIds(roomIdList, currentDate);
		return rentContractEntityList;
	}
	
	/**
	 * <p>批量保存解约协议及更新合同中的解约申请日期</p>
	 * @author xiangb
	 * @created 2017年11月2日
	 */
	public void saveSurrenderAndUpdateRentContract(List<SurrenderEntity> surrenderEntitys){
			if(!Check.NuNCollection(surrenderEntitys)){
				String surParentCode = "";
				String surParentId = "";
				for(SurrenderEntity surrender : surrenderEntitys){
					/*将解约申请日期保存到合同的申请日期中一份*/
			        if (!Check.NuNStr(surrender.getContractId())) {
			        	RentContractEntity contract = rentContractDao.findContractBaseByContractId(surrender.getContractId());
			        	if(!Check.NuNObj(contract)){
			        		if(!Check.NuNStr(contract.getSurParentRentId()) && Check.NuNStr(surParentCode)){
			        			LogUtil.info(LOGGER, "【saveSurrenderAndUpdateRentContract】生成父解约号参数:{}", contract.getSurParentRentId());
			        			//查询解约了几次
			        			Integer times = surrenderDao.selectSurrenderTimeByCode(contract.getSurParentRentCode());
			        			times = Check.NuNObj(times)?1:times+1;
			        			String parentCode = Check.NuNStr(contract.getSurParentRentCode())?contract.getConRentCode():contract.getSurParentRentCode(); 
			        			surParentCode = ContractValueConstant.UNRENT_PREFIX+parentCode+String.format("%03d", times);
			        			surParentId = UUID.randomUUID()+"";
			        		}else if(!(CustomerTypeEnum.PERSON.getCode() == contract.getCustomerType()) && contract.getDataVersion() == ContractDataVersionEnum.OLD.getCode()){
			        			//兼容企业签约一个房间的情况。//TODO 去掉 旧数据企业不支持解约
			        			LogUtil.info(LOGGER, "【saveSurrenderAndUpdateRentContract】旧数据企业解约生成父解约号参数:{}", contract.getSurParentRentId());
			        			Integer times = 1;
			        			surParentCode = ContractValueConstant.UNRENT_PREFIX+contract.getConRentCode()+String.format("%03d", times);
			        			surParentId = UUID.randomUUID()+"";
			        		}
			        		if(!Check.NuNStr(surParentCode)){
			        			surrender.setSurParentCode(surParentCode);
			        			surrender.setSurParentId(surParentId);
			        		}
//			        		surrender.setSurrenderId(UUID.randomUUID().toString());
			        		String rentCode = contract.getConRentCode();
			        		surrender.setFsurrendercode(ContractValueConstant.UNRENT_PREFIX+rentCode);
			        		String roomId = contract.getRoomId() == null ? "" : contract.getRoomId();
			        		surrender.setRoomId(roomId);
			        		if(Check.NuNStr(surrender.getSurrenderId())){
			        			//防止一个合同对应多个解约协议,先删除所有可用的解约协议
			        			surrenderDao.deleteSurrenderByContractId(surrender.getContractId());
			        			//保存解约协议
			        			surrenderDao.saveSurrrender(surrender);
			        		}else{
			        			surrenderDao.updateSurrender(surrender);
			        		}
			        	}
			            if (surrender.getFapplicationdate() != null || surrender.getFexpecteddate() != null) {
			                contract.setFapplicationdate(surrender.getFapplicationdate());
			                contract.setFexpecteddate(surrender.getFexpecteddate());
			                contract.setFsurrenderid(surrender.getSurrenderId());
			                rentContractDao.updateBaseContractById(contract);
			            }else{
			            	throw new RuntimeException("解约协议未传解约申请日期");
			            }
			        }else{
			        	throw new RuntimeException("解约协议没有合同ID异常");
			        }
				}
			}
	}
	/**
	 * <p>根据前合同号查询续约合同</p>
	 * @author xiangb
	 * @created 2017年11月3日
	 */
	public RentContractEntity findRenewContractByPreRentCode(String preRentCode){
		return rentContractDao.findRenewContractByPreRentCode(preRentCode);
	}
	/**
	 * 查询合同详情列表
	 * @author jixd
	 * @created 2017年11月09日 14:25:06
	 * @param
	 * @return
	 */
	public PagingResult<RentDetailEntity> listRentDetailBySurParentRentId(ContractPageDto contractPageDto){
		return rentDetailDao.listRentDetailBySurParentRentId(contractPageDto);
	}

	/**
	 * 父合同号查询下面所有子合同id
	 * @author jixd
	 * @created 2017年11月12日 11:25:08
	 * @param
	 * @return
	 */
	public List<RentContractEntity> listContractBySurParentRentId(String surParentRentId){
		return rentContractDao.listContractBySurParentRentId(surParentRentId);
	}
	/**
	 * 查询企业父合同号下面所有的 合同明细
	 * @author jixd
	 * @created 2017年11月13日 11:23:19
	 * @param
	 * @return
	 */
	public List<RentDetailEntity> listRentDetailBySurParentRentId(String surParentRentId){
		return rentDetailDao.listRentDetailBySurParentRentId(surParentRentId);
	}
	

	public List<RentContractEntity> findYQYContractListByUid(String uid) {
		return this.rentContractDao.findYQYContractListByUid(uid);
	}

	/**
	 * 只查询一个前合同的实体
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public RentContractEntity findOnePreContractInfoByPreSurParentRentId(String preSurParentRentId) {
		return this.rentContractDao.findOnePreContractInfoByPreSurParentRentId(preSurParentRentId);
	}

	/**
	 * 批量获取父合同号
	 * @param contractCodes 子合同号列表
	 * @return
	 * @author cuigh6
	 * @Date 2017年11月
	 */
	public List<RentContractEntity> getBatchParentContractCode(List<String> contractCodes) {
		return this.rentContractDao.getBatchParentContractCode(contractCodes);
	}

	/**
	 * 查询未同步到财务的合同
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public PagingResult<RentContractEntity> findContractNotSyncToFin(Date queryDate, PageBounds pageBounds) {
		return this.rentContractDao.findContractNotSyncToFin(queryDate, pageBounds);
	}

	/**
	 * 查询未生成合同文本pdf的合同
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public PagingResult<RentContractEntity> findContractNotTransferToPdf(Date queryDate, PageBounds pageBounds) {
		return this.rentContractDao.findContractNotTransferToPdf(queryDate, pageBounds);
	}


	/**
	  * @description: 根据父合同号批量查询合同号
	  * @author: lusp
	  * @date: 2017/11/25 下午 17:46
	  * @params: parentCode
	  * @return: List<String>
	  */
	public List<String> getBatchContractCodeByParentCode(String parentCode) {
		return this.rentContractDao.getBatchContractCodeByParentCode(parentCode);
	}

	/**
	 * @description: 根据父合同号批量查询合同号（除了已退租和已关闭的）
	 * @author: lusp
	 * @date: 2017/11/25 下午 17:46
	 * @params: parentCode
	 * @return: List<String>
	 */
	public List<String> getCodesByParentCodeOnCondition(String parentCode) {
		return this.rentContractDao.getCodesByParentCodeOnCondition(parentCode);
	}

	/**
	 * 分页查询合同列表
	 * @author jixd
	 * @created 2017年11月27日 18:15:50
	 * @param
	 * @return
	 */
	public PagingResult<ContractManageDto> listContractByPage(ContractSearchPageDto contractSearchPageDto){
		return rentContractDao.listContractByPage(contractSearchPageDto);
	}

	/**
	 * 修改未签约房间价格
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public boolean updateWqyContractRoomSalesPrice(RentContractEntity rentContractEntity) {
		int size = this.rentContractDao.updateWqyContractRoomSalesPrice(rentContractEntity);
		return size > 0;
	}
	/**
	 * 分页查询过期合同更改为 已过期状态并同步财务
	 * @author jixd
	 * @created 2017年12月05日 16:45:42
	 * @param
	 * @return
	 */
	public PagingResult<RentContractEntity> listExpireContractPage(BasePageParamDto basePageParamDto){
		return rentContractDao.listExpireContractPage(basePageParamDto);
	}

	/**
	 *
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public int updateIsTransferPdf(String contractId) {
		return rentContractDao.updateIsTransferPdf(contractId);
	}

    public String finOldDataContractId(String contractId) {
        return rentContractDao.finOldDataContractId(contractId);
    }

	/**
	 * 查询房间有效的合同
	 * @param roomId 房间标识
	 * @return
	 */
	public List<RentContractEntity> findValidContractByRoomId(String roomId) {
		return this.rentContractDao.findValidContractByRoomId(roomId);
	}

	/**
	  * @description: 根据主合同号查询合同列表
	  * @author: lusp
	  * @date: 2018/1/11 下午 18:11
	  * @params: surParentRentCode
	  * @return: List<RentContractEntity>
	  */
	public List<RentContractEntity> findContractListByParentCode(String surParentRentCode) {
		return this.rentContractDao.findContractListByParentCode(surParentRentCode);
	}

	/**
	 *  查询当前房间的最近的合同
	 * @author yd
	 * @created
	 * @param
	 * @return
	 */
	public  List<RoomContractSmartVo>  findCurrentContract(RoomStmartDto roomStmartDto){
		return  rentContractDao.findCurrentContract(roomStmartDto);
	}

    /**
     *
     * 根据合同id和房间id，更新子表uid
     *
     * @author zhangyl2
     * @created 2018年01月30日 15:11
     * @param
     * @return
     */
    public int updateUidByContractIdAndRoomId(RentDetailEntity rentDetailEntity){
        return rentDetailDao.updateUidByContractIdAndRoomId(rentDetailEntity);
    }


}
