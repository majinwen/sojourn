package com.ziroom.minsu.services.common.utils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;

/**
 * <p>获取值信息</p>
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
public class ValueUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValueUtil.class);





    /**
     * 获取当前房源的评价的分数
     * @author afi
     * @param sore
     * @return
     */
    public static Double getEvaluteSoreDefault(String sore){
        if(Check.NuNStr(sore)){
            return 5.0;
        }
        if (sore.equals("0") || sore.startsWith("0.0")){
            return 5.0;
        }
        BigDecimal bigDecimal=new BigDecimal(sore);
        return bigDecimal.setScale(1,BigDecimal.ROUND_DOWN).doubleValue();
    }
    
    /**
     * 获取真实的评价分数
     * @author zl
     * @param sore
     * @return
     */
    public static Double getRealEvaluteSore(String sore){
        if(Check.NuNStr(sore)){
            return 0.0;
        }
        BigDecimal bigDecimal=new BigDecimal(sore);
        return bigDecimal.setScale(1,BigDecimal.ROUND_DOWN).doubleValue();
    }



    /**
     * 获取当前的基础url不包含前缀
     * @author afi
     * @param url
     * @return
     */
    public static String getBaseUrl(String url){
        if(Check.NuNStr(url)){
            return "";
        }
        if(url.startsWith("http://")){
            url = url.substring(7);
        }else if(url.startsWith("https://")){
            url = url.substring(8);
        }
        if(url.indexOf("?") > -1){
            url= url.substring(0, url.indexOf("?"));
        }
        return url;
    }


    /**
     * 清理map，如果map的value为空直接用 ""
     * @param par
     * @return
     */
    public static Map cleanMap(Map par){
        if(Check.NuNObj(par)){
            return null;
        }
        Iterator entries = par.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            Object key = entry.getKey();
            if(Check.NuNObj(entry.getValue())){
                par.put(key,"");
            }
        }
        return par;
    }

    /**
     * 获取相反的0 -1的值
     * @param org
     * @return
     */
    public static  int getRebelValue(int org){
        if(org==0){
            return 1;
        }else {
            return 0;
        }
    }


    /**
     * 获取非负数的最小值
     * @author afi
     * @param objs
     * @return
     */
    public static int getMin(Object... objs){
        return getMinNonegative(objs);
    }

    /**
     * 获取非负数的最小值
     * @author afi
     * @param argArray
     * @return
     */
    private static int getMinNonegative(Object[] argArray){

        if(argArray != null && argArray.length != 0) {
            int min=0;
            for(int i=0;i< argArray.length;i++){
                if(i==0){
                    min = getintValue(argArray[i]);
                }
                if(min >=  getintValue(argArray[i])){
                    min = getintValue(argArray[i]);
                }
            }
            return min;
        } else {
            throw new BusinessException("key empty or null argument array");
        }
    }


    /**
     * 校验当前是不是字母
     * @author afi
     * @param org
     * @return
     */
    public static boolean checkZimuOnly(String org) {
        if(Check.NuNStr(org)){
            return false;
        }
        return org.matches("[a-zA-Z]+");
    }




    /**
     * 从字符串中过滤数字
     * @author afi
     * @param str
     * @return
     */
    public static String removeFristNum(String str) {
        str = StringUtils.removeInvalidCharNoSpace(str);
        if(Check.NuNStr(str)){
            return str;
        }
        String regEx = "[0-9]*";
        if(str.substring(0,1).matches(regEx)){
            return removeFristNum(str.substring(1));
        }else {
            return str;
        }
    }




    /**
     * 从字符串中过滤数字
     * @author afi
     * @param str
     * @return
     */
    public static String removeNumAndOthers(String str) {
        str = StringUtils.removeInvalidCharNoSpace(str);
        String regEx = "[0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        //替换与模式匹配的所有字符（即数字的字符将被""替换）
        return m.replaceAll("").trim();
    }

    /**
     * 获取对象的字符值
     * @author afi
     * @param obj
     * @return
     */
    public static String getStrValue(Object obj){
        if(Check.NuNObj(obj)){
            return "";
        }
        try {
            String rst = String.valueOf(obj);
            return rst;
        }catch (Exception e){
            LogUtil.error(LOGGER,"e:{}",e);
            return "";
        }
    }

    /**
     *
     * @author afi
     * @param obj
     * @return
     */
    public static String getTrimStrValue(Object obj){
        if(Check.NuNObj(obj)){
            return "";
        }
        try {
            String rst = String.valueOf(obj).replace(" ", "");
            return rst;
        }catch (Exception e){
        	LogUtil.error(LOGGER,"e:{}",e);
            return "";
        }
    }

    /**
     * 获取对象的字符值
     * @author afi
     * @param obj
     * @return
     */
    public static int getintValue(Object obj){
        if(Check.NuNObj(obj)){
            return 0;
        }
        try {
            Integer rst = Integer.valueOf(getStrValue(obj));
            return rst;
        }catch (Exception e){
        	LogUtil.error(LOGGER,"e:{}",e);
            return 0;
        }
    }

    /**
     * 获取
     * @author afi
     * @param obj
     * @return
     */
    public static Long getlongValue(Object obj){
        if(Check.NuNObj(obj)){
            return 0L;
        }
        try {
            Long rst = Long.parseLong(getStrValue(obj));
            return rst;
        }catch (Exception e){
        	LogUtil.error(LOGGER,"e:{}",e);
            return 0L;
        }
    }

    /**
     * 获取
     * @author afi
     * @param obj
     * @return
     */
    public static double getdoubleValue(Object obj){
        if(Check.NuNObj(obj)){
            return 0;
        }
        try {
            Double rst = Double.parseDouble(getStrValue(obj));
            return rst;
        }catch (Exception e){
        	LogUtil.error(LOGGER,"e:{}",e);
            return 0;
        }
    }

    /**
     * 获取总共的页码
     * @author afi
     * @param count
     * @param limit
     * @return
     */
    public static int getPage(Integer count,Integer limit){
        double countD = new Double(count);
        double limitD = new Double(limit);
        double c = countD/limitD;
        return (int)Math.ceil(c);
    }


    /**
     * 将；list转化成set
     * @author afi
     * @param list
     * @return
     */
    public static Set<String> transList2SetAndFill(List<String> list){
        Set<String> set = transList2Set(list);
        if(set.isEmpty()){
            set.add("111111");
        }
        return set;
    }

    /**
     * 将；list转化成set
     * @author afi
     * @param list
     * @return
     */
    public static Set<String> transList2Set(List<String> list){
        Set<String>  set = new HashSet<>();
        if(!Check.NuNObj(list)){
            for(String ele: list){
                set.add(ele);
            }
        }
        return set;
    }

    /**
     * 将；list转化成str
     * @author afi
     * @param list
     * @return
     */
    public static String transList2Str(List<String> list){
        String split = ",";
        if(Check.NuNCollection(list) || list.size() ==0){
            throw new BusinessException("list is null");
        }
        StringBuffer sb = new StringBuffer();
        int i = 0;
        if(!Check.NuNObj(list)){
            for(String ele: list){
                if(i==0){
                    sb.append(ele);
                }else {
                    sb.append(split).append(ele);
                }
                i++;
            }
        }
        return sb.toString();
    }

    /**
     * 将 list转化成string in sql
     * @author afi
     * @param list
     * @return
     */
    public static String transList2StrInSql(List<String> list){
        return transList2StrInSql(list, null);
    }

    /**
     * 将 list转化成string in sql
     * @author afi
     * @param list
     * @return
     */
    public static String transList2StrInSql(List<String> list,String split){
        if(Check.NuNStr(split)){
            split = ",";
        }
        if(Check.NuNCollection(list) || list.size() ==0){
            throw new BusinessException("list is null");
        }
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        int i = 0;
        if(!Check.NuNObj(list)){
            for(String ele: list){
                if(i==0){
                    sb.append("'").append(ele).append("'");
                }else {
                    sb.append(split).append("'").append(ele).append("'");
                }
                i++;
            }
        }
        sb.append(")");
        return sb.toString();
    }



    /**
     * 将 list转化成string in solr
     * @author afi
     * @param list
     * @return
     */
    public static String transList2StrInSolr(List<String> list){
        String split = " OR ";
        if(Check.NuNCollection(list) || list.size() ==0){
            throw new BusinessException("list is null");
        }
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        int i = 0;
        if(!Check.NuNObj(list)){
            for(String ele: list){
                if(i==0){
                    sb.append(ele);
                }else {
                    sb.append(split).append(ele);
                }
                i++;
            }
        }
        sb.append(")");
        return sb.toString();
    }



    /**
     * 将分钟转化成带单位的信息
     * @author afi
     * @param minute
     * @return
     */
    public static String getTimeInfoByMinute(Integer minute){
        if (Check.NuNObj(minute)){
            return "";
        }
        int minuteValue = ValueUtil.getintValue(minute);
        if (minuteValue < 60){
            return minute + "分钟";
        }
        int hourInt = minuteValue/60;
        int minuteInt = minuteValue % 60;
        StringBuffer sb = new StringBuffer();
        sb.append(hourInt).append("小时");
        if (minuteInt > 0){
            sb.append(minuteInt).append("分钟");
        }
        return sb.toString();
    }

    /**
     * 将小时转化成带单位的信息
     * @author afi
     * @param hour
     * @return
     */
    public static String getTimeInfoByHour(Integer hour){
        if (Check.NuNObj(hour)){
            return "";
        }
        int hourValue = ValueUtil.getintValue(hour);
        if (hourValue < 24){
            return hour + "小时";
        }
        int dayInt = hourValue/24;
        int hourInt = hourValue % 24;
        StringBuffer sb = new StringBuffer();
        sb.append(dayInt).append("天");
        if (hourInt > 0){
            sb.append(hourInt).append("小时");
        }
        return sb.toString();
    }

    /**
     * 将天转化成带单位的信息
     * @author afi
     * @param day
     * @return
     */
    public static String getTimeInfoByDay(Integer day){
        if (Check.NuNObj(day)){
            return "";
        }
        int dayValue = ValueUtil.getintValue(day);
        if (dayValue == 1){
            return "24小时";
        }
        return day + "天";

    }




    public static void main(String[] args) {
        Integer aa = 223;
        System.out.println(ValueUtil.getTimeInfoByHour(aa));
    }

}

