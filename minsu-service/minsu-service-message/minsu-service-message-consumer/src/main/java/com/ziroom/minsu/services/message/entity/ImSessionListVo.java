package com.ziroom.minsu.services.message.entity;

import java.util.Date;

/**
 * im返回vo
 * @author jixd
 * @created 2017年04月01日 18:04:31
 * @param
 * @return
 */
public class ImSessionListVo {
    /**
     * 发送人uid
     */
    public String fromUid;
    /**
     * 接受人uid
     */
    public String toUid;
    /**
     * 发送人类型
     */
    public Integer msgSenderType;
    /**
     * 消息内容
     */
    public String msgContent;
    /**
     *
     */
    public Date msgSendTime;
    
    /**
     * 用来判断单聊还是群聊。chat: 单聊；groupchat: 群聊 环信同步
     */
    private String chatType;

    /**
     * 扩展消息内容  json字符串
     */
    public ImExtVo appChatRecordsExt;
    
    /**
     * 长租扩展消息内容  
     */
    public ImExtForChangzuVo changZuExt;
    
    /**
     * 消息类型。txt: 文本消息；img: 图片；loc: 位置；audio: 语音
     */
    private String type;

    /**
     * 图片语音等文件的网络URL，图片和语音消息有这个属性
     */
    private String url;

    /**
     * 文件名字，图片和语音消息有这个属性
     */
    private String filename;
    
	/**
	 * secret在上传图片后会返回，只有含有secret才能够下载此图片 或 secret在上传文件后会返回
	 */
	private String secret;
	
	/**
	 * 图片附件大小 或 语音附件大小（单位：字节）
	 */
	private Integer fileLength;
	
	/**
	 * 图片尺寸
	 */
	private String size;
	
    /**
	 * @return the chatType
	 */
	public String getChatType() {
		return chatType;
	}

	/**
	 * @param chatType the chatType to set
	 */
	public void setChatType(String chatType) {
		this.chatType = chatType;
	}

	public String getFromUid() {
        return fromUid;
    }

    public void setFromUid(String fromUid) {
        this.fromUid = fromUid;
    }

    public String getToUid() {
        return toUid;
    }

    public void setToUid(String toUid) {
        this.toUid = toUid;
    }

    public Integer getMsgSenderType() {
        return msgSenderType;
    }

    public void setMsgSenderType(Integer msgSenderType) {
        this.msgSenderType = msgSenderType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Date getMsgSendTime() {
        return msgSendTime;
    }

    public void setMsgSendTime(Date msgSendTime) {
        this.msgSendTime = msgSendTime;
    }

    public ImExtVo getAppChatRecordsExt() {
        return appChatRecordsExt;
    }

    public void setAppChatRecordsExt(ImExtVo appChatRecordsExt) {
        this.appChatRecordsExt = appChatRecordsExt;
    }

	public ImExtForChangzuVo getChangZuExt() {
		return changZuExt;
	}

	public void setChangZuExt(ImExtForChangzuVo changZuExt) {
		this.changZuExt = changZuExt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
}
