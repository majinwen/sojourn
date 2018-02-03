package com.ziroom.minsu.entity.message;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class HuanxinImOfflineEntity extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3636873786189952266L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 关联编号
     */
    private String msgId;

    /**
     * 消息发送人uid
     */
    private String fromUid;

    /**
     * 消息接收人uid
     */
    private String toUid;

    /**
     * 自如网标识  ZIROOM_MINSU_IM= 代表民宿 ZIROOM_ZRY_IM= 自如驿  ZIROOM_CHANGZU_IM= 自如长租
     */
    private String ziroomFlag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除 默认0(0：不删除 1：删除)
     */
    private Integer isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
    }

    public String getFromUid() {
        return fromUid;
    }

    public void setFromUid(String fromUid) {
        this.fromUid = fromUid == null ? null : fromUid.trim();
    }

    public String getToUid() {
        return toUid;
    }

    public void setToUid(String toUid) {
        this.toUid = toUid == null ? null : toUid.trim();
    }

    public String getZiroomFlag() {
        return ziroomFlag;
    }

    public void setZiroomFlag(String ziroomFlag) {
        this.ziroomFlag = ziroomFlag == null ? null : ziroomFlag.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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