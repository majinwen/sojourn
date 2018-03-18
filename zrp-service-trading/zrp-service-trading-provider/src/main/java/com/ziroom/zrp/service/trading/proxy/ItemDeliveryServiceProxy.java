package com.ziroom.zrp.service.trading.proxy;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.*;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.houses.entity.ItemListEntity;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.houses.entity.RoomInfoExtEntity;
import com.ziroom.zrp.service.houses.api.ItemsService;
import com.ziroom.zrp.service.houses.api.ProjectService;
import com.ziroom.zrp.service.houses.api.RoomService;
import com.ziroom.zrp.service.houses.api.SmartPlatformService;
import com.ziroom.zrp.service.houses.dto.smartplatform.waterwatt.WaterElectricityInfoDto;
import com.ziroom.zrp.service.houses.dto.smartplatform.waterwatt.WaterMeterStateDto;
import com.ziroom.zrp.service.houses.dto.smartplatform.waterwatt.WattHistoryElectricMeterDto;
import com.ziroom.zrp.service.houses.entity.ExtRoomItemsConfigVo;
import com.ziroom.zrp.service.houses.entity.smartplatform.waterwatt.WaterElectricityInfoVo;
import com.ziroom.zrp.service.houses.entity.smartplatform.waterwatt.WaterMeterStateVo;
import com.ziroom.zrp.service.houses.valenum.ItemTypeEnum;
import com.ziroom.zrp.service.trading.api.ItemDeliveryService;
import com.ziroom.zrp.service.trading.dto.ContractRoomDto;
import com.ziroom.zrp.service.trading.dto.MeterDeliverySaveDto;
import com.ziroom.zrp.service.trading.dto.SharerDto;
import com.ziroom.zrp.service.trading.dto.WattRechargeInfo;
import com.ziroom.zrp.service.trading.dto.finance.BillDto;
import com.ziroom.zrp.service.trading.dto.finance.ModifyReceiptBillRequest;
import com.ziroom.zrp.service.trading.dto.finance.ReceiptBillRequest;
import com.ziroom.zrp.service.trading.dto.finance.ReceiptBillResponse;
import com.ziroom.zrp.service.trading.entity.DeliveryRoomInfoVo;
import com.ziroom.zrp.service.trading.entity.LifeItemVo;
import com.ziroom.zrp.service.trading.proxy.commonlogic.FinanceBaseCall;
import com.ziroom.zrp.service.trading.proxy.commonlogic.FinanceCommonLogic;
import com.ziroom.zrp.service.trading.proxy.commonlogic.RentContractLogic;
import com.ziroom.zrp.service.trading.proxy.commonlogic.WaterClearingLogic;
import com.ziroom.zrp.service.trading.service.*;
import com.ziroom.zrp.service.trading.utils.builder.DataTransferObjectBuilder;
import com.ziroom.zrp.service.trading.valenum.ItemUsedStateEnum;
import com.ziroom.zrp.service.trading.valenum.ReceiBillGenWayEnum;
import com.ziroom.zrp.service.trading.valenum.ReceiBillStateEnum;
import com.ziroom.zrp.service.trading.valenum.ReceiBillTypeEnum;
import com.ziroom.zrp.service.trading.valenum.base.IsDelEnum;
import com.ziroom.zrp.service.trading.valenum.base.IsPayEnum;
import com.ziroom.zrp.service.trading.valenum.delivery.DeliveryFromEnum;
import com.ziroom.zrp.service.trading.valenum.finance.CostCodeEnum;
import com.ziroom.zrp.service.trading.valenum.finance.DocumentTypeEnum;
import com.ziroom.zrp.service.trading.valenum.surrender.ExpenseItemEnum;
import com.ziroom.zrp.service.trading.valenum.surrender.ResponsibilityEnum;
import com.ziroom.zrp.service.trading.valenum.waterwatt.WaterwattReadTypeEnum;
import com.ziroom.zrp.trading.entity.*;
import com.zra.common.exception.FinServiceException;
import com.zra.common.utils.DateUtilFormate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>合住人信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月18日 15:31
 * @since 1.0
 */
