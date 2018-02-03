package com.ziroom.minsu.valenum.search;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.search.MinsuSortEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zl
 * @version 1.0
 * @Date Created in 2017年08月04日 20:38
 * @since 1.0
 */
public enum CmsSortTypeEnum {

    DEFAULT(0,1, "默认排序",true),
    ;

    private final Integer code;

    private final Integer index;

    private final String name;

    private final Boolean show;


    public Map<String,Object>  getSortMap(){
        Map<String,Object> sortMap = new LinkedHashMap<>();
        sortMap.put("score","desc");
        sortMap.put("weights","desc");
        sortMap.put("randNum","desc");
        return sortMap;
    }

    CmsSortTypeEnum(Integer code,Integer index,String name,Boolean show) {
        this.code = code;
        this.index = index;
        this.name = name;
        this.show = show;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }

    public Boolean getShow() {
        return show;
    }


    /**
     * 通过code过去当前的
     * @param code
     * @return
     */
    public static CmsSortTypeEnum getByCode(Integer code){
        if (Check.NuNObj(code)){
            return DEFAULT;
        }
        for(final CmsSortTypeEnum sortTypeEnum : CmsSortTypeEnum.values()){
            if(sortTypeEnum.getCode() == code){
                return sortTypeEnum;
            }
        }
        return DEFAULT;
    }

    /**
     * 获取当前的搜索条件
     * @author zl
     * @created 2017/8/4 20:41
     * @param
     * @return
     */
    public static List<MinsuSortEntity> getSortList(){
        List<MinsuSortEntity> sortList = new ArrayList<MinsuSortEntity>();
        for (final CmsSortTypeEnum sortTypeEnum : CmsSortTypeEnum.values()) {

            if (!sortTypeEnum.getShow()) {
                continue;
            }

            MinsuSortEntity ele = new MinsuSortEntity();
            ele.setCode(sortTypeEnum.code);
            ele.setIndex(sortTypeEnum.index);
            ele.setName(sortTypeEnum.name);
            sortList.add(ele);
        }
        return sortList;
    }


}
