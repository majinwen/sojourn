package com.ziroom.minsu.services.customer.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerLocationEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.customer.dto.CustomerBaseMsgDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>地理位置记录</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/29.
 * @version 1.0
 * @since 1.0
 */
@Repository("customer.customerLocationDao")
public class CustomerLocationDao {

    private String SQLID="customer.customerLocationDao.";

    @Autowired
    @Qualifier("customer.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;




    /**
     * 保存
     * @author afi
     * @created 2016年11月29日 下午4:22:02
     *
     * @param customerLocationEntity
     */
    public int saveCustomerLocation(CustomerLocationEntity customerLocationEntity){
        return mybatisDaoContext.save(SQLID+"saveCustomerLocation", customerLocationEntity);
    }



    /**
     * 修改
     * @author afi
     * @created 2016年11月29日 下午4:22:02
     *
     * @param customerLocationEntity
     */
    public int updateCustomerLocation(CustomerLocationEntity customerLocationEntity){
        return mybatisDaoContext.save(SQLID+"updateCustomerLocation", customerLocationEntity);
    }


    /**
     * 异常的ip
     * @author afi
     * @created 2016年11月29日 下午4:22:02
     * @param fid
     */
    public int updateStatusByFid(String fid,int synStatus){
        if (Check.NuNStr(fid)){
            throw new BusinessException("fid is null updateErrorIpByFid");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("fid",fid);
        par.put("synStatus",synStatus);
        return mybatisDaoContext.update(SQLID+"updateStatusByFid", par);
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
        //当前参数异常
        if (Check.NuNStr(uid) && Check.NuNStr(deviceNo)){
            return null;
        }
        if (!Check.NuNStr(uid)){
            return this.getCustomerLocationOneHasUid(uid);
        }else if (!Check.NuNStr(deviceNo)){
            return this.getCustomerLocationOneHasDevice(deviceNo);
        }
        return null;
    }


    /**
     * 分页获取经纬度
     * @author afi
     * @return
     */
    public List<CustomerLocationEntity> getIpLocation(int limit){
        return mybatisDaoContext.findAll(SQLID + "getIpLocation", CustomerLocationEntity.class,limit);
    }



    /**
     * 获取需要填充的条数
     * @author afi
     * @return
     */
    public Long countIpLocation(){
        return mybatisDaoContext.findOne(SQLID + "countIpLocation", Long.class);
    }


    /**
     * 校验1个小时内是否保存过
     * @author afi
     * @created 2016年11月29日 下午4:22:02
     * @param uid
     * @return
     */
    public CustomerLocationEntity getCustomerLocationOneHasUid(String uid){
        //当前参数异常
        if (Check.NuNStr(uid)){
            return null;
        }
        return mybatisDaoContext.findOne(SQLID+"getCustomerLocationOneHasUid",CustomerLocationEntity.class, uid);
    }





    /**
     * 校验1个小时内是否保存过
     * @author afi
     * @created 2016年11月29日 下午4:22:02
     * @param deviceNo
     * @return
     */
    public CustomerLocationEntity getCustomerLocationOneHasDevice(String deviceNo){
        //当前参数异常
        if (Check.NuNStr(deviceNo)){
            return null;
        }
        return mybatisDaoContext.findOne(SQLID+"getCustomerLocationOneHasDevice",CustomerLocationEntity.class, deviceNo);
    }
}
