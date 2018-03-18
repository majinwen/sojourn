package com.zra.common.utils;
/** 
* @author cuiy9
* @version 创建时间：Mar 2, 2017 6:11:08 PM 
*/
public class PicUtils {

    public static String wrapPicUrl(String picUrl) {
        if (StrUtils.isNullOrBlank(picUrl)) {
            return picUrl;
        }
        if (picUrl.startsWith("http:")) {
            return picUrl; 
        }
        //此处以后还需要扩展，根据不同前缀添加不同URL
        String picPrefix = PropUtils.getString(ZraApiConst.PIC_PREFIX_URL);
        return picPrefix + picUrl;
    }
    private PicUtils() {
        
    }
}
