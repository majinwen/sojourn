package com.ziroom.minsu.api.house.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.abs.AbstractController;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.common.header.Header;
import com.ziroom.minsu.api.house.service.HouseService;
import com.ziroom.minsu.api.house.valueenum.RulesEnum;
import com.ziroom.minsu.api.order.dto.RulesRequest;
import com.ziroom.minsu.api.order.dto.RulesResponse;
import com.ziroom.minsu.entity.house.HouseDescEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.house.api.inner.TenantHouseService;
import com.ziroom.minsu.services.house.dto.HouseCheckDto;
import com.ziroom.minsu.services.house.entity.TenantHouseDetailVo;
import com.ziroom.minsu.valenum.house.HouseSourceEnum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum0020;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005005001Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005005Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/12/6 15:17
 * @version 1.0
 * @since 1.0
 */
@RequestMapping("/rules")
@Controller
public class RulesController extends AbstractController{

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(RulesController.class);

    @Resource(name = "api.houseService")
    private HouseService houseService;

    @Resource(name = "house.tenantHouseService")
    private TenantHouseService tenantHouseService;
    

	@Resource(name="basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;


    /**
     * 根据规则code，获取规则模板
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2016/12/6 15:24
     */
    @RequestMapping(value = "/${UNLOGIN_AUTH}/showRules", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> showRules(HttpServletRequest request) {
        try {
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(LOGGER, "params:{}", paramJson);
            
            Header header = getHeader(request);

            RulesRequest rulesRequest = JsonEntityTransform.json2Object(paramJson, RulesRequest.class);
            if (Check.NuNObj(rulesRequest) || Check.NuNObj(rulesRequest.getCode())) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数错误"), HttpStatus.OK);
            }
            RulesEnum rulesEnum = RulesEnum.getEnumByCode(rulesRequest.getCode());
            if (Check.NuNObj(rulesEnum)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数错误"), HttpStatus.OK);
            }

            String content = "";
            
            
            if (rulesEnum == RulesEnum.RETURN_STRICT) {
                content = this.getCheckOutContent(TradeRulesEnum005Enum.TradeRulesEnum005001.getValue());
            } else if (rulesEnum == RulesEnum.RETURN_MEDIUM) {
                content = this.getCheckOutContent(TradeRulesEnum005Enum.TradeRulesEnum005002.getValue());
            } else if (rulesEnum == RulesEnum.RETURN_FLEXIBLE) {
                content = this.getCheckOutContent(TradeRulesEnum005Enum.TradeRulesEnum005003.getValue());
            } else if (rulesEnum == RulesEnum.RETURN_LONG) {
                content = this.getLongCheckOutContent(TradeRulesEnum005Enum.TradeRulesEnum005004.getValue());
            } else if (rulesEnum == RulesEnum.HOUSE_RULES) {
                content = this.getHouseDesc(rulesRequest);
            } else {
                content = rulesEnum.getContent();
                
            }

            RulesResponse rulesResponse = new RulesResponse();
            rulesResponse.setTitle(rulesEnum.getTitle());
            
			content = content.replaceAll("</br>", "\n\n");
			
			if(!Check.NuNObj(header) && !Check.NuNStr(header.getOsType()) && String.valueOf(HouseSourceEnum.IOS.getCode()).equals(header.getOsType().trim())){
				content = content.replaceAll("\n\n", "\n");
			}
			
            rulesResponse.setContent(content);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(rulesResponse), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "e:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }
    }


    /**
     * 获取退订政策文案
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2016/12/7 11:33
     */
    private String getCheckOutContent(String rulesEnum) {
        TradeRulesVo tradeRulesVo = houseService.getTradeRulesCommon(rulesEnum);
        if (Check.NuNObj(tradeRulesVo)) {
            return "";
        }
        StringBuilder content = new StringBuilder();
        if(!Check.NuNObj(tradeRulesVo.getCheckInPreNameM())){
            content.append(tradeRulesVo.getCheckInPreNameM());
            content.append("\n\n");
        }
        if(!Check.NuNObj(tradeRulesVo.getCheckInOnNameM())){
            content.append(tradeRulesVo.getCheckInOnNameM());
            content.append("\n\n");
        }
        if(!Check.NuNObj(tradeRulesVo.getCheckOutEarlyNameM())){
            content.append(tradeRulesVo.getCheckOutEarlyNameM());
            content.append("\n\n");
        }
        if(!Check.NuNObj(tradeRulesVo.getCommonShowName())){
            content.append(tradeRulesVo.getCommonShowName());
            content.append("\n\n");
        }
        if(!Check.NuNObj(tradeRulesVo.getExplain())){
            content.append(tradeRulesVo.getExplain());
        }
        return content.toString();
    }


    /**
     * 获取长租退订政策文案
     *
     * @author lishaochuan
     * @create 2016/12/8 16:55
     * @param 
     * @return 
     */
    private String getLongCheckOutContent(String rulesEnum) {
        TradeRulesVo tradeRulesVo = houseService.getTradeRulesCommon(rulesEnum);
        if (Check.NuNObj(tradeRulesVo)) {
            return "";
        }
        String string =tradeRulesVo.getCommonShowName(); 
        
        if(!Check.NuNObj(tradeRulesVo.getExplain())){
        	string=string+"\n\n"+tradeRulesVo.getExplain();
        }
        return string;
    }


    /**
     * 获取房源描述
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2016/12/7 15:26
     */
    private String getHouseDesc(RulesRequest rulesRequest) {
        try {
            HouseCheckDto request = new HouseCheckDto();
            request.setFid(rulesRequest.getFid());
            request.setRentWay(rulesRequest.getRentWay());
            String resultJson = tenantHouseService.findHoseDesc(JsonEntityTransform.Object2Json(request));
            LogUtil.info(LOGGER, "房屋守则返回值:{}",resultJson);
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if (dto.getCode() == DataTransferObject.SUCCESS) {
                HouseDescEntity houseDesc = SOAResParseUtil.getValueFromDataByKey(resultJson, "houseDesc", HouseDescEntity.class);
                return Check.NuNObj(houseDesc)||Check.NuNStrStrict(houseDesc.getHouseRules())?"这是房东心爱的美居，入住时请爱惜房屋，保持卫生整洁。":houseDesc.getHouseRules();
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
        }
        return "";
    }

}
