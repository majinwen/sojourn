package com.zra.common.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/1.
 */
public final class CommonUtil {
    private static final List<String> countryNameList = new ArrayList<>();

    private CommonUtil() {
    }

    /**
     * 根据逗号",",拼成"','"的样子，主要会用于sql的in查询时，且类型为varchar。
     * 注意：后面多的逗号并不会拆成""，前面多的逗号则会分成""
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-01
     */
    public static String splitByComma(String str) {
        String replaceStr = str.replaceAll("(\\s)*,(\\s)*", "','");
        return "'" + replaceStr + "'";
    }

    /**
     * 将Double转成String，去掉.0或者无用的0
     *
     * @param d
     * @return
     */
    public static String doubleToString(Double d){
        return new BigDecimal(d.toString()).stripTrailingZeros().toPlainString();
    }

    /**
     * 将分转成元
     * @param fen 分
     * @return
     */
    public static Double fenTOYuan(int fen) {
        return new BigDecimal(fen).divide(new BigDecimal(100),BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 获取各个国家名称列表，其中[中国]在第一位，顺便把[台湾地区]给干掉了(推动国家统一，哈哈哈)
     *
     * @Author: wangxm113
     * @CreateDate: 2017-03-15
     */
    public static List<String> getAllCountryNameList() {
        if (countryNameList.size() > 0) {
            return countryNameList;
        }
        String TaiWan = Locale.TAIWAN.getDisplayCountry();
        String ZGName = Locale.CHINA.getDisplayCountry();
        countryNameList.add(ZGName);
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
            String displayCountry = locale.getDisplayCountry();
            if (displayCountry.length() <= 0 || ZGName.equals(displayCountry) || TaiWan.equals(displayCountry)) {
                continue;
            } else {
                countryNameList.add(displayCountry);
            }
        }
        return countryNameList;
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
