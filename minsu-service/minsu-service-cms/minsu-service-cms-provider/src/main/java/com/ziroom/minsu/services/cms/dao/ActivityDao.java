package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityEntity;
import com.ziroom.minsu.entity.cms.ActivityVo;
import com.ziroom.minsu.services.cms.dto.ActivityInfoRequest;
import com.ziroom.minsu.services.cms.dto.ZrpActRequest;
import com.ziroom.minsu.valenum.cms.ActStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>活动表Dao-new</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年7月13日
 * @since 1.0
 * @version 1.0
 */
@Repository("cms.activityDao")
public class ActivityDao {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(ActivityDao.class);

	private String SQLID = "cms.activityDao.";

	@Autowired
	@Qualifier("cms.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;





	/**
	 * 获取当前的最新的种子房东免佣金的逻辑
	 * @author afi
	 * @return
	 */
	public ActivityEntity getSeedActivityLast(){
		return mybatisDaoContext.findOne(SQLID + "getSeedActivityLast", ActivityEntity.class);
	}

	/**
	 * 根据activitySn查询活动信息
	 * @author lishaochuan
	 * @create 2016年6月23日下午6:10:14
	 * @param actSn
	 * @return
	 */
	public ActivityEntity selectByActSn(String actSn) {
		if (Check.NuNStr(actSn)) {
			LogUtil.error(LOGGER, "参数为空,actSn:{}", actSn);
			throw new BusinessException("actSn参数为空");
		}
		return mybatisDaoContext.findOne(SQLID + "selectByActSn", ActivityEntity.class, actSn);
	}

	/**
	 * 根据groupSn查询活动信息
	 * @author lunan
	 * @create 2016年10月10日
	 * @param groupSn
	 * @return
	 */
	public ActivityEntity selectByGroupSn(String groupSn) {
		if (Check.NuNStr(groupSn)) {
			LogUtil.error(LOGGER, "参数为空,groupSn:{}", groupSn);
			throw new BusinessException("groupSn参数为空");
		}
		return mybatisDaoContext.findOne(SQLID + "selectByGroupSn", ActivityEntity.class, groupSn);
	}

	/**
	 * 根据活动号查询所有有效的活动
	 * @author jixd
	 * @create 2017年03月02日
	 * @param groupSn
	 * @return
	 */
	public List<ActivityEntity> listActivityByGroupSn(String groupSn){
		return mybatisDaoContext.findAll(SQLID + "listActivityByGroupSn", ActivityEntity.class, groupSn);
	}

	/**
	 * 查询有效的活动列表
	 * @author lishaochuan
	 * @create 2016年6月23日下午8:08:26
	 * @return
	 */
	public List<ActivityVo> getUnderwayActivityList(){
		Map<String, Object> map = new HashMap<String, Object>();

		List<Integer> actStatusList = new ArrayList<Integer>();
		actStatusList.add(ActStatusEnum.ABLE.getCode());
		map.put("actStatusList", actStatusList);
		return mybatisDaoContext.findAllByMaster(SQLID + "getUnderwayActivityList", ActivityVo.class, map);
	}


	/**
	 * 获取全部返现活动列表
	 * @author afi
	 * @return
	 */
	public List<ActivityVo> getCashbackListAll(){
		return mybatisDaoContext.findAll(SQLID + "getCashbackListAll", ActivityVo.class);
	}

	/**
	 * 获取全部返现活动列表
	 * @author afi
	 * @return
	 */
	public List<ActivityVo> getCashbackListOk(){
		return mybatisDaoContext.findAll(SQLID + "getCashbackListOk", ActivityVo.class);
	}


	/**
	 * 分页查询活动信息
	 * @author lishaochuan
	 * @create 2016年6月23日下午7:52:48
	 * @param request
	 * @return
	 */
	public PagingResult<ActivityVo> getActivityVoListByCondiction(ActivityInfoRequest request){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(request.getLimit());
		pageBounds.setPage(request.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getActivityVoListByCondiction", ActivityVo.class, request, pageBounds);
	}

	/**
	 * 分页查询活动信息
	 * @author lishaochuan
	 * @create 2016年6月23日下午7:52:48
	 * @param request
	 * @return
	 */
	public PagingResult<ActivityVo> getActivityVoListByCondictionByCity(ActivityInfoRequest request){
		if (Check.NuNStr(request.getCityCode())){
			LogUtil.error(LOGGER,"cityCode参数为空");
			throw new BusinessException("cityCode参数为空");
		}
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(request.getLimit());
		pageBounds.setPage(request.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getActivityVoListByCondictionByCity", ActivityVo.class, request, pageBounds);
	}


	/**
	 * 保存活动信息
	 * @author lishaochuan
	 * @create 2016年6月23日下午2:57:42
	 * @param activityEntity
	 * @return
	 */
	public int saveActivity(ActivityEntity activityEntity){
		if(Check.NuNObj(activityEntity)){
			LogUtil.error(LOGGER,"activityEntity参数为空");
			throw new BusinessException("activityEntity参数为空");
		}
		if(Check.NuNStr(activityEntity.getActSn())){
			LogUtil.error(LOGGER,"actSn参数为空");
			throw new BusinessException("actSn参数为空");
		}

		if (!Check.NuNObj(activityEntity.getCheckOutTime())){
			activityEntity.setCheckOutTime(activityEntity.getCheckOutTime());
		}

		return mybatisDaoContext.save(SQLID + "saveActivity", activityEntity);
	}

	/**
	 * 修改活动信息
	 * @author lishaochuan
	 * @create 2016年6月23日下午3:48:19
	 * @param activityEntity
	 * @return
	 */
	public int updateByActSn(ActivityEntity activityEntity){
		if(Check.NuNObj(activityEntity)){
			LogUtil.error(LOGGER, "activityEntity参数为空");
			throw new BusinessException("activityEntity参数为空");
		}
		if(Check.NuNStr(activityEntity.getActSn())){
			LogUtil.error(LOGGER, "actSn为空");
			throw new BusinessException("actSn为空");
		}
		if (!Check.NuNObj(activityEntity.getActEndTime())){
			activityEntity.setActEndTime(activityEntity.getActEndTime());
		}
		if (!Check.NuNObj(activityEntity.getCheckOutTime())){
			activityEntity.setCheckOutTime(activityEntity.getCheckOutTime());
		}
		return mybatisDaoContext.update(SQLID + "updateByActivitySn", activityEntity);
	}


	/**
	 * 修改优惠券活动信息
	 * @author lishaochuan
	 * @create 2016年6月23日下午3:48:19
	 * @param activityEntity
	 * @return
	 */
	public int updateCouponActBySn(ActivityEntity activityEntity){
		if(Check.NuNObj(activityEntity)){
			LogUtil.error(LOGGER, "activityEntity参数为空");
			throw new BusinessException("activityEntity参数为空");
		}
		if(Check.NuNStr(activityEntity.getActSn())){
			LogUtil.error(LOGGER, "actSn为空");
			throw new BusinessException("actSn为空");
		}
		if (!Check.NuNObj(activityEntity.getActEndTime())){
			activityEntity.setActEndTime(activityEntity.getActEndTime());
		}
		if (!Check.NuNObj(activityEntity.getCheckOutTime())){
			activityEntity.setCheckOutTime(activityEntity.getCheckOutTime());
			activityEntity.setCheckOutTime(activityEntity.getCheckOutTime());
		}
		return mybatisDaoContext.update(SQLID + "updateCouponActBySn", activityEntity);
	}


	/**
	 * 启动活动
	 * @author lishaochuan
	 * @create 2016年6月23日下午5:43:04
	 * @return
	 */
	public int enableActivity(String actSn){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oldStatus", ActStatusEnum.DISABLE.getCode());
		map.put("actStatus", ActStatusEnum.ABLE.getCode());
		map.put("actSn", actSn);
		return mybatisDaoContext.update(SQLID + "enableActivity", map);
	}


	/**
	 * 终止活动
	 * @author afi
	 * @create 2016年6月23日下午6:01:31
	 * @param actSn
	 * @return
	 */
	public int endActivity(String actSn){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oldStatus", ActStatusEnum.ABLE.getCode());
		map.put("actStatus", ActStatusEnum.END.getCode());
		map.put("actSn", actSn);
		return mybatisDaoContext.update(SQLID + "enableActivity", map);
	}


	/**
	 * 修改当前活动的优惠券状态
	 * @author afi
	 * @param actSn
	 * @return
	 */
	public int updateIsCouponStatus(String actSn,int couponStatus){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isCoupon", couponStatus);
		map.put("actSn", actSn);
		return mybatisDaoContext.update(SQLID + "updateIsCouponStatus", map);
	}




	/**
	 * 修改当前的优惠券活动为生成中
	 *
	 * @author afi
	 * @param actSn
	 * @return
	 */
	public int updateAcCouponIng(String actSn){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("actSn", actSn);
		return mybatisDaoContext.update(SQLID + "updateAcCouponIng", map);
	}

	/**
	 * @Description: 获取首单立减活动详情
	 * @Author:lusp
	 * @Date: 2017/6/5 11:31
	 * @Params:
	 */
	public ActivityEntity getSDLJActivityInfo(){
		return mybatisDaoContext.findOne(SQLID + "getSDLJActivityInfo", ActivityEntity.class);
	}

	/**
	 * 查询自如寓活动列表
	 *
	 * @param
	 * @return
	 * @author jixd
	 * @created 2017年10月16日 14:41:58
	 */
	public List<ActivityEntity> listSuitActForZrp(ZrpActRequest zrpActRequest) {
		return mybatisDaoContext.findAll(SQLID + "listSuitActForZrp", ActivityEntity.class, zrpActRequest);
	}

	/**
	 * 查询用户组对应得活动列表
	 *
	 * @param
	 * @return
	 * @author jixd
	 * @created 2018年01月26日 14:29:44
	 */
	public List<ActivityEntity> listUserGroupActForZrp(String userGroupFid) {
		return mybatisDaoContext.findAll(SQLID + "listUserGroupActForZrp", ActivityEntity.class, userGroupFid);
	}
}
