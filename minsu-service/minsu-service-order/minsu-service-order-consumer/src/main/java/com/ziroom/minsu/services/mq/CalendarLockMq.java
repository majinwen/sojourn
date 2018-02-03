package com.ziroom.minsu.services.mq;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.services.order.dto.LockHouseRequest;

/**
 * <p>spider mq 发送的日历数据</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年08月25日 15:52
 * @since 1.0
 */
public class CalendarLockMq extends BaseEntity {


    private static final long serialVersionUID = -9105957510144844608L;

    private int status;

    private String message;

    private LockHouseRequest data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LockHouseRequest getData() {
        return data;
    }

    public void setData(LockHouseRequest data) {
        this.data = data;
    }
}
