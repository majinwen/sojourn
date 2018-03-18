package com.ziroom.zrp.service.houses.proxy;
/*
 * <P></P>
 * <P>
 * <PRE>
 * <BR> 修改记录
 * <BR>------------------------------------------
 * <BR> 修改日期       修改人        修改内容
 * </PRE>
 * 
 * @Author lusp
 * @Date Create in 2017年09月 18日 15:35
 * @Version 1.0
 * @Since 1.0
 */

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dto.EspContractDto;
import com.ziroom.zrp.service.trading.dto.EspSignatureDto;
import com.ziroom.zrp.service.trading.dto.EspUserInfoDto;
import com.ziroom.zrp.service.trading.entity.EspContractVo;
import com.ziroom.zrp.service.trading.entity.EspResponseVo;
import com.ziroom.zrp.service.trading.entity.EspSignContractVo;
import com.ziroom.zrp.service.trading.proxy.CheckSignServiceProxy;
import com.ziroom.zrp.service.trading.service.RentContractServiceImpl;
import com.ziroom.zrp.service.trading.utils.EspUtil;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckSignServiceProxyTest extends BaseTest{

    @Resource(name="trading.checkSignServiceProxy")
    private CheckSignServiceProxy checkSignServiceProxy;

    @Resource(name="trading.rentContractServiceImpl")
    private RentContractServiceImpl rentContractServiceImpl;

    @Test
    public void testCreateUser(){
        EspUserInfoDto espUserInfoDto = new EspUserInfoDto();
        espUserInfoDto.setIdCardNum("41052219880825722X");
        espUserInfoDto.setMobile("17701338460");
        espUserInfoDto.setUserType(0);
        espUserInfoDto.setFullname("任秀姣");
        espUserInfoDto.setUserId("9e0981d1-19e1-3990-33bb-bd0a767ba805");
        String paramJson = JsonEntityTransform.Object2Json(espUserInfoDto);
        String resultJson = checkSignServiceProxy.createUser(JsonEntityTransform.Object2Json(espUserInfoDto));


    }

    @Test
    public void testCreateOrg(){
        DataTransferObject dto = new DataTransferObject();
        try {
            JSONObject jsonObject = EspUtil.getJSONRequest("itrus");
            Map<String, Object> user = new HashMap<String, Object>();
            user.put("userType", 1);
            user.put("fullname", "自如寓测试");
            user.put("userId", "zry88888888");
            user.put("orgCode", "88888888");
            user.put("idCardType", "身份证");
            user.put("legalPersonName", "super");
            user.put("transactorName", "admin");
            user.put("transactorIdCardNum", "123456789000");
            user.put("transactorMobile", "18899993333");
            user.put("autoCert", "true");
            jsonObject.put("user",user);
            String jsonRespStr = EspUtil.send("http://esp.t.ziroom.com/esp/api/createUser?lang=zh_CN",jsonObject.toJSONString(),"a12d765e3319adc129c9b822d5c1eb84");
            EspResponseVo espResponseVo = JsonEntityTransform.json2Object(jsonRespStr,EspResponseVo.class);
            dto.putValue("espResponseVo",espResponseVo);
        } catch (BusinessException e) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
    }

    @Test
    public void testJson(){
//        String jsonRespStr = "{\"isOK\":false}";
//        EspResponseVo espResponseVo = JsonEntityTransform.json2Object(jsonRespStr,EspResponseVo.class);
//        System.out.println(espResponseVo);

        EspResponseVo espResponseVo = new EspResponseVo();
        espResponseVo.setResult(false);
        System.out.println(JsonEntityTransform.Object2Json(espResponseVo));

    }

    @Test
    public void testGetUserByIdCardNum(){
        String paramJson = "777888899990001";
        String resultJson = checkSignServiceProxy.getUserByIdCardNum(paramJson);
//        checkSignServiceProxy.createSealByUserId();

    }


    @Test
    public void testGetCertsByUser(){
        String paramJson = "411425162955552362";
        String resultJson = checkSignServiceProxy.getCertsByUser(paramJson);

    }

    @Test
    public void testSign() throws Exception{

        EspSignatureDto espSignatureDto1 = new EspSignatureDto();
        espSignatureDto1.setUserType(0);
        espSignatureDto1.setIdCardNum("411425162955552362");
        espSignatureDto1.setPage(1);
        espSignatureDto1.setPositionX(80);
        espSignatureDto1.setPositionY(515);

        EspSignatureDto espSignatureDto2 = new EspSignatureDto();
        espSignatureDto2.setUserType(1);
        espSignatureDto2.setOrgCode("91110228MA005HNQXF");
        espSignatureDto2.setPage(1);
        espSignatureDto2.setPositionX(200);
        espSignatureDto2.setPositionY(200);
        List<EspSignatureDto> espSignatureDtos = new ArrayList<>();
        espSignatureDtos.add(espSignatureDto1);
        espSignatureDtos.add(espSignatureDto2);
        EspContractDto espContractDto = new EspContractDto();
        espContractDto.setTitle("自如寓测试");
        espContractDto.setFileName("测试.pdf");
        espContractDto.setDocNum("test100001");
        espContractDto.setDocType("pdf");
        espContractDto.setSignatures(espSignatureDtos);

        File file = new File("C:\\Users\\Administrator\\Desktop\\测试合同\\TEST_new.pdf");
        String base64Str = EspUtil.getPDFBinary(file);
        espContractDto.setDoc(base64Str);

        String resultJson = checkSignServiceProxy.sign(JsonEntityTransform.Object2Json(espContractDto));

        DataTransferObject signDto = JsonEntityTransform.json2DataTransferObject(resultJson);
        EspSignContractVo espSignContractVo = SOAResParseUtil.getValueFromDataByKey(resultJson,"espSignContractVo",EspSignContractVo.class);
        List<EspContractVo> espContractVos = espSignContractVo.getContracts();
        for(EspContractVo espContractVo:espContractVos){
            String base64Str2 = espContractVo.getDoc();
            EspUtil.base64StringToPDF(base64Str2,"C:\\Users\\Administrator\\Desktop\\测试合同\\001.pdf");
        }

    }

//    public String createSealByUserId() {
//        DataTransferObject dto = new DataTransferObject();
//        try {
//            JSONObject sealJson = new JSONObject();
//            sealJson.put("sealImage",EspUtil.getPDFBinary(new File("E:\\apartment\\upload\\contractFile\\自如寓酒管-合同.png")));
//            sealJson.put("sealDesc","test");
//            JSONObject jsonObject = EspUtil.getJSONRequest(espApiId);
//            jsonObject.put("type",1);
//            jsonObject.put("userId","88888888");
//            jsonObject.put("seal",sealJson);
//            String jsonRespStr = EspUtil.send(espUrl+"/api/createSealByUserId",jsonObject.toJSONString(),espApiSecret);
//
//
//            EspResponseVo espResponseVo = JsonEntityTransform.json2Object(jsonRespStr,EspResponseVo.class);
//            dto.putValue("espResponseVo",espResponseVo);
//        } catch (BusinessException e) {
//            dto.setErrCode(DataTransferObject.ERROR);
//            dto.setMsg("系统错误");
//        }
//        return dto.toJsonString();
//    }

    @Test
    public void testGetSignContract() throws Exception{
        String resultJson = checkSignServiceProxy.getSignContract("8a90930060f86f3d0160f86f77a40005");
//        JSONObject jsonObject = JSONObject.parseObject(resultJson);
//        String base64Str = jsonObject.getString("doc");
//        EspUtil.base64StringToPDF(base64Str);

        EspContractVo espContractVo = SOAResParseUtil.getValueFromDataByKey(resultJson,"espContractVo",EspContractVo.class);
        String base64Str = espContractVo.getDoc();
        EspUtil.base64StringToPDF(base64Str,"D:\\自如寓签约合同1.PDF");
    }

    @Test
    public void testVerify() throws Exception{
        String resultJson = checkSignServiceProxy.verify("8a909300611bb5a801611c8ed35b0007");
        EspResponseVo espResponseVo = SOAResParseUtil.getValueFromDataByKey(resultJson,"espResponseVo",EspResponseVo.class);
    }
    
    @Test
    public void findCheckinPerson() throws Exception{
//        String resultJson = checkSignServiceProxy.findCheckinPerson("8a9e988f5ea28f9d015ea34f43910010");
//        System.err.println(resultJson);
    }

    @Test
    public void testHtmlToPdf()throws Exception{
//        RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId("8a908e105fc2a30c015fc2af2b77000c");
//        String contractHtml = checkSignServiceProxy.getContractHtmlStr(rentContractEntity,"pdf");//获取合同的html字符串
//        HtmltoPDF.htmlStrToPdf(contractHtml,"E:\\apartment\\upload\\contractFile\\test.pdf");
    }

    @Test
    public void testgeneratePDFContractAndSignature(){
        checkSignServiceProxy.generatePDFContractAndSignature("8a909300611bb5a801611c8ed35b0007");
    }
}
