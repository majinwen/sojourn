package com.ziroom.minsu.api.search.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.search.common.header.Header;
import com.ziroom.minsu.api.search.controller.abs.AbstractController;
import com.ziroom.minsu.services.common.utils.StringUtils;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * <p>搜索的api层</p>
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
@RequestMapping("/searchSuggest")
public class SuggestController extends AbstractController{

    private static final Logger LOGGER = LoggerFactory.getLogger(SuggestController.class);

    /**
     * 搜索的api
     */
    @Resource(name = "search.searchServiceApi")
    private SearchService searchService;


    /**
     * 获取用户的联想词
     * @param request
     * @param response
     * @param cn 联想词
     * @param cc 城市code
     * @return
     */
    @RequestMapping(value ="/suggest")
    public @ResponseBody
    DataTransferObject suggest(HttpServletRequest request,HttpServletResponse response, String cn,String cc) {

        Header header = getHeader(request);
        if(Check.NuNObj(header)){
            LogUtil.debug(LOGGER,"query head:{}", JsonEntityTransform.Object2Json(header));
        }
        if(LOGGER.isDebugEnabled()){
            LogUtil.debug(LOGGER,"suggest par cn:{} .cc:{}",cn,cc);
        }

        DataTransferObject dto = null;
        String suggestName = StringUtils.removeInvalidChar(ValueUtil.getStrValue(cn));
        String cityCode = StringUtils.removeInvalidChar(ValueUtil.getStrValue(cc));
        if(Check.NuNObj(suggestName) || Check.NuNObj(cityCode)){
            dto =  new DataTransferObject();
            dto.putValue("list", new ArrayList<>());
            dto.putValue("total", 0);
            return dto;
        }
        String jsonRst = null;
        try {
            //获取用户的联想词
            jsonRst = searchService.getSuggestInfo(suggestName, cityCode);
            dto = JsonEntityTransform.json2DataTransferObject(jsonRst);
        }catch (Exception e){
            dto =  new DataTransferObject();
            LogUtil.error(LOGGER, "suggest par cn:{} .cc:{} and e:{}", cn, cc, e);
            dto.putValue("list", new ArrayList<>());
            dto.putValue("total", 0);
            return dto;
        }
        if(LOGGER.isDebugEnabled()){
            LogUtil.debug(LOGGER,"fix result {}.",jsonRst);
        }
        return dto;
    }


    /**
     * 获取房东的联想词
     * @param request
     * @param response
     * @param pre 联想词
     * @return
     */
    @RequestMapping(value ="/complate", produces = "application/json; charset=utf-8")
    public @ResponseBody
    String complate(HttpServletRequest request,HttpServletResponse response, String pre) {

        Header header = getHeader(request);
        if(LOGGER.isDebugEnabled() && Check.NuNObj(header)){
            LogUtil.debug(LOGGER,"query head:{}", JsonEntityTransform.Object2Json(header));
        }

        if(LOGGER.isDebugEnabled()){
            LogUtil.debug(LOGGER,"complate par pre:{}",pre);
        }
        DataTransferObject dto = new DataTransferObject();
        String preName = StringUtils.removeInvalidChar(ValueUtil.getStrValue(pre));
        if(Check.NuNObj(preName)){
            dto.putValue("comSet", new HashSet<>());
            return dto.toJsonString();
        }

        String jsonRst = null;
        try {
            //获取楼盘名称的联想
            jsonRst =searchService.getComplateTermsCommunityName(preName);
        }catch (Exception e){
            LogUtil.error(LOGGER, "complate par cn:{} .and e:{}", pre, e);
            dto.putValue("comSet", new HashSet<>());
            return dto.toJsonString();
        }
        if(LOGGER.isDebugEnabled()){
            LogUtil.debug(LOGGER,"fix result {}.",jsonRst);
        }
        return jsonRst;
    }

}
