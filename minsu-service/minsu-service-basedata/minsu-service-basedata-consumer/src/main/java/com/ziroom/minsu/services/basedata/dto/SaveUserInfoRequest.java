package com.ziroom.minsu.services.basedata.dto;

import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.entity.sys.RoleEntity;
import com.ziroom.minsu.entity.sys.CityEntity;
import java.util.List;



/**
 * <p>修改用户信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/12.
 * @version 1.0
 * @since 1.0
 */
public class SaveUserInfoRequest extends CurrentuserEntity{


    /**
     * 城市列表
     */
    private List<CityEntity>  citys ;

    /**
     * 城市列表
     */
    private List<RoleEntity>  roles ;

    public List<CityEntity> getCitys() {
        return citys;
    }

    public void setCitys(List<CityEntity> citys) {
        this.citys = citys;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }
}
