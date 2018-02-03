package com.ziroom.minsu.services.order.test.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.order.FinanceIncomeEntity;
import com.ziroom.minsu.services.finance.dto.FinanceIncomeRequest;
import com.ziroom.minsu.services.finance.entity.FinanceIncomeVo;
import com.ziroom.minsu.services.order.dao.FinanceIncomeDao;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.services.order.utils.OrderSnUtil;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.order.IncomeSourceTypeEnum;
import com.ziroom.minsu.valenum.order.IncomeStatusEnum;
import com.ziroom.minsu.valenum.order.IncomeTypeEnum;

/**
 * <p>财务收入测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/22.
 * @version 1.0
 * @since 1.0
 */
public class FinanceIncomeDaoTest extends BaseTest{

    @Resource(name = "order.financeIncomeDao")
    private FinanceIncomeDao financeIncomeDao;


    @Test
    public void deleteIncomeByIncomeSnList() {
        List<String> list = new ArrayList<>();
        list.add("SR160511R95YQX0X215654");
        int num = financeIncomeDao.deleteIncomeByIncomeSnList(list);
        System.out.println("deleteIncomeByIncomeSnListNum:" + num);
    }

    @Test
    public void insertFinanceIncomeTest(){
    	FinanceIncomeEntity financeIncomeEntity = new FinanceIncomeEntity();
    	financeIncomeEntity.setIncomeSn(OrderSnUtil.getIncomeSn());
    	financeIncomeEntity.setIncomeSourceType(IncomeSourceTypeEnum.TASK.getCode());
    	financeIncomeEntity.setIncomeType(IncomeTypeEnum.LANDLORD_RENT_COMMISSION.getCode());
    	financeIncomeEntity.setOrderSn("123");
    	financeIncomeEntity.setCityCode("BJ");
    	financeIncomeEntity.setPayUid("321");
    	financeIncomeEntity.setTotalFee(100);
    	financeIncomeEntity.setIncomeStatus(100);
    	financeIncomeEntity.setGenerateFeeTime(new Date());
    	financeIncomeEntity.setRunTime(new Date());
    	
    	int num = financeIncomeDao.insertFinanceIncome(financeIncomeEntity);
    	System.out.println("num:"+num);
    }
    
    @Test
    public void updateIncomeLandlordPunishTest(){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("orderSn", "1605022FDZ6352190804");
    	map.put("isSend", YesOrNoEnum.NO.getCode());
    	int num = financeIncomeDao.updateIncomeLandlordPunish(map);
    	System.out.println("num:"+num);
    }
    
    @Test
    public void queryFinanceIncomeByPageTest(){
    	
    	FinanceIncomeRequest financeIncomeRequest = new FinanceIncomeRequest();
    	
    	PagingResult<FinanceIncomeVo> listPagingResult = this.financeIncomeDao.queryFinanceIncomeByPage(financeIncomeRequest);
    	
    	System.out.println(listPagingResult);
    }
    
    @Test
    public void getNotIncomeCountTest(){
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("incomeStatus", IncomeStatusEnum.NO.getCode());
		List<Integer> incomeTypeList = new ArrayList<Integer>();
		incomeTypeList.add(IncomeTypeEnum.LANDLORD_PUNISH_COMMISSION.getCode());
		incomeTypeList.add(IncomeTypeEnum.LANDLORD_PUNISH.getCode());
		map.put("incomeTypeList", incomeTypeList);
		map.put("isSend", YesOrNoEnum.NO.getCode());
    	
    	Long count = financeIncomeDao.getNotIncomeCount(map);
    	System.err.println("count:"+count);
    }
    
    
    @Test
    public void getNotIncomeListTest(){
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("incomeStatus", IncomeStatusEnum.NO.getCode());
		List<Integer> incomeTypeList = new ArrayList<Integer>();
		incomeTypeList.add(IncomeTypeEnum.LANDLORD_PUNISH_COMMISSION.getCode());
		incomeTypeList.add(IncomeTypeEnum.LANDLORD_PUNISH.getCode());
		map.put("incomeTypeList", incomeTypeList);
		map.put("isSend", YesOrNoEnum.NO.getCode());
		map.put("limit", 20);
		List<FinanceIncomeEntity> list = financeIncomeDao.getNotIncomeList(map);
		System.err.println("list:"+list);
    }
    
    @Test
    public void consumeIncome(){
    	FinanceIncomeEntity incomeEntity = new FinanceIncomeEntity();
		incomeEntity.setIncomeSn("SR1605229Q5A4DG4152100");
		incomeEntity.setIncomeStatus(IncomeStatusEnum.YES.getCode());
		incomeEntity.setActualIncomeTime(new Date());
		//incomeEntity.setIsSend(YesOrNoEnum.YES.getCode());
		int i = financeIncomeDao.consumeIncome(incomeEntity);
		System.err.println(i);
    }
    
}
