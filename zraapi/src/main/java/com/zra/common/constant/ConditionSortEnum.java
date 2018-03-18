package com.zra.common.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>查询条件排序枚举</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/10 9:49
 * @since 1.0
 */
public enum ConditionSortEnum {

    CITY(1, "cityList","100"), //城市
    ROOMNUMER(2, "roomNumber","001"), //房间号
    AREA(3, "area","011"),// 面积
    PRICE(4, "price","111"),// 价格
    CHECKINTIME(5, "checkInTime","111"), // 可入住时间
    FLOOR(6, "floorNumberList","011") , // 楼层
    DIRECTION(7, "directionList","011"); // 朝向

    private Integer order;
    private String code;
    private String type;

    ConditionSortEnum(Integer order, String code, String type) {
        this.order = order;
        this.code = code;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ConditionSortEnum getByCode(String code){
        for(ConditionSortEnum conditionSortEnum: ConditionSortEnum.values()){
            if(code.equals(conditionSortEnum.getCode())){
                return conditionSortEnum;
            }
        }
        return null;
    }

    /**
     * 查询条件排序Map
     */
    protected static final Map<String, String> sortMap = new HashMap<>();

    static {
        for (ConditionSortEnum conditionSortEnum : ConditionSortEnum.values()) {
            sortMap.put(conditionSortEnum.getCode(), conditionSortEnum.getType());
        }
    }

    /**
     * 根据类型位数获取排序Map列表
     * @param no
     * @return
     */
    public static List<Map> getSortMapList(Integer no){
        List<Map> sortList = new ArrayList<>();
        Map<String, Object> sortMap;
        for (Map.Entry<String, String> entry: ConditionSortEnum.sortMap.entrySet()){
            if ("1".equals(entry.getValue().substring(no - 1, no))){
                sortMap = new HashMap<>();
                sortMap.put("code", entry.getKey());
                ConditionSortEnum sortEnum = ConditionSortEnum.getByCode(entry.getKey());
                sortMap.put("order", sortEnum == null ? 0 : sortEnum.getOrder());
                sortList.add(sortMap);
            }
        }
        return sortList;
    }

}
