package com.zra.repair.resources;

import com.alibaba.fastjson.JSONObject;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.github.pagehelper.PageInfo;
import com.zra.common.utils.CommonUtil;
import com.zra.common.utils.PropUtils;
import com.zra.house.entity.dto.CityDto;
import com.zra.repair.entity.ZryRepairOrder;
import com.zra.repair.entity.ZryRepairOrderLog;
import com.zra.repair.entity.dto.ZryRepairOrderPageDto;
import com.zra.repair.entity.ResultMap;
import com.zra.repair.entity.dto.ZryRepairOrderCancelDto;
import com.zra.repair.entity.dto.ZryRepairOrderDto;
import com.zra.repair.logic.ZryRepairOrderLogic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>自如寓报修单逻辑</p>
 * <ul>
 *     <li>新增报修单,post</li>
 *     <li>删除报修单,delete</li>
 *     <li>获取报修单,get</li>
 * </ul>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author fengbo.yue
 * @Date Created in 2017年09月28日 15:39
 * @since 1.0
 * @version 1.0
 */
@Component
@Api(value="/zra/repair")
@Path("/zra/repair")
public class ZryRepairOrderResources {

    private static final Logger log = LoggerFactoryProxy.getLogger(ZryRepairOrderResources.class);

    @Autowired
    private ZryRepairOrderLogic zryRepairOrderLogic;

