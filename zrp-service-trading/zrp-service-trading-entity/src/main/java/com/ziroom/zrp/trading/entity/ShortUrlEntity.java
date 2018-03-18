package com.ziroom.zrp.trading.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>短链实体类</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年9月26日
 * @since 1.0
 */
public class ShortUrlEntity extends BaseEntity {
    /**
     *
     * 表字段 : cf_short_url.id
     *
     */

    private Integer id;

    /**
     * 短链ID;即短链地址
     * 表字段 : cf_short_url.suid
     *
     */
    private String suid;

    /**
     * 长链接
     * 表字段 : cf_short_url.long_url
     *
     */
    private String longUrl;

    /**
     * 创建时间
     * 表字段 : cf_short_url.create_time
     *
     */
    private Date createTime;



    public ShortUrlEntity() {
		super();
	}

	public ShortUrlEntity(String suid, String longUrl, Date createTime) {
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