package com.ziroom.minsu.mapp.customer.controller;

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

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.entity.evaluate.StatsHouseEvaEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.mapp.common.constant.MappMessageConst;
import com.ziroom.minsu.mapp.common.logic.ParamCheckLogic;
import com.ziroom.minsu.mapp.common.logic.ValidateResult;
import com.ziroom.minsu.mapp.customer.vo.LandlordDto;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerDetailImageVo;
import com.ziroom.minsu.services.customer.entity.LandlordIntroduceVo;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.evaluate.dto.StatsHouseEvaRequest;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import com.ziroom.minsu.services.search.dto.LandHouseRequest;
import com.ziroom.minsu.valenum.customer.CustomerPicTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * <p>客户相关信息接口</p>
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

@RequestMapping("landlord")
@Controller
public class LandlordInfoController {
	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LandlordInfoController.class);
	
	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;
	
	@Resource(name = "evaluate.evaluateOrderService")
	private EvaluateOrderService evaluateOrderService;
	
	@Resource(name="house.houseIssueService")
	private HouseIssueService houseIssueService;

	@Resource(name="mapp.messageSource")
	private MessageSource messageSource;

	@Resource(name="mapp.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;
	
	@Resource(name = "search.searchServiceApi")
    private SearchService searchService;
	
	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;
	
	@Value("#{'${default_head_size}'.trim()}")
	private String default_head_size;
	
	@Value("#{'${default_icon_size}'.trim()}")
	private String defaultIconSize;
	
	@Value("#{'${USER_DEFAULT_PIC_URL}'.trim()}")
	private String USER_DEFAULT_PIC_URL;
	
	
	/**
	 * 
	 * 房东个人信息接口
	 *
	 * @author liujun
	 * @created 2016年6月15日
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${NO_LOGIN_AUTH}/landlordInfo")
	public String landlordIntroduce(HttpServletRequest request, LandlordDto landlordDto){
		DataTransferObject dto = new DataTransferObject();
		try{
			String paramJson = JsonEntityTransform.Object2Json(landlordDto);
			LogUtil.info(LOGGER, "参数:{}", paramJson);
			
			ValidateResult<LandlordDto> validateResult =
	                paramCheckLogic.checkParamValidate(paramJson, LandlordDto.class);
	        if (!validateResult.isSuccess()) {
	        	LogUtil.error(LOGGER, "错误信息:{}", validateResult.getDto().getMsg());
	        	return "error/error";
	        }
	        request.setAttribute("landlordDto", landlordDto);
	        //判断是否是预览按钮跳转
			String sourceFrom=request.getParameter("sourceFrom");
			if(!Check.NuNStr(sourceFrom) && "previewBtn".equals(sourceFrom)){
				request.setAttribute("sourceFrom", sourceFrom);
			}
			
			String customerBaseJson = customerMsgManagerService.getCustomerDetailImage(landlordDto.getLandlordUid());
			dto = JsonEntityTransform.json2DataTransferObject(customerBaseJson);
			if(dto.getCode() != DataTransferObject.SUCCESS){
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.LANDLORD_INFO_NULL));
				LogUtil.error(LOGGER, "error:{}", dto.toJsonString());
				return "error/error";
			}
			CustomerDetailImageVo customerMsg = dto.parseData("customerImageVo", new TypeReference<CustomerDetailImageVo>() {});
			
			LandlordIntroduceVo introduceVo = new LandlordIntroduceVo();
			List<CustomerPicMsgEntity> customerPicList = customerMsg.getCustomerPicList();
			introduceVo.setNickName(customerMsg.getNickName());
			introduceVo.setHeadPicUrl(USER_DEFAULT_PIC_URL);
			if(!Check.NuNCollection(customerPicList)){
				for(CustomerPicMsgEntity picMsgEntity : customerPicList){
					if(picMsgEntity.getPicType() == CustomerPicTypeEnum.YHTX.getCode()){
						if(!Check.NuNStr(picMsgEntity.getPicServerUuid())){
							//如果是用户头像
							String headPicUrl = PicUtil.getFullPic(picBaseAddrMona, picMsgEntity.getPicBaseUrl(), picMsgEntity.getPicSuffix(), default_head_size);
							introduceVo.setHeadPicUrl(headPicUrl);
						}
					}
				}
			}
			
			//查询房东的个人介绍
			dto = JsonEntityTransform.json2DataTransferObject(
					this.customerMsgManagerService.selectCustomerExtByUid(landlordDto.getLandlordUid()));
			if(dto.getCode() == DataTransferObject.SUCCESS){
				CustomerBaseMsgExtEntity customerBaseMsgExt = dto.parseData("customerBaseMsgExt", new TypeReference<CustomerBaseMsgExtEntity>() {});
				if(!Check.NuNObj(customerBaseMsgExt)){
					introduceVo.setIntroduce(customerBaseMsgExt.getCustomerIntroduce());
				}
			} else {
				LogUtil.error(LOGGER, "customerMsgManagerService#selectCustomerExtByUiderror接口调用失败,landlordUid={},结果:{}",
						landlordDto.getLandlordUid(), dto.toJsonString());
			}
			
			
			//如果是合租的话 先查询到houseBaseFid
			if(landlordDto.getRentWay() == RentWayEnum.ROOM.getCode()){
				String houseRoomFid = landlordDto.getHouseFid();
				String roomMsgJson = houseIssueService.searchHouseRoomMsgByFid(houseRoomFid);
				dto = JsonEntityTransform.json2DataTransferObject(roomMsgJson);
				if(dto.getCode() == DataTransferObject.SUCCESS){
					HouseRoomMsgEntity roomMsgEntity = dto.parseData("obj",
							new TypeReference<HouseRoomMsgEntity>() {});
					if(Check.NuNObj(roomMsgEntity)){
						landlordDto.setHouseFid(roomMsgEntity.getHouseBaseFid());
					}
				} else {
					LogUtil.error(LOGGER, "houseIssueService#searchHouseRoomMsgByFid接口调用失败,houseRoomFid={},结果:{}",
							houseRoomFid, dto.toJsonString());
				}
			}
			
			//房源的评分
			StatsHouseEvaRequest statsHouseEvaRequest = new StatsHouseEvaRequest();
			statsHouseEvaRequest.setHouseFid(landlordDto.getHouseFid());
			
			String statsHouseJson = evaluateOrderService.queryStatsHouseEvaByCondition(JsonEntityTransform.Object2Json(statsHouseEvaRequest));
			dto = JsonEntityTransform.json2DataTransferObject(statsHouseJson);
			if(dto.getCode() == DataTransferObject.SUCCESS){
				List<StatsHouseEvaEntity> statsHouseEvaList = dto.parseData("listStatsHouseEvaEntities", 
						new TypeReference<List<StatsHouseEvaEntity>>() {});
				if(!Check.NuNCollection(statsHouseEvaList)){
					StatsHouseEvaEntity statsHouseEvaEntity = statsHouseEvaList.get(0);
					Float houseCleanAva = statsHouseEvaEntity.getHouseCleanAva();
					Float desMatchAva = statsHouseEvaEntity.getDesMatchAva();
					Float safeDegreeAva = statsHouseEvaEntity.getSafeDegreeAva();
					Float trafPosAva = statsHouseEvaEntity.getTrafPosAva();
					Float costPerforAva = statsHouseEvaEntity.getCostPerforAva();
					
					Float totalAva = (houseCleanAva + desMatchAva + safeDegreeAva + trafPosAva + costPerforAva)/5;
					introduceVo.setEva(totalAva);
				}
			} else {
				LogUtil.error(LOGGER, "evaluateOrderService#queryStatsHouseEvaByCondition接口调用失败,参数:{},结果:{}",
						JsonEntityTransform.Object2Json(statsHouseEvaRequest), dto.toJsonString());
			}
			
			request.setAttribute("introduceVo", introduceVo);
			LogUtil.info(LOGGER, "返回结果:{}", dto.toJsonString());
			
			/*LandHouseRequest landRequest = new LandHouseRequest();
			landRequest.setLandlordUid(landlordDto.getLandlordUid());
			this.landList(request, landRequest);*/
		}catch(Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			LogUtil.error(LOGGER, "error:{}",e);
		}
		return "share/landlordInfo";
	}
	
	/**
     * 房源的搜索
     * 
     * @param request
     * @param response
     * @return
     */
    public void landList(HttpServletRequest request, LandHouseRequest landRequest) {
        //参数校验
        if(Check.NuNStr(landRequest.getLandlordUid())){
			LogUtil.error(LOGGER, "参数:{}",
					MessageSourceUtil.getChinese(messageSource, MappMessageConst.LANDLORDUID_NULL));
			return;
        }
        try {
            //获取搜索结果
			String resultJson = searchService.getLandHouseList(defaultIconSize,
					JsonEntityTransform.Object2Json(landRequest));
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if(dto.getCode() == DataTransferObject.ERROR){
            	LogUtil.error(LOGGER, "searchService#getLandHouseList调用接口失败,参数:{},结果:{}", 
            			JsonEntityTransform.Object2Json(landRequest), dto.toJsonString());
            }
            Integer total = Integer.valueOf(dto.getData().get("total").toString());
            List<Object> list =  dto.parseData("list", new TypeReference<List<Object>>() {});
            request.setAttribute("total", total);
            request.setAttribute("list", list);
            LogUtil.info(LOGGER, "返回结果:{}", JsonEntityTransform.Object2Json(list));
        } catch (Exception e) {
            LogUtil.error(LOGGER, "landList par :{} e:{}", JsonEntityTransform.Object2Json(landRequest), e);
        }
    }
	
}
