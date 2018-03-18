package com.ziroom.zrp.service.houses.dto.smartplatform;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.zrp.service.houses.valenum.HouseTypeEnum;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>下发有效期密码</p>
 * wiki-http://wiki.ziroom.com/pages/viewpage.action?pageId=337674251
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @version 1.0
 * @Date Created in 2017年12月12日
 * @since 1.0
 */
@Data
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class SmartPlatformAddPasswordDto extends BaseEntity {

    /**
     * 收房合同/项目id
     */
    private String hireContractCode;

    /**
     * 房租类型
     * 1.友家 4.自如寓
     */
    private Integer houseType = HouseTypeEnum.ZYU.getCode();

    /**
     * 操作人人名称
     * 操作人，内部员工，名称
     */
    private String opsUser;

    /**
     * 操作人标识
     * 操作人，内部员工，工号
     */
    private String opsUserIdentifier;

    /**
     * 操作人类型
     * 操作人类型(1.自如客 2.自如员工 3.第三方)
     */
    private String opsUserType;

    /**
     * 用户姓名
     * 密码使用者的名字，如果是租客，就是租客名字，如果是自如工作人员，就是工作人员名字
     */
    private String userName;

    /**
     * 用户电话
     * 密码使用者电话号码
     */
    private String userPhone;

    /**
     * 开锁密码（6位），数字字符串。如果不带此参数，则后台随机生成密码。
     */
    private String password;

    /**
     * 设备维度1
     * 友家就是houseId 自如寓是项目id
     */
    private String positionRank1;

    /**
     * 设备维度2
     * 友家roomId 自如寓房间id
     */
    private String positionRank2;

    /**
     * 设备维度3
     */
    private String positionRank3;

    /**
     * 设备维度4
     */
    private String positionRank4;

    /**
     * 设备维度5
     */
    private String positionRank5;

    /**
     * 密码类型
     * 密码类型：1：租客密码 2：内部员工 3：第三方
     */
    private String passwordType;

    /**
     * 如果password_type为1，此参数为合同号；如果如果password_type为2；此参数为员工号；如果password_type为3，此参数为订单号。
     */
    private String userIdentify;

    /**
     * 是否发短信
     * 密码生成成功后，是否下发短信给用户手机，手机号为user_phone。1：不发送;2：发送
     */
    private String isSendSms;

    /**
     * 开始时间
     * 密码有效期开始时间
     */
    private String permissionBegin;

    /**
     * 结束时间
     * 密码有效期结束时间
     */
    private String permissionEnd;

    /**
     * 回调地址
     */
    private String backUrl;

}