    /**
     * 新建报修单
     * @param zryRepairOrderDto
     * @return
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "新建报修单", notes = "新建报修单,并且添加报修操作日志", response = Response.class)
    public Object addAndLog(@NotNull ZryRepairOrderDto zryRepairOrderDto) {

        ResultMap resultMap;
        boolean result;

        ZryRepairOrder repairOrder = new ZryRepairOrder();
        ZryRepairOrderLog repairOrderLog = new ZryRepairOrderLog();

        try {
            // 维修单 业务Fid
            String orderFid = CommonUtil.uuid();

            // 维修单 装配
            BeanUtils.copyProperties(repairOrder, zryRepairOrderDto);
            repairOrder.setFid(orderFid);
            // 新建的单子状态“待接单”
            repairOrder.setOrderStatus(ZryRepairOrder.OrderStatus.INIT.getStatus());

            //新增维修单操作日志
            repairOrderLog.setFid(CommonUtil.uuid());
            repairOrderLog.setFromStatus(ZryRepairOrder.OrderStatus.INIT.getStatus());
            repairOrderLog.setToStatus(ZryRepairOrder.OrderStatus.INIT.getStatus());
            repairOrderLog.setRepairOrder(orderFid);
            repairOrderLog.setCreateFid(zryRepairOrderDto.getCreateFid());

            //保存维修单，并且记录操作日志
            result = this.zryRepairOrderLogic.saveAndLog(repairOrder,repairOrderLog);

            if (result) {
                resultMap = ResultMap.success(result);
            } else {
                resultMap = ResultMap.fail().setData(result);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("addAndLog fail : param [{}]", JSONObject.toJSONString(zryRepairOrderDto), e);
            resultMap = ResultMap.fail().setMessage(e.getMessage()).setData(zryRepairOrderDto);
        } catch(Exception e) {
            log.error("addAndLog fail : param [{}]", JSONObject.toJSONString(zryRepairOrderDto), e);
            resultMap = ResultMap.fail().setMessage(e.getMessage());
        }

        return resultMap;
    }

    /***
     * 废弃报修单
     * @param zryRepairOrderLogDto
     * @return
     */
    @POST
    @Path("/cancel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "废弃报修单", notes = "废弃状态是“未上门”的报修单,添加报修操作日志", response = Response.class)
    public Object cancel(@NotNull ZryRepairOrderCancelDto zryRepairOrderLogDto) {

        ResultMap resultMap;
        boolean result;

        if (zryRepairOrderLogDto == null || StringUtils.isBlank(zryRepairOrderLogDto.getRepairOrder())){
            return ResultMap.fail()
                    .setData(false)
                    .setMessage("the repairOrder num must be not null!");
        }
        // 维修单号fId
        String fid = zryRepairOrderLogDto.getRepairOrderFid();

        ZryRepairOrderLog orderLog = new ZryRepairOrderLog();
        orderLog.setCreateFid(zryRepairOrderLogDto.getCreateFid());
        orderLog.setRepairOrder(fid);
        orderLog.setFromStatus(zryRepairOrderLogDto.getFromStatus());
        orderLog.setToStatus(ZryRepairOrder.OrderStatus.CANCEL.getStatus());
        orderLog.setFid(CommonUtil.uuid());
        orderLog.setRemark(zryRepairOrderLogDto.getRemark());
        orderLog.setCancelReason(zryRepairOrderLogDto.getCancelReason());
        try {
            // 废弃维修单，并且记录操作日志
            result = this.zryRepairOrderLogic.cancelAndLogByFId(orderLog);
            if (result) {
                resultMap = ResultMap.success(result);
            } else {
                resultMap = ResultMap.fail().setData(result);
            }
        } catch (Exception e) {
            log.error("cancel fail : param [{}]", JSONObject.toJSONString(zryRepairOrderLogDto), e);
            resultMap = ResultMap.fail().setMessage(e.getMessage());
        }
        return resultMap;
    }

    /**
     * 维修单条件分页查询
     * @param pageNum 页码
     * @param pageSize 行数
     * @param itemFid   项目Fid
     * @param orderSn   维修单号
     * @return
     */
    @GET
    @Path("/search")
    @Produces("application/json")
    @ApiOperation(value = "分页查询维修单", notes = "分页查询维修单", response = Response.class)
    public Object page (
            @DefaultValue("1")@QueryParam("pageNum") int pageNum, @DefaultValue("10")@QueryParam("pageSize") int pageSize,
            @QueryParam("projectFid") String itemFid, @QueryParam("orderSn") String orderSn,
            @QueryParam("roomNum") String roomNum, @QueryParam("repairsMan") String repairsMan,
            @QueryParam("areaType") Integer areaType, @QueryParam("categoryCode") String categoryCode,
            @QueryParam("businessType") String businessType, @QueryParam("orderStatus") Integer orderStatus,
            @QueryParam("visitLinkman") String visitLinkman, @QueryParam("visitMobile") String visitMobile,
            @QueryParam("goodsCode") String goodsCode, @QueryParam("areaCode") String areaCode,
            @QueryParam("start") String start, @QueryParam("end") String end,@QueryParam("cityCode") String cityCode
            ){

        ZryRepairOrderPageDto repairOrderPage = ZryRepairOrderPageDto.builder()
                .pageNum(pageNum).pageSize(pageSize)
                .orderSn(orderSn)
                .pageNum(pageNum)
                .itemFid(itemFid).areaCode(areaCode)
                .areaType(areaType).businessType(businessType).categoryCode(categoryCode)
                .goodsCode(goodsCode).orderStatus(orderStatus).repairsMan(repairsMan)
                .visitLinkman(visitLinkman).visitMobile(visitMobile).roomNum(roomNum).cityCode(cityCode)
                .build();

        try {
            if (StringUtils.isNotBlank(start)) {
                repairOrderPage.setStart(Timestamp.valueOf(LocalDateTime.parse(start)));
            }
            if (StringUtils.isNotBlank(end)) {
                repairOrderPage.setEnd(Timestamp.valueOf(LocalDateTime.parse(end)));
            }
        } catch (Exception e) {
            // do
            log.error(e.getMessage(),e);
        }

        PageInfo<ZryRepairOrder> zryRepairOrderPage = this.zryRepairOrderLogic.pagingRepairOrder(repairOrderPage);

        return zryRepairOrderPage;
    }

    /***
     * 查询报修单操作日志，根据报修单Fid
     * @param fid
     * @return
     */
    @GET
    @Path("/log")
    @Produces("application/json")
    @ApiOperation(value = "查询维修单操作日志", notes = "查询维修单操作日志，通过维修单Fid", response = Response.class)
    public Object log(@NotNull @QueryParam("fid") String fid){
        ResultMap resultMap;
        List<ZryRepairOrderLog> zryRepairOrderPage;
        try {
            zryRepairOrderPage = this.zryRepairOrderLogic.findLogByFid(fid);

            resultMap = ResultMap.success(zryRepairOrderPage);
        } catch (Exception e) {
            log.error("get repair log fail : param [{}]", fid, e);
            resultMap = ResultMap.fail().setMessage(e.getMessage());
        }

        return resultMap;
    }

    /***
     * 删除报修单
     * @param fid
     * @return
     */
    @DELETE
    @Path("/{fid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean delete(@Valid @PathParam("fid") String fid) {
        // 没有这个功能需求，暂时不开放接口
        //return this.zryRepairOrderLogic.deleteByFId(fid);
        return true;
    }

    /**
     * 查询维修单根据维修单单号
     * @param orderSn
     * @return
     */
    @GET
    @Path("/{orderSn}/order")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "查询维修单根据维修单单号", notes = "查询维修单操作日志，通过维修单orderSn", response = Response.class)
    public Object findByOrderSn(@NotNull @PathParam("orderSn") String orderSn) {
    	try {
	        if (StringUtils.isBlank(orderSn)) {
	            return ResultMap.fail()
	                    .setData(false)
	                    .setMessage("the repairOrder orderSn must be not null!");
	        }
	        return this.zryRepairOrderLogic.findByOrderSn(orderSn);
    	} catch (Exception e) {
            log.error("get repair log fail : param [{}]", orderSn, e);
        }
    	return null;
    }

