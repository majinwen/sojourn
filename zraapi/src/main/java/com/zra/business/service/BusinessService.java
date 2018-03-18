package com.zra.business.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.business.dao.BusinessMapper;
import com.zra.business.dao.BusinessResultMapper;
import com.zra.business.dao.CustomerMapper;
import com.zra.business.entity.BusinessEntity;
import com.zra.business.entity.BusinessListBusInfoEntity;
import com.zra.business.entity.BusinessResultEntity;
import com.zra.business.entity.CustomerEntity;
import com.zra.business.entity.dto.BusinessEvaluateSMSDto;
import com.zra.business.entity.vo.BusinessResultVo;
import com.zra.common.dto.business.BOQueryParamDto;
import com.zra.common.dto.business.BusinessDto;
import com.zra.common.dto.business.BusinessFullDto;
import com.zra.common.dto.business.BusinessResultDto;
import com.zra.common.dto.business.BusinessShowDto;
import com.zra.common.dto.business.CustomerDto;
import com.zra.common.enums.ErrorEnum;
import com.zra.common.error.ResultException;
import com.zra.common.utils.SysConstant;
import com.zra.system.entity.EmployeeEntity;

/**
 * @author wangws21 2016年8月1日
 * 商机服务
 */
@Service
public class BusinessService {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(BusinessService.class);

    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private BusinessResultMapper businessResultMapper;

    
    /**
     * 保存商机 wangws21 2016-8-1
     * @param business 商机实体
     * @return true/false
     */
    public boolean save(BusinessEntity business){
    	int effectNum = this.businessMapper.insert(business);
    	return effectNum>0;
    }
    
    /**
     * 查询商机
     * @author tianxf9
     * @param paramsDto
     * @return
     */
    public List<BusinessShowDto> query(BOQueryParamDto paramsDto) {
    	return this.businessMapper.query(paramsDto);
    }
    
    /**
     * 查询未完结商机数量.
     * 根据用户手机号、预约项目ID、房型ID查询未完结商机数据
     * @param projectId 项目ID
     * @param houseTypeId 房型ID
     * @param customerNumber 客户手机号
     * @return 商机数量
     */
    public int queryByYkInfo(String projectId, String houseTypeId, String phone) {
        return this.businessMapper.queryByYkInfo(projectId, houseTypeId, phone);
    }

    /**
     * 获取指定项目，指定周几的ZO列表
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-03
     */
    public List<EmployeeEntity> getZOByProIdAndWeek(String projectId, int week){
        return businessMapper.getZOByProIdAndWeek(projectId, week);
    }

    /**
     * 根据管家列表，查出其中当日（也可能是次日）分配商机数最少的管家
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-04
     */
    public EmployeeEntity getLeastBusinessZO(String ZOids, Byte handState) {
        return businessMapper.getLeastBusinessZO(ZOids, handState);
    }

