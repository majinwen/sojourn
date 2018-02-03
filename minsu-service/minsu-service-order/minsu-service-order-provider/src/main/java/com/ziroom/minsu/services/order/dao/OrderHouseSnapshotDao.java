package com.ziroom.minsu.services.order.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.SnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.order.OrderHouseSnapshotEntity;
import com.ziroom.minsu.services.order.dto.OrderRequest;
import com.ziroom.minsu.services.order.entity.HouseSnapshotVo;


/**
 * <p>
 * 城市信息
 * </p>
 * <p/>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie on 2016/4/1.
 * @version 1.0
 * @since 1.0
 */
@Repository("order.houseSnapshotDao")
public class OrderHouseSnapshotDao {

	private String SQLID = "order.houseSnapshotDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;


    /**
     * 安装智能锁
     * @author afi
     * @param orderSn
     * @return
     */
    public int installLockByOrderSn(String orderSn) {
        if (Check.NuNStr(orderSn)){
            return 0;
        }
        Map<String,Object> par = new HashMap<>();
        par.put("orderSn",orderSn);
        return mybatisDaoContext.update(SQLID + "installLockByOrderSn", par);
    }



	/**
	 *
	 * 插入资源记录
	 *
	 * @author liyingjie
	 * @created 2016年4月1日 
	 *
	 * @param houseSnapshotEntity
	 */
	public int insertHouseSnapshotRes(OrderHouseSnapshotEntity houseSnapshotEntity) {
		return mybatisDaoContext.save(SQLID + "insertHouseSnapshot", houseSnapshotEntity);
	}


	/**
	 * 获取订单的房源信息
	 * @author afi
	 * @param orderSn
	 * @return
	 */
	public OrderHouseSnapshotEntity findHouseSnapshotByOrderSn(String orderSn){
		return mybatisDaoContext.findOne(SQLID + "findHouseSnapshotByOrderSn", OrderHouseSnapshotEntity.class, orderSn);
	}

	/**
	 *
	 * 查询 资源记录
	 *
	 * @author liyingjie
	 * @created 2016年4月1日 
	 *
	 * @param 
	 */
	public List<OrderHouseSnapshotEntity> findHouseSnapshotByCondiction(Map<String,Object> paramMap){
		return mybatisDaoContext.findAll(SQLID + "selectByCondition", OrderHouseSnapshotEntity.class, paramMap);
	}

	/**
	 * 
	 * 查询订单快照
	 *
	 * @author yd
	 * @created 2016年5月2日 下午6:32:38
	 *
	 * @param orderRequest
	 * @return
	 */
	public PagingResult<HouseSnapshotVo> findHouseSnapshotByOrder(OrderRequest orderRequest){

		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(orderRequest.getLimit());
		pageBounds.setPage(orderRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "findHouseSnapshotByOrder", HouseSnapshotVo.class, orderRequest.toMap(), pageBounds);
	}


	/**
	 * 统计三小时内新增的当前的恶意下单数量
	 * @author afi
	 * @return
	 */
	public List<MinsuEleEntity> findMaliceOrder(int num){
		Map<String,Object>  paramMap = new HashMap<>();
		paramMap.put("num",num);
		paramMap.put("preTime", SnUtil.getDatePre());
		paramMap.put("tillTime", DateSplitUtil.jumpHours(new Date(),-3));
		return mybatisDaoContext.findAll(SQLID + "findMaliceOrder", MinsuEleEntity.class, paramMap);
	}

}
