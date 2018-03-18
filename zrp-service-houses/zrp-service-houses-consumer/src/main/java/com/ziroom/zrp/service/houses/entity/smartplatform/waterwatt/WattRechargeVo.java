package com.ziroom.zrp.service.houses.entity.smartplatform.waterwatt;

import com.asura.framework.base.entity.BaseEntity;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>智能充电 返回vo</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2018年01月25日 20:40
 * @since 1.0
 */
@Data
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class WattRechargeVo extends BaseEntity {

    /**
     * 设备uuid
     */
    private  String deviceUuid;

    /**
     * 接口返回信息no
     */
    private  String errNo;


    /**
     * 唯一请求标识
     */
    private  String serviceId;

    /**
     *
     */
    private  String serviceOpr;


    /**
     * 基本信息
     */
    private  IntellectWattBaseVo data;

    public String getDeviceUuid() {
        return deviceUuid;
    }

    public void setDeviceUuid(String deviceUuid) {
        this.deviceUuid = deviceUuid;
    }

    public String getErrNo() {
        return errNo;
    }

    public void setErrNo(String errNo) {
        this.errNo = errNo;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceOpr() {
        return serviceOpr;
    }

    public void setServiceOpr(String serviceOpr) {
        this.serviceOpr = serviceOpr;
    }

    public IntellectWattBaseVo getData() {
        return data;
    }

    public void setData(IntellectWattBaseVo data) {
        this.data = data;
    }
}
