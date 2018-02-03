package com.ziroom.minsu.services.message.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.HuanxinImOfflineEntity;

@Repository("message.huanxinImOfflineDao")
public class HuanxinImOfflineDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgUserRelDao.class);

	private String SQLID = "message.huanxinImOfflineDao.";

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
    public int insertSelective(HuanxinImOfflineEntity record){
		if(Check.NuNObj(record) ){
			LogUtil.info(logger, "HuanxinImOfflineEntity is null");
			return 0;
		}
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
    public HuanxinImOfflineEntity selectByMsgid(String msgId){
    	if(Check.NuNStr(msgId)){
			return null;
		}
		return mybatisDaoContext.findOne(SQLID+"selectByMsgid", HuanxinImOfflineEntity.class, msgId);
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
    public int updateByMsgid(HuanxinImOfflineEntity record){
    	if(Check.NuNStr(record.getMsgId())){
			return 0;
		}
    	return this.mybatisDaoContext.update(SQLID+"updateByMsgid", record);
    };

}