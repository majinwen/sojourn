package com.ziroom.zrp.service.trading.dto.smarthydropower;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>低电量回调类</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author xiangbin
 * @version 1.0
 * @Date Created in 2018年02月27日 09:24
 * @since 1.0
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LowLevelCallBackDto {

    private Integer code;//返回code码, 0成功 其他失败
    private String message;//描述信息
    private LowLevelData data;//返回参数

}
