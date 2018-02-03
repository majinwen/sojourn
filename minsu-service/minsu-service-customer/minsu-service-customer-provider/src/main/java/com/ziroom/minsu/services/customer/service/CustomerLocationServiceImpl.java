package com.ziroom.minsu.services.customer.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerLocationEntity;
import com.ziroom.minsu.services.customer.dao.CustomerLocationDao;

/**
 * <p>用户位置收集</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/30.
 * @version 1.0
 * @since 1.0
 */
@Service("customer.customerLocationServiceImpl")
public class CustomerLocationServiceImpl {

	private static Logger logger = LoggerFactory.getLogger(CustomerLocationServiceImpl.class);

    @Resource(name="customer.customerLocationDao")
    private CustomerLocationDao customerLocationDao;


    /**
     * 异常的ip
     * @author afi
     * @created 2016年11月29日 下午4:22:02
     * @param fid
     */
    public int updateStatusByFid(String fid,int synStatus){
        return customerLocationDao.updateStatusByFid(fid,synStatus);
    }



    /**
     * 分页获取经纬度
     * @author afi
     * @return
     */
    public List<CustomerLocationEntity> getIpLocation(int limit){
        return customerLocationDao.getIpLocation(limit);
    }



    /**
     * 获取需要填充的条数
     * @author afi
     * @return
     */
    public Long countIpLocation(){
        return customerLocationDao.countIpLocation();
    }




    /**
     * 保存当前的位置信息
     * @param customerLocationEntity
     * @return
     */
    public int saveCustomerLocation(CustomerLocationEntity customerLocationEntity){
        if (Check.NuNObj(customerLocationEntity)){
            return  0;
        }
        if (!Check.NuNStr(customerLocationEntity.getPhoneModel()) && customerLocationEntity.getPhoneModel().length() > 49){
            String model = customerLocationEntity.getPhoneModel();
            model = model.replace(" ","");
            if (model.length() > 49){
                model = model.substring(0,49);
            }
            customerLocationEntity.setPhoneModel(model);
        }
        
        if(!Check.NuNStr(customerLocationEntity.getImei()) && customerLocationEntity.getImei().length() > 50){
        	LogUtil.info(logger, "saveCustomerLocation参数异常:imei{}", customerLocationEntity.getImei());
        	customerLocationEntity.setImei("");
        }
        return customerLocationDao.saveCustomerLocation(customerLocationEntity);
    }


    /**
     * 修改当前的位置信息
     * @param customerLocationEntity
     * @return
     */
    public int updateCustomerLocation(CustomerLocationEntity customerLocationEntity){
        return customerLocationDao.updateCustomerLocation(customerLocationEntity);
    }


    /**
     * 校验1个小时内是否保存过
     * @author afi
     * @created 2016年11月29日 下午4:22:02
     * @param uid
     * @param deviceNo
     * @return
     */
    public CustomerLocationEntity getCustomerLocationOneHas(String uid,String deviceNo){
        return customerLocationDao.getCustomerLocationOneHas(uid,deviceNo);
    }

}
