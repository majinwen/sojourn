package com.ziroom.zrp.service.houses.proxy;

/*
 * <P></P>
 * <P>
 * <PRE>
 * <BR> 修改记录
 * <BR>------------------------------------------
 * <BR> 修改日期       修改人        修改内容
 * </PRE>
 * 
 * @Author lusp
 * @Date Create in 2017年09月 21日 17:40
 * @Version 1.0
 * @Since 1.0
 */

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dto.PaymentBillsDto;
import com.ziroom.zrp.service.trading.dto.finance.*;
import com.ziroom.zrp.service.trading.proxy.CallFinanceServiceProxy;
import com.ziroom.zrp.trading.entity.RentContractEntity;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CallFinanceServiceProxyTest extends BaseTest{

    @Resource(name="trading.callFinanceServiceProxy")
    private CallFinanceServiceProxy callFinanceServiceProxy;

    @Test
    public void getReceivableBillNumTest(){
        String result = callFinanceServiceProxy.createBillNum("1007");
    }

    @Test
    public void getReceivableBillNumBatchTest(){
        String result = callFinanceServiceProxy.createBillNumBatch("1007",10);
    }

    @Test
    public void syncContractTest(){
        RentContractEntity rentContractEntity = new RentContractEntity();
        rentContractEntity.setContractId("8a9099cb5de48f08015e2379ea291dc0");
        String result = callFinanceServiceProxy.syncContract(JsonEntityTransform.Object2Json(rentContractEntity));

    }

    @Test
    public void updateContractTest(){
        RentContractEntity rentContractEntity = new RentContractEntity();
        rentContractEntity.setContractId("8a9e98b75f33d1ab015f37af36c30023");
        String result = callFinanceServiceProxy.updateContract(JsonEntityTransform.Object2Json(rentContractEntity));

    }

    @Test
    public void testJsonObject(){
        String str = null;
        JSONObject jsonObject = JSONObject.parseObject(str);
        String aaa = jsonObject.getString("aaa");
    }

    @Test
    public void test(){
        String []datas = new String[] {"peng","zhao","li"};
        Arrays.sort(datas);
//        Stream.of(datas).forEach(param ->     System.out.println(param));
    }

    @Test
    public void createReceiptBillTest(){
        String result = callFinanceServiceProxy.createReceiptBill("1000039");
    }
    @Test
    public void testGetReceivableBillInfo() {
        ReceiptBillRequest request = new ReceiptBillRequest();
        request.setOutContractCode("BJZS041756852");
        request.setDocumentType("1001");
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.callFinanceServiceProxy.getReceivableBillInfo(JsonEntityTransform.Object2Json(request)));

        List<ReceiptBillResponse> listBill = dto.parseData("listStr", new TypeReference<List<ReceiptBillResponse>>() {
        });
        if (!Check.NuNCollection(listBill)){
            System.err.println("不为空");
        }
        System.err.println(JsonEntityTransform.Object2Json(listBill));
    }

    @Test
    public void testGetReceiptBillListByContract() {
        ReceiptBillListRequestDto receiptBillListRequestDto = new ReceiptBillListRequestDto();
        receiptBillListRequestDto.setContractCode("12313");
        receiptBillListRequestDto.setPeriods("1");
        this.callFinanceServiceProxy.getReceiptBillListByContract(JsonEntityTransform.Object2Json(receiptBillListRequestDto));


    }

    @Test
    public void testGetReceiptBillByContracts() {
        List<String> outContractList = new ArrayList<>();
        outContractList.add("BJZYS041755119");

        ReceiptBillContractsRequest receiptBillContractsRequest = new ReceiptBillContractsRequest();
        receiptBillContractsRequest.setOutContractList(outContractList);

        String paramJson = JsonEntityTransform.Object2Json(receiptBillContractsRequest);
        System.out.println("testGetReceiptBillByContracts paramJson:"  + paramJson);
        String result = this.callFinanceServiceProxy.getReceiptBillByContracts(paramJson);
        System.out.println("testGetReceiptBillByContracts result:" + result);
    }

    @Test
    public void testModifyReceiptBill(){
        BillDto billDto = new BillDto();
        billDto.setBillNum("1007201711020001639");
        billDto.setDocumentAmount(367500);

        callFinanceServiceProxy.modifyReceiptBill(JsonEntityTransform.Object2Json(billDto));

    }

    @Test
    public void testGetReceivableBillListByCondition() {
        ReceivableBillListReqDto dto = new ReceivableBillListReqDto();
        dto.setOutContractList(Arrays.asList("BJZS041756752"));
        dto.setPageSize(10);
        dto.setStartPage(1);
        System.err.println(callFinanceServiceProxy.getReceivableBillListByCondition(JsonEntityTransform.Object2Json(dto)));
    }

    @Test
    public void testGetZRAReceiptBill(){
        PaymentBillsDto paymentBillsDto = new PaymentBillsDto();
        paymentBillsDto.setProjectId("21");
        paymentBillsDto.setStartPage(1);
        paymentBillsDto.setPageSize(10);
        callFinanceServiceProxy.getZRAReceiptBill(JsonEntityTransform.Object2Json(paymentBillsDto));
    }

    @Test
    public void testGetFzckList() {
        String fzckList = callFinanceServiceProxy.getFzckList("16");
        System.out.println(fzckList);
    }

    @Test
    public void testGetFollowupRecord() {
        String fzckList = callFinanceServiceProxy.getFollowupRecord("100");
        System.out.println(fzckList);
    }

    @Test
    public void testgetRecordList() {
        String fzckList = callFinanceServiceProxy.getRecordList("100");
        System.out.println(fzckList);
    }

    @Test
    public void testsaveFollowupRecord() {
        SaveFollowupDto dto = new SaveFollowupDto();
        dto.setCode("20236468");
        dto.setName("王小明");
        dto.setDuty("ZO");
        dto.setUploadfile(new ArrayList<String>() {{
            add("/apartment/upload/20171114/1.jpg");
        }});
        dto.setBillId(100);
        dto.setContractCode("BJZY0001");
        dto.setUrgeDesc("跟进说明");
        dto.setDebtReason("1");
        dto.setUrgeAction("1");
        String fzckList = callFinanceServiceProxy.saveFollowupRecord(JsonEntityTransform.Object2Json(dto));
        System.out.println(fzckList);
    }

    @Test
    public void testCreateReceipt(){
        String param = "{\n" +
                "\t\"sysCode\": null,\n" +
                "\t\"isContract\": null,\n" +
                "\t\"isCheckContract\": null,\n" +
                "\t\"preBillNumList\": [\"100720180120000011233547\",\"100720180120000011233548\",\"100720180120000011233549\"],\n" +
                "\t\"receiptList\": [{\n" +
                "\t\t\"billNum\": \"SHZY20180140813\",\n" +
                "\t\t\"amount\": 771300,\n" +
                "\t\t\"paySerialNum\": \"1003190738000168\",\n" +
                "\t\t\"payType\": \"yhzz_9200\",\n" +
                "\t\t\"payTime\": \"2017-12-27 00:00:00\",\n" +
                "\t\t\"receiptMothed\": \"xxzf\",\n" +
                "\t\t\"payer\": \"赵帅\",\n" +
                "\t\t\"posId\": null,\n" +
                "\t\t\"referenceNum\": null,\n" +
                "\t\t\"checkNumber\": null,\n" +
                "\t\t\"makerCode\": \"20201384\",\n" +
                "\t\t\"makerName\": \"刘倩倩\",\n" +
                "\t\t\"makerDept\": \"\",\n" +
                "\t\t\"confirmStatus\": 0,\n" +
                "\t\t\"receiptStatus\": 1,\n" +
                "\t\t\"accountFlag\": 0,\n" +
                "\t\t\"callType\": null,\n" +
                "\t\t\"callUrl\": null,\n" +
                "\t\t\"annexList\": [\"http://pic.ziroom.com/apartment/upload/20180129/image/5966474570489765.jpg\"]\n" +
                "\t}]\n" +
                "}";
        String resultJson = callFinanceServiceProxy.createReceipt(param);
    }

    @Test
    public void testGetZRAReceiptBillListForMS(){
        String paramJson = "{\"bussinessUnitCode\":null,\"companyCode\":null,\"outContractList\":null,\"receiptNo\":null,\"payName\":null,\"startCreateDate\":null,\"endCreateDate\":null,\"startPayDate\":null,\"endPayDate\":null,\"receiptStatus\":null,\"auditStatus\":null,\"payMethod\":null,\"payTypeCode\":null,\"startPage\":0,\"pageSize\":10,\"projectId\":\"8a9099cb576ba5c101576ea29c8a0027\",\"roomCode\":null,\"conRentCode\":null,\"parentConRentCode\":\"SHZYD081716085Z\",\"startDate\":null,\"endDate\":null,\"state\":null}";
        String resultJson = callFinanceServiceProxy.getZRAReceiptBillListForMS(paramJson);
    }

    @Test
    public void testGetZRAReceiptBill11(){
        String paramJson = "{\"bussinessUnitCode\":\"0603\",\"companyCode\":\"310000\",\"outContractList\":[\"SHZYD081732683\",\"SHZYD081732684\"],\"receiptNo\":null,\"payName\":null,\"startCreateDate\":null,\"endCreateDate\":null,\"startPayDate\":null,\"endPayDate\":null,\"receiptStatus\":null,\"auditStatus\":null,\"payMethod\":null,\"payTypeCode\":null,\"startPage\":0,\"pageSize\":10}";
        String resultJson = callFinanceServiceProxy.getZRAReceiptBill(paramJson);
    }

    @Test
    public void testgetReceiptMethod(){
        String companyCode = "310000";
        callFinanceServiceProxy.getReceiptMethod(companyCode);
    }

    @Test
    public void testUpdateReceipt(){
        ReceiptUpdateRequest receiptUpdateRequest = new ReceiptUpdateRequest();
        List<String> preBillNumList = new ArrayList<>();
        preBillNumList.add("100520171221000010432142");
        preBillNumList.add("100520171221000010432143");
        preBillNumList.add("100520171221000010432144");
//        preBillNumList.add("100120180119000011206748");
//        preBillNumList.add("100120180119000011206747");
        ReceiptUpdateRequest.Receipt receipt = new ReceiptUpdateRequest.Receipt();
        receipt.setBillNum("SHZY20171237691");
        receipt.setPayTime("2017-12-20 00:00:00");
        receipt.setAmount(2189900);
//        receipt.setPosId("89PP7462");
//        receipt.setReferenceNum("32447087");
        List<String> annexList = new ArrayList<>();
//        annexList.add("http://pic.ziroom.com/apartment/upload/20180119/1516351449135-2165.jpg");
//        receipt.setAnnexList(annexList);
        receipt.setConfirmStatus(0);
        receipt.setReceiptStatus(1);
        List<ReceiptUpdateRequest.Receipt> receipts = new ArrayList<>();
        receipts.add(receipt);
        receiptUpdateRequest.setReceiptList(receipts);
        receiptUpdateRequest.setPreBillNumList(preBillNumList);
        String resultJson = callFinanceServiceProxy.updateReceipt(JsonEntityTransform.Object2Json(receiptUpdateRequest));
    }

    @Test
    public void testgetZRAReceiptBill(){
        String paramJson = "{\"bussinessUnitCode\":\"0603\",\"companyCode\":\"310000\",\"outContractList\":[\"SHZYD071735772\",\"SHZYS071735319\",\"SHZYS071715903\",\"SHZYS071726811\",\"SHZY071611204-01\",\"SHZYS071730718\",\"SHZYS071732796\"],\"receiptNo\":null,\"payName\":null,\"startCreateDate\":null,\"endCreateDate\":null,\"startPayDate\":null,\"endPayDate\":null,\"receiptStatus\":null,\"auditStatus\":null,\"payMethod\":null,\"payTypeCode\":null,\"startPage\":0,\"pageSize\":10}";
        String resultJson = callFinanceServiceProxy.getZRAReceiptBill(paramJson);
        System.err.println(resultJson);
    }

    @Test
    public void testGetReceiptListByBillnum(){
        ReceiptQueryRequest receiptQueryRequest = new ReceiptQueryRequest();
        receiptQueryRequest.setBillNum("ZY20170445642");
        String resultJson = callFinanceServiceProxy.getReceiptListByBillnum(JsonEntityTransform.Object2Json(receiptQueryRequest));
    }

    @Test
    public void invailbillNum(){
        String paramJson = "{\n" +
                "\t\"sysCode\": \"ZRYU\",\n" +
                "\t\"billNums\": [\n" +
                "\t\t\"10052017072102436\"\n" +
                "\t]\n" +
                "}";
        String resultJson = CloseableHttpUtil.sendPost("http://zfservice.ziroom.com/receivableBill/addBadDebt", paramJson);

        System.err.println(resultJson);

    }

    @Test
    public  void  paymentCallbackTest(){
        String paramJson ="{\n" +
                "\t\"list\": [{\n" +
                "\t\t\"billAmount\": \"10000\",\n" +
                "\t\t\"billNum\": \"100120180122000009240475\",\n" +
                "\t\t\"costCode\": \"zrydf\",\n" +
                "\t\t\"receivedAmount\": \"10000\"\n" +
                "\t}],\n" +
                "\t\"type\": \"1\"\n" +
                "}";
        String resultJson = callFinanceServiceProxy.paymentCallback(paramJson);
        System.out.println(resultJson);
    }

}
