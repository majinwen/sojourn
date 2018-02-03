package com.ziroom.minsu.services.cms.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityApplyDescEntity;
import com.ziroom.minsu.entity.cms.ActivityApplyEntity;
import com.ziroom.minsu.entity.cms.ActivityApplyExtEntity;
import com.ziroom.minsu.services.cms.dao.ActivityApplyDao;
import com.ziroom.minsu.services.cms.dao.ActivityApplyDescDao;
import com.ziroom.minsu.services.cms.dao.ActivityApplyExtDao;
import com.ziroom.minsu.services.cms.dto.LanApplayRequest;
import com.ziroom.minsu.services.cms.entity.ActivityApplyVo;

/**
 * <p>
 * 活动申请service
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年6月29日
 * @since 1.0
 * @version 1.0
 */
@Service("cms.activityApplyServiceImpl")
public class ActivityApplyServiceImpl {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(ActivityApplyServiceImpl.class);

	@Resource(name = "cms.activityApplyDao")
	private ActivityApplyDao activityApplyDao;
	
	@Resource(name = "cms.ActivityApplyDescDao")
	private ActivityApplyDescDao applyDescDao;

	@Resource(name = "cms.activityApplyExtDao")
	private ActivityApplyExtDao activityApplyExtDao;
	
	
	/**
	 * 根据手机号查询申请信息
	 * @author lishaochuan
	 * @create 2016年6月30日上午10:13:34
	 * @param mobile
	 * @return
	 */
	public ActivityApplyEntity getApplyByMobile(String mobile){
    	return activityApplyDao.getApplyByMobile(mobile);
    }
	

	/**
	 * 保存申请信息
	 * @author lishaochuan
	 * @create 2016年6月29日下午3:46:19
	 * @param apply
	 * @param applyExt
	 */
	public void save(ActivityApplyEntity apply,ActivityApplyDescEntity applyDesc, List<ActivityApplyExtEntity> applyExtList) {
		int num = activityApplyDao.save(apply);
		if (num != 1) {
			LogUtil.error(LOGGER, "保存申请信息失败,num:{},apply:{}", num, apply);
			throw new BusinessException("保存申请信息失败");
		}
		applyDesc.setActivityApplyFid(apply.getFid());
		num=applyDescDao.save(applyDesc);
		if (num != 1) {
			LogUtil.error(LOGGER, "保存申请描述信息失败,num:{},applyDesc:{}", num, applyDesc);
			throw new BusinessException("保存申请描述信息失败");
		}
		for (ActivityApplyExtEntity applyExt : applyExtList) {
			num = activityApplyExtDao.save(applyExt);
			if (num != 1) {
				LogUtil.error(LOGGER, "保存申请扩展信息失败,num:{},applyExt:{}", num, applyExt);
				throw new BusinessException("保存申请扩展信息失败");
			}
		}
	}
	
	
	/**
     * 
     * 获取 收益订单列表
     * @author liyingjie
     * @created 2016年6月25日 下午6:47:58
     * @param request
     * @return
     */
    public PagingResult<ActivityApplyVo> queryApplayList(LanApplayRequest request){
    	if(Check.NuNObj(request)){
    		LogUtil.info(LOGGER,"queryApplayList 参数为空.");
            throw new BusinessException("queryApplayList 参数为空.");
    	}
    	PagingResult<ActivityApplyVo> pageingResult = new PagingResult<ActivityApplyVo>();
        pageingResult = activityApplyDao.getApplayList(request);
	    return pageingResult;
	 }
    
    
    /**
	 * 查询申请详情
	 * @author zl
	 * @param applyFid
	 * @return
	 */
    public ActivityApplyVo getApplyDetailWithBLOBs(String applyFid){
    	if (Check.NuNStr(applyFid)) {
    		LogUtil.info(LOGGER, "getApplyDetailWithBLOBs 参数为空");
			return null;
		}
    	return activityApplyDao.getApplyDetailWithBLOBs(applyFid);
    }

}
