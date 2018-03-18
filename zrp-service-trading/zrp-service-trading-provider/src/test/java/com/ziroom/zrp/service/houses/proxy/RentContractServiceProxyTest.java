package com.ziroom.zrp.service.houses.proxy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.ziroom.zrp.service.trading.dto.ContractParamDto;
import com.ziroom.zrp.service.trading.dto.contract.ContractSearchPageDto;
import com.ziroom.zrp.service.trading.valenum.ContractCloseTypeEnum;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.google.gson.JsonObject;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.houses.dto.SignInviteDto;
import com.ziroom.zrp.service.trading.dto.ContractFirstDto;
import com.ziroom.zrp.service.trading.dto.EnterpriseCustomerDto;
import com.ziroom.zrp.service.trading.proxy.RentContractServiceProxy;
import com.ziroom.zrp.trading.entity.RentContractEntity;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月11日 16:48
 * @since 1.0
 */
public class RentContractServiceProxyTest extends BaseTest{

    @Resource(name="trading.rentContractServiceProxy")
    private RentContractServiceProxy rentContractServiceProxy;

    
    @Test
    public void testfindContractDetail(){
        String contract = rentContractServiceProxy.findContractBaseByContractId("8a9099cb58b04b960158b86948ed01ae");
        System.err.println("获取的合同信息为："+contract);
    }
    @Test
    public void testfindContractByContractId(){
    	String contractId = "8a908e10606293930160685acf0f06bd";
    	String deCode = "XQ";
    	Map<String, Object> map = new HashMap<>();
    	map.put("contractId", contractId);
    	map.put("deCode", deCode);
    	String contract = rentContractServiceProxy.findContractByContractId(JSONObject.toJSONString(map));
    	System.err.println("获取的合同信息为："+contract);
    }
    @Test
    public void testfindContractRoomInfoByContractId(){
    	String contractId = "2c908d174d1f0fa8014d1f308ec9000";
    	String contract = rentContractServiceProxy.findRentRoomInfo(contractId);
    	System.err.println("获取的房间信息为："+contract);
    	
    }

    @Test
    public void testSaveContractBySignInvite() {

        SignInviteDto signInviteDto  = new SignInviteDto();
        signInviteDto.setProjectId("16");
        signInviteDto.setRoomIds("1001247");
        signInviteDto.setProjectName("北京酒仙桥将府自如寓");
        signInviteDto.setConStartDate(new Date());
        signInviteDto.setRentPeriod(1);
        signInviteDto.setHandZo("刘倩倩");
        signInviteDto.setHandZoCode("20201384");
        signInviteDto.setEmployeeId("9000082288820201384");
        signInviteDto.setCustomerUid("8fdc471a-536c-6ab6-a334-1f8f16fc690f");
        signInviteDto.setPhone("13521263178");

        String result = rentContractServiceProxy.saveContractBySignInvite(signInviteDto.toJsonStr());
        System.out.println("result:" + result);
    }

    @Test
    public  void testvalidContract() {
        String billDetail = rentContractServiceProxy.validContract("8a9093006097eb9f01609bd925550129");
        System.err.println(billDetail);
    }

    @Test
    public void testFindRentRoomsInfoByParentId() {
        String parentId = "8a908e106092b36e016092c20f3c0044";
        String result = rentContractServiceProxy.findRentRoomsInfoByParentId(parentId);
        System.out.println("result:" + result);
    }


    @Test
    public void testSaveContractByFirst() {
        ContractFirstDto contractFirstDto = new ContractFirstDto();
//        contractFirstDto.setProjectId("20");
//        contractFirstDto.setPreContractIds("8a9099cb5de48f08015e504290183738,8a9099cb5de48f08015e3269ffb4249c");
        contractFirstDto.setRoomIds("1002628");
        contractFirstDto.setEmployeeId("9000072281320120012");

//        contractFirstDto.setSignType("1");
//        contractFirstDto.setCustomerType("3");
        // 新签 - 企业
        String result = rentContractServiceProxy.saveContractByFirst(contractFirstDto.toJsonStr());
        System.out.println("新签result:" + result);



        // 续约 - 企业
//        result = rentContractServiceProxy.saveContractByFirst(contractFirstDto.toJsonStr());
//        System.out.println("续约result:" + result);
    }

