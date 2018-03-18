package com.ziroom.zrp.service.trading.proxy.commonlogic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.zrp.service.trading.dto.finance.*;
import com.ziroom.zrp.service.trading.entity.SyncContractVo;
import com.ziroom.zrp.service.trading.valenum.finance.ResponseCodeEnum;
import com.ziroom.zrp.service.trading.valenum.finance.VerificateStatusEnum;
import com.zra.common.exception.FinServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>财务调用基础类  防止互相调用 结构更清晰</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2018年01月05日 18:07
 * @since 1.0
 */
@Component("trading.financeBaseCall")
public class FinanceBaseCall {

    private static final Logger LOGGER = LoggerFactory.getLogger(FinanceBaseCall.class);


    @Value("#{'${finance_create_pay_voucher_url}'.trim()}")
    private String getCreatePayVoucherUrl;

    @Value("#{'${finance_contract_receiptList_url}'.trim()}")
    private String getContractReceiptListUrl;

    @Value("#{'${finace_get_pay_voucher_url}'.trim()}")
    private String getPayVoucherUrl;

    @Value("#{'${finance_sys_code}'.trim()}")
    private String sysCode;

    @Value("#{'${finance_create_bill_num_url}'.trim()}")
    private String createBillNumUrl;

    @Value("#{'${finance_create_bill_num_batch_url}'.trim()}")
    private String createBillNumBatchUrl;

    @Value("#{'${finance_update_contract_url}'.trim()}")
    private String updateContractUrl;

    @Value("#{'${finance_receiptBill_url}'.trim()}")
    private String finance_receiptBill_url;

    @Value("#{'${finance_sync_contract_url}'.trim()}")
    private String syncContractUrl;

    @Value("#{'${finance_check_contract_audit_url}'.trim()}")
    private String checkContractAuditUrl;

    @Value("#{'${finance_createReceiptBill_url}'.trim()}")
    private String createReceiptBillUrl;

    /**
     * 创建付款单
     * <p>
     *   wiki地址:http://wiki.ziroom.com/pages/viewpage.action?pageId=173178925
     * </p>
     *
     * @param  {"paySerialNum":"sf","outContractCode":"BJWL15901797","uid":"1002112","payVouchersDetail":[{"costCode":"sf","refundAmount":-200}]}]
     * @return json
     * @author cuigh6
     * @Date 2017年10月16日
     */
    public void createPayVouchers(List<PayVoucherReqDto> payList) throws FinServiceException{
        String param = JSONObject.toJSONString(payList);
        LogUtil.info(LOGGER,"【createPayVouchers】创建付款单 URL={},param={}",getCreatePayVoucherUrl,param);
        //调用财务接口
        String result = CloseableHttpUtil.sendPost(getCreatePayVoucherUrl, param);
        LogUtil.info(LOGGER,"【createPayVouchers】创建付款单返回结果:{}",result);
        JSONObject responseJson = JSONObject.parseObject(result);
        if (Check.NuNObj(responseJson)) {
            LogUtil.error(LOGGER, "【createPayVouchers】 调用财务创建付款单返回json为空");
            throw new FinServiceException("财务返回结果为空");
        }
        int code = responseJson.getInteger("code");
        if (Check.NuNObj(code) || code != ResponseCodeEnum.SUCCESS.getCode()) {
            LogUtil.error(LOGGER, "【createPayVouchers】 调用财务创建付款单，message:{}", responseJson.getString("message"));
            throw new FinServiceException(responseJson.getString("message"));
        }
    }


