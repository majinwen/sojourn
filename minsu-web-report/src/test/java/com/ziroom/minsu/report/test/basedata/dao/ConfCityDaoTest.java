package com.ziroom.minsu.report.test.basedata.dao;

import base.BaseTest;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.basedata.dao.ConfCityDao;
import com.ziroom.minsu.report.basedata.dto.NationRegionCityRequest;
import com.ziroom.minsu.report.basedata.vo.CityRegionVo;
import com.ziroom.minsu.report.basedata.dto.CityRegionRequest;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.vo.NationRegionCityVo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/1/19.
 * @version 1.0
 * @since 1.0
 */
public class ConfCityDaoTest extends BaseTest {


    private static final Logger LOGGER = LoggerFactory.getLogger(ConfCityDaoTest.class);

    @Resource(name="report.confCityDao")
    private ConfCityDao confCityDao;

    @Test
    public void getNationRegionCity(){
        try {
            NationRegionCityRequest cityRegionRequest = new NationRegionCityRequest();
            cityRegionRequest.setNationCode("100000");
            cityRegionRequest.setRegionFid("100000");
            cityRegionRequest.setCityCode("100000");
            List<NationRegionCityVo> list = confCityDao.getNationRegionCity(cityRegionRequest);
            System.out.println(JsonEntityTransform.Object2Json(list));
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}", e);
        }
    }



    @Test
    public void getOrderEvaluateListByCityCode(){
        try {
            CityRegionRequest cityRegionRequest = new CityRegionRequest();
            cityRegionRequest.setNationCode("100000");
            PagingResult<CityRegionVo> pagingResult = confCityDao.getCityRegionBypage(cityRegionRequest);

            System.out.println(JsonEntityTransform.Object2Json(pagingResult));
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}", e);
        }
    }




    @Test
    public void getCityByRegionFid(){
        try {

            List<ConfCityEntity> list = confCityDao.getCityByRegionFid("");

            System.out.println(JsonEntityTransform.Object2Json(list));
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}", e);
        }
    }

}
