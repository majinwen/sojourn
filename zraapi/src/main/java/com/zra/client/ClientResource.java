package com.zra.client;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.zrp.service.trading.valenum.ZKConfigEnum;
import com.zra.business.entity.dto.*;
import com.zra.business.logic.BusinessLogic;
import com.zra.cardCoupon.logic.CardCouponLogic;
import com.zra.common.dto.appbase.AppBaseDto;
import com.zra.common.dto.pay.*;
import com.zra.common.enums.ErrorEnum;
import com.zra.common.error.AppResult;
import com.zra.common.error.ResultException;
import com.zra.common.security.DESUtils;
import com.zra.common.utils.PropUtils;
import com.zra.common.utils.StrUtils;
import com.zra.common.utils.ZraApiConst;
import com.zra.evaluate.entity.dto.*;
import com.zra.evaluate.logic.EvaluateLogic;
import com.zra.house.entity.dto.*;
import com.zra.house.logic.ProjectLogic;
import com.zra.pay.logic.PayLogic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * App接口
 *
 * @Author: wangxm113
 * @CreateDate: 2016-07-28
 */
@Component
@Path("/client/")
@Api(value = "/client")
public class ClientResource {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ClientResource.class);

    @Autowired
    private ProjectLogic projectLogic;

    @Autowired
    private BusinessLogic businessLogic;

    @Autowired
    private EvaluateLogic evaluateLogic;

    @Autowired
    private PayLogic payLogic;
    @Autowired
    private CardCouponLogic cardCouponLogic;


    @Autowired
    @Qualifier("basedata.zkSysService")
    private ZkSysService zkSysService;

    @POST
    @Path("/project/search/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "项目搜索", notes = ZraApiConst.CON_NEED_PARAM
            + "<p>city(String)-城市;<br/>price({minPrice(Double)-最低价格; maxPrice(Double)-最高价格;})-价格区间"
            + "<br/>checkInTime(String)-可入住时间", response = ProjectListDto.class)
    public AppResult getSearchProject(@FormParam("p") String p, @FormParam("sign") String sign) {
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            SearchOfProjectReturnDto dto = jacksonReadValue(p, SearchOfProjectReturnDto.class);
            ProjectListDto result = projectLogic.getSearchProject(dto);
            return AppResult.toSuccess(result);
        } catch (Exception e) {
            LOGGER.error("[项目搜索]出错！", e);
            return AppResult.toFail(e);
        }
    }

    @POST
    @Path("/ht/search/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "房型搜索", notes = ZraApiConst.CON_NEED_PARAM
            + "<p>projectId(String)-项目的id"
            + "<br/>price({minPrice(Double)-最低价格; maxPrice(Double)-最高价格;})-价格区间"
            + "<br/>area({minArea(Double)-最小面积; maxArea(Double)-最大面积;})-面积区间"
            + "<br/>checkInTime(String)-可入住时间"
            + "<br/>floor(String)-楼层"
            + "<br/>direction(String)-朝向", response = ProjectDetailDto.class)
    public AppResult getSearchHouseType(@FormParam("p") String p, @FormParam("sign") String sign) {
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            SearchOfHTReturnDto dto = jacksonReadValue(p, SearchOfHTReturnDto.class);
            ProjectDetailDto result = projectLogic.getSearchHouseType(dto);
            return AppResult.toSuccess(result);
        } catch (Exception e) {
            LOGGER.error("[房型搜索]出错！", e);
            return AppResult.toFail(e);
        }
    }


    /**
     * 条件查询房型信息（包括房型配置信息）
     * @param p
     * @param sign
     * @return
     */
    @POST
    @Path("/housetype/search/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "条件查询房型信息（包括房型配置信息）(0608App优化)", notes = ZraApiConst.CON_NEED_PARAM
            + "<br/>houseTypeId(String)-房型id", response = HouseTypeDetailDto.class)
    public AppResult getHouseTypeInfo(@FormParam("p") String p, @FormParam("sign") String sign){

        if (StrUtils.isNullOrBlank(p)){
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            HouseTypeReqDto dto = jacksonReadValue(p, HouseTypeReqDto.class);
            if(StringUtils.isBlank(dto.getHouseTypeId())) {
            	throw new ResultException(ErrorEnum.MSG_PARAM_NULL);
            }
            HouseTypeDetailDto result = projectLogic.findHouseTypeDetail(dto);
            if(result == null){
                return AppResult.toFail(null,ErrorEnum.MSG_FAIL);
            }else{
                return AppResult.toSuccess(result);
            }
        } catch (Exception e) {
            LOGGER.error("[条件查询房型信息]出错！", e);
            return AppResult.toFail(e);
        }
    }

    /**
     * 条件查询房间信息
     * <p>
     *
     *     修改人  --cuigh6
     *     修改内容-- 房间列表 固定返回5条数据
     * </p>
     * @param p
     * @param sign
     * @return
     */
    @POST
    @Path("/room/search/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "房间搜索", notes = ZraApiConst.CON_NEED_PARAM
            + "<br/>houseTypeId(String)-房型id"
            + "<br/>roomNumber(String)-房间号"
            + "<br/>price({minPrice(Double)-最低价格; maxPrice(Double)-最高价格;})-价格区间"
            + "<br/>area({minArea(Double)-最小面积; maxArea(Double)-最大面积;})-面积区间"
            + "<br/>checkInTime(String)-可入住时间"
            + "<br/>floor(String)-楼层"
            + "<br/>direction(String)-朝向"
            + "<br/>pageNum(Integer)-获取第几页"
            + "<br/>pageSize(Integer)-每页数量", response = RoomPriceDetailDto.class)
    public AppResult getSearchRoomInfo(@FormParam("p") String p, @FormParam("sign") String sign){

        if (StrUtils.isNullOrBlank(p)){
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            SearchRoomReqDto dto = jacksonReadValue(p, SearchRoomReqDto.class);
            int pageNum = dto.getPageNum();
            final String DEFAULT_PAGENUM = "1";//默认的页数
            final String ROOM_SEARCH_PAGE_SWITCH = "ROOM_SEARCH_PAGE_SWITCH";
            final String DEFAULT_SWITCH = "true";
            final String ROOM_SEARCH_PAGESIZE = "ROOM_SEARCH_PAGESIZE";
            String DEFAULT_PAGESIZE = "5";//默认的大小
            String pageSwitch = PropUtils.getString(ROOM_SEARCH_PAGE_SWITCH)==null ? DEFAULT_SWITCH:PropUtils.getString(ROOM_SEARCH_PAGE_SWITCH);
            String pageSize = PropUtils.getString(ROOM_SEARCH_PAGESIZE) == null ? DEFAULT_PAGESIZE : PropUtils.getString(ROOM_SEARCH_PAGESIZE);
            //设置默认页码和分页
            if (Boolean.valueOf(pageSwitch)) {
                dto.setPageNum(Integer.parseInt(DEFAULT_PAGENUM));
                dto.setPageSize(Integer.parseInt(pageSize));
            }

            RoomPriceDetailDto result = projectLogic.getRoomDetailByCondition(dto);
            if (Boolean.valueOf(pageSwitch)) {
                result.setTotal((long)result.getRoomDetailDtoList().size());//ios 判断 将总个数 设置为房间列表数据量 ios 就不再请求
                if (pageNum > 1) {//android 判断 如果页码大于1 返回总的个数为0
                    result.setTotal(0L);
                    result.setRoomDetailDtoList(new ArrayList<RoomDetailDto>());
                }
            }
            return AppResult.toSuccess(result);
        } catch (Exception e) {
            LOGGER.error("[房间搜索]出错！", e);
            return AppResult.toFail(e);
        }
    }

    /**
     * jackson将json串解析成类
     *
     * @Author: wangxm113
     * @CreateDate: 2016-07-29
     */
    private <T> T jacksonReadValue(String p, Class<T> className) throws Exception {
        String decryptP = DESUtils.decrypt(p);// 解密
        return new ObjectMapper()
                .configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false)// 解析未知参数
                .readValue(decryptP, className);
    }

    /**
     * 获取首页(项目)查询条件
     * @param p
     * @param sign
     */
    @POST
    @Path("/project/searchCondition/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "获取首页(项目)查询条件", notes = ZraApiConst.CON_NEED_PARAM, response = SearchConditionDto.class)
    public AppResult getSearchCondition(@FormParam("p") String p, @FormParam("sign") String sign){
        if(StrUtils.isNullOrBlank(p)){
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }

        try {
            AppBaseDto dto = jacksonReadValue(p, AppBaseDto.class);
            LOGGER.info("获取首页(项目)查询条件start:");
            SearchConditionDto searchConditionDto = projectLogic.getSearchCondition();
            LOGGER.info("获取首页(项目)查询条件data:"+JsonEntityTransform.Object2Json(searchConditionDto));
            return AppResult.toSuccess(searchConditionDto);
        } catch (Exception e) {
            LOGGER.error("[获取首页(项目)查询条件]出错！", e);
            return AppResult.toFail(e);
        }
    }
    
    
    /**
     * 0608App首页优化
     * 获取项目列表页查询条件
     * @author tianxf9
     * @param p
     * @param sign
     */
    @POST
    @Path("/project/searchCondition/v2")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "获取项目列表页查询条件（0608App优化）", notes = ZraApiConst.CON_NEED_PARAM, response = SearchProjectCondition.class)
    public AppResult getSearchProCondition(@FormParam("p") String p, @FormParam("sign") String sign){
    	
         if(StrUtils.isNullOrBlank(p)){
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
         
        try {
            LOGGER.info("获取项目列表页查询条件（0608App优化）start:");
            SearchProjectCondition searchProjectCondition = projectLogic.getSearchProCondition();
            LOGGER.info("获取项目列表页查询条件（0608App优化）data:"+JsonEntityTransform.Object2Json(searchProjectCondition));
            return AppResult.toSuccess(searchProjectCondition);
        } catch (Exception e) {
            LOGGER.error("获取项目列表页查询条件（0608App优化）出错！", e);
            return AppResult.toFail(e);
        }
    }

    @POST
    @Path("/business/apply/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "预约看房", notes = ZraApiConst.CON_NEED_PARAM
            + "<p>uuid(String)-用户的uid"
            + "<br/>name(String)-姓名"
            + "<br/>phone(String)-手机号"
            + "<br/>company(String)-工作单位"
            + "<br/>expectTime(String)-约看时间(yyyy-MM-dd HH:mm)"
            + "<br/>expectInStartTime(String)-期望入住时间(yyyy-MM-dd)"
            + "<br/>message(String)-给ZO留言"
            + "<br/>projectId(String)-约看项目id"
            + "<br/>htId(String)-约看房型id", response = BusinessApplyReturnDto.class)
    public AppResult insertBusinessApply(@FormParam("p") String p, @FormParam("sign") String sign) {
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            BusinessOrderDto dto = jacksonReadValue(p, BusinessOrderDto.class);
            dto.setSourceOfBu((byte) 2);
            //dto.setSourceZra((byte) 2);
            BusinessApplyReturnDto result = businessLogic.insertBusinessApply(dto);
            return AppResult.toSuccess(result);
        } catch (Exception e) {
            LOGGER.error("[预约看房]出错！", e);
            return AppResult.toFail(e);
        }
    }

