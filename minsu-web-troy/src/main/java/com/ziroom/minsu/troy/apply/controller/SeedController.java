package com.ziroom.minsu.troy.apply.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityEntity;
import com.ziroom.minsu.entity.cms.ActivityFreeEntity;
import com.ziroom.minsu.entity.customer.CustomerRoleBaseEntity;
import com.ziroom.minsu.entity.customer.CustomerRoleEntity;
import com.ziroom.minsu.services.cms.api.inner.ActivityApplyService;
import com.ziroom.minsu.services.cms.api.inner.ActivityGiftService;
import com.ziroom.minsu.services.cms.api.inner.ActivityRecordService;
import com.ziroom.minsu.services.cms.api.inner.ActivityService;
import com.ziroom.minsu.services.cms.dto.LanApplayRequest;
import com.ziroom.minsu.services.cms.entity.ActivityApplyVo;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerRoleService;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.valenum.cms.FreeTypeEnum;
import com.ziroom.minsu.valenum.cms.RoleCodeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>种子房东</p>
 * <p>
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
@Controller
@RequestMapping("apply")
public class SeedController {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(SeedController.class);

    @Resource(name = "cms.activityApplyService")
    private ActivityApplyService activityApplyService;

    @Resource(name = "cms.activityService")
    private ActivityService activityService;

    @Resource(name = "customer.customerRoleService")
    private CustomerRoleService customerRoleService;

    @Resource(name = "cms.activityRecordService")
    private ActivityRecordService activityRecordService;

    @Resource(name = "cms.activityGiftService")
    private ActivityGiftService activityGiftService;

    /**
     * 种子房东申请主页
     *
     * @param request
     * @author afi
     */
    @RequestMapping("/listApply")
    public ModelAndView listApply(HttpServletRequest request) {

        ModelAndView maView = new ModelAndView("/apply/listApply");
        return maView;
    }


    @RequestMapping("showRoles")
    @ResponseBody
    public PageResult showRoles(String uid, PageRequest pageRequest) {

        if (Check.NuNStr(uid)) {
            return new PageResult();
        }
        try {
            String resultJson = customerRoleService.getBaseRolesByPage(JsonEntityTransform.Object2Json(pageRequest));
            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
            List<CustomerRoleBaseEntity> roleList = resultDto.parseData("list", new TypeReference<List<CustomerRoleBaseEntity>>() {
            });

            List<CustomerRoleEntity> list = new ArrayList<>();
            if (!Check.NuNCollection(roleList)) {
                String jsonRst = customerRoleService.getCustomerRolesMapWithoutFrozen(uid);
                DataTransferObject rstDto = JsonEntityTransform.json2DataTransferObject(jsonRst);
                Map<String, CustomerRoleEntity> roleMap = rstDto.parseData("roleMap", new TypeReference<Map<String, CustomerRoleEntity>>() {
                });
                if (Check.NuNObj(roleMap)) {
                    roleMap = new HashMap<>();
                }
                for (CustomerRoleBaseEntity entity : roleList) {
                    CustomerRoleEntity role = new CustomerRoleEntity();
                    BeanUtils.copyProperties(entity, role);
                    role.setUid(uid);
                    if (roleMap.containsKey(entity.getRoleCode())) {
                        CustomerRoleBaseEntity userRole = roleMap.get(entity.getRoleCode());
                        role.setIsFrozen(userRole.getIsFrozen());
                    } else {
                        role.setIsFrozen(null);
                    }
                    list.add(role);
                }
            }
            PageResult pageResult = new PageResult();
            pageResult.setRows(list);
            pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
            return pageResult;
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            return new PageResult();
        }

    }

    /**
     * 用户列表
     *
     * @param request
     * @author afi
     */
    @RequestMapping("listCustomer")
    public ModelAndView listCustomer(HttpServletRequest request) {
        ModelAndView maView = new ModelAndView("/apply/listCustomer");

        String result = customerRoleService.getBaseRoles();
        List<CustomerRoleBaseEntity> list = null;
        try {
            list = SOAResParseUtil.getListValueFromDataByKey(result, "roles", CustomerRoleBaseEntity.class);
            //返回结果
        } catch (Exception e) {
            LogUtil.error(LOGGER, "数据转化异常e={}", e);
        }
        request.setAttribute("list", list);
        return maView;
    }


    /**
     * 种子房东申请查看
     *
     * @return
     * @author afi
     */
    @RequestMapping("/seedApplyList")
    @ResponseBody
    public PageResult seedApplyList(@ModelAttribute("paramRequest") LanApplayRequest paramRequest, HttpServletRequest request) {

        String resultJson = activityApplyService.applyList(JsonEntityTransform.Object2Json(paramRequest));
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
        List<ActivityApplyVo> actCouponList = resultDto.parseData("list", new TypeReference<List<ActivityApplyVo>>() {
        });

        PageResult pageResult = new PageResult();
        pageResult.setRows(actCouponList);
        pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
        return pageResult;
    }


