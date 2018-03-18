package com.ziroom.zrp.service.trading.proxy.commonlogic;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.zrp.houses.entity.EmployeeEntity;
import com.ziroom.zrp.houses.entity.RoomInfoExtEntity;
import com.ziroom.zrp.service.houses.api.EmployeeService;
import com.ziroom.zrp.service.houses.api.RoomService;
import com.zra.common.exception.ZrpServiceException;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>访问houses服务</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2018年01月18日 11:02
 * @since 1.0
 */
@Component("trading.housesCommonLogic")
public class HousesCommonLogic {

    @Resource(name="houses.employeeService")
    private EmployeeService employeeService;

    @Resource(name = "houses.roomService")
    private RoomService roomService;

    /**
     * 根据employeeId获取员工信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public EmployeeEntity getHousesEmployByEmployeeId(String employeeId) {

        try {
            DataTransferObject employeeObject = JsonEntityTransform.json2DataTransferObject(employeeService.findEmployeeById(employeeId));
            if (employeeObject.getCode() == DataTransferObject.SUCCESS) {
                EmployeeEntity employeeEntity = employeeObject.parseData("employeeEntity", new TypeReference<EmployeeEntity>() {});
                return employeeEntity;
            }
            throw  new ZrpServiceException("未查询到员工信息:" + employeeId);
        } catch (Exception e) {
            throw  new ZrpServiceException("访问获取员工信息接口失败;", e);
        }
    }

    /**
     * 查询RoomInfoExtEntity
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public RoomInfoExtEntity getRoomInfoExtEntityByRoomId(String roomId) {

        try {
            String roomExt = roomService.getRoomInfoExtByRoomId(roomId);
            RoomInfoExtEntity roomInfoExtEntity = SOAResParseUtil.getValueFromDataByKey(roomExt, "roomExt", RoomInfoExtEntity.class);
            return roomInfoExtEntity;
        } catch (SOAParseException e) {
            throw  new ZrpServiceException("查询房间扩展信息异常,roomId:" + roomId, e);
        }
    }

    /**
     * 根据房间ID，判断是否支持智能水
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public boolean isSupportIntellectWater(String roomId) {
        RoomInfoExtEntity roomInfoExtEntity = getRoomInfoExtEntityByRoomId(roomId);
        if (roomInfoExtEntity == null) {
            throw  new ZrpServiceException("查询房间扩展信息异常,roomId:" + roomId);
        }
        boolean isSupportIntellectWater =  (roomInfoExtEntity.getIsBindWatermeter() == YesOrNoEnum.YES.getCode()) ;
        return isSupportIntellectWater;
    }

}
