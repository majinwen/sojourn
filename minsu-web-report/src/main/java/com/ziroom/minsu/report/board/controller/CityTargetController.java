package com.ziroom.minsu.report.board.controller;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.report.basedata.service.ConfCityService;
import com.ziroom.minsu.report.board.dto.CityTargetRequest;
import com.ziroom.minsu.report.board.entity.CityTargetEntity;
import com.ziroom.minsu.report.board.entity.CityTargetLogEntity;
import com.ziroom.minsu.report.board.service.CityTargetService;
import com.ziroom.minsu.report.board.vo.CityTargetItem;
import com.ziroom.minsu.report.board.vo.RegionItem;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.common.util.UserUtil;
import com.ziroom.minsu.report.basedata.dto.CityRequest;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityRegionService;
import com.ziroom.minsu.services.basedata.entity.UpsUserVo;

import org.apache.commons.collections.map.HashedMap;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 城市目标
 * </p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd on 2017/1/9.
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/cityTarget")
public class CityTargetController {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CityTargetController.class);

    @Resource(name="basedata.cityRegionService")
    private CityRegionService cityRegionService;

    @Resource(name="report.cityTargetService")
    private CityTargetService cityTargetService;

    @Resource(name = "report.confCityService")
    ConfCityService confCityService;

    /**
     * 显示列表
     * @author jixd
     * @created 2017年01月11日 11:02:27
     * @param
     * @return
     */
    @RequestMapping("/showList")
    public String toTargetList(Model model){
        DataTransferObject resultJson = JsonEntityTransform.json2DataTransferObject(cityRegionService.fillAllRegion());
        List<CityRegionEntity> list = resultJson.parseData("list", new TypeReference<List<CityRegionEntity>>() {});
        model.addAttribute("regionList",list);
        List<Map<String,String>> cityMapList = this.getCityList();
        model.addAttribute("cityList", cityMapList);

        return "board/cityTargetList";
    }

    /**
     * 保存城市目标
     * @author jixd
     * @created 2017年01月12日 17:22:58
     * @param
     * @return
     */
    @RequestMapping("/saveOrUpdateCityTarget")
    @ResponseBody
    public String saveOrUpdateCityTarget(CityTargetEntity cityTargetEntity){
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(cityTargetEntity.getCityCode())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("城市code为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(cityTargetEntity.getCityName())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("城市名字为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(cityTargetEntity.getTargetMonth())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("目标月份为空");
            return dto.toJsonString();
        }
        if (Check.NuNObj(cityTargetEntity.getTargetRentNum()) && Check.NuNObj(cityTargetEntity.getTargetSelfHouseNum())&&
                Check.NuNObj(cityTargetEntity.getTargetRentNum())&&Check.NuNObj(cityTargetEntity.getTargetOrderNum())
                &&Check.NuNObj(cityTargetEntity.getTargetPushHouseNum())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("请至少填写一项");
            return dto.toJsonString();
        }
        try{
            int i = 0;
            if (Check.NuNStr(cityTargetEntity.getFid())){
                UpsUserVo userVo=UserUtil.getUpsUserMsg();
                cityTargetEntity.setCreateEmpCode(userVo.getEmployeeEntity().getEmpCode());
                cityTargetEntity.setCreateEmpName(userVo.getEmployeeEntity().getEmpName());
                i = cityTargetService.saveCityTarget(cityTargetEntity);
            }else{
                i = cityTargetService.updateCityTarget(cityTargetEntity);
            }
            if (i == 0){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("目标已存在");
                return dto.toJsonString();
            }
        }catch (Exception e){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务异常");
            return dto.toJsonString();
        }
        return dto.toJsonString();
    }

    /**
     * 显示城市目标详情
     * @author jixd
     * @created 2017年01月12日 18:12:50
     * @param
     * @return
     */
    @RequestMapping("/showCityTargetDetail")
    @ResponseBody
    public String showCityTargetDetail(String cityCode,String targetMonth){
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(cityCode)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("城市code为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(targetMonth)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("月份为空");
            return dto.toJsonString();
        }
        try{
            CityTargetRequest cityTargetRequest = new CityTargetRequest();
            cityTargetRequest.setCityCode(cityCode);
            cityTargetRequest.setTargetMonth(targetMonth);
            List<CityTargetEntity> targetEntityList = cityTargetService.findTargetCityEntityList(cityTargetRequest);
            if (Check.NuNCollection(targetEntityList)){
                return dto.toJsonString();
            }
            CityTargetEntity cityTargetEntity = targetEntityList.get(0);
            List<CityTargetLogEntity> logList = cityTargetService.findCityTargetLog(cityTargetEntity.getFid());
            dto.putValue("target",cityTargetEntity);
            dto.putValue("log",logList);
        }catch (Exception e){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务错误");
            return dto.toJsonString();
        }
        return dto.toJsonString();
    }

    /**
     * 数据显示
     * @author jixd
     * @created 2017年01月11日 11:02:51
     * @param
     * @return
     */
    @RequestMapping("/dataList")
    @ResponseBody
    public JSONObject dataList(CityTargetRequest cityTargetRequest){
        PagingResult<RegionItem> pagingResult = cityTargetService.groupByRegionFidList(cityTargetRequest);
        List<RegionItem> rows = pagingResult.getRows();
        List<CityTargetItem> list = new ArrayList<>();
        if (!Check.NuNCollection(rows)){
            for (RegionItem regionItem : rows){
                if (!Check.NuNObj(regionItem)){
                    cityTargetRequest.setRegionFid(regionItem.getRegionFid());
                    //查询大区的数据
                    List<CityTargetItem> targetCityList =  cityTargetService.findTargetCityList(cityTargetRequest);
                    //计算大区数据的总和
                    CityTargetItem regionTotalNumItem = calTotalNum(targetCityList,0);
                    List<CityTargetItem> listByGroup = getListByGroup(targetCityList);
                    listByGroup.add(0,regionTotalNumItem);
                    list.addAll(listByGroup);
                }
            }
        }

        JSONObject result = new JSONObject();
        Long size = pagingResult.getTotal();
        Long totalpages = 0L;
        if(!Check.NuNObj(size)){
            if(size % cityTargetRequest.getLimit() == 0){
                totalpages = size/cityTargetRequest.getLimit();
            }else {
                totalpages = size/cityTargetRequest.getLimit() + 1;
            }
        }
        result.put("totalpages", totalpages);
        result.put("currPage", cityTargetRequest.getPage());
        result.put("totalCount", 0);
        result.put("dataList", list);
        return result;
    }

    /**
     * 导出数据到excel
     * @Author lunan【lun14@ziroom.com】
     * @Date 2017/1/19 15:29
     */
    @RequestMapping("/allDataToExcel")
    @ResponseBody
    public void allDataToExcel(CityTargetRequest cityTargetRequest,HttpServletResponse response){
        try{
            DealExcelUtil dealExcelUtil = new DealExcelUtil(null,null,null,"city_data_list"+cityTargetRequest.getPage());
            PagingResult<RegionItem> pagingResult = cityTargetService.groupByRegionFidList(cityTargetRequest);
            List<RegionItem> rows = pagingResult.getRows();
            List<CityTargetItem> list = new ArrayList<>();
            if (!Check.NuNCollection(rows)){
                for (RegionItem regionItem : rows){
                    if (!Check.NuNObj(regionItem)){
                        cityTargetRequest.setRegionFid(regionItem.getRegionFid());
                        //查询大区的数据
                        List<CityTargetItem> targetCityList = cityTargetService.findTargetCityList(cityTargetRequest);
                        //计算大区数据的总和
                        CityTargetItem regionTotalNumItem = calTotalNum(targetCityList,0);
                        List<CityTargetItem> listByGroup = getListByGroup(targetCityList);
                        listByGroup.add(0,regionTotalNumItem);
                        list.addAll(listByGroup);
                    }
                }
            }
            List<BaseEntity> dataList = new ArrayList<>();
            dataList.addAll(list);
            //导出excel
            dealExcelUtil.exportExcelFile(response,dataList);
        }catch (Exception e){
            LogUtil.error(LOGGER,"错误信息:{}",e);
        }

    }

    /**
     * 对大区下面城市分组，计算后重新组合
     * @author jixd
     * @created 2017年01月11日 20:54:44
     * @param
     * @return
     */
    private List<CityTargetItem> getListByGroup(List<CityTargetItem> list){
        if (Check.NuNCollection(list)){
            return new ArrayList<>();
        }
        List<CityTargetItem> bigList = new ArrayList<>();
        Map<String,List<CityTargetItem>> map = new HashedMap();
        for (CityTargetItem item : list){
            if (map.containsKey(item.getCityCode())){
                List<CityTargetItem> targetList = map.get(item.getCityCode());
                targetList.add(item);
            }else{
                List<CityTargetItem> addList = new ArrayList<>();
                addList.add(item);
                map.put(item.getCityCode(),addList);
            }
        }
        for (Map.Entry<String, List<CityTargetItem>> entry : map.entrySet()) {
            List<CityTargetItem> tempList = entry.getValue();
            if (!Check.NuNCollection(tempList)){
                //城市没有填写过目标的也要显示，并且有设置项
                if (tempList.size() == 1){
                    CityTargetItem cityTargetItem = tempList.get(0);
                    if (Check.NuNObj(cityTargetItem.getTargetHouseNum()) && Check.NuNObj(cityTargetItem.getTargetOrderNum())
                            && Check.NuNObj(cityTargetItem.getTargetPushHouseNum()) && Check.NuNObj(cityTargetItem.getTargetRentNum())
                            && Check.NuNObj(cityTargetItem.getTargetSelfHouseNum())){
                        cityTargetItem.setIsSet(1);
                    }else{
                        CityTargetItem cityTotalNumItem = calTotalNum(tempList,1);
                        cityTotalNumItem.setIsSet(1);
                        tempList.add(0,cityTotalNumItem);
                    }
                }else{
                    CityTargetItem cityTotalNumItem = calTotalNum(tempList,1);
                    cityTotalNumItem.setIsSet(1);
                    tempList.add(0,cityTotalNumItem);
                }
                bigList.addAll(tempList);
            }
        }
        return bigList;
    }


    /**
     * 计算总数
     * @author jixd
     * @created 2017年01月11日 21:13:41
     * @param
     * @return
     */
    public CityTargetItem calTotalNum(List<CityTargetItem> list,int flag){
        CityTargetItem totalItem = new CityTargetItem();

        for (CityTargetItem cityItem : list){
            totalItem.setTargetHouseNum(integerAdd(totalItem.getTargetHouseNum(),cityItem.getTargetHouseNum()));
            totalItem.setTargetOrderNum(integerAdd(totalItem.getTargetOrderNum(),cityItem.getTargetOrderNum()));
            totalItem.setTargetPushHouseNum(integerAdd(totalItem.getTargetPushHouseNum(),cityItem.getTargetPushHouseNum()));
            totalItem.setTargetSelfHouseNum(integerAdd(totalItem.getTargetSelfHouseNum(),cityItem.getTargetSelfHouseNum()));
            totalItem.setTargetRentNum(integerAdd(totalItem.getTargetRentNum(),cityItem.getTargetRentNum()));
        }
        //设置 对应的属性
        CityTargetItem cityTargetItem = list.get(0);
        totalItem.setRegionFid(cityTargetItem.getRegionFid());
        totalItem.setRegionName(cityTargetItem.getRegionName());
        if (flag == 1){
            //设置大区 不设置城市
            totalItem.setCityCode(cityTargetItem.getCityCode());
            totalItem.setCityName(cityTargetItem.getCityName());
        }
        return totalItem;
    }

    private Integer integerAdd(Integer a,Integer b){
        if (Check.NuNObj(a)){
            a = 0;
        }
        if (Check.NuNObj(b)){
            b = 0;
        }
        return a+b;
    }


    private List<Map<String,String>> getCityList(){
        CityRequest cityRequest = new CityRequest();
        List<ConfCityEntity> cityList = confCityService.getOpenCity(cityRequest);
        List<Map<String,String>> cityMapList = new ArrayList<Map<String,String>>(cityList.size());
        Map<String,String> paramMap = new HashMap<String, String>(2);
        for(ConfCityEntity cEntity : cityList){
            paramMap = new HashMap<String, String>(2);
            paramMap.put("name", cEntity.getShowName());
            paramMap.put("code", cEntity.getCode());
            cityMapList.add(paramMap);
        }
        return cityMapList;
    }
}
