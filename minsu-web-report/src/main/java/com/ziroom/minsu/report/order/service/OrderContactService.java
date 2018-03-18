package com.ziroom.minsu.report.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.report.basedata.dao.ConfCityDao;
import com.ziroom.minsu.report.basedata.vo.NationRegionCityVo;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.common.util.NationRegionCityUtil;
import com.ziroom.minsu.report.order.dao.OrderContactDao;
import com.ziroom.minsu.report.order.dto.OrderContactRequest;
import com.ziroom.minsu.report.order.vo.OrderContactVo;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/3/10.
 * @version 1.0
 * @since 1.0
 */
@Service("report.orderContactService")
public class OrderContactService  implements ReportService <OrderContactVo,OrderContactRequest>{


    private static final Logger LOGGER = LoggerFactory.getLogger(OrderContactService.class);

    @Resource(name = "report.orderContactDao")
    private OrderContactDao orderContactDao;

    @Resource(name = "report.confCityDao")
    private ConfCityDao confCityDao;


    /**
     * 分页查询
     * @author afi
     * @param orderContactRequest
     * @return
     */
    public PagingResult<OrderContactVo> getOrderContactByPage(OrderContactRequest orderContactRequest){
        //获取当前的城市列表
        List<NationRegionCityVo>  nationRegionCityList =  confCityDao.getNationRegionCity(orderContactRequest);
        if (Check.NuNCollection(nationRegionCityList)){
            return new PagingResult<>();
        }
        Map<String,NationRegionCityVo> map = new HashMap<>();
        List<String> cityList = new ArrayList<>();
        NationRegionCityUtil.fillNationRegionCity(nationRegionCityList,map,cityList);
        //填充城市code
        if (!orderContactRequest.checkEmpty()){
            //当前参数城市相关配置不为空
            orderContactRequest.setCityList(cityList);
        }
        PagingResult<OrderContactVo>  pagingResult = orderContactDao.getOrderContactByPage(orderContactRequest);
        if (!Check.NuNCollection(pagingResult.getRows())){
            for (OrderContactVo vo : pagingResult.getRows()) {
                String cityCode = vo.getCityCode();
                if (!map.containsKey(cityCode)){
                    continue;
                }
                fillContact(map, vo, cityCode);
            }
        }
        return pagingResult;
    }



    /**
     * 不分页查询
     * @author afi
     * @param orderContactRequest
     * @return
     */
    public List<OrderContactVo> getOrderContactList(OrderContactRequest orderContactRequest){

        //获取当前的城市列表
        List<NationRegionCityVo>  nationRegionCityList =  confCityDao.getNationRegionCity(orderContactRequest);
        if (Check.NuNCollection(nationRegionCityList)){
            return new ArrayList<>();
        }
        Map<String,NationRegionCityVo> map = new HashMap<>();
        List<String> cityList = new ArrayList<>();
        NationRegionCityUtil.fillNationRegionCity(nationRegionCityList,map,cityList);
        //填充城市code
        if (!orderContactRequest.checkEmpty()){
            //当前参数城市相关配置不为空
            orderContactRequest.setCityList(cityList);
        }
        List<OrderContactVo>  contactList = orderContactDao.getOrderContactList(orderContactRequest);
        if (!Check.NuNCollection(contactList)){
            for (OrderContactVo vo : contactList) {
                String cityCode = vo.getCityCode();
                if (!map.containsKey(cityCode)){
                    continue;
                }
                fillContact(map, vo, cityCode);
            }
        }
        return contactList;
    }

    /**
     * 填充当前的数据信息
     * @author afi
     * @param map
     * @param vo
     * @param cityCode
     */
    private void fillContact(Map<String, NationRegionCityVo> map, OrderContactVo vo, String cityCode) {
        NationRegionCityVo regionCityVo = map.get(cityCode);
        vo.setRegionName(regionCityVo.getRegionName());
        vo.setNationName(regionCityVo.getNationName());
        vo.setCityName(regionCityVo.getCityName());
    }



	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#getPageInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public PagingResult<OrderContactVo> getPageInfo(OrderContactRequest par) {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#countDataInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public Long countDataInfo(OrderContactRequest par) {
		// TODO Auto-generated method stub
		return null;
	}


}
