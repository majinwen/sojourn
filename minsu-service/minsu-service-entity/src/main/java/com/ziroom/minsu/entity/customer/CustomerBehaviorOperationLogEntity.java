package com.ziroom.minsu.entity.customer;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>客户行为（成长体系）操作日志</p>
 * <p>
 * <PRE>
 * <BR>    修改记录 
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2017年10月13日 
 * @version 1.0
 * @since 1.0
 */
public class CustomerBehaviorOperationLogEntity extends BaseEntity{


    private static final long serialVersionUID = 1665317723839710826L;
    /**
     * 主键
     */
    private Integer id;

    /**
     * 业务fid
     */
    private String fid;

    /**
     * 行为记录fid
     */
    private String behaviorFid;

    /**
     * 行为初始属性
     */
    private Integer fromAttribute;

    /**
     * 修改后行为属性
     */
    private Integer toAttribute;

    /**
     * 操作员工编号
     */
    private String empCode;

    /**
     * 操作员工姓名
     */
    private String empName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 是否删除(0-否 1-是)
     */
    private Integer isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getBehaviorFid() {
        return behaviorFid;
    }

    public void setBehaviorFid(String behaviorFid) {
        this.behaviorFid = behaviorFid;
    }

    public Integer getFromAttribute() {
        return fromAttribute;
    }

    public void setFromAttribute(Integer fromAttribute) {
        this.fromAttribute = fromAttribute;
    }

    public Integer getToAttribute() {
        return toAttribute;
    }

    public void setToAttribute(Integer toAttribute) {
        this.toAttribute = toAttribute;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}