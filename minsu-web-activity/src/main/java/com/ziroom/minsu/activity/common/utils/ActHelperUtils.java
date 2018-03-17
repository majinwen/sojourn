package com.ziroom.minsu.activity.common.utils;

import com.asura.framework.base.util.Check;

import java.util.List;

/**
 * 活动工具类
 * @author jixd
 * @created 2017年04月01日 10:46:02
 * @param
 * @return
 */
public class ActHelperUtils {
    private ActHelperUtils(){}

    /**
     * 获取随机的列表
     * @author jixd
     * @created 2017年04月01日 10:47:32
     * @param
     * @return
     */
    public static List<String> getRandomList(List<String> oralList){
        if(Check.NuNCollection(oralList)){
            return oralList;
        }
        int size = oralList.size();
        for (int i = 0;i<size;i++){
            int random = (int) (size * Math.random());
            String temp = oralList.get(i);
            oralList.set(i,oralList.get(random));
            oralList.set(random,temp);
        }
        return oralList;
    }
}
