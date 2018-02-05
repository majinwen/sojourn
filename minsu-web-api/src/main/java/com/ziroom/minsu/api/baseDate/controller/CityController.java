package com.ziroom.minsu.api.baseDate.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.ApiConst;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>城市</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/5.
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/base")
public class CityController {


    @Resource(name="basedata.confCityService")
    private ConfCityService confCityService;
    
    @Resource(name = "basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CityController.class);


    public static void main(String[] args) {

    }


    /**
     * 城市列表 带热门车型的
     * @author afi
     * @param request
     * @return
     */
    @RequestMapping("/${NO_LGIN_AUTH}/cityListHot")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> cityListHot(HttpServletRequest request,HttpServletResponse response){
    	
		response.addHeader( "Access-Control-Allow-Origin", "*" ); // open your api to any client
		response.addHeader( "Access-Control-Allow-Methods", "POST" ); // a allow post
		response.addHeader( "Access-Control-Max-Age", "3000" ); // time from request to response before timeout
    	
        try{
            String resultJson =  confCityService.getOpenCityAndHot();
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            //判断调用状态
            if (dto.getCode() != DataTransferObject.SUCCESS) {
                return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(dto.getMsg()), HttpStatus.OK);
            }
            LogUtil.debug(LOGGER, "结果：" + resultJson);
            return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(dto.getData()), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("服务错误"), HttpStatus.OK);
        }
    }


    /**
     * 城市列表
     * @author afi
     * @param request
     * @return
     */
    @RequestMapping("/${NO_LGIN_AUTH}/cityList")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> cityList(HttpServletRequest request){
        try{
            String resultJson =  confCityService.getOpenCity();
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            //判断调用状态
            if (dto.getCode() != DataTransferObject.SUCCESS) {
                return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(dto.getMsg()), HttpStatus.OK);
            }
            LogUtil.debug(LOGGER, "结果：" + resultJson);
            return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(dto.getData()), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("服务错误"), HttpStatus.OK);
        }
    }
    
    /**
     * 搜索页面基础数据
     * 
     * @author zl
     * @param request
     * @return
     */
    @RequestMapping("/${NO_LGIN_AUTH}/initSearchBaseData")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> initSearchBaseData(HttpServletRequest request){
    	try{
    		
    		String cityCode = (String) request.getParameter("cityCode");//
			LogUtil.debug(LOGGER, "cityCode：" + cityCode);
    		if (!Check.NuNStr(cityCode)) {
				
			}
    		
            String houseTypeJson = cityTemplateService.getEffectiveSelectEnum(null, ProductRulesEnum.ProductRulesEnum001.getValue());
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseTypeJson);
            //判断调用状态
            if (dto.getCode() != DataTransferObject.SUCCESS) {
                return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(dto.getMsg()), HttpStatus.OK);
            }
            
            List<EnumVo> houseTypeList = SOAResParseUtil.getListValueFromDataByKey(houseTypeJson, "selectEnum", EnumVo.class);
            Map map = new HashMap();
            map.put("houseTypeList", houseTypeList);
            
            LogUtil.debug(LOGGER, "结果：" + JsonEntityTransform.Object2Json(map));
            return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(map), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("服务错误"), HttpStatus.OK);
        }
    }

}
