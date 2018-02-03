package com.ziroom.minsu.services.customer.service;

import com.ziroom.minsu.entity.customer.LandlordBehaviorEntity;
import com.ziroom.minsu.services.customer.dao.LandlordBehaviorDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>房东数据统计服务类</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@Service("customer.landlordBehaviorImpl")
public class LandlordBehaviorImpl {

    @Resource(name="customer.landlordBehaviorDao")
    private LandlordBehaviorDao landlordBehaviorDao;

    /**
     * 根据房东uid查询用户行为数据
     * @author jixd
     * @created 2016年11月04日 下午4:18:56
     * @param uid
     * @return
     */
    public LandlordBehaviorEntity findLandlordBehavior(String uid){
        return landlordBehaviorDao.findLandlordBehavior(uid);
    }

    /**
     * @author jixd
     * @created 2016年11月04日 下午4:18:56
     * @param landlordBehaviorEntity
     * @return
     */
    public int saveLandlordBehavior(LandlordBehaviorEntity landlordBehaviorEntity){
        return landlordBehaviorDao.insertLandlordBehavior(landlordBehaviorEntity);
    }

    /**
     * 更新用户统计数据
     * @author jixd
     * @created 2016年11月04日 10:21:42
     * @param landlordBehaviorEntity
     * @return
     */
    public int updateLandlordBehaviorByUid(LandlordBehaviorEntity landlordBehaviorEntity){
        return landlordBehaviorDao.updateLandlordBehavior(landlordBehaviorEntity);
    }
}
