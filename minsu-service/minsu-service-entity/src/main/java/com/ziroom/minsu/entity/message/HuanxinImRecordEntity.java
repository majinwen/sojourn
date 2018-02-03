package com.ziroom.minsu.entity.message;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>TODO</p>
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
public class HuanxinImRecordEntity  extends BaseEntity{
    /**
	 * 序列id
	 */
	private static final long serialVersionUID = 6252036377929153420L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 关联编号
     */
    private String fid;

    /**
     * uuid 标识  环信同步
     */
    private String uuid;

    /**
     * 接口类型  对应消息主type
     */
    private String interfaceType;

    /**
     * 消息创建时间  环信同步时间戳
     */
    private Date created;

    /**
     * 消息修改时间 环信同步时间戳
     */
    private Date modified;

    /**
     * 消息发送时间 环信同步时间戳
     */
    private Date timestampSend;

    /**
     * 发送人uid  环信同步
     */
    private String fromUid;

    /**
     * 接收人uid 环信同步
     */
    private String toUid;

    /**
     * 消息id
     */
    private String msgId;

    /**
     * 用来判断单聊还是群聊。chat: 单聊；groupchat: 群聊 环信同步
     */
    private String chatType;

    /**
     * 入库时间
     */
    private Date createDate;

    /**
     * 自如网标识  ziroom_minsu = 代表民宿   民宿这边只同步民宿的聊天记录
     */
    private String ziroomFlag;

    /**
     * 消息扩展json串
     */
    private String ext;

    /**
     * 消息内容 
     */
    private String msg;

    /**
     * 消息类型。txt: 文本消息；img: 图片；loc: 位置；audio: 语音
     */
    private String type;
    
    /**
     * 文本消息类型（100：一般聊天文本消息， 101：打招呼消息， 102：卡片消息， 103：活动消息，200：屏蔽消息 201：取消屏蔽消息 202：投诉消息）
     */
    private Integer ziroomType;

    /**
     * 语音时长，单位为秒，这个属性只有语音消息有
     */
    private Integer length;

    /**
     * 图片语音等文件的网络URL，图片和语音消息有这个属性
     */
    private String url;

    /**
     * 文件名字，图片和语音消息有这个属性
     */
    private String filename;
    
    /**
     * 附件大小，图片，视频，语音，文件都有这个属性
     */
    private Integer fileLength;

    /**
     * 图片尺寸json串，图片和视频消息有这个属性
     */
    private String size;

    /**
     * 获取文件的secret，图片和语音消息有这个属性
     */
    private String secret;

    /**
     * 发送的位置的纬度，只有位置消息有这个属性
     */
    private Float lat;

    /**
     * 位置经度，只有位置消息有这个属性
     */
    private Float lng;

    /**
     * 位置消息详细地址，只有位置消息有这个属性
     */
    private String addr;

    /**
     * 是否删除  0=不删除（默认） 1=删除
     */
    private Integer isDel;

    /**
     * 是否已读  0=未读（默认） 1=已读  （此字段针对环信没有意义，在这里只是备用）
     */
    private Integer isRead;

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
        this.fid = fid == null ? null : fid.trim();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType == null ? null : interfaceType.trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getTimestampSend() {
        return timestampSend;
    }

    public void setTimestampSend(Date timestampSend) {
        this.timestampSend = timestampSend;
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

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType == null ? null : chatType.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getZiroomFlag() {
        return ziroomFlag;
    }

    public void setZiroomFlag(String ziroomFlag) {
        this.ziroomFlag = ziroomFlag == null ? null : ziroomFlag.trim();
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext == null ? null : ext.trim();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret == null ? null : secret.trim();
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

	public Integer getFileLength() {
		return fileLength;
	}

	public void setFileLength(Integer fileLength) {
		this.fileLength = fileLength;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getZiroomType() {
		return ziroomType;
	}

	public void setZiroomType(Integer ziroomType) {
		this.ziroomType = ziroomType;
	}

}