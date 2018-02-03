/**
 * @FileName: CustomerMsgManagerServiceProxy.java
 * @Package com.ziroom.minsu.services.customer.proxy
 * 
 * @author jixd
 * @created 2016年4月26日 下午4:57:48
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity;
import com.ziroom.minsu.entity.customer.CustomerOperHistoryEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerRoleEntity;
import com.ziroom.minsu.entity.customer.CustomerUpdateFieldAuditNewlogEntity;
import com.ziroom.minsu.entity.customer.TelExtensionEntity;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditNewlogEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.StringUtils;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.dto.CustomerAuditRequest;
import com.ziroom.minsu.services.customer.dto.CustomerBaseMsgDto;
import com.ziroom.minsu.services.customer.dto.CustomerExtDto;
import com.ziroom.minsu.services.customer.dto.CustomerInfoDto;
import com.ziroom.minsu.services.customer.dto.CustomerPicDto;
import com.ziroom.minsu.services.customer.entity.CustomerDetailImageVo;
import com.ziroom.minsu.services.customer.entity.CustomerDetailVo;
import com.ziroom.minsu.services.customer.entity.CustomerExt;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.customer.service.CustomerBankCardServiceImpl;
import com.ziroom.minsu.services.customer.service.CustomerBaseMsgServiceImpl;
import com.ziroom.minsu.services.customer.service.CustomerOperHistoryImpl;
import com.ziroom.minsu.services.customer.service.CustomerPicMsgServiceImpl;
import com.ziroom.minsu.services.customer.service.CustomerRoleServiceImpl;
import com.ziroom.minsu.services.customer.service.TelExtensionServiceImpl;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.common.YesOrNoOrFrozenEnum;
import com.ziroom.minsu.valenum.customer.AuditStatusEnum;
import com.ziroom.minsu.valenum.customer.CusotmerAuthEnum;
import com.ziroom.minsu.valenum.customer.CustomerRoleEnum;
import com.ziroom.minsu.valenum.customer.CustomerSexEnum;
import com.ziroom.minsu.valenum.customer.CustomerSourceEnum;
import com.ziroom.minsu.valenum.customer.IsLandlordEnum;
import com.ziroom.minsu.valenum.customer.PicTypeEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@Component("customer.customerMsgManagerServiceProxy")
public class CustomerMsgManagerServiceProxy implements CustomerMsgManagerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerMsgManagerServiceProxy.class);

	@Resource(name = "customer.messageSource")
	private MessageSource messageSource;	

	@Resource(name="customer.customerBaseMsgServiceImpl")
	private CustomerBaseMsgServiceImpl customerBaseMsgServiceImpl;

	@Resource(name="customer.customerOperHistoryImpl")
	private CustomerOperHistoryImpl customerOperHistoryImpl;

	@Resource(name="customer.customerPicMsgServiceImpl")
	private CustomerPicMsgServiceImpl customerPicMsgServiceImpl;

	@Resource(name="customer.customerBankCardServiceImpl")
	private CustomerBankCardServiceImpl customerBankCardServiceImpl;

	@Resource(name = "customer.telExtensionServiceImpl")
	private TelExtensionServiceImpl telExtensionService;

	@Autowired
	private RedisOperations redisOperations;

    @Resource(name = "customer.customerRoleServiceImpl")
    private CustomerRoleServiceImpl customerRoleService;

	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;

	@Value("#{'${default_head_size}'.trim()}")
	private String picSize;

	@Value(value = "${crm.400.phone}")
	private String ziroomPhonePre;
	
	@Value(value = "${USER_DEFAULT_PIC_URL}")
	private String USER_DEFAULT_PIC_URL;



	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerBaseMsgService#getCustomerDetail(java.lang.String)
	 */
	public String getCustomerDetail(String uid) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(uid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("uid is null");
			return dto.toJsonString();
		}
		CustomerDetailVo customerDetail = customerBaseMsgServiceImpl.getCustomerDetail(uid);
		dto.putValue("customerDetail", customerDetail);
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService#getCustomerDetailImage(java.lang.String)
	 */
	@Override
	public String getCustomerDetailImage(String uid) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(uid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("uid is null");
			return dto.toJsonString();
		}
		CustomerDetailImageVo customerImageVo = customerBaseMsgServiceImpl.getCustomerDetailImage(uid);
		dto.putValue("customerImageVo", customerImageVo);
		return dto.toJsonString();
	}


	/**
	 * 获取用户的信息
	 * @author afi
	 * @param uid
	 * @return
	 */
	public String getCustomerBaseMsgEntitybyUid(String uid){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(uid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("uid is null");
			return dto.toJsonString();
		}
		CustomerBaseMsgEntity customer = customerBaseMsgServiceImpl.getCustomerBaseMsgEntitybyUid(uid);
		dto.putValue("customer", customer);
		return dto.toJsonString();
	}



    /**
     * 获取角色信息列表
     * @author afi
     * @param reuest
     * @return
     */
    public String queryCustomerRoleMsg(String reuest){

        LogUtil.info(LOGGER, "请求参数:{}", reuest);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(reuest)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("request is null");
            return dto.toJsonString();
        }
        CustomerExtDto customerExtDto = JsonEntityTransform.json2Object(reuest, CustomerExtDto.class);
        //获取当前的加角色的信息
        PagingResult<CustomerExt> pageReuslt = this.getCustomerExtList(customerExtDto);
        dto.putValue("total", pageReuslt.getTotal());
        dto.putValue("customerList", pageReuslt.getRows());
        return dto.toJsonString();
    }

    /**
     * 获取当前用户的扩展信息
     * @author afi
     * @param customerExtDto
     * 
     * @return
     */
    private PagingResult<CustomerExt> getCustomerExtList(CustomerExtDto customerExtDto){
        PagingResult<CustomerExt> pageReuslt = customerBaseMsgServiceImpl.getCustomerExtList(customerExtDto);
        if (!Check.NuNCollection(pageReuslt.getRows())){
            for (CustomerExt ext:pageReuslt.getRows()){
                this.fillCustomerRoles(ext);
            }
        }
        return pageReuslt;
    }

    /**
     * 填充用户的角色信息
     * @author afi
     * @param ext
     */
    private void fillCustomerRoles(CustomerExt ext){
        if (Check.NuNObj(ext)){
            return;
        }
        if (Check.NuNStr(ext.getUid())){
            return;
        }
        List<CustomerRoleEntity> roleEntityList = customerRoleService.getCustomerRoles(ext.getUid());
        String rolses = "";
        if (!Check.NuNCollection(roleEntityList)){
            for (CustomerRoleEntity roleEntity : roleEntityList) {
                if (!Check.NuNStr(rolses)){
                    rolses += ",";
                }
                rolses += roleEntity.getRoleName();
                if (roleEntity.getRoleCode().equals(CustomerRoleEnum.SEED.getStr())){
                    if (roleEntity.getIsFrozen() == YesOrNoEnum.YES.getCode()){
                        ext.setIsSeed(YesOrNoOrFrozenEnum.FROZEN.getCode());
                    }else {
                        ext.setIsSeed(YesOrNoOrFrozenEnum.YES.getCode());
                    }
                }
            }
        }
        //修改角色名称
        ext.setRoleNames(rolses);
    }

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerBaseMsgService#queryCustomerBaseMsg(java.lang.String)
	 */
	public String queryCustomerBaseMsg(String reuest) {
		LogUtil.info(LOGGER, "请求参数:{}", reuest);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(reuest)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("request is null");
			return dto.toJsonString();
		}
		CustomerBaseMsgDto customerRequest = JsonEntityTransform.json2Object(reuest, CustomerBaseMsgDto.class);
		PagingResult<CustomerBaseMsgEntity> pageReuslt = customerBaseMsgServiceImpl.queryCustomerBaseMsg(customerRequest);
		dto.putValue("total", pageReuslt.getTotal());
		dto.putValue("customerList", pageReuslt.getRows());
		return dto.toJsonString();
	}



	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerBaseMsgService#updateCustomerBaseMsg(java.lang.String)
	 */
	public String updateCustomerBaseMsg(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(paramJson)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("paramJson is null");
			return dto.toJsonString();
		}
		CustomerBaseMsgEntity customerBaseEntity = JsonEntityTransform.json2Entity(paramJson, CustomerBaseMsgEntity.class);
		customerBaseMsgServiceImpl.updateCustomerInfo(customerBaseEntity);
		try {
			redisOperations.del(RedisKeyConst.getCutomerKey(customerBaseEntity.getUid()));
		} catch (Exception e) {
            LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}
		
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerOperHistoryService#saveCustomerOperHistory(java.lang.String)
	 */
	public String saveCustomerOperHistory(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		CustomerOperHistoryEntity entity = JsonEntityTransform.json2Entity(paramJson, CustomerOperHistoryEntity.class);
		try{
			dto.putValue("count", customerOperHistoryImpl.insertCustomerOperHistory(entity));
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}


	/**
	 * 上传头像 上传成功删除同类型的其他照片
	 * @author afi
	 * @created 2017年2月23日 下午15:46:39
	 *
	 * @param paramJson
	 * @return
	 */
	public String insertCustomerPicMsgAndDelOthers(String paramJson){
		DataTransferObject dto = new DataTransferObject();
		try{
			CustomerPicMsgEntity picmsgEntity = JsonEntityTransform.json2Entity(paramJson, CustomerPicMsgEntity.class);
			if (Check.NuNObj(picmsgEntity)){
				LogUtil.error(LOGGER, "par:{}", paramJson);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数异常");
			}

			if (Check.NuNStr(picmsgEntity.getUid())){
				LogUtil.error(LOGGER, "par:{}", paramJson);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("uid为空");
			}
			if (Check.NuNObj(picmsgEntity.getPicType())){
				LogUtil.error(LOGGER, "par:{}", paramJson);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("异常的图片类型");
			}

			customerPicMsgServiceImpl.insertCustomerPicMsgAndDelOthers(picmsgEntity);

			try {
				redisOperations.del(RedisKeyConst.getCutomerKey(picmsgEntity.getUid()));
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}

		}catch (Exception e){
			LogUtil.error(LOGGER, "par:{}", paramJson);
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/**
	 * 插入用户图片，更新redis
	 */
	public String insertCustomerPicMsg(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try{
			CustomerPicMsgEntity picmsgEntity = JsonEntityTransform.json2Entity(paramJson, CustomerPicMsgEntity.class);
			customerPicMsgServiceImpl.insertCustomerPicMsg(picmsgEntity);
			
			try {
				redisOperations.del(RedisKeyConst.getCutomerKey(picmsgEntity.getUid()));
			} catch (Exception e) {
                LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}
			
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/**
	 * 批量增加客户图片信息
	 * @author lishaochuan
	 * @create 2016年5月7日下午5:57:06
	 * @param paramJson
	 * @return
	 */
	public String insertCustomerPicMsgList(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try{
			List<CustomerPicMsgEntity> picmsgEntityList = JsonEntityTransform.json2List(paramJson, CustomerPicMsgEntity.class);
			customerPicMsgServiceImpl.insertCustomerPicMsgList(picmsgEntityList);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerPicService#updateCustomerPicMsg(java.lang.String)
	 */
	public String getCustomerPicByType(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		CustomerPicDto picDto = JsonEntityTransform.json2Object(paramJson, CustomerPicDto.class);
		if(Check.NuNStr(picDto.getUid()) || Check.NuNObj(picDto.getPicType())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("uid is null or pictype is null");
		}
		try{
			CustomerPicMsgEntity entity = customerPicMsgServiceImpl.getCustomerPicByType(picDto.getUid(), picDto.getPicType());
			dto.putValue("customerPicMsgEntity", entity);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
	
	/**
	 * 根据图片类型查询集合
	 */
	public String getCustomerPicListByType(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		CustomerPicDto picDto = JsonEntityTransform.json2Object(paramJson, CustomerPicDto.class);
		if(Check.NuNStr(picDto.getUid()) || Check.NuNObj(picDto.getPicType())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("uid is null or pictype is null");
		}
		try{
			List<CustomerPicMsgEntity> customerPicList = customerPicMsgServiceImpl.getCustomerPicListByType(picDto.getUid(), picDto.getPicType());
			dto.putValue("customerPicList", customerPicList);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
	
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerPicService#getCustomerPicByType(java.lang.String)
	 */
	public String updateCustomerPicMsg(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try{
			CustomerPicMsgEntity picmsgEntity = JsonEntityTransform.json2Entity(paramJson, CustomerPicMsgEntity.class);
			dto.putValue("count", customerPicMsgServiceImpl.updateCustomerPicMsg(picmsgEntity));;
			if(Check.NuNObj(picmsgEntity)){
				try {
					redisOperations.del(RedisKeyConst.getCutomerKey(picmsgEntity.getUid()));
				} catch (Exception e) {
                    LogUtil.error(LOGGER, "redis错误,e:{}",e);
				}
				
			}
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}


	/**
	 * 
	 * 给API使用，其他人慎用
	 * 
	 * 保存用户相关信息 (存在则修改)
	 * 1.用户基本信息  2.用户照片信息  3.用户银行卡信息不做保存
	 * 
	 * 说明： A.用户的基本信息 保存成功  2 或 3 不用保证  故不能放在一个事物中  
	 *      B.如果当前用户是房东，更新操作，全部禁止
	 *  
	 * 更新用户数据分三类情况处理
	 *   A. 无论民宿库是否有值，都需要更新
	 *    1. real_name    真实姓名   2.customer_birthday 用户生日  3. id_type  4.id_no   5. customer_mobile 
	 *   B. 民宿库已有值，不更新，否则更新
	 *    2.customer_sex
	 *    3. customer_email        4. nick_name 
	 *    5. reside_addr           6.customer_edu
	 *    7. customer_job
	 *   C. 无需更新数据
	 *
	 * @author yd
	 * @created 2016年5月6日 下午4:59:41
	 *
	 * @param customerInfo
	 * @return
	 */
	@Override
	public String saveCustomerInfo(String customerInfo) {

		DataTransferObject dto = new DataTransferObject();

		CustomerInfoDto customerInfoDto = JsonEntityTransform.json2Object(customerInfo, CustomerInfoDto.class);

		if(Check.NuNObj(customerInfoDto)||Check.NuNObj(customerInfoDto.getCustomerBaseMsg())||Check.NuNStr(customerInfoDto.getCustomerBaseMsg().getUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("用户基本信息不存在");
			return dto.toJsonString();
		}
		CustomerBaseMsgEntity customMsg = customerInfoDto.getCustomerBaseMsg();
		String uid = customMsg.getUid();
	
		CustomerBaseMsgEntity customerBaseMsgEntity = this.customerBaseMsgServiceImpl.getCustomerBaseMsgEntitybyUid(uid);
		
		LogUtil.info(LOGGER, "请求参数customerInfoDto={},根据用户uid={}，查询返回实体customerBaseMsgEntity={}", customerInfoDto.toString(),uid,customerBaseMsgEntity);
		//当前用户是房东，不做任何更新
		int isLandlord = 0;
		customMsg.setLastModifyDate(new Date());
		
		try {
			if(Check.NuNObj(customerBaseMsgEntity)){
				
				customMsg.setCreateDate(new Date());
				customMsg.setCustomerSource(CustomerSourceEnum.Api.getCode());
				if(!Check.NuNStr(customMsg.getCustomerMobile())){
					customMsg.setIsContactAuth(CusotmerAuthEnum.IsAuth.getCode());
				}
				this.customerBaseMsgServiceImpl.insertCustomerInfo(customMsg);
			}else{
				
				isLandlord = customerBaseMsgEntity.getIsLandlord();
				if(IsLandlordEnum.IS_LANDLORD.getCode() == isLandlord){
					LogUtil.info(LOGGER, "当前待更新用户是房东,uid={},故不做任何更新操作，要更新redis中的用户数据", uid);
					dto.putValue("result", "当前用户是房东，不做任何更新");
					String userheadImg = "";
					CustomerPicMsgEntity pic = this.customerPicMsgServiceImpl.getCustomerPicByType(uid, PicTypeEnum.USER_PHOTO.getCode());
					if(!Check.NuNObj(pic)){
						userheadImg =  PicUtil.getFullPic(picBaseAddrMona, pic.getPicBaseUrl(), pic.getPicSuffix(), picSize);
					}
					//更新redis用户数据
					saveCustomerVoToRedis(uid, userheadImg, customerBaseMsgEntity);
					return dto.toJsonString();
				}
				
				//更新： 1. 数据已有数据不做更新  2. 不论数据库有没有的数据都需要修改
				CustomerBaseMsgEntity updateCustomer  = new CustomerBaseMsgEntity();
				if(!Check.NuNObj(customerBaseMsgEntity.getUid())){
					updateCustomer.setUid(customerBaseMsgEntity.getUid());
					
					if(Check.NuNStr(customerBaseMsgEntity.getCustomerEmail())){
						updateCustomer.setCustomerEmail(customMsg.getCustomerEmail());
					}
					
					if(Check.NuNStr(customerBaseMsgEntity.getResideAddr())){
						updateCustomer.setResideAddr(customMsg.getResideAddr());
					}
					if (Check.NuNObj(customerBaseMsgEntity.getCustomerSex())) {
						updateCustomer.setCustomerSex(customMsg.getCustomerSex());
					}
					if(Check.NuNStr(customerBaseMsgEntity.getNickName())){
						updateCustomer.setNickName(customMsg.getNickName());
					}
					if(Check.NuNStr(customerBaseMsgEntity.getCustomerJob())){
						updateCustomer.setCustomerJob(customMsg.getCustomerJob());
					}
					
					if(Check.NuNObj(customerBaseMsgEntity.getCustomerEdu())){
						updateCustomer.setCustomerEdu(customMsg.getCustomerEdu());
					}
					
					//有手机号 就认证通过
					if(!Check.NuNStr(customMsg.getCustomerMobile())){
						if(customerBaseMsgEntity.getIsContactAuth() == CusotmerAuthEnum.NotAuth.getCode()){
							updateCustomer.setIsContactAuth(CusotmerAuthEnum.IsAuth.getCode());
						}
						updateCustomer.setCustomerMobile(customMsg.getCustomerMobile());
					}
					
					updateCustomer.setRealName(customMsg.getRealName());
					updateCustomer.setIdType(customMsg.getIdType());
					updateCustomer.setIdNo(customMsg.getIdNo());
					updateCustomer.setCustomerBirthday(customMsg.getCustomerBirthday());
					
					this.customerBaseMsgServiceImpl.updateCustomerBaseMsgPortion(updateCustomer);
				}
				

			}
			
			LogUtil.info(LOGGER, "保存用户基本信息结束customerBaseMsgEntity={}", customMsg.toString());
			//保存照片信息
			//List<CustomerPicMsgEntity> listCustomerPicMsg = customerInfoDto.getListCustomerPicMsg();
			CustomerPicMsgEntity pic = this.customerPicMsgServiceImpl.getCustomerPicByType(uid, PicTypeEnum.USER_PHOTO.getCode());
			String headImg = "";//头型不存在 给出默认值
			/*if(!Check.NuNCollection(listCustomerPicMsg)&&Check.NuNObj(pic)){
				//用户头像  只有不存在 才插入
				pic = listCustomerPicMsg.get(0);
				if(Check.NuNStr(pic.getFid())) pic.setFid(UUIDGenerator.hexUUID());
				customerPicMsgServiceImpl.insertCustomerPicMsg(pic);
			}*/
			if(!Check.NuNObj(pic)){
				headImg = PicUtil.getFullPic(picBaseAddrMona, pic.getPicBaseUrl(), pic.getPicSuffix(), picSize);
			}
		
			saveCustomerVoToRedis(uid, headImg, customMsg);
		}catch(DuplicateKeyException e){
			LogUtil.error(LOGGER, "保存用户信息异常，此处由于是用户uid唯一约束，不影响具体业务，即把此异常吞掉,用户uid={},e={}", uid,e);
		}catch (Exception e) {
			LogUtil.error(LOGGER, "保存用户信息异常，用户uid={},e={}", uid,e);
		}
	
		return dto.toJsonString();
	}

	/**
	 * 
	 * 保存用户基本信息到redis
	 *
	 * @author yd
	 * @created 2016年6月13日 下午5:24:51
	 *
	 * @param uid
	 * @param headImg
	 * @param customMsg
	 */
	private void saveCustomerVoToRedis(String uid,String headImg,CustomerBaseMsgEntity customMsg){
		
		TelExtensionEntity telExtensionEntity =telExtensionService.getExtensionByUid(uid);
		String telExtension = null;
		if(!Check.NuNObj(telExtensionEntity)){
			telExtension = telExtensionEntity.getZiroomPhone();
		}
		CustomerVo customerVo = new CustomerVo();
		customerVo.setUserPicUrl(headImg);
		setCustomerVo(customMsg, customerVo,telExtension);
		//用户信息 先放redis
		String customerVoKey = RedisKeyConst.getCutomerKey(uid);
		//暂定保存12小时 保存条件：真实姓名或者手机号不能为null
		if(!Check.NuNObj(customerVoKey)){
			
			//放入redis（保存条件：电话号或者真实姓名或身份证信息为null，放redis2分钟  ，其他保存12小时
			int time =  RedisKeyConst.CUSTOMERVO_LOCK_CACHE_TIME;
			if(Check.NuNStr(customerVo.getCustomerMobile())
					||Check.NuNStr(customerVo.getRealName())
					||Check.NuNStr(customerVo.getIdNo())
					||customerVo.getCustomerMobile().equals(customerVo.getRealName())){
				time = RedisKeyConst.CUSTOMERVO_LOCK_CACHE_TIME_SHORT;
			}
			try {
				redisOperations.setex(customerVoKey,time, JsonEntityTransform.Object2Json(customerVo));
			} catch (Exception e) {
                LogUtil.error(LOGGER, "保存用户uid={},redis错误,e:{}",uid,e);
			}
			
		}
		LogUtil.debug(LOGGER, "保存用户redis信息customerVo={}", JsonEntityTransform.Object2Json(customerVo));
	}
	/**
	 * 
	 * 通过用户uid获取用户基本(先从redis获取，没有在查库)
	 *
	 * @author yd
	 * @created 2016年5月7日 下午3:10:09
	 *
	 * @param uid
	 * @return
	 */
	@Override
	public String getCutomerVo(String uid) {

		DataTransferObject dto  = new DataTransferObject();

		if(Check.NuNStr(uid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("uid is null");
			return dto.toJsonString();
		}
		String key = RedisKeyConst.getCutomerKey(uid);
		String customerVoStr = null;
		try {
			 customerVoStr =this.redisOperations.get(key);
		} catch (Exception e) {
            LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}
		
		LogUtil.info(LOGGER, "从redis中根据key={}，获取用户基本信息customerVo={}",key,customerVoStr);
		CustomerVo customerVo= null;
		if(Check.NuNStr(customerVoStr)){
			//去数据库查询
			CustomerBaseMsgEntity customMsg = this.customerBaseMsgServiceImpl.getCustomerBaseMsgEntitybyUid(uid);
			if(!Check.NuNObj(customMsg)){
				CustomerPicMsgEntity customerPicMsg = this.customerPicMsgServiceImpl.getCustomerPicByType(uid, PicTypeEnum.USER_PHOTO.getCode());
				if(!Check.NuNObj(customMsg)){
					customerVo = new CustomerVo();
					if(!Check.NuNObj(customerPicMsg)&&!Check.NuNStr(customerPicMsg.getPicBaseUrl())){
						customerVo.setUserPicUrl(customerPicMsg.getPicBaseUrl());
						if(!customerPicMsg.getPicBaseUrl().contains("http")){
                            String fullPic = PicUtil.getFullPic(picBaseAddrMona, customerPicMsg.getPicBaseUrl(), customerPicMsg.getPicSuffix(), picSize);
							customerVo.setUserPicUrl(fullPic);
						}
					}
					TelExtensionEntity telExtensionEntity =telExtensionService.getExtensionByUid(uid);
					String telExtension = null;
					if(!Check.NuNObj(telExtensionEntity)){
						telExtension = telExtensionEntity.getZiroomPhone();
					}
					setCustomerVo(customMsg,customerVo,telExtension);
				}

			}
		}else{
			customerVo=JsonEntityTransform.json2Object(customerVoStr, CustomerVo.class);
		}
		dto.putValue("customerVo", customerVo);
		return dto.toJsonString();
	}

	/**
	 * 
	 * 通过用户uid获取用户基本
	 *
	 * @author yd
	 * @created 2016年5月7日 下午3:10:09
	 *
	 * @param uid
	 * @return
	 */
	@Override
	public String getCutomerVoFromRedis(String uid) {

		DataTransferObject dto  = new DataTransferObject();
		CustomerVo customerVo= null;
		if(Check.NuNStr(uid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("uid is null");
			return dto.toJsonString();
		}
		String customerVoStr = null;
		try {
			 customerVoStr =this.redisOperations.get(RedisKeyConst.getCutomerKey(uid));
		} catch (Exception e) {
            LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}
		
		if(!Check.NuNStr(customerVoStr)){
			customerVo=JsonEntityTransform.json2Object(customerVoStr, CustomerVo.class);
		}
		LogUtil.info(LOGGER, "去redis获取数据customerVoStr", customerVoStr);
		dto.putValue("customerVo", customerVo);
		return dto.toJsonString();
	}

	/**
	 * 说明：这块信息 只放基本不会怎么变化的用户数据，若需要用户的实施数据，请去数据库实施查询  （请注意：银行卡信息 是专门的服务接口）
	 * @param customMsg
	 * @param customerVo
	 * @param telExtension
	 */
	private void setCustomerVo(CustomerBaseMsgEntity customMsg,CustomerVo customerVo,String telExtension){
		if(!Check.NuNObj(customMsg)){
			customerVo.setRealName(customMsg.getRealName());
			if(!Check.NuNStr(customMsg.getCustomerMobile())){
				customerVo.setShowMobile(customMsg.getCustomerMobile());
				customerVo.setCustomerMobile(StringUtils.getPhoneSecret(customMsg.getCustomerMobile()));
			}
			if(!Check.NuNObj(customMsg.getCustomerSex())){
				CustomerSexEnum customerSexEnum = CustomerSexEnum.getCustomerSexEnumByCode(customMsg.getCustomerSex().intValue());
				if(!Check.NuNObj(customerSexEnum)){
					customerVo.setCustomerSex(customerSexEnum.getValue());
				}
			}

			if(!Check.NuNObj(customMsg.getCustomerBirthday())){
				customerVo.setCustomerBirthday(DateUtil.dateFormat(customMsg.getCustomerBirthday()));
			}
			customerVo.setCustomerEmail(customMsg.getCustomerEmail());
			customerVo.setIdType(customMsg.getIdType());
			customerVo.setIdNo(customMsg.getIdNo());
			customerVo.setNickName(customMsg.getNickName());
			customerVo.setIsLandlord(customMsg.getIsLandlord());
			customerVo.setResideAddr(customMsg.getResideAddr());
			customerVo.setCustomerEdu(customMsg.getCustomerEdu());
			customerVo.setCustomerJob(customMsg.getCustomerJob());
			customerVo.setCityCode(customMsg.getCityCode());
			customerVo.setUid(customMsg.getUid());
			customerVo.setZiroomPhone(telExtension);
			customerVo.setHostNumber(ziroomPhonePre);
			customerVo.setCountryCode(customMsg.getCountryCode());
			
			if(Check.NuNStr(customerVo.getUserPicUrl())){
				customerVo.setUserPicUrl(USER_DEFAULT_PIC_URL);
			}
		}
	}


	/**
	 * 
	 * 通过用户uid去获取用户信息，直接从库中获取  这里主要给mapp端提供的接口  那边用户信息保存在session中
	 *
	 * @author yd
	 * @created 2016年5月7日 下午3:10:09
	 *
	 * @param uid
	 * @return
	 */
	@Override
	public String getCutomerVoFromDb(String uid) {

		DataTransferObject dto  = new DataTransferObject();

		if(Check.NuNStr(uid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("uid is null");
			return dto.toJsonString();
		}

		CustomerVo customerVo= null;
		//去数据库查询
		CustomerBaseMsgEntity customMsg = this.customerBaseMsgServiceImpl.getCustomerBaseMsgEntitybyUid(uid);
		if(!Check.NuNObj(customMsg)){
			//CustomerBankCardMsgEntity customerBankCardMsg = this.customerBankCardServiceImpl.getCustomerBankCardByUid(uid);

			TelExtensionEntity telExtensionEntity =telExtensionService.getExtensionByUid(uid);
			String telExtension = null;
			if(!Check.NuNObj(telExtensionEntity)){
				telExtension = telExtensionEntity.getZiroomPhone();
			}
			CustomerPicMsgEntity customerPicMsg = this.customerPicMsgServiceImpl.getCustomerPicByType(uid, PicTypeEnum.USER_PHOTO.getCode());
			if(!Check.NuNObj(customMsg)){
				customerVo = new CustomerVo();
				if(!Check.NuNObj(customerPicMsg)&&!Check.NuNStr(customerPicMsg.getPicBaseUrl())){

					customerVo.setUserPicUrl(customerPicMsg.getPicBaseUrl());
					if(!customerPicMsg.getPicBaseUrl().contains("http")){

                        String fullPic = PicUtil.getFullPic(picBaseAddrMona, customerPicMsg.getPicBaseUrl(), customerPicMsg.getPicSuffix(), picSize);
						customerVo.setUserPicUrl(fullPic);
					}

				}
				setCustomerVo(customMsg, customerVo,telExtension);
				CustomerBaseMsgExtEntity ext = 	this.customerBaseMsgServiceImpl.selectCustomerExtByUid(uid);
				if(!Check.NuNObj(ext)){
					customerVo.setCustomerIntroduce(ext.getCustomerIntroduce());
				}
			}

		}
		dto.putValue("customerVo", customerVo);
		return dto.toJsonString();
	}

	/**
	 * 
	 * get entity by uid
	 *
	 * @author yd
	 * @created 2016年5月26日 下午9:48:38
	 *
	 * @param uid
	 * @return
	 */
	@Override
	public String selectCustomerExtByUid(String uid) {

		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNStr(uid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("用户uid不存在");
			return dto.toJsonString();
		}
		CustomerBaseMsgExtEntity customerBaseMsgExt = this.customerBaseMsgServiceImpl.selectCustomerExtByUid(uid);
		dto.putValue("customerBaseMsgExt", customerBaseMsgExt);
		return dto.toJsonString();
	}

	/**
	 * 
	 * 保存用户扩展信息
	 *
	 * @author yd
	 * @created 2016年5月26日 下午9:51:41
	 *
	 * @param customerBaseMsgExt
	 * @return
	 */
	@Override
	public String insertCustomerExt(String customerBaseMsgExt) {

		DataTransferObject dto = new DataTransferObject();

		CustomerBaseMsgExtEntity customerExt = JsonEntityTransform.json2Object(customerBaseMsgExt, CustomerBaseMsgExtEntity.class);
		if(Check.NuNObj(customerExt)||Check.NuNStr(customerExt.getUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("用户uid不存在");
			return dto.toJsonString();
		}
		dto.putValue("result", this.customerBaseMsgServiceImpl.insertCustomerExtSelective(customerExt));
		return dto.toJsonString();
	}

	/**
	 * 
	 * update entity by uid
	 *
	 * @author yd
	 * @created 2016年5月26日 下午9:54:55
	 *
	 * @param customerBaseMsgExt
	 * @return
	 */
	@Override
	public String updateCustomerExtByUid(String customerBaseMsgExt) {

		DataTransferObject dto = new DataTransferObject();

		CustomerBaseMsgExtEntity customerExt = JsonEntityTransform.json2Object(customerBaseMsgExt, CustomerBaseMsgExtEntity.class);
		if(Check.NuNObj(customerExt)||Check.NuNStr(customerExt.getUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("用户uid不存在");
			return dto.toJsonString();
		}
		dto.putValue("result", this.customerBaseMsgServiceImpl.updateCustomerExtByUidSelective(customerExt));
		return dto.toJsonString();
	}

	@Override
	public String getCustomerPicByUid(String uid) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(uid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("uid is null");
			return dto.toJsonString();
		}
		List<CustomerPicMsgEntity> picList = this.customerPicMsgServiceImpl.getCustomerPicByUid(uid);
		dto.putValue("list", picList);
		return dto.toJsonString();
	}

	/**
	 * 更新昵称和个人介绍
	 * @author wangwt
	 * @created 2017年06月22日 17:14:23
	 * @param paramJson
	 * @return
	 */
	@Override
	public String saveNickNameAndIntroduce(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		CustomerVo customerVo = JsonEntityTransform.json2Object(paramJson, CustomerVo.class);
		//根据uid查询客户信息
		String uid = customerVo.getUid();
		String nickName = customerVo.getNickName();
		String customerIntroduce = customerVo.getCustomerIntroduce();
		CustomerBaseMsgEntity customer = customerBaseMsgServiceImpl.getCustomerBaseMsgEntitybyUid(uid);

		if (!Check.NuNObj(customer) && customer.getIsUploadIcon() != 1) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请先上传头像");
			return dto.toJsonString();
		}

		//如果已认证，昵称不能为空
		if (!Check.NuNObj(customer) && customer.getAuditStatus() == AuditStatusEnum.COMPLETE.getCode()) {
			if (Check.NuNStr(nickName)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("昵称不能为空");
				return dto.toJsonString();
			}
			if (Check.NuNStr(customerIntroduce)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("个人介绍不能为空");
				return dto.toJsonString();
			}
		}

		try {
			//如果不存在个人介绍 插入新的
			CustomerBaseMsgExtEntity customerBaseMsgExtEntity = customerBaseMsgServiceImpl.selectCustomerExtByUid(uid);
			if (Check.NuNObj(customerBaseMsgExtEntity)) {
                CustomerBaseMsgExtEntity extEntity = new CustomerBaseMsgExtEntity();
                extEntity.setFid(UUIDGenerator.hexUUID());
                extEntity.setUid(uid);
                extEntity.setCustomerIntroduce(customerIntroduce);
                customerBaseMsgServiceImpl.insertCustomerExtSelective(extEntity);
            } else {
                //存在更新个人介绍
                customerBaseMsgExtEntity.setCustomerIntroduce(customerIntroduce);
                customerBaseMsgServiceImpl.updateCustomerExtByUidSelective(customerBaseMsgExtEntity);
            }
			//更新昵称
			customer.setNickName(nickName);
			customerBaseMsgServiceImpl.updateCustomerInfo(customer);
		} catch (Exception e) {
			LogUtil.info(LOGGER, "saveNickNameAndIntroduce error ： {}", e);
		}
		long startTime1=System.currentTimeMillis();
		//删除redis
		try {
			redisOperations.del(RedisKeyConst.getCutomerKey(uid));
		} catch (Exception e) {
			LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}
		long time1=System.currentTimeMillis()-startTime1;
		LogUtil.info(LOGGER,"redis删除用户信息缓存用时："+time1);

		return dto.toJsonString();
	}

	/**
	 * 审核拒绝
	 *
	 * @author loushuai
	 * @created 2017年8月9日 上午11:20:42
	 *
	 * @param object2Json
	 * @return
	 */
	@Override
	public String updateCustomerUpdateFieldAuditNewlogByFid(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		try{
			CustomerUpdateFieldAuditNewlogEntity customerUpdateFieldAuditNewlog = JsonEntityTransform.json2Entity(paramJson,CustomerUpdateFieldAuditNewlogEntity.class);
			if(Check.NuNObj(customerUpdateFieldAuditNewlog)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource,MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			int num = customerBaseMsgServiceImpl.updateCustomerUpdateFieldAuditNewlogByFid(customerUpdateFieldAuditNewlog);
			dto.putValue("num",num);
		}catch (Exception e){
			LogUtil.error(LOGGER,"error:{}",e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("系统异常");
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/**
	 * 审核后，修改base，ext，pic三个表中的数据
	 *
	 * @author loushuai
	 * @created 2017年8月9日 下午2:37:51
	 *
	 * @param object2Json
	 * @return
	 */
	@Override
	public String updateBaseAndExtOrPic(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		try{
			CustomerAuditRequest customerAuditRequest = JsonEntityTransform.json2Entity(paramJson,CustomerAuditRequest.class);
			if(Check.NuNObj(customerAuditRequest)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource,MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			int num = customerBaseMsgServiceImpl.updateBaseAndExtOrPic(customerAuditRequest);
			
			dto.putValue("num",num);
		}catch (Exception e){
			LogUtil.error(LOGGER,"error:{}",e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("系统异常");
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	
	/**
	 * 
	 * 用户信息 审核通过
	 *
	 * @author yd
	 * @created 2017年9月12日 上午10:05:11
	 *
	 * @param paramJson
	 * @return
	 */
	@Override
	public String auditedCustomerInfo(String paramJson){
		DataTransferObject dto=new DataTransferObject();
		try{
			CustomerAuditRequest customerAuditRequest = JsonEntityTransform.json2Entity(paramJson,CustomerAuditRequest.class);
			if(Check.NuNObj(customerAuditRequest)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource,MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			customerBaseMsgServiceImpl.auditedCustomerInfo(customerAuditRequest);
		}catch (Exception e){
			LogUtil.error(LOGGER,"error:{}",e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("系统异常");
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}
	/**
	 * 根据t_customer_update_field_audit_newlog表的fid获取对象
	 *
	 * @author loushuai
	 * @created 2017年8月11日 下午7:44:41
	 *
	 * @param fieldHeadPicKey
	 * @return
	 */
	@Override
	public String getUpdateFieldAuditNewlogByFid(String UpdateFieldAuditNewlogFid) {
		DataTransferObject dto=new DataTransferObject();
		try{
			if(Check.NuNStr(UpdateFieldAuditNewlogFid)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource,MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			CustomerUpdateFieldAuditNewlogEntity customerUpdateFieldAuditNewlog = customerBaseMsgServiceImpl.getUpdateFieldAuditNewlogByFid(UpdateFieldAuditNewlogFid);
			dto.putValue("updateFieldAuditNewlog",customerUpdateFieldAuditNewlog);
		}catch (Exception e){
			LogUtil.error(LOGGER,"error:{}",e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("系统异常");
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/**
	 * 修改用户个人介绍（不需要审核，如：troy上修改）
	 *
	 * @author loushuai
	 * @created 2017年11月16日 下午3:55:12
	 *
	 * @param ext
	 * @return
	 */
	@Override
	public String updateCustomerExtNotAudit(String param) {
		LogUtil.info(LOGGER, "updateCustomerExtNotAudit param={}", param);
		DataTransferObject dto = new DataTransferObject();

		CustomerBaseMsgExtEntity customerExt = JsonEntityTransform.json2Object(param, CustomerBaseMsgExtEntity.class);
		if(Check.NuNObj(customerExt)||Check.NuNStr(customerExt.getUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("用户uid不存在");
			return dto.toJsonString();
		}
		dto.putValue("result", this.customerBaseMsgServiceImpl.updateCustomerExtNotAudit(customerExt));
		return dto.toJsonString();
	}


}
