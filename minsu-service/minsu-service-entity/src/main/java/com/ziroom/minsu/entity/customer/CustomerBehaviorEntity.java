package com.ziroom.minsu.entity.customer;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.valenum.customer.LandlordBehaviorEnum;

import java.util.Date;

/**
 * <p>客户行为（成长体系）</p>
 * <p>
 * <PRE>
 * <BR>    修改记录 
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2017年10月11日 
 * @version 1.0
 * @since 1.0
 */
public class CustomerBehaviorEntity extends BaseEntity{


    private static final long serialVersionUID = 7297105981200719911L;

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 行为记录fid
     */
    private String fid;

    /**
     * 关联的具体行为的当事表fid(如评价综合评分低，则为房客评价表fid)
     */
    private String proveFid;

    /**
     * 关联的具体行为参数(key1=value1&key2=value2)
     */
    private String proveParam;

    /**
     * 行为属性(1-限制行为 2-激励行为 3-中性行为)
     * @see
     */
    private Integer attribute;

    /**
     * 行为角色(1-房东 2-房客)
     */
    private Integer role;

    /**
     * 客户uid
     */
    private String uid;

    /**
     * 行为类型(1***-限制行为：10**-人工录入，11**-系统生成；
     * 2***-激励行为：20**-人工录入，21**-系统生成；
     * 3***-中性行为：30**-人工录入，31**-系统生成)
     *
     * 1001-IM沟通违规(引流、提供联系方式、恶意引导等),
     * 1002-辱骂客户,
     * 1003-刷单、优惠券套现,
     * 1004-营销活动作弊,
     * 1005-联系不上、不愿管理,
     * 1006-发布虚假房源,
     *
     * 1101-拒绝申请/次,
     * 1102-接单后房东申请取消,
     * 1103-评价综合评分≤3分/条,
     * 1104-评价存在单一评价维度评分≤2分/条,
     *
     * 2101-接受预定申请/次
     *
     * @see LandlordBehaviorEnum
     */
    private Integer type;

    /**
     * 行为分值
     */
    private Double score;

    /**
     * 人工录入的员工fid/系统生成时为客户uid
     */
    private String createFid;

    /**
     * 创建类型(1-系统生成 2-人工录入)
     */
    private Integer createType;

    /**
     * 人工录入备注(100字以内)/系统生成的为行为详情
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后更新时间
     */
    private Date lastModifyDate;

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

    public String getProveFid() {
        return proveFid;
    }

    public void setProveFid(String proveFid) {
        this.proveFid = proveFid;
    }

    public String getProveParam() {
        return proveParam;
    }

    public void setProveParam(String proveParam) {
        this.proveParam = proveParam;
    }

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getCreateFid() {
        return createFid;
    }

    public void setCreateFid(String createFid) {
        this.createFid = createFid;
    }

    public Integer getCreateType() {
        return createType;
    }

    public void setCreateType(Integer createType) {
        this.createType = createType;
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

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}