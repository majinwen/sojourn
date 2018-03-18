package com.ziroom.zrp.service.trading.api;

/*
 * <P>调用财务接口服务</P>
 * <P>
 * <PRE>
 * <BR> 修改记录
 * <BR>------------------------------------------
 * <BR> 修改日期       修改人        修改内容
 * </PRE>
 * 
 * @Author lusp
 * @Date Create in 2017年09月 21日 11:42
 * @Version 1.0
 * @Since 1.0
 */

import com.asura.framework.base.entity.DataTransferObject;

public interface CallFinanceService {

    /**
     * @description: 传入账单类型从财务获取应收账单编号
     * @author: lusp
     * @date: 2017/9/21 11:44
     * @params: billType
     * @return: String
     */
    String createBillNum(String billType);

    /**
     * @description: 传入账单类型从财务获取应收账单编号(批量)
     * @author: lusp
     * @date: 2017/11/14 11:44
     * @params: billType,total
     * @return: String
     */
    String createBillNumBatch(String billType,int total);

    /**
     * @description: 同步合同到财务
     * @author: lusp
     * @date: 2017/9/22 15:42
     * @params: paramJson
     * @return: String
     */
    String syncContract(String paramJson);


    /**
     * @description: 更新合同信息到财务
     * @author: lusp
     * @date: 2017/9/22 20:01
     * @params: paramJson
     * @return: String
     */
    String updateContract(String paramJson);
    
    /**
      * @description: 更新企业老数据合同状态信息到财务
      * @author: lusp
      * @date: 2018/1/16 上午 10:31
      * @params: contractId,contractId
      * @return: String
      */
    String updateContractForOldEsp(String contractId,String conRentCode,String statusCode);

    /**
     * <p>查询应收账单  请求示例</p>
     * <pre>
     * ReceiptBillRequest receiptBillRequest = new ReceiptBillRequest();
     * receiptBillRequest.setOutContractCode(rentContractEntity.getConRentCode());
     * receiptBillRequest.setBillNum(DocumentTypeEnum.LIFE_FEE.getCode());
     * </pre>
     * <pre>
     *  {
     *   "billNum":"10012017090120011"  //String类型 账单编号（账单编号与出房合同号不能同时为空）
     *   "outContractCode":"BJ101160629066"  //String类型 出房合同号
     *   "documentType":"1001"  //String类型 非必填 账单类型（1001：生活费用；1007：友家收款计划；1009：友家出房违约金）
     *   "periods":1  //Integer类型 非必填 期数
     *  }
     * </pre>
     * <p>wiki地址 http://wiki.ziroom.com/pages/viewpage.action?pageId=187465806</p>
     *
     * @author jixd
     * @created 2017年11月06日 16:35:24
     * @param
     * @return listStr(List ReceiptBillResponse)  isPay isEmpty
     */
    String getReceivableBillInfo(String paramJson);


    /**
     * @description: 根据合同id 同步应收账单到财务（提交合同时）
     * @author: lusp
     * @date: 2017/9/26 16:27
     * @params: contractId
     * @return: String
     */
    String createReceiptBill(String contractId);

    /**
      * @description: 修改应收账单到财务
      *  {
      *     "billNum":"1001201701010003"  //String类型 账单编号
      *     "documentAmount":100  //Integer类型 必填 费用金额（单位分）
      *     "endTime":"2016-08-01"  //String类型 非必填 账单周期结束时间 yyyy-MM-dd
      *     "isDel":1 //Integer类型 非必填（0未删除，1逻辑删除 不填不对该字段状态进行修改）
      *  }
      * @author: lusp
      * @date: 2017/11/7 下午 12:50
      * @params: paramJson
      * @return: String
      */
    String modifyReceiptBill(String paramJson);

