package com.ziroom.minsu.troy.invite.service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.dto.CustomerBaseMsgDto;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/3.
 * @version 1.0
 * @since 1.0
 */
@Service("api.customerQueryService")
public class CustomerQueryService {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(CustomerQueryService.class);

    @Resource(name = "customer.customerInfoService")
    private CustomerInfoService customerInfoService;


    /**
     * 通过用户信息获取用户
     * @author afi
     * @param realName
     * @param customerMobile
     * @return
     */
    public List<CustomerBaseMsgEntity>  getCustomerList(String realName,String customerMobile){
        CustomerBaseMsgDto customerBaseMsgDto = new CustomerBaseMsgDto();
        customerBaseMsgDto.setRealName(realName);
        customerBaseMsgDto.setCustomerMobile(customerMobile);
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(customerInfoService.selectByCondition(JsonEntityTransform.Object2Json(customerBaseMsgDto)));
        List<CustomerBaseMsgEntity> listCustomerBaseMsg = dto.parseData("listCustomerBaseMsg", new TypeReference<List<CustomerBaseMsgEntity>>() {});
        return listCustomerBaseMsg;
    }


    /**
     * 通过用户信息获取用户
     * @author afi
     * @param uidSet
     * @return
     */
    public Map<String,CustomerBaseMsgEntity> getCustomerMap(Set<String> uidSet){
        Map<String,CustomerBaseMsgEntity>  rst = new HashMap<>();
        if (Check.NuNCollection(uidSet)){
            return rst;
        }
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(customerInfoService.getCustomerListByUidList(JsonEntityTransform.Object2Json(new ArrayList<String>(uidSet) )));
        List<CustomerBaseMsgEntity> listCustomerBaseMsg = dto.parseData("customerList", new TypeReference<List<CustomerBaseMsgEntity>>() {});
        if (!Check.NuNCollection(listCustomerBaseMsg)){
            for (CustomerBaseMsgEntity msgEntity : listCustomerBaseMsg) {
                rst.put(msgEntity.getUid(),msgEntity);
            }
        }
        return rst;
    }

}
