package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActCouponEntity;
import com.ziroom.minsu.entity.cms.ActCouponUserEntity;
import com.ziroom.minsu.services.cms.dto.ActCouponRequest;
import com.ziroom.minsu.services.cms.dto.BindCouponRequest;
import com.ziroom.minsu.services.cms.dto.CheckCouponRequest;
import com.ziroom.minsu.services.cms.dto.OutCouponRequest;
import com.ziroom.minsu.services.cms.entity.ActCouponInfoUserVo;
import com.ziroom.minsu.services.cms.entity.CouponUserUidVo;
import com.ziroom.minsu.valenum.order.CouponStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository("cms.actCouponDao")
public class ActCouponDao {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(ActCouponDao.class);

    private String SQLID = "cms.actCouponDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;







    /**
     * 获取当前优惠券列表
     * @author afi
     * @param actCouponRequest
     * @return
     */
    public PagingResult<ActCouponUserEntity> getCouponFullList(ActCouponRequest actCouponRequest){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(actCouponRequest.getLimit());
        pageBounds.setPage(actCouponRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID + "getCouponFullList", ActCouponUserEntity.class, actCouponRequest, pageBounds);


    }


    /**
     * 通过优惠券获取优惠券信息
     * @author afi
     * @param couponSn
     * @return
     */
    public ActCouponEntity getCouponBySn(String couponSn){
       if(Check.NuNStr(couponSn)) {
            LogUtil.error(logger, "getCouponBySn couponSn:{}", couponSn);
            throw new BusinessException("couponSn is null on  getCouponBySn");
        }
        return mybatisDaoContext.findOne(SQLID + "selectBySn", ActCouponEntity.class, couponSn);
    }


    /**
     * 通过活动编号获取优惠券列表
     * 包括已经删除的优惠券信息
     * @author afi
     * @param request
     * @return
     */
    public PagingResult<ActCouponEntity> getCouponListAllByActSn(ActCouponRequest request){
        if(Check.NuNObj(request)) {
            LogUtil.error(logger, "getCouponListAllByActSn request:{}", request);
            throw new BusinessException("request is null on  getCouponListAllByActSn");
        }
        if(Check.NuNStr(request.getActSn())) {
            LogUtil.error(logger, "getCouponListAllByActSn actSn:{}", request.getActSn());
            throw new BusinessException("actSn is null on  getCouponListAllByActSn");
        }
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(request.getLimit());
        pageBounds.setPage(request.getPage());
        return mybatisDaoContext.findForPage(SQLID + "getCouponListAllByActSn", ActCouponEntity.class, request, pageBounds);
    }



    /**
     * 通过活动编号获取优惠券列表
     * @author afi
     * @param request
     * @return
     */
    public PagingResult<ActCouponEntity> getCouponListByActSn(ActCouponRequest request){
    	if(Check.NuNObj(request)) {
            LogUtil.error(logger, "getCouponListByActSn request:{}", request);
            throw new BusinessException("request is null on  getCouponListByActSn");
        }
        if(Check.NuNStr(request.getActSn())) {
            LogUtil.error(logger, "getCouponListByActSn actSn:{}", request.getActSn());
            throw new BusinessException("actSn is null on  getCouponListByActSn");
        }
        PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(request.getLimit());
		pageBounds.setPage(request.getPage());
        return mybatisDaoContext.findForPage(SQLID + "getCouponListByActSn", ActCouponEntity.class, request, pageBounds);
    }

    /**
     * 保存优惠券信息
     * @author afi
     * @param actCouponEntity
     * @return
     */
    public int saveCoupon(ActCouponEntity actCouponEntity){
        if(Check.NuNObj(actCouponEntity)){
            LogUtil.error(logger,"优惠券的参数为空");
            throw new BusinessException("优惠券的参数为空");
        }
        if (Check.NuNStr(actCouponEntity.getCouponSn())){
             LogUtil.error(logger,"优惠券的编号为空 couponSn：{}",actCouponEntity.getCouponSn());
            throw new BusinessException("优惠券的编号为空");
        }
        //直接保存优惠券信息
        return mybatisDaoContext.save(SQLID + "saveCoupon", actCouponEntity);
    }

