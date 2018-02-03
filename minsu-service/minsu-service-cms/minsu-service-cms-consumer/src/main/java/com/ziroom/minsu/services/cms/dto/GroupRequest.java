package com.ziroom.minsu.services.cms.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>组分页查询</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 16/10/18.
 * @version 1.0
 * @since 1.0
 */
public class GroupRequest extends PageRequest {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -4601503358849413081L;

    /** 组编号 */
    private String groupSn;

    /** 组名字 */
    private String groupName;

    /** 组fid */
    private String groupFid;

    public String getGroupSn() {
        return groupSn;
    }

    public void setGroupSn(String groupSn) {
        this.groupSn = groupSn;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupFid() {
        return groupFid;
    }

    public void setGroupFid(String groupFid) {
        this.groupFid = groupFid;
    }
}
