package com.ziroom.minsu.services.lianjia.service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.AsuraHttpClient;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.search.service.SpellIndexServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>同步链家的房源信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/29.
 * @version 1.0
 * @since 1.0
 */
@Service("search.syncHousesInfoServiceImpl")
public class SyncHousesInfoServiceImpl {


    @Resource(name = "search.spellIndexServiceImpl")
    private SpellIndexServiceImpl spellIndexService;


    private static final Logger LOGGER = LoggerFactory.getLogger(SyncHousesInfoServiceImpl.class);

    @Value("#{'${lianjian.sync.houses.url}'.trim()}")
    private String url;

    /**
     * 同步楼盘信息
     */
    public  void syncHousesInfoByCode(String cityCodeS){
        String [] cityCodes = cityCodeS.split("\\|");
        for (String cityCode : cityCodes){
            this.syncHousesInfo(cityCode);
        }
    }

    /**
     * 同步楼盘信息
     */
    public  void syncHousesInfo(String cityCode) {

        Map<String,String> map = new HashMap<>();
        map.put("cityCode", cityCode);
        map.put("size", "10");
        map.put("pageNum", "1");
        map.put("startDate", "2011-03-28");
        map.put("endDate", DateUtil.dateFormat(new Date()));
        AsuraHttpClient hc = AsuraHttpClient.getInstance();
        for (int i = 1; ; i++) { // 从第二页开始循环查询需要的数据
            map.put("pageNum", i + "");
            DataTransferObject dto = hc.post(url, map); // 读取资产接口
            if(!this.parsingResblockInfo(dto)){
                break;
            }
        }

    }

    /**
     * 解析楼盘信息数据
     * @param dto
     * @return
     */
    private  boolean parsingResblockInfo(DataTransferObject dto){
        boolean flag = false;
        if (200 == dto.getCode()) {
            String data = (String)dto.getData().get("data");
            Map returnValue = JsonEntityTransform.json2Map(data);
            if ("success".equals(returnValue.get("status"))){
                List<Map> list = (List<Map>)returnValue.get("data");
                spellIndexService.creatIndexFromSyncHousesInfo(list);
                flag = true;
            } else {
                LogUtil.error(LOGGER, "楼盘接口返回错误信息，返回值：" + dto.toString());
            }
        } else {
            LogUtil.error(LOGGER, "楼盘接口http异常，返回值：" + dto.toString());
        }
        return flag;
    }


}
