package com.ziroom.minsu.services.order.test.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.minsu.entity.order.FinancePunishEntity;
import com.ziroom.minsu.services.order.dao.FinancePunishDao;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.services.order.utils.OrderSnUtil;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.order.PunishStatusEnum;

/**
 * 
 * <p>
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年4月25日
 * @since 1.0
 * @version 1.0
 */
public class FinancePunishDaoTest extends BaseTest {

	@Resource(name = "order.financePunishDao")
	private FinancePunishDao financePunishDao;

	@Test
	public void saveFinancePunish() {
		FinancePunishEntity financePunishEntity = new FinancePunishEntity();
		financePunishEntity.setPunishSn(OrderSnUtil.getPunishSn());
		financePunishEntity.setOrderSn("1234565678");
		financePunishEntity.setCityCode("BJ");
		financePunishEntity.setPunishUid("1111");
		financePunishEntity.setPunishType(UserTypeEnum.LANDLORD.getUserType());
		financePunishEntity.setPunishFee(100);
		financePunishEntity.setPunishStatus(PunishStatusEnum.NO.getCode());
		financePunishEntity.setPunishDescribe("房东取消罚款");
		financePunishEntity.setLastPayTime(new Date());
		financePunishEntity.setCreateId("lishaochuan");
		int num = financePunishDao.saveFinancePunish(financePunishEntity);
		System.out.println("num:" + num);
	}


    @Test
    public void countPunishOverTime() {

        Long num = financePunishDao.countPunishOverTime("8a9e9a8b53d6da8b0153d6da8bae0000");
        System.out.println("num:" + num);
    }
}
