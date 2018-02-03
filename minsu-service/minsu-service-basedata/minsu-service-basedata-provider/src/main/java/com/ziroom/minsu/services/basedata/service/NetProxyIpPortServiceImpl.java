package com.ziroom.minsu.services.basedata.service;

import com.ziroom.minsu.entity.base.NetProxyIpPortEntity;
import com.ziroom.minsu.services.basedata.dao.HotRegionDao;
import com.ziroom.minsu.services.basedata.dao.NetProxyIpPortDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author jixd
 * @created 2017年04月19日 15:37:17
 * @param
 * @return
 */
@Service("basedata.netProxyIpPortServiceImpl")
public class NetProxyIpPortServiceImpl {


    @Resource(name = "basedata.netProxyIpPortDao")
    private NetProxyIpPortDao netProxyIpPortDao;

    /**
     * 查询有效的代理ip
     * @author jixd
     * @created 2017年04月19日 15:38:54
     * @param
     * @return
     */
    public List<NetProxyIpPortEntity> listNetProxyIp(){
        return netProxyIpPortDao.listNetProxyIp();
    }

    /**
     * 保存代理 ip地址
     * @author jixd
     * @created 2017年04月19日 15:40:38
     * @param
     * @return
     */
    public int saveNetProxyIp(NetProxyIpPortEntity netIp){
        return netProxyIpPortDao.saveNetProxyIp(netIp);
    }

    /**
     * 更新代理ip地址
     * @author jixd
     * @created 2017年04月19日 15:41:18
     * @param
     * @return
     */
    public int updateNetProxyIp(NetProxyIpPortEntity netIp){
        return netProxyIpPortDao.updateNetProxyIp(netIp);
    }

    /**
     * 更新count
     * @author jixd
     * @created 2017年04月19日 18:16:50
     * @param
     * @return
     */
    public int updateCountByProxyIp(String proxyIp){
        return netProxyIpPortDao.updateCountByProxyIp(proxyIp);
    }

}
