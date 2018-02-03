package com.ziroom.minsu.valenum.search;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.search.MinsuSortEntity;

/**
 * <p>搜索排序了类型</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/23.
 * @version 1.0
 * @since 1.0
 */
public enum  SortTypeEnum {


    DEFAULT(0,1, "默认排序",true),
    MARK(1, 2,"评分最高",true){
        @Override
        public Map<String,Object>  getSortMap(){
            Map<String,Object> sortMap = new LinkedHashMap<>();
            sortMap.put("evaluateScore","desc");
            sortMap.put("adIndex","desc");
            sortMap.put("weights","desc");
            sortMap.put("houseQualityGrade","desc");
            return sortMap;
        }

    },
    PRICE_LOW(2,3, "价格最低",true){
        @Override
        public Map<String,Object>  getSortMap(){
            Map<String,Object> sortMap = new LinkedHashMap<>();
            sortMap.put("price","asc");
            sortMap.put("adIndex","desc");
            sortMap.put("weights","desc");
            sortMap.put("houseQualityGrade","desc");
            return sortMap;
        }
    },
    PRICE_HIGH(3,4, "价格最高",true){
        @Override
        public Map<String,Object>  getSortMap(){
            Map<String,Object> sortMap = new LinkedHashMap<>();
            sortMap.put("price","desc");
            sortMap.put("adIndex","desc");
            sortMap.put("weights","desc");
            sortMap.put("houseQualityGrade","desc");
            return sortMap;
        }
    },
    EVALUATE_HIGH(4,5, "评价最多",true){
        @Override
        public Map<String,Object>  getSortMap(){
            Map<String,Object> sortMap = new LinkedHashMap<>();
            sortMap.put("evaluateCount","desc");
            sortMap.put("adIndex","desc");
            sortMap.put("weights","desc");
            sortMap.put("houseQualityGrade","desc");
            return sortMap;
        }
    },
    TONIGHT_ARTICLE(5,6, "今日特惠",false){
        @Override
        public Map<String,Object>  getSortMap(){
            Map<String,Object> sortMap = new LinkedHashMap<>();
            sortMap.put("tonightDiscount","asc");
            sortMap.put("score","desc");
            sortMap.put("adIndex","desc");
            sortMap.put("weights","desc");
            sortMap.put("houseQualityGrade","desc");
            return sortMap;
        }
    },
    TOP50_LIST(6,7, "top50榜单",false){
        @Override
        public Map<String,Object>  getSortMap(){
            Map<String,Object> sortMap = new LinkedHashMap<>();
            sortMap.put("score","desc");
            sortMap.put("adIndex","desc");
            sortMap.put("weights","desc");
            sortMap.put("houseQualityGrade","desc");
            return sortMap;
        }
    };

    private final Integer code;

    private final Integer index;

    private final String name;
    
    private final Boolean show;


    public Map<String,Object> getSortMap(){
        Map<String,Object> sortMap = new LinkedHashMap<>();
        sortMap.put("sum(product(query($q),100,0.4),product(weights,0.6))", "desc");
        sortMap.put("score","desc");
        sortMap.put("adIndex","desc");
        sortMap.put("weights","desc");
        sortMap.put("houseQualityGrade","desc");
        return sortMap;
    }

    SortTypeEnum(Integer code,Integer index,String name,Boolean show) {
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
    public static SortTypeEnum getByCode(Integer code){
        if (Check.NuNObj(code)){
            return DEFAULT;
        }
        for(final SortTypeEnum sortTypeEnum : SortTypeEnum.values()){
            if(sortTypeEnum.getCode() == code){
                return sortTypeEnum;
            }
        }
        return DEFAULT;
    }


    /**
     * 获取当前的搜索条件
     * @author afi
     * @return
     */
    public static List<MinsuSortEntity> getSortList(){
        List<MinsuSortEntity> sortList = new ArrayList<MinsuSortEntity>();
        for (final SortTypeEnum sortTypeEnum : SortTypeEnum.values()) {
        	
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
