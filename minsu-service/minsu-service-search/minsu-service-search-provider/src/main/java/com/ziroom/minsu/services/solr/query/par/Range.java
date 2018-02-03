package com.ziroom.minsu.services.solr.query.par;

import com.asura.framework.base.util.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>搜索的参数条件</p>
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
public  class Range {


    public final static String START = "start";
    public final static String END = "end";
    public final static String LEFT = "left";
    public final static String RIGHT = "right";

    /** 开始值 */
    private Object start;

    /** 结束值 */
    private Object end;

    /** 左侧包含. 默认为true, 则包含开始值. 若为false, 则不包含开始值. */
    private boolean leftInclude;

    /** 右侧包含. 默认为true, 则包含结束值; 若为 false, 则不包含结束值 */
    private boolean rightInclude;

    public Range(final Object start, final Object end, final boolean leftInclude, final boolean rightInclude) {
        this.start = start;
        this.end = end;
        this.leftInclude = leftInclude;
        this.rightInclude = rightInclude;
    }

    public Object getStart() {
        return start;
    }

    public void setStart(final Object start) {
        this.start = start;
    }

    public Object getEnd() {
        return end;
    }

    public void setEnd(final Object end) {
        this.end = end;
    }

    public boolean isLeftInclude() {
        return leftInclude;
    }

    public void setLeftInclude(final boolean leftInclude) {
        this.leftInclude = leftInclude;
    }

    public boolean isRightInclude() {
        return rightInclude;
    }

    public void setRightInclude(final boolean rightInclude) {
        this.rightInclude = rightInclude;
    }

    @Override
    public String toString() {
        final StringBuilder toRange = new StringBuilder();
        toRange.append(isLeftInclude() == true ? '[' : '{');
        toRange.append(start).append(" TO ").append(end);
        toRange.append(isRightInclude() == true ? ']' : '}');

        return toRange.toString();
    }

    public static Map<String,Object> getRangeMap(Object start,Object end,boolean includeLeft,boolean includeRigth){

        Map<String, Object> rangeMap = new HashMap<String, Object>();
        if(Check.NuNObj(start)){
            rangeMap.put("start", "*");
        }else{
            rangeMap.put("start", start);
        }
        if(Check.NuNObj(end)){
            rangeMap.put("end", "*");
        }else{
            rangeMap.put("end", end);
        }
        rangeMap.put("left", includeLeft);
        rangeMap.put("right", includeRigth);
        return rangeMap;
    }

}
