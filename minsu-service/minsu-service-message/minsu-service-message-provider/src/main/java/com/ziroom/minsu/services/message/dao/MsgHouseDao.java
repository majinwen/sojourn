/**
 * @FileName: MsgHouseDao.java
 * @Package com.ziroom.minsu.services.message.dao
 * 
 * @author yd
 * @created 2016年4月16日 下午3:27:15
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgHouseEntity;
import com.ziroom.minsu.services.common.vo.HouseStatsVo;
import com.ziroom.minsu.services.message.dto.MsgHouseRequest;
import com.ziroom.minsu.services.message.entity.MsgHouseListVo;

/**
 * <p>房源留言关联持久化/p>
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
@Repository("message.msgHouseDao")
public class MsgHouseDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgHouseDao.class);

	private String SQLID = "message.msgHouseDao.";

	@Autowired
	@Qualifier("message.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 
	 * 条件分页查询留言房源关系实体
	 *
	 * @author yd
	 * @created 2016年4月16日 下午3:41:14
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public PagingResult<MsgHouseEntity> queryByPage(MsgHouseRequest msgHouseRequest){

		if(Check.NuNObj(msgHouseRequest)) msgHouseRequest = new MsgHouseRequest();
		LogUtil.info(logger, "当前查询条件msgHouseRequest={}", msgHouseRequest);
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(msgHouseRequest.getLimit());
		pageBounds.setPage(msgHouseRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "queryByPage", MsgHouseEntity.class, msgHouseRequest, pageBounds);
	}
	/**
	 * 
	 * 查询租客列表信息
	 *
	 * @author jixd
	 * @created 2016年4月21日 下午8:01:15
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public PagingResult<MsgHouseListVo> queryTenantMsgList(MsgHouseRequest msgHouseRequest){

		if(Check.NuNObj(msgHouseRequest)){
			LogUtil.error(logger, "landlordfid  is null");
		}
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(msgHouseRequest.getLimit());
		pageBounds.setPage(msgHouseRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "selectTenantMsgList", MsgHouseListVo.class, msgHouseRequest, pageBounds);
	}
	/**
	 * 
	 * 查询房东列表信息
	 *
	 * @author jixd
	 * @created 2016年4月21日 下午8:01:53
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public PagingResult<MsgHouseListVo> queryLandlordMsgList(MsgHouseRequest msgHouseRequest){

		if(Check.NuNObj(msgHouseRequest)){
			LogUtil.error(logger, "landlordfid  is null");
		}
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(msgHouseRequest.getLimit());
		pageBounds.setPage(msgHouseRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "selectLandlordMsgList", MsgHouseListVo.class, msgHouseRequest, pageBounds);
	}

	/**
	 *
	 * query MsgHouseEntity by fid
	 *
	 * @author yd
	 * @created 2016年4月18日 下午1:33:43
	 *
	 * @param fid
	 * @return
	 */
	public MsgHouseEntity queryByFid(String fid){

		if(Check.NuNStr(fid)){
			LogUtil.info(logger, "fid is null ,return null");
			return null;
		}
		return this.mybatisDaoContext.findOne(SQLID+"queryByFid", MsgHouseEntity.class, fid);
	}
	/**
	 * 
	 * 根据fid删除
	 *
	 * @author yd
	 * @created 2016年4月16日 下午3:54:25
	 *
	 * @param fid
	 */
	public int updateByFid(MsgHouseEntity msgHouseEntity){

		if(Check.NuNObj(msgHouseEntity)||Check.NuNStr(msgHouseEntity.getFid())){
			LogUtil.info(logger,"msgHouseEntity or fid is null or empty");
			return 0;
		}
		return this.mybatisDaoContext.update(SQLID+"updateByFid", msgHouseEntity);
	}

	/**
	 * 
	 * 保存实体
	 *
	 * @author yd
	 * @created 2016年4月16日 下午3:58:10
	 *
	 * @param msgHouseEntity
	 * @return
	 */
	public int save(MsgHouseEntity msgHouseEntity){
		if(Check.NuNObj(msgHouseEntity)){
			LogUtil.info(logger,"msgHouseEntity is null");
			return 0;
		}
		if(Check.NuNStr(msgHouseEntity.getFid())) msgHouseEntity.setFid(UUIDGenerator.hexUUID());
		return this.mybatisDaoContext.save(SQLID+"insertSelective", msgHouseEntity);
	}

	/**
	 * 
	 * 查询好友（根据房东好友uid或查询房客好友uid）
	 * 
	 * 只能单向查询(不能同时包含两个uid或同时为null)
	 *
	 * @author yd
	 * @created 2016年9月14日 下午4:13:29
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public List<String> queryFriendsUid(MsgHouseRequest msgHouseRequest){

		if(Check.NuNObj(msgHouseRequest)||(Check.NuNObj(msgHouseRequest.getLandlordUid())&&Check.NuNObj(msgHouseRequest.getTenantUid()))||
				(!Check.NuNObj(msgHouseRequest.getLandlordUid())&&!Check.NuNObj(msgHouseRequest.getTenantUid()))){
			return null;
		}
		return this.mybatisDaoContext.findAll(SQLID+"queryFriendsUid", String.class, msgHouseRequest);
	}
	
	/**
	 * 
	 * 按房客uid或房东uid 查询最近一条聊天主记录 （作用： 现在聊天以人与人为单位  之前是以房源  因此查询 最近一条 俩人聊天记录   房客与房东   或者房东与房客  都是符合条件的）
	 *
	 * @author yd
	 * @created 2016年9月21日 下午5:19:46
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public MsgHouseEntity queryOneMsgHouse(MsgHouseRequest msgHouseRequest){
		return this.mybatisDaoContext.findOne(SQLID+"queryOneMsgHouse", MsgHouseEntity.class, msgHouseRequest);
	}
	
	/**
	 * 
	 * 查询单位时间内房源(房间)咨询量
	 * 单位时间内相同房客,相同房东,相同房源(房间)的所有IM消息算一次咨询
	 *
	 * @author liujun
	 * @created 2016年12月1日
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public List<HouseStatsVo> queryConsultNumByHouseFid(Map<String, Object> paramMap){
		if (Check.NuNObjs(paramMap, paramMap.get("startTime"), paramMap.get("endTime"))) {
			paramMap.put("startTime", DateUtil.getDayBeforeCurrentDate());
			paramMap.put("endTime", DateUtil.dateFormat(new Date()));
		}
		return this.mybatisDaoContext.findAll(SQLID+"queryConsultNumByHouseFid", HouseStatsVo.class, paramMap);
	}
}
