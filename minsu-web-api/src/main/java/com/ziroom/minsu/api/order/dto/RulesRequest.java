package com.ziroom.minsu.api.order.dto;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/12/6 15:22
 * @version 1.0
 * @since 1.0
 */
public class RulesRequest {

    /**
     * 规则模板编号
     */
    private Integer code;


    /**
     * 房源或房间fid
     */
    private String fid;

    /**
     * 租住方式
     */
    private Integer rentWay;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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

    @Override
    public String toString() {
        return "RulesRequest{" +
                "code=" + code +
                ", fid='" + fid + '\'' +
                ", rentWay=" + rentWay +
                '}';
    }
}
