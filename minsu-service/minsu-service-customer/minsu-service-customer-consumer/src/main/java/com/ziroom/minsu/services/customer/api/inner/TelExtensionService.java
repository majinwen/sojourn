package com.ziroom.minsu.services.customer.api.inner;

/**
 * <p>400 电话的绑定</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/6.
 * @version 1.0
 * @since 1.0
 */
public interface TelExtensionService {


    /**
     * 分页获取电话的绑定情况
     * @author afi
     * @param extjson
     * @return
     */
    String getExtensionVOByPage(String extjson);



    /**
     * 给当前的uid分配分机号
     * @param uid
     * @param createUid
     * @return
     */
    String bindZiroomPhone(String uid,String createUid);

    /**
     * 给当前的uid分配分机号
     * 异步
     * @author afi
     * @param uid
     * @param createUid
     * @return
     */
    String bindZiroomPhoneAsynchronous(String uid,String createUid);





    /**
     * 解绑当前400电话
     * @param uid
     * @param createUid
     * @return
     */
    String breakBind(String uid,String createUid);



    /**
     * 解绑当前400电话
     * 异步
     * @author afi
     * @param uid
     * @param createUid
     * @return
     */
    String breakBindAsynchronous(String uid,String createUid);


    /**
     * 获取当前用户id的分机号码
     * @author afi
     * @param uid
     * @return
     */
    String getZiroomPhone(String uid);

    /**
     * 获取当前用户id的绑定情况
     * @author afi
     * @param uid
     * @return
     */
    String getExtensionByUid(String uid);


    /**
     * 获取当前用户id的绑定vo
     * @author afi
     * @param uid
     * @return
     */
    String getExtensionVoByUid(String uid);

    /**
     * 保存当前的绑定信息 幂等操作
     * @author afi
     * @param saveJson
     * @return
     */
    String saveExtensionIdempotent(String saveJson);

    /**
     * 更改订单的绑定状态
     * @author afi
     * @param uid
     * @param errorCode
     * @param status
     * @return
     */
    String updateBindStatus(String uid,Integer errorCode,Integer status,String createUid);
    
    
    /**
     * 通过uid获取唯一绑定成功的电话
     * @author lishaochuan
     * @create 2016年5月12日下午10:20:35
     * @param uid
     * @return
     */
    String getZiroomPhoneByUid(String uid);
}
