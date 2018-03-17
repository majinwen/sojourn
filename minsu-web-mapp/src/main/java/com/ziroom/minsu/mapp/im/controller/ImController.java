
package com.ziroom.minsu.mapp.im.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgHouseEntity;
import com.ziroom.minsu.mapp.common.util.CustomerVoUtils;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.house.dto.HouseCheckDto;
import com.ziroom.minsu.services.message.api.inner.MsgBaseService;
import com.ziroom.minsu.services.message.api.inner.MsgHouseService;
import com.ziroom.minsu.services.message.dto.MsgBaseRequest;
import com.ziroom.minsu.services.message.dto.MsgCountRequest;
import com.ziroom.minsu.services.message.dto.MsgHouseRequest;
import com.ziroom.minsu.services.message.entity.MsgHouseListVo;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.IMIsDelEnum;

/**
 * <p>房东端IM聊天业务控制层</p>
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
@RequestMapping("im")
public class ImController {


	/**
	 * 日志对象
	 */
	private Logger logger = LoggerFactory.getLogger(ImController.class);

	@Resource(name = "message.msgHouseService")
	private MsgHouseService msgHouseService;

	@Resource(name = "message.msgBaseService")
	private MsgBaseService msgBaseService;

	@Resource(name = "house.houseManageService")
	private HouseManageService houseManageService;

	@Resource(name = "mapp.messageSource")
	private MessageSource messageSource;

	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	@Value(value = "${IM_TOMCAT_URL}")
	private String IM_TOMCAT_URL;

	/**
	 * 每页展示记录条数
	 */
	public  final static int pageNum = 8;

	/**
	 * 
	 * 到im聊天列表页面
	 *
	 * @author yd
	 * @created 2016年6月15日 上午10:59:36
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/imIndex")
	public String toImList(HttpServletRequest request){
		CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		
		String msgType = request.getParameter("msgType")==null?"1":request.getParameter("msgType");//消息类型 1=房东 2=房客 默认是1来自M站  2代表来自房客端
		String version = request.getParameter("version") == null?"-1":request.getParameter("version");//客户的版本号
		String imSourceList = request.getParameter("imSourceList") == null?"-1":request.getParameter("imSourceList");//客户的版本号
		request.setAttribute("imUrl", IM_TOMCAT_URL);
		request.setAttribute("msgType", msgType);
		request.setAttribute("menuType", "im");
		request.setAttribute("landlordUid", customerVo.getUid());
		request.setAttribute("version", version);
		request.setAttribute("imSourceList", imSourceList);
		
		return "/im/imList";
	}

	/**
	 * 
	 * 查询IM消息列表
	 *
	 * @author yd
	 * @created 2016年6月15日 上午11:08:22
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/queryImList")
	@ResponseBody
	public DataTransferObject queryImList(HttpServletRequest request,@ModelAttribute("msRequest")MsgHouseRequest msRequest){

		msRequest.setLimit(pageNum);
		CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		msRequest.setLandlordUid(customerVo.getUid());
		msRequest.setTenantUid(customerVo.getUid());
		DataTransferObject dto = new DataTransferObject();
		
		String msgType = request.getParameter("msgType")==null?"1":request.getParameter("msgType");//消息类型 1=房东 2=房客 默认是1来自M站  2代表来自房客端

		if(Check.NuNStr(msRequest.getTenantUid())||Check.NuNStr(msRequest.getLandlordUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("用户非法");
			return dto;
		}
		
		if(msgType.equals(String.valueOf(UserTypeEnum.LANDLORD.getUserType()))){
			dto = JsonEntityTransform.json2DataTransferObject(msgHouseService.queryLandlordList(JsonEntityTransform.Object2Json(msRequest)));
		}
		if(msgType.equals(String.valueOf(UserTypeEnum.TENANT.getUserType()))){
			dto = JsonEntityTransform.json2DataTransferObject(msgHouseService.queryTenantList(JsonEntityTransform.Object2Json(msRequest)));
		}

		if(dto.getCode() == DataTransferObject.SUCCESS){
			List<MsgHouseListVo> listHouseListVos = dto.parseData("listMsg", new TypeReference<List<MsgHouseListVo>>() {
			});

			if(!Check.NuNCollection(listHouseListVos)){
				for (MsgHouseListVo msgHouseListVo : listHouseListVos) {

					if(!Check.NuNStr(msgHouseListVo.getTenantUid())){
						String uid = msgHouseListVo.getTenantUid();
						String nickName = "房客";
						if(msgType.equals(String.valueOf(UserTypeEnum.TENANT.getUserType()))){
							uid = msgHouseListVo.getLandlordUid();
							nickName = "房东";
						}
						DataTransferObject dtoN =  JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.getCutomerVo(uid));
						if(dtoN.getCode() == DataTransferObject.SUCCESS){
							CustomerVo cust = dtoN.parseData("customerVo", new TypeReference<CustomerVo>() {
							});
							if(!Check.NuNObj(cust)){
								msgHouseListVo.setTenantPicUrl(cust.getUserPicUrl());
								msgHouseListVo.setLanlordPicUrl(cust.getUserPicUrl());
								msgHouseListVo.setNickName(cust.getNickName());
								if(Check.NuNStr(msgHouseListVo.getNickName())){
									msgHouseListVo.setNickName(nickName);
								}
							}
						}
					}
					if(!Check.NuNObj(msgHouseListVo.getRentWay())){
						HouseCheckDto houseCheckDto = new HouseCheckDto();
						houseCheckDto.setRentWay(msgHouseListVo.getRentWay());
						houseCheckDto.setFid(msgHouseListVo.getHouseFid());
						if(msgHouseListVo.getRentWay().intValue() == RentWayEnum.ROOM.getCode()){
							houseCheckDto.setFid(msgHouseListVo.getRoomFid());
						}
						
						DataTransferObject dtoN  = JsonEntityTransform.json2DataTransferObject(this.houseManageService.checkHouseOrRoom(JsonEntityTransform.Object2Json(houseCheckDto)));
						if(dtoN.getCode() == DataTransferObject.SUCCESS){
							String houseName = String.valueOf(dtoN.getData().get("houseName"));
							if(msgHouseListVo.getRentWay().intValue() == RentWayEnum.ROOM.getCode()){
								houseName = String.valueOf(dtoN.getData().get("roomName"));
							}
							msgHouseListVo.setHouseName(houseName);
						}
					}
					
					//查询消息未读数
					if(!Check.NuNStr(msgHouseListVo.getMsgHouseFid())){
						MsgBaseRequest msgBaseRequest = new MsgBaseRequest();
						msgBaseRequest.setMsgHouseFid(msgHouseListVo.getMsgHouseFid());
						if(Integer.parseInt(msgType) == UserTypeEnum.LANDLORD.getUserType()){
							msgBaseRequest.setMsgSenderType(UserTypeEnum.TENANT.getUserType());
						}else{
							msgBaseRequest.setMsgSenderType(UserTypeEnum.LANDLORD.getUserType());
						}
						
						DataTransferObject countDto = JsonEntityTransform.json2DataTransferObject(msgBaseService.queryMsgCountByItem(JsonEntityTransform.Object2Json(msgBaseRequest)));
						if(countDto.getCode() == 0){
							int msgNum = (int) countDto.getData().get("count");
							msgHouseListVo.setUnReadNum(msgNum);
						}
					}
				}
				dto.putValue("listMsg", listHouseListVos);
			}

		}
		return dto;
	}
	
	
	/**
	 * 
	 * 删除IM聊天记录
	 *
	 * @author yd
	 * @created 2016年6月16日 下午5:33:32
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/deleteIm")
	@ResponseBody
	public DataTransferObject deleteImByFid(HttpServletRequest request){
		
		DataTransferObject dto = new DataTransferObject();
		String msgHouseFid = request.getParameter("msgHouseFid");
		String msgType = request.getParameter("msgType");
		if(Check.NuNStr(msgHouseFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误，请刷新重试");
			return dto;
		}
		if(Check.NuNStr(msgType)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("发送人类型不确定");
			return dto;
		}
		MsgHouseEntity msgHouse = new MsgHouseEntity();
		msgHouse.setFid(msgHouseFid);
		if(Integer.parseInt(msgType) == UserTypeEnum.LANDLORD.getUserType()){
			msgHouse.setIsDel(IMIsDelEnum.LAN_DEL.getCode());
		}else if(Integer.parseInt(msgType) == UserTypeEnum.TENANT.getUserType()){
			msgHouse.setIsDel(IMIsDelEnum.TEN_DEL.getCode());
		}
		dto = JsonEntityTransform.json2DataTransferObject(this.msgHouseService.deleteByFid(JsonEntityTransform.Object2Json(msgHouse)));
		return dto;
	}
	
	/**
	 * 
	 * 查询未读消息数目
	 *
	 * @author jixd
	 * @created 2016年7月5日 下午4:20:50
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/getAllUnReadMsgNum")
	@ResponseBody
	public DataTransferObject getAllUnReadMsgNum(HttpServletRequest request){
		DataTransferObject dto = new DataTransferObject();
		String landlordUid = request.getParameter("landlordUid");
		if(Check.NuNStr(landlordUid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房东Uid为空");
			return dto;
		}
		MsgCountRequest msgRequest = new MsgCountRequest();
		msgRequest.setLandlordUid(landlordUid);
		msgRequest.setMsgSenderType(UserTypeEnum.TENANT.getUserType());
		String resultJson = msgBaseService.queryMsgCountByUid(JsonEntityTransform.Object2Json(msgRequest));
		LogUtil.info(logger, "未读消息数目查询返回msg={}", resultJson);
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}
	/**
	 * 
	 * 房东设置消息已读状态（房客发的消息设置成已读）
	 *
	 * @author jixd
	 * @created 2016年7月5日 下午6:33:00
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/setLanMsgRead")
	@ResponseBody
	public DataTransferObject setMsgRead(MsgBaseRequest msgBaseRequest){
		DataTransferObject dto = new DataTransferObject();
		
		if(Check.NuNStr(msgBaseRequest.getMsgHouseFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("消息fid为空");
			return dto;
		}
		if(Check.NuNObj(msgBaseRequest.getMsgSenderType())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("类型为空");
			return dto;
		}
		if(msgBaseRequest.getMsgSenderType() == UserTypeEnum.LANDLORD.getUserType()){
			msgBaseRequest.setMsgSenderType(UserTypeEnum.TENANT.getUserType());
		}else{
			msgBaseRequest.setMsgSenderType(UserTypeEnum.LANDLORD.getUserType());
		}
		return JsonEntityTransform.json2DataTransferObject(msgBaseService.updateByMsgHouseReadFid(JsonEntityTransform.Object2Json(msgBaseRequest)));
	}
	
}
