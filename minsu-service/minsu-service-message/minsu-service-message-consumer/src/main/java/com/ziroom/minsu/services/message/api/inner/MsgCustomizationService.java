/**
 * @FileName: MsgBaseService.java
 * @Package com.ziroom.minsu.services.message.api.inner
 * 
 * @author yd
 * @created 2016年4月18日 下午4:16:25
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.api.inner;

/**
 * <p>自定义回复信息接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lunan
 * @since 1.0
 * @version 1.0
 */
public interface MsgCustomizationService {

    /**
     *
     * 查询用户自定义消息集合
     *
     * @author lunan
     * @created 2017年3月29日
     *
     * @param
     * @return
     */
    String queryMsgCustomizationByUid(String uid);


    /**
     *
     * 添加用户自定义消息
     *
     * @author lunan
     * @created 2017年3月29日
     *
     * @param
     * @return
     */
    String addMsgCustomization(String paramJson);

    /**
     *
     * 修改或者删除用户自定义消息
     *
     * @author lunan
     * @created 2017年3月29日
     *
     * @param
     * @return
     */
    String updateMsgCustomization(String paramJson);


}
