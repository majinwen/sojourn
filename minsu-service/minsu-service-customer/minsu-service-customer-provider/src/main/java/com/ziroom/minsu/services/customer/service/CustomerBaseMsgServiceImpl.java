/**
 * @FileName: CustomerBaseMsgServiceImpl.java
 * @Package com.ziroom.minsu.services.customer.service
 * 
 * @author bushujie
 * @created 2016年4月22日 下午5:07:26
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerUpdateFieldAuditManagerEntity;
import com.ziroom.minsu.entity.customer.CustomerUpdateFieldAuditNewlogEntity;
import com.ziroom.minsu.entity.customer.CustomerUpdateHistoryExtLogEntity;
import com.ziroom.minsu.entity.customer.CustomerUpdateHistoryLogEntity;
import com.ziroom.minsu.services.common.utils.ClassReflectUtils;
import com.ziroom.minsu.services.customer.dao.CustomerBaseMsgDao;
import com.ziroom.minsu.services.customer.dao.CustomerBaseMsgExtDao;
import com.ziroom.minsu.services.customer.dao.CustomerOperHistoryDao;
import com.ziroom.minsu.services.customer.dao.CustomerPicMsgDao;
import com.ziroom.minsu.services.customer.dao.CustomerUpdateFieldAuditManagerDao;
import com.ziroom.minsu.services.customer.dao.CustomerUpdateFieldAuditNewlogDao;
import com.ziroom.minsu.services.customer.dao.CustomerUpdateHistoryExtLogDao;
import com.ziroom.minsu.services.customer.dao.CustomerUpdateHistoryLogDao;
import com.ziroom.minsu.services.customer.dto.CustomerAuditRequest;
import com.ziroom.minsu.services.customer.dto.CustomerBaseExtDto;
import com.ziroom.minsu.services.customer.dto.CustomerBaseMsgDto;
import com.ziroom.minsu.services.customer.dto.CustomerExtDto;
import com.ziroom.minsu.services.customer.dto.StaticsCusBaseReqDto;
import com.ziroom.minsu.services.customer.entity.CustomerAuthVo;
import com.ziroom.minsu.services.customer.entity.CustomerDetailImageVo;
import com.ziroom.minsu.services.customer.entity.CustomerDetailVo;
import com.ziroom.minsu.services.customer.entity.CustomerExt;
import com.ziroom.minsu.services.customer.util.CustomerUtils;
import com.ziroom.minsu.valenum.customer.AuditStatusEnum;
import com.ziroom.minsu.valenum.customer.CustomerAuditStatusEnum;
import com.ziroom.minsu.valenum.customer.CustomerIdTypeEnum;
import com.ziroom.minsu.valenum.customer.CustomerPicTypeEnum;
import com.ziroom.minsu.valenum.customer.CustomerUpdateLogEnum;
import com.ziroom.minsu.valenum.house.CreaterTypeEnum;
import com.ziroom.minsu.valenum.house.HouseSourceEnum;
import com.ziroom.minsu.valenum.house.IsTextEnum;

/**
 * <p>TODO</p>
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
@Service("customer.customerBaseMsgServiceImpl")
public class CustomerBaseMsgServiceImpl {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerBaseMsgServiceImpl.class);
	
	
	@Resource(name="customer.customerBaseMsgDao")
	private CustomerBaseMsgDao customerBaseMsgDao;
	
	@Resource(name="customer.customerBaseMsgExtDao")
	private CustomerBaseMsgExtDao customerBaseMsgExtDao;
	
	@Resource(name="customer.customerPicMsgDao")
	private CustomerPicMsgDao customerPicMsgDao;
	
	@Resource(name="customer.customerUpdateHistoryLogDao")
	private CustomerUpdateHistoryLogDao customerUpdateHistoryLogDao;

	@Resource(name="customer.customerUpdateHistoryExtLogDao")
    private CustomerUpdateHistoryExtLogDao customerUpdateHistoryExtLogDao;
    
    @Resource(name="customer.customerUpdateFieldAuditManagerDao")
    private CustomerUpdateFieldAuditManagerDao customerUpdateFieldAuditManagerDao;
    
    @Resource(name="customer.customerUpdateFieldAuditNewlogDao")
    private CustomerUpdateFieldAuditNewlogDao customerUpdateFieldAuditNewlogDao;

    @Resource(name="customer.customerOperHistoryDao")
    private CustomerOperHistoryDao customerOperHistoryDao;
	/**
	 * 
	 * 更新客户基础信息
	 *
	 * @author bushujie
	 * @created 2016年4月22日 下午5:10:05
	 *
	 * @param mobileNo
	 * @param uid
	 */
	public void updateCustomerInfo(CustomerBaseMsgEntity customerBaseMsgEntity){
		CustomerBaseMsgEntity oldCustomer = customerBaseMsgDao.queryCustomerBaseMsgByUid(customerBaseMsgEntity.getUid());
		if(!Check.NuNObj(oldCustomer) && !Check.NuNObj(customerBaseMsgEntity) && !Check.NuNObj(oldCustomer.getAuditStatus())
				&& oldCustomer.getAuditStatus()==CustomerAuditStatusEnum.AUDIT_ADOPT.getCode()){
			//用户修改昵称，添加日志
			saveCustomereMsgUpdateLog(customerBaseMsgEntity, oldCustomer);
		}else{
			customerBaseMsgDao.updateCustomerBaseMsg(customerBaseMsgEntity);
		}
	}
	
	/**
	 * 
	 * 插入客户基本信息
	 *
	 * @author bushujie
	 * @created 2016年4月25日 下午1:56:19
	 *
	 * @param customerBaseMsgEntity
	 */
	public void insertCustomerInfo(CustomerBaseMsgEntity customerBaseMsgEntity){
		customerBaseMsgDao.insertCustomerBaseMsg(customerBaseMsgEntity);
	}
	
    /**
	 * 查询客户列表
	 *
	 * @author jixd
	 * @created 2016年4月25日 上午9:53:57
	 *
	 * @param customerBaseMsgDto
	 * @return
	 */
	public PagingResult<CustomerBaseMsgEntity> queryCustomerBaseMsg(CustomerBaseMsgDto customerBaseMsgDto){
		return customerBaseMsgDao.queryCustomerBaseMsg(customerBaseMsgDto);
	}


    /**
     * 查询用户角色列表
     * @author afi
     * @param customerExtDto
     * @return
     */
    public PagingResult<CustomerExt> getCustomerExtList(CustomerExtDto customerExtDto){
        return customerBaseMsgDao.getCustomerExtList(customerExtDto);
    }





	/**
	 * 
	 * 查询客户详细信息
	 *
	 * @author jixd
	 * @created 2016年4月25日 上午9:55:14
	 *
	 * @param uid
	 * @return
	 */
	public CustomerDetailVo getCustomerDetail(String uid){
		return customerBaseMsgDao.getCustomerDetail(uid);
	}
	/**
	 * 
	 * 获取带图片的用户详情
	 *
	 * @author jixd
	 * @created 2016年5月25日 下午9:53:49
	 *
	 * @param uid
	 * @return
	 */
	public CustomerDetailImageVo getCustomerDetailImage(String uid){
		return customerBaseMsgDao.getCustomerDetailImage(uid);
	}
	
	/**
	 * 
	 * 查询客户基本信息，根据uid
	 *
	 * @author bushujie
	 * @created 2016年4月26日 下午5:12:24
	 *
	 * @param uid
	 * @return
	 */
	public CustomerBaseMsgEntity getCustomerBaseMsgEntitybyUid(String uid){
		return customerBaseMsgDao.queryCustomerBaseMsgByUid(uid);
	}
	
	/**
	 * 根据uidList批量查询客户信息
	 * 注意uidList不要太长
	 * @author lishaochuan
	 * @create 2016年8月10日下午4:16:55
	 * @param uidList
	 * @return
	 */
	public List<CustomerBaseMsgEntity> queryCustomerListByUidList(List<String> uidList){
		return customerBaseMsgDao.queryCustomerListByUidList(uidList);
	}
	
	/**
	 * 
	 * 条件查询用户基本信息（条件不让为null 为null后就直接查全库了）
	 * 
	 * @author yd
	 * @created 2016年5月10日 下午5:36:54
	 *
	 * @param customerDto
	 * @return
	 */
	public List<CustomerBaseMsgEntity> selectByCondition(CustomerBaseMsgDto customerBaseDto){
		return customerBaseMsgDao.selectByCondition(customerBaseDto);
	}
	
	/**
	 * 
	 * get entity by uid
	 *
	 * @author yd
	 * @created 2016年5月26日 下午9:48:38
	 *
	 * @param uid
	 * @return
	 */
	public CustomerBaseMsgExtEntity selectCustomerExtByUid(String uid){
		return customerBaseMsgExtDao.selectByUid(uid);
	}
	
	/**
	 * 
	 * 保存用户扩展信息
	 *
	 * @author yd
	 * @created 2016年5月26日 下午9:51:41
	 *
	 * @param customerBaseMsgExt
	 * @return
	 */
	public int insertCustomerExtSelective(CustomerBaseMsgExtEntity customerBaseMsgExt){
		return customerBaseMsgExtDao.insertSelective(customerBaseMsgExt);
	}
	
	/**
	 * 
	 * update entity by uid
	 *
	 * @author yd
	 * @created 2016年5月26日 下午9:54:55
	 *
	 * @param customerBaseMsgExt
	 * @return
	 */
	public int updateCustomerExtByUidSelective(CustomerBaseMsgExtEntity customerBaseMsgExt){
		CustomerBaseMsgExtEntity oldCustomerExt=customerBaseMsgExtDao.selectByUid(customerBaseMsgExt.getUid());
		int i=0;
		CustomerBaseMsgEntity customerBaseMsg = customerBaseMsgDao.queryCustomerBaseMsgByUid(customerBaseMsgExt.getUid());
		if(!Check.NuNObj(oldCustomerExt) && !Check.NuNObj(customerBaseMsg) && !Check.NuNObj(customerBaseMsg.getAuditStatus())
				&& customerBaseMsg.getAuditStatus()==CustomerAuditStatusEnum.AUDIT_ADOPT.getCode()){
			//用户修改个人介绍，添加日志
			i = i + saveCustomereMsgExtUpdateLog(customerBaseMsgExt, oldCustomerExt);
		}else{
			customerBaseMsgExtDao.updateByUidSelective(customerBaseMsgExt);
		}
		return i;
	}
	
	/**
	 * 
	 * 手机号查询客户信息
	 *
	 * @author bushujie
	 * @created 2016年7月11日 下午4:50:43
	 *
	 * @param mobile
	 * @return
	 */
	public CustomerBaseMsgEntity getCustomerByMobile(String mobile){
		return customerBaseMsgDao.getCustomerByMobile(mobile);
	}
	
	/**
	 * 
	 * 手机号查询所有用户信息集合
	 *
	 * @author lunan
	 * @created 2016年10月8日 下午10:00:13
	 *
	 * @param mobile
	 * @return
	 */
	public List<CustomerBaseMsgEntity> getCustomerListByMobile(String mobile){
		return customerBaseMsgDao.getCustomerListByMobile(mobile);
		
	}

	/**
	 * 分页查询业主信息
	 * @author liyingjie
	 * @created 2016年7月11日 下午4:50:43
	 * @return
	 */
	public PagingResult<CustomerBaseMsgEntity> staticsGetLandlordList(StaticsCusBaseReqDto staticsCusBaseReqDto){
		return customerBaseMsgDao.staticsGetLandlordList(staticsCusBaseReqDto);
	}

	 /**
     * 统计房东的数量
     * @author liyingjie
     * @param
     * @return
     */
    public Long countLanlordNum(){
        return customerBaseMsgDao.countLanlordNum();
    }

	/**
	 *
	 * pc端更新客户个人资料
	 *
	 * @author bushujie
	 * @created 2016年7月26日 下午3:03:04
	 *
	 * @param customerBaseExtDto
	 */
	public int updatePersonDataForPc(CustomerBaseExtDto customerBaseExtDto){
		int upNum = 0;
		CustomerBaseMsgEntity oldCustomerBaseMsg = customerBaseMsgDao.queryCustomerBaseMsgByUid(customerBaseExtDto.getUid());
		
		CustomerBaseMsgEntity customerBase=new CustomerBaseMsgEntity();
		BeanUtils.copyProperties(customerBaseExtDto, customerBase);
		//房东昵称更新或修改
		if(!Check.NuNObj(oldCustomerBaseMsg) && !Check.NuNObj(oldCustomerBaseMsg.getAuditStatus())
				&& oldCustomerBaseMsg.getAuditStatus()==CustomerAuditStatusEnum.AUDIT_ADOPT.getCode()){
			saveCustomereMsgUpdateLog(customerBase, oldCustomerBaseMsg);
		}else{
			 upNum=customerBaseMsgDao.updateCustomerBaseMsg(customerBase);
		}
		
		//个人介绍更新或者插入
		CustomerBaseMsgExtEntity customerExt=customerBaseMsgExtDao.selectByUid(customerBaseExtDto.getUid());
		
		if(!Check.NuNObj(customerExt)){
			//用户修改个人介绍，添加日志
			if(!Check.NuNStr(customerBaseExtDto.getCustomerIntroduce()) && !Check.NuNObj(oldCustomerBaseMsg) && !Check.NuNObj(oldCustomerBaseMsg.getAuditStatus())
					&& oldCustomerBaseMsg.getAuditStatus()==CustomerAuditStatusEnum.AUDIT_ADOPT.getCode()){
				CustomerBaseMsgExtEntity newCustomerExt = new CustomerBaseMsgExtEntity();
				newCustomerExt.setCustomerIntroduce(customerBaseExtDto.getCustomerIntroduce());
				saveCustomereMsgExtUpdateLog(newCustomerExt, customerExt);
			}else{
				customerExt.setCustomerIntroduce(customerBaseExtDto.getCustomerIntroduce());
				customerBaseMsgExtDao.updateByUidSelective(customerExt);
			}
		} else {
			customerExt=new CustomerBaseMsgExtEntity();
			customerExt.setFid(UUIDGenerator.hexUUID());
			customerExt.setUid(customerBaseExtDto.getUid());
			customerExt.setCustomerIntroduce(customerBaseExtDto.getCustomerIntroduce());
			customerBaseMsgExtDao.insertSelective(customerExt);
		}
		return upNum;
	}

	/**
	 *
	 * 获取客户认证资料信息
	 *
	 * @author bushujie
	 * @created 2016年7月26日 下午8:32:29
	 *
	 * @param uid
	 * @return
	 */
	public CustomerAuthVo getCustomerAuthDataForPc(String uid){
		CustomerAuthVo customerAuthVo = customerBaseMsgDao.getCustomerAuthData(uid);
		if(customerAuthVo.getIdType().intValue()==CustomerIdTypeEnum.CHARTER.getCode()){
			Map<String, Object> map = new HashMap<>();
			map.put("uid", uid);
			map.put("picType", CustomerPicTypeEnum.YYZZ.getCode());
			CustomerPicMsgEntity customerPicMsgEntity = customerPicMsgDao.getCustomerPicByType(map);
			customerAuthVo.setVoucherHandPic(customerPicMsgEntity);
		}
		return customerAuthVo;
	}

	/**
	 *
	 * 更新客户认证资料
	 *
	 * @author bushujie
	 * @created 2016年7月27日 下午8:49:06
	 *
	 * @param customerAuthVo
	 * @return
	 */
	public List<CustomerPicMsgEntity> updateCustomerAuthData(CustomerAuthVo customerAuthVo){
		//接收最新图片集合
		List<CustomerPicMsgEntity> picList=new ArrayList<CustomerPicMsgEntity>();
		CustomerBaseMsgEntity customerBaseMsgEntity=new CustomerBaseMsgEntity();
		BeanUtils.copyProperties(customerAuthVo, customerBaseMsgEntity);
		int upNum=customerBaseMsgDao.updateCustomerBaseMsg(customerBaseMsgEntity);
		//查询类型图片参数
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("uid", customerAuthVo.getUid());
		CustomerPicMsgEntity customerPicMsgEntity=null;
		//更新客户证件图片(正面)
		if(!Check.NuNStr(customerAuthVo.getVoucherFrontPic().getFid())){
			customerPicMsgDao.updateCustomerPicMsg(customerAuthVo.getVoucherFrontPic());
		} else {
			//查询是否已存在正面图片
			paramMap.put("picType", CustomerPicTypeEnum.ZJZM.getCode());
			customerPicMsgEntity=customerPicMsgDao.getCustomerPicByType(paramMap);
			if(Check.NuNObj(customerPicMsgEntity)){
				customerAuthVo.getVoucherFrontPic().setFid(UUIDGenerator.hexUUID());
				customerPicMsgDao.insertCustomerPicMsg(customerAuthVo.getVoucherFrontPic());
			} else {
				customerAuthVo.getVoucherFrontPic().setFid(customerPicMsgEntity.getFid());
				customerPicMsgDao.updateCustomerPicMsg(customerAuthVo.getVoucherFrontPic());
			}
		}
		picList.add(customerAuthVo.getVoucherFrontPic());
		//更新客户证件图片(反面)
		if(!Check.NuNStr(customerAuthVo.getVoucherBackPic().getFid())){
			customerPicMsgDao.updateCustomerPicMsg(customerAuthVo.getVoucherBackPic());
		} else {
			//查询是否已存在反面图片
			paramMap.put("picType", CustomerPicTypeEnum.ZJFM.getCode());
			customerPicMsgEntity=customerPicMsgDao.getCustomerPicByType(paramMap);
			if(Check.NuNObj(customerPicMsgEntity)){
				customerAuthVo.getVoucherBackPic().setFid(UUIDGenerator.hexUUID());
				customerPicMsgDao.insertCustomerPicMsg(customerAuthVo.getVoucherBackPic());
			} else {
				customerAuthVo.getVoucherBackPic().setFid(customerPicMsgEntity.getFid());
				customerPicMsgDao.updateCustomerPicMsg(customerAuthVo.getVoucherBackPic());
			}
		}
		picList.add(customerAuthVo.getVoucherBackPic());
		//更新客户证件图片(手持)
		if(!Check.NuNStr(customerAuthVo.getVoucherHandPic().getFid())){
			customerPicMsgDao.updateCustomerPicMsg(customerAuthVo.getVoucherHandPic());
		} else {
			//查询是否已存在手持图片
			if(customerAuthVo.getIdType()==CustomerIdTypeEnum.CHARTER.getCode()){
				paramMap.put("picType", CustomerPicTypeEnum.YYZZ.getCode());
			}else{
				paramMap.put("picType", CustomerPicTypeEnum.ZJSC.getCode());
			}
			customerPicMsgEntity=customerPicMsgDao.getCustomerPicByType(paramMap);
			if(Check.NuNObj(customerPicMsgEntity)){
				customerAuthVo.getVoucherHandPic().setFid(UUIDGenerator.hexUUID());
				customerPicMsgDao.insertCustomerPicMsg(customerAuthVo.getVoucherHandPic());
			} else {
				customerAuthVo.getVoucherHandPic().setFid(customerPicMsgEntity.getFid());
				customerPicMsgDao.updateCustomerPicMsg(customerAuthVo.getVoucherHandPic());
			}
		}
		picList.add(customerAuthVo.getVoucherHandPic());
		return picList;
	}
	
	/**
	 * 
	 * api同步用户 部分更新用户（其他接口 不要用）
	 *
	 * @author bushujie
	 * @created 2016年4月22日 下午4:15:46
	 *
	 * @param customerBaseMsgEntity
	 * @return
	 */
	public int updateCustomerBaseMsgPortion(CustomerBaseMsgEntity customerBaseMsgEntity){
		return this.customerBaseMsgDao.updateCustomerBaseMsgPortion(customerBaseMsgEntity);
	}

	/**
	 * 根据手机号和名称获取用户uid
	 *
	 * @author loushuai
	 * @created 2017年5月25日 下午2:25:59
	 *
	 * @param customerBaseMsgEntity
	 */
	public List<CustomerBaseMsgEntity> getByCustomNameAndTel(Map<String, Object> paramMap) {
		return this.customerBaseMsgDao.getByCustomNameAndTel(paramMap);
	}
	
	/**
	 * 
	 * pc修改个人介绍，添加修改日志
	 * 
	 * 有修改 即保存
	 *
	 * @author loushuai
	 * @created 2017年8月7日 下午2:06:55
	 *
	 * @param newCustomerMsgExt
	 * @param oldCustomerMsgExt
	 */
	private  int saveCustomereMsgExtUpdateLog(CustomerBaseMsgExtEntity newCustomerMsgExt,CustomerBaseMsgExtEntity oldCustomerMsgExt){
		LogUtil.info(LOGGER, "saveCustomereMsgExtUpdateLog，newCustomerMsgExt={}，oldCustomerMsgExt={}",
				JsonEntityTransform.Object2Json(newCustomerMsgExt),JsonEntityTransform.Object2Json(oldCustomerMsgExt));
		int i=0;
		if(Check.NuNObj(newCustomerMsgExt)||Check.NuNStr(oldCustomerMsgExt.getFid())){
			return 0;
		}

		List<CustomerUpdateHistoryLogEntity> list = new ArrayList<CustomerUpdateHistoryLogEntity>();
		
		CustomerUtils.contrastCustomerBaseExtObj(newCustomerMsgExt, oldCustomerMsgExt, list);

		if(!Check.NuNCollection(list)){
			for (CustomerUpdateHistoryLogEntity customerUpdateHistoryLog : list) {
				customerUpdateHistoryLog.setUid(oldCustomerMsgExt.getUid());
		    	customerUpdateHistoryLog.setCreaterFid(oldCustomerMsgExt.getUid());
				customerUpdateHistoryLog.setCreaterType(CreaterTypeEnum.LANLORD.getCode());
				customerUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
				customerUpdateHistoryLog.setFieldDesc(CustomerUpdateLogEnum.Customer_Base_Msg_Ext_Introduce.getFieldDesc());
				customerUpdateHistoryLog.setFieldPath(ClassReflectUtils.getFieldNamePath(CustomerBaseMsgExtEntity.class,CustomerUpdateLogEnum.Customer_Base_Msg_Ext_Introduce.getFieldName()));
				String fieldPathKey = MD5Util.MD5Encode(customerUpdateHistoryLog.getFieldPath()+oldCustomerMsgExt.getUid(), "UTF-8");
				customerUpdateHistoryLog.setFieldPathKey(fieldPathKey);
				customerUpdateHistoryLog.setNewValue(newCustomerMsgExt.getCustomerIntroduce());
				customerUpdateHistoryLog.setOldValue(oldCustomerMsgExt.getCustomerIntroduce());
				customerUpdateHistoryLog.setSourceType(HouseSourceEnum.PC.getCode());
			    i = i+ saveCustomerUpdateHistoryLog(customerUpdateHistoryLog, oldCustomerMsgExt.getUid());
				return  i;
			}
		}

        return i;
	}
	
	/**
	 * 
	 * pc修改昵称，添加修改日志
	 * 
	 * 有修改 即保存
	 *
	 * @author loushuai
	 * @created 2017年8月7日 下午2:06:55
	 *
	 * @param newCustomerMsg
	 * @param oldCustomerMsg
	 */
	private  int saveCustomereMsgUpdateLog(CustomerBaseMsgEntity newCustomerMsg,CustomerBaseMsgEntity oldCustomerMsg){
		LogUtil.info(LOGGER, "saveCustomereMsgUpdateLog，newCustomerMsg={}，oldCustomerMsg={}",
				JsonEntityTransform.Object2Json(newCustomerMsg),JsonEntityTransform.Object2Json(oldCustomerMsg));
		int i=0;
		if(Check.NuNObj(newCustomerMsg)||Check.NuNStr(oldCustomerMsg.getUid())){
			return 0;
		}

		List<CustomerUpdateHistoryLogEntity> list = new ArrayList<CustomerUpdateHistoryLogEntity>();
		
		CustomerUtils.contrastCustomerBaseMsgEntityObj(newCustomerMsg, oldCustomerMsg, list);

		if(!Check.NuNCollection(list)){
			for (CustomerUpdateHistoryLogEntity customerUpdateHistoryLog : list) {
				customerUpdateHistoryLog.setUid(oldCustomerMsg.getUid());
		    	customerUpdateHistoryLog.setCreaterFid(oldCustomerMsg.getUid());
				customerUpdateHistoryLog.setCreaterType(CreaterTypeEnum.LANLORD.getCode());
				customerUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
				customerUpdateHistoryLog.setFieldDesc(CustomerUpdateLogEnum.Customer_Base_Msg_NickName.getFieldDesc());
				customerUpdateHistoryLog.setFieldPath(ClassReflectUtils.getFieldNamePath(CustomerBaseMsgEntity.class,CustomerUpdateLogEnum.Customer_Base_Msg_NickName.getFieldName()));
				String fieldPathKey = MD5Util.MD5Encode(customerUpdateHistoryLog.getFieldPath()+oldCustomerMsg.getUid(), "UTF-8");
				customerUpdateHistoryLog.setFieldPathKey(fieldPathKey);
				customerUpdateHistoryLog.setNewValue(newCustomerMsg.getNickName());
				customerUpdateHistoryLog.setOldValue(oldCustomerMsg.getNickName());
				customerUpdateHistoryLog.setSourceType(HouseSourceEnum.PC.getCode());
			    i = i+ saveCustomerUpdateHistoryLog(customerUpdateHistoryLog, oldCustomerMsg.getUid());
				return  i;
			}
		}
		newCustomerMsg.setNickName(null);
		customerBaseMsgDao.updateCustomerBaseMsg(newCustomerMsg);
        return i;
	}
	
	
	/**
	 * 
	 * 保存 
	 * 1. 保存 修改历史 CustomerUpdateHistoryLogEntity 如果 是大字段 则存入大字段表
	 * 2. 更新 最新记录表
	 *
	 * @author loushuai
	 * @created 2017年8月7日 下午4:34:42
	 *
	 * @param customerUpdateHistoryLog
	 */
	public int saveCustomerUpdateHistoryLog(CustomerUpdateHistoryLogEntity customerUpdateHistoryLog,String uid){
		
		if(!Check.NuNObj(customerUpdateHistoryLog)){
			
			if(Check.NuNStr(customerUpdateHistoryLog.getFid())) customerUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
			//此字段 必传 在上层做校验
			int isTest = customerUpdateHistoryLog.getIsText();
			int i =  0;
			if(isTest == IsTextEnum.IS_TEST.getCode()){
				
				LogUtil.info(LOGGER, "【保存房东昵称修改大字段】isTest={},fieldPathKey={},", isTest,customerUpdateHistoryLog.getFieldPathKey());
				CustomerUpdateHistoryExtLogEntity customerUpdateHistoryExtLog = new CustomerUpdateHistoryExtLogEntity();
				customerUpdateHistoryExtLog.setFid(customerUpdateHistoryLog.getFid());
				customerUpdateHistoryExtLog.setNewValue(customerUpdateHistoryLog.getNewValue());
				customerUpdateHistoryExtLog.setOldValue(customerUpdateHistoryLog.getOldValue());
				customerUpdateHistoryLog.setNewValue("");
				customerUpdateHistoryLog.setOldValue("");
				
				i = i + this.customerUpdateHistoryExtLogDao.insertSelective(customerUpdateHistoryExtLog);
			}
			i = i + this.customerUpdateHistoryLogDao.insertSelective(customerUpdateHistoryLog);
			//t_customer_update_field_audit_newlog 审核字段的最新记录 只做第一次插入  这里状态更改在 审核时候 才会状态更改  
			if(i>0){
				//查询 审核字段
				String md5Encode = MD5Util.MD5Encode(customerUpdateHistoryLog.getFieldPath(), "UTF-8");
				CustomerUpdateFieldAuditManagerEntity 	customerUpdateFieldAuditManagerEntity = this.customerUpdateFieldAuditManagerDao.findCustomerUpdateFieldAuditManagerByFid(md5Encode);
				if(Check.NuNObj(customerUpdateFieldAuditManagerEntity)){
					LogUtil.info(LOGGER, "【保存房东审核字段】当前字段非审核字段：fieldPath={}", customerUpdateHistoryLog.getFieldPath());
					return i;
				}
				CustomerBaseMsgEntity customerBaseMsg = customerBaseMsgDao.queryCustomerBaseMsgByUid(uid);
				if(Check.NuNObj(customerBaseMsg)){
					//房东如果是非认证状态，不插入newlog表
					return i;
				}
				CustomerUpdateFieldAuditNewlogEntity customerUpdateFieldAuditNewlog = this.customerUpdateFieldAuditNewlogDao.findCustomerUpdateFieldAuditNewlogByFid(customerUpdateHistoryLog.getFieldPathKey());
				if(Check.NuNObj(customerUpdateFieldAuditNewlog)){
					customerUpdateFieldAuditNewlog = new CustomerUpdateFieldAuditNewlogEntity();
					customerUpdateFieldAuditNewlog.setFieldPath(customerUpdateHistoryLog.getFieldPath());
					customerUpdateFieldAuditNewlog.setFieldDesc(customerUpdateHistoryLog.getFieldDesc());
					customerUpdateFieldAuditNewlog.setFid(customerUpdateHistoryLog.getFieldPathKey());
					customerUpdateFieldAuditNewlog.setCreaterFid(customerUpdateHistoryLog.getCreaterFid());
					customerUpdateFieldAuditNewlog.setCreaterType(customerUpdateHistoryLog.getCreaterType());
					customerUpdateFieldAuditNewlog.setFieldAuditStatu(AuditStatusEnum.SUBMITAUDIT.getCode());
					customerUpdateFieldAuditNewlog.setUid(customerUpdateHistoryLog.getUid());
					i = i + this.customerUpdateFieldAuditNewlogDao.insertSelective(customerUpdateFieldAuditNewlog);
				}else{
					customerUpdateFieldAuditNewlog.setFieldAuditStatu(CustomerAuditStatusEnum.UN_AUDIT.getCode());
					i = i + customerUpdateFieldAuditNewlogDao.updateCustomerUpdateFieldAuditNewlogByFid(customerUpdateFieldAuditNewlog);
				}
			}
			
			return i;
		}
		
		
		return 0;
	}

	/**
	 * 审核拒绝
	 *
	 * @author loushuai
	 * @created 2017年8月9日 上午11:25:40
	 *
	 * @param customerUpdateFieldAuditNewlog
	 * @return
	 */
	public int updateCustomerUpdateFieldAuditNewlogByFid(CustomerUpdateFieldAuditNewlogEntity customerUpdateFieldAuditNewlog) {
		return customerUpdateFieldAuditNewlogDao.updateCustomerUpdateFieldAuditNewlogByFid(customerUpdateFieldAuditNewlog);
	}

	/**
	 * 审核后，修改base，ext，pic三个表中的数据
	 *
	 * @author loushuai
	 * @created 2017年8月9日 下午2:37:51
	 *
	 * @param object2Json
	 * @return
	 */
	public int updateBaseAndExtOrPic(CustomerAuditRequest customerAuditRequest) {
		LogUtil.info(LOGGER, "updateBaseAndExtOrPic方法参数，customerAuditRequest={}", JsonEntityTransform.Object2Json(customerAuditRequest));
		int num =0;
		CustomerBaseMsgEntity customerBaseMsg = customerAuditRequest.getCustomerBaseMsg();
		
		//更新用户base表
		if(!Check.NuNObj(customerBaseMsg)){
			num = num + customerBaseMsgDao.updateCustomerBaseMsg(customerBaseMsg);
		}
		
		if(!Check.NuNObj(customerAuditRequest.getHistoryEntity())){
			customerOperHistoryDao.insertCustomerOperHistoryEntity(customerAuditRequest.getHistoryEntity());
		}
		
		return num;
	}
	
	
	/**
	 * 
	 * 用户信息 审核通过
	 *
	 * @author yd
	 * @created 2017年9月12日 上午9:10:23
	 *
	 * @param customerAuditRequest
	 */
	public void auditedCustomerInfo(CustomerAuditRequest customerAuditRequest){
		
		CustomerBaseMsgExtEntity customerBaseMsgExt = customerAuditRequest.getCustomerBaseMsgExt();
		CustomerPicMsgEntity customerPicMsg = customerAuditRequest.getCustomerPicMsg();
		CustomerBaseMsgEntity customerBaseMsg = customerAuditRequest.getCustomerBaseMsg();
		
		//待修改对象 fid
		Map<String, Object> fieldAuditNewLogMap = customerAuditRequest.getFieldAuditNewLogMap();
		
		if(!Check.NuNMap(fieldAuditNewLogMap)){
			Object introduceRejectId = fieldAuditNewLogMap.get("introduceRejectId");
			Object headPicRejectId = fieldAuditNewLogMap.get("headPicRejectId");
			Object unAuditNickNameFieldFid = fieldAuditNewLogMap.get("unAuditNickNameFieldFid");
			
			LogUtil.info(LOGGER, "【用户信息审核通过】introduceRejectId={},headPicRejectId={}", introduceRejectId,headPicRejectId);
			//审核用户介绍
			if(!Check.NuNObjs(introduceRejectId,customerBaseMsgExt)
					&&!Check.NuNStr(customerBaseMsgExt.getCustomerIntroduce())
					&&!Check.NuNStr(customerBaseMsgExt.getUid())){
				//更新 附上新值
				int changeIntroduce = customerBaseMsgExtDao.updateByUidSelective(customerBaseMsgExt);
				if(changeIntroduce > 0){
					CustomerUpdateFieldAuditNewlogEntity customerUpdateFieldAuditNewlog = new CustomerUpdateFieldAuditNewlogEntity();
					customerUpdateFieldAuditNewlog.setFid(introduceRejectId.toString());
					customerUpdateFieldAuditNewlog.setFieldAuditStatu(CustomerAuditStatusEnum.AUDIT_ADOPT.getCode());
					customerUpdateFieldAuditNewlog.setUid(customerBaseMsgExt.getUid());
					customerUpdateFieldAuditNewlogDao.updateCustomerUpdateFieldAuditNewlogByFid(customerUpdateFieldAuditNewlog);
				}
			}
			
			//更新头像 当前只能有一张 审核通过的头像
			if(!Check.NuNObj(customerPicMsg) 
					&& !Check.NuNObj(customerPicMsg.getFid()) 
					&& !Check.NuNStr(customerPicMsg.getUid()) 
					&& !Check.NuNObj(headPicRejectId)){
				int num = 0;
				int newPic = customerPicMsgDao.updatePicMsgByCondition(customerPicMsg);
				num = num + newPic;
				if(newPic>0){
					num = num + customerPicMsgDao.updateSetOtherHeadPicIsdel(customerPicMsg);
					CustomerUpdateFieldAuditNewlogEntity customerUpdateFieldAuditNewlog = new CustomerUpdateFieldAuditNewlogEntity();
					customerUpdateFieldAuditNewlog.setFid(headPicRejectId.toString());
					customerUpdateFieldAuditNewlog.setFieldAuditStatu(CustomerAuditStatusEnum.AUDIT_ADOPT.getCode());
					customerUpdateFieldAuditNewlog.setUid(customerPicMsg.getUid());
					num = num + customerUpdateFieldAuditNewlogDao.updateCustomerUpdateFieldAuditNewlogByFid(customerUpdateFieldAuditNewlog);
				}
			}
			
			//更新昵称 
			if(!Check.NuNObjs(unAuditNickNameFieldFid,customerBaseMsg)
					&&!Check.NuNStr(customerBaseMsg.getNickName())
					&&!Check.NuNStr(customerBaseMsg.getUid())){
				//更新 附上新值
				int changeNickName = customerBaseMsgDao.updateCustomerBaseMsg(customerBaseMsg);
				if(changeNickName > 0){
					CustomerUpdateFieldAuditNewlogEntity customerUpdateFieldAuditNewlog = new CustomerUpdateFieldAuditNewlogEntity();
					customerUpdateFieldAuditNewlog.setFid(unAuditNickNameFieldFid.toString());
					customerUpdateFieldAuditNewlog.setFieldAuditStatu(CustomerAuditStatusEnum.AUDIT_ADOPT.getCode());
					customerUpdateFieldAuditNewlog.setUid(customerBaseMsg.getUid());
					customerUpdateFieldAuditNewlogDao.updateCustomerUpdateFieldAuditNewlogByFid(customerUpdateFieldAuditNewlog);
				}
			}
		}
	}

	/**
	 * 根据t_customer_update_field_audit_newlog表的fid获取对象
	 *
	 * @author loushuai
	 * @created 2017年8月11日 下午7:44:41
	 *
	 * @param fieldHeadPicKey
	 * @return
	 */
	public CustomerUpdateFieldAuditNewlogEntity getUpdateFieldAuditNewlogByFid(String updateFieldAuditNewlogFid) {
		return customerUpdateFieldAuditNewlogDao.findCustomerUpdateFieldAuditNewlogByFid(updateFieldAuditNewlogFid);
	}

	/**
	 * 修改用户个人介绍（不需要审核，如：troy上修改）
	 *
	 * @author loushuai
	 * @created 2017年11月16日 下午4:05:16
	 *
	 * @param customerExt
	 * @return
	 */
	public int updateCustomerExtNotAudit(CustomerBaseMsgExtEntity customerExt) {
		return customerBaseMsgExtDao.updateByUidSelective(customerExt);
	}
	
}
