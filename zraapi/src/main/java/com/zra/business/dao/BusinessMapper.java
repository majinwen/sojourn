package com.zra.business.dao;

import java.util.List;
import java.util.Map;

import com.zra.business.entity.BusinessListBusInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zra.business.entity.BusinessEntity;
import com.zra.common.dto.business.BOQueryParamDto;
import com.zra.common.dto.business.BusinessShowDto;
import com.zra.system.entity.EmployeeEntity;

/**
 * 商机dao
 * @author wangws21 2016-8-1
 *
 */
@Repository
public interface BusinessMapper {
	
	
    /**
     * 保存商机  wangws21 2016-8-1
     * @param business 商机
     * @return effectNum
     */
    int insert(BusinessEntity business);
    
    /**
     * 查询商机
     * @author tianxf9
     * @param paramsDto
     * @return
     */
    List<BusinessShowDto> query(BOQueryParamDto paramsDto);
    
    /**
     * 查询未完结商机数量.
     * 根据用户手机号、预约项目ID、房型ID查询未完结商机数据
     * @param projectId 项目ID
     * @param houseTypeId 房型ID
     * @param customerNumber 客户手机号
     * @return 商机数量
     */
    int queryByYkInfo(@Param("projectId") String projectId, @Param("houseTypeId") String houseTypeId, @Param("phone") String phone);
    
    /**
     * 获取指定项目，指定周几的ZO列表
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-03
     */
    List<EmployeeEntity> getZOByProIdAndWeek(@Param("projectId") String projectId, @Param("week") int week);

    /**
     * 根据管家列表，查出其中当日（也可能是次日）分配商机数最少的管家
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-04
     */
    EmployeeEntity getLeastBusinessZO(@Param("zOids") String zOids, @Param("handState") Byte handState);

    /**
     * 根据项目id，周几，处理状态判断分配给哪个管家这个商机
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-04
     */
    List<EmployeeEntity> getLeastZOOfBusiness(@Param("projectId") String projectId, @Param("week") int week, @Param("handState") Byte handState);

    /**
   	 * 获取商机  用于商机处理
   	 * @author wangws21 2016-8-4
   	 * @param businessBid 商机bid
   	 * @return BusinessEntity
   	 */
	BusinessEntity getBusinessByBid(@Param("businessBid") String businessBid);

	/**
	 * 获取商机  用于取消约看
	 *
	 * @Author: wangxm113
	 * @CreateDate: 2016-08-26
	 */
	BusinessEntity getBusinessById(@Param("businessId") String businessId);


	
	/**
     * 获取最近一次商机
     * @author tianxf9
     * @param projectId
     * @param customerPhone
     * @return
     */
    List<BusinessEntity> getNearesetBusiness(@Param("projectId")String projectId,@Param("customerPhone")String customerPhone);
    
    /**
     * 更新商机
     * @author tianxf9
     * @param entity
     * @return
     */
    int updateBusiness(BusinessEntity entity);

    /**
     * 更新商机  TODO 与tianxf9方法不同  根据bid更新
     * @author wangws21 2016-8-6 
	 * @param business 商机实体
	 * @return 更新数
	 */
	int update(BusinessEntity business);

    /**
     * 查询客户商机列表
     * @param uid
     * @param state
     * @return
     */
    List<BusinessListBusInfoEntity> findUserBusinessList(@Param("uid") String uid, @Param("state") Integer state);

    /**
     * 获取所有待处理的商机
     * @author tianxf9
     * @return
     */
    List<BusinessEntity> getTODOBusiness();

	/**
	 * 根据商机业务id集合查询商机信息列表
	 *
	 * @author liujun
	 * @created 2016年8月12日
	 *
	 * @param paramMap
	 * @return
	 */
	List<BusinessEntity> getBusinessListByBidList(Map<String, Object> paramMap);

	/**
	 * 查询今日待办且已经处理了的管家分配商机数
	 *
	 * @Author: wangxm113
	 * @CreateDate: 2016-08-25
	 */
	List<EmployeeEntity> getTodayDealCount(@Param("projectId") String projectId, @Param("week") int week);
}