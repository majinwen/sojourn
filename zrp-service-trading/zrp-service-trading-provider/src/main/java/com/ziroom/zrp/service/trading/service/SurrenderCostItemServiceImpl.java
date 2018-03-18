package com.ziroom.zrp.service.trading.service;

import com.ziroom.zrp.service.trading.dao.SurMeterDetailDao;
import com.ziroom.zrp.service.trading.dao.SurrenderCostItemDao;
import com.ziroom.zrp.service.trading.entity.SurrenderCostSumBodyVo;
import com.ziroom.zrp.trading.entity.SurrenderCostItemEntity;
import org.springframework.stereotype.Service;

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
 * @Date 2017年10月13日 15时37分
 * @Version 1.0
 * @Since 1.0
 */
@Service("trading.surrenderCostItemServiceImpl")
public class SurrenderCostItemServiceImpl {
    @Resource(name = "trading.surrenderCostItemDao")
    private SurrenderCostItemDao surrenderCostItemDao;

    /**
     * 查询此合同已有的解约费用明细（tsurrendercostitem）
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日
     */
    public List<SurrenderCostSumBodyVo> getCostItemsBySurCostId(String surrenderCostId) {
        return surrenderCostItemDao.getCostItemsBySurCostId(surrenderCostId);
    }
}
