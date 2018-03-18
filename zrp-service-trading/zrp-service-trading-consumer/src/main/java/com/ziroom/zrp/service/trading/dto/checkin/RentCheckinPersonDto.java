package com.ziroom.zrp.service.trading.dto.checkin;

import com.ziroom.zrp.service.trading.dto.PersonalInfoDto;
import com.ziroom.zrp.service.trading.dto.customer.Cert;
import com.ziroom.zrp.service.trading.dto.customer.Education;
import com.ziroom.zrp.service.trading.dto.customer.Extend;
import com.ziroom.zrp.service.trading.dto.customer.Profile;
import com.ziroom.zrp.trading.entity.RentCheckinPersonEntity;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>入住人Dto</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author phil
 * @Date Created in 2017年12月04日 18:14
 * @Version 1.0
 * @Since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentCheckinPersonDto implements Serializable{

    private static final long serialVersionUID = -2470464157464049816L;


    private Integer id;

    /**
     * 国籍
     */
    private String nationality;

    /**
     * 籍贯
     */
    private String hometown;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 紧急联系人
     */
    private String emergencyContact;

    /**
     * 与紧急联系人关系
     */
    private String relationship;

    /**
     * 社会资质证明
     */
    private Integer socialProof;

    /**
     * 城市id
     */
    private String cityId;

    /**
     * 客户库标识
     */
    private String uid;

    /**
     * 职业
     */
    private String job;

    /**
     * 证件号
     */
    private String certNum;

    /**
     * 证件照片1
     */
    private String certPic1;

    /**
     * 证件照片2
     */
    private String certPic2;

    /**
     * 社会资质证明照
     */
    private String socialProofPic;

    /**
     * 公司/学校
     */
    private String organization;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phoneNum;

    /**
     * 学历
     */
    private Integer degree;

    /**
     * 性别 1-男 2-女
     */
    private Integer sex;

    /**
     * 是否结婚 1-未婚 2-已婚 3-离异
     */
    private Integer marriage;

    /**
     * 证件类型
     */
    private Integer certType;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否有效 1-有效 0-无效
     */
    private Integer isValid;

    /**
     * 是否删除 0-正常 1-删除
     */
    private Integer isDel;

    /**
     * 创建人标识
     */
    private String creatorId;

    /**
     * 更新人标识
     */
    private String updaterId;

    /**
     * 更新时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 邮编
     */
    private String postalCode;

    /**
     * 客户来源(0-其他；1-自如网；2-店面接待；3-社区开发；4-400；5-热线；6-自如客户推荐；7-朋友介绍；8-大客户渠道；9-丁丁接待)
     */
    private Integer customerFrom;

    /**
     * 紧急联系人电话
     */
    private String emcyCntPhone;

    /**
     * 生日-年
     */
    private Integer birthdayYear;

    /**
     * 生日-月和天 例如“0808”
     */
    private String birthdayMonday;
    /**
     * 公司地址
     */
    private String workAddress;
    /**
     * 合同ID
     */
    private String contractId;


    //-------项目信息-------//
    private String projectId;
    private String projectName;

    //-------房间信息-------//
    private String roomId;
    private String roomNumber;


    /**
     * 转换入住人po实体
     * @param rentCheckinPersonDto
     * @return
     * @throws Exception
     */
    public static RentCheckinPersonEntity turn(RentCheckinPersonDto rentCheckinPersonDto) throws Exception {

        RentCheckinPersonEntity rentCheckinPersonEntity = RentCheckinPersonEntity.builder().build();

        BeanUtils.copyProperties(rentCheckinPersonDto,rentCheckinPersonEntity);

        return rentCheckinPersonEntity;
    }

    /**
     * 检查电话
     * @return
     * @throws Exception
     */
    public boolean checkPhone() throws Exception {
        Assert.notNull(phoneNum, "入住人电话不能为空");
        return true;
    }

    public static RentCheckinPersonDto of(PersonalInfoDto person) {

        Profile profile = person.getProfile();// 个人信息
        Education education = person.getEducation();// 学历
        Extend extend = person.getExtend();// 扩展
        Cert cert = person.getCert();// 身份信息

        RentCheckinPersonDto dto = new RentCheckinPersonDto();
        if (profile != null) {
            dto.setPhoneNum(profile.getPhone());
            dto.setName(profile.getUser_name());
            dto.setNationality(profile.getNationality());
            dto.setHometown(profile.getLocation());
            dto.setJob(profile.getJob());
            dto.setSex(profile.getGender());
        }

        if (education != null) {
            dto.setDegree(education.getEducation());
            if (StringUtils.isBlank(dto.getOrganization())) {
                dto.setOrganization(education.getSchool());
            }
        }

        if (extend != null) {
            dto.setEmergencyContact(extend.getUrgency_name());
            dto.setRelationship(extend.getUrgency_relation());
            dto.setEmcyCntPhone(extend.getUrgency_phone());
            dto.setWorkAddress(extend.getWork_address());
            dto.setOrganization(extend.getWork_name());// 工作单位、学校
        }

        if (cert != null) {
            dto.setCertNum(cert.getCert_num());
            if (StringUtils.isNotBlank(cert.getCert_type())) {
                dto.setCertType(Integer.valueOf(cert.getCert_type()));
            }
        }
        return dto;
    }

    public static RentCheckinPersonDto of(RentCheckinPersonEntity personEntity) {
        RentCheckinPersonDto rentCheckinPersonDto = null;
        if (personEntity != null) {
            rentCheckinPersonDto = new RentCheckinPersonDto();
            BeanUtils.copyProperties(personEntity, rentCheckinPersonDto);
        }
        return rentCheckinPersonDto;
    }
}
