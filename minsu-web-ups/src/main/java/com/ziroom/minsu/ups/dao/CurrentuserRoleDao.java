package com.ziroom.minsu.ups.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.sys.CurrentuserRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>角色测试</p>
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
@Repository("ups.currentuserRoleDao")
public class CurrentuserRoleDao {


    private String SQLID="ups.currentuserRoleDao.";

    @Autowired
    @Qualifier("ups.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    /**
     *
     * 插入用户关系表
     *
     * @author afi
     * @created 2016年3月12日
     *
     * @param currentuserRoleEntity
     */
    public void insertCurrentuserRole(CurrentuserRoleEntity currentuserRoleEntity){
        mybatisDaoContext.save(SQLID+"insertCurrentuserRole", currentuserRoleEntity);
    }

    /**
     *
     * 删除用户角色关系表
     *
     * @author afi
     * @created 2016年3月12日
     *
     * @param userFid
     */
    public void delCurrentuserRoleByUserFid(String userFid){
        Map<String,Object> par = new HashMap<>();
        par.put("userFid",userFid);
        mybatisDaoContext.delete(SQLID + "delCurrentuserRoleByUserFid", par);
    }

    /**
     * 根据用户fid 查询角色fid集合
     * @author yd
     * @created
     * @param
     * @return
     */
    public List<String> queryRoleFidsByCurFid(String curFid){
        return  mybatisDaoContext.findAll(SQLID+"queryRoleFidsByCurFid",String.class,curFid);
    }
}
