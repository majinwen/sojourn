package com.ziroom.zrp.service.houses.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dto.contract.ContractManageDto;
import com.ziroom.zrp.service.trading.dto.contract.ContractSearchPageDto;
import com.ziroom.zrp.service.trading.entity.SyncContractVo;
import com.ziroom.zrp.trading.entity.RentContractAndDetailEntity;
import com.ziroom.zrp.trading.entity.RentEpsCustomerEntity;

import com.zra.common.dto.base.BasePageParamDto;
import org.junit.Assert;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.service.trading.dao.RentContractDao;
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
 * @Date Created in 2017年09月07日 20:30
 * @since 1.0
 */
public class RentContractDaoTest extends BaseTest{

    @Resource(name = "trading.rentContractDao")
    private RentContractDao rentContractDao;

    @Test
    public void testfindContractByRentCode(){
        RentContractEntity contractByRentCode = rentContractDao.findValidContractByRentCode("BJZY041617561-01");
        System.err.println(contractByRentCode);

    }
    @Test
    public void testfindContractDetail(){
    	RentContractEntity rentContractEntity = rentContractDao.findContractBaseByContractId("8a9e9ab35ca55b8a015ca55cfa320002");
    	System.err.println(JsonEntityTransform.Object2Json(rentContractEntity));
    }

    @Test
    public void testupdatePayCodeByContractId(){
        rentContractDao.updatePayCodeByContractId("1000032","12");


    }

    @Test
    public void testFindSyncContractVoById(){
        SyncContractVo syncContractVo = rentContractDao.findSyncContractVoById("2c908d174e05edef014e05fc21d40002");
    }
    
    @Test
    public void testfindLatelyContractByUid(){
    	String uid = "22d6ef61-4ec8-9a83-99ee-91528e36b928";
    	List<RentContractEntity> contracts = rentContractDao.listContractByUid(uid);
    	System.err.println(JSONObject.toJSONString(contracts));
    	System.err.println(contracts.size());
    }

    @Test
    public void testFindYqyContractByParentId() {
        String partentId = "1";
        List<RentContractEntity> rentContractEntityList = rentContractDao.findYqyContractByParentId(partentId);
        System.out.println("size:" + (rentContractEntityList == null? "NULL" : rentContractEntityList.size()));
    }

    @Test
    public void testupdateContractStatus(){
        RentContractEntity rentContractEntity = new RentContractEntity();
        rentContractEntity.setConStatusCode("wqy");
        rentContractEntity.setContractId("8a9e98b75f0436ae015f0436aefa0000");
        int num = rentContractDao.updateContractToTargetStatus(rentContractEntity);
        System.out.println("====================="+num);
    }
    public void testFindFirstBillPayOvertimeForPage(){

//        PagingResult<RentContractEntity> pagingResult = rentContractDao.findFirstBillPayOvertimeForPage()
    }

    @Test
    public void testFindContractListByContractIds() {
        String contractIds = "8a9099cb5de48f08015e1ca8ed041874,8a9099cb5de48f08015e13cfd1e11594";
        List<RentContractEntity> rentContractEntityList = rentContractDao.findContractListByContractIds(Arrays.asList(contractIds.split(",")));
        System.out.println("testFindContractListByContractIds:" + rentContractEntityList.size());
    }


    @Test
    public void updateContractCustomerInfoByParentId() {
        String surParentRentId = "8a9eae535f28f817015f28f8178d0000";
        String customerId = "1";
        String customerUid = "22";
        RentEpsCustomerEntity rentEpsCustomerEntity = new RentEpsCustomerEntity();
        rentEpsCustomerEntity.setId(customerId);
        rentEpsCustomerEntity.setCustomerUid(customerUid);
        int count = rentContractDao.updateContractCustomerInfoByParentId(surParentRentId, rentEpsCustomerEntity);
        System.out.println("count : " + count);
    }
    @Test
    public void testlistContractAndDetailByUid(){
    	String uid = "198faa1e-6cc6-a004-1ea1-3271edf19a1d";
    	List<RentContractAndDetailEntity> list = rentContractDao.listContractAndDetailByUid(uid);
    	System.err.println(JSONObject.toJSONString(list));
    	System.err.println(list.size());
    }
    @Test
    public void testupdateContractCustomerName(){
    	String contractId = "1000032";
    	String customerName = "小李";
    	String customerMobile = "15213261515";
    	int isSuccess = rentContractDao.updateContractCustomerName(contractId, customerName, customerMobile);
    	System.err.println(isSuccess);
    }

    @Test
    public void testUpdateContractByParentId() {

        RentContractEntity rentContractEntity = new RentContractEntity();
        rentContractEntity.setSurParentRentId("123456");
        rentContractEntity.setConType("1");
        rentContractEntity.setConCycleCode("3");
        rentContractEntity.setConStartDate(new Date());
        rentContractEntity.setConEndDate(new Date());
        rentContractEntity.setConRentYear(12);
        rentContractEntity.setUpdaterid("00a5c8067eb94192bca7691afd99783b");

        int size = rentContractDao.updateContractByParentId(rentContractEntity);
        System.out.println("size:" +  size);
    }

    @Test
    public void updateContractStatusDzfByParentId() {
        String surParentRentId = "8a9091bd5fe78f82015fe7b982500057";
        int size = rentContractDao.updateContractStatusDzfByParentId(surParentRentId);
        System.out.println("updateContractStatusDzfByParentId:" + size);
    }

