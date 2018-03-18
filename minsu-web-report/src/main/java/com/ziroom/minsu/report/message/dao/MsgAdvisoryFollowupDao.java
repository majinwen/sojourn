/**
 * @FileName: MsgAdvisoryFollowupDao.java
 * @Package com.ziroom.minsu.report.message.dao
 * 
 * @author ls
 * @created 2017年5月31日 上午11:35:24
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.message.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.message.MsgAdvisoryFollowupEntity;
import com.ziroom.minsu.report.message.dto.MsgAdvisoryFollowRequest;
import com.ziroom.minsu.report.message.vo.MsgAdvisoryFollowVo;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author ls
 * @since 1.0
 * @version 1.0
 */
@Repository("report.msgAdvisoryFollowupDao")
public class MsgAdvisoryFollowupDao {
	
	@Autowired
	@Qualifier("minsuReport.all.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	private String SQLID="report.msgAdvisoryFollowupDao.";
	
	
	/**
	 * TODO
	 *
	 * @author ls
	 * @created 2017年5月31日 上午11:37:25
	 *
	 * @param paramRequest
	 * @return
	 */
	public PagingResult<MsgAdvisoryFollowVo> exportAllNeedFollowByPage(
			MsgAdvisoryFollowRequest paramRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(paramRequest.getLimit());
		pageBounds.setPage(paramRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID+"exportAllNeedFollowByPage", MsgAdvisoryFollowVo.class, paramRequest.toMap(), pageBounds); 
	}
	
	/**
	 * TODO
	 *
	 * @author ls
	 * @created 2017年5月31日 上午11:37:25
	 *
	 * @param paramRequest
	 * @return
	 */
	public PagingResult<MsgAdvisoryFollowVo> queryAllNeedFollowPage(
			MsgAdvisoryFollowRequest paramRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(paramRequest.getLimit());
		pageBounds.setPage(paramRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID+"queryAllNeedFollowPage", MsgAdvisoryFollowVo.class, paramRequest.toMap(), pageBounds); 
	}
	
	
	
	/**
	 * TODO
	 *
	 * @author ls
	 * @created 2017年5月31日 下午8:47:06
	 *
	 * @param paramRequest
	 * @return
	 */
	public List<MsgAdvisoryFollowVo> queryAllNeedFollowList(
			MsgAdvisoryFollowRequest paramRequest) {
		return mybatisDaoContext.findAll(SQLID+"queryAllNeedFollowList", MsgAdvisoryFollowVo.class, paramRequest.toMap());
	}
	
	/**
	 * 根据msgFirstAdvisoryFid查出所有跟进人集合
	 *
	 * @author ls
	 * @created 2017年5月31日 上午11:51:41
	 *
	 * @param msgFirstAdvisoryFid
	 * @return
	 */
	public List<String> getAllEmpNameByFirstAdvFid(
			String msgFirstAdvisoryFid) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("msgFirstAdvisoryFid", msgFirstAdvisoryFid);
		return mybatisDaoContext.findAll(SQLID+"getAllEmpNameByFirstAdvFid", String.class, param);
	}
	
	
	/**
	 * 根据msgFirstAdvisoryFid查出所有跟进人及其跟进记录
	 *
	 * @author ls
	 * @created 2017年5月31日 上午11:51:41
	 *
	 * @param msgFirstAdvisoryFid
	 * @return
	 */
	public List<MsgAdvisoryFollowupEntity> getMsgFollowupByFirstAdvFid(
			String msgFirstAdvisoryFid) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("msgFirstAdvisoryFid", msgFirstAdvisoryFid);
		return mybatisDaoContext.findAll(SQLID+"getMsgFollowupByFirstAdvFid", MsgAdvisoryFollowupEntity.class, param);
	}

	/**
	 * 根据首次咨询fid获取房东首次回复时间
	 *
	 * @author ls
	 * @created 2017年7月7日 下午5:58:32
	 *
	 * @param paramMap
	 * @return
	 */
	public MsgAdvisoryFollowVo getLandLordFirstReplyTime(Map<String, Object> paramMap) {
		return mybatisDaoContext.findOne(SQLID+"getLandLordFirstReplyTime", MsgAdvisoryFollowVo.class, paramMap);
	}
	

	
}