    /**
     * 查询收款单列表 通过合同号和期数
     * <p>
     *     wiki地址:http://wiki.ziroom.com/pages/viewpage.action?pageId=215777392
     *     请求地址:http://zfreceipt.t.ziroom.com/api/zryj/getReceiptListByContract
     * </p>
     * @author cuigh6
     * @Date 2017年10月10日
     * @param paramJson { "periods":"1",期数 null为全部 "confirmStatus":"1", 审核状态：3 未提交 0 未审核，1 审核通过，2 审核未通过 null为全部 "receiptStatus":"0",收款状态:0已收款,1未收款 2打回 null为全部 "verificateStatus":"1",应收账单核销状态 0未核销,1已核销,2部分核销 null为全部 "contractCode":"BJZYCW81707040014"出房合同号 必传 }
     * @return
     */
    String getReceiptBillListByContract(String paramJson);
    /**
     * <p>（CRM、资产）查询实收信息（根据账单编号）</p>
     * <p>wiki地址：http://wiki.ziroom.com/pages/viewpage.action?pageId=187465832</p>
     * <p>描述：查询实收信息（根据账单编号）</p>
	 * <p>接口地址：http://zfreceipt.t.ziroom.com/api/zryj/getReceiptInfo</p>
	 * <p>入参：应收账单编号</p>
     * @author xiangb
     * @created 2017年10月13日
     * @param billNoList 入参格式：["10072017061400084","10072017061400085","10072017061400086"]
     * @return
     */
    String getReceiptInfoByBill(String billNoList);

    /**
     * 查询实收信息（根据账单编号）<br/>
     * url=http://zfreceipt.t.ziroom.com/api/zryj/getReceiptInfo
     * wiki=http://wiki.ziroom.com/pages/viewpage.action?pageId=187465832
     *
     * @Author: wangxm113
     * @Date: 2017年10月15日 15时33分34秒
     */
    String getReceiptInfo(String paramJson);

    /**
     * <p>
     *     生成付款单接口
     *     wiki地址:http://wiki.ziroom.com/pages/viewpage.action?pageId=173178925
     * </p>
     * @param paramJson [{"paySerialNum":"sf","outContractCode":"BJWL15901797","uid":"1002112","payVouchersDetail":[{"costCode":"sf","refundAmount":-200}]}]
     * @return json
     * @author cuigh6
     * @Date 2017年10月16日
     */
    DataTransferObject createPayVouchers(String paramJson);

    /**
     *
     * 根据合同号列表查询应收账单信息接口<br/>
     * url=http://zfreceipt.t.ziroom.com//api/tbReceivableBill/getReceiptBillByContracts
     * wiki=hhttp://wiki.ziroom.com/pages/viewpage.action?pageId=298549353
     *
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    String getReceiptBillByContracts(String paramJson);

    /**
     * 查询收款方式 从财务系统
     * <p>
     *     url:http://zfbaseinfo.t.ziroom.com/api/offlineReceiptBank/getOfflineReceiptBank
     *     wiki:http://wiki.ziroom.com/pages/viewpage.action?pageId=246874182
     * </p>
     * @param companyCode 公司编码
     * @return json
     * @author cuigh6
     * @Date 2017年11月1日
     */
    String getReceiptMethod(String companyCode);
    /**
     * 根据条件获取应收账单列表
     * <p>
     *    wiki地址: http://wiki.ziroom.com/pages/viewpage.action?pageId=304906242
     *    url: http:zfreceipt.ziroom.com/api/tbReceivableBill/getZRARecBill
     * </p>
     * @param paramJson 参数
     * @return
     * @author cuigh6
     * @Date 2017年11月14日
     */
    String getReceivableBillListByCondition(String paramJson);

    /**
     * 查询催收工单列表（新）<br/>
     * url:http://urge.t.ziroom.com/api/zra/getFollowList<br/>
     * wiki:http://wiki.ziroom.com/pages/viewpage.action?pageId=315621415
     *
     * @Author: wangxm113
     * @Date: 2017年11月10日 14时37分07秒
     */
    String getFzckList(String projectId);

    /**
     * 催收系统工单详情接口<br/>
     * url: http://urge.t.ziroom.com/api/zra/getFollowDetail<br/>
     * wiki: http://wiki.ziroom.com/pages/viewpage.action?pageId=315621417
     *
     * @Author: wangxm113
     * @Date: 2017年11月10日 10时36分43秒
     */
    String getFollowupRecord(String paramStr);

