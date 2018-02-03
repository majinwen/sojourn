package com.ziroom.minsu.services.basedata.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.entity.sys.CurrentuserRoleEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
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
@Repository("basedata.currentuserRoleDao")
public class CurrentuserRoleDao {


    private String SQLID="basedata.currentuserRoleDao.";

    @Autowired
    @Qualifier("basedata.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
	/**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(CurrentuserRoleDao.class);

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
        if(Check.NuNStr(userFid)){
        	LogUtil.info(logger,"on delCurrentuserRoleByUserFid the userFid is null ");
            throw  new BusinessException("on delCurrentuserRoleByUserFid the userFid is null ");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("userFid",userFid);
        mybatisDaoContext.delete(SQLID + "delCurrentuserRoleByUserFid", par);
    }
}
