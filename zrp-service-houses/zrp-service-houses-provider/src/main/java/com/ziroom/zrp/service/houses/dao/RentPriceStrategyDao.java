package com.ziroom.zrp.service.houses.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.houses.entity.RentPriceStrategyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p>价格调幅配置持久层</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @Date Created in 2017年09月14日 11:15
 * @version 1.0
 * @since 1.0
 */
@Repository("houses.rentPriceStrategyDao")
public class RentPriceStrategyDao {
	private String SQLID = "houses.rentPriceStrategyDao.";

	@Autowired
	@Qualifier("houses.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	public RentPriceStrategyEntity getRentPriceStrategy(RentPriceStrategyEntity entity) {
		return mybatisDaoContext.findOneSlave(SQLID + "getRentPriceStrategy", RentPriceStrategyEntity.class, entity);
	}
}