    @Test
    public void testFindOneRentContractByParentId() {
        String surParentRentId = "8a9eae535f28f817015f28f8178d0000";
        String result = rentContractServiceProxy.findOneRentContractByParentId(surParentRentId);
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(result);
        RentContractEntity rentContractEntity = dto.parseData("data", new TypeReference<RentContractEntity>() {
        });
        System.out.println("testFindOneRentContractByParentId:" + rentContractEntity.getContractId());

    }
    @Test
    public void testfindCheckinPerson(){
    	String contractId = "8a9e98b75f0436ae015f0436aefa0000";
    	String json = rentContractServiceProxy.findCheckinPerson(contractId);
    	System.err.println(json);
    }

   /* @Test
    public void testSaveOrUpdateEnterpriseContractCustomerInfo() {
        EnterpriseCustomerDto enterpriseCustomerDto = new EnterpriseCustomerDto();

        enterpriseCustomerDto.setSurParentRentId("8a9e98b75f336290015f3372ad590006");
        enterpriseCustomerDto.setName("121");
        enterpriseCustomerDto.setCode("121");
        enterpriseCustomerDto.setAddress("121");
        enterpriseCustomerDto.setEmail("cuiyh9@ziroom.com");
        enterpriseCustomerDto.setContacterNum("121");
        enterpriseCustomerDto.setContacter("121");
        enterpriseCustomerDto.setContacterTel("13521263178");
        enterpriseCustomerDto.setProxyPicurl("/apartment/upload/20171019/1508398122012-a.jpg");
        enterpriseCustomerDto.setCreaterId("9000072281320120012");
        enterpriseCustomerDto.setUpdaterId("9000072281320120012");
        enterpriseCustomerDto.setCustomerUid("8fdc471a-536c-6ab6-a334-1f8f16fc690f");

        enterpriseCustomerDto.setUpdaterId("009ff1ea8e5f4e6d9efc84f0c080dc20");

        String result = rentContractServiceProxy.saveOrUpdateEnterpriseContractCustomerInfo(enterpriseCustomerDto.toJsonStr());
        System.out.println("testSaveOrUpdateEnterpriseContractCustomerInfo : " + result);
    }*/

    @Test
    public void findSupportConTypeByParentId() {
        String surParentRentId = "8a9eae535f28fd8c015f28fd8cc00000";
        String result = rentContractServiceProxy.findSupportConTypeByParentId(surParentRentId);
        System.out.println("result:" + result);
    }


    @Test
    public void testCloseContract() {
        System.err.println(this.rentContractServiceProxy.closeContract("8a9e98b75f33d1ab015f37af7097002d", ContractCloseTypeEnum.CUSTOMER_CLOSE.getCode()));
    }

