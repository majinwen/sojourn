package com.ziroom.minsu.api.search.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.search.common.header.Header;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import com.ziroom.minsu.services.search.dto.LandHouseRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/14.
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
    /**
     * 搜索的api
     */
    @Resource(name = "search.searchServiceApi")
    private SearchService searchService;


    /**
     * 获取模板列表
     * @param request
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public void toResourceList(HttpServletRequest request) {

    }


    /**
     * 房源的搜索
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value ="/test")
    public @ResponseBody
    DataTransferObject test(HttpServletRequest request,HttpServletResponse response,String par) {
        DataTransferObject dto = new DataTransferObject();
        String jsonRst = "";
        try {
            //获取搜索结果
            jsonRst =searchService.getIkList(par);
            dto = JsonEntityTransform.json2DataTransferObject(jsonRst);
        } catch (Exception e) {
            e.printStackTrace();
            return dto;
        }
        LogUtil.debug(LOGGER,"test.",jsonRst);
        return dto;
    }

}
