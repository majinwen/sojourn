package com.ziroom.minsu.api.search.common.interceptor.collector;

/**
 * <p>收集器</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/15.
 * @version 1.0
 * @since 1.0
 */
public interface Collector<T, V, R> {

    /**
     * 收集信息.
     * @param t
     * @param v
     * @return
     */
    R collector(T t, V v);
}
