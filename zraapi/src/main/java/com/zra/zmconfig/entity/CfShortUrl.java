package com.zra.zmconfig.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="")
public class CfShortUrl {
    /**
     * 
     * 表字段 : cf_short_url.id
     * 
     */
    @ApiModelProperty(value="")
    private Integer id;

    /**
     * 短链ID;即短链地址
     * 表字段 : cf_short_url.suid
     * 
     */
    @ApiModelProperty(value="短链ID;即短链地址")
    private String suid;

    /**
     * 长链接
     * 表字段 : cf_short_url.long_url
     * 
     */
    @ApiModelProperty(value="长链接")
    private String longUrl;

    /**
     * 创建时间
     * 表字段 : cf_short_url.create_time
     * 
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    
    

    public CfShortUrl() {
		super();
	}

	public CfShortUrl(String suid, String longUrl, Date createTime) {
		super();
		this.suid = suid;
		this.longUrl = longUrl;
		this.createTime = createTime;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSuid() {
        return suid;
    }

    public void setSuid(String suid) {
        this.suid = suid == null ? null : suid.trim();
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl == null ? null : longUrl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}