    /**
     * 通过优惠券号获取 优惠券信息、绑定信息
     * @author lishaochuan
     * @create 2016年6月14日下午6:02:47
     * @param couponSn
     * @return
     */
    public ActCouponUserEntity getActCouponUserVoByCouponSn(String couponSn){
    	if(Check.NuNStr(couponSn)){
            LogUtil.error(logger,"优惠券号为空");
            throw new BusinessException("优惠券号为空");
        }
    	return mybatisDaoContext.findOne(SQLID + "getActCouponUserVoByCouponSn", ActCouponUserEntity.class, couponSn);
    }
    
    /**
     * 通过优惠券号获取 活动信息 、优惠券信息
     * @author lishaochuan
     * @create 2016年6月15日下午2:13:10
     * @param couponSn
     * @return
     */
    public ActCouponInfoUserVo getActCouponInfoVoByCouponSn(String couponSn){
    	if(Check.NuNStr(couponSn)){
            LogUtil.error(logger,"优惠券号为空");
            throw new BusinessException("优惠券号为空");
        }
    	return mybatisDaoContext.findOne(SQLID + "getActCouponInfoVoByCouponSn", ActCouponInfoUserVo.class, couponSn);
    }
    
    /**
     * 获取用户当前活动下的优惠券信息
     * @author lishaochuan
     * @create 2016年6月15日下午4:04:16
     * @param actSn
     * @param uid
     * @return
     */
    public List<ActCouponUserEntity> getCouponListByActUid(String actSn, String uid){
    	if(Check.NuNStr(actSn)){
            LogUtil.error(logger,"活动号为空");
            throw new BusinessException("活动号为空");
        }
    	if(Check.NuNStr(uid)){
            LogUtil.error(logger,"用户uid为空");
            throw new BusinessException("用户uid为空");
        }
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("actSn", actSn);
		map.put("uid", uid);
		return mybatisDaoContext.findAllByMaster(SQLID + "getCouponListByActUid", ActCouponUserEntity.class, map);
    }



    /**
     * 通过活动编号获取优惠券列表
     * @author afi
     * @param request
     * @return
     */
    public List<ActCouponUserEntity> getCouponListCheckByUid(CheckCouponRequest request){
        if(Check.NuNObj(request)) {
            LogUtil.error(logger, "getCouponListCheckByUid request:{}", request);
            throw new BusinessException("request is null on  getCouponListCheckByUid");
        }
        return mybatisDaoContext.findAll(SQLID + "getCouponListCheckByUid", ActCouponUserEntity.class, request);
    }

    /**
     * 获取当前的免天券
     * @author afi
     * @param request
     * @return
     */
    public List<ActCouponUserEntity> getCouponListCheckByUidDefault(CheckCouponRequest request){
        if(Check.NuNObj(request)) {
            LogUtil.error(logger, "getCouponListCheckByUidDefault request:{}", request);
            throw new BusinessException("request is null on  getCouponListCheckByUidDefault");
        }
        return mybatisDaoContext.findAll(SQLID + "getCouponListCheckByUidDefault", ActCouponUserEntity.class, request);
    }


    /**
     * 获取当前用户下的优惠券信息
     * @author afi
     * @param request
     * @return
     */
    public PagingResult<ActCouponUserEntity> getCouponPageCheckByUid(CheckCouponRequest request){
        if(Check.NuNObj(request)) {
            LogUtil.error(logger, "getCouponPageCheckByUid request:{}", request);
            throw new BusinessException("request is null on  getCouponPageCheckByUid");
        }
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(request.getLimit());
        pageBounds.setPage(request.getPage());
        return mybatisDaoContext.findForPage(SQLID + "getCouponPageCheckByUid", ActCouponUserEntity.class, request, pageBounds);
    }



