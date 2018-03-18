package com.ziroom.zrp.service.houses.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>获取电表充值页面dto</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2018年01月22日 09:27
 * @since 1.0
 */
public class WattRechargeDto extends BaseEntity {


    /**
     * 项目id
     */
    private  String projectId;

    /**
     * 房间id
     */
    private  String roomId;


    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
