package com.ziroom.minsu.mapp.evaluate.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.mapp.common.constant.MappMessageConst;
import com.ziroom.minsu.mapp.customer.vo.LandlordDto;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerDetailImageVo;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.evaluate.entity.TenantEvaItemVo;
import com.ziroom.minsu.services.evaluate.entity.TenantEvaluateVo;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.customer.CustomerPicTypeEnum;

/**
 * <p>房客评价相关接口API</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@RequestMapping("tenantEva")
@Controller
public class TenantEvaController {

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LandlordEvaController.class);

	@Resource(name = "evaluate.evaluateOrderService")
	private EvaluateOrderService evaluateOrderService;

	@Resource(name = "mapp.messageSource")
	private MessageSource messageSource;

	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;

	@Value("#{'${detail_big_pic}'.trim()}")
	private String detail_big_pic;
	
	@Value("#{'${default_head_size}'.trim()}")
	private String default_head_size;
	
	@Value("#{'${USER_DEFAULT_PIC_URL}'.trim()}")
	private String USER_DEFAULT_PIC_URL;

	/**
	 * 
	 * 查询当前房东被评价的列表
	 *
	 * @author liujun
	 * @created 2016年6月16日
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${NO_LOGIN_AUTH}/tenEvaList")
	@ResponseBody
	public DataTransferObject queryEvaluateInfo(HttpServletRequest request, LandlordDto landlordDto) {
		DataTransferObject dto  = new DataTransferObject();
		try{
			LogUtil.info(LOGGER, "参数:{}", JsonEntityTransform.Object2Json(landlordDto));
			
			if(Check.NuNStr(landlordDto.getLandlordUid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.LANDLORDUID_NULL));
				LogUtil.error(LOGGER, "error:{}", dto.toString());
			}
			
			EvaluateRequest evaluateRequest = new EvaluateRequest();
			evaluateRequest.setPage(landlordDto.getPage());
			evaluateRequest.setLimit(landlordDto.getLimit());
			//被评人房东uid
			evaluateRequest.setRatedUserUid(landlordDto.getLandlordUid());
			//评价人类型房客
			evaluateRequest.setEvaUserType(UserTypeEnum.TENANT.getUserType());
			
			String landlordEvaPage = evaluateOrderService.queryTenantEvaluateByPage(JsonEntityTransform.Object2Json(evaluateRequest));
			dto = JsonEntityTransform.json2DataTransferObject(landlordEvaPage);
			if(dto.getCode() != DataTransferObject.SUCCESS){
				LogUtil.error(LOGGER, "evaluateOrderService#queryTenantEvaluateByPage接口调用失败,参数:{},结果:{}",
						JsonEntityTransform.Object2Json(evaluateRequest), dto.toJsonString());
				return dto;
			}
			
			List<TenantEvaluateVo> tenantEvalList = dto.parseData("listTenantEvaluateVo",
					new TypeReference<List<TenantEvaluateVo>>() {});
			List<TenantEvaItemVo> evaList = new ArrayList<TenantEvaItemVo>();
			for(TenantEvaluateVo tenantEva : tenantEvalList){
				TenantEvaItemVo itemVo = new TenantEvaItemVo();
				itemVo.setContent(tenantEva.getContent());
				itemVo.setCreateTime(DateUtil.dateFormat(tenantEva.getCreateTime()));
				//获取评价人uid
				String userUid = tenantEva.getEvaUserUid();
				setCustomerInfo(userUid,itemVo);
				evaList.add(itemVo);
			}
			Integer total = Integer.valueOf(dto.getData().get("total").toString());
			
			dto.setErrCode(DataTransferObject.SUCCESS);
			dto.putValue("total", total);
			dto.putValue("evaList", evaList);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());;
		}
		LogUtil.info(LOGGER, "返回结果:{}", dto.toJsonString());
		return dto;
	}
	
	/**
	 * 
	 * 获取用户昵称和头像
	 *
	 * @author liujun
	 * @created 2016年6月16日
	 *
	 * @param uid
	 * @return
	 */
	private void setCustomerInfo(String uid, TenantEvaItemVo itemVo) {
		try {
			String customerJson = customerMsgManagerService.getCustomerDetailImage(uid);

			DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
			if (customerDto.getCode() == DataTransferObject.SUCCESS) {
				CustomerDetailImageVo vo = customerDto.parseData("customerImageVo", 
						new TypeReference<CustomerDetailImageVo>() {});
				if (Check.NuNObj(vo)) {
					itemVo.setNickName("");
					itemVo.setUserHeadPic(USER_DEFAULT_PIC_URL);
					return;
				}
				
				if (Check.NuNStr(vo.getNickName())) {
					itemVo.setNickName(vo.getRealName());
				}else{
					itemVo.setNickName(vo.getNickName());
				}
				itemVo.setUserHeadPic(USER_DEFAULT_PIC_URL);
				for (CustomerPicMsgEntity picMsg : vo.getCustomerPicList()) {
					if (picMsg.getPicType() == CustomerPicTypeEnum.YHTX.getCode()) {
						if (!Check.NuNStr(picMsg.getPicServerUuid())) {
							String headPicUrl = PicUtil.getFullPic(picBaseAddrMona, picMsg.getPicBaseUrl(),
									picMsg.getPicSuffix(), default_head_size);
							itemVo.setUserHeadPic(headPicUrl);
						}
						
					}
				}
			} else {
				itemVo.setNickName("");
				itemVo.setUserHeadPic(USER_DEFAULT_PIC_URL);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}
	}
}
