package com.ziroom.minsu.services.cms.service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.*;
import com.ziroom.minsu.services.cms.dao.*;
import com.ziroom.minsu.services.cms.dto.ActivityGiftInfoRequest;
import com.ziroom.minsu.services.cms.dto.ActivityInfoRequest;
import com.ziroom.minsu.services.cms.dto.InviteStateUidRequest;
import com.ziroom.minsu.services.cms.dto.ZrpActRequest;
import com.ziroom.minsu.valenum.cms.ActTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>活动信息service</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月23日
 * @since 1.0
 * @version 1.0
 */
@Service("cms.activityServiceImpl")
public class ActivityServiceImpl {

	/**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityServiceImpl.class);
	
    @Resource(name = "cms.activityDao")
    private ActivityDao activityDao;


    @Resource(name = "cms.activityExtCouponDao")
    private ActivityExtCouponDao activityExtCouponDao;


    @Resource(name = "cms.activityCityDao")
    private ActivityCityDao activityCityDao;

    @Resource(name = "cms.activityHouseDao")
    private ActivityHouseDao activityHouseDao;

    @Resource(name = "cms.groupUserRelDao")
    private GroupUserRelDao groupUserRelDao;

    @Resource(name = "cms.groupHouseRelDao")
    private GroupHouseRelDao groupHouseRelDao;
    
    @Resource(name = "cms.activityGiftItemServiceImpl")
    private ActivityGiftItemServiceImpl activityGiftItemServiceImpl;

    @Resource(name="cms.inviteDao")
    private InviteDao inviteDao;



    /**
     * 获取当前的最新的种子房东免佣金的逻辑
     * @author afi
     * @return
     */
    public ActivityEntity getSeedActivityLast() {
        return activityDao.getSeedActivityLast();
    }


    /**
     * 根据actSn查询活动信息
     * @author lishaochuan
     * @create 2016年6月23日下午6:11:08
     * @param actSn
     * @return
     */
    public ActivityEntity selectByActSn(String actSn) {
		return activityDao.selectByActSn(actSn);
	}
    
    /**
     * 根据groupSn查询活动信息
     * @author lunan
     * @create 2016年10月10日
     * @param groupSn
     * @return
     */
    public ActivityEntity selectByGroupSn(String groupSn){
    	return activityDao.selectByGroupSn(groupSn);
    }

    /**
     * 根据活动组号查询所有有效的活动
     * @author jixd
     * @created 2017年03月02日 11:25:18
     * @param
     * @return
     */
    public List<ActivityEntity> listActivityByGroupSn(String groupSn){
        return activityDao.listActivityByGroupSn(groupSn);
    }

    /**
     * 当前活动开通的城市
     * @author lishaochuan
     * @create 2016年6月23日下午8:08:26
     * @return
     */
    public List<ActivityCityEntity> getActivityCitiesByActSn(String actSn) {
        if (Check.NuNStr(actSn)) {
            return null;
        }
        return activityCityDao.getActivityCitiesByActSn(actSn);
    }


    /**
     * 获取返现活动列表
     * @author afi
     * @param cachType
     * @return
     */
    public List<ActivityVo> getCashbackList(Integer cachType){
        if (!Check.NuNObj(cachType) && cachType == YesOrNoEnum.NO.getCode()){
            return activityDao.getCashbackListAll();
        }else {
            return activityDao.getCashbackListOk();
        }
    }


    /**
     * 查询有效的活动列表
     * @author lishaochuan
     * @create 2016年6月23日下午8:09:42
     * @return
     */
    public List<ActivityVo> getUnderwayActivityList(){
        List<ActivityVo> activityVoList = activityDao.getUnderwayActivityList();
        if (!Check.NuNCollection(activityVoList)){
            for (ActivityVo activityVo : activityVoList) {
                activityVo.setCityList(activityCityDao.getActivityCitiesByActSn(activityVo.getActSn()));
            }
        }
        return activityVoList;
    }
    
    /**
     * 分页查询活动信息
     * @author lishaochuan
     * @create 2016年6月23日下午8:10:43
     * @param request
     * @return
     */
    public PagingResult<ActivityVo> getActivityVoListByCondiction(ActivityInfoRequest request){
        if (Check.NuNObj(request)){
            return new PagingResult<>();
        }
        PagingResult<ActivityVo> pagingResult = null;
        if (Check.NuNStr(request.getCityCode())){
            pagingResult = activityDao.getActivityVoListByCondiction(request);
        }else {
            pagingResult = activityDao.getActivityVoListByCondictionByCity(request);
        }
        if (!Check.NuNCollection(pagingResult.getRows())){
            for (ActivityVo activityVo : pagingResult.getRows()) {
                activityVo.setCityList(activityCityDao.getActivityCitiesByActSn(activityVo.getActSn()));
            }
        }
        return pagingResult;
    }
    
