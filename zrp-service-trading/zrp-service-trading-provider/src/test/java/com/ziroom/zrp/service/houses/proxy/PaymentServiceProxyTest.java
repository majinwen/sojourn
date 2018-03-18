package com.ziroom.zrp.service.houses.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.RentContractDao;
import com.ziroom.zrp.service.trading.dto.PaymentTermsDto;
import com.ziroom.zrp.service.trading.proxy.PaymentServiceProxy;
import com.ziroom.zrp.service.trading.proxy.commonlogic.FinanceCommonLogic;
import com.ziroom.zrp.service.trading.utils.DateConvertUtils;
import com.ziroom.zrp.trading.entity.RentContractEntity;

import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
 * @Date Created in 2017年09月25日 15:24
 * @since 1.0
 */
public class PaymentServiceProxyTest extends BaseTest {

	@Resource(name = "trading.paymentServiceProxy")
	private PaymentServiceProxy proxy;

	@Resource(name = "trading.rentContractDao")
	private RentContractDao rentContractDao;

	@Resource(name="trading.financeCommonLogic")
	private FinanceCommonLogic financeCommonLogic;

	@Test
	public void getPaymentDetail() {

		ProjectEntity projectEntity = new ProjectEntity();
		projectEntity.setFaddress("项目地址");
		projectEntity.setFHeadFigureUrl("http://pic.ziroom.com/");
		projectEntity.setFname("项目名称");

		long start = System.currentTimeMillis();
		System.err.println(proxy.getPaymentDetail("123", "8a90930060c02db40160c0e3a0fc0402"));
		System.err.println(System.currentTimeMillis() - start);
	}

	@Test
	public void getPaymentItems(){
		RentContractEntity rentContractEntity = rentContractDao.findContractBaseByContractId("8a908e10604fa6a301604fddcecd00d4");
		DataTransferObject dto = null;
		try {
			dto = financeCommonLogic.getPaymentItems(rentContractEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		PaymentTermsDto paymentTermsDto = (PaymentTermsDto) dto.getData().get("items");
		System.err.println(dto);
	}

	@Test
	public void testFindBillListByContractId() {
		System.err.println(this.proxy.findBillListByContractId("8a908e105f9f738e015fa0b3c655007d", 1));
	}

	@Test
	public void testFindPayPageDetail() {
		System.err.println(this.proxy.findPayPageDetail("8a9091bd5f9439c7015f9439c76c0001", 1,"1007"));
	}

	@Test
	public  void testFindBillDetail() {
		String billDetail = proxy.findBillPayDetail("8a9e98b75f33d1ab015f37b04c34003f", 1);
		System.err.println(billDetail);
	}
	@Test
	public void testValidPayForFinance() {
		Map<String, Object> map = new HashMap<>();
		map.put("billNums", "10052017010405459");
		System.err.println(this.proxy.validPayForFinance(JsonEntityTransform.Object2Json(map)));
	}
	@Test
	public  void testFindHistoryLifeFeeList() {
		System.err.println(this.proxy.findHistoryLifeFeeList("8a9091bd5f958136015f95846d310025"));
	}

	@Test
	public void testGetMustPayBillList(){
		System.err.print(this.proxy.getMustPayBillList("dd0ae2f5-ce32-4b6a-a94d-905fe77fa3ab"));
	}

	@Test
	public void testDate() {
		System.err.println(DateConvertUtils.asUtilDate("2017-11-01 10:11:11".replaceAll("\\s+","T")));
	}

}

