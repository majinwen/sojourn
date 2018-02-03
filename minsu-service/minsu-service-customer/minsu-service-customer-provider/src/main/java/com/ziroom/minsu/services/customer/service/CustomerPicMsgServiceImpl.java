/**
 * @FileName: CustomerPicMsgServiceImpl.java
 * @Package com.ziroom.minsu.services.customer.service
 * 
 * @author bushujie
 * @created 2016年4月23日 下午2:05:45
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerUpdateFieldAuditManagerEntity;
import com.ziroom.minsu.entity.customer.CustomerUpdateFieldAuditNewlogEntity;
import com.ziroom.minsu.entity.customer.CustomerUpdateHistoryExtLogEntity;
import com.ziroom.minsu.entity.customer.CustomerUpdateHistoryLogEntity;
import com.ziroom.minsu.services.common.utils.ClassReflectUtils;
import com.ziroom.minsu.services.customer.dao.CustomerBaseMsgDao;
import com.ziroom.minsu.services.customer.dao.CustomerPicMsgDao;
import com.ziroom.minsu.services.customer.dao.CustomerUpdateFieldAuditManagerDao;
import com.ziroom.minsu.services.customer.dao.CustomerUpdateFieldAuditNewlogDao;
import com.ziroom.minsu.services.customer.dao.CustomerUpdateHistoryExtLogDao;
import com.ziroom.minsu.services.customer.dao.CustomerUpdateHistoryLogDao;
import com.ziroom.minsu.services.customer.util.CustomerUtils;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.AuditStatusEnum;
import com.ziroom.minsu.valenum.customer.CustomerAuditStatusEnum;
import com.ziroom.minsu.valenum.customer.CustomerPicTypeEnum;
import com.ziroom.minsu.valenum.customer.CustomerUpdateLogEnum;
import com.ziroom.minsu.valenum.house.CreaterTypeEnum;
import com.ziroom.minsu.valenum.house.HouseSourceEnum;
import com.ziroom.minsu.valenum.house.IsTextEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>客户头像业务实现</p>
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
@Service("customer.customerPicMsgServiceImpl")
public class CustomerPicMsgServiceImpl {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerPicMsgServiceImpl.class);
	
	@Resource(name="customer.customerPicMsgDao")
	private CustomerPicMsgDao customerPicMsgDao;
	
	@Resource(name="customer.customerBaseMsgDao")
	private CustomerBaseMsgDao customerBaseMsgDao;
	
	@Resource(name="customer.customerUpdateHistoryLogDao")
	private CustomerUpdateHistoryLogDao customerUpdateHistoryLogDao;

	@Resource(name="customer.customerUpdateHistoryExtLogDao")
    private CustomerUpdateHistoryExtLogDao customerUpdateHistoryExtLogDao;
    
    @Resource(name="customer.customerUpdateFieldAuditManagerDao")
    private CustomerUpdateFieldAuditManagerDao customerUpdateFieldAuditManagerDao;
    
    @Resource(name="customer.customerUpdateFieldAuditNewlogDao")
    private CustomerUpdateFieldAuditNewlogDao customerUpdateFieldAuditNewlogDao;
	/**
	 * 
	 * 插入客户图片信息
	 *
	 * @author bushujie
	 * @created 2016年4月23日 下午2:11:48
	 *
	 * @param customerPicMsgEntity
	 */
	public void insertCustomerPicMsg(CustomerPicMsgEntity customerPicMsgEntity){
		customerPicMsgDao.insertCustomerPicMsg(customerPicMsgEntity);
	}


	/**
	 * 上传头像 上传成功删除同类型的其他照片
	 * @author afi
	 * @created 2017年2月23日 下午15:46:39
	 *
	 * @param customerPicMsgEntity
	 * @return
	 */
	public void insertCustomerPicMsgAndDelOthers(CustomerPicMsgEntity customerPicMsgEntity) {
		//删除其他头像 没有的话也没问题
		customerPicMsgDao.delCustomerPicMsgByType(customerPicMsgEntity.getUid(),customerPicMsgEntity.getPicType());
		//插入新的头像信息
		customerPicMsgDao.insertCustomerPicMsg(customerPicMsgEntity);
		if (customerPicMsgEntity.getPicType() == CustomerPicTypeEnum.YHTX.getCode()){
			//如果是头像更改为已经上传
			CustomerBaseMsgEntity customerBase = new CustomerBaseMsgEntity();
			customerBase.setUid(customerPicMsgEntity.getUid());
			customerBase.setIsUploadIcon(YesOrNoEnum.YES.getCode());
			customerBaseMsgDao.updateCustomerBaseMsg(customerBase);
		}

	}
	
	/**
	 * 批量插入客户图片信息
	 * @author lishaochuan
	 * @create 2016年5月7日下午5:58:41
	 * @param customerPicMsgEntity
	 */
	public void insertCustomerPicMsgList(List<CustomerPicMsgEntity> customerPicMsgEntityList){
		for (CustomerPicMsgEntity customerPicMsgEntity : customerPicMsgEntityList) {
			customerPicMsgDao.insertCustomerPicMsg(customerPicMsgEntity);
		}
	}
	
	/**
	 * 
	 * 类型查询客户图片
	 *
	 * @author bushujie
	 * @created 2016年4月23日 下午7:47:30
	 *
	 * @param uid
	 * @param picType
	 * @return
	 */
	public CustomerPicMsgEntity getCustomerPicByType(String uid,Integer picType){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("uid",uid);
		paramMap.put("picType", picType);
		CustomerPicMsgEntity pic=customerPicMsgDao.getCustomerPicByType(paramMap);
		return pic;
	}
	/**
	 * 
	 * 获取list集合
	 *
	 * @author jixd
	 * @created 2016年6月27日 下午12:58:24
	 *
	 * @param uid
	 * @param picType
	 * @return
	 */
	public List<CustomerPicMsgEntity> getCustomerPicListByType(String uid,Integer picType){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("uid",uid);
		paramMap.put("picType", picType);
		return customerPicMsgDao.getCustomerPicListByType(paramMap);
    }

	/**
	 * 
	 * 类型查询客户图片
	 *
	 * @author bushujie
	 * @created 2016年4月23日 下午7:47:30
	 *
	 * @param uid
	 * @param picType
	 * @return
	 */
	public List<CustomerPicMsgEntity> getCustomerPicByUid(String uid){
		 return customerPicMsgDao.queryCustomerAllPicByUid(uid);
	}
	
	/**
	 * 
	 * 更新客户图片信息
	 *
	 * @author bushujie
	 * @created 2016年4月23日 下午7:49:03
	 *
	 * @param customerPicMsgEntity
	 * @return
	 */
	public int updateCustomerPicMsg(CustomerPicMsgEntity customerPicMsgEntity){
		int count = 0;
		CustomerBaseMsgEntity customerBaseMsg = customerBaseMsgDao.queryCustomerBaseMsgByUid(customerPicMsgEntity.getUid());
		if(CustomerPicTypeEnum.YHTX.getCode()==customerPicMsgEntity.getPicType() && !Check.NuNObj(customerPicMsgEntity)
				&& !Check.NuNObj(customerBaseMsg) && !Check.NuNObj(customerBaseMsg.getAuditStatus())
				&& customerBaseMsg.getAuditStatus()==CustomerAuditStatusEnum.AUDIT_ADOPT.getCode()){
			//用户头像已经存在，此时是在更新用户头像
			Map<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.put("uid",customerPicMsgEntity.getUid());
			paramMap.put("picType", customerPicMsgEntity.getPicType());
			CustomerPicMsgEntity oldCustomerPic= customerPicMsgDao.getCustomerPicByType(paramMap);
			saveCustomereMsgUpdateLog(customerPicMsgEntity, oldCustomerPic);
			//如果是用户头像，将之前的用户头像状态改为0，新插入一条用户头像记录
			customerPicMsgEntity.setAuditStatus(YesOrNoEnum.NO.getCode());
			customerPicMsgEntity.setFid(UUIDGenerator.hexUUID());
			customerPicMsgEntity.setCreateDate(null);
			customerPicMsgEntity.setId(null);
		    int insertCustomerPicMsg = customerPicMsgDao.insertCustomerPicMsg(customerPicMsgEntity);
		    count = count+insertCustomerPicMsg;
		}else{
			 count =customerPicMsgDao.updateCustomerPicMsg(customerPicMsgEntity);
		}
		return count;
	}
	
	/**
	 * 
	 * pc修改房东头像，添加修改日志
	 * 
	 * 有修改 即保存
	 *
	 * @author loushuai
	 * @created 2017年8月7日 下午2:06:55
	 *
	 * @param newcustomerPicMsg
	 * @param oldCustomerPicMsg
	 */
	private  void saveCustomereMsgUpdateLog(CustomerPicMsgEntity newcustomerPicMsg,CustomerPicMsgEntity oldCustomerPicMsg){
		LogUtil.info(LOGGER, "pc端，saveCustomereMsgUpdateLog，newcustomerPicMsg={},oldCustomerPicMsg={}", 
				JsonEntityTransform.Object2Json(newcustomerPicMsg),JsonEntityTransform.Object2Json(oldCustomerPicMsg));
		if(Check.NuNObj(newcustomerPicMsg)||Check.NuNObj(oldCustomerPicMsg)){
			return ;
		}

		List<CustomerUpdateHistoryLogEntity> list = new ArrayList<CustomerUpdateHistoryLogEntity>();
		
		CustomerUtils.contrastCustomerPicMsgEntityObj(newcustomerPicMsg, oldCustomerPicMsg, list);

		if(!Check.NuNCollection(list)){
			for (CustomerUpdateHistoryLogEntity customerUpdateHistoryLog : list) {
				customerUpdateHistoryLog.setUid(oldCustomerPicMsg.getUid());
		    	customerUpdateHistoryLog.setCreaterFid(oldCustomerPicMsg.getUid());
				customerUpdateHistoryLog.setCreaterType(CreaterTypeEnum.LANLORD.getCode());
				customerUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
				customerUpdateHistoryLog.setFieldDesc(CustomerUpdateLogEnum.Customer_Pic_Msg_Pic_Base_Url.getFieldDesc());
				customerUpdateHistoryLog.setFieldPath(ClassReflectUtils.getFieldNamePath(CustomerPicMsgEntity.class,CustomerUpdateLogEnum.Customer_Pic_Msg_Pic_Base_Url.getFieldName()));
				String fieldPathKey = MD5Util.MD5Encode(customerUpdateHistoryLog.getFieldPath()+oldCustomerPicMsg.getUid(), "UTF-8");
				customerUpdateHistoryLog.setFieldPathKey(fieldPathKey);
				customerUpdateHistoryLog.setNewValue(newcustomerPicMsg.getPicBaseUrl());
				customerUpdateHistoryLog.setOldValue(oldCustomerPicMsg.getPicBaseUrl());
				customerUpdateHistoryLog.setSourceType(HouseSourceEnum.PC.getCode());
				saveCustomerUpdateHistoryLog(customerUpdateHistoryLog,oldCustomerPicMsg);
			}
		}


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
	public int saveCustomerUpdateHistoryLog(CustomerUpdateHistoryLogEntity customerUpdateHistoryLog,CustomerPicMsgEntity oldCustomerPicMsg){
		
		if(!Check.NuNObj(customerUpdateHistoryLog)){
			
			if(Check.NuNStr(customerUpdateHistoryLog.getFid())) customerUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
			//此字段 必传 在上层做校验
			int isTest = customerUpdateHistoryLog.getIsText();
			int i =  0;
			if(isTest == IsTextEnum.IS_TEST.getCode()){
				
				LogUtil.info(LOGGER, "【保存房东头像修改大字段】isTest={},fieldPathKey={},", isTest,customerUpdateHistoryLog.getFieldPathKey());
				CustomerUpdateHistoryExtLogEntity customerUpdateHistoryExtLog = new CustomerUpdateHistoryExtLogEntity();
				customerUpdateHistoryExtLog.setFid(customerUpdateHistoryLog.getFid());
				customerUpdateHistoryExtLog.setNewValue(customerUpdateHistoryLog.getNewValue());
				customerUpdateHistoryExtLog.setOldValue(customerUpdateHistoryLog.getOldValue());
				customerUpdateHistoryLog.setNewValue("");
				customerUpdateHistoryLog.setOldValue("");
				
				this.customerUpdateHistoryExtLogDao.insertSelective(customerUpdateHistoryExtLog);
			}
			i = this.customerUpdateHistoryLogDao.insertSelective(customerUpdateHistoryLog);
			//t_house_update_field_audit_newlog 审核字段的最新记录 只做第一次插入  这里状态更改在 审核时候 才会状态更改  
			if(i>0){
				//查询 审核字段
				CustomerUpdateFieldAuditManagerEntity 	customerUpdateFieldAuditManagerEntity = this.customerUpdateFieldAuditManagerDao.findCustomerUpdateFieldAuditManagerByFid(MD5Util.MD5Encode(customerUpdateHistoryLog.getFieldPath(), "UTF-8"));
				if(Check.NuNObj(customerUpdateFieldAuditManagerEntity)){
					LogUtil.info(LOGGER, "【保存房源修改审核字段】当前字段非审核字段：fieldPath={}", customerUpdateHistoryLog.getFieldPath());
					return i;
				}
				CustomerBaseMsgEntity customerBaseMsg = customerBaseMsgDao.queryCustomerBaseMsgByUid(oldCustomerPicMsg.getUid());
				if(Check.NuNObj(customerBaseMsg)){
					//房东数据为空
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
					i = this.customerUpdateFieldAuditNewlogDao.insertSelective(customerUpdateFieldAuditNewlog);
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
	 * 
	 * 获取最新修改且尚未审核的照片
	 *
	 * @author loushuai
	 * @created 2017年8月8日 下午5:08:40
	 *
	 * @param headPicMap
	 * @return
	 */
	public CustomerPicMsgEntity getLatestUnAuditHeadPic(Map<String, Object> paramMap) {
		return customerPicMsgDao.getLatestUnAuditHeadPic(paramMap);
	}
	
}
