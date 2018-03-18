package com.ziroom.zrp.service.trading.entity;

import com.asura.framework.base.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * <p>物业交割提示信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月28日 16:28
 * @since 1.0
 */
@Data
public class DeliveryContractNotifyVo extends BaseEntity{

    private static final long serialVersionUID = 8009587690595661096L;

    private String contractId;

    private String roomId;

    private String projectName;

    private String roomName;

    private String contractCode;

    private String customerName;

    private String customerUid;

    private String customerMobile;

    private String zoName;

    private String zoCode;

    private Date firstPayTime;

}
