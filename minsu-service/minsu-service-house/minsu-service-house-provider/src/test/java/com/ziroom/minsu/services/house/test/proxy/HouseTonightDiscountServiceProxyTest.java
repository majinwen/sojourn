package com.ziroom.minsu.services.house.test.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.minsu.entity.house.TonightDiscountEntity;
import com.ziroom.minsu.services.common.utils.JsonTransform;
import com.ziroom.minsu.services.house.proxy.TonightDiscountServiceProxy;
import com.ziroom.minsu.services.house.test.base.BaseTest;

import org.junit.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by homelink on 2017/4/18.
 */
public class HouseTonightDiscountServiceProxyTest extends BaseTest {

    @Resource(name = "house.tonightDiscountServiceProxy")
    private TonightDiscountServiceProxy tonightDiscountServiceProxy;

    @Test
    public void findTonightDiscountByCondition() {
        TonightDiscountEntity TonightDiscountEntity = new TonightDiscountEntity();
        TonightDiscountEntity.setHouseFid("8a9e98b45bf22ca4015bf22ca4bd0002");
        Date date = new Date();
        try {
            date = DateUtil.parseDate("2017-05-10", "yyyy-MM-dd");
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        TonightDiscountEntity.setDiscountDate(date);
        String paramJson = JsonTransform.Object2Json(TonightDiscountEntity);
        String resultJson = tonightDiscountServiceProxy.findTonightDiscountByCondition(paramJson);
        System.out.println("resultJosn :" + resultJson);
    }

    @Test
    public void findTonightDiscountByRentway() {
        try {
            TonightDiscountEntity TonightDiscountEntity = new TonightDiscountEntity();
//            TonightDiscountEntity.setHouseFid("8a9e98b45bf591a6015bf591a6c70002");
            TonightDiscountEntity.setRoomFid("8a9084df556cd72c01556d1081070010");
            TonightDiscountEntity.setRentWay(1);
            String paramJson = JsonTransform.Object2Json(TonightDiscountEntity);
            String resultJson = tonightDiscountServiceProxy.findTonightDiscountByRentway(paramJson);
            System.out.println("findTonightDiscountByRentway : " + resultJson);
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            //判断调用状态
            if (dto.getCode() == DataTransferObject.SUCCESS) {
                TonightDiscountEntity tonightDiscountEntity = SOAResParseUtil.getValueFromDataByKey(resultJson, "data", TonightDiscountEntity.class);
                System.out.println("discount : " + tonightDiscountEntity.getDiscount());
                Map<String, Object> map = dto.getData();
                System.out.println("discount2 : " + map.get("data"));
            }
        } catch (SOAParseException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void findRemindLandlordUidTest(){
        int limit = 150;
        Map<String, Object> paramMap=new HashMap<>();
        paramMap.put("page", 1);
        paramMap.put("limit", limit);
        String lockTime=DateUtil.dateFormat(new Date(), "yyyy-MM-dd")+" 00:00:00";
        paramMap.put("lockTime", lockTime);
        List<String> houseFids=new ArrayList<String>();
        houseFids.add("8a9099775658507701566e0a68fe0939");
        houseFids.add("8a9099785655007301566e2aca11091f");
        houseFids.add("8a909978566e33fb01566e377caa0011");
        List<String> roomFids=new ArrayList<String>();
        roomFids.add("8a909977576250f0015764da1bd20408");
        roomFids.add("8a90997757bca9a70157bcb53a3c003b");
        roomFids.add("8a90997756fe38e70156ffc7401202b5");
        paramMap.put("houseFids", houseFids);
		paramMap.put("roomFids", roomFids);
		String landlordUidJson=tonightDiscountServiceProxy.findRemindLandlordUid(JsonEntityTransform.Object2Json(paramMap));
		System.err.println(landlordUidJson);
    }
}
