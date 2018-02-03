package com.ziroom.minsu.services.order.dto;

import com.ziroom.minsu.entity.conf.GlobalConfigEntity;

/**
 * <p>带有配置项</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author afi
 * @since 1.0
 * @version 1.0
 */
public class ConfigBaseRequest {

    /**
     * 全局的配置
     */
    private GlobalConfigEntity globalConfigEntity = new GlobalConfigEntity();


    public GlobalConfigEntity getGlobalConfigEntity() {
        return globalConfigEntity;
    }

    public void setGlobalConfigEntity(GlobalConfigEntity globalConfigEntity) {
        this.globalConfigEntity = globalConfigEntity;
    }
}
