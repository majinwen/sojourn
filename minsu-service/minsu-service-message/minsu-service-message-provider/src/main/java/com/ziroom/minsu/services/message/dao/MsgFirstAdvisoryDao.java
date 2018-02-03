/**
 * @FileName: MsgFirstAdvisoryDao.java
 * @Package com.ziroom.minsu.services.message.dao
 * 
 * @author yd
 * @created 2017年4月8日 上午11:14:58
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgFirstAdvisoryEntity;
import com.ziroom.minsu.services.message.dto.MsgAdvisoryFollowRequest;
import com.ziroom.minsu.services.message.dto.MsgFirstDdvisoryRequest;
import com.ziroom.minsu.services.message.entity.MsgAdvisoryFollowVo;
import com.ziroom.minsu.services.message.entity.SysMsgVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>首次咨询执行 持久化</p>
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
@Repository("message.msgFirstAdvisoryDao")
public class MsgFirstAdvisoryDao {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(MsgFirstAdvisoryDao.class);

	private String SQLID = "message.msgFirstAdvisoryDao.";

	@Autowired
	@Qualifier("message.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
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
		
		if(Check.NuNObj(msgFirstAdvisory) ){
			LogUtil.info(LOGGER, "msgFirstAdvisory is null");
			return 0;
		}
		if(Check.NuNStr(msgFirstAdvisory.getFid())) msgFirstAdvisory.setFid(UUIDGenerator.hexUUID());
		
		return this.mybatisDaoContext.save(SQLID+"insertSelective", msgFirstAdvisory);
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
		
		if(Check.NuNObj(msgFirstAdvisory)||Check.NuNStr(msgFirstAdvisory.getFromUid())
				||Check.NuNStr(msgFirstAdvisory.getToUid())){
			LogUtil.info(LOGGER, "msgFirstAdvisory or fid is null or empty");
			return 0;
		}
		return this.mybatisDaoContext.update(SQLID+"updateByUid", msgFirstAdvisory);
	}

	/**
	  *	根据fid更新followStatus
	  * @author wangwentao
	  * @created 2017/6/3 10:51
	  *
	  * @param
	  * @return
	  */
	public int updateFollowStatusByFid(MsgFirstAdvisoryEntity msgFirstAdvisory){

		if(Check.NuNObj(msgFirstAdvisory)||Check.NuNStr(msgFirstAdvisory.getFid())
				||Check.NuNObj(msgFirstAdvisory.getFollowStatus())){
			LogUtil.info(LOGGER, "msgFirstAdvisory or fid or followStatus is null or empty");
			return 0;
		}
		return this.mybatisDaoContext.update(SQLID+"updateFollowStatusByFid", msgFirstAdvisory);
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
	
		if(Check.NuNObj(msgFirstDdvisoryRequest)){
			LogUtil.error(LOGGER, "msgFirstDdvisoryRequest  is null");
		}
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(msgFirstDdvisoryRequest.getLimit());
		pageBounds.setPage(msgFirstDdvisoryRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "queryByPage", SysMsgVo.class, msgFirstDdvisoryRequest.toMap(), pageBounds);
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
		
		if(Check.NuNObj(msgFirstDdvisoryRequest)){
			LogUtil.error(LOGGER, "msgFirstDdvisoryRequest  is null");
		}
		
		return mybatisDaoContext.findAllByMaster(SQLID + "queryByPage",SysMsgVo.class, msgFirstDdvisoryRequest.toMap());
	}

	public MsgFirstAdvisoryEntity queryByMsgBaseFid(String msgBaseFid) {
		if (Check.NuNStr(msgBaseFid)) {
			return null;
		}
		return mybatisDaoContext.findOne(SQLID + "queryByMsgBaseFid", MsgFirstAdvisoryEntity.class, msgBaseFid);

	}

	/**
	 *  分页获取所有需要跟进的首次咨询对象uid
	 *
	 * @author loushuai
	 * @created 2017年5月26日 上午9:54:40
	 *
	 * @param paramRequet
	 * @return
	 */
	public PagingResult<MsgAdvisoryFollowVo> queryAllNeedFollowPage(
			MsgAdvisoryFollowRequest paramRequet) {
		if(Check.NuNObj(paramRequet)){
			LogUtil.error(LOGGER, "paramRequet  is null");
		}
		PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(paramRequet.getLimit());
        pageBounds.setPage(paramRequet.getPage());
		
		return  mybatisDaoContext.findForPage(SQLID + "queryAllNeedFollowPage",MsgAdvisoryFollowVo.class, paramRequet.toMap(), pageBounds);
	}

	/**
	 *  获取所有需要跟进的首次咨询对象list
	 *
	 * @author loushuai
	 * @created 2017年5月27日 上午10:52:42
	 *
	 * @param paramRequet
	 * @return
	 */
	public List<MsgAdvisoryFollowVo> queryAllNeedFollowList(
			MsgAdvisoryFollowRequest paramRequet) {
		return mybatisDaoContext.findAll(SQLID + "queryAllNeedFollowList",MsgAdvisoryFollowVo.class, paramRequet.toMap());
	}

	/**
	 * 分页获取所有需要跟进的首次咨询对象(houseFidList和roomFidList其中一个为空)
	 *
	 * @author loushuai
	 * @created 2017年5月27日 下午5:05:20
	 *
	 * @param paramRequet
	 * @return
	 */
	public PagingResult<MsgAdvisoryFollowVo> queryAllNeedFollowPageNoUnion(
			MsgAdvisoryFollowRequest paramRequet) {
		if(Check.NuNObj(paramRequet)){
			LogUtil.error(LOGGER, "paramRequet  is null");
		}
		PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(paramRequet.getLimit());
        pageBounds.setPage(paramRequet.getPage());
		return mybatisDaoContext.findForPage(SQLID + "queryAllNeedFollowPageNoUnion",MsgAdvisoryFollowVo.class, paramRequet.toMap(), pageBounds);
	}
}
