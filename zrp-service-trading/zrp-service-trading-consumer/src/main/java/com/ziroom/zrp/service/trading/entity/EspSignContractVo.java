package com.ziroom.zrp.service.trading.entity;

/*
 * <P></P>
 * <P>
 * <PRE>
 * <BR> 修改记录
 * <BR>------------------------------------------
 * <BR> 修改日期       修改人        修改内容
 * </PRE>
 * 
 * @Author lusp
 * @Date Create in 2017年09月 19日 14:15
 * @Version 1.0
 * @Since 1.0
 */

import java.util.List;

public class EspSignContractVo extends EspResponseVo{

    private static final long serialVersionUID = -5339314190534631308L;

    private List<EspContractVo> contracts;

    public List<EspContractVo> getContracts() {
        return contracts;
    }

    public void setContracts(List<EspContractVo> contracts) {
        this.contracts = contracts;
    }
}
