package com.ziroom.minsu.services.search.namevalue;

import java.util.List;


/**
 * <p>NameValue</p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author afi on 2016/3/16.
 * @version 1.0
 * @since 1.0
 */
public class LongsNameValue extends NameValue<String, List<LongNameValue>> {

	public LongsNameValue(String n, List<LongNameValue> v) {
		super(n, v);
	}
}
