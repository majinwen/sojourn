package com.ziroom.minsu.report.house.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.report.basedata.dto.CityRequest;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.service.ConfCityService;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.house.dto.HouseGrapherRequest;
import com.ziroom.minsu.report.house.service.HouseGrapherService;
import com.ziroom.minsu.services.basedata.api.inner.CityRegionService;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>房源评价 报表</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author wangwentao on 2017/4/21.
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/houseGrapher")
public class HouseGrapherController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HouseGrapherController.class);

    @Resource(name = "report.houseGrapherService")
    HouseGrapherService houseGrapherService;

    @Resource(name = "report.confCityService")
    ConfCityService confCityService;

    @Resource(name = "basedata.cityRegionService")
    private CityRegionService cityRegionService;

    /**
     * 整租 当前房源评价统计页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toEntireHouseGrapher")
    public ModelAndView toEntireHouseOrderLifeCycle(HttpServletRequest request) {
        ModelAndView maView = new ModelAndView("/house/houseGrapher/entireHouseGrapher");
        //国家下拉列表
        List<Map<String, String>> nationList = this.getNationList();
        List<CityRegionEntity> regionList = this.getRegionList();
        List<Map<String, String>> cityMapList = this.getCityList();
        List<Map<String, Object>> houseStatusList = this.getHouseStatusList();

        maView.addObject("nationList", nationList);
        maView.addObject("regionList", regionList);
        maView.addObject("cityList", cityMapList);
        maView.addObject("houseStatusList", houseStatusList);
        return maView;
    }

    /**
     * 分租 当前房源状态统计页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toSubHouseGrapher")
    public ModelAndView toSubHouseOrderLifeCycle(HttpServletRequest request) {
        ModelAndView maView = new ModelAndView("/house/houseGrapher/subHouseGrapher");

        //国家下拉列表
        List<Map<String, String>> nationList = this.getNationList();
        List<CityRegionEntity> regionList = this.getRegionList();
        List<Map<String, String>> cityMapList = this.getCityList();
        List<Map<String, Object>> houseStatusList = this.getHouseStatusList();

        maView.addObject("nationList", nationList);
        maView.addObject("regionList", regionList);
        maView.addObject("cityList", cityMapList);
        maView.addObject("houseStatusList", houseStatusList);

        return maView;
    }


    /**
     * 当前房源状态统计页面
     *
     * @param paramRequest
     * @param request
     * @return
     */

    @RequestMapping("/dataList")
    @ResponseBody
    public PagingResult houseGrapher(HouseGrapherRequest paramRequest, HttpServletRequest request) {
        LogUtil.info(LOGGER, "houseGrapher param:{}", JsonEntityTransform.Object2Json(paramRequest));
        PagingResult result = new PagingResult();
        try {
            result = houseGrapherService.getPageInfo(paramRequest);
        } catch (Exception ex) {
            LogUtil.error(LOGGER, "houseGrapher param:{},error:{}", JsonEntityTransform.Object2Json(paramRequest), ex);
        }

        return result;
    }


    /**
     * 获取国家列表
     *
     * @return
     * @author liyingjie
     */
    private List<Map<String, String>> getNationList() {
        List<ConfCityEntity> nationsList = confCityService.getNations();
        List<Map<String, String>> nationMapList = new ArrayList<Map<String, String>>(nationsList.size());
        for (ConfCityEntity cEntity : nationsList) {
            Map<String, String> paramMap = new HashMap<>(2);
            paramMap.put("name", cEntity.getShowName());
            paramMap.put("code", cEntity.getCode());
            nationMapList.add(paramMap);
        }
        return nationMapList;
    }

    /**
     * 获取地区列表
     *
     * @return
     * @author liyingjie
     */
    private List<CityRegionEntity> getRegionList() {
        DataTransferObject resultJson = JsonEntityTransform.json2DataTransferObject(cityRegionService.fillAllRegion());
        List<CityRegionEntity> list = resultJson.parseData("list", new TypeReference<List<CityRegionEntity>>() {
        });
        return list;
    }

    /**
     * 获取城市列表
     *
     * @return
     * @author liyingjie
     */
    private List<Map<String, String>> getCityList() {
        CityRequest cityRequest = new CityRequest();
        List<ConfCityEntity> cityList = confCityService.getOpenCity(cityRequest);
        List<Map<String, String>> cityMapList = new ArrayList<Map<String, String>>(cityList.size());
        Map<String, String> paramMap = new HashMap<String, String>(2);
        for (ConfCityEntity cEntity : cityList) {
            paramMap = new HashMap<String, String>(2);
            paramMap.put("name", cEntity.getShowName());
            paramMap.put("code", cEntity.getCode());
            cityMapList.add(paramMap);
        }
        return cityMapList;
    }

    /**
     * 获取房源状态
     *
     * @return
     * @author liyingjie
     */
    private List<Map<String, Object>> getHouseStatusList() {
        List<Map<String, Object>> houseStatusMapList = new ArrayList<>();
        Map<Integer, String> EnumMap = HouseStatusEnum.getEnumMap();
        for (Map.Entry<Integer, String> entry : EnumMap.entrySet()) {
            Integer code = entry.getKey();
            String value = entry.getValue();
            Map<String, Object> temp = new HashMap<>();
            temp.put("code", code);
            temp.put("name", value);
            houseStatusMapList.add(temp);
        }
        return houseStatusMapList;
    }

    /**
     * 房源评价excel导出
     *
     * @param paramRequest
     * @param paramRequest
     * @return
     */
    @RequestMapping("/houseGrapherExcelList")
    public void houseGrapherExcelList(HouseGrapherRequest paramRequest, HttpServletResponse response) {
        DealExcelUtil test = new DealExcelUtil(houseGrapherService, paramRequest, null, "houseGrapher-oper");
        test.exportZipFile(response);
    }

}
