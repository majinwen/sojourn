package com.zra.syncc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zra.syncc.entity.PhonePreserveEntity;

/**
 * HOMELINK_PHONEPRESERVE表
 * @author tianxf9
 *
 */
@Repository
public interface PhonePreserveMapper {
	
	
	/**
	 * 新增cc表数据HOMELINK_PHONEPRESERVE
	 * @author tianxf9
	 * @param phoneEntity
	 * @return
	 */
	int insertPhonePreserve(PhonePreserveEntity phoneEntity);
	
	/**
	 * 删除
	 * @author tianxf9
	 * @param extNums
	 * @return
	 */
	int delPhonePreserve(@Param("extNums")List<String> extNums);
	
	/**
	 * 更新
	 * @author tianxf9
	 * @param phoneEntity
	 * @return
	 */
	int updatePhonePreserve(PhonePreserveEntity phoneEntity);

}
