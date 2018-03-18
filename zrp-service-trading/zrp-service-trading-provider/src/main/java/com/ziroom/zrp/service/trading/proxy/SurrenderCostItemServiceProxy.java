package com.ziroom.zrp.service.trading.proxy;

import com.ziroom.zrp.service.trading.entity.SurrenderCostSumBodyVo;
import com.ziroom.zrp.service.trading.service.SurrenderCostItemServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年10月15日 13时21分
 * @Version 1.0
 * @Since 1.0
 */
@Component("trading.surrenderCostItemServiceProxy")
public class SurrenderCostItemServiceProxy {
    @Resource(name = "trading.surrenderCostItemServiceImpl")
    private SurrenderCostItemServiceImpl surrenderCostItemServiceImpl;

    /**
     * 查询此合同已有的解约费用明细（tsurrendercostitem）
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时20分44秒
     */
    public List<SurrenderCostSumBodyVo> getCostItemsBySurCostId(String surrenderCostId) {
        return surrenderCostItemServiceImpl.getCostItemsBySurCostId(surrenderCostId);
    }
}
