package test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yanb
 * @version 1.0
 * @Date Created in 2017年11月24日 13:24
 * @since 1.0
 */
public class JsonTest {

    public static void main(String[] args) {
        String result = "{\n" +
                "\t\"error_code\": 0,\n" +
                "\t\"error_message\": \"成功\",\n" +
                "\t\"status\": \"success\",\n" +
                "\t\"data\": {\n" +
                "\t\t\"numFound\": 4,\n" +
                "\t\t\"start\": 0,\n" +
                "\t\t\"content\": [{\n" +
                "\t\t\t\"id\": 104642,\n" +
                "\t\t\t\"houseId\": \"45289\",\n" +
                "\t\t\t\"houseCode\": \"1115033416544\",\n" +
                "\t\t\t\"houseSourceCode\": \"BJZRCP89914502_05\",\n" +
                "\t\t\t\"roomId\": \"290339\",\n" +
                "\t\t\t\"roomCode\": \"05\",\n" +
                "\t\t\t\"roomName\": \"南卧_05\",\n" +
                "\t\t\t\"villageId\": \"1111027382546\",\n" +
                "\t\t\t\"buildingId\": \"1112027447440\",\n" +
                "\t\t\t\"address\": \"昌平区紫金新干线19号楼3单元6层601\",\n" +
                "\t\t\t\"uid\": \"e4f66dbd-cdfe-696b-ca37-6b294fc159e1\",\n" +
                "\t\t\t\"contractCode\": \"BJCW81511170353\",\n" +
                "\t\t\t\"signDate\": \"2015-11-17 00:00:00\",\n" +
                "\t\t\t\"startDate\": \"2015-11-17 00:00:00\",\n" +
                "\t\t\t\"stopDate\": \"2016-11-16 00:00:00\",\n" +
                "\t\t\t\"houseType\": 1,\n" +
                "\t\t\t\"propertyState\": \"yqr\",\n" +
                "\t\t\t\"monthRentPrice\": 1860.00,\n" +
                "\t\t\t\"dayRentPrice\": 0.00,\n" +
                "\t\t\t\"deposit\": 1860.00,\n" +
                "\t\t\t\"originCommission\": 2009.00,\n" +
                "\t\t\t\"commission\": 2009.00,\n" +
                "\t\t\t\"paymentCycle\": 3,\n" +
                "\t\t\t\"tenancyType\": \"p1\",\n" +
                "\t\t\t\"cityCode\": \"110000\",\n" +
                "\t\t\t\"contractStrategy\": 1,\n" +
                "\t\t\t\"isShort\": false,\n" +
                "\t\t\t\"isRentback\": false,\n" +
                "\t\t\t\"isChangeSign\": false,\n" +
                "\t\t\t\"isBlank\": false,\n" +
                "\t\t\t\"isZwhite\": false,\n" +
                "\t\t\t\"reserveDeposit\": 0.00,\n" +
                "\t\t\t\"createTime\": \"2016-01-01 00:00:00\",\n" +
                "\t\t\t\"lastModifyTime\": \"2017-08-17 20:45:39\",\n" +
                "\t\t\t\"isResign\": false,\n" +
                "\t\t\t\"isMortgaged\": 0,\n" +
                "\t\t\t\"isJointRent\": 1\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"id\": 737106,\n" +
                "\t\t\t\"houseId\": \"45289\",\n" +
                "\t\t\t\"houseCode\": \"1115033416544\",\n" +
                "\t\t\t\"houseSourceCode\": \"BJZRCP89914502_05\",\n" +
                "\t\t\t\"roomId\": \"290339\",\n" +
                "\t\t\t\"roomCode\": \"05\",\n" +
                "\t\t\t\"roomName\": \"南卧_05\",\n" +
                "\t\t\t\"villageId\": \"1111027382546\",\n" +
                "\t\t\t\"buildingId\": \"1112027447440\",\n" +
                "\t\t\t\"address\": \"昌平区紫金新干线19号楼3单元6层601\",\n" +
                "\t\t\t\"uid\": \"e4f66dbd-cdfe-696b-ca37-6b294fc159e1\",\n" +
                "\t\t\t\"contractCode\": \"BJCW81511170353-01\",\n" +
                "\t\t\t\"signDate\": \"2016-10-11 00:00:00\",\n" +
                "\t\t\t\"startDate\": \"2016-11-17 00:00:00\",\n" +
                "\t\t\t\"stopDate\": \"2017-10-03 00:00:00\",\n" +
                "\t\t\t\"houseType\": 1,\n" +
                "\t\t\t\"contractState\": \"ytz\",\n" +
                "\t\t\t\"propertyState\": \"yqr\",\n" +
                "\t\t\t\"monthRentPrice\": 1860.00,\n" +
                "\t\t\t\"dayRentPrice\": 0.00,\n" +
                "\t\t\t\"deposit\": 1860.00,\n" +
                "\t\t\t\"originCommission\": 1238.00,\n" +
                "\t\t\t\"commission\": 1238.00,\n" +
                "\t\t\t\"paymentCycle\": 3,\n" +
                "\t\t\t\"tenancyType\": \"b6s12\",\n" +
                "\t\t\t\"cityCode\": \"110000\",\n" +
                "\t\t\t\"contractStrategy\": 1,\n" +
                "\t\t\t\"isShort\": false,\n" +
                "\t\t\t\"isRentback\": true,\n" +
                "\t\t\t\"isChangeSign\": false,\n" +
                "\t\t\t\"isBlank\": false,\n" +
                "\t\t\t\"isZwhite\": false,\n" +
                "\t\t\t\"reserveDeposit\": 0.00,\n" +
                "\t\t\t\"createTime\": \"2016-10-11 11:52:45\",\n" +
                "\t\t\t\"lastModifyTime\": \"2017-09-29 15:37:32\",\n" +
                "\t\t\t\"isResign\": true,\n" +
                "\t\t\t\"isMortgaged\": 0,\n" +
                "\t\t\t\"isJointRent\": 1\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"id\": 2977951,\n" +
                "\t\t\t\"houseId\": \"9685\",\n" +
                "\t\t\t\"houseCode\": \"1115032477392\",\n" +
                "\t\t\t\"houseSourceCode\": \"BJCP85568029_05\",\n" +
                "\t\t\t\"roomId\": \"77322\",\n" +
                "\t\t\t\"roomCode\": \"05\",\n" +
                "\t\t\t\"roomName\": \"南卧_05\",\n" +
                "\t\t\t\"villageId\": \"1111027380088\",\n" +
                "\t\t\t\"buildingId\": \"1112027442281\",\n" +
                "\t\t\t\"address\": \"昌平区回龙观田园风光雅苑18号楼五单元第4层402\",\n" +
                "\t\t\t\"uid\": \"e4f66dbd-cdfe-696b-ca37-6b294fc159e1\",\n" +
                "\t\t\t\"contractCode\": \"BJCW81304200017\",\n" +
                "\t\t\t\"signDate\": \"2013-04-20 00:00:00\",\n" +
                "\t\t\t\"startDate\": \"2013-04-20 00:00:00\",\n" +
                "\t\t\t\"stopDate\": \"2014-04-19 00:00:00\",\n" +
                "\t\t\t\"houseType\": 0,\n" +
                "\t\t\t\"contractState\": \"ydq\",\n" +
                "\t\t\t\"propertyState\": \"yqr\",\n" +
                "\t\t\t\"monthRentPrice\": 1560.00,\n" +
                "\t\t\t\"dayRentPrice\": 0.00,\n" +
                "\t\t\t\"deposit\": 1560.00,\n" +
                "\t\t\t\"originCommission\": 1872.00,\n" +
                "\t\t\t\"commission\": 1872.00,\n" +
                "\t\t\t\"paymentCycle\": 3,\n" +
                "\t\t\t\"tenancyType\": \"p1\",\n" +
                "\t\t\t\"cityCode\": \"110000\",\n" +
                "\t\t\t\"contractStrategy\": 3,\n" +
                "\t\t\t\"isShort\": false,\n" +
                "\t\t\t\"isRentback\": false,\n" +
                "\t\t\t\"isChangeSign\": false,\n" +
                "\t\t\t\"isBlank\": false,\n" +
                "\t\t\t\"isZwhite\": false,\n" +
                "\t\t\t\"reserveCode\": \"\",\n" +
                "\t\t\t\"createTime\": \"2017-02-14 16:18:08\",\n" +
                "\t\t\t\"lastModifyTime\": \"2017-02-14 16:18:09\",\n" +
                "\t\t\t\"isResign\": false,\n" +
                "\t\t\t\"isMortgaged\": 0,\n" +
                "\t\t\t\"isJointRent\": 1\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"id\": 3056781,\n" +
                "\t\t\t\"houseId\": \"9685\",\n" +
                "\t\t\t\"houseCode\": \"1115032477392\",\n" +
                "\t\t\t\"houseSourceCode\": \"BJCP85568029_05\",\n" +
                "\t\t\t\"roomId\": \"77322\",\n" +
                "\t\t\t\"roomCode\": \"05\",\n" +
                "\t\t\t\"roomName\": \"南卧_05\",\n" +
                "\t\t\t\"villageId\": \"1111027380088\",\n" +
                "\t\t\t\"buildingId\": \"1112027442281\",\n" +
                "\t\t\t\"address\": \"昌平区回龙观田园风光雅苑18号楼五单元第4层402\",\n" +
                "\t\t\t\"uid\": \"e4f66dbd-cdfe-696b-ca37-6b294fc159e1\",\n" +
                "\t\t\t\"contractCode\": \"BJCW81304200017-01\",\n" +
                "\t\t\t\"signDate\": \"2014-04-19 00:00:00\",\n" +
                "\t\t\t\"startDate\": \"2014-04-20 00:00:00\",\n" +
                "\t\t\t\"stopDate\": \"2015-04-19 00:00:00\",\n" +
                "\t\t\t\"houseType\": 0,\n" +
                "\t\t\t\"contractState\": \"ytz\",\n" +
                "\t\t\t\"propertyState\": \"yqr\",\n" +
                "\t\t\t\"monthRentPrice\": 0.00,\n" +
                "\t\t\t\"dayRentPrice\": 0.00,\n" +
                "\t\t\t\"deposit\": 0.00,\n" +
                "\t\t\t\"originCommission\": 0.00,\n" +
                "\t\t\t\"commission\": 0.00,\n" +
                "\t\t\t\"paymentCycle\": 3,\n" +
                "\t\t\t\"tenancyType\": \"p1\",\n" +
                "\t\t\t\"cityCode\": \"110000\",\n" +
                "\t\t\t\"contractStrategy\": 3,\n" +
                "\t\t\t\"isShort\": false,\n" +
                "\t\t\t\"isRentback\": false,\n" +
                "\t\t\t\"isChangeSign\": false,\n" +
                "\t\t\t\"isBlank\": false,\n" +
                "\t\t\t\"isZwhite\": false,\n" +
                "\t\t\t\"createTime\": \"2017-02-14 16:18:08\",\n" +
                "\t\t\t\"lastModifyTime\": \"2017-08-17 17:29:23\",\n" +
                "\t\t\t\"isResign\": false,\n" +
                "\t\t\t\"isMortgaged\": 0,\n" +
                "\t\t\t\"isJointRent\": 0\n" +
                "\t\t}]\n" +
                "\t}\n" +
                "}";

        JSONObject resultObj1 = JSONObject.parseObject(result);
        JSONObject resultObj2 = JSONObject.parseObject(resultObj1.getString("data"));
        JSONArray dataArray = resultObj2.getJSONArray("content");
        for(int j=0;j<dataArray.size();j++){
            JSONObject obj = dataArray.getJSONObject(j);
                System.out.println(obj.getString("contractState"));
            if (obj.containsKey("contractState") && obj.getString("contractState").equals("ydq")) {
                break;
            }
        }
    }
}

