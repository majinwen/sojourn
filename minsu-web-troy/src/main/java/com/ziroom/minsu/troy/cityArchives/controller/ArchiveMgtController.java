package com.ziroom.minsu.troy.cityArchives.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.file.FileCityEntity;
import com.ziroom.minsu.entity.file.FileRegionEntity;
import com.ziroom.minsu.entity.file.FileRegionItemsEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityArchiveService;
import com.ziroom.minsu.services.basedata.dto.CityArchivesRequest;
import com.ziroom.minsu.services.basedata.entity.CityArchivesVo;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.troy.common.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User: lunan
 * Date: 2016/11/7
 */
@Controller()
@RequestMapping("archive/archiveMgt")
public class ArchiveMgtController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArchiveMgtController.class);

    @Resource(name="basedata.cityArchiveService")
    private CityArchiveService cityArchiveService;

    /**
     * 到添加城市档案页面
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/7 11:02
     */
    @RequestMapping("addCityArchive")
    public void addCityArchive(HttpServletRequest request,CityArchivesRequest cityRequest) throws SOAParseException {
        if(!Check.NuNObj(cityRequest.getShowName())){
            request.setAttribute("showName",cityRequest.getShowName().substring(0,cityRequest.getShowName().length()-1));
        }
        if(!Check.NuNObj(cityRequest.getCityCode())){
            request.setAttribute("cityCode",cityRequest.getCityCode());
            //根据cityCode查询商圈景点
            String regionJson = cityArchiveService.getRegionList(JsonEntityTransform.Object2Json(cityRequest));
            List<CityArchivesVo> regionList = SOAResParseUtil.getListValueFromDataByKey(regionJson, "regionList", CityArchivesVo.class);
            request.setAttribute("regionList",regionList);
            //根据cityCode查询这个城市已存在内容的景点商圈
            cityRequest.setIsFile(1);
            String existJson = cityArchiveService.getRegionList(JsonEntityTransform.Object2Json(cityRequest));
            List<CityArchivesVo> existRegionList = SOAResParseUtil.getListValueFromDataByKey(existJson, "regionList", CityArchivesVo.class);
            request.setAttribute("existRegionList",existRegionList);
            //根据cityCode查询景点商圈及下级推荐项
            String regionItems = cityArchiveService.getRegionItems(JsonEntityTransform.Object2Json(cityRequest));
            List<CityArchivesVo> itemList = SOAResParseUtil.getListValueFromDataByKey(regionItems, "regionItems", CityArchivesVo.class);
            request.setAttribute("itemList",itemList);
        }
        if(!Check.NuNObj(cityRequest.getFid())){
            String archive = cityArchiveService.getArchive(cityRequest.getFid());
            FileCityEntity fileCity = JsonEntityTransform.json2Object(archive, FileCityEntity.class);
            request.setAttribute("fileCity",fileCity);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("searchRegionList")
    @ResponseBody
    public DataTransferObject searchRegionList(HttpServletRequest request,String cityCode){
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNObjs(cityCode)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("未传入城市编码");
            return dto;
        }
        CityArchivesRequest cityRequest = new CityArchivesRequest();
        cityRequest.setCityCode(cityCode);
        cityRequest.setIsFile(0);
        try{
            //根据cityCode查询商圈景点
            String regionJson = cityArchiveService.getRegionList(JsonEntityTransform.Object2Json(cityRequest));
            List<CityArchivesVo> regionList = SOAResParseUtil.getListValueFromDataByKey(regionJson, "regionList", CityArchivesVo.class);
            dto.putValue("list",regionList);
        }catch (Exception e){
            dto.setErrCode(DataTransferObject.ERROR);
        }
        return dto;
    }

    /**
     * 查找一条景点商圈内容
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/10 11:17
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("searchRegionOne")
    @ResponseBody
    public DataTransferObject searchRegionOne(FileRegionEntity fileRegionEntity,HttpServletRequest request) throws SOAParseException {
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNObj(fileRegionEntity.getHotRegionFid())) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("未选择景点商圈");
            return dto;
        }
        String region = cityArchiveService.getRegion(JsonEntityTransform.Object2Json(fileRegionEntity));
        FileRegionEntity regionEntity = SOAResParseUtil.getValueFromDataByKey(region, "region", FileRegionEntity.class);
//        FileRegionEntity regionEntity = JsonEntityTransform.json2Object(region, FileRegionEntity.class);
        dto.putValue("region",regionEntity);
        return dto;
    }

    /**
     * 保存商圈景点(修改)
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/8 18:06
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("saveHotRegion")
    @ResponseBody
    public DataTransferObject saveHotRegion(HttpServletRequest request,CityArchivesRequest cityRequest){
        DataTransferObject dto = new DataTransferObject();
        if(!Check.NuNObj(cityRequest.getHotRegionFid())){
            if(Check.NuNObj(cityRequest.getRegionContentFid())){
                //获取当前登录人fid
                String createFid = UserUtil.getCurrentUserFid();
                cityRequest.setCreateFid(createFid);
            }
            //需要页面传入cityCode用于更新city表中的是否有档案状态
            String resultJson = cityArchiveService.saveOrUpRegion(JsonEntityTransform.Object2Json(cityRequest));
            dto = JsonEntityTransform.json2DataTransferObject(resultJson);
        }else{
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("请正确选择商圈景点");
        }
        return dto;
    }

    /**
     * 保存推荐项(修改)
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/8 18:06
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("saveRegionItem")
    @ResponseBody
    public DataTransferObject saveRegionItem(HttpServletRequest request,CityArchivesRequest cityRequest){
        DataTransferObject dto = new DataTransferObject();
        if(!Check.NuNObj(cityRequest.getHotRegionFid())){
            if(Check.NuNObj(cityRequest.getItemFid())){
                //获取当前登录人fid
                String createFid = UserUtil.getCurrentUserFid();
                cityRequest.setCreateFid(createFid);
            }
            String resultJson = cityArchiveService.saveOrUpItem(JsonEntityTransform.Object2Json(cityRequest));
            dto = JsonEntityTransform.json2DataTransferObject(resultJson);
        }else{
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("请正确选择商圈景点");
        }
        return dto;
    }

    /**
     * 保存城市档案添加(修改)
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/8 18:05
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("saveCityArchive")
    @ResponseBody
    public DataTransferObject saveCityArchive(HttpServletRequest request,CityArchivesRequest cityRequest){
        DataTransferObject dto = new DataTransferObject();
        if(!Check.NuNObj(cityRequest.getCityCode())){
            if(Check.NuNObj(cityRequest.getFid())){//第一插入城市档案才创建createFid
                String createFid = UserUtil.getCurrentUserFid();
                cityRequest.setCreateFid(createFid);
            }
            String resultJson = cityArchiveService.saveOrUpArchive(JsonEntityTransform.Object2Json(cityRequest));
        }else{
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("请选择一个城市");
        }
        return dto;

    }

    /**
     * 根据itemFid查询一条推荐项
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/11 15:58
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("getRegionItem")
    @ResponseBody
    public DataTransferObject getItem(HttpServletRequest request,String itemFid){
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNObj(itemFid)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("请选择一个推荐项进行修改");
        }else{
            String regionItem = cityArchiveService.getRegionItem(itemFid);
            FileRegionItemsEntity item = JsonEntityTransform.json2Object(regionItem,FileRegionItemsEntity.class);
            dto.putValue("item",item);
        }
        return dto;
    }

    /**
     * 到城市档案列表页
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/9 11:35
     */
    @RequestMapping("listCityArchive")
    public void listCityArchive(HttpServletRequest request){
        //新功能添加处
    }

    /**
     * 查询城市档案列表
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/8 14:18
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("showArchivesMsg")
    @ResponseBody
    public PageResult showArchivesMsg(HttpServletRequest request, CityArchivesRequest cityRequest){
        return showCommonCityList(cityRequest);
    }

    /**
     * 城市档案相关列表
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/8 14:19
     */
    private PageResult showCommonCityList(CityArchivesRequest cityRequest){
        try{
            String resultJson = cityArchiveService.getCityArchiveList(JsonEntityTransform.Object2Json(cityRequest));
            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
            // 判断调用状态
            if(resultDto.getCode() == DataTransferObject.ERROR){
                LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(cityRequest));
                return new PageResult();
            }
            PageResult pageResult = new PageResult();
            List<CityArchivesVo> cityArchives = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", CityArchivesVo.class);
            pageResult.setRows(cityArchives);
            pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
            return pageResult;
        }catch(Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            return new PageResult();
        }
    }
    
    /**
     * 
     * 城市档案-展示城市档案详情
     *
     * @author baiwei
     * @created 2017年4月12日 下午3:06:14
     *
     * @param request
     * @param cityRequest
     */
    @RequestMapping("cityArchiveDetail")
    public void cityArchiveDetail(HttpServletRequest request,CityArchivesRequest cityRequest){
    	request.setAttribute("showName", cityRequest.getShowName());
    	String archive = cityArchiveService.getArchive(cityRequest.getFid());
    	FileCityEntity fileCity = JsonEntityTransform.json2Object(archive, FileCityEntity.class);
    	request.setAttribute("fileCity", fileCity);
    }

}
