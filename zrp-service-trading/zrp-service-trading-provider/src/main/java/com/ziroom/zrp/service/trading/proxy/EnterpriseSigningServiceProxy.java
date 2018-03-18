package com.ziroom.zrp.service.trading.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.zrp.houses.entity.CityEntity;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.service.houses.api.CityService;
import com.ziroom.zrp.service.houses.api.ProjectService;
import com.ziroom.zrp.service.houses.api.RoomService;
import com.ziroom.zrp.service.trading.api.EnterpriseSigningService;
import com.ziroom.zrp.service.trading.dep.RoomDep;
import com.ziroom.zrp.service.trading.dto.*;
import com.ziroom.zrp.service.trading.dto.finance.*;
import com.ziroom.zrp.service.trading.entity.LifeItemVo;
import com.ziroom.zrp.service.trading.proxy.commonlogic.FinanceCommonLogic;
import com.ziroom.zrp.service.trading.proxy.commonlogic.WaterClearingLogic;
import com.ziroom.zrp.service.trading.service.*;
import com.ziroom.zrp.service.trading.utils.HtmltoPDF;
import com.ziroom.zrp.service.trading.utils.HttpUtil;
import com.ziroom.zrp.service.trading.utils.builder.DataTransferObjectBuilder;
import com.ziroom.zrp.service.trading.valenum.ContractSignTypeEnum;
import com.ziroom.zrp.service.trading.valenum.ContractStatusEnum;
import com.ziroom.zrp.service.trading.valenum.CustomerTypeEnum;
import com.ziroom.zrp.service.trading.valenum.delivery.DeliveryStateEnum;
import com.ziroom.zrp.service.trading.valenum.finance.CostCodeEnum;
import com.ziroom.zrp.service.trading.valenum.finance.DocumentTypeEnum;
import com.ziroom.zrp.trading.entity.*;
import com.zra.common.utils.DateUtilFormate;
import com.zra.common.vo.base.BaseFieldVo;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>企业签约服务代理类</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月25日 10:13
 * @since 1.0
 */
