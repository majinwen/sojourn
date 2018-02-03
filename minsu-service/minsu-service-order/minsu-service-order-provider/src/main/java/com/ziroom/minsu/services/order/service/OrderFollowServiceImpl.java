package com.ziroom.minsu.services.order.service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderFlagEntity;
import com.ziroom.minsu.entity.order.OrderFollowEntity;
import com.ziroom.minsu.entity.order.OrderFollowLogEntity;
import com.ziroom.minsu.services.order.dao.OrderFlagDao;
import com.ziroom.minsu.services.order.dao.OrderFollowDao;
import com.ziroom.minsu.services.order.dao.OrderFollowLogDao;
import com.ziroom.minsu.services.order.dto.OrderFollowRequest;
import com.ziroom.minsu.services.order.entity.OrderFollowVo;
import com.ziroom.minsu.services.order.entity.OrderHouseVo;
import com.ziroom.minsu.services.order.entity.UidVo;
import com.ziroom.minsu.valenum.order.FollowStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
@Service("order.orderFollowServiceImpl")
public class OrderFollowServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderFollowServiceImpl.class);

    @Resource(name="order.orderFollowLogDao")
    private OrderFollowLogDao orderFollowLogDao;


    @Resource(name="order.orderFollowDao")
    private OrderFollowDao orderFollowDao;


    public PagingResult<UidVo> getOrderFollowUidByPage(OrderFollowRequest request){
        return orderFollowDao.getOrderFollowUidByPage(request);
    }


    /**
     * 分页查询24小时之内的信息
     * @author afi
     * @param request
     * @return
     */
    public List<OrderFollowVo> getOrderFollow(OrderFollowRequest request){
        return orderFollowDao.getOrderFollow(request);
    }

    /**
     * 获取跟进表的操作记录
     * @author afi
     * @created 2016年12月18日 下午20:22:38
     * @return
     */
    public List<OrderFollowLogEntity> getOrderFollowLogListByOrderSn(String orderSn){
        return orderFollowLogDao.getOrderFollowLogListByOrderSn(orderSn);
    }

    /**
     * 保存订单的跟进记录
     * @author afi
     * @create 2016年12月14日上午10:25:29
     * @param orderFollowLogEntity
     * @return
     */
    public void saveOrderFollow(OrderFollowLogEntity orderFollowLogEntity, DataTransferObject dto){
        if (Check.NuNObj(orderFollowLogEntity)){
            LogUtil.info(LOGGER,"orderFollowLogEntity is null");
            throw new BusinessException("orderFollowLogEntity is null");
        }
        OrderFollowEntity has = orderFollowDao.getOrderFollowByOrderSn(orderFollowLogEntity.getOrderSn());
        if (Check.NuNObj(has)){
            //创建新的跟进记录
            this.saveOrderFollowCreate(orderFollowLogEntity);
        }else {
            if (has.getFollowStatus() == FollowStatusEnum.OVER.getCode()){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("当前订单已经跟进完成,不能继续跟进");
                return;
            }
            //添加跟进记录
            orderFollowLogEntity.setFollowFid(has.getFid());
            this.saveOrderFollowContinue(orderFollowLogEntity);
        }
    }


    /**
     * 追加已经存在的跟进记录
     * @author afi
     * @create 2016年12月14日上午10:25:29
     * @param orderFollowLogEntity
     * @return
     */
    private void saveOrderFollowContinue(OrderFollowLogEntity orderFollowLogEntity){
        Integer followStatus = orderFollowLogEntity.getFollowStatus();
        if (followStatus == FollowStatusEnum.OVER.getCode()){
            //更新订单跟进信息
            int num = orderFollowDao.updateOrderFollowOverByOrderSn(orderFollowLogEntity.getOrderSn());
            if (num == 0){
                return;
            }
        }
        //保存跟进记录
        orderFollowLogDao.saveOrderFollowLog(orderFollowLogEntity);
    }


    /**
     * 创建新的跟进记录
     * @author afi
     * @create 2016年12月14日上午10:25:29
     * @param orderFollowLogEntity
     * @return
     */
    private void saveOrderFollowCreate(OrderFollowLogEntity orderFollowLogEntity){
        String fid = UUIDGenerator.hexUUID();
        OrderFollowEntity follow = new  OrderFollowEntity();
        follow.setFid(fid);
        follow.setOrderStatus(orderFollowLogEntity.getOrderStatus());
        follow.setCreateFid(orderFollowLogEntity.getCreateFid());
        follow.setCreateName(orderFollowLogEntity.getCreateName());
        follow.setFollowStatus(orderFollowLogEntity.getFollowStatus());
        follow.setOrderSn(orderFollowLogEntity.getOrderSn());
        orderFollowDao.saveOrderFollow(follow);
        orderFollowLogEntity.setFollowFid(fid);
        orderFollowLogDao.saveOrderFollowLog(orderFollowLogEntity);
    }
}
