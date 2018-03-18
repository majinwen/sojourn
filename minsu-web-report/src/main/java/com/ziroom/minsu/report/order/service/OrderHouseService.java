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
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.report.basedata.dao.ConfCityDao;
import com.ziroom.minsu.report.basedata.entity.CityNumEntity;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.vo.CityRegionVo;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.house.entity.HouseNumEntity;
import com.ziroom.minsu.report.order.dao.OrderHouseDao;
import com.ziroom.minsu.report.order.dto.OrderHouseRequest;
import com.ziroom.minsu.report.order.entity.OrderHouseEntity;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;

/**
 * <p>订单数量</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/1/19.
 * @version 1.0
 * @since 1.0
 */
@Service("report.orderHouseService")
public class OrderHouseService implements ReportService <OrderHouseEntity,OrderHouseRequest>{


    private static final Logger LOGGER = LoggerFactory.getLogger(OrderHouseService.class);

    @Resource(name = "report.orderHouseDao")
    private OrderHouseDao orderHouseDao;

    @Resource(name = "report.confCityDao")
    private ConfCityDao confCityDao;


    public OrderHouseService() {
    }

    /**
     * 分页查询
     * @author afi
     * @param orderHouseRequest
     * @return
     */
    public PagingResult<OrderHouseEntity> getOrderHouseBypage(OrderHouseRequest orderHouseRequest){

        ConfCityEntity  nation =  confCityDao.getNationByCode(orderHouseRequest.getNationCode());

        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(orderHouseRequest.getLimit());
        pageBounds.setPage(orderHouseRequest.getPage());
        PagingResult<CityRegionVo> pagingResult = confCityDao.getCityRegionBypage(orderHouseRequest);
        PagingResult<OrderHouseEntity> rst = new PagingResult<>();

        rst.setTotal(pagingResult.getTotal());
        List<OrderHouseEntity>  list = new ArrayList<>();


        OrderHouseEntity all = new OrderHouseEntity();
        if (!Check.NuNObj(nation)){
            all.setRegionName(nation.getShowName());
            all.setCityName("");
            all.setCityCode("");
            list.add(all);
        }


        if (!Check.NuNCollection(pagingResult.getRows())){
            List<ConfCityEntity> cityList = new ArrayList<>();
            for (CityRegionVo regionVo : pagingResult.getRows()) {
                if (!Check.NuNCollection(regionVo.getCityList())){
                    cityList.addAll(regionVo.getCityList());
                }
            }
            List<String> codeList = new ArrayList<>();
            for (ConfCityEntity entity : cityList) {
                codeList.add(entity.getCode());
            }
            orderHouseRequest.setCityList(codeList);

            //发布数量
            orderHouseRequest.setToStatus(HouseStatusEnum.YFB.getCode());
            Map<String,HouseNumEntity>  houseMapFb =  transHouseList2Map(orderHouseDao.getHouseNumByCityCode(orderHouseRequest));

            //上架数量
            orderHouseRequest.setToStatus(HouseStatusEnum.SJ.getCode());
            Map<String,HouseNumEntity>  houseMapSj =  transHouseList2Map(orderHouseDao.getHouseNumByCityCode(orderHouseRequest));


            //订单数量
            Map<String,CityNumEntity>  orderMap =  transCityList2Map(orderHouseDao.getOrderNumListByCityCode(orderHouseRequest));

            //间夜数量
            Map<String,CityNumEntity>  dayMap =  transCityList2Map(orderHouseDao.getDayNumListByCityCode(orderHouseRequest));

            for (CityRegionVo regionVo : pagingResult.getRows()) {

                OrderHouseEntity  sum = new OrderHouseEntity();
                sum.setRegionName(regionVo.getRegionName());
                sum.setCityName("");
                sum.setCityCode("");
                list.add(sum);

                if (!Check.NuNCollection(regionVo.getCityList())){
                    for (ConfCityEntity entity : regionVo.getCityList()) {
                        OrderHouseEntity  ele = new OrderHouseEntity();
                        ele.setRegionName(regionVo.getRegionName());
                        ele.setCityName(entity.getShowName());
                        ele.setCityCode(entity.getCode());

                        if (houseMapFb.containsKey(entity.getCode())){
                            HouseNumEntity fb = houseMapFb.get(entity.getCode());
                            ele.setFabuNum(fb.getNum());
                            ele.setZizhuFabuNum(fb.getZizhuNum());
                            ele.setDituiFabuNum(fb.getDituiNum());
                        }
                        if (houseMapSj.containsKey(entity.getCode())){
                            HouseNumEntity sj = houseMapSj.get(entity.getCode());
                            ele.setShangjiaNum(sj.getNum());
                            ele.setZizhuShangjiaNum(sj.getZizhuNum());
                            ele.setDituiShangjiaNum(sj.getDituiNum());
                        }

                        if (orderMap.containsKey(entity.getCode())){
                            CityNumEntity order = orderMap.get(entity.getCode());
                            ele.setOrderNum(order.getNum());
                        }

                        if (dayMap.containsKey(entity.getCode())){
                            CityNumEntity day = dayMap.get(entity.getCode());
                            ele.setDayNum(day.getNum());
                        }
                        pullEle2Sum(ele,sum);
                        pullEle2Sum(ele,all);
                        list.add(ele);
                    }
                }
            }
        }
        rst.setRows(list);
        return rst;
    }


    /**
     * 将当前的元素信息填充到汇总
     * @author afi
     * @param ele
     * @param sum
     */
    private void pullEle2Sum(OrderHouseEntity ele,OrderHouseEntity sum){
        if (Check.NuNObjs(ele,sum)){
            return;
        }
        sum.setFabuNum(sum.getFabuNum()+ele.getFabuNum());
        sum.setZizhuFabuNum(sum.getZizhuFabuNum()+ele.getZizhuFabuNum());
        sum.setDituiFabuNum(sum.getDituiFabuNum()+ele.getDituiFabuNum());
        sum.setShangjiaNum(sum.getShangjiaNum()+ele.getShangjiaNum());
        sum.setZizhuShangjiaNum(sum.getZizhuShangjiaNum()+ele.getZizhuShangjiaNum());
        sum.setDituiShangjiaNum(sum.getDituiShangjiaNum()+ele.getDituiShangjiaNum());
        sum.setOrderNum(sum.getOrderNum()+ele.getOrderNum());
        sum.setDayNum(sum.getDayNum()+ele.getDayNum());
    }

    /**
     * 将当前的list转化成map
     * @param list
     * @return
     */
    private Map<String,CityNumEntity> transCityList2Map(List<CityNumEntity> list){
        Map<String,CityNumEntity> map = new HashMap<>();
        if (!Check.NuNCollection(list)){
            for (CityNumEntity cityNumEntity : list) {
                map.put(cityNumEntity.getCityCode(),cityNumEntity);
            }
        }
        return map;
    }


    /**
     * 将当前的list转化成map
     * @param list
     * @return
     */
    private Map<String,HouseNumEntity> transHouseList2Map(List<HouseNumEntity> list){
        Map<String,HouseNumEntity> map = new HashMap<>();
        if (!Check.NuNCollection(list)){
            for (HouseNumEntity houseNumEntity : list) {
                map.put(houseNumEntity.getCityCode(),houseNumEntity);
            }
        }
        return map;
    }

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#getPageInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public PagingResult<OrderHouseEntity> getPageInfo(OrderHouseRequest par) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#countDataInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public Long countDataInfo(OrderHouseRequest par) {
		// TODO Auto-generated method stub
		return null;
	}
}