    /**
     * 保存活动信息
     * @author afi
     * @create 2016年6月23日下午3:15:08
     * @param activityEntity
     * @return
     */
    public int saveActivityInfo(ActivityEntity activityEntity, String cityCode){
    	if(Check.NuNStr(activityEntity.getActSn())){
    		activityEntity.setActSn(UUIDGenerator.hexUUID());
    	}
        if(Check.NuNStr(activityEntity.getActSn())){
            LogUtil.error(LOGGER, "actSn参数为空");
            throw new BusinessException("actSn参数为空");
        }
        //城市code
        if (Check.NuNStr(cityCode)){
            LogUtil.error(LOGGER, "cityCode参数为空");
            throw new BusinessException("cityCode参数为空");
        }
        if (cityCode.equals("0")){
            //全部城市的保存
            ActivityCityEntity activityCityEntity = new ActivityCityEntity();
            activityCityEntity.setCityCode(cityCode);
            activityCityEntity.setActSn(activityEntity.getActSn());
            activityCityDao.saveActivityCity(activityCityEntity);
        }else {
            //
            String[] strarray = cityCode.split(",");
            for (int i = 0; i < strarray.length; i++){
                String ele = strarray[i];
                if (ele.trim().equals("")){
                    continue;
                }
                ActivityCityEntity activityCityEntity = new ActivityCityEntity();
                activityCityEntity.setCityCode(ele);
                activityCityEntity.setActSn(activityEntity.getActSn());
                activityCityDao.saveActivityCity(activityCityEntity);
            }
        }
    	return activityDao.saveActivity(activityEntity);
    }


    /**
     * 保存优惠券活动信息
     * @author afi
     * @create 2016年6月23日下午3:15:08
     * @param activityEntity
     * @return
     */
    public int updateActivityInfo(ActivityEntity activityEntity, String cityCode){
        return updateAcInfo(activityEntity, cityCode, null);
    }
    

    /**
     * 更新优惠券活动信息
     * @author afi
     * @create 2016年6月23日下午3:15:08
     * @param
     * @return
     */
    public int updateGiftActivityInfo(ActivityGiftInfoRequest activityGiftInfoRe, String cityCode) {
        ActivityEntity ac = activityGiftInfoRe.getAc();
        return updateAcInfo(ac, cityCode, activityGiftInfoRe.getListAcGiftItems());
    }
    
    /**
     * 
     * 更新活动信息
     * 
     * 说明： 修改活动相时候 删除之前保存的 活动  从新添加 礼物
     *
     * @author yd
     * @created 2016年10月10日 下午9:33:57
     *
     * @param activityEntity
     * @param cityCode
     * @return
     */
    private int updateAcInfo(ActivityEntity activityEntity, String cityCode,List<ActivityGiftItemEntity> listAcGiftItems){
    	 if(Check.NuNStr(activityEntity.getActSn())){
             activityEntity.setActSn(UUIDGenerator.hexUUID());
         }
         if(Check.NuNStr(activityEntity.getActSn())){
             LogUtil.error(LOGGER, "actSn参数为空");
             throw new BusinessException("actSn参数为空");
         }
         //城市code
         if (Check.NuNStr(cityCode)){
             LogUtil.error(LOGGER, "cityCode参数为空");
             throw new BusinessException("cityCode参数为空");
         }
         //把原来的删掉
         activityCityDao.deleteByActSn(activityEntity.getActSn());
         if (cityCode.equals("0")){
             //全部城市的保存
             ActivityCityEntity activityCityEntity = new ActivityCityEntity();
             activityCityEntity.setCityCode(cityCode);
             activityCityEntity.setActSn(activityEntity.getActSn());
             activityCityDao.saveActivityCity(activityCityEntity);
         }else {
             //
             String[] strarray = cityCode.split(",");
             for (int i = 0; i < strarray.length; i++){
                 String ele = strarray[i];
                 if (ele.trim().equals("")){
                     continue;
                 }
                 ActivityCityEntity activityCityEntity = new ActivityCityEntity();
                 activityCityEntity.setCityCode(ele);
                 activityCityEntity.setActSn(activityEntity.getActSn());
                 activityCityDao.saveActivityCity(activityCityEntity);
             }
         }
         int actType = activityEntity.getActType();
         LogUtil.info(LOGGER, "根据活动编码actSn={},删除当前活动礼品相，新增活动相集合listAcGiftItems={}", activityEntity.getActSn(),listAcGiftItems);
     	 int i =  this.activityGiftItemServiceImpl.updateAcItemByActSn(activityEntity.getActSn());
         if(actType == ActTypeEnum.GIFT_AC.getCode()){
        	this.activityGiftItemServiceImpl.bachSaveGiftItem(listAcGiftItems);
         }
         return activityDao.updateCouponActBySn(activityEntity);
    }
    
