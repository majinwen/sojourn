package com.ziroom.zrp.service.houses.dao;

import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.ExpenseItemDao;
import com.ziroom.zrp.trading.entity.ExpenseItemEntity;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月27日 15:08
 * @since 1.0
 */
public class ExpenseItemDaoTest extends BaseTest {

    @Resource(name="trading.expenseItemDao")
    private ExpenseItemDao expenseItemDao;

    @Test
    public void testSelectExpenseItemsByIdFids() {
        List<Integer> expenseItemFids = new ArrayList<>();
        expenseItemFids.add(1);
        expenseItemFids.add(3);
        expenseItemFids.add(2);
        List<ExpenseItemEntity> expenseItemEntityList = expenseItemDao.selectExpenseItemsByIdFids(expenseItemFids);
        System.out.println("size:" + expenseItemEntityList.size());
    }
}
