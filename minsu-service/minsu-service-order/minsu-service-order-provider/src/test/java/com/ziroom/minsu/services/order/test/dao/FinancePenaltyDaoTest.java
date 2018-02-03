package com.ziroom.minsu.services.order.test.dao;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.order.FinancePenaltyEntity;
import com.ziroom.minsu.entity.order.FinancePenaltyLogEntity;
import com.ziroom.minsu.services.order.dao.FinancePenaltyDao;
import com.ziroom.minsu.services.order.dao.FinancePenaltyLogDao;
import com.ziroom.minsu.services.order.dao.FinancePenaltyPayRelDao;
import com.ziroom.minsu.services.order.dto.PenaltyRequest;
import com.ziroom.minsu.services.order.entity.FinancePenaltyPayRelVo;
import com.ziroom.minsu.services.order.entity.PenaltyVo;
import com.ziroom.minsu.services.order.test.base.BaseTest;

import org.junit.Test;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd on 2017/5/10.
 * @version 1.0
 * @since 1.0
 */
public class FinancePenaltyDaoTest extends BaseTest {

    @Resource(name = "order.financePenaltyDao")
    private FinancePenaltyDao financePenaltyDao;
    

    @Resource(name = "order.financePenaltyPayRelDao")
    private FinancePenaltyPayRelDao financePenaltyPayRelDao;
    
    @Resource(name = "order.financePenaltyLogDao")
    private FinancePenaltyLogDao financePenaltyLogDao;


    @Test
    public void testSave(){
        FinancePenaltyEntity financePenaltyEntity = new FinancePenaltyEntity();
        financePenaltyEntity.setOrderSn("ddddd");
        financePenaltyEntity.setPenaltyFee(300);
        financePenaltyEntity.setPenaltySn("dfdf");
        financePenaltyEntity.setPenaltyStatus(1);
        financePenaltyEntity.setPenaltyType(1);
        financePenaltyEntity.setPenaltyLastFee(300);
        financePenaltyEntity.setPenaltyUserType(1);
        financePenaltyEntity.setRemark("dsfsdf");
        financePenaltyEntity.setPenaltyUid("dfsdfsdf");
        financePenaltyDao.saveFinancePenalty(financePenaltyEntity);
    }

    @Test
    public void testListFinancePenaltyByUid(){
        List<FinancePenaltyEntity> dfsdfsdf = financePenaltyDao.listAvaliableFinancePenaltyByUid("dfsdfsdf");
    }

    @Test
    public void testupdateFinancePenaltyByOrderSn(){
        FinancePenaltyEntity financePenaltyEntity = new FinancePenaltyEntity();
        financePenaltyEntity.setOrderSn("ddddd");
        financePenaltyEntity.setPenaltyStatus(2);
        financePenaltyEntity.setPenaltyLastFee(100);
        financePenaltyDao.updateFinancePenaltyByOrderSn(financePenaltyEntity);
    }

    @Test
    public void findLanFinancePenaltyByOrderSn(){

        FinancePenaltyEntity financePenaltyEntity = financePenaltyDao.findLanFinancePenaltyByOrderSn("1605249U5E18VG160535");
        System.out.println(financePenaltyEntity);
    }

    @Test
    public void testlistPenaltyPageByCondition(){
        PenaltyRequest penaltyRequest = new PenaltyRequest();
        PagingResult<PenaltyVo> pagingResult = financePenaltyDao.listPenaltyPageByCondition(penaltyRequest);
    }
    
    @Test
    public void findFinancePenaltyPayRelVoByPvOrderSn(){
    	
    	List<FinancePenaltyPayRelVo> list = financePenaltyPayRelDao.findFinancePenaltyPayRelVoByPvOrderSn("170220KFRM5Y75152006");
    	
    	System.out.println(list);
    }

    @Test
    public void testcountAvaliableFinancePenaltyByUid(){
        long l = financePenaltyDao.countAvaliableFinancePenaltyByUid("4b37c562-6ce9-7065-e9e6-aab2182cdbc4");
        System.err.println(l);
    }
    
    @Test
    public void testSaveLog(){
        FinancePenaltyLogEntity financePenaltyLogEntity = new FinancePenaltyLogEntity();
        financePenaltyLogEntity.setCreateTime(new Date());
        financePenaltyLogEntity.setEmpCode("dfds");
        financePenaltyLogEntity.setEmpName("dfds");
        financePenaltyLogEntity.setFid("dfds");
        financePenaltyLogEntity.setFromFee(10);
        financePenaltyLogEntity.setFromStatus(10);
        financePenaltyLogEntity.setPenaltySn("dfds");
        financePenaltyLogEntity.setRemark("客服张金锦申请作废：171027NVX7U3M8230107     大望路国贸CBD中心区域 高档国际公寓LOFT独立卧室    房客：陈思多18645746665  房东：杨思思13810663179   这个订单客服取消订单的时候 误操作点选生成罚款单，麻烦您给删除一下扣罚单");
        financePenaltyLogEntity.setToFee(1);
        financePenaltyLogEntity.setToStatus(1);
        financePenaltyLogDao.saveFinancePenaltyLog(financePenaltyLogEntity);
    }
}
