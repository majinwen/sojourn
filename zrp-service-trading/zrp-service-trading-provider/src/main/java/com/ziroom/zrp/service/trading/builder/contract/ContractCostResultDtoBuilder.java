package com.ziroom.zrp.service.trading.builder.contract;

import com.asura.framework.base.util.Check;
import com.ziroom.zrp.houses.entity.HouseTypeEntity;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.service.trading.dto.ContractCostResultDto;
import com.ziroom.zrp.service.trading.dto.ContractRoomCostResultDto;
import com.ziroom.zrp.service.trading.dto.PaymentTermsDto;
import com.ziroom.zrp.service.trading.dto.RoomRentBillDto;
import com.ziroom.zrp.trading.entity.RentContractActivityEntity;
import com.ziroom.zrp.trading.entity.RentContractEntity;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>未签实体对象builder合同信息页面</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月23日 14:00
 * @since 1.0
 */
public class ContractCostResultDtoBuilder {

    private  ContractCostResultDto contractCostResultDto;

    public ContractCostResultDtoBuilder() {
        contractCostResultDto = new ContractCostResultDto();
    }


    /**
     * 第一步，构建房间费用名细
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public ContractCostResultDtoBuilder  buildContractRoomCostResultDtoList(List<RentContractEntity> rentContractEntityList,
                                                                               Map<String, RoomInfoEntity> roomInfoEntityMap,
                                                                               Map<String, HouseTypeEntity> houseTypeEntityMap,
                                                                               Map<String, PaymentTermsDto> paymentTermsDtoMap) {

        List<ContractRoomCostResultDto>  contractRoomCostResultDtoList = new ArrayList<>();

        for(RentContractEntity rentContractEntity : rentContractEntityList) {

            RoomInfoEntity roomInfoEntity = roomInfoEntityMap.get(rentContractEntity.getRoomId());
            HouseTypeEntity houseTypeEntity = houseTypeEntityMap.get(roomInfoEntity.getHousetypeid());
            PaymentTermsDto paymentTermsDto = paymentTermsDtoMap.get(rentContractEntity.getContractId());

            ContractRoomCostResultDto contractRoomCostResultDto = new ContractRoomCostResultDto();
            contractRoomCostResultDto.setRoomId(roomInfoEntity.getFid());
            contractRoomCostResultDto.setRoomNumber(roomInfoEntity.getFroomnumber());
            contractRoomCostResultDto.setHouseTypeName(houseTypeEntity.getFhousetypename());
            contractRoomCostResultDto.setRoomArea(String.valueOf(roomInfoEntity.getFroomarea()));
            contractRoomCostResultDto.setBasePrice(BigDecimal.valueOf(roomInfoEntity.getFlongprice()));
            contractRoomCostResultDto.setActualPrice(paymentTermsDto.getRentPrice());
            contractRoomCostResultDto.setMustCommission(paymentTermsDto.getOriginServicePrice());

            //优惠后服务费用与实际押金需要动态从房间中获取
            List<RoomRentBillDto> roomRentBillDtoList = paymentTermsDto.getRoomRentBillDtos();

            // 服务费
            BigDecimal discountCommission = BigDecimal.ZERO;

            //押金
            BigDecimal mustDeposit = BigDecimal.ZERO;

            for (RoomRentBillDto roomRentBillDto : roomRentBillDtoList) {
                if (roomRentBillDto.getServicePrice() != null) {
                    discountCommission = discountCommission.add(roomRentBillDto.getServicePrice());
                }

                if (roomRentBillDto.getDepositPrice() != null) {
                    mustDeposit = mustDeposit.add(roomRentBillDto.getDepositPrice());
                }

            }

            contractRoomCostResultDto.setDiscountCommission(discountCommission);
            contractRoomCostResultDto.setMustDeposit(mustDeposit);
            contractRoomCostResultDtoList.add(contractRoomCostResultDto);

        }
        contractCostResultDto.setContractRoomCostResultDtoList(contractRoomCostResultDtoList);
        return this;
    }

    /**
     * 第二部、构建活动
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public ContractCostResultDtoBuilder buildActivityEntityList(List<RentContractActivityEntity> actList) {

        if (actList == null || actList.size() == 0) {
            return this;
        }

        //处理没有活动id的情况 -- 人工维护上了
        Map<String, Integer> activityIdMap = new HashMap<>();
        Integer tempActivityId = -1;

        List<RentContractActivityEntity> rentContractActivityEntities = new ArrayList<>();

        for (RentContractActivityEntity rentContractActivityEntity : actList) {
            String acitivityName = rentContractActivityEntity.getActivityName();

            if (rentContractActivityEntity.getActivityId() != null) {
                rentContractActivityEntities.add(rentContractActivityEntity);
            } else if (rentContractActivityEntity.getActivityId() == null &&  acitivityName != null && acitivityName.length() > 0) {
                Integer tAcId = activityIdMap.get(acitivityName);
                if (tAcId == null) {
                    tAcId = tempActivityId;
                    activityIdMap.put(acitivityName, tAcId);
                }
                rentContractActivityEntity.setActivityId(tAcId);
                rentContractActivityEntities.add(rentContractActivityEntity);
            }
            tempActivityId = tempActivityId + tempActivityId;
        }

        Map<Integer, List<RentContractActivityEntity>> activityMap = rentContractActivityEntities.stream().collect(Collectors.groupingBy(RentContractActivityEntity::getActivityId));
        List<RentContractActivityEntity> activityEntityList = new ArrayList<>();

        activityMap.forEach((activityId , rentContractActivityEntityList) -> {
            BigDecimal discountAccount = BigDecimal.ZERO;

            RentContractActivityEntity activityEntity = new RentContractActivityEntity();
            RentContractActivityEntity firstActivityEntity = rentContractActivityEntityList.get(0);
            for (RentContractActivityEntity tempActiveEntity : rentContractActivityEntityList) {
                Double tempDiscountAccount = tempActiveEntity.getDiscountAccount() == null ? 0D : tempActiveEntity.getDiscountAccount();
                discountAccount = discountAccount.add(new BigDecimal(String.valueOf(tempDiscountAccount)));
            }

            activityEntity.setActivityId(firstActivityEntity.getActivityId());
            activityEntity.setActivityName(firstActivityEntity.getActivityName());
            activityEntity.setExpenseItemCode(firstActivityEntity.getExpenseItemCode());
            activityEntity.setDiscountAccount(discountAccount.doubleValue());
            activityEntity.setCategory(firstActivityEntity.getCategory());

            activityEntityList.add(activityEntity);

        });
        contractCostResultDto.setActList(activityEntityList);
        return this;
    }

    /**
     *  第三步,构建 ContractCostResultDto
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public ContractCostResultDtoBuilder buildContractCostResultDto() {


        BigDecimal roomSalesPrice = new BigDecimal("0");
        BigDecimal actualPrice = new BigDecimal("0");
        BigDecimal conDeposit = new BigDecimal("0");
        BigDecimal conCommission = new BigDecimal("0");
        BigDecimal servicePrice = new BigDecimal("0");

        for (ContractRoomCostResultDto contractRoomCostResultDto : contractCostResultDto.getContractRoomCostResultDtoList()) {
            roomSalesPrice = roomSalesPrice.add(contractRoomCostResultDto.getBasePrice());
            actualPrice = actualPrice.add(contractRoomCostResultDto.getActualPrice());
            conDeposit = conDeposit.add(contractRoomCostResultDto.getMustDeposit());
            conCommission = conCommission.add(contractRoomCostResultDto.getMustCommission());
            servicePrice = servicePrice.add(contractRoomCostResultDto.getDiscountCommission());

        }

        contractCostResultDto.setRoomSalesPrice(roomSalesPrice);
        contractCostResultDto.setActualPrice(actualPrice);
        contractCostResultDto.setConDeposit(conDeposit);
        contractCostResultDto.setConCommission(conCommission);
        contractCostResultDto.setServicePrice(servicePrice);
        return this;
    }

    /**
     * 第四步，返回构建对象
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public ContractCostResultDto returnResult(){
        return contractCostResultDto;
    }

}
