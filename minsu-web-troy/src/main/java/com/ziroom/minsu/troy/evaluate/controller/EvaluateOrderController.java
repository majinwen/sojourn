/**
 * @FileName: EvaluateController.java
 * @Package com.ziroom.minsu.troy.evaluate.controller
 * @author yd
 * @created 2016年4月11日 上午10:25:52
 * <p>
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.troy.evaluate.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.AuthMenuEntity;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.LandlordEvaluateEntity;
import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseGuardRelEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.entity.order.OrderHouseSnapshotEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.constant.BaseDataConstant;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.evaluate.dto.EvaluateOrderRequest;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.evaluate.entity.EvaluateVo;
import com.ziroom.minsu.services.house.api.inner.HouseGuardService;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;
import com.ziroom.minsu.services.house.entity.HouseCityVo;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.entity.OrderHouseVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import com.ziroom.minsu.troy.auth.menu.EvaluateAuthUtils;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.valenum.base.RoleTypeEnum;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateRulesEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateUserEnum;
import com.ziroom.minsu.valenum.evaluate.IsReleaseEnum;
import com.ziroom.minsu.valenum.evaluate.OrderEvaFlagEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.order.OrderEvaStatusEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>评价系统管理业务入口</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("evaluate")
public class EvaluateOrderController {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(EvaluateOrderController.class);
    @Resource(name = "evaluate.evaluateOrderService")
    private EvaluateOrderService evaluateOrderService;

    @Resource(name = "order.orderCommonService")
    private OrderCommonService orderCommonService;

    @Resource(name = "customer.customerMsgManagerService")
    private CustomerMsgManagerService customerMsgManagerService;

    @Resource(name = "house.troyHouseMgtService")
    private TroyHouseMgtService troyHouseMgtService;

    @Resource(name = "basedata.confCityService")
    private ConfCityService confCityService;

    @Resource(name = "house.houseGuardService")
    private HouseGuardService houseGuardService;

    @Resource(name = "basedata.cityTemplateService")
    private CityTemplateService cityTemplateService;

    @Resource(name = "search.searchServiceApi")
    private SearchService searchService;

    /**
     *
     * 按条件分页查询评价
     * 说明：这里分两类的分页  1.房东对房客的评价  2.房客对房源的评价
     *
     * 权限说明：
     * 1. 普通权限 正常走
     * 2. 区域权限  查询角色区域下房源fid,查询评价
     * 3. 数据角色 根据区域管家关系查询当前当前管家管的房源fid集合，查询评价
     * 4. 数据区域权限  2和3取交集
     *
     * @author yd
     * @created 2016年4月11日 上午10:31:05
     *
     * @param request
     */
    @RequestMapping("queryEvaluateByPage")
    public @ResponseBody
    PageResult queryEvaluateByPage(HttpServletRequest request, @ModelAttribute("evaluateRequest") EvaluateRequest evaluateRequest) {
        try {

            Object authMenu = request.getAttribute("authMenu");
            evaluateRequest.setRoleType(RoleTypeEnum.ADMIN.getCode());
            //查询房东对房客的评价
            if (!Check.NuNObj(evaluateRequest.getEvaUserType()) && evaluateRequest.getEvaUserType().intValue() == 0) {
                evaluateRequest.setEvaUserType(null);
            }
            //维护管家查询条件
            if (!Check.NuNStr(evaluateRequest.getEmpGuardName())) {
                List<String> fid = new ArrayList<>();
                HouseGuardRelEntity houseGuard = new HouseGuardRelEntity();
                houseGuard.setEmpGuardName(evaluateRequest.getEmpGuardName());
                String resultJson = houseGuardService.findHouseGuardRelByCondition(JsonEntityTransform.Object2Json(houseGuard));
                List<HouseGuardRelEntity> houseGuardRels = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseGuardRelEntity.class);
                if (Check.NuNCollection(houseGuardRels)) {
                    return new PageResult();
                }
                for (HouseGuardRelEntity houseGuardRel : houseGuardRels) {
                    fid.add(houseGuardRel.getHouseFid());
                }
                evaluateRequest.setListFid(fid);
            }


            //houseSn查询条件
            if (!Check.NuNStr(evaluateRequest.getHouseSn())) {
                String resultJson = troyHouseMgtService.findHouseBaseByHouseSn(evaluateRequest.getHouseSn());
                HouseBaseMsgEntity houseBase = SOAResParseUtil.getValueFromDataByKey(resultJson, "houseBase", HouseBaseMsgEntity.class);
                if (Check.NuNObj(houseBase)) {
                    return new PageResult();
                }
                evaluateRequest.getListFid().add(houseBase.getFid());
            }

            //评价菜单添加权限
            if (!addAuthData(authMenu, evaluateRequest)) {
                return null;
            }
            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.queryAllEvaluateByPage(JsonEntityTransform.Object2Json(evaluateRequest)));
            List<EvaluateVo> lsitEvaluateVo = resultDto.parseData("lsitEvaluateVo", new TypeReference<List<EvaluateVo>>() {
            });
            //返回结果
            PageResult pageResult = new PageResult();
            if (!Check.NuNObj(resultDto.getData().get("total"))) {
                pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
            }
            if (!Check.NuNCollection(lsitEvaluateVo)) {
                Set<String> houseFids = new HashSet<>();
                for (EvaluateVo evaluateVo : lsitEvaluateVo) {
                    houseFids.add(evaluateVo.getHouseFid());

                    //填充管家信息
                    String houseGuardRelJson = houseGuardService.findHouseGuardRelByHouseBaseFid(evaluateVo.getHouseFid());
                    HouseGuardRelEntity houseGuardRelEntity = null;
                    houseGuardRelEntity = SOAResParseUtil.getValueFromDataByKey(houseGuardRelJson, "houseGuardRel", HouseGuardRelEntity.class);

                    if (!Check.NuNObj(houseGuardRelEntity)) {
                        evaluateVo.setEmpGuardName(houseGuardRelEntity.getEmpGuardName());
                    }

                    resultDto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.getCutomerVo(evaluateVo.getEvaUserUid()));
                    if (resultDto.getCode() == DataTransferObject.SUCCESS) {
                        CustomerVo customerVo = resultDto.parseData("customerVo", new TypeReference<CustomerVo>() {
                        });
                        if (!Check.NuNObj(customerVo))
                            evaluateVo.setEvaUserName(customerVo.getRealName());
                    }
                    resultDto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.getCutomerVo(evaluateVo.getRatedUserUid()));
                    if (resultDto.getCode() == DataTransferObject.SUCCESS) {
                        CustomerVo customerVo = resultDto.parseData("customerVo", new TypeReference<CustomerVo>() {
                        });
                        if (!Check.NuNObj(customerVo))
                            evaluateVo.setRatedUserName(customerVo.getRealName());
                    }
                }

                //填充cityCode、houseSn
                String houseJson = troyHouseMgtService.getHouseCityVoByFids(JsonEntityTransform.Object2Json(Arrays.asList(houseFids)));
                DataTransferObject houseDto = JsonEntityTransform.json2DataTransferObject(houseJson);
                List<HouseCityVo> houseCityVoList = houseDto.parseData("houseCityVoList", new TypeReference<List<HouseCityVo>>() {
                });
                for (EvaluateVo evaluateVo : lsitEvaluateVo) {
                    for (HouseCityVo houseCityVo : houseCityVoList) {
                        if (evaluateVo.getHouseFid().equals(houseCityVo.getFid())) {
                            evaluateVo.setHouseSn(houseCityVo.getHouseSn());
                            evaluateVo.setCityCode(houseCityVo.getCityCode());
                        }
                    }
                }

                //获取城市信息
                String cityJson = confCityService.getOpenCityMap();
                DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(cityJson);
                Map<String, String> cityMap = cityDto.parseData("map", new TypeReference<Map<String, String>>() {
                });
                for (EvaluateVo evaluateVo : lsitEvaluateVo) {
                    evaluateVo.setCityName(cityMap.get(evaluateVo.getCityCode()));
                }

            }
            pageResult.setRows(lsitEvaluateVo);

            return pageResult;
        } catch (Exception e) {
            LogUtil.error(logger, "e:{}", e);
            return null;
        }
    }


    /**
     *
     * 评价权限配置
     *
     * @author yd
     * @created 2016年10月31日 下午3:51:31
     *
     * @param authMenu
     * @param evaluateRequest
     */
    private boolean addAuthData(Object authMenu, EvaluateRequest evaluateRequest) {

        boolean addFlag = false;
        //权限过滤
        if (!Check.NuNObj(authMenu)) {
            AuthMenuEntity authMenuEntity = (AuthMenuEntity) authMenu;
            if (!Check.NuNObj(authMenuEntity.getRoleType()) && authMenuEntity.getRoleType().intValue() > 0) {
                evaluateRequest.setRoleType(authMenuEntity.getRoleType());
                DataTransferObject authDto = EvaluateAuthUtils.getAuthHouseFids(authMenuEntity, troyHouseMgtService);
                if (authDto.getCode() == DataTransferObject.ERROR) {
                    LogUtil.error(logger, "当前菜单类型：{},权限异常error={}", "查看评价", authDto.getMsg());
                    return addFlag;
                }
                try {
                    List<String> fids = SOAResParseUtil.getListValueFromDataByKey(authDto.toJsonString(), "houseFids", String.class);
                    if (Check.NuNCollection(fids)) {
                        LogUtil.error(logger, "当前菜单类型：{},无权限，fids={}", "查看评价", fids);
                        return addFlag;
                    }
                    evaluateRequest.setListFid(fids);

                } catch (SOAParseException e) {
                    LogUtil.error(logger, "评价权限查询房源集合异常e={}", e);
                    return addFlag;
                }


            }
        }
        return true;
    }

    /**
     *
     * 到评价管理页面
     *
     * @author yd
     * @created 2016年4月11日 上午11:26:51
     *
     * @param request
     */
    @RequestMapping("queryListEvaluate")
    public void queryListEvaluate(HttpServletRequest request, String editFlag) {
        request.setAttribute("editFlag", editFlag);
    }

    /**
     *
     * 审核评论，根据fid修改状态
     *
     * @author yd
     * @created 2016年4月11日 下午5:50:01
     *
     * @param request
     */
    @RequestMapping("examineEvaluateByFid")
    public @ResponseBody
    String examineEvaluateByFid(HttpServletRequest request) {


        EvaluateOrderRequest evaluateOrder = new EvaluateOrderRequest();

        String fid = request.getParameter("fid");
        String evaStatu = request.getParameter("evaStatu");
        String orderSn = request.getParameter("orderSn");
        LogUtil.info(logger, "根据fid={}审核评价,状态修改为evaStatu={}", fid, evaStatu);
        String result = "0";
        if (!Check.NuNStr(fid) && !Check.NuNStr(evaStatu)) {
            evaluateOrder.setFid(fid);
            evaluateOrder.setEvaStatu(Integer.parseInt(evaStatu));
            evaluateOrder.setCreateUid(UserUtil.getCurrentUserFid());

            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(orderCommonService.getOrderByOrderSn(orderSn));
            OrderEntity order = null;
            if (resultDto.getCode() == 0) {
                order = resultDto.parseData("order", new TypeReference<OrderEntity>() {
                });
            }
            resultDto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.updateEvaluateOrderByFid(JsonEntityTransform.Object2Json(evaluateOrder), JsonEntityTransform.Object2Json(order)));

            if (resultDto.getCode() == DataTransferObject.SUCCESS) {
                //如果人工下架成功 重新统计;或者上架
                if (Integer.parseInt(evaStatu) == EvaluateStatuEnum.ONLINE.getEvaStatuCode() || Integer.parseInt(evaStatu) == EvaluateStatuEnum.PERSON_OFFLINE.getEvaStatuCode()) {
                    statsEva(fid);
                }
            }

            result = resultDto.getData().get("result").toString();
        }
        return JsonEntityTransform.Object2Json(result);
    }

    /**
     * 重新统计评价分数
     * @author jixd
     * @created 2017年03月15日 10:09:49
     * @param
     * @return
     */
    private void statsEva(final String evaFid) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                LogUtil.info(logger, "人工下架后重新统计开始");
                EvaluateRequest request = new EvaluateRequest();
                List<String> paramList = new ArrayList<>(1);
                paramList.add(evaFid);
                request.setListFid(paramList);
                DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(evaluateOrderService.listAllEvaOrderByCondition(JsonEntityTransform.Object2Json(request)));
                if (resultDto.getCode() == DataTransferObject.SUCCESS) {
                    List<EvaluateOrderEntity> currentList = resultDto.parseData("list", new TypeReference<List<EvaluateOrderEntity>>() {
                    });
                    if (Check.NuNCollection(currentList)) {
                        return;
                    }
                    //当前操作的记录
                    EvaluateOrderEntity evaluateOrderEntity = currentList.get(0);
                    EvaluateRequest evaluateRequest = new EvaluateRequest();
                    evaluateRequest.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
                    if (evaluateOrderEntity.getEvaUserType().intValue() == EvaluateUserEnum.TEN.getCode()) {
                        //评价人是房客
                        evaluateRequest.setHouseFid(evaluateOrderEntity.getHouseFid());
                        evaluateRequest.setRoomFid(evaluateOrderEntity.getRoomFid());
                        evaluateRequest.setRentWay(evaluateOrderEntity.getRentWay());
                        evaluateRequest.setEvaUserType(UserTypeEnum.TENANT.getUserType());
                        DataTransferObject listDto = JsonEntityTransform.json2DataTransferObject(evaluateOrderService.listAllEvaOrderByCondition(JsonEntityTransform.Object2Json(evaluateRequest)));
                        if (listDto.getCode() == DataTransferObject.SUCCESS) {
                            List<EvaluateOrderEntity> evalist = listDto.parseData("list", new TypeReference<List<EvaluateOrderEntity>>() {
                            });
                            if (Check.NuNCollection(evalist)) {
                                LogUtil.info(logger, "删除房源统计数据evaluateOrderEntity={}", JsonEntityTransform.Object2Json(evaluateOrderEntity));
                                //说明评价被强制下架，并且没有其他数据，需要清空，对应房源统计评价表中的数据
                                evaluateOrderService.delStatsData(JsonEntityTransform.Object2Json(evaluateOrderEntity));
                            } else {
                                LogUtil.info(logger, "更新房源统计数据evaluateOrderEntity={}", JsonEntityTransform.Object2Json(evaluateOrderEntity));
                                List<EvaluateOrderEntity> showStatList = canShowAndStat(evalist);
                                String s = evaluateOrderService.updateShowAndStatEva(JsonEntityTransform.Object2Json(showStatList),
                                        JsonEntityTransform.Object2Json(evaluateOrderEntity), String.valueOf(UserTypeEnum.TENANT.getUserType()));
                                LogUtil.info(logger, "同步房客评价统计和显示result={}", s);
                            }
                        }
                    }
                    if (evaluateOrderEntity.getEvaUserType().intValue() == EvaluateUserEnum.LAN.getCode()) {
                        evaluateRequest.setEvaUserType(UserTypeEnum.LANDLORD.getUserType());
                        evaluateRequest.setRatedUserUid(evaluateOrderEntity.getRatedUserUid());
                        DataTransferObject listDto = JsonEntityTransform.json2DataTransferObject(evaluateOrderService.listAllEvaOrderByCondition(JsonEntityTransform.Object2Json(evaluateRequest)));
                        if (listDto.getCode() == DataTransferObject.SUCCESS) {
                            List<EvaluateOrderEntity> evalist = listDto.parseData("list", new TypeReference<List<EvaluateOrderEntity>>() {
                            });
                            if (Check.NuNCollection(evalist)) {
                                LogUtil.info(logger, "删除房客统计数据evaluateOrderEntity={}", JsonEntityTransform.Object2Json(evaluateOrderEntity));
                                //清除房东对房客的评价统计
                                evaluateOrderService.delStatsData(JsonEntityTransform.Object2Json(evaluateOrderEntity));
                            } else {
                                LogUtil.info(logger, "更新房客统计数据evaluateOrderEntity={}", JsonEntityTransform.Object2Json(evaluateOrderEntity));
                                List<EvaluateOrderEntity> showStatList = canShowAndStat(evalist);
                                String s = evaluateOrderService.updateShowAndStatEva(JsonEntityTransform.Object2Json(showStatList),
                                        JsonEntityTransform.Object2Json(evaluateOrderEntity), String.valueOf(UserTypeEnum.LANDLORD.getUserType()));
                                LogUtil.info(logger, "同步房东评价统计和显示result={}", s);
                            }
                        }
                    }
                }
            }
        };
        new Thread(r).start();
    }

    /**
     * 判断是否可以展示和统计
     * @author jixd
     * @created 2017年03月15日 11:07:20
     * @param
     * @return
     */
    private List<EvaluateOrderEntity> canShowAndStat(List<EvaluateOrderEntity> evalist) {
        int limitDay = getEvalShowLimitDay();
        List<EvaluateOrderEntity> getList = new ArrayList<>();
        for (EvaluateOrderEntity evaOrder : evalist) {
            String orderSn = evaOrder.getOrderSn();
            DataTransferObject orderDto = JsonEntityTransform.json2DataTransferObject(orderCommonService.getOrderByOrderSn(orderSn));
            if (orderDto.getCode() == DataTransferObject.ERROR) {
                //更新显示状态
                getList.add(evaOrder);
                continue;
            }
            OrderEntity order = orderDto.parseData("order", new TypeReference<OrderEntity>() {
            });
            if (Check.NuNObj(order)) {
                //更新显示状态
                getList.add(evaOrder);
                continue;
            }
            Integer evaUserType = evaOrder.getEvaUserType();
            Integer evaStatus = order.getEvaStatus();
            Date realEndTime = order.getRealEndTime();
            //评价人是房东
            if (evaStatus == OrderEvaStatusEnum.ALL_EVA.getCode()) {
                //更新显示状态
                getList.add(evaOrder);
            }
            if ((evaUserType == UserTypeEnum.LANDLORD.getUserType() && evaStatus == OrderEvaStatusEnum.LANLORD_EVA.getCode())
                    || (evaUserType == UserTypeEnum.TENANT.getUserType() && evaStatus == OrderEvaStatusEnum.UESR_HVA_EVA.getCode())) {
                if (!Check.NuNObj(realEndTime) && isEvaExpire(realEndTime, limitDay)) {
                    //更新
                    getList.add(evaOrder);
                }
            }
        }
        return getList;
    }

    /**
     * 获取评价展示时间
     * @author jixd
     * @created 2017年03月15日 11:15:54
     * @param
     * @return
     */
    public int getEvalShowLimitDay() {
        String timeStrJson = cityTemplateService.getTextValue(null, String.valueOf(EvaluateRulesEnum.EvaluateRulesEnum005.getValue()));
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(timeStrJson);
        if (resultDto.getCode() != DataTransferObject.SUCCESS) {
            LogUtil.error(logger, "获取订单结束后展示评价天数,timeStrJson:{}", timeStrJson);
            throw new BusinessException("获取订单结束后展示评价天数失败！");
        }
        int limitDay = ValueUtil.getintValue(resultDto.getData().get("textValue"));
        return limitDay;
    }

    /**
     * 评价过期时间
     * @author jixd
     * @created 2017年03月15日 11:14:19
     * @param
     * @return
     */
    public boolean isEvaExpire(Date realEndTime, int limitDay) {
        if (Check.NuNObj(realEndTime)) {
            return false;
        }
        long between = ((new Date()).getTime() - realEndTime.getTime()) / 1000;
        if (between > (limitDay * 24 * 3600)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @author yd
     * @created 2016年4月12日 下午10:54:04
     *
     * @param request
     */
    @RequestMapping("goToEvaluateInfo")
    public @ResponseBody
    String goToEvaluateInfo(HttpServletRequest request) {

        String orderSn = request.getParameter("orderSn");
        EvaluateRequest evaluateRequest = new EvaluateRequest();
        evaluateRequest.setOrderSn(orderSn);
        evaluateRequest.setEvaUserType(UserTypeEnum.All.getUserType());
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.queryEvaluateByOrderSn(JsonEntityTransform.Object2Json(evaluateRequest)));
        Object object = resultDto.getData().get("evaluateMap");
        return JsonEntityTransform.Object2Json(object);
    }


    /**
     *
     * 保存房客的评价
     *
     * @author yd
     * @created 2017年5月26日 下午8:22:10
     *
     * @param request
     * @return
     */
    @RequestMapping("saveTenantEva")
    public @ResponseBody
    DataTransferObject saveTenantEva(HttpServletRequest request, TenantEvaApiRequest tenantEvaApiRequest) {

        DataTransferObject dto = new DataTransferObject();

        try {
            dto = checkEvaluateOrder(tenantEvaApiRequest, dto);

            if (dto.getCode() != 0) {
                return dto;
            }

            //查询当前订单
            dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.getOrderInfoByOrderSn(tenantEvaApiRequest.getOrderSn()));

            if (dto.getCode() == 0) {
                OrderInfoVo order = SOAResParseUtil.getValueFromDataByKey(dto.toJsonString(), "orderInfoVo", OrderInfoVo.class);

                //校验订单状态
                int orderStatu = order.getOrderStatus().intValue();
                if (orderStatu < OrderStatusEnum.CHECKED_IN.getOrderStatus()) {
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("订单状态非法，不能评价");
                    return dto;
                }
                //查询订单快照
                dto = JsonEntityTransform.json2DataTransferObject(orderCommonService.findHouseSnapshotByOrderSn(tenantEvaApiRequest.getOrderSn()));
                //订单快照无
                if (dto.getCode() == 1) {
                    return dto;
                }
                OrderHouseSnapshotEntity orderHouseSnapshot = dto.parseData("orderHouseSnapshot", new TypeReference<OrderHouseSnapshotEntity>() {
                });
                //评论内容过滤
                dto = JsonEntityTransform.json2DataTransferObject(this.searchService.getIkList(tenantEvaApiRequest.getContent()));

                List<String> listStrings = dto.parseData("ikList", new TypeReference<List<String>>() {
                });

                if (!Check.NuNCollection(listStrings)) {
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("评论包含敏感词，不能包含" + listStrings);
                    return dto;
                }

                //校验订单房客是否已评价
                int evaStatus = order.getEvaStatus().intValue();
                if (evaStatus == OrderEvaStatusEnum.WAITINT_EVA.getCode() || evaStatus == OrderEvaStatusEnum.LANLORD_EVA.getCode()) {
                    //保存房客信息
                    EvaluateOrderEntity evaluateOrder = saveTenantEvaluate(order, orderHouseSnapshot, dto, tenantEvaApiRequest, order);
                    if (dto.getCode() == 0) {
                        if (!Check.NuNObj(order)) {
                            //更新订单状态
                            OrderEntity upOrder = new OrderEntity();
                            upOrder.setOrderSn(order.getOrderSn());
                            upOrder.setOldStatus(order.getOrderStatus());
                            upOrder.setOrderStatus(order.getOrderStatus());
                            if (evaStatus == OrderEvaStatusEnum.WAITINT_EVA.getCode()) {
                                upOrder.setEvaStatus(OrderEvaStatusEnum.UESR_HVA_EVA.getCode());
                            } else {
                                upOrder.setEvaStatus(OrderEvaStatusEnum.ALL_EVA.getCode());
                            }
                            //修改评价状态
                            dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.updateOrderBaseByOrderSn(JsonEntityTransform.Object2Json(upOrder)));
                            if (dto.getCode() == 0) {
                                evaluateOrder.setOrderEvaFlag(OrderEvaFlagEnum.ORDE_HAVE_EVA.getCode());
                                evaluateOrder.setLastModifyDate(new Date());
                                dto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.updateEvaluateOrderByCondition(JsonEntityTransform.Object2Json(evaluateOrder)));

                                return dto;
                            }
                        }
                    }
                } else {
                    return dto;
                }
            }
        } catch (Exception e) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("添加评价异常,请重试");
        }
        return dto;
    }

    /**
     * 保存房客评价信息
     *
     * @param order
     * @param orderHouseSnapshot
     * @param dto
     * @author yd
     * @created 2016年5月1日 下午5:31:15
     */
    private EvaluateOrderEntity saveTenantEvaluate(OrderHouseVo order, OrderHouseSnapshotEntity orderHouseSnapshot, DataTransferObject dto, TenantEvaApiRequest tenantEvaApiRequest, OrderEntity orderEntity) {

        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(cityTemplateService.getTextValue(null, String.valueOf(EvaluateRulesEnum.EvaluateRulesEnum001.getValue())));
        //房客未评价
        EvaluateOrderEntity evaluateOrder = new EvaluateOrderEntity();
        evaluateOrder.setCreateTime(new Date());
        evaluateOrder.setEvaStatu(EvaluateStatuEnum.AUDIT.getEvaStatuCode());
        evaluateOrder.setEvaUserType(UserTypeEnum.TENANT.getUserType());
        evaluateOrder.setEvaUserUid(order.getUserUid());
        evaluateOrder.setFid(UUIDGenerator.hexUUID());
        evaluateOrder.setHouseFid(orderHouseSnapshot.getHouseFid());
        evaluateOrder.setLastModifyDate(new Date());
        evaluateOrder.setOrderEvaFlag(OrderEvaFlagEnum.ORDER_NOT_HAVE_EVA.getCode());
        evaluateOrder.setOrderSn(order.getOrderSn());
        evaluateOrder.setRatedUserUid(order.getLandlordUid());
        evaluateOrder.setRentWay(order.getRentWay());
        evaluateOrder.setRoomFid(orderHouseSnapshot.getRoomFid());
        evaluateOrder.setBedFid(orderHouseSnapshot.getBedFid());


        if (resultDto.getCode() == DataTransferObject.SUCCESS
                && !Check.NuNObj(resultDto.getData().get("textValue"))
                && Integer.valueOf(resultDto.getData().get("textValue").toString()).intValue() == IsReleaseEnum.Release.getCode()) {
            evaluateOrder.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
        }

        TenantEvaluateEntity tenantEvaluate = new TenantEvaluateEntity();
        tenantEvaluate.setContent(tenantEvaApiRequest.getContent());
        tenantEvaluate.setCostPerformance(tenantEvaApiRequest.getCostPerformance());
        tenantEvaluate.setCreateTime(new Date());
        tenantEvaluate.setDescriptionMatch(tenantEvaApiRequest.getDescriptionMatch());
        tenantEvaluate.setEvaOrderFid(evaluateOrder.getFid());
        tenantEvaluate.setFid(UUIDGenerator.hexUUID());
        tenantEvaluate.setHouseClean(tenantEvaApiRequest.getHouseClean());
        tenantEvaluate.setIsDel(IsDelEnum.NOT_DEL.getCode());
        tenantEvaluate.setLastModifyDate(new Date());
        tenantEvaluate.setSafetyDegree(tenantEvaApiRequest.getSafetyDegree());
        tenantEvaluate.setTrafficPosition(tenantEvaApiRequest.getTrafficPosition());

        dto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.saveTenantEvaluate(JsonEntityTransform.Object2Json(tenantEvaluate), JsonEntityTransform.Object2Json(evaluateOrder), JsonEntityTransform.Object2Json(orderEntity)));

        return evaluateOrder;
    }

    /**
     * 校验评价订单关联实体
     *
     * @param evaluateOrder
     * @author yd
     * @created 2016年5月1日 上午11:16:37
     */
    private DataTransferObject checkEvaluateOrder(TenantEvaApiRequest evaluateOrder, DataTransferObject dto) {

        if (dto == null) dto = new DataTransferObject();

        if (Check.NuNObj(evaluateOrder) || Check.NuNStr(evaluateOrder.getOrderSn())
                || Check.NuNStr(evaluateOrder.getContent())
                || Check.NuNObj(evaluateOrder.getHouseClean())
                || Check.NuNObj(evaluateOrder.getDescriptionMatch())
                || Check.NuNObj(evaluateOrder.getSafetyDegree())
                || Check.NuNObj(evaluateOrder.getTrafficPosition())
                || Check.NuNObj(evaluateOrder.getCostPerformance())) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("评价内容不能为空");
            return dto;
        }
        if (evaluateOrder.getHouseClean() == 0 || evaluateOrder.getDescriptionMatch() == 0
                || evaluateOrder.getSafetyDegree() == 0 || evaluateOrder.getTrafficPosition() == 0 || evaluateOrder.getCostPerformance() == 0) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("请选择星级");
            return dto;
        }

        dto = limitContent(evaluateOrder.getContent().trim().length());
        return dto;
    }


    /**
     * 校验评价订单关联实体-房东端
     * @author wangwt
     * @created 2017年08月07日 17:23:58
     * @param
     * @return
     */
    private DataTransferObject checkEvaluateOrderLandlord(TenantEvaApiRequest evaluateOrder, DataTransferObject dto) {

        if (dto == null) dto = new DataTransferObject();

        //只校验房东满意度和房东评价
        if (Check.NuNObj(evaluateOrder) || Check.NuNStr(evaluateOrder.getOrderSn())
                || Check.NuNStr(evaluateOrder.getEvaOrderIdLandlord())
                || Check.NuNObj(evaluateOrder.getLandlordSatisfied())) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("房东评价内容不能为空");
            return dto;
        }
        if (evaluateOrder.getLandlordSatisfied()== 0 ) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("请选择星级");
            return dto;
        }

        dto = limitContent(evaluateOrder.getLanContent().trim().length());
        return dto;
    }

    /**
     *
     * 评价限制字数
     *
     * @author yd
     * @created 2017年5月26日 下午8:26:33
     *
     * @param len
     * @return
     */
    private DataTransferObject limitContent(int len) {
        DataTransferObject dto = new DataTransferObject();
        DataTransferObject limitDto = JsonEntityTransform.json2DataTransferObject(cityTemplateService.getTextValue(null, EvaluateRulesEnum.EvaluateRulesEnum002.getValue()));
        if (limitDto.getCode() == DataTransferObject.SUCCESS) {
            String limitStr = limitDto.parseData("textValue", new TypeReference<String>() {
            });
            int index = limitStr.indexOf(BaseDataConstant.EVAL_SPLIT);
            int min = ValueUtil.getintValue(limitStr.substring(0, index));
            int max = ValueUtil.getintValue(ValueUtil.getintValue(limitStr.substring(index + 1)));
            if (len < min) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("内容太少");
            }
            if (len > max) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("内容太多");
            }
        }
        return dto;
    }

    /**
     *
     * 校验评价订单关联实体
     *
     *
     * @author yd
     * @created 2016年5月1日 上午11:16:37
     *
     * @param lanlordEvaRequest
     */
    private DataTransferObject checkEvaluateOrder(LanlordEvaRequest lanlordEvaRequest, DataTransferObject dto) {

        if (dto == null) dto = new DataTransferObject();

        if (Check.NuNObj(lanlordEvaRequest) || Check.NuNStr(lanlordEvaRequest.getOrderSn())
                || Check.NuNStr(lanlordEvaRequest.getContent())
                || Check.NuNObj(lanlordEvaRequest.getLandlordSatisfied())) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("评价内容不能为空");
            return dto;
        }

        if (lanlordEvaRequest.getContent().length() > 400) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("评价内容字数过多");
        }

        return dto;
    }

    /**
     *
     * 修改房客的评价，评分
     *
     * @author yd
     * @created 2017年5月26日 下午8:22:10
     *
     * @param request
     * @return
     */
    @RequestMapping("updateTenantEva")
    public @ResponseBody
    DataTransferObject updateTenantEva(HttpServletRequest request, TenantEvaApiRequest tenantEvaApiRequest) {
        DataTransferObject dto = new DataTransferObject();
        try {
            //校验房客评价是不为空，则更新房客相关
            if(!Check.NuNStr(tenantEvaApiRequest.getEvaOrderFid())){
                dto = checkEvaluateOrder(tenantEvaApiRequest, dto);
                if (dto.getCode() != 0) {
                    return dto;
                }
                //评论内容过滤
                dto = JsonEntityTransform.json2DataTransferObject(this.searchService.getIkList(tenantEvaApiRequest.getContent()));

                List<String> listStrings = dto.parseData("ikList", new TypeReference<List<String>>() {
                });

                if (!Check.NuNCollection(listStrings)) {
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("房客评论包含敏感词，不能包含" + listStrings);
                    return dto;
                }
                //更新房客对房源的评价，评分 开始
                TenantEvaluateEntity tenantEvaluateEntity = new TenantEvaluateEntity();
                tenantEvaluateEntity.setEvaOrderFid(tenantEvaApiRequest.getEvaOrderFid());
                tenantEvaluateEntity.setContent(tenantEvaApiRequest.getContent());
                tenantEvaluateEntity.setCostPerformance(tenantEvaApiRequest.getCostPerformance());
                tenantEvaluateEntity.setDescriptionMatch(tenantEvaApiRequest.getDescriptionMatch());
                tenantEvaluateEntity.setHouseClean(tenantEvaApiRequest.getHouseClean());
                tenantEvaluateEntity.setSafetyDegree(tenantEvaApiRequest.getSafetyDegree());
                tenantEvaluateEntity.setTrafficPosition(tenantEvaApiRequest.getTrafficPosition());
                tenantEvaluateEntity.setLastModifyDate(new Date());
                String resultJson = evaluateOrderService.updateTenantEvaluate(JsonEntityTransform.Object2Json(tenantEvaluateEntity));
                LogUtil.info(logger, "更新房客评价返回：{}", resultJson);
                if (Check.NuNStr(resultJson)) {
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("修改房客评价异常");
                    return dto;
                }

                dto = JsonEntityTransform.json2DataTransferObject(resultJson);
                if (dto.getCode() != DataTransferObject.SUCCESS) {
                    return dto;
                }
                Integer updateCount = dto.parseData("result", new TypeReference<Integer>() {});
                if (updateCount == 0) {
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("更新房客评价，数量为0 ");
                    return dto;
                }
                //更新房客对房源的评价，评分 结束

                //统计同一房源下所有的房客评价（有效评价）,并重新计算分值 开始
                EvaluateOrderEntity evaluateOrderEntity = new EvaluateOrderEntity();
                evaluateOrderEntity.setRatedUserUid(tenantEvaApiRequest.getLandlordUid());
                evaluateOrderEntity.setHouseFid(tenantEvaApiRequest.getHouseFid());
                evaluateOrderEntity.setRoomFid(tenantEvaApiRequest.getRoomFid());
                evaluateOrderEntity.setRentWay(tenantEvaApiRequest.getRentWay());
                evaluateOrderEntity.setFid(tenantEvaApiRequest.getEvaOrderFid());
                evaluateOrderEntity.setOrderSn(tenantEvaApiRequest.getOrderSn());
                evaluateOrderEntity.setEvaStatu(tenantEvaApiRequest.getEvaStatu());
                evaluateOrderEntity.setEvaUserType(tenantEvaApiRequest.getEvaUserType());

                EvaluateRequest evaluateRequest = new EvaluateRequest();
                evaluateRequest.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
                evaluateRequest.setHouseFid(tenantEvaApiRequest.getHouseFid());
                evaluateRequest.setRoomFid(tenantEvaApiRequest.getRoomFid());
                evaluateRequest.setRentWay(tenantEvaApiRequest.getRentWay());
                evaluateRequest.setEvaUserType(UserTypeEnum.TENANT.getUserType());

                LogUtil.info(logger, "更新房源/房间 评价统计参数：{}", JsonEntityTransform.Object2Json(evaluateRequest));
                String updateResult = evaluateOrderService.listAllEvaOrderByCondition(JsonEntityTransform.Object2Json(evaluateRequest));
                LogUtil.info(logger, "更新房源/房间 评价统计返回：{}", updateResult);
                DataTransferObject listDto = JsonEntityTransform.json2DataTransferObject(updateResult);
                if (listDto.getCode() == DataTransferObject.SUCCESS) {
                    List<EvaluateOrderEntity> evalist = listDto.parseData("list", new TypeReference<List<EvaluateOrderEntity>>() {
                    });
                    if (Check.NuNCollection(evalist)) {
                        LogUtil.info(logger, "删除房源统计数据 参数evaluateOrderEntity={}", JsonEntityTransform.Object2Json(evaluateOrderEntity));
                        //说明评价被强制下架，并且没有其他数据，需要清空，对应房源统计评价表中的数据
                        String result = evaluateOrderService.delStatsData(JsonEntityTransform.Object2Json(evaluateOrderEntity));
                        LogUtil.info(logger, "删除房源统计数据 返回 :{}", result);
                        if (Check.NuNStr(result)) {
                            dto = JsonEntityTransform.json2DataTransferObject(result);
                            if (dto.getCode() != DataTransferObject.SUCCESS) {
                                return dto;
                            }
                        }
                    } else {
                        LogUtil.info(logger, "更新房源统计数据 参数evaluateOrderEntity={}", JsonEntityTransform.Object2Json(evaluateOrderEntity));
                        List<EvaluateOrderEntity> showStatList = canShowAndStat(evalist);
                        int count = 0;
                        String s = evaluateOrderService.updateEvaluateAndStatsHouseEva(JsonEntityTransform.Object2Json(showStatList),
                                JsonEntityTransform.Object2Json(evaluateOrderEntity), count);
                        LogUtil.info(logger, "更新房源统计数据 返回={}", s);
                        if (!Check.NuNStr(s)) {
                            dto = JsonEntityTransform.json2DataTransferObject(s);
                            if (dto.getCode() != DataTransferObject.SUCCESS) {
                                return dto;
                            }
                        }
                    }
                } else {
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("更新房源/房间 评价统计失败");
                    return dto;
                }
                //统计同一房源下所有的房客评价（有效评价）,并重新计算分值 结束
            }


            //校验房东评价不为空，则更新房东相关
            if(!Check.NuNStr(tenantEvaApiRequest.getEvaOrderIdLandlord())){
                dto = checkEvaluateOrderLandlord(tenantEvaApiRequest, dto);
                if (dto.getCode() != 0) {
                    return dto;
                }
                //评论内容过滤
                dto = JsonEntityTransform.json2DataTransferObject(this.searchService.getIkList(tenantEvaApiRequest.getLanContent()));

                List<String> listStrings = dto.parseData("ikList", new TypeReference<List<String>>() {
                });

                if (!Check.NuNCollection(listStrings)) {
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("房东评论包含敏感词，不能包含" + listStrings);
                    return dto;
                }
                //更新房东对房客的评价，评分 开始
                LandlordEvaluateEntity landlordEvaluateEntity = new LandlordEvaluateEntity();
                landlordEvaluateEntity.setEvaOrderFid(tenantEvaApiRequest.getEvaOrderIdLandlord());
                landlordEvaluateEntity.setContent(tenantEvaApiRequest.getLanContent());
                landlordEvaluateEntity.setLandlordSatisfied(tenantEvaApiRequest.getLandlordSatisfied());
                landlordEvaluateEntity.setLastModifyDate(new Date());
                landlordEvaluateEntity.setIsDel(0);
                LogUtil.info(logger, "更新房东评价参数：{}", JsonEntityTransform.Object2Json(landlordEvaluateEntity));
                String resultJson = evaluateOrderService.updateLandlordEvaluate(JsonEntityTransform.Object2Json(landlordEvaluateEntity));
                LogUtil.info(logger, "更新房东评价返回：{}", resultJson);
                if (Check.NuNStr(resultJson)) {
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("修改房东评价异常");
                    return dto;
                }

                dto = JsonEntityTransform.json2DataTransferObject(resultJson);
                if (dto.getCode() != DataTransferObject.SUCCESS) {
                    return dto;
                }
                Integer updateCount = dto.parseData("result", new TypeReference<Integer>() {});
                if (updateCount == 0) {
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("更新房东评价，数量为0 ");
                    return dto;
                }
                //更新房东对房客的评价，评分 结束

                //统计房客的平均分 开始
                EvaluateOrderEntity evaluateOrderEntityLandlord = new EvaluateOrderEntity();
                evaluateOrderEntityLandlord.setRatedUserUid(tenantEvaApiRequest.getTenantUid());
                evaluateOrderEntityLandlord.setHouseFid(tenantEvaApiRequest.getHouseFid());
                evaluateOrderEntityLandlord.setRoomFid(tenantEvaApiRequest.getRoomFid());
                evaluateOrderEntityLandlord.setRentWay(tenantEvaApiRequest.getRentWay());
                evaluateOrderEntityLandlord.setFid(tenantEvaApiRequest.getEvaOrderIdLandlord());
                evaluateOrderEntityLandlord.setOrderSn(tenantEvaApiRequest.getOrderSn());
                evaluateOrderEntityLandlord.setEvaStatu(tenantEvaApiRequest.getEvaStatu());
                evaluateOrderEntityLandlord.setEvaUserType(UserTypeEnum.LANDLORD.getUserType());

                EvaluateRequest evaRequest = new EvaluateRequest();
                evaRequest.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
                evaRequest.setEvaUserType(UserTypeEnum.LANDLORD.getUserType());
                //更新房东对房客的评价时， 被评人是房客
                evaRequest.setRatedUserUid(tenantEvaApiRequest.getTenantUid());
                DataTransferObject listDto = JsonEntityTransform.json2DataTransferObject(evaluateOrderService.listAllEvaOrderByCondition(JsonEntityTransform.Object2Json(evaRequest)));
                if (listDto.getCode() == DataTransferObject.SUCCESS) {
                    List<EvaluateOrderEntity> evalist = listDto.parseData("list", new TypeReference<List<EvaluateOrderEntity>>() {
                    });
                    if (Check.NuNCollection(evalist)) {
                        LogUtil.info(logger, "删除房客统计数据 参数，evaluateOrderEntity={}", JsonEntityTransform.Object2Json(evaluateOrderEntityLandlord));
                        //清除房东对房客的评价统计
                        String result = evaluateOrderService.delStatsData(JsonEntityTransform.Object2Json(evaluateOrderEntityLandlord));
                        LogUtil.info(logger, "删除房客统计数据 返回: {}", result);
                        if (Check.NuNStr(result)) {
                            dto = JsonEntityTransform.json2DataTransferObject(result);
                            if (dto.getCode() != DataTransferObject.SUCCESS) {
                                return dto;
                            }
                        }
                    } else {
                        LogUtil.info(logger, "更新房客统计数据 参数 evaluateOrderEntity={}", JsonEntityTransform.Object2Json(evaluateOrderEntityLandlord));
                        List<EvaluateOrderEntity> showStatList = canShowAndStat(evalist);
                        int count = 0;
                        String s = evaluateOrderService.updateLandAndStatsTenantEva(JsonEntityTransform.Object2Json(showStatList),
                                JsonEntityTransform.Object2Json(evaluateOrderEntityLandlord), count);
                        LogUtil.info(logger, "更新房客统计数据 返回: {}", s);
                        if (!Check.NuNStr(s)) {
                            dto = JsonEntityTransform.json2DataTransferObject(s);
                            if (dto.getCode() != DataTransferObject.SUCCESS) {
                                return dto;
                            }
                        }

                    }
                }
                //统计房客的平均分 结束
            }
        } catch (Exception e) {
            LogUtil.error(logger, "修改房客和房东的评价,评分异常, e:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("修改房客和房东的评价,评分异常,请重试");
        }
        return dto;
    }

    /**
     *
     * 保存 房东 评价
     *
     * @author yd
     * @created 2017年5月26日 下午8:35:33
     *
     * @param request
     * @return
     */
    @RequestMapping("saveLanlordEva")
    public @ResponseBody
    DataTransferObject saveLanlordEva(HttpServletRequest request, LanlordEvaRequest lanlordEvaRequest) {

        DataTransferObject dto = new DataTransferObject();

        try {
            dto = checkEvaluateOrder(lanlordEvaRequest, dto);

            if (dto.getCode() != 0) {
                return dto;
            }

            //查询当前订单
            dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.getOrderInfoByOrderSn(lanlordEvaRequest.getOrderSn()));

            if (dto.getCode() == 0) {
                OrderInfoVo order = SOAResParseUtil.getValueFromDataByKey(dto.toJsonString(), "orderInfoVo", OrderInfoVo.class);

                //校验订单状态
                int orderStatu = order.getOrderStatus().intValue();
                if (orderStatu < OrderStatusEnum.CHECKING_OUT.getOrderStatus()) {
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("订单状态不对，不能评价");
                    return dto;
                }
                //查询订单快照
                dto = JsonEntityTransform.json2DataTransferObject(orderCommonService.findHouseSnapshotByOrderSn(lanlordEvaRequest.getOrderSn()));
                //订单快照无
                if (dto.getCode() == 1) {
                    return dto;
                }
                OrderHouseSnapshotEntity orderHouseSnapshot = dto.parseData("orderHouseSnapshot", new TypeReference<OrderHouseSnapshotEntity>() {
                });
                //评论内容过滤
                dto = JsonEntityTransform.json2DataTransferObject(this.searchService.getIkList(lanlordEvaRequest.getContent()));

                List<String> listStrings = dto.parseData("ikList", new TypeReference<List<String>>() {
                });
                if (Check.NuNCollection(listStrings)) {
                    //修改订单的评价状态
                    dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.getOrderByOrderSn(lanlordEvaRequest.getOrderSn()));
                    OrderEntity orderEntity = dto.parseData("order", new TypeReference<OrderEntity>() {
                    });
                    //校验订单房东是否已评价
                    int evaStatus = orderEntity.getEvaStatus().intValue();
                    if (evaStatus == OrderEvaStatusEnum.WAITINT_EVA.getCode() || evaStatus == OrderEvaStatusEnum.UESR_HVA_EVA.getCode()) {
                        //保存房东信息
                        EvaluateOrderEntity evaluateOrder = saveLandlordEvaluate(order, orderHouseSnapshot, dto, lanlordEvaRequest, orderEntity);
                        if (dto.getCode() == 0) {
                            if (!Check.NuNObj(orderEntity)) {
                                orderEntity.setOldStatus(orderEntity.getOrderStatus());
                                if (evaStatus == OrderEvaStatusEnum.WAITINT_EVA.getCode()) {
                                    orderEntity.setEvaStatus(OrderEvaStatusEnum.LANLORD_EVA.getCode());
                                } else {
                                    orderEntity.setEvaStatus(OrderEvaStatusEnum.ALL_EVA.getCode());
                                }
                                //修改评价状态
                                dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.updateOrderBaseByOrderSn(JsonEntityTransform.Object2Json(orderEntity)));
                                if (dto.getCode() == 0) {
                                    evaluateOrder.setOrderEvaFlag(OrderEvaFlagEnum.ORDE_HAVE_EVA.getCode());
                                    evaluateOrder.setLastModifyDate(new Date());
                                    dto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.updateEvaluateOrderByCondition(JsonEntityTransform.Object2Json(evaluateOrder)));
                                    return dto;
                                }
                            }
                        }
                    }
                    if (dto.getCode() == 0) {
                        dto = new DataTransferObject();
                        dto.setErrCode(DataTransferObject.ERROR);
                        dto.setMsg("不能重复评价");
                    }

                } else {
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("评论包含敏感词，不能包含" + listStrings);
                }
            }
        } catch (Exception e) {
            LogUtil.error(logger, "error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务错误-点评异常");
        }
        return dto;
    }

    /**
     *
     * 保存房客评价信息
     *
     * @author yd
     * @created 2016年5月1日 下午5:31:15
     *
     * @param order
     * @param orderHouseSnapshot
     * @param dto
     */
    private EvaluateOrderEntity saveLandlordEvaluate(OrderHouseVo order, OrderHouseSnapshotEntity orderHouseSnapshot, DataTransferObject dto, LanlordEvaRequest lanlordEvaApiRequest, OrderEntity orderEntity) {

        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(cityTemplateService.getTextValue(null, String.valueOf(EvaluateRulesEnum.EvaluateRulesEnum001.getValue())));
        //房客未评价
        EvaluateOrderEntity evaluateOrder = new EvaluateOrderEntity();
        evaluateOrder.setCreateTime(new Date());
        evaluateOrder.setEvaStatu(EvaluateStatuEnum.AUDIT.getEvaStatuCode());
        evaluateOrder.setEvaUserType(UserTypeEnum.LANDLORD.getUserType());
        evaluateOrder.setEvaUserUid(order.getLandlordUid());
        evaluateOrder.setFid(UUIDGenerator.hexUUID());
        evaluateOrder.setHouseFid(orderHouseSnapshot.getHouseFid());
        evaluateOrder.setLastModifyDate(new Date());
        evaluateOrder.setOrderEvaFlag(OrderEvaFlagEnum.ORDER_NOT_HAVE_EVA.getCode());
        evaluateOrder.setOrderSn(order.getOrderSn());
        evaluateOrder.setRatedUserUid(order.getUserUid());
        evaluateOrder.setRentWay(order.getRentWay());
        evaluateOrder.setRoomFid(orderHouseSnapshot.getRoomFid());
        evaluateOrder.setBedFid(orderHouseSnapshot.getBedFid());

        if (resultDto.getCode() == DataTransferObject.SUCCESS
                && !Check.NuNObj(resultDto.getData().get("textValue"))
                && Integer.valueOf(resultDto.getData().get("textValue").toString()).intValue() == IsReleaseEnum.Release.getCode()) {
            evaluateOrder.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
        }
        LandlordEvaluateEntity landlordEvaluateEntity = new LandlordEvaluateEntity();
        landlordEvaluateEntity.setContent(lanlordEvaApiRequest.getContent());
        landlordEvaluateEntity.setCreateTime(new Date());
        landlordEvaluateEntity.setEvaOrderFid(evaluateOrder.getFid());
        landlordEvaluateEntity.setFid(UUIDGenerator.hexUUID());
        landlordEvaluateEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
        landlordEvaluateEntity.setLandlordSatisfied(lanlordEvaApiRequest.getLandlordSatisfied());
        landlordEvaluateEntity.setLastModifyDate(new Date());

        dto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.saveLandlordEvaluate(JsonEntityTransform.Object2Json(landlordEvaluateEntity), JsonEntityTransform.Object2Json(evaluateOrder), JsonEntityTransform.Object2Json(orderEntity)));

        return evaluateOrder;
    }

    /**
     *
     * 去评价页面
     *
     * @author yd
     * @created 2017年5月26日 下午8:51:50
     *
     * @param lanlordEvaRequest
     * @return
     */
    @RequestMapping("goToEvaluate")
    public String goToEvaluate(HttpServletRequest request) {

        //1=房客评价  2=房东
        String evaType = request.getParameter("evaType") == null ? "1" : request.getParameter("evaType");
        request.setAttribute("evaType", evaType);
        return "evaluate/addEvaluate";
    }
}
