package com.ziroom.zrp.service.trading.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.service.trading.service.SurrenderCostServiceImpl;
import com.ziroom.zrp.trading.entity.SurrenderCostEntity;
import com.ziroom.zrp.trading.entity.SurrenderCostItemEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
 * @Date 2017年10月15日 13时20分
 * @Version 1.0
 * @Since 1.0
 */
@Component("trading.surrenderCostServiceProxy")
public class SurrenderCostServiceProxy {
	 private static final Logger LOGGER = LoggerFactory.getLogger(SurrenderCostServiceProxy.class);
    @Resource(name = "trading.surrenderCostServiceImpl")
    private SurrenderCostServiceImpl surrenderCostServiceImpl;

    /**
     * 更新tsurrendercostitem、删除tsurrendercost（需要保证如果costItem没有数据，cost也应该没有）
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时19分58秒
     */
    public void deleteCostAndUpdateCostItem(List<String> needDeleteList, List<SurrenderCostItemEntity> needUpdateList) {
        surrenderCostServiceImpl.deleteCostAndUpdateCostItem(needDeleteList, needUpdateList);
    }
}