    /**
     * 修改活动信息
     * @author lishaochuan
     * @create 2016年6月23日下午3:49:21
     * @param activityEntity
     * @return
     */
    public int updateByActSn(ActivityEntity activityEntity){
    	return activityDao.updateByActSn(activityEntity);
    }
    
    /**
     * 启动活动
     * @author lishaochuan
     * @create 2016年6月23日下午6:01:31
     * @param actSn
     * @return
     */
    public int enableActivity(String actSn){
    	return activityDao.enableActivity(actSn);
    }



    /**
     * 终止活动
     * @author afi
     * @create 2016年6月23日下午6:01:31
     * @param actSn
     * @return
     */
    public int endActivity(String actSn){
        return activityDao.endActivity(actSn);
    }

    /**
     * 修改当前活动的优惠券状态
     * @author afi
     * @param actSn
     * @return
     */
    public int updateIsCouponStatus(String actSn,int couponStatus){
        int changeSum = activityDao.updateIsCouponStatus(actSn, couponStatus);
        if (changeSum != 1){
            LogUtil.error(LOGGER,"更新当前的活动状态失败：actSn{},couponStatus{}",actSn,couponStatus);
            throw new BusinessException("异常的更新活动的条数");
        }
        return changeSum;
    }


    /**
     * 修改当前活动的优惠券状态
     * @author afi
     * @param actSn
     * @return
     */
    public void updateAcCouponIng(String actSn,DataTransferObject dto){
        int changeSum = activityDao.updateAcCouponIng(actSn);
        if (changeSum != 1){
           dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("请校验当前的活动状态");
        }
    }

    /**
     * 修改当前活动的优惠券状态
     * @author afi
     * @param activityVo
     * @return
     */
    public void updateActCoupon(ActivityFullEntity activityVo, String cityCode){
        //优惠券活动基本信息
        ActivityEntity activity = new ActivityEntity();
        BeanUtils.copyProperties(activityVo, activity);
        //优惠券活动 优惠券 基本信息
        ActivityExtCouponEntity activityExt = new ActivityExtCouponEntity();
        BeanUtils.copyProperties(activityVo, activityExt);
        this.updateActivityInfo(activity, cityCode);
        activityExtCouponDao.updateActivityExtCoupon(activityExt);
    }


    /**
     * 修改当前活动的优惠券状态
     * @author afi
     * @param activityVo
     * @return
     */
    public void addActCoupon(ActivityFullEntity activityVo, String cityCode,String houseSns){
        //优惠券活动基本信息
        ActivityEntity activity = new ActivityEntity();
        BeanUtils.copyProperties(activityVo, activity);
        //优惠券活动 优惠券 基本信息
        ActivityExtCouponEntity activityExt = new ActivityExtCouponEntity();
        BeanUtils.copyProperties(activityVo, activityExt);
        this.saveActivityInfo(activity, cityCode);
        this.saveActivityHouse(activity,houseSns);
        activityExtCouponDao.insertActivityExtCoupon(activityExt);
    }

    /**
     * 保存活动限制房源
     * @author jixd
     * @created 2016年11月18日 14:08:48
     * @param
     * @return
     */
    private int saveActivityHouse(ActivityEntity activity, String houseSns) {
        if (activity.getIsLimitHouse() == YesOrNoEnum.NO.getCode()){
            return 0;
        }
        if (Check.NuNStr(houseSns)){
            return 0;
        }
        String[] houseSnArr =  houseSns.split(",");
        int count = 0;
        for (String houseSn : houseSnArr){
            if (Check.NuNStr(houseSn.trim()))continue;
            ActivityHouseEntity activityHouseEntity = new ActivityHouseEntity();
            activityHouseEntity.setActSn(activity.getActSn());
            activityHouseEntity.setHouseSn(houseSn);
            count += activityHouseDao.saveActivityHouse(activityHouseEntity);
        }
        return count;
    }

