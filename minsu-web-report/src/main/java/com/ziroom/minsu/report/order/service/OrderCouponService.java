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
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.report.basedata.dao.ConfCityDao;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.vo.CityRegionVo;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.common.util.PercentUtils;
import com.ziroom.minsu.report.order.dao.OrderCouponDao;
import com.ziroom.minsu.report.order.dto.OrderCouponRequest;
import com.ziroom.minsu.report.order.entity.OrderCouponEntity;
import com.ziroom.minsu.report.order.vo.OrderCouponVo;
import com.ziroom.minsu.services.common.utils.ValueUtil;

/**
 * <p>房东的统计信息</p>
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
@Service("report.orderCouponService")
public class OrderCouponService implements ReportService <OrderCouponVo,OrderCouponRequest>{


    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCouponService.class);

    @Resource(name = "report.orderCouponDao")
    private OrderCouponDao orderCouponDao;

    @Resource(name = "report.confCityDao")
    private ConfCityDao confCityDao;


    /**
     * 分页查询
     * @author afi
     * @param orderCouponRequest
     * @return
     */
    public PagingResult<OrderCouponVo> getOrderCouponBypage(OrderCouponRequest orderCouponRequest){


        ConfCityEntity  nation =  confCityDao.getNationByCode(orderCouponRequest.getNationCode());

        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(orderCouponRequest.getLimit());
        pageBounds.setPage(orderCouponRequest.getPage());
        PagingResult<CityRegionVo> pagingResult = confCityDao.getCityRegionBypage(orderCouponRequest);
        PagingResult<OrderCouponVo> rst = new PagingResult<>();
        rst.setTotal(pagingResult.getTotal());
        List<OrderCouponVo> list = new ArrayList<>();
        OrderCouponVo  all = new OrderCouponVo();
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
            orderCouponRequest.setCityList(codeList);
            //填充当前的城市的数据信息
            Map<String,OrderCouponVo> map = this.getOrderCouponMap(orderCouponRequest);
            for (CityRegionVo regionVo : pagingResult.getRows()) {
                OrderCouponVo  sum = new OrderCouponVo();
                sum.setRegionName(regionVo.getRegionName());
                sum.setCityName("");
                sum.setCityCode("");
                list.add(sum);
                if (!Check.NuNCollection(regionVo.getCityList())){

                    for (ConfCityEntity entity : regionVo.getCityList()) {
                        OrderCouponVo  ele;
                        if (map.containsKey(entity.getCode())){
                            ele = map.get(entity.getCode());
                        }else {
                           ele = new OrderCouponVo();
                        }
                        ele.setRegionName(regionVo.getRegionName());
                        ele.setCityName(entity.getShowName());
                        ele.setCityCode(entity.getCode());
                        this.pullEle2Sum(ele,sum);
                        this.pullEle2Sum(ele,all);
                        this.fillRateInfo(ele);
                        list.add(ele);
                    }
                }
                this.fillRateInfo(sum);
            }
        }
        this.fillRateInfo(all);
        rst.setRows(list);
        return rst;
    }

    /**
     * 将当前的元素信息填充到汇总
     * @author afi
     * @param ele
     * @param sum
     */
    private void pullEle2Sum(OrderCouponVo  ele,OrderCouponVo  sum){
        if (Check.NuNObjs(ele,sum)){
            return;
        }
        sum.setRealMoney(BigDecimalUtil.add(sum.getRealMoney(),ele.getRealMoney()));
        sum.setRentalMoney(BigDecimalUtil.add(sum.getRentalMoney(),ele.getRentalMoney()));
        sum.setCouponMoney(BigDecimalUtil.add(sum.getCouponMoney(),ele.getCouponMoney()));
        sum.setOrderCouponNum(sum.getOrderCouponNum()+ele.getOrderCouponNum());
        sum.setOrderNum(sum.getOrderNum()+ele.getOrderNum());
    }

    /**
     * 填充当前的率的修改
     * @author afi
     * @param ele
     */
    private void fillRateInfo(OrderCouponVo  ele){
        if (Check.NuNObj(ele)){
            return;
        }
        if (ValueUtil.getdoubleValue(ele.getOrderCouponNum()) >0){
            ele.setCouponMoneyAvg(BigDecimalUtil.div(ele.getCouponMoney(),ele.getOrderCouponNum()));
        }
        if (ValueUtil.getdoubleValue(ele.getRentalMoney()) >0){
            ele.setCouponMoneyRate(PercentUtils.getPercent(ele.getCouponMoney(),ele.getRentalMoney()));
        }
        if (ValueUtil.getdoubleValue(ele.getOrderNum()) >0){
            ele.setCouponNumRate(PercentUtils.getPercent(ele.getOrderCouponNum(),ele.getOrderNum()));
        }

    }



    /**
     * 获取当前的map信息
     * @author afi
     * @param orderCouponRequest
     * @return
     */
    private Map<String,OrderCouponVo> getOrderCouponMap(OrderCouponRequest orderCouponRequest){
        Map<String,OrderCouponVo>  voMap = new HashMap<>();

        List<OrderCouponEntity> couponEntityList = orderCouponDao.getOrderCouponByCity(orderCouponRequest);
        if (!Check.NuNCollection(couponEntityList)){
            for (OrderCouponEntity couponEntity : couponEntityList) {
                String cityCode = couponEntity.getCityCode();
                OrderCouponVo vo = new OrderCouponVo();

                Double real = BigDecimalUtil.sub(ValueUtil.getintValue(couponEntity.getRentalMoney()), ValueUtil.getintValue(couponEntity.getCouponMoney()));
                vo.setRealMoney(BigDecimalUtil.div(real,100));
                vo.setRentalMoney(BigDecimalUtil.div(ValueUtil.getintValue(couponEntity.getRentalMoney()),100));
                vo.setCouponMoney(BigDecimalUtil.div(ValueUtil.getintValue(couponEntity.getCouponMoney()),100));
                vo.setOrderCouponNum(ValueUtil.getintValue(couponEntity.getCouponNum()));
                vo.setOrderNum(ValueUtil.getintValue(couponEntity.getOrderNum()));
                voMap.put(cityCode,vo);
            }
        }
        return voMap;
    }

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#getPageInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public PagingResult<OrderCouponVo> getPageInfo(OrderCouponRequest par) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#countDataInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public Long countDataInfo(OrderCouponRequest par) {
		// TODO Auto-generated method stub
		return null;
	}








}
