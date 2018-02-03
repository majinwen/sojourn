package com.ziroom.minsu.services.common.utils;

import com.asura.framework.base.util.Check;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

/**
 * <p>对象的拼接</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/7/7.
 * @version 1.0
 * @since 1.0
 */
public class JoinUtil {



    /**
     * 拼接map串
     * @author afi
     * @param map
     * @param separator
     * @param ignoreEmptyValue
     * @return
     */
    public static String joinSortMap(Map<String, String> map, String separator, boolean ignoreEmptyValue) {
        return joinMap(new TreeMap<String, String>(map),null,null,separator,ignoreEmptyValue,null);
    }

    /**
     * 拼接map串
     * @author afi
     * @param map
     * @param separator
     * @param ignoreEmptyValue
     * @return
     */
    public static String joinMap(Map<String, String> map, String separator, boolean ignoreEmptyValue) {
        return joinMap(map,null,null,separator,ignoreEmptyValue,null);
    }



    /**
     * 拼接map串
     * @author afi
     * @param map
     * @param prefix
     * @param suffix
     * @param separator
     * @param ignoreEmptyValue
     * @param ignoreKeys
     * @return
     */
    public static String joinSortMap(Map<String, String> map, String prefix, String suffix, String separator, boolean ignoreEmptyValue, String... ignoreKeys) {
        return joinMap(new TreeMap<String, String>(map),prefix,suffix,separator,ignoreEmptyValue,ignoreKeys);
    }


    /**
     * 拼接map串
     * @author afi
     * @param map
     * @param prefix
     * @param suffix
     * @param separator
     * @param ignoreEmptyValue
     * @param ignoreKeys
     * @return
     */
    public static String joinMap(Map<String, String> map, String prefix, String suffix, String separator, boolean ignoreEmptyValue, String... ignoreKeys) {
        List<String> list = new ArrayList<String>();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = ValueUtil.getStrValue(entry.getValue());
                if (!Check.NuNStr(key) && !ArrayUtils.contains(ignoreKeys, key) && (!ignoreEmptyValue ||  !Check.NuNStr(value) )) {
                    list.add(key + "=" + (value != null ? value : ""));
                }
            }
        }
        return ValueUtil.getStrValue(prefix) + JoinUtil.join(list, ValueUtil.getStrValue(separator)) + ValueUtil.getStrValue(suffix);
    }




    /**
     * 拼接数组
     * @author afi
     * @param collection
     * @param separator
     * @return
     */
    public static String join(Collection collection, String separator) {
        return collection == null?null:join(collection.iterator(), separator);
    }


    /**
     * 拼接迭代器
     * @author afi
     * @param iterator
     * @param separator
     * @return
     */
    public static String join(Iterator iterator, String separator) {
        if(iterator == null) {
            return null;
        } else if(!iterator.hasNext()) {
            return "";
        } else {
            Object first = iterator.next();
            if(!iterator.hasNext()) {
                return ValueUtil.getStrValue(first);
            } else {
                StringBuffer buf = new StringBuffer(256);
                if(first != null) {
                    buf.append(first);
                }
                while(iterator.hasNext()) {
                    if(separator != null) {
                        buf.append(separator);
                    }
                    Object obj = iterator.next();
                    if(obj != null) {
                        buf.append(obj);
                    }
                }
                return buf.toString();
            }
        }
    }
}
