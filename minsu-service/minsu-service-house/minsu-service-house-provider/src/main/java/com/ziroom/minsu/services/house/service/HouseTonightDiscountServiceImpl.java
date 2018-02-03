package com.ziroom.minsu.services.house.service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.TonightDiscountEntity;
import com.ziroom.minsu.services.house.dao.TonightDiscountDao;
import com.ziroom.minsu.valenum.house.RentWayEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author wangwentao 2017/5/10
 * @version 1.0
 * @since 1.0
 */
@Service("house.houseTonightDiscountServiceImpl")
public class HouseTonightDiscountServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseTonightDiscountServiceImpl.class);

    @Resource(name = "house.tonightDiscountDao")
    private TonightDiscountDao tonightDiscountDao;

    public List<TonightDiscountEntity> findTonightDiscountByCondition(TonightDiscountEntity tonightDiscountEntity) {
        return tonightDiscountDao.findTonightDiscountByCondition(tonightDiscountEntity);
    }

     /**
     * 
     * 今夜特价设置查询
     *
     * @author wangwentao
     * @created 2017年5月11日 下午3:14:28
     *
     * @param houseTonightDiscountDto
     * @return
     */
    public TonightDiscountEntity findTonightDiscountByRentway(TonightDiscountEntity tonightDiscountEntity) {
        if (RentWayEnum.HOUSE.getCode() == tonightDiscountEntity.getRentWay()) {
            return tonightDiscountDao.findTonightDiscountEntire(tonightDiscountEntity);
        } else {
            return tonightDiscountDao.findTonightDiscountSub(tonightDiscountEntity);
        }
    }
    
    /**
     * 
     * 插入房源今夜特价
     *
     * @author bushujie
     * @created 2017年5月11日 下午3:30:25
     *
     * @param tonightDiscountEntity
     */
    public void insertTonightDiscount(TonightDiscountEntity tonightDiscountEntity){
    	tonightDiscountDao.insertTonightDiscount(tonightDiscountEntity);
    }
    
    /**
     * 
     * 查询今日要提醒房东设置今日特价的房东uid列表
     *
     * @author bushujie
     * @created 2017年5月16日 下午5:51:35
     *
     * @param paramMap
     * @return
     */
    public PagingResult<HouseBaseMsgEntity> findRemindLandlordUid(Map<String, Object> paramMap){
    	return tonightDiscountDao.findRemindLandlordUid(paramMap);
    }
}
