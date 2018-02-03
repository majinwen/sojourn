package com.ziroom.minsu.services.cms.service;

import java.util.List;

import javax.annotation.Resource;

import com.asura.framework.base.exception.BusinessException;
import com.ziroom.minsu.entity.cms.ActivityFreeEntity;
import com.ziroom.minsu.entity.cms.CouponMobileLogEntity;
import com.ziroom.minsu.services.cms.dao.ActivityFreeDao;
import com.ziroom.minsu.services.cms.dto.ActivityRecordRequest;
import com.ziroom.minsu.services.cms.dto.BindActivityRequest;
import com.ziroom.minsu.services.cms.dto.MobileCouponRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.cms.ActivityRecordEntity;
import com.ziroom.minsu.services.cms.dao.ActivityRecordDao;
import com.ziroom.minsu.services.cms.entity.ActRecordVo;

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
@Service("cms.activityRecordServiceImpl")
public class ActivityRecordServiceImpl {

	/**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityRecordServiceImpl.class);
	
    @Resource(name = "cms.activityRecordDao")
    private  ActivityRecordDao  activityRecordDao;

	@Resource(name = "cms.activityFreeDao")
	private ActivityFreeDao activityFreeDao;




	/**
	 * 根据uid获取五周年的免佣金活动
	 * @author 爱抚
	 * @param uid
	 * @return
	 */
	public ActivityFreeEntity getFive(String uid) {

		return activityFreeDao.getFive(uid);
	}

	/**
	 * 根据uid获取免佣金实体
	 * @author afi
	 * @param uid
	 * @param freeType
	 * @return
	 */
	public ActivityFreeEntity getByUidAndType(String uid,int freeType) {

		return activityFreeDao.getByUidAndType(uid,freeType);
	}

	/**
	 * 根据uid获取免佣金实体
	 * @author lisc
	 * @param uid
	 * @return
	 */
	public ActivityFreeEntity getByUid(String uid) {

		return activityFreeDao.getByUid(uid);
	}


	/**
	 * 更新当前的礼品地址
	 * @author afi
	 * @create 2016年10月22日下午7:59:04
	 * @param recordFid
	 * @param name
	 * @param address
	 * @return
	 */
	public int updateAddress(String recordFid,String name,String address){
		return activityRecordDao.updateAddress(recordFid,name,address);
	}



	/**
	 * 强制绑定当前的免佣金的活动
	 * @author afi
	 * @param request
	 * @param recordFid
	 * @return
	 */
	public String forceUserRecord4Free(BindActivityRequest request, String recordFid,ActivityFreeEntity activityFreeEntity ){
		if (Check.NuNStr(recordFid)){
			throw new BusinessException("请求参数为空");
		}
		if (Check.NuNObj(request)){
			throw new BusinessException("请求参数为空");
		}
		if ( Check.NuNStr(request.getGroupSn()) || Check.NuNStr(request.getUid())){
			throw new BusinessException("请求参数为空");
		}
		int rst = activityRecordDao.updateLockUserRecord(recordFid,request.getUid(),request.getMobile());
		if (rst > 0){
			if (!Check.NuNObj(activityFreeEntity)){
				//保存当前的免佣的活动
				activityFreeDao.save(activityFreeEntity);
			}
			return recordFid;
		}else {
			return "";
		}
	}




	/**
	 * 获取但前未领取的数量
	 * @author afi
	 * @param groupSn
	 * @return
	 */
	public Long getNoRecordCountByGroupSn(String groupSn){
		return activityRecordDao.getNoRecordCountByGroupSn(groupSn);
	}


	/**
	 * 获取记录表
	 * @author afi
	 * @param recordFid
	 * @return
	 */
	public ActivityRecordEntity getRecordByFid(String recordFid){
		return activityRecordDao.getRecordByFid(recordFid);
	}

	/**
	 * 通过组随机获取一条活动记录
	 * @author afi
	 * @param groupSn
	 * @return
	 */
	public ActivityRecordEntity getOneRecordByGroupSn(String groupSn){
		return activityRecordDao.selectOneRecordByGroupSn(groupSn);
	}

	/**
	 * 当前用户已经领取的数量
	 * @author afi
	 * @param groupSn
	 * @return
	 */
	public Long getHasRecordCountByGroupSnUid(String groupSn,String uid){
		return activityRecordDao.getHasRecordCountByGroupSnUid(groupSn,uid);
	}

	/**
	 * 当前电话已经领取的数量
	 * @author afi
	 * @param groupSn
	 * @return
	 */
	public Long getHasRecordCountByGroupSnMobile(String groupSn,String mobile){
		return activityRecordDao.getHasRecordCountByGroupSnMobile(groupSn,mobile);
	}


    /**
	 * 
	 * 保存活动礼物领取记录
	 *
	 * @author yd
	 * @created 2016年10月9日 下午2:01:14
	 *
	 * @return
	 */
	public int saveActivityRecord(ActivityRecordEntity activityRecord){
		return activityRecordDao.saveActivityRecord(activityRecord);
	}
	
	/**
	 * 
	 * 批量保存活动 礼物记录
	 *
	 * @author yd
	 * @created 2016年10月9日 下午2:20:46
	 *
	 * @param listAcRes
	 */
	public void bachSaveActivityRecord(List<ActivityRecordEntity> listAcRes){
		activityRecordDao.bachSaveActivityRecord(listAcRes);
	}
	
	/**
	 * 
	 * 根据actSn 删除 当前活动礼物项 （未领取的）
	 *
	 * @author yd
	 * @created 2016年10月11日 上午12:05:09
	 *
	 * @param actSn
	 * @return
	 */
	public int deleteAcRecord(String actSn){
		return activityRecordDao.deleteAcRecord(actSn);
	}
	
	/**
	 * 
	 * 活动记录 分页查询
	 *
	 * @author yd
	 * @created 2016年10月9日 下午2:11:48
	 *
	 * @param activityRecordRequest
	 * @return
	 */
	public PagingResult<ActRecordVo> queryAcRecordInfoByPage(ActivityRecordRequest activityRecordRequest){
		return activityRecordDao.queryAcRecordInfoByPage(activityRecordRequest);
	}
}