    /**
     * 获取当前用户下的优惠券信息
     * @author afi
     * @param request
     * @return
     */
    public PagingResult<ActCouponUserEntity> getCouponListByUid(OutCouponRequest request){
        if(Check.NuNObj(request)) {
            LogUtil.error(logger, "getCouponListByUid request:{}", request);
            throw new BusinessException("request is null on  getCouponListByUid");
        }
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(request.getLimit());
        pageBounds.setPage(request.getPage());
        String sql = SQLID;
        /** 优惠券状态1-可用；2-过期；3-全部 */
        if ("3".equals(request.getStatus())){
            //全部
            sql += "getCouponListAllByUid";
        }else if ("2".equals(request.getStatus())){
            //全部
            sql += "getCouponListTimeOutByUid";
        }else if ("1".equals(request.getStatus())){
            //全部
            sql += "getCouponListOkByUid";
        }else {
            return new PagingResult<ActCouponUserEntity>(0L,new ArrayList<ActCouponUserEntity>());
        }

        return mybatisDaoContext.findForPage(sql, ActCouponUserEntity.class, request, pageBounds);
    }



    
    /**
     * 根据优惠券号修改优惠券信息
     * @author lishaochuan
     * @create 2016年6月15日下午5:30:27
     * @param actCouponEntity
     * @return
     */
    public int updateCoupon(ActCouponEntity actCouponEntity){
    	if(Check.NuNObj(actCouponEntity)){
            LogUtil.error(logger,"参数为空");
            throw new BusinessException("参数为空");
        }
    	if(Check.NuNStr(actCouponEntity.getCouponSn())){
            LogUtil.error(logger,"优惠券号为空");
            throw new BusinessException("优惠券号为空");
        }
    	return mybatisDaoContext.update(SQLID + "updateCoupon", actCouponEntity);
    }


