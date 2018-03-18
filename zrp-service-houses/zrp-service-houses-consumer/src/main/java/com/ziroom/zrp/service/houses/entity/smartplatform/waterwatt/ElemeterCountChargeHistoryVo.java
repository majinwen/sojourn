package com.ziroom.zrp.service.houses.entity.smartplatform.waterwatt;

import com.asura.framework.base.entity.BaseEntity;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * <p>B端获取智能电表设备充值记录</p>
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
public class ElemeterCountChargeHistoryVo extends BaseEntity {

    private String room_id;

    private String devId;

    private String uuid;

    /**
     * 此次充电电量
     */
    private Double amount;

    /**
     * 总充电电量
     */
    private Double total_amount;

    private Date time;

}
