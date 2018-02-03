package com.ziroom.minsu.services.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;

/**
 * <p>汉子转拼音</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/9/19.
 * @version 1.0
 * @since 1.0
 */
public class PinYinUtil {


    /**
     * 日志文件
     */
    private static final Logger logger = LoggerFactory
            .getLogger(PinYinUtil.class);


    private static Map<String,String> spMap  = new HashMap<>();
    
    static{
    	spMap.put("重庆", "chongqing");
    	spMap.put("重庆市", "chongqingshi");
    	spMap.put("蚌埠市", "bengbushi");
    	spMap.put("成都市", "chengdushi");
    	spMap.put("厦门市", "xiamenshi");
    	spMap.put("厦门", "xiamen");
    	spMap.put("长春市", "changchunshi");
    	spMap.put("长春", "changchun");
    	spMap.put("长海县", "changhaixian");
     	spMap.put("长海", "changhai");
    	spMap.put("长乐市", "changleshi");
    	spMap.put("长乐", "changle");
    	spMap.put("长沙", "changsha");
    	spMap.put("长沙市", "changshashi");
    	spMap.put("长治市", "changzhishi");
    	spMap.put("长治", "changzhi");
    	spMap.put("昌都市", "changdushi");
    	spMap.put("喀什市", "kashishi");
    	spMap.put("那曲县", "naquxian");
    	spMap.put("长岛县", "changdaoxian");
    	spMap.put("长岛", "changdao");
    	spMap.put("长丰县", "changfengxian");
    	spMap.put("长丰", "changfeng");
    	spMap.put("长泰县", "changtaixian");
    	spMap.put("长泰", "changtai");
    	spMap.put("长汀县", "changtingxian");
    	spMap.put("长汀", "changting");
    	spMap.put("长兴县", "changxingxian");
    	spMap.put("长兴", "changxing");
    	spMap.put("枞阳县", "congyangxian");
    	spMap.put("都昌县", "duchangxian");
    	spMap.put("都江堰市", "dujiangyanshi");
    	spMap.put("丰都县", "fengduxian");
    	spMap.put("江都区", "jiangduqu");
    	spMap.put("宁都县", "ningduxian");
    	spMap.put("莘县", "shenxian");
    	spMap.put("天长市", "tianchangshi");
    	spMap.put("荥阳市", "xingyangshi");
    	spMap.put("于都县", "yuduxian");
    	
    }


    /**
     * 获取当前城市的拼音
     * @param hanzi
     * @return
     */
    public static String getCityPinyin(String hanzi){
        if (Check.NuNObj(hanzi)){
            return "#";
        }
        if (spMap.containsKey(hanzi)){
            return spMap.get(hanzi);
        }
        return getPinYin(hanzi);
    }


    /**
     *
     * 汉字转拼音
     *
     * @author zhangshaobin
     * @created 2016年4月13日 上午4:41:53
     *
     * @param hanzi
     * @return
     */
    public static String getPinYin(String hanzi) {
        char[] t1 = null;
        t1 = hanzi.toCharArray();
        String[] t2 = new String[t1.length];
        // 设置汉字拼音输出的格式
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 设置大小写
        /**
         * WITH_TONE_MARK: 汉语拼音输出调 ，这个可能是字母上面的， 报错
         * WITH_TONE_NUMBER：汉语拼音的声调以数字形式输出，例如： 广东=guang3dong1 WITHOUT_TONE:
         * 不输出音调 例如：广东=guangdong
         */
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        /**
         * WITH_V: 驴车= lvche， WITH_U_UNICODE:驴车= lüche， WITH_U_AND_COLON:驴车=
         * lu:che
         */
        t3.setVCharType(HanyuPinyinVCharType.WITH_V); // v这个字符的输出
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    // 将汉字的几种全拼都存到t2数组中
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    // 取出该汉字全拼的第一种读音并连接到字符串t4后
                    t4 += t2[0];
                } else {
                    // 如果不是汉字字符，直接取出字符并连接到字符串t4后
                    t4 += Character.toString(t1[i]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            LogUtil.error(logger, "不包含多音字,拼音转汉字异常:{}", e);
        }
        return t4;
    }



