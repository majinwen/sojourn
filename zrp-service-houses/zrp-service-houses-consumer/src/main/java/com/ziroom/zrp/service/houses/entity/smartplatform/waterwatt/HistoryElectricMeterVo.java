package com.ziroom.zrp.service.houses.entity.smartplatform.waterwatt;

import com.asura.framework.base.entity.BaseEntity;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * <p>B端获取智能电表抄表历史</p>
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
public class HistoryElectricMeterVo extends BaseEntity {

    private String home_id;

    private String room_id;

    /**
     * 抄表时间
     */
    private Date time;

    /**
     * 充值电量，同power_total
     */
    private Double total_amount;

    private String uuid;

    private String devId;

    /**
     * 用电量
     */
    private Double consume_amount;

}