@Slf4j
@Component("trading.itemDeliveryServiceProxy")
public class ItemDeliveryServiceProxy implements ItemDeliveryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemDeliveryServiceProxy.class);

    @Resource(name = "trading.rentItemDeliveryServiceImpl")
    private RentItemDeliveryServiceImpl rentItemDeliveryServiceImpl;

    @Resource(name = "trading.rentContractServiceImpl")
    private RentContractServiceImpl rentContractServiceImpl;

    @Resource(name = "trading.shareServiceImpl")
    private ShareServiceImpl sharerServiceImpl;

    @Resource(name = "trading.expenseItemServiceImpl")
    private ExpenseItemServiceImpl expenseItemServiceImpl;

    @Resource(name = "trading.finReceiBillServiceImpl")
    private FinReceiBillServiceImpl finReceiBillServiceImpl;

    @Resource(name = "trading.finReceiBillDetailServiceImpl")
    private FinReceiBillDetailServiceImpl finReceiBillDetailServiceImpl;

    @Resource(name = "trading.callFinanceServiceProxy")
    private CallFinanceServiceProxy callFinanceServiceProxy;

    @Resource(name = "trading.rentContractLogic")
    private RentContractLogic rentContractLogic;

    @Resource(name="houses.projectService")
    private ProjectService projectService;

    @Resource(name="houses.itemsService")
    private ItemsService itemsService;

    @Resource(name="trading.financeBaseCall")
    private FinanceBaseCall financeBaseCall;

    @Resource(name="trading.financeCommonLogic")
    private FinanceCommonLogic financeCommonLogic;

    @Resource(name = "houses.roomService")
    private RoomService roomService;

    @Resource(name = "houses.smartPlatformService")
    private SmartPlatformService smartPlatformService;

    @Resource(name = "trading.waterClearingLogic")
    private WaterClearingLogic waterClearingLogic;

    @Override
    public String saveOrUpdateSharer(String paramJson) {
        LogUtil.info(LOGGER,"【saveSharer】参数para={}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        SharerDto sharerDto = JsonEntityTransform.json2Object(paramJson, SharerDto.class);
        if (Check.NuNStr(sharerDto.getName())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("真实姓名为空");
            return dto.toJsonString();
        }
        if (Check.NuNObj(sharerDto.getCertType())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("证件类型为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(sharerDto.getCertNo())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("证件号为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(sharerDto.getPhone())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("联系方式为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(sharerDto.getContractId())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("合同id为空");
            return dto.toJsonString();
        }
        String fid = sharerDto.getFid();
        SharerEntity sharerEntity = new SharerEntity();
        sharerEntity.setFname(sharerDto.getName());
        sharerEntity.setFcerttype(sharerDto.getCertType());
        sharerEntity.setFcertnum(sharerDto.getCertNo());
        sharerEntity.setFmobile(sharerDto.getPhone());
        sharerEntity.setFcontractid(sharerDto.getContractId());
        sharerEntity.setFid(sharerDto.getFid());
        if (Check.NuNStr(sharerDto.getFid())){
            fid = sharerServiceImpl.saveSharer(sharerEntity);
        }else{
            sharerServiceImpl.updateByFid(sharerEntity);
        }
        dto.putValue("fid",fid);
        return dto.toJsonString();
    }

    @Override
    public String listSharerByContractId(String paramJson) {
        LogUtil.info(LOGGER,"【listSharerByContractId】参数para={}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(paramJson)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        List<SharerEntity> sharerEntities = sharerServiceImpl.listSharerByContractId(paramJson);
        dto.parseData("list", new TypeReference<List<String>>() {
        });
        dto.putValue("list",sharerEntities);
        return dto.toJsonString();
    }

    @Override
    public String deleteSharerByFid(String paramJson) {
        LogUtil.info(LOGGER,"【updateByFid】参数para={}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(paramJson)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        int count = sharerServiceImpl.deleteByFid(paramJson);
        dto.putValue("count",count);
        return dto.toJsonString();
    }

    @Override
    public String listValidItemByContractIdAndRoomId(String paramJson) {
        LogUtil.info(LOGGER,"参数={}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        ContractRoomDto contractRoomDto = JsonEntityTransform.json2Object(paramJson, ContractRoomDto.class);
        if (Check.NuNObj(contractRoomDto)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(contractRoomDto.getContractId())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("合同号为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(contractRoomDto.getRoomId())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("房间ID为空");
            return dto.toJsonString();
        }
        List<RentItemDeliveryEntity> list = rentItemDeliveryServiceImpl.listValidItemByContractIdAndRoomId(contractRoomDto);
        dto.putValue("list",list);
        return dto.toJsonString();
    }

    /**
     * 获取有效物品
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    @Override
    public String listValidItemByContractIds(String contractIds) {
        LogUtil.info(LOGGER,"listValidItemByContractIds参数={}",contractIds);
        if (Check.NuNStrStrict(contractIds)) {
            return DataTransferObjectBuilder.buildErrorJsonStr("参数为空");
        }
        List<String> contractIdList = Arrays.asList(contractIds.split(","));
        List<RentItemDeliveryEntity> list =  rentItemDeliveryServiceImpl.listValidItemByContractIds(contractIdList);
        return DataTransferObjectBuilder.buildOkJsonStr(list);
    }

    @Override
    public String findMeterDetailById(String paramJson) {
        LogUtil.info(LOGGER,"【findMeterDetailById】参数={}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(paramJson)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        ContractRoomDto contractRoomDto = JsonEntityTransform.json2Object(paramJson, ContractRoomDto.class);
        if (Check.NuNStr(contractRoomDto.getContractId())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("合同号为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(contractRoomDto.getRoomId())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("房间ID为空");
            return dto.toJsonString();
        }
        MeterDetailEntity meterDetail = rentItemDeliveryServiceImpl.findMeterDetailById(contractRoomDto);
        dto.putValue("meterDetail",meterDetail);
        return dto.toJsonString();
    }

    /**
     * 查询水电交割详情信息
     * @author cuiyh9
     * @created 2017年09月25日 18:18:16
     * @param
     * @return
     */
    @Override
    public String findMeterDetailsByContractIds(String contractIds) {
        LogUtil.info(LOGGER,"findMeterDetailsByContractIds={}",contractIds);
        if (Check.NuNStrStrict(contractIds)) {
            return DataTransferObjectBuilder.buildErrorJsonStr("参数为空");
        }

        List<String> contractIdList = Arrays.asList(contractIds.split(","));
        List<MeterDetailEntity> meterDetailEntityList = rentItemDeliveryServiceImpl.findMeterDetailsByContractIds(contractIdList);
        return DataTransferObjectBuilder.buildOkJsonStr(meterDetailEntityList);
    }

    @Override
    public String confirmDelivery(String contractId) {
        LogUtil.info(LOGGER,"【confirmDelivery】contractId={}",contractId);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(contractId)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("合同号为空");
            return dto.toJsonString();
        }
        dto = rentContractLogic.confirmDelivery(contractId);
        return dto.toJsonString();
    }
    /**
     * <p>通过合同ID查询生活费用项</p>
     * @author xiangb
     * @created 2017年9月28日
     * @param
     * @return
     */
    public String findLifeItemByContractId(String contractId){
    	LogUtil.info(LOGGER,"【findLifeItemByContractId】contractId={}",contractId);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(contractId)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("合同ID为空！");
            return dto.toJsonString();
        }
        try{
        	RentContractEntity contractEntity = rentContractServiceImpl.findContractBaseByContractId(contractId);
        	LogUtil.info(LOGGER,"【findLifeItemByContractId】查询合同信息返回：{}",JSONObject.toJSON(contractEntity));
            if (Check.NuNObj(contractEntity)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同不存在!");
                return dto.toJsonString();
            }
            String roomId = contractEntity.getRoomId();
            ContractRoomDto contractRoomDto = new ContractRoomDto();
            contractRoomDto.setContractId(contractId);
            contractRoomDto.setRoomId(roomId);
            List<LifeItemVo> lifeItems = rentItemDeliveryServiceImpl.findLifeItemByContractIdAndRoomId(contractRoomDto);
            LogUtil.info(LOGGER,"【findLifeItemByContractId】查询其他费用项返回：{}",JSONObject.toJSON(lifeItems));
            dto.putValue("lifeItems", lifeItems);
            return dto.toJsonString();
        }catch(Exception e){
        	LogUtil.info(LOGGER,"【findLifeItemByContractId】出错：{}",e);
        	dto.setErrCode(DataTransferObject.ERROR);
        	dto.setMsg("系统异常！");
        	return dto.toJsonString();
        }
        
    }

    /**
     * <p>通过合同ID查询生活费用项</p>
     * @author xiangb
     * @created 2017年9月28日
     * @param
     * @return
     */
    public String findLifeItemByContractIdList(String contractIds) {
        LogUtil.info(LOGGER,"【findLifeItemByContractIdList】contractIds={}", contractIds);
        if (Check.NuNStr(contractIds)){
            return DataTransferObjectBuilder.buildErrorJsonStr("参数为空");
        }
        List<String> contractIdList = Arrays.asList(contractIds.split(","));
        List<LifeItemVo> lifeItems = rentItemDeliveryServiceImpl.listLifeItemByContractIds(contractIdList);
        return DataTransferObjectBuilder.buildOkJsonStr(lifeItems);
    }

    /**
     * 查询物业交割相关信息
     * @author jixd
     * @created 2017年10月30日 19:44:51
     * @param
     * @return
     */
    @Override
    public String findDeliveryInfo(String paramJson) {
        LogUtil.info(LOGGER,"【findLeftItemAndMeterInfo】参数={}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        ContractRoomDto contractRoomDto = JsonEntityTransform.json2Object(paramJson, ContractRoomDto.class);
        if (Check.NuNObj(contractRoomDto)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(contractRoomDto.getContractId())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("合同Id为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(contractRoomDto.getRoomId())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("房间号为空");
            return dto.toJsonString();
        }
        //费用项
        List<ExpenseItemEntity> expenseItemEntities = expenseItemServiceImpl.listExpenseByItemCodes(CostCodeEnum.getLifeFeeCode());
        //水电交割数据
        MeterDetailEntity meterDetailEntity = rentItemDeliveryServiceImpl.findMeterDetailById(contractRoomDto);
        //生活费用项
        List<LifeItemVo> leftItems = rentItemDeliveryServiceImpl.findLifeItemByContractIdAndRoomId(contractRoomDto);
        //物业交割物品信息
        List<RentItemDeliveryEntity> rentItemDeliveryEntities = rentItemDeliveryServiceImpl.listValidItemByContractIdAndRoomId(contractRoomDto);
        //合同详情
        RentDetailEntity rentDetail = rentContractServiceImpl.findRentDetailById(contractRoomDto);

        dto.putValue("expenseItems",expenseItemEntities);
        dto.putValue("meterDetail",meterDetailEntity);
        dto.putValue("leftItems",leftItems);
        dto.putValue("rentItems",rentItemDeliveryEntities);
        dto.putValue("rentDetail",rentDetail);

        LogUtil.info(LOGGER,"结果={}",dto.toJsonString());
        return dto.toJsonString();
    }

    /**
     * 保存物业交割相关信息 个人签约后台
     * @author jixd
     * @created 2017年11月03日 15:28:37
     * @param
     * @return
     */
    @Override
    public String saveDeliveryInfo(String paramJson) {
        LogUtil.info(LOGGER,"【saveDeliveryInfo】参数param={}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        MeterDeliverySaveDto meterDeliverySaveDto = JsonEntityTransform.json2Object(paramJson, MeterDeliverySaveDto.class);
        if (Check.NuNObj(meterDeliverySaveDto)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(meterDeliverySaveDto.getContractId())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("合同ID为空");
            return dto.toJsonString();
        }

        if (Check.NuNObj(meterDeliverySaveDto.getMeterDetail())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("水电费用为空");
            return dto.toJsonString();
        }
        if(Check.NuNCollection(meterDeliverySaveDto.getRentItems())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("物品信息为空");
            return dto.toJsonString();
        }

        try{
            String contractId = meterDeliverySaveDto.getContractId();
            RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId(contractId);
            if (Check.NuNObj(rentContractEntity)){
                LogUtil.info(LOGGER,"【saveDeliveryInfo】合同不存在contractId={}",contractId);
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同不存在");
                return dto.toJsonString();
            }

            //查询应收账单
            List<ReceiptBillResponse> listBill = null;
            //来自app需要查询财务账单是否支付
            if (meterDeliverySaveDto.getFrom() == DeliveryFromEnum.APP.getCode()){
                ReceiptBillRequest receiptBillRequest = new ReceiptBillRequest();
                receiptBillRequest.setOutContractCode(rentContractEntity.getConRentCode());
                receiptBillRequest.setDocumentType(DocumentTypeEnum.LIFE_FEE.getCode());
                Map<String,Object> receiptMap = financeBaseCall.getReceivableBillInfo(receiptBillRequest);
                int isPay = (int)receiptMap.get("isPay");
                int isEmpty = (int)receiptMap.get("isEmpty");
                //已支付 并且账单不为空
                if (isPay == IsPayEnum.YES.getCode() && isEmpty == 1){
                    LogUtil.info(LOGGER,"【saveDeliveryInfo】合同{}水电生活费用账单已支付不能修改!",contractId);
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("水电生活费用账单已支付不能修改！");
                    return dto.toJsonString();
                }
                listBill = (List<ReceiptBillResponse>) receiptMap.get("listStr");
                LogUtil.info(LOGGER,"【saveDeliveryInfo】来自app物业交割,财务账单数据contractId={}",meterDeliverySaveDto.getContractId());
            }

            //处理需要保存的物业交割数据
            String roomId = rentContractEntity.getRoomId();
            String createId = meterDeliverySaveDto.getCreateId();
            String cityId = meterDeliverySaveDto.getCityId();
            List<RentItemDeliveryEntity> rentItems = meterDeliverySaveDto.getRentItems();
            for (RentItemDeliveryEntity item:rentItems){
                item.setFid(null);
                item.setContractid(contractId);
                item.setRoomId(roomId);
                item.setCreaterid(createId);
                item.setUpdaterid(createId);
                item.setCityid(cityId);
                item.setFstate(String.valueOf(0));//使用状态 [0 正常 ,1 损坏，2 丢失]
                item.setFunitmeter(0.0);
                item.setForiginalnum(item.getFactualnum());
            }

            List<RentLifeItemDetailEntity> lifeFeeItems = meterDeliverySaveDto.getLifeFeeItems();
            if (!Check.NuNCollection(lifeFeeItems)){
                for (RentLifeItemDetailEntity lifeItemDetailEntity : lifeFeeItems){
                    lifeItemDetailEntity.setContractId(contractId);
                    lifeItemDetailEntity.setRoomId(roomId);
                    lifeItemDetailEntity.setCreaterId(createId);
                }
            }

            MeterDetailEntity meterDetail = meterDeliverySaveDto.getMeterDetail();
            meterDetail.setFcontractid(contractId);
            meterDetail.setRoomId(roomId);
            meterDetail.setFcreaterid(createId);
            //保存或者更新物业交割数据
            int count = rentItemDeliveryServiceImpl.saveDeliveryInfo(meterDeliverySaveDto);

            LogUtil.info(LOGGER,"【saveDeliveryInfo】保存物业交割数据count={},contractId={}",count,contractId);

            //如果是来自from = 1 来自app物业交割
            if (count >0 && meterDeliverySaveDto.getFrom() == DeliveryFromEnum.APP.getCode()){
                LogUtil.info(LOGGER,"【saveDeliveryInfo】app物业交割开始同步财务应收,contractId={}",meterDeliverySaveDto.getContractId());
                if (Check.NuNCollection(lifeFeeItems)){
                    lifeFeeItems = new ArrayList<>();
                }
                //水电费用放到生活费用中去
                if (!Check.NuNObj(meterDetail.getFpaywaterprice())){
                    RentLifeItemDetailEntity waterEntity = new RentLifeItemDetailEntity();
                    waterEntity.setExpenseitemId(String.valueOf(CostCodeEnum.ZRYSF.getZraId()));
                    waterEntity.setPaymentAmount(meterDetail.getFpaywaterprice());
                    lifeFeeItems.add(waterEntity);
                }
                if (!Check.NuNObj(meterDetail.getFpayelectricprice())){
                    RentLifeItemDetailEntity eleEntity = new RentLifeItemDetailEntity();
                    eleEntity.setExpenseitemId(String.valueOf(CostCodeEnum.ZRYDF.getZraId()));
                    eleEntity.setPaymentAmount(meterDetail.getFpayelectricprice());
                    lifeFeeItems.add(eleEntity);
                }

                //生活费用账单不为空 如果金额有变动更新应收
                if (!Check.NuNCollection(listBill)){
                    LogUtil.info(LOGGER,"财务账单不为空，开始处理修改财务数据");
                    //修改物业交割应收数据
                    DataTransferObject modifyDto = modifyFinanceLifeItem(listBill, lifeFeeItems,rentContractEntity,createId);
                   if (modifyDto.getCode() == DataTransferObject.ERROR){
                       LogUtil.info(LOGGER,"修改应收账单错误error={}",modifyDto.toJsonString());
                       return modifyDto.toJsonString();
                   }
                }else{
                    createFinBill(rentContractEntity,lifeFeeItems,createId,0);
                }

                // 清算水费流程
                waterClearingLogic.clearNewContract(rentContractEntity);
            }
            dto.putValue("count",count);
            LogUtil.info(LOGGER,"【saveDeliveryInfo】结果={}",dto.toJsonString());
            return dto.toJsonString();
        }catch (Exception e){
            LogUtil.info(LOGGER,"【saveDeliveryInfo】异常错误e={}",e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务错误");
            return dto.toJsonString();
        }
    }

    /**
     * 创建账单  或者修改以后会产生新得数据订单 本地也要记录
     * @author jixd
     * @created 2017年11月29日 16:30:57
     * @param
     * @param
     * @return
     */
    private DataTransferObject createFinBill(RentContractEntity rentContractEntity, List<RentLifeItemDetailEntity> lifeFeeItems, String createId,int isModify){
        DataTransferObject dto = new DataTransferObject();
        LogUtil.info(LOGGER,"创建本地账单，同步应收账单");
        try {
            Date firstPayTime = rentContractEntity.getFirstPayTime();
            String startTime = DateUtil.dateFormat(rentContractEntity.getFirstPayTime(),"yyyy-MM-dd");
            Date endDate = DateUtilFormate.addHours(firstPayTime,48);
            String endTime = DateUtil.dateFormat(endDate,"yyyy-MM-dd");
            String proDate = startTime;
            String conRentCode = rentContractEntity.getConRentCode();
            FinReceiBillEntity finReceiBillEntity = new FinReceiBillEntity();//生活费用
            List<FinReceiBillDetailEntity> receiBillDetails = new ArrayList<>();//生活费用明细
            String billFid = UUIDGenerator.hexUUID();
            finReceiBillEntity.setFid(billFid);
            finReceiBillEntity.setContractId(rentContractEntity.getContractId());
            finReceiBillEntity.setBillState(ReceiBillStateEnum.WSK.getCode());
            finReceiBillEntity.setBillType(1);
            finReceiBillEntity.setBillState(0);
            finReceiBillEntity.setGenWay(1);
            finReceiBillEntity.setPlanGatherDate(rentContractEntity.getFirstPayTime());
            finReceiBillEntity.setStartCycle(rentContractEntity.getFirstPayTime());
            finReceiBillEntity.setEndCycle(endDate);
            finReceiBillEntity.setCreateId(createId);
            finReceiBillEntity.setCityId(rentContractEntity.getCityid());
            //总金额
            double totalPay = 0.0;
            //财务同步应收列表
            List<BillDto> bills = new ArrayList<>();
            //批量获取财务生活费用应收账单编号
            JSONArray lifeBillNumArray = financeBaseCall.callFinaCreateBillNumBatch(DocumentTypeEnum.LIFE_FEE.getCode(),lifeFeeItems.size());
            int indexLife = 0;//用于从应收账单数组中取出应收编号(生活费用)
            for (RentLifeItemDetailEntity rentLifeItemDetailEntity : lifeFeeItems){
                //生活费用账单
                BillDto billDto = new BillDto();
                Double paymentAmount = rentLifeItemDetailEntity.getPaymentAmount() == null ? 0.0:rentLifeItemDetailEntity.getPaymentAmount();
                Integer payMoney = BigDecimal.valueOf(paymentAmount).multiply(BigDecimal.valueOf(100)).intValue();
                if (payMoney == 0){
                    //如果金额是0 不需要同步
                    continue;
                }

                String costCode = CostCodeEnum.getById(Integer.parseInt(rentLifeItemDetailEntity.getExpenseitemId())).getCode();
                billDto.setBillNum(lifeBillNumArray.getString(indexLife));
                indexLife++;
                billDto.setDocumentType(DocumentTypeEnum.LIFE_FEE.getCode());
                billDto.setUid(rentContractEntity.getCustomerUid());
                billDto.setUsername(rentContractEntity.getCustomerName());
                billDto.setPreCollectionDate(proDate);
                billDto.setStartTime(startTime);
                billDto.setEndTime(endTime);
                billDto.setHouseCode(rentContractEntity.getHouseRoomNo());
                billDto.setHouseId(rentContractEntity.getProjectId());
                billDto.setCostCode(conRentCode);
                billDto.setHouseKeeperCode(rentContractEntity.getFhandlezocode());
                billDto.setCostCode(costCode);
                billDto.setDocumentAmount(payMoney);
                bills.add(billDto);
                //本地保存应收数据组装
                totalPay += rentLifeItemDetailEntity.getPaymentAmount();
                FinReceiBillDetailEntity detailEntity = new FinReceiBillDetailEntity();
                detailEntity.setFid(UUIDGenerator.hexUUID());
                detailEntity.setBillFid(billFid);
                detailEntity.setExpenseItemId(Integer.parseInt(rentLifeItemDetailEntity.getExpenseitemId()));
                detailEntity.setOughtAmount(rentLifeItemDetailEntity.getPaymentAmount());
                detailEntity.setRoomId(rentContractEntity.getRoomId());
                detailEntity.setCreateId(createId);
                detailEntity.setCityId(rentContractEntity.getCityid());
                detailEntity.setUpdateId(createId);
                detailEntity.setBillType(DocumentTypeEnum.LIFE_FEE.getCode());
                detailEntity.setBillNum(billDto.getBillNum());
                receiBillDetails.add(detailEntity);
            }
            finReceiBillEntity.setOughtTotalAmount(totalPay);

            //同步账单
            if (!Check.NuNCollection(bills)){
                LogUtil.info(LOGGER,"【saveDeliveryInfo】开始同步财务数据");
                DataTransferObject financeDto = financeBaseCall.callFinaCreateReceiptBill(conRentCode, bills);
                LogUtil.info(LOGGER,"【saveDeliveryInfo】同步财务结果={}",financeDto.toJsonString());
                saveFinReceiBill(finReceiBillEntity,receiBillDetails,rentContractEntity,financeDto,isModify);
            }
        }catch (Exception e){
            LogUtil.error(LOGGER,"【saveDeliveryInfo】新增生活费用应收账单错误e={}",e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("异常错误");
        }
        return dto;
    }


    /**
     *  电费充值： 生成电费应收账单
     *
     *   1. 参数校验,成功
     *
     *   2. 去财务生成应收账单，成功
     *
     *   3. 同步财务数据,成功
     *
     *   4. 落地应收账单、应收账单明细到自如寓库，成功
     *
     *   5. 以上失败，处理失败流程，并返回失败信息
     * @author yd
     * @created
     * @param
     * @return
     */
    @Override
    public String generatingElectricityBill(String paramJson){


        DataTransferObject dto = new DataTransferObject();

        try {
            WattRechargeInfo  wattRechargeInfo = JsonEntityTransform.json2Object(paramJson,WattRechargeInfo.class);

            if (wattRechargeInfo == null){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数不存在");
                return  dto.toJsonString();
            }

            log.info("【电费充值： 生成电费应收账单】参数memterRechargeDto={}",paramJson);
            if(Check.NuNStr(wattRechargeInfo.getContractId())){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同号不存在");
                return  dto.toJsonString();
            }

            if(Check.NuNObj(wattRechargeInfo.getRechargeMoney())){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("充值金额错误");
                return  dto.toJsonString();
            }

            RentContractEntity rentContractEntity =  this.rentContractServiceImpl.findContractBaseByContractId(wattRechargeInfo.getContractId());

            if (rentContractEntity == null){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同已经不存在");
                return  dto.toJsonString();
            }

            wattRechargeInfo.setRentContractEntity(rentContractEntity);

            //填充应收账单
            fillReceiBill(wattRechargeInfo,dto);

            if(dto.getCode() == DataTransferObject.ERROR){
                return  dto.toJsonString();
            }
            List<BillDto> bills  = wattRechargeInfo.getBills();
            if (!Check.NuNCollection(bills)){
                LogUtil.info(LOGGER,"【generatingElectricityBill】开始同步财务数据");
                DataTransferObject financeDto = financeBaseCall.callFinaCreateReceiptBill(rentContractEntity.getConRentCode(), bills);
                LogUtil.info(LOGGER,"【generatingElectricityBill】同步财务结果={}",financeDto.toJsonString());
                dto =  saveFinReceiBill(wattRechargeInfo.getFinReceiBillEntity(),wattRechargeInfo.getReceiBillDetails(),rentContractEntity,financeDto,0);

                if (financeDto.getCode() == DataTransferObject.ERROR){
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("充值失败,请重试");
                }else{
                    dto.putValue("list",financeDto.getData().get("list"));
                }
            }

        }catch (Exception e){
            LogUtil.error(LOGGER,"【generatingElectricityBill】新增生活费用应收账单错误e={}",e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("电费充值失败");
        }

        return  dto.toJsonString();
    }


    /**
     *  填充 应收账单
     * @author yd
     * @created
     * @param
     * @return
     */
    private void fillReceiBill(WattRechargeInfo  wattRechargeInfo,DataTransferObject dto){


        try {
            RentContractEntity rentContractEntity = wattRechargeInfo.getRentContractEntity();
            FinReceiBillEntity finReceiBillEntity = new FinReceiBillEntity();//生活费用
            List<FinReceiBillDetailEntity> receiBillDetails = new ArrayList<>();//生活费用明细
            //财务同步应收列表
            List<BillDto> bills = new ArrayList<>();

            wattRechargeInfo.setFinReceiBillEntity(finReceiBillEntity);
            wattRechargeInfo.setReceiBillDetails(receiBillDetails);
            wattRechargeInfo.setBills(bills);

            //收款时间
            Date startTime = new Date();
            String billFid = UUIDGenerator.hexUUID();
            finReceiBillEntity.setFid(billFid);
            finReceiBillEntity.setBillState(ReceiBillStateEnum.WSK.getCode());
            finReceiBillEntity.setBillType(ReceiBillTypeEnum.INTELLECT_WATT.getCode());
            finReceiBillEntity.setGenWay(ReceiBillGenWayEnum.ZDSC.getCode());
            //finReceiBillEntity.setPayNum(1);
            finReceiBillEntity.setPlanGatherDate(startTime);

            BigDecimal  rechargeMoney = BigDecimal.valueOf(wattRechargeInfo.getRechargeMoney()).divide(BigDecimal.valueOf(100));
            finReceiBillEntity.setOughtTotalAmount(rechargeMoney.doubleValue());
            finReceiBillEntity.setActualTotalAmount(0.0);
            finReceiBillEntity.setStartCycle(startTime);
            finReceiBillEntity.setEndCycle(startTime);

            finReceiBillEntity.setContractId(rentContractEntity.getContractId());
            finReceiBillEntity.setCityId(rentContractEntity.getCityid());
            finReceiBillEntity.setCreateId(rentContractEntity.getCustomerUid());
            finReceiBillEntity.setUpdateId(rentContractEntity.getCustomerUid());
            finReceiBillEntity.setIsCalcWyj(1);
            String billNum =  financeBaseCall.callFinaCreateBillNum(DocumentTypeEnum.LIFE_FEE.getCode());
            BillDto billDto = new BillDto();
            Double paymentAmount = finReceiBillEntity.getOughtTotalAmount();
            Integer payMoney = BigDecimal.valueOf(paymentAmount).multiply(BigDecimal.valueOf(100)).intValue();

            if (payMoney == 0){
                //如果金额是0 不需要同步
                return;
            }

            FinReceiBillDetailEntity detailEntity = new FinReceiBillDetailEntity();
            detailEntity.setFid(UUIDGenerator.hexUUID());
            detailEntity.setBillFid(billFid);
            detailEntity.setExpenseItemId(Integer.valueOf(ExpenseItemEnum.DF.getId()));
            detailEntity.setOughtAmount(paymentAmount);
            detailEntity.setCityId(rentContractEntity.getCityid());
            detailEntity.setRoomId(rentContractEntity.getRoomId());
            detailEntity.setCreateId(rentContractEntity.getCustomerUid());
            detailEntity.setUpdateId(rentContractEntity.getCustomerUid());
            detailEntity.setBillType(DocumentTypeEnum.LIFE_FEE.getCode());
            detailEntity.setBillNum(billNum);
            receiBillDetails.add(detailEntity);

            String costCode = CostCodeEnum.getById(Integer.valueOf(ExpenseItemEnum.DF.getId())).getCode();
            billDto.setBillNum(billNum);
            billDto.setDocumentType(DocumentTypeEnum.LIFE_FEE.getCode());
            billDto.setUid(rentContractEntity.getCustomerUid());
            billDto.setUsername(rentContractEntity.getCustomerName());

            billDto.setPreCollectionDate(DateUtil.dateFormat(startTime,"yyyy-MM-dd"));
            billDto.setStartTime(DateUtil.dateFormat(startTime,"yyyy-MM-dd"));
            billDto.setEndTime(DateUtil.dateFormat(startTime,"yyyy-MM-dd"));
            billDto.setHouseCode(rentContractEntity.getHouseRoomNo());
            billDto.setHouseId(rentContractEntity.getProjectId());
            billDto.setHouseKeeperCode(rentContractEntity.getFhandlezocode());
            billDto.setCostCode(costCode);
            billDto.setDocumentAmount(payMoney);
            bills.add(billDto);

        }catch (FinServiceException e){
            log.error("【电费充值fillReceiBill】生成应收账单异常e={}",e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("生成应收账单失败");
        }catch (Exception e){
            log.error("【电费充值fillReceiBill】生成应收账单异常e={}",e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("生成应收账单失败");
        }

    }

    /**
     * 修改财务生活费用账单
     * @author jixd
     * @created 2017年11月08日 19:56:44
     * @param
     * @param rentContractEntity
     *@param createId @return
     */
    private DataTransferObject modifyFinanceLifeItem(List<ReceiptBillResponse> listBill, List<RentLifeItemDetailEntity> lifeFeeItems, RentContractEntity rentContractEntity, String createId) {
        LogUtil.info(LOGGER,"【saveDeliveryInfo】生活费用账单不为空，更新应收数据");
        DataTransferObject dto = new DataTransferObject();
        List<ReceiptBillResponse> upResponses = new ArrayList<>();
        List<ReceiptBillResponse> noUpResponses = new ArrayList<>();
        List<ReceiptBillResponse> delResponses = new ArrayList<>();
        delResponses.addAll(listBill);

        for (RentLifeItemDetailEntity lifeItemDetailEntity: lifeFeeItems ){
            String expenseitemId = lifeItemDetailEntity.getExpenseitemId();
            CostCodeEnum costCodeEnum = CostCodeEnum.getById(Integer.parseInt(expenseitemId));
            String code = costCodeEnum.getCode();
            for (ReceiptBillResponse billResponse : listBill){
                if (code.equals(billResponse.getCostCode())){
                    //应收金额
                    Integer receiptBillAmount = billResponse.getReceiptBillAmount();
                    Double paymentAmount = lifeItemDetailEntity.getPaymentAmount();
                    int payment = BigDecimal.valueOf(paymentAmount).multiply(BigDecimal.valueOf(100)).intValue();
                    LogUtil.info(LOGGER,"code相同costcode={},inputMoney={},fincMoney",billResponse.getCostCode(),payment,receiptBillAmount.intValue());
                    if (payment == receiptBillAmount.intValue()){
                        noUpResponses.add(billResponse);
                    }else{
                        upResponses.add(billResponse);
                        billResponse.setReceiptBillAmount(payment);
                    }
                    break;
                }
            }
        }

        LogUtil.info(LOGGER,"【saveDeliveryInfo】upResponses={}",JsonEntityTransform.Object2Json(upResponses));
        LogUtil.info(LOGGER,"【saveDeliveryInfo】noUpResponses={}",JsonEntityTransform.Object2Json(noUpResponses));

        delResponses.removeAll(upResponses);
        delResponses.removeAll(noUpResponses);

        LogUtil.info(LOGGER,"【saveDeliveryInfo】delResponses={}",JsonEntityTransform.Object2Json(delResponses));

        List<ModifyReceiptBillRequest> modifyRequests = new ArrayList<>();
        List<FinReceiBillDetailEntity> updateDetailLists = new ArrayList<>();
        if (!Check.NuNCollection(upResponses)){
            LogUtil.info(LOGGER,"【saveDeliveryInfo】app物业交割更新应收数据");
            for(ReceiptBillResponse billResponse : upResponses){
                //财务数据更新
                ModifyReceiptBillRequest modifyReceiptBillRequest = new ModifyReceiptBillRequest();
                modifyReceiptBillRequest.setBillNum(billResponse.getBillNum());
                modifyReceiptBillRequest.setDocumentAmount(billResponse.getReceiptBillAmount());
                modifyRequests.add(modifyReceiptBillRequest);
                //本地数据跟新
                FinReceiBillDetailEntity finReceiBillDetailEntity = new FinReceiBillDetailEntity();
                finReceiBillDetailEntity.setBillNum(billResponse.getBillNum());
                finReceiBillDetailEntity.setOughtAmount(BigDecimal.valueOf(billResponse.getReceiptBillAmount()).divide(BigDecimal.valueOf(100)).doubleValue());
                updateDetailLists.add(finReceiBillDetailEntity);
            }
        }
        if (!Check.NuNCollection(delResponses)){
            LogUtil.info(LOGGER,"【saveDeliveryInfo】app物业交割删除应收数据");
            for (ReceiptBillResponse billResponse : delResponses){
                //财务数据删除
                ModifyReceiptBillRequest modifyReceiptBillRequest = new ModifyReceiptBillRequest();
                modifyReceiptBillRequest.setBillNum(billResponse.getBillNum());
                modifyReceiptBillRequest.setDocumentAmount(billResponse.getReceiptBillAmount());
                modifyReceiptBillRequest.setIsDel(IsDelEnum.YES.getCode());
                modifyRequests.add(modifyReceiptBillRequest);
                //本地数据删除
                FinReceiBillDetailEntity finReceiBillDetailEntity = new FinReceiBillDetailEntity();
                finReceiBillDetailEntity.setBillNum(billResponse.getBillNum());
                finReceiBillDetailEntity.setIsDel(IsDelEnum.YES.getCode());
                updateDetailLists.add(finReceiBillDetailEntity);
            }
        }


        //修改应收账单部分
        if (!Check.NuNCollection(modifyRequests)){
            LogUtil.info(LOGGER,"【saveDeliveryInfo】修改财务单据billRequest={}", JsonEntityTransform.Object2Json(modifyRequests));
            for (ModifyReceiptBillRequest billRequest : modifyRequests){
                DataTransferObject modifyDto = JsonEntityTransform.json2DataTransferObject(callFinanceServiceProxy.modifyReceiptBill(JsonEntityTransform.Object2Json(billRequest)));
                if (modifyDto.getCode() == DataTransferObject.ERROR){
                    LogUtil.error(LOGGER,"【saveDeliveryInfo】app修改水电交割错误,dto={}",modifyDto.toJsonString());
                    return modifyDto;
                }
                //更新本地数据
                if (!Check.NuNCollection(updateDetailLists)){
                    for (FinReceiBillDetailEntity finReceiBillDetailEntity : updateDetailLists){
                        finReceiBillDetailServiceImpl.updateFinReceiBillDetailByBillNum(finReceiBillDetailEntity);
                    }
                }

            }
        }

        //新增部分
        List<RentLifeItemDetailEntity> hasBillLifeItems = new ArrayList<>();
        LogUtil.info(LOGGER,"【saveDeliveryInfo】列表原始数据listBill={}",JsonEntityTransform.Object2Json(listBill));
        for (ReceiptBillResponse receiptBillResponse : listBill){
            String costCode = receiptBillResponse.getCostCode();
            for (RentLifeItemDetailEntity itemDetailEntity : lifeFeeItems){
                String code = CostCodeEnum.getById(Integer.parseInt(itemDetailEntity.getExpenseitemId())).getCode();
                if (costCode.equals(code)){
                    hasBillLifeItems.add(itemDetailEntity);
                    break;
                }
            }
        }
        lifeFeeItems.removeAll(hasBillLifeItems);
        if (!Check.NuNCollection(lifeFeeItems)){
            LogUtil.info(LOGGER,"【saveDeliveryInfo】newLifeItem 新增部分数据list={}",JsonEntityTransform.Object2Json(lifeFeeItems));
            dto = createFinBill(rentContractEntity, lifeFeeItems, createId, 1);
        }

        return dto;
    }

    /**
     * 保存到应收数据
     * @author jixd
     * @created 2017年11月08日 09:33:38
     * @param
     * @param isModify
     * @return
     */
    private DataTransferObject saveFinReceiBill(FinReceiBillEntity finReceiBillEntity, List<FinReceiBillDetailEntity> receiBillDetails, RentContractEntity rentContractEntity, DataTransferObject financeDto, int isModify){
        LogUtil.info(LOGGER,"【saveDeliveryInfo】开始保存本地应收账单");
        DataTransferObject dto = new DataTransferObject();
        if (finReceiBillEntity.getOughtTotalAmount() == 0.0){
            return dto;
        }
        String projectJson = projectService.findProjectById(rentContractEntity.getProjectId());
        DataTransferObject projectDto = JsonEntityTransform.json2DataTransferObject(projectJson);
        if(projectDto.getCode()==DataTransferObject.ERROR){
            LogUtil.error(LOGGER,"【saveDeliveryInfo】查询为空项目信息为空");
            return projectDto;
        }
        ProjectEntity projectEntity = projectDto.parseData("projectEntity", new TypeReference<ProjectEntity>() {});
        //设置本地账单编号
        finReceiBillEntity.setBillNumber(financeCommonLogic.genReceiBillCode(projectEntity.getFcode()));
        int status = 1;
        String errorMsg = "";
        if (financeDto.getCode() == DataTransferObject.ERROR){
            status = 2;
            errorMsg = financeDto.getMsg();
        }
        for (FinReceiBillDetailEntity finReceiBillDetailEntity : receiBillDetails){
            finReceiBillDetailEntity.setStatus(status);
            finReceiBillDetailEntity.setFailMsg(errorMsg);
        }

        int count = 0;
        if (isModify == 1){
            List<FinReceiBillDetailEntity> finReceiBillDetailEntities = finReceiBillDetailServiceImpl.listFinReceiBillDetailByContractId(rentContractEntity.getContractId(), DocumentTypeEnum.LIFE_FEE.getCode(),null);
            if (Check.NuNCollection(finReceiBillDetailEntities)){
                count = finReceiBillServiceImpl.saveFinReceiBillAndDetail(finReceiBillEntity,receiBillDetails);
            }else{
                //更新增加
                FinReceiBillDetailEntity finReceiBillDetailEntity = finReceiBillDetailEntities.get(0);
                String billFid = finReceiBillDetailEntity.getBillFid();
                double sum = finReceiBillDetailEntities.stream().mapToDouble(FinReceiBillDetailEntity::getOughtAmount).sum();
                double addSum = receiBillDetails.stream().mapToDouble(FinReceiBillDetailEntity::getOughtAmount).sum();
                double total = sum + addSum;
                FinReceiBillEntity updateFinRecei = new FinReceiBillEntity();
                updateFinRecei.setFid(billFid);
                updateFinRecei.setOughtTotalAmount(total);
                receiBillDetails.forEach(r -> r.setBillFid(billFid));

                count += finReceiBillServiceImpl.updateFinReceiBillAndSaveDetails(updateFinRecei,receiBillDetails);
            }
        }else{
            count = finReceiBillServiceImpl.saveFinReceiBillAndDetail(finReceiBillEntity,receiBillDetails);
        }

        LogUtil.info(LOGGER,"【saveDeliveryInfo】保存应收数据count={}",count);
        dto.putValue("count",count);
        return dto;
    }


    /**
     * 查询房间物业交割信息 （打印需要的数据）
     * @author jixd
     * @created 2017年11月12日 10:52:35
     * @param
     * @return
     */
    @Override
    public String findDeliveryRoomInfo(String contractId) {
        LogUtil.info(LOGGER,"【findDeliveryRoomInfo】参数={}",contractId);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(contractId)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("合同ID为空");
            return dto.toJsonString();
        }

        DeliveryRoomInfoVo roomInfoVo = new DeliveryRoomInfoVo();

        RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId(contractId);
        if (Check.NuNObj(rentContractEntity)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("合同不存在");
            return dto.toJsonString();
        }
        roomInfoVo.setContractId(rentContractEntity.getContractId());
        roomInfoVo.setRoomId(rentContractEntity.getRoomId());
        roomInfoVo.setRoomNo(rentContractEntity.getHouseRoomNo());
        roomInfoVo.setProName(rentContractEntity.getProName());

        ContractRoomDto contractRoomDto = new ContractRoomDto();
        contractRoomDto.setContractId(rentContractEntity.getContractId());
        contractRoomDto.setRoomId(rentContractEntity.getRoomId());
        MeterDetailEntity meterDetailEntity = rentItemDeliveryServiceImpl.findMeterDetailById(contractRoomDto);
        double waterNum = 0.0;
        double electricNum = 0.0;
        if (!Check.NuNObj(meterDetailEntity)){
            waterNum = meterDetailEntity.getFwatermeternumber();
            electricNum = meterDetailEntity.getFelectricmeternumber();
        }
        roomInfoVo.setWaterNumber(waterNum);
        roomInfoVo.setElecNumber(electricNum);
        roomInfoVo.setPayWaterPrice(meterDetailEntity.getFpaywaterprice());
        roomInfoVo.setPayElecPrice(meterDetailEntity.getFpayelectricprice());

        List<RentItemDeliveryEntity> rentItemDeliveryEntityList = rentItemDeliveryServiceImpl.listValidItemByContractIdAndRoomId(contractRoomDto);
        List<DeliveryRoomInfoVo.RentItemVo> itemVos = new ArrayList<>();
        if (!Check.NuNCollection(rentItemDeliveryEntityList)){
            for (RentItemDeliveryEntity deliveryEntity : rentItemDeliveryEntityList){
                DeliveryRoomInfoVo.RentItemVo rentItemVo = new DeliveryRoomInfoVo.RentItemVo();
                rentItemVo.setItemName(deliveryEntity.getItemname());
                rentItemVo.setItemNum(deliveryEntity.getForiginalnum());
                String typeName = ItemTypeEnum.getByCode(Integer.parseInt(deliveryEntity.getItemType())).getName();
                rentItemVo.setItemType(typeName);
                rentItemVo.setPrice(deliveryEntity.getPrice());
                itemVos.add(rentItemVo);
            }
        }
        roomInfoVo.setItemList(itemVos);

        List<LifeItemVo> lifeItemVo = rentItemDeliveryServiceImpl.findLifeItemByContractIdAndRoomId(contractRoomDto);

        roomInfoVo.setLifeFees(lifeItemVo);
        dto.putValue("roomInfoVo",roomInfoVo);
        LogUtil.info(LOGGER,"【findDeliveryRoomInfo】结果={}",dto.toJsonString());
        return dto.toJsonString();
    }

    /**
     * 查询生活费用项
     * @return
     * @author cuigh6
     * @Date 2017年11月
     */
    public String listExpenseByItemCodes() {
        LogUtil.info(LOGGER, "【listExpenseByItemCodes】请求");
        DataTransferObject dto = new DataTransferObject();
        List<ExpenseItemEntity> expenseItemEntities = expenseItemServiceImpl.listExpenseByItemCodes(CostCodeEnum.getLifeFeeCodeWithSD());
        dto.putValue("list", expenseItemEntities);
        return dto.toJsonString();
    }
	@Override
	public String getItemDeliveryListForView(String param) {
		LogUtil.info(LOGGER, "【getItemDeliveryListForView】入参：{}", param);
		DataTransferObject dto = new DataTransferObject();
		try{
			Map<String,String> map = (Map<String,String>)JsonEntityTransform.json2Map(param);
			String roomId = map.get("roomId");
			String type = map.get("type");
//			UserAccount user = UserUtil.getCurrentUser();  // 操作用户
	        List<RentItemDeliveryEntity> list = new ArrayList<RentItemDeliveryEntity>();
	        if (!StringUtils.isBlank(roomId) && "0".equals(type)) {
	            // 根据房间id查询房间物品信息
	            List<ExtRoomItemsConfigVo> roomConfigList = null;
	            String itemStr = itemsService.getConfigsByRoomId(roomId);
	            LogUtil.info(LOGGER, "【getItemDeliveryListForView】查询房间物品信息返回：{}", itemStr);
	            DataTransferObject itemObj = JsonEntityTransform.json2DataTransferObject(itemStr);
	            if(itemObj.getCode() == DataTransferObject.SUCCESS){
	            	roomConfigList = itemObj.parseData("result", new TypeReference<List<ExtRoomItemsConfigVo>>() {
					});
	            }
	            		//roomConfigService.getConfigsByRoomId(roomId, user);
				/*List<RoomConfig> roomConfigList = signingService.getAllRoomConfigs(roomId);*/
	            if (roomConfigList != null && !roomConfigList.isEmpty()) {
	                for (ExtRoomItemsConfigVo roomConfig : roomConfigList) {
	                	RentItemDeliveryEntity itemDelivery = new RentItemDeliveryEntity();
	                    itemDelivery.setFid("");
	                    itemDelivery.setFtype(ResponsibilityEnum.COMPANY.getCode());
	                    itemDelivery.setItemname(roomConfig.getItemName());
	                    itemDelivery.setForiginalnum(roomConfig.getFnumber() == null ? 0 : roomConfig.getFnumber());
	                    itemDelivery.setFactualnum(roomConfig.getFnumber() == null ? 0 : roomConfig.getFnumber());
	                    itemDelivery.setFstate(String.valueOf(ItemUsedStateEnum.NORMAL.getStatus()));
	//TODO                    itemDelivery.setPrice(roomConfig.getItemId().getPrice() == null ? 0 : roomConfig.getItemId().getPrice());
	                    itemDelivery.setFpayfee(0.0);
	//TODO                    itemDelivery.setItemFrom(roomConfig.getItemFrom());
	                    itemDelivery.setIsbeditem(roomConfig.getFtype());    // 配置类别 0：房间，1：床位
	                    //itemDelivery.setItemType(roomConfig.getItemType() == null ? "" : roomConfig.getItemType().toString());
	                    itemDelivery.setItemid(roomConfig.getItemid() == null ? "" : roomConfig.getItemid());
	                    list.add(itemDelivery);
	                }
	            }
	        } else if ("1".equals(type)) {
	            // 查询房间装修信息
//	            Map searchMap = new HashMap();
//	            searchMap.put("filter_and_isDel_EQ_I", SysConstant.IS_NOT_DEL);
//	            searchMap.put("filter_and_itemType_EQ_I", SysConstant.IS_DEL);
//	            List<Item> itemList = itemDao.findByFilterMap(Item.class, searchMap);
	            List<ItemListEntity> itemList = null;
	            String itemListStr = itemsService.listItems(String.valueOf(ItemTypeEnum.HOUSE.getCode()), null);
	            LogUtil.info(LOGGER, "【getItemDeliveryListForView】查询房间物品信息返回：{}", itemListStr);
	            DataTransferObject itemListObj = JsonEntityTransform.json2DataTransferObject(itemListStr);
	            if(itemListObj.getCode() == DataTransferObject.SUCCESS){
	            	itemList = itemListObj.parseData("list", new TypeReference<List<ItemListEntity>>() {
					});
	            }
	            if (itemList != null && !itemList.isEmpty()) {
	                for (ItemListEntity item : itemList) {
	                	RentItemDeliveryEntity itemDelivery = new RentItemDeliveryEntity();
	                    itemDelivery.setFid("");
	                    itemDelivery.setFtype(ResponsibilityEnum.CUSTOMER.getCode());
	                    itemDelivery.setItemname(item.getFname() == null ? "" : item.getFname());
	                    itemDelivery.setFneworold("0");
	                    itemDelivery.setFunitmeter(0.0);
	                    itemDelivery.setFstate(String.valueOf(ItemUsedStateEnum.NORMAL.getStatus()));
	                    itemDelivery.setFpayfee(0.0);
	                    itemDelivery.setItemType(item.getFtype());
	                    itemDelivery.setItemid(item.getFid());
	                    list.add(itemDelivery);
	                }
	            }
	        }
	        dto.putValue("list", list);
	        return dto.toJsonString();
		}catch(Exception e){
			LogUtil.error(LOGGER, "【getItemDeliveryListForView】异常：{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("真实姓名为空");
            return dto.toJsonString();
		}
	}
	/**
     * 根据itemIds获取物品
     * @author xiangbin
     * @created 2017年11月20日
     * @param
     * @return
     */
    @Override
    public String listItemDeliverysByItemIds(String itemIds) {
        LogUtil.info(LOGGER,"listItemDeliverysByItemIds参数={}",itemIds);
        if (Check.NuNStrStrict(itemIds)) {
            return DataTransferObjectBuilder.buildErrorJsonStr("参数为空");
        }
        List<String> itemIdList = Arrays.asList(itemIds.split(","));
        List<RentItemDeliveryEntity> list =  rentItemDeliveryServiceImpl.listItemDeliverysByItemIds(itemIdList);
        return DataTransferObjectBuilder.buildOkJsonStr(list);
    }
    
    /**
     * 保存或者更新物品
     * @author xiangbin
     * @created 2017年11月20日
     * @param
     * @return
     */
    @Override
    public String saveOrUpdateItemDelivery(String param) {
        LogUtil.info(LOGGER,"【saveOrUpdateItemDelivery】参数={}",param);
        DataTransferObject dto = new DataTransferObject();
        try{
        	RentItemDeliveryEntity rentItemDeliveryEntity = JsonEntityTransform.json2Entity(param, RentItemDeliveryEntity.class);
            if(Check.NuNObj(rentItemDeliveryEntity)){
            	dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("入参异常");
                return dto.toJsonString();
            }
            if(Check.NuNStr(rentItemDeliveryEntity.getFid())){
            	rentItemDeliveryServiceImpl.insert(rentItemDeliveryEntity);
            }else{
            	rentItemDeliveryServiceImpl.update(rentItemDeliveryEntity);
            }
            return dto.toJsonString();
        }catch(Exception e){
        	LogUtil.error(LOGGER,"【saveOrUpdateItemDelivery】异常={}",e);
        	dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常");
            return dto.toJsonString();
        }
    }
    /**
     * 根据多个条件查询物品
     * @author xiangbin
     * @created 2017年11月20日
     * @param
     * @return
     */
    @Override
    public String selectRentItemDeliveryEntityByparam(String param) {
        LogUtil.info(LOGGER,"【selectRentItemDeliveryEntityByparam】参数={}",param);
        DataTransferObject dto = new DataTransferObject();
        try{
        	Map<String,String> map = (Map<String,String>)JsonEntityTransform.json2Map(param);
        	String contractId = map.get("contractId");
        	String itemId = map.get("itemId");
        	String surrenderId = map.get("surrenderId");
        	String isNewFlag = map.get("isNewFlag");
        	RentItemDeliveryEntity rentItemDeliveryEntity = rentItemDeliveryServiceImpl.selectRentItemDeliveryEntityByparam(contractId, itemId, surrenderId, isNewFlag);
        	LogUtil.info(LOGGER,"【selectRentItemDeliveryEntityByparam】查询物品信息返回：{}",JsonEntityTransform.Object2Json(rentItemDeliveryEntity));
        	dto.putValue("rentItemDeliveryEntity", rentItemDeliveryEntity);
            return dto.toJsonString();
        }catch(Exception e){
        	LogUtil.error(LOGGER,"【selectRentItemDeliveryEntityByparam】异常={}",e);
        	dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常");
            return dto.toJsonString();
        }
    }

    @Override
    public String updateMeterByContractId(String param) {
        LogUtil.info(LOGGER,"【updateMeterByContractId】参数={}",param);
        try{
            MeterDetailEntity meterDetailEntity = JsonEntityTransform.json2Object(param, MeterDetailEntity.class);
            if (Check.NuNObj(meterDetailEntity)){
                DataTransferObjectBuilder.buildErrorJsonStr("参数为空");
            }
            if (Check.NuNStr(meterDetailEntity.getFcontractid())){
                DataTransferObjectBuilder.buildErrorJsonStr("合同Id为空");
            }
            int i = rentItemDeliveryServiceImpl.updateMeterInfoByContractId(meterDetailEntity);
            return DataTransferObjectBuilder.buildOkJsonStr(i);
        }catch(Exception e){
            LogUtil.error(LOGGER,"【updateMeterByContractId】异常={}",e);
            return DataTransferObjectBuilder.buildErrorJsonStr("服务错误");
        }
    }

    @Override
    public String copyMeterInfo(String contractId) {
        LogUtil.info(LOGGER,"复制物业交割入参contractId={}",contractId);
        try {
            int i = rentItemDeliveryServiceImpl.copyPreContractDeliveryInfo(contractId);
            if(i > 0){
                return DataTransferObjectBuilder.buildOkJsonStr(i);
            }else{
                return DataTransferObjectBuilder.buildErrorJsonStr("更新错误");
            }
        }catch (Exception e){
            LogUtil.error(LOGGER,"copy物业交割数据错误error={}",e);
            return DataTransferObjectBuilder.buildErrorJsonStr("服务错误");
        }
    }

    @Override
    public String readSmartDeviceByRoomInfo(String paramJson) {
        String logPre = "【readSmartDeviceByRoomInfo】智能设备抄表并返回当前状态信息-";
        String payTypeMsg = "该房间使用了智能水电表，水只支持后付费，电只支持预付费，请仔细检查并去水电表管理处进行修改。";
        LogUtil.info(LOGGER, logPre+"入参:paramJson:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        WaterElectricityInfoVo waterElectricityInfoVo = new WaterElectricityInfoVo();
        try {
            WaterElectricityInfoDto waterElectricityInfoDto = JsonEntityTransform.json2Entity(paramJson,WaterElectricityInfoDto.class);
            if(Check.NuNObj(waterElectricityInfoDto)||
                    Check.NuNStrStrict(waterElectricityInfoDto.getProjectId())||
                    Check.NuNStrStrict(waterElectricityInfoDto.getRoomId())||
                    Check.NuNStrStrict(waterElectricityInfoDto.getContractId())||
                    Check.NuNObj(waterElectricityInfoDto.getWaterwattReadType())
                    ){
                LogUtil.error(LOGGER, logPre+"部分参数为空,paramJson:{}", paramJson);
                return errDto(dto,"参数不能为空");
            }

            //1.先查询当前房间扩展信息
            String roomExtJson = roomService.getRoomInfoExtByRoomId(waterElectricityInfoDto.getRoomId());
            DataTransferObject roomExtDto = JsonEntityTransform.json2DataTransferObject(roomExtJson);
            RoomInfoExtEntity roomExt = SOAResParseUtil.getValueFromDataByKey(roomExtJson,"roomExt",RoomInfoExtEntity.class);
            if(DataTransferObject.ERROR==roomExtDto.getCode()||Check.NuNObj(roomExt)){
                LogUtil.error(LOGGER, logPre+"根据roomId查询房间扩展信息失败,roomId:{}",waterElectricityInfoDto.getRoomId());
                return roomExtDto.toJsonString();
            }
            waterElectricityInfoVo.setRoomInfoExtEntity(roomExt);

            //2.如果该房间没有绑定智能水电表
            if(roomExt.getIsBindAmmeter()==0 && roomExt.getIsBindWatermeter()==0){
                LogUtil.info(LOGGER, logPre+"该房间没有绑定智能设备,roomId:{}",waterElectricityInfoDto.getRoomId());
                dto.putValue("waterElectricityInfoVo",waterElectricityInfoVo);
                return dto.toJsonString();
            }
            //电表
            if(roomExt.getIsBindAmmeter()==1){
                //电表抄表
                WattHistoryElectricMeterDto elctrictP = new WattHistoryElectricMeterDto();
                elctrictP.setHireContractCode(waterElectricityInfoDto.getProjectId());
                elctrictP.setPositionRank1(waterElectricityInfoDto.getProjectId());
                elctrictP.setPositionRank2(waterElectricityInfoDto.getRoomId());

                DataTransferObject elctrictDto = JsonEntityTransform.json2DataTransferObject(smartPlatformService.readNewestElectricMeter(elctrictP.toJsonStr()));
                if(DataTransferObject.ERROR == elctrictDto.getCode()){
                    LogUtil.error(LOGGER, logPre + "智能平台电表抄表异常！p={},dto={}", elctrictP, elctrictDto);
                    return errDto(dto,"智能设备电表抄表失败");
                }
            }
            //水表
            if(roomExt.getIsBindWatermeter()==1){
                //获取智能水表详情
                WaterMeterStateDto waterMeterStateDto = new WaterMeterStateDto();
                waterMeterStateDto.setHireContractCode(waterElectricityInfoDto.getProjectId());
                waterMeterStateDto.setPositionRank1(waterElectricityInfoDto.getProjectId());
                waterMeterStateDto.setPositionRank2(waterElectricityInfoDto.getRoomId());
                String waterMeterStateJson = smartPlatformService.waterMeterState(JsonEntityTransform.Object2Json(waterMeterStateDto));
                DataTransferObject WaterDto = JsonEntityTransform.json2DataTransferObject(waterMeterStateJson);
                WaterMeterStateVo waterMeterStateVo = WaterDto.parseData("data", new org.codehaus.jackson.type.TypeReference<WaterMeterStateVo>() {});
                if(waterMeterStateVo.getPayType()==0){
                    LogUtil.info(LOGGER, logPre+"该房间智能水表的付费方式为预付费,roomId:{}",waterElectricityInfoDto.getRoomId());
                    return errDto(dto,payTypeMsg);
                }
                waterElectricityInfoVo.setWaterMeterStateVo(waterMeterStateVo);//水表状态详情vo
                //水表抄表
                Double waterReading = waterClearingLogic.readWaterMeter(waterElectricityInfoDto.getProjectId(),waterElectricityInfoDto.getRoomId(),WaterwattReadTypeEnum.valueOf(waterElectricityInfoDto.getWaterwattReadType()),waterElectricityInfoDto.getContractId());
                waterElectricityInfoVo.setWaterReading(waterReading);//水表示数，如果为空就是抄表失败
            }
            dto.putValue("waterElectricityInfoVo",waterElectricityInfoVo);
        }catch (Exception e){
            LogUtil.error(LOGGER, logPre+"error:{},paramJson:{}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常");
        }
        return dto.toJsonString();
    }

    private String errDto(DataTransferObject dto,String errMsg){
        dto.setErrCode(DataTransferObject.ERROR);
        dto.setMsg(errMsg);
        return dto.toJsonString();
    }
}
