package com.ziroom.minsu.services.cms.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.InviterCodeEntity;

@Repository("cms.inviterCodeDao")
public class InviterCodeDao {

	/**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(CouponMobileLogDao.class);

    private String SQLID = "cms.inviterCodeDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 插入实体
     * @param record
     * @return
     */
    public int insertInviterCod(InviterCodeEntity record){
    	return mybatisDaoContext.save(SQLID+"insertInviterCod", record);
    };

    /**
     * 根据inviteUid查询实体
     * @param record
     * @return
     */
    public InviterCodeEntity selectByInviteUid(String inviteUid){
    	return mybatisDaoContext.findOne(SQLID+"selectByInviteUid", InviterCodeEntity.class, inviteUid);
    };
    
    /**
     * 更新实体
     * @param record
     * @return
     */
    public int updateByInviteUid(InviterCodeEntity record){
    	 if (Check.NuNObj(record)) {
             LogUtil.error(LOGGER, "InviterCodeEntity参数为空");
             throw new BusinessException("InviterCodeEntity参数为空");
         }
    	return mybatisDaoContext.update(SQLID+"updateByInviteUid", record);
    };


    /**
     * 根据inviteCode查询实体
     * @author yanb
     * @param record
     * @return
     */
    public InviterCodeEntity selectByInviteCode(String inviteCode){
        return mybatisDaoContext.findOne(SQLID+"selectByInviteCode", InviterCodeEntity.class, inviteCode);
    }

}