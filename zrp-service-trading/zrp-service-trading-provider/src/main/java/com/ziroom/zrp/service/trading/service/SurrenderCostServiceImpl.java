package com.ziroom.zrp.service.trading.service;

import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.service.trading.dao.SurrenderCostDao;
import com.ziroom.zrp.service.trading.dao.SurrenderCostItemDao;
import com.ziroom.zrp.trading.entity.SurrenderCostEntity;
import com.ziroom.zrp.trading.entity.SurrenderCostItemEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
 * @Date 2017年10月13日 15时30分
 * @Version 1.0
 * @Since 1.0
 */
@Service("trading.surrenderCostServiceImpl")
public class SurrenderCostServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(SurrenderCostServiceImpl.class);

    @Resource(name = "trading.surrenderCostDao")
    private SurrenderCostDao surrenderCostDao;

    @Resource(name = "trading.surrenderCostItemDao")
    private SurrenderCostItemDao surrenderCostItemDao;

    /**
     * 更新tsurrendercostitem、删除tsurrendercost（需要保证如果costItem没有数据，cost也应该没有）
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日
     */
    public void deleteCostAndUpdateCostItem(List<String> needDeleteList, List<SurrenderCostItemEntity> needUpdateList) {
        //删除tsurrendercost
        long count = needDeleteList.stream().distinct().count();
        if (count > 0) {
            List<String> list = needDeleteList.stream().distinct().collect(Collectors.toList());
            int i = surrenderCostDao.deleteBatch(list);
            LogUtil.info(LOGGER, "[解约费用结算]costIds={}，tsurrendercost更新{} 条。", list.stream().collect(Collectors.joining(",")), i);
        }
        //更新tsurrendercostitem
        needUpdateList.forEach(p -> {
            int j = surrenderCostItemDao.updateItemById(p);
            LogUtil.info(LOGGER, "[解约费用结算]更新tsurrendercostitem，costItemId={}，更新 = {} 条。", p.getFid(), j);
        });
    }
    /**
     * <p>根据合同ID和解约ID查询</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    public List<SurrenderCostEntity> selectSurrenderCostByParam(String contractId,String surrenderId){
    	return surrenderCostDao.selectSurrenderCostByParam(contractId, surrenderId);
    }
    /**
     * <p>根据surrendercostId查询</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    public SurrenderCostEntity findSurrenderCostByFid(String surrendercostId){
    	return surrenderCostDao.findSurrenderCostByFid(surrendercostId);
    }
    /**
     * <p>更新SurrenderCostEntity</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    public int updateBySurId(SurrenderCostEntity surrenderCostEntity){
    	return surrenderCostDao.updateBySurId(surrenderCostEntity);
    }
}