    /**
     * wangws21 2017-1-16 设置商机客源量标记.
     * @param business 待保存的商机实体
     * @param customer 待保存的客户实体
     */
    private void setCustomerKylFlag(BusinessEntity business,CustomerEntity customer){
        if(StringUtils.isNotBlank(customer.getPhone())){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, SysConstant.KYL_ADVANCE_DAYS);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startDate = dateFormat.format(calendar.getTime());
            int custNum = this.customerMapper.getCustNum4KYL(business.getProjectId(), customer.getPhone(), startDate);
            if(custNum > 0){
                customer.setKylFlag(0);
            } else {
                customer.setKylFlag(1);
            }
        }
    }

    /**
     * 保存商机和客户信息
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-04
     */
    public boolean insertBusinessApply(CustomerEntity ce, BusinessEntity be) {
        //wangws21 2017-1-18 客户表插入客源量标示
        this.setCustomerKylFlag(be, ce);
		int i = customerMapper.insert(ce);
		if (i != 1) {
			LOGGER.info("[预约看房]保存客户出错");
			throw new ResultException(ErrorEnum.MSG_BUSINESS_SAVECUSTOMER_FAIL);
		}
		i = businessMapper.insert(be);
		if (i != 1) {
			LOGGER.info("[预约看房]保存商机出错");
			throw new ResultException(ErrorEnum.MSG_BUSINESS_CALENDTIME_FAIL);
		}
		return true;
	}



    /**
     * 保存商机实体  包括 商机、商机客户、商机处理结果   wangws21 2016-8-3
     * @param business 商机实体
     * @return true/false
     */
    public boolean insertFullBusinessEntitys(BusinessEntity business,CustomerEntity customer, BusinessResultEntity businessResult){
    	/*先保存商机用户*/
        //wangws21 2017-1-18 客户表插入客源量标示
        this.setCustomerKylFlag(business, customer);
    	int effectNum = customerMapper.insert(customer);
    	/*在保存商机*/
    	if(effectNum>0){
    		effectNum = businessMapper.insert(business);
    	}
    	/*最后保存默认的处理结果*/
    	if(effectNum>0){
    		businessResultMapper.insert(businessResult);
    	}
    	return effectNum>0;
    }
    
    
    /**
     * 获取最近一次商机
     * @author tianxf9
     * @param projectId
     * @param customerPhone
     * @return
     */
    public List<BusinessEntity> getNearestBusiness(String projectId,String customerPhone) {
    	return this.businessMapper.getNearesetBusiness(projectId,customerPhone);
    }
    
    
    /**
     * 更新商机
     * @author tianxf9
     * @param entity
     * @return
     */
    public int updateBusinessEntity(BusinessEntity entity) {
    	return this.businessMapper.updateBusiness(entity);
    }
    
    /**
     * 根据项目id，周几，处理状态判断分配给哪个管家这个商机
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-04
     */
    public List<EmployeeEntity> getLeastZOOfBusiness(String projectId, int week, Byte handState) {
        return businessMapper.getLeastZOOfBusiness(projectId, week, handState);
    }

    /**
	 * 获取商机完整信息   用于商机处理
	 * @author wangws21 2016-8-4
	 * @param businessBid 商机bid
	 * @return BusinessSaveDto
	 */
	public BusinessFullDto getBusinessDetail(String businessBid) {
		
		BusinessEntity business = this.businessMapper.getBusinessByBid(businessBid);
		//查不到就返回空
		if(business==null) {
			return null;
		}
		CustomerEntity customer = this.customerMapper.getCustomerByBid(business.getCustomerId());
		
		//返回的商机实体
		BusinessDto businessDto = new BusinessDto();
		CustomerDto customerDto = new CustomerDto();
		
		try {
			BeanUtils.copyProperties(businessDto, business);
			BeanUtils.copyProperties(customerDto, customer);
		} catch (Exception e) {
			LOGGER.error("[获取商机详情]，实体拷贝失败，异常："+e);
			throw new ResultException(ErrorEnum.MSG_BUSINESS_GET_FAIL);
		}
		BusinessFullDto businessFull = new BusinessFullDto();
		businessFull.setBusiness(businessDto);
		businessFull.setCustomer(customerDto);
		businessFull.setBusinessResult(new BusinessResultDto());
		return businessFull;
	}
	
	/**
	 * 处理商机 更新商机、客户信息  保存商机处理结果
	 * wangws21 2016-8-6 
     * @param business 待更新的商机实体
     * @param customer 待更新的客户实体
     * @param businessResult 待保存的商机处理结果实体
     * @return true/false
     */
    public boolean updateFullBusinessEntitys(BusinessEntity business,CustomerEntity customer, BusinessResultEntity businessResult){
    	/*先更新商机用户*/
    	int effectNum = customerMapper.update(customer);
    	/*在更行商机*/
    	if(effectNum>0){
    		effectNum = businessMapper.update(business);
    	}
    	/*最后添加处理结果*/
    	if(effectNum>0){
    		businessResultMapper.insert(businessResult);
    	}
    	return effectNum>0;
    }

    /**
	 * 获取商机信息 
	 * @author wangws21 2016-8-6
	 * @param businessBid 商机bid
	 * @return BusinessEntity
	 */
	public BusinessEntity getBusinessByBid(String businessBid) {
		return this.businessMapper.getBusinessByBid(businessBid);
	}

	/**
	 * 获取商机信息
	 *
	 * @Author: wangxm113
	 * @CreateDate: 2016-08-26
	 */
	public BusinessEntity getBusinessById(String businessId) {
		return this.businessMapper.getBusinessById(businessId);
	}

	/**
	 * wangws21 2016-8-8
	 * 获取商机处理历史结果
	 * @param businessBid 商机bid
	 * @return List<BusinessResultDto>
	 */
	public List<BusinessResultDto> getBusinessResultList(String businessBid) {
		return this.businessResultMapper.getBusinessResultList(businessBid);
	}

	/**
	 * 取消商机
	 * @param businessEntity
	 * @param businessResultEntity
	 * @return
	 */
	public Boolean cancelBusiness(BusinessEntity businessEntity, BusinessResultEntity businessResultEntity){

		int effectNum = businessMapper.update(businessEntity);

		if(effectNum>0){
			businessResultMapper.insert(businessResultEntity);
		}
		return effectNum>0;
	}

	/**
	 * 根据uid获取商机列表
	 * @param uid
	 * @param state
	 * @return
	 */
	public List<BusinessListBusInfoEntity> findUserBusinessList(String uid, Integer state){
		return businessMapper.findUserBusinessList(uid, state);
	}

	
	/**
	 * wangws21 2016-8-11
	 * 获取最新的商机处理结果
	 * @param businessBid
	 * @return 商机处理结果
	 */
	public BusinessResultEntity getLastBusinessResult(String businessBid) {
		return this.businessResultMapper.getLastBusinessResult(businessBid);
	}
	
	/**
	 * 获取所有待处理商机
	 * @author tianxf9
	 * @return
	 */
	public List<BusinessEntity> getAllTODOBusiness() {
		return this.businessMapper.getTODOBusiness();
	}

	/**
	 * 根据商机bid更新商机信息
	 *
	 * @author liujun
	 * @created 2016年8月11日
	 *
	 * @param entity
	 * @return
	 */
	public int updateBusinessEntityByBid(BusinessEntity entity) {
		return this.businessMapper.update(entity);
	}

	/**
	 * 关闭商机
	 *
	 * @author liujun
	 * @created 2016年8月12日
	 *
	 * @param business
	 * @param businessResult
	 * @return
	 */
	public int closeBusinessEntity(BusinessEntity business,BusinessResultEntity businessResult){
		int effectNum = businessMapper.update(business);
		if (effectNum > 0) {
			businessResultMapper.insert(businessResult);
		} else {
			throw new ResultException(ErrorEnum.MSG_BUSINESS_CLOSE_FAIL);
		}
		return effectNum;
	}

	/**
	 * 根据商机业务id集合查询商机信息列表
	 *
	 * @author liujun
	 * @created 2016年8月12日
	 *
	 * @param listBusinessBid
	 * @return
	 */
	public List<BusinessEntity> getBusinessListByBidList(
			List<String> listBusinessBid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("listBusinessBid", listBusinessBid);
		return this.businessMapper.getBusinessListByBidList(paramMap);
	}

	/**
	 * 查询发送约看提醒信息列表
	 *
	 * @author liujun
	 * @created 2016年8月15日
	 *
	 * @param deadline 约看截止时间
	 * @return
	 */
	public List<BusinessResultVo> getYkRemindSmsList(Date deadline) {
		return businessResultMapper.getYkRemindSmsList(deadline);
	}

	/**
	 * 根据业务id更新商机处理结果信息
	 *
	 * @author liujun
	 * @created 2016年8月15日
	 *
	 * @param businessResultEntity
	 */
	public int updateBusinessResultEntity(
			BusinessResultEntity businessResultEntity) {
		return businessResultMapper.updateBusinessResultEntity(businessResultEntity);
	}

	/**
	 * 查询发送约看提醒信息列表
	 * @author wangws21
	 * @created 2016年8月23日
	 * @param endDate 截止时间
	 * @return List<BusinessEvaluateSMSDto>
	 */
	public List<BusinessEvaluateSMSDto> getBusinessEvaluateSmsList(Date endDate) {
		return this.businessResultMapper.getBusinessEvaluateSmsList(endDate);
	}

	/**
	 * 查询今日待办且已经处理了的管家分配商机数
	 *
	 * @Author: wangxm113
	 * @CreateDate: 2016-08-25
	 */
	public List<EmployeeEntity> getTodayDealCount(String projectId, int week) {
		return businessMapper.getTodayDealCount(projectId, week);
	}
}
