package com.ziroom.zrp.service.trading.dto;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.zrp.trading.entity.RentCheckinPersonEntity;
import com.ziroom.zrp.trading.entity.SharerEntity;
import lombok.Data;

import java.util.List;

/**
 * <p>入住人和合住人信息</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年10月28日 11:55
 * @since 1.0
 */
@Data
public class PersonAndSharerDto extends BaseEntity {
    private String rentedetailId;
    private String oldUid;
	private RentCheckinPersonEntity rentCheckinPersonEntity;
	private List<SharerEntity> sharerEntities;

}
