package com.ziroom.minsu.services.common.sms;

/**
 * <p>发送消息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/12.
 * @version 1.0
 * @since 1.0
 */
public interface SendService<X,Y,Z,P> {


    /**
     * 发送消息.
     * @param x
     * @param y
     * @param z
     * @param p
     * @return
     */
    boolean send(X x, Y y,Z z,P p);
}
