/**
 * @FileName: HouseUpdateFieldAuditManagerDao.java
 * @Package com.ziroom.minsu.services.house.dao
 * 
 * @author yd
 * @created 2017年6月30日 下午4:25:21
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditManagerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>房源审核字段管理 数据持久</p>
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
@Repository("house.houseUpdateFieldAuditManagerDao")
public class HouseUpdateFieldAuditManagerDao {


    private String SQLID="house.houseUpdateFieldAuditManagerDao.";

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
     * @param houseUpdateFieldAuditManager
     */
    public int saveHouseUpdateFieldAuditManager(HouseUpdateFieldAuditManagerEntity houseUpdateFieldAuditManager){
    	return mybatisDaoContext.save(SQLID+"saveHouseUpdateFieldAuditManager", houseUpdateFieldAuditManager);
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
	public HouseUpdateFieldAuditManagerEntity findHouseUpdateFieldAuditManagerByFid(String fid){

		return mybatisDaoContext.findOne(SQLID+"findHouseUpdateFieldAuditManagerByFid", HouseUpdateFieldAuditManagerEntity.class, fid);
	}

	/**
	 * @description: 根据fid 和 type 获取需要审核的字段
	 * @author: lusp
	 * @date: 2017/8/8 17:13
	 * @params: houseUpdateFieldAuditManagerEntity
	 * @return:
	 */
	public HouseUpdateFieldAuditManagerEntity findHouseUpdateFieldAuditManagerByFidAndType(HouseUpdateFieldAuditManagerEntity houseUpdateFieldAuditManagerEntity){
		return mybatisDaoContext.findOne(SQLID+"findHouseUpdateFieldAuditManagerByFidAndType", HouseUpdateFieldAuditManagerEntity.class, houseUpdateFieldAuditManagerEntity);
	}

	/**
	 * @description: 获取所有需要审核的字段
	 * @author: lusp
	 * @date: 2017/8/8 11:19
	 * @params:
	 * @return:
	 */
	public List<HouseUpdateFieldAuditManagerEntity> findAllHouseUpdateFieldAuditManager(){
		return mybatisDaoContext.findAll(SQLID+"findAllHouseUpdateFieldAuditManager");
	}

	/**
	 * @description: 根据type获取待审核的字段
	 * @author: lusp
	 * @date: 2017/8/8 15:37
	 * @params: type
	 * @return:
	 */
	public List<HouseUpdateFieldAuditManagerEntity> findHouseUpdateFieldAuditManagerByType(Integer type){
		return mybatisDaoContext.findAll(SQLID+"findHouseUpdateFieldAuditManagerByType",HouseUpdateFieldAuditManagerEntity.class, type);
	}


}
