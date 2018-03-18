package com.ziroom.minsu.report.board.vo;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>返回目标item</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd on 2017/1/11.
 * @version 1.0
 * @since 1.0
 */
public class RegionItem extends BaseEntity{

    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -8375681603485685201L;

	/**
	 * 大区逻辑id
	 */
	private String regionFid;
    
	/**
	 * 大区名称
	 */
    private String regionName;
    
    /**
     * 国家编码
     */
    private String nationCode;
    
    /**
     * 国家名称
     */
    private String nationName;

    public String getRegionFid() {
        return regionFid;
    }

    public void setRegionFid(String regionFid) {
        this.regionFid = regionFid;
    }

	public String getRegionName() {
		return regionName;
	}
	
	public String getNationCode() {
		return nationCode;
	}
	
	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}
}
