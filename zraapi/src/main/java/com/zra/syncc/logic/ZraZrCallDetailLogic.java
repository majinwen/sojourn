package com.zra.syncc.logic;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zra.business.entity.BusinessEntity;
import com.zra.business.entity.CustomerEntity;
import com.zra.business.logic.BusinessLogic;
import com.zra.business.logic.CustomerLogic;
import com.zra.client.ClientResource;
import com.zra.common.dto.log.LogRecordDto;
import com.zra.common.enums.BussSystemEnums;
import com.zra.common.utils.DateUtil;
import com.zra.common.utils.KeyGenUtils;
import com.zra.house.entity.dto.ProjectTelDto;
import com.zra.house.logic.ProjectLogic;
import com.zra.log.logic.LogLogic;
import com.zra.marketing.logic.MkNumberLogic;
import com.zra.syncc.entity.ZraZrCallDetailEntity;
import com.zra.syncc.service.ZraZrCallDetailService;
import com.zra.system.entity.EmployeeEntity;
import com.zra.system.logic.EmployeeLogic;

/**
 * 
 * @author tianxf9
 *
 */
@Component
public class ZraZrCallDetailLogic {
	
	@Autowired
	private ZraZrCallDetailService service;
	
	@Autowired
	private ProjectLogic projectLogic;
	
	@Autowired
	private BusinessLogic businessLogic;
	
	@Autowired
	private CustomerLogic customerLogic;
	
	@Autowired
	private EmployeeLogic employeeLogic;
	
