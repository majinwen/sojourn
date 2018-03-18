package com.zra.syncc.dao;

import java.util.List;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zra.syncc.entity.ZraZrCallDetailEntity;

/**
 * 自如寓400通话详情持久化
 * @author tianxf9
 *
 */
@Repository
public interface ZraZrCallDetailMapper {
	
	/**
     * 从400查询自如寓400来电详情 新平台
     * @author tianxf9
     * @return
     */
	List<ZraZrCallDetailEntity> getCallDetailFromNewCC(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("extNum")String extNum);
	
	
	/**
	 * 保存自如寓400来电详情
	 * @author tianxf9
	 * @param detailEntitys
	 * @return
	 */
	int saveCallDetail(ZraZrCallDetailEntity detailEntitys);
	
	
	/**
	 * 根据callId查询来电详情
	 * @author tianxf9
	 * @param callId
	 * @return
	 */
	List<ZraZrCallDetailEntity> getCallDetailById(String callId);
	
	
	/**
	 * 更新通话详情的Initiateddate（拨号结果字段）
	 * @author tianxf9
	 * @param detailEntity
	 * @return
	 */
	int updateCallDetailEntity(ZraZrCallDetailEntity detailEntity);

}
