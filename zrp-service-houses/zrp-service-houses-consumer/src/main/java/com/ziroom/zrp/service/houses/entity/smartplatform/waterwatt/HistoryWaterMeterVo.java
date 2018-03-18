package com.ziroom.zrp.service.houses.entity.smartplatform.waterwatt;

import com.asura.framework.base.entity.BaseEntity;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * <p>B端水表抄表历史</p>
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
public class HistoryWaterMeterVo extends BaseEntity {

    /**
     * 水表类型，1冷水表，2热水表
     */
    private Integer type;

    private String uuid;

    private String manufactory;

    /**
     * 水表读数
     */
    private Double device_amount;

    private Date ctime;

    /**
     * 房间用水量
     */
    private Double room_amount;
}
