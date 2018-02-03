package com.ziroom.minsu.services.order.test.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.house.HouseDayRevenueEntity;
import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.services.finance.dto.FinancePayVosRequest;
import com.ziroom.minsu.services.finance.entity.FinancePayVouchersVo;
import com.ziroom.minsu.services.order.dao.FinancePayVouchersDao;
import com.ziroom.minsu.services.order.entity.FinancePayDetailInfoVo;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.services.order.utils.OrderSnUtil;

public class FinancePayVouchersDaoTest extends BaseTest {

	@Resource(name = "order.financePayVouchersDao")
	private FinancePayVouchersDao payVouchersDao;



	@Test
	public void insertPayVouchersTest() {
		FinancePayVouchersEntity payVouchersEntity = new FinancePayVouchersEntity();
		payVouchersEntity.setPvSn(OrderSnUtil.getPvSn());
		payVouchersEntity.setOrderSn("8a9e9cc253e9938a0153e9938d8e0019");
		payVouchersEntity.setCityCode("BJ");
		payVouchersEntity.setReceiveUid("123456");
		payVouchersEntity.setReceiveType(1);
		payVouchersEntity.setPayUid("654321");
		payVouchersEntity.setPayType(1);
		payVouchersEntity.setPaymentType("yhfk");
		payVouchersEntity.setTotalFee(10010);
		payVouchersEntity.setAuditStatus(1);
		payVouchersEntity.setPaymentStatus(1);
		payVouchersEntity.setGenerateFeeTime(new Date());
		payVouchersEntity.setRunTime(new Date());
		
		int num = payVouchersDao.insertPayVouchers(payVouchersEntity);
		System.out.println("insertPayVouchersNum:" + num);
	}
	
	@Test
	public void findLandlordDayRevenueListTest() throws ParseException{
		Map<String, Object> paramMap=new HashMap<String, Object>();
        paramMap.put("startDate", DateUtil.parseDate(DateUtil.getDayBeforeCurrentDate() + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        paramMap.put("endDate", DateUtil.parseDate(DateUtil.getDayBeforeCurrentDate() + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		List<HouseDayRevenueEntity> list=payVouchersDao.findLandlordDayRevenueList(paramMap);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void queryFinancePayVosByPageTest(){
		
		FinancePayVosRequest financePayVosRequest = new FinancePayVosRequest();
		List<String> houseFids=new ArrayList<String>();
		houseFids.add("8a9e9a9a548b218201548b223c98000e");
		houseFids.add("8a9084df56d4eb6c0156d51a806500bd");
		financePayVosRequest.setHouseFids(houseFids);
		financePayVosRequest.setRoleType(2);
		PagingResult<FinancePayVouchersVo> listPagingResult = this.payVouchersDao.queryFinancePayVosByPage(financePayVosRequest);
		
		System.out.println(listPagingResult);
	}
	
	@Test
	public void getFinancePayDetailInfoVo() {
		FinancePayDetailInfoVo p = payVouchersDao.getFinancePayDetailInfoVo("FK160824SDQ5S7Y6163509");
		System.err.println("p:" + p);
	}

	@Test
	public void testcountHasNotPayVouchers(){
		long l = payVouchersDao.countHasNotPayVouchers("1705268AI745ZT171801");
		System.err.println(l);
	}
}
