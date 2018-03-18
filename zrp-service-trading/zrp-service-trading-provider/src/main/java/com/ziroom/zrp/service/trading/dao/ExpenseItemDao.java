package com.ziroom.zrp.service.trading.dao;


import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.trading.entity.ExpenseItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository("trading.expenseItemDao")
public class ExpenseItemDao {

    private  final String SQLID = "trading.expenseItemDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 根所在费用id查询费用信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public List<ExpenseItemEntity> selectExpenseItemsByIdFids(List<Integer> expeseItemFids) {
        Map<String, Object> map = new HashMap<>();
        map.put("expeseItemFids", expeseItemFids);
        return mybatisDaoContext.findAll(SQLID + "selectExpenseItemsByIdFids", ExpenseItemEntity.class, map);
    }

    /**
     * 查询费用项根据code
     * @author jixd
     * @created 2017年10月30日 19:27:00
     * @param
     * @return
     */
    public List<ExpenseItemEntity> listExpenseByItemCodes(List<String> expenseItemCode){
        Map<String, Object> map = new HashMap<>();
        map.put("expeseItemCodes", expenseItemCode);
        return mybatisDaoContext.findAll(SQLID + "listExpenseByItemCodes", ExpenseItemEntity.class, map);
    }
}