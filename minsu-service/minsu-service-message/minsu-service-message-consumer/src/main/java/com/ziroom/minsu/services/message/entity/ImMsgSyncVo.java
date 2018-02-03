package com.ziroom.minsu.services.message.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>im统计数据</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class ImMsgSyncVo extends BaseEntity{
    private static final long serialVersionUID = 7377870273162297052L;
    /**
     * 房客uid
     */
    private String tenantUid;
    /**
     * 房东uid
     */
    private String landlordUid;
    /**
     * 消息发送人类型（10=房东 20=房客）
     */
    private Integer msgSenderType;
    /**
     * 房源fid
     */
    private String houseFid;
    /**
     * 出租方式
     */
    private Integer rentWay;

    /**
     * 消息体（即：消息内容）
     */
    private String msgContent;
    
    /**
     * 消息体（即：消息内容）
     */
    private String msgRealContent;
    /**
     * 环信消息的发送时间
     */
    private Date msgSendTime;
    /**
     * 是否删除
     */
    private Integer isDel;
    /**
     * 图片url
     */
    private String url;
    /**
     * secret在上传图片后会返回，只有含有secret才能够下载此图片 或 secret在上传文件后会返回
     */
    private String secret ;
    /**
     * 语音名称
     */
    private String filename ;
    /**
     * 图片附件大小
     */
    private Integer fileLength;
    /**
     * 环信消息类型
     */
    private String type;
    /**
     * 图片尺寸
     */
    private String size;
    
    public String getTenantUid() {
        return tenantUid;
    }

    public void setTenantUid(String tenantUid) {
        this.tenantUid = tenantUid;
    }

    public String getLandlordUid() {
        return landlordUid;
    }

    public void setLandlordUid(String landlordUid) {
        this.landlordUid = landlordUid;
    }

    public Integer getMsgSenderType() {
        return msgSenderType;
    }

    public void setMsgSenderType(Integer msgSenderType) {
        this.msgSenderType = msgSenderType;
    }

    public String getHouseFid() {
        return houseFid;
    }

    public void setHouseFid(String houseFid) {
        this.houseFid = houseFid;
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
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

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getFileLength() {
		return fileLength;
	}

	public void setFileLength(Integer fileLength) {
		this.fileLength = fileLength;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getMsgRealContent() {
		return msgRealContent;
	}

	public void setMsgRealContent(String msgRealContent) {
		this.msgRealContent = msgRealContent;
	}
    
}
