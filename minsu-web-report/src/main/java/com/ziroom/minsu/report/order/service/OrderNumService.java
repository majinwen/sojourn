package com.ziroom.minsu.report.order.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.report.basedata.dao.ConfCityDao;
import com.ziroom.minsu.report.basedata.entity.CityNumEntity;
import com.ziroom.minsu.report.common.util.PercentUtils;
import com.ziroom.minsu.report.basedata.vo.CityRegionVo;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.order.dao.OrderNumDao;
import com.ziroom.minsu.report.order.dto.OrderNumRequest;
import com.ziroom.minsu.report.order.entity.OrderNumEntity;
import com.ziroom.minsu.report.order.vo.OrderNumVo;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Service("report.orderNumService")
public class OrderNumService {


    private static final Logger LOGGER = LoggerFactory.getLogger(OrderNumService.class);

    @Resource(name = "report.orderNumDao")
    private OrderNumDao orderNumDao;

    @Resource(name = "report.confCityDao")
    private ConfCityDao confCityDao;


    /**
     * 分页查询
     * @author afi
     * @param orderNumRequest
     * @return
     */
    public PagingResult<OrderNumVo> getOrderNumBypage(OrderNumRequest orderNumRequest){

        ConfCityEntity  nation =  confCityDao.getNationByCode(orderNumRequest.getNationCode());

        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(orderNumRequest.getLimit());
        pageBounds.setPage(orderNumRequest.getPage());
        PagingResult<CityRegionVo> pagingResult = confCityDao.getCityRegionBypage(orderNumRequest);
        PagingResult<OrderNumVo> rst = new PagingResult<>();
        rst.setTotal(pagingResult.getTotal());
        List<OrderNumVo> list = new ArrayList<>();
        OrderNumVo  all = new OrderNumVo();

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
            orderNumRequest.setCityList(codeList);
            //填充当前的城市的数据信息
            Map<String,OrderNumVo> map = this.getOrderNumMap(orderNumRequest);
            for (CityRegionVo regionVo : pagingResult.getRows()) {
                OrderNumVo  sum = new OrderNumVo();
                sum.setRegionName(regionVo.getRegionName());
                sum.setCityName("");
                sum.setCityCode("");
                list.add(sum);
                if (!Check.NuNCollection(regionVo.getCityList())){

                    for (ConfCityEntity entity : regionVo.getCityList()) {
                        OrderNumVo  ele;
                        if (map.containsKey(entity.getCode())){
                            ele = map.get(entity.getCode());
                        }else {
                           ele = new OrderNumVo();
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
    private void pullEle2Sum(OrderNumVo  ele,OrderNumVo  sum){
        if (Check.NuNObjs(ele,sum)){
            return;
        }
        sum.setApplyNum(sum.getApplyNum()+ele.getApplyNum());
        sum.setAcceptNum(sum.getAcceptNum()+ele.getAcceptNum());
        sum.setPayNum(sum.getPayNum()+ele.getPayNum());
        sum.setSuccessUserNum(sum.getSuccessUserNum()+ele.getSuccessUserNum());
        sum.setUserNum(sum.getUserNum()+ele.getUserNum());
        sum.setConsoltNum(sum.getConsoltNum()+ele.getConsoltNum());
    }

    /**
     * 填充当前的率的修改
     * @author afi
     * @param ele
     */
    private void fillRateInfo(OrderNumVo  ele){
        if (Check.NuNObj(ele)){
            return;
        }
        //房东接单率=房东接单量/申请量；
        if (ValueUtil.getintValue(ele.getApplyNum()) > 0){
            Double rate = PercentUtils.getPercent(ele.getAcceptNum(),ele.getApplyNum());
            ele.setAcceptRate(rate);
        }
        //客户支付率=客户支付量/房东接单量；
        if (ValueUtil.getintValue(ele.getAcceptNum()) > 0){
            Double rate = PercentUtils.getPercent(ele.getPayNum(),ele.getAcceptNum());
            ele.setPayRate(rate);
        }
        //订单成功率=客户支付量/申请量；
        if (ValueUtil.getintValue(ele.getApplyNum()) > 0){
            Double rate = PercentUtils.getPercent(ele.getPayNum(),ele.getApplyNum());
            ele.setSuccessRate(rate);
        }
        //客户预定成功率=成功客户量/查询时间段内提交订单的客户量；
        if (ValueUtil.getintValue(ele.getUserNum()) > 0){
            Double rate = PercentUtils.getPercent(ele.getSuccessUserNum(),ele.getUserNum());
            ele.setSuccessUserRate(rate);
        }
    }



    /**
     * 获取当前的map信息
     * @author afi
     * @param orderNumRequest
     * @return
     */
    private Map<String,OrderNumVo> getOrderNumMap(OrderNumRequest orderNumRequest){
        Map<String,OrderNumVo>  voMap = new HashMap<>();

        /**
         * 填充当前咨询数
         */
        this.fillConsultNumMap(voMap,orderNumRequest);

        /**
         * 填充订单数量
         */
        this.fillOrderNumMap(voMap,orderNumRequest);

        /**
         * 填充当前的申请人数
         */
        this.fillUserNumMap(voMap,orderNumRequest);
        /**
         * 填充申请成功数
         */
        this.fillUserSuccessNumMap(voMap,orderNumRequest);
        return voMap;
    }


    /**
     * 填充当前的申请成功人数
     * @author afi
     * @param voMap
     * @param orderNumRequest
     */
    private void fillUserSuccessNumMap(Map<String,OrderNumVo>  voMap, OrderNumRequest orderNumRequest){
        /**
         * 填充当前的申请人数
         */
        List<CityNumEntity>   userList = orderNumDao.getUserSuccessNumByCity(orderNumRequest);
        if (!Check.NuNCollection(userList)){
            for (CityNumEntity cityNumEntity : userList) {
                String cityCode = cityNumEntity.getCityCode();
                OrderNumVo vo;
                if (voMap.containsKey(cityCode)){
                    vo = voMap.get(cityCode);
                }else {
                    vo = new OrderNumVo();
                    voMap.put(cityCode,vo);
                }
                vo.setSuccessUserNum(cityNumEntity.getNum());
            }
        }
    }


    /**
     * 填充当前的申请人数
     * @author afi
     * @param voMap
     * @param orderNumRequest
     */
    private void fillUserNumMap(Map<String,OrderNumVo>  voMap, OrderNumRequest orderNumRequest){
        /**
         * 填充当前的申请人数
         */
        List<CityNumEntity>   userList = orderNumDao.getUserNumByCity(orderNumRequest);
        if (!Check.NuNCollection(userList)){
            for (CityNumEntity cityNumEntity : userList) {
                String cityCode = cityNumEntity.getCityCode();
                OrderNumVo vo;
                if (voMap.containsKey(cityCode)){
                    vo = voMap.get(cityCode);
                }else {
                    vo = new OrderNumVo();
                    voMap.put(cityCode,vo);
                }
                vo.setUserNum(cityNumEntity.getNum());
            }
        }
    }


    /**
     * 填充当前的咨询信息
     * @author afi
     * @param voMap
     * @param orderNumRequest
     */
    private void fillConsultNumMap(Map<String,OrderNumVo>  voMap, OrderNumRequest orderNumRequest){
        /**
         * 咨询信息
         */
        List<CityNumEntity>   consultList = orderNumDao.getConsultNumByCity(orderNumRequest);
        if (!Check.NuNCollection(consultList)){
            for (CityNumEntity cityNumEntity : consultList) {
                String cityCode = cityNumEntity.getCityCode();
                OrderNumVo vo;
                if (voMap.containsKey(cityCode)){
                    vo = voMap.get(cityCode);
                }else {
                    vo = new OrderNumVo();
                    voMap.put(cityCode,vo);
                }
                vo.setConsoltNum(cityNumEntity.getNum());
            }
        }
    }


    /**
     * 填充当前的订单统计信息
     * @author afi
     * @param voMap
     * @param orderNumRequest
     */
    private void fillOrderNumMap(Map<String,OrderNumVo>  voMap, OrderNumRequest orderNumRequest){
        /**
         * 订单统计
         */
        List<OrderNumEntity>   orderNumList = orderNumDao.getOrderNumByCity(orderNumRequest);

        if (!Check.NuNCollection(orderNumList)){
            for (OrderNumEntity orderNumEntity : orderNumList) {
                String cityCode = orderNumEntity.getCityCode();
                OrderNumVo vo;
                if (voMap.containsKey(cityCode)){
                    vo = voMap.get(cityCode);
                }else {
                    vo = new OrderNumVo();
                    voMap.put(cityCode,vo);
                }
                vo.setApplyNum(orderNumEntity.getApplyNum());
                vo.setAcceptNum(orderNumEntity.getAcceptNum());
                vo.setPayNum(orderNumEntity.getPayNum());
            }
        }
    }







}