	@Autowired
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ClientResource.class);
	
    @Autowired
    private LogLogic logLogic;
    
    @Autowired
    private MkNumberLogic mkNumberLogic;
	/**
	 * 从400库获取自如寓400来电详情
	 * @author tianxf9
	 * @return
	 */
	public int synBusinessFromCC(String startDate,String endDate) {
		LOGGER.info("========从400获取通话详情参数========startDate="+startDate+";endDate="+endDate);
		//获取分机号和项目关系
        Map<String,ProjectTelDto> extNumProjectIdMap = this.getExtNumAndProjectIdMap();
        if(extNumProjectIdMap.size()==0) {
        	LOGGER.info("========没有从400获取到通话详情原因项目分机号为空或者不合理=================");
        	return 0;
        }
        
        //获得所有项目的分机号 ：'分机号'，'分机号'，'分机号'
        String extNum = null;
		for(String key :extNumProjectIdMap.keySet()) {
			if(extNum==null) {
				extNum = "'"+key+"'";
			}else {
				extNum = extNum+",'"+key+"'";
			}
		}
		
		//从400系统获取通话详情
		List<ZraZrCallDetailEntity> callDetailList = this.service.getCallDetailFromNewCC(startDate,endDate,extNum);
		
		String ccResultStr = JSON.toJSONString(callDetailList);
		LOGGER.info("========从400获得的结果================="+ccResultStr);
		int detailRows = this.saveCallDetails(callDetailList);
		if(detailRows==0) {
			return 0;
		}
		//转换成商机
		List<BusinessEntity> businessList = this.callDetailToBusiness(callDetailList, extNumProjectIdMap);
		return this.saveBusienessList(businessList);
	}
	
	/**
	 * 获取所有项目分机号和项目id的对应关系
	 * @author tianxf9
	 * @return
	 */
	public Map<String,ProjectTelDto> getExtNumAndProjectIdMap() {
		
		//获取所有项目的分机号
		List<ProjectTelDto> telMsgList = this.projectLogic.getAllProjectTelMsg();
		Map<String,ProjectTelDto> telProjectIdMap = new HashMap<String,ProjectTelDto>();
		for(ProjectTelDto telDto:telMsgList) {
			String telStr = telDto.getMarketTel();
			if(telStr.contains(",")) {
				String[] telStrs = telStr.split(",");
				if(telStrs!=null&&telStrs.length==2) {
				telProjectIdMap.put(telStrs[1], telDto);
				}else {
					continue;
				}
			}else {
				continue;
			}
		}
		
		//add by tianxf9 获取渠道分机号
		List<ProjectTelDto> mkNumDtos= this.mkNumberLogic.getAllNumber();
		for(ProjectTelDto telDto:mkNumDtos) {
			telProjectIdMap.put(telDto.getMarketTel(), telDto);
		}
		return telProjectIdMap;
	}
	
	
	/**
	 * 保存通话详情（去掉已经保存了的数据）
	 * @author tianxf9
	 * @param entitys
	 * @return
	 */
	public int saveCallDetails(List<ZraZrCallDetailEntity> entitys) {
		
		int rows = 0;
		for(ZraZrCallDetailEntity callDetail:entitys) {
			String callId = callDetail.getCallId();
			List<ZraZrCallDetailEntity> existCallDetail = this.service.getCallDetailById(callId);
			if(existCallDetail!=null&&existCallDetail.size()>0) {
				//已经存在
				continue;
			}else {
				callDetail.setCreateTime(new Date());
				callDetail.setIsDel(0);
				callDetail.setBid(KeyGenUtils.genKey());
				rows = rows+this.service.saveCallDetailEntitys(callDetail);
			}
		}
		
		return rows;
	}
	
	/**
	 * 将通话详情转换成商机
	 * @author tianxf9
	 * @param entitys
	 * @param extNumProjectIdMap
	 * @return
	 */
	public List<BusinessEntity> callDetailToBusiness(List<ZraZrCallDetailEntity> entitys,Map<String,ProjectTelDto> extNumProjectIdMap) {
		
		//通话详情中管家电话号码:'号码'，'号码'
		String ZOPhoneNum = null;
	    for(ZraZrCallDetailEntity entity:entitys) {
	    	if(ZOPhoneNum==null) {
	    		ZOPhoneNum = "'"+entity.getDialNumber()+"'";
	    	}else {
	    		ZOPhoneNum = ZOPhoneNum+",'"+entity.getDialNumber()+"'";
	    	}
		}
	    
	    //调用根据电话号码查询出管家和管家号码的关系
	    Map<String,EmployeeEntity> zoPhoneMap = new HashMap<String,EmployeeEntity>();
	    List<EmployeeEntity> employees = this.employeeLogic.getEmployeeByMobile(ZOPhoneNum);
	    for(EmployeeEntity entity:employees) {
	    	zoPhoneMap.put(entity.getMobile(), entity);
	    }
	    
	    //得到系统管理员
	    EmployeeEntity sysAdimn = this.employeeLogic.getEmployeeByUserAccount("system");
	    //转换为商机
		List<BusinessEntity> businessEntityList = new ArrayList<BusinessEntity>();
		
		for(ZraZrCallDetailEntity entity:entitys) {
			//and by tianxf9 出现重复商机,去除已经保存了的数据
			if(entity.getBid()!=null) {
			
			BusinessEntity businessEntity = new BusinessEntity();
			if(extNumProjectIdMap.get(entity.getExtensionNumber()) ==null) {
				continue;
			}else {
			   businessEntity.setProjectId(extNumProjectIdMap.get(entity.getExtensionNumber()).getProjectId());
			   businessEntity.setCustomerNumber(entity.getRemoteNumberFmt());
			   if(zoPhoneMap.get(entity.getDialNumber())!=null) {
				   businessEntity.setZoId(zoPhoneMap.get(entity.getDialNumber()).getId());
				   businessEntity.setZoName(zoPhoneMap.get(entity.getDialNumber()).getName());
			   }else {
				   businessEntity.setZoId(null);
				   businessEntity.setZoName(null);
			   }

			   businessEntity.setCallBid(entity.getBid());
			   businessEntity.setCityId(extNumProjectIdMap.get(entity.getExtensionNumber()).getCityId());
			   businessEntity.setCreaterId(sysAdimn.getId());
			   businessEntity.setCreateTime(new Date());
			   Date endTime = null;
				try {
					endTime = businessLogic.getDYKEndTime();
				} catch (ParseException e) {
					LOGGER.error("========callId为:"+entity.getBid()+"计算约看时间出错()=============", e);
					continue;
				}
			   businessEntity.setEndTime(endTime);
			   Byte handState;
				try {
					handState = DateUtil.getDealStatusByEndTime(endTime);
				} catch (ParseException e) {
					LOGGER.error("========callId为:"+entity.getBid()+"计算处理状态出错=============", e);
					continue;
				}
			   businessEntity.setHandState(handState);
			   businessEntity.setIsAnswer(entity.getDialResult());
			   businessEntity.setIsDel((byte) 0);
			   //1：400来电  2：在线预约 3：云销 4：其他'
			   businessEntity.setSource((byte) 1);
			   //businessEntity.setSourceZra((byte) 1);
			   businessEntity.setSourceZra(extNumProjectIdMap.get(entity.getExtensionNumber()).getChannelBid());
			   //1：待约看 2：待带看 3：待回访 4：成交 5：未成交',
			   businessEntity.setStep((byte) 1);
			   businessEntity.setCustomerNumber(entity.getRemoteNumberFmt());
			   
			   businessEntityList.add(businessEntity);
			}
			}
		}
		
		return businessEntityList;
		
	}
	
	
	/**
	 * 保存商机
	 * @author tianxf9
	 * @param entitys
	 * @return
	 */
	public int saveBusienessList(List<BusinessEntity> entitys) {
		
		List<BusinessEntity> saveBusinessList = new ArrayList<BusinessEntity>();
		for(BusinessEntity entity:entitys) {
			//查询最近一次该商机的客户相同，项目相同
			List<BusinessEntity> existEntity = this.businessLogic.getNearestBusinessEntity(entity.getProjectId(),entity.getCustomerNumber());
			if(existEntity==null||existEntity.size()==0||(existEntity.get(0)!=null&&(isInHalfHour(existEntity.get(0).getCreateTime(),entity.getCreateTime())))) {
				saveBusinessList.add(entity);
			}else {
				if(existEntity.get(0)!=null) {
				//30分钟内有项目相同并且客户相同的商机
				if(!(("success").equals(entity.getIsAnswer()))) {
					//未接听 过滤
					continue;
				}else {
					if(entity.getZoId()!=null&&!entity.getZoId().equals(existEntity.get(0).getZoId())) {
						//分配不同管家
						if(existEntity.get(0).getStep().byteValue()==1) {
							//已经存在的商机是待约看状态替换原来的商机
							entity.setId(existEntity.get(0).getId());
							this.businessLogic.updateBusinessEntity(entity);
						}else {
							continue;
						}
					}else {
						//分配相同管家
						continue;
					}
				}
			}
		  }
		}
		
		for(BusinessEntity entity:saveBusinessList) {
			//首先要维护一遍客户信息
			CustomerEntity customerEntity = new CustomerEntity();
			customerEntity.setPhone(entity.getCustomerNumber());
			customerEntity.setCusBid(KeyGenUtils.genKey());
			this.customerLogic.saveCustomerEntity(customerEntity);
			//然后查询该客户信息将客户信息的Bid的到放到商机表里
			customerEntity = this.customerLogic.getCustByPhone(entity.getCustomerNumber()).get(0);
			entity.setCustomerId(customerEntity.getCusBid());
			entity.setBusinessBid(KeyGenUtils.genKey());
			this.businessLogic.saveBusiness(entity);
			//给管家发送约看短信
			this.businessLogic.sendYytzMsg(entity.getBusinessBid());
	    	/*保存操作记录*/
			LogRecordDto logRecord = new LogRecordDto(BussSystemEnums.ZRA.getKey(), "business", entity.getBusinessBid(), "系统", "新建商机");
			LOGGER.info("logRecord.toString():" + logRecord.getSystemId() + "," + logRecord.getOperModId() + "," + logRecord.getOperObjId() + "," + logRecord.getOperator() + "," + logRecord.getLoginfo());
			boolean flag = logLogic.saveLog(logRecord);
			LOGGER.info("save info flag:" + flag);
		}
        return saveBusinessList.size();
	}
	
	/**
	 * oldDate与newDate之间的时间差是否大于半小时
	 * @author tianxf9
	 * @param oldDate
	 * @param newDate
	 * @return
	 */
	public boolean isInHalfHour(Date oldDate,Date newDate) {
		
		long poor = (newDate.getTime()-oldDate.getTime())/(1000*60);
		if(poor>30) {
			return true;
		}else {
			return false;
		}
		 
	}
	
	/**
	 * 同步通话详情的拨号结果
	 * @author tianxf9
	 * @param dateStr
	 */
	public void sycDialResult(String startDate,String endDate) {
		
		//获取分机号和项目关系
        Map<String,ProjectTelDto> extNumProjectIdMap = this.getExtNumAndProjectIdMap();
        if(extNumProjectIdMap.size()==0) {
        	LOGGER.info("========没有从400获取到通话详情原因项目分机号为空或者不合理=================");
        	return ;
        }
        
        //获得所有项目的分机号 ：'分机号'，'分机号'，'分机号'
        String extNum = null;
		for(String key :extNumProjectIdMap.keySet()) {
			if(extNum==null) {
				extNum = "'"+key+"'";
			}else {
				extNum = extNum+",'"+key+"'";
			}
		}
		
		LOGGER.info("========从400获取通话详情参数========startDate="+startDate+";endDate="+endDate+";extNum="+extNum);
		//从400系统获取通话详情
		List<ZraZrCallDetailEntity> callDetailList = this.service.getCallDetailFromNewCC(startDate,endDate,extNum);
		this.updateDialResult(callDetailList);
	}
	
	/**
	 * 更新通话详情的拨号结果
	 * @author tianxf9
	 * @param callDetailList
	 */
	public void updateDialResult(List<ZraZrCallDetailEntity> callDetailList) {
		
		if(CollectionUtils.isNotEmpty(callDetailList)) {
			for(ZraZrCallDetailEntity callDetailEntity:callDetailList) {
				this.service.updateCallDetailEntity(callDetailEntity);
			}
		}
		
	}

}