    @Test
    public void testFindWqyEpsContractPageInfo() {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date conStartDate =  new Date();
        Date conEndDate =  new Date();
        try {
            conStartDate = df.parse("20181207");
            conEndDate = df.parse("20191206");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ContractParamDto contractParamDto = new ContractParamDto();
        contractParamDto.setSurParentRentId("8a9e9890602b45fc01602b45fccc0000");
        contractParamDto.setConCycleCode("3");
        contractParamDto.setConType("1");
        contractParamDto.setConStartDate(conStartDate);
        contractParamDto.setConEndDate(conEndDate);
        contractParamDto.setConRentYear(1);


        System.out.println("json:" + contractParamDto.toJsonStr());
        String result = rentContractServiceProxy.findWqyEpsContractPageInfo(contractParamDto.toJsonStr());
        System.out.println("testFindWqyEpsContractPageInfo:" +result);
    }



    @Test
    public void testFindWqyContractInviteByRoomIds() {
        String roomIds = "1002620,";
        String result = rentContractServiceProxy.findWqyContractInviteByRoomIds(roomIds);
        System.out.println("testFindWqyContractInviteByRoomIds:" + result);
    }



    @Test
    public void testIsExistNotFinishedBill() {
        System.err.println(this.rentContractServiceProxy.isExistNotFinishedBill("findHistoryLifeFeeList", null));
    }
    @Test
    public void testfindContractListByUid(){
    	System.err.println(this.rentContractServiceProxy.findContractListByUid("198faa1e-6cc6-a004-1ea1-3271edf19a1d"));
    }


    
    @Test
    public void testfindInvalidContractList(){
    	System.err.println(this.rentContractServiceProxy.findInvalidContractList("dd0ae2f5-ce32-4b6a-a94d-905fe77fa3ab"));
    }

    @Test
    public void testGetBatchContractCodeByParentCode(){
        System.err.print(this.rentContractServiceProxy.getBatchContractCodeByParentCode("BJZD041755342Z"));
    }

    @Test
    public void testgetCodesByParentCodeOnCondition(){
        System.err.print(this.rentContractServiceProxy.getCodesByParentCodeOnCondition("SHZYD081733048Z"));
    }

    @Test
    public void testlistContractByPage(){
        ContractSearchPageDto contractSearchPageDto = new ContractSearchPageDto();
        contractSearchPageDto.setPage(1);
        contractSearchPageDto.setRows(5);
        //contractSearchPageDto.setConStatusCode("dzf");
        contractSearchPageDto.setConRentCode("SHZYS081729601");
        rentContractServiceProxy.listContractByPage(JsonEntityTransform.Object2Json(contractSearchPageDto));
    }
    @Test
    public void testsaveSurrenderAndUpdateRentContract(){
    	String param = "[{\"surrenderId\":null,\"contractId\":\"8a908e10606ea6be01606ead9a52000e\",\"surrendercostId\":null,\"freleasedate\":null,\"createrid\":\"bbb9bf36d53245dca879ee05a9bd5516\",\"updaterid\":null,\"fcreatetime\":1513930821096,\"fupdatetime\":null,\"fvalid\":null,\"fisdel\":null,\"fsurtype\":null,\"fsurreason\":null,\"fsurreasonother\":null,\"frentalorderspic\":null,\"fapplicationdate\":1513930821098,\"factualdate\":null,\"fexpecteddate\":1513930821098,\"fsurrendercode\":null,\"surParentCode\":null,\"surParentId\":null,\"roomId\":null,\"fnewrentcode\":null,\"fsubletname\":null,\"fsubletpersonid\":null,\"ftenantname\":null,\"fiscancel\":null,\"fsubmitstatus\":null,\"frentauditstatus\":null,\"fhandlezo\":null,\"fauditor\":null,\"frentauditdate\":null,\"fdeliverydate\":null,\"frentenddate\":null,\"fapplystatus\":0,\"fhandlezoname\":null,\"fauditorname\":null,\"cityid\":null,\"fsource\":\"1\",\"rentType\":null,\"zwFirstAuditDate\":null,\"zwApproveDate\":null,\"cwFirstAuditDate\":null,\"cwApproveDate\":null,\"conRentCode\":null,\"conStatuCode\":null}]";
    	rentContractServiceProxy.saveSurrenderAndUpdateRentContract(param);
    	System.out.println("1");
    }
    
    @Test
    public void testfindLatelyContractByUid(){
    	String uid = "1cd0de90-1b46-541c-8299-854a610e79c4";
    	rentContractServiceProxy.findLatelyContractByUid(uid);
    	System.out.println("1");
    }
    /*@Test
    public void testvalidSignPerson(){
    	String contractId = "8a908e106034caf6016034e5b0b50061";
    	rentContractServiceProxy.validSignPerson(contractId);
    }*/

    @Test
    public void testfindContractByCode(){
        String code = "SHZYS081733115";
        rentContractServiceProxy.findContractByCode(code);
        System.out.println("1");
    }

    @Test
    public void testGetRoomValidContractList() {
        String roomId = "8a90a3ab57f4410a0157fbb9fb6618b7";
        String roomValidContractList = rentContractServiceProxy.getRoomValidContractList(roomId);
        System.err.println(roomValidContractList);
    }
}

