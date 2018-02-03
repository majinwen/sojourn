package com.ziroom.minsu.services.message.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgUserRelOperaEntity;
@Repository("message.msgUserRelOperaDao")
public class MsgUserRelOperaDao {
    
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgUserRelOperaDao.class);

	private String SQLID = "message.msgUserRelOperaDao.";

	@Autowired
	@Qualifier("message.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 插入对象
	 *
	 * @author loushuai
	 * @created 2017年8月30日 下午6:23:23
	 *
	 * @param record
	 * @return
	 */
    public int insertSelective(MsgUserRelOperaEntity record){
		if(Check.NuNObj(record) ){
			LogUtil.info(logger, "MsgUserRelOperaEntity is null");
			return 0;
		}
		if(Check.NuNStr(record.getFid())) record.setFid(UUIDGenerator.hexUUID());
		return this.mybatisDaoContext.save(SQLID+"insertSelective", record);
    };


    /**
     * 
     * 查询对象
     *
     * @author loushuai
     * @created 2017年8月30日 下午6:26:24
     *
     * @param fid
     * @return
     */
    public MsgUserRelOperaEntity selectByFid(String fid){
    	if(Check.NuNStr(fid)){
			return null;
		}
		return mybatisDaoContext.findOne(SQLID+"selectByFid", MsgUserRelOperaEntity.class, fid);
    };

    /**
     * 
     * 更新对象
     *
     * @author loushuai
     * @created 2017年8月30日 下午6:26:09
     *
     * @param record
     * @return
     */
    public int updateByFid(MsgUserRelOperaEntity record){
    	if(Check.NuNStr(record.getFid())){
			return 0;
		}
    	return this.mybatisDaoContext.update(SQLID+"updateByFid", record);
    };

  
}