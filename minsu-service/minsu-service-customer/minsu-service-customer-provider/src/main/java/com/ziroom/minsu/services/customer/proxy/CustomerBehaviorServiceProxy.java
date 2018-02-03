package com.ziroom.minsu.services.customer.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBehaviorEntity;
import com.ziroom.minsu.entity.customer.CustomerBehaviorOperationLogEntity;
import com.ziroom.minsu.services.customer.api.inner.CustomerBehaviorService;
import com.ziroom.minsu.services.customer.dto.CustomerBehaviorRequest;
import com.ziroom.minsu.services.customer.entity.CustomerBehaviorVo;
import com.ziroom.minsu.services.customer.service.CustomerBehaviorServiceImpl;

import com.ziroom.minsu.valenum.customer.CustomerBehaviorAttributeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.List;

/**
 * <p>客户认证服务代理层</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Component("customer.customerBehaviorServiceProxy")
public class CustomerBehaviorServiceProxy implements CustomerBehaviorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerBehaviorServiceProxy.class);

    @Resource(name = "customer.customerBehaviorServiceImpl")
    CustomerBehaviorServiceImpl customerBehaviorServiceImpl;

    /**
     *
     * 保存用户行为
     *
     * @author zhangyl2
     * @created 2017年10月12日 15:24
     * @param
     * @return
     */
    @Override
    public String saveCustomerBehavior(String paramJson) {
        LogUtil.info(LOGGER, "[用户行为服务]saveCustomerBehavior，参数={}", paramJson);

        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(paramJson)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        CustomerBehaviorEntity customerBehaviorEntity = JsonEntityTransform.json2Object(paramJson, CustomerBehaviorEntity.class);
        if (Check.NuNObj(customerBehaviorEntity.getProveFid())
                || Check.NuNObj(customerBehaviorEntity.getAttribute())
                || Check.NuNObj(customerBehaviorEntity.getRole())
                || Check.NuNObj(customerBehaviorEntity.getUid())
                || Check.NuNObj(customerBehaviorEntity.getType())
                || Check.NuNObj(customerBehaviorEntity.getScore())
                || Check.NuNObj(customerBehaviorEntity.getCreateFid())
                || Check.NuNObj(customerBehaviorEntity.getCreateType())
                || Check.NuNStr(customerBehaviorEntity.getRemark())) {

            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数非法");
            return dto.toJsonString();
        }

        int result = customerBehaviorServiceImpl.saveCustomerBehavior(customerBehaviorEntity);
        dto.putValue("result", result);
        return dto.toJsonString();
    }

    /**
     *
     * 定时任务补偿
     * 查询过去一段时间某类型行为记录
     *
     * @author zhangyl2
     * @created 2017年10月12日 18:08
     * @param
     * @return
     */
    @Override
    public String queryCustomerBehaviorProveFidsForJob(String paramJson) {
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(paramJson)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        CustomerBehaviorRequest customerBehaviorRequest = JsonEntityTransform.json2Object(paramJson, CustomerBehaviorRequest.class);

        if(Check.NuNObj(customerBehaviorRequest.getType())
                || Check.NuNStr(customerBehaviorRequest.getStartTime())
                || Check.NuNStr(customerBehaviorRequest.getEndTime())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数缺失");
            return dto.toJsonString();
        }

        List<String> proveFids = customerBehaviorServiceImpl.queryCustomerBehaviorProveFidsForJob(customerBehaviorRequest);
        dto.putValue("proveFidList", proveFids);
        return dto.toJsonString();
    }
    
    /**
	 * 根据uid查询所有行为日记录
	 *
	 * @author loushuai
	 * @created 2017年10月13日 上午11:06:31
	 *
	 * @param paramJson
	 * @return
	 */
	@Override
	public String getCustomerBehaviorList(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		CustomerBehaviorRequest customerBehaviorRequest = JsonEntityTransform.json2Entity(paramJson, CustomerBehaviorRequest.class);
		if(Check.NuNObj(customerBehaviorRequest) || Check.NuNStr(customerBehaviorRequest.getUid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		PagingResult<CustomerBehaviorVo> pageResult = customerBehaviorServiceImpl.getCustomerBehaviorList(customerBehaviorRequest);
		dto.putValue("total", pageResult.getTotal());
	    dto.putValue("customerBehaviorList", pageResult.getRows());
		return dto.toJsonString();
	}

	/**
	 * 
	 * 修改用户行为
	 * 
	 * @author zhangyl2
	 * @created 2017年10月14日 17:28
	 * @param 
	 * @return 
	 */
    @Override
    public String updateCustomerBehaviorAttr(String paramJson) {
        DataTransferObject dto = new DataTransferObject();
        CustomerBehaviorOperationLogEntity logEntity = JsonEntityTransform.json2Entity(paramJson, CustomerBehaviorOperationLogEntity.class);
        if(Check.NuNObj(logEntity)
                || Check.NuNStr(logEntity.getBehaviorFid())
                || Check.NuNStr(logEntity.getEmpCode())
                || Check.NuNStr(logEntity.getEmpName())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        CustomerBehaviorEntity customerBehaviorEntity = new CustomerBehaviorEntity();
        customerBehaviorEntity.setFid(logEntity.getBehaviorFid());
        CustomerBehaviorVo oldBehavior = customerBehaviorServiceImpl.getOneCustomerBehavior(customerBehaviorEntity);

        //目前只能限制行为改为中性行为
        if(oldBehavior.getAttribute() == CustomerBehaviorAttributeEnum.LIMIT_BEHAVIOR.getCode()
                && oldBehavior.getOperated() == 0){

            customerBehaviorEntity.setAttribute(CustomerBehaviorAttributeEnum.NEUTRAL_BEHAVIOR.getCode());
            //分值改为0
            customerBehaviorEntity.setScore(0d);

            int result = customerBehaviorServiceImpl.updateCustomerBehaviorAttr(customerBehaviorEntity);

            if(result == 1){
                // 保存操作日志
                logEntity.setFromAttribute(CustomerBehaviorAttributeEnum.LIMIT_BEHAVIOR.getCode());
                logEntity.setToAttribute(CustomerBehaviorAttributeEnum.NEUTRAL_BEHAVIOR.getCode());
                result = customerBehaviorServiceImpl.insertCustomerBehaviorLog(logEntity);
            }

            if(result == 1){
                dto.putValue("result", result);
                return dto.toJsonString();
            }else{
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("修改失败");
                return dto.toJsonString();
            }

        }else{
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("非法的修改");
            return dto.toJsonString();
        }
    }

}