//    @POST
//    @Path("/business/unfinish/v1")
//    @Produces("application/json")
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @ApiOperation(value = "未完成约看信息查询", notes = ZraApiConst.CON_NEED_PARAM
//            + "<p>uid(String)-用户的uid" +
//            "<br/>state(String)-状态 0:未完成；1:已完成", response = BusinessNoFinishListDto.class)
//    public AppResult businessUnfinishList(@FormParam("p") String p, @FormParam("sign") String sign) {
//        if (StrUtils.isNullOrBlank(p)) {
//            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
//        }
//        try {
//            BusinessListReqDto dto = jacksonReadValue(p, BusinessListReqDto.class);
//            List<BusinessNoFinishListDto> result = businessLogic.businessUnfinishList(dto.getUid());
//            return AppResult.toSuccess(result);
//        } catch (Exception e) {
//            LOGGER.info("[约看信息查询]出错！", e);
//            return AppResult.toFail(e);
//        }
//    }

    @POST
    @Path("/business/list/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "商机列表查询", notes = ZraApiConst.CON_NEED_PARAM
            + "<p>uid(String)-用户的uid"
            + "<br/>state(String)-状态 0:未完成；1:已完成", response = BusinessListDto.class)
    public AppResult businessFinishedList(@FormParam("p") String p, @FormParam("sign") String sign) {
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            BusinessListReqDto dto = jacksonReadValue(p, BusinessListReqDto.class);
            List<BusinessListDto> result = businessLogic.businessList(dto.getUid(), dto.getState());
            return AppResult.toSuccess(result);
        } catch (Exception e) {
            LOGGER.error("[约看信息查询]出错！", e);
            return AppResult.toFail(e);
        }
    }

    @POST
    @Path("/business/cancel/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "约看取消", notes = ZraApiConst.CON_NEED_PARAM
            + "<p>bid(String)-约看业务id")
    public AppResult cancelBusinessById(@FormParam("p") String p, @FormParam("sign") String sign) {
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            BusinessReqDto dto = jacksonReadValue(p, BusinessReqDto.class);
            Boolean result = businessLogic.cancelBusiness(dto.getBid());
            if (result){
                return AppResult.toSuccess(result);
            }else{
                return AppResult.toFail(result);
            }
        } catch (Exception e) {
            LOGGER.error("[约看信息查询]出错！", e);
            return AppResult.toFail(e);
        }
    }

    /**
     * 获取房型查询条件
     * @param p
     * @param sign
     */
    @POST
    @Path("/houseType/searchCondition/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "获取房型查询条件", notes = ZraApiConst.CON_NEED_PARAM
            + "<br/>projectId(String)-项目id", response = HouseTypeConditionDto.class)
    public AppResult getHouseTypeSearchCondition(@FormParam("p") String p, @FormParam("sign") String sign){
        if(StrUtils.isNullOrBlank(p)){
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            SearchReqDto searchReqDto = jacksonReadValue(p, SearchReqDto.class);
            LOGGER.info("获取首页(项目)查询条件start:");
            HouseTypeConditionDto searchConditionDto = projectLogic.getHouseTypeSearchConditon(searchReqDto);
            LOGGER.info("获取首页(项目)查询条件data:"+JsonEntityTransform.Object2Json(searchConditionDto));
            return AppResult.toSuccess(searchConditionDto);
        } catch (Exception e) {
            LOGGER.error("[获取房型查询条件]出错！", e);
            return AppResult.toFail(e);
        }

    }

    /**
     * 获取房间查询条件
     * @param p
     * @param sign
     */
    @POST
    @Path("/room/searchCondition/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "获取房间查询条件", notes = ZraApiConst.CON_NEED_PARAM
            + "<br/>houseTypeId(String)-房型id", response = RoomConditionDto.class)
    public AppResult getRoomSearchCondition(@FormParam("p") String p, @FormParam("sign") String sign){
        if(StrUtils.isNullOrBlank(p)){
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            SearchRoomReqDto searchReqDto = jacksonReadValue(p, SearchRoomReqDto.class);
            LOGGER.info("获取房间查询条件start:");
            RoomConditionDto searchConditionDto = projectLogic.getRoomSearchConditon(searchReqDto);
            LOGGER.info("获取房间查询条件data:"+JsonEntityTransform.Object2Json(searchConditionDto));
            return AppResult.toSuccess(searchConditionDto);
        } catch (Exception e) {
            LOGGER.error("[获取房间查询条件]出错！", e);
            return AppResult.toFail(e);
        }

    }