    /**
     * 更新当前状态为已经发送
     * @param actCouponSn
     * @return
     */
    public int updateCouponMobile(String actCouponSn){
        if(Check.NuNObj(actCouponSn)){
            LogUtil.error(logger,"参数为空");
            throw new BusinessException("参数为空");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("couponSn",actCouponSn);
        return mybatisDaoContext.update(SQLID + "updateCouponMobile", par);
    }


    /**
     * 更新优惠券状态，已发送→已领取
     * @author lisc
     * @param couponSn
     * @return
     */
    public int updateCouponMobileStatus(String couponSn){
        if(Check.NuNObj(couponSn)){
            LogUtil.error(logger,"参数为空");
            throw new BusinessException("参数为空");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("couponSn",couponSn);
        par.put("couponStatus",CouponStatusEnum.GET.getCode());
        par.put("oldCouponStatus",CouponStatusEnum.SEND.getCode());
        return mybatisDaoContext.update(SQLID + "updateCouponMobileStatus", par);
    }

    
    /**
     * 获取已过期未使用的优惠券count
     * @author lishaochuan
     * @create 2016年6月16日下午7:42:37
     * @return
     */
    public Long getExpireCount(){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Integer> couponStatusList = new ArrayList<Integer>();
		couponStatusList.add(CouponStatusEnum.WAIT.getCode());
		couponStatusList.add(CouponStatusEnum.GET.getCode());
		map.put("couponStatusList", couponStatusList);
		return mybatisDaoContext.findOne(SQLID + "getExpireCount", Long.class, map);
    }
	
    /**
     * 修改优惠券过期
     * @author lishaochuan
     * @create 2016年6月16日下午7:42:46
     * @param limit
     * @return
     */
	public int updateExpireList(int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("couponStatus", CouponStatusEnum.OVER_TIME.getCode());
		List<Integer> couponStatusList = new ArrayList<Integer>();
		couponStatusList.add(CouponStatusEnum.WAIT.getCode());
		couponStatusList.add(CouponStatusEnum.GET.getCode());
		map.put("couponStatusList", couponStatusList);
		map.put("limit", limit);
		return mybatisDaoContext.update(SQLID + "updateExpireList", map);
	}

    /**
     * 检验当前用户是否参加了当前组
     * @author afi
     * @create 2016年10月21日下午7:42:37
     * @return
     */
    public Long getCountUserGroupUidNum(BindCouponRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", request.getUid());
        map.put("groupSn", request.getGroupSn());
        return mybatisDaoContext.findOne(SQLID + "getCountUserGroupUidNum", Long.class, map);
    }


	
	/**
     * 校验用户是否已经领取活动的优惠券
     * @author liyingjie
     * @create 2016年7月21日下午7:42:37
     * @return
     */
    public Long getCountUserActCouNum(BindCouponRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", request.getUid());
		map.put("actSn", request.getActSn());
		return mybatisDaoContext.findOne(SQLID + "countUserActCou", Long.class, map);
    }



    /**
     * 通过组获取当前的优惠券
     * @author afi
     * @param groupSn
     * @return
     */
    public ActCouponEntity getOneActCouByGroupSn(String groupSn){
        if(Check.NuNStr(groupSn)) {
            LogUtil.error(logger, "getOneActCouByGroupSn actSn:{}", groupSn);
            throw new BusinessException("actSn is null on  getOneActCouByGroupSn");
        }
        return mybatisDaoContext.findOne(SQLID + "getOneActCouByGroupSn", ActCouponEntity.class, groupSn);
    }

    /**
     * 通过组和活动列表找到一个优惠券信息
     * @author jixd
     * @created 2017年03月02日 16:28:25
     * @param
     * @return
     */
    public ActCouponEntity getActCouByGroupSnAndActList(String groupSn,List<String> actSns){
        Map<String,Object> map = new HashMap<>();
        map.put("groupSn",groupSn);
        map.put("actSns",actSns);
        return mybatisDaoContext.findOne(SQLID + "getActCouByGroupSnAndActList",ActCouponEntity.class,map);
    }

    
    /**
     * 通过优惠券获取优惠券信息
     * @author afi
     * @param actSn
     * @return
     */
    public ActCouponEntity getCouponByActSn(String actSn){
       if(Check.NuNStr(actSn)) {
            LogUtil.error(logger, "getCouponByActSn actSn:{}", actSn);
            throw new BusinessException("actSn is null on  getCouponByActSn");
        }
        return mybatisDaoContext.findOne(SQLID + "getOneActCou", ActCouponEntity.class, actSn);
    }


    /**
     * 获取当前可用的优惠券信息
     * @author afi
     * @param actSn
     * @return
     */
    public ActCouponEntity getAvailableCouponByActSn(String actSn){
        if(Check.NuNStr(actSn)) {
            LogUtil.error(logger, "getAvailableCouponByActSn actSn:{}", actSn);
            throw new BusinessException("actSn is null on  getAvailableCouponByActSn");
        }
        return mybatisDaoContext.findOne(SQLID + "getAvailableCouponByActSn", ActCouponEntity.class, actSn);
    }

    /**
     * 获取当前未领取的数量
     * @author afi
     * @create
     * @return
     */
    public Long getNoExchangeCountByGroupSn(String groupSn){
        if (Check.NuNStr(groupSn)){
            return 0L;
        }
        return mybatisDaoContext.findOne(SQLID + "getNoExchangeCountByGroupSn", Long.class, groupSn);
    }

    /**
     * 查询可用优惠券数量 根据活动号
     * @author jixd
     * @created 2017年02月17日 16:30:16
     * @param
     * @return
     */
    public Long countAvaliableCouponByActSn(String actSn){
        if (Check.NuNStr(actSn)){
            return 0L;
        }
        return mybatisDaoContext.findOne(SQLID + "countAvaliableCouponByActSn", Long.class, actSn);
    }

    /**
     * 通过组号获取已经领取的优惠券列表
     * @author jixd
     * @created 2017年06月16日 15:36:55
     * @param
     * @return
     */
    public PagingResult<CouponUserUidVo> getOneMonthExpireCouponUidByGroupSN(ActCouponRequest request){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(request.getLimit());
        pageBounds.setPage(request.getPage());
        return mybatisDaoContext.findForPage(SQLID + "getOneMonthExpireCouponUidByGroupSN", CouponUserUidVo.class, request, pageBounds);
    }

    /**
     * 通过优惠券获取优惠券信息
     * @author yanb
     * @param couponSn
     * @return
     */
    public ActCouponEntity getActSnStatusByCouponSn(String couponSn){
        if(Check.NuNStr(couponSn)) {
            LogUtil.error(logger, "getCouponBySn couponSn:{}", couponSn);
            throw new BusinessException("couponSn is null on  getCouponBySn");
        }
        return mybatisDaoContext.findOne(SQLID + "selectActSnStatusByCouponSn", ActCouponEntity.class, couponSn);
    }


}
