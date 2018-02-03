package com.ziroom.minsu.services.customer.service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.customer.CustomerBlackEntity;
import com.ziroom.minsu.services.customer.dao.CustomerBlackDao;
import com.ziroom.minsu.services.customer.dto.CustomerBlackDto;
import com.ziroom.minsu.services.customer.entity.CustomerBlackVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>黑名单服务类</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@Service("customer.customerBlackServiceImpl")
public class CustomerBlackServiceImpl {

    @Resource(name="customer.customerBlackDao")
    private CustomerBlackDao customerBlackDao;

    /**
     * @author jixd
     * @created 2016年10月27日 下午4:18:56
     * 根据uid查询是黑名单实体
     * @param uid
     * @return
     */
    public CustomerBlackEntity findCustomerBlackByUid(String uid){
        return customerBlackDao.selectByUid(uid);
    }

    /**
     * @author jixd
     * @created 2016年10月27日 下午4:18:56
     * @param customerBlackEntity
     * @return
     */
    public int saveCustomerBlack(CustomerBlackEntity customerBlackEntity){
        return customerBlackDao.insertSelective(customerBlackEntity);
    }


    /**
     * 分页查询信息
     * @author afi
     * @param customerBlackDto
     * @return
     */
    public PagingResult<CustomerBlackVo> queryCustomerBlackList(CustomerBlackDto customerBlackDto){
        return customerBlackDao.queryCustomerBlackList(customerBlackDto);
    }


    /**
     * 获取当前的黑名单信息
     * @author afi
     * @param fid
     * @return
     */
    public CustomerBlackVo getCustomerBlackVo(String fid){
        return customerBlackDao.getCustomerBlackVo(fid);
    }

    /**
     * 修改黑名单
     * @Author lunan【lun14@ziroom.com】
     * @Date 2017/1/12 20:26
     */
    public void upBlack(CustomerBlackEntity black){
        customerBlackDao.updateByFid(black);
    }

    /**
     * 根据imei查询黑明单
     * @author wangwt
     * @created 2017年08月02日 15:24:47
     * @param
     * @return
     */
    public CustomerBlackEntity findCustomerBlackByImei(String imei) {
        return customerBlackDao.selectByImei(imei);
    }
}
