package com.ziroom.zrp.service.houses.entity;

import java.io.Serializable;

/**
 * <p>房间状态vo</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2017年12月06日 14:07
 * @since 1.0
 */
public class RoomStatuVo implements Serializable{

    private static final long serialVersionUID = 5344358242713041771L;

    /**
     * 状态值
     */
    private String currentState;

    /**
     * 状态名称
     */
    private String currentStateName;

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getCurrentStateName() {
        return currentStateName;
    }

    public void setCurrentStateName(String currentStateName) {
        this.currentStateName = currentStateName;
    }
}
