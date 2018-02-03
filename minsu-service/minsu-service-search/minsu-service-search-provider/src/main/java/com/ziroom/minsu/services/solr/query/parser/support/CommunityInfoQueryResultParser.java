package com.ziroom.minsu.services.solr.query.parser.support;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.search.entity.HouseInfoEntity;
import com.ziroom.minsu.services.solr.query.parser.AbstractQueryResultParser;
import org.apache.solr.common.SolrDocument;

import java.util.Map;

/**
 * <p>房源的搜索</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/16.
 * @version 1.0
 * @since 1.0
 */
public class CommunityInfoQueryResultParser extends AbstractQueryResultParser<HouseInfoEntity>{


    /**
     * 重写了参数解析
     * @param doc
     * @return
     */
    @Override
    protected HouseInfoEntity doParser(Map<String,Object> par,SolrDocument doc) {
        if(Check.NuNObj(doc)){
            return null;
        }
        HouseInfoEntity house = new HouseInfoEntity();
        house.setPicUrl(ValueUtil.getStrValue(doc.getFieldValue("picUrl")));
        house.setPrice(ValueUtil.getintValue(doc.getFieldValue("price")));
        house.setPersonCount(ValueUtil.getintValue(doc.getFieldValue("personCount")));
        house.setRoomCount(ValueUtil.getintValue(doc.getFieldValue("roomCount")));
        house.setOrderTypeName(ValueUtil.getStrValue(doc.getFieldValue("orderTypeName")));
        house.setHouseTypeName(ValueUtil.getStrValue(doc.getFieldValue("houseTypeName")));
        house.setHouseName(ValueUtil.getStrValue(doc.getFieldValue("houseName")));
        house.setHouseAddr(ValueUtil.getStrValue(doc.getFieldValue("houseAddr")));
        house.setUpdateTime(ValueUtil.getlongValue(doc.getFieldValue("refreshDate")));
        house.setLandlordUrl(ValueUtil.getStrValue(doc.getFieldValue("landlordUrl")));
        house.setWeights(ValueUtil.getlongValue(doc.getFieldValue("weights")));
        house.setDist(ValueUtil.getStrValue(doc.getFieldValue("dist")));
        return house;
    }


}
