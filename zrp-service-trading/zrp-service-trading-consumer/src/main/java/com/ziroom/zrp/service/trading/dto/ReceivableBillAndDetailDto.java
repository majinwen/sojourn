package com.ziroom.zrp.service.trading.dto;

import com.ziroom.zrp.trading.entity.FinReceiBillDetailEntity;
import com.ziroom.zrp.trading.entity.FinReceiBillEntity;

import java.util.List;

/**
 * <p>应收账单和明细dto</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年11月20日 14:00
 * @since 1.0
 */
public class ReceivableBillAndDetailDto {
	private FinReceiBillEntity billEntity;
	private List<FinReceiBillDetailEntity> detailEntities;

	public FinReceiBillEntity getBillEntity() {
		return billEntity;
	}

	public void setBillEntity(FinReceiBillEntity billEntity) {
		this.billEntity = billEntity;
	}


	public List<FinReceiBillDetailEntity> getDetailEntities() {
		return detailEntities;
	}

	public void setDetailEntities(List<FinReceiBillDetailEntity> detailEntities) {
		this.detailEntities = detailEntities;
	}
}
