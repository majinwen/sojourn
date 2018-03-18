package com.ziroom.zrp.service.trading.dto.smarthydropower;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author xiangbin
 * @version 1.0
 * @Date Created in 2018年02月27日 10:00
 * @since 1.0
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LowLevelDeviceUuid {
    /**
     * 位置级别一 友家就是houseId 自如寓是项目id
     */
    private String positionRank1;
    /**
     * 位置级别二 友家roomId 自如寓房间id
     */
    private String positionRank2;
    /**
     * 位置级别三
     */
    private String positionRank3;
    /**
     * 位置级别四
     */
    private String positionRank4;
    /**
     * 位置级别五
     */
    private String positionRank5;
}
