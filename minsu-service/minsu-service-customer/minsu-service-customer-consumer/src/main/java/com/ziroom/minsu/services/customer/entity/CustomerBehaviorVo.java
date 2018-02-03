package com.ziroom.minsu.services.customer.entity;

import com.ziroom.minsu.entity.customer.CustomerBehaviorEntity;

/**
 * <p>用户行为</p>
 * <p>
 * <PRE>
 * <BR>    修改记录 
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2017年10月14日
 * @version 1.0
 * @since 1.0
 */
public class CustomerBehaviorVo extends CustomerBehaviorEntity {

    private static final long serialVersionUID = -5223278012473249140L;

    /**
     * 是否存在修改日志（0-否 1-是）
     */
    private Integer operated;

    /**
     * 行为属性名称
     */
    private String attributeName;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 行为类型名称
     */
    private String typeName;

    /**
     * 记录人（人工录入的显示为录入uid的角色姓名；系统生成的显示为admin）
     */
    private String operationName;

    /**
     * 用户行为创建类型(1-系统生成 2-人工录入)
     */
    private String createTypeName;

    public Integer getOperated() {
        return operated;
    }

    public void setOperated(Integer operated) {
        this.operated = operated;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getCreateTypeName() {
        return createTypeName;
    }

    public void setCreateTypeName(String createTypeName) {
        this.createTypeName = createTypeName;
    }
}
