package com.ziroom.minsu.services.basedata.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>强制取消相关配置</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年5月3日
 * @since 1.0
 * @version 1.0
 */
public class ConfigForceVo extends BaseEntity {

	/** 序列化 id */
	private static final long serialVersionUID = 7153310866796998700L;

	/** 强制取消房东无责任时限 */
	private int limitDay;

	/** 罚金交付时限 */
	private int punishDay;

	/** 罚金天数 */
	private Integer tillDay;

    /** 付钱金额 */
    private int dayPrice;

	public int getLimitDay() {
		return limitDay;
	}

	public void setLimitDay(int limitDay) {
		this.limitDay = limitDay;
	}

	public int getPunishDay() {
		return punishDay;
	}

	public void setPunishDay(int punishDay) {
		this.punishDay = punishDay;
	}

	public int getTillDay() {
		return tillDay;
	}

	public void setTillDay(int tillDay) {
		this.tillDay = tillDay;
	}

    public Integer getDayPrice() {
        return dayPrice;
    }

    public void setDayPrice(Integer dayPrice) {
        this.dayPrice = dayPrice;
    }
}
