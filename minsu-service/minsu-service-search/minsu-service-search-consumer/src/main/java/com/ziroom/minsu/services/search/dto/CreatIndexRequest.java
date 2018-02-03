
package com.ziroom.minsu.services.search.dto;


import com.ziroom.minsu.services.common.dto.PageRequest;

import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 后台国家、城市查询参数
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author afi
 * @since 1.0
 * @version 1.0
 */
public class CreatIndexRequest extends PageRequest {


	/** areaCode */
	private String areaCode;


    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
