/**
 * @FileName: MsgFirstAdvisoryServiceImpl.java
 * @Package com.ziroom.minsu.services.message.service
 *
 * @author yd
 * @created 2017年4月8日 下午2:44:58
 *
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.service;

import com.ziroom.minsu.entity.message.MsgAdvisoryFollowupEntity;
import com.ziroom.minsu.entity.message.MsgFirstAdvisoryEntity;
import com.ziroom.minsu.services.message.dao.MsgAdvisoryFollowupDao;
import com.ziroom.minsu.services.message.dao.MsgFirstAdvisoryDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * <BR> 2017-06-03      王文涛			先保存实体,再更新首次咨询表
 * </PRE>
 *
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@Service("message.msgAdvisoryFollowupServiceImpl")
public class MsgAdvisoryFollowupServiceImpl {

	@Resource(name = "message.msgAdvisoryFollowupDao")
	private MsgAdvisoryFollowupDao msgAdvisoryFollowupDao;

	@Resource(name = "message.msgFirstAdvisoryDao")
	private MsgFirstAdvisoryDao msgFirstAdvisoryDao;

	/**
	 *
	 * 保存实体,并更新首次咨询表
	 *
	 * @author loushuai
	 * @created 2017年5月25日 下午4:49:05
	 *
	 * @param msgBaseEntity
	 * @return
	 */
	public int save(MsgAdvisoryFollowupEntity entity){
		MsgFirstAdvisoryEntity msgFirstAdvisoryEntity = new MsgFirstAdvisoryEntity();
		msgFirstAdvisoryEntity.setFid(entity.getMsgFirstAdvisoryFid());
		msgFirstAdvisoryEntity.setFollowStatus(entity.getAfterStatus());
		msgFirstAdvisoryEntity.setLastModifyDate(new Date());
		int result = msgAdvisoryFollowupDao.save(entity);
		if (result == 1) {
			result = msgFirstAdvisoryDao.updateFollowStatusByFid(msgFirstAdvisoryEntity);
		}
		return result;
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
		return msgAdvisoryFollowupDao.getByFid(fid);
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
		return msgAdvisoryFollowupDao.getAllByFisrtAdvisoryFid(msgAdvisoryfid);
	}
}
