package com.ziroom.minsu.entity.message;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>IM错误消息实体</p>
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
public class MsgBaseErrorEntity extends BaseEntity{
    /**
	 * 序列id
	 */
	private static final long serialVersionUID = 4057502838541694824L;

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
     * 消息内容
     */
    private String msgContent;

    /**
     * 消息发送人类型(1=房东，2=房客，10=房东的环信IM消息， 20=房客的环信IM消息)
     */
    private Integer msgSentType;

    /**
     * 房源或房间fid
     */
    private String houseFid;

    /**
     * 出租方式(0=整租 1=分租)
     */
    private Integer rentWay;

    /**
     * 入住起始时间
     */
    private Date startDate;

    /**
     * 入住结束时间
     */
    private Date endDate;

    /**
     * 入住人数
     */
    private Integer personNum;

    /**
     * 房源名称
     */
    private String houseName;

    /**
     * 消息来源（0=其他 1=安卓，2=IOS）
     */
    private Integer msgSource;

    /**
     * 房源图片URL
     */
    private String housePicUrl;

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

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent == null ? null : msgContent.trim();
    }

    public Integer getMsgSentType() {
        return msgSentType;
    }

    public void setMsgSentType(Integer msgSentType) {
        this.msgSentType = msgSentType;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName == null ? null : houseName.trim();
    }

    public Integer getMsgSource() {
        return msgSource;
    }

    public void setMsgSource(Integer msgSource) {
        this.msgSource = msgSource;
    }

    public String getHousePicUrl() {
        return housePicUrl;
    }

    public void setHousePicUrl(String housePicUrl) {
        this.housePicUrl = housePicUrl == null ? null : housePicUrl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}