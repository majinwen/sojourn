package com.ziroom.minsu.services.customer.dto;

import java.util.List;

import com.ziroom.minsu.entity.customer.CustomerUpdateFieldAuditNewlogEntity;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/29.
 * @version 1.0
 * @since 1.0
 */
public class CustomerExtDto extends  CustomerBaseMsgDto {

    /**
     * 角色编码
     */
    private String roleCode;


    /**
     * 是否是种子房东
     */
    private Integer isSeed;

    /* 背景：troy系统==》客户信息审核功能，当auditstatus=1时，不能在点击展示审核页面。
            此字段意义：  当房东审核通过后，再次修改“需要审核的字段”时，此时审核状态是1，
            但需要给这样的房东一个isCanShow=1（默认是0，不能点击）的标识，
            此标识只在前台使用，在数据库没有对应的字段*/
    private Integer isCanShow;

    private List<String> uidList;
    
    
    public Integer getIsSeed() {
        return isSeed;
    }

    public void setIsSeed(Integer isSeed) {
        this.isSeed = isSeed;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
    
	public Integer getIsCanShow() {
		return isCanShow;
	}

	public void setIsCanShow(Integer isCanShow) {
		this.isCanShow = isCanShow;
	}

	public List<String> getUidList() {
		return uidList;
	}

	public void setUidList(List<String> uidList) {
		this.uidList = uidList;
	}

	
	
}
