package com.ziroom.zrp.service.trading.dto.waterwatt;

import com.zra.common.dto.base.BasePageParamDto;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * <p>抄表记录dto</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2018年02月26日
 * @version 1.0
 * @since 1.0
 */
@Data
public class IntellectWatermeterReadDto extends BasePageParamDto{

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 项目id集合
     */
    private List<String> projectIds;

    /**
     * 抄表状态
     * @see com.ziroom.zrp.service.trading.valenum.waterwatt.WaterwattReadStatusEnum
     */
    private Integer readStatus;
}
