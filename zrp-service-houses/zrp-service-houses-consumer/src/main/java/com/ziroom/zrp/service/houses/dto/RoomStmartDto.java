package com.ziroom.zrp.service.houses.dto;

import com.ziroom.zrp.houses.entity.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2017年12月04日 16:22
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomStmartDto extends PageRequest {


    private static final long serialVersionUID = 2867500657986562921L;

    private String fid;
    /**
     * 项目id
     */
    private   String  projectId;

    /**
     * 户型id
     */
    private   String  houseTypeId;

    /**
     * 客户姓名
     *
     */
    private   String  customerName;

    /**
     * 房间状态
     */
    private   Integer  currentState;

    /**
     * 城市id
     */
    private  String cityId;

    /**
     * 户型id
     */
    private String houseTypeiId;

    /**
     *房间id
     */
    private String roomId;

    /**
     * 回调状态	0 获取中，1 成功，2 失败
     */
    private Integer status;

    /**
     * 房间号
     */
    private String roomNumber;

    /**
     * 入住人姓名
     */
    private String userName;

    /**
     * 房间id集合
     */
    private List<String> listRoomIds = new ArrayList<String>();

}
