package com.ziroom.minsu.troy.search.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.search.api.inner.CmsSearchService;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import com.ziroom.minsu.services.search.api.inner.ZrySearchService;
import com.ziroom.minsu.services.search.dto.HouseInfoRequest;
import com.ziroom.minsu.services.search.entity.HouseInfoEntity;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.valenum.search.SearchDataSourceTypeEnum;
import com.ziroom.minsu.valenum.search.SearchSourceTypeEnum;
import com.ziroom.minsu.valenum.search.SoreWeightEnum;
import com.ziroom.minsu.valenum.search.SortTypeEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>搜索的白板页面</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/28.
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/search/query")
public class SearchController {


    @Value("#{'${minsu.static.resource.url}'.trim()}")
    private String staticResourceUrl;


    @Resource(name = "search.searchServiceApi")
    private SearchService searchService;

    @Resource(name = "search.zrySearchService")
    private ZrySearchService zrySearchService;

    @Resource(name = "search.cmsSearchService")
    private CmsSearchService cmsSearchService;


    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(SearchController.class);


    /**
     * 获取查询页面
     * @param request
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void toResourceList(HttpServletRequest request) {
        Map<String, Object> weightEnumMap = new TreeMap<>();
        for(SoreWeightEnum weight : SoreWeightEnum.values()){
            weightEnumMap.put(weight.name(), weight.getName() + "-" + weight.getCode());
        }
        request.setAttribute("weightEnumMap", JsonEntityTransform.Object2Json(weightEnumMap));

        // 默认排序综合得分函数
        Map.Entry<String, Object> entry = SortTypeEnum.DEFAULT.getSortMap().entrySet().iterator().next();
        String totalScoreFuc = entry.getKey() + " " + entry.getValue();
        request.setAttribute("totalScoreFuc", totalScoreFuc);

        // 当前搜索排序方式
        request.setAttribute("sortList", SortTypeEnum.getSortList());
        request.setAttribute("staticResourceUrl", staticResourceUrl);
    }


    /**
     * 获取模板列表
     * @param request
     */
    @RequestMapping(value = "/fresh", method = RequestMethod.GET)
    public void fresh(HttpServletRequest request) {
        request.setAttribute("staticResourceUrl", staticResourceUrl);
    }

    /**
     * 获取模板列表
     * @param request
     */
    @RequestMapping(value = "/suggest", method = RequestMethod.GET)
    public void suggest(HttpServletRequest request) {
        request.setAttribute("staticResourceUrl", staticResourceUrl);
    }

    /**
     * 刷新单条房源信息
     * @param request
     * @return
     */
    @RequestMapping("/freshHouseIndex")
    public @ResponseBody
    Object freshHouseIndex(HttpServletRequest request,String houseFid) {
        return searchService.freshIndexByHouseFid(houseFid);
    }



    /**
     * 刷新单条房源信息
     * @param request
     * @return
     */
    @RequestMapping("/freshAreaCode")
    public @ResponseBody
    Object freshAreaCode(HttpServletRequest request,String areaCode) {
        return searchService.creatAllIndexByArea(areaCode);
    }


    /**
     * 刷新全量的房源信息
     * @param request
     * @return
     */
    @RequestMapping("/freshAll")
    public @ResponseBody
    Object freshAll(HttpServletRequest request,Integer dataSource,String projectId) {
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNObj(dataSource) || Check.NuNObj(SearchDataSourceTypeEnum.getByCode(dataSource))){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数错误");
            return dto;
        }

        if(dataSource==SearchDataSourceTypeEnum.minsu.getCode()){
            return searchService.creatAllIndex();
        }else if(dataSource==SearchDataSourceTypeEnum.ziruyi.getCode()){
            return zrySearchService.freshIndex(projectId);
        }else if(dataSource==SearchDataSourceTypeEnum.cms.getCode()){
            return cmsSearchService.freshIndex();
        }

        return dto;
    }


    /**
     * 获取模板列表
     * @param request
     * @return
     */
    @RequestMapping("/complate")
    public @ResponseBody
    Object complate(HttpServletRequest request,String complateName) {
        Map<String,Object> rst = new HashMap<>();
        return searchService.getComplateTermsCommunityName(complateName);
    }


    /**
     * 获取模板列表
     * @param request
     * @return
     */
    @RequestMapping("/suggestInfo")
    public @ResponseBody
    Object suggestInfo(HttpServletRequest request,String suggestName,String cityCode) {
        Map<String,Object> rst = new HashMap<>();
        return searchService.getSuggestInfo(suggestName,cityCode);
    }


    /**
     * 房源的搜索
     * 
     * @author zhangyl
     * @created 2017/9/12 18:46
     * @param 
     * @return 
     */
    @RequestMapping("/select")
    public @ResponseBody
    PageResult select(HouseInfoRequest houseInfoRequest) {

        Long start = new Date().getTime();

        if (Check.NuNObj(houseInfoRequest)) {
            houseInfoRequest = new HouseInfoRequest();
        }
        houseInfoRequest.setSearchSourceTypeEnum(SearchSourceTypeEnum.troy_search_list);
        String resultJson = searchService.getHouseListInfo(null, JsonEntityTransform.Object2Json(houseInfoRequest));

        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);

        List<HouseInfoEntity> houseInfoEntityList = resultDto.parseData("list", new TypeReference<List<HouseInfoEntity>>() {
        });

        //返回结果
        PageResult pageResult = new PageResult();
        pageResult.setRows(houseInfoEntityList);
        pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));

        Long end = new Date().getTime();
        LogUtil.info(logger, "troy搜索白板end -star:", end - start);
        return pageResult;
    }

}
