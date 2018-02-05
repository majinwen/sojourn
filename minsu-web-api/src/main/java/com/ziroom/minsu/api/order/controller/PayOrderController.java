package com.ziroom.minsu.api.order.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.constant.LoginAuthConst;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.order.dto.ToPayApiDto;
import com.ziroom.minsu.api.order.service.CallOrderPayService;
import com.ziroom.minsu.services.common.constant.CatConstant;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.order.dto.ToPayRequest;
import com.ziroom.minsu.valenum.order.OrderSourceEnum;
import com.ziroom.minsu.valenum.pay.PayErrorCodeEnum;
import com.ziroom.minsu.valenum.pay.PayErrorOldCodeEnum;

/**
 * <p>
 * 支付
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年4月30日
 * @since 1.0
 * @version 1.0
 */
@RequestMapping("/pay")
@Controller
public class PayOrderController {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(PayOrderController.class);

	@Resource(name = "pay.callOrderPayService")
	private CallOrderPayService callOrderPayService;

	/**
     * <p>描述:</p>
     * @author afi
     * <p>&nbsp; &nbsp; &nbsp; &nbsp;<b>去支付</b></p>
     * <p>请求示例：<b>auth/pay/toPay</b></p>
     * <p>返回结果示例:<b>
     *     {"code":0,"msg":"","data":{"status":"fail","error_message":"失败","error_code":1}}
     * </b></p>
     * <div>
     * <table width='100%' cellpadding='1' cellspacing='1' bgcolor='gray'>
     *     <tr bgcolor="#DDDDDD" style="font-size: 12px; ">
     *         <td colspan="4" textAlign="center" >入参明细</td>
     *     </tr>
     *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
     *         <td>参数名</td>
     *         <td>类型</td>
     *         <td>是否必须(是或否)</td>
     *         <td>含义</td>
     *     </tr>
     *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
     *         <td>toPayType</td>
     *         <td>Integer</td>
     *         <td>是</td>
     *         <td>支付类型:1 正常支付 2：罚款支付</td>
     *     </tr>
     *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
     *         <td>cityCode</td>
     *         <td>String</td>
     *         <td>是</td>
     *         <td>城市编码</td>
     *     </tr>
     *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
     *         <td>paySn</td>
     *         <td>String</td>
     *         <td>是</td>
     *         <td>订单编号/罚款单号</td>
     *     </tr>
     *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
     *         <td>payType</td>
     *         <td>String</td>
     *         <td>是</td>
     *         <td>支付方式 如：微信:wx 支付宝 zfb</td>
     *     </tr>
     *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
     *         <td>payType</td>
     *         <td>String</td>
     *         <td>是</td>
     *         <td>支付方式 如：银联：yl 微信:wx 支付宝 zfb</td>
     *     </tr>
     *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
     *         <td>sourceType</td>
     *         <td>String</td>
     *         <td>是</td>
     *         <td>设备表示：PC、ios、ad、app</td>
     *     </tr>
     * </table>
     *
     * <table width='100%' cellpadding='1' cellspacing='1' bgcolor='gray'>
     *     <tr bgcolor="#DDDDDD" style="font-size: 12px; ">
     *         <td colspan="4" textAlign="center">返回结果明细</td>
     *     </tr>
     *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
     *         <td>参数名</td>
     *         <td>类型</td>
     *         <td>是否必须(是或否)</td>
     *         <td>含义</td>
     *     </tr>
     *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
     *         <td>data域</td>
     *         <td>Json</td>
     *         <td>是</td>
     *         <td>例：{\"prepayid\":\"201511161521351939688\"}</td>
     *     </tr>
     * </table>
     * </div>
     * @return
     */
	@RequestMapping("${LOGIN_AUTH}/toPay")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> toPay(HttpServletRequest request) {
		try{
			//获取版本信息,处理老版本问题
			String version = (String)request.getAttribute(LoginAuthConst.VERSION);
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "【去支付】请求入参params：{}", paramJson);
			//统计点击去支付数量
			Transaction payTran = Cat.newTransaction("PayOrderController", CatConstant.TO_PAY);
            try {
                Cat.logMetricForCount("点击去支付数量");
                payTran.setStatus(Message.SUCCESS);
            } catch(Exception ex) {
                Cat.logError("点击去支付数量 打点异常", ex);
                payTran.setStatus(ex);
            } finally {
            	payTran.complete();
            }
			ToPayApiDto toPayApi = JsonEntityTransform.json2Object(paramJson, ToPayApiDto.class);
			ToPayRequest toPayRequest = new ToPayRequest();
			BeanUtils.copyProperties(toPayApi, toPayRequest);
			toPayRequest.setUserUid(toPayApi.getUid());
			DataTransferObject resultDto = callOrderPayService.toPay(toPayRequest);
	        LogUtil.info(LOGGER, "【去支付】 获取去支付的信息完毕：{}", resultDto.toJsonString());
			//获取不同版本的不同返回结果
			return this.transVersionRstInfo(resultDto,toPayApi.getSourceType(),version);

		}catch(Exception e){
			LogUtil.error(LOGGER, "【去支付】错误, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}


	/**
	 * 获取不同版本的不同返回结果
	 * @param resultDto
	 * @param sourceType
	 * @param version
     * @return
     */
	private ResponseEntity<ResponseSecurityDto> transVersionRstInfo(DataTransferObject resultDto,String sourceType,String version){
		if (resultDto.getCode() == DataTransferObject.SUCCESS){
			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(resultDto), HttpStatus.OK);
		}
		Integer errorCode = ValueUtil.getintValue(resultDto.getMsg());
		if (errorCode == 0){
			errorCode = PayErrorCodeEnum.common105.getCode();
		}

		/**
		 * 主要是兼容了 历史版本的……
		 * 要是想不明白 你懂得
		 */
		boolean old = false;
		//对ios老版本的处理
		if (Integer.valueOf(sourceType)==OrderSourceEnum.IOS.getCode() && SysConst.IOS_OLD_VERSION.equals(version)) {
			old = true;
		}
		//对安卓老版本的处理
		if(Integer.valueOf(sourceType)==OrderSourceEnum.ANDROID.getCode() && !Check.NuNStr(version) &&Integer.valueOf(version) <= SysConst.AD_OLD_VERSION) {
			old = true;
		}
		PayErrorCodeEnum payErrorCodeEnum = PayErrorCodeEnum.getByCode(errorCode,old);

		if(old){
			resultDto.setMsg(ValueUtil.getStrValue(payErrorCodeEnum.getCode()));
		}else {
			resultDto.setMsg(ValueUtil.getStrValue(payErrorCodeEnum.getMessage()));
		}
		LogUtil.info(LOGGER, "【去支付】获取去支付的信息完毕：{}", resultDto.toJsonString());
		return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(resultDto), HttpStatus.OK);

	}
}
