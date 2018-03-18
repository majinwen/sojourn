package com.ziroom.zrp.service.houses.api;

/**
 * <p>价格调幅配置接口</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @Date Created in 2017年09月14日 11:20
 * @version 1.0
 * @since 1.0
 */
public interface RentPriceStrategyService {
	/**
	 * 获取价格调幅策略
	 * @param paramJson json {projectId,rentType}
	 * @return
	 */
	String getRentPriceStrategy(String paramJson);
}
