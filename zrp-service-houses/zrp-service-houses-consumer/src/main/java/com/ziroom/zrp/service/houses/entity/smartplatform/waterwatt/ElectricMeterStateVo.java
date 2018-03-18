package com.ziroom.zrp.service.houses.entity.smartplatform.waterwatt;

import com.asura.framework.base.entity.BaseEntity;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * <p>B端获取电表详情接口</p>
 * wiki-http://wiki.ziroom.com/pages/viewpage.action?pageId=341835936
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2018年01月15日
 * @version 1.0
 * @since 1.0
 */
@Data
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class ElectricMeterStateVo extends BaseEntity {

    /**
     * 设备device_id
     */
    private String uuid;

    /**
     * 绑定的电表采集器设备device_id
     */
    private String elecollectorUuid;

    /**
     * 设备MAC码
     */
    private String mac;

    /**
     * 设备SN码
     */
    private String sn;

    /**
     * 设备在线状态 1-在线 2-离线
     */
    private Integer onoffLine;

    /**
     * 设备离线时间
     */
    private Date onoffTime;

    /**
     * 设备绑定时间
     */
    private Date bindTime;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 设备型号
     */
    private String model;

    /**
     * 已经充值并且到达电表的电量
     */
    private Double powerTotal;

    /**
     * 上次充值到表的时间
     */
    private Date powerTotalTime;

    /**
     * 电表开合闸状态，1合闸，2断闸
     */
    private Integer enableState;

    /**
     * 开合闸状态更新时间
     */
    private Date enableStateTime;

    /**
     * 透支额度
     */
    private Double overdraft;

    /**
     * 透支额度设置时间
     */
    private Date overdraftTime;

    /**
     * 最大功率（咱们不修改这个，默认最大）
     */
    private Integer capacity;

    /**
     * 最大功率更新时间
     */
    private Date capacityTime;

    /**
     * 剩余电量
     */
    private Double consumeAmount;

    /**
     * 付费模式
     * 该字段是我们这边判断后加的
     * 0 "预付费" 1 "后付费"
     */
    private Integer payType;

}
