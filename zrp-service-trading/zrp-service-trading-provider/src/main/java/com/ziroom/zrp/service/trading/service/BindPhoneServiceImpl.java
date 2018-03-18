package com.ziroom.zrp.service.trading.service;

import com.ziroom.zrp.service.trading.dao.BindPhoneDao;
import com.ziroom.zrp.trading.entity.BindPhoneEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>管家400电话</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年11月01日 19:28
 * @since 1.0
 */
@Service("trading.bindPhoneServiceImpl")
public class BindPhoneServiceImpl {

    @Resource(name="trading.bindPhoneDao")
    private BindPhoneDao bindPhoneDao;

    /**
     * 根据员工id查询员工400分机信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public BindPhoneEntity selectByEmployeeId(String employeeId) {
        return this.bindPhoneDao.selectByEmployeeId(employeeId);
    }

}
