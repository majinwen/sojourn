package com.ziroom.zrp.service.houses.service;

import com.ziroom.zrp.houses.entity.RentPriceStrategyEntity;
import com.ziroom.zrp.service.houses.dao.RentPriceStrategyDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>价格调幅配置事务层</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @Date Created in 2017年09月14日 11:16
 * @version 1.0
 * @since 1.0
 */
@Service("houses.rentPriceStrategyServiceImpl")
public class RentPriceStrategyServiceImpl {

	@Resource(name = "houses.rentPriceStrategyDao")
	private RentPriceStrategyDao rentPriceStrategyDao;

	public RentPriceStrategyEntity getRentPriceStrategy(RentPriceStrategyEntity rentPriceStrategyEntity) {
		return this.rentPriceStrategyDao.getRentPriceStrategy(rentPriceStrategyEntity);
	}
}
