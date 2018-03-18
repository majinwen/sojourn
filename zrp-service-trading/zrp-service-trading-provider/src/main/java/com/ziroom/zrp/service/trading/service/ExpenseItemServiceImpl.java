package com.ziroom.zrp.service.trading.service;

import com.ziroom.zrp.service.trading.dao.ExpenseItemDao;
import com.ziroom.zrp.trading.entity.ExpenseItemEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
 * @Date Created in 2017年10月27日 15:29
 * @since 1.0
 */
@Service("trading.expenseItemServiceImpl")
public class ExpenseItemServiceImpl {

    @Resource(name="trading.expenseItemDao")
    private ExpenseItemDao expenseItemDao;

    /**
     * 根所在费用id查询费用信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public List<ExpenseItemEntity> selectExpenseItemsByIdFids(List<Integer> expenseItemFids) {
        if (expenseItemFids == null || expenseItemFids.isEmpty()) {
            return null;
        }
        List<ExpenseItemEntity> expenseItemEntityList = expenseItemDao.selectExpenseItemsByIdFids(expenseItemFids);
        return expenseItemEntityList;
    }


    /**
     * 查询费用项列表
     * @author jixd
     * @created 2017年10月30日 19:28:31
     * @param
     * @return
     */
    public List<ExpenseItemEntity> listExpenseByItemCodes(List<String> expenseItemCodes){
        return expenseItemDao.listExpenseByItemCodes(expenseItemCodes);
    }
}