@Component("trading.enterpriseSigningServiceProxy")
public class EnterpriseSigningServiceProxy implements EnterpriseSigningService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnterpriseSigningServiceProxy.class);

    @Resource(name = "trading.finReceiBillServiceImpl")
    private FinReceiBillServiceImpl finReceiBillServiceImpl;

    @Resource(name = "trading.finReceiBillDetailServiceImpl")
    private FinReceiBillDetailServiceImpl finReceiBillDetailServiceImpl;

    @Resource(name = "trading.rentContractServiceImpl")
    private RentContractServiceImpl rentContractServiceImpl;

    @Resource(name="trading.expenseItemServiceImpl")
    private ExpenseItemServiceImpl expenseItemServiceImpl;

    @Resource(name="trading.rentItemDeliveryServiceImpl")
    private RentItemDeliveryServiceImpl rentItemDeliveryServiceImpl;

    @Resource(name = "trading.callFinanceServiceProxy")
    private CallFinanceServiceProxy callFinanceServiceProxy;

    @Resource(name = "trading.paymentServiceProxy")
    private PaymentServiceProxy paymentServiceProxy;

    @Resource(name = "trading.roomDep")
    private RoomDep roomDep;

    @Resource(name = "trading.finReceiBillServiceProxy")
    private FinReceiBillServiceProxy finReceiBillServiceProxy;

    @Resource(name = "houses.projectService")
    private ProjectService projectService;

    @Resource(name = "houses.roomService")
    private RoomService roomService;

    @Resource(name = "houses.cityService")
    private CityService cityService;

    @Value("#{'${zrams_get_enterprise_contracthtml_url}'.trim()}")
    private String zramsGetEnterpriseContracthtmlUrl;

    @Value("#{'${zrams_transfer_enterprise_pdf_url}'.trim()}")
    private String zramsTransferEnterprisePdfUrl;

    @Resource(name = "trading.rentContractServiceProxy")
    private RentContractServiceProxy rentContractServiceProxy;

    @Resource(name="trading.financeCommonLogic")
    private FinanceCommonLogic financeCommonLogic;

    @Resource(name = "trading.rentCheckinPersonServiceImpl")
    private RentCheckinPersonServiceImpl rentCheckinPersonServiceImpl;

    @Resource(name = "trading.shareServiceImpl")
    private ShareServiceImpl shareServiceImpl;

    @Resource(name = "trading.waterClearingLogic")
    private WaterClearingLogic waterClearingLogic;

    /**
     * 成功标识
     */
    private static final String SUCCESS = "success";

    /**
     *
     * 企业合同信息跳转到物业交割页面时修改合同部分信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    @Override
    public String updateContractInfoBySecond(String paramJson) {
        LogUtil.info(LOGGER,"【updateContractAndSaveReceiBillBySecond】:{}", paramJson);
        if (Check.NuNStrStrict(paramJson)) {
            return DataTransferObjectBuilder.buildErrorJsonStr("入参为空");
        }

        try {
            ContractParamDto contractParamDto = JsonEntityTransform.json2Entity(paramJson, ContractParamDto.class);
            List<RentContractEntity> rentContractEntityList = rentContractServiceImpl.findContractListByParentId(contractParamDto.getSurParentRentId());
            RentContractEntity rentContractEntity = rentContractEntityList.get(0);

            String projectJson = projectService.findProjectById(rentContractEntity.getProjectId());
            DataTransferObject projectDto = JsonEntityTransform.json2DataTransferObject(projectJson);
            ProjectEntity projectEntity = null;

            projectEntity = SOAResParseUtil.getValueFromDataByKey(projectJson,"projectEntity",ProjectEntity.class);
            String cityResult = this.cityService.findById(rentContractEntity.getCityid());
            DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(cityResult);
            CityEntity cityEntity = null;
            if (DataTransferObject.SUCCESS == projectDto.getCode()) {
                cityEntity = cityDto.parseData("data", new TypeReference<CityEntity>() {
                });
            } else {
                LogUtil.error(LOGGER,"查询城市为空,cityId:{}",rentContractEntity.getCityid());
                return DataTransferObjectBuilder.buildErrorJsonStr("查询城市为空,cityId:" + rentContractEntity.getCityid());
            }

            boolean flag = rentContractServiceImpl.updateContractInfoByContractParamDto(contractParamDto, projectEntity, cityEntity ,rentContractEntityList);
            if (flag) {
                return DataTransferObjectBuilder.buildOkJsonStr("");
            } else {
                return DataTransferObjectBuilder.buildErrorJsonStr("更新合同或保存应收信息失败!");
            }

        } catch (Exception e) {
            LogUtil.error(LOGGER,"updateContractInfoBySecond ", e);
            return DataTransferObjectBuilder.buildErrorJsonStr("系统异常,updateContractInfoBySecond");
        }





    }

    /**
     * 查询应收账单信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    @Override
    public String queryFinReceiBillInfo(String surParentRentId) {
        LogUtil.info(LOGGER,"【queryFinReceiBillInfo】{}", surParentRentId);
        if (Check.NuNStrStrict(surParentRentId)) {
            return DataTransferObjectBuilder.buildErrorJsonStr("入参为空");
        }

        try {
            List<RentContractEntity> rentContractEntityList = rentContractServiceImpl.findContractListByParentId(surParentRentId);
            if (rentContractEntityList == null || rentContractEntityList.size() == 0) {
                return DataTransferObjectBuilder.buildErrorJsonStr("查询合同为空");
            }

            //是否为续约标识
            boolean isRenew = false;

            isRenew = ContractSignTypeEnum.RENEW.getValue().equals(rentContractEntityList.stream().findFirst().get().getFsigntype()) ;

            List<String> preConRentCodeList = null;
            Map<String, String> preConRentCodeRoomMap = null;
            if (isRenew) {
                preConRentCodeList = rentContractEntityList.stream().map(RentContractEntity::getPreConRentCode).collect(Collectors.toList());
                preConRentCodeRoomMap = rentContractEntityList.stream().collect(Collectors.toMap(RentContractEntity::getPreConRentCode, RentContractEntity::getRoomId));
            }

            String roomIds   = rentContractEntityList.stream().map(RentContractEntity::getRoomId).distinct().collect(Collectors.joining(","));
            List<RoomInfoEntity> roomInfoEntityList = getRoomListByRoomIds(roomIds);
            if (roomInfoEntityList == null || roomInfoEntityList.size() == 0) {
                return DataTransferObjectBuilder.buildErrorJsonStr("查询房间为空");
            }

            Map<String, RoomInfoEntity> roomInfoEntityMap = roomInfoEntityList.stream().collect(Collectors.toMap(RoomInfoEntity::getFid, roomInfoEntity -> roomInfoEntity));
            List<String> contactIdList  = rentContractEntityList.stream().map(RentContractEntity::getContractId).distinct().collect(Collectors.toList());
            List<FinReceiBillDetailEntity> finReceiBillDetailEntityList = finReceiBillDetailServiceImpl.selectGroupExpenseItemByContractIds(contactIdList);
            if (finReceiBillDetailEntityList == null || finReceiBillDetailEntityList.size() == 0) {
                return DataTransferObjectBuilder.buildErrorJsonStr("未查到账单信息");
            }

            //组装下面所需要的数据结构
            Map<String, List<FinReceiBillDetailEntity>> roomFinReceiBillDetailMap = finReceiBillDetailEntityList.stream().collect(Collectors.groupingBy(FinReceiBillDetailEntity::getRoomId));
            Map<Integer, List<FinReceiBillDetailEntity>> expenseItemFinReceiBillDetailMap = finReceiBillDetailEntityList.stream().collect(Collectors.groupingBy(FinReceiBillDetailEntity::getExpenseItemId));
            List<Integer> finExpenseItemList = finReceiBillDetailEntityList.stream().map(FinReceiBillDetailEntity::getExpenseItemId).collect(Collectors.toList());
            finReceiBillDetailEntityList = null;

            List<ExpenseItemEntity> expenseItemEntityList = expenseItemServiceImpl.selectExpenseItemsByIdFids(finExpenseItemList);
            Map<Integer, ExpenseItemEntity> expenseItemEntityMap = expenseItemEntityList.stream().collect(Collectors.toMap(ExpenseItemEntity::getFid, expenseItemEntity -> expenseItemEntity));
            expenseItemEntityList = null;


            List<ReceiBillItemRoomDto> roomReceiBillItemList = new ArrayList<>();

            //按房间统计费用项信息
            for (Map.Entry<String, List<FinReceiBillDetailEntity>> entry : roomFinReceiBillDetailMap.entrySet()) {

                ReceiBillItemRoomDto roomReceiBillItemDto = new ReceiBillItemRoomDto();

                List<FinReceiBillDetailEntity> tempFinReceiBillDetailEntityList = entry.getValue();


                List<ReceiBillItemExpenseDto> itemReceiBillItemDtoList = tempFinReceiBillDetailEntityList.stream()
                        .map(finReceiBillDetailEntity -> {
                            Integer expenseItemIdInt = finReceiBillDetailEntity.getExpenseItemId();
                            String expenseItemId = String.valueOf(expenseItemIdInt);
                            String expenseItemName = expenseItemEntityMap.get(expenseItemIdInt).getItemName();
                            BigDecimal itemOughtAmount = BigDecimal.valueOf(finReceiBillDetailEntity.getOughtAmount());
                            ReceiBillItemExpenseDto receiBillItemDto = new ReceiBillItemExpenseDto(itemOughtAmount, expenseItemId, expenseItemName);
                            return receiBillItemDto;
                        }).collect(Collectors.toList());


                BigDecimal roomTotalOughtAmount = BigDecimal.valueOf(0D);
                int num = 1;
                for (ReceiBillItemExpenseDto receiBillItemDto : itemReceiBillItemDtoList) {
                    roomTotalOughtAmount = roomTotalOughtAmount.add(receiBillItemDto.getOughtAmount());
                    receiBillItemDto.setOrderNum(formatNum(num));
                    num ++ ;
                }

                String roomId = entry.getKey();
                roomReceiBillItemDto.setRoomId(roomId);
                roomReceiBillItemDto.setRoomNum(roomInfoEntityMap.get(roomId).getFroomnumber());
                roomReceiBillItemDto.setRoomOughtAmount(roomTotalOughtAmount);
                roomReceiBillItemDto.setExpenseReceiBillItemDtoList(itemReceiBillItemDtoList);
                roomReceiBillItemList.add(roomReceiBillItemDto);

            }


            List<ReceiBillItemExpenseDto> totalReceiBillItemList = new ArrayList<>();

            BigDecimal expenseOughtAmount = BigDecimal.valueOf(0D);
            int num = 1;
            for (Map.Entry<Integer, List<FinReceiBillDetailEntity>> entry : expenseItemFinReceiBillDetailMap.entrySet()) {
                Integer expItemId =  entry.getKey();

                List<FinReceiBillDetailEntity> expFinReceiBillDetailList = entry.getValue();
                ReceiBillItemExpenseDto expenseReceiBillItemDto  = new ReceiBillItemExpenseDto();
                expenseReceiBillItemDto.setOrderNum(formatNum(num));
                expenseReceiBillItemDto.setExpenseItemId(String.valueOf(expItemId));
                expenseReceiBillItemDto.setExpenseItemName(expenseItemEntityMap.get(expItemId).getItemName());
                BigDecimal expenseOughtAmountTemp = BigDecimal.valueOf(0D);
                for (FinReceiBillDetailEntity tempFinReceiBillDetailEntity : expFinReceiBillDetailList) {
                    expenseOughtAmountTemp =  expenseOughtAmountTemp.add(new BigDecimal(Double.toString(tempFinReceiBillDetailEntity.getOughtAmount())));
                }

                expenseReceiBillItemDto.setOughtAmount(expenseOughtAmountTemp);

                expenseOughtAmount = expenseOughtAmount.add(expenseOughtAmountTemp);
                totalReceiBillItemList.add(expenseReceiBillItemDto);
                num ++;
            }

            ReceiBillItemTotalDto totalReceiBillItemDto = new ReceiBillItemTotalDto();
            totalReceiBillItemDto.setTotalOughtAmount(expenseOughtAmount);
            totalReceiBillItemDto.setExpenseReceiBillItemDtoList(totalReceiBillItemList);


            ReceiBillItemPageDto receiBillItemPageDto = new ReceiBillItemPageDto();
            receiBillItemPageDto.setReceiBillItemTotalDto(totalReceiBillItemDto);
            receiBillItemPageDto.setReceiBillItemRoomDtoList(roomReceiBillItemList);

            // 获取续约转款数据
            if (preConRentCodeList == null || preConRentCodeList.isEmpty()) {
                return DataTransferObjectBuilder.buildOkJsonStr(receiBillItemPageDto);
            }

            //每个房间押金转款
            Map<String, Double> depositMap = this.getPreDeposit(preConRentCodeList);
            //总押金转款
            BigDecimal totalYjReceivedBillAmount = BigDecimal.ZERO;


            if (depositMap != null || depositMap.size() > 0) {
                List<ReceiBillItemRoomDto> receiBillItemRoomDtoList = receiBillItemPageDto.getReceiBillItemRoomDtoList();

                for (ReceiBillItemRoomDto receiBillItemRoomDto : receiBillItemRoomDtoList) {
                    String roomId = receiBillItemRoomDto.getRoomId();
                    Double receivedBillAmountBd = depositMap.get(roomId);
                    List<ReceiBillItemExpenseDto> expenseReceiBillItemDtoList = receiBillItemRoomDto.getExpenseReceiBillItemDtoList();
                    int size = expenseReceiBillItemDtoList.size();
                    size =  size + 1;
                    ReceiBillItemExpenseDto receiBillItemExpenseDto = new ReceiBillItemExpenseDto();
                    receiBillItemExpenseDto.setOrderNum(formatNum(size));
                    receiBillItemExpenseDto.setExpenseItemName("押金转款");
                    receiBillItemExpenseDto.setOughtAmount(BigDecimal.valueOf(receivedBillAmountBd));
                    expenseReceiBillItemDtoList.add(receiBillItemExpenseDto);
                    receiBillItemRoomDto.setExpenseReceiBillItemDtoList(expenseReceiBillItemDtoList);
                    totalYjReceivedBillAmount = totalYjReceivedBillAmount.add(BigDecimal.valueOf(receivedBillAmountBd));
                }
                List<ReceiBillItemExpenseDto> receiBillItemExpenseDto = receiBillItemPageDto.getReceiBillItemTotalDto().getExpenseReceiBillItemDtoList();
                int size = receiBillItemExpenseDto.size();
                size =  size + 1;
                ReceiBillItemExpenseDto totalReceiBillItemExpenseDto = new ReceiBillItemExpenseDto();
                totalReceiBillItemExpenseDto.setExpenseItemName("押金转款");
                totalReceiBillItemExpenseDto.setOrderNum(formatNum(size));
                totalReceiBillItemExpenseDto.setOughtAmount(totalYjReceivedBillAmount);
                receiBillItemPageDto.getReceiBillItemTotalDto().getExpenseReceiBillItemDtoList().add(totalReceiBillItemExpenseDto);
            }

            return DataTransferObjectBuilder.buildOkJsonStr(receiBillItemPageDto);

        } catch (Exception e) {
            LogUtil.error(LOGGER,"queryFinReceiBillInfo surParentRentId:{}", surParentRentId, e);
            return DataTransferObjectBuilder.buildErrorJsonStr("系统异常 queryFinReceiBillInfo: " + surParentRentId);
        }

    }


    /**
     * 续约获取账单信息
     * @deprecated
     * @author cuiyuhui
     * @created  
     * @param 
     * @return 
     */
    private List<ReceiptBillResponse> getReceiptBillByContracts(List<String> outContractList) {
        List<ReceiptBillResponse> listBill = null;
        try {
            ReceiptBillContractsRequest receiptBillContractsRequest = new ReceiptBillContractsRequest();
            receiptBillContractsRequest.setOutContractList(outContractList);
            String paramJson = JsonEntityTransform.Object2Json(receiptBillContractsRequest);
            String result = callFinanceServiceProxy.getReceiptBillByContracts(paramJson);
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(result);
            if (dto.getCode() == DataTransferObject.ERROR) {
                return null;
            }
            listBill = SOAResParseUtil.getListValueFromDataByKey(result,"data", ReceiptBillResponse.class);
        } catch (Exception e) {
            LogUtil.error(LOGGER,"", e);
            return null;
        }


        return listBill;
    }

    /**
     * 获取前合同的押金
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    private Map<String, Double> getPreDeposit(List<String> outContractList) {
        Map<String, Double> map = new HashMap<>(16);
        for (String rentCode : outContractList) {
            RentContractEntity rentContractEntity = this.rentContractServiceImpl.findValidContractByRentCode(rentCode);
            map.put(rentContractEntity.getRoomId(), rentContractEntity.getConDeposit());
        }
        return map;

    }
    /**
     * 格式化数字编号
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    private String formatNum(int i) {
        if (String.valueOf(i).length() == 1) {
            return "0" + i;
        } else {
            return String.valueOf(i);
        }
    }


    /**
     * 查询roomInfo信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    private List<RoomInfoEntity> getRoomListByRoomIds(String roomIds) {
        //根据roomId 查询
        String roomResult = roomService.getRoomListByRoomIds(roomIds);
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(roomResult);
        if (dto.getCode() ==  DataTransferObject.ERROR) {
            return null;
        }

        try {
            List<RoomInfoEntity> roomInfoEntityList = SOAResParseUtil.getListValueFromDataByKey(roomResult, "roomInfoList", RoomInfoEntity.class);
            return roomInfoEntityList;
        } catch (SOAParseException e) {
            LogUtil.error(LOGGER,"{}", e);
        }

        return null;
    }


    /**
     * 查询签约成功页面房间信息列表
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    @Override
    public String querySignRoomList(String surParentRentId) {
        try {
            if (Check.NuNStrStrict(surParentRentId)) {
                return DataTransferObjectBuilder.buildErrorJsonStr("参数surParentRentId为空");
            }
            List<RentContractEntity> rentContractEntityList = this.rentContractServiceImpl.findContractListByParentId(surParentRentId);
            String roomIds = rentContractEntityList.stream().map(RentContractEntity::getRoomId).collect(Collectors.joining(","));
            List<RoomInfoEntity>  roomInfoEntityList = roomDep.getRoomListByRoomIds(roomIds);
            Map<String, RoomInfoEntity> roomInfoEntityMap = roomInfoEntityList.stream().collect(Collectors.toMap(RoomInfoEntity::getFid, roomInfoEntity -> roomInfoEntity));
            List<ContractRoomDto> contractRoomDtoList = new ArrayList<>();
            for (RentContractEntity rentContractEntity : rentContractEntityList) {
                ContractRoomDto contractRoomDto = new ContractRoomDto();
                contractRoomDto.setContractId(rentContractEntity.getContractId());
                contractRoomDto.setRoomId(rentContractEntity.getRoomId());
                contractRoomDto.setRoomNum(roomInfoEntityMap.get(rentContractEntity.getRoomId()).getFroomnumber());
                contractRoomDto.setConType(rentContractEntity.getConType());
                contractRoomDto.setRoomType(roomInfoEntityMap.get(rentContractEntity.getRoomId()).getFtype());
                contractRoomDto.setSignType(rentContractEntity.getFsigntype());
                contractRoomDto.setRoomRentType(roomInfoEntityMap.get(rentContractEntity.getRoomId()).getRentType());
                contractRoomDtoList.add(contractRoomDto);
            }

            return DataTransferObjectBuilder.buildOkJsonStr(contractRoomDtoList);
        } catch (Exception e) {
            LogUtil.error(LOGGER,"querySignRoomList surParentRentId:{}", surParentRentId, e);
            return DataTransferObjectBuilder.buildErrorJsonStr("系统异常 querySignRoomList:" + surParentRentId);
        }


    }

    /**
     * 客户签字
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    @Override
    public String customerSignature(String surParentRentId) {


        //更新合同状态
        try {
            if (Check.NuNStrStrict(surParentRentId)) {
                return DataTransferObjectBuilder.buildErrorJsonStr("参数为空");
            }
            List<RentContractEntity> rentContractEntityList = this.rentContractServiceImpl.findContractListByParentId(surParentRentId);

            if (rentContractEntityList == null || rentContractEntityList.isEmpty()) {
                return DataTransferObjectBuilder.buildErrorJsonStr("无法查询到合同，请重新签约或联系管理员");
            }
            RentContractEntity rentContractEntity = rentContractEntityList.get(0);
            boolean isRenew =  ContractSignTypeEnum.RENEW.getValue().equals(rentContractEntity.getFsigntype());
            //新签
            for (RentContractEntity tempRentContractEntity :  rentContractEntityList) {
                if (!ContractStatusEnum.WQY.getStatus().equals(tempRentContractEntity.getConStatusCode())) {
                    return DataTransferObjectBuilder.buildErrorJsonStr("合同状态发生变化，请得新操作,子合同id:" + tempRentContractEntity.getContractId() + ",子合同号:" + tempRentContractEntity.getConRentCode());
                }
            }


            String roomIds = rentContractEntityList.stream().map(RentContractEntity::getRoomId).collect(Collectors.joining(","));

            //只有新签才进行相应判断
            if (!isRenew) {
                //更新房间状态
                boolean flag = roomDep.updateRoomStateFromSigningToRental(roomIds);
                if (!flag) {
                    LogUtil.info(LOGGER, "房间状态发生变化: {}", roomIds);
                    return DataTransferObjectBuilder.buildErrorJsonStr("房间状态发生变化，请重新操作");
                }

                // 清算水费流程
                for(RentContractEntity tempRentContractEntity : rentContractEntityList){
                    waterClearingLogic.clearNewContract(tempRentContractEntity);
                }

            }
            if (isRenew){

                RentContractEntity oneContract = rentContractEntityList.get(0);
                String preSurParentRentId = oneContract.getPreSurParentRentId();

                //续约合同copy入住人 合住人数据
                Map<String, String> contractMap = rentContractEntityList.stream().collect(Collectors.toMap(RentContractEntity::getPreConRentCode,RentContractEntity::getContractId));

                List<RentContractEntity> preContractList = this.rentContractServiceImpl.findContractListByParentId(preSurParentRentId);
                Map<String, String> preContractMap = preContractList.stream().collect(Collectors.toMap(RentContractEntity::getConRentCode, RentContractEntity::getContractId));

                Map<RentCheckinPersonEntity,List<SharerEntity>> saveMap = new HashMap<>();
                for (Map.Entry<String,String> entry: preContractMap.entrySet()){
                    String preConrentCode = entry.getKey();
                    String preContractId = entry.getValue();

                    String currentId = contractMap.get(preConrentCode);
                    RentCheckinPersonEntity checinPerson = rentCheckinPersonServiceImpl.findCheckinPersonByContractId(preContractId);
                    checinPerson.setContractId(currentId);
                    checinPerson.setCreateTime(new Date());
                    checinPerson.setUpdateTime(new Date());
                    checinPerson.setId(null);


                    List<SharerEntity> sharerEntities = shareServiceImpl.listSharerByRentId(checinPerson.getId());
                    if (!Check.NuNCollection(sharerEntities)){
                        sharerEntities.forEach(a -> {
                            a.setFcontractid(currentId);
                            a.setUid(checinPerson.getUid());
                            a.setFcreatetime(new Date());
                            a.setFupdatetime(new Date());
                        });
                    }
                    saveMap.put(checinPerson,sharerEntities);

                    // 更新合同 录入状态
                    RentDetailEntity entity = RentDetailEntity.builder().contractId(currentId).build();
                    entity.putInPersonData(true);
                    rentContractServiceImpl.updateRentDetailByContractId(entity);
                }
                rentCheckinPersonServiceImpl.saveEnterpriseCheckinAndSharer(saveMap);
            }

            this.rentContractServiceImpl.updateContractStatusDzfByParentId(surParentRentId, rentContractEntityList, isRenew);
        } catch (Exception e) {
            LogUtil.error(LOGGER,"{}", e);
            //TODO 此处更新失败，考虑回滚房间?
            return DataTransferObjectBuilder.buildErrorJsonStr("更新合同状态失败,surParentRentId:"  + surParentRentId + " ,msg:"+ e.getMessage());
        }
        return DataTransferObjectBuilder.buildOkJsonStr("签约成功");
    }

    @Override
    public String listRentDetailBySurParentRentId(String surParentPage) {
        LogUtil.info(LOGGER,"【listRentDetailBySurParentRentId】入参={}",surParentPage);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(surParentPage)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        ContractPageDto contractPageDto = JsonEntityTransform.json2Object(surParentPage, ContractPageDto.class);
        if (Check.NuNStr(contractPageDto.getSurParentRentId())){
            if (Check.NuNStr(surParentPage)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("id为空");
                return dto.toJsonString();
            }
        }
        PagingResult<RentDetailEntity> pagingResult = rentContractServiceImpl.listRentDetailBySurParentRentId(contractPageDto);
        dto.putValue("total",pagingResult.getTotal());
        dto.putValue("list",pagingResult.getRows());

        return dto.toJsonString();
    }

    @Override
    public String listContractBySurParentRentId(String surParentRentId) {
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(surParentRentId)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("ID为空");
            return dto.toJsonString();
        }

        List<RentContractEntity> list = rentContractServiceImpl.listContractBySurParentRentId(surParentRentId);
        dto.putValue("list",list);
        return dto.toJsonString();
    }

    @Override
    public String closeEpsContract(String contractId, Integer closeType) {
        LogUtil.info(LOGGER, "【closeEpsContract】请求参数：contractId={}", contractId);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(contractId)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("合同ID为空");
            return dto.toJsonString();
        }

        RentContractEntity rentContractEntity = this.rentContractServiceImpl.findContractBaseByContractId(contractId);
        if (Check.NuNStr(rentContractEntity.getSurParentRentId())) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("父合同ID为空");
            return dto.toJsonString();
        }
        List<RentContractEntity> rentContractEntities = this.rentContractServiceImpl.findContractListByParentId(rentContractEntity.getSurParentRentId());
        StringJoiner contractCodes = new StringJoiner(",");

        rentContractEntities.forEach(v->{
            try {
                String s = this.rentContractServiceProxy.closeContract(v.getContractId(), closeType);
                DataTransferObject dataTransferObject = JsonEntityTransform.json2DataTransferObject(s);
                if (dataTransferObject.getCode() == DataTransferObject.ERROR) {
                    LogUtil.error(LOGGER, "【closeContract】企业关闭合同失败－合同号:{}", v.getConRentCode());
                }
            } catch (Exception e) {
                contractCodes.add(v.getConRentCode());
            }
        });
        dto.putValue("data", contractCodes.toString());
        return dto.toJsonString();
    }


    /**
     * 只查询房租的应收信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    @Override
    public String selectRentFinReceiBillByContractIds(String contractIds) {
        LogUtil.info(LOGGER,"【selectRentFinReceiBillByContractIds】入参={}", contractIds);
        try {
            if (Check.NuNStr(contractIds)){
                return DataTransferObjectBuilder.buildErrorJsonStr("参数为空");
            }
            List<String> contractIdList = Arrays.asList(contractIds.split(","));
            List<RentFinReceiBillDto> rentFinReceiBillDtoList = this.finReceiBillDetailServiceImpl.selectRentFinReceiBillByContractIds(contractIdList);
            return DataTransferObjectBuilder.buildOkJsonStr(rentFinReceiBillDtoList);
        } catch (Exception e) {
            LogUtil.error(LOGGER,"selectRentFinReceiBillByContractIds contractIds:{}", contractIds, e);
            return DataTransferObjectBuilder.buildErrorJsonStr("系统异常 contractIds:" + contractIds);
        }

    }

    @Override
    public String isFinishAllDelivery(String surParentRentId) {
        LogUtil.info(LOGGER,"【isFinishAllDelivery】参数surParentRentId={}",surParentRentId);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(surParentRentId)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("id为空");
            return dto.toJsonString();
        }
        List<RentDetailEntity> rentDetailEntities = rentContractServiceImpl.listRentDetailBySurParentRentId(surParentRentId);
        if (Check.NuNCollection(rentDetailEntities)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("物业交割信息为空");
            return dto.toJsonString();
        }
        boolean b = rentDetailEntities.stream().allMatch(p -> (p.getDeliveryState() == DeliveryStateEnum.JJG.getCode()));
        int isAllDelivery = b? YesOrNoEnum.YES.getCode():YesOrNoEnum.NO.getCode();
        dto.putValue("isAllDelivery",isAllDelivery);
        LogUtil.info(LOGGER,"【isFinishAllDelivery】结果={}",dto.toJsonString());
        return dto.toJsonString();
    }

    @Override
    public String saveEnterpriseReceiBill(String surParentRentId,String createId) {
        DataTransferObject dto = new DataTransferObject();
        LogUtil.info(LOGGER,"【saveEnterpriseReceiBill】入参surParentRentId={},createdId={}",surParentRentId,createId);
        if (Check.NuNStr(surParentRentId)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("id为空");
            return dto.toJsonString();
        }
        try{
            List<RentContractEntity> contracts = rentContractServiceImpl.listContractBySurParentRentId(surParentRentId);
            if (Check.NuNCollection(contracts)){
                LogUtil.info(LOGGER,"合同不存在");
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同不存在");
                return dto.toJsonString();
            }
            RentContractEntity oneContract = contracts.get(0);

            //签约类型   如果是续约 不处理生活费用
            String signType = oneContract.getFsigntype();

            Map<RentContractEntity,PaymentTermsDto> payMap = new HashMap<>(32);
            List<ContractRoomDto> contractRooms = new ArrayList<>();
            //遍历查询对应的账单列表
            for (RentContractEntity contractEntity : contracts){
                DataTransferObject paymentDto = JsonEntityTransform.json2DataTransferObject(paymentServiceProxy.getPaymentDetailForPC(JsonEntityTransform.Object2Json(contractEntity)));
                if (paymentDto.getCode() == DataTransferObject.ERROR){
                    LogUtil.info(LOGGER,"【saveEnterpriseReceiBill】获取支付详情错误error={},contract={}",paymentDto.toJsonString(),JsonEntityTransform.Object2Json(contractEntity));
                    return paymentDto.toJsonString();
                }
                PaymentTermsDto paymentTermsDto = paymentDto.parseData("items", new TypeReference<PaymentTermsDto>() {});
                payMap.put(contractEntity,paymentTermsDto);

                ContractRoomDto contractRoomDto = new ContractRoomDto();
                contractRoomDto.setContractId(contractEntity.getContractId());
                contractRoomDto.setRoomId(contractEntity.getRoomId());
                contractRooms.add(contractRoomDto);
            }
            LogUtil.info(LOGGER,"合同签约类型signType={}",signType);
            Map<String,List<LifeItemVo>> lifeMap = new HashMap<>(8);
            //新签才处理物业交割  生活费用数据
            if (signType.equals(ContractSignTypeEnum.NEW.getValue())){
                LogUtil.info(LOGGER,"生活费用账单查询组装开始...");
                for (ContractRoomDto contractRoom : contractRooms){
                    MeterDetailEntity meterDetailEntity = rentItemDeliveryServiceImpl.findMeterDetailById(contractRoom);
                    List<LifeItemVo> lifeList = rentItemDeliveryServiceImpl.findLifeItemByContractIdAndRoomId(contractRoom);
                    Double waterPrice = meterDetailEntity.getFpaywaterprice() == null ? 0.0:meterDetailEntity.getFpaywaterprice();
                    Double electPrice = meterDetailEntity.getFpayelectricprice() == null ? 0.0:meterDetailEntity.getFpayelectricprice();

                    LifeItemVo waterLife = new LifeItemVo();
                    waterLife.setContractId(contractRoom.getContractId());
                    waterLife.setRoomFid(contractRoom.getRoomId());
                    waterLife.setPaymentAmount(waterPrice);
                    waterLife.setItemCode(CostCodeEnum.ZRYSF.getZraCode());
                    waterLife.setExpenseItemId(String.valueOf(CostCodeEnum.ZRYSF.getZraId()));
                    waterLife.setItemName(CostCodeEnum.ZRYSF.getName());

                    LifeItemVo electLife = new LifeItemVo();
                    electLife.setContractId(contractRoom.getContractId());
                    electLife.setRoomFid(contractRoom.getRoomId());
                    electLife.setPaymentAmount(electPrice);
                    electLife.setItemCode(CostCodeEnum.ZRYDF.getZraCode());
                    electLife.setExpenseItemId(String.valueOf(CostCodeEnum.ZRYDF.getZraId()));
                    electLife.setItemName(CostCodeEnum.ZRYDF.getName());
                    lifeList.add(waterLife);
                    lifeList.add(electLife);
                    lifeMap.put(contractRoom.getContractId(),lifeList);
                }
                LogUtil.info(LOGGER,"生活费用账单查询组装结束...");
            }
            //定义需要保存或者更新的数据集
            List<FinReceiBillEntity> saveFinReceiBills = new ArrayList<>();
            List<FinReceiBillDetailEntity> saveFinReceiDetailBills = new ArrayList<>();
            List<RentContractEntity> updateContracts = new ArrayList<>();
            List<RentDetailEntity> upRentDetails = new ArrayList<>();
            List<RentContractActivityEntity> saveActList = new ArrayList<>();

            //组装信息
            for(Map.Entry<RentContractEntity,PaymentTermsDto> entry : payMap.entrySet()){
                RentContractEntity rentContractEntity = entry.getKey();
                PaymentTermsDto paymentTermsDto = entry.getValue();
                String contractId = rentContractEntity.getContractId();
                String roomId = rentContractEntity.getRoomId();
                DataTransferObject projectDto = JsonEntityTransform.json2DataTransferObject(projectService.findProjectById(rentContractEntity.getProjectId()));
                if (projectDto.getCode() == DataTransferObject.ERROR){
                    LogUtil.info(LOGGER,"查询项目信息错误msg={}",projectDto.toJsonString());
                    return projectDto.toJsonString();
                }
                ProjectEntity projectEntity = projectDto.parseData("projectEntity", new TypeReference<ProjectEntity>() {});
                List<RentContractActivityEntity> actList = paymentTermsDto.getActList();
                List<RoomRentBillDto> roomRentBillDtos = paymentTermsDto.getRoomRentBillDtos();

                //第一期账单
                RoomRentBillDto roomRentFirstBillDto = roomRentBillDtos.get(0);
                //处理更新的合同 主要是金额数据
                RentContractEntity updateContract = new RentContractEntity();
                updateContract.setContractId(contractId);
                updateContract.setRoomId(roomId);
                //押金
                updateContract.setConDeposit(roomRentFirstBillDto.getDepositPrice().doubleValue());
                //应收服务费
                updateContract.setConCommission(paymentTermsDto.getOriginServicePrice().doubleValue());
                Double discountServicePrice = paymentTermsDto.getOriginServicePrice().subtract(roomRentFirstBillDto.getServicePrice()).doubleValue();
                //折扣价格
                updateContract.setFdiscountserprice(discountServicePrice);
                //实际出房价格
                updateContract.setFactualprice(roomRentFirstBillDto.getRentPrice().doubleValue());
                //实收服务费
                updateContract.setFserviceprice(roomRentFirstBillDto.getServicePrice().doubleValue());
                //活动优惠金额
                double activityMoney = 0.0;
                if (!Check.NuNCollection(actList)){
                    activityMoney = actList.stream().mapToDouble(RentContractActivityEntity::getDiscountAccount).sum();
                }
                updateContract.setActivitymoney(String.valueOf(activityMoney));
                updateContracts.add(updateContract);

                RentDetailEntity upRentDetailEntity = new RentDetailEntity();
                upRentDetailEntity.setContractId(contractId);
                upRentDetailEntity.setRoomId(roomId);
                //应收押金
                upRentDetailEntity.setMustDeposit(roomRentFirstBillDto.getDepositPrice().doubleValue());
                //应收服务费
                upRentDetailEntity.setMustCommission(paymentTermsDto.getOriginServicePrice().doubleValue());
                //实收服务费
                upRentDetailEntity.setDiscountCommission(roomRentFirstBillDto.getServicePrice().doubleValue());
                //实际金额
                upRentDetailEntity.setActualPrice(roomRentFirstBillDto.getRentPrice().doubleValue());
                //原价
                upRentDetailEntity.setBasePrice(rentContractEntity.getRoomSalesPrice());
                upRentDetails.add(upRentDetailEntity);
                if (!Check.NuNCollection(actList)){
                    saveActList.addAll(actList);
                }

                //用来复制属性使用 水电交割部分
                FinReceiBillEntity finReceiBillEntityBak = null;
                //开始处理账单数据
                for (RoomRentBillDto rentBillDto : roomRentBillDtos){
                    FinReceiBillEntity finReceiBillEntity = new FinReceiBillEntity();
                    finReceiBillEntity.setFid(UUIDGenerator.hexUUID());
                    finReceiBillEntity.setContractId(contractId);
                    finReceiBillEntity.setBillNumber(financeCommonLogic.genReceiBillCode(projectEntity.getFcode()));
                    finReceiBillEntity.setBillState(0);
                    finReceiBillEntity.setBillType(0);
                    finReceiBillEntity.setGenWay(0);
                    finReceiBillEntity.setPlanGatherDate(DateUtilFormate.formatStringToDate(rentBillDto.getStartDate(),"yyyy-MM-dd"));
                    finReceiBillEntity.setStartCycle(DateUtilFormate.formatStringToDate(rentBillDto.getStartDate(),"yyyy-MM-dd"));
                    finReceiBillEntity.setEndCycle(DateUtilFormate.formatStringToDate(rentBillDto.getEndDate(),"yyyy-MM-dd"));
                    finReceiBillEntity.setOughtTotalAmount(rentBillDto.getPeriodTotalMoney().doubleValue());
                    finReceiBillEntity.setPayNum(rentBillDto.getPeriod());
                    finReceiBillEntity.setCityId(rentContractEntity.getCityid());
                    saveFinReceiBills.add(finReceiBillEntity);
                    finReceiBillEntityBak = finReceiBillEntity;

                    FinReceiBillDetailEntity finReceiDetailRentBill = new FinReceiBillDetailEntity();
                    finReceiDetailRentBill.setBillFid(finReceiBillEntity.getFid());
                    finReceiDetailRentBill.setExpenseItemId(CostCodeEnum.KHFZ.getZraId());
                    //房租
                    finReceiDetailRentBill.setOughtAmount(rentBillDto.getPeriodTotalRentPrice().doubleValue());
                    finReceiDetailRentBill.setCityId(rentContractEntity.getCityid());
                    finReceiDetailRentBill.setRoomId(rentContractEntity.getRoomId());
                    finReceiDetailRentBill.setBillType(DocumentTypeEnum.RENT_FEE.getCode());
                    saveFinReceiDetailBills.add(finReceiDetailRentBill);
                    if (!Check.NuNObj(rentBillDto.getDepositPrice()) && rentBillDto.getDepositPrice().doubleValue() != 0.0){
                        FinReceiBillDetailEntity finReceiDetailDepositBill = new FinReceiBillDetailEntity();
                        BeanUtils.copyProperties(finReceiDetailRentBill,finReceiDetailDepositBill);
                        finReceiDetailDepositBill.setExpenseItemId(CostCodeEnum.KHYJ.getZraId());
                        finReceiDetailDepositBill.setBillType(DocumentTypeEnum.DEPOSIT_FEE.getCode());
                        finReceiDetailDepositBill.setOughtAmount(rentBillDto.getDepositPrice().doubleValue());
                        saveFinReceiDetailBills.add(finReceiDetailDepositBill);
                    }
                    if (!Check.NuNObj(rentBillDto.getServicePrice()) && rentBillDto.getServicePrice().doubleValue() != 0.0){
                        FinReceiBillDetailEntity finReceiDetailServiceBill = new FinReceiBillDetailEntity();
                        BeanUtils.copyProperties(finReceiDetailRentBill,finReceiDetailServiceBill);
                        finReceiDetailServiceBill.setExpenseItemId(CostCodeEnum.KHFWF.getZraId());
                        finReceiDetailServiceBill.setBillType(DocumentTypeEnum.SERVICE_FEE.getCode());
                        finReceiDetailServiceBill.setOughtAmount(rentBillDto.getServicePrice().doubleValue());
                        saveFinReceiDetailBills.add(finReceiDetailServiceBill);
                    }
                }


                //开始处理生活费用数据
                List<LifeItemVo> lifeItemVos = lifeMap.get(contractId);
                if (Check.NuNObj(finReceiBillEntityBak)){
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("账单错误");
                    return dto.toJsonString();
                }
                if (!Check.NuNCollection(lifeItemVos)){
                    //生活费用 列表不为空 才处理
                    FinReceiBillEntity finReceiLifeBillEntity = new FinReceiBillEntity();
                    BeanUtils.copyProperties(finReceiBillEntityBak,finReceiLifeBillEntity);
                    double totalFee = 0.0;
                    finReceiLifeBillEntity.setFid(UUIDGenerator.hexUUID());
                    finReceiLifeBillEntity.setBillNumber(financeCommonLogic.genReceiBillCode(projectEntity.getFcode()));
                    //生活费用 期数传null
                    finReceiLifeBillEntity.setPayNum(null);
                    for (LifeItemVo lifeItemVo : lifeItemVos){
                        Double paymentAmount = lifeItemVo.getPaymentAmount();
                        if (paymentAmount.doubleValue() == 0.0){
                            continue;
                        }
                        totalFee += paymentAmount;
                        FinReceiBillDetailEntity finReceiBillDetailEntity = new FinReceiBillDetailEntity();
                        finReceiBillDetailEntity.setBillFid(finReceiLifeBillEntity.getFid());
                        finReceiBillDetailEntity.setExpenseItemId(Integer.parseInt(lifeItemVo.getExpenseItemId()));
                        finReceiBillDetailEntity.setOughtAmount(lifeItemVo.getPaymentAmount().doubleValue());
                        finReceiBillDetailEntity.setCityId(rentContractEntity.getCityid());
                        finReceiBillDetailEntity.setRoomId(rentContractEntity.getRoomId());
                        finReceiBillDetailEntity.setBillType(DocumentTypeEnum.LIFE_FEE.getCode());
                        saveFinReceiDetailBills.add(finReceiBillDetailEntity);
                    }
                    finReceiLifeBillEntity.setOughtTotalAmount(totalFee);
                    if (totalFee != 0.0){
                        saveFinReceiBills.add(finReceiLifeBillEntity);
                    }
                }
            }

            saveFinReceiBills.forEach(a -> {
                a.setCreateId(createId);
                a.setUpdateId(createId);
            });
            saveFinReceiDetailBills.forEach(a -> {
                a.setCreateId(createId);
                a.setUpdateId(createId);
            });

            int count = finReceiBillServiceImpl.saveEnterpriceReceiBills(saveFinReceiBills, saveFinReceiDetailBills, updateContracts, upRentDetails, saveActList);

            //如果是续约合同 批量copy物业交割数据
            if (signType.equals(ContractSignTypeEnum.RENEW.getValue())){
                for (RentContractEntity contractEntity : contracts) {
                    if (contractEntity.getFsigntype().equals(ContractSignTypeEnum.RENEW.getValue())){
                        count += rentItemDeliveryServiceImpl.copyPreContractDeliveryInfo(contractEntity.getContractId());
                        LogUtil.info(LOGGER,"copy物业交割数据upcount={}",count);
                    }
                }
            }
            LogUtil.info(LOGGER,"保存结果count={}",count);
            dto.putValue("count",count);
        }catch (Exception e){
            LogUtil.error(LOGGER,"保存企业账单出错error={}",e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务错误");
        }

        return dto.toJsonString();
    }


    /**
     * 企业续约时使用，获取前一个合同号的合同信息
     * 获取前一个
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    @Override
    public String findOnePreContractInfoByPreSurParentRentId(String preSurParentRentId) {
        try {
            if (Check.NuNStrStrict(preSurParentRentId)) {
                return DataTransferObjectBuilder.buildErrorJsonStr("参数为空");
            }
            RentContractEntity rentContractEntity = this.rentContractServiceImpl.findOnePreContractInfoByPreSurParentRentId(preSurParentRentId);
            return DataTransferObjectBuilder.buildOkJsonStr(rentContractEntity);
        } catch (Exception e) {
            LogUtil.error(LOGGER,"findOnePreContractInfoByPreSurParentRentId preSurParentRentId:{}", preSurParentRentId, e);
            return DataTransferObjectBuilder.buildErrorJsonStr("系统异常 findOnePreContractInfoByPreSurParentRentId preSurParentRentId:" + preSurParentRentId);
        }

    }

    @Override
    public String contractNotModifyBySurParentRentId(String surParentRentId) {
        LogUtil.info(LOGGER,"【contractNotModifyBySurParentRentId】surParentRentId={}",surParentRentId);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(surParentRentId)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        RentContractEntity rentContractEntity = new RentContractEntity();
        rentContractEntity.setSurParentRentId(surParentRentId);
        rentContractEntity.setIsPossibleModify(0);
        int i = rentContractServiceImpl.updateContractBySurParentRentId(rentContractEntity);

        LogUtil.info(LOGGER,"【contractNotModifyBySurParentRentId】更新合同不可修改结果={}",i);
        dto.putValue("count",i);
        return dto.toJsonString();
    }

    /**
     * 同步企业合同和应收账单到财务
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    @Override
    public String asyncEntContractAndBillsToFin(String surParentRentId) {
        LogUtil.info(LOGGER,"asyncEntContractAndBillsToFin{}", surParentRentId);
        try {
            if (Check.NuNStrStrict(surParentRentId)) {
                return DataTransferObjectBuilder.buildErrorJsonStr("asyncEntContractAndBillsToFin 参数为空");
            }
            StringJoiner failJs = new StringJoiner(",");
            List<RentContractEntity> rentContractEntityList = rentContractServiceImpl.findContractListByParentId(surParentRentId);
            for (RentContractEntity rentContractEntity : rentContractEntityList ) {

                DataTransferObject dto = syncEntSubContractToFin(rentContractEntity);

                if (dto.getCode() == DataTransferObject.ERROR) {
                    LogUtil.error(LOGGER,"syncEntSubContractToFin contractId:" + rentContractEntity.getContractId() + ",msg:" + dto.getMsg());
                    failJs.add(rentContractEntity.getContractId());
                }
            }

            if (failJs.length() > 0) {
                return DataTransferObjectBuilder.buildErrorJsonStr("asyncEntContractAndBillsToFin:" + failJs.toString());
            }
            return DataTransferObjectBuilder.buildOkJsonStr("");
        } catch (Exception e) {
            LogUtil.error(LOGGER, "asyncEntContractAndBillsToFin surParentRentId:{}", surParentRentId, e);
            return DataTransferObjectBuilder.buildErrorJsonStr("系统异常 asyncEntContractAndBillsToFin surParentRentId:" + surParentRentId);
        }


    }

    /**
     * 定时任务同步合同数据
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    @Override
    public String asyncRetrySyncEntSubContractToFin() {
        LogUtil.info(LOGGER,"begin retrySyncEntSubContractToFin");
        long start = System.currentTimeMillis();

        int defaultLimit = 50;
        int defaultHours = -6;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, defaultHours);
        //分页查询
        Date queryDate = calendar.getTime();
        PageBounds pageBounds=new PageBounds();

        int page = 1;
        int size = 0;

        try {


            do {
                pageBounds.setPage(page);
                pageBounds.setLimit(defaultLimit);
                PagingResult<RentContractEntity> pagingResult = this.rentContractServiceImpl.findContractNotSyncToFin(queryDate, pageBounds);
                //查询时间为6小时以内需要同步的数据
                List<RentContractEntity> rentContractEntityList = pagingResult.getRows();
                if (rentContractEntityList != null && rentContractEntityList.size() > 0) {
                    for (RentContractEntity rentContractEntity : rentContractEntityList) {
                        //这个地方跳转个人合同，个人合同如果有需求可以将这段代码放开
                        if (rentContractEntity.getCustomerType() != CustomerTypeEnum.ENTERPRICE.getCode()) {
                            continue;
                        }

                        syncEntSubContractToFin(rentContractEntity);
                        LogUtil.info(LOGGER,"syncEntSubContractToFin conRentCode:{}, queryDate:{}", rentContractEntity.getConRentCode());
                        //企业合同，验证修改  是否可以进行首次收款操作.
                        size++;
                    }

                    if (rentContractEntityList.size() < defaultLimit) {
                        break;
                    }

                } else {
                    break;
                }
                page ++;
            } while (true);

        } catch (Exception e) {
            LogUtil.error(LOGGER, "", e);
        }


        long end = System.currentTimeMillis();

        //查询时间为6小时以外需要同步的数据 -- 告警? TODO cuiyh9 系统稳定后添加
        LogUtil.info(LOGGER,"end retrySyncEntSubContractToFin size:{}, queryDate:{}, time:{}", size, queryDate, (end - start));
        return null;
    }

    /**
     * 同就单个合同到财务，方便研发使用接口手工调用
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public String syncEntSubContractToFinByContractId(String contractId) {
        if (Check.NuNStrStrict(contractId)) {
            return DataTransferObjectBuilder.buildErrorJsonStr("参数为空");
        }

        RentContractEntity rentContractEntity = this.rentContractServiceImpl.findContractBaseByContractId(contractId);
        if (rentContractEntity == null) {
            return DataTransferObjectBuilder.buildErrorJsonStr("无法查询到合同 contractId:" + contractId);
        } else if (rentContractEntity.getIsSyncToFin() == 1) {
            return DataTransferObjectBuilder.buildOkJsonStr("合同已同步，请验证 contractId:" + contractId );
        } else {
            syncEntSubContractToFin(rentContractEntity);
        }
        return DataTransferObjectBuilder.buildOkJsonStr("成功同步");

    }

    /**
     * 同步单个合同到财务
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    private DataTransferObject syncEntSubContractToFin(RentContractEntity rentContractEntity) {
        try {
            LogUtil.info(LOGGER,"before syncContract contractId:{}", rentContractEntity.getContractId());
            String result = financeCommonLogic.syncContractToFina(rentContractEntity.getContractId());
            LogUtil.info(LOGGER,"syncContract result result:{},contractId:{}", result, rentContractEntity.getContractId());
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(result);
            if (dto.getCode() == DataTransferObject.SUCCESS) {
                // 同步企业应收账单到财务
                DataTransferObject synReceiptDto = this.finReceiBillServiceProxy.syncReceiptBillToFinByEntity(rentContractEntity);
                return synReceiptDto;
            }
            return dto;
        } catch (Exception e) {
            LogUtil.error(LOGGER, "e:{},contractId:{}",e, rentContractEntity.getContractId());
            return DataTransferObjectBuilder.buildError("syncEntSubContractToFin exception 合同id:" + rentContractEntity.getContractId());
        }
    }

    /**
     * 根据父合同id生成子合同pdf
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    @Override
    public String asyncSubContractTransferToPdfByParentId(String surParentRentId) {
        LogUtil.info(LOGGER,"asyncSubContractTransferToPdfByParentId {}", surParentRentId);
        try {

            if (Check.NuNStrStrict(surParentRentId)) {
                return DataTransferObjectBuilder.buildErrorJsonStr("参数为空");
            }
            List<RentContractEntity> rentContractEntityList = this.rentContractServiceImpl.findContractListByParentId(surParentRentId);

            if (rentContractEntityList == null || rentContractEntityList.size() == 0) {
                LogUtil.error(LOGGER,"asyncSubContractTransferToPdfByParentId 未查到子合同 {}", surParentRentId);
                return DataTransferObjectBuilder.buildErrorJsonStr("合同数据为空 surParentRentId:" + surParentRentId);
            }
            for (RentContractEntity rentContractEntity : rentContractEntityList) {
                transferToPdfByZrams(rentContractEntity);
            }
            return DataTransferObjectBuilder.buildOkJsonStr("异步生成合同操作完成");
        } catch (Exception e) {
            LogUtil.error(LOGGER, "asyncSubContractTransferToPdfByParentId surParentRentId:{}", surParentRentId, e);
            return DataTransferObjectBuilder.buildErrorJsonStr("系统异常 asyncSubContractTransferToPdfByParentId");
        }

    }

    /**
     * 补偿生成合同文本
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    @Override
    public String asyncRetrySubContractTransferToPdf() {
        LogUtil.info(LOGGER,"begin asyncRetrySubContractTransferToPdf");
        long start = System.currentTimeMillis();
        int defaultLimit = 50;
        int defaultHours = -6;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, defaultHours);
        //分页查询
        Date queryDate = calendar.getTime();
        PageBounds pageBounds=new PageBounds();
        int page = 1;
        int size = 0;

        try {

            do {
                pageBounds.setPage(page);
                pageBounds.setLimit(defaultLimit);
                PagingResult<RentContractEntity> pagingResult = this.rentContractServiceImpl.findContractNotTransferToPdf(queryDate, pageBounds);
                //查询时间为6小时以内需要同步的数据
                List<RentContractEntity> rentContractEntityList = pagingResult.getRows();
                if (rentContractEntityList != null && rentContractEntityList.size() > 0) {
                    for (RentContractEntity rentContractEntity : rentContractEntityList) {
                        transferToPdfByZrams(rentContractEntity);
                        LogUtil.info(LOGGER,"deal asyncRetrySubContractTransferToPdf conRentCode : {}", rentContractEntity.getConRentCode());
                        size++;
                    }

                    if (rentContractEntityList.size() < defaultLimit) {
                        break;
                    }

                } else {
                    break;
                }
                page ++;


            } while (true);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "asyncRetrySubContractTransferToPdf ", e);
        }


        long end = System.currentTimeMillis();
        LogUtil.info(LOGGER,"end asyncRetrySubContractTransferToPdf total size:{},queryDate:{}, time:{}", size, queryDate, (end - start));
        return null;
    }

    /**
     * 获取续约合同的父合同id
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    @Override
    public String findRenewRootParentContractIds(String contractIds) {
        LogUtil.info(LOGGER, "--findRenewRootParentContractIds:{}", contractIds);
        try {
            if (Check.NuNStrStrict(contractIds)) {
                return DataTransferObjectBuilder.buildErrorJsonStr("参数为空");
            }
            String[] contractIdsArr = contractIds.split(",");
            List<BaseFieldVo> baseFieldVoList = new ArrayList<>();
            String preContractId = null;
            for (String contractId : contractIdsArr) {
                preContractId = contractId;
                RentContractEntity currentContract = null;
                do {
                    currentContract = rentContractServiceImpl.findContractBaseByContractId(contractId);
                    if (Check.NuNObj(currentContract)){
                        break;
                    }
                    String preConRentCode = currentContract.getPreConRentCode();
                    currentContract = rentContractServiceImpl.findValidContractByRentCode(preConRentCode);
                    contractId = currentContract.getContractId();
                }while (ContractSignTypeEnum.RENEW.getValue().equals(currentContract.getFsigntype()));
                BaseFieldVo baseFieldVo = new BaseFieldVo();
                baseFieldVo.setName(preContractId);
                baseFieldVo.setValue(contractId);
                baseFieldVoList.add(baseFieldVo);
            }

            return DataTransferObjectBuilder.buildOkJsonStr(baseFieldVoList);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "findRenewRootParentContractIds contractIds:{}", contractIds, e);
            return DataTransferObjectBuilder.buildErrorJsonStr("系统异常 findRenewRootParentContractIds:" + contractIds);
        }

    }

    /**
     * 通过zrams生成pdf
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public DataTransferObject transferToPdfByZrams(RentContractEntity rentContractEntity) {
        String url = String.format(zramsTransferEnterprisePdfUrl, rentContractEntity.getContractId());
        try {
            LogUtil.info(LOGGER,"transferToPdfByZrams url:{}", url);
            String result = HttpUtil.sendGetRequest(url, null, null);
            LogUtil.info(LOGGER,"transferToPdfByZrams url:{},result:{}", url, result);
            if (!Check.NuNStrStrict(result)) {
                Map map = JsonEntityTransform.json2Map(result);
                String status = (String)map.get("status");
                if (SUCCESS.equals(status)) {
                    this.rentContractServiceImpl.updateIsTransferPdf(rentContractEntity.getContractId());
                    LogUtil.info(LOGGER,"transferToPdfByZrams ConRentCode:{}, ContractId:{}", rentContractEntity.getConRentCode(), rentContractEntity.getContractId());
                    return DataTransferObjectBuilder.buildOk("");
                }

            }
            LogUtil.error(LOGGER, "transferToPdfByZrams result ERROR {},{}", url, result);
            return DataTransferObjectBuilder.buildError("");
        } catch (Exception e) {
            LogUtil.error(LOGGER, "transferToPdfByZrams ERROR  contractId:{},{}",rentContractEntity.getContractId(), rentContractEntity.getConRentCode(), e);
            return DataTransferObjectBuilder.buildError("生成pdf 错误:" + rentContractEntity.getConRentCode());
        }
    }

    /**
     * 生成pdf
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    @Deprecated
    public DataTransferObject transferToPdf(RentContractEntity rentContractEntity) {
        //TODO cuiyh9 重复如何处理？reids加锁还是?
        String url = String.format(zramsGetEnterpriseContracthtmlUrl, rentContractEntity.getContractId());
        String contractHtml = null;
        try {
            LogUtil.info(LOGGER,"transferToPdf url:{}", url);

            contractHtml = HttpUtil.sendGetRequest(url, null, null);
            if (Check.NuNStrStrict(contractHtml)) {
                LogUtil.error(LOGGER,"contractId:{},{},{}",rentContractEntity.getContractId(), rentContractEntity.getConRentCode());
                return DataTransferObjectBuilder.buildError("获取html 错误" + rentContractEntity.getConRentCode());
            }

            //生成pdf
            StringBuilder pdfFilePath = new StringBuilder(HtmltoPDF.pdfPath);
            pdfFilePath.append(HtmltoPDF.contractFile);

            pdfFilePath.append(rentContractEntity.getConRentCode()).append(HtmltoPDF.pdfSuffix);
            HtmltoPDF.htmlStrToPdf(contractHtml, pdfFilePath.toString());
            this.rentContractServiceImpl.updateIsTransferPdf(rentContractEntity.getContractId());
            LogUtil.info(LOGGER,"transferToPdf ConRentCode:{}, ContractId:{}", rentContractEntity.getConRentCode(), rentContractEntity.getContractId());
            return DataTransferObjectBuilder.buildOk("");
        } catch (Exception e) {
            LogUtil.error(LOGGER, "transferToPdf ERROR  contractId:{},{}",rentContractEntity.getContractId(), rentContractEntity.getConRentCode(), e);
            return DataTransferObjectBuilder.buildError("生成pdf 错误:" + rentContractEntity.getConRentCode());
        }




    }

    /**
     * 延后续约
     * @author cuiyuhui
     * @created
     * @param contractId 合同ID
     * @return
     */
    @Override
    public String clearDelayRenewContract(String contractId) {
        RentContractEntity renewRentContractEntity = this.rentContractServiceImpl.findContractBaseByContractId(contractId);
        waterClearingLogic.clearDelayRenewContract(renewRentContractEntity);
        return DataTransferObjectBuilder.buildOkJsonStr("");
    }


}
