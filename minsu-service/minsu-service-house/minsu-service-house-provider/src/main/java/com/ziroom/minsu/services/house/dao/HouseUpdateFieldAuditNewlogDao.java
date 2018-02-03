/**
 * @FileName: HouseUpdateFieldAuditNewlogDao.java
 * @Package com.ziroom.minsu.services.house.dao
 * 
 * @author yd
 * @created 2017年6月30日 下午4:26:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditNewlogEntity;
import com.ziroom.minsu.services.house.entity.HouseFieldAuditLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>房源审核字段修改最新记录 数据持久层</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@Repository("house.houseUpdateFieldAuditNewlogDao")
public class HouseUpdateFieldAuditNewlogDao {

	private String SQLID="house.houseUpdateFieldAuditNewlogDao.";

	@Autowired
	@Qualifier("house.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;


	/**
	 * 
	 * 保存 
	 *
	 * @author yd
	 * @created 2017年6月30日 下午4:34:42
	 *
	 * @param houseUpdateFieldAuditNewlog
	 */
	public int saveHouseUpdateFieldAuditNewlog(HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlog){
		return mybatisDaoContext.save(SQLID+"saveHouseUpdateFieldAuditNewlog", houseUpdateFieldAuditNewlog);
	}

	/**
	 * 
	 * 根据 fid查询 
	 *
	 * @author yd
	 * @created 2017年7月3日 下午3:20:36
	 *
	 * @param fid
	 * @return
	 */
	public HouseUpdateFieldAuditNewlogEntity findHouseUpdateFieldAuditNewlogByFid(String fid){

		return mybatisDaoContext.findOne(SQLID+"findHouseUpdateFieldAuditNewlogByFid", HouseUpdateFieldAuditNewlogEntity.class, fid);
	}

	/**
	 * 
	 * 根据 fid 更新
	 *
	 * @author yd
	 * @created 2017年7月3日 下午3:30:46
	 *
	 * @param houseUpdateFieldAuditNewlog
	 */
	public int updateHouseUpdateFieldAuditNewlogByFid(HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlog){
		return mybatisDaoContext.update(SQLID+"updateHouseUpdateFieldAuditNewlogByFid", houseUpdateFieldAuditNewlog);
	}

	/**
	 * @description: 根据条件查询（houseFid 、roomFid、rentWay等）
	 * @author: lusp
	 * @date: 2017/7/30 11:13
	 * @params: houseUpdateFieldAuditNewlog
	 * @return:
	 */
	public List<HouseFieldAuditLogVo> findHouseUpdateFieldAuditNewlogByCondition(HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlog){
		return mybatisDaoContext.findAll(SQLID+"findHouseUpdateFieldAuditNewlogByCondition", houseUpdateFieldAuditNewlog);
	}

	/**
	 * @description: 根据条件进行更新
	 * @author: lusp
	 * @date: 2017/8/2 16:06
	 * @params: houseUpdateFieldAuditNewlog
	 * @return:
	 */
	public int updateHouseUpdateFieldAuditNewlogByCondition(HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlog){
		return mybatisDaoContext.update(SQLID+"updateHouseUpdateFieldAuditNewlogByCondition", houseUpdateFieldAuditNewlog);
	}

	/**
	 * @description: 根据id 更新审核记录表数据
	 * @author: lusp
	 * @date: 2017/8/3 14:42
	 * @params: houseUpdateFieldAuditNewlog
	 * @return:
	 */
	public int updateHouseUpdateFieldAuditNewlogById(HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlog){
		return mybatisDaoContext.update(SQLID+"updateHouseUpdateFieldAuditNewlogById", houseUpdateFieldAuditNewlog);
	}


}