    /**
     * 查询收款单列表 通过合同号和期数
     * <p>
     *     wiki地址:http://wiki.ziroom.com/pages/viewpage.action?pageId=215777392
     *     请求地址:http://zfreceipt.t.ziroom.com/api/zryj/getReceiptListByContract
     * </p>
     * @author cuigh6
     * @Date 2017年10月10日
     * @param receiptBillListRequestDto { "periods":"1",期数 null为全部 "confirmStatus":"1", 审核状态：3 未提交 0 未审核，1 审核通过，2 审核未通过 null为全部 "receiptStatus":"0",收款状态:0已收款,1未收款 2打回 null为全部 "verificateStatus":"1",应收账单核销状态 0未核销,1已核销,2部分核销 null为全部 "contractCode":"BJZYCW81707040014"出房合同号 必传 }
     * @return
     */
    public  List<ReceiptBillListResponseDto> getReceiptBillListByContract(ReceiptBillListRequestDto receiptBillListRequestDto) throws FinServiceException{
        String param = JSONObject.toJSONString(receiptBillListRequestDto);
        LogUtil.info(LOGGER,"【getReceiptBillListByContract】查询收款单列表 URL={},param={}",getContractReceiptListUrl,param);
        //调用财务接口
        String result = CloseableHttpUtil.sendPost(getContractReceiptListUrl, param);
        LogUtil.info(LOGGER, "【getReceiptBillListByContract】调用财务查询合同收款单列表返回:{}", result);
        JSONObject responseJson = JSONObject.parseObject(result);
        if (Check.NuNObj(responseJson)) {
            LogUtil.error(LOGGER, "【getReceiptBillListByContract】 调用财务查询合同收款单列表返回json为空");
            throw new FinServiceException("财务返回结果为空");
        }
        int code = responseJson.getInteger("code");
        if (Check.NuNObj(code) || code != ResponseCodeEnum.SUCCESS.getCode()) {
            LogUtil.error(LOGGER, "【getReceiptBillListByContract】 调用财务查询合同收款单列表，message:{}", responseJson.getString("message"));
            throw new FinServiceException(responseJson.getString("message"));
        }
        List<ReceiptBillListResponseDto> receiptBillListResponseDtos = JSONArray.parseArray(responseJson.getString("data"), ReceiptBillListResponseDto.class);
        return receiptBillListResponseDtos;
    }

