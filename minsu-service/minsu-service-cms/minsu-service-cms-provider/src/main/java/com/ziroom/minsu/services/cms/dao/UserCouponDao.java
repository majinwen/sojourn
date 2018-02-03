package com.ziroom.minsu.services.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.UserCouponEntity;
import com.ziroom.minsu.services.cms.dto.MobileCouponRequest;

/**
 * <p>优惠券绑定dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月15日
 * @since 1.0
 * @version 1.0
 */
@Repository("cms.userCouponDao")
public class UserCouponDao {
	
	/**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(UserCouponDao.class);

    private String SQLID = "cms.userCouponDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 保存优惠券绑定关系信息
     * @author lishaochuan
     * @create 2016年6月15日下午5:38:38
     * @param userCouponEntity
     * @return
     */
    public int saveUserCoupon(UserCouponEntity userCouponEntity){
    	if(Check.NuNObj(userCouponEntity)){
            LogUtil.error(logger,"参数为空");
            throw new BusinessException("参数为空");
        }
        return mybatisDaoContext.save(SQLID + "saveUserCoupon", userCouponEntity);
    }


    /**
     * 保存优惠券绑定关系信息（存在则忽略）
     * @author lisc
     * @param userCouponEntity
     * @return
     */
    public int saveUserCouponIgnore(UserCouponEntity userCouponEntity){
    	if(Check.NuNObj(userCouponEntity)){
            LogUtil.error(logger,"参数为空");
            throw new BusinessException("参数为空");
        }
        return mybatisDaoContext.save(SQLID + "saveUserCouponIgnore", userCouponEntity);
    }
	
    /**
     * 修改优惠券绑定关系
     * @author lishaochuan
     * @create 2016年6月18日下午6:49:03
     * @param userCouponEntity
     * @return
     */
    public int updateByCouponSn(UserCouponEntity userCouponEntity){
    	if(Check.NuNObj(userCouponEntity)){
            LogUtil.error(logger,"参数为空");
            throw new BusinessException("参数为空");
        }
        return mybatisDaoContext.update(SQLID + "updateByCouponSn", userCouponEntity);
    }
    
    /**
     * 条件查询 用户根据uid绑定的优惠券数量
     * @param request
     * @return
     */
    public Long  countUidAc(MobileCouponRequest request){
        return mybatisDaoContext.count(SQLID+"countUidAc", request.toMap());
    }
    
    /**
     * 条件查询 用户在 组内领取的活动
     * @param request
     * @return
     */
    public List<String>  findActSnsByUid(MobileCouponRequest request){
    	 return mybatisDaoContext.findAllByMaster(SQLID+"findActSnsByUid", String.class, request.toMap());
    }


    /**
     *  查询 uid领取活动优惠券数量
     * @author jixd
     * @created 2017年06月14日 20:04:08
     * @param
     * @return
     */
    public long countCouponNumByUidAndAct(String uid,String actSn,String date){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("uid",uid);
        paramMap.put("actSn",actSn);
        paramMap.put("date",date);
        return mybatisDaoContext.count(SQLID + "countCouponNumByUidAndAct",paramMap);
    }


}
