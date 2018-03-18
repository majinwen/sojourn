package com.ziroom.zrp.service.houses.api;

/**
 * <p>只封装对智能平台的数据对接，没有其他业务参与</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @version 1.0
 * @Date Created in 2017年12月11日
 * @since 1.0
 */
public interface SmartPlatformService {

    /**
     *
     * 初始化智能平台维度数据(非后台研发人员勿用)
     * wiki：http://wiki.ziroom.com/pages/viewpage.action?pageId=333348876
     *
     * @author zhangyl2
     * @created 2018年02月07日 15:28
     * @param
     * @return
     */
    String initSmartHireContract(String paramJson);

    /**
     * 智能平台wiki：http://wiki.ziroom.com/pages/viewpage.action?pageId=337674251
     */

    /**
     *
     * 新增智能平台出房合同
     *
     * @author zhangyl2
     * @created 2017年12月12日 16:04
     * @param
     * @return
     */
    String addRentContract(String paramJson);

    /**
     *
     * 退租
     *
     * @author zhangyl2
     * @created 2017年12月12日 17:21
     * @param
     * @return
     */
    String backRent(String paramJson);

    /**
     *
     * 续约
     *
     * @author zhangyl2
     * @created 2017年12月12日 17:56
     * @param
     * @return
     */
    String continueAbout(String paramJson);

    /**
     * 
     * 更换入住人信息
     * 
     * @author zhangyl2
     * @created 2017年12月12日 17:58
     * @param 
     * @return 
     */
    String changeOccupants(String paramJson);
    
    /**
     * 
     * 获取智能锁设备状态
     * 
     * @author zhangyl2
     * @created 2017年12月12日 17:59
     * @param 
     * @return 
     */
    String getLockInfo(String paramJson);
    
    /**
     * 
     * 下发有效期密码
     * 
     * @author zhangyl2
     * @created 2017年12月12日 18:01
     * @param 
     * @return 
     */
    String addPassword(String paramJson);
    
    /**
     * 
     * 获取临时密码
     * 
     * @author zhangyl2
     * @created 2017年12月12日 18:02
     * @param 
     * @return
     */
    String temporaryPassword(String paramJson);

    /**
     * 智能平台水电表接口
     * wiki：
     * http://wiki.ziroom.com/pages/viewpage.action?pageId=341835936
     */

    /**
     *
     * 获取电表详情接口
     *
     * @author zhangyl2
     * @created 2018年01月11日 18:22
     * @param
     * @return
     */
    String electricMeterState(String paramJson);

    /**
     * 
     * 获取水表详情接口
     * 
     * @author zhangyl2
     * @created 2018年01月15日 21:02
     * @param 
     * @return 
     */
    String waterMeterState(String paramJson);

    /**
     *
     * 抄表电表(异步)
     * 暂时不用
     * 暂时不用
     * 暂时不用
     *
     * @author zhangyl2
     * @created 2018年01月15日 21:02
     * @param
     * @return
     */
    String readElectricMeter(String paramJson);

    /**
     *
     * 设置房间电表付费模式
     *
     * @author zhangyl2
     * @created 2018年01月15日 21:02
     * @param
     * @return
     */
    String setRoomPayType(String paramJson);

    /**
     *
     * 设置房间电表电表透支额度
     *
     * @author zhangyl2
     * @created 2018年01月15日 21:02
     * @param
     * @return
     */
    String meterOverdraftQuota(String paramJson);

    /**
     *
     * 电表充电(异步)
     *
     * @author zhangyl2
     * @created 2018年01月15日 21:02
     * @param
     * @return
     */
    String meterCharging(String paramJson);

    /**
     *
     * 电表清零(异步)
     *
     * @author zhangyl2
     * @created 2018年01月15日 21:02
     * @param
     * @return
     */
    String electricMeterReset(String paramJson);

    /**
     *
     * 水表抄表(异步)
     * 暂时不用
     * 暂时不用
     * 暂时不用
     *
     * @author zhangyl2
     * @created 2018年01月15日 21:02
     * @param
     * @return
     */
    String readWaterMeter(String paramJson);

    /**
     *
     * 最新抄表水表记录
     *
     * @author zhangyl2
     * @created 2018年01月15日 21:02
     * @param
     * @return
     */
    String readNewestWaterMeter(String paramJson);

    /**
     *
     * 最新抄表电表记录
     *
     * @author zhangyl2
     * @created 2018年01月31日 15:54
     * @param
     * @return
     */
    String readNewestElectricMeter(String paramJson);

    /**
     *  查询电表充值页面信息
     * @author yd
     * @created
     * @param
     * @return
     */
    String queryWattRechargeInfo(String paramJson);
}