    /**
     * <p>（CRM、资产）查询付款单（根据合同号）</p>
     * <p>wiki地址：http://wiki.ziroom.com/pages/viewpage.action?pageId=191791167</p>
     * <p>
     *     {
     "collectContract":"BJW13902215W"  //String类型 收房合同 参数不能同时为空
     "outContract":"BJCW81509040703"  //String类型 出房合同
     "orderCode":"ZRBJ20170306_202805"  //String类型 订单号
     }
     * </p>
     * @author xiangb
     * @created 2017年11月7日
     * @param
     * @return
     */
    public List<PayVoucherResponse> getPayVouchers(String contractCode) throws FinServiceException{
        if(Check.NuNStr(contractCode)){
           throw new FinServiceException("参数为空");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("collectContract", null);
        param.put("outContract", contractCode);
        param.put("orderCode", null);
        LogUtil.info(LOGGER,"【getPayVouchers】查询付款单 URL={},param={}",getPayVoucherUrl,JsonEntityTransform.Object2Json(param));
        String resultJson = CloseableHttpUtil.sendPost(getPayVoucherUrl, param);
        LogUtil.info(LOGGER,"【getPayVouchers】查询付款单返回结果={}",resultJson);
        JSONObject responseJson = JSONObject.parseObject(resultJson);
        if (Check.NuNObj(responseJson)) {
            LogUtil.error(LOGGER,"【getPayVouchers】查询付款单失败,param={}", JsonEntityTransform.Object2Json(param));
            throw new FinServiceException("财务返回结果为空");
        }
        int code = responseJson.getInteger("code");
        if (Check.NuNObj(code) || code != ResponseCodeEnum.SUCCESS.getCode()) {
            LogUtil.error(LOGGER, "【getPayVouchers】 查询付款单失败，message:{}", responseJson.getString("message"));
            throw new FinServiceException(responseJson.getString("message"));
        }
        return JSONArray.parseArray(responseJson.getString("data"),PayVoucherResponse.class);
    }

    /**
     *
     *  更新财务合同接口
     *  入参：SyncContractVo
     *
     * <p>wiki 地址：http://wiki.ziroom.com/pages/viewpage.action?pageId=175505503</p>
     * <p>接口地址：http://zfdataline.ziroom.com/api/contract/updateContract</p>
     * @param syncContractVo
     * @return
     */
    public void updateContract(SyncContractVo syncContractVo)throws FinServiceException{
        if (Check.NuNObj(syncContractVo)) {
            throw new FinServiceException("参数为空");
        }
        LogUtil.info(LOGGER,"【updateContract】更新合同 URL={},param={}",updateContractUrl,JsonEntityTransform.Object2Json(syncContractVo));
        String result = CloseableHttpUtil.sendPost(updateContractUrl, JsonEntityTransform.Object2Json(syncContractVo));
        LogUtil.info(LOGGER, "【updateContract】更新合同财务返回结果=:{}", result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (Check.NuNObj(jsonObject)) {
            LogUtil.error(LOGGER, "【updateContract】 更新合同到财务返回json为空");
            throw new FinServiceException("财务返回结果为空");
        }
        Integer code = jsonObject.getInteger("code");
        String status = jsonObject.getString("status");
        if (Check.NuNObj(code) || code != 0 || Check.NuNStrStrict(status) || !status.equals("success")) {
            LogUtil.error(LOGGER, "【updateContract】 调用财务更新合同到财务失败，result:{},message:{}", result, jsonObject.getString("message"));
            throw new FinServiceException(jsonObject.getString("message"));
        }
    }

    /**
     *
     *  同步合同到财务接口
     *  入参：SyncContractVo
     *
     * <p>wiki 地址：http://wiki.ziroom.com/pages/viewpage.action?pageId=175505499</p>
     * <p>接口地址：http://zfdataline.ziroom.com/api/contract/addContract</p>
     * @param syncContractVo
     * @return
     */
    public void syncContract(SyncContractVo syncContractVo)throws FinServiceException{
        if (Check.NuNObj(syncContractVo)) {
            throw new FinServiceException("参数为空");
        }
        LogUtil.info(LOGGER, "【syncContract】 同步合同到财务 conRentCode:{},apiUrl={},paramjson:{}",syncContractVo.getRentContractCode(),syncContractUrl,JsonEntityTransform.Object2Json(syncContractVo));
        String result = CloseableHttpUtil.sendPost(syncContractUrl, JsonEntityTransform.Object2Json(syncContractVo));
        LOGGER.info("【syncContract】 同步财务合同 conRentCode:{}, resultJson:{}", syncContractVo.getRentContractCode(), result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (Check.NuNObj(jsonObject)) {
            LogUtil.error(LOGGER, "【syncContract】 同步合同到财务返回json为空");
            throw new FinServiceException("财务返回结果为空");
        }
        Integer code = jsonObject.getInteger("code");
        String status = jsonObject.getString("status");
        if (Check.NuNObj(code) || code != 0 || Check.NuNStrStrict(status) || !status.equals("success")) {
            LogUtil.error(LOGGER, "【syncContract】 调用同步合同到财务失败，resultJson:{},message:{}", result, jsonObject.getString("message"));
            throw new FinServiceException(jsonObject.getString("message"));
        }
    }

    /**
     *
     * 获取应收账单
     * <p>wiki地址 http://wiki.ziroom.com/pages/viewpage.action?pageId=187465806</p>
     * ReceiptBillRequest receiptBillRequest
     * @return
     */
    public Map<String,Object> getReceivableBillInfo(ReceiptBillRequest receiptBillRequest)throws FinServiceException{
        if (Check.NuNObj(receiptBillRequest)){
            throw new FinServiceException("参数为空");
        }
        String paramJson = JsonEntityTransform.Object2Json(receiptBillRequest);
        LogUtil.info(LOGGER,"【receiptBillRequest】查询应收账单信息 URL={},param={}",updateContractUrl,paramJson);
        String resultJson = CloseableHttpUtil.sendPost(finance_receiptBill_url, paramJson);
        if (Check.NuNStr(resultJson)){
            LogUtil.error(LOGGER,"【getReceiptBillInfo】获取应收账单超时,param={}",paramJson);
            throw new FinServiceException("财务返回应收账单错误");
        }
        LogUtil.info(LOGGER,"财务应收返回结果={}",resultJson);

        JSONObject jsonObj = JSONObject.parseObject(resultJson);
        int code = jsonObj.getIntValue("code");
        if (code != ResponseCodeEnum.SUCCESS.getCode()){
            throw new FinServiceException(jsonObj.getString("message"));
        }
        int isPay = 0; //是否支付 0未支付 1已支付或没有账单 2部分付款
        int isEmpty = 1;//账单是否为空  0为空 1不为空
        List<ReceiptBillResponse> listBill = JSONArray.parseArray(jsonObj.getString("data"), ReceiptBillResponse.class);
        if (Check.NuNCollection(listBill)){
            isPay = 1;
            isEmpty = 0;
        }else{
            //是否都已核销
            boolean isVer = listBill.stream().allMatch(b -> b.getVerificateStatus() == VerificateStatusEnum.DONE.getCode());
            if (isVer){
                isPay = 1;
            }else{
                boolean isPart = listBill.stream().anyMatch(b -> b.getVerificateStatus() == VerificateStatusEnum.PART.getCode());
                if (isPart){
                    isPay = 2;
                }
            }
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("listStr",listBill);
        resultMap.put("isPay",isPay);
        resultMap.put("isEmpty",isEmpty);
        return resultMap;
    }

    /**
      * @description: 财务:获取应收账单编号（单个）
      *               wiki:http://wiki.ziroom.com/pages/viewpage.action?pageId=15335437
      * @author: lusp
      * @date: 2018/1/9 下午 16:51
      * @params: billType
      * @return: String
      */
    public String callFinaCreateBillNum(String billType) throws FinServiceException{
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("sysCode", sysCode);
        mapParam.put("type", 1);
        mapParam.put("billType", billType);
        LogUtil.info(LOGGER,"【callFinaCreateBillNum】 调用财务获取应收账单编号,apiUrl:{},paramJson:{}",createBillNumUrl,JsonEntityTransform.Object2Json(mapParam));
        String result = CloseableHttpUtil.sendPost(createBillNumUrl, JsonEntityTransform.Object2Json(mapParam));
        LogUtil.info(LOGGER,"【callFinaCreateBillNum】 调用财务获取应收账单返回结果,resultJson:{}",result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (Check.NuNObj(jsonObject)) {
            LogUtil.error(LOGGER, "【callFinaCreateBillNum】 调用财务生成应收账单编号返回json为空");
            throw new FinServiceException("调用财务生成应收账单编号返回json为空");
        }
        Integer code = jsonObject.getInteger("code");
        if (Check.NuNObj(code) || code != ResponseCodeEnum.SUCCESS.getCode()) {
            LogUtil.error(LOGGER, "【callFinaCreateBillNum】 调用财务生成应收账单编号失败，返回结果result:{}", result);
            throw new FinServiceException("调用财务生成应收账单编号失败");
        }
        String billNum = jsonObject.getString("data");
        return billNum;
    }

    /**
     * @description: 财务:生成应收账单编号（批量）
     *               wiki:http://wiki.ziroom.com/pages/viewpage.action?pageId=316145803
     * @author: lusp
     * @date: 2018/1/9 下午 16:53
     * @params: billType,total
     * @return: JSONArray
     */
    public JSONArray callFinaCreateBillNumBatch(String billType, int total) throws FinServiceException{
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("total", total);
        mapParam.put("billType", billType);
        LogUtil.info(LOGGER,"【callFinaCreateBillNumBatch】 调用财务批量获取应收账单编号,apiUrl:{},paramJson:{}",createBillNumBatchUrl,JsonEntityTransform.Object2Json(mapParam));
        String result = CloseableHttpUtil.sendPost(createBillNumBatchUrl, JsonEntityTransform.Object2Json(mapParam));
        LogUtil.info(LOGGER,"【callFinaCreateBillNumBatch】 调用财务批量获取应收账单返回结果,resultJson:{}",result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (Check.NuNObj(jsonObject)) {
            LogUtil.error(LOGGER, "【callFinaCreateBillNumBatch】 调用财务批量生成应收账单编号返回json为空");
            throw new FinServiceException("调用财务生成应收账单编号返回json为空");
        }
        Integer code = jsonObject.getInteger("code");
        if (Check.NuNObj(code) || code != ResponseCodeEnum.SUCCESS.getCode()) {
            LogUtil.error(LOGGER, "【callFinaCreateBillNumBatch】 调用财务批量生成应收账单编号失败，返回结果result:{}", result);
            throw new FinServiceException("调用财务生成应收账单编号失败");
        }
        JSONArray billNums = jsonObject.getJSONArray("data");
        return billNums;
    }
    
    /**
      * @description: 财务合同审核前校验逻辑
      *               wiki:http://wiki.ziroom.com/pages/viewpage.action?pageId=206077972
      *               url:http://zfreceipt.t.ziroom.com/api/zryj/checkContractAudit
      * @author: lusp
      * @date: 2018/1/18 下午 20:13
      * @params: conRentCode
      * @return: Boolean
      */
    public Boolean callFinaCheckContractAudit(String conRentCode) throws FinServiceException{
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("outContractCode", conRentCode);
        LogUtil.info(LOGGER,"【callFinaCheckContractAudit】 调用财务校验合同是否能审核接口,apiUrl:{},paramJson:{}",checkContractAuditUrl,JsonEntityTransform.Object2Json(mapParam));
        String result = CloseableHttpUtil.sendPost(checkContractAuditUrl, JsonEntityTransform.Object2Json(mapParam));
        LogUtil.info(LOGGER,"【callFinaCheckContractAudit】 调用财务校验合同是否能审核返回结果,resultJson:{}",result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (Check.NuNObj(jsonObject)) {
            LogUtil.error(LOGGER, "【callFinaCheckContractAudit】 调用财务校验合同是否能审核返回json为空");
            throw new FinServiceException("调用财务校验合同是否能审核返回json为空");
        }
        Integer code = jsonObject.getInteger("code");
        if (Check.NuNObj(code) || code != ResponseCodeEnum.SUCCESS.getCode()) {
            LogUtil.info(LOGGER, "【callFinaCheckContractAudit】 调用财务校验合同是否能审核返回不能审核,msg:{}",jsonObject.getString("message"));
            return false;
        }
        return true;
    }

    /**
      * @description: 同步应收账单到财务
     *               wiki:http://wiki.ziroom.com/pages/viewpage.action?pageId=172130307
     *               url:http://zfreceipt.t.ziroom.com/api/zryj/createReceiptBill
      * @author: lusp
      * @date: 2018/2/8 上午 10:34
      * @params: conRentCode,billList
      * @return: DataTransferObject
      */
    public DataTransferObject callFinaCreateReceiptBill(String conRentCode , List<BillDto> billList){
        DataTransferObject dto = new DataTransferObject();
        JSONObject requestJson = new JSONObject();
        requestJson.put("sysCode",sysCode);
        requestJson.put("contractCode",conRentCode);
        requestJson.put("isContract",1);
        requestJson.put("billList",billList);
        LogUtil.info(LOGGER, "【callFinaCreateReceiptBill】 调用财务同步应收账单入参paramJson:{}",requestJson.toJSONString());
        //调用财务接口
        String result = CloseableHttpUtil.sendPost(createReceiptBillUrl, requestJson.toJSONString());
        LogUtil.info(LOGGER, "【callFinaCreateReceiptBill】{} 调用财务同步应收账单返回,conRentCode:{},resultStr:{}", conRentCode, result);
        JSONObject responseJson = JSONObject.parseObject(result);
        if (Check.NuNObj(responseJson)) {
            LogUtil.error(LOGGER, "【callFinaCreateReceiptBill】 调用财务同步应收账单返回json为空");
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("调用财务同步应收账单返回json为空");
            return dto;
        }
        Integer code = responseJson.getInteger("code");
        if (Check.NuNObj(code) || code != ResponseCodeEnum.SUCCESS.getCode()) {
            LogUtil.error(LOGGER, "【createReceiptBill】 调用财务同步应收账单失败，code:{},message:{}", code,responseJson.getString("message"));
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg(responseJson.getString("message"));
            return dto;
        }
        dto.setMsg(responseJson.getString("message"));
        dto.putValue("list",responseJson.getJSONArray("data"));
        return dto;
    }
}
