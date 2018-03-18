package com.zra.house.entity.dto;

import com.zra.common.dto.appbase.AppBaseDto;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/2 11:12
 * @since 1.0
 */
public class SearchReqDto extends AppBaseDto {

    /**
     * 项目id
     */
    private String projectId;
    /**
     * 房型id
     */
    private String houseTypeId;

    public String getHouseTypeId() {
        return houseTypeId;
    }

    public void setHouseTypeId(String houseTypeId) {
        this.houseTypeId = houseTypeId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
