package com.ziroom.minsu.services.customer.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.services.search.vo.BrandLandlordVo;
import com.ziroom.minsu.services.search.vo.CustomerDbInfoVo;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/5.
 * @version 1.0
 * @since 1.0
 */
@Repository("search.customerDbDao")
public class CustomerDbDao {

    private String SQLID="search.evaluateDbDao.";

    @Autowired
    @Qualifier("searchCustomer.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 获取房东的头像信息
     * @author afi
     * @param uid
     * @return
     */
    public CustomerDbInfoVo getCustomerInfo(String uid){
        if(Check.NuNStr(uid)){
            return null;
        }
        return mybatisDaoContext.findOneSlave(SQLID + "getCustomerInfo", CustomerDbInfoVo.class, uid);
    }


    /**
     * 获取当前用户的收藏信息
     * @author afi
     * @param uid
     * @return
     */
    public List<String> getCustomerCollect(String uid){
        if(Check.NuNStr(uid)){
            return null;
        }
        return mybatisDaoContext.findAll(SQLID + "getCustomerCollect", String.class, uid);
    }
    
    /**
     * 
     * 查询房东所属的品牌列表
     *
     * @author zl
     * @created 2017年4月1日 下午4:51:21
     *
     * @return
     */
    public List<BrandLandlordVo> getBrandLandLordList(String uid){         
        return mybatisDaoContext.findAll(SQLID + "getBrandLandLordList", BrandLandlordVo.class,uid);
    }
    

}
