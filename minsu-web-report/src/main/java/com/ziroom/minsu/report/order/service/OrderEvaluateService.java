package com.ziroom.minsu.report.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.report.basedata.dao.ConfCityDao;
import com.ziroom.minsu.report.basedata.dto.NationRegionCityRequest;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.vo.CityRegionVo;
import com.ziroom.minsu.report.basedata.vo.NationRegionCityVo;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.common.util.NationRegionCityUtil;
import com.ziroom.minsu.report.common.util.PercentUtils;
import com.ziroom.minsu.report.order.dao.OrderEvaluateDao;
import com.ziroom.minsu.report.order.dto.OrderEvaluate2Request;
import com.ziroom.minsu.report.order.dto.OrderEvaluateRequest;
import com.ziroom.minsu.report.order.entity.OrderEvaluateEntity;
import com.ziroom.minsu.report.order.vo.OrderEvaluateVo;
import com.ziroom.minsu.services.common.utils.ValueUtil;

/**
 * <p>TODO</p>
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
@Service("report.orderEvaluateService")
public class OrderEvaluateService implements ReportService <OrderEvaluateEntity,OrderEvaluateRequest>{


    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEvaluateService.class);

    @Resource(name = "report.orderEvaluateDao")
    private OrderEvaluateDao orderEvaluateDao;

    @Resource(name = "report.confCityDao")
    private ConfCityDao confCityDao;

    /**
     * 分页查询
     * @author afi
     * @param orderEvaluateRequest
     * @return
     */
    public PagingResult<OrderEvaluateEntity> getOrderEvaluateBypage(OrderEvaluateRequest orderEvaluateRequest){

        ConfCityEntity  nation =  confCityDao.getNationByCode(orderEvaluateRequest.getNationCode());

        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(orderEvaluateRequest.getLimit());
        pageBounds.setPage(orderEvaluateRequest.getPage());
        PagingResult<CityRegionVo> pagingResult = confCityDao.getCityRegionBypage(orderEvaluateRequest);
        PagingResult<OrderEvaluateEntity> rst = new PagingResult<>();
        rst.setTotal(pagingResult.getTotal());
        List<OrderEvaluateEntity>  list = new ArrayList<>();

        OrderEvaluateEntity all = new OrderEvaluateEntity();
        OrderEvaluateEntity allOld = new OrderEvaluateEntity();
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
            orderEvaluateRequest.setCityList(codeList);
            Map<String,OrderEvaluateEntity>  map =  transList2Map(orderEvaluateDao.getOrderEvaluateListByCityCode(orderEvaluateRequest));

            Map<String,OrderEvaluateEntity>  mapOld = null;
            OrderEvaluateRequest orderEvaluateRequestNeg = OrderEvaluateRequest.clone2negative(orderEvaluateRequest);
            if (Check.NuNObj(orderEvaluateRequestNeg)){
                mapOld = new HashMap<>();
            }else {
                mapOld = transList2Map(orderEvaluateDao.getOrderEvaluateListByCityCode(orderEvaluateRequestNeg));
            }
            //全国
            for (CityRegionVo regionVo : pagingResult.getRows()) {

                OrderEvaluateEntity  sum = new OrderEvaluateEntity();
                OrderEvaluateEntity sumOld = new OrderEvaluateEntity();
                sum.setRegionName(regionVo.getRegionName());
                sum.setCityName("");
                sum.setCityCode("");
                list.add(sum);


                if (!Check.NuNCollection(regionVo.getCityList())){
                    for (ConfCityEntity entity : regionVo.getCityList()) {
                        OrderEvaluateEntity  ele = new OrderEvaluateEntity();
                        if (map.containsKey(entity.getCode())){
                            BeanUtils.copyProperties(map.get(entity.getCode()),ele);
                        }
                        ele.setRegionName(regionVo.getRegionName());
                        ele.setCityName(entity.getShowName());
                        ele.setCityCode(entity.getCode());
                        //对称时间
                        OrderEvaluateEntity old = null;
                        if (mapOld.containsKey(entity.getCode())){
                            old = mapOld.get(entity.getCode());
                        }
                        this.pullEle2Sum(ele,sum);
                        this.pullEle2Sum(ele,all);

                        this.pullEle2Sum(old,sumOld);
                        this.pullEle2Sum(old,allOld);

                        this.fillRateInfo(ele,old);
                        list.add(ele);
                    }
                    this.fillRateInfo(sum,sumOld);
                }
            }
        }
        this.fillRateInfo(all,allOld);
        rst.setRows(list);
        return rst;
    }


    /**
     * 订单评价信息报表
     *
     * @author lishaochuan
     * @create 2017/3/10 18:34
     * @param 
     * @return 
     */
    public PagingResult<OrderEvaluateVo> getOrderEvaluate(OrderEvaluate2Request request) {
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
        //循环翻译国家、大区、城市
        PagingResult<OrderEvaluateVo> orderEvaluate = orderEvaluateDao.getOrderEvaluate(request);
        if (!Check.NuNCollection(orderEvaluate.getRows())){
            for (OrderEvaluateVo vo : orderEvaluate.getRows()) {
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
        return orderEvaluate;
    }



    /**
     * 将当前的元素信息填充到汇总
     * @author afi
     * @param ele
     * @param sum
     */
    private void pullEle2Sum(OrderEvaluateEntity ele,OrderEvaluateEntity sum){
        if (Check.NuNObjs(ele,sum)){
            return;
        }
        sum.setOrderNum(sum.getOrderNum()+ele.getOrderNum());
        sum.setTenNum(sum.getTenNum()+ele.getTenNum());
        sum.setLanNum(sum.getLanNum()+ele.getLanNum());
    }


    /**
     * 填充当前的率的修改
     * @author afi
     * @param newObj
     * @param oldObj
     */
    private void fillRateInfo(OrderEvaluateEntity newObj,OrderEvaluateEntity oldObj){
        if (!Check.NuNObj(newObj)){
            this.fillRateEle(newObj);
        }

        if (!Check.NuNObj(oldObj)){
            this.fillRateEle(oldObj);
        }

        if (Check.NuNObj(oldObj)){
            newObj.setTenInfo(null);
            return;
        }

        //房东接单率=房东接单量/申请量；
        if (!Check.NuNObj(newObj) && ValueUtil.getdoubleValue(oldObj.getTenRate()) > 0){
            Double rate = PercentUtils.getPercent(newObj.getTenRate(),oldObj.getTenRate());
            newObj.setTenInfo(rate);
        }
    }


    /**
     * 填充当前的率的修改
     * @author afi
     * @param ele
     */
    private void fillRateEle(OrderEvaluateEntity ele){
        if (!Check.NuNObj(ele)){
            if (ValueUtil.getintValue(ele.getOrderNum()) > 0){
                Double rate = PercentUtils.getPercent(ele.getTenNum(),ele.getOrderNum());
                Double rateLand = PercentUtils.getPercent(ele.getLanNum(),ele.getOrderNum());
                ele.setTenRate(rate);
                ele.setLanRate(rateLand);
            }else {
                ele.setTenRate(null);
                ele.setLanRate(null);
            }
        }

    }



    /**
     * 将当前的list转化成map
     * @param list
     * @return
     */
    private Map<String,OrderEvaluateEntity> transList2Map(List<OrderEvaluateEntity> list){
        Map<String,OrderEvaluateEntity> map = new HashMap<>();
        if (!Check.NuNCollection(list)){
            for (OrderEvaluateEntity orderEvaluateEntity : list) {
                map.put(orderEvaluateEntity.getCityCode(),orderEvaluateEntity);
            }
        }
        return map;
    }


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#getPageInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public PagingResult<OrderEvaluateEntity> getPageInfo(OrderEvaluateRequest par) {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#countDataInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public Long countDataInfo(OrderEvaluateRequest par) {
		// TODO Auto-generated method stub
		return null;
	}
}
