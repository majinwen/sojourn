package com.ziroom.zrp.service.houses.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>封装 项目以及户型的 关系vo</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2017年12月06日 10:39
 * @since 1.0
 */
public class ProjectHouseTypeVo implements Serializable{

    private static final long serialVersionUID = -8603194281954894426L;
    /**
     * 项目id
     */
    private String projectId;

    /**
     * 项目民称
     */
    private String projectName;


    /**
     * 当前项目下户型
     */
    private List<HouseTypeVo> listHouseTypeVo = new ArrayList<HouseTypeVo>();

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<HouseTypeVo> getListHouseTypeVo() {
        return listHouseTypeVo;
    }

    public void setListHouseTypeVo(List<HouseTypeVo> listHouseTypeVo) {
        this.listHouseTypeVo = listHouseTypeVo;
    }
}
