package com.ziroom.minsu.report.order.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.basedata.dao.ConfCityDao;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.vo.CityRegionVo;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.order.dao.OrderEvaluateDao;
import com.ziroom.minsu.report.order.dto.OrderEvaluateDetailRequest;
import com.ziroom.minsu.report.order.entity.OrderEvaluateDetailEntity;
import com.ziroom.minsu.report.order.entity.OrderEvaluateEntity;

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
@Service("report.orderEvaluateDetailService")
public class OrderEvaluateDetailService  implements ReportService <OrderEvaluateDetailEntity,OrderEvaluateDetailRequest>{


    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEvaluateDetailService.class);

    @Resource(name = "report.orderEvaluateDao")
    private OrderEvaluateDao orderEvaluateDao;

    @Resource(name = "report.confCityDao")
    private ConfCityDao confCityDao;

    /**
     * 分页查询
     * @author afi
     * @param orderEvaluateDetailRequest
     * @return
     */
    public PagingResult<OrderEvaluateDetailEntity> getOrderEvaluateDetailBypage(OrderEvaluateDetailRequest orderEvaluateDetailRequest){
        LogUtil.info(LOGGER, "getOrderEvaluateDetailBypage param:{}", JsonEntityTransform.Object2Json(orderEvaluateDetailRequest));
        ConfCityEntity  nation =  confCityDao.getNationByCode(orderEvaluateDetailRequest.getNationCode());
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(orderEvaluateDetailRequest.getLimit());
        pageBounds.setPage(orderEvaluateDetailRequest.getPage());
        PagingResult<CityRegionVo> pagingResult = confCityDao.getCityRegionBypage(orderEvaluateDetailRequest);
        PagingResult<OrderEvaluateDetailEntity> rst = new PagingResult<>();
        rst.setTotal(pagingResult.getTotal());
        List<OrderEvaluateDetailEntity>  list = new ArrayList<>();

        OrderEvaluateDetailEntity all = new OrderEvaluateDetailEntity();
        if (!Check.NuNObj(nation)){
            all.setRegionName(nation.getShowName());
            all.setCityName("");
            all.setCityCode("");
            list.add(all);
        }

        if (!Check.NuNCollection(pagingResult.getRows())){
            //全国
            for (CityRegionVo regionVo : pagingResult.getRows()) {
                OrderEvaluateDetailEntity  sum = new OrderEvaluateDetailEntity();
                sum.setRegionName(regionVo.getRegionName());
                sum.setCityName("");
                sum.setCityCode("");
                list.add(sum);
                if (!Check.NuNCollection(regionVo.getCityList())){
                    for (ConfCityEntity entity : regionVo.getCityList()) {
                        orderEvaluateDetailRequest.setCityCode(entity.getCode());
                        OrderEvaluateDetailEntity  ele = getOrderEvaluateDetailEntity(orderEvaluateDetailRequest);
                        ele.setRegionName(regionVo.getRegionName());
                        ele.setCityName(entity.getShowName());
                        ele.setCityCode(entity.getCode());
                        this.pullEle2Sum(ele,sum);
                        this.pullEle2Sum(ele,all);
                        this.fillRateInfo(ele);
                        list.add(ele);
                    }
                    this.fillRateInfo(sum);
                }
            }
        }
        this.fillRateInfo(all);
        rst.setRows(list);
        return rst;
    }


    /**
     * 查询订单统计相关 对外接口
     * @author afi
     * @param par
     * @return
     */
    public OrderEvaluateDetailEntity getOrderEvaluateDetailEntity(OrderEvaluateDetailRequest par){
        OrderEvaluateDetailEntity result = new OrderEvaluateDetailEntity();
        if(Check.NuNObj(par)){
            return result;
        }
        //获取当前的平均分数
        OrderEvaluateDetailEntity orderEvaluateDetailEntity = orderEvaluateDao.avgLanScoreInfoByCityCode(par);
        if (Check.NuNObj(orderEvaluateDetailEntity)){
            orderEvaluateDetailEntity = new  OrderEvaluateDetailEntity();
        }
        OrderEvaluateEntity orderEvaluateEntity = orderEvaluateDao.getOrderEvaluateByCityCode(par.getCityCode(),par.getStarTimeStr(),par.getEndTimeStr());
        if (!Check.NuNObj(orderEvaluateEntity)){
            orderEvaluateDetailEntity.setOrderNum(orderEvaluateEntity.getOrderNum());
            orderEvaluateDetailEntity.setUserOrderNum(orderEvaluateEntity.getTenNum());
            orderEvaluateDetailEntity.setLanOrderNum(orderEvaluateEntity.getLanNum());
        }
        Double  avgTen = orderEvaluateDao.avgUserScoreInfo(par);
        orderEvaluateDetailEntity.setSumAvgUserScore(avgTen);
        orderEvaluateDetailEntity.setSumAvgUserScoreAll(BigDecimalUtil.mul(orderEvaluateDetailEntity.getSumAvgLanScore(),orderEvaluateDetailEntity.getNumEval()));
        //
        if (!Check.NuNObj(orderEvaluateDetailEntity.getSumAvgLanScore())){
            orderEvaluateDetailEntity.setSumAvgLanScore(BigDecimalUtil.div(orderEvaluateDetailEntity.getSumAvgLanScore(),1));
        }

        if (!Check.NuNObj(orderEvaluateDetailEntity.getSumAvgUserScore())){
            orderEvaluateDetailEntity.setSumAvgUserScore(BigDecimalUtil.div(orderEvaluateDetailEntity.getSumAvgUserScore(),1));
        }
        if (!Check.NuNObj(orderEvaluateDetailEntity.getHouseAvgCleanScore())){
            orderEvaluateDetailEntity.setHouseAvgCleanScore(BigDecimalUtil.div(orderEvaluateDetailEntity.getHouseAvgCleanScore(),1));
        }

        if (!Check.NuNObj(orderEvaluateDetailEntity.getDescriptionAvgMatchScore())){
            orderEvaluateDetailEntity.setDescriptionAvgMatchScore(BigDecimalUtil.div(orderEvaluateDetailEntity.getDescriptionAvgMatchScore(),1));
        }
        if (!Check.NuNObj(orderEvaluateDetailEntity.getTrafficPositionAvgScore())){
            orderEvaluateDetailEntity.setTrafficPositionAvgScore(BigDecimalUtil.div(orderEvaluateDetailEntity.getTrafficPositionAvgScore(),1));
        }
        if (!Check.NuNObj(orderEvaluateDetailEntity.getSafetyAvgDegreeScore())){
            orderEvaluateDetailEntity.setSafetyAvgDegreeScore(BigDecimalUtil.div(orderEvaluateDetailEntity.getSafetyAvgDegreeScore(),1));
        }
        if (!Check.NuNObj(orderEvaluateDetailEntity.getCostPerformanceAvgScore())){
            orderEvaluateDetailEntity.setCostPerformanceAvgScore(BigDecimalUtil.div(orderEvaluateDetailEntity.getCostPerformanceAvgScore(),1));
        }
        return orderEvaluateDetailEntity;
    }


    /**
     * 将当前的元素信息填充到汇总
     * @author afi
     * @param ele
     * @param sum
     */
    private void pullEle2Sum(OrderEvaluateDetailEntity ele,OrderEvaluateDetailEntity sum){
        if (Check.NuNObjs(ele,sum)){
            return;
        }
        sum.setOrderNum(sum.getOrderNum()+ele.getOrderNum());
        sum.setLanOrderNum(sum.getLanOrderNum()+ele.getLanOrderNum());
        sum.setUserOrderNum(sum.getUserOrderNum()+ele.getUserOrderNum());

        sum.setSumAvgLanScoreAll(BigDecimalUtil.add(ele.getSumAvgLanScoreAll(),sum.getSumAvgLanScoreAll()));
        sum.setSumAvgUserScoreAll(BigDecimalUtil.add(ele.getSumAvgUserScoreAll(),sum.getSumAvgUserScoreAll()));

        sum.setHouseAvgCleanScoreAll(BigDecimalUtil.add(ele.getHouseAvgCleanScoreAll(),sum.getHouseAvgCleanScoreAll()));
        sum.setDescriptionAvgMatchScoreAll(BigDecimalUtil.add(ele.getDescriptionAvgMatchScoreAll(),sum.getDescriptionAvgMatchScoreAll()));
        sum.setTrafficPositionAvgScoreAll(BigDecimalUtil.add(ele.getTrafficPositionAvgScoreAll(),sum.getTrafficPositionAvgScoreAll()));
        sum.setSafetyAvgDegreeScoreAll(BigDecimalUtil.add(ele.getSafetyAvgDegreeScoreAll(),sum.getSafetyAvgDegreeScoreAll()));
        sum.setCostPerformanceAvgScoreAll(BigDecimalUtil.add(ele.getCostPerformanceAvgScoreAll(),sum.getCostPerformanceAvgScoreAll()));
        sum.setNumEval(sum.getNumEval()+ele.getNumEval());
    }


    /**
     * 填充当前的率的修改
     * @author afi
     * @param ele
     */
    private void fillRateInfo(OrderEvaluateDetailEntity ele){
        if (!Check.NuNObj(ele) && ele.getNumEval()>0){
            ele.setSumAvgLanScore(BigDecimalUtil.div(ele.getSumAvgLanScoreAll(),ele.getNumEval()));
            ele.setSumAvgUserScore(BigDecimalUtil.div(ele.getSumAvgUserScoreAll(),ele.getNumEval()));
            ele.setHouseAvgCleanScore(BigDecimalUtil.div(ele.getHouseAvgCleanScoreAll(),ele.getNumEval()));
            ele.setDescriptionAvgMatchScore(BigDecimalUtil.div(ele.getDescriptionAvgMatchScoreAll(),ele.getNumEval()));
            ele.setTrafficPositionAvgScore(BigDecimalUtil.div(ele.getTrafficPositionAvgScoreAll(),ele.getNumEval()));
            ele.setSafetyAvgDegreeScore(BigDecimalUtil.div(ele.getSafetyAvgDegreeScoreAll(),ele.getNumEval()));
            ele.setCostPerformanceAvgScore(BigDecimalUtil.div(ele.getCostPerformanceAvgScoreAll(),ele.getNumEval()));
        }
    }


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#getPageInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public PagingResult<OrderEvaluateDetailEntity> getPageInfo(OrderEvaluateDetailRequest par) {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#countDataInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public Long countDataInfo(OrderEvaluateDetailRequest par) {
		// TODO Auto-generated method stub
		return null;
	}

}
