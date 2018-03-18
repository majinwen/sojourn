package com.zra.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: wangxm113
 * CreateDate: 2017/3/9.
 */
public class HtmlTagUtil {
    private static final String regEx_HTML = "<[^>]+>";

    public static String removeHtmlTags(String s) {
        Pattern p_style = Pattern.compile(regEx_HTML, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(s);
        String s1 = m_style.replaceAll("");
        String s2 = System.getProperty("line.separator", "\n");
        String s3 = s1.replace(s2, "");
        while (s3.endsWith("\n")) {
            s3 = s3.substring(0, s3.length() - 1);
        }
        s3 = s3.replace("\r", "");
        return s3; // 过滤style标签
    }

    public static String replaceTags(String s){
        return s.replace("&nbsp;", "  ").replace("&lt;", "<").replace("&gt;", ">");
    }
}
