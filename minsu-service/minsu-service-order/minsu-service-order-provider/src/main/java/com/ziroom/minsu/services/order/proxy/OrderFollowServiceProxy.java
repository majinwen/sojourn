package com.ziroom.minsu.services.order.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderFollowLogEntity;
import com.ziroom.minsu.services.order.api.inner.OrderFollowService;
import com.ziroom.minsu.services.order.dto.OrderFollowRequest;
import com.ziroom.minsu.services.order.entity.OrderFollowVo;
import com.ziroom.minsu.services.order.entity.OrderHouseVo;
import com.ziroom.minsu.services.order.entity.UidVo;
import com.ziroom.minsu.services.order.service.OrderFollowServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/12/14.
 * @version 1.0
 * @since 1.0
 */
@Component("order.orderFollowServiceProxy")
public class OrderFollowServiceProxy implements OrderFollowService{

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderFollowServiceProxy.class);


    @Resource(name = "order.orderFollowServiceImpl")
    private OrderFollowServiceImpl orderFollowService;

    /**
     * 分页查询24小时之内的信息
     * @author afi
     * @param params
     * @return
     */
    public String getOrderFollowByPage(String params){
        DataTransferObject dto = new DataTransferObject();
        try {
            if(Check.NuNStr(params)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数订单号为空");
                return dto.toJsonString();
            }
            OrderFollowRequest request = JsonEntityTransform.json2Object(params, OrderFollowRequest.class);
            if (Check.NuNObj(request)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数异常");
                return dto.toJsonString();
            }

            PagingResult<UidVo> orderFollowUidByPage = orderFollowService.getOrderFollowUidByPage(request);
            List<UidVo> rows = orderFollowUidByPage.getRows();
            if(orderFollowUidByPage.getTotal()<=0){
                dto.putValue("orderList", new ArrayList<OrderHouseVo>());
                dto.putValue("size", 0);
                return dto.toJsonString();
            }

            List<String> uidList = new ArrayList<>();
            for (UidVo row : rows) {
                uidList.add(row.getUserUid());
            }
            request.setUserUidList(uidList);
            if (!Check.NuNCollection(uidList)){
                List<OrderFollowVo> orderFollows = orderFollowService.getOrderFollow(request);
                dto.putValue("orderList", orderFollows);
            }else {
                dto.putValue("orderList", new ArrayList<OrderHouseVo>());
            }
            dto.putValue("size", orderFollowUidByPage.getTotal());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "查询需要跟进的订单 params :{}error:{}",params, e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("查询失败");
            return dto.toJsonString();
        }
        return dto.toJsonString();
    }


    /**
     * 查看跟进记录
     * @author afi
     * @create 2016年12月14日上午10:25:29
     * @param orderSn
     * @return
     */
    @Override
    public String getOrderFollowListByOrderSn(String orderSn) {
        LogUtil.debug(LOGGER, "【查看跟进记录】params:{}", orderSn);
        DataTransferObject dto = new DataTransferObject();
        try {

            if(Check.NuNStr(orderSn)){
                LogUtil.info(LOGGER, "查看跟进记录 订单号为空");
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数订单号为空");
                return dto.toJsonString();
            }

            List<OrderFollowLogEntity> list = orderFollowService.getOrderFollowLogListByOrderSn(orderSn);
            dto.putValue("list",list);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【查看跟进记录】 orderSn :{}error:{}",orderSn, e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("操作失败");
            return dto.toJsonString();
        }
        return dto.toJsonString();
    }



    /**
     * 保存订单的跟进记录
     * @author afi
     * @create 2016年12月14日上午10:25:29
     * @param params
     * @return
     */
    @Override
    public String saveOrderFollow(String params) {
        LogUtil.info(LOGGER, "【保存订单的跟进记录】params:{}", params);
        DataTransferObject dto = new DataTransferObject();
        try {
            OrderFollowLogEntity orderFollowLogEntity = JsonEntityTransform.json2Object(params, OrderFollowLogEntity.class);
            if (Check.NuNObj(orderFollowLogEntity)){
                LogUtil.info(LOGGER, "跟进记录 参数异常,params:{}", params);
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数异常");
                return dto.toJsonString();
            }
            if(Check.NuNStr(orderFollowLogEntity.getOrderSn())){
                LogUtil.info(LOGGER, "跟进记录 订单号为空,params:{}", params);
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数订单号为空");
                return dto.toJsonString();
            }
            if (Check.NuNObjs(orderFollowLogEntity.getCreateFid(),
                    orderFollowLogEntity.getCreateName(),
                    orderFollowLogEntity.getFollowStatus(),
                    orderFollowLogEntity.getOrderStatus()
                    )){
                LogUtil.info(LOGGER, "跟进记录 参数异常,params:{}", params);
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数异常");
                return dto.toJsonString();
            }
            orderFollowService.saveOrderFollow(orderFollowLogEntity,dto);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【保存订单的跟进记录】 par :{}error:{}",params, e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("操作失败");
            return dto.toJsonString();
        }
        return dto.toJsonString();
    }





}
