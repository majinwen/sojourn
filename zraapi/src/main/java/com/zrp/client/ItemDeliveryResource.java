package com.zrp.client;

import com.alibaba.fastjson.JSONObject;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.RegExpUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.entity.entityenum.ServiceLineEnum;
import com.ziroom.minsu.services.common.utils.DataFormat;
import com.ziroom.minsu.valenum.zrpenum.ContractTradingEnum;
import com.ziroom.zrp.houses.entity.CostStandardEntity;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.service.houses.api.ProjectService;
import com.ziroom.zrp.service.houses.api.RoomService;
import com.ziroom.zrp.service.houses.valenum.ItemTypeEnum;
import com.ziroom.zrp.service.houses.valenum.MeterTypeEnum;
import com.ziroom.zrp.service.houses.valenum.RoomTypeEnum;
import com.ziroom.zrp.service.trading.api.BindPhoneService;
import com.ziroom.zrp.service.trading.api.CallFinanceService;
import com.ziroom.zrp.service.trading.api.ItemDeliveryService;
import com.ziroom.zrp.service.trading.api.RentContractService;
import com.ziroom.zrp.service.trading.dto.ContractRoomDto;
import com.ziroom.zrp.service.trading.dto.SharerDto;
import com.ziroom.zrp.service.trading.dto.finance.ReceiptBillRequest;
import com.ziroom.zrp.service.trading.dto.finance.ReceiptBillResponse;
import com.ziroom.zrp.service.trading.valenum.CertTypeEnum;
import com.ziroom.zrp.service.trading.valenum.ContractStatusEnum;
import com.ziroom.zrp.service.trading.valenum.base.IsPayEnum;
import com.ziroom.zrp.service.trading.valenum.finance.CostCodeEnum;
import com.ziroom.zrp.service.trading.valenum.finance.DocumentTypeEnum;
import com.ziroom.zrp.service.trading.valenum.finance.VerificateStatusEnum;
import com.ziroom.zrp.trading.entity.*;
import com.zra.common.constant.BillMsgConstant;
import com.zra.common.constant.ContractMsgConstant;
import com.zra.common.enums.ErrorEnum;
import com.zra.common.enums.ItemDeliveryMsgEnum;
import com.zra.common.enums.RentTypeEunm;
import com.zra.common.result.ResponseDto;
import com.zra.common.utils.Check;
import com.zra.common.utils.DateTool;
import com.zra.common.utils.DateUtilFormate;
import com.zra.common.vo.base.*;
import com.zra.common.vo.delivery.CatalogItemVo;
import com.zra.common.vo.delivery.FeeHydropowerVo;
import com.zra.common.vo.delivery.PayFieldVo;
import com.zra.common.vo.perseon.SharerItemPersonVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>物业交割相关逻辑</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月14日 11:58
 * @since 1.0
 */
