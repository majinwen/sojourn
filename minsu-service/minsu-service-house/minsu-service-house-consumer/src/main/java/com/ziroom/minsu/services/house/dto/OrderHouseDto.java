package com.ziroom.minsu.services.house.dto;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;

import java.util.Date;

/**
 * 查询订单需要的房源信息
 * Created with IntelliJ IDEA
 * User: lunan
 * Date: 2016/12/7
 */
public class OrderHouseDto {

    //房源或者房间fid
    private String fid;

    //出租方式
    private Integer rentWay;

    //开始时间
    private Date startDate;

    //结束时间
    private Date endDate;

    public long calcDaysByDate(){
        long days = 0;
        try{
            if(!Check.NuNObjs(this.startDate,this.endDate)){
                long star = DateUtil.formatDateToLong(DateUtil.dateFormat(this.startDate, "yyyy-MM-dd"));
                long end = DateUtil.formatDateToLong(DateUtil.dateFormat(this.endDate, "yyyy-MM-dd"));
                days = (end - star) / (24 * 60 * 60 * 1000);
            }
        }catch (Exception e){
            days = -1;
        }
        return days;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
