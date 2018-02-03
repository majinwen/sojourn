package com.ziroom.minsu.services.search.service;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.solr.utils.RandomUtil;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>一些值得测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/26.
 * @version 1.0
 * @since 1.0
 */
public class ValueService {

    private static Map<Integer,String> pp = new HashMap<>();
    static {
        pp.put(1,"东湾小区");
        pp.put(2,"");
        pp.put(3,"");
        pp.put(4,"光华绿苑");
        pp.put(5,"徐汇臻园");
        pp.put(6,"华发小区");
        pp.put(7,"明丰新纪苑");
        pp.put(8,"名人花苑");
        pp.put(9,"徐汇华园");
        pp.put(10,"华发路100弄");
        pp.put(11,"印象欧洲城");
        pp.put(12,"迪盛小区");
        pp.put(13,"桂林南路150弄");
        pp.put(14,"华欣家园");
        pp.put(15,"三江小区");
        pp.put(16,"徐汇苑");
        pp.put(17,"宛南六村");
        pp.put(18,"上缝小区");
        pp.put(19,"龙南三村");
        pp.put(20,"金龙花苑");
        pp.put(21,"凯翔小区");
        pp.put(22,"龙华西路21弄");
        pp.put(23,"漕溪二村");
        pp.put(24,"俞一小区");
        pp.put(25,"海波花苑");
        pp.put(26,"盛大花园");
        pp.put(27,"漕溪四村");
        pp.put(28,"龙南六村");
        pp.put(29,"协昌小区");
        pp.put(30,"漕溪一村");
        pp.put(31,"华龙新苑");
        pp.put(32,"龙吴路11弄");
        pp.put(33,"中山苑");
        pp.put(34,"华富小区");
        pp.put(35,"金谷园");
        pp.put(36,"申航小区");
        pp.put(37,"宛南五村");
        pp.put(38,"东荡小区");
        pp.put(39,"嘉萱苑");
        pp.put(40,"龙华机场新村");
        pp.put(41,"明佳苑");
        pp.put(42,"保利星苑");
        pp.put(43,"漕溪三村");
        pp.put(44,"狮城花苑");
        pp.put(45,"宛南四村");
        pp.put(46,"住友公寓");
        pp.put(47,"舒城苑");
        pp.put(48,"华侨新村");
        pp.put(49,"龙漕路235弄");
        pp.put(50,"龙漕路66弄");
        pp.put(51,"气象苑");
    }

    public static  int getHentWay(){
        if(RandomUtil.getRandomInt()>50){
            return 2;
        }else{
            return 1;
        }
    }

    public static  String getRentWayName(int type){
        if(type == 1){
            return "整租";
        }else{
            return "合租";
        }
    }

    public static  int getOrderType(){
        if(RandomUtil.getRandomInt()>50){
            return 2;
        }else{
            return 1;
        }
    }

    public static  String getOrderTypeName(int orderType){
        if(orderType == 1){
            return "极速订单";
        }else{
            return "普通订单";
        }
    }

    public static  int getDayCount(){
        if(RandomUtil.getRandomInt()>60){
            return 3;
        }else if(RandomUtil.getRandomInt()>30){
            return 2;
        }else {
            return 1;
        }
    }

    public static Set<String> getDaySet() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = null;
        Date end = null;
        int randon = RandomUtil.getRandomInt();
        if(randon > 90){
            start = sdf.parse("2016-03-10 00:00:00");
            end = sdf.parse("2016-04-01 00:00:00");
        }else if(randon > 80){
            start = sdf.parse("2016-08-10 00:00:00");
            end = sdf.parse("2016-09-01 00:00:00");
        }else if(randon > 70){
            start = sdf.parse("2016-07-10 00:00:00");
            end = sdf.parse("2016-08-01 00:00:00");
        }else if(randon > 50){
            start = sdf.parse("2016-06-10 00:00:00");
            end = sdf.parse("2016-07-21 00:00:00");
        }else if(randon > 30){
            start = sdf.parse("2016-05-10 00:00:00");
            end = sdf.parse("2016-06-01 00:00:00");
        }else{
            start = sdf.parse("2016-03-10 00:00:00");
            end = sdf.parse("2016-04-01 00:00:00");
        }
        return DateSplitUtil.getDateSplitSet(start,end);

    }


    public static String getRandomName(){

        String name =   pp.get(RandomUtil.getRandomInt()/2);
        if(Check.NuNStr(name)){
            return "测试";
        }
        return name;
    }

    public static Set<String> getRandomHot(){
        Set<String> set = new HashSet<>();

        set.add(getRandomName());
        set.add(getRandomName());
        set.add(getRandomName());
        return set;
    }
}
