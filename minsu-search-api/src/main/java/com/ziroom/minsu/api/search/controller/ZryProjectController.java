package com.ziroom.minsu.api.search.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.search.common.header.Header;
import com.ziroom.minsu.api.search.common.log.user.QueryLog;
import com.ziroom.minsu.api.search.common.log.vo.TraceInfoVo;
import com.ziroom.minsu.api.search.controller.abs.AbstractController;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.search.api.inner.ZrySearchService;
import com.ziroom.minsu.services.search.dto.HouseInfoRequest;
import com.ziroom.minsu.valenum.search.SearchSourceTypeEnum;

@Controller
@RequestMapping("/zry")
public class ZryProjectController extends AbstractController{

    private static final Logger LOGGER = LoggerFactory.getLogger(ZryProjectController.class);
    
    /**
     * 用于记录搜索的记录
     */
    private static final Logger record = LoggerFactory.getLogger(QueryLog.class);

    /**
     * 搜索的api
     */
    @Resource(name = "search.zrySearchService")
    private ZrySearchService searchService;
    
    @Resource(name="searchApi.messageSource")
    private MessageSource messageSource;
    
    /**
     * 
     * 搜索自如驿
     *
     * @author zhangyl
     * @created 2017年7月28日 下午3:17:58
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value ="/query")
    public @ResponseBody DataTransferObject query(HttpServletRequest request,HttpServletResponse response) {

		DataTransferObject dto = null;
		
		Header header = getHeader(request);

		HouseInfoRequest houseInfo = getEntity(request, HouseInfoRequest.class);
		if (Check.NuNObj(houseInfo)) {
			dto = new DataTransferObject();
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto;
		}

		try {
			if (!Check.NuNObj(header) && !Check.NuNStr(header.getVersionCode())) {
				houseInfo.setVersionCode(ValueUtil.getintValue(header.getVersionCode()));
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "zry 版本号转化错误，versionCode={},e={}", header.getVersionCode(), e);
		}

		LogUtil.info(LOGGER, "zry query par:{}", JsonEntityTransform.Object2Json(houseInfo));

		// 参数校验
		if (!Check.NuNStr(houseInfo.getStartTime())) {
			try {
				Date startTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(houseInfo.getStartTime()), "yyyy-MM-dd");
				Date now = DateUtil.parseDate(DateUtil.dateFormat(new Date()), "yyyy-MM-dd");
				if (startTime.before(now)) {
					dto = new DataTransferObject();
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("只能选择今天及以后的日期");
					return dto;
				}
			} catch (Exception e) {
				LogUtil.error(LOGGER, "zry query par:{},e:{}", JsonEntityTransform.Object2Json(houseInfo), e);
				dto = new DataTransferObject();
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto;
			}
		}

		// 参数校验
		if (!Check.NuNStr(houseInfo.getStartTime()) && !Check.NuNStr(houseInfo.getEndTime())) {
			try {
				Date startTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(houseInfo.getStartTime()), "yyyy-MM-dd");
				Date endTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(houseInfo.getEndTime()), "yyyy-MM-dd");
				if (startTime.after(endTime)) {
					dto = new DataTransferObject();
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(" startTime is after endTime");
					return dto;
				}
			} catch (Exception e) {
				LogUtil.error(LOGGER, "zry query par:{},e:{}", JsonEntityTransform.Object2Json(houseInfo), e);
				dto = new DataTransferObject();
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto;
			}
		}

		if (Check.NuNStr(houseInfo.getQ())) {
			houseInfo.setQ("*:*");
		}

		houseInfo.setSearchSourceTypeEnum(SearchSourceTypeEnum.search_list);

		String jsonRst = null;

		TraceInfoVo traceInfo = new TraceInfoVo();
		traceInfo.setHeader(header);
		traceInfo.setRequest(houseInfo);
		Long start = System.currentTimeMillis();
        
        try {
        	
			String par = JsonEntityTransform.Object2Json(houseInfo);
			// 获取搜索结果
			long searchBegin = new Date().getTime();
			jsonRst = searchService.getHouseListByConditionAndRecommend(par);
			LogUtil.info(LOGGER, "zry 搜索结束，共耗时{}ms", (new Date().getTime() - searchBegin));
			dto = JsonEntityTransform.json2DataTransferObject(jsonRst);
        } catch (Exception e) {
            traceInfo.setCode(-1);
            traceInfo.setMsg("调用服务异常");
            Long end = System.currentTimeMillis();
            traceInfo.setCost(end-start);
            dto = new DataTransferObject();
            LogUtil.error(record,"zry traceinfo:{}",JsonEntityTransform.Object2Json(traceInfo));
            LogUtil.error(LOGGER, "zry query par :{} e:{}", JsonEntityTransform.Object2Json(houseInfo), e);
            return dto;
        }
        Long end = System.currentTimeMillis();
        traceInfo.setCost(end-start);
        traceInfo.setCode(dto.getCode());
        traceInfo.setMsg(dto.getMsg());
        if (dto.getCode() == DataTransferObject.SUCCESS){
            traceInfo.setTotal(ValueUtil.getintValue(dto.getData().get("total")));
        }
        LogUtil.info(record,"zry traceinfo:{}",JsonEntityTransform.Object2Json(traceInfo));

        LogUtil.debug(LOGGER,"zry query result {}.",jsonRst);
        return dto;
    }
    
    
}
