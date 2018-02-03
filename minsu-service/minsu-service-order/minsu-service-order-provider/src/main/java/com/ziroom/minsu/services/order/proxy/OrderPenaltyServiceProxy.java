package com.ziroom.minsu.services.order.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.order.api.inner.OrderPenaltyService;
import com.ziroom.minsu.services.order.dto.PenaltyCancelRequest;
import com.ziroom.minsu.services.order.dto.PenaltyRequest;
import com.ziroom.minsu.services.order.entity.PenaltyRelVo;
import com.ziroom.minsu.services.order.entity.PenaltyVo;
import com.ziroom.minsu.services.order.service.FinancePenaltyServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>订单罚款单相关服务</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@Service("order.orderPenaltyServiceProxy")
public class OrderPenaltyServiceProxy implements OrderPenaltyService{
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderPenaltyServiceProxy.class);

    @Resource(name = "order.financePenaltyServiceImpl")
    private FinancePenaltyServiceImpl financePenaltyServiceImpl;


    @Override
    public String listPenaltyPageByCondition(String paramJson) {
        DataTransferObject dto = new DataTransferObject();
        PenaltyRequest penaltyRequest = JsonEntityTransform.json2Object(paramJson, PenaltyRequest.class);
        if (Check.NuNObj(penaltyRequest)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        PagingResult<PenaltyVo> pagingResult = financePenaltyServiceImpl.listPenaltyPageByCondition(penaltyRequest);
        dto.putValue("list",pagingResult.getRows());
        dto.putValue("total",pagingResult.getTotal());
        return dto.toJsonString();
    }

    @Override
    public String cancelPenalty(String paramJson) {
        DataTransferObject dto = new DataTransferObject();
        PenaltyCancelRequest penaltyCancelRequest = JsonEntityTransform.json2Object(paramJson, PenaltyCancelRequest.class);
        if (Check.NuNObj(penaltyCancelRequest)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(penaltyCancelRequest.getPenaltySn())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("罚款单编号为空");
            return dto.toJsonString();
        }
        int count = financePenaltyServiceImpl.cancelPenalty(penaltyCancelRequest);
        if (count == 0){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("作废罚款单失败或罚款单已执行");
        }else{
            dto.putValue("count",count);
        }
        return dto.toJsonString();
    }

    @Override
    public String listPenaltyPayAndIncomeRel(String penaltySn) {
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(penaltySn)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("罚款单号为空");
            return dto.toJsonString();
        }
        List<PenaltyRelVo> penaltyRelVos = financePenaltyServiceImpl.listPenaltyPayAndIncomeRel(penaltySn);
        dto.putValue("list",penaltyRelVos);
        return dto.toJsonString();
    }
}
