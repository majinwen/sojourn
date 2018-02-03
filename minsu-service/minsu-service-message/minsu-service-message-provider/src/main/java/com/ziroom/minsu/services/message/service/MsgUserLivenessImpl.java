/**
 * @FileName: MsgUserLivenessImpl.java
 * @Package com.ziroom.minsu.services.message.service
 * 
 * @author loushuai
 * @created 2017年9月1日 上午11:49:36
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.message.MsgUserLivenessEntity;
import com.ziroom.minsu.entity.message.MsgUserRelEntity;
import com.ziroom.minsu.services.message.dao.MsgUserLivenessDao;

/**
 * <p>TODO</p>
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
@Service("message.msgUserLivenessImpl")
public class MsgUserLivenessImpl {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgUserLivenessImpl.class);
	
	@Resource(name="message.msgUserLivenessDao")
	private MsgUserLivenessDao msgUserLivenessDao;
	
	/**
	 * 同步聊天用户活跃时间
	 *
	 * @author loushuai
	 * @created 2017年9月1日 上午11:57:38
	 *
	 * @param msgReplySetRequest
	 * @return
	 */
	public int syncLivenessTime(List<MsgUserLivenessEntity> paramList) {
		int i=0;
		for (MsgUserLivenessEntity msgUserLiveness : paramList) {
			MsgUserLivenessEntity dataMsgUserLiveness = msgUserLivenessDao.selectByUid(msgUserLiveness.getUid());
			if(Check.NuNObj(dataMsgUserLiveness)){
				i=i+msgUserLivenessDao.insertSelective(msgUserLiveness);
				continue;
			}
			MsgUserLivenessEntity newMsgUserLiveness = new MsgUserLivenessEntity();
			newMsgUserLiveness.setLastLiveTime(msgUserLiveness.getLastLiveTime());
			newMsgUserLiveness.setUid(msgUserLiveness.getUid());
			i=i+msgUserLivenessDao.updateByUid(newMsgUserLiveness);
		}
		return i;
	}

	/**
	 * 获取所有uid的活跃度
	 *
	 * @author loushuai
	 * @created 2017年9月1日 下午7:16:50
	 *
	 * @param toUidList
	 * @return
	 */
	public List<MsgUserLivenessEntity> getAllUidLiveness(List<String> toUidList) {
		return msgUserLivenessDao.getAllUidLiveness(toUidList);
	}

	/**
	 *  查询用户活跃度
	 *
	 * @author loushuai
	 * @created 2017年9月14日 下午1:55:38
	 *
	 * @param msgUserLiveness
	 * @return
	 */
	public MsgUserLivenessEntity queryLiveness(MsgUserLivenessEntity msgUserLiveness) {
		return msgUserLivenessDao.selectByUid(msgUserLiveness.getUid());
	}
}
