package com.ziroom.zrp.service.trading.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.houses.entity.EmployeeEntity;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.service.houses.api.EmployeeService;
import com.ziroom.zrp.service.houses.api.ProjectService;
import com.ziroom.zrp.service.trading.api.BindPhoneService;
import com.ziroom.zrp.service.trading.proxy.commonlogic.RentContractLogic;
import com.ziroom.zrp.service.trading.service.BindPhoneServiceImpl;
import com.ziroom.zrp.service.trading.service.schedule.ScheduleServiceImpl;
import com.ziroom.zrp.service.trading.utils.builder.DataTransferObjectBuilder;
import com.ziroom.zrp.trading.entity.BindPhoneEntity;
import com.ziroom.zrp.trading.entity.SchedulePersonEntity;
import com.zra.common.constant.ContractValueConstant;
import com.zra.common.utils.DateUtilFormate;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
 * @Date Created in 2017年11月01日 19:30
 * @since 1.0
 */
@Component("trading.bindPhoneServiceProxy")
public class BindPhoneServiceProxy implements BindPhoneService{

    private static final Logger LOGGER = LoggerFactory.getLogger(BindPhoneServiceProxy.class);

    @Resource(name="trading.bindPhoneServiceImpl")
    private BindPhoneServiceImpl bindPhoneServiceImpl;

    @Resource(name="houses.employeeService")
    private EmployeeService employeeService;

    @Resource(name = "trading.rentContractLogic")
    private RentContractLogic rentContractLogic;

    /**
     * 根据员工id查询员工400分机信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    @Override
    public String findBindPhoneByEmployeeId(String employeeId) {
        if (Check.NuNStrStrict(employeeId)) {
            return DataTransferObjectBuilder.buildErrorJsonStr("参数为空");
        }
        BindPhoneEntity bindPhoneEntity = this.bindPhoneServiceImpl.selectByEmployeeId(employeeId);
        return DataTransferObjectBuilder.buildOkJsonStr(bindPhoneEntity);
    }

    @Override
    public String findBindPhoneByEmployeeCode(String projectId,String employeeCode) {
        if (Check.NuNStr(employeeCode) || Check.NuNStr(projectId)){
            return DataTransferObjectBuilder.buildErrorJsonStr("参数为空");
        }
        String employeePhone = rentContractLogic.getEmployeePhone(projectId, employeeCode);
        return DataTransferObjectBuilder.buildOkJsonStr(employeePhone);
    }
}
