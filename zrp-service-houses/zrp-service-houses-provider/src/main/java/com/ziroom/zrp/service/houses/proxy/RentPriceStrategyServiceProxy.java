package com.ziroom.zrp.service.houses.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.houses.entity.RentPriceStrategyEntity;
import com.ziroom.zrp.service.houses.api.RentPriceStrategyService;
import com.ziroom.zrp.service.houses.service.RentPriceStrategyServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>价格调幅配置逻辑层</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年09月14日 11:16
 * @since 1.0
 */
@Component("houses.rentPriceStrategyServiceProxy")
public class RentPriceStrategyServiceProxy implements RentPriceStrategyService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RentPriceStrategyServiceProxy.class);

	@Resource(name = "houses.rentPriceStrategyServiceImpl")
	private RentPriceStrategyServiceImpl rentPriceStrategyService;

	@Override
	public String getRentPriceStrategy(String paramJson) {
		LogUtil.info(LOGGER, "【getRentPriceStrategy】参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			RentPriceStrategyEntity rentPriceStrategyEntity = JsonEntityTransform.json2Entity(paramJson,RentPriceStrategyEntity.class);
			if(Check.NuNObj(rentPriceStrategyEntity)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto.toJsonString();
			}
			if(Check.NuNStr(rentPriceStrategyEntity.getProjectId())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("项目id为空");
				return dto.toJsonString();
			}
			if(Check.NuNObj(rentPriceStrategyEntity.getRentType())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("出租方式为空");
				return dto.toJsonString();
			}
			RentPriceStrategyEntity rentPriceStrategyEntity1 = rentPriceStrategyService.getRentPriceStrategy(rentPriceStrategyEntity);
			dto.putValue("rentPriceStrategy", rentPriceStrategyEntity1);
		} catch (BusinessException e) {
			LogUtil.error(LOGGER, "【getRentPriceStrategy】 error:{},paramJson={}", e, paramJson);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
			return dto.toString();
		}
		return dto.toJsonString();
	}

}
