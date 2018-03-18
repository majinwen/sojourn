package com.ziroom.zrp.service.trading.dto.smarthydropower;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>低电量回调参数</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author xiangbin
 * @version 1.0
 * @Date Created in 2018年02月27日 09:37
 * @since 1.0
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LowLevelData {

    private LowLevelDetail detail;//电表示数相关
    private LowLevelDeviceUuid deviceUuid;//设备维度相关
    private String event;//事件dbLowlevelAlarm
    private String time;//时间
}
