package com.ziroom.minsu.report.house.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.house.dto.HouseGrapherRequest;
import com.ziroom.minsu.report.house.valenum.HouseRequestTypeEnum;
import com.ziroom.minsu.report.house.vo.HouseGrapherVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author wangwentao 2017/5/5
 * @version 1.0
 * @since 1.0
 */
@Repository("report.housePhotoGrapherDao")
public class HousePhotoGrapherDao {

    private static final Logger logger = LoggerFactory.getLogger(HousePhotoGrapherDao.class);

    private String SQLID="report.housePhotoGrapherDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
      * 分页查询房源摄影信息
      * @author wangwentao
      * @created 2017/5/5 17:14
      *
      * @param
      * @return
      */
    public PagingResult<HouseGrapherVo> getHouseGrapherInfo(HouseGrapherRequest houseRequest){
        if(Check.NuNObj(houseRequest)){
            LogUtil.info(logger, "getHouseEvaInfo param :{}", JsonEntityTransform.Object2Json(houseRequest));
            return null;
        }
        if(Check.NuNObj(houseRequest.getRentWay())){
            LogUtil.info(logger, "getHouseEvaInfo param rentWay:{}", houseRequest.getRentWay());
            return null;
        }
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(houseRequest.getLimit());
        pageBounds.setPage(houseRequest.getPage());
        if(houseRequest.getRentWay() == HouseRequestTypeEnum.ENTIRE_RENT.getCode()){
            return mybatisDaoContext.findForPage(SQLID + "entireHouseGrapherInfo", HouseGrapherVo.class, houseRequest, pageBounds);
        }else if(houseRequest.getRentWay() == HouseRequestTypeEnum.SUB_RENT.getCode()){
            return mybatisDaoContext.findForPage(SQLID + "subHouseGrapherInfo", HouseGrapherVo.class, houseRequest, pageBounds);
        }
        return null;
    }

















}
