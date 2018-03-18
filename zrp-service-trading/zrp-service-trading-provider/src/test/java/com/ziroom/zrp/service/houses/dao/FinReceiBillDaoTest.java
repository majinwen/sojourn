package com.ziroom.zrp.service.houses.dao;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.FinReceiBillDao;
import com.ziroom.zrp.trading.entity.FinReceiBillEntity;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年10月13日 20:05
 * @since 1.0
 */

public class FinReceiBillDaoTest extends BaseTest{

    @Resource(name = "trading.finReceiBillDao")
    FinReceiBillDao finReceiBillDao;

    @Test
    public void testSelectPayBillSeq(){
        int num = finReceiBillDao.selectPayBillSeq();
        System.err.println(num);
    }

    @Test
    public void testGetReceiptContractByDate() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("startDate", "2018-01-15");
        paramMap.put("endDate", "2018-02-15");
        //paramMap.put("projectIds", new String[]{"19"});
        System.err.println(JsonEntityTransform.Object2Json(this.finReceiBillDao.getReceiptContractByDate(paramMap)));
    }

    @Test
    public void testSelectByContractId() {
        String contractId = "2c908d194f6e5e5f014f976c48a20408";
        List<FinReceiBillEntity> list = this.finReceiBillDao.selectByContractId(contractId);
        System.out.println("list:" + list.size());
    }

    @Test
    public void testSelectByFid() {
        String fid = "00199ca5a51b4c80bedbc9442bfd6b6e";
        FinReceiBillEntity finReceiBillEntity = this.finReceiBillDao.selectByFid(fid);
        System.out.println("finReceiBillEntity:" + finReceiBillEntity);
    }

    @Test
    public void testdeleteReceiptBillByFid(){
        List<String> fids = new ArrayList<>();
        fids.add("8a90a3ab5969d70e01596a24a8fc0070");
        fids.add("4d2380c8e1074fb0a9e3a96a2134c822");
        int i = this.finReceiBillDao.deleteReceiptBillByFid(fids);
        System.err.println("==========="+i+"===================");
    }
}
