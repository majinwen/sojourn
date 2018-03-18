package com.ziroom.minsu.report.board.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.CityRegionRelEntity;
import com.ziroom.minsu.report.basedata.service.ConfCityService;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityRegionService;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 大区信息
 * </p>
 * <p/>
 *
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
@RequestMapping("/cityRegion")
public class CityRegionController {

    @Resource(name="basedata.cityRegionService")
    private CityRegionService cityRegionService;

    @Resource(name = "report.confCityService")
    private ConfCityService confCityService;
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CityRegionController.class);

    /**
     * 保存大区信息
     * @author jixd
     * @created 2017年01月10日 11:43:53
     * @param
     * @return
     */
    @RequestMapping("/saveCityRegion")
    @ResponseBody
    private String saveCityRegion(String regionName){
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(regionName)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("大区名字为空");
            return dto.toJsonString();
        }
        return cityRegionService.insertCityRegion(regionName);
    }


    /**
     * 删除大区
     * @author jixd
     * @created 2017年01月10日 12:00:36
     * @param
     * @return
     */
    @RequestMapping("/delCityRegion")
    @ResponseBody
    public String delCityRegion(String fid){
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(fid)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        return cityRegionService.delCityRegion(fid);
    }


    @RequestMapping("/list")
    @ResponseBody
    public String findAllRegion(){
        return cityRegionService.fillAllRegion();
    }

    /**
     * 绑定大区关系
     * @author jixd
     * @created 2017年01月10日 12:03:46
     * @param
     * @return
     */
    @RequestMapping("/bindRegionRel")
    @ResponseBody
    public String bindRegion(String regionFid,String code){
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(regionFid) || Check.NuNStr(code)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        CityRegionRelEntity cityRegionRelEntity = new CityRegionRelEntity();
        cityRegionRelEntity.setRegionFid(regionFid);
        cityRegionRelEntity.setProvinceCode(code);
        cityRegionRelEntity.setCountryCode("100000");
        return cityRegionService.insertCityRegionRel(JsonEntityTransform.Object2Json(cityRegionRelEntity));
    }

    /**
     * 删除大区关系
     * @author jixd
     * @created 2017年01月10日 13:54:56
     * @param
     * @return
     */
    @RequestMapping("/delRegionRel")
    @ResponseBody
    public String delRegionRel(String regionFid,String code){
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(regionFid) || Check.NuNStr(code)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        CityRegionRelEntity cityRegionRelEntity = new CityRegionRelEntity();
        cityRegionRelEntity.setRegionFid(regionFid);
        cityRegionRelEntity.setProvinceCode(code);
        cityRegionRelEntity.setIsDel(IsDelEnum.DEL.getCode());
        return cityRegionService.updateCityRegionRel(JsonEntityTransform.Object2Json(cityRegionRelEntity));
    }

    /**
     *
     * @author jixd
     * @created 2017年01月10日 16:53:53
     * @param
     * @return
     */
    @RequestMapping("/provinceList")
    @ResponseBody
    public DataTransferObject provinceList(){
        DataTransferObject dto = new DataTransferObject();
        try{
            //中国
            List<ConfCityEntity> list = confCityService.getListByPcode("100000");
            JSONArray pArray = wrapCityList(list);
            dto.putValue("array",pArray);
        }catch (Exception e){
            LogUtil.error(LOGGER,"获取省份列表异常e={}",e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务异常");
        }
        return dto;
    }

    /**
     * 获取关联城市
     * @author jixd
     * @created 2017年01月10日 17:53:37
     * @param
     * @return
     */
    @RequestMapping("/cityRegionRel")
    @ResponseBody
    public DataTransferObject cityRegionRel(String regionFid){
        DataTransferObject dto = new DataTransferObject();
        try{
            List<ConfCityEntity> regionRelList = confCityService.getRegionRelList(regionFid);
            if (Check.NuNCollection(regionRelList)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("结果为空");
                return dto;
            }
            JSONArray pArray = wrapCityList(regionRelList);
            dto.putValue("array",pArray);
        }catch (Exception e){
            LogUtil.error(LOGGER,"获取省份列表异常e={}",e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务异常");
        }
        return dto;
    }

    /**
     * 包装一次符合树状结构
     * @author jixd
     * @created 2017年01月10日 18:07:01
     * @param
     * @return
     */
    private JSONArray wrapCityList(List<ConfCityEntity> list) {
        JSONArray pArray = new JSONArray();
        JSONObject pObj = new JSONObject();
        pObj.put("text","中国");
        pObj.put("href","100000");
        JSONArray sArray = new JSONArray();
        if (!Check.NuNCollection(list)){
            for (ConfCityEntity confCityEntity : list){
                JSONObject object = new JSONObject();
                if (Check.NuNObj(confCityEntity)){
                    continue;
                }
                object.put("text",confCityEntity.getShowName());
                object.put("href",confCityEntity.getCode());
                sArray.add(object);
            }
        }
        pObj.put("nodes",sArray);
        pArray.add(pObj);
        return pArray;
    }




}
