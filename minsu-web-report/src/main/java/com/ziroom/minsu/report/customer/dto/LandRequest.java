package com.ziroom.minsu.report.customer.dto;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.report.basedata.dto.CityRegionRequest;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;

import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>房东信息查询条件</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/2/19.
 * @version 1.0
 * @since 1.0
 */
public class LandRequest extends CityRegionRequest {

    

    /**
	 * 
	 */
	private static final long serialVersionUID = -972065038242623100L;

	/**
     * 开始时间
     */
    private String starTimeStr;

    /**
     * 结束时间
     */
    private String endTimeStr;


    /**
     * 开始时间
     */
    private Date starTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 城市code
     */
    private List<String>  cityList;
    
    /**
	 * 国家编码
	 */
	private String nationCode;
    
    /**
     * 大区名字
     */
    private String regionName;
    /**
     * 大区fid
     */
    private String regionFid;
    /**
     * 城市名字
     */
    private String cityName;
    /**
     * 城市code
     */
    private String cityCode;

    public Date getStarTime() {
        return starTime;
    }

    public void setStarTime(Date starTime) {
        this.starTime = starTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<String> getCityList() {
        return cityList;
    }

    public void setCityList(List<String> cityList) {
        this.cityList = cityList;
    }


    public String getStarTimeStr() {
        return starTimeStr;
    }

    public void setStarTimeStr(String starTimeStr) {
        this.starTimeStr = starTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }
    
    
    /**
	 * @return the nationCode
	 */
	public String getNationCode() {
		return nationCode;
	}

	/**
	 * @param nationCode the nationCode to set
	 */
	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	/**
	 * @return the regionName
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * @param regionName the regionName to set
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * @return the regionFid
	 */
	public String getRegionFid() {
		return regionFid;
	}

	/**
	 * @param regionFid the regionFid to set
	 */
	public void setRegionFid(String regionFid) {
		this.regionFid = regionFid;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
     * 获取当前时间的对称时间
     * @param old
     * @return
     */
    public static LandRequest clone2negative(LandRequest old){

        LandRequest newObj = new LandRequest();
        BeanUtils.copyProperties(old,newObj);
        if (!Check.NuNObj(newObj.getStarTime())){
            newObj.setEndTime(newObj.getStarTime());
        }else {
            return null;
        }
        Date end = new Date();
        if (!Check.NuNObj(old.getEndTime())){
            end = old.getEndTime();
        }
        Integer count = DateSplitUtil.countDateSplit(newObj.getStarTime(),end);
        newObj.setStarTime(DateSplitUtil.jumpDate(newObj.getEndTime(),-count));
        return newObj;
    }
}
