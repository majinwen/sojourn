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

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.message.MsgFirstAdvisoryEntity;
import com.ziroom.minsu.services.message.dao.MsgFirstAdvisoryDao;
import com.ziroom.minsu.services.message.dto.MsgAdvisoryFollowRequest;
import com.ziroom.minsu.services.message.dto.MsgFirstDdvisoryRequest;
import com.ziroom.minsu.services.message.entity.MsgAdvisoryFollowVo;
import com.ziroom.minsu.services.message.entity.SysMsgVo;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

/**
 * <p>TODO</p>
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
@Service("message.msgFirstAdvisoryServiceImpl")
public class MsgFirstAdvisoryServiceImpl {

	@Resource(name = "message.msgFirstAdvisoryDao")
	private MsgFirstAdvisoryDao msgFirstAdvisoryDao;


	/**
	 *
	 * 保存实体
	 *
	 * @author yd
	 * @created 2017年4月8日 下午1:04:53
	 *
	 * @param msgFirstAdvisory
	 */
	public int saveMsgFirstAdvisory(MsgFirstAdvisoryEntity msgFirstAdvisory){
		return msgFirstAdvisoryDao.saveMsgFirstAdvisory(msgFirstAdvisory);
	}

	/**
	 *
	 * 根据fid 更新实体
	 *
	 * @author yd
	 * @created 2017年4月8日 下午1:06:58
	 *
	 * @param msgFirstAdvisory
	 * @return
	 */
	public int updateByUid(MsgFirstAdvisoryEntity msgFirstAdvisory){

		return msgFirstAdvisoryDao.updateByUid(msgFirstAdvisory);
	}

	/**
	 *
	 * 批量更新
	 *
	 * @author yd
	 * @created 2017年4月10日 下午8:06:20
	 *
	 * @param listMsgFirstAdvisory
	 */
	public int updateBathByUid(List<MsgFirstAdvisoryEntity> listMsgFirstAdvisory){

		int i =  0;
		if(!Check.NuNCollection(listMsgFirstAdvisory)){
			for (MsgFirstAdvisoryEntity msgFirstAdvisoryEntity : listMsgFirstAdvisory) {
				i += updateByUid(msgFirstAdvisoryEntity);
			}
		}
		return i;
	}


	/**
	 *
	 * 条件查询
	 *
	 * @author yd
	 * @created 2017年4月8日 下午2:35:59
	 *
	 * @param msgFirstDdvisoryRequest
	 * @return
	 */
	public List<SysMsgVo> findAllByCondition(MsgFirstDdvisoryRequest msgFirstDdvisoryRequest){
		return msgFirstAdvisoryDao.findAllByCondition(msgFirstDdvisoryRequest);
	}

	/**
	 *
	 * 条件分页查询留言房源关系实体 （房源留言fid必须要有）
	 *
	 * @author yd
	 * @created 2016年4月16日 下午3:41:14
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public PagingResult<SysMsgVo> queryByPage(MsgFirstDdvisoryRequest msgFirstDdvisoryRequest){
		return msgFirstAdvisoryDao.queryByPage(msgFirstDdvisoryRequest);
	}

	/**
	  * 按照msgBaseFid查找
	  * @author wangwentao
	  * @created 2017/5/25 17:08
	  *
	  * @param
	  * @return
	  */
	public MsgFirstAdvisoryEntity queryByMsgBaseFid(String msgBaseFid) {
		return msgFirstAdvisoryDao.queryByMsgBaseFid(msgBaseFid);
	}

	/**
	 * 分页获取所有需要跟进的首次咨询对象
	 *
	 * @author loushuai
	 * @created 2017年5月26日 上午9:53:26
	 *
	 * @param paramRequet
	 * @return
	 */
	public PagingResult<MsgAdvisoryFollowVo> queryAllNeedFollowPage(MsgAdvisoryFollowRequest paramRequet) {
		return msgFirstAdvisoryDao.queryAllNeedFollowPage(paramRequet);
	}

	/**
	 * 获取所有需要跟进的首次咨询对象List
	 *
	 * @author loushuai
	 * @created 2017年5月27日 上午10:51:39
	 *
	 * @param paramRequet
	 * @return
	 */
	public List<MsgAdvisoryFollowVo> queryAllNeedFollowList(
			MsgAdvisoryFollowRequest paramRequet) {
		return msgFirstAdvisoryDao.queryAllNeedFollowList(paramRequet);
	}

	/**
	 * 分页获取所有需要跟进的首次咨询对象(houseFidList和roomFidList其中一个为空)
	 *
	 * @author loushuai
	 * @created 2017年5月27日 下午5:04:19
	 *
	 * @param paramRequet
	 * @return
	 */
	public PagingResult<MsgAdvisoryFollowVo> queryAllNeedFollowPageNoUnion(
			MsgAdvisoryFollowRequest paramRequet) {
		return msgFirstAdvisoryDao.queryAllNeedFollowPageNoUnion(paramRequet);
	}
}
