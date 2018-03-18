package com.ziroom.zrp.service.trading.proxy;

/*
 * <P>电子验签接口实现</P>
 * <P>查询签约人信息</P>
 * <PRE>
 * <BR> 修改记录
 * <BR>------------------------------------------
 * <BR> 修改日期       修改人        修改内容
 * <BR>2017年9月23日  xiangbin  增加查询签约人信息
 * </PRE>
 * 
 * @Author lusp
 * @Date Create in 2017年09月 12日 17:45
 * @Version 1.0
 * @Since 1.0
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.service.trading.api.CheckSignService;
import com.ziroom.zrp.service.trading.dto.EspContractDto;
import com.ziroom.zrp.service.trading.dto.EspSignatureDto;
import com.ziroom.zrp.service.trading.dto.EspUserInfoDto;
import com.ziroom.zrp.service.trading.dto.PersonalInfoDto;
import com.ziroom.zrp.service.trading.dto.customer.Cert;
import com.ziroom.zrp.service.trading.dto.customer.Profile;
import com.ziroom.zrp.service.trading.entity.*;
import com.ziroom.zrp.service.trading.service.CheckSignErrorLogServiceImpl;
import com.ziroom.zrp.service.trading.service.RentCheckinPersonServiceImpl;
import com.ziroom.zrp.service.trading.service.RentContractServiceImpl;
import com.ziroom.zrp.service.trading.utils.CustomerLibraryUtil;
import com.ziroom.zrp.service.trading.utils.EspUtil;
import com.ziroom.zrp.service.trading.utils.HtmltoPDF;
import com.ziroom.zrp.service.trading.utils.HttpUtil;
import com.ziroom.zrp.service.trading.valenum.CustomerTypeEnum;
import com.ziroom.zrp.service.trading.valenum.EspUserTypeEnum;
import com.ziroom.zrp.trading.entity.CheckSignErrorLogEntity;
import com.ziroom.zrp.trading.entity.RentContractEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("trading.checkSignServiceProxy")
public class CheckSignServiceProxy implements CheckSignService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckSignServiceProxy.class);
    
    @Resource(name = "trading.rentCheckinPersonServiceImpl")
    private RentCheckinPersonServiceImpl rentCheckinPersonServiceImpl;
    @Resource(name="trading.rentContractServiceImpl")
    private RentContractServiceImpl rentContractServiceImpl;
    
    public CheckSignServiceProxy() {
    	
	}
    @Value("#{'${esp_url}'.trim()}")
    private String espUrl;

    @Value("#{'${esp_apiId}'.trim()}")
    private String espApiId;

    @Value("#{'${esp_apiSecret}'.trim()}")
    private String espApiSecret;

    @Value("#{'${zrams_toContractHtml_url}'.trim()}")
    private String ZramsToContractHtmlUrl;
    
    @Value("#{'${signature_esp_keyWord}'.trim()}")
    private String signatureEspKeyWord;

    @Value("#{'${signature_person_keyWord}'.trim()}")
    private String signaturePersonKeyWord;

    @Value("#{'${signature_esp_orgCode}'.trim()}")
    private String signatureEspOrgCode;

    @Value("#{'${contract_pdf_url}'.trim()}")
    private String contractPdfUrl;

    @Resource(name = "trading.checkSignErrorLogServiceImpl")
    private CheckSignErrorLogServiceImpl checkSignErrorLogServiceImpl;

    @Override
    public String findCheckSignCusInfoVoByUid(String paramJson) {
        LogUtil.info(LOGGER,"【findCheckSignCusInfoVoByUid】入参:{}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            RentContractEntity rentContractEntity = JsonEntityTransform.json2Entity(paramJson,RentContractEntity.class);
            if(Check.NuNObj(rentContractEntity)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("实体不存在");
                return dto.toJsonString();
            }
            if(Check.NuNStr(rentContractEntity.getContractId())){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同id为空");
                return dto.toJsonString();
            }
            CheckSignCusInfoVo checkSignCusInfoVo = rentCheckinPersonServiceImpl.findCheckSignCusInfoVoByUid(rentContractEntity);
            //调用友家客户库查询用户信息
            PersonalInfoDto personalInfoDto = CustomerLibraryUtil.findAuthInfoFromCustomer(checkSignCusInfoVo.getCustomerUid());
            if(Check.NuNObj(personalInfoDto)){
                LogUtil.error(LOGGER, "【findCheckSignCusInfoVoByUid】 根据customerUid调用友家客户库查询客户信息失败,customerUid:{}", checkSignCusInfoVo.getCustomerUid());
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("客户信息不存在");
                return dto.toJsonString();
            }
            Cert cert = personalInfoDto.getCert();
            if(!Check.NuNObj(cert)){
                checkSignCusInfoVo.setCertType(Integer.valueOf(cert.getCert_type()));
                checkSignCusInfoVo.setCertNum(cert.getCert_num());
            }
            dto.putValue("obj", checkSignCusInfoVo);
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【findCheckSignCusInfoVoByUid】 error:{},paramJson={}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    @Override
    public String createUser(String paramJson) {
        LogUtil.info(LOGGER,"【createUser】入参:{}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            EspUserInfoDto espUserInfoDto = JsonEntityTransform.json2Object(paramJson,EspUserInfoDto.class);
            if(Check.NuNObj(espUserInfoDto)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("实体不存在");
                return dto.toJsonString();
            }
            JSONObject jsonObject = EspUtil.getJSONRequest(espApiId);
            espUserInfoDto.setAutoCert(true);//自动生成证书和签章
            jsonObject.put("user",espUserInfoDto);
            LogUtil.info(LOGGER,"【createUser】apiUrl:{},espApiSecret:{},paramJson:{}",espUrl+"/api/createUser?lang=zh_CN",espApiSecret,jsonObject.toJSONString());
            String jsonRespStr = EspUtil.send(espUrl+"/api/createUser?lang=zh_CN",jsonObject.toJSONString(),espApiSecret);
            LogUtil.info(LOGGER,"【createUser】resultJson:{},paramJson:{}",jsonRespStr,jsonObject.toJSONString());
            EspResponseVo espResponseVo = JsonEntityTransform.json2Object(jsonRespStr,EspResponseVo.class);
            dto.putValue("espResponseVo",espResponseVo);
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【createUser】 error:{},paramJson={}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    @Override
    public String getUserByIdCardNum(String paramJson) {
        LogUtil.info(LOGGER,"【getUserByIdCardNum】入参:{}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            if(Check.NuNStrStrict(paramJson)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto.toJsonString();
            }
            JSONObject jsonObject = EspUtil.getJSONRequest(espApiId);
            jsonObject.put("type",0);
            jsonObject.put("idCardNum",paramJson);
            String jsonRespStr = EspUtil.send(espUrl+"/api/getUserByIdCardNum?lang=en_US",jsonObject.toJSONString(),espApiSecret);
            EspResponseVo espResponseVo = JsonEntityTransform.json2Object(jsonRespStr,EspResponseVo.class);
            dto.putValue("espResponseVo",espResponseVo);
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【getUserByIdCardNum】 error:{},paramJson={}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    @Override
    public String getCertsByUser(String paramJson) {
        LogUtil.info(LOGGER,"【getCertsByUser】入参:{}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            if(Check.NuNStrStrict(paramJson)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto.toJsonString();
            }
            JSONObject jsonObject = EspUtil.getJSONRequest(espApiId);
            jsonObject.put("type",0);
            jsonObject.put("userId",paramJson);
            jsonObject.put("pageNum","0");
            jsonObject.put("pageSize",10);
            String jsonRespStr = EspUtil.send(espUrl+"/api/getCertsByUser?lang=en_US",jsonObject.toJSONString(),espApiSecret);
            JSONObject responseJson = JSONObject.parseObject(jsonRespStr);
            JSONObject certsJson = responseJson.getJSONObject("certs");
            JSONArray jsonArray = certsJson.getJSONArray("list");
            EspCertVo espCertVo = jsonArray.getObject(0,EspCertVo.class);
            dto.putValue("espCertVo",espCertVo);
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【getCertsByUser】 error:{},paramJson={}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

//    @Override
//    public String createSealByUserId(String paramJson) {
//        return null;
//    }

    @Override
    public String sign(String paramJson) {
        LogUtil.info(LOGGER,"【sign】入参:{}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            if(Check.NuNStrStrict(paramJson)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto.toJsonString();
            }
            EspContractDto espContractDto = JsonEntityTransform.json2Object(paramJson,EspContractDto.class);
            List<EspContractDto> contracts = new ArrayList<>();
            contracts.add(espContractDto);
            JSONObject jsonObject = EspUtil.getJSONRequest(espApiId);
            jsonObject.put("docRequired",true);
            jsonObject.put("contracts",contracts);
            LogUtil.info(LOGGER,"【sign】apiUrl:{},espApiSecret:{},paramJson:{}",espUrl+"/api/sign?lang=zh_CN",espApiSecret,jsonObject.toJSONString());
            String jsonRespStr = EspUtil.send(espUrl+"/api/sign?lang=zh_CN",jsonObject.toJSONString(),espApiSecret);
            LogUtil.info(LOGGER,"【sign】resultJson:{},paramJson:{}",jsonRespStr,jsonObject.toJSONString());
            EspSignContractVo espSignContractVo = JsonEntityTransform.json2Object(jsonRespStr,EspSignContractVo.class);
            dto.putValue("espSignContractVo",espSignContractVo);
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【sign】 error:{},paramJson={}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    @Override
    public String getSignContract(String paramJson) {
        LogUtil.info(LOGGER,"【getSignContract】入参:{}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            if(Check.NuNStrStrict(paramJson)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto.toJsonString();
            }
            JSONObject jsonObject = EspUtil.getJSONRequest(espApiId);
            jsonObject.put("docNum",paramJson);
            String jsonRespStr = EspUtil.send(espUrl+"/api/getSignContract?lang=zh_CN",jsonObject.toJSONString(),espApiSecret);
            EspContractVo espContractVo = JsonEntityTransform.json2Object(jsonRespStr,EspContractVo.class);
            dto.putValue("espContractVo",espContractVo);
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【getSignContract】 error:{},paramJson={}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    @Override
    public String verify(String paramJson) {
        LogUtil.info(LOGGER,"【verify】入参:{}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            if(Check.NuNStrStrict(paramJson)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto.toJsonString();
            }
            JSONObject jsonObject = EspUtil.getJSONRequest(espApiId);
            jsonObject.put("contractId",paramJson);
            String jsonRespStr = EspUtil.send(espUrl+"/contract/verify?lang=zh_CN",jsonObject.toJSONString(),espApiSecret);
            EspResponseVo espResponseVo = JsonEntityTransform.json2Object(jsonRespStr,EspResponseVo.class);
            dto.putValue("espResponseVo",espResponseVo);
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【verify】 error:{},paramJson={}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    @Override
    public String saveCheckSignErrorLog(String paramJson) {
        LogUtil.info(LOGGER,"【saveCheckSignErrorLog】入参:{}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            if(Check.NuNStrStrict(paramJson)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto.toJsonString();
            }
            CheckSignErrorLogEntity checkSignErrorLogEntity = JsonEntityTransform.json2Entity(paramJson,CheckSignErrorLogEntity.class);
            if(Check.NuNObj(checkSignErrorLogEntity)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数错误");
                return dto.toJsonString();
            }
            if(Check.NuNStrStrict(checkSignErrorLogEntity.getContractId())){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数错误");
                return dto.toJsonString();
            }
            if(Check.NuNStrStrict(checkSignErrorLogEntity.getErrMsg())){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("错误消息不能为空");
                return dto.toJsonString();
            }
            if(checkSignErrorLogEntity.getErrMsg().length()>4000){
                checkSignErrorLogEntity.setErrMsg(checkSignErrorLogEntity.getErrMsg().substring(0,3999));
            }
            int num = checkSignErrorLogServiceImpl.saveCheckSignErrorLog(checkSignErrorLogEntity);
            dto.putValue("num",num);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【saveCheckSignErrorLog】 error:{},paramJson={}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    /**
     * @description: 生成pdf合同，同时电子签章
     * @author: lusp
     * @date: 2017/10/23 下午 17:16
     * @params: ContractId
     * @return: String
     */
    @Override
    public String generatePDFContractAndSignature(String contractId){
        LogUtil.info(LOGGER,"【generatePDFContractAndSignature】入参contractId:{}",contractId);
        DataTransferObject dto = new DataTransferObject();
        try {
            if(Check.NuNStrStrict(contractId)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同id为空");
                return dto.toJsonString();
            }
            RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId(contractId);
            if(Check.NuNObj(rentContractEntity)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同不存在");
                return dto.toJsonString();
            }
            //1.生成pdf合同，并保存在对应合同目录中
            String contractHtml = getContractHtmlStr(rentContractEntity,"pdf");//获取合同的html字符串
            if(Check.NuNStrStrict(contractHtml)){
                LogUtil.error(LOGGER, "【generatePDFContractAndSignature】 调用zrams获取合同html字符串为空,contractId:{}", rentContractEntity.getContractId());
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("调用zrams获取合同html字符串为空");
                return dto.toJsonString();
            }
            StringBuilder pdfFilePath = new StringBuilder(HtmltoPDF.pdfPath);
            if(rentContractEntity.getCustomerType()== CustomerTypeEnum.PERSON.getCode()){
                pdfFilePath.append(HtmltoPDF.contractFile);
            }else {
                pdfFilePath.append(HtmltoPDF.contractEpsFile);
            }
            pdfFilePath.append(rentContractEntity.getConRentCode()).append(HtmltoPDF.pdfSuffix);
            HtmltoPDF.htmlStrToPdf(contractHtml, pdfFilePath.toString());
            //2.创建用户信息、生成签章
            PersonalInfoDto personalInfoDto = CustomerLibraryUtil.findAuthInfoFromCustomer(rentContractEntity.getCustomerUid());
            if(Check.NuNObj(personalInfoDto)){
                LogUtil.error(LOGGER, "【generatePDFContractAndSignature】 根据customerUid调用友家客户库查询客户信息失败,customerUid:{}", rentContractEntity.getCustomerUid());
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("客户信息不存在");
                return dto.toJsonString();
            }
            Profile profile = personalInfoDto.getProfile();
            Cert cert = personalInfoDto.getCert();
            EspUserInfoDto espUserInfoDto = new EspUserInfoDto();
            espUserInfoDto.setIdCardNum(cert.getCert_num());
            espUserInfoDto.setMobile(profile.getPhone());
            espUserInfoDto.setUserType(EspUserTypeEnum.PERSON.getCode());
            espUserInfoDto.setFullname(profile.getUser_name());
            espUserInfoDto.setUserId(rentContractEntity.getCustomerUid());
            String createUserJson = createUser(JsonEntityTransform.Object2Json(espUserInfoDto));
            DataTransferObject createUserDto = JsonEntityTransform.json2DataTransferObject(createUserJson);
            if(createUserDto.getCode()==DataTransferObject.ERROR){
                LogUtil.error(LOGGER, "【generatePDFContractAndSignature】创建用户、生成用户签章失败 errMsg:{},contractId:{}", createUserDto.getMsg(), contractId);
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("创建用户、生成用户签章失败");
                return dto.toJsonString();
            }
            //获取关键词所在坐标位置集合，供签章使用
            LogUtil.info(LOGGER, "【generatePDFContractAndSignature】关键字定位开始 pdfFilePath:{},signatureEspKeyWord:{},signaturePersonKeyWord:{}", pdfFilePath.toString(), signatureEspKeyWord,signaturePersonKeyWord);
            List<HashMap<String,Object>> espCoordinateMaps = HtmltoPDF.getKeyWordsCoordinate(pdfFilePath.toString(),signatureEspKeyWord);
            List<HashMap<String,Object>> userCoordinateMaps = HtmltoPDF.getKeyWordsCoordinate(pdfFilePath.toString(),signaturePersonKeyWord);
            //3.个人和公司电子签章（需找产品确认签章位置）
            List<EspSignatureDto> espSignatureDtos = new ArrayList<>();
            //企业签章位置
            for(HashMap<String,Object> espCoordinateMap:espCoordinateMaps){
                EspSignatureDto espSignatureDto = new EspSignatureDto();
                espSignatureDto.setUserType(EspUserTypeEnum.COMPANY.getCode());
                espSignatureDto.setOrgCode(signatureEspOrgCode);//自如寓企业code
                espSignatureDto.setPage((Integer) espCoordinateMap.get("pageNum"));
                espSignatureDto.setPositionX(((Float) espCoordinateMap.get("X")).intValue()+70);
                espSignatureDto.setPositionY(((Float) espCoordinateMap.get("Y")).intValue()-80);
                espSignatureDtos.add(espSignatureDto);
            }
            //个人签章位置
            for(HashMap<String,Object> userCoordinateMap:userCoordinateMaps){
                EspSignatureDto espSignatureDto = new EspSignatureDto();
                espSignatureDto.setUserType(EspUserTypeEnum.PERSON.getCode());
                espSignatureDto.setIdCardNum(cert.getCert_num());
                espSignatureDto.setPage((Integer) userCoordinateMap.get("pageNum"));
                espSignatureDto.setPositionX(((Float) userCoordinateMap.get("X")).intValue()+70);
                espSignatureDto.setPositionY(((Float) userCoordinateMap.get("Y")).intValue()-30);
                espSignatureDtos.add(espSignatureDto);
            }
            EspContractDto espContractDto = new EspContractDto();
            espContractDto.setTitle("自如寓合同");//合同title
            espContractDto.setFileName(rentContractEntity.getConRentCode());
            espContractDto.setDocNum(contractId);
            espContractDto.setDocType(HtmltoPDF.pdfSuffix);
            if(Check.NuNCollection(espSignatureDtos)){
                LogUtil.error(LOGGER, "【generatePDFContractAndSignature】关键字定位失败,签章坐标集合为空 contractId:{}", contractId);
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("电子签章失败");
                return dto.toJsonString();
            }
            espContractDto.setSignatures(espSignatureDtos);

            File file = new File(pdfFilePath.toString());
            String base64Str = EspUtil.getPDFBinary(file);
            espContractDto.setDoc(base64Str);
            String signJson = sign(JsonEntityTransform.Object2Json(espContractDto));
            DataTransferObject signDto = JsonEntityTransform.json2DataTransferObject(signJson);
            EspSignContractVo espSignContractVo = SOAResParseUtil.getValueFromDataByKey(signJson,"espSignContractVo",EspSignContractVo.class);
            if(signDto.getCode()==DataTransferObject.ERROR||Check.NuNObj(espSignContractVo)){
                LogUtil.error(LOGGER, "【generatePDFContractAndSignature】电子签章失败 errMsg:{},contractId:{}", createUserDto.getMsg(), contractId);
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("电子签章失败");
                return dto.toJsonString();
            }
            /*******************获取签约后合同修改，改为直接从签约结果获取******************/
            //4.获取签约后的合同
//            String getSignContractJson = getSignContract(contractId);
//            DataTransferObject getSignContractDto = JsonEntityTransform.json2DataTransferObject(getSignContractJson);
//            if(getSignContractDto.getCode()==DataTransferObject.ERROR){
//                LogUtil.error(LOGGER, "【generatePDFContractAndSignature】获取签章后电子合同失败 errMsg:{},contractId:{}", createUserDto.getMsg(), contractId);
//                dto.setErrCode(DataTransferObject.ERROR);
//                dto.setMsg("获取签章后电子合同失败");
//                return dto.toJsonString();
//            }
//            EspContractVo espContractVo = SOAResParseUtil.getValueFromDataByKey(getSignContractJson,"espContractVo",EspContractVo.class);
            /*******************获取签约后合同修改，改为直接从签约结果获取******************/
            List<EspContractVo> espContractVos = espSignContractVo.getContracts();
            if(Check.NuNCollection(espContractVos)){
                LogUtil.error(LOGGER, "【generatePDFContractAndSignature】获取电子签章合同失败, 签约返回值 resultJson:{}", signJson);
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("获取电子签章合同失败");
                return dto.toJsonString();
            }
            for(EspContractVo espContractVo:espContractVos){
                String base64Str2 = espContractVo.getDoc();
                EspUtil.base64StringToPDF(base64Str2,pdfFilePath.toString());
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【generatePDFContractAndSignature】 error:{},contractId={}", e, contractId);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    @Override
    public String getContractHtml(String contractId) {
        LogUtil.info(LOGGER, "【getContractHtml】contractId:{}", contractId);
        DataTransferObject dto = new DataTransferObject();
        try {
            if (Check.NuNStr(contractId)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同id为空");
                return dto.toJsonString();
            }
            //查询合同
            RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId(contractId);
            if(Check.NuNObj(rentContractEntity)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同信息不存在");
                return dto.toJsonString();
            }
            String contractHtml = getContractHtmlStr(rentContractEntity,"app");
            LogUtil.info(LOGGER, "【getContractHtml】请求ZRAMS返回：{}", contractHtml);
            dto.putValue("contractPage",contractHtml);
        }catch (Exception e){
            LogUtil.error(LOGGER, "【getContractHtml】 contractId={},error:{},", contractId, e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    private String getContractHtmlStr(RentContractEntity rentContractEntity,String source) throws Exception{
        // 调用ZRAMS
        Map<String, String> map = new HashMap<>();
        map.put("contractId", rentContractEntity.getContractId());
        map.put("phoneNum", rentContractEntity.getCustomerMobile());
        map.put("roomId", rentContractEntity.getRoomId());
        map.put("source",source);
        InputStream inputStream = HttpUtil.sendPostRequest(ZramsToContractHtmlUrl, map);
        return HttpUtil.getTextContent(inputStream, "utf-8");
    }
    /**
     * <p>获取合同pdf</p>
     * @author xiangb
     * @created 2017年11月14日
     * @param
     * @return
     */
    @Override
    public String getContractPdf(String param) {
        LogUtil.info(LOGGER, "【getContractPdf】入参:{}", param);
        DataTransferObject dto = new DataTransferObject();
        BufferedReader in = null;
        try {
        	Map<String,String> map = (Map<String, String>) JsonEntityTransform.json2Map(param);
        	String uid = map.get("uid");
        	String contractId = map.get("contractId");
            if (Check.NuNStr(uid) || Check.NuNStr(contractId)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto.toJsonString();
            }
            //查询合同
            RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId(contractId);
            if(Check.NuNObj(rentContractEntity) || Check.NuNStr(rentContractEntity.getConRentCode())){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同信息不存在");
                return dto.toJsonString();
            }
            if(!(uid.equals(rentContractEntity.getCustomerUid()))){
            	dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("用户uid未对应");
                return dto.toJsonString();
            }
            File file = new File(contractPdfUrl+rentContractEntity.getConRentCode()+".pdf");
            if(!(file.exists())){
            	LogUtil.info(LOGGER, "【getContractPdf】合同PDF文件不存在，contractId:{}", contractId);
            	dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同文件不存在");
                return dto.toJsonString();
            }
            String base64Str = EspUtil.getPDFBinary(file);
            LogUtil.info(LOGGER, "【getContractHtml】请求ZRAMS返回：{}",base64Str);
            dto.putValue("contractPage",base64Str);
        }catch (Exception e){
            LogUtil.error(LOGGER, "【getContractPdf】 error:{},param={}", e, param);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }
}
