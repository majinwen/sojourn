package com.ziroom.minsu.api.search.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.search.common.header.Header;
import com.ziroom.minsu.api.search.controller.abs.AbstractController;
import com.ziroom.minsu.api.search.vo.SensitiveVo;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * <p>对外接口</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/8.
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/external")
public class ExternalController extends AbstractController{


    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalController.class);



    /**
     * 搜索的api
     */
    @Resource(name = "search.searchServiceApi")
    private SearchService searchService;


    /**
     * 校验当前是否包含敏感词
     * @author afi
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value ="/sensitive")
    public @ResponseBody
    DataTransferObject sensitive(HttpServletRequest request, HttpServletResponse response) {

        Header header = getHeader(request);
        if(Check.NuNObj(header)){
            LogUtil.debug(LOGGER,"query head:{}", JsonEntityTransform.Object2Json(header));
        }
        DataTransferObject dto = null;
        SensitiveVo houseInfo = getEntity(request,SensitiveVo.class);
        if (Check.NuNObj(houseInfo)) {
        	LogUtil.info(LOGGER, "校验敏感词[参数为空]");
        	dto =  new DataTransferObject();
        	dto.putValue("ikList", new ArrayList<>());
            dto.putValue("isSensitive", YesOrNoEnum.NO.getCode());
            return dto;
		}
        
        String jsonRst = null;
        try {
            //获取用户的联想词
            jsonRst = searchService.getChangzuIkList(ValueUtil.getStrValue(houseInfo.getContent()));
            dto = JsonEntityTransform.json2DataTransferObject(jsonRst);
        }catch (Exception e){
            dto =  new DataTransferObject();
            LogUtil.error(LOGGER, "suggest par param:{} and e:{}", JsonEntityTransform.Object2Json(houseInfo),e);
            dto.putValue("ikList", new ArrayList<>());
            dto.putValue("isSensitive", YesOrNoEnum.NO.getCode());
            return dto;
        }
        return dto;
    }


}
