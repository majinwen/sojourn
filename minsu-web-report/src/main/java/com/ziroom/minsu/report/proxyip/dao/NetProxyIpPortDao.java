package com.ziroom.minsu.report.proxyip.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.base.NetProxyIpPortEntity;

/**
 * 
 * <p>代理ipMapper</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangyl
 * @since 1.0
 * @version 1.0
 */
@Repository("report.netProxyIpPortDao")
public class NetProxyIpPortDao {

	private String SQLID = "report.netProxyIpPortDao.";

	@Autowired
    @Qualifier("minsuReport.basedata.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 保存代理ip
	 *
	 * @author zhangyl
	 * @created 2017年7月5日 下午4:21:41
	 *
	 * @param netProxyIpPortEntity
	 * @return
	 */
	public int saveNetProxyIp(NetProxyIpPortEntity netProxyIpPortEntity){
		return mybatisDaoContext.save(SQLID + "saveNetProxyIp", netProxyIpPortEntity);
	}
	
	/**
	 * 
	 * 获取有效代理ip地址列表, 排除从该网站抓的ip
	 *
	 * @author zhangyl
	 * @created 2017年7月13日 下午8:05:30
	 *
	 * @param size
	 * @return
	 */
	public List<NetProxyIpPortEntity> listNetProxyIp(){
		return mybatisDaoContext.findAll(SQLID + "listNetProxyIp", NetProxyIpPortEntity.class, null);
	}
}
