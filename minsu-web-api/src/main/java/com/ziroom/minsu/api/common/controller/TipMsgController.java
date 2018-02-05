package com.ziroom.minsu.api.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.services.basedata.api.inner.StaticResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>公用提示信息接口</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年06月21日 13:33
 * @since 1.0
 */
@Controller
@RequestMapping("/tipMsg")
public class TipMsgController {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TipMsgController.class);

    @Resource(name="basedata.staticResourceService")
    private StaticResourceService staticResourceService;

    /**
     * 根据code查询提示信息
     * @author jixd
     * @created 2017年06月21日 13:47:14
     * @param request
     * @return
     */
    @RequestMapping("/${UNLOGIN_AUTH}/showMsgByCode")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> showMsgByCode(HttpServletRequest request){
        String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
        LogUtil.info(LOGGER,"【TipMsgController.showMsgByCode】参数={}",paramJson);
        JSONObject paramObject = JSONObject.parseObject(paramJson);
        String code = paramObject.getString("code");
        if (Check.NuNStr(code)){
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("编码为空"), HttpStatus.OK);
        }
        String resultJson = staticResourceService.getTipsMsgHasSubTitleByCode(code);
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
        LogUtil.info(LOGGER,"【TipMsgController.showMsgByCode】result={}",resultJson);
        return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(resultDto), HttpStatus.OK);

    }
}
