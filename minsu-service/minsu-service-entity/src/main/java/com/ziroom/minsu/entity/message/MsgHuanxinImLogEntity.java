package com.ziroom.minsu.entity.message;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>消息记录</p>
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
/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class MsgHuanxinImLogEntity extends BaseEntity{
    /**
	 * 序列id
	 */
	private static final long serialVersionUID = -625490039781550771L;

	/**
     * 唯一编号
     */
    private Integer id;

    /**
     * 消息id  唯一
     */
    private String msgId;

    /**
     * 消息发送人uid
     */
    private String fromUid;

    /**
     * 消息接收人uid  群聊是组id
     */
    private String toUid;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 扩展消息,json串格式
     */
    private String ext;

    /**
     * 用来判断单聊还是群聊。chat: 单聊；groupchat: 群聊 环信同步
     */
    private String chatType;

    /**
     * 自如网标识 ZIROOM_MINSU_IM= 代表民宿 ZIROOM_ZRY_IM= 自如驿
     */
    private String ziroomFlag;

    /**
     * 消息类型。txt: 文本消息；img: 图片；loc: 位置；audio: 语音
     */
    private String type;
    
    /**
     * 文本消息类型（100：一般聊天文本消息， 101：打招呼消息， 102：卡片消息， 103：活动消息，200：屏蔽消息 201：取消屏蔽消息 202：投诉消息）
     */
    private Integer ziroomType;
    
    /**
     * 图片语音等文件的网络URL，图片和语音消息有这个属性
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
     * 消息状态  0=成功的消息  1=失败的消息  默认0
     */
    private Integer chatStatu;

    /**
     * 入库时间
     */
    private Date createDate;
    

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext == null ? null : ext.trim();
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType == null ? null : chatType.trim();
    }

    public String getZiroomFlag() {
        return ziroomFlag;
    }

    public void setZiroomFlag(String ziroomFlag) {
        this.ziroomFlag = ziroomFlag == null ? null : ziroomFlag.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getChatStatu() {
        return chatStatu;
    }

    public void setChatStatu(Integer chatStatu) {
        this.chatStatu = chatStatu;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
		this.url = url;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
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
		this.addr = addr;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
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