package com.ziroom.minsu.valenum.search;

import com.asura.framework.base.util.Check;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>房间数的枚举</p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi
 * @version 1.0
 * @since 1.0
 */
public enum  RoomCountEnum {


    OTHER(1,0, "其他"),
    ONE(2,1, "一室"),
    TWO(4,2, "二室"),
    THREE(8,3, "三室"),
    FORE(16,4, "四室");

    private final Integer value;

    private final Integer code;

    private final String name;

    RoomCountEnum(Integer value,Integer code,String name) {
        this.value = value;
        this.code = code;
        this.name = name;
    }



    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }

    /**
     * 将房价数转化成solr需要的语法结构
     * @param roomCount
     * @return
     */
    public static String getSolrQuery(Integer roomCount){
        if (Check.NuNObj(roomCount)){
            return null;
        }
        List<String> solrList = new ArrayList();
        for (final RoomCountEnum roomCountEnum : RoomCountEnum.values()) {
            if ((roomCountEnum.getValue() & roomCount) == roomCountEnum.getValue()) {
                if (roomCountEnum.getCode() == RoomCountEnum.OTHER.getCode()){
                    //其他的搜索条件
                    solrList.add("5");
                    solrList.add("6");
                    solrList.add("7");
                    solrList.add("8");
                    solrList.add("9");
                    solrList.add("10");
                    solrList.add("0");
                }else {
                    solrList.add(roomCountEnum.getCode()+"");
                }
            }
        }
        return transList2StrInSolr(solrList);
    }

    /**
     * 通过房间数量获取搜索的房间数量
     * @author afi
     * @param roomCount
     * @return
     */
    public static int getSolrCountByRoomCount(Integer roomCount){
        if (Check.NuNObj(roomCount)){
            return OTHER.getCode();
        }
        //获取当前的房间数量并把数量转化成索引房间数量
        for (final RoomCountEnum roomCountEnum : RoomCountEnum.values()) {
            if (roomCountEnum.getCode().intValue() == roomCount){
                return roomCountEnum.getCode();
            }
        }
        return OTHER.getCode();
    }

    /**
     * 将list转成solr的搜索条件
     * @author afi
     * @param list
     * @return
     */
    private static String transList2StrInSolr(List<String> list){
        String split = " OR ";
        if(Check.NuNCollection(list) || list.size() ==0){
           return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        int i = 0;
        if(!Check.NuNObj(list)){
            for(String ele: list){
                if(i==0){
                    sb.append(ele);
                }else {
                    sb.append(split).append(ele);
                }
                i++;
            }
        }
        sb.append(")");
        return sb.toString();
    }




}
