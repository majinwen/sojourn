package com.ziroom.zrp.service.houses.entity.smartplatform.waterwatt;

import com.asura.framework.base.entity.BaseEntity;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>B端获取水表详情接口</p>
 * wiki-http://wiki.ziroom.com/pages/viewpage.action?pageId=341835936
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @version 1.0
 * @Date Created in 2018年01月15日
 * @since 1.0
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WaterMeterStateVo extends BaseEntity {

    private String manufactory;

    private String uuid;

    private String user_id;

    private String parent;

    private String type;

    private Integer check_time;

    private Integer removed;

    private String mtime;

    private String ctime;

    /**
     * 绑定时间
     */
    private String bind_time;

    /**
     * 设备在线状态 1-在线 2-离线
     */
    private Integer onoff;

    private String home_id;

    private String room_id;

    /**
     * 水表类型，1冷水表，2热水表
     */
    private Integer meter_type;

    /**
     * 付费模式
     * 该字段是我们这边判断后加的
     * 0 "预付费" 1 "后付费"
     */
    private Integer payType;

    //水表读数
    private String device_amount;

}
