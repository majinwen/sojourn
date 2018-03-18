package com.ziroom.zrp.service.houses.entity;

/*
 * <P>添加房源分组相关信息vo</P>
 * <P>
 * <PRE>
 * <BR> 修改记录
 * <BR>------------------------------------------
 * <BR> 修改日期       修改人        修改内容
 * </PRE>
 * 
 * @Author lusp
 * @Date Create in 2017年10月 19日 14:10
 * @Version 1.0
 * @Since 1.0
 */

import com.asura.framework.base.entity.BaseEntity;
import lombok.Data;

@Data
public class AddHouseGroupVo extends BaseEntity {

    /**
     * 序列id
     */
    private static final long serialVersionUID = -7421405774522017968L;

    /**
     * 城市id
     */
    private String cityid;

    /**
     * 项目id
     */
    private String projectid;

    /**
     * 项目名称
     */
    private String projectname;

    /**
     * 户型id
     */
    private String housetypeid;

    /**
     * 户型名字
     */
    private String housetypename;

    /**
     * 楼栋id
     */
    private String buildingid;

    /**
     * 楼栋名称
     */
    private String buildingname;

    /**
     * 楼层数
     */
    private Integer ffloornumber;

    /**
     * 房间id
     */
    private String roomid;

    /**
     * 房间编号
     */
    private String roomnumber;

}
