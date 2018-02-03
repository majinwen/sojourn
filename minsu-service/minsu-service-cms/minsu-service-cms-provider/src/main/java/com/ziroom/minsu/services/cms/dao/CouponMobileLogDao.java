package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.CouponMobileLogEntity;
import com.ziroom.minsu.entity.cms.UserCouponEntity;
import com.ziroom.minsu.services.cms.dto.MobileCouponRequest;
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

/**
 * <p>手机号领取优惠券活动</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author afi
 * @since 1.0
 * @version 1.0
 */
@Repository("cms.couponMobileLogDao")
public class CouponMobileLogDao {
	
	/**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(CouponMobileLogDao.class);

    private String SQLID = "cms.couponMobileLogDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 获取当前的领数量
     * @param request
     * @return
     */
    public Long countMobileAc(MobileCouponRequest request){
        if (Check.NuNObj(request)){
            LogUtil.error(logger, "countMobileAc par:{}", JsonEntityTransform.Object2Json(request));
            throw new BusinessException("par is null on  countMobileAc");
        }

        if (Check.NuNStr(request.getMobile()) || Check.NuNStr(request.getActSn())){
            LogUtil.error(logger, "countMobileAc par:{}", JsonEntityTransform.Object2Json(request));
            throw new BusinessException("par is null on  countMobileAc");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("customerMobile",request.getMobile());
        par.put("actSn",request.getActSn());
        return mybatisDaoContext.count(SQLID + "countMobileAc", par);
    }

    /**
     * 校验当前的未领取数量
     * @author afi
     * @return
     */
    public Long getNoExchangeCountByGroupSn(String groupSn){
        if (Check.NuNStr(groupSn)){
            return 0L;
        }
        Map<String,Object> par = new HashMap<>();
        par.put("groupSn",groupSn);
        return mybatisDaoContext.count(SQLID + "getNoExchangeCountByGroupSn", par);
    }


    /**
     * 获取当前的领数量
     * @param request
     * @return
     */
    public Long countMobileGroup(MobileCouponRequest request){
        if (Check.NuNObj(request)){
            LogUtil.error(logger, "countMobileGroup par:{}", JsonEntityTransform.Object2Json(request));
            throw new BusinessException("par is null on  countMobileGroup");
        }

        if (Check.NuNStr(request.getMobile()) || Check.NuNStr(request.getGroupSn())){
            LogUtil.error(logger, "countMobileGroup par:{}", JsonEntityTransform.Object2Json(request));
            throw new BusinessException("par is null on  countMobileAc");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("customerMobile",request.getMobile());
        par.put("groupSn",request.getGroupSn());
        return mybatisDaoContext.count(SQLID + "countMobileGroup", par);
    }

    /**
     * 获取
     * @author jixd
     * @created 2017年03月30日 15:28:23
     * @param
     * @return
     */
    public Long countMobileGroupSns(MobileCouponRequest request){
        Map<String,Object> par = new HashMap<>();
        par.put("customerMobile",request.getMobile());
        par.put("groupSns",request.getGroupSns());
        return mybatisDaoContext.count(SQLID + "countMobileGroupSns", par);
    }

    /**
     * 获取未绑定uid的优惠券
     * @author lisc
     * @param customerMobile
     * @return
     */
    public List<CouponMobileLogEntity> getNotBindCouponMobile(String customerMobile){
        if (Check.NuNObj(customerMobile)){
            LogUtil.error(logger, "getNotBindCouponMobile参数为空:{}", customerMobile);
            throw new BusinessException("getNotBindCouponMobile参数为空");
        }
        return mybatisDaoContext.findAll(SQLID + "getNotBindCouponMobile", CouponMobileLogEntity.class, customerMobile);
    }



    /**
     * 保存通过手机号领取优惠券的信息
     * @author afi
     * @param couponMobileLogEntity
     * @return
     */
    public int saveMobileCoupon(CouponMobileLogEntity couponMobileLogEntity){
        if(Check.NuNObj(couponMobileLogEntity)){
            LogUtil.error(logger,"参数为空");
            throw new BusinessException("参数为空");
        }
        if (Check.NuNStr(couponMobileLogEntity.getFid())){
            couponMobileLogEntity.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID + "saveMobileCoupon", couponMobileLogEntity);
    }

    /**
     * 根据fid查询记录
     * @param fid
     * @return
     */
    public CouponMobileLogEntity getCouponMobileLogByFid(String fid){
        return mybatisDaoContext.findOne(SQLID + "getCouponMobileLogByFid", CouponMobileLogEntity.class,fid);
    }

    /**
     * 根据组号和手机查询
     * @author jixd
     * @created 2017年03月02日 14:29:19
     * @param
     * @return
     */
    public List<String> listActSnByGroup(MobileCouponRequest request){
        Map<String,Object> par = new HashMap<>();
        par.put("customerMobile",request.getMobile());
        par.put("groupSn",request.getGroupSn());
        return mybatisDaoContext.findAll(SQLID + "listActSnByGroup",String.class,par);
    }

}
