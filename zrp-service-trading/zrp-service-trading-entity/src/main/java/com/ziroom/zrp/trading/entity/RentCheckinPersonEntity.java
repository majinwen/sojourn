package com.ziroom.zrp.trading.entity;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.util.Check;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentCheckinPersonEntity extends BaseEntity {

    /**
     * 序列id
     */
    private static final long serialVersionUID = -3773710443998828458L;

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
     * 创建人NAME
     */
    private String createName;

    /**
     * 更新人标识
     */
    private String updaterId;

    /**
     * 更新人NAME
     */
    private String updateName;

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


    /**
     * 兼容图片地址
     * @param domain 域名
     * @return
     */
    public RentCheckinPersonEntity validPicDomain(String domain) {
        String domainStart = "http";
        if (Check.NuNStr(domain)) {
            return this;
        }
        if (!Check.NuNStr(certPic1) && !certPic1.startsWith(domainStart)) {
            certPic1 = domain + certPic1;
        }

        if (!Check.NuNStr(certPic2) && !certPic2.startsWith(domainStart)) {
            certPic2 = domain + certPic2;
        }

        if (!Check.NuNStr(socialProofPic) && !socialProofPic.startsWith(domainStart)) {
            socialProofPic = domain + socialProofPic;
        }
        return this;
    }
}