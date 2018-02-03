package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.cms.ActivityRemindLogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * <p>活动提醒通知记录表</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lusp on 2017年6月6日
 * @since 1.0
 * @version 1.0
 */
@Repository("cms.activityRemindLogDao")
public class ActivityRemindLogDao {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(ActivityRemindLogDao.class);

	private String SQLID = "cms.activityRemindLogDao.";

	@Autowired
	@Qualifier("cms.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;


	/**
	 * @Description: 插入活动提醒信息记录表（不重复插入）
	 * @Author: lusp
	 * @Date: 2017/6/6 16:27
	 * @Params:
	 */
	public int insertActivityRemindLogIgnore(ActivityRemindLogEntity activityRemindLogEntity){
		return mybatisDaoContext.save(SQLID+"insertActivityRemindLogIgnore", activityRemindLogEntity);
	}

	/**
	 * @Description: 根据用户uid 逻辑删除一条记录
	 * @Author: lusp
	 * @Date: 2017/6/6 16:34
	 * @Params:
	 */
	public int deleteActivityRemindLogByUid(ActivityRemindLogEntity activityRemindLogEntity){
		return mybatisDaoContext.update(SQLID+"deleteActivityRemindLogByUid", activityRemindLogEntity);
	}

	/**
	 * @Description: 分页查询已经触发通知的新用户信息
	 * @Author:lusp
	 * @Date: 2017/6/7 11:39
	 * @Params: paramMap
	 */
	public PagingResult<ActivityRemindLogEntity> queryRemindUidInfoByPage(Map<String, Object> paramMap){
		PageBounds pageBounds=new PageBounds();
		pageBounds.setPage(Integer.valueOf(paramMap.get("page").toString()));
		pageBounds.setLimit(Integer.valueOf(paramMap.get("limit").toString()));
		return mybatisDaoContext.findForPage(SQLID+"queryRemindUidInfoByPage", ActivityRemindLogEntity.class, paramMap, pageBounds);
	}

	/**
	 * @Description: 更新已发送次数 + 1
	 * @Author:lusp
	 * @Date: 2017/6/7 16:08
	 * @Params:
	 */
	public int updateSendTimesRunTimeByUid(ActivityRemindLogEntity activityRemindLogEntity){
		return mybatisDaoContext.update(SQLID+"updateSendTimesRunTimeByUid", activityRemindLogEntity);
	}


}
