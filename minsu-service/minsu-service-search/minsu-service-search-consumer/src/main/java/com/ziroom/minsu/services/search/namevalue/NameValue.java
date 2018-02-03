package com.ziroom.minsu.services.search.namevalue;

/**
 * <p>NameValue</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/16.
 * @version 1.0
 * @since 1.0
 */
public class NameValue<N, V> {

    private final N name;

    private final V value;

    public NameValue(N n, V v) {
        name = n;
        value = v;
    }

    public N getName() {
        return name;
    }

    public V getValue() {
        return value;
    }
}