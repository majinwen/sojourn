package com.ziroom.minsu.report.order.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.basedata.dao.ConfCityDao;
import com.ziroom.minsu.report.basedata.dto.NationRegionCityRequest;
import com.ziroom.minsu.report.basedata.vo.NationRegionCityVo;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.common.util.NationRegionCityUtil;
import com.ziroom.minsu.report.house.vo.HouseLifeCycleVo;
import com.ziroom.minsu.report.order.dao.OrderDetailDao;
import com.ziroom.minsu.report.order.dto.OrderFinanceRequest;
import com.ziroom.minsu.report.order.dto.OrderFreshRequest;
import com.ziroom.minsu.report.order.dto.OrderInformationRequest;
import com.ziroom.minsu.report.order.dto.OrderRequest;
import com.ziroom.minsu.report.order.vo.OrderDetailVo;
import com.ziroom.minsu.report.order.vo.OrderFinanceVo;
import com.ziroom.minsu.report.order.vo.OrderFreshVo;
import com.ziroom.minsu.report.order.vo.OrderInformationVo;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.house.OrderTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.order.OrderPayStatusEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>房源 HouseDetailService</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/6.
 * @version 1.0
 * @since 1.0
 */
@Service("report.orderDetailService")
public class OrderDetailService implements ReportService <HouseLifeCycleVo,OrderRequest>{

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDetailService.class);
 
    @Resource(name="report.orderDetailDao")
    private OrderDetailDao orderDetailDao;

	@Resource(name = "report.confCityDao")
	private ConfCityDao confCityDao;

    /**
     * 订单明细信息
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public PagingResult<OrderDetailVo> getOrderStaticsInfo(OrderRequest orderRequest) {
    	if(Check.NuNObj(orderRequest)){
			LogUtil.info(LOGGER, " OrderDetailService getOrderStaticsInfo param:{}", JsonEntityTransform.Object2Json(orderRequest));
    		return null;
    	}
    	return orderDetailDao.getOrderStaticsInfo(orderRequest);
    }

	@Override
	public Long countDataInfo(OrderRequest par) {
		return null;
	}


	@Override
	public PagingResult<HouseLifeCycleVo> getPageInfo(OrderRequest par) {
		return null;
	}




	/**
	 * 订单信息报表
	 * @author lishaochuan
	 * @create 2017/3/7 16:28
	 * @param
	 * @return
	 */
	public PagingResult<OrderInformationVo> getOrderInformation(OrderInformationRequest request) {
        //获取当前的城市列表
        NationRegionCityRequest nationRegionCityRequest = new NationRegionCityRequest();
        nationRegionCityRequest.setCityCode(request.getCity());
        nationRegionCityRequest.setRegionFid(request.getRegion());
        List<NationRegionCityVo> nationRegionCityList =  confCityDao.getNationRegionCity(nationRegionCityRequest);
        if (Check.NuNCollection(nationRegionCityList)){
            return new PagingResult<OrderInformationVo>();
        }
        //填充到集合，方便翻译查询结果
        Map<String,NationRegionCityVo> map = new HashMap<>();
        List<String> cityList = new ArrayList<>();
        NationRegionCityUtil.fillNationRegionCity(nationRegionCityList,map,cityList);
        //填充城市code
        if (!Check.NuNStr(request.getRegion()) || !Check.NuNStr(request.getCity())){
            //当前参数城市相关配置不为空
            request.setCityList(cityList);
        }
        PagingResult<OrderInformationVo> orderInformation = orderDetailDao.getOrderInformation(request);
        //循环翻译国家、大区、城市
        if (!Check.NuNCollection(orderInformation.getRows())){
            for (OrderInformationVo vo : orderInformation.getRows()) {
                String cityCode = vo.getCityCode();
                if (!map.containsKey(cityCode)){
                    continue;
                }
                NationRegionCityVo regionCityVo = map.get(cityCode);
                vo.setNation(regionCityVo.getNationName());
                vo.setRegionName(regionCityVo.getRegionName());
                vo.setCityName(regionCityVo.getCityName());
            }
        }

        for (OrderInformationVo orderInformationVo:orderInformation.getRows()) {
            // 翻译订单状态名称
            if(!Check.NuNObj(orderInformationVo.getOrderStatus())){
                OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusByCode(ValueUtil.getintValue(orderInformationVo.getOrderStatus()));
                if(!Check.NuNObj(orderStatusEnum)){
                    orderInformationVo.setOrderStatusName(orderStatusEnum.getStatusName());
                }
            }
            // 翻译支付状态名称
            if(!Check.NuNObj(orderInformationVo.getPayStatus())){
                OrderPayStatusEnum orderPayStatusEnum = OrderPayStatusEnum.getPayStatusByCode(ValueUtil.getintValue(orderInformationVo.getPayStatus()));
                if(!Check.NuNObj(orderPayStatusEnum)){
                    orderInformationVo.setPayStatusName(orderPayStatusEnum.getStatusName());
                }
            }
            // 翻译租住方式名称
            if(!Check.NuNObj(orderInformationVo.getRentWay())){
                RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(ValueUtil.getintValue(orderInformationVo.getRentWay()));
                if(!Check.NuNObj(rentWayEnum)){
                    orderInformationVo.setRentWayName(rentWayEnum.getName());
                }
            }
            // 翻译预定类型名称
            if(!Check.NuNObj(orderInformationVo.getOrderType())){
                OrderTypeEnum orderTypeEnum = OrderTypeEnum.getOrderTypeByCode(ValueUtil.getintValue(orderInformationVo.getOrderType()));
                if(!Check.NuNObj(orderTypeEnum)){
                    orderInformationVo.setOrderTypeName(orderTypeEnum.getName());
                }
            }
        }

        return orderInformation;
	}


	/**
	 * 订单财务报表
	 * @author lishaochuan
	 * @create 2017/3/9 14:56
	 * @param
	 * @return
	 */
	public PagingResult<OrderFinanceVo> getOrderFinance(OrderFinanceRequest request) {
		//获取当前的城市列表
		NationRegionCityRequest nationRegionCityRequest = new NationRegionCityRequest();
		nationRegionCityRequest.setCityCode(request.getCity());
		nationRegionCityRequest.setRegionFid(request.getRegion());
		List<NationRegionCityVo> nationRegionCityList =  confCityDao.getNationRegionCity(nationRegionCityRequest);
		if (Check.NuNCollection(nationRegionCityList)){
			return new PagingResult<>();
		}
		//填充到集合，方便翻译查询结果
		Map<String,NationRegionCityVo> map = new HashMap<>();
		List<String> cityList = new ArrayList<>();
		NationRegionCityUtil.fillNationRegionCity(nationRegionCityList,map,cityList);
		//填充城市code
		if (!Check.NuNStr(request.getRegion()) || !Check.NuNStr(request.getCity())){
			//当前参数城市相关配置不为空
			request.setCityList(cityList);
		}
        PagingResult<OrderFinanceVo> orderFinance = orderDetailDao.getOrderFinance(request);
        //循环翻译国家、大区、城市
        if (!Check.NuNCollection(orderFinance.getRows())){
            for (OrderFinanceVo vo : orderFinance.getRows()) {
                String cityCode = vo.getCityCode();
                if (!map.containsKey(cityCode)) {
                    continue;
                }
                NationRegionCityVo regionCityVo = map.get(cityCode);
                vo.setNation(regionCityVo.getNationName());
                vo.setRegionName(regionCityVo.getRegionName());
                vo.setCityName(regionCityVo.getCityName());

                // 翻译订单状态名称
                if(!Check.NuNObj(vo.getOrderStatus())){
                    OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusByCode(ValueUtil.getintValue(vo.getOrderStatus()));
                    if(!Check.NuNObj(orderStatusEnum)){
                        vo.setOrderStatusName(orderStatusEnum.getStatusName());
                    }
                }
                // 翻译支付状态名称
                if(!Check.NuNObj(vo.getPayStatus())){
                    OrderPayStatusEnum orderPayStatusEnum = OrderPayStatusEnum.getPayStatusByCode(ValueUtil.getintValue(vo.getPayStatus()));
                    if(!Check.NuNObj(orderPayStatusEnum)){
                        vo.setPayStatusName(orderPayStatusEnum.getStatusName());
                    }
                }

            }
        }
        return orderFinance;
	}


    /**
     * 刷单信息报表
     * @author lishaochuan
     * @create 2017/3/14 10:03
     * @param 
     * @return 
     */
    public PagingResult<OrderFreshVo> getOrderFresh(OrderFreshRequest request) {
        //获取当前的城市列表
        NationRegionCityRequest nationRegionCityRequest = new NationRegionCityRequest();
        nationRegionCityRequest.setCityCode(request.getCity());
        nationRegionCityRequest.setRegionFid(request.getRegion());
        List<NationRegionCityVo> nationRegionCityList =  confCityDao.getNationRegionCity(nationRegionCityRequest);
        if (Check.NuNCollection(nationRegionCityList)){
            return new PagingResult<>();
        }
        //填充到集合，方便翻译查询结果
        Map<String,NationRegionCityVo> map = new HashMap<>();
        List<String> cityList = new ArrayList<>();
        NationRegionCityUtil.fillNationRegionCity(nationRegionCityList,map,cityList);
        //填充城市code
        if (!Check.NuNStr(request.getRegion()) || !Check.NuNStr(request.getCity())){
            //当前参数城市相关配置不为空
            request.setCityList(cityList);
        }
        PagingResult<OrderFreshVo> orderFresh = orderDetailDao.getOrderFresh(request);
        //循环翻译国家、大区、城市
        if (!Check.NuNCollection(orderFresh.getRows())){
            for (OrderFreshVo vo : orderFresh.getRows()) {
                String cityCode = vo.getCityCode();
                if (!map.containsKey(cityCode)) {
                    continue;
                }
                NationRegionCityVo regionCityVo = map.get(cityCode);
                vo.setNation(regionCityVo.getNationName());
                vo.setRegionName(regionCityVo.getRegionName());
                vo.setCityName(regionCityVo.getCityName());
            }
        }
        return orderFresh;
    }
}