    /**
     * 同意成为种子房东
     *
     * @param uid
     * @return
     * @author afi
     */
    @RequestMapping("agreeSeed")
    @ResponseBody
    public DataTransferObject agreeSeed(String uid, String roleCode) {
        DataTransferObject dto = new DataTransferObject();
        DataTransferObject acDto = null;
        try {
            LogUtil.info(LOGGER,"同意成为种子房东参数：uid{}，roleCode：{}", uid, roleCode);
            if (Check.NuNStr(uid) || Check.NuNStr(roleCode)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto;
            }

            //校验当前的角色信息
            String checkJson = customerRoleService.checkCustomerRole(uid, roleCode);
            LogUtil.info(LOGGER,"校验角色信息返回：{}", checkJson);
            DataTransferObject checkDto = JsonEntityTransform.json2DataTransferObject(checkJson);
            if (checkDto.getCode() != DataTransferObject.SUCCESS) {
                LogUtil.error(LOGGER, "验证当前角色失败：{} ", checkDto.toJsonString());
                return dto;
            } else {
                Integer has = checkDto.parseData("has", new TypeReference<Integer>() {
                });
                if (has == YesOrNoEnum.YES.getCode()) {
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("当前角色已经存在");
                    return dto;
                }
            }
            boolean freeFlag = false;
            String saveJson = null;
            ActivityEntity act = getActivitySeed();
            if (!Check.NuNObj(act)) {
                if (Check.NuNStr(act.getRoleCode()) || "0".equals(act.getRoleCode()) || act.getRoleCode().equals(roleCode)) {
                    if (roleCode.equals(ValueUtil.getStrValue(RoleCodeEnum.SEED.getCode()))) {
                        ActivityFreeEntity request = new ActivityFreeEntity();
                        request.setActCode(act.getActSn());
                        request.setActName(act.getActName());
                        request.setUid(uid);
                        String userFid = UserUtil.getCurrentUserFid();
                        request.setCreateId(userFid);
                        request.setFreeType(FreeTypeEnum.SEED_FREE.getCode());
                        saveJson = activityRecordService.saveFreeComm(JsonEntityTransform.Object2Json(request), act.getActCut());
                        LogUtil.info(LOGGER,"同意成为种子房东，插入免佣金表返回：{}", saveJson);
                        DataTransferObject saveDto = JsonEntityTransform.json2DataTransferObject(saveJson);
                        if (saveDto.getCode() != DataTransferObject.SUCCESS) {
                            LogUtil.error(LOGGER, "插入免佣金表失败：{} ", saveDto.toJsonString());
                            return dto;
                        } else {
                            freeFlag = true;
                        }
                    }
                }
            }
            String json = customerRoleService.saveCustomerRole(uid, roleCode);
            LogUtil.info(LOGGER,"同意成为种子房东返回：{}", json);
            acDto = JsonEntityTransform.json2DataTransferObject(json);
            if (freeFlag && acDto.getCode() != DataTransferObject.SUCCESS) {
                LogUtil.error(LOGGER, "插入免佣金表成功，但是处理角色表失败，请跟踪,免佣数据：{} ", saveJson);
            }
        } catch (BusinessException e) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("同意成为种子房东异常");
            LogUtil.error(LOGGER, "同意成为种子房东异常e:{}", e);
        }
        return acDto;
    }


    /**
     * 冻结种子房东
     *
     * @param uid
     * @return
     * @author afi
     */
    @RequestMapping("frozenSeed")
    @ResponseBody
    public DataTransferObject frozenSeed(String uid, String roleCode) {
        DataTransferObject dto = new DataTransferObject();
        try {
            LogUtil.info(LOGGER,"冻结种子房东种子房东参数：uid{}，roleCode：{}", uid, roleCode);
            if (Check.NuNStr(uid) || Check.NuNStr(roleCode)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto;
            }

            //将天使房东免佣金记录删掉(逻辑删除)
            ActivityFreeEntity activityFreeEntity = new ActivityFreeEntity();
            activityFreeEntity.setUid(uid);
            activityFreeEntity.setIsDel(YesOrNoEnum.YES.getCode());
            String paramJson = JsonEntityTransform.Object2Json(activityFreeEntity);
            String cancelFreeCommissionJson = activityGiftService.cancelFreeCommission(paramJson);
            LogUtil.info(LOGGER,"冻结种子房东种子，取消免佣金返回：{}", cancelFreeCommissionJson);
            DataTransferObject cancelFreeCommissionDto = JsonEntityTransform.json2DataTransferObject(cancelFreeCommissionJson);
            //只有将免佣金取消之后，再去取消天使房东资格
            if (cancelFreeCommissionDto.getCode() == DataTransferObject.SUCCESS) {//取消天使房东免佣金成功
                String json = customerRoleService.frozenCustomerRoleByType(uid, roleCode);
                LogUtil.info(LOGGER,"冻结种子房东种子房东返回：{}", json);
                DataTransferObject acDto = JsonEntityTransform.json2DataTransferObject(json);
                if (acDto.getCode() != DataTransferObject.SUCCESS) {
                    LogUtil.error(LOGGER, "免佣金取消成功，但冻结天使房东失败，请后续关注数据,{}", acDto);
                    return acDto;
                }
            } else {
                LogUtil.error(LOGGER, "免佣金取消失败,{}", cancelFreeCommissionDto);
                return cancelFreeCommissionDto;
            }
        } catch (BusinessException e) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("冻结种子房东异常");
            LogUtil.error(LOGGER, "冻结种子房东异常e:{}", e);
        }
        return dto;
    }


    /**
     * 解冻冻结种子房东
     *
     * @param uid
     * @return
     * @author afi
     */
    @RequestMapping("unfrozenSeed")
    @ResponseBody
    public DataTransferObject unfrozenSeed(String uid, String roleCode) {
        DataTransferObject dto = new DataTransferObject();
        try {
            LogUtil.info(LOGGER,"解冻冻结种子房东参数：uid{}，roleCode：{}", uid, roleCode);
            if (Check.NuNStr(uid) || Check.NuNStr(roleCode)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto;
            }
            String json = customerRoleService.unfrozenCustomerRoleByType(uid, roleCode);
            dto = JsonEntityTransform.json2DataTransferObject(json);
            if (dto.getCode() != DataTransferObject.SUCCESS) {
                LogUtil.error(LOGGER, "解冻天使房东失败:{}", dto.toJsonString());
                return dto;
            }
            ActivityEntity act = getActivitySeed();
            if (!Check.NuNObj(act)) {
                if (Check.NuNStr(act.getRoleCode()) || "0".equals(act.getRoleCode()) || act.getRoleCode().equals(roleCode)) {
                    if (roleCode.equals(ValueUtil.getStrValue(RoleCodeEnum.SEED.getCode()))) {
                        ActivityFreeEntity request = new ActivityFreeEntity();
                        request.setActCode(act.getActSn());
                        request.setActName(act.getActName());
                        request.setUid(uid);
                        String userFid = UserUtil.getCurrentUserFid();
                        request.setCreateId(userFid);
                        request.setFreeType(FreeTypeEnum.SEED_FREE.getCode());
                        String saveJson = activityRecordService.saveFreeComm(JsonEntityTransform.Object2Json(request), act.getActCut());
                        LogUtil.info(LOGGER,"解冻天使房东时，插入佣金表返回:{}", saveJson);
                        DataTransferObject saveDto = JsonEntityTransform.json2DataTransferObject(saveJson);
                        if (saveDto.getCode() != DataTransferObject.SUCCESS) {
                            LogUtil.error(LOGGER, "插入免佣金表失败：{} ", saveDto.toJsonString());
                            dto.setErrCode(DataTransferObject.ERROR);
                            dto.setMsg("解冻天使房东时，插入佣金表失败");
                            return dto;
                        }
                    }
                }
            }
            LogUtil.info(LOGGER,"解冻冻结种子房东返回：{}", json);
        } catch (BusinessException e) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("解冻冻结种子房东异常");
            LogUtil.error(LOGGER, "解冻冻结种子房东异常e:{}", e);
        }
        return dto;
    }


    /**
     * 获取当前的种子房东信息
     *
     * @author afi
     */
    private ActivityEntity getActivitySeed() {
        ActivityEntity act = null;
        //获取获取信息
        String json = activityService.getSeedActivityLast();

        DataTransferObject acDto = JsonEntityTransform.json2DataTransferObject(json);
        if (acDto.getCode() != DataTransferObject.SUCCESS) {
            LogUtil.error(LOGGER, "获取活动信息失败：{} ", acDto.toJsonString());
        } else {
            act = acDto.parseData("activity", new TypeReference<ActivityEntity>() {
            });
            LogUtil.info(LOGGER, "获取活动信息：{} ", JsonEntityTransform.Object2Json(act));
        }
        LogUtil.info(LOGGER,"获取当前的种子房东信息返回：{}", json);
        return act;
    }


    /**
     * 查询申请详情
     *
     * @param applyFid
     * @return
     * @author zl
     */
    @RequestMapping("getApplyDetail")
    @ResponseBody
    public DataTransferObject getApplyDetail(String applyFid) {
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(applyFid)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto;
        }

        try {
            String json = activityApplyService.getApplyDetailWithBLOBs(applyFid);
            dto = JsonEntityTransform.json2DataTransferObject(json);
            if (dto.getCode() != DataTransferObject.SUCCESS) {
                LogUtil.error(LOGGER, "查询申请详情失败,fid={},msg:{}", applyFid, dto.toJsonString());
                return dto;
            }


        } catch (Exception e) {
            LogUtil.error(LOGGER, "查询申请详情失败,fid={},error:{}", applyFid, e);
        }

        return dto;
    }


}
