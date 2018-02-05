package com.ziroom.minsu.api.cms.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.evaluate.dto.LandlordEvaListRequest;
import com.ziroom.minsu.entity.cms.NpsEntiy;
import com.ziroom.minsu.services.cms.api.inner.NpsService;
import com.ziroom.minsu.services.cms.dto.NpsAttendRequest;
import com.ziroom.minsu.services.cms.dto.NpsGetRequest;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/11/11 16:05
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/nps")
public class NpsController {
    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(NpsController.class);

    @Resource(name = "cms.npsService")
    private NpsService npsService;


    @RequestMapping("/${LOGIN_AUTH}/getRecommendNps")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> getRecommendNps(HttpServletRequest request) {
        DataTransferObject dto = new DataTransferObject();
        dto.putValue("can", YesOrNoEnum.NO.getCode());
        try {
            String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.debug(logger, "getRecommendNps params:{}", paramJson);
            Map par = JsonEntityTransform.json2Map(paramJson);
            String uid = ValueUtil.getStrValue(par.get("uid"));


            NpsGetRequest npsGetRequest = new NpsGetRequest();
            npsGetRequest.setUid(uid);
            npsGetRequest.setUserType(UserTypeEnum.TENANT.getUserCode());
//            npsGetRequest.setNpsCode("recommend");

            String npsJson = npsService.getNps(JsonEntityTransform.Object2Json(npsGetRequest));
            DataTransferObject npsDto = JsonEntityTransform.json2DataTransferObject(npsJson);
            if(npsDto.getCode() != DataTransferObject.SUCCESS){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
            }

            Boolean can = SOAResParseUtil.getBooleanFromDataByKey(npsJson, "can");
            if(Check.NuNObj(can) || !can){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
            }

            NpsEntiy nps = SOAResParseUtil.getValueFromDataByKey(npsJson, "nps", NpsEntiy.class);
            if(Check.NuNObj(nps)){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
            }

            dto.putValue("can", YesOrNoEnum.YES.getCode());
            dto.putValue("content", nps.getNpsContent());
            return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(logger, "e={}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
        }
    }

    /**
     * 保存nps信息
     * @param request
     * @return
     */
    @RequestMapping("/${LOGIN_AUTH}/saveNps")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> saveNps(HttpServletRequest request) {
        DataTransferObject dto = new DataTransferObject();
        try {
            String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            NpsAttendRequest npsAttendRequest = JsonEntityTransform.json2Object(paramJson, NpsAttendRequest.class);
            npsAttendRequest.setUserType(UserTypeEnum.TENANT.getUserCode());
//            npsAttendRequest.setNpsCode("recommend");
            Map par = JsonEntityTransform.json2Map(paramJson);
            String uid = ValueUtil.getStrValue(par.get("uid"));
            String orderSn = ValueUtil.getStrValue(par.get("orderSn"));
            Integer score = Integer.parseInt(ValueUtil.getStrValue(par.get("score")));
            //设置uid
            npsAttendRequest.setUid(uid);
            //设置订单号
            npsAttendRequest.setOrderSn(orderSn);
            //设置分数
            npsAttendRequest.setScore(score);
            String npsJson = npsService.saveNpsAttend(JsonEntityTransform.Object2Json(npsAttendRequest));
            DataTransferObject npsDto = JsonEntityTransform.json2DataTransferObject(npsJson);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(npsDto), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(logger, "e={}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("保存失败");
            return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
        }
    }

}
