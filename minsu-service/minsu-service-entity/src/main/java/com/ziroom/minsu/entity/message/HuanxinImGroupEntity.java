package com.ziroom.minsu.entity.message;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>群组实体</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class HuanxinImGroupEntity extends BaseEntity{
    /**
	 * 序列id
	 */
	private static final long serialVersionUID = -8929797837845064437L;

	/**
     * 唯一标识
     */
    private Integer id;

    /**
     * 群组 ID，群组唯一标识符，由环信服务器生成，等同于单个用户的环信 ID
     */
    private String groupId;

    /**
     * 群组名称，根据用户输入创建，字符串类型
     */
    private String name;

    /**
     * 群组描述，根据用户输入创建，字符串类型（存入json格式  其中包括扩展消息）
     */
    private String description;

    /**
     * 组类型：0=公开群，1=私有群  默认0
     */
    private Integer isPublic;

    /**
     * 加入群组是否需要群主或者群管理员审批。0=是，1=否  默认1
     */
    private Integer membersonly;

    /**
     * 是否允许群成员邀请别人加入此群( 0：允许群成员邀请人加入此群，1：只有群主才可以往群里加人 默认1)
     */
    private Integer allowinvites;

    /**
     * 群成员上限，创建群组的时候设置，可修改  （默认2000）
     */
    private Integer maxusers;

    /**
     * 现有成员总数
     */
    private Integer affiliationsCount;

    /**
     * 邀请加群，被邀请人是否需要确认。如果是0，表示邀请加群需要被邀请人确认；如果是1，表示不需要被邀请人确认 
     */
    private Integer inviteNeedConfirm;

    /**
     * 群主的环信 ID
     */
    private String owner;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除 0=不删除 1=删除
     */
    private Integer isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Integer isPublic) {
        this.isPublic = isPublic;
    }

    public Integer getMembersonly() {
        return membersonly;
    }

    public void setMembersonly(Integer membersonly) {
        this.membersonly = membersonly;
    }

    public Integer getAllowinvites() {
        return allowinvites;
    }

    public void setAllowinvites(Integer allowinvites) {
        this.allowinvites = allowinvites;
    }

    public Integer getMaxusers() {
        return maxusers;
    }

    public void setMaxusers(Integer maxusers) {
        this.maxusers = maxusers;
    }

    public Integer getAffiliationsCount() {
        return affiliationsCount;
    }

    public void setAffiliationsCount(Integer affiliationsCount) {
        this.affiliationsCount = affiliationsCount;
    }

    public Integer getInviteNeedConfirm() {
        return inviteNeedConfirm;
    }

    public void setInviteNeedConfirm(Integer inviteNeedConfirm) {
        this.inviteNeedConfirm = inviteNeedConfirm;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
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