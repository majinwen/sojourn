package com.ziroom.minsu.services.basedata.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.services.search.vo.SubwayStationVo;


/**
 * <p>模板的配置</p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi
 * @version 1.0
 * @since 1.0
 */
@Repository("search.subwayDbInfoDao")
public class SubwayDbInfoDao {




    private String SQLID="search.subwayDbInfoDao.";

    @Autowired
    @Qualifier("searchBase.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    
    
    /**
     * 获取地铁站点信息
     * @author lishaochuan
     * @create 2016年8月23日下午3:07:16
     * @param cityCode
     * @return
     */
    public List<SubwayStationVo> getSubwayStation(String cityCode){
    	if(Check.NuNStr(cityCode) ){
            return null;
        }
    	return mybatisDaoContext.findAll(SQLID + "getSubwayStation", cityCode);
    }



}
