package com.ziroom.minsu.services.search.proxy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import com.ziroom.minsu.services.common.constant.SearchConstant;
import com.ziroom.minsu.services.search.service.SearchConditionServiceImpl;
import com.ziroom.minsu.services.search.vo.StaticResourceVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.search.api.inner.ZrySearchService;
import com.ziroom.minsu.services.search.dto.HouseInfoRequest;
import com.ziroom.minsu.services.search.entity.QueryResult;
import com.ziroom.minsu.services.search.service.FreshZryIndexServiceImpl;
import com.ziroom.minsu.services.search.service.SearchZryServiceImpl;
import com.ziroom.minsu.services.solr.constant.SolrConstant;

/**
 * <p>自如驿对外暴露的接口服务都走这里</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zl
 * @version 1.0
 * @Date Created in 2017年07月25日 15:04
 * @since 1.0
 */
@Component("search.zrySearchProxy")
public class ZrySearchProxy implements ZrySearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZrySearchProxy.class);

    @Resource(name = "search.messageSource")
    private MessageSource messageSource;

    @Value("#{'${default_pic_size}'.trim()}")
    private String defaultPicSize;

    @Resource(name = "search.freshZryIndexServiceImpl")
    private FreshZryIndexServiceImpl freshZryIndexService;

    @Resource(name = "search.searchZryServiceImpl")
    private SearchZryServiceImpl searchZryServiceImpl;

    @Resource(name = "search.searchConditionServiceImpl")
    SearchConditionServiceImpl searchConditionServiceImpl;
    
    @Override
    public String freshIndex(String projectBid) {
        return freshZryIndexService.syncDealSearchIndex(projectBid);
    }

    @Override
    public String getHouseListByConditionAndRecommend(String paramsJson) {
        DataTransferObject dto = new DataTransferObject();
        try {
            HouseInfoRequest houseInfoRequest = JsonEntityTransform.json2Object(paramsJson, HouseInfoRequest.class);
            
            dto = vilidateHouseListBaseParam(houseInfoRequest);
            
            if (dto.getCode() == DataTransferObject.ERROR) {
				return dto.toJsonString();
			}

            // 条件查询后台
            QueryResult getHouseListInfo = searchZryServiceImpl.searchHouseList(houseInfoRequest);

            if (getHouseListInfo.getTotal() == 0) {
            	HouseInfoRequest suggestRequest = new HouseInfoRequest();
				suggestRequest.setLimit(SolrConstant.suggest_page_limit);
                QueryResult suggest = searchZryServiceImpl.searchHouseList(suggestRequest);
                dto.putValue("suggest", suggest.getValue());
                dto.putValue("suggestMsg",getMsg(SearchConstant.StaticResourceCode.NO_MORE_TIPS_ZRY ));
            } else {
                dto.putValue("suggest", new ArrayList<>());
            }
            dto.putValue("list", getHouseListInfo.getValue());
            dto.putValue("total", getHouseListInfo.getTotal());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "getHouseListByConditionAndRecommend ,param={},e:{}", paramsJson, e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }

        return dto.toJsonString();
    }


    /**
     * 查询静态资源
     * @author zl
     * @created 2017/8/9 17:54
     * @param
     * @return
     */
    private String getMsg(String resCode ){

        try {
            StaticResourceVo resource=  searchConditionServiceImpl.getStaticResourceByResCode(resCode);
            if (!Check.NuNObj(resource)) return resource.getResContent();

        } catch (Exception e) {
            LogUtil.error(LOGGER, "查询静态资源异常 ,param={},e:{}", resCode, e);
        }

        return  "";
    }
    
    /**
     * 
     * 校验搜索参数
     *
     * @author zhangyl
     * @created 2017年8月1日 上午9:59:21
     *
     * @param houseInfoRequest
     * @return
     */
    private DataTransferObject vilidateHouseListBaseParam(HouseInfoRequest houseInfoRequest){
    	
    	DataTransferObject dto = new DataTransferObject();
    	
    	if(Check.NuNObj(houseInfoRequest)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto;
        }
    	
    	if(Check.NuNStr(houseInfoRequest.getPicSize())){
        	houseInfoRequest.setPicSize(defaultPicSize);
        }
    	
		if (!Check.NuNStr(houseInfoRequest.getStartTime()) && !Check.NuNStr(houseInfoRequest.getEndTime())) {
			try {
				Date startTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(houseInfoRequest.getStartTime()), "yyyy-MM-dd");
				Date endTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(houseInfoRequest.getEndTime()), "yyyy-MM-dd");
                if(startTime.after(endTime)){
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                    return dto;
                }
				
		        Calendar calendar = Calendar.getInstance();
		        Date now = calendar.getTime();
		        
		        // TODO 出租日历最长天数 暂定180天（自如驿是从第二天开始算）
                // 例：最远可预订两天，则今明后都可预订；在计算库存时DateSplitUtil.dateSplit(startDate, endDate)时只有今明。所以多加一天
		        int calendarMounthLimit = 181;
		        calendar.add(Calendar.DATE, calendarMounthLimit);
		        Date maxDate = calendar.getTime();
				
				if (endTime.after(maxDate)) { // 防止日期过长
					LogUtil.info(LOGGER, "搜索时间段过长，缩短处理，原始时间段：{}--{}", houseInfoRequest.getStartTime(), houseInfoRequest.getEndTime());
					endTime = maxDate;
					houseInfoRequest.setEndTime(DateUtil.dateFormat(endTime, "yyyy-MM-dd"));
				}

                if (startTime.after(endTime)) {
                    startTime = endTime;
                }

                if (startTime.before(now)) {
                    startTime = now;
                }

                houseInfoRequest.setStartTime(DateUtil.dateFormat(startTime, "yyyy-MM-dd"));

			} catch (Exception e) {
				LogUtil.error(LOGGER, "getHouseListByConditionAndRecommend 时间校验异常 ,param={},e:{}", JsonEntityTransform.Object2Json(houseInfoRequest), e);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto;
			}
		}
    	
        return dto;
    }
}
