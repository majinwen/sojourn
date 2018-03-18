package com.ziroom.zrp.service.houses.proxy;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dto.ContractParamDto;
import com.ziroom.zrp.service.trading.proxy.EnterpriseSigningServiceProxy;
import com.ziroom.zrp.trading.entity.RentContractEntity;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月25日 15:09
 * @since 1.0
 */
public class EnterpriseSigningServiceProxyTest extends BaseTest {


    @Resource(name="trading.enterpriseSigningServiceProxy")
    private EnterpriseSigningServiceProxy enterpriseSigningServiceProxy;

    @Test
    public void testUpdateContractInfoBySecond() {
        ContractParamDto contractParamDto = new ContractParamDto();

        contractParamDto.setSurParentRentId("8a9091bd5fe6cccd015fe76bcf7d0008");
        contractParamDto.setConType("1");
        contractParamDto.setConCycleCode("3");
        contractParamDto.setConStartDate(new Date());
        contractParamDto.setConEndDate(new Date());
        contractParamDto.setConRentYear(12);
        contractParamDto.setUpdateId("00a5c8067eb94192bca7691afd99783b");

        String result = enterpriseSigningServiceProxy.updateContractInfoBySecond(contractParamDto.toJsonStr());
        System.out.println("testUpdateContractInfoBySecond:" +  result);
    }

    @Test
    public void testQueryFinReceiBillInfo() {
        String paramJson = "8a9e98906010f10d016011016bd9002a";
        String result = enterpriseSigningServiceProxy.queryFinReceiBillInfo(paramJson);
        System.out.println("result:" + result);
    }

    @Test
    public void testQuerySignRoomList() {
//        String surParentRentId = "8a9eae535f28fd8c015f28fd8cc00000";
        String surParentRentId = "12345";
        String result = enterpriseSigningServiceProxy.querySignRoomList(surParentRentId);
        System.out.println("testQuerySignRoomList : " + result);
    }

    @Test
    public void testCustomerSignature() {
        String surParentRentId = "8a9091bd5fe78f82015fe7b982500057";
        String result = enterpriseSigningServiceProxy.customerSignature(surParentRentId);
        System.out.println("customerSignature:" + result);
    }

    @Test
    public void testSelectRentFinReceiBillByContractIds() {
        String contractIds = "8a8ac42651429cf501514c747ad0010d, 8a8ac4255378ddbb01537a3343530124";
        String result = enterpriseSigningServiceProxy.selectRentFinReceiBillByContractIds(contractIds);
        System.out.println("result:" + result);
    }

    @Test
    public void testFindOnePreContractInfoByPreSurParentRentId() {
        String preSurParentRentId = "3333333";
        String result = enterpriseSigningServiceProxy.findOnePreContractInfoByPreSurParentRentId(preSurParentRentId);
        System.out.println("result:" + result);
    }

    @Test
    public void testAsyncEntContractAndBillsToFin() {
        String preSurParentRentId = "8a908e106108248701610832814400a7";
        String result = enterpriseSigningServiceProxy.asyncEntContractAndBillsToFin(preSurParentRentId);
        System.out.println("result:" + result);
    }

    @Test
    public void testFindRenewRootParentContractIds() {
        String currentContractIds = "8a9e98906010f10d016011016bd9002b";
        String result = this.enterpriseSigningServiceProxy.findRenewRootParentContractIds(currentContractIds);
        System.out.println("result:" + result);
    }

    @Test
    public void testCloseEspContract() {
        this.enterpriseSigningServiceProxy.closeEpsContract("8a9e989060254cb8016025686d3f0163", 3);
    }

    @Test
    public void testAsyncRetrySubContractTransferToPdf() {
        this.enterpriseSigningServiceProxy.asyncRetrySubContractTransferToPdf();
    }

    @Test
    public void testAsyncRetrySyncEntSubContractToFin() {
        this.enterpriseSigningServiceProxy.asyncRetrySyncEntSubContractToFin();
    }

    @Test
    public void testTransferToPdfByZrams() {
        RentContractEntity rentContractEntity = new RentContractEntity();
        rentContractEntity.setContractId("8a9e98906073b06f0160773080a4009b");
        rentContractEntity.setConRentCode("SHZYD081729959Z");
        this.enterpriseSigningServiceProxy.transferToPdfByZrams(rentContractEntity);
    }

    @Test
    public void testTransferToPdf() {
        RentContractEntity rentContractEntity = new RentContractEntity();
        rentContractEntity.setContractId("8a908e106072f16b0160730ce637009f");
        rentContractEntity.setConRentCode("SHZYD081732532");
        this.enterpriseSigningServiceProxy.transferToPdf(rentContractEntity);
    }
}