//    /**
//     * 获取房型配置物品
//     * @param p
//     * @param sign
//     */
//    @POST
//    @Path("/house/config/v1")
//    @Produces("application/json")
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @ApiOperation(value = "获取房型配置物品", notes = ZraApiConst.CON_NEED_PARAM
//            + "<br/>houseTypeId(String)-房型id", response = HouseConfigDto.class)
//    public AppResult findItemsConfigByHouseTypeId(@FormParam("p") String p, @FormParam("sign") String sign){
//        if(StrUtils.isNullOrBlank(p)){
//            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
//        }
//        try {
//            HouseTypeReqDto searchReqDto = jacksonReadValue(p, HouseTypeReqDto.class);
//            List<HouseConfigDto> houseConfigDtoList = projectLogic.findItemsConfigByHouseTypeId(searchReqDto.getHouseTypeId());
//            return AppResult.toSuccess(houseConfigDtoList);
//        } catch (Exception e) {
//            LOGGER.info("[获取房型配置物品]出错！", e);
//            return AppResult.toFail(e);
//        }
//    }


    @POST
    @Path("/evaluate/question/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "获取评价问题信息", notes = ZraApiConst.CON_NEED_PARAM
            + "<p/>channelCode(String)-渠道编码"
            + "<br/>beEvaluatorId(String)-受评对象id"
            + "<br/>beEvaluatorType(String)-受评对象类型"
            + "<br/>questionType(String)-定义问题类型，问题类型的编码值", response = GetQuestionReturnDto.class)
    public AppResult getEvaluateQuestions(@FormParam("p") String p, @FormParam("sign") String sign) {
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            GetQuestionDto dto = jacksonReadValue(p, GetQuestionDto.class);
            GetQuestionReturnDto result = evaluateLogic.getQuestion(dto);
            return AppResult.toSuccess(result);
        } catch (Exception e) {
            LOGGER.error("[获取评价问题信息]出错！", e);
            return AppResult.toFail(e);
        }

    }

    @POST
    @Path("/evaluate/answer/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "评价问题采集", notes = ZraApiConst.CON_NEED_PARAM
            + "<p/>beEvaluateId(String)-受评人id"
            +"<br/>businessBid(Integer)-商机的业务id"
            + "<br/>tokenId(String)-接口中返回的tokenId"
            + "<br/>evaluateMsg({<p/>requesterId(String)-请评对象id" +
            "                    <br/>requesterType(String)-请评对象类型" +
            "                    <br/>orderCode(String)-评价的订单号或合同号" +
            "                    <br/>ext(String)-拓展字段" +
            "                    <br/>questions([{" +
            "                                     <p/>code(String)-问题编码" +
            "                                     <br/>optionCode(String)-问题选择项" +
            "                                     <br/>content(String)-评价内容" +
            "                                     <br/>picUrl(String)-图片的路径}]" +
            "                              <br/>)-问题" +
            "              <br/>)-数据")
    public AppResult saveEvaluate(@FormParam("p") String p, @FormParam("sign") String sign) {
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            EvaluateDto dto = jacksonReadValue(p, EvaluateDto.class);
            evaluateLogic.evaluatePlatform(dto);
            return AppResult.toSuccess(null);
        } catch (Exception e) {
            LOGGER.error("[评价问题采集]出错！", e);
            return AppResult.toFail(e);
        }

    }

    @POST
    @Path("/evaluate/history/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "获取评价历史详情", notes = ZraApiConst.CON_NEED_PARAM
            + "<p/>requesterId(String)-请评对象uid"
            + "<br/>tokenId(String)-评价系统生成的评价唯一标识", response = EvaluateHistoryReturnDto.class)
    public AppResult getEvaluateHistory(@FormParam("p") String p, @FormParam("sign") String sign) {
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            EvaluateHistoryDto dto = jacksonReadValue(p, EvaluateHistoryDto.class);
            EvaluateHistoryReturnDto result = evaluateLogic.getEvaluateHistory(dto);
            return AppResult.toSuccess(result);
        } catch (Exception e) {
            LOGGER.error("[获取评价历史详情]出错！", e);
            return AppResult.toFail(e);
        }

    }

    @POST
    @Path("/project/list/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "项目列表", notes = ZraApiConst.CON_NEED_PARAM, response = ProjectListReturnDto.class)
    public AppResult getProjectList(@FormParam("p") String p, @FormParam("sign") String sign) {
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(new ArrayList(), ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            List<ProjectListReturnDto> result = projectLogic.getProjectList();
            return AppResult.toSuccess(result);
        } catch (Exception e) {
            LOGGER.error("[项目列表]出错！", e);
            return AppResult.toFailList(e);
        }

    }

    @POST
    @Path("/business/unevaluate/list/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "待评价约看列表", notes = ZraApiConst.CON_NEED_PARAM
            + "<p>uid(String)-用户的uid", response = BusinessListDto.class)
    public AppResult businessUnevaluateList(@FormParam("p") String p, @FormParam("sign") String sign) {
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            BusinessListReqDto dto = jacksonReadValue(p, BusinessListReqDto.class);
            List<BusinessListDto> result = businessLogic.businessUnevaluateList(dto.getUid());
            return AppResult.toSuccess(result);
        } catch (Exception e) {
            LOGGER.error("[约看信息查询]出错！", e);
            return AppResult.toFail(e);
        }
    }


    /**
     * 获取待支付列表 created by cuigh6 on 2016/12/24
     * @param p 加密
     * @param sign 加密
     * @return
     */
    @POST
    @Path("/toPay/list/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "获取待支付列表", notes = ZraApiConst.CON_NEED_PARAM
            + "<p>uid(String)-用户的uid", response = ToPayDto.class)
    public AppResult getToPayList(@FormParam("p") String p, @FormParam("sign") String sign) {
        if (!isUpgrade()) {
            return AppResult.toFail(new ArrayList<>(), ErrorEnum.FORCE_UPGRADE_TXT);// update by cuigh6 强制升级到新版app
        }
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            ToPayDto dto = jacksonReadValue(p, ToPayDto.class);
            List<ToPayDto> result = payLogic.getToPayList(dto.getUid());
            return AppResult.toSuccess(result);
        } catch (Exception e) {
            LOGGER.error("[待支付列表]出错！", e);
            return AppResult.toFail(e);
        }
    }

    /**
     * 获取账单详情 created by cuigh6 on 2016/12/24
     * @param p 加密
     * @param sign 加密
     * @return
     */
    @POST
    @Path("/bill/detail/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "获取账单详情", notes = ZraApiConst.CON_NEED_PARAM
            + "<p>billFid(String)-账单的标识", response = BillDetailDto.class)
    public AppResult getBillDetail(@FormParam("p") String p, @FormParam("sign") String sign) {
        /*if ("true".equals(PropUtils.getString("isUpgrade"))) {
            return AppResult.toFail(null, ErrorEnum.FORCE_UPGRADE_TXT);// update by cuigh6 强制升级到新版app
        }*/
        if (!isUpgrade()) {
            return AppResult.toFail(null, ErrorEnum.FORCE_UPGRADE_TXT);// update by cuigh6 强制升级到新版app
        }
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            BillDetailDto dto = jacksonReadValue(p, BillDetailDto.class);
            BillDetailDto result = payLogic.getBillDetail(dto);
            return AppResult.toSuccess(result);
        } catch (Exception e) {
            LOGGER.error("[账单详情]出错！", e);
            return AppResult.toFail(e);
        }
    }
    
    
    
    /**
     * 获取账单详情(对接卡券系统) 
     * @author tianxf9
     * @param p 加密
     * @param sign 加密
     * @return
     */
    @POST
    @Path("/bill/detail/v2")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "获取账单详情（对接卡券系统）", notes = ZraApiConst.CON_NEED_PARAM
            + "<p>billFid(String)-账单的标识<br/>uid(String)-用户uid", response = BillDetailDto.class)
    public AppResult getNewBillDetail(@FormParam("p") String p, @FormParam("sign") String sign) {
        /*if ("true".equals(PropUtils.getString("isUpgrade"))) {
            return AppResult.toFail(null, ErrorEnum.FORCE_UPGRADE_TXT);// update by cuigh6 强制升级到新版app
        }*/
        if (!isUpgrade()) {
            return AppResult.toFail(null, ErrorEnum.FORCE_UPGRADE_TXT);// update by cuigh6 强制升级到新版app
        }
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            BillDetailDto dto = jacksonReadValue(p, BillDetailDto.class);
            if(StringUtils.isBlank(dto.getBillFid())||StringUtils.isBlank(dto.getUid())) {
            	throw new ResultException(ErrorEnum.MSG_PARAM_NULL);
            }
            BillDetailDto result = payLogic.getNewBillDetail(dto);
            return AppResult.toSuccess(result);
        } catch (Exception e) {
            LOGGER.error("[账单详情]出错！", e);
            return AppResult.toFail(e);
        }
    }
    
    
    /**
     * 兑换卡券的接口(对接卡券系统) 
     * @author tianxf9
     * @param p 加密
     * @param sign 加密
     * @return
     */
    @POST
    @Path("/cardcoupon/exchange/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "兑换优惠券（对接卡券系统）", notes = ZraApiConst.CON_NEED_PARAM
            + "<p>redeemCode(String)-兑换码<br/>uid(String)-兑换人id(用户uid)", response = CardCouponDto.class)
    public AppResult exchangeCoupon(@FormParam("p") String p, @FormParam("sign") String sign) {
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
        	ExchangeCouponDto dto = jacksonReadValue(p, ExchangeCouponDto.class);
        	if(StringUtils.isBlank(dto.getRedeemCode())||StringUtils.isBlank(dto.getUid())) {
        		throw new ResultException(ErrorEnum.MSG_PARAM_NULL);
        	}
        	CardCouponDto coupon = this.cardCouponLogic.couponBind(dto.getRedeemCode(), dto.getUid());
            return AppResult.toSuccess(coupon);
        } catch (Exception e) {
            LOGGER.error("兑换卡券出错！", e);
            return AppResult.toFail(e);
        }
    }
    
    
    /**
     * 获取优惠券列表(对接卡券系统) 
     * @author tianxf9
     * @param p 加密
     * @param sign 加密
     * @return
     */
    @POST
    @Path("/cardcoupon/getlist/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "获取优惠券列表（对接卡券系统）", notes = ZraApiConst.CON_NEED_PARAM
            + "<p>uid(String)-用户uid", response = CardCouponDto.class)
    public AppResult getCouponList(@FormParam("p") String p, @FormParam("sign") String sign) {
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
        	ExchangeCouponDto dto = jacksonReadValue(p, ExchangeCouponDto.class);
        	if(StringUtils.isBlank(dto.getUid())) {
        		throw new ResultException(ErrorEnum.MSG_PARAM_NULL);
        	}
        	//modify by tianxf9 自如六周年专享自如寓服务费直减券——关闭app优惠券消费
        	//List<CardCouponDto> coupon = this.cardCouponLogic.couponQuery(dto.getUid(), SysConstant.CARD_COUPON_STATUS);
        	List<CardCouponDto> coupon = new ArrayList<>();
            return AppResult.toSuccess(coupon);
        } catch (Exception e) {
            LOGGER.error("查询优惠券列表出错", e);
            return AppResult.toFail(e);
        }
    }

    /**
     * 获取我的合同列表 created by cuigh6 on 2016/12/24
     * @param p 加密
     * @param sign 加密
     * @return
     */
    @POST
    @Path("/contracts/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "获取我的合同列表", notes = ZraApiConst.CON_NEED_PARAM
            + "<p>uid(String)-用户的uid", response = MyContractDto.class)
    public AppResult getMyContractList(@FormParam("p") String p, @FormParam("sign") String sign) {
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(new ArrayList<>(), ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            MyContractDto dto = jacksonReadValue(p, MyContractDto.class);
            List<MyContractDto> result = payLogic.getMyContractList(dto.getUid());
            return AppResult.toSuccess(result);
        } catch (Exception e) {
            LOGGER.error("[合同列表]出错！", e);
            return AppResult.toFail(e);
        }
    }

    /**
     * 获取我的合同详情 created by cuigh6 on 2016/12/24
     * @param p 加密
     * @param sign 加密
     * @return
     */
    @POST
    @Path("/contracts/detail/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "获取我的合同详情", notes = ZraApiConst.CON_NEED_PARAM
            + "<p>contractId(String)-合同标识", response = MyContractDetailDto.class)
    public AppResult getContractDetail(@FormParam("p") String p, @FormParam("sign") String sign) {
        /*if ("true".equals(PropUtils.getString("isUpgrade"))) {
            return AppResult.toFail(null, ErrorEnum.FORCE_UPGRADE_TXT);// update by cuigh6 强制升级到新版app
        }*/
        if (!isUpgrade()) {
            return AppResult.toFail(null, ErrorEnum.FORCE_UPGRADE_TXT);// update by cuigh6 强制升级到新版app
        }
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            MyContractDetailDto dto = jacksonReadValue(p, MyContractDetailDto.class);
            MyContractDetailDto result = payLogic.getContractDetail(dto.getContractId());
            return AppResult.toSuccess(result);
        } catch (Exception e) {
            LOGGER.error("[合同详情]出错！", e);
            return AppResult.toFail(e);
        }
    }

    /**
     * 查询合同账单列表 created by cuigh6 on 2016/12/24
     * @param p 加密
     * @param sign 加密
     * @return
     */
    @POST
    @Path("/contracts/bills/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "查询合同账单列表", notes = ZraApiConst.CON_NEED_PARAM
            + "<p>contractId(String)-合同标识", response = MyContractDetailDto.class)
    public AppResult getContractBillList(@FormParam("p") String p, @FormParam("sign") String sign) {
        /*if ("true".equals(PropUtils.getString("isUpgrade"))) {
            return AppResult.toFail(null, ErrorEnum.FORCE_UPGRADE_TXT);// update by cuigh6 强制升级到新版app
        }*/
        if (!isUpgrade()) {
            return AppResult.toFail(null, ErrorEnum.FORCE_UPGRADE_TXT);// update by cuigh6 强制升级到新版app
        }
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            MyContractDetailDto dto = jacksonReadValue(p, MyContractDetailDto.class);
            MyContractDetailDto result = payLogic.getContractBillList(dto.getContractId());
            return AppResult.toSuccess(result);
        } catch (Exception e) {
            LOGGER.error("[合同账单列表]出错！", e);
            return AppResult.toFail(e);
        }
    }

    /**
     * 获取支付方式 created by cuigh6 on 2016/12/27
     * @param p 加密
     * @param sign 加密
     * @return
     */
    @POST
    @Path("/payWay/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "获取支付方式", notes = ZraApiConst.CON_NEED_PARAM
            + "<p>billFid(String)-账单标识;<p>billNum(String)-账单号;<p>payAmount(Double)-支付金额;<p>uid-(String)用户标识", response = PayWayDto.class)
    public AppResult getPayWay(@FormParam("p") String p, @FormParam("sign") String sign) {
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            PaymentNumParamDto dto = jacksonReadValue(p, PaymentNumParamDto.class);
            PayWayDto result = payLogic.getPayWay(dto);
            return AppResult.toSuccess(result);
        } catch (Exception e) {
            LOGGER.error("[获取支付方式]出错！", e);
            return AppResult.toFail(e);
        }
    }
    
    
    /**
     * 获取支付方式(对接卡券系统) 
     * @author tianxf9
     * @param p 加密
     * @param sign 加密
     * @return
     */
    @POST
    @Path("/payWay/v2")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "获取支付方式（对接卡券系统）", notes = ZraApiConst.CON_NEED_PARAM
            + "<p>billFid(String)-账单标识;<p>billNum(String)-账单号;<p>payAmount(Double)-支付金额;<p>uid-(String)用户标识;<p>billType(Integer)-账单类型;<p>cardCouponCodes(List<String>)-优惠券或卡券codes", response = PayWayDto.class)
    public AppResult getNewPayWay(@FormParam("p") String p, @FormParam("sign") String sign) {
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            PaymentNumParamDto dto = jacksonReadValue(p, PaymentNumParamDto.class);
            if(!this.payLogic.validParams(dto)) {
            	throw new ResultException(ErrorEnum.MSG_PARAM_NULL);
            }
            PayWayDto result = payLogic.getNewPayWay(dto);
            return AppResult.toSuccess(result);
        } catch (Exception e) {
            LOGGER.error("[获取支付方式]出错！", e);
            return AppResult.toFail(e);
        }
    }

    /**
     * 支付 created by cuigh6 on 2016/12/28
     * @param p
     * @param sign
     * @return
     */
    @POST
    @Path("/pay/v1/")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "支付(选定支付方式时调用)", notes = ZraApiConst.CON_NEED_PARAM
            + "payChannel(String)-支付方式(wx_ios_pay/wx_ad_pay:\"42\");<br> paymentNum<String>-支付订单号;<br> payAmount(Double)-支付金额", response = String.class)
    public AppResult getPay(@FormParam("p") String p, @FormParam("sign") String sign) {
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            PayParamDto dto = jacksonReadValue(p, PayParamDto.class);
            String data =this.payLogic.payToPaymentPlatform(dto);
            return AppResult.toSuccess(new ObjectMapper().readValue(data,Map.class));
        } catch (Exception e) {
            LOGGER.error("[向支付平台支付]出错！", e);
            return AppResult.toFail(e);
        }
    }

    @POST
    @Path("/project/list/v2")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "新-项目列表", notes = ZraApiConst.CON_NEED_PARAM, response = NewProjectListDto.class)
    public AppResult getNewProjectList(@FormParam("p") String p, @FormParam("sign") String sign) {
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(new ArrayList(), ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            AppBaseDto dto = jacksonReadValue(p, AppBaseDto.class);
            List<NewProjectListDto> result = projectLogic.getNewProjectList(dto);
            return AppResult.toSuccess(result);
        } catch (Exception e) {
            LOGGER.error("[新-项目列表]出错！", e);
            return AppResult.toFailList(e);
        }
    }
    
    
    /**
     * 根据条件查询项目列表
     * @author tianxf9
     * @param p
     * @param sign
     * @return
     */
    @POST
    @Path("/queryProject/list/v2")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "app根据条件查询项目列表(0608App优化)", notes = ZraApiConst.CON_NEED_PARAM
            + "<p>city(String)-城市;<br/>minPrice(Double)-最低价格; <br/>maxPrice(Double)-最高价格;"
            + "<br/>checkInTime(String)-可入住时间-yyyy-MM-dd",response = NewProjectListDto.class)
    public AppResult getQueryProjectList(@FormParam("p") String p, @FormParam("sign") String sign) {
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(new ArrayList(), ErrorEnum.MSG_PARAM_NULL);
        }
        try {
        	SearchOfProjectParamDto dto = jacksonReadValue(p, SearchOfProjectParamDto.class);
            List<NewProjectListDto> result = projectLogic.queryProjectByCondition(dto);
            return AppResult.toSuccess(result);
        } catch (Exception e) {
            LOGGER.error("app根据条件查询项目列表", e);
            return AppResult.toFailList(e);
        }
    }
    
    @POST
    @Path("/project/detail/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "新-项目详情", notes = ZraApiConst.CON_NEED_PARAM + "<p>projectId(String)-项目fid;", response = NewProjectDetailDto.class)
    public AppResult getNewProjectDetail(@FormParam("p") String p, @FormParam("sign") String sign) {
        if (StrUtils.isNullOrBlank(p)) {
            return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
        }
        try {
            SearchReqDto dto = jacksonReadValue(p, SearchReqDto.class);
            NewProjectDetailDto result = projectLogic.getNewProjectDetail(dto);
            return AppResult.toSuccess(result);
        } catch (Exception e) {
            LOGGER.error("[新-项目详情]出错！", e);
            return AppResult.toFailList(e);
        }
    }

    /**
     * 是否强制升级到新版本
     * @return false or true
     * @author cuigh6
     */
    private boolean isUpgrade() {
        String result = zkSysService.getZkSysValue(ZKConfigEnum.ZK_CONFIG_ENUM_002.getType(), ZKConfigEnum.ZK_CONFIG_ENUM_002.getCode());
        LOGGER.info("ZK获取是否升级接口:" + result);
        if ("1".equals(result)) {
            return false;
        }
        return true;
    }

}
