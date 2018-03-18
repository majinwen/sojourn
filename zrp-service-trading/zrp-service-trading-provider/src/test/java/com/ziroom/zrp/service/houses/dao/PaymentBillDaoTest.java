package com.ziroom.zrp.service.houses.dao;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.PaymentBillDao;
import com.ziroom.zrp.service.trading.dao.PaymentBillDetailDao;
import com.ziroom.zrp.service.trading.dto.finance.PayVoucherDetailReqDto;
import com.ziroom.zrp.trading.entity.PaymentBillDetailEntity;
import com.ziroom.zrp.trading.entity.PaymentBillEntity;
import com.zra.common.utils.DateUtilFormate;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年10月19日 15:06
 * @since 1.0
 */
public class PaymentBillDaoTest extends BaseTest{
	@Resource(name = "trading.paymentBillDao")
	private PaymentBillDao paymentBillDao;

	@Resource(name = "trading.paymentBillDetailDao")
	private PaymentBillDetailDao paymentBillDetailDao;


	@Test
	public void testSavePaymentBill() {
		//构建保存数据
		PaymentBillEntity paymentBillEntity = new PaymentBillEntity();
		List<PaymentBillEntity> paymentBillEntityList = new ArrayList<>();
		String fid = UUIDGenerator.hexUUID();
		paymentBillEntity.setFid(fid);
		paymentBillEntity.setPaySerialNum("12313123131313");
		paymentBillEntity.setOutContract("BJ1012312313");
		paymentBillEntity.setSourceCode("xxzf");
		paymentBillEntity.setPayTime(DateUtilFormate.formatStringToDate("2017-10-10 12:00:00"));
		paymentBillEntity.setAccountFlag(2);
		paymentBillEntity.setBillType("C");
		paymentBillEntity.setUid("1231313");
		paymentBillEntity.setAuditFlag(4);
		paymentBillEntity.setPayFlag(2);
		paymentBillEntity.setGenWay(0);// 自动生成
		paymentBillEntity.setBusId("BJ1012312313" + "_" + System.currentTimeMillis());
		paymentBillEntity.setCreateDate(new Date());
		paymentBillEntity.setPanymentTypeCode("zhdjzye");// zhdjzye
		paymentBillEntity.setMarkCollectCode("yzrk");
		paymentBillEntityList.add(paymentBillEntity);

		PaymentBillDetailEntity paymentBillDetailEntity;
		List<PaymentBillDetailEntity> paymentBillDetailEntityList = new ArrayList<>();
		List<PayVoucherDetailReqDto> list = new ArrayList<>();

		PayVoucherDetailReqDto detailReqDto = new PayVoucherDetailReqDto();
		detailReqDto.setCostCode("zrysf");
		detailReqDto.setRefundAmount(BigDecimal.valueOf(100).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
		list.add(detailReqDto);

		paymentBillDetailEntity = new PaymentBillDetailEntity();
		paymentBillDetailEntity.setBillFid(fid);
		paymentBillDetailEntity.setCostCode("zrysf");
		paymentBillDetailEntity.setCreateDate(new Date());
		paymentBillDetailEntity.setFid(UUIDGenerator.hexUUID());
		paymentBillDetailEntity.setRefundAmount(detailReqDto.getRefundAmount());
		paymentBillDetailEntityList.add(paymentBillDetailEntity);

		this.paymentBillDao.savePaymentBill(paymentBillEntityList);
		this.paymentBillDetailDao.savePaymentBillDetail(paymentBillDetailEntityList);
	}

	@Test
	public void testUpdatePaymentSyncStatus() {
		PaymentBillEntity paymentBillEntity = new PaymentBillEntity();
		paymentBillEntity.setSynFinance(0);
		paymentBillEntity.setFid("8a9e98c65f33835c015f33835ce10000");
		int affect = this.paymentBillDao.updatePaymentSyncStatus(paymentBillEntity);
		System.err.println(affect);
	}
}
