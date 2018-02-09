package com.ziroom.minsu.troy.message.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.sun.xml.bind.v2.runtime.reflect.opt.Const;
import com.ziroom.minsu.entity.message.SysMsgEntity;
import com.ziroom.minsu.entity.message.SysMsgManagerEntity;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.message.api.inner.SysMsgManagerService;
import com.ziroom.minsu.services.message.api.inner.SysMsgService;
import com.ziroom.minsu.services.message.dto.SysMsgManagerRequest;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.troy.constant.Constant;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.msg.IsReadEnum;
import com.ziroom.minsu.valenum.msg.IsReleaseEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.minsu.valenum.msg.TargetTypeEnum;

/**
 * @author jixd on 2016年4月18日
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("message")
public class SysMsgController {
	
	private static Logger logger = LoggerFactory.getLogger(SysMsgController.class);
	
	@Resource(name="message.sysMsgManagerService")
	private SysMsgManagerService sysMsgManagerService;
	
	@Resource(name="message.sysMsgService")
	private SysMsgService sysMsgService;
	
	@RequestMapping("getSysMsgList")
	@ResponseBody
	public PageResult showList(@ModelAttribute SysMsgManagerRequest sysMsgManagerRequest){
		sysMsgManagerRequest.setIsDel(IsDelEnum.NOT_DEL.getCode());
		
		String json = JsonEntityTransform.Object2Json(sysMsgManagerRequest);
		DataTransferObject resultData = JsonEntityTransform.json2DataTransferObject(sysMsgManagerService.queryMsgPage(json));
		
		PageResult pageResult = new PageResult();
		pageResult.setRows(resultData.getData().get("listSysMsg"));
		pageResult.setTotal(Long.parseLong(resultData.getData().get("total").toString()));
		return pageResult;
	}
	/**
	 * 
	 * TODO
	 *
	 * @author jixd
	 * @created 2016年4月19日 下午6:07:03
	 *
	 * @param request
	 * @param sysMsgManagerEntity
	 * @return
	 */
	@RequestMapping("saveOrUpdateSysMsg")
	public String saveOrUpdateSysMsg(HttpServletRequest request,SysMsgManagerEntity sysMsgManagerEntity){
		
		DataTransferObject resultData = null;
		if(Check.NuNStr(sysMsgManagerEntity.getFid())){
			sysMsgManagerEntity.setCreateUid(UserUtil.getCurrentUserFid());
			sysMsgManagerEntity.setCreateTime(new Date());
			sysMsgManagerEntity.setLastModifyDate(new Date());
			sysMsgManagerEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
			sysMsgManagerEntity.setIsRelease(IsReleaseEnum.NOT_RELEASE.getCode());
			String paramJson = JsonEntityTransform.Object2Json(sysMsgManagerEntity);
			resultData = JsonEntityTransform.json2DataTransferObject(sysMsgManagerService.saveSysMsgManager(paramJson));
		}else{
			sysMsgManagerEntity.setLastModifyDate(new Date());
			sysMsgManagerEntity.setModifyUid(UserUtil.getCurrentUserFid());
			String paramJson = JsonEntityTransform.Object2Json(sysMsgManagerEntity);
			resultData = JsonEntityTransform.json2DataTransferObject(sysMsgManagerService.updateMsg(paramJson));
		}
		String result = (String) resultData.getData().get("result").toString();
		if(Integer.parseInt(result)>0){
			LogUtil.info(logger,"添加成功");
		}else{
			LogUtil.info(logger,"添加失败");
		}
		return "redirect:/message/sysMsgList";
	}
	
	@RequestMapping("addSysMsg")
	public void addSysMsg(HttpServletRequest request){
		String flag = request.getParameter("flag");
		String fid = request.getParameter("fid");
		//编辑模式
		if("1".equals(flag) && !Check.NuNStr(fid)){
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(sysMsgManagerService.findSysMsgManagerByFid(fid));
			request.setAttribute("sysMsgManagerEntity", dto.getData().get("sysMsgManagerEntity"));
		}
	}
	
	@RequestMapping("releaseSysMsg")
	public String releaseSysMsg(String fid){
		if(Check.NuNStr(fid)){
			return "";
		}
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(sysMsgManagerService.findSysMsgManagerByFid(fid));
		final SysMsgManagerEntity sysMsgManagerEntity = dto.parseData("sysMsgManagerEntity", new TypeReference<SysMsgManagerEntity>() {});
		sysMsgManagerEntity.setIsRelease(IsReleaseEnum.RELEASE.getCode());
		String releaseJson = sysMsgManagerService.releaseMsg(JsonEntityTransform.Object2Json(sysMsgManagerEntity));
		DataTransferObject releDto = JsonEntityTransform.json2DataTransferObject(releaseJson);
		Integer count = releDto.parseData("result", new TypeReference<Integer>() {});
		//更新成功才真正发布,异步处理
		if(count > 0){
			new Thread(new Runnable() {
				@Override
				public void run() {
					handMsgType(sysMsgManagerEntity);
				}
			}).start();
			
		}
		
		return "redirect:/message/sysMsgList";
	}
	/**
	 * 跳转列表页
	 * @author jixd on 2016年4月19日
	 */
	@RequestMapping("sysMsgList")
	public void sysMsgList(){}
	
	/**
	 * 处理各种类型消息
	 * @author jixd on 2016年4月19日
	 */
	private void handMsgType(SysMsgManagerEntity sysMsgManagerEntity){
		int type = sysMsgManagerEntity.getMsgTargetType();
		List<SysMsgEntity> sysMsgList = null;
		String resultJson = "";
		if(type == TargetTypeEnum.SINGLE_USER.getCode()){
			SysMsgEntity sysMsgEntity = sendToSingle(sysMsgManagerEntity);
			resultJson = sysMsgService.saveSysMsg(JsonEntityTransform.Object2Json(sysMsgEntity));
		}else if(type == TargetTypeEnum.ALL_LANDLORD.getCode()){
			sysMsgList = sendToAllLandlord(sysMsgManagerEntity);
		}else if(type == TargetTypeEnum.ALL_TENANT.getCode()){
			sysMsgList = sendToAllTenant(sysMsgManagerEntity);
		}else if(type == TargetTypeEnum.ALL_USER.getCode()){
			sysMsgList = sendToAllUser(sysMsgManagerEntity);
		}else{
			LogUtil.error(logger, "未知类型");
		}
		if(sysMsgList != null){
			resultJson = sysMsgService.saveSysMsgBatch(JsonEntityTransform.Object2Json(sysMsgList));
		}
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		Integer count = dto.parseData("result", new TypeReference<Integer>() {});
		if(count>0){
			LogUtil.info(logger, "消息发布成功，发布消息"+count+"条，消息类型："+TargetTypeEnum.getTargetTypeEnum(sysMsgManagerEntity.getMsgTargetType()));
		}else{
			LogUtil.error(logger, "消息发布失败，没有发布消息或者异常");
		}
	}
	
	private SysMsgEntity sendToSingle(SysMsgManagerEntity sysMsgManagerEntity){
		SysMsgEntity sysMsgEntity = new SysMsgEntity();
		sysMsgEntity.setCreateTime(new Date());
		sysMsgEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
		sysMsgEntity.setIsRead(IsReadEnum.UNREAD.getCode());
		sysMsgEntity.setMsgTargetType(sysMsgManagerEntity.getMsgTargetType());
		sysMsgEntity.setMsgTmpType(MessageTemplateCodeEnum.CUSTOM.getCode());
		sysMsgEntity.setMsgContent(sysMsgManagerEntity.getMsgContent());
		sysMsgEntity.setMsgTitle(sysMsgManagerEntity.getMsgTitle());
		sysMsgEntity.setMsgTargetUid(sysMsgManagerEntity.getMsgTargetUid());
		return sysMsgEntity;
	}
	
	/**
	 * 
	 * 所有租客
	 *
	 * @author jixd
	 * @created 2016年4月19日 下午6:09:49
	 *
	 * @param sysMsgManagerEntity
	 * @return
	 */
	public List<SysMsgEntity> sendToAllLandlord(SysMsgManagerEntity sysMsgManagerEntity){
		List<SysMsgEntity> list = new ArrayList<>();
		for(int i = 0;i<40;i++){
			String targetuid = String.valueOf(Constant.COUNT_BEGIN_NUM+i);
			
			SysMsgEntity sysMsgEntity = new SysMsgEntity();
			sysMsgEntity.setCreateTime(new Date());
			sysMsgEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
			sysMsgEntity.setIsRead(IsReadEnum.UNREAD.getCode());
			sysMsgEntity.setMsgTargetType(sysMsgManagerEntity.getMsgTargetType());
			sysMsgEntity.setMsgTmpType(MessageTemplateCodeEnum.CUSTOM.getCode());
			sysMsgEntity.setMsgContent(sysMsgManagerEntity.getMsgContent());
			sysMsgEntity.setMsgTitle(sysMsgManagerEntity.getMsgTitle());
			sysMsgEntity.setMsgTargetUid(targetuid);
			list.add(sysMsgEntity);
		}
		return list;
	}
	
	/**
	 * 
	 * 所有租客
	 *
	 * @author jixd
	 * @created 2016年4月19日 下午6:08:58
	 *
	 * @param sysMsgManagerEntity
	 * @return
	 */
	public List<SysMsgEntity> sendToAllTenant(SysMsgManagerEntity sysMsgManagerEntity){
		
		return null;
	}
	
	/**
	 * 
	 * 所有用户
	 *
	 * @author jixd
	 * @created 2016年4月19日 下午6:09:20
	 *
	 * @param sysMsgManagerEntity
	 * @return
	 */
	public List<SysMsgEntity> sendToAllUser(SysMsgManagerEntity sysMsgManagerEntity){
		
		return null;
	}
}
