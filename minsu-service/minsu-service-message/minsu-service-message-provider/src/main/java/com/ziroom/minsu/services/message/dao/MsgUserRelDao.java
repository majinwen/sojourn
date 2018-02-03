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
import com.ziroom.minsu.entity.message.MsgUserRelEntity;


@Repository("message.msgUserRelDao")
public class MsgUserRelDao {
   
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgUserRelDao.class);

	private String SQLID = "message.msgUserRelDao.";

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
    public int insertSelective(MsgUserRelEntity record){
		if(Check.NuNObj(record) ){
			LogUtil.info(logger, "MsgUserRelEntity is null");
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
    public MsgUserRelEntity selectByFid(String fid){
    	if(Check.NuNStr(fid)){
			return null;
		}
		return mybatisDaoContext.findOne(SQLID+"selectByFid", MsgUserRelEntity.class, fid);
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
    public int updateByFid(MsgUserRelEntity record){
    	if(Check.NuNStr(record.getFid())){
			return 0;
		}
    	return this.mybatisDaoContext.update(SQLID+"updateByFid", record);
    }

	/**
	 * 根据from_uid,to_uid,zoomFlg三个字段获取对象
	 *
	 * @author loushuai
	 * @created 2017年9月4日 下午4:18:06
	 *
	 * @param msgUserRel
	 * @return
	 */
	public MsgUserRelEntity selectByParam(MsgUserRelEntity msgUserRel) {
		return mybatisDaoContext.findOne(SQLID+"selectByParam", MsgUserRelEntity.class, msgUserRel);
	};

}