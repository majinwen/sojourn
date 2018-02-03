package com.ziroom.minsu.services.cms.dto;

import com.ziroom.minsu.entity.cms.GroupHouseRelEntity;

import java.util.List;

/**
 * <p>批量添加房源组入参</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年10月20日 10:18
 * @since 1.0
 */

public class GroupHouseRelRequest {

    /**
     * 房源组集合
     */
    private List<GroupHouseRelEntity> groupHouseRelEntities;

    public List<GroupHouseRelEntity> getGroupHouseRelEntities() {
        return groupHouseRelEntities;
    }

    public void setGroupHouseRelEntities(List<GroupHouseRelEntity> groupHouseRelEntities) {
        this.groupHouseRelEntities = groupHouseRelEntities;
    }
}