    /**
     *
     * 汉字转换成汉语全拼，英文字符不变，支持多音字，生成方式如（人参：rencen,renshen,rencan）
     *
     * @author zhangshaobin
     * @created 2016年4月13日 上午5:28:07
     *
     * @param hanzi
     * @return
     */
    public static String getPinYinMulti(String hanzi) {
        StringBuffer pinyinName = new StringBuffer();
        char[] nameChar = hanzi.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

        //处理第一个字母简写
        StringBuffer fristEleSb = new StringBuffer();
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    // 取得当前汉字的所有全拼
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(
                            nameChar[i], defaultFormat);
                    if (strs != null) {

                        if(strs.length>0){
                            //设置第一个多音字的第一个字母的简写
                            fristEleSb.append(strs[0].substring(0,1));
                        }
                        for (int j = 0; j < strs.length; j++) {
                            pinyinName.append(strs[j]);
                            if (j != strs.length - 1) {
                                pinyinName.append(",");
                            }
                        }
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    LogUtil.error(logger, "包含多音字,拼音转汉字异常:{}", e);
                }
            } else {
                pinyinName.append(nameChar[i]);
            }
            pinyinName.append(" ");
        }
        String simpleEle = fristEleSb.toString();

        //处理信息完成
        String baseStr =parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
//        if(!baseStr.contains(simpleEle)){
//            return baseStr + ","+ simpleEle;
//        }else {
//            return simpleEle;
//        }
        return baseStr;
    }

    /**
     *
     * 去除多音字重复数据
     *
     * @author zhangshaobin
     * @created 2016年4月13日 上午5:29:15
     *
     * @param theStr
     * @return
     */
    private static List<Map<String, Integer>> discountTheChinese(String theStr) {
        // 去除重复拼音后的拼音列表
        List<Map<String, Integer>> mapList = new ArrayList<Map<String, Integer>>();
        // 用于处理每个字的多音字，去掉重复
        Map<String, Integer> onlyOne = null;

        String[] firsts = theStr.split(" ");
        // 读出每个汉字的拼音
        for (String str : firsts) {
            onlyOne = new Hashtable<String, Integer>();
            String[] china = str.split(",");
            // 多音字处理
            for (String s : china) {
                Integer count = onlyOne.get(s);
                if (count == null) {
                    onlyOne.put(s, new Integer(1));
                } else {
                    onlyOne.remove(s);
                    count++;
                    onlyOne.put(s, count);
                }
            }
            mapList.add(onlyOne);
        }
        return mapList;
    }




    /**
     *
     * 解析并组合拼音
     *
     * @author zhangshaobin
     * @created 2016年4月13日 上午5:29:29
     *
     * @param list
     * @return
     */
    private static String parseTheChineseByObject(
            List<Map<String, Integer>> list) {
        Map<String, Integer> first = null; // 用于统计每一次,集合组合数据
        // 遍历每一组集合
        for (int i = 0; i < list.size(); i++) {
            // 每一组集合与上一次组合的Map
            Map<String, Integer> temp = new Hashtable<String, Integer>();
            // 第一次循环，first为空
            if (first != null) {
                // 取出上次组合与此次集合的字符，并保存
                for (String s : first.keySet()) {
                    for (String s1 : list.get(i).keySet()) {
                        String str = s + s1;
                        temp.put(str, 1);
                    }
                }
                // 清理上一次组合数据
                if (temp != null && temp.size() > 0) {
                    first.clear();
                }
            } else {
                for (String s : list.get(i).keySet()) {
                    String str = s;
                    temp.put(str, 1);
                }
            }
            // 保存组合数据以便下次循环使用
            if (temp != null && temp.size() > 0) {
                first = temp;
            }
        }
        String returnStr = "";
        if (first != null) {
            // 遍历取出组合字符串
            for (String str : first.keySet()) {
                returnStr += (str + ",");
            }
        }
        if (returnStr.length() > 0) {
            returnStr = returnStr.substring(0, returnStr.length() - 1);
        }
        return returnStr;
    }
 
}
