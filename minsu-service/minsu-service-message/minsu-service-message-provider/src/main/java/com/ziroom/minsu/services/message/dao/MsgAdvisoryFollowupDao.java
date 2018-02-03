package com.ziroom.minsu.services.message.dao;

/**
 * <p>首次咨询跟踪持久化层</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgAdvisoryFollowupEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("message.msgAdvisoryFollowupDao")
public class MsgAdvisoryFollowupDao {
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgAdvisoryFollowupDao.class);

	private String SQLID = "message.msgAdvisoryFollowupDao.";

	@Autowired
	@Qualifier("message.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 保存实体
	 *
	 * @author loushuai
	 * @created 2017年5月25日 下午4:49:05
	 *
	 * @param msgBaseEntity
	 * @return
	 */
	public int save(MsgAdvisoryFollowupEntity msgAdvisoryFollowupEntity){
		
		if(Check.NuNObj(msgAdvisoryFollowupEntity) ){
			LogUtil.info(logger, "msgAdvisoryFollowupEntity is null");
			return 0;
		}
		if(Check.NuNStr(msgAdvisoryFollowupEntity.getFid())) msgAdvisoryFollowupEntity.setFid(UUIDGenerator.hexUUID());
		return this.mybatisDaoContext.save(SQLID+"insertSelective", msgAdvisoryFollowupEntity);
	}
	
	/**
	 * 
	 * 根据t_msg_first_advisory的fid获取MsgAdvisoryFollowupEntity
	 *
	 * @author loushuai
	 * @created 2017年5月25日 上午10:58:19
	 *
	 * @param msgAdvisoryfid
	 * @return
	 */
	public MsgAdvisoryFollowupEntity getByFid(String fid) {
		if(Check.NuNStr(fid)){
			LogUtil.info(logger, "fid is null");
			return null;
		}
	
		return this.mybatisDaoContext.findOne(SQLID+"getByFid", MsgAdvisoryFollowupEntity.class, fid);
	}

	/**
	  * 根据首次咨询表fid查询所有跟进记录
	  * @author wangwentao
	  * @created 2017/5/27 10:08
	  *
	  * @param
	  * @return
	  */
	public List<MsgAdvisoryFollowupEntity> getAllByFisrtAdvisoryFid(String msgAdvisoryfid) {
		if(Check.NuNStr(msgAdvisoryfid)){
			LogUtil.info(logger, "msgAdvisoryfid is null");
			return null;
		}
		return this.mybatisDaoContext.findAll(SQLID+"getAllByMsgAdvisoryfid", MsgAdvisoryFollowupEntity.class, msgAdvisoryfid);
	}
	
}