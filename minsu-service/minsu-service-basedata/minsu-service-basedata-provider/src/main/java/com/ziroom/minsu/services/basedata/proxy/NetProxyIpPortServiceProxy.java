package com.ziroom.minsu.services.basedata.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.base.NetProxyIpPortEntity;
import com.ziroom.minsu.services.basedata.api.inner.NetProxyIpPortService;
import com.ziroom.minsu.services.basedata.service.NetProxyIpPortServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>ip代理类</p>
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
@Component("basedata.netProxyIpPortServiceProxy")
public class NetProxyIpPortServiceProxy implements NetProxyIpPortService{

    private static final Logger LOGGER = LoggerFactory.getLogger(NetProxyIpPortServiceProxy.class);

    @Resource(name="basedata.netProxyIpPortServiceImpl")
    private NetProxyIpPortServiceImpl netProxyIpPortServiceImpl;

    @Override
    public String listNetProxyIp() {
        DataTransferObject dto = new DataTransferObject();
        List<NetProxyIpPortEntity> list = netProxyIpPortServiceImpl.listNetProxyIp();
        Set<String> set = new HashSet<>();
        if (!Check.NuNCollection(list)){
            for (NetProxyIpPortEntity entity : list){
                set.add(entity.getProxyIp()+":"+entity.getProxyPort());
            }
        }
        dto.putValue("set",set);
        return dto.toJsonString();
    }

    @Override
    public String saveNetProxyIp(String paramJson) {
        DataTransferObject dto = new DataTransferObject();
        NetProxyIpPortEntity netProxyIpPortEntity = JsonEntityTransform.json2Object(paramJson, NetProxyIpPortEntity.class);
        if (Check.NuNObj(netProxyIpPortEntity)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        int count = netProxyIpPortServiceImpl.saveNetProxyIp(netProxyIpPortEntity);
        dto.putValue("count",count);
        return dto.toJsonString();
    }

    @Override
    public String updateNetProxyIp(String paramJson) {
        DataTransferObject dto = new DataTransferObject();
        NetProxyIpPortEntity netProxyIpPortEntity = JsonEntityTransform.json2Object(paramJson, NetProxyIpPortEntity.class);
        if (Check.NuNObj(netProxyIpPortEntity)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        int count = netProxyIpPortServiceImpl.updateNetProxyIp(netProxyIpPortEntity);
        dto.putValue("count",count);
        return dto.toJsonString();
    }

    @Override
    public String updateCountByProxyIp(String proxyIp) {
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(proxyIp)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }

        int count = netProxyIpPortServiceImpl.updateCountByProxyIp(proxyIp);
        dto.putValue("count",count);
        return dto.toJsonString();
    }
}
