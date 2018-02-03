package com.ziroom.minsu.services.basedata.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.base.AuthIdentifyEntity;


@Repository("basedata.authIdentifyDao")
public class AuthIdentifyDao {
	
	private String SQLID = "basedata.authIdentifyDao.";

	@Autowired
	@Qualifier("basedata.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	

	/**
	 * 
	 * 插入对象
	 *
	 * @author loushuai
	 * @created 2017年8月30日 下午1:33:12
	 *
	 * @param record
	 * @return
	 */
   public int insertSelective(AuthIdentifyEntity record){
    	return mybatisDaoContext.save(SQLID+"insertSelective", record);
    };
 
    /**
	 * 
	 * 更新对象
	 *
	 * @author loushuai
	 * @created 2017年8月30日 下午1:33:12
	 *
	 * @param record
	 * @return
	 */
    public int updateByPrimaryKeySelective(AuthIdentifyEntity record){
    	return mybatisDaoContext.update(SQLID+"updateByPrimaryKeySelective", record);
    }

    /**
   	 * 
   	 * 查询对象
   	 *
   	 * @author loushuai
   	 * @created 2017年8月30日 下午1:33:12
   	 *
   	 * @param record
   	 * @return
   	 */
	public AuthIdentifyEntity getByParam(AuthIdentifyEntity record) {
		return mybatisDaoContext.findOne(SQLID+"getByParam", AuthIdentifyEntity.class, record);
	};

}