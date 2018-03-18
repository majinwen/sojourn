package com.ziroom.zrp.service.trading.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.zrp.service.houses.dto.RoomStmartDto;
import com.ziroom.zrp.service.houses.entity.RoomContractSmartVo;
import com.ziroom.zrp.service.trading.dto.contract.ContractManageDto;
import com.ziroom.zrp.service.trading.dto.contract.ContractSearchPageDto;
import com.ziroom.zrp.service.trading.entity.CloseContractNotifyVo;
import com.ziroom.zrp.service.trading.entity.SyncContractVo;
import com.ziroom.zrp.service.trading.valenum.ContractStatusEnum;
import com.ziroom.zrp.trading.entity.RentContractAndDetailEntity;
import com.ziroom.zrp.trading.entity.RentContractEntity;
import com.ziroom.zrp.trading.entity.RentEpsCustomerEntity;
import com.zra.common.dto.base.BasePageParamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 项目信息
 * </p>
 * <p/>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd on 2017年09月06日
 * @version 1.0
 * @since 1.0
 */
@Repository("trading.rentContractDao")
public class RentContractDao {

	private String SQLID = "trading.rentContractDao.";

	@Autowired
	@Qualifier("trading.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 保存项目信息
	 * @author jixd
	 * @created 2017年09月06日 09:59:58
	 * @param
	 * @return
	 */
	public int saveRentContract(RentContractEntity  rentContractEntity){
		if(Check.NuNStr(rentContractEntity.getContractId())){
			rentContractEntity.setContractId(UUIDGenerator.hexUUID());
		}
		return mybatisDaoContext.save(SQLID + "saveRentContract",rentContractEntity);
	}

	/**
	 * 查找合同 根据合同号(去除已关闭、已作废合同)
	 * 兼容历史数据中一个合同号多条数据  @Author: lusp  @Date:2017/12/25
	 * @author jixd
	 * @created 2017年09月07日 20:02:21
	 * @param
	 * @return
	 */
	public RentContractEntity findValidContractByRentCode(String code){
		List<RentContractEntity> rentContractEntities = mybatisDaoContext.findAll(SQLID + "findContractByRentCode",RentContractEntity.class,code);
		List<RentContractEntity> rentContractEntitiesFilter = rentContractEntities.stream().filter((RentContractEntity r) -> !r.getConStatusCode().equals(ContractStatusEnum.YGB.getStatus())&&!r.getConStatusCode().equals(ContractStatusEnum.YZF.getStatus())).collect(Collectors.toList());
		if(!Check.NuNCollection(rentContractEntitiesFilter)){
			return rentContractEntitiesFilter.get(0);
		}
		return null;
	}

	/**
	 * 查找找到第一个合同
	 * @param code
	 * @return
	 */
	public RentContractEntity findContractByRentCode(String code){
		List<RentContractEntity> rentContractEntities = mybatisDaoContext.findAll(SQLID + "findContractByRentCode",RentContractEntity.class,code);
		if (!Check.NuNCollection(rentContractEntities)){
			return rentContractEntities.get(0);
		}
		return null;
	}

	/**
	 * 根据合同标识查询合同详情
	 * @author xiangb
	 * @created 2017年9月12日
	 * @param contractId 合同标识
	 * @return MyContractDetailDto APP用到的实体类
	 */
	public RentContractEntity findContractBaseByContractId(String contractId){
		return mybatisDaoContext.findOne(SQLID + "findContractBaseByContractId",RentContractEntity.class,contractId);
	}

	/**
	 * 根据合同id查询多个合同
	 * @author cuiyuhui
	 * @created
	 * @param contractIds 多个合同标识
	 * @return
	 */
	public List<RentContractEntity> findContractListByContractIds(List<String> contractIds) {

		if (contractIds == null || contractIds.size() == 0) {
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("contractIds", contractIds);

		return mybatisDaoContext.findAll(SQLID + "findContractListByContractIds", RentContractEntity.class, map);
	}

	/**
	 * 更新付款方式
	 * @author jixd
	 * @created 2017年09月13日 17:34:23
	 * @param
	 * @return
	 */
	public int updatePayCodeByContractId(String contractId,String payCode){
		RentContractEntity rentContractEntity = new RentContractEntity();
		rentContractEntity.setContractId(contractId);
		rentContractEntity.setConCycleCode(payCode);
		return mybatisDaoContext.update(SQLID + "updateBaseContractById",rentContractEntity);
	}

	/**
	 * @description: 查询同步合同到财务的相关信息
	 * @author: lusp
	 * @date: 2017/9/22 15:26
	 * @params: contractId
	 * @return: SyncContractVo
	 */
	public SyncContractVo findSyncContractVoById(String contractId){
		return mybatisDaoContext.findOne(SQLID + "findSyncContractVoById",SyncContractVo.class,contractId);
	}

	/**
	 * 更新合同当前状态为续约中
	 * @author cuiyh9
	 * @created 2017年09月29日 19:12
	 * @param
	 * @return
	 */
	public int updateXyzByContractIds(String contractIds){
		if (Check.NuNStrStrict(contractIds)) {
			return 0;
		}

		List<String> contractIdList = Arrays.asList(contractIds.split(","));
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("contractIds", contractIdList);
		return mybatisDaoContext.update(SQLID + "updateXyzByContractIds",paramMap);
	}

	/**
	  * @description: 此方法为app提交合同失败回滚使用,将合同状态由待支付更新为未签约,,同时删除合同号和合同的价格、提交时间等等数据
	  * @author: lusp
	  * @date: 2018/1/16 下午 16:25
	  * @params: contractId
	  * @return: int
	  */
	public int updateContractRollBackForAppSubmit(String contractId){
		RentContractEntity rentContractEntity = new RentContractEntity();
		rentContractEntity.setContractId(contractId);
		return mybatisDaoContext.update(SQLID + "updateContractRollBackForAppSubmit",rentContractEntity);
	}

	/**
	  * @description: 此方法为zrams提交合同失败回滚使用,将合同状态由待支付更新为未签约,修改不可修改标志,同时删除合同号和合同的价格、提交时间等等数据
	  * @author: lusp
	  * @date: 2018/1/16 下午 16:36
	  * @params: contractId
	  * @return: int
	  */
	public int updateContractRollBackForZramsSubmit(String contractId){
		RentContractEntity rentContractEntity = new RentContractEntity();
		rentContractEntity.setContractId(contractId);
		return mybatisDaoContext.update(SQLID + "updateContractRollBackForZramsSubmit",rentContractEntity);
	}

	/**
	 * <p>根据uid查询所有合同</p>
	 * @author xiangb
	 * @created 2017年10月3日
	 * @param
	 * @return
	 */
	public List<RentContractEntity> listContractByUid(String uid){
		return mybatisDaoContext.findAll(SQLID+"listContractByUid", RentContractEntity.class,uid);
	}
	/**
	 * <p>根据uid查询所有合同联合物业交割状态</p>
	 * @author xiangb
	 * @created 2017年10月3日
	 * @param
	 * @return
	 */
	public List<RentContractAndDetailEntity> listContractAndDetailByUid(String uid){
		return mybatisDaoContext.findAll(SQLID+"listContractAndDetailByUid", RentContractAndDetailEntity.class,uid);
	}

	/**
	  * @description: 根据合同id查询合同信息以及物业交割状态
	  * @author: lusp
	  * @date: 2017/12/20 下午 17:21
	  * @params: contractId
	  * @return: RentContractAndDetailEntity
	  */
	public List<RentContractAndDetailEntity> findContractAndDetailsByContractId(String contractId){
		return mybatisDaoContext.findAll(SQLID+"findContractAndDetailsByContractId",RentContractAndDetailEntity.class,contractId);
	}

    /**
      * @description: 分页查询需要关闭的首笔账单超时未支付的合同
      * @author: lusp
      * @date: 2017/10/13 上午 11:12
      * @params: paramMap
      * @return: PagingResult<RentContractEntity>
      */
    public PagingResult<CloseContractNotifyVo> findFirstBillPayOvertimeForPage(Map<String ,Object> paramMap){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(paramMap.get("page")==null?1:(int) paramMap.get("page"));
		pageBounds.setLimit(paramMap.get("limit")==null?100:(int) paramMap.get("limit"));
    	return mybatisDaoContext.findForPage(SQLID+"findFirstBillPayOvertimeForPage",CloseContractNotifyVo.class,paramMap,pageBounds);
	}

	/**
	 * @description: 分页查询首笔账单距离超时n小时未支付的合同
	 * @author: lusp
	 * @date: 2017/12/11 上午 11:16
	 * @params: paramMap
	 * @return: PagingResult<CloseContractNotifyVo>
	 */
	public PagingResult<CloseContractNotifyVo> findFirstBillPayBeforeOvertimeForPage(Map<String ,Object> paramMap){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(paramMap.get("page")==null?1:(int) paramMap.get("page"));
		pageBounds.setLimit(paramMap.get("limit")==null?100:(int) paramMap.get("limit"));
		return mybatisDaoContext.findForPage(SQLID+"findFirstBillPayBeforeOvertimeForPage",CloseContractNotifyVo.class,paramMap,pageBounds);
	}

	/**
	 * @description: 分页查询合同状态是续约中同时合同到期日后一天没有续约的合同
	 * @author: lusp
	 * @date: 2017/10/20 上午 11:15
	 * @params: paramMap
	 * @return: PagingResult<CloseContractNotifyVo>
	 */
	public PagingResult<CloseContractNotifyVo> findSameUnsignedForPage(Map<String ,Object> paramMap){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(paramMap.get("page")==null?1:(int) paramMap.get("page"));
		pageBounds.setLimit(paramMap.get("limit")==null?100:(int) paramMap.get("limit"));
		return mybatisDaoContext.findForPage(SQLID+"findSameUnsignedForPage",CloseContractNotifyVo.class,paramMap,pageBounds);
	}

	/**
	 * @description: 分页查询距离签约有效期小于n小时的未签约合同
	 * @author: lusp
	 * @date: 2017/10/20 上午 11:23
	 * @params: paramMap
	 * @return: PagingResult<CloseContractNotifyVo>
	 */
	public PagingResult<CloseContractNotifyVo> findSameDayBeforeOvertimeForPage(Map<String ,Object> paramMap){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(paramMap.get("page")==null?1:(int) paramMap.get("page"));
		pageBounds.setLimit(paramMap.get("limit")==null?100:(int) paramMap.get("limit"));
		return mybatisDaoContext.findForPage(SQLID+"findSameDayBeforeOvertimeForPage",CloseContractNotifyVo.class,paramMap,pageBounds);
	}

	/**
	 * @description: 分页查询需要关闭的当天未签约的合同
	 * @author: lusp
	 * @date: 2017/10/20 上午 11:12
	 * @params: paramMap
	 * @return: PagingResult<CloseContractNotifyVo>
	 */
	public PagingResult<CloseContractNotifyVo> findSameUnrenewedForPage(Map<String ,Object> paramMap){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(paramMap.get("page")==null?1:(int) paramMap.get("page"));
		pageBounds.setLimit(paramMap.get("limit")==null?100:(int) paramMap.get("limit"));
		return mybatisDaoContext.findForPage(SQLID+"findSameUnsignedForPage",CloseContractNotifyVo.class,paramMap,pageBounds);
	}


	/**
	 * <p>根据parent查询已签约合同</p>
	 * @author xiangb
	 * @created 2017年10月3日
	 * @param
	 * @return
	 */
	public List<RentContractEntity> findYqyContractByParentId(String parentId) {
		return mybatisDaoContext.findAll(SQLID+"findYqyContractByParentId", RentContractEntity.class, parentId);
	}

	/**
	 * 查询出可以进行续约操作的合同
	 * @param parentId
	 * @return
	 */
	public List<RentContractEntity> findCanRenewContractByParentId(String parentId) {
		return mybatisDaoContext.findAll(SQLID+"findCanRenewContractByParentId", RentContractEntity.class, parentId);
	}

	/**
	 * 查询出可以进行解约操作的合同
	 * @param parentId
	 * @return
	 */
	public List<RentContractEntity> findCanSurrenderContractByParentId(String parentId) {
		return mybatisDaoContext.findAll(SQLID+"findCanSurrenderContractByParentId", RentContractEntity.class, parentId);
	}
	public List<RentContractEntity> findContractListByParentId(String surParentRentId) {
		return mybatisDaoContext.findAll(SQLID+"findContractListByParentId", RentContractEntity.class, surParentRentId);
	}

	public String getContractSeq(String nextValName) {
		return mybatisDaoContext.findOne(SQLID + "nextval" , String.class, nextValName);
	}

	/**
	 * 更改合同状态
	 *
	 * @param contractId 合同标识
	 * @param status     合同状态
	 * @param closeType  合同关闭类型
	 * @return int
	 * @author cuigh6
	 * @Date 2017年10月13日
	 */
	public int updateContractStatus(String contractId, String status, Integer closeType) {
		RentContractEntity rentContractEntity = new RentContractEntity();
		rentContractEntity.setContractId(contractId);
		rentContractEntity.setConStatusCode(status);
		rentContractEntity.setCloseType(closeType);
		return mybatisDaoContext.update(SQLID + "updateBaseContractById", rentContractEntity);
	}
	/**
	 * <p>更新合同状态</p>
	 * @author xiangb
	 * @created 2017年10月17日
	 * @param rentContractEntity
	 * @return
	 */
	public int updateContractToTargetStatus(RentContractEntity rentContractEntity){
		return mybatisDaoContext.update(SQLID + "updateBaseContractById", rentContractEntity);
	}


	/**
	 * 根据父合同id更新合同客户信息
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public int updateContractCustomerInfoByParentId(String surParentRentId, RentEpsCustomerEntity rentEpsCustomerEntity) {
		RentContractEntity rentContractEntity = new RentContractEntity();
		//查询条件
		rentContractEntity.setSurParentRentId(surParentRentId);

		//需要修改的值
		rentContractEntity.setCustomerId(rentEpsCustomerEntity.getId());
		rentContractEntity.setCustomerUid(rentEpsCustomerEntity.getCustomerUid());
		rentContractEntity.setCustomerName(rentEpsCustomerEntity.getName());
		rentContractEntity.setCustomerMobile(rentEpsCustomerEntity.getContacterTel());
		rentContractEntity.setProxyPicurl(rentEpsCustomerEntity.getProxyPicurl());
		rentContractEntity.setUpdaterid(rentEpsCustomerEntity.getUpdaterId());
		rentContractEntity.setFupdatetime(new Date());



		return mybatisDaoContext.update(SQLID + "updateContractByParentId", rentContractEntity);
	}
	/**
	 * <p>更新合同签约人姓名和电话</p>
	 * @author xiangb
	 * @created 2017年10月23日
	 * @param contractId合同ID customerName客户姓名 customerMobile 目标状态
	 * @return
	 */
	public int updateContractCustomerName(String contractId,String customerName,String customerMobile){
		RentContractEntity rentContractEntity = new RentContractEntity();
		rentContractEntity.setContractId(contractId);
		rentContractEntity.setCustomerName(customerName);
		rentContractEntity.setCustomerMobile(customerMobile);
		return mybatisDaoContext.update(SQLID + "updateBaseContractById", rentContractEntity);
	}


	/**
	 * @description: 更新同步财务状态
	 * @author: lusp
	 * @date: 2017/10/23 上午 10:21
	 * @params: rentContractEntity
	 * @return: int
	 */
	public int updateSyncToFinByContractId(RentContractEntity rentContractEntity){
		return mybatisDaoContext.update(SQLID + "updateBaseContractById",rentContractEntity);
	}

	/**
	 * 根据父合同id修改所有子合同信息
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public int updateContractByParentId(RentContractEntity rentContractEntity) {
		return mybatisDaoContext.update(SQLID + "updateContractByParentId", rentContractEntity);
	}

	/**
	 * 根据合同id修改合同信息
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public int updateContractByContractId(RentContractEntity rentContractEntity) {
		return mybatisDaoContext.update(SQLID + "updateContractByContractId", rentContractEntity);
	}

    /**
     * 作废解约协议，还原合同状态
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时16分30秒
     */
    public int cancelSurrender(String foriginalstate, String zoId, String zoName, String contractId) {
        Map<String, Object> map = new HashMap<>();
        map.put("foriginalstate", foriginalstate);
        map.put("zoId", zoId);
        map.put("zoName", zoName);
        map.put("contractId", contractId);
        return mybatisDaoContext.update(SQLID + "cancelSurrender", map);
    }


    /**
     * 更新合同基本信息
     * @author jixd
     * @created 2017年10月27日 10:18:05
     * @param
     * @return
     */
    public int updateBaseContractById(RentContractEntity rentContractEntity){
		return mybatisDaoContext.update(SQLID + "updateBaseContractById",rentContractEntity);
	}


	/**
	 * 修改合同状态为待支付
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public int updateContractStatusDzfByParentId(String surParentRentId) {
		if (Check.NuNStrStrict(surParentRentId)) {
			return 0;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("surParentRentId", surParentRentId);
		int size = mybatisDaoContext.update(SQLID + "updateContractStatusDzfByParentId", map);
		return size;
	}

	/**
	 * 修改合同状态为待支付
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public int updateContractStatusYqyByParentId(String surParentRentId) {
		if (Check.NuNStrStrict(surParentRentId)) {
			return 0;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("surParentRentId", surParentRentId);
		int size = mybatisDaoContext.update(SQLID + "updateContractStatusYqyByParentId", map);
		return size;
	}

	/**
	 * 查询有效的签约邀请
	 * 有效:
	 * APP的，且日期大于等于当前时间且合同状态为未签约,
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public List<RentContractEntity> findWqyContractInviteByRoomIds(List<String> roomIdList, Date date) {

		Map<String, Object> map = new HashMap<>();
		map.put("roomIds", roomIdList);
		map.put("currentDate", date);
		List<RentContractEntity> rentContractEntityList = mybatisDaoContext.findAll(SQLID+"findWqyContractInviteByRoomIds", map);
		return rentContractEntityList;
	}
	/**
	 * <p>根据前合同号查询续约合同</p>
	 * @author xiangb
	 * @created 2017年11月3日
	 */
	public RentContractEntity findRenewContractByPreRentCode(String preRentCode){
		return mybatisDaoContext.findOne(SQLID+"findRenewContractByPreRentCode", RentContractEntity.class, preRentCode);
	}

	/**
	 * 只查询一个前合同的实体
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public RentContractEntity findOnePreContractInfoByPreSurParentRentId(String preSurParentRentId) {
		return mybatisDaoContext.findOne(SQLID+"findOnePreContractInfoByPreSurParentRentId", RentContractEntity.class, preSurParentRentId);
	}
	/**
	 * 父合同号查询下面所有子合同id
	 * @author jixd
	 * @created 2017年11月12日 11:25:08
	 * @param
	 * @return
	 */
	public List<RentContractEntity> listContractBySurParentRentId(String surParentRentId){
		return mybatisDaoContext.findAll(SQLID +"listContractBySurParentRentId",RentContractEntity.class,surParentRentId);
	}

	/**
	 * 查询已签约的合同列表通过uid
	 * @param uid
	 * @return
	 */
	public List<RentContractEntity> findYQYContractListByUid(String uid) {
		return mybatisDaoContext.findAll(SQLID + "findYQYContractListByUid", uid);
	}

	/**
	 * 批量获取父合同号
	 * @param contractCodes 子合同号列表
	 * @return
	 * @author cuigh6
	 * @Date 2017年11月
	 */
	public List<RentContractEntity> getBatchParentContractCode(List<String> contractCodes) {
		Map map = new HashMap();
		map.put("contractCodes", contractCodes);
		return this.mybatisDaoContext.findAll(SQLID + "getBatchParentContractCode", map);
	}

	/**
	 * 查询未同步到财务的合同
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public PagingResult<RentContractEntity> findContractNotSyncToFin(Date queryDate, PageBounds pageBounds) {
		Map map = new HashMap();
		map.put("queryDate", queryDate);
		return mybatisDaoContext.findForPage(SQLID+"findContractNotSyncToFin",RentContractEntity.class ,map,pageBounds);
	}

	/**
	 * 查询未生成合同文本pdf的合同
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public PagingResult<RentContractEntity> findContractNotTransferToPdf(Date queryDate, PageBounds pageBounds) {
		Map map = new HashMap();
		map.put("queryDate", queryDate);
		return mybatisDaoContext.findForPage(SQLID+"findContractNotTransferToPdf",RentContractEntity.class ,map,pageBounds);
	}

	/**
	 * @description: 根据父合同号查询子合同号集合
	 * @author: lusp
	 * @date: 2017/11/25 下午 17:46
	 * @params: parentCode
	 * @return: List<String>
	 */
	public List<String> getBatchContractCodeByParentCode(String parentCode) {
		return mybatisDaoContext.findAll(SQLID+"getBatchContractCodeByParentCode",String.class,parentCode);
	}

	/**
	 * @description: 根据父合同号批量查询合同号（除了已退租和已关闭的）
	 * @author: lusp
	 * @date: 2017/12/26 下午 17:46
	 * @params: parentCode
	 * @return: String
	 */
	public List<String> getCodesByParentCodeOnCondition(String parentCode) {
		return mybatisDaoContext.findAll(SQLID+"getCodesByParentCodeOnCondition",String.class,parentCode);
	}

	/**
	 * 分页查询合同信息
	 * @author jixd
	 * @created 2017年11月27日 15:31:18
	 * @param
	 * @return
	 */
	public PagingResult<ContractManageDto> listContractByPage(ContractSearchPageDto contractSearchPageDto){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(contractSearchPageDto.getPage());
		pageBounds.setLimit(contractSearchPageDto.getRows());
		Map<String, Object> map = contractSearchPageDto.toMap();
		return mybatisDaoContext.findForPage(SQLID + "listContractByPage",ContractManageDto.class,map,pageBounds);
	}

	/**
	 * 修改未签约房间价格
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public int updateWqyContractRoomSalesPrice(RentContractEntity rentContractEntity) {
		return mybatisDaoContext.update(SQLID + "updateWqyContractRoomSalesPrice",rentContractEntity);
	}

	/**
	 * 修改房间状态为已续约
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public int updateContractStatusYxyByCode(String conRentCode) {
		Map map = new HashMap();
		map.put("conRentCode", conRentCode);
		return mybatisDaoContext.update(SQLID + "updateContractStatusYxyByCode", map);
	}

	/**
	  * @description: 将合同状态由"已续约"--> "续约中"
	  * @author: lusp
	  * @date: 2017/12/14 下午 20:33
	  * @params: conRentCode
	  * @return: int
	  */
	public int updateContractStatusXyzByCode(String conRentCode) {
		Map map = new HashMap();
		map.put("conRentCode", conRentCode);
		return mybatisDaoContext.update(SQLID + "updateContractStatusXyzByCode", map);
	}

	/**
	 * 查询已过期合同更改状态
	 * @author jixd
	 * @created 2017年12月05日 16:00:23
	 * @param
	 * @return
	 */
	public PagingResult<RentContractEntity> listExpireContractPage(BasePageParamDto basePageParamDto){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(basePageParamDto.getPage());
		pageBounds.setLimit(basePageParamDto.getRows());
		return mybatisDaoContext.findForPage(SQLID + "listExpireContractPage",RentContractEntity.class,null,pageBounds);
	}

	/**
	 * 
	 * @author cuiyuhui
	 * @created  
	 * @param 
	 * @return 
	 */
	public int  updateIsTransferPdf(String contractId) {
		RentContractEntity rentContractEntity = new RentContractEntity();
		rentContractEntity.setContractId(contractId);
		rentContractEntity.setIsTransferPdf(1);
		return mybatisDaoContext.update(SQLID + "updateBaseContractById", rentContractEntity);
	}

    public String finOldDataContractId(String contractId) {
        return mybatisDaoContext.findOne(SQLID + "finOldDataContractId", String.class, contractId);
    }

	/**
	 * 通过房间标识 查询有效合同列表
	 * @param roomId 房间标识
	 * @return
	 * @author cuigh6
	 * @Date 2018年1月
	 */
	public List<RentContractEntity> findValidContractByRoomId(String roomId) {
		return mybatisDaoContext.findAll(SQLID + "findValidContractByRoomId", roomId);
	}

	/**
	 * @description: 根据主合同号查询合同列表
	 * @author: lusp
	 * @date: 2018/1/11 下午 18:12
	 * @params: surParentRentCode
	 * @return: List<RentContractEntity>
	 */
	public List<RentContractEntity> findContractListByParentCode(String surParentRentCode) {
		return mybatisDaoContext.findAll(SQLID + "findContractListByParentCode", surParentRentCode);
	}


	/**
	 *  查询当前房间的最近的合同
	 * @author yd
	 * @created
	 * @param
	 * @return
	 */
	public  List<RoomContractSmartVo>  findCurrentContract(RoomStmartDto roomStmartDto){
		return  mybatisDaoContext.findAll(SQLID + "findCurrentContract",RoomContractSmartVo.class,roomStmartDto);
	}
}
