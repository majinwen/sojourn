package com.ziroom.minsu.services.house.api.inner;

/**
 * <p>房源今天特价service</p>
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
public interface HouseTonightDiscountService {

    /**
     * 根据条件查找
     * @param paramJson
     * @return
     */
    String findTonightDiscountByCondition(String paramJson);

    /**
     * 根据house_fid,room_fid, rent_way查找
     *
     * @param
     * @return
     * @author wangwentao
     * @created 2017/5/11 11:58
     */
    String findTonightDiscountByRentway(String paramJson);
    
    /**
     * 
     * 设置房源今夜特价
     *
     * @author bushujie
     * @created 2017年5月11日 下午3:11:35
     *
     * @param paramJson
     * @return
     */
    String setHouseTodayDiscount(String paramJson);
    
    /**
     * 
     * 查询今日要提醒房东设置今日特价的房东uid列表(分页)
     *
     * @author bushujie
     * @created 2017年5月16日 下午6:11:19
     *
     * @param paramJson
     * @return
     */
    String findRemindLandlordUid(String paramJson);
}
