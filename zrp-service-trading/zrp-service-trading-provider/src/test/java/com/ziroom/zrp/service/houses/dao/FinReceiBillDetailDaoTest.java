package com.ziroom.zrp.service.houses.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.valenum.order.FeeItemCodeEnum;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.FinReceiBillDetailDao;
import com.ziroom.zrp.service.trading.dto.RentFinReceiBillDto;
import com.ziroom.zrp.service.trading.valenum.FeeItemEnum;
import com.ziroom.zrp.trading.entity.FinReceiBillDetailEntity;
import com.zra.common.enums.BillTypeEnum;

import org.junit.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * @Date Created in 2017年10月26日 15:29
 * @since 1.0
 */
public class FinReceiBillDetailDaoTest extends BaseTest {

    @Resource(name="trading.finReceiBillDetailDao")
    private FinReceiBillDetailDao finReceiBillDetailDao;

    @Test
    public void testSelectGroupExpenseItemByContractIds() {
        List<String> contractIdList = new ArrayList<>();
        contractIdList.add("1000233");
        contractIdList.add("1000234");
        contractIdList.add("1000235");
        contractIdList.add("1000236");
        List<FinReceiBillDetailEntity> receiBillDetailEntityList = finReceiBillDetailDao.selectGroupExpenseItemByContractIds(contractIdList);
        System.out.println(":" + receiBillDetailEntityList.size());
        for (FinReceiBillDetailEntity finReceiBillDetailEntity : receiBillDetailEntityList) {
            System.out.println("" + finReceiBillDetailEntity.getRoomId() + ", " + finReceiBillDetailEntity.getExpenseItemId() + "," +  finReceiBillDetailEntity.getOughtAmount());
        }
    }

    @Test
    public void testSelectRentFinReceiBillByContractIds() {
        List<String> contractIdList = new ArrayList<>();
        contractIdList.add("1000233");
        contractIdList.add("1000234");
        contractIdList.add("1000235");
        contractIdList.add("1000236");
        List<RentFinReceiBillDto> list = finReceiBillDetailDao.selectRentFinReceiBillByContractIds(contractIdList);
        System.out.println("list:" + list.size());

    }

    @Test
    public void testSelectByBillFid() {

        String billFid = "8a90a3ab5bd1861c015be11b2fcd042f";
        List<FinReceiBillDetailEntity> list = this.finReceiBillDetailDao.selectByBillFid(billFid);
        System.out.println("list:" + list);

    }

    @Test
    public void testSelectByReceiBillDetailFid() {
        String receiBillDetailFid = "a86085a5b7794483b7fc6525fe993f78";
        FinReceiBillDetailEntity finReceiBillDetailEntity = this.finReceiBillDetailDao.selectByReceiBillDetailFid(receiBillDetailFid);
        System.out.println("FinReceiBillDetailEntity:" + finReceiBillDetailEntity);
    }
    
    @Test
    public void getFinReceiBillByContractId(){
    	List<FinReceiBillDetailEntity> list =finReceiBillDetailDao.getFinReceiBillByContractId("8a9091bd61031474016108361500001d", FeeItemEnum.WATER_FEE.getItemFid());
    	System.err.println(JsonEntityTransform.Object2Json(list));
    }
    @Test
    public void testSelectOutTimeFinReceiBill(){
        List<FinReceiBillDetailEntity> finReceiBillDetailEntitys = this.finReceiBillDetailDao.selectOutTimeFinReceiBillDetail();
        for (FinReceiBillDetailEntity finReceiBillDetailEntity : finReceiBillDetailEntitys){
            System.out.println(JsonEntityTransform.Object2Json(finReceiBillDetailEntity));
        }
    }
}
