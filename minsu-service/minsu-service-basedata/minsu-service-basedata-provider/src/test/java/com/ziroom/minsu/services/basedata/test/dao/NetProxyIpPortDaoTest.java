package com.ziroom.minsu.services.basedata.test.dao;

import com.ziroom.minsu.entity.base.NetProxyIpPortEntity;
import com.ziroom.minsu.services.basedata.dao.NetProxyIpPortDao;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by homelink on 2017/4/19.
 */
public class NetProxyIpPortDaoTest extends BaseTest{

    @Resource(name = "basedata.netProxyIpPortDao")
    private NetProxyIpPortDao netProxyIpPortDao;
    @Test
    public void testsaveNetProxyIp(){
        NetProxyIpPortEntity netProxyIpPortEntity = new NetProxyIpPortEntity();
        netProxyIpPortEntity.setProxyIp("221.226.82.130");
        netProxyIpPortEntity.setProxyPort(8998);
        netProxyIpPortEntity.setIsValid(1);
        netProxyIpPortDao.saveNetProxyIp(netProxyIpPortEntity);
    }

    @Test
    public void testlistNetProxyIp(){
        List<NetProxyIpPortEntity> netProxyIpPortEntities = netProxyIpPortDao.listNetProxyIp();

    }

    @Test
    public void testupdateCountByProxyIp(){
        netProxyIpPortDao.updateCountByProxyIp("121.199.28.155");
    }
}
