package com.ziroom.minsu.services.customer.entity;

import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;

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
public class CustomerExt extends CustomerBaseMsgEntity {

    /**
     * 角色名称
     */
    private String roleNames;

    /**    */
    private Integer isSeed = 0;
    
	 /* 背景：troy系统==》客户信息审核功能，当auditstatus=1时，不能在点击展示审核页面。
	           此字段意义：  当房东审核通过后，再次修改“需要审核的字段”时，此时审核状态是1，
	    但需要给这样的房东一个isCanShow=1（默认是0，不能点击）的标识，
	    此标识只在前台使用，在数据库没有对应的字段*/
    private Integer isCanShow=0;

    public Integer getIsSeed() {
        return isSeed;
    }

    public void setIsSeed(Integer isSeed) {
        this.isSeed = isSeed;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

	public Integer getIsCanShow() {
		return isCanShow;
	}

	public void setIsCanShow(Integer isCanShow) {
		this.isCanShow = isCanShow;
	}
    
}