    @Test
    public void testFindWqyContractInviteByRoomIds() {
        List<String> roomIdList = new ArrayList<>();
        roomIdList.add("1001497");
        roomIdList.add("1002650");
        Date currentDate = new Date();

        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String cDate = df.format(currentDate);
        try {
            currentDate = df.parse(cDate);
            System.err.println("cc:" + currentDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<RentContractEntity> rentContractEntityList = rentContractDao.findWqyContractInviteByRoomIds(roomIdList, currentDate);
        System.out.println("testFindWqyContractInviteByRoomIds size:" + rentContractEntityList.size());

        rentContractEntityList.forEach(rentContractEntity -> {
            System.out.println(rentContractEntity.getRoomId()  + "," +  rentContractEntity.getConSignDate());
        });
    }

    @Test
    public void findRenewContractByPreRentCode(){
    	String preRentCode = "BJZY051511094";
    	RentContractEntity rentContractEntity = rentContractDao.findRenewContractByPreRentCode(preRentCode);
    	System.out.println(JsonEntityTransform.Object2Json(rentContractEntity));
    }
    @Test
    public void updateBaseContractById(){
    	RentContractEntity rentContractEntity = new RentContractEntity();
    	rentContractEntity.setContractId("8a9091bd5f958136015f95846d310026");
    	rentContractEntity.setFapplicationdate(new Date());
    	rentContractEntity.setFexpecteddate(new Date());
    	rentContractEntity.setFsurrenderid(UUID.randomUUID().toString());
    	rentContractEntity.setIsRenew(1);
    	rentContractEntity.setActivitymoney("0.0");
    	int i = rentContractDao.updateBaseContractById(rentContractEntity);
    	System.out.println(i);
    }

    @Test
    public void testFindOnePreContractInfoByPreSurParentRentId() {
        String preSurParentRentId = "3333333";
        RentContractEntity rentContractEntity = rentContractDao.findOnePreContractInfoByPreSurParentRentId(preSurParentRentId);
        System.out.println("rentContractEntity:" + rentContractEntity.getConRentCode());
    }

    @Test
    public void testGetBatchParentContractCode(){
        List<String> contractCode = new ArrayList<>();
        contractCode.add("SHZD081720524");

        System.err.println(JsonEntityTransform.Object2Json(rentContractDao.getBatchParentContractCode(contractCode)));
    }

    @Test
    public void testlistContractByPage(){
        ContractSearchPageDto contractSearchPageDto = new ContractSearchPageDto();
        contractSearchPageDto.setPage(1);
        contractSearchPageDto.setRows(20);
        List<String> list = new ArrayList<>();
        list.add("2c908d194f5f09b8014f62b8a9ab0024");
        contractSearchPageDto.setProjectIds(list);
        PagingResult<ContractManageDto> contractManageDtoPagingResult = rentContractDao.listContractByPage(contractSearchPageDto);
        Assert.assertNotNull(contractManageDtoPagingResult);
    }

    @Test
    public void testUpdateWqyContractRoomSalesPrice() {
        RentContractEntity rentContractEntity = new RentContractEntity();
        int size = this.rentContractDao.updateWqyContractRoomSalesPrice(rentContractEntity);
        System.out.println("size:" + size);
    }

    @Test
    public void testFindContractNotTransferToPdf() {
        int defaultLimit = 50;
        int defaultHours = -6;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, defaultHours);
        //分页查询
        Date queryDate = calendar.getTime();
        PageBounds pageBounds = new PageBounds();

        int page = 1;

        do {
            pageBounds.setPage(page);
            pageBounds.setLimit(defaultLimit);
            PagingResult<RentContractEntity> pagingResult = this.rentContractDao.findContractNotTransferToPdf(queryDate, pageBounds);
            //查询时间为6小时以内需要同步的数据
            List<RentContractEntity> rentContractEntityList = pagingResult.getRows();
            if (rentContractEntityList != null && rentContractEntityList.size() > 0) {
                for (RentContractEntity rentContractEntity : rentContractEntityList) {
                    System.out.println("rentContractEntity:" + rentContractEntity);
                }
                if (rentContractEntityList.size() < defaultLimit) {
                    break;
                }

            } else {
                break;
            }
            page ++;


        } while (true);
        System.out.println("end----");
    }

    @Test
    public void testUpdateContractStatusYxyByCode() {
        String conRentCode = "SHZYD081729446";
        int size = this.rentContractDao.updateContractStatusYxyByCode(conRentCode );
        System.out.println("size:" + size);
    }


    @Test
    public void testlistExpireContractPage(){
        BasePageParamDto basePageParamDto = new BasePageParamDto() {};
        basePageParamDto.setPage(1);
        basePageParamDto.setRows(20);
//        PagingResult<ContractManageDto> pagingResult = rentContractDao.listExpireContractPage(basePageParamDto);
//        Assert.assertNotNull(pagingResult);
    }

    @Test
    public void testUpdate(){
        RentContractEntity updateRentContractEntity = new RentContractEntity();
        updateRentContractEntity.setContractId("8a908e106057e481016058052a98004f");
        updateRentContractEntity.setConSignDate(new Date());
        rentContractDao.updateBaseContractById(updateRentContractEntity);
    }

    @Test
    public void testUpdateIsTransferPdf() {
        String contractId = "8a9e9890607340890160734089850001";
        int size = rentContractDao.updateIsTransferPdf(contractId);
        System.out.println("size:" + size);
    }

    @Test
    public void testFindValidContractByRoomId() {
        List<RentContractEntity> validContractByRoomId = this.rentContractDao.findValidContractByRoomId("1002544");
        System.err.println(validContractByRoomId.size());
    }



}
