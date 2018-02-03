package com.ziroom.minsu.services.common.thread;

import com.ziroom.minsu.services.common.sms.SendService;

/**
 * <p>发送消息的线程</p>
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
public class SendThread<X,Y,Z,P> implements Runnable{

    X x;

    Y y;

    Z z;

    P p;

    SendService sendService;


    /**
     * 构造函数
     * @author afi
     * @param sendService
     * @param x
     * @param y
     */
    public SendThread(final SendService sendService, final X x, final Y y, final Z z,final P p) {
        this.sendService = sendService;
        this.x = x;
        this.y = y;
        this.z = z;
        this.p = p;
    }

    @Override
    public void run() {
        sendService.send(x, y,z,p);
    }

}
