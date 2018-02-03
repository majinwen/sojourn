package com.ziroom.minsu.services.message.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgUserLivenessEntity;
import com.ziroom.minsu.entity.message.MsgUserRelEntity;


@Repository("message.msgUserLivenessDao")
public class MsgUserLivenessDao {
	 
		/**
		 * 日志对象
		 */
		private static Logger logger = LoggerFactory.getLogger(MsgUserLivenessDao.class);

		private String SQLID = "message.msgUserLivenessDao.";

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
	    public int insertSelective(MsgUserLivenessEntity record){
			if(Check.NuNObj(record) ){
				LogUtil.info(logger, "MsgUserLivenessEntity is null");
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
	    public MsgUserLivenessEntity selectByUid(String uid){
	    	if(Check.NuNStr(uid)){
				return null;
			}
			return mybatisDaoContext.findOne(SQLID+"selectByUid", MsgUserLivenessEntity.class, uid);
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
	    public int updateByUid(MsgUserLivenessEntity record){
	    	if(Check.NuNStr(record.getUid())){
				return 0;
			}
	    	return this.mybatisDaoContext.update(SQLID+"updateByUid", record);
	    }

		/**
		 * 获取所有用户的活跃度
		 *
		 * @author loushuai
		 * @created 2017年9月1日 下午7:17:47
		 *
		 * @param toUidList
		 * @return
		 */
		public List<MsgUserLivenessEntity> getAllUidLiveness(List<String> toUidList) {
			if(Check.NuNCollection(toUidList)){
				return null;
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("toUidList", toUidList);
			map.put("isDel", 0);
			return this.mybatisDaoContext.findAll(SQLID+"getAllUidLiveness", MsgUserLivenessEntity.class, map);
		};

}