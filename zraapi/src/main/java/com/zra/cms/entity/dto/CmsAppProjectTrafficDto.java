package com.zra.cms.entity.dto;

import java.io.Serializable;

/**
 * 项目周边交通信息实体.
 * @author cuiyh9
 */
public class CmsAppProjectTrafficDto implements Serializable {

    /**
     * 具体交通信息
     * 
     */
    private String trafficDes;
    
    /**
     * 排列顺序
     */
    private int trafficOrder;
    
    public CmsAppProjectTrafficDto() {
        
    }

    public CmsAppProjectTrafficDto(String trafficDes, int trafficOrder) {
        super();
        this.trafficDes = trafficDes;
        this.trafficOrder = trafficOrder;
    }

    public String getTrafficDes() {
        return trafficDes;
    }

    public void setTrafficDes(String trafficDes) {
        this.trafficDes = trafficDes;
    }

    public int getTrafficOrder() {
        return trafficOrder;
    }

    public void setTrafficOrder(int trafficOrder) {
        this.trafficOrder = trafficOrder;
    }
    

}