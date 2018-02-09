package com.ziroom.minsu.troy.house.controller;


import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.house.airbnb.dto.AbHouseDto;
import com.ziroom.minsu.services.house.api.inner.AbHouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/*
 * <P>提供给Spider项目的api接口</P>
 * <P>
 * <PRE>
 * <BR> 修改记录
 * <BR>------------------------------------------
 * <BR> 修改日期       修改人        修改内容
 * </PRE>
 *
 * @Author lusp
 * @Date Create in 2017年08月 25日 10:25
 * @Version 1.0
 * @Since 1.0
 */
@Controller
@RequestMapping("airbnbRelate")
public class AirbnbRelateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirbnbRelateController.class);

    @Resource(name = "house.abHouseService")
    private AbHouseService abHouseService;


    /**
     * @description: 分页获取房源与第三方房源关系
     * @author: lusp
     * @date: 2017/8/25 10:28
     * @params: request
     * @return:
     */
    @RequestMapping("/getHouseRelate")
    @ResponseBody
    public DataTransferObject getHouseRelate(HttpServletRequest request){
        DataTransferObject dto = new DataTransferObject();
        try {
            String pageStr = request.getParameter("page");
            String limitStr = request.getParameter("limit");
            if(Check.NuNStrStrict(pageStr)||Check.NuNStrStrict(limitStr)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数错误");
                return dto;
            }
            int page = Integer.valueOf(request.getParameter("page"));
            int limit = Integer.valueOf(request.getParameter("limit"));
            AbHouseDto requestDto = new AbHouseDto();
            requestDto.setPage(page);
            requestDto.setLimit(limit);
            String listHouseRelateVoStr = abHouseService.listHouseRelateVoByPage(JsonEntityTransform.Object2Json(requestDto));
            dto = JsonEntityTransform.json2DataTransferObject(listHouseRelateVoStr);
            if(dto.getCode() == DataTransferObject.ERROR){
                LogUtil.error(LOGGER, "【getHouseRelate】分页获取房源对应关系失败",dto.getMsg());
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("系统异常");
                return dto;
            }
        }catch (Exception e){
            LogUtil.error(LOGGER,"【getHouseRelate】分页获取房源关系失败，param={},e={}",JsonEntityTransform.Object2Json(request.getAttribute("abHouseDto")),e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常");
            return dto;
        }
        return dto;
    }


}
