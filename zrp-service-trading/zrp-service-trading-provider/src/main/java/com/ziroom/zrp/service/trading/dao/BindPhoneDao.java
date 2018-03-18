package com.ziroom.zrp.service.trading.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.trading.entity.BindPhoneEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("trading.bindPhoneDao")
public class BindPhoneDao {

    private String SQLID = "trading.bindPhoneDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 根据员工id查询员工400分机信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public BindPhoneEntity selectByEmployeeId(String employeeId) {
        BindPhoneEntity bindPhoneEntity = mybatisDaoContext.findOneSlave(SQLID + "selectByEmployeeId",BindPhoneEntity.class,employeeId);
        return bindPhoneEntity;
    }

}