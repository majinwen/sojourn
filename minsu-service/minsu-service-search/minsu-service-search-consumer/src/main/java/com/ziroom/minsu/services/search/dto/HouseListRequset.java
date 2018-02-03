package com.ziroom.minsu.services.search.dto;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.util.Check;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>获取头像列表</p>
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
public class HouseListRequset extends BaseEntity{

    private List<HouseSearchRequest> houseList;

    /**
     * 图标的尺寸大小
     * @see com.ziroom.minsu.valenum.search.IconPicTypeEnum
     */
    private Integer iconPicType;

    /**
     * 图片尺寸
     */
    private String picSize;

    private String uid;

    /**
     * 开始时间
     */
    private Date startTime;


    /**
     * 结束时间
     */
    private Date endTime;


    public List<HouseSearchRequest> getHouseList() {
        return houseList;
    }

    public void setHouseList(List<HouseSearchRequest> houseList) {
        this.houseList = houseList;
    }


    /**
     * 获取
     * @return
     */
    public List<String> transQuertList() {
        List<String> queryList = null;
        if (Check.NuNCollection(houseList)){
            return queryList;
        }
        queryList = new ArrayList<>();
        for(HouseSearchRequest searchRequest: houseList){
            if(!Check.NuNObjs(searchRequest.getFid(),searchRequest.getRentWay())){
                queryList.add(searchRequest.getFid()+"_"+searchRequest.getRentWay());
            }
        }
        return queryList;
    }


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getIconPicType() {
        return iconPicType;
    }

    public void setIconPicType(Integer iconPicType) {
        this.iconPicType = iconPicType;
    }

    public String getPicSize() {
        return picSize;
    }

    public void setPicSize(String picSize) {
        this.picSize = picSize;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