@Component
@Path("/itemDelivery")
@Api(value = "itemDelivery",description = "物业交割")
public class ItemDeliveryResource {

    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ItemDeliveryResource.class);

    @Resource(name = "trading.rentContractService")
    private RentContractService rentContractService;

    @Resource(name = "basedata.cityTemplateService")
    private CityTemplateService cityTemplateService;

    @Resource(name = "trading.itemDeliveryService")
    private ItemDeliveryService itemDeliveryService;

    @Resource(name = "trading.callFinanceService")
    private CallFinanceService callFinanceService;

    @Resource(name = "houses.projectService")
    private ProjectService projectService;

    @Resource(name = "houses.roomService")
    private RoomService roomService;

    @Resource(name = "trading.bindPhoneService")
    private BindPhoneService bindPhoneService;

    @Value("#{'${PIC_PREFIX_URL}'.trim()}")
    private String PIC_PREFIX_URL;





    /**
     * 物业交割主面板
     * @author jixd
     * @created 2017年09月14日 12:01:23
     * @param
     * @return
     */
    @POST
    @Path("/panle/v1")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "panle",response = ResponseDto.class)
    public ResponseDto panle(@FormParam("contractId") String contractId){
        LogUtil.info(LOGGER,"【panle】参数contractId={}",contractId);
        try {
            if (Check.NuNStr(contractId)){
                return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
            }
            DataTransferObject contractDto = JsonEntityTransform.json2DataTransferObject(rentContractService.findContractBaseByContractId(contractId));
            if (contractDto.getCode() == DataTransferObject.ERROR){
                return ResponseDto.responseDtoFail(contractDto.getMsg());
            }
            RentContractEntity rentContractEntity = contractDto.parseData("rentContractEntity", new TypeReference<RentContractEntity>() {});
            if (!ContractStatusEnum.YQY.getStatus().equals(rentContractEntity.getConStatusCode())){
                return ResponseDto.responseDtoFail("合同状态错误");
            }
            DataTransferObject timeDto = JsonEntityTransform.json2DataTransferObject(cityTemplateService.getTextValueForCommon(ServiceLineEnum.ZRP.getCode(),ContractTradingEnum.ContractTradingEnum007.getValue()));
            if (timeDto.getCode() == DataTransferObject.ERROR){
                return ResponseDto.responseDtoFail(timeDto.getMsg());
            }
            //首次支付时间
            Date firstPayTime = rentContractEntity.getFirstPayTime();
            if (Check.NuNObj(firstPayTime)){
                LogUtil.error(LOGGER,"【panle】支付时间为空");
                return ResponseDto.responseDtoFail("支付时间为空");
            }
            String time = timeDto.parseData("textValue", new TypeReference<String>() {});
            String tillTimeStr = DateUtilFormate.formatDateToString(DateTool.getDatePlusHours(firstPayTime, Integer.parseInt(time)), DateUtilFormate.DATEFORMAT_6);

            String msg = String.format(ContractMsgConstant.DELIVERY_TIME_MSG, tillTimeStr);

            DataTransferObject roomDto = JsonEntityTransform.json2DataTransferObject(roomService.getRoomByFid(rentContractEntity.getRoomId()));
            if (roomDto.getCode() == DataTransferObject.ERROR){
                LogUtil.error(LOGGER,"【panle】查询房间信息错误={}",roomDto.toJsonString());
                return ResponseDto.responseDtoFail(roomDto.getMsg());
            }
            RoomInfoEntity roomInfo = roomDto.parseData("roomInfo", new TypeReference<RoomInfoEntity>() {});

            //是否支付完成
            ReceiptBillRequest receiptBillRequest = new ReceiptBillRequest();
            receiptBillRequest.setOutContractCode(rentContractEntity.getConRentCode());
            receiptBillRequest.setDocumentType(DocumentTypeEnum.LIFE_FEE.getCode());
            DataTransferObject billDto = JsonEntityTransform.json2DataTransferObject(callFinanceService.getReceivableBillInfo(JSONObject.toJSONString(receiptBillRequest)));
            LogUtil.info(LOGGER,"【panle】账单查询结果result={}",billDto.toJsonString());
            if (billDto.getCode() == DataTransferObject.ERROR){
                LogUtil.info(LOGGER,"【panle】查询账单异常,result={}",billDto.toJsonString());
                return ResponseDto.responseDtoFail(billDto.getMsg());
            }

            int isPay = (int)billDto.getData().get("isPay");
            //部分付款按照未付款处理
            isPay = (isPay == 2 ? 0:isPay);
            List<BaseItemDescVo> panList = new ArrayList<>();
            for (ItemDeliveryMsgEnum msgEnum : ItemDeliveryMsgEnum.values()){
                if (msgEnum.getCode() == ItemDeliveryMsgEnum.SHFW.getCode()){
                    BaseItemDescColorVo baseItemDescColorVo = new BaseItemDescColorVo();
                    baseItemDescColorVo.setName(msgEnum.getName());
                    baseItemDescColorVo.setCode(msgEnum.getCode());
                    baseItemDescColorVo.setDesc(msgEnum.getDesc(isPay));
                    baseItemDescColorVo.setColor(msgEnum.getColor(isPay));
                    panList.add(baseItemDescColorVo);
                    continue;
                }
                if (msgEnum.getCode() == ItemDeliveryMsgEnum.HZXX.getCode() && roomInfo.getFtype() == RoomTypeEnum.BED.getCode()){
                    continue;
                }
                BaseItemDescVo baseItemVo = new BaseItemDescVo();
                baseItemVo.setName(msgEnum.getName());
                baseItemVo.setCode(msgEnum.getCode());
                baseItemVo.setDesc(msgEnum.getDesc());
                panList.add(baseItemVo);
            }

            //是否有合住人
            int hasSharer = 1;
            if (roomInfo.getFtype() == RoomTypeEnum.ROOM.getCode()){
                DataTransferObject sharListDto = JsonEntityTransform.json2DataTransferObject(itemDeliveryService.listSharerByContractId(contractId));
                if (sharListDto.getCode() == DataTransferObject.ERROR){
                    LogUtil.error(LOGGER,"查询合住人错误dto={}",sharListDto.toJsonString());
                    return ResponseDto.responseDtoFail(sharListDto.getMsg());
                }
                List<SharerEntity> sharerList = sharListDto.parseData("list", new TypeReference<List<SharerEntity>>() {});
                if (Check.NuNCollection(sharerList)){
                    hasSharer = 0;
                }
            }else{
                hasSharer = 0;
            }
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("list",panList);
            resultMap.put("tipMsg",msg);
            resultMap.put("isPay",isPay);
            resultMap.put("hasSharer",hasSharer);
            LogUtil.info(LOGGER,"【panle】result={}",JsonEntityTransform.Object2Json(resultMap));
            return ResponseDto.responseOK(resultMap);
        }catch (Exception e){
            LogUtil.info(LOGGER,"【panle】错误e={}",e);
            return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
        }
    }

    /**
     * 合住人列表
     * @author jixd
     * @created 2017年09月21日 10:23:42
     * @param
     * @return
     */
    @POST
    @Path("/sharerList/v1")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "/sharerList",response = ResponseDto.class)
    public ResponseDto sharerList(@FormParam("contractId") String contractId) {
        LogUtil.info(LOGGER, "【sharerList】参数contractId={}", contractId);
        if (Check.NuNStr(contractId)) {
            return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
        }
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(itemDeliveryService.listSharerByContractId(contractId));
        if (resultDto.getCode() == DataTransferObject.ERROR) {
            return ResponseDto.responseDtoFail(resultDto.getMsg());
        }
        try {
            List<SharerEntity> list = resultDto.parseData("list", new TypeReference<List<SharerEntity>>() {
            });
            List<SharerItemPersonVo> sharerList = new ArrayList<>();
            for (SharerEntity sharerEntity : list) {
                SharerItemPersonVo sharerItemPersonVo = new SharerItemPersonVo();
                sharerItemPersonVo.setFid(sharerEntity.getFid());
                sharerItemPersonVo.setName(sharerEntity.getFname());
                BaseItemValueVo cardInfo = new BaseItemValueVo();
                String fcerttype = sharerEntity.getFcerttype();
                if (!Check.NuNStr(fcerttype)) {
                    cardInfo.setName(CertTypeEnum.getByCode(Integer.parseInt(fcerttype)).getName());
                }
                cardInfo.setValue(sharerEntity.getFcertnum());
                cardInfo.setCode(Integer.parseInt(fcerttype));
                sharerItemPersonVo.setCertInfo(cardInfo);
                sharerItemPersonVo.setPhone(sharerEntity.getFmobile());
                sharerList.add(sharerItemPersonVo);
            }
            List<BaseItemVo> certTypeList = new ArrayList<>();
            for (CertTypeEnum certTypeEnum : CertTypeEnum.getSelectType()) {
                BaseItemVo baseItemVo = new BaseItemVo();
                baseItemVo.setCode(certTypeEnum.getCode());
                baseItemVo.setName(certTypeEnum.getName());
                certTypeList.add(baseItemVo);
            }
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("sharerList", sharerList);
            paramMap.put("certTypeList", certTypeList);
            return ResponseDto.responseOK(paramMap);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【sharerList】异常e={}", e);
            return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
        }
    }



    /**
     * 保存或者更新合住人信息
     * @author jixd
     * @created 2017年09月21日 11:32:38
     * @param
     * @return
     */
    @POST
    @Path("/saveOrUpdateSharer/v1")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "/saveOrUpdateSharer",response = ResponseDto.class)
    public ResponseDto saveOrUpdateSharer(@FormParam("fid") String fid,
                                          @FormParam("contractId") String contractId,
                                          @FormParam("name") String name,
                                          @FormParam("certType") String certType,
                                          @FormParam("certNo") String certNo,
                                          @FormParam("phone") String phone){
        SharerDto sharerDto = new SharerDto();
        sharerDto.setFid(fid);
        sharerDto.setContractId(contractId);
        sharerDto.setName(name);
        sharerDto.setCertType(certType);
        sharerDto.setCertNo(certNo);
        sharerDto.setPhone(phone);
        LogUtil.info(LOGGER,"【saveOrUpdateSharer】入参param={}",JsonEntityTransform.Object2Json(sharerDto));
        try{
            if (Check.NuNStr(certNo)){
                return ResponseDto.responseDtoFail("证件类型为空");
            }
            CertTypeEnum certTypeEnum = CertTypeEnum.getByCode(Integer.parseInt(certType));
            if (Check.NuNObj(certTypeEnum)){
                return ResponseDto.responseDtoFail("证件类型不支持");
            }
            if (Integer.parseInt(certType) == CertTypeEnum.CERTCARD.getCode()){
                if (!RegExpUtil.idIdentifyCardNum(certNo)){
                    return ResponseDto.responseDtoFail("身份证格式错误");
                }
            }else{
                if (!Check.NuNStr(certNo) && certNo.length() > 40){
                    return ResponseDto.responseDtoFail(certTypeEnum.getName()+"格式错误");
                }
            }

            String resultJson = itemDeliveryService.saveOrUpdateSharer(JsonEntityTransform.Object2Json(sharerDto));
            LogUtil.info(LOGGER,"返回结果={}",resultJson);
            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
            return ResponseDto.responseDtoForData(resultDto);
        }catch (Exception e){
            LogUtil.error(LOGGER, "【sharerList】异常e={}", e);
            return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
        }

    }

    /**
     * 删除合住人
     * @author jixd
     * @created 2017年09月21日 12:03:10
     * @param
     * @return
     */
    @POST
    @Path("/deleteSharer/v1")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "/deleteSharer",response = ResponseDto.class)
    public ResponseDto deleteSharer(@FormParam("contractId") String contractId,@FormParam("fid") String fid){
        LogUtil.info(LOGGER,"参数contractId={},fid={}",contractId,fid);
        if (Check.NuNStr(contractId) || Check.NuNStr(fid)){
            return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
        }
        try{
           return ResponseDto.responseDtoForData(JsonEntityTransform.json2DataTransferObject(itemDeliveryService.deleteSharerByFid(fid)));
        }catch (Exception e){
            LogUtil.error(LOGGER, "【deleteSharer】异常e={}", e);
            return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
        }
    }

    /**
     * 配置物品列表接口
     * @author jixd
     * @created 2017年09月21日 16:20:20
     * @param
     * @return
     */
    @POST
    @Path("/catalogItems/v1")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "/catalogItems",response = ResponseDto.class)
    public ResponseDto catalogItems(@FormParam("contractId") String contractId){
        LogUtil.info(LOGGER,"【catalogItems】contractId={}",contractId);
        if (Check.NuNStr(contractId)){
            return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            DataTransferObject contractDto = JsonEntityTransform.json2DataTransferObject(rentContractService.findContractBaseByContractId(contractId));
            if (contractDto.getCode() == DataTransferObject.ERROR){
                return ResponseDto.responseDtoFail(contractDto.getMsg());
            }
            RentContractEntity rentContractEntity = contractDto.parseData("rentContractEntity", new TypeReference<RentContractEntity>() {});
            ContractRoomDto contractRoomDto = new ContractRoomDto();
            contractRoomDto.setContractId(contractId);
            contractRoomDto.setRoomId(rentContractEntity.getRoomId());
            DataTransferObject itemDto = JsonEntityTransform.json2DataTransferObject(itemDeliveryService.listValidItemByContractIdAndRoomId(JsonEntityTransform.Object2Json(contractRoomDto)));
            if (itemDto.getCode() == DataTransferObject.ERROR){
                return ResponseDto.responseDtoFail(itemDto.getMsg());
            }
            List<RentItemDeliveryEntity> list = itemDto.parseData("list", new TypeReference<List<RentItemDeliveryEntity>>() {});
            if (Check.NuNCollection(list)){
                LogUtil.info(LOGGER,"【catalogItems】没有物品信息");
                return ResponseDto.responseDtoFail("没有物品信息");
            }

            //分组统计
            Map<String, List<RentItemDeliveryEntity>> mapItem = list.stream().collect(Collectors.groupingBy(RentItemDeliveryEntity::getItemType));

            List<CatalogItemVo> resultList = new ArrayList<>();
            for (Map.Entry<String,List<RentItemDeliveryEntity>> entry : mapItem.entrySet()){
                CatalogItemVo catalogItemVo = new CatalogItemVo();
                ItemTypeEnum itemTypeEnum = ItemTypeEnum.getByCode(Integer.parseInt(entry.getKey()));
                catalogItemVo.setName(itemTypeEnum.getName());
                List<RentItemDeliveryEntity> rentList = entry.getValue();
                for (RentItemDeliveryEntity itemDeliveryEntity : rentList){
                    CatalogItemVo.ItemInfo itemInfo = catalogItemVo.new ItemInfo();
                    itemInfo.setName(itemDeliveryEntity.getItemname());
                    itemInfo.setPrice(String.format(ContractMsgConstant.DELIVERY_ITEM_MONEY_MSG,itemDeliveryEntity.getPrice()));
                    itemInfo.setNum(itemDeliveryEntity.getFactualnum());
                    catalogItemVo.getItems().add(itemInfo);
                }
                resultList.add(catalogItemVo);
            }
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("catalogItems",resultList);

            LogUtil.info(LOGGER,"【catalogItems】result={}",JsonEntityTransform.Object2Json(paramMap));
            return ResponseDto.responseOK(paramMap);

        }catch (Exception e){
            LogUtil.error(LOGGER, "【catalogItems】异常e={}", e);
            return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
        }
    }

    /**
     * 生活费用项
     * @author jixd
     * @created 2017年09月25日 16:34:42
     * @param
     * @return
     */
    @POST
    @Path("/lifeFeeItems/v1")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "/lifeFeeItems",response = ResponseDto.class)
    public ResponseDto lifeFeeItems(@FormParam("contractId") String contractId){
        LogUtil.info(LOGGER,"【lifeFeeItems】contractId={}",contractId);
        if (Check.NuNStr(contractId)){
            return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            DataTransferObject contractDto = JsonEntityTransform.json2DataTransferObject(rentContractService.findContractBaseByContractId(contractId));
            if (contractDto.getCode() == DataTransferObject.ERROR){
                LogUtil.error(LOGGER,"【lifeFeeItems】查询合同错误dto={}",contractDto.toJsonString());
                return ResponseDto.responseDtoFail(contractDto.getMsg());
            }
            RentContractEntity rentContractEntity = contractDto.parseData("rentContractEntity", new TypeReference<RentContractEntity>() {});
            String projectId = rentContractEntity.getProjectId();

            DataTransferObject constDto = JsonEntityTransform.json2DataTransferObject(projectService.findCostStandardByProjectId(projectId));
            if (constDto.getCode() == DataTransferObject.ERROR){
                LogUtil.error(LOGGER,"【lifeFeeItems】查询基础基础物品价格错误dto={}",constDto.toJsonString());
                return ResponseDto.responseDtoFail(constDto.getMsg());
            }

            LogUtil.info(LOGGER,"【lifeFeeItems】查询基础基础物品价格={}",constDto.toJsonString());

            FeeHydropowerVo waterVo = new FeeHydropowerVo();
            waterVo.setName(MeterTypeEnum.WATER.getMachineName());
            FeeHydropowerVo electricVo = new FeeHydropowerVo();
            electricVo.setName(MeterTypeEnum.ELECTRICITY.getMachineName());

            //填充单价数据
            List<CostStandardEntity> costList = constDto.parseData("list", new TypeReference<List<CostStandardEntity>>() {});
            List<PayFieldVo> feeList = new ArrayList<>();
            //设置单价
            for (CostStandardEntity costStandardEntity : costList){
                if (costStandardEntity.getFmetertype().equals(String.valueOf(MeterTypeEnum.WATER.getCode()))){
                    String price = String.format(BillMsgConstant.RMB_CHINESE,costStandardEntity.getFprice());
                    waterVo.setUnit(String.format(ContractMsgConstant.DELIVERY_LIFEFEE_UNIT_MSG,price,MeterTypeEnum.WATER.getUnit()));
                }
                if (costStandardEntity.getFmetertype().equals(String.valueOf(MeterTypeEnum.ELECTRICITY.getCode()))){
                    String price = String.format(BillMsgConstant.RMB_CHINESE,costStandardEntity.getFprice());
                    electricVo.setUnit(String.format(ContractMsgConstant.DELIVERY_LIFEFEE_UNIT_MSG,price,MeterTypeEnum.ELECTRICITY.getUnit()));
                }
            }

            feeList.add(0,waterVo);
            feeList.add(1,electricVo);

            //填充底表示数
            ContractRoomDto contractRoomDto = new ContractRoomDto();
            contractRoomDto.setContractId(contractId);
            contractRoomDto.setRoomId(rentContractEntity.getRoomId());
            DataTransferObject meterDetailDto = JsonEntityTransform.json2DataTransferObject(itemDeliveryService.findMeterDetailById(JsonEntityTransform.Object2Json(contractRoomDto)));
            if (meterDetailDto.getCode() == DataTransferObject.ERROR){
                LogUtil.error(LOGGER,"【lifeFeeItems】底表示数获取错误dto={}",meterDetailDto.toJsonString());
                return ResponseDto.responseDtoFail(meterDetailDto.getMsg());
            }
            LogUtil.info(LOGGER,"【lifeFeeItems】底表示数={}",meterDetailDto.toJsonString());
            MeterDetailEntity meterDetail = meterDetailDto.parseData("meterDetail", new TypeReference<MeterDetailEntity>() {});
            if (Check.NuNObj(meterDetail)){
                LogUtil.info(LOGGER,"【lifeFeeItems】水电费未录入");
                return ResponseDto.responseDtoFail("水电费未录入");
            }
            waterVo.setNumber(String.format(ContractMsgConstant.DELIVERY_LIFEFEE_NUMBER_MSG,meterDetail.getFwatermeternumber()));
            waterVo.setPicUrl(PIC_PREFIX_URL + meterDetail.getFwatermeterpic());

            electricVo.setNumber(String.format(ContractMsgConstant.DELIVERY_LIFEFEE_NUMBER_MSG,meterDetail.getFelectricmeternumber()));
            electricVo.setPicUrl(PIC_PREFIX_URL +meterDetail.getFelectricmeterpic());


            //填充财务相关支付数据
            ReceiptBillRequest receiptBillRequest = new ReceiptBillRequest();
            receiptBillRequest.setOutContractCode(rentContractEntity.getConRentCode());
            receiptBillRequest.setDocumentType(DocumentTypeEnum.LIFE_FEE.getCode());
            DataTransferObject billDto = JsonEntityTransform.json2DataTransferObject(callFinanceService.getReceivableBillInfo(JSONObject.toJSONString(receiptBillRequest)));
            LogUtil.info(LOGGER,"【lifeFeeItems】财务生活费用查询结果={}",billDto.toJsonString());
            if (billDto.getCode() == DataTransferObject.ERROR){
                LogUtil.error(LOGGER,"【lifeFeeItems】查询账单异常,result={}",billDto.toJsonString());
                return ResponseDto.responseDtoFail(billDto.getMsg());
            }
            int payMoney = 0;
            List<ReceiptBillResponse> listBill = billDto.parseData("listStr", new TypeReference<List<ReceiptBillResponse>>() {});
            if (!Check.NuNCollection(listBill)){
                for (ReceiptBillResponse receiptBillResponse : listBill){
                    int isPay = VerificateStatusEnum.DONE.getCode() == receiptBillResponse.getVerificateStatus() ? IsPayEnum.YES.getCode():IsPayEnum.NO.getCode();
                    if (receiptBillResponse.getCostCode().equals(CostCodeEnum.ZRYSF.getCode())){
                        waterVo.setValue(String.format(BillMsgConstant.RMB_CHINESE,DataFormat.formatHundredPrice(receiptBillResponse.getReceiptBillAmount())));
                        payMoney += (receiptBillResponse.getReceiptBillAmount() - receiptBillResponse.getReceivedBillAmount());
                        waterVo.setIsPay(isPay);
                        continue;
                    }
                    if (receiptBillResponse.getCostCode().equals(CostCodeEnum.ZRYDF.getCode())){
                        electricVo.setValue(String.format(BillMsgConstant.RMB_CHINESE,DataFormat.formatHundredPrice(receiptBillResponse.getReceiptBillAmount())));
                        payMoney += (receiptBillResponse.getReceiptBillAmount() - receiptBillResponse.getReceivedBillAmount());
                        electricVo.setIsPay(isPay);
                        continue;
                    }
                    CostCodeEnum costCodeEnum = CostCodeEnum.getByCode(receiptBillResponse.getCostCode());
                    if (!Check.NuNObj(costCodeEnum)){
                        PayFieldVo payFieldVo = new PayFieldVo();
                        payFieldVo.setName(costCodeEnum.getName());
                        payFieldVo.setValue(String.format(BillMsgConstant.RMB_CHINESE,DataFormat.formatHundredPrice(receiptBillResponse.getReceiptBillAmount())));
                        payFieldVo.setIsPay(isPay);
                        feeList.add(payFieldVo);
                        payMoney += (receiptBillResponse.getReceiptBillAmount() - receiptBillResponse.getReceivedBillAmount());
                    }
                }
            }
            if (Check.NuNObj(waterVo.getValue())){
                waterVo.setValue("0.0");
            }
            if (Check.NuNObj(electricVo.getValue())){
                electricVo.setValue("0.0");
            }
            int isPay = (int)billDto.getData().get("isPay");
            isPay = (isPay == 2 ? 0:isPay);
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("feeList",feeList);
            resultMap.put("payMoney",payMoney);
            resultMap.put("isPay",isPay);
            LogUtil.info(LOGGER,"【lifeFeeItems】返回结果result={}",JsonEntityTransform.Object2Json(resultMap));
            return ResponseDto.responseOK(resultMap);
        }catch (Exception e){
            LogUtil.error(LOGGER, "【lifeFeeItems】异常e={}", e);
            return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
        }
    }


    /**
     * 证件列表
     * @author jixd
     * @created 2017年10月20日 09:37:34
     * @param
     * @return
     */
    @POST
    @Path("/certTypeList/v1")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "/certTypeList",response = ResponseDto.class)
    public ResponseDto certTypeList(@FormParam("contractId") String contractId){
        LogUtil.info(LOGGER,"【certTypeList】contractId={}",contractId);
        List<BaseItemVo> certTypeList = new ArrayList<>();
        for (CertTypeEnum certTypeEnum : CertTypeEnum.getSelectType()) {
            BaseItemVo baseItemVo = new BaseItemVo();
            baseItemVo.setCode(certTypeEnum.getCode());
            baseItemVo.setName(certTypeEnum.getName());
            certTypeList.add(baseItemVo);
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("certTypeList", certTypeList);
        return ResponseDto.responseOK(paramMap);
    }

    /**
     * 确认物业交割
     * @author jixd
     * @created 2017年09月26日 13:47:21
     * @param
     * @return
     */
    @POST
    @Path("/confirmDelivery/v1")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "/confirmDelivery",response = ResponseDto.class)
    public ResponseDto confirmDelivery(@FormParam("contractId") String contractId){
        LogUtil.info(LOGGER,"【confirmDelivery】contractId={}",contractId);
        if (Check.NuNStr(contractId)){
            return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
        }

        try {
            DataTransferObject contractDto = JsonEntityTransform.json2DataTransferObject(rentContractService.findContractBaseByContractId(contractId));
            if (contractDto.getCode() == DataTransferObject.ERROR){
                return ResponseDto.responseDtoFail(contractDto.getMsg());
            }
            RentContractEntity rentContractEntity = contractDto.parseData("rentContractEntity", new TypeReference<RentContractEntity>() {});

            //是否支付完成
            ReceiptBillRequest receiptBillRequest = new ReceiptBillRequest();
            receiptBillRequest.setOutContractCode(rentContractEntity.getConRentCode());
            receiptBillRequest.setDocumentType(DocumentTypeEnum.LIFE_FEE.getCode());
            DataTransferObject billDto = JsonEntityTransform.json2DataTransferObject(callFinanceService.getReceivableBillInfo(JSONObject.toJSONString(receiptBillRequest)));
            LogUtil.info(LOGGER,"【confirmDelivery】账单查询结果result={}",billDto.toJsonString());
            if (billDto.getCode() == DataTransferObject.ERROR){
                LogUtil.info(LOGGER,"【confirmDelivery】查询账单异常,result={}",billDto.toJsonString());
                return ResponseDto.responseDtoFail(billDto.getMsg());
            }

            int isPay = (int)billDto.getData().get("isPay");
            if (isPay == 0){
                return ResponseDto.responseDtoFail("您还有生活费用账单未支付完成");
            }

            String json = itemDeliveryService.confirmDelivery(contractId);
            LogUtil.info(LOGGER,"更新结果result={}",json);

            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(json);
            return ResponseDto.responseDtoForData(dto);
        }catch (Exception e){
            LogUtil.error(LOGGER, "【confirmDelivery】异常e={}", e);
            return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
        }
    }

    /**
     * 管家是否录入物业交割信息
     * @author jixd
     * @created 2017年11月05日 14:41:44
     * @param
     * @return
     */
    @POST
    @Path("/isEnterDelivery/v1")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "/isEnterDelivery",response = ResponseDto.class)
    public ResponseDto isEnterDelivery(@FormParam("contractId") String contractId){
        LogUtil.info(LOGGER,"【isEnterDelivery】contractId={}",contractId);
        if (Check.NuNStr(contractId)){
            return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            DataTransferObject contractDto = JsonEntityTransform.json2DataTransferObject(rentContractService.findContractBaseByContractId(contractId));
            if (contractDto.getCode() == DataTransferObject.ERROR) {
                return ResponseDto.responseDtoFail(contractDto.getMsg());
            }
            RentContractEntity rentContractEntity = contractDto.parseData("rentContractEntity", new TypeReference<RentContractEntity>() {});
            String zoCode = rentContractEntity.getFhandlezocode();
            ContractRoomDto contractRoomDto = new ContractRoomDto();
            contractRoomDto.setContractId(contractId);
            contractRoomDto.setRoomId(rentContractEntity.getRoomId());
            DataTransferObject meterDetailDto = JsonEntityTransform.json2DataTransferObject(itemDeliveryService.findMeterDetailById(JsonEntityTransform.Object2Json(contractRoomDto)));
            if (meterDetailDto.getCode() == DataTransferObject.ERROR){
                LogUtil.error(LOGGER,"【isEnterDelivery】底表示数获取错误dto={}",meterDetailDto.toJsonString());
                return ResponseDto.responseDtoFail(meterDetailDto.getMsg());
            }
            LogUtil.info(LOGGER,"【isEnterDelivery】底表示数={}",meterDetailDto.toJsonString());
            MeterDetailEntity meterDetail = meterDetailDto.parseData("meterDetail", new TypeReference<MeterDetailEntity>() {});

            int isEnter = 0;
            String telephoneNum = "";
            if (!Check.NuNObj(meterDetail)){
                isEnter = 1;
            }
            LogUtil.info(LOGGER,"【isEnterDelivery】管家编号={}",zoCode);
            if (isEnter == 0){
                String bindPhoneJson = bindPhoneService.findBindPhoneByEmployeeCode(rentContractEntity.getProjectId(),zoCode);
                LogUtil.info(LOGGER,"查询结果",bindPhoneJson);
                DataTransferObject bindPhoneDto = JsonEntityTransform.json2DataTransferObject(bindPhoneJson);
                if (bindPhoneDto.getCode() == DataTransferObject.ERROR){
                    LogUtil.error(LOGGER,"【isEnterDelivery】查询400电话错误={}",bindPhoneDto.toJsonString());
                    return ResponseDto.responseDtoFail(bindPhoneDto.getMsg());
                }
                telephoneNum = bindPhoneDto.parseData("data", new TypeReference<String>() {});
                if (Check.NuNStr(telephoneNum)){
                    return ResponseDto.responseDtoFail("未查询到管家联系电话");
                }
            }
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("isEnter",isEnter);
            resultMap.put("telephoneNum",telephoneNum);
            resultMap.put("msg",ContractMsgConstant.DELIVERY_CUSTOMER_TIP_CONTACT);
            ResponseDto responseDto = ResponseDto.responseOK(resultMap);
            LogUtil.info(LOGGER,"【isEnterDelivery】返回结果={}",JsonEntityTransform.Object2Json(responseDto));
            return responseDto;
        }catch (Exception e){
            LogUtil.error(LOGGER, "【isEnterDelivery】异常e={}", e);
            return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
        }
    }


}
