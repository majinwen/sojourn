package com.ziroom.minsu.report.house.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>房源统计返回结果</p>
 * <PRE>
 * </PRE>
 *
 * @author afi
 * @version 1.0
 * @since 1.0
 */
public class HouseCountResultVo extends BaseEntity{
    /**
	 * 
	 */
	@FieldMeta(skip = true)
	private static final long serialVersionUID = -3150347032505006355L;
	@FieldMeta(name="城市编号",order=1)
	private String cityCode;
	@FieldMeta(name="城市名称",order=2)
    private String cityName;
	@FieldMeta(name="发布量",order=3)
    private Long publish;
	@FieldMeta(name="管家拒绝量",order=4)
    private Long guardRejict;
	@FieldMeta(name="管家接受量",order=5)
    private Long guardAccept;
	@FieldMeta(name="品质拒绝量",order=6)
    private Long qualityReject;
	@FieldMeta(name="上架量",order=7)
    private Long on;
	@FieldMeta(name="下架量",order=8)
    private Long off;
	@FieldMeta(name="强制下架量",order=9)
    private Long forceOff;

    public HouseCountResultVo(){
    	
    }

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getPublish() {
		return publish;
	}

	public void setPublish(Long publish) {
		this.publish = publish;
	}

	public Long getGuardRejict() {
		return guardRejict;
	}

	public void setGuardRejict(Long guardRejict) {
		this.guardRejict = guardRejict;
	}

	public Long getGuardAccept() {
		return guardAccept;
	}

	public void setGuardAccept(Long guardAccept) {
		this.guardAccept = guardAccept;
	}

	public Long getQualityReject() {
		return qualityReject;
	}

	public void setQualityReject(Long qualityReject) {
		this.qualityReject = qualityReject;
	}

	public Long getOn() {
		return on;
	}

	public void setOn(Long on) {
		this.on = on;
	}

	public Long getOff() {
		return off;
	}

	public void setOff(Long off) {
		this.off = off;
	}

	public Long getForceOff() {
		return forceOff;
	}

	public void setForceOff(Long forceOff) {
		this.forceOff = forceOff;
	}
    
    

}