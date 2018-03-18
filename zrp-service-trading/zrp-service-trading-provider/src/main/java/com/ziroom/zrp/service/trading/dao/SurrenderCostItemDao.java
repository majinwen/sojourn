package com.ziroom.zrp.service.trading.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.service.trading.entity.SurrenderCostSumBodyVo;
import com.ziroom.zrp.trading.entity.SurrenderCostItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

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
 * @Date 2017年10月13日 15时35分
 * @Version 1.0
 * @Since 1.0
 */
@Repository("trading.surrenderCostItemDao")
public class SurrenderCostItemDao {
    private String SQLID = "com.ziroom.zrp.service.trading.dao.SurrenderCostItemDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 查询此合同已有的解约费用明细（tsurrendercostitem）
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时20分54秒
     */
    public List<SurrenderCostSumBodyVo> getCostItemsBySurCostId(String surrenderCostId) {
        return mybatisDaoContext.findAll(SQLID + "getCostItemsBySurCostId", SurrenderCostSumBodyVo.class, surrenderCostId);
    }

    /**
     * 生成tsurrendercostitem
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时21分10秒
     */
    public int insertSelective(SurrenderCostItemEntity entity) {
        return mybatisDaoContext.save(SQLID + "insertSelective", entity);
    }

    /**
     * 更新tsurrendercostitem
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时21分14秒
     */
    public int updateItemById(SurrenderCostItemEntity itemEntity) {
        return mybatisDaoContext.update(SQLID + "updateItemById", itemEntity);
    }
}
