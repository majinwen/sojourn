package com.ziroom.minsu.services.basedata.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.base.NetProxyIpPortEntity;
import com.ziroom.minsu.entity.conf.ConfDicEntity;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>配置结构表</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/21.
 * @version 1.0
 * @since 1.0
 */
@Repository("basedata.netProxyIpPortDao")
public class NetProxyIpPortDao {


    private String SQLID="basedata.netProxyIpPortDao.";

    @Autowired
    @Qualifier("basedata.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 查找代理ip
     * @author jixd
     * @created 2017年04月19日 00:15:49
     * @param
     * @return
     */
    public List<NetProxyIpPortEntity> listNetProxyIp(){
        return mybatisDaoContext.findAll(SQLID + "listNetProxyIp",NetProxyIpPortEntity.class);
    }

    /**
     * 增加代理ip
     * @author jixd
     * @created 2017年04月19日 10:47:06
     * @param
     * @return
     */
    public int saveNetProxyIp(NetProxyIpPortEntity netIp){
        if (Check.NuNStr(netIp.getFid())){
            netIp.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID + "saveNetProxyIp",netIp);
    }

    /**
     * 更新代理ip地址
     * @author jixd
     * @created 2017年04月19日 15:31:59
     * @param
     * @return
     */
    public int updateNetProxyIp(NetProxyIpPortEntity netIp){
        return mybatisDaoContext.update(SQLID + "updateNetProxyIp",netIp);
    }

    /**
     *
     * @author jixd
     * @created 2017年04月19日 18:14:11
     * @param
     * @return
     */
    public int updateCountByProxyIp(String proxyIp){
        Map<String,Object> par = new HashMap<>();
        par.put("proxyIp",proxyIp);
        return mybatisDaoContext.update(SQLID + "updateCountByProxyIp",par);
    }

}