    /**
     * 某合同下的跟进记录<br/>
     * url:http://urge.t.ziroom.com/api/zra/getFollowRecord<br/>
     * wiki:http://wiki.ziroom.com/pages/viewpage.action?pageId=315621419
     *
     * @Author: wangxm113
     * @Date: 2017年11月10日 14时10分17秒
     */
    String getRecordList(String contractCode);

    /**
     * 获取催收工单信息的数目<br/>
     * url:http://urge.t.ziroom.com/api/zra/getFollowListCount<br/>
     * wiki:http://wiki.ziroom.com/pages/viewpage.action?pageId=315621527
     *
     * @Author: wangxm113
     * @Date: 2017年11月10日 16时29分50秒
     */
    String getFzckCount(String projectId);

    /**
     * 新增催收跟进记录<br/>
     * url:http://urge.t.ziroom.com/api/zra/saveFollowRecord<br/>
     * wiki:http://wiki.ziroom.com/pages/viewpage.action?pageId=316145711
     *
     * @Author: wangxm113
     * @Date: 2017年11月10日 16时43分41秒
     */
    String saveFollowupRecord(String paramStrDto);

    /**
     * <p>创建收款单</p>
     * <pre>
     *     入参示例
     *    {
     *   "sysCode":"CRM",  //String类型 系统标示
     *   "isCheckContract":1  //Integer类型 是否校验合同（0：不校验；1：校验）
     *   "preBillNumList":["1001201704230003","1001201704230004"],  //应收账单编号
     *   "receiptList":[{
     *   ......
     *   "annexList":["http://10.16.5.168:9090/upload/14_1.png","http://456/kdt/q.txt"]
     *   }]
     *   }
     *
     * </pre>
     * <p>wiki地址 http://wiki.ziroom.com/pages/viewpage.action?pageId=172130396</p>
     * @author jixd
     * @created 2017年11月22日 10:13:13
     * @param
     * @return
     */
    String createReceipt(String param);

    /**
      * @description: zrams收款单管理列表查询接口
      * @author: lusp
      * @date: 2017/11/27 下午 16:32
      * @params: paramJson
      * @return: String
      */
    String getZRAReceiptBillListForMS(String paramJson);

    /**
      * @description: 获取财务收款单列表
      * @author: lusp
      * @date: 2017/11/21 上午 11:11
      * @params: paramJson
      * @return: String
      */
    String getZRAReceiptBill(String paramJson);

    /**
     * @description: 收款单回调修改本地收款单状态
     * @author: lusp
     * @date: 2017/12/6 下午 15:11
     * @params: paramJson
     * @return: String
     */
    String receiptBillCallbackUpdateStatus(String paramJson);
    /**
     * <p>APP支付回调</p>
     * @author xiangb
     * @created 2017年12月14日
     * @param
     * @return
     */
    String paymentCallback(String param);

    /**
      * @description: 修改收款单
      * @author: lusp
      * @date: 2017/12/19 下午 14:42
      * @params: paramJson
      * @return: String
      */
    String updateReceipt(String paramJson);

    /**
      * @description: 调用财务校验合同是否能审核
      * @author: lusp
      * @date: 2018/1/18 下午 20:27
      * @params: conRentCode
      * @return: String
      */
    String checkContractAudit(String conRentCode);

    /**
     * @description: 调用财务校验合同是否能审核(企业老数据)
     * @author: lusp
     * @date: 2018/1/18 下午 20:42
     * @params: contractId,conRentCode
     * @return: String
     */
    String checkContractAuditForEspOld(String contractId,String conRentCode);

    /**
      * @description: 根据合同号或者收款单号获取收款信息
      *             wiki:http://wiki.ziroom.com/pages/viewpage.action?pageId=381091962
      *             apiUrl:http://zfreceipt.t.ziroom.com/api/zryj/getReceiptListByBillnum
      * @author: lusp
      * @date: 2018/2/1 下午 15:29
      * @params: paramJson
      * @return: String
      */
    String getReceiptListByBillnum(String paramJson);

}
