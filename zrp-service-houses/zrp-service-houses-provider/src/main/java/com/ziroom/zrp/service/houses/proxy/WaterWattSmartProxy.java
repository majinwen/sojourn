package com.ziroom.zrp.service.houses.proxy;

import com.alibaba.fastjson.JSON;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.service.houses.api.WaterWattSmartService;
import com.ziroom.zrp.service.houses.dto.WaterWattPagingDto;
import com.ziroom.zrp.service.houses.service.WaterWattSmartServiceImpl;
import com.ziroom.zrp.service.houses.valenum.MeterTypeEnum;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>智能水电代理层</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author phil
 * @Date Created in 2018年02月04日 19:29
 * @Version 1.0
 * @Since 1.0
 */
@Slf4j
@Component("houses.waterWattSmartProxy")
public class WaterWattSmartProxy implements WaterWattSmartService {

    @Resource(name = "houses.waterWattSmartServiceImpl")
    private WaterWattSmartServiceImpl waterWattSmartService;

    /**
     * 智能水电分页接口
     *
     * @param paramJson
     * @return
     */
    @Override
    public String pagingWaterWatt(@NonNull String paramJson) {

        LogUtil.info(log,"【pagingWaterWatt】参数={}", paramJson);

        DataTransferObject dto = new DataTransferObject();

        WaterWattPagingDto waterWattPagingDto = JsonEntityTransform.json2Entity(paramJson, WaterWattPagingDto.class);

        if(Check.NuNObj(waterWattPagingDto) ||
                Check.NuNObj(waterWattPagingDto.getDeviceType())){

            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数错误");
            return dto.toJsonString();
        }

        PagingResult pagingResult = new PagingResult();

        if (MeterTypeEnum.ELECTRICITY.getCode() == waterWattPagingDto.getDeviceType()) {
            try {
                pagingResult = waterWattSmartService.pagingWatt(waterWattPagingDto);
            } catch (Exception e) {
                log.error("[pagingWaterWatt.pagingWatt] fail param : [{}]", JSON.toJSONString(waterWattPagingDto));
                log.error(e.getMessage(), e);
            }
        } else if (MeterTypeEnum.WATER.getCode() == waterWattPagingDto.getDeviceType()) {
            try {
                pagingResult = waterWattSmartService.pagingWater(waterWattPagingDto);
            } catch (Exception e) {
                log.error("[pagingWaterWatt.pagingWatt] fail param : [{}]", JSON.toJSONString(waterWattPagingDto));
                log.error(e.getMessage(), e);
            }
        } else {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("未知设备类型, 请重试");
            return dto.toJsonString();
        }

        dto.putValue("list", pagingResult.getRows());
        dto.putValue("total", pagingResult.getTotal());

        return dto.toJsonString();
    }
}
