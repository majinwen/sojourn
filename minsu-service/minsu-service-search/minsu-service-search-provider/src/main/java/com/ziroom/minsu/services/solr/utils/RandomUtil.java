package com.ziroom.minsu.services.solr.utils;

/**
 * <p>随机数，这个以后会删掉</p>
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
public class RandomUtil {

    /**
     * 获取100之内的随机数
     * @return
     */
    public  static  int getRandomInt(){
        int x=1+(int)(Math.random()*100);
        return x;
    }


}
