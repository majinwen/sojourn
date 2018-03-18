package com.ziroom.zrp.service.houses.dao;


import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.ReceiptDao;
import com.ziroom.zrp.trading.entity.ReceiptEntity;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;


public class ReceiptDaoTest extends BaseTest{

	@Resource(name = "trading.receiptDao")
	private ReceiptDao receiptDao;

	@Test
	public void testSave(){
		ReceiptEntity receiptEntity = new ReceiptEntity();
		receiptEntity.setBillNum("fjldksjfjdls");
		receiptEntity.setAmount(23);
		receiptEntity.setPaySerialNum("fkljdslkfj");
		receiptEntity.setPayType("3");
		receiptEntity.setPayTime("");
		receiptEntity.setReceiptMothed("4");
		receiptEntity.setPayer("测试用户");
		receiptEntity.setPosId("78979797673");
		receiptEntity.setReferenceNum("jflkdsjfjl7979");
		receiptEntity.setCheckNumber("8980980783753979");
		receiptEntity.setMakerCode("20015356");
		receiptEntity.setMakerDept("管家部");
		receiptEntity.setMakerName("测试管家");
		receiptEntity.setConfirmStatus(0);
		receiptEntity.setReceiptStatus(0);
		receiptEntity.setAccountFlag(0);
		receiptEntity.setAttach("北京自如寓");
		int result = receiptDao.saveReceipt(receiptEntity);
		System.err.println(result);
	}

	@Test
	public void testUpdate(){
		ReceiptEntity receiptEntity = new ReceiptEntity();
		receiptEntity.setBillNum("fjldksjfjdls");
		receiptEntity.setReceiptStatus(1);
		int result = receiptDao.updateByBillNum(receiptEntity);
		System.err.println(result);
	}

	@Test
	public void testcountReceiptByContractId(){
		long count = receiptDao.countReceiptByContractId("8a908e1060018abc0160018c1fca0002");
		System.err.println(count);
	}

}
