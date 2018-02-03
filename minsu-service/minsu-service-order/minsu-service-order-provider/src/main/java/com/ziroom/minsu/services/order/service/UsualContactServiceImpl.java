
package com.ziroom.minsu.services.order.service;

import javax.annotation.Resource;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import org.springframework.stereotype.Service;

import com.ziroom.minsu.entity.order.UsualContactEntity;
import com.ziroom.minsu.services.order.dao.OrderContactDao;
import com.ziroom.minsu.services.order.dao.UsualContactDao;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>常用联系人的业务实现层</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@Service("order.usualContactServiceImpl")
public class UsualContactServiceImpl {
	
	@Resource(name = "order.usualContactsDao")
	private UsualContactDao usualContactDao;
	@Resource(name="order.orderContactDao")
	private OrderContactDao orderContactDao;
	
	 /**
     * 
     * update by fid
     *
     * @author yd
     * @created 2016年5月2日 上午11:54:10
     *
     * @param usualContactEntity
     * @return
     */
    public int updateByFid(UsualContactEntity usualContactEntity){
    	return usualContactDao.updateByFid(usualContactEntity);
    }



	/**
	 * 根据fid查询联系人
	 * @author lishaochuan
	 * @create 2016/12/1 17:29
	 * @param
	 * @return
	 */
	public UsualContactEntity getContactByFid(String fid, String userUid){
		return usualContactDao.getContactByFid(fid, userUid);
	}

	/**
	 * 查询是否有相同证件类型、证件号码的入住人
	 * @author lishaochuan
	 * @create 2016/12/1 15:29
	 * @param
	 * @param usualContactEntity
	 * @return
	 */
	public long checkHaveContact(UsualContactEntity usualContactEntity){
		return usualContactDao.checkHaveContact(usualContactEntity);
	}


	/**
	 * 查询当前用户的预订人联系方式
	 * @author lishaochuan
	 * @create 2016/12/1 18:30
	 * @param
	 * @return
	 */
	public UsualContactEntity getBookerContact(String userUid){
		return usualContactDao.getBookerContact(userUid);
	}

	/**
	 * 逻辑删除并添加联系人
	 * @author lishaochuan
	 * @create 2016/12/1 9:46
	 * @param 
	 * @return 
	 */
	public String deleteAndInsertContact(UsualContactEntity usualContactEntity){
		int num = usualContactDao.deleteUsualContractByFid(usualContactEntity.getFid(), usualContactEntity.getUserUid(), usualContactEntity.getIsBooker());
		String fid = UUIDGenerator.hexUUID();
		if(num == 1){
			usualContactEntity.setFid(fid);
			num = usualContactDao.insertUsualContact(usualContactEntity);
		}
		return fid;
	}


	/**
	 * 逻辑删除联系人
	 * @author lishaochuan
	 * @create 2016/12/2 9:21
	 * @param 
	 * @return 
	 */
	public void deleteContact(String fid, String userUid){
		usualContactDao.deleteUsualContractByFid(fid, userUid, YesOrNoEnum.NO.getCode());
	}

    
    /**
     * 
     * 查询订单入住人数量
     *
     * @author jixd
     * @created 2016年5月4日 下午10:53:02
     *
     * @param orderSn
     * @return
     */
    public long queryOrderContactNum(String orderSn){
    	return orderContactDao.selectCountContactByOrderSn(orderSn);
    }


	/**
	 * 查询是否有相同信息的入住人
	 * @author lishaochuan
	 * @create 2017/1/5 19:46
	 * @param
	 * @return
	 */
	public UsualContactEntity getContactByInfo(UsualContactEntity usualContactEntity ){
		Map<String, Object> map = new HashMap<>();
		map.put("userUid", usualContactEntity.getUserUid());
		map.put("conName", usualContactEntity.getConName());
		map.put("cardType", usualContactEntity.getCardType());
		map.put("cardValue", usualContactEntity.getCardValue());
		map.put("conTel", usualContactEntity.getConTel());
		return usualContactDao.getContactByInfo(map);
	}
}
