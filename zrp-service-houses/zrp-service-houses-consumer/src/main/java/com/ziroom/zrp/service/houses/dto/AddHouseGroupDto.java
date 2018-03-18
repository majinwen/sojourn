package com.ziroom.zrp.service.houses.dto;

import com.ziroom.zrp.houses.entity.PageRequest;

/**
 * <p>添加房源分组入参</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年10月19日 10:42
 * @since 1.0
 */
public class AddHouseGroupDto extends PageRequest {

    private static final long serialVersionUID = -3092094872822996184L;

    /**
     * 城市id
     */
    private String cityid;

    /**
     * 项目id
     */
    private String projectid;

    /**
     * 户型id
     */
    private String housetypeid;

    /**
     * 楼栋id
     */
    private String buildingid;

    /**
     * 楼层数
     */
    private Integer ffloornumber;

    /**
     * 房间号
     */
    private String froomnumber;

    /**
     * 房间状态
     */
    private String fcurrentstate;

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getHousetypeid() {
        return housetypeid;
    }

    public void setHousetypeid(String housetypeid) {
        this.housetypeid = housetypeid;
    }

    public String getBuildingid() {
        return buildingid;
    }

    public void setBuildingid(String buildingid) {
        this.buildingid = buildingid;
    }

    public Integer getFfloornumber() {
        return ffloornumber;
    }

    public void setFfloornumber(Integer ffloornumber) {
        this.ffloornumber = ffloornumber;
    }

    public String getFroomnumber() {
        return froomnumber;
    }

    public void setFroomnumber(String froomnumber) {
        this.froomnumber = froomnumber;
    }

    public String getFcurrentstate() {
        return fcurrentstate;
    }

    public void setFcurrentstate(String fcurrentstate) {
        this.fcurrentstate = fcurrentstate;
    }
}
