package com.zra.zmconfig.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="")
public class CfZmConfig {
    /**
     * 自增ID
     * 表字段 : cf_zm_config.id
     * 
     */
    @ApiModelProperty(value="自增ID")
    private Integer id;

    /**
     * 系统ID
     * 表字段 : cf_zm_config.system_id
     * 
     */
    @ApiModelProperty(value="系统ID")
    private String systemId;

    /**
     * 配置KEY
     * 表字段 : cf_zm_config.cf_key
     * 
     */
    @ApiModelProperty(value="配置KEY")
    private String cfKey;

    /**
     * 配置值
     * 表字段 : cf_zm_config.cf_value
     * 
     */
    @ApiModelProperty(value="配置值")
    private String cfValue;

    /**
     * 中文描述
     * 表字段 : cf_zm_config.cf_desc
     * 
     */
    @ApiModelProperty(value="中文描述")
    private String cfDesc;

    /**
     * 创建时间
     * 表字段 : cf_zm_config.create_time
     * 
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * '是否删除(0:否,1:是)',
     * 表字段 : cf_zm_config.is_del
     * 
     */
    @ApiModelProperty(value="'是否删除(0:否,1:是)',")
    private Byte isDel;

    /**
     * 最后修改时间
     * 表字段 : cf_zm_config.last_modifytime
     * 
     */
    @ApiModelProperty(value="最后修改时间")
    private Date lastModifytime;
    
    
    
    public CfZmConfig(){
    	
    }
    

    
    public CfZmConfig(String systemId, String cfKey) {
		super();
		this.systemId = systemId;
		this.cfKey = cfKey;
	}



	public CfZmConfig(String systemId, String cfKey, String cfValue, String cfDesc) {
		super();
		this.systemId = systemId;
		this.cfKey = cfKey;
		this.cfValue = cfValue;
		this.cfDesc = cfDesc;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId == null ? null : systemId.trim();
    }

    public String getCfKey() {
        return cfKey;
    }

    public void setCfKey(String cfKey) {
        this.cfKey = cfKey == null ? null : cfKey.trim();
    }

    public String getCfValue() {
        return cfValue;
    }

    public void setCfValue(String cfValue) {
        this.cfValue = cfValue == null ? null : cfValue.trim();
    }

    public String getCfDesc() {
        return cfDesc;
    }

    public void setCfDesc(String cfDesc) {
        this.cfDesc = cfDesc == null ? null : cfDesc.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

    public Date getLastModifytime() {
        return lastModifytime;
    }

    public void setLastModifytime(Date lastModifytime) {
        this.lastModifytime = lastModifytime;
    }
}