    /***
     * 查询报修单详情
     * @param fid
     * @return
     */
    @GET
    @Path("/{fid}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Object find(@NotNull @PathParam("fid") String fid) {
        if (StringUtils.isBlank(fid)) {
            return null;
        }
        return this.zryRepairOrderLogic.findByFId(fid);
    }

    /**
     * 查询全部报修单
     * @param zryRepairOrder
     * @return
     */
    @GET
    @Path("/all")
    public Object all(ZryRepairOrder zryRepairOrder) {
        //return this.zryRepairOrderLogic.listAll();
        return null;
    }
    
    /***
     * 维修查询供应商
     * @param
     * @return
     */
    @GET
    @Path("/findSupplier")
    @ApiOperation(value = "維修查詢供應商", notes = "維修查詢供應商", response = Response.class)
    public Object findSupplier(@QueryParam("houseSourceCode") String houseSourceCode,
            @QueryParam("goodName") String goodName,
            @QueryParam("contractNum") String contractNum, @QueryParam("goodType") int goodType,@QueryParam("cityCode") String cityCode) {
    	log.info("維修查詢供應商！");
    	Map<String, Object> resutMap=new HashMap<String, Object>();
    	resutMap.put("code", 0);
    	resutMap.put("message", "");
    	if(cityCode==null || cityCode.equals("110000")){
        	resutMap.put("goodBelong", Integer.valueOf(PropUtils.getString("repair_supplier_goodBelong")));
        	resutMap.put("isGuaranteePeriod", Integer.valueOf(PropUtils.getString("repair_supplier_isGuaranteePeriod")));
        	resutMap.put("settleSupplier", PropUtils.getString("repair_supplier_name"));
    	} else if(cityCode.equals("310000")) {
        	resutMap.put("goodBelong", Integer.valueOf(PropUtils.getString("repair_supplier_goodBelong")));
        	resutMap.put("isGuaranteePeriod", Integer.valueOf(PropUtils.getString("repair_supplier_isGuaranteePeriod")));
        	resutMap.put("settleSupplier", PropUtils.getString("sh_repair_supplier_name"));
		}
        return resutMap;
    }
}
