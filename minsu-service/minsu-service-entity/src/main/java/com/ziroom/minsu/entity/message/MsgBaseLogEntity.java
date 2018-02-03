package com.ziroom.minsu.entity.message;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 
 * <p>消息日志表</p>
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
public class  MsgBaseLogEntity extends BaseEntity {
    /**
	 * 序列id
	 */
	private static final long serialVersionUID = -3447584469072851850L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 业务编号
     */
    private String fid;

    /**
     * 房客uid
     */
    private String tenantUid;

    /**
     * 房东uid
     */
    private String landlordUid;

    /**
     * 房源fid或房间fid或者床位fid
     */
    private String houseFid;

    /**
     * 出租类型 0：整租，1：合租，2：床位
     */
    private Integer rentWay;

    /**
     * 消息体（即：消息内容） 环信消息 不包含扩展信息
     */
    private String msgRealContent;

    /**
     * 消息体（即：消息内容） 环信消息 存内容和扩展信息
     */
    private String msgContentExt;

    /**
     * 10=房东——环信 20=房客——环信
     */
    private Integer msgSenderType;

    /**
     * 环信消息id（唯一)
     */
    private String huanxinMsgId;

    /**
     * 是否删除 默认0(0：不删除 1：删除)
     */
    private Integer isDel;

    /**
     * 创建时间
     */
    private Date createTime;
    
	/**
	 * 上传图片消息地址，在上传图片成功后会返回UUID 或 上传语音远程地址，在上传语音后会返回UUID
	 */
	private String url;

	
	/**
	 * secret在上传图片后会返回，只有含有secret才能够下载此图片 或 secret在上传文件后会返回
	 */
	private String secret;
	
	/**
	 * 语音名称 或 图片名称
	 */
	private String filename;
	
	/**
	 * 图片附件大小 或 语音附件大小（单位：字节）
	 */
	private Integer fileLength;
	
	/**
	 * 环信消息类型txt: 文本消息；img: 图片；loc: 位置；audio: 语音
	 */
	private String type;
	
	/**
	 * 图片尺寸
	 */
	private String size;
	
	
	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

    /**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the secret
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * @param secret the secret to set
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the fileLength
	 */
	public Integer getFileLength() {
		return fileLength;
	}

	/**
	 * @param fileLength the fileLength to set
	 */
	public void setFileLength(Integer fileLength) {
		this.fileLength = fileLength;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

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

    public String getTenantUid() {
        return tenantUid;
    }

    public void setTenantUid(String tenantUid) {
        this.tenantUid = tenantUid == null ? null : tenantUid.trim();
    }

    public String getLandlordUid() {
        return landlordUid;
    }

    public void setLandlordUid(String landlordUid) {
        this.landlordUid = landlordUid == null ? null : landlordUid.trim();
    }

    public String getHouseFid() {
        return houseFid;
    }

    public void setHouseFid(String houseFid) {
        this.houseFid = houseFid == null ? null : houseFid.trim();
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    /**
	 * @return the msgRealContent
	 */
	public String getMsgRealContent() {
		return msgRealContent;
	}

	/**
	 * @param msgRealContent the msgRealContent to set
	 */
	public void setMsgRealContent(String msgRealContent) {
		this.msgRealContent = msgRealContent;
	}

	public String getMsgContentExt() {
        return msgContentExt;
    }

    public void setMsgContentExt(String msgContentExt) {
        this.msgContentExt = msgContentExt == null ? null : msgContentExt.trim();
    }

    public Integer getMsgSenderType() {
        return msgSenderType;
    }

    public void setMsgSenderType(Integer msgSenderType) {
        this.msgSenderType = msgSenderType;
    }

    public String getHuanxinMsgId() {
        return huanxinMsgId;
    }

    public void setHuanxinMsgId(String huanxinMsgId) {
        this.huanxinMsgId = huanxinMsgId == null ? null : huanxinMsgId.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}