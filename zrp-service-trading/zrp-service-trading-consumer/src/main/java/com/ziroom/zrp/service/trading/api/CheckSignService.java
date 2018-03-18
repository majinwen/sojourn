package com.ziroom.zrp.service.trading.api;

/*
 * <P>电子验签接口</P>
 * <P>
 * <PRE>
 * <BR> 修改记录
 * <BR>------------------------------------------
 * <BR> 修改日期       修改人        修改内容
 * </PRE>
 * 
 * @Author lusp
 * @Date Create in 2017年09月 12日 17:42
 * @Version 1.0
 * @Since 1.0
 */

public interface CheckSignService {

    /**
     * @description: 根据合同id查询用户验签信息
     * @author: lusp
     * @date: 2017/9/12 17:49
     * @params: paramJson
     * @return: String
     */
    String findCheckSignCusInfoVoByUid(String paramJson);

    /**
     * @description: 电子签章创建新用户
     * @author: lusp
     * @date: 2017/9/18 11:44
     * @params: paramJson
     * @return: String
     */
    String createUser(String paramJson);

    /**
     * @description: 根据用户的 idCardNum 获取用户信息
     * @author: lusp
     * @date: 2017/9/18 11:44
     * @params: No such property: code for class: Script1
     * @return:
     */
    String getUserByIdCardNum(String paramJson);
    
    /**
     * @description: 获取用户的数字证书信息
     * @author: lusp
     * @date: 2017/9/18 11:45
     * @params: paramJson
     * @return: String
     */  
    String getCertsByUser(String paramJson);

    /**
     * @description: 为用户创建新的签章
     * @author: lusp
     * @date: 2017/9/18 11:46
     * @params: paramJson
     * @return: String
     */
//    String createSealByUserId(String paramJson);

    /**
     * @description: 签署合同
     * @author: lusp
     * @date: 2017/9/18 11:47
     * @params: paramJson
     * @return: String
     */
    String sign(String paramJson);

    /**
     * @description: 获取合同
     * @author: lusp
     * @date: 2017/9/18 11:47
     * @params: paramJson
     * @return: String
     */
    String getSignContract(String paramJson);

    /**
     * @description: 合同查验
     * @author: lusp
     * @date: 2017/9/18 11:48
     * @params: paramJson
     * @return: String
     */
    String verify(String paramJson);

    /**
     * @description: 保存电子验签错误日志
     * @author: lusp
     * @date: 2017/9/25 20:13
     * @params: paramJson
     * @return: String
     */
    String saveCheckSignErrorLog(String paramJson);

    /**
      * @description: 生成pdf合同，同时电子签章
      * @author: lusp
      * @date: 2017/10/24 上午 9:18
      * @params: contractId
      * @return: String
      */
    String generatePDFContractAndSignature(String contractId);

    /**
      * @description: 根据合同id获取合同的html字符串(提供给api,返回给app)
      * @author: lusp
      * @date: 2017/10/30 下午 18:37
      * @params: contractId
      * @return: String
      */
    String getContractHtml(String contractId);
    /**
     * <p>根据合同id获取合同的pdf</p>
     * @author xiangb
     * @created 2017年11月14日
     * @param
     * @return
     */
    String getContractPdf(String param);
}