    /**
     * @Description: 获取首单立减活动详情
     * @Author:lusp
     * @Date: 2017/6/5 11:30
     * @Params:
     */
    public ActivityEntity getSDLJActivityInfo() {
        return activityDao.getSDLJActivityInfo();
    }

    /**
     * 获取符合条件的活动  自如寓
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月16日 15:28:59
     */
    public List<ActivityEntity> listActConditionForZrp(ZrpActRequest zrpActRequest) {
        List<ActivityEntity> baseActList = activityDao.listSuitActForZrp(zrpActRequest);
        if (Check.NuNCollection(baseActList)) {
            return null;
        }
        //过滤不限制的条件
        List<ActivityEntity> unLimitUserList = baseActList.stream().filter(a -> ("0".equals(a.getGroupUserFid()))).collect(Collectors.toList());
        if (!Check.NuNCollection(unLimitUserList)) {
            //去除不限制部分
            baseActList.removeAll(unLimitUserList);
        }
        //校验是否在用户组中
        if (!Check.NuNCollection(baseActList)) {
            List<String> groupUserFids = baseActList.stream().map(ActivityEntity::getGroupUserFid).collect(Collectors.toList());
            List<GroupUserRelEntity> groupUserReLList = groupUserRelDao.listUserRelFilter(groupUserFids, zrpActRequest.getUid());
            if (!Check.NuNCollection(groupUserReLList)) {
                Map<String, List<ActivityEntity>> groupUserMap = baseActList.stream().collect(Collectors.groupingBy(ActivityEntity::getGroupUserFid));
                baseActList.clear();
                for (GroupUserRelEntity groupUserRelEntity : groupUserReLList) {
                    List<ActivityEntity> activityEntities = groupUserMap.get(groupUserRelEntity.getGroupFid());
                    if (!Check.NuNCollection(activityEntities)) {
                        baseActList.addAll(activityEntities);
                    }
                }
            } else {
                baseActList.clear();
            }
        }
        baseActList.addAll(unLimitUserList);

        //校验是否在房源组中
        List<ActivityEntity> unLimitHouseList = baseActList.stream().filter(a -> ("0".equals(a.getGroupHouseFid()))).collect(Collectors.toList());
        if (!Check.NuNCollection(unLimitHouseList)) {
            baseActList.removeAll(unLimitHouseList);
        }
        if (!Check.NuNCollection(baseActList)) {
            List<String> groupHouseFids = baseActList.stream().map(ActivityEntity::getGroupHouseFid).collect(Collectors.toList());
            List<GroupHouseRelEntity> groupHouseRelList = groupHouseRelDao.listHouseRelFilter(groupHouseFids, zrpActRequest);
            if (!Check.NuNCollection(groupHouseRelList)) {
                Map<String, List<ActivityEntity>> groupHouseMap = baseActList.stream().collect(Collectors.groupingBy(ActivityEntity::getGroupHouseFid));
                baseActList.clear();
                for (GroupHouseRelEntity groupHouseRelEntity : groupHouseRelList) {
                    List<ActivityEntity> activityEntities = groupHouseMap.get(groupHouseRelEntity.getGroupFid());
                    if (!Check.NuNCollection(activityEntities)) {
                        baseActList.addAll(activityEntities);
                    }
                }
            } else {
                baseActList.clear();
            }
        }

        baseActList.addAll(unLimitHouseList);
        return baseActList;
    }

    /**
     * 根据uid查询用户是否已经参加过邀请活动
     * 必须同时传入uid和inviteSource
     * @created 2017年12月06日 18:35:01
     * @author yanb
     * @param  * @param uid
     * @return java.lang.String
     */
    public Integer checkUserInviteStateByUid(InviteStateUidRequest inviteStateUidRequest) {
        return inviteDao.isInvitedByUid(inviteStateUidRequest);
    }

    /**
     * 查询用户组对应得活动列表
     *
     * @param
     * @return
     * @author jixd
     * @created 2018年01月26日 14:31:51
     */
    public List<ActivityEntity> listUserGroupActForZrp(String userGroupFid) {
        return activityDao.listUserGroupActForZrp(userGroupFid);
    }
